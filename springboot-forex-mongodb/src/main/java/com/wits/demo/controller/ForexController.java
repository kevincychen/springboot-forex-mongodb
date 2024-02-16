package com.wits.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wits.demo.service.ForexService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
public class ForexController {

	@Autowired
	ForexService forexService;

	@Tag(name = "1. 呼叫 API，取得外匯成交資料後insert到DB")
	@RequestMapping(value = "/getJsonByCallingApiThenInsertDB", method = RequestMethod.GET)
	public void getJsonByCallingApiThenInsertDB() {
		List<Object> forexDataList = forexService.getForexData();
		forexService.insertToMongoDb(forexDataList);
	}
}
