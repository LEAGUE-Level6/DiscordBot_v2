package org.jointheleague.features.examples.third_features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
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
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class NewsApiTest {

    private final String testChannelName = "test";
    private NewsApi newsApi;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        newsApi = new NewsApi(testChannelName);
        newsApi.setWebClient(webClientMock);
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
        String command = newsApi.COMMAND;

        //Then

        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = newsApi.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = newsApi.getHelpEmbed().getTitle();
        String command = newsApi.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void givenMessageWithCommandAndNoTopic_whenHandle_thenSendErrorMessage() {
        //given
        when(messageCreateEvent.getMessageContent()).thenReturn(newsApi.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        //when
        newsApi.handle(messageCreateEvent);

        //then
        verify(webClientMock, never()).get();
        verify(textChannel, times(1)).sendMessage("Please put a topic after the command (e.g. " + newsApi.COMMAND + " cats)");
    }

    @Test
    void givenMessageWithCommandAndTopic_whenHandle_thenSendStoryMessage() {
        //given
        String topic = "Cows";
        String articleTitle = "Cows: Nature's Menace?";
        String articleContent = "No, they are not.";
        String articleUrl = "www.cowtruth.com";

        Article expectedArticle = new Article();
        expectedArticle.setTitle(articleTitle);
        expectedArticle.setContent(articleContent);
        expectedArticle.setUrl(articleUrl);
        List<Article> expectedArticles = Collections.singletonList(expectedArticle);

        ApiExampleWrapper expectedApiExampleWrapper = new ApiExampleWrapper();
        expectedApiExampleWrapper.setArticles(expectedArticles);

        when(messageCreateEvent.getMessageContent()).thenReturn(newsApi.COMMAND + topic);
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ApiExampleWrapper.class))
                .thenReturn(apiExampleWrapperMonoMock);
        when(apiExampleWrapperMonoMock.block())
                .thenReturn(expectedApiExampleWrapper);

        String expectedStory = articleTitle + " -\n"
                + articleContent
                + "\nFull article: " + articleUrl;

        //when
        newsApi.handle(messageCreateEvent);

        //then
        verify(webClientMock, times(1)).get();
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void givenMessageWithoutCommand_whenHandle_thenDoNothing() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        newsApi.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

}