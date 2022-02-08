package org.jointheleague.features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discord_bot.plus;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class plustest {

    private final String testChannelName = "test";
    private final plus plus = new plus(testChannelName);

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
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.out.println(actual);
        System.setOut(originalOut);
    }
    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = plus.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(plus.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(plus.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        plus.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        plus.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = plus.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = plus.getHelpEmbed().getTitle();
        String command = plus.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    @Test
    void itShouldTellTheUserIfTheirGuessIsCorrect() {
        //Given
        int guess = 100;
        String command = "!plus" + guess;
        plus.ans = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        plus.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("correct");
    }

    void itShouldSendErrorMessageIfTHEYGUESSEDWHENTHEYDONTNEEDTO() {
        //Given
        String guess = "100";
        String command = "!plus " + guess;
        plus.ans = 0;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        plus.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("._.");
    }
}
