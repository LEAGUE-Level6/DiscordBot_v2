package org.jointheleague.features.examples.third_features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
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

public class CatFactsApiTest {

    private final String testChannelName = "test";
    private CatFactsApi catFactsApi;

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
    Mono<CatWrapper> catWrapperMonoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        catFactsApi = new CatFactsApi(testChannelName);
        catFactsApi.setWebClient(webClientMock);
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
        String command = catFactsApi.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = catFactsApi.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = catFactsApi.getHelpEmbed().getTitle();
        String command = catFactsApi.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void givenMessageWithCommand_whenHandle_thenSendCatFact() {
        //given
        String catFact = "Some cats have more toes than others";
        List<String> data = Collections.singletonList(catFact);

        CatWrapper catWrapper = new CatWrapper();
        catWrapper.setData(data);

        when(messageCreateEvent.getMessageContent()).thenReturn(catFactsApi.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CatWrapper.class))
                .thenReturn(catWrapperMonoMock);
        when(catWrapperMonoMock.block())
                .thenReturn(catWrapper);

        //when
        catFactsApi.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(catFact);
    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenDoNothing() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        catFactsApi.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

}