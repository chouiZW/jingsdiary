package com.diary.calendar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.diary.calendar.mapper")
@SpringBootApplication(scanBasePackages = "com.diary.calendar")
public class DiaryCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaryCalendarApplication.class, args);
	}

}
