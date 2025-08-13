package org.jointheleague.features.student.third_feature;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class FeatureThreeTest {
    private final String testChannelName = "test";
    private final FeatureThree featureThree = new FeatureThree(testChannelName);

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
        String command = featureThree.COMMAND;

        //Then

        if(!(featureThree instanceof Feature)){
            assertNotEquals("!command", command);
        }

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertNotEquals("!command", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() throws IOException {
        TextChannel text = mock(TextChannel.class);
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(featureThree.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn(featureThree.COMMAND);

        when(receivedMessage.getMessageChannel()).thenReturn(text);

        //When
        featureThree.handle(receivedMessage);



        //Then

    }


    @Test
    void itShouldNotHandleMessagesWithoutCommand()  {
        //Given
        String command = "";
        when(receivedMessage.getMessageContent()).thenReturn(command);

        //When
        try {
            featureThree.handle(receivedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Then
        verify(receivedMessage, never()).sendResponse("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = featureThree.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = featureThree.getHelpEmbed().getTitle();
        String command = featureThree.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
