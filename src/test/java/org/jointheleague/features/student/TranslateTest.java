package org.jointheleague.features.student;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TranslateTest {

    private final String testChannelName = "test";
    private final Translate trans = new Translate(testChannelName);

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
        String command = trans.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(trans.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(trans.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        trans.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
        
        //Given
        HelpEmbed helpEmbed2 = new HelpEmbed(trans.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(trans.COMMAND + " notRight");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        trans.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(2)).sendMessage(anyString());
        
        HelpEmbed helpEmbed3 = new HelpEmbed(trans.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(trans.COMMAND + " en fr Hi there, how are you?");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        trans.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(3)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        trans.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = trans.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = trans.getHelpEmbed().getTitle();
        String command = trans.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    
}