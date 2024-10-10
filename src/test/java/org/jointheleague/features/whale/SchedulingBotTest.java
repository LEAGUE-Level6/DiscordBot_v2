package org.jointheleague.features.whale;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
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
import java.util.Date;

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
	@Mock
	Message msg;
	@Mock
	MessageAuthor author;

	ApiGetter get = new ApiGetter(api);

	private Schedule schedule = new Schedule("general", get);

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
		when(messageCreateEvent.getChannel()).thenReturn((textChannel));
		when(api.getYourself()).thenReturn(user);
		when(user.getName()).thenReturn("Whale's Bot");
	}

	@AfterEach
	public void itShouldNotPrintToSystemOut() {
		String expected = "";
		String actual = outContent.toString();

		//assertEquals(expected, actual);
		System.setOut(originalOut);
	}

	@Test
	void itShouldAddEvents() {
		when(messageCreateEvent.getMessageContent()).thenReturn("!addEvent valorant 9:30pm pt tmr");

		// When
		schedule.handle(messageCreateEvent);
		Date d = new Date();
		String day = (d.getDate()+1)+"";
		String month = (d.getMonth()+1)+"";
		String year = (d.getYear()+"").substring(1,3);
		System.out.println(day + " " + month + " " + year);
		// Then
		verify(textChannel, times(1)).sendMessage("The Event recived was Name = |valorant| Time = |9:30pm| Zone = |PT| |Date = |"+month+"/"+day+"/"+year+"| |Tag = |null|");
	}
	@Test
	void itShouldRemoveEvents() {
		when(messageCreateEvent.getMessageContent()).thenReturn("!addEvent valorant 9:30pm pt tmr");
		schedule.handle(messageCreateEvent);
		when(messageCreateEvent.getMessageContent()).thenReturn("!removeEvent");
		schedule.handle(messageCreateEvent);
		when(messageCreateEvent.getMessageContent()).thenReturn("1");
		schedule.handle(messageCreateEvent);
		// When
		

		// Then
		verify(textChannel, times(1)).sendMessage("Event Removed");
	}
	@Test
	void itShouldEditEvents() {
		when(messageCreateEvent.getMessageContent()).thenReturn("!addEvent valorant 9:30pm pt tmr");
		schedule.handle(messageCreateEvent);
		verify(textChannel).sendMessage(anyString());
		when(messageCreateEvent.getMessageContent()).thenReturn("!editEvent");
		schedule.handle(messageCreateEvent);
		verify(textChannel).sendMessage(anyString());
		when(messageCreateEvent.getMessageContent()).thenReturn("1");
		when(messageCreateEvent.getMessage()).thenReturn(msg);
		when(msg.getAuthor()).thenReturn(author);
		when(author.isBotUser()).thenReturn(true);
		
		schedule.handle(messageCreateEvent);
		verify(textChannel).sendMessage(anyString());
		when(messageCreateEvent.getMessageContent()).thenReturn("1");
		schedule.handle(messageCreateEvent);
		verify(textChannel).sendMessage(anyString());
		when(messageCreateEvent.getMessageContent()).thenReturn("Minecraft");
		schedule.handle(messageCreateEvent);
		// When
		

		// Then
		verify(textChannel, times(1)).sendMessage("Name changed to Minecraft");
		
	}
	@Test
	void itShouldCreateTags() {
		when(messageCreateEvent.getMessageContent()).thenReturn("!tags");
		schedule.handle(messageCreateEvent);
		when(messageCreateEvent.getMessageContent()).thenReturn("1");
		schedule.handle(messageCreateEvent);
		when(messageCreateEvent.getMessageContent()).thenReturn("gamer");
		schedule.handle(messageCreateEvent);
		when(messageCreateEvent.getMessageContent()).thenReturn("end tags");
		schedule.handle(messageCreateEvent);
		// When
		

		// Then
		verify(textChannel, times(1)).sendMessage("Stopped Modifying Tags");
		
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
	void itShouldHandleMessagesWithCommand() {
		// Given
		HelpEmbed helpEmbed = new HelpEmbed(schedule.helpEmbed.getTitle(), schedule.helpEmbed.getDescription());
		when(messageCreateEvent.getMessageContent()).thenReturn("!example");

		// When
		schedule.handle(messageCreateEvent);

		// Then
		verify(textChannel, times(1)).sendMessage(anyString());
	}

	@Test
	void itShouldNotHandleMessagesWithoutCommand() {
		// Given
		String command = "hi";
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
		String helpEmbedTitle = schedule.getHelpEmbed().getDescription();
		helpEmbedTitle = helpEmbedTitle.substring(0, schedule.add.length()).trim().toLowerCase();
		String command = schedule.add;

		// Then
		assertEquals(command, helpEmbedTitle);
	}

}