package org.jointheleague.features.sameer_bot.second;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class BuyTest {
    private final String testChannelName = "test";
    private final Buy buy = new Buy(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

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
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = buy.COMMAND;

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
    void itShouldSayNotSpecifyItem() {
        //Given
        String command = "!buy";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        buy.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("You did not specify an item");
    }

    @Test
    void itShouldNotWorkIfItemIsNotInShop() {
        // Given
        String command="!buy aosidjaosidj";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        //When
        buy.handle(messageCreateEvent);

        //Then
        verify(textChannel,times(1)).sendMessage("That item isn't in the shop");
    }

    @Test
    void buyMilk() {
        // Given
        String command = "!buy milk";
        int price = 10;
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        // When
        buy.handle(messageCreateEvent);

        // Then
        verify(textChannel,times(1)).sendMessage("You bought milk for :coin: 10");
        assertEquals((int)Data.userToCoins.get("724786310711214118"), 40);
        Data.userToCoins.put("724786310711214118", 50);
    }

    @Test
    void doesNotWorkIfUserDoesNotHaveEnoughMoney() {
        // Given
        Data.userToCoins.put("724786310711214118", 5);
        String command = "!buy milk";
        int price = 10;
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        // When
        buy.handle(messageCreateEvent);

        // Then
        verify(textChannel,times(1)).sendMessage("You need :coin: 10 to buy this item.");
        assertEquals((int) Data.userToCoins.get("724786310711214118"), 5);
        Data.userToCoins.put("724786310711214118", 50);
    }
}
