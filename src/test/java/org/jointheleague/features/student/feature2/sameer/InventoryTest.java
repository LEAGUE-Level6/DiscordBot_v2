package org.jointheleague.features.student.feature2.sameer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class InventoryTest {
    private final String testChannelName = "test";
    private final Inventory inventory = new Inventory(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ArrayList<String> inv = new ArrayList<>();
    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @Mock
    private MessageAuthor messageAuthor;

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
        inv = new ArrayList<>();
        Data.userToInventory.put("724786310711214118", inv);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = inventory.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
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
    void itShouldShowNoItems() {
        // Given
        String command = "!inventory";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        // When
        inventory.handle(messageCreateEvent);

        // Then
        verify(textChannel, times(1)).sendMessage("Your inventory is empty");
    }

    @Test
    void itShouldShowMilk() {
        // Given
        String command = "!inventory";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");
        inv.add("milk");
        Data.userToInventory.put("724786310711214118", inv);

        // When
        inventory.handle(messageCreateEvent);

        // Then
        verify(textChannel, times(1)).sendMessage(":milk: Milk\n");
    }

    @Test
    void itShouldShowMilkAndTomato() {
        // Given
        String command = "!inventory";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");
        inv.add("milk");
        inv.add("tomato");
        Data.userToInventory.put("724786310711214118", inv);

        // When
        inventory.handle(messageCreateEvent);

        // Then
        verify(textChannel, times(1)).sendMessage(":milk: Milk\n:tomato: Tomato\n");
    }
}
