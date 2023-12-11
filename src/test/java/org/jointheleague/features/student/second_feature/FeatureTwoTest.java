package org.jointheleague.features.student.second_feature;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
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
import static org.mockito.Mockito.never;

public class FeatureTwoTest {
    private final String testChannelName = "test";
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
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = featureTwo.COMMAND;

        //Then

        if(!(featureTwo instanceof FeatureTemplate)){
            assertNotEquals("!command", command);
        }

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
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
@Test
void itShouldNotAcceptGuessIfGameIsNotStarted(){
    // Do the same thing as HighLowGameTest.java
    String command = "!popquiz do something";
    when(messageCreateEvent.getMessageContent()).thenReturn(command);
    when(messageCreateEvent.getChannel()).thenReturn(textChannel);

    featureTwo.handle(messageCreateEvent);

    verify(textChannel, times(1)).sendMessage("Please be sure to start the game before adding any answer prompt. Start the game by typing !\u200Epopquiz.");
    }
@Test
void itShouldTellUserTheirAnswerIsCorrect(){
    featureTwo.questionNumber = 5;
    featureTwo.points = 4;
    String command = "!popquiz A";
    when(messageCreateEvent.getMessageContent()).thenReturn(command);
    when(messageCreateEvent.getChannel()).thenReturn(textChannel);

    featureTwo.handle(messageCreateEvent);

    verify(textChannel, times(1)).sendMessage("Correct! Adding one point to your score. Time to tally your final score!");
    assertEquals(5, featureTwo.points);
    }
@Test
void itShouldTellUserTheirAnswerIsIncorrect(){
    //Have it check point addition, message printing, and other things related to the answer being correct/incorrect.
    featureTwo.questionNumber = 5;
    featureTwo.points = 4;
    String command = "!popquiz C";
    when(messageCreateEvent.getMessageContent()).thenReturn(command);
    when(messageCreateEvent.getChannel()).thenReturn(textChannel);

    featureTwo.handle(messageCreateEvent);

    verify(textChannel, times(1)).sendMessage("Ooh, so close. Sadly we cannot give points for incorrect answers. Time to tally your final score!");
    assertEquals(4, featureTwo.points);
    }
}
