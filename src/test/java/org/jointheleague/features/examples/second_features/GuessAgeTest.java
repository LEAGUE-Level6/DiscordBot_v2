package org.jointheleague.features.examples.second_features;

import net.aksingh.owmjapis.api.APIException;
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
import static org.mockito.Mockito.times;

public class GuessAgeTest {
    private final String testChannelName = "test";
    private final GuessAge guessAge = new GuessAge(testChannelName);

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
        String command = guessAge.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand(){
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(guessAge.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(guessAge.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() throws APIException {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = guessAge.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = guessAge.getHelpEmbed().getTitle();
        String command = guessAge.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

//FEATURE-SPECIFIC  TESTS

    @Test
    void itShouldNotAcceptGuessIfGameIsNotStarted(){
        //Given
        String command = "!guessAge 5";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please start the game first using just the command");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsTooLow(){
        //Given
        int guess = 1;
        String command = "!guessAge " + guess;
        guessAge.ageGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(guess + " is too low.  Guess again!");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsTooHigh() {
        //Given
        int guess = 100;
        String command = "!guessAge " + guess;
        guessAge.ageGuess = 1;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("You are old enough to drive!");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsCorrect(){
        //Given
        int years = 100;
        String command = "!guessAge " + years;
        guessAge.ageGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Unfortunately you need to be" + years + " to drive");
    }

    @Test
    void itShouldSendErrorMessageIfGuessIsNotANumber() {
        //Given
        String guess = "ten";
        String command = "!GuessAge " + guess;
        guessAge.ageGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        guessAge.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please format your guess like this: " + guessAge.COMMAND + " 5");
    }
}
