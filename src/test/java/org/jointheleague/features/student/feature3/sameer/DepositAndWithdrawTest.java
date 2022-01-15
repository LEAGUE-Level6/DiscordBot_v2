package org.jointheleague.features.student.feature3.sameer;


import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepositAndWithdrawTest {

    private final String testChannelName = "test";
    private Deposit deposit;
    private Withdraw withdraw;

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
        deposit = new Deposit(testChannelName, client);
        withdraw = new Withdraw(testChannelName, client);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void shouldNotPrintToSystemOut() {
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    void shouldHaveACommand() {
        //Given

        //When
        String dcommand = deposit.COMMAND;
        String wcommand = withdraw.COMMAND;
        //Then
        assertNotEquals("", dcommand);
        assertNotEquals("!", dcommand);
        assertEquals('!', dcommand.charAt(0));
        assertNotNull(dcommand);
        assertNotEquals("", wcommand);
        assertNotEquals("!", wcommand);
        assertEquals('!', wcommand.charAt(0));
        assertNotNull(wcommand);
    }
    @Test
    void shouldDeposit() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!deposit 1");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        deposit.handle(messageCreateEvent);
        //Then
        verify(client, times(1)).findOneAndUpdate("724786310711214118", Updates.inc("mincoDollars", -1));
        verify(client, times(1)).findOneAndUpdate("724786310711214118", Updates.inc("bank", 1));
    }
    @Test
    void shouldWithdraw() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!withdraw 1");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 50);
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        withdraw.handle(messageCreateEvent);
        //Then
        verify(client, times(1)).findOneAndUpdate("724786310711214118", Updates.inc("mincoDollars", 1));
        verify(client, times(1)).findOneAndUpdate("724786310711214118", Updates.inc("bank", -1));
    }
    @Test
    void shouldCheckForInvalidIntegerWithdraw() {
        when(messageCreateEvent.getMessageContent()).thenReturn("!withdraw yee");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);

        //When
        withdraw.handle(messageCreateEvent);

        verify(textChannel, times(1)).sendMessage("You sent an invalid number");
    }

    @Test
    void shouldCheckForInvalidIntegerDeposit() {
        when(messageCreateEvent.getMessageContent()).thenReturn("!deposit yee");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);

        //When
        deposit.handle(messageCreateEvent);

        verify(textChannel, times(1)).sendMessage("You sent an invalid number");
    }

    @Test
    void shouldCheckForNoParameterWithdraw() {
        when(messageCreateEvent.getMessageContent()).thenReturn("!withdraw");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);

        //When
        withdraw.handle(messageCreateEvent);

        verify(textChannel, times(1)).sendMessage("You did not provide an integer parameter");
    }
    @Test
    void shouldCheckForNoParameterDeposit() {
        when(messageCreateEvent.getMessageContent()).thenReturn("!deposit");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);

        //When
        deposit.handle(messageCreateEvent);

        verify(textChannel, times(1)).sendMessage("You did not provide an integer parameter");
    }

    @Test
    void shouldNotWorkWithNotEnoughMoneyWithdraw() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!withdraw 1");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 50);
        map.put("bank", 0);
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        withdraw.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You don't have that amount of money to withdraw");
    }

    @Test
    void shouldNotWorkWithNotEnoughMoneyDeposit() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn("!deposit 1");
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("mincoDollars", 0);
        map.put("bank", 50);
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        deposit.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("You don't have that amount of money to deposit");
    }

    @Test
    void shouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        deposit.handle(messageCreateEvent);
        withdraw.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void shouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed dactualHelpEmbed = deposit.getHelpEmbed();
        HelpEmbed wactualHelpEmbed = withdraw.getHelpEmbed();
        //Then
        assertNotNull(dactualHelpEmbed);
        assertNotNull(wactualHelpEmbed);
    }

    @Test
    void shouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String dhelpEmbedTitle = deposit.getHelpEmbed().getTitle();
        String dcommand = deposit.COMMAND;
        String whelpEmbedTitle = withdraw.getHelpEmbed().getTitle();
        String wcommand =withdraw.COMMAND;

        //Then
        assertEquals(dcommand, dhelpEmbedTitle);
        assertEquals(wcommand, whelpEmbedTitle);
    }

}
