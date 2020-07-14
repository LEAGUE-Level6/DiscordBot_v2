package org.jointheleague.features.example;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.example.help_embed.HelpListener;
import org.jointheleague.pojo.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HelpListenerTest {

    private final String testChannelName = "test";
    private final HelpListener underTest = new HelpListener(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @AfterEach
    public void itShouldNotPrintToSystemOut() {
        assertThat(outContent.toString()).isBlank();
        System.setOut(originalOut);
    }
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = underTest.COMMAND;

        //Then
        assertThat(command).isNotBlank();
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(underTest.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(underTest.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        org.springframework.test.util
                .ReflectionTestUtils.setField(
                underTest,
                "helpEmbeds",
                Collections.singletonList(helpEmbed));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(any(EmbedBuilder.class));
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();

    }
    
}