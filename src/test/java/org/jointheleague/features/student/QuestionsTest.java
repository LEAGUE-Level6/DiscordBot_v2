package org.jointheleague.features.student;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.student.questions.Questions;
import org.jointheleague.features.student.questions.QuestionsWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.questions.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class QuestionsTest {

    private final String testChannelName = "test";
    private Questions questionsapi;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @Mock
    WebClient webClientMock;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    WebClient.ResponseSpec responseSpecMock;

    @Mock
    Mono<QuestionsWrapper> questionsWrapperMonoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        questionsapi = new Questions(testChannelName);
        questionsapi.setWebClient(webClientMock);
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
    void itShouldHaveACommand() {
        //Given

        //When
        String command = questionsapi.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = questionsapi.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = questionsapi.getHelpEmbed().getTitle();
        String command = questionsapi.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void givenMessageWithCommand_whenHandle_thenSendQuestion() {
        //given
        String question = "What engine did the original Half-Life run on?";
        Result result = new Result();
        result.setQuestion(question);
        /*result.setCorrectAnswer("a");
        result.setCategory("b");
        result.setDifficulty("c");
        result.setType("d");
        List<String> list = Arrays.asList("e", "f");
        result.setIncorrectAnswers(list);*/

        List<Result> data = Collections.singletonList(result);

        QuestionsWrapper questionsWrapper = new QuestionsWrapper();
        questionsWrapper.setResults(data);
        //questionsWrapper.setResponseCode(1);

        when(messageCreateEvent.getMessageContent()).thenReturn(questionsapi.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(QuestionsWrapper.class))
                .thenReturn(questionsWrapperMonoMock);
        when(questionsWrapperMonoMock.block())
                .thenReturn(questionsWrapper);

        //when
        questionsapi.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(question);
    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenPrintWrong() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!attempt Ohio");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        questionsapi.gameStarted = true;

        //When
        questionsapi.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Good job.");
    }

    @Test
    void itShouldSendAnswerRequestAfterThreeFails() {
        //Given
        String answer = "answer";
        Result result = new Result();
        result.setCorrectAnswer(answer);
        List<Result> data = Collections.singletonList(result);

        QuestionsWrapper questionsWrapper = new QuestionsWrapper();
        questionsWrapper.setResults(data);

        when(messageCreateEvent.getMessageContent()).thenReturn(questionsapi.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(QuestionsWrapper.class))
                .thenReturn(questionsWrapperMonoMock);
        when(questionsWrapperMonoMock.block())
                .thenReturn(questionsWrapper);

        //when
        questionsapi.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(answer);

        questionsapi.gameStarted = true;
        questionsapi.wrongCounter = 3;
        when(messageCreateEvent.getMessageContent()).thenReturn("!request answer");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        questionsapi.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("answer");
    }

}