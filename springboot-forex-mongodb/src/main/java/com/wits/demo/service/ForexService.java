package com.wits.demo.service;

import java.util.List;

import com.wits.demo.dto.QueryDto;
import com.wits.demo.dto.QueryResponseSuccess;

public interface ForexService {

	List<Object> getForexData();

	void insertToMongoDb(List<Object> forexDataList);

	boolean validateQueryRequest(QueryDto queryDto);

	QueryResponseSuccess queryDb(QueryDto queryDto);

}