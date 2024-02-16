package com.wits.demo.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wits.demo.service.ForexService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "A Handler for calling api of getting forex data daily then doing mongodb insertion.")
@Component
public class ForexEventHandler {

	@Autowired
	ForexService forexService;

	@Scheduled(cron = "0 0 18 * * ?", zone = "Asia/Taipei")
	public void getJsonByCallingApiThenInsertDBTask() {
		List<Object> forexDataList = forexService.getForexData();
		forexService.insertToMongoDb(forexDataList);
	}

}
