package com.wits.demo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wits.demo.dto.Collection;
import com.wits.demo.service.ForexService;

@SpringBootTest
@RunWith(value = Parameterized.class)
@TestInstance(Lifecycle.PER_CLASS)
class ForexServiceImplTests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Autowired
	ForexService forexService;

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
	@MethodSource
	void testGetForexData(Object data) {
		assertThat((Collection) data instanceof Collection).isTrue();
		System.out.println("testGetForexData: Date: " + ((Collection) data).getDate());
	}

	@Test
	void testInsertToMongoDb() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateQueryRequest() {
		fail("Not yet implemented");
	}

	@Test
	void testQueryDb() {
		fail("Not yet implemented");
	}

}
