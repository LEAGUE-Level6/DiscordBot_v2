package org.jointheleague.features.student.first_feature;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.CatFactsApi;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class PunApiTest {

    private final String testChannelName = "test";
    private PunApi punApi;

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
    Mono<PunWrapper> punWrapperMonoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        punApi = new PunApi(testChannelName);
        punApi.setWebClient(webClientMock);
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
        String command = punApi.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = punApi.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = punApi.getHelpEmbed().getTitle();
        String command = punApi.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void givenMessageWithCommand_whenHandle_thenSendCatFact() {
        //given
        String pun = "Some cats have more toes than others";
        List<String> data = Collections.singletonList(pun);

        PunWrapper punWrapper = new PunWrapper();
        punWrapper.setPun(data.get(0));

        when(messageCreateEvent.getMessageContent()).thenReturn(punApi.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(PunWrapper.class))
                .thenReturn(punWrapperMonoMock);
        when(punWrapperMonoMock.block())
                .thenReturn(punWrapper);

        //when
        punApi.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(pun);
    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenDoNothing() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        punApi.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage("");
    }

}