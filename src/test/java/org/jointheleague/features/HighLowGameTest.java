package org.jointheleague.features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HighLowGameTest {

    private final String testChannelName = "test";
    private final HighLowGame underTest = new HighLowGame(testChannelName);

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
        when(messageCreateEvent.getMessageContent()).thenReturn(underTest.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
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

    @Test
    void itShouldNotAcceptGuessIfGameIsNotStarted() {
        //Given
        String command = "!highLow 5";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please start the game first using just the command");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsTooLow() {
        //Given
        int guess = 1;
        String command = "!highLow " + guess;
        underTest.numberToGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(guess + " is too low.  Guess again!");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsTooHigh() {
        //Given
        int guess = 100;
        String command = "!highLow " + guess;
        underTest.numberToGuess = 1;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(guess + " is too high.  Guess again!");

    }

    @Test
    void itShouldTellTheUserIfTheirGuessIsCorrect() {
        //Given
        int guess = 100;
        String command = "!highLow " + guess;
        underTest.numberToGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Correct!  The number I picked was " + guess);
    }

    @Test
    void itShouldSendErrorMessageIfGuessIsNotANumber() {
        //Given
        String guess = "ten";
        String command = "!highLow " + guess;
        underTest.numberToGuess = 100;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please format your guess like this: " + underTest.COMMAND + " 5");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When

        //Then
        assertThat(underTest.getHelpEmbed()).isNotNull();
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When

        //Then
        assertThat(underTest.getHelpEmbed().getTitle()).isEqualTo(underTest.COMMAND);
    }

}