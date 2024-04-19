package org.jointheleague.features.student.third_feature;

import org.jointheleague.features.student.third_feature.data_transfer_objects.NumFact;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ApiExample {

private WebClient webClient;
private static final String baseUrl = "http://numbersapi.com";

public ApiExample() {
    this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
}
public NumFact getNumFact(String number) {
    Mono<NumFact> numMono = webClient.get()
            .uri("/" + number)
            .retrieve()
            .bodyToMono(NumFact.class);
    return numMono.block();
}
public String findNumFact(String numberPick) {
    NumFact numWrap = getNumFact(numberPick);
    String numFact = numWrap.getText();
    return numFact;
}

public void setWebClient(WebClient webClient) {
    this.webClient = webClient;
}
    }