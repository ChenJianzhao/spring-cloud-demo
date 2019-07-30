package com.example.jackson.entity;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Data
public class People {

	private String name;
	private Integer age;
	private boolean adult;
	private LocalDate birthday;
	private LocalDateTime bornTime;
	private Instant createTime;
}
