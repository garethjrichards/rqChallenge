package com.example.rqchallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RqChallengeApplication {

    @Value("${rq.client.uri}")
    private String uri;

    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

    /**
     * Build a RestTemplate for accessing RestApiExample's API. User-Agent required to get around 429 Too many request errors
     *
     * @param builder RestTemplateBuilder from spring
     * @return A RestTemplate to call RestApiExample's APIs
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(uri)
                .defaultHeader("User-Agent", "RqChallenge/1.0")
                      .build();
    }
}
