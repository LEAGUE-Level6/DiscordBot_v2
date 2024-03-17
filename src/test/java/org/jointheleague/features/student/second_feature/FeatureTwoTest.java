package org.jointheleague.features.student.second_feature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.second_features.HighLowGame;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FeatureTwoTest {
	private final String testChannelName = "FeatureTwo";
    private final FeatureTwo featureTwo = new FeatureTwo(testChannelName);

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
        String command = featureTwo.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(featureTwo.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(featureTwo.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        featureTwo.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        featureTwo.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage("");
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

//FEATURE-SPECIFIC  TESTS

    @Test
    void itShouldNotAcceptGuessIfGameIsNotStarted() {
        //Given
        String command = "!Hit";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        featureTwo.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please start the game before using this command.");

    }

    

    @Test
    void itShouldTellTheUserIfTheyLose() {
        //Given
        String command = "!Hit";
        featureTwo.yourValue = 20;
        featureTwo.botValue = 21;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        featureTwo.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("You lose! Score: " + featureTwo.botValue);

    }

    @Test
    void itShouldTellTheUserIfTheyWin() {
        //Given
        String command = "!Hit";
        featureTwo.yourValue = 21;
        featureTwo.botValue = 20;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        featureTwo.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("You win! Score: " + featureTwo.yourValue);
    }

   
}
