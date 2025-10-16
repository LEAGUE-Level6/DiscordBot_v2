package org.jointheleague.features.student.first_feature;

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
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " startQuiz");
        //When
    	featureOne.handle(receivedMessage);
    	
    }
}
