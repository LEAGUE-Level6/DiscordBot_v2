package org.jointheleague.features.examples.second_features;

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

public class HighLowGameTest {

    private final String testChannelName = "test";
    private final HighLowGame highLowGame = new HighLowGame(testChannelName);

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
        String command = highLowGame.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(highLowGame.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(highLowGame.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = highLowGame.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = highLowGame.getHelpEmbed().getTitle();
        String command = highLowGame.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

//FEATURE-SPECIFIC  TESTS

    @Test
    void itShouldNotAcceptGuessIfGameIsNotStarted() {
        //Given
        String command = "!highLow 5";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please start the game first using just the command");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsTooLow() {
        //Given
        int guess = 1;
        String command = "!highLow " + guess;
        highLowGame.numberToGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(guess + " is too low.  Guess again!");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsTooHigh() {
        //Given
        int guess = 100;
        String command = "!highLow " + guess;
        highLowGame.numberToGuess = 1;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(guess + " is too high.  Guess again!");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsCorrect() {
        //Given
        int guess = 100;
        String command = "!highLow " + guess;
        highLowGame.numberToGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Correct!  The number I picked was " + guess);
    }

    @Test
    void itShouldSendErrorMessageIfGuessIsNotANumber() {
        //Given
        String guess = "ten";
        String command = "!highLow " + guess;
        highLowGame.numberToGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        highLowGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please format your guess like this: " + highLowGame.COMMAND + " 5");
    }
}