//package org.jointheleague.features.example;
//
//import org.javacord.api.entity.channel.TextChannel;
//import org.javacord.api.entity.message.embed.EmbedBuilder;
//import org.javacord.api.event.message.MessageCreateEvent;
//import org.jointheleague.help_embed.HelpListener;
//import org.jointheleague.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.mockito.Mockito.*;
//
//public class HelpListenerTest {
//
//    private final String testChannelName = "test";
//    private final HelpListener underTest = new HelpListener(testChannelName);
//
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//
//
//    @Mock
//    private MessageCreateEvent messageCreateEvent;
//
//    @Mock
//    private TextChannel textChannel;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @AfterEach
//    public void itShouldNotPrintToSystemOut() {
//        String expected  = "";
//        String actual = outContent.toString();
//
//        assertEquals(expected, actual);
//        System.setOut(originalOut);
//    }
//
//    @Test
//    void itShouldHaveACommand() {
//        //Given
//
//        //When
//        String command = underTest.COMMAND;
//
//        //Then
//        assertNotEquals("", command);
//        assertNotEquals("!command", command);
//    }
//
//    @Test
//    void itShouldHandleMessagesWithCommand() {
//        //Given
//        HelpEmbed helpEmbed = new HelpEmbed(underTest.COMMAND, "test");
//        when(messageCreateEvent.getMessageContent()).thenReturn(underTest.COMMAND);
//        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
//
//
//        //When
//        underTest.handle(messageCreateEvent);
//
//        //Then
//        verify(textChannel, times(1)).sendMessage(anyString());
//    }
//
//    @Test
//    void itShouldNotHandleMessagesWithoutCommand() {
//        //Given
//        String command = "";
//        when(messageCreateEvent.getMessageContent()).thenReturn(command);
//
//        //When
//        underTest.handle(messageCreateEvent);
//
//        //Then
//        verify(textChannel, never()).sendMessage();
//    }
//
//}