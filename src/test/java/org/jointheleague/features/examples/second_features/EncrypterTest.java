package org.jointheleague.features.examples.second_features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
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

public class EncrypterTest {

    private final String testChannelName = "test";
    private final Encrypter encrypter = new Encrypter(testChannelName);

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
        String command = encrypter.COMMAND;

        //Then

        assertNotEquals("", command);
        assertNotEquals("q!", command);
        assertEquals('q', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(encrypter.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(encrypter.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        encrypter.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        encrypter.handle(messageCreateEvent);

        //Then
        //verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = encrypter.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = encrypter.getHelpEmbed().getTitle();
        String command = encrypter.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void itShouldSendErrorMessageIfNoMessageIsGiven() {
        String command = "q!encrypt G";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        encrypter.handle(messageCreateEvent);
        verify(textChannel, times(1)).sendMessage("Please include both a [shiftSequence] and a [message].");
    }

    @Test
    void itShouldSendErrorMessageIfNoInputIsGiven() {
        String command = "q!encrypt ";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        encrypter.handle(messageCreateEvent);
        verify(textChannel, times(1)).sendMessage("Please include both a [shiftSequence] and a [message].");
    }

    @Test
    void itShouldSendErrorMessageIfOnlyCommandIsGiven() {
        String command = "q!encrypt";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        encrypter.handle(messageCreateEvent);
        verify(textChannel, times(1)).sendMessage("Please include both a [shiftSequence] and a [message].");
    }

    @Test
    void itShouldEncryptTheEntireMessage() {
        String command = "q!encrypt GHIJKLMNOPQRST ABCDEFGHIJKLMNOP";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        encrypter.handle(messageCreateEvent);
        verify(textChannel, times(1)).sendMessage("GIKMOQSUWYACEGHI");
    }

}