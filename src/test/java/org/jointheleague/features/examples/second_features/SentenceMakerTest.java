package org.jointheleague.features.examples.second_features;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
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

class SentenceMakerTest {

    private final String testChannelName = "test";
    private final SentenceMaker sentenceMaker = new SentenceMaker(testChannelName);

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
        String command = sentenceMaker.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }
    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(sentenceMaker.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(sentenceMaker.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        sentenceMaker.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }
    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        sentenceMaker.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }
    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = sentenceMaker.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }
    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = sentenceMaker.getHelpEmbed().getTitle();
        String command = sentenceMaker.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

//FEATURE SPECIFIC TESTS

    @Test
    void itShouldSendErrorMessageIfResponseIsNotANumber() {
        //Given
        String guess = "ten";
        String command = "!maker " + guess;
        sentenceMaker.check = 3;
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        sentenceMaker.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Please format your response like this: " + sentenceMaker.COMMAND + " 5");
    }


}