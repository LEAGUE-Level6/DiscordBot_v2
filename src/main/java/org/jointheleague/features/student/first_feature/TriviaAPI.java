package org.jointheleague.features.student.first_feature;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.apache.commons.text.StringEscapeUtils;

//Documentation for the API can be found here: https://newsapi.org/docs/get-started
public class TriviaAPI extends Feature {

    public final String COMMAND = "!triviaAPI";

    private WebClient webClient;
    private static final String baseUrl = "https://opentdb.com/api.php";

    public TriviaAPI(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "TriviaAPI gives trivia true or false questions of different topics. This starts a trivia quiz related to topic 9 (e.g. !triviaAPI 9)");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }
    boolean quizStarted = false;
    TriviaQuestions tq;
    int currentQuestion = 0;
    int score = 0;
    Map<Integer, String> categories = new TreeMap<>();
    {
    	categories.put(9, "General Knowledge");
    	categories.put(10, "Books");
    	categories.put(11, "Film");
    	categories.put(12, "Music");
    	categories.put(13, "Musical and Theater");
    	categories.put(14, "Television");
    	categories.put(15, "Video Games");
    	categories.put(16, "Board Games");
    	categories.put(17, "Science and Nature");
    	categories.put(18, "Computers");
    	categories.put(19, "Math");
    	categories.put(20, "Mythology");
    	categories.put(21, "Sports");
    	categories.put(22, "Geography");
    	categories.put(23, "History");
    	categories.put(24, "Politics");
    	categories.put(25, "Art");
    	categories.put(26, "Celebrities");
    	categories.put(27, "Animals");
    	categories.put(28, "Vehicles");
    	categories.put(29, "Comics");
    	categories.put(30, "Gadgets");
    	categories.put(31, "Anime");
    	categories.put(32, "Cartoons");
    }
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            if (messageContent.equals("!triviaAPI help")) {
                event.sendResponse("Commands:\n`!triviaAPI categories` to list all topics.\n`!triviaAPI startQuiz [topic number]` to start a quiz based on a topic.\n`!triviaAPI stopQuiz` to stop the current quiz.\n`!triviaAPI answer [answer]` to answer a question.");
            }
            if (messageContent.equals("!triviaAPI categories")) {
            	StringBuilder sb = new StringBuilder();
            	for (Integer key : categories.keySet()) {
            		sb.append(key + " : " + categories.get(key) + "\n");
            	}
            	event.sendResponse(sb.toString());
            }
            if (messageContent.equals("!triviaAPI stopQuiz")) {
            	if (!quizStarted) {
            		event.sendResponse("There is no quiz started.");
            		return;
            	}
            	quizStarted = false;
            	event.sendResponse("Quiz stopped. You got a final score of " + score + "/" + currentQuestion);
            	currentQuestion = 0;
            	score = 0;
            }
            if (messageContent.startsWith("!triviaAPI startQuiz")){
            	messageContent = messageContent.replace("!triviaAPI startQuiz ", "");
            	quizStarted = true;
              //  String story = findStory(messageContent); 
            
            
            
            
//            	try {
               
                	tq = getQuestionsByTopic(messageContent);
                	event.sendResponse("Starting 10 question quiz about topic: " + (categories.get(Integer.parseInt(messageContent))));
                //
                	event.sendResponse("Question #" + (currentQuestion+1) + ": " + StringEscapeUtils.unescapeHtml4(tq.getResults().get(currentQuestion).getQuestion() + " True or false?"));
//            	}
//            	catch(Exception e) {
//            		e.printStackTrace();
//            	}
            }
            if (messageContent.startsWith("!triviaAPI answer")) {
            	if (quizStarted) {
            		messageContent = messageContent.replace("!triviaAPI answer ", "");
            		if (messageContent.toLowerCase().equals(tq.getResults().get(currentQuestion).getCorrectAnswer().toLowerCase())) {
            			event.sendResponse("Correct!");
            			score++;
            			currentQuestion++;
            		}
            		else {
            			event.sendResponse("Incorrect! The answer was " + tq.getResults().get(currentQuestion).getCorrectAnswer());
            			currentQuestion++;
        
            		}
            		if (currentQuestion == 10) {
            			event.sendResponse("Congrats! You have finished the quiz! You got a final score of " + score + "/" + currentQuestion);
            			currentQuestion = 0;
            			score = 0;
            		}
            		else {
            			event.sendResponse("Question #" + (currentQuestion+1) + ": " + StringEscapeUtils.unescapeHtml4(tq.getResults().get(currentQuestion).getQuestion() + " True or false?"));
            		}
            	}
            	else {
            		event.sendResponse("There is no quiz started.");
            	}
            }
        }
    }

    
    public TriviaQuestions getQuestionsByTopic(String topic) {
        Mono<TriviaQuestions> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("amount", 10)
                        .queryParam("category", topic)
                        .queryParam("type", "boolean")
                        .build())
                .retrieve()
                .bodyToMono(TriviaQuestions.class);
        TriviaQuestions tq = apiExampleWrapperMono.block();
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

