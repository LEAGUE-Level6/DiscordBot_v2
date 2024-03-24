package org.jointheleague.features.student.third_feature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class Holidays {

    private static final String baseUrl = "https://holidays.abstractapi.com/v1/";
    private final String apiKey = "736d5028a46c42ada682689ef387e002";
    private WebClient webClient;

    public Holidays(){
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public void testRequest() {

        /*
        Just like in the Cheetah Search API, we use the uri() method to add the token as a query parameter
        The resulting uri would look like:
        http://newsapi.org/v2/everything?q=pizza&sortBy=popularity&apiKey=59ac01326c584ac0a069a29798794bec
         */
        Mono<String> stringMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey)
                        .queryParam("country", "US")
                        .queryParam("year","2021")
                        .queryParam("month", "01")
                        .queryParam("day","01")
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String response = stringMono.block();

        System.out.println(response);
    }
    public static void main(String[] args) {

    Holidays holidays = new Holidays();
    holidays.testRequest();


    }

}





