package org.jointheleague.features.whale;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.jointheleague.features.whale.Schedule;
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

class SchedulingBotTest {

	@Mock
	DiscordApi api;
	@Mock
	User user;

	ApiGetter get = new ApiGetter(api);

	private final Schedule schedule = new Schedule("general", get);

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
		// Given

		// When
		String command = schedule.add;

		// Then
		assertNotEquals("", command);
		assertNotEquals("!", command);
		assertEquals('!', command.charAt(0));
		assertNotNull(command);
	}

	@Test
	void itShouldHandleMessagesWithHelpCommand() {
		// Given
		HelpEmbed helpEmbed = new HelpEmbed(schedule.helpEmbed.getTitle(), schedule.helpEmbed.getDescription());
		when(messageCreateEvent.getMessageContent()).thenReturn(helpEmbed.getDescription());
		when(messageCreateEvent.getChannel()).thenReturn((textChannel));
		when(api.getYourself()).thenReturn(user);
		when(user.getName()).thenReturn("Whale's Bot");

		// When
		schedule.handle(messageCreateEvent);

		// Then
		verify(textChannel, times(1)).sendMessage(anyString());
	}

	@Test
	void itShouldNotHandleMessagesWithoutCommand() {
		// Given
		String command = "";
		when(messageCreateEvent.getMessageContent()).thenReturn(command);

		// When
		schedule.handle(messageCreateEvent);

		// Then
		verify(textChannel, never()).sendMessage("");
	}

	@Test
	void itShouldHaveAHelpEmbed() {
		// Given

		// When
		HelpEmbed actualHelpEmbed = schedule.getHelpEmbed();

		// Then
		assertNotNull(actualHelpEmbed);
	}

	@Test
	void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
		// Given

		// When
		String helpEmbedTitle = schedule.getHelpEmbed().getTitle();
		String command = schedule.add;

		// Then
		assertEquals(command, helpEmbedTitle);
	}

}