package com.example.demo.db.initializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInDbInit {

    @Bean(initMethod = "init")
    TestDataInitializer testDataInitializer() {
        return new TestDataInitializer();
    }
}
