package org.jointheleague.features.student.first_feature;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.second_features.HighLowGame;
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
import static org.mockito.Mockito.*;

public class HangmanTest {

    private final String testChannelName = "test";
    private final Hangman hangman = new Hangman(testChannelName);

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
        String command = hangman.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(hangman.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(hangman.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hangman.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        hangman.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = hangman.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = hangman.getHelpEmbed().getTitle();
        String command = hangman.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

//FEATURE-SPECIFIC  TESTS

    @Test
    void itShouldNotAcceptGuessIfGameIsNotStarted() {
        //Given
        String command = "!guess e";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hangman.play= false;
        hangman.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(0)).sendMessage("");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsWrong() {
        //Given
        String guess = "z";
        String command = "!guess " + guess;
        hangman.mystery = "enamor";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hangman.play = true;
        hangman.lives = 7;
        hangman.blank = "______";
        hangman.guessed = "";
        hangman.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Incorrect! You have 6 guesses left!");
        assertEquals(6, hangman.lives);
    }


    @Test
    void itShouldTellTheUserIfTheirGuessIsCorrect() {
        //Given
        String guess = "e";
        String command = "!guess " + guess;
        hangman.mystery = "enamor";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hangman.guessed = "";
        hangman.blank = "______";
        hangman.play = true;
        hangman.lives = 7;
        hangman.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Correct! e_____");
        assertEquals(7, hangman.lives);
        assertEquals("e_____", hangman.blank);
    }

    @Test
    void itShouldSendErrorMessageIfGuessIsNotALetter() {
        //Given
        String guess = "1";
        String command = "!hangman " + guess;
        hangman.mystery = "enamor";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        hangman.guessed = "";
        hangman.blank = "______";
        hangman.play = true;
        hangman.lives=7;
        hangman.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please format your guess correctly.");
    }
}