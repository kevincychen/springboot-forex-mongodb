package com.wits.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wits.demo.dto.Collection;
import com.wits.demo.dto.QueryDto;
import com.wits.demo.dto.QueryResponseSuccess;
import com.wits.demo.repository.CollectionRepository;
import com.wits.demo.service.ForexService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ForexServiceImplTests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Autowired
	ForexService forexService;

	@Autowired
	private CollectionRepository collectionRepo;

	List<Object> testGetForexData() {
		List<Object> objectList = forexService.getForexData();
		List<Object> resultList = new ArrayList<>();
		for (int i = 0; i < objectList.size(); i++) {
			ObjectMapper objectMapper = new ObjectMapper();
			Collection collectionTemp = null;
			try {
				collectionTemp = objectMapper.readValue(objectMapper.writeValueAsString(objectList.get(i)),
						Collection.class);
				resultList.add(collectionTemp);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	@DisplayName("test_get_forex_data")
	@ParameterizedTest
	@MethodSource("testGetForexData")
	void testGetForexData(Object data) {
		assertThat((Collection) data instanceof Collection).isTrue();
		System.out.println("testGetForexData: Date: " + ((Collection) data).getDate());
	}

	@Test
	void testInsertToMongoDb() {
		Collection collection = new Collection();
		collection.setDate("20240102");
		collection.setUsdNtd("30.866");
		collection.setRmbNtd("4.325685");
		collection.setEurUsd("1.1031");
		collection.setUsdHkd("141.345");
		collection.setUsdRmb("7.14795");
		collection.setUsdZar("18.54865");
		collection.setNzdUsd("0.6264");
		List<Object> collectionList = new ArrayList<>();
		collectionList.add(collection);
		forexService.insertToMongoDb(collectionList);
		List<Collection> resultList = collectionRepo.findAll();
		int index = 0;
		for (index = 0; !CollectionUtils.isEmpty(resultList) && index < resultList.size(); index++) {
			if("30.866".equals(resultList.get(index).getUsdNtd())) {
				assertEquals("30.866", resultList.get(index).getUsdNtd());
				break;
			}
		}
	}

	@Test
	void testValidateQueryRequest() {
		QueryDto queryDto = new QueryDto();
		// 比一年前還早的起始時間
		queryDto.setStartDate("2023/2/18");
		queryDto.setEndDate("2024/2/18");
		queryDto.setCurrency("usd");
		assertEquals(false, forexService.validateQueryRequest(queryDto));
	}

	@Test
	void testQueryDb() {
		QueryDto queryDto = new QueryDto();
		queryDto.setStartDate("2024/01/02");
		queryDto.setEndDate("2024/01/02");
		queryDto.setCurrency("usd");
		QueryResponseSuccess queryResponseSuccess = forexService.queryDb(queryDto);
		assertEquals("0000", queryResponseSuccess.getError().getCode());
	}

}
