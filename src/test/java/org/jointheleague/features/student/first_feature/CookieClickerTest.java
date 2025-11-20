package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CookieClickerTest {
    private final String testChannelName = "test";
    private final CookieClicker featureOne = new CookieClicker(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private ReceivedMessage receivedMessage;

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
        String command = featureOne.COMMAND;

        

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertNotEquals("!command", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(featureOne.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND);

        //When
        featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("Please type something after the command.");
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(receivedMessage.getMessageContent()).thenReturn(command);

        //When
        featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, never()).sendResponse("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = featureOne.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = featureOne.getHelpEmbed().getTitle();
        String command = featureOne.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    @Test
    void itShouldHelp() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " help");
        //When
    	featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("Commands:\n`!cookieCliker click` to bake a cookie.\n`!cookieClicker cookies` to see how much cookies you have.\n`!cookieClicker upgrades` to buy upgrades.");

    }
    @Test
    void itShouldClick() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " click");
        //When
    	
    	featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("You gained " + featureOne.click + " cookie");

    }
    @Test
    void itShouldShowCookies() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " cookies");
        //When
    	featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("You have " + featureOne.cookies + " cookies in total.");

    }
    @Test
    void itShouldGiveUpgrades() {
        //Given
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " upgrades");
        //When
    	featureOne.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("Upgrades: Increases cookies per click. To buy, just type `!cookieClicker [upgrade name]`\nCursor (+1): 10 cookies\nGramma (+5): 50 cookies\nFarm (+10): 100 cookies\nMine (+50): 300 cookies");
    }
    @Test
    void itShouldBuyCursor() {
        //Given
    	int clicks = featureOne.click;
    	int cookies = featureOne.cookies;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " cursor");
        //When
    	featureOne.handle(receivedMessage);

        //Then
    	
        assertEquals(clicks+1, featureOne.click);
        assertEquals(cookies-10, featureOne.cookies);
    }
    @Test
    void itShouldBuyGramma() {
        //Given
    	int clicks = featureOne.click;
    	int cookies = featureOne.cookies;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " gramma");
        //When
    	featureOne.handle(receivedMessage);

        //Then
    	
        assertEquals(clicks+5, featureOne.click);
        assertEquals(cookies-50, featureOne.cookies);
    }
    @Test
    void itShouldBuyFarm() {
        //Given
    	int clicks = featureOne.click;
    	int cookies = featureOne.cookies;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " farm");
        //When
    	featureOne.handle(receivedMessage);

        //Then
    	
        assertEquals(clicks+10, featureOne.click);
        assertEquals(cookies-100, featureOne.cookies);
    }
    @Test
    void itShouldBuyMine() {
        //Given
    	int clicks = featureOne.click;
    	int cookies = featureOne.cookies;
    	when(receivedMessage.getMessageContent()).thenReturn(featureOne.COMMAND + " mine");
        //When
    	featureOne.handle(receivedMessage);

        //Then
    	
        assertEquals(clicks+50, featureOne.click);
        assertEquals(cookies-300, featureOne.cookies);
    }
}
