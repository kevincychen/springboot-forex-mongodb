package com.wits.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wits.demo.dto.QueryDto;
import com.wits.demo.dto.QueryResponse;
import com.wits.demo.dto.QueryResponseFailed;
import com.wits.demo.service.ForexService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/forex")
@Tag(name = "外匯查詢平台：Forex")
public class ForexController<T> {

	@Autowired
	ForexService forexService;

	@Operation(summary = "1. 呼叫 API，取得外匯成交資料後insert到DB", description = "1. 呼叫 API，取得外匯成交資料後insert到DB")
	@GetMapping(path = "/getJsonByCallingApiThenInsertDB")
	public void getJsonByCallingApiThenInsertDB() {
		List<Object> forexDataList = forexService.getForexData();
		forexService.insertToMongoDb(forexDataList);
	}

	@SuppressWarnings("unchecked")
	@Operation(summary = "2. 提供一forex API，從DB取出日期區間內美元/台幣的歷史資料", description = "2. 提供一forex API，從DB取出日期區間內美元/台幣的歷史資料")
	@PostMapping("/query")
	public T query(@RequestBody QueryDto queryDto) {
		if (forexService.validateQueryRequest(queryDto)) {
			return (T) forexService.queryDb(queryDto);
		} else {
			QueryResponseFailed queryResponseFailed = new QueryResponseFailed();
			QueryResponse queryResponse = new QueryResponse();
			queryResponse.setCode("E001");
			queryResponse.setMessage("日期區間不符");
			queryResponseFailed.setError(queryResponse);
			return (T) queryResponseFailed;
		}
	}
}
