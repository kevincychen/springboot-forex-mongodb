package com.wits.demo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wits.demo.dto.Collection;
import com.wits.demo.dto.QueryDto;
import com.wits.demo.dto.QueryForexResult;
import com.wits.demo.dto.QueryResponse;
import com.wits.demo.dto.QueryResponseSuccess;
import com.wits.demo.repository.CollectionRepository;
import com.wits.demo.service.ForexService;

@Service
public class ForexServiceImpl implements ForexService {

	@Autowired
	private CollectionRepository collectionRepo;

	@Override
	public List<Object> getForexData() {
		String url = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";

		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);

		Object[] forexData = restTemplate.getForObject(url, Object[].class);

		return Arrays.asList(forexData);
	}

	@Override
	public void insertToMongoDb(List<Object> forexDataList) {
		for (int i = 0; i < forexDataList.size(); i++) {
			// creating date format
			SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// parsing string to date using parse() method
			Date parsedDate = null;
			ObjectMapper objectMapper = new ObjectMapper();
			Collection collectionTemp = null;
			try {
				collectionTemp = objectMapper.readValue(objectMapper.writeValueAsString(forexDataList.get(i)),
						Collection.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			try {
				parsedDate = originalFormat.parse(collectionTemp.getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Collection collection = new Collection();
			collection.setDate(dateFormat.format(parsedDate));
			collection.setUsdNtd(collectionTemp.getUsdNtd());
			collectionRepo.save(collection);
		}

	}

	@Override
	public boolean validateQueryRequest(QueryDto queryDto) {
		SimpleDateFormat queryDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = queryDateFormat.parse(queryDto.getStartDate());
			endDate = queryDateFormat.parse(queryDto.getEndDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String startDateStr = queryDateFormat.format(startDate);
		int startDateInt = Integer
				.parseInt(startDateStr.split("/")[0] + startDateStr.split("/")[1] + startDateStr.split("/")[2]);
		String endDateStr = queryDateFormat.format(endDate);
		int endDateInt = Integer
				.parseInt(endDateStr.split("/")[0] + endDateStr.split("/")[1] + endDateStr.split("/")[2]);

		// 一年前
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date oneYearAgoDate = calendar.getTime();
		String oneYearAgoDateStr = queryDateFormat.format(oneYearAgoDate);
		int oneYearAgoDateInt = Integer.parseInt(
				oneYearAgoDateStr.split("/")[0] + oneYearAgoDateStr.split("/")[1] + oneYearAgoDateStr.split("/")[2]);

		// 昨天
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		String yesterdayStr = queryDateFormat.format(yesterday);
		int yesterdayInt = Integer
				.parseInt(yesterdayStr.split("/")[0] + yesterdayStr.split("/")[1] + yesterdayStr.split("/")[2]);

		if ((startDateInt >= oneYearAgoDateInt && startDateInt <= yesterdayInt)
				&& (endDateInt >= oneYearAgoDateInt && endDateInt <= yesterdayInt)) {
			return true;
		}

		return false;
	}

	@Override
	public QueryResponseSuccess queryDb(QueryDto queryDto) {
		SimpleDateFormat queryDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Collection> queryCollectionList = collectionRepo.findAll();
		QueryResponseSuccess queryResponseSuccess = new QueryResponseSuccess();
		QueryResponse queryResponse = new QueryResponse();
		queryResponse.setCode("0000");
		queryResponse.setMessage("成功");
		queryResponseSuccess.setError(queryResponse);
		List<QueryForexResult> queryQueryForexResultList = new ArrayList<>();
		for (Collection collection : queryCollectionList) {
			Date collectionDate = null;
			try {
				collectionDate = dateFormat.parse(collection.getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String collectionDateStr = queryDateFormat.format(collectionDate);
			int collectionDateInt = Integer.parseInt(collectionDateStr.split("/")[0] + collectionDateStr.split("/")[1]
					+ collectionDateStr.split("/")[2]);
			int fromDateInt = Integer.parseInt(queryDto.getStartDate().split("/")[0]
					+ queryDto.getStartDate().split("/")[1] + queryDto.getStartDate().split("/")[2]);
			int toDateInt = Integer.parseInt(queryDto.getEndDate().split("/")[0] + queryDto.getEndDate().split("/")[1]
					+ queryDto.getEndDate().split("/")[2]);
			if ((toDateInt >= collectionDateInt) && (fromDateInt <= collectionDateInt)) {
				QueryForexResult queryForexResult = new QueryForexResult();
				queryForexResult.setDate(collection.getDate());
				queryForexResult.setUsd(collection.getUsdNtd());
				queryQueryForexResultList.add(queryForexResult);
			}
		}
		queryResponseSuccess.setCurrency(queryQueryForexResultList);
		return queryResponseSuccess;
	}

}