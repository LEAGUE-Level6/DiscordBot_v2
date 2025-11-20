package org.jointheleague.features.student.first_feature;

import org.apache.commons.text.StringEscapeUtils;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TriviaAPITest {
    private final String testChannelName = "test";
    private final TriviaAPI featureOne = new TriviaAPI(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private ReceivedMessage receivedMessage;
    @Mock
    WebClient webClientMock;
    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    WebClient.ResponseSpec responseSpecMock;
    @Mock
    Mono<ApiExampleWrapper> apiExampleWrapperMonoMock;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void itShouldNotPrintToSystemOut() {
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }
    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(receivedMessage.getMessageContent()).thenReturn(command);

        //When
        featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, never()).sendResponse("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = featureOne.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = featureOne.getHelpEmbed().getTitle();
        String command = featureOne.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    @Test
    void itShouldHelp() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " help");
        //When
    	featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("Commands:\n`!triviaAPI categories` to list all topics.\n`!triviaAPI startQuiz [topic number]` to start a quiz based on a topic.\n`!triviaAPI stopQuiz` to stop the current quiz.\n`!triviaAPI answer [answer]` to answer a question.");

    }
    @Test
    void itShouldGiveCategories() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " categories");
        //When
    	featureOne.handle(receivedMessage);
    	StringBuilder sb = new StringBuilder();
    	for (Integer key : featureOne.categories.keySet()) {
    		sb.append(key + " : " + featureOne.categories.get(key) + "\n");
    	}
        //Then
        verify(receivedMessage, times(1)).sendResponse(sb.toString());

    }
    @Test
    void itShouldStopQuiz() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " stopQuiz");
        //When
    	featureOne.handle(receivedMessage);
    	if (!featureOne.quizStarted) {
    		verify(receivedMessage, times(1)).sendResponse("There is no quiz started.");
    	}
    	featureOne.quizStarted = true;
    	featureOne.handle(receivedMessage);
    	assertEquals(featureOne.quizStarted, false);
    	verify(receivedMessage, times(1)).sendResponse("Quiz stopped. You got a final score of " + featureOne.score + "/" + featureOne.currentQuestion);
    	assertEquals(featureOne.currentQuestion, 0);
    	assertEquals(featureOne.score, 0);

    }
    @Test
    void itShouldStartQuiz() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " startQuiz 15");
        //When
    	featureOne.handle(receivedMessage);
    	verify(receivedMessage).sendResponse("Starting 10 question quiz about topic: " + (featureOne.categories.get(15)));
    	verify(receivedMessage).sendResponse("Question #" + (featureOne.currentQuestion+1) + ": " + StringEscapeUtils.unescapeHtml4(featureOne.tq.getResults().get(featureOne.currentQuestion).getQuestion() + " True or false?"));
    }
    @Test
    void testUserSendsAnswerWithoutQuizStarted() {
    	featureOne.quizStarted = false;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " answer true");
    	featureOne.handle(receivedMessage);
    	verify(receivedMessage).sendResponse("There is no quiz started.");
    }
    @Test
    void itShouldHaveIncorrectAnswer() {
    	Question q = new Question();
    	q.question = "1+1 = 2";
    	q.correctAnswer = "true";
    	Question q2 = new Question();
    	q2.question = "2+2 = 5";
    	q2.correctAnswer = "false";
    	TriviaQuestions tq = new TriviaQuestions();
    	tq.results = new ArrayList<Question> ();
    	tq.results.add(q);
    	tq.results.add(q2);
    	featureOne.tq = tq;
    	int currentQuestion = featureOne.currentQuestion;
    	featureOne.quizStarted = true;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " answer false");
    	featureOne.handle(receivedMessage);
    	verify(receivedMessage).sendResponse("Incorrect! The answer was " + featureOne.tq.getResults().get(currentQuestion).getCorrectAnswer());
    	assertEquals(currentQuestion+1, featureOne.currentQuestion);
    }
    @Test
    void itShouldHaveCorrectAnswer() {
    	Question q = new Question();
    	q.question = "1+1 = 2";
    	q.correctAnswer = "true";
    	Question q2 = new Question();
    	q2.question = "2+2 = 5";
    	q2.correctAnswer = "false";
    	TriviaQuestions tq = new TriviaQuestions();
    	tq.results = new ArrayList<Question> ();
    	tq.results.add(q);
    	tq.results.add(q2);
    	featureOne.tq = tq;
        //Given
    	featureOne.quizStarted = true;
    	int score = featureOne.score;
    	int currentQuestion = featureOne.currentQuestion;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " answer true");
    	featureOne.handle(receivedMessage);
    	verify(receivedMessage).sendResponse("Correct!");
    	assertEquals(currentQuestion+1, featureOne.currentQuestion);
    	assertEquals(score+1, featureOne.score);
    }
    @Test
    void itShouldHave10Questions() {
    	featureOne.totalNumberOfQuestions = 1;
    	Question q = new Question();
    	q.question = "1+1 = 2";
    	q.correctAnswer = "true";
    	TriviaQuestions tq = new TriviaQuestions();
    	tq.results = new ArrayList<Question> ();
    	tq.results.add(q);
    	featureOne.tq = tq;
    	featureOne.currentQuestion = 0;
        //Given
    	featureOne.quizStarted = true;
    	int score = featureOne.score;
    	int currentQuestion = featureOne.currentQuestion;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " answer false");
    	featureOne.handle(receivedMessage);
    	verify(receivedMessage).sendResponse("Incorrect! The answer was " + tq.getResults().get(0).getCorrectAnswer());
    	verify(receivedMessage).sendResponse("Congrats! You have finished the quiz! You got a final score of " + score + "/" + (currentQuestion+1));
    	assertEquals(featureOne.score, 0);
    	assertEquals(featureOne.currentQuestion, 0);
    }
}
