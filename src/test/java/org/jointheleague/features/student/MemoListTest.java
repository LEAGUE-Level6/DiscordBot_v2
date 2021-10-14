package org.jointheleague.features.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MemoListTest {
	private final String testChannelName = "test";
    private final MemoList MemoList = new MemoList(testChannelName);

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
        String command = MemoList.COMMAND1;

        //Then

        if(!(MemoList instanceof MemoList)){
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
        HelpEmbed helpEmbed = new HelpEmbed(MemoList.COMMAND1, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(MemoList.COMMAND1);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        MemoList.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        MemoList.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = MemoList.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = MemoList.getHelpEmbed().getTitle();
        String command = MemoList.COMMAND1;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    @Test
    void listShouldWork() {
    	//Given
    	String listIndex = "0";
    	String command = MemoList.COMMAND1 + " " + listIndex;
    	MemoList.list.add("test1");
    	MemoList.list.add("test2");
    	MemoList.list.add("test3");
    	MemoList.list.add("test4");
    	MemoList.list.add("test5");
    	when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
    	
    	//When
    	MemoList.handle(messageCreateEvent);
    	//Then
    	verify(textChannel, times(1)).sendMessage("0. test1\n"
    			+ "1. test2\n"
    			+ "2. test3\n"
    			+ "3. test4\n"
    			+ "4. test5\n");
    }
    
    @Test
    void addShouldConfirm() {
    	//Given
    	String command = MemoList.COMMAND2 + " Test";
    	when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
    	//When
    	MemoList.handle(messageCreateEvent);
    	//Then
    	verify(textChannel, times(1)).sendMessage("Added \"" +"Test"+"\" to the list.");
    }
    @Test
    void removeShouldConfirm() {
    	//Given
    	String command = MemoList.COMMAND3 + " 0";
    	when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
        MemoList.list.add("test1");
        MemoList.list.add("test2");
    	//When
        MemoList.handle(messageCreateEvent);
    	//Then
    	verify(textChannel, times(1)).sendMessage("Removed \"" +"test1"+"\" from the list.");
    }
    @Test
    void itShouldGiveErrorMessage() {
    	//Given
    	String command = MemoList.COMMAND1 + " Test";
    	when(messageCreateEvent.getMessageContent()).thenReturn(command);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
    	//When
        MemoList.handle(messageCreateEvent);
    	//Then
    	verify(textChannel, times(1)).sendMessage("Command not formatted correctly");
    }
    
}
