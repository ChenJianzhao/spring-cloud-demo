package com.example.jackson;

import com.example.jackson.entity.People;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.time.*;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
public class JacksonApplicationTests {

	private ObjectMapper mapper;

	@Test
	public void contextLoads() {
	}

	@Before
	public void configuration() {
		mapper = new ObjectMapper();

		// SerializationFeature for changing how JSON is written

		// to enable standard indentation ("pretty-printing"):
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// to allow serialization of "empty" POJOs (no properties to serialize)
		// (without this setting, an exception is thrown in those cases)
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// to write java.util.Date, Calendar as number (timestamp):
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// DeserializationFeature for changing how JSON is read as POJOs:

		// to prevent exception when encountering unknown property:
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// to allow coercion of JSON empty String ("") to null Object value:
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		// 注册自定义 module 支持
		// JavaTimeModule java8 LocalDateTime 序列化支持
		mapper.registerModule(new JavaTimeModule());
	}


	@Test
	public void JacksonTest() throws IOException {
		People people = new People();
		people.setAdult(true);
		people.setAge(18);
		people.setName("Charles Chen");
		people.setBirthday(LocalDate.of(1992, Month.JULY, 11));
		people.setBornTime(people.getBirthday().atTime(9, 0,0));
		people.setCreateTime(Instant.now());

		String peopleString = mapper.writeValueAsString(people);

		System.out.println(peopleString);

		JsonNode jsonNode = mapper.readTree(peopleString);
		Assert.assertTrue(jsonNode.get("adult").asBoolean());
		Assert.assertEquals(18, jsonNode.get("age").asInt());
		Assert.assertEquals("Charles Chen", jsonNode.get("name").asText());

	}

}
