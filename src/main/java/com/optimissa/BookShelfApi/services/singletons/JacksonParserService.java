package com.optimissa.BookShelfApi.services.singletons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonParserService {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
