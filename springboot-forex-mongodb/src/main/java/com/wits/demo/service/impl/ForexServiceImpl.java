package com.wits.demo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

}