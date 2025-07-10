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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FirstFeatureTest {
    private final String testChannelName = "testing";
    private final FirstFeature firstFeature = new FirstFeature(testChannelName);

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
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = firstFeature.COMMAND;

        //Then

        if(!(firstFeature instanceof Feature)){
            assertNotEquals("!command", command);
        }

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertNotEquals("!command", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(firstFeature.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn(firstFeature.COMMAND);

        //When
        firstFeature.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(receivedMessage.getMessageContent()).thenReturn(command);

        //When
        firstFeature.handle(receivedMessage);

        //Then
        verify(receivedMessage, never()).sendResponse("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = firstFeature.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldAnswer() {
        String commandOne = firstFeature.ANSWERCOMMAND;
        firstFeature.riddleSent = true;
        firstFeature.generatedRiddle = firstFeature.generateRiddle(0);
        when(receivedMessage.getMessageContent()).thenReturn(commandOne);

        //When
        firstFeature.handle(receivedMessage);

        //Then
        verify(receivedMessage.getMessageContent().contains("The answer to your riddle is:"));

    }

    @Test
    void haventAsked() {
        String commandOne = firstFeature.ANSWERCOMMAND;
        firstFeature.riddleSent = false;
        firstFeature.generatedRiddle = firstFeature.generateRiddle(0);

        when(receivedMessage.getMessageContent()).thenReturn(commandOne);
        //When
        firstFeature.handle(receivedMessage);
        //Then
        verify(receivedMessage, times(1)).sendResponse("You have to ask for a riddle first!");
        assertFalse(firstFeature.riddleSent);
    }

    @Test
    void itShouldReturnTheRightThings() {


        for (int i = 0; i < 9; i++) {
            String[] riddle = firstFeature.generateRiddle(i);
            if (riddle[0].equals("What is always on the ground but never dirty?")) {
                assertEquals(riddle[1], "A shadow");

            } else if (riddle[0].equals("What can fill a room without taking up any space?")) {
                assertEquals(riddle[1], "Light");
            } else if (riddle[0].equals("What do you bury alive, but dig up dead?")) {
                assertEquals(riddle[1], "A plant");

            } else if (riddle[0].equals("I am always old, but sometimes also new. While I'm never sad, sometimes I am blue. \nI am never empty, but only sometimes full. I never push, but I always pull. What am I?")) {
                assertEquals(riddle[1], "The moon");

            } else if (riddle[0].equals("If you give me a drink, I die, but if you feed me I grow, what am I?")) {
                assertEquals(riddle[1], "Fire");

            } else if (riddle[0].equals("What can go up but can never come down?")) {
                assertEquals(riddle[1], "Your age");

            } else if (riddle[0].equals("What word in the dictionary is spelled incorrectly?")) {
                assertEquals(riddle[1], "Incorrectly");

            } else if (riddle[0].equals("What occurs once in a minute, twice in a moment, and never in 1000 years?")) {
                assertEquals(riddle[1], "The letter 'M'");

            } else if (riddle[0].equals("What is so fragile that saying its name breaks it?")) {
                assertEquals(riddle[1], "Silence");

            } else if (riddle[0].equals("What has 13 hearts but no other organs?")) {
                assertEquals(riddle[1], "A deck of cards");

            } else if (riddle[0].equals("What has 4 fingers and a thumb, but isn't alive?")) {
                assertEquals(riddle[1], "A deck of cards");

            }

        }
        assertNull(firstFeature.generateRiddle(10));

    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = firstFeature.getHelpEmbed().getTitle();
        String command = firstFeature.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
