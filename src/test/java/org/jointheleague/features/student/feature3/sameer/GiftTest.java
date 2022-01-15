package org.jointheleague.features.student.feature3.sameer;


import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiftTest {

    private final String testChannelName = "test";
    private Gift giftTest;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @Mock
    private Client client;

    @Mock
    private Message message;

    @Mock
    private User user;

    @Mock
    private MessageAuthor author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftTest = new Gift(testChannelName, client);
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
        String command = giftTest.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!gift 1 <@1>");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessage()).thenReturn(message);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(message.getMentionedUsers()).thenReturn(Collections.singletonList(user));
        when(user.getIdAsString()).thenReturn("1");
        when(author.getIdAsString()).thenReturn("2");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("1")).thenReturn(new Document(map));
        when(client.findOne("2")).thenReturn(new Document(map));
        //When
        giftTest.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You gifted 1 minco dollars to <@1>");
        verify(client, times(1)).findOneAndUpdate("1", Updates.inc("mincoDollars", 1));
        verify(client, times(1)).findOneAndUpdate("2", Updates.inc("mincoDollars", -1));
    }

    @Test
    void shouldNotWorkWithInvalidInteger() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!gift yee <@1>");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessage()).thenReturn(message);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(message.getMentionedUsers()).thenReturn(Collections.singletonList(user));
        when(user.getIdAsString()).thenReturn("1");
        when(author.getIdAsString()).thenReturn("2");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("1")).thenReturn(new Document(map));
        when(client.findOne("2")).thenReturn(new Document(map));
        //When
        giftTest.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You wrote an invalid amount of minco dollars");
        verify(client, never()).findOneAndUpdate("1", Updates.inc("mincoDollars", 1));
        verify(client, never()).findOneAndUpdate("2", Updates.inc("mincoDollars", -1));
    }
    @Test
    void shouldNotWorkWithTooLittleMoney() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!gift 100 <@1>");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessage()).thenReturn(message);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(message.getMentionedUsers()).thenReturn(Collections.singletonList(user));
        when(user.getIdAsString()).thenReturn("1");
        when(author.getIdAsString()).thenReturn("2");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("1")).thenReturn(new Document(map));
        when(client.findOne("2")).thenReturn(new Document(map));
        //When
        giftTest.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You don't have that many minco dollars!");
        verify(client, never()).findOneAndUpdate("1", Updates.inc("mincoDollars", 1));
        verify(client, never()).findOneAndUpdate("2", Updates.inc("mincoDollars", -1));
    }

    @Test
    void shouldNotWorkWithNoMentionedUsers() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!gift 100 <@1>");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessage()).thenReturn(message);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(message.getMentionedUsers()).thenReturn(Collections.emptyList());
        when(user.getIdAsString()).thenReturn("1");
        when(author.getIdAsString()).thenReturn("2");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("1")).thenReturn(new Document(map));
        when(client.findOne("2")).thenReturn(new Document(map));
        //When
        giftTest.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You didn't mention a user or you mentioned multiple users");
        verify(client, never()).findOneAndUpdate("1", Updates.inc("mincoDollars", 1));
        verify(client, never()).findOneAndUpdate("2", Updates.inc("mincoDollars", -1));
    }

    @Test
    void shouldNotWorkWithMultipleUsers() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!gift 100 <@1>");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessage()).thenReturn(message);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(message.getMentionedUsers()).thenReturn(Arrays.asList(new User[]{user,user}));
        when(user.getIdAsString()).thenReturn("1");
        when(author.getIdAsString()).thenReturn("2");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("1")).thenReturn(new Document(map));
        when(client.findOne("2")).thenReturn(new Document(map));
        //When
        giftTest.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You didn't mention a user or you mentioned multiple users");
        verify(client, never()).findOneAndUpdate("1", Updates.inc("mincoDollars", 1));
        verify(client, never()).findOneAndUpdate("2", Updates.inc("mincoDollars", -1));
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        giftTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = giftTest.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = giftTest.getHelpEmbed().getTitle();
        String command = giftTest.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
