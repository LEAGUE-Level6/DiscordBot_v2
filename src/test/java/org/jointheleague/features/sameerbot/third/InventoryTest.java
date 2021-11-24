package org.jointheleague.features.sameerbot.third;


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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class InventoryTest {

    private final String testChannelName = "test";
    private Inventory inventory;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventory = new Inventory(testChannelName, client);
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
        String command = inventory.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void ifNoInventoryIsFound() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(inventory.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("inventory", new ArrayList<String>());
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        inventory.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You don't have any items in your inventory");
    }

    @Test
    void allItemsTest() {
        when(messageCreateEvent.getMessageContent()).thenReturn(inventory.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("inventory", new ArrayList<>(Arrays.asList(new String[]{"01","02","03","04","05","06","07","08","09","10","11","11-0", "11-1", "11-2", "12"})));
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        inventory.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage(":ring: Marriage Ring\n:diamond_shape_with_a_dot_inside: Diamond Crown\n:cowboy: Cowboy Hat\n:tomato: Tomato\n:candy: Candy\nJellyfish\n:bear: Bear\n:cactus: Cactus\n:fire: Fire\nLootbox\n:egg: Raw Egg\n:egg: Boiled Egg\n:egg: Scrambled Eggs\n:egg: Omelette\n:banana: Banana\n");
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        inventory.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = inventory.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = inventory.getHelpEmbed().getTitle();
        String command = inventory.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
