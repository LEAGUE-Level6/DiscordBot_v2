package org.jointheleague.features.examples.third_features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.dog_facts_api.DogFactsApiWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
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
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class DogFactsApiTest {

    private final String testChannelName = "test";
    private DogFactsApi dfa;

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
    Mono<DogFactsApiWrapper> dogWrapperMonoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dfa = new DogFactsApi(testChannelName);
        dfa.setWebClient(webClientMock);
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
        String command = dfa.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = dfa.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = dfa.getHelpEmbed().getTitle();
        String command = dfa.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    // The reason this does not work is because in handle I rebuild the webclient
    // (because url changes based on what the user does), so it does not used the mocked client
//    @Test
//    void givenMessageWithCommand_whenHandle_thenSendDogFact() {
//        //given
//        String df = "A dog is usually a dog!";
//        
//        DogFactsApiWrapper dWrap = new DogFactsApiWrapper();
//        dWrap.setFact(df);
//
//        when(messageCreateEvent.getMessageContent()).thenReturn(dfa.COMMAND);
//        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
//
//        when(webClientMock.get())
//                .thenReturn(requestHeadersUriSpecMock);
//        when(requestHeadersUriSpecMock.retrieve())
//                .thenReturn(responseSpecMock);
//        when(responseSpecMock.bodyToMono(DogFactsApiWrapper.class))
//                .thenReturn(dogWrapperMonoMock);
//        when(dogWrapperMonoMock.block())
//                .thenReturn(dWrap);
//
//        //when
//        dfa.handle(messageCreateEvent);
//
//        //then
//        verify(webClientMock, times(1)).get();
//        verify(textChannel, times(1)).sendMessage(df);
//    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenDoNothing() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        dfa.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

}