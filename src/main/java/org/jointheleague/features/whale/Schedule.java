package org.jointheleague.features.whale;

import static org.mockito.ArgumentMatchers.intThat;

import java.io.BufferedReader;
import java.util.*;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;
import org.w3c.dom.DOMException;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import net.aksingh.owmjapis.api.APIException;

//To fix
//adding a tag to an event making the date setting not work
//TODO
//allow a "all" command when setting tags
public class Schedule extends Feature {
	Timezones timeZones = new Timezones();
	ArrayList<Person> users = new ArrayList<Person>();
	ArrayList<Person> printedUsers = new ArrayList<Person>();
	ArrayList<Person> printedUsers2 = new ArrayList<Person>();
	List<Role> roles = new ArrayList<Role>();
	DiscordApi api;
	ApiGetter get;
	Boolean AMRmode = false;
	public final String add = "!addevent";
	public final String remove = "!removeevent";
	public final String[] edit = { "!editevent", "!edit" };
	public final String editTags = "!tags";
	public final String schedule = "!schedule";
	public final String start = "!startevent";
	public final String end = "!endevent";
	public final String settings = "!settings";
	public final String[] available = { "!ican", "imavailable" };
	public final String userTimeZone = "!timezones";
	// removing
	boolean areWeRemoving;

	// edit events
	boolean areWeEditing;
	boolean isEventSelected;
	boolean setName;
	boolean setTime;
	boolean setDate;
	boolean setPeople;
	int indexOfEvent;

	// tags/zones
	String lastRoleUsed;
	boolean areWePeopleing;
	boolean areWeTimezoneing;
	boolean areTagsBeingSet;
	boolean areZonesBeingSet;
	boolean nextTags;
	boolean nextZones;
	String mostRecentUserName;
	String mostRecentUserName2;
	int mostRecentUserIndex2;
	String lastRoleUsed2;

	// start events
	boolean areWeSelectingStartEvent;
	int closestDayAsInt;
	List<Event> closestEvents = new ArrayList<>();
	int howManyMinBeforeEventShouldReminderBeSent = 15;

	ArrayList<Event> eventList = new ArrayList<Event>();

	public Schedule(String channelName, ApiGetter get) {
		super(channelName);
		this.get = get;

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed("Schedule Bot", ""
				+ "!addEvent: Adds an event. Start with the event name, time, date, tag(optional but recomended(!tags)) \n\n"
				+ "!removeEvent: Removes an event\n\n"
				+ "!editEvent: change the name, time, date, or people attending an event\n\n"
				+ "!tags: edit the tags of users in an event (add a role name to only show users with that role)\n\n"
				+ "!timezones: edit users timezones so the bot knows what time for them the events will start (add a role name to only show users with that role)\n\n"
				+ "!schedule: lists all events \n\n" + "!startEvent: starts the nearest event early\n\n"
				+ "!endEvent: ends the current event \n\n"
				+ "!iCan/!imAvailable: allows you to choose what event(s) you can make it to \n\n"
				+ "!examples: shows examples for every command \n\n" + "!settings: configure the bot\n\n");
	}

	@Override
	public void handle(MessageCreateEvent discord) {
		String detectedTag = "null";
		api = get.getApi();
		String messageContent = discord.getMessageContent();
		if (messageContent.contains(api.getYourself().getName() + " has connected")) {
			setup(discord);
			checkTime(discord);
			System.out.println("setup ran");
		}
		if (messageContent.toLowerCase().startsWith("!examples")
				|| messageContent.toLowerCase().startsWith("!example")) {
			String examples = "Examples: \n"
					+ "a /'->/' will mean a new messgae and (/'command/'/'command 2/') will mean 2 possible commands to send \n"
					+ "!addEvent Valorant Grind 9:30pm pdt fri &val \n" + "!removeEvent -> (1/3, 5)\n"
					+ "!tags spectator -> 1 tags -> spectator\n" + "!timezones -> 2 timezones -> GMT\n" + "!schedule \n"
					+ "!endEvent \n" + "!pollEvent -> 2\n" + "!iCan/!imAvailable -> (1,2/3)\n" + "!examples \n"
					+ "!settings -> addTimeZone IST +12:30";
			discord.getChannel().sendMessage(examples);
		}
		// thsi will add the strings to the eventList
		// ADD
		// ADD
		// ADD
		// ADD
		if (messageContent.toLowerCase().startsWith(add) || messageContent.toLowerCase().startsWith("!addevnt")) {
			System.out.println("!addEvent called");
			String eventString = messageContent.substring(add.length()).trim();
			if (eventString.contains(" ")) {
				System.out.println("message contains a space");
				String[] values = eventString.split(" ");
				ArrayList<User> usersForEvent = new ArrayList<User>();
				String time = "";
				Time realTime;
				String name = "";
				String date = "";
				String realDate = "";

				// get tbe time/date from the rest of the string
				boolean isThereTime = false;
				ArrayList<Integer> indexOfTimes = new ArrayList<Integer>();
				for (int i = 0; i < 10; i++) {
					if (eventString.contains(i + ":")) {
						indexOfTimes.add(eventString.indexOf(i + ":") + 1);
						System.out.println("A \':\' was found at index" + (eventString.indexOf(i + ":") + 1));
						isThereTime = true;
					}
				}
				if (isThereTime == false) {
					discord.getChannel().sendMessage("No time included!");
					discord.addReactionsToMessage("❌");
					System.out.println("No time found");
				} else {
					System.out.println("A time was found ");
				}
				int indexOfTime = Integer.MAX_VALUE;
				for (int i = 0; i < indexOfTimes.size(); i++) {
					if (indexOfTimes.get(i) < indexOfTime) {
						indexOfTime = indexOfTimes.get(i);
						System.out.println("The index of the time was found(" + indexOfTime + ")");
					}
				}
				if (isANumber(eventString.charAt(indexOfTime - 2) + "")) {
					System.out.println("found at indexoftime -2");

					name = eventString.substring(0, indexOfTime - 3);
					time = eventString.substring(indexOfTime - 2, eventString.length()).trim();

					realTime = new Time(time.charAt(0) + "" + time.charAt(1) + "",
							time.charAt(3) + "" + time.charAt(4) + "");

					for (int i = 0; i < timeZones.usTimezoneMap.size(); i++) {
						if (time.toUpperCase().contains(" " + timeZones.usTimezoneMap.keySet().toArray()[i])) {
							System.out.println("Found timezone");
							realTime.setTimeZone((String) timeZones.usTimezoneMap.keySet().toArray()[i]);
							System.out.println("realTime timezone " + realTime.getTimeZone());
							break;
						}
					}
				} else if (isANumber(eventString.charAt(indexOfTime - 1) + "")) {

					System.out.println("found at indexoftime -1");
					name = eventString.substring(0, indexOfTime - 2);
					time = eventString.substring(indexOfTime - 1, eventString.length()).trim();
					realTime = new Time(time.charAt(0) + "", time.charAt(2) + "" + time.charAt(3) + "");
					for (int i = 0; i < timeZones.usTimezoneMap.size(); i++) {
						if (time.toUpperCase().contains("" + timeZones.usTimezoneMap.keySet().toArray()[i])) {
							System.out.println("Found timezone");
							realTime.setTimeZone((String) timeZones.usTimezoneMap.keySet().toArray()[i]);
							System.out.println("realTime timezone " + realTime.getTimeZone());
							break;
						}
					}

				} else {
					System.out.println("invalid time format");
					discord.getChannel().sendMessage("Invalid Format");
					discord.addReactionsToMessage("❌");
					realTime = null;
				}
				if (time.toLowerCase().contains("am")) {
					realTime.setIsPm(false);
				} else if (time.toLowerCase().contains("pm")) {
					realTime.setIsPm(true);
				}

				if (time.toLowerCase().contains("&")) {
					String tag = time.substring(time.indexOf('&') + 1, time.length());
					tag = tag.trim();
					for (int i = 0; i < users.size(); i++) {
						for (int o = 0; o < users.get(i).getTags().size(); o++) {
							if (tag.equalsIgnoreCase(users.get(i).getTags().get(o))) {
								usersForEvent.add(users.get(i).getUser());

							}
						}
					}
					time = time.substring(0, time.indexOf('&') - 1);
					detectedTag = tag;
				}
				System.out.println("time after prossesing is " + realTime.getHour() + " " + realTime.getMin());

				System.out.println("name and realtime values set");
				// get the date from the time var -Done
				// tmr will be the next day - Done
				// nothing will be same day or tmr depending on if the time is in the past
				// all days of the week(mon) will be the next day that is that day of the week
				// a maunual date (5/21) set that date in the same year
				String[] dateTime = time.split(" ");
				System.out.println("Time is " + time);
				date = dateTime[dateTime.length - 1];
				if (date.toLowerCase().contains("tmr")) {
					System.out.println("the string \"tmr\" was found");
					Date d = new Date();
					d.setMonth(d.getMonth());

					realDate = ((d.getMonth() + 1) + "/" + (d.getDate() + 1) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
					System.out.println("realDate set");
				} else if (date.toLowerCase().contains("mon") || date.toLowerCase().contains("monday")) {
					System.out.println("\'mon\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));
					if (d.getDay() == 1) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 1) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("tue") || date.toLowerCase().contains("tues")
						|| date.toLowerCase().contains("tuesday")) {
					System.out.println("\'tue\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 2) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 2) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("wed") || date.toLowerCase().contains("wednesday")) {
					System.out.println("\'tue\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 3) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 3) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("thurs") || date.toLowerCase().contains("thu")
						|| date.toLowerCase().contains("thur") || date.toLowerCase().contains("thursday")) {
					System.out.println("\'thurs\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 4) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 4) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("fri") || date.toLowerCase().contains("friday")) {
					System.out.println("\'fri\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 5) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 5) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("sat") || date.toLowerCase().contains("saturday")) {
					System.out.println("\'sat\' was found");
					Date d = new Date();
					int currentDate = d.getDate();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));
					if (d.getDay() == 6) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 6) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("sun") || date.toLowerCase().contains("sunday")) {
					System.out.println("\'sun\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 0) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");

					}
					while (d.getDay() != 0) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("/") || date.toLowerCase().contains("-")) {
					String[] fullDate;
					Date d = new Date();
					if (date.toLowerCase().contains("/")) {
						fullDate = date.split("/");
					} else if (date.toLowerCase().contains("-")) {
						fullDate = date.split("-");
					} else {
						discord.getChannel().sendMessage("Add a \"/\" or \"-\" between numbers in the date");
						discord.addReactionsToMessage("❌");
						fullDate = null;
					}
					if (fullDate.length == 2) {
						realDate = (fullDate[0] + "/" + fullDate[1] + "/"
								+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
					} else if (fullDate.length == 3) {
						realDate = (fullDate[0] + "/" + fullDate[1] + "/" + fullDate[2]);
					} else {
						discord.getChannel().sendMessage("Invalid Date");
						discord.addReactionsToMessage("❌");
					}
				} else {
					Date d = new Date();
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				}

				Event event = new Event(name, realTime, realDate);
				for (int i = 0; i < usersForEvent.size(); i++) {
					Person p = new Person(usersForEvent.get(i));
					event.addPeople(p);
				}
				eventList.add(event);

				System.out.println("Date = " + realDate);
				System.out.println("Timezone " + eventList.get(eventList.size() - 1).getTime().getTimeZone());
				System.out.println("event constructed");
				discord.addReactionsToMessage("✅");

			} else {
				discord.getChannel().sendMessage("Invalid Format");
				discord.addReactionsToMessage("❌");
			}

			discord.getChannel()
					.sendMessage("The Event recived was Name = |" + eventList.get(eventList.size() - 1).getName()
							+ "| Time = |" + eventList.get(eventList.size() - 1).getTime().getTimeAsString()
							+ "| Zone = |" + eventList.get(eventList.size() - 1).getTime().getTimeZone() + "|"
							+ " |Date = |" + eventList.get(eventList.size() - 1).getDate() + "|" + " |Tag = |"
							+ detectedTag + "|");
			System.out.println("Message sent to channel");

			// event.getChannel().sendMessage("Sending a message to the channel");
			for (int i = 0; i < eventList.size(); i++) {
				if (eventList.get(i).isLive() == true) {
					eventList.remove(i);
					i = 0;
				}
			}
		}

		// this will remove an event from the list
		// REMOVE
		// REMOVE
		// REMOVE
		// REMOVE
		if (messageContent.toLowerCase().startsWith(remove) || messageContent.toLowerCase().startsWith("!removeevnt")) {
			areWeRemoving = true;
			String listOfEvents = "Enter the number next to the event to remove it sperate numbers with commas to remove multiple\n\n";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += (i + 1) + ": " + eventList.get(i).getName() + " "
						+ eventList.get(i).getTime().getTimeAsString() + " " + eventList.get(i).getDate() + "\n";
			}

			discord.getChannel().sendMessage(listOfEvents);

		} else if (areWeRemoving) {
			int index = -1;
			if (isANumber(discord.getMessageContent())) {
				System.out.println("No commas");
				index = Integer.parseInt(discord.getMessageContent());
				eventList.remove(index - 1);
				discord.getChannel().sendMessage("Event Removed");
				areWeRemoving = false;
			} else if (discord.getMessageContent().contains(",")) {
				System.out.println("Has commas");
				String[] indexesString = discord.getMessageContent().split(",");
				System.out.println("split");
				Integer[] indexes = new Integer[indexesString.length];
				System.out.println("int array made");
				int smallest = Integer.MAX_VALUE;
				for (int i = 0; i < indexesString.length; i++) {
					System.out.println("parseInt for " + indexesString[i]);
					indexes[i] = Integer.parseInt(indexesString[i]);
				}
				Event[] events = new Event[indexes.length];
				System.out.println("Event List created");
				for (int i = 0; i < events.length; i++) {
					events[i] = eventList.get(indexes[i] - 1);
					System.out.println("Setting" + (indexes[i] - 1));
				}
				for (int i = 0; i < events.length; i++) {
					eventList.remove(events[i]);
					System.out.println("removing " + events[i].getName());
				}
				areWeRemoving = false;
			}

		}

		// EDIT EVENT
		if (messageContent.toLowerCase().startsWith(edit[0])
				|| messageContent.toLowerCase().startsWith(edit[1]) && !discord.getMessage().getAuthor().isBotUser()) {
			areWeEditing = true;
			String listOfEvents = "Enter the number next to the event to edit it\n\n";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += (i + 1) + ": " + eventList.get(i).getName() + " "
						+ eventList.get(i).getTime().getTimeAsString() + " " + eventList.get(i).getDate() + "\n";
			}
			discord.getChannel().sendMessage(listOfEvents);
		} else if (areWeEditing && !discord.getMessage().getAuthor().isBotUser()) {
			int index = -1;
			if (isANumber(discord.getMessageContent())) {
				index = Integer.parseInt(discord.getMessageContent());
				discord.getChannel()
						.sendMessage("Event: " + eventList.get(index - 1).getName() + " "
								+ eventList.get(index - 1).getTime().getTimeAsString() + " "
								+ eventList.get(index - 1).getDate() + " selected");
				indexOfEvent = index - 1;
				discord.getChannel().sendMessage("Enter \n 1: name \n 2: time \n 3: date \n 4: people");
				isEventSelected = true;
				areWeEditing = false;
			}
		} else if (isEventSelected && isANumber(discord.getMessageContent())
				&& !discord.getMessage().getAuthor().isBotUser()) {
			int option = Integer.parseInt(discord.getMessageContent());
			if (option == 1) {
				discord.getChannel().sendMessage("Enter the new name for the event");
				setName = true;
				areWeEditing = false;
			} else if (option == 2) {
				discord.getChannel().sendMessage(
						"Enter the new time for the event (ex. 10:30am PT) or add an end time by saying end time (ex. end time 12:30pm PT) format exactly like the examples");
				setTime = true;
				areWeEditing = false;
			} else if (option == 3) {
				discord.getChannel()
						.sendMessage("Enter the new date for the event (ex. 10/12/24) format exactly like this");
				setDate = true;
				areWeEditing = false;
			} else if (option == 4) {
				Event event = eventList.get(indexOfEvent);
				long serverId = discord.getServer().get().getId();
				Server server = api.getServerById(serverId).get();
				discord.getChannel().sendMessage(
						"Enter the number next to the user then remove (ex. 3 remove) to add say a username then add (ex. whale1929 add) make sure to use the username not the nickname. Use commas to add and remove multiple people (ex. 1 add, 4 remove)");
				String peopleInEvent = "";
				if (event.getPeople().size() == 0) {
					peopleInEvent += "\n There are no users in this event";
				} else {
					for (int i = 0; i < event.getPeople().size(); i++) {
						String nickname = "err";
						try {
							nickname = api.getUserById(event.getPeople().get(i).getUser().getId()).get()
									.getNickname(server) + "";
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						event.getPeople().get(i).setNickname(nickname);
						peopleInEvent += (i + 1) + ": " + event.getPeople().get(i).getNickname() + "("
								+ event.getPeople().get(i).getUsername() + ")\n";
					}
				}
				discord.getChannel().sendMessage(peopleInEvent);
				setPeople = true;
				areWeEditing = false;
			}
			isEventSelected = false;
		}

		else if (setName && !discord.getMessage().getAuthor().isBotUser()) {
			eventList.get(indexOfEvent).setName(messageContent.trim());
			discord.getChannel().sendMessage("Name changed to " + eventList.get(indexOfEvent).getName());
			setName = false;
		} else if (setTime && !discord.getMessage().getAuthor().isBotUser()) {
			boolean endEvent = false;
			if (messageContent.toLowerCase().contains("end event")) {
				messageContent = messageContent.toLowerCase();
				messageContent.replace("end event", "");
				endEvent = true;
			}
			String[] time = messageContent.trim().split(":");
			String hour = time[0];
			String[] time2 = time[1].split("");
			String min = time2[0] + time2[1];
			Time t = new Time(hour, min);
			if (time2[3].equalsIgnoreCase("a")) {
				t.setIsPm(false);
			} else if (time2[3].equalsIgnoreCase("p")) {
				t.setIsPm(true);
			}
			String timezone = time[1].split(" ")[1].trim();
			t.setTimeZone(timezone);
			if (endEvent == false) {
				eventList.get(indexOfEvent).setTime(t);
			} else if (endEvent == true) {
				eventList.get(indexOfEvent).setEndTime(t);
			}
			discord.getChannel()
					.sendMessage("Time changed to " + eventList.get(indexOfEvent).getTime().getTimeAsString() + " "
							+ eventList.get(indexOfEvent).getTime().getTimeZone());
			setTime = false;
		} else if (setDate && !discord.getMessage().getAuthor().isBotUser()) {
			eventList.get(indexOfEvent).setDate(messageContent.trim());
			discord.getChannel().sendMessage("Date changed to " + eventList.get(indexOfEvent).getDate());
			setDate = false;
		} else if (setPeople && !discord.getMessage().getAuthor().isBotUser()) {
			String[] split = null;
			if (messageContent.contains(",")) {
				split = messageContent.trim().split(",");
			} else {
				split = new String[1];
				split[0] = messageContent.trim();
			}
			for (int i = 0; i < split.length; i++) {
				String[] msg = split[i].trim().split(" ");
				if (msg[1].contains("add")) {
					System.out.println("adding");
					int indexOfClosest = -1;
					String name = msg[0];
					LevenshteinDistance distance = new LevenshteinDistance();
					System.out.println("LevenshteinDistance made");
					int closest = Integer.MAX_VALUE;
					System.out.println("User size " + users.size());
					for (int o = 0; o < users.size(); o++) {
						System.out.println("checking distance");
						int howFar = distance.calculate(name, users.get(o).getUsername());
						if (howFar < closest) {
							System.out.println("new distance found");
							closest = howFar;
							indexOfClosest = o;
						}
					}
					if (eventList.get(indexOfEvent).getPeople().contains(users.get(indexOfClosest))) {
						discord.getChannel().sendMessage(users.get(indexOfClosest).getNickname() + "("
								+ users.get(indexOfClosest).getUsername() + ") is already in the event");
					} else {
						eventList.get(indexOfEvent).addPeople(users.get(indexOfClosest));
						discord.getChannel().sendMessage(users.get(indexOfClosest).getNickname() + "("
								+ users.get(indexOfClosest).getUsername() + ") was added to the event");
					}
				} else if (msg[1].contains("remove")) {
					int index = Integer.parseInt(msg[0].trim()) - (1 + i);

					discord.getChannel()
							.sendMessage(eventList.get(indexOfEvent).getPeople().get(index).getNickname() + "("
									+ eventList.get(indexOfEvent).getPeople().get(index).getUsername()
									+ ") was removed from the event");
					eventList.get(indexOfEvent).removePeople(index);
				} else {
					discord.getChannel().sendMessage("Message must contain a number/name then add/remove");
					discord.addReactionsToMessage("❌");
				}

			}
			setPeople = false;
		}

		System.out.println(areWeRemoving);
		// SCHEDULE
		// SCHEDULE
		// SCHEDULE
		// SCHEDULE
		if (messageContent.toLowerCase().startsWith(schedule)) {
			String listOfEvents = "";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += eventList.get(i).getName() + " " + eventList.get(i).getTime().getTimeAsString() + " "
						+ eventList.get(i).getDate() + "\n";
			}

			discord.getChannel().sendMessage(listOfEvents);
		}

//		START EVENT
//		START EVENT
//		START EVENT
//		START EVENT
		if (messageContent.toLowerCase().startsWith(start)) {
			try {
				System.out.println("start event called");

				Double[] closestDay = { Double.MAX_VALUE, -1.0 };
				for (int i = 0; i < eventList.size(); i++) {
					String[] tempDate = eventList.get(i).getDate().split("/");
					double hour = Integer.parseInt(eventList.get(i).getTime().getHour());
					hour -= timeZones.usTimezoneMap.get(eventList.get(i).getTime().getTimeZone());
					int min = Integer.parseInt(eventList.get(i).getTime().getMin());
					double dateAsInt = Double.parseDouble(tempDate[2] + tempDate[0] + tempDate[1]) + hour + min;
					if (dateAsInt < closestDay[0]) {
						closestDay[0] = dateAsInt;
						closestDay[1] = i + 0.0;
						closestEvents.clear();
						closestEvents.add(eventList.get(i));
					} else if (dateAsInt == closestDay[0]) {
						closestEvents.add(eventList.get(i));
					}
				}
				if (closestEvents.size() > 1) {
					String msg = "";
					int i = 0;
					for (Event event : closestEvents) {
						msg += i + ": " + event.getName() + " " + event.getTime().getTimeAsString() + " "
								+ event.getTime().getTimeZone() + " " + event.getDate() + "\n";
						i++;
					}
					discord.getChannel().sendMessage("There are " + closestEvents.size()
							+ " events, enter the number next to the event to start it \n" + msg);
					areWeSelectingStartEvent = true;
				} else {
					closestDayAsInt = Integer
							.parseInt((closestDay[1] + "").substring(0, (closestDay[1] + "").indexOf('.')));
					discord.getChannel()
							.sendMessage("Closest event is " + eventList.get(closestDayAsInt).getName() + " "
									+ eventList.get(closestDayAsInt).getTime().getTimeAsString() + " "
									+ eventList.get(closestDayAsInt).getTime().getTimeZone() + " "
									+ eventList.get(closestDayAsInt).getDate() + "\nEnter \'start\' to start it");
					areWeSelectingStartEvent = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (areWeSelectingStartEvent) {
			if (messageContent.toLowerCase().contains("start") && !discord.getMessage().getAuthor().isBotUser()) {
				startEvent(eventList.get(closestDayAsInt), discord);
			} else {
				int index = Integer.parseInt(messageContent.trim());
				for (int i = 0; i < eventList.size(); i++) {
					if (eventList.get(i).equals(closestEvents.get(index))) {
						startEvent(eventList.get(i), discord);
						break;
					}
				}
			}
			areWeSelectingStartEvent = false;
		}
		// TAG
		String listOfPeople = "err";
		if (messageContent.toLowerCase().startsWith(editTags) || nextTags) {
			nextTags = false;
			areTagsBeingSet = false;
			String role = null;

			if (messageContent.contains(" ")) {
				role = messageContent.substring(6, messageContent.length()).trim();

			}
			if (messageContent.contains(" ") && !nextTags) {

			} else {
				role = "everyone";
			}
			if (discord.getMessage().getAuthor().isBotUser()) {
				role = lastRoleUsed;
			}
			System.out.println("role string created");
			System.out.println("!tags called");
			long serverId = discord.getMessage().getServer().get().getId();
			listOfPeople = "Tags are used to categorize people for easy event management \n add an \'&\' then the tag at the end of the message when creating an event \n Everyone with that tag will be added to that event and pinged when it starts \n\n"
					+ "Enter the number next to the user, say \"end tags\" to stop editing tags\n";
			if (users.size() <= 0) {
				api.getServerById(serverId).ifPresent(server -> {
					System.out.println(server.getMemberCount());
					for (int i = 0; i < server.getMemberCount(); i++) {
						System.out.println(server.getMembers().toArray()[i]);
						String userInfo = server.getMembers().toArray()[i].toString();
						String userId = userInfo.substring(userInfo.indexOf("id") + 4, userInfo.indexOf(','));
						Person p = null;
						try {
							p = new Person(api.getUserById(userId).get());
							p.setNickname(api.getUserById(userId).get().getNickname(server) + "");
							p.setUsername(api.getUserById(userId).get().getName() + "");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						users.add(p);
					}

				});
				System.out.println("users in list");
			}
			Server server = api.getServerById(serverId).get();
			System.out.println("user list size " + users.size());
			int indexOfClosest = -1;
			if (role != null) {
				LevenshteinDistance distance = new LevenshteinDistance();
				int closest = Integer.MAX_VALUE;
				roles = server.getRoles();
				for (int i = 0; i < roles.size(); i++) {
					System.out.println("checking distance");
					int howFar = distance.calculate(role, roles.get(i).getName());
					if (howFar < closest) {
						System.out.println("new distance found");
						closest = howFar;
						indexOfClosest = i;
					}
				}

				System.out.println("Org Role: " + role);
				System.out.println("indexofClosest: " + indexOfClosest);
				System.out.println("Closest Role name: " + roles.get(indexOfClosest).getName());
				lastRoleUsed = roles.get(indexOfClosest).getName();
				System.out.println("Found distance");
			}
			int numberForList = 0;
			for (int i = 0; i < users.size(); i++) {
				try {
					if (role != null) {
						roles = server.getRoles();
						users.get(i).setUser(api.getUserById(users.get(i).getUser().getId()).get());
						if (users.get(i).getUser().getRoles(server).contains(roles.get(indexOfClosest))) {
							users.get(i).setNickname(
									api.getUserById(users.get(i).getUser().getId()).get().getNickname(server) + "");
							users.get(i)
									.setUsername(api.getUserById(users.get(i).getUser().getId()).get().getName() + "");
							printedUsers.add(users.get(i));
							System.out.println("updated users with specific roles");
							listOfPeople += ((numberForList + 1) + ": " + users.get(i).getNickname() + " ("
									+ users.get(i).getUsername() + ")\n");
							numberForList++;
						}
					} else {
						continue;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			System.out.println("list of people string is made");

			System.out.println("List of people updated");
			System.out.println(listOfPeople);
			discord.getChannel().sendMessage(listOfPeople);
			areWePeopleing = true;
		} else if (areWePeopleing && !discord.getMessage().getAuthor().isBotUser()) {
			if (messageContent.equalsIgnoreCase("end tags")) {
				discord.getChannel().sendMessage("Stopped Modifying Tags");
				areWePeopleing = false;
				areTagsBeingSet = false;
				nextTags = false;
			}
			if (areTagsBeingSet && !discord.getMessage().getAuthor().isBotUser()) {
				int index = 0;
				for (int i = 0; i < users.size(); i++) {
					if (mostRecentUserName.equals(users.get(i).getUsername())) {
						index = i;
						break;
					}
				}
				if (messageContent.contains(",")) {

					System.out.println("Most recent user index is " + index);
					System.out.println("Person at " + users.get(index));
					String[] tags = messageContent.trim().split(", ");
					users.get(index).addTags(tags);
					String whatTagsAreSet = "Tags ";
					for (int i = 0; i < tags.length; i++) {
						whatTagsAreSet += tags[i] + ", ";
					}
					whatTagsAreSet = whatTagsAreSet.substring(0, whatTagsAreSet.lastIndexOf(','));
					areTagsBeingSet = false;
					discord.getChannel().sendMessage(whatTagsAreSet + " set to " + users.get(index).getNickname());
					nextTags = true;

				} else if (messageContent.toLowerCase().contains("no tag")) {
					discord.getChannel().sendMessage("No tags set");
					areTagsBeingSet = false;
					nextTags = true;
				} else {
					System.out.println("Most recent user index is " + index);
					System.out.println("Person at " + users.get(index));
					String[] tags = { messageContent.trim() };
					users.get(index).addTags(tags);
					System.out.println("Tags of user: " + users.get(index).getTags().get(0));
					areTagsBeingSet = false;
					discord.getChannel().sendMessage("Tag " + tags[0] + " set to " + users.get(index).getNickname());
					nextTags = true;
				}
			} else {
				int index = -1;
				String[] msg = discord.getMessageContent().split(" ");
				if (isANumber(msg[0].trim())) {
					System.out.println("No commas");
					index = Integer.parseInt(msg[0].trim());
					System.out.println("parse");
					// if (discord.getMessageContent().toLowerCase().contains("tags")) {
					String userStatus = printedUsers.get(index - 1).getNickname() + "("
							+ printedUsers.get(index - 1).getUsername() + ")\n";
					System.out.println("user status made");
					userStatus += "Current Tags: ";
					if (printedUsers.get(index - 1).getTags().size() == 0) {
						userStatus += "none";
					} else {
						for (int i = 0; i < printedUsers.get(index - 1).getTags().size(); i++) {
							userStatus += printedUsers.get(index - 1).getTags().get(i) + ", ";
							userStatus = userStatus.substring(0, userStatus.length() - 2);
						}
						System.out.println("for loop ran");
					}
					discord.getChannel().sendMessage(
							"enter a list of tags to add or just one (ex. gamer, all) say \"no tag\" to not add a tag\n"
									+ userStatus);

					areTagsBeingSet = true;
					mostRecentUserName = printedUsers.get(index - 1).getUsername();
					System.out.println("message sent ");

					// }
					System.out.println(printedUsers.size());
					// printedUsers.get(index - 1).setTags(tags);
					System.out.println("set tags");

					printedUsers = new ArrayList<Person>();
				}

				// update list
				for (int i = 0; i < printedUsers.size(); i++) {
					for (int o = 0; o < users.size(); o++) {
						if (printedUsers.get(i).getUser().getId() == users.get(o).getUser().getId()) {
							users.remove(o);
							users.add(printedUsers.get(i));
						}
					}
				}
			}
		}
		// TIMEZONE
		// TIMEZONE
		// TIMEZONE
		// TIMEZONE
		// TIMEZONE
		String listOfPeople2 = "err";
		if (messageContent.toLowerCase().startsWith(userTimeZone)
				|| messageContent.toLowerCase().startsWith("!timezone") || nextZones) {
			nextZones = false;
			areZonesBeingSet = false;
			String role = null;
			if (messageContent.contains(" ")) {
				role = messageContent.substring(6, messageContent.length()).trim();
			}
			if (messageContent.contains(" ") && !nextZones) {

			} else {
				role = "everyone";
			}
			if (discord.getMessage().getAuthor().isBotUser()) {
				role = lastRoleUsed2;
			}
			System.out.println("role string created");
			System.out.println("!timezones called");
			long serverId = discord.getMessage().getServer().get().getId();
			listOfPeople = "Enter the number next to the user to edit thier timezone say \"end timezone\" to stop editing timezones\n";
			if (users.size() <= 0) {
				api.getServerById(serverId).ifPresent(server -> {
					System.out.println(server.getMemberCount());
					for (int i = 0; i < server.getMemberCount(); i++) {
						System.out.println(server.getMembers().toArray()[i]);
						String userInfo = server.getMembers().toArray()[i].toString();
						String userId = userInfo.substring(userInfo.indexOf("id") + 4, userInfo.indexOf(','));
						Person p = null;
						try {
							p = new Person(api.getUserById(userId).get());
							p.setNickname(api.getUserById(userId).get().getNickname(server) + "");
							p.setUsername(api.getUserById(userId).get().getName() + "");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						users.add(p);
					}

				});
				System.out.println("users in list");
			}
			Server server = api.getServerById(serverId).get();
			System.out.println("user list size " + users.size());
			int indexOfClosest = -1;
			if (role != null) {
				LevenshteinDistance distance = new LevenshteinDistance();
				int closest = Integer.MAX_VALUE;
				roles = server.getRoles();
				for (int i = 0; i < roles.size(); i++) {
					System.out.println("checking distance");
					int howFar = distance.calculate(role, roles.get(i).getName());
					if (howFar < closest) {
						System.out.println("new distance found");
						closest = howFar;
						indexOfClosest = i;
					}
				}

				System.out.println("Org Role: " + role);
				System.out.println("indexofClosest: " + indexOfClosest);
				System.out.println("Closest Role name: " + roles.get(indexOfClosest).getName());
				lastRoleUsed2 = roles.get(indexOfClosest).getName();
				System.out.println("Found distance");
			}
			int numberForList = 0;
			for (int i = 0; i < users.size(); i++) {
				try {
					if (role != null) {
						roles = server.getRoles();
						users.get(i).setUser(api.getUserById(users.get(i).getUser().getId()).get());
						if (users.get(i).getUser().getRoles(server).contains(roles.get(indexOfClosest))) {
							users.get(i).setNickname(
									api.getUserById(users.get(i).getUser().getId()).get().getNickname(server) + "");
							users.get(i)
									.setUsername(api.getUserById(users.get(i).getUser().getId()).get().getName() + "");
							printedUsers2.add(users.get(i));
							System.out.println("updated users with specific roles");
							listOfPeople += ((numberForList + 1) + ": " + users.get(i).getNickname() + " ("
									+ users.get(i).getUsername() + ")\n");
							numberForList++;
						}
					} else {
						continue;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			System.out.println("list of people string is made");

			System.out.println("List of people updated");
			System.out.println(listOfPeople);
			discord.getChannel().sendMessage(listOfPeople);
			areWeTimezoneing = true;
		} else if (areWeTimezoneing && !discord.getMessage().getAuthor().isBotUser()) {
			if (messageContent.equalsIgnoreCase("end timezone")) {
				discord.getChannel().sendMessage("Stopped Modifying Timezones");
				areWeTimezoneing = false;
				areZonesBeingSet = false;
				nextZones = false;
			}
			if (areZonesBeingSet && !discord.getMessage().getAuthor().isBotUser()) {
				int index = 0;
				for (int i = 0; i < users.size(); i++) {
					if (mostRecentUserName2.equals(users.get(i).getUsername())) {
						index = i;
						break;
					}
				}
				if (messageContent.toLowerCase().contains("no timezone")) {
					discord.getChannel().sendMessage("No timezone set");
					areZonesBeingSet = false;
					nextZones = true;
				} else {
					System.out.println("Most recent user index is " + index);
					System.out.println("Person at " + users.get(index));
					String timezone = messageContent.trim();
					timezone = timezone.toUpperCase();
					for (int i = 0; i < timeZones.getTimezones().length; i++) {
						if (timeZones.getTimezones()[i].equals(timezone)) {
							users.get(index).setTimezone(timezone);
							break;
						}
					}
					if (!users.get(index).getTimezone().equals(timezone)) {
						discord.getChannel().sendMessage("Timezone not created in !settings or is invalid");
					}
					areZonesBeingSet = false;
					discord.getChannel().sendMessage(
							"Timezone " + users.get(index).getTimezone() + " set to " + users.get(index).getNickname());
					nextZones = true;
				}
			} else {
				int index = -1;
				String[] msg = discord.getMessageContent().split(" ");
				if (isANumber(msg[0].trim())) {
					System.out.println("No commas");
					index = Integer.parseInt(msg[0].trim());
					System.out.println("parse");
					String userStatus = printedUsers2.get(index - 1).getNickname() + "("
							+ printedUsers2.get(index - 1).getUsername() + ")\n";
					System.out.println("user status made");
					userStatus += "Current Timezone: " + printedUsers2.get(index - 1).getTimezone();

					discord.getChannel().sendMessage(
							"enter a timezone or say \"no timezone\" to not add a timezone\n" + userStatus);

					areZonesBeingSet = true;
					mostRecentUserName2 = printedUsers2.get(index - 1).getUsername();
					System.out.println("message sent ");

					System.out.println(printedUsers.size());

					printedUsers2 = new ArrayList<Person>();
				}

				// update list
				for (int i = 0; i < printedUsers2.size(); i++) {
					for (int o = 0; o < users.size(); o++) {
						if (printedUsers2.get(i).getUser().getId() == users.get(o).getUser().getId()) {
							users.remove(o);
							users.add(printedUsers2.get(i));
						}
					}
				}
			}
		}
//		SETTINGS
//		SETTINGS
//		SETTINGS
//		SETTINGS
		if (messageContent.toLowerCase().startsWith(settings)) {
			discord.getChannel().sendMessage(
					"Welcome to Settings here is the list of settings(for all commands say the command then a space then the value) \n"
							+ "!addTimeZone: Enter a timezone abbreviation then the time diiffrence from PT (ex. IST +12:30)\n"
							+ "!setReminder: Enter a number that will be the number of minutes before an event a reminder will go out\n"
							+ "addTimeZone\n" + "addTimeZone\n" + "addTimeZone\n" + "");
		} else if (messageContent.toLowerCase().startsWith("!addtimezone")) {
			System.out.println("add time zone called");
			String[] time = messageContent.split(" ");
			System.out.println("split");
			if (time[2].contains("+")) {
				time[2] = time[2].substring(1).trim();
				System.out.println("+ removed and trimed");
			}
			if (time[2].contains(":30")) {
				time[2] = time[2].replace(":30", ".5");
			}
			if (time[2].contains(":45")) {
				time[2] = time[2].replace(":45", ".75");
			}
			System.out.println("time at 1 " + time[1] + ", time at 2 " + time[2]);
			timeZones.addTimezone(time[1], Double.parseDouble(time[2]));
			System.out.println("timezone added");
		} else if (messageContent.toLowerCase().startsWith("!setreminder")) {
			try {
				System.out.println("set reminder called");
				String[] time = messageContent.split(" ");
				System.out.println("split");
				int reminderTime = Integer.parseInt(time[1].trim());
				howManyMinBeforeEventShouldReminderBeSent = reminderTime;
			} catch (Exception e) {
				discord.getChannel().sendMessage("Make sure you are entering a number");
			}
		}

		if (messageContent.toLowerCase().startsWith("!test")) {
			System.out.println("test");
			System.out.println(timeZones.getCurrentTime(true));
			long currentTime = Instant.parse(timeZones.getCurrentTimeForStamp()).getEpochSecond();
	        // Format for Discord timestamp
	        String discordTimestamp = "<t:" + currentTime + ":t>"; // 'f' is the format for date and time
	        discord.getChannel().sendMessage(discordTimestamp);
		}
		if (messageContent.toLowerCase().startsWith("!stop")) {
			discord.getChannel().sendMessage("bye");
			System.exit(0);
		}
		checkTime(discord);
	}

	void checkTime(MessageCreateEvent discord) {
		Date d = new Date();
		Date d2 = new Date();
		System.out.println("time checked");
		for (int i = 0; i < eventList.size(); i++) {
			Event e = eventList.get(i);
			d.setDate(Integer.parseInt(e.getDate().split("/")[0]));
			d.setDate(Integer.parseInt(e.getDate().split("/")[1]));
			d.setYear(Integer.parseInt(e.getDate().split("/")[2]));
			if(e.getTime().getIsPm() == true) {
				d.setHours(Integer.parseInt(e.getTime().getHour())+12);
			} else {
				d.setHours(Integer.parseInt(e.getTime().getHour()));
			}
			d.setMinutes(Integer.parseInt(e.getTime().getMin()));
			d2.setTime(Instant.now().getEpochSecond());
			if (eventList.get(i).getDate().equals(timeZones.getCurrentDate())) {
				String currentTime = timeZones.getCurrentTime(false).split(":")[0];
				String eventTime = eventList.get(i).getTime().get24Hour();
				if (currentTime.equals(eventTime) || Integer.parseInt(currentTime) > Integer.parseInt(eventTime)) {
					System.out.println("same hour");
					String currentTime2 = timeZones.getCurrentTime(false).split(":")[1];
					String eventTime2 = eventList.get(i).getTime().getMin();
					if (currentTime2.equals(eventTime2) || Integer.parseInt(currentTime2) > Integer.parseInt(eventTime2)) {
						System.out.println("same min");
						startEvent(eventList.get(i), discord);
					}
				}
			}
		}
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				checkTime(discord);
			}
		};
		timer.schedule(task, 1000*30);
	}

	void startEvent(Event event, MessageCreateEvent discord) {
		if (event.isLive() == false) {
		event.setLive(true);
		discord.getChannel().sendMessage("Event **" + event.getName() + "** started");
		String people = "";
		for (int i = 0; i < event.getPeople().size(); i++) {
			people += event.getPeople().get(i).getUser().getMentionTag() + "\n";
		}
		if (people.equals("")) {
			people = "none";
		}
		discord.getChannel().sendMessage("People Participating: \n" + people);
		}
	}

	public static boolean isANumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void sendDm(String userId, String message, MessageCreateEvent discord) {
		discord.getApi().getUserById(userId).thenAccept(user -> user.sendMessage(message));
	}

	public void setup(MessageCreateEvent discord) {
		long serverId = discord.getMessage().getServer().get().getId();
		if (users.size() <= 0) {
			api.getServerById(serverId).ifPresent(server -> {
				System.out.println(server.getMemberCount());
				for (int i = 0; i < server.getMemberCount(); i++) {
					System.out.println(server.getMembers().toArray()[i]);
					String userInfo = server.getMembers().toArray()[i].toString();
					String userId = userInfo.substring(userInfo.indexOf("id") + 4, userInfo.indexOf(','));
					Person p = null;
					try {
						p = new Person(api.getUserById(userId).get());
						p.setNickname(api.getUserById(userId).get().getNickname(server) + "");
						p.setUsername(api.getUserById(userId).get().getName() + "");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					users.add(p);
				}

			});
			System.out.println("users in list");
		}
	}

}
