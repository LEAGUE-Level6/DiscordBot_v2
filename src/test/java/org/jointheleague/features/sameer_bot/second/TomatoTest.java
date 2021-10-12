package org.jointheleague.features.sameer_bot.second;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TomatoTest {
    private final String testChannelName = "test";
    private final Tomato tomato = new Tomato(testChannelName);

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
        Data.userToCoins.put("724786310711214118", 50);
        ArrayList<String> inv = new ArrayList<>();
        inv.add("tomato");
        Data.userToInventory.put("724786310711214118", inv);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = tomato.COMMAND;

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
        tomato.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = tomato.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldNotWorkIfUserDoesNotHaveATomato(){
        //Given
        Data.userToInventory.put("724786310711214118", new ArrayList());
        String command = "!tomato";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        //When
        tomato.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("You don't have a tomato!");
    }

    @Test
    void itShouldSendString() {
        //Given
        String command = "!tomato";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        //When
        tomato.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldIncreaseMoney() {//Given
        String command = "!tomato";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        //When

        tomato.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
        assertNotNull(Data.userToCoins.get("724786310711214118"));
        assertTrue(Data.userToCoins.get("724786310711214118") > 42);
    }

    @Test
    void itShouldRemoveTomato() {
        String command = "!tomato";
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
        when(messageAuthor.getIdAsString()).thenReturn("724786310711214118");

        //When

        tomato.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
        assertNotNull(Data.userToInventory.get("724786310711214118"));
        assertTrue(!Data.userToInventory.get("724786310711214118").contains("tomato"));
    }

}
