package org.jointheleague.features.example.covid_tracker;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.covid_tracker.Actuals;
import org.jointheleague.features.covid_tracker.CovidTracker;
import org.jointheleague.features.covid_tracker.Example;
import org.jointheleague.features.examples.third_features.NewsApi;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CovidTrackerTest {

    private final String testChannelName = "test";
    private CovidTracker covidTracker;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;
    @Mock
    Mono<Example> examples;
    @Mock
  Actuals actual;
    @Mock
    WebClient webClientMock;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    WebClient.ResponseSpec responseSpecMock;

    @Mock
    Mono<ApiExampleWrapper> apiExampleWrapperMonoMock;

    @Mock
    private TextChannel textChannel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        covidTracker = new CovidTracker(testChannelName);
        covidTracker.setWebClient(webClientMock);
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
        String command = covidTracker.COMMAND;

        //Then

        if(false/*!(covidTracker instanceof FeatureTemplate)*/){
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
        when(messageCreateEvent.getMessageContent()).thenReturn(covidTracker.COMMAND+" cases CA");
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        when(webClientMock.get())
        .thenReturn(requestHeadersUriSpecMock);
when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any()))
        .thenReturn(requestHeadersSpecMock);
when(requestHeadersSpecMock.retrieve())
        .thenReturn(responseSpecMock);
when(responseSpecMock.bodyToMono(Example.class))
        .thenReturn(examples);
//CONTINUE FROM HERE
when(examples.block().getActuals())
        .thenReturn(actual);

        //When
     


        covidTracker.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);
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
               //When
        covidTracker.handle(messageCreateEvent);
        
        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = covidTracker.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = covidTracker.getHelpEmbed().getTitle();
        String command = covidTracker.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}