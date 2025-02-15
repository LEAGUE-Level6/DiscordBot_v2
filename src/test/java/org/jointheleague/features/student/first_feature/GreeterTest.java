package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class GreeterTest {
	private final String testChannelName = "test";
    private final Greeter greetFeature = new Greeter(testChannelName);

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
        assertEquals("", outContent.toString().trim()); // Trim to avoid unexpected whitespace failures
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        // Given

        // When
        String command = greetFeature.COMMAND;

        // Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertTrue(command.startsWith("!"));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        // Given
        when(receivedMessage.getMessageContent()).thenReturn(greetFeature.COMMAND);

        // When
        greetFeature.handle(receivedMessage);

        // Then
        verify(receivedMessage, times(1)).sendResponse(anyString()); // Ensures a response is sent
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        // Given
        when(receivedMessage.getMessageContent()).thenReturn("");

        // When
        greetFeature.handle(receivedMessage);

        // Then
        verify(receivedMessage, never()).sendResponse(anyString()); // Ensures no response is sent
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        // Given

        // When
        HelpEmbed actualHelpEmbed = greetFeature.getHelpEmbed();

        // Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        // Given

        // When
        String helpEmbedTitle = greetFeature.getHelpEmbed().getTitle();
        String command = greetFeature.COMMAND;

        // Then
        assertEquals(command, helpEmbedTitle);
    }
}
