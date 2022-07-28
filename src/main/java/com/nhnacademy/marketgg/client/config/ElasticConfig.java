package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.utils.KoreanToEnglishTranslator;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ElasticConfig {

    @Bean
    public KoreanToEnglishTranslator converter() {
        return new KoreanToEnglishTranslator();
    }

    @Bean
    public JSONParser parser() {
        return new JSONParser();
    }

}
