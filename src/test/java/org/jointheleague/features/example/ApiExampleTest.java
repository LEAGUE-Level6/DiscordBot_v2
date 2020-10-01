//package org.jointheleague.features.example;
//
//import org.javacord.api.entity.channel.TextChannel;
//import org.javacord.api.event.message.MessageCreateEvent;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.mockito.Mockito.*;
//
//public class ApiExampleTest {
//
//    private final String testChannelName = "test";
//    private final ApiExample underTest = new ApiExample(testChannelName);
//
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//
//    @Mock
//    private MessageCreateEvent messageCreateEvent;
//
//    @Mock
//    private TextChannel textChannel;
//
//    @AfterEach
//    public void itShouldNotPrintToSystemOut() {
//        assertThat(outContent.toString()).isBlank();
//        System.setOut(originalOut);
//    }
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        System.setOut(new PrintStream(outContent));
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
//        assertThat(command).isNotBlank();
//    }
//
//    @Test
//    void itShouldHandleMessagesWithOnlyCommandByAskingForTopic() {
//        //Given
//        when(messageCreateEvent.getMessageContent()).thenReturn(underTest.COMMAND);
//        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
//
//        //When
//        underTest.handle(messageCreateEvent);
//
//        //Then
//        verify(textChannel, times(1)).sendMessage("Please put a word after the command");
//    }
//
//    @Test
//    void itShouldHandleMessagesWithTopic() {
//        //Given
//        String topic = "cats";
//        when(messageCreateEvent.getMessageContent()).thenReturn(underTest.COMMAND + " " + topic);
//        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
//
//        //When
//        underTest.handle(messageCreateEvent);
//
//        //Then
//        verify(textChannel, times(1)).sendMessage(anyString());
//
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
//
//    }
//
//    @Test
//    void itShouldHaveAHelpEmbed() {
//        //Given
//
//        //When
//
//        //Then
//        assertThat(underTest.getHelpEmbed()).isNotNull();
//    }
//
//    @Test
//    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
//        //Given
//
//        //When
//
//        //Then
//        assertThat(underTest.getHelpEmbed().getTitle()).isEqualTo(underTest.COMMAND);
//    }
//
//}