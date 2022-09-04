package org.jointheleague.features.APOD_Testing;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.Feature_3.apod;
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
import static org.mockito.Mockito.never;

public class Apod_Test {

    private final String testChannelName = "test";
    private final apod ApodTest = new apod(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

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
        String command = ApodTest.COMMAND;

        //Then

        if (!(ApodTest instanceof apod)) {
            assertNotEquals("!command", command);
        }

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(ApodTest.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(ApodTest.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        ApodTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        ApodTest.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = ApodTest.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = ApodTest.getHelpEmbed().getTitle();
        String command = ApodTest.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    @Test
    void itShouldHaveExplanation(){
       // assertEquals()
        String exp = "Did you see the transit? While some watched by webcast, sky gazers in Europe, the Middle East, " +
                "Africa, and Asia were able to witness the complete 6 hour journey of Venus' silhouetted disk across the " +
                "face of the Sun. As seen from North America, the much heralded Venus Transit of 2004 was nearing its " +
                "final stages at sunrise yesterday in this telescopic image. The view looks across the Atlantic from " +
                "Tybee Island near Savannah, Georgia, USA. In fact, many in eastern North America experienced a dramatic " +
                "view of a perfect, dark, round Venus against a reddened Sun filtered by banks of low clouds. Ironically, the " +
                "Sun takes on the appearance of a cloud covered planet itself " +
                "as Venus marches toward the right through this dreamlike scene.";
        String cmd = "!apod 2004-06-09";
        when(messageCreateEvent.getMessageContent()).thenReturn(cmd);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        ApodTest.handle(messageCreateEvent);

        verify(textChannel, times(1)).sendMessage(exp);

    }

    @Test
    void itShouldHaveImage(){
        // assertEquals()
        String img = "https://apod.nasa.gov/apod/image/0406/vt2004_westlake_c1.jpg";
        String cmd = "!apod 2004-06-09";
        when(messageCreateEvent.getMessageContent()).thenReturn(cmd);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        ApodTest.handle(messageCreateEvent);

        verify(textChannel, times(1)).sendMessage(img);

    }
}


