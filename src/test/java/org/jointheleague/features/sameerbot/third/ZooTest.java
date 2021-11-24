package org.jointheleague.features.sameerbot.third;


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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZooTest {

    private final String testChannelName = "test";
    private Zoo zoo;

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

    @Mock
    private Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        zoo = new Zoo(testChannelName, client);
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
        String command = zoo.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void ifNoZooIsFound() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(zoo.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(author);
        when(messageCreateEvent.getMessage()).thenReturn(message);
        when(message.getMentionedUsers()).thenReturn(Collections.emptyList());
        userOptional = Optional.of(user);
        when(message.getUserAuthor()).thenReturn(userOptional);
        when(user.getName()).thenReturn("person");
        when(user.getIdAsString()).thenReturn("724786310711214118");
        when(author.getIdAsString()).thenReturn("724786310711214118");
        HashMap<String, Object> map = new HashMap<>();
        map.put("zoo", new ArrayList<String>());
        when(client.findOne("724786310711214118")).thenReturn(new Document(map));

        //When
        zoo.handle(messageCreateEvent);
        //Then
        verify(textChannel, times(1)).sendMessage("person doesn't have any animals in their zoo");
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        zoo.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = zoo.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = zoo.getHelpEmbed().getTitle();
        String command = zoo.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
