package org.jointheleague.features.example.crossy_road;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.AnagramGame.AnagramGame;
import org.jointheleague.features.CrossyRoad.CrossyRoad;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CrossyRoadTest {

    private final String testChannelName = "test";
    private final CrossyRoad road = new CrossyRoad(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final 	class myClass implements MessageAuthor{

		@Override
		public DiscordApi getApi() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Message getMessage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<String> getDiscriminator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Icon getAvatar() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isUser() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Optional<User> asUser() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isWebhook() {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean isYourself() {
			return false;
		}
		}
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
        String command = road.COMMAND;

        //Then

        if(!(false /*road instanceof FeatureTemplate*/)){
            assertNotEquals("!command", command);
        }

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
    	   String command = "!road start";
           when(messageCreateEvent.getMessageContent()).thenReturn(command);

           //When
           when(messageCreateEvent.getChannel()).thenReturn(textChannel);
           when(messageCreateEvent.getMessageAuthor()).thenReturn(new MessageAuthor(){

       		@Override
       		public DiscordApi getApi() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public long getId() {
       			// TODO Auto-generated method stub
       			return 0;
       		}

       		@Override
       		public String getName() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public Message getMessage() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public Optional<String> getDiscriminator() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public Icon getAvatar() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public boolean isUser() {
       			// TODO Auto-generated method stub
       			return false;
       		}

       		@Override
       		public Optional<User> asUser() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public boolean isWebhook() {
       			// TODO Auto-generated method stub
       			return false;
       		}
       		public boolean isYourself() {
       			return false;
       		}
       		});
           road.handle(messageCreateEvent);
          
           //Then
           verify(textChannel, times(1)).sendMessage(anyString());
    }
    @Test
    void itShouldHandleSetup() {
    	   String command = "setup";
           when(messageCreateEvent.getMessageContent()).thenReturn(command);

           //When
           when(messageCreateEvent.getChannel()).thenReturn(textChannel);
           when(messageCreateEvent.getMessageAuthor()).thenReturn(new MessageAuthor(){

       		@Override
       		public DiscordApi getApi() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public long getId() {
       			// TODO Auto-generated method stub
       			return 0;
       		}

       		@Override
       		public String getName() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public Message getMessage() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public Optional<String> getDiscriminator() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public Icon getAvatar() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public boolean isUser() {
       			// TODO Auto-generated method stub
       			return false;
       		}

       		@Override
       		public Optional<User> asUser() {
       			// TODO Auto-generated method stub
       			return null;
       		}

       		@Override
       		public boolean isWebhook() {
       			// TODO Auto-generated method stub
       			return false;
       		}
       		public boolean isYourself() {
       			return true;
       		}
       		});
           road.testCase = true;
           road.handle(messageCreateEvent);
          
           //Then
           verify(textChannel, times(1)).sendMessage(anyString());
    }
    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
    
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getMessageAuthor()).thenReturn(new myClass());
        //When
        road.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = road.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = road.getHelpEmbed().getTitle();
        String command = road.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}