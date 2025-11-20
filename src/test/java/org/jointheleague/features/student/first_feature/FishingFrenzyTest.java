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
import static org.mockito.Mockito.never;

public class FishingFrenzyTest {
    private final String testChannelName = "general";
    private final FishingFrenzy fishingFrenzy = new FishingFrenzy(testChannelName);

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
        String command = fishingFrenzy.COMMAND;

        //Then

  

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertNotEquals("!command", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommandAndHelp() {
        //Given
        when(receivedMessage.getMessageContent()).thenReturn(fishingFrenzy.COMMAND + " help");

        //When
        fishingFrenzy.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("Commands:\n`!fishingFrenzy cast` to cast your line.\n`!fishingFrenzy balance` to see how much coins you have. ");
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(receivedMessage.getMessageContent()).thenReturn(command);

        //When
        fishingFrenzy.handle(receivedMessage);

        //Then
        verify(receivedMessage, never()).sendResponse("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = fishingFrenzy.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = fishingFrenzy.getHelpEmbed().getTitle();
        String command = fishingFrenzy.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    @Test
    void itShouldGiveFishDrops() {
        //Given
    	fishingFrenzy.fishdrops(receivedMessage, 0);
    	verify(receivedMessage, times(1)).sendResponse("You caught a Dragonfish! (Legendary: 1%)\nYou gained 750 coins!");
    	fishingFrenzy.fishdrops(receivedMessage, 1);
    	verify(receivedMessage, times(1)).sendResponse("You caught a Golden Koi! (Mythic: 4%)\nYou gained 300 coins!");
    	fishingFrenzy.fishdrops(receivedMessage, 10);
    	verify(receivedMessage, times(1)).sendResponse("You caught a Swordfish! (Epic: 10%)\nYou gained 150 coins!");
    	fishingFrenzy.fishdrops(receivedMessage, 20);
    	verify(receivedMessage, times(1)).sendResponse("You caught a Salmon! (Rare: 15%)\nYou gained 75 coins!");
    	fishingFrenzy.fishdrops(receivedMessage, 50);
    	verify(receivedMessage, times(1)).sendResponse("You caught a Mackeral! (Uncommon: 25%)\nYou gained 30 coins!");
    	fishingFrenzy.fishdrops(receivedMessage, 60);
    	verify(receivedMessage, times(1)).sendResponse("You caught a Carp! (Common: 45%)\nYou gained 10 coins!");
    	
    }
    @Test
    void itShouldFish() {
        //Given
        when(receivedMessage.getMessageContent()).thenReturn(fishingFrenzy.COMMAND + " fish");

        //When
        fishingFrenzy.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse(anyString());
    }
    @Test
    void itShouldBalance() {
        //Given
        when(receivedMessage.getMessageContent()).thenReturn(fishingFrenzy.COMMAND + " balance");

        //When
        fishingFrenzy.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("You have " + fishingFrenzy.coins + " coins.");
    }
}
