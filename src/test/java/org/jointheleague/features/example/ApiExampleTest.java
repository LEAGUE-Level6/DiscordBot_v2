package org.jointheleague.features.example;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.example.ApiExample;
import org.jointheleague.features.example.RandomNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ApiExampleTest {

    private final String testChannelName = "test";
    private final ApiExample underTest = new ApiExample(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    //test that:
    //there are no print statements
    //


    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = RandomNumber.COMMAND;

        //Then
        assertThat(command).isNotBlank();
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(RandomNumber.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();

    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given
        //When

        //Then
        assertThat(underTest.getHelpEmbed()).isNotNull();
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given
        //When

        //Then
        assertThat(underTest.getHelpEmbed().getTitle()).isEqualTo(RandomNumber.COMMAND);
    }

    @Test
    void itShouldNotPrintAnythingUsingSystemOutPrintlnWhenHandlingMessages() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(RandomNumber.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        assertThat(outContent.toString()).isBlank();
    }



    //Specific to RandomNumber
    @Test
    void itShouldHandleMessagesWhenRangeOfValuesIsProvided() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(RandomNumber.COMMAND + " 10-100");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        underTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

}