package feature3.Nathan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class Feature3Test {
	 private final String testChannelName = "test";
	    private final Feature3 feature3 = new Feature3(testChannelName);

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
	        String command = feature3.COMMAND;

	        //Then

	        if(!(feature3 instanceof Feature3)){
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
	        HelpEmbed helpEmbed = new HelpEmbed(feature3.COMMAND, "test");
	        when(messageCreateEvent.getMessageContent()).thenReturn(feature3.COMMAND);
	        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

	        //When
	        feature3.handle(messageCreateEvent);

	        //Then
	        verify(textChannel, times(1)).sendMessage(anyString());
	    }

	    @Test
	    void itShouldNotHandleMessagesWithoutCommand() {
	        //Given
	        String command = "";
	        when(messageCreateEvent.getMessageContent()).thenReturn(command);

	        //When
	        feature3.handle(messageCreateEvent);

	        //Then
	        verify(textChannel, never()).sendMessage();
	    }

	    @Test
	    void itShouldHaveAHelpEmbed() {
	        //Given

	        //When
	        HelpEmbed actualHelpEmbed = feature3.getHelpEmbed();

	        //Then
	        assertNotNull(actualHelpEmbed);
	    }

	    @Test
	    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
	        //Given

	        //When
	        String helpEmbedTitle = feature3.getHelpEmbed().getTitle();
	        String command = feature3.COMMAND;

	        //Then
	        assertEquals(command, helpEmbedTitle);
	    }

}
