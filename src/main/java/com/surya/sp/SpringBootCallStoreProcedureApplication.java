package com.surya.sp;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCallStoreProcedureApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCallStoreProcedureApplication.class, args);
	}
	
	@ConfigurationProperties(prefix="spring.datasource")
	@Bean(name = "data_oracle")
	public DataSource getDataSource() {
		return DataSourceBuilder
				.create()
				.build();
	}

}
