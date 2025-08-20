package org.jointheleague.features.student.third_feature;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

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
        MessageCreateAction mockAction = mock(MessageCreateAction.class);

        when(receivedMessage.getMessageContent()).thenReturn(featureThree.COMMAND);
        when(receivedMessage.getMessageChannel()).thenReturn(text);

        when(text.sendFiles(any(FileUpload.class))).thenReturn(mockAction);
        when(mockAction.setEmbeds(any(MessageEmbed.class))).thenReturn(mockAction);
        when(mockAction.complete()).thenReturn(null);

        when(text.sendMessage(anyString())).thenReturn(mockAction);
        doNothing().when(mockAction).queue(any());
        featureThree.handle(receivedMessage);

        verify(text).sendFiles(any(FileUpload.class));
        verify(text).sendMessage(anyString());
        verify(mockAction).queue(any());
    }
    @Test
    void itShouldGetAPI() throws IOException {
        ArrayList<String> dataList = featureThree.getAPODData();
        assertNotNull(dataList);
    }
    @Test
    void itShouldGiveMoreInfo() throws IOException {
        featureThree.messageSent = true;
        featureThree.waitForMessage = true;
        when(receivedMessage.getMessageContent()).thenReturn("yes");
        TextChannel mockChannel = mock(TextChannel.class);
        when(receivedMessage.getMessageChannel()).thenReturn( mockChannel);

        MessageCreateAction mockAction = mock(MessageCreateAction.class);
        when(mockChannel.sendMessageEmbeds(any(MessageEmbed.class))).thenReturn(mockAction);
        doNothing().when(mockAction).queue(any());
        featureThree.handle(receivedMessage);


        verify(mockChannel, times(1)).sendMessageEmbeds(any(MessageEmbed.class));
        verify(mockAction, times(1)).queue();
    }
    @Test
    void itShouldResetWaitForMessageOnNonYes() throws IOException {
        featureThree.messageSent = true;
        featureThree.waitForMessage = true;
        when(receivedMessage.getMessageContent()).thenReturn("no");

        featureThree.handle(receivedMessage);

        assertFalse(featureThree.waitForMessage);
        assertTrue(featureThree.messageSent); // still true, only reset on "yes"
    }
    @Test
    void itShouldBeReadyForResponse() throws IOException {
        TextChannel text = mock(TextChannel.class);
        MessageCreateAction mockAction = mock(MessageCreateAction.class);

        when(receivedMessage.getMessageContent()).thenReturn(featureThree.COMMAND);
        when(receivedMessage.getMessageChannel()).thenReturn(text);
        when(text.sendFiles(any(FileUpload.class))).thenReturn(mockAction);
        when(mockAction.setEmbeds(any(MessageEmbed.class))).thenReturn(mockAction);
        when(mockAction.complete()).thenReturn(null);

        when(text.sendMessage(anyString())).thenReturn(mockAction);
        doAnswer(invocation -> {
            Consumer<Message> successCallback = invocation.getArgument(0);
            successCallback.accept(mock(Message.class));
            return null;
        }).when(mockAction).queue(any());
        featureThree.handle(receivedMessage);
        assertTrue(featureThree.waitForMessage);
        assertTrue(featureThree.messageSent);
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
