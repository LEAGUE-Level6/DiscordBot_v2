package org.jointheleague.features.examples.third_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.SmashWrapper;
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

public class SmashAPITest {
    private final String testChannelName = "test";
    private SmashAPI smashAPI;

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
    Mono<SmashWrapper> SmashWrapperMonoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        smashAPI = new SmashAPI(testChannelName);
        smashAPI.setWebClient(webClientMock);
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
        String command = smashAPI.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = smashAPI.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = smashAPI.getHelpEmbed().getTitle();
        String command = smashAPI.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void givenMessageWithCommand_whenHandle_thenSendCatFact() throws APIException {
        //given
        String smashFact = "";
        List<String> data = Collections.singletonList(smashFact);

        SmashWrapper smashwrapper = new SmashWrapper();
        smashwrapper.setData(data);

        when(messageCreateEvent.getMessageContent()).thenReturn(smashAPI.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(SmashWrapper.class))
                .thenReturn(SmashWrapperMonoMock);
        when(SmashWrapperMonoMock.block())
                .thenReturn(smashwrapper);

        //when
        smashAPI.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(smashFact);
    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenDoNothing() throws APIException {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        smashAPI.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

}
