package org.jointheleague.features.student;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.Hello;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HelloTest {

    private final String testChannelName = "test";
    private final Hello hello = new Hello(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    
    @Mock
    MessageAuthor ma;
    
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
        String command = hello.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(hello.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(hello.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hello.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
        
        HelpEmbed helpEmbed2 = new HelpEmbed(hello.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(hello.COMMAND + " personName");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hello.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(2)).sendMessage(anyString());
        
      //Given
        HelpEmbed helpEmbed3 = new HelpEmbed(hello.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(hello.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        when(messageCreateEvent.getMessageAuthor()).thenReturn((ma));
        when(ma.getDisplayName()).thenReturn(("JUnitTestPerson"));
        
        //When
        hello.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(3)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        hello.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = hello.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = hello.getHelpEmbed().getTitle();
        String command = hello.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}