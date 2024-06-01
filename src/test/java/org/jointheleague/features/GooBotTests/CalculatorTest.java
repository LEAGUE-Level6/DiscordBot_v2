package org.jointheleague.features.GooBotTests;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.GooBotFeatures.Calculator;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    private final String testChannelName = "Calculator";
    private final Calculator calculator = new Calculator(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    double num1 = 5;

    double num2 = 7;

    String multiply = "x";
    String divide = "/";
    String subtract = "-";
    String add = "+";
    String modulo = "%";
    String exponent = "^";

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
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = calculator.COMMAND;

        //Then

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertNotEquals("!command", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(calculator.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(calculator.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        calculator.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        calculator.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = calculator.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = calculator.getHelpEmbed().getTitle();
        String command = calculator.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void itShouldMultiply() {
        //Test Multiply
        when(messageCreateEvent.getMessageContent()).thenReturn("!calculator 5,x,7");
        verify(mock(Calculator.class), times(1)).calculate(messageCreateEvent, num1, num2, multiply);

        double expected = 35;
        double actual = calculator.answer;
        assertEquals(expected, actual, 0);
    }

    @Test
    void itShouldAdd() {
        //Test Add
        when(messageCreateEvent.getMessageContent()).thenReturn("!calculator 5,+,7");
        verify(mock(Calculator.class), times(1)).calculate(messageCreateEvent, num1, num2, add);

        double expected = 12;
        double actual = calculator.answer;
        assertEquals(expected, actual, 0);
    }

    @Test
    void itShouldSubtract() {
        //Test Subtract
        when(messageCreateEvent.getMessageContent()).thenReturn("!calculator 5,-,7");
        verify(mock(Calculator.class), times(1)).calculate(messageCreateEvent, num1, num2, subtract);

        double expected = -2;
        double actual = calculator.answer;
        assertEquals(expected, actual, 0);
    }

    @Test
    void itShouldDivide() {
        //Test Divide
        when(messageCreateEvent.getMessageContent()).thenReturn("!calculator 5,/,7");
        verify(mock(Calculator.class), times(1)).calculate(messageCreateEvent, num1, num2, divide);

        double expected = 0.714;
        double actual = calculator.answer;
        assertEquals(expected, actual, 1);
    }

    @Test
    void itShouldExponentiate() {
        //Test Exponentiation
        when(messageCreateEvent.getMessageContent()).thenReturn("!calculator 5,^,7");
        verify(mock(Calculator.class), times(1)).calculate(messageCreateEvent, num1, num2, exponent);

        double expected = 78125.0;
        double actual = calculator.answer;
        assertEquals(expected, actual, 0);

    }

    @Test
    void itShouldUseModulo() {
        //Test Modulo Ability
        when(messageCreateEvent.getMessageContent()).thenReturn("!calculator 5,%,7");
        verify(mock(Calculator.class), times(1)).calculate(messageCreateEvent, num1, num2, modulo);

        double expected = 5;
        double actual = calculator.answer;
        assertEquals(expected, actual, 0);

    }

}
