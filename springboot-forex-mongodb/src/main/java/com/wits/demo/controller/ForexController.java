package com.wits.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wits.demo.service.ForexService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/forex")
@Tag(name = "外匯查詢平台：Forex")
public class ForexController {

	@Autowired
	ForexService forexService;

	@Operation(summary = "1. 呼叫 API，取得外匯成交資料後insert到DB", description = "1. 呼叫 API，取得外匯成交資料後insert到DB")
	@GetMapping(path = "/getJsonByCallingApiThenInsertDB")
	public void getJsonByCallingApiThenInsertDB() {
		List<Object> forexDataList = forexService.getForexData();
		forexService.insertToMongoDb(forexDataList);
	}
}
