package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//Documentation for the API can be found here: https://newsapi.org/docs/get-started
public class TriviaAPI extends Feature {

    public final String COMMAND = "!triviaAPI";

    private WebClient webClient;
    private static final String baseUrl = "https://opentdb.com/api.php";

    public TriviaAPI(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "TriviaAPI gives trivia questions of different topics. This returns a trivia question related to topic 3 (e.g. !triviaAPI 3)");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            messageContent = messageContent
                    .replace(COMMAND, "")
                    .replace(" " , "");
            if (messageContent.equals("")) {
                event.sendResponse("Please put a topic after the command (e.g. " + COMMAND + " 3)");
            }
            else{
            	System.out.println(messageContent);
              //  String story = findStory(messageContent);
            	try {
                TriviaQuestions tq = getQuestionsByTopic(messageContent);
            	//event.sendResponse(tq.toString());
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
                event.sendResponse("after tq");
            }
        }
    }

    public TriviaQuestions getQuestionsByTopic(String topic) {
        Mono<TriviaQuestions> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("amount", 10)
                        .queryParam("category", "19")
                        .build())
                .retrieve()
                .bodyToMono(TriviaQuestions.class);
        System.out.println("before block");
        TriviaQuestions tq = apiExampleWrapperMono.block();
        System.out.println("after block");
        return tq;
    }

//    public String findStory(int topic){
//
//        //Get a story from News API
//        Result question = getQuestionByTopic(topic);
//
//        //Get the first article
//        Article article = apiExampleWrapper.getArticles().get(0);
//
//        //Get the title of the article
//        String articleTitle = article.getTitle();
//
//        //Get the content of the article
//        String articleContent = article.getContent();
//
//        //Get the URL of the article
//        String articleUrl = article.getUrl();
//
//        //Create the message
//        String message =
//                articleTitle + " -\n"
//                        + articleContent
//                        + "\nFull article: " + articleUrl;
//
//        //Send the message
//        return message;
//    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

