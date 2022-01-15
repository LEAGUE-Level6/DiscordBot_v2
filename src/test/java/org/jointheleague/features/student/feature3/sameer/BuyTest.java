package org.jointheleague.features.student.feature3.sameer;


import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.javacord.api.entity.channel.TextChannel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BuyTest {

    private final String testChannelName = "test";
    private Buy buy;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @Mock
    private Client client;

    @Mock
    private MessageAuthor author;

    private Optional<User> userOptional;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buy = new Buy(testChannelName, client);
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
        String command = buy.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }
    // all tests are with candy
    @Test
    void itShouldDecreaseMoney() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!buy candy");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        map.put("inventory", new ArrayList());
        when(client.findOne(anyString())).thenReturn(new Document(map));

        //When
        buy.handle(messageCreateEvent);
        //Then
        verify(client, times(1)).findOneAndUpdate("724786310711214118", Updates.inc("mincoDollars", -33));
    }

    @Test
    void itShouldNotWorkWithTooLittleMoney() {
        when(messageCreateEvent.getMessageContent()).thenReturn("!buy candy");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 1);
        map.put("bank", 50000);
        map.put("inventory", new ArrayList());
        when(client.findOne(anyString())).thenReturn(new Document(map));

        //When
        buy.handle(messageCreateEvent);
        //Then
        verify(client, times(0)).findOneAndUpdate("724786310711214118", Updates.inc("mincoDollars", -33));
        verify(textChannel, times(1)).sendMessage("You need 33 md to buy a candy");
    }

    @Test
    void itShouldNotWorkIfItemAlreadyExists() {
        when(messageCreateEvent.getMessageContent()).thenReturn("!buy candy");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        ArrayList<String> list = new ArrayList<>();
        list.add("05");
        map.put("inventory", list);
        when(client.findOne(anyString())).thenReturn(new Document(map));

        //When
        buy.handle(messageCreateEvent);
        //Then
        verify(client, times(0)).findOneAndUpdate("724786310711214118", Updates.inc("mincoDollars", -33));
        verify(textChannel, times(1)).sendMessage("You already have a candy!");
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        buy.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = buy.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = buy.getHelpEmbed().getTitle();
        String command = buy.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
