package org.jointheleague.features.whale;

import java.io.BufferedReader;
import java.util.*;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
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

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

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
	public final String poll = "!pollevent";
	public final String[] available = { "!ican", "imavailable" };
	public final String userTimeZone = "!timezones";
	boolean areWeRemoving;
	boolean areWePeopleing;
	boolean areWeTimezoneing;
	boolean areTagsBeingSet;
	boolean areZonesBeingSet;
	boolean nextTags;
	boolean nextZones;
	int mostRecentUserIndex;
	int mostRecentUserIndex2;
	ArrayList<Event> eventList = new ArrayList<Event>();

	public Schedule(String channelName, ApiGetter get) {
		super(channelName);
		this.get = get;

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed("Schedule Bot",
				"!addEvent: Adds an event. Start with the event name, time, date, tag(optional but recomended(!tags)) \n\n"
						+ "!removeEvent: Removes an event\n\n"
						+ "!tags: edit the tags of users in an event (add a role name to only show users with that role)\n\n"
						+ "!timezones: edit users timezones so the bot knows what time for them the events will start (add a role name to only show users with that role)\n\n"
						+ "!schedule: lists all events \n\n" + "!startEvent: starts the nearest event early\n\n"
						+ "!endEvent: ends the current event \n\n"
						+ "!pollEvent: ask users if they can make it to the event with a message and reactions\n\n"
						+ "!iCan/!imAvailable: allows you to choose what event(s) you can make it to \n\n"
						+ "!examples: shows examples for every command \n\n"
						+ "!settings: configure the bot\n\n");

	}

	@Override
	public void handle(MessageCreateEvent discord) {
		api = get.getApi();
		String messageContent = discord.getMessageContent();
		if (messageContent.toLowerCase().startsWith("!examples") || messageContent.toLowerCase().startsWith("!example")) {
			String examples = "Examples: \n"
							+"a -> will mean a new messgae and (ex/ex) will mean 2 possible commands to send"
							+"!addEvent Valorant Grind 9:30pm pdt fri #val \n"
							+ "!removeEvent -> (1/3, 5)\n"
							+ "!tags spectator -> 1 tags -> spectator\n"
							+ "!timezones -> 2 timezones -> GMT\n"
							+ "!schedule \n"
							+ "!endEvent \n"
							+ "!pollEvent -> 2\n"
							+ "!iCan/!imAvailable -> (1,2/3)\n"
							+ "!examples \n"
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
					String tag = time.substring(time.indexOf('&'), time.length()-1);
					tag = tag.trim();
					for (int i = 0; i< users.size(); i++) {
						for (int o = 0; o < users.get(i).getTags().size(); o++) {
							if (tag.equalsIgnoreCase(users.get(i).getTags().get(o))) {
								try {
									usersForEvent.add(users.get(i).getUser().get());
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
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
							+ " |Date = |" + eventList.get(eventList.size() - 1).getDate() + "|");

			System.out.println("Message sent to channel");
			// event.getChannel().sendMessage("Sending a message to the channel");
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
			List<Event> closestEvents = new ArrayList<>();
			int[] closestDay = { Integer.MAX_VALUE, -1 };
			for (int i = 0; i < eventList.size(); i++) {
				String[] tempDate = eventList.get(i).getDate().split("/");
				int dateAsInt = Integer.parseInt(tempDate[2] + tempDate[0] + tempDate[1]
						+ (eventList.get(i).getTime().getHour()
								+ timeZones.usTimezoneMap.get(eventList.get(i).getTime().getTimeZone()))
						+ eventList.get(i).getTime().getMin());
				if (dateAsInt < closestDay[0]) {
					closestDay[0] = dateAsInt;
					closestDay[1] = i;
					closestEvents.clear(); // Clear previous closest events
					closestEvents.add(eventList.get(i));
				} else if (dateAsInt == closestDay[0]) {
					// If event falls on the same closest day, add it to the list
					closestEvents.add(eventList.get(i));
				}
			}
			System.out.println("Closest date is " + eventList.get(closestDay[1]).getDate() + " "
					+ eventList.get(closestDay[1]).getName());
			// If there are multiple events on the closest day, print them
			if (closestEvents.size() > 1) {
				System.out.println("Events on the closest day:");
				for (Event event : closestEvents) {
					System.out.println(event.getDate()); // or print event details as needed
				}
			}
		}
		// PEOPLE
		// PEOPLE
		// PEOPLE
		// PEOPLE
		String listOfPeople = "err";
		if (messageContent.toLowerCase().startsWith(editTags) || nextTags) {
			nextTags = false;
			areTagsBeingSet = false;
			String role = messageContent.substring(7, messageContent.length()).trim();
			if (messageContent.contains(" ") && !nextTags) {

			} else {
				role = null;
			}
			System.out.println("role string created");
			discord.getChannel().sendMessage("");
			System.out.println("!people called");
			long serverId = discord.getMessage().getServer().get().getId();
			listOfPeople = "Enter the number next to the user, then what you want to modify (ex. 1 tags) say \"end tags\" to stop editing tags\n";
			if (users.size() <= 0) {
				api.getServerById(serverId).ifPresent(server -> {
					System.out.println(server.getMemberCount());
					for (int i = 0; i < server.getMemberCount(); i++) {
						System.out.println(server.getMembers().toArray()[i]);
						String userInfo = server.getMembers().toArray()[i].toString();
						String userId = userInfo.substring(userInfo.indexOf("id") + 4, userInfo.indexOf(','));
						Person p = new Person(api.getUserById(userId));
						try {
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
				System.out.println("Found distance");
			}

			for (int i = 0; i < users.size(); i++) {
				try {
					if (role != null) {
						roles = server.getRoles();
						users.get(i).setUser(api.getUserById(users.get(i).getUser().get().getId()));
						if (users.get(i).getUser().get().getRoles(server).contains(roles.get(indexOfClosest))) {
							users.get(i).setNickname(
									api.getUserById(users.get(i).getUser().get().getId()).get().getNickname(server)
											+ "");
							users.get(i).setUsername(
									api.getUserById(users.get(i).getUser().get().getId()).get().getName() + "");
							printedUsers.add(users.get(i));
							System.out.println("updated users with specific roles");
						} else {
							users.get(i).setUser(api.getUserById(users.get(i).getUser().get().getId()));
							users.get(i).setNickname(
									api.getUserById(users.get(i).getUser().get().getId()).get().getNickname(server)
											+ "");
							users.get(i).setUsername(
									api.getUserById(users.get(i).getUser().get().getId()).get().getName() + "");
							printedUsers.add(users.get(i));
							System.out.println("updated all users");
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
				listOfPeople += ((i + 1) + ": " + users.get(i).getNickname() + " (" + users.get(i).getUsername()
						+ ")\n");

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
				if (messageContent.contains(",")) {
					if (mostRecentUserIndex == -1) {
						discord.getChannel().sendMessage("Somthing Went Wrong");
					} else {
						String[] tags = messageContent.trim().split(", ");
						users.get(mostRecentUserIndex).addTags(tags);
						String whatTagsAreSet = "Tags ";
						for (int i = 0; i < tags.length; i++) {
							whatTagsAreSet += tags[i] + ", ";
						}
						whatTagsAreSet = whatTagsAreSet.substring(0, whatTagsAreSet.lastIndexOf(','));
						areTagsBeingSet = false;
						discord.getChannel().sendMessage(
								whatTagsAreSet + " set to " + users.get(mostRecentUserIndex).getNickname());
						nextTags = true;
					}
				} else if (messageContent.toLowerCase().contains("no tag")) {
					discord.getChannel().sendMessage("No tags set");
					areTagsBeingSet = false;
					nextTags = true;
				} else {
					if (mostRecentUserIndex == -1) {
						discord.getChannel().sendMessage("Somthing Went Wrong");
					} else {
						String[] tags = { messageContent.trim() };
						users.get(mostRecentUserIndex).addTags(tags);
						System.out.println("Tags of user: " + users.get(mostRecentUserIndex).getTags().get(0));
						areTagsBeingSet = false;
						discord.getChannel().sendMessage(
								"Tag " + tags[0] + " set to " + users.get(mostRecentUserIndex).getNickname());
						nextTags = true;
					}
				}
			} else {
				int index = -1;
				String[] msg = discord.getMessageContent().split(" ");
				if (isANumber(msg[0].trim())) {
					System.out.println("No commas");
					index = Integer.parseInt(msg[0].trim());
					System.out.println("parse");
					if (discord.getMessageContent().toLowerCase().contains("tags")) {
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
						mostRecentUserIndex = index - 1;
						System.out.println("message sent ");
					} else {

					}
					System.out.println(printedUsers.size());
					// printedUsers.get(index - 1).setTags(tags);
					System.out.println("set tags");

					printedUsers = new ArrayList<Person>();
				}

				// update list
				for (int i = 0; i < printedUsers.size(); i++) {
					for (int o = 0; o < users.size(); o++) {
						try {
							if (printedUsers.get(i).getUser().get().getId() == users.get(o).getUser().get().getId()) {
								users.remove(o);
								users.add(printedUsers.get(i));
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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

		if (messageContent.toLowerCase().startsWith(userTimeZone) || messageContent.toLowerCase().startsWith("!timezone") || nextZones) {
			String listOfPeople2 = "err";
			nextZones = false;
			areZonesBeingSet = false;
			String role = messageContent.substring(7, messageContent.length()).trim();
			if (messageContent.contains(" ") && !nextZones) {

			} else {
				role = "everyone";
			}
			System.out.println("role string created");
			discord.getChannel().sendMessage("");
			System.out.println("!people called");
			long serverId = discord.getMessage().getServer().get().getId();
			listOfPeople = "Enter the number next to the user, then what you want to modify (ex. 1 timezone) say \"end timezone\" to stop editing timezones\n";
			if (users.size() <= 0) {
				api.getServerById(serverId).ifPresent(server -> {
					System.out.println(server.getMemberCount());
					for (int i = 0; i < server.getMemberCount(); i++) {
						System.out.println(server.getMembers().toArray()[i]);
						String userInfo = server.getMembers().toArray()[i].toString();
						String userId = userInfo.substring(userInfo.indexOf("id") + 4, userInfo.indexOf(','));
						Person p = new Person(api.getUserById(userId));
						try {
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
				System.out.println("Found distance");
			}

			for (int i = 0; i < users.size(); i++) {
				try {
					if (role != null) {
						roles = server.getRoles();
						users.get(i).setUser(api.getUserById(users.get(i).getUser().get().getId()));
						if (users.get(i).getUser().get().getRoles(server).contains(roles.get(indexOfClosest))) {
							users.get(i).setNickname(
									api.getUserById(users.get(i).getUser().get().getId()).get().getNickname(server)
											+ "");
							users.get(i).setUsername(
									api.getUserById(users.get(i).getUser().get().getId()).get().getName() + "");
							printedUsers2.add(users.get(i));
							System.out.println("updated users with specific roles");
						} else {
							users.get(i).setUser(api.getUserById(users.get(i).getUser().get().getId()));
							users.get(i).setNickname(
									api.getUserById(users.get(i).getUser().get().getId()).get().getNickname(server)
											+ "");
							users.get(i).setUsername(
									api.getUserById(users.get(i).getUser().get().getId()).get().getName() + "");
							printedUsers2.add(users.get(i));
							System.out.println("updated all users");
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
				listOfPeople += ((i + 1) + ": " + users.get(i).getNickname() + " (" + users.get(i).getUsername()
						+ ")\n");

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
				if (messageContent.toLowerCase().contains("no zone")) {
					discord.getChannel().sendMessage("No timezones set");
					areZonesBeingSet = false;
					nextZones = true;
				} else {
					if (mostRecentUserIndex2 == -1) {
						discord.getChannel().sendMessage("Somthing Went Wrong");
					} else {
						String timezone = messageContent.trim();
						timezone = timezone.toUpperCase();
						for (int i = 0; i < timeZones.getTimezones().length; i++) {
							if (timeZones.getTimezones()[i].equals(timezone)) {
								users.get(mostRecentUserIndex2).setTimezone(timezone);
								break;
							}
						}
						if (!users.get(mostRecentUserIndex2).getTimezone().equals(timezone)) {
							discord.getChannel().sendMessage("Timezone not added or is invalid");
						}
						
						System.out.println("Zone of user: " + users.get(mostRecentUserIndex2).getTimezone());
						areZonesBeingSet = false;
						discord.getChannel().sendMessage(
								"Timezone " + timezone + " set to " + users.get(mostRecentUserIndex).getNickname());
						nextZones = true;
					}
				}
			} else {
				int index = -1;
				String[] msg = discord.getMessageContent().split(" ");
				if (isANumber(msg[0].trim())) {
					System.out.println("No commas");
					index = Integer.parseInt(msg[0].trim());
					System.out.println("parse");

					if (discord.getMessageContent().toLowerCase().contains("timezone") || discord.getMessageContent().toLowerCase().contains("timezones")) {
						String userStatus = printedUsers2.get(index - 1).getNickname() + "("
								+ printedUsers2.get(index - 1).getUsername() + ")\n";
						System.out.println("user status made");
						userStatus += "Current Timezone: ";
						if (printedUsers2.get(index - 1).getTimezone().equals("none")) {
							userStatus += "none";
						} else {
							userStatus += printedUsers2.get(index - 1).getTimezone();
						}
						String allTimeZones = "List of all added timezones ";
						for (int i = 0; i < timeZones.getTimezones().length; i++) {
							allTimeZones += timeZones.getTimezones()[i] + ", ";
							
						}
						allTimeZones = allTimeZones.substring(0, allTimeZones.lastIndexOf(','));
						discord.getChannel().sendMessage(
								"enter an abbreviation for a timezone (the timezone must be added using !settings if it is not PT, MT, CT, ET) say \"no zone\" to not add a timezone\n"
										+ userStatus + "\n" + allTimeZones);

						areZonesBeingSet = true;
						mostRecentUserIndex2 = index - 1;
						System.out.println("message sent ");
					} else {

					}
					System.out.println(printedUsers2.size());
					// printedUsers.get(index - 1).setTags(tags);
					System.out.println("set zones");

					printedUsers2 = new ArrayList<Person>();
				}

				// update list
				for (int i = 0; i < printedUsers2.size(); i++) {
					for (int o = 0; o < users.size(); o++) {
						try {
							if (printedUsers2.get(i).getUser().get().getId() == users.get(o).getUser().get().getId()) {
								users.remove(o);
								users.add(printedUsers2.get(i));
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

		if (messageContent.toLowerCase().startsWith("!amrmode")) {
			AMRmode = true;
			discord.getChannel().sendMessage("AMR mode has be activated(you can disable it with !settings)");
			System.out.println("AMR mode on");
		}
//		SETTINGS
//		SETTINGS
//		SETTINGS
//		SETTINGS
		if (messageContent.toLowerCase().startsWith(settings)) {
			discord.getChannel().sendMessage("Welcome to Settings here is the list of settings \n"
					+ "addTimeZone: Enter a timezone abbreviation then the time diiffrence from PT (ex. IST +12:30)\n"
					+ "disableAMR\n" + "addTimeZone\n" + "addTimeZone\n" + "addTimeZone\n" + "addTimeZone\n" + "");
		} else if (messageContent.toLowerCase().startsWith("addtimezone")) {
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
		}

		if (messageContent.toLowerCase().startsWith("!test")) {

		}
		if (messageContent.toLowerCase().startsWith("!stop")) {
			discord.getChannel().sendMessage("bye");
			System.exit(0);
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

}
