package org.jointheleague.features.student.third_feature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.CatFactsApi;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.second_feature.FeatureTwo;
import org.jointheleague.features.student.third_features.plain_old_java_objects.bored_api.FeatureThreeWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class FeatureThreeTest {

    private final String testChannelName = "FeatureThree";
    private FeatureThree featureThree;

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
    Mono<FeatureThreeWrapper> featureThreeWrapperMonoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        featureThree = new FeatureThree(testChannelName);
        featureThree.setWebClient(webClientMock);
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
        String command = featureThree.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = featureThree.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = featureThree.getHelpEmbed().getTitle();
        String command = featureThree.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void givenMessageWithCommand_whenHandle_thenSendCatFact() {
        //given
        String activity = "Start a garden";
        List<String> data = Collections.singletonList(activity);

        FeatureThreeWrapper featureThreeWrapper = new FeatureThreeWrapper();
        featureThreeWrapper.setData(data);

        when(messageCreateEvent.getMessageContent()).thenReturn(featureThree.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(FeatureThreeWrapper.class))
                .thenReturn(featureThreeWrapperMonoMock);
        when(featureThreeWrapperMonoMock.block())
                .thenReturn(featureThreeWrapper);

        //when
        featureThree.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(activity);
    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenDoNothing() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        featureThree.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage("");
    }

}
