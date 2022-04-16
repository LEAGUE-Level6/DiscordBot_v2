package feature2.Nathan;

	import org.javacord.api.entity.channel.TextChannel;
	import org.javacord.api.event.message.MessageCreateEvent;
	import org.jointheleague.features.abstract_classes.Feature;
	import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.Mock;
	import org.mockito.MockitoAnnotations;

	import java.io.ByteArrayOutputStream;
	import java.io.PrintStream;

	import static org.junit.Assert.assertEquals;
	import static org.junit.Assert.assertNotNull;
	import static org.junit.jupiter.api.Assertions.*;
	import static org.mockito.ArgumentMatchers.anyString;
	import static org.mockito.Mockito.*;
	import static org.mockito.Mockito.never;

	class Feature2Test {

	    private final String testChannelName = "test";
	    private final Feature2 feature2 = new Feature2(testChannelName);

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
	        String command = feature2.COMMAND;

	        //Then

	        if(!(feature2 instanceof Feature2)){
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
	        HelpEmbed helpEmbed = new HelpEmbed(feature2.COMMAND, "test");
	        when(messageCreateEvent.getMessageContent()).thenReturn(feature2.COMMAND);
	        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

	        //When
	        feature2.handle(messageCreateEvent);

	        //Then
	        verify(textChannel, times(1)).sendMessage(anyString());
	    }

	    @Test
	    void itShouldNotHandleMessagesWithoutCommand() {
	        //Given
	        String command = "";
	        when(messageCreateEvent.getMessageContent()).thenReturn(command);

	        //When
	        feature2.handle(messageCreateEvent);

	        //Then
	        verify(textChannel, never()).sendMessage();
	    }

	    @Test
	    void itShouldHaveAHelpEmbed() {
	        //Given

	        //When
	        HelpEmbed actualHelpEmbed = feature2.getHelpEmbed();

	        //Then
	        assertNotNull(actualHelpEmbed);
	    }

	    @Test
	    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
	        //Given

	        //When
	        String helpEmbedTitle = feature2.getHelpEmbed().getTitle();
	        String command = feature2.COMMAND;

	        //Then
	        assertEquals(command, helpEmbedTitle);
	    }

	}

