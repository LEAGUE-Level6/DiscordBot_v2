package org.jointheleague.features.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReverseTextTest {
	private final String testChannelName = "test";
    private final ReverseText reverseText = new ReverseText(testChannelName);

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
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = reverseText.COMMAND;

        //Then


        //assertNotEquals("!command", command);
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithOnlyCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(reverseText.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(reverseText.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        reverseText.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Be sure to enter a string after the command.");
    }
    
    @Test
    void itShouldHandleMessagesWithCommandAndString() {
    	//Given
        when(messageCreateEvent.getMessageContent()).thenReturn(reverseText.COMMAND + "testing");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        reverseText.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("gnitset");
    }
    
    @Test
    void itShouldHandleMessagesWithCommandAndStringAndTrimResult() {
    	//Given
        when(messageCreateEvent.getMessageContent()).thenReturn("       " + reverseText.COMMAND + "      testing      ");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        reverseText.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("gnitset");
    }
    
    @Test
    void itShouldHandleMessagesWithCommandAndStringAfterCommand() {
    	//Given
        when(messageCreateEvent.getMessageContent()).thenReturn("not here" + reverseText.COMMAND + "testing");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        reverseText.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("gnitset");
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        reverseText.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = reverseText.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = reverseText.getHelpEmbed().getTitle();
        String command = reverseText.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    
}
