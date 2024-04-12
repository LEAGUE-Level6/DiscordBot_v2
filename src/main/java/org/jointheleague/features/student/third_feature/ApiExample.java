package org.jointheleague.features.student.third_feature;

import org.jointheleague.features.student.third_feature.data_transfer_objects.NumFact;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ApiExample {

private WebClient webClient;
private static final String baseUrl = "numbersapi.com";

public ApiExample() {
    this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
}
public NumFact getNumFact(String number) {
    System.out.println("Test!!");
    Mono<NumFact> numMono = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/" + number + "?json")
                    .build())
            .retrieve()
            .bodyToMono(NumFact.class);
    System.out.println("Test :(");
    return numMono.block();
}
public String findNumFact(String numberPick) {
    System.out.println(numberPick);
    NumFact numWrap = getNumFact(numberPick);
    String numFact = numWrap.getText();
    System.out.println(numFact + "TEST");
    return numFact;
}

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
    }