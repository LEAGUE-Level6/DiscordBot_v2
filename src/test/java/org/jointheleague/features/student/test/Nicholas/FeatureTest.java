package org.jointheleague.features.student.test.Nicholas;

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
import org.jointheleague.features.student.first_feature.FeatureOne;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FeatureTest {
	 private final String testChannelName = "test";
	    private final FeatureOne featureOne = new FeatureOne(testChannelName);

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
	        String command = featureTemplate.COMMAND;

	        //Then

	        if(!(featureTemplate instanceof FeatureTemplate)){
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
	        HelpEmbed helpEmbed = new HelpEmbed(featureTemplate.COMMAND, "test");
	        when(messageCreateEvent.getMessageContent()).thenReturn(featureTemplate.COMMAND);
	        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

	        //When
	        featureTemplate.handle(messageCreateEvent);

	        //Then
	        verify(textChannel, times(1)).sendMessage(anyString());
	    }

	    @Test
	    void itShouldNotHandleMessagesWithoutCommand() {
	        //Given
	        String command = "";
	        when(messageCreateEvent.getMessageContent()).thenReturn(command);

	        //When
	        featureTemplate.handle(messageCreateEvent);

	        //Then
	        verify(textChannel, never()).sendMessage("");
	    }

	    @Test
	    void itShouldHaveAHelpEmbed() {
	        //Given

	        //When
	        HelpEmbed actualHelpEmbed = featureTemplate.getHelpEmbed();

	        //Then
	        assertNotNull(actualHelpEmbed);
	    }

	    @Test
	    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
	        //Given

	        //When
	        String helpEmbedTitle = featureTemplate.getHelpEmbed().getTitle();
	        String command = featureTemplate.COMMAND;

	        //Then
	        assertEquals(command, helpEmbedTitle);
	    }
}
