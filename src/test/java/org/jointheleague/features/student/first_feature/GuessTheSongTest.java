package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GuessTheSongTest {
    private final String testChannelName = "test";
    private final GuessTheSong guessTheSong = new GuessTheSong(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private ReceivedMessage receivedMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void itShouldNotPrintToSystemOut() {
        assertEquals("", outContent.toString().trim()); // Trim to avoid whitespace failures
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        // Given

        // When
        String command = guessTheSong.COMMAND;

        // Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertTrue(command.startsWith("!"));
        assertNotNull(command);
    }

    @Test
    void itShouldStartGameWhenCommand() {
        // Given
        when(receivedMessage.getMessageContent()).thenReturn(guessTheSong.COMMAND);

        // When
        guessTheSong.handle(receivedMessage);

        // Then
        verify(receivedMessage, times(1)).sendResponse(anyString());
    }

    @Test
    void itShouldNotStartNewGameAlreadyPlaying() {
        // Given
        when(receivedMessage.getMessageContent()).thenReturn(guessTheSong.COMMAND);

        // When
        guessTheSong.handle(receivedMessage); 
        guessTheSong.handle(receivedMessage); 

        // Then
        verify(receivedMessage, times(1)).sendResponse(contains("Game is in progress."));  
    }

    @Test
    void itShouldEndGameOnCorrectGuess() {
        // Given
        when(receivedMessage.getMessageContent()).thenReturn(guessTheSong.COMMAND);
        guessTheSong.handle(receivedMessage);

        when(receivedMessage.getMessageContent()).thenReturn(guessTheSong.currentSong);

        // When
        guessTheSong.handle(receivedMessage);

        // Then
        verify(receivedMessage, times(1)).sendResponse("Correct!");
    }

    @Test
    void itShouldNotEndGameOnIncorrectGuess() {
        // Given
        when(receivedMessage.getMessageContent()).thenReturn(guessTheSong.COMMAND);
        guessTheSong.handle(receivedMessage);

        when(receivedMessage.getMessageContent()).thenReturn("Some Wrong Song");

        // When
        guessTheSong.handle(receivedMessage);

        // Then
        verify(receivedMessage, never()).sendResponse("Correct!");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        assertNotNull(guessTheSong.getHelpEmbed());
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        assertEquals(guessTheSong.COMMAND, guessTheSong.getHelpEmbed().getTitle());
    }
}
