package org.jointheleague.features.student.second_feature;

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FeatureTwoTest {
    private final String testChannelName = "testing";
    private final FeatureTwo featureTwo = new FeatureTwo(testChannelName);

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
        String command = featureTwo.COMMAND;

        //Then

        if(!(featureTwo instanceof Feature)){
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
        HelpEmbed helpEmbed = new HelpEmbed(featureTwo.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn(featureTwo.COMMAND);

        //When
        featureTwo.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(receivedMessage.getMessageContent()).thenReturn(command);

        //When
        featureTwo.handle(receivedMessage);

        //Then
        verify(receivedMessage, never()).sendResponse("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = featureTwo.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = featureTwo.getHelpEmbed().getTitle();
        String command = featureTwo.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void itShouldCheckScramble() {
        for (int i = 0; i < 7; i++) {
            String[] testScram = featureTwo.getScrambled(i, 1);
            assertNotEquals(testScram[0], testScram[1]);
        }
    }

    @Test
    void checkLevelsAdvance() {
        featureTwo.levelCounter = 0;
        featureTwo.ready = false;
        when(receivedMessage.getMessageContent()).thenReturn(featureTwo.COMMAND);
        featureTwo.handle(receivedMessage);
        for (int i = 0; i <= 7; i++) {


            if (i != 7) {
                for (int j = 0; j < featureTwo.words[i].length - 1; j++) {
                    String word = featureTwo.words[i][j];
                    when(receivedMessage.getMessageContent()).thenReturn(featureTwo.COMMAND + " " + word);

                    featureTwo.handle(receivedMessage);
                    if (featureTwo.responseScramble[1].equals(word)) {
                        assertEquals(i + 1, featureTwo.levelCounter);
                    } else {
                        assertNotEquals(word, featureTwo.responseScramble[1]);
                    }
                }


            } else {
                for (int j = 0; j < featureTwo.words[i].length - 1; j++) {
                    String word = featureTwo.words[i][j];
                    when(receivedMessage.getMessageContent()).thenReturn(featureTwo.COMMAND + " " + word);

                    featureTwo.handle(receivedMessage);
                    if (featureTwo.responseScramble[1].equals(word)) {
                        assertEquals();
                    } else {
                        assertNotEquals(word, featureTwo.responseScramble[1]);
                    }
                }
            }
        }

    }


}
