package org.jointheleague.features.student.third_feature;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class FeatureThreeTest {
    private final String testChannelName = "test";
    private final FeatureThree featureThree = new FeatureThree(testChannelName);

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
        String command = featureThree.COMMAND;

        //Then

        if(!(featureThree instanceof FeatureThree)){
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
        HelpEmbed helpEmbed = new HelpEmbed(featureThree.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(featureThree.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        featureThree.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        featureThree.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage("");
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = featureThree.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = featureThree.getHelpEmbed().getTitle();
        String command = featureThree.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void itShouldApplyFishEffect(){
        featureThree.money = 125.00;
        featureThree.location = "The Coveted Coves";
        featureThree.luckModifier = 0.0;
        featureThree.fishTimes = 1;
        double PreMoney = featureThree.money;
        when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        featureThree.fishEffect("Luckfish", messageCreateEvent);
        featureThree.fishEffect("Boatfish", messageCreateEvent);
        featureThree.fishEffect("Rodfish", messageCreateEvent);
        featureThree.fishEffect("Snarefish", messageCreateEvent);
        assert(PreMoney > featureThree.money);
        assertEquals(1.25,featureThree.luckModifier, 0);
        assert(!featureThree.location.equals("The Coveted Coves"));
        assertEquals(2, featureThree.fishTimes);
    }

    @Test
    void itShouldFeedLocationalDataWhenUsingRandomFish(){
       //lootTableA[] expected
        //System.out.println("TEST");
        //System.out.println("TESTEST");
        featureThree.location = "Trench";
when(messageCreateEvent.getChannel()).thenReturn(textChannel);
//when(messageCreateEvent.getChannel().sendMessage("")).thenReturn(null);
        featureThree.randomFish(featureThree.location, messageCreateEvent);
        // lootTableA[] test = {};
//when(featureThree.lootAssigning()).thenReturn(test);
        lootTableA[] expected = {new lootTableA(25.65,35,"Telescopefish"),new lootTableA(20.2575,40,"Rhinochimaera"),new lootTableA(15.125,55,"Anglerfish"),new lootTableA(12.25751,60,"Barreleye"),new lootTableA(11.524575,70,"Lanternfish"),new lootTableA(10.2575,75,"Glowing Minnow"),new lootTableA(8.7525,90,"Flying Minnow"),new lootTableA(5.245,125,"Abyssal Grenadier"),new lootTableA(5.65,120,"Marine Hatchetfish"),new lootTableA(2.5,165,"Goblin Shark"),new lootTableA(1.15, 225,"Jellyfish"),new lootTableA(0.75, 345, "Six-Gilled Shark"),new lootTableA(0.000125,35000000,"Snailfish")};
        //System.out.println(expected);
       // System.out.println(featureThree.testList);
        assertNotEquals(featureThree.testList, null);
        //assertEquals(expected, featureThree.testList);
        //System.err.println(featureThree.testList[0]);
        //System.err.println(expected[0]);
       for(int i =0; i < featureThree.testList.length; i++) {
           assertTrue(expected[i].name().equals(featureThree.testList[i].name()));
           assertTrue(expected[i].getValue()==featureThree.testList[i].getValue());
           assertTrue(expected[i].getChance()==featureThree.testList[i].getChance());
       }
    }
    //All other features without a test written in this file have been tested manually and have an issue with the way there are coded that makes them unable to have an accurate test written without major rewrites.
}
