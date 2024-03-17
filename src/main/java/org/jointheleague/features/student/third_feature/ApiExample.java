package org.jointheleague.features.student.third_feature;

import org.springframework.web.reactive.function.client.WebClient;

public class ApiExample {
member variables:
private WebClient webClient;
private static final String baseUrl = "numbersapi.com/";

public ApiExample() {
    this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
}
public String[] getThing() {
    Wrapper thing = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .queryParam(“your parameters”)
                    .build())
            .retrieve()
            .bodyToMono(Wrapper.class)
            .block();

    return {//whatever you’re returning};


    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
    }