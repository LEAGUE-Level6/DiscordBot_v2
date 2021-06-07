package org.jointheleague.features.help_embed;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class HelpListenerTest {

    private final String testChannelName = "test";
    private final HelpListener helpListener = new HelpListener(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void itShouldNotPrintToSystemOut() {
        String expected  = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = helpListener.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(helpListener.COMMAND, "test");
        helpListener.setHelpEmbeds(Collections.singletonList(helpEmbed));
        when(messageCreateEvent.getMessageContent()).thenReturn(helpListener.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        helpListener.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(any(EmbedBuilder.class));
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        helpListener.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

}
