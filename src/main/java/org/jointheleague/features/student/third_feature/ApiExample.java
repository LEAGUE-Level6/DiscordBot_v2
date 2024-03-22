package org.jointheleague.features.student.third_feature;

import org.jointheleague.features.student.third_feature.data_transfer_objects.ApiExampleWrapper;
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
public ApiExampleWrapper getNumFact(String number) {
    Mono<ApiExampleWrapper> numMono = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/" + number)
                    .build())
            .retrieve()
            .bodyToMono(ApiExampleWrapper.class);
    System.out.println(numMono.block().getText().get(0));
    return numMono.block();
}
public String findNumFact(String numberPick) {
    ApiExampleWrapper numWrap = getNumFact(numberPick);
    String numFact = numWrap.getText().get(0);
    System.out.println(numFact);
    return numFact;
}

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
    }