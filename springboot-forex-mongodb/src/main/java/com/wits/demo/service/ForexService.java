package com.wits.demo.service;

import java.util.List;

public interface ForexService {

	List<Object> getForexData();

	void insertToMongoDb(List<Object> forexDataList);

}