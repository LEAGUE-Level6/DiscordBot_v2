package org.jointheleague.features.whale;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SerpAPI {
	private static final String baseUrl = "https://serpapi.com/search.json?engine=google&q=%searchhere%&location=United+States&google_domain=google.com&gl=us&hl=en&tbm=isch&api_key=5d839296455a3e7b9dcc3d060fb7ad2584f9dc2eb413c98089ce0a51d756bb25";
	private WebClient webClient;

	public SerpAPI() {
		this.webClient = WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SerpAPI api = new SerpAPI();
		//api.getImage("valorant");
	}

	public String getImage(String q) {
		String url = baseUrl.replace("%searchhere%", q);

		// Make the request and retrieve the response as an ImageSearchResult
//        Mono<ImageSearchResult> ISRMono = webClient.get()
//                .uri(url)
//                .retrieve()
//                .bodyToMono(ImageSearchResult.class);
//
//        // Block and get the response
//        ImageSearchResult response = ISRMono.block();
//        
//        if (response != null && response.getImagesResults() != null && !response.getImagesResults().isEmpty()) {
//            String imageURL = response.getImagesResults().get(1).getOriginal();
//            System.out.println(response.getImagesResults().size());
//            System.out.println("URL of 1st image: " + imageURL);
//        } else {
//            System.out.println("No image results found.");
//        }

		// Optionally, you can print the raw JSON response as a string if needed
		Mono<String> stringMono = webClient.get().uri(url).retrieve().bodyToMono(String.class);

		String responseS = stringMono.block();
		// System.out.println("Raw JSON response: " + responseS);
		String imgUrl = findUrl(responseS);

		while (isImageFormatGood(imgUrl) == false) {
			imgUrl = findUrl(responseS.substring(imgUrl.indexOf("original")+8, responseS.length() - 1));
			responseS = responseS.substring(imgUrl.indexOf("original")+8, responseS.length() - 1);
		}
		System.out.println("filtered image url: " + imgUrl);
		return imgUrl.substring(4, imgUrl.length()-2);
	}

	boolean isImageFormatGood(String imgUrl) {
		if (imgUrl.contains(".png") || imgUrl.contains(".jpeg") || imgUrl.contains(".jpg")) {
			return true;
		} else {
			return false;
		}
	}

	String findUrl(String responseS) {
		int indexOfOrgStr = responseS.indexOf("original");
		int index = indexOfOrgStr + 8;
		String imgUrl = "";
		while (responseS.charAt(index) != ',') {
			imgUrl = imgUrl + responseS.charAt(index);
			index = index + 1;
		}
		imgUrl = imgUrl.trim();
		return imgUrl;
	}
}
