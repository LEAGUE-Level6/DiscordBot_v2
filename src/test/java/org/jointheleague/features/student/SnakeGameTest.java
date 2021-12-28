package org.jointheleague.features.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.Timer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SnakeGameTest {
	private final String testChannelName = "test";
    private final SnakeGame snakeGame = Mockito.spy(new SnakeGame(testChannelName));

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;
    
    @Mock
    private Message receivedMessage;
    
    @Mock
    private Message sentMessage;
    
    @Mock
    private CompletableFuture<Message> completableMessageFuture;
    
    @Mock
    private CompletableFuture<List<User>> completableUserList;
    
    @Mock
    private CompletableFuture<List<User>> falseCompletableUserList;
    
    @Mock
    private List<User> userList;
    
    @Mock
    private List<User> falseUserList;
    
    @Mock
    private User user;
    
    @Mock
    private Reaction reaction;
    
    @Mock
    private Reaction falseReaction;
    
    @Mock
    private EmbedBuilder embed;
    
    @Mock
    private ActionEvent actionEvent;
    
    @Mock
    private Timer timer;
    
    @Mock
    private Timer fakeTimer;
    
    Optional<Reaction> optionalReaction;
    Optional<Reaction> falseOptionalReaction;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
        
        optionalReaction = Optional.of(reaction);
        falseOptionalReaction = Optional.of(falseReaction);
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
        String command = snakeGame.COMMAND;

        //Then

        if(!(snakeGame instanceof Feature)){
            assertNotEquals("!command", command);
        }

        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotNull(command);
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        snakeGame.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = snakeGame.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = snakeGame.getHelpEmbed().getTitle();
        String command = snakeGame.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }
    
    @Test
    void itShouldHandleSetUpGame() {
        Optional<User> optionalUser = Optional.of(user);
    	
        //Given
        try {
        	when(messageCreateEvent.getMessageContent()).thenReturn(snakeGame.COMMAND);
        	
            when(messageCreateEvent.getMessage()).thenReturn(receivedMessage);
            when(receivedMessage.getUserAuthor()).thenReturn(optionalUser);
            
            when(messageCreateEvent.getChannel()).thenReturn(textChannel);
            when(snakeGame.createEmbed()).thenReturn(embed);
            when(textChannel.sendMessage(embed)).thenReturn(completableMessageFuture);
            when(completableMessageFuture.get()).thenReturn(sentMessage);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

        //When
        snakeGame.handle(messageCreateEvent);

        //Then
        verify(snakeGame, times(1)).setup();
    }
    
    @Test
    void itShouldHandleInterruptedException() {
    	//Given
    	InterruptedException ie = Mockito.spy(new InterruptedException());
    	
        try {
        	when(messageCreateEvent.getMessageContent()).thenReturn(snakeGame.COMMAND);
        	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        	when(snakeGame.createEmbed()).thenReturn(embed);
        	when(textChannel.sendMessage(embed)).thenReturn(completableMessageFuture);
			when(completableMessageFuture.get()).thenThrow(ie);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        //When
        snakeGame.handle(messageCreateEvent);
        
        //Then
        verify(ie, times(1)).printStackTrace();
    }
    
    @Test
    void itShouldHandleExecutionException() {
    	//Given
    	ExecutionException ee = Mockito.spy(new ExecutionException(null));
    	
        try {
        	when(messageCreateEvent.getMessageContent()).thenReturn(snakeGame.COMMAND);
        	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
        	when(snakeGame.createEmbed()).thenReturn(embed);
        	when(textChannel.sendMessage(embed)).thenReturn(completableMessageFuture);
			when(completableMessageFuture.get()).thenThrow(ee);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        //When
        snakeGame.handle(messageCreateEvent);
        
        //Then
        verify(ee, times(1)).printStackTrace();
    }
    
    @Test
    void itShouldSayGameStarted() {
    	//Given
    	snakeGame.setGameStarted(true);
    	when(messageCreateEvent.getMessageContent()).thenReturn(snakeGame.COMMAND);
    	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
    	
    	//When
    	snakeGame.handle(messageCreateEvent);
    	
    	//Then
    	verify(textChannel, times(1)).sendMessage("Game Already Started!");
    }
    
    @Test
    void itShouldStopGame() {
    	String stopCommand = "!stop";
    	
    	//Given
    	snakeGame.setGameStarted(true);
    	when(messageCreateEvent.getMessageContent()).thenReturn(stopCommand);
    	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
    	
    	//When
    	snakeGame.setup();
    	snakeGame.handle(messageCreateEvent);
    	
    	//Then
    	verify(snakeGame, times(1)).stopGame();
    	verify(textChannel, times(1)).sendMessage("Game Stopped");
    }
    
    @Test
    void itShouldNotStopGame() {
    	String stopCommand = "!stop";
    	
    	//Given
    	snakeGame.setGameStarted(false);
    	when(messageCreateEvent.getMessageContent()).thenReturn(stopCommand);
    	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
    	
    	//When
    	snakeGame.setup();
    	snakeGame.handle(messageCreateEvent);
    	
    	//Then
    	verify(snakeGame, never()).stopGame();
    	verify(textChannel, never()).sendMessage("Game Stopped");
    }
    
    @Test
    void itShouldTurnSnakeHeadUpAndUpdateSnake() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
			when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
			
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
			when(completableUserList.get()).thenReturn(userList);
			when(userList.contains(user)).thenReturn(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, times(1)).removeReactionByEmoji(user, snakeGame.emojis[1]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(1, snakeGame.getDirection());
    	verify(snakeGame, times(1)).updateSnakePositionOnBoard();
    }
    
    @Test
    void itShouldTurnSnakeHeadDownAndUpdateSnake() {
    	//Given
    	//For some the get method for the optional reaction throws a NoSuchElementException even though there is a value
    	//This causes unit test to fail even though coverage shows the if statement goes through
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
			
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
			when(completableUserList.get()).thenReturn(userList);
			when(userList.contains(user)).thenReturn(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, times(1)).removeReactionByEmoji(user, snakeGame.emojis[2]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(3, snakeGame.getDirection());
    	verify(snakeGame, times(1)).updateSnakePositionOnBoard();
    }
    
    @Test
    void itShouldTurnSnakeHeadLeftAndUpdateSnake() {
    	//Given
    	//For some the get method for the optional reaction throws a NoSuchElementException even though there is a value
    	//This causes unit test to fail even though coverage shows the if statement goes through
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(4, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(4, 5));
    	testSegments.add(new SnakeSegment(4, 6));
    	
    	
    	try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
			when(completableUserList.get()).thenReturn(userList);
			when(userList.contains(user)).thenReturn(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(1);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, times(1)).removeReactionByEmoji(user, snakeGame.emojis[0]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(4, snakeGame.getDirection());
    	verify(snakeGame, times(1)).updateSnakePositionOnBoard();
    }
    
    @Test
    void itShouldTurnSnakeHeadRightAndUpdateSnake() {
    	//Given
    	//For some the get method for the optional reaction throws a NoSuchElementException even though there is a value
    	//This causes unit test to fail even though coverage shows the if statement goes through
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(4, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(4, 5));
    	testSegments.add(new SnakeSegment(4, 6));
    	
    	
    	try {
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
			when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
    		
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
			when(completableUserList.get()).thenReturn(userList);
			when(userList.contains(user)).thenReturn(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(1);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, times(1)).removeReactionByEmoji(user, snakeGame.emojis[3]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(2, snakeGame.getDirection());
    	verify(snakeGame, times(1)).updateSnakePositionOnBoard();
    }
    
    @Test
    void itShouldNotTurnSnakeHeadLeft() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(6, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(5, 4));
    	testSegments.add(new SnakeSegment(4, 4));
    	
    	
    	try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
			when(completableUserList.get()).thenReturn(userList);
			when(userList.contains(user)).thenReturn(true);

			when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
        	when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(2);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, never()).removeReactionByEmoji(user, snakeGame.emojis[0]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(2, snakeGame.getDirection());
    }
    
    @Test
    void itShouldNotTurnSnakeHeadRight() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(4, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(5, 4));
    	testSegments.add(new SnakeSegment(6, 4));
    	
    	
    	try {
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
    		when(completableUserList.get()).thenReturn(userList);
    		when(userList.contains(user)).thenReturn(true);

    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
        	when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
    		when(falseCompletableUserList.get()).thenReturn(falseUserList);
    		when(falseUserList.contains(user)).thenReturn(false);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	} catch (ExecutionException e) {
    		e.printStackTrace();
    	}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(4);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, never()).removeReactionByEmoji(user, snakeGame.emojis[3]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(4, snakeGame.getDirection());
    }
    
    @Test
    void itShouldNotTurnSnakeHeadUp() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(4, 6);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(4, 5));
    	testSegments.add(new SnakeSegment(4, 4));
    	
    	
    	try {
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
    		when(completableUserList.get()).thenReturn(userList);
    		when(userList.contains(user)).thenReturn(true);

    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
        	when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
    		when(falseCompletableUserList.get()).thenReturn(falseUserList);
    		when(falseUserList.contains(user)).thenReturn(false);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	} catch (ExecutionException e) {
    		e.printStackTrace();
    	}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(3);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, never()).removeReactionByEmoji(user, snakeGame.emojis[1]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(3, snakeGame.getDirection());
    }
    
    @Test
    void itShouldNotTurnSnakeHeadDown() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(4, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(4, 5));
    	testSegments.add(new SnakeSegment(4, 6));
    	
    	
    	try {
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
    		when(completableUserList.get()).thenReturn(userList);
    		when(userList.contains(user)).thenReturn(true);

    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
    		when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
        	when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
    		when(falseCompletableUserList.get()).thenReturn(falseUserList);
    		when(falseUserList.contains(user)).thenReturn(false);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	} catch (ExecutionException e) {
    		e.printStackTrace();
    	}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(1);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, never()).removeReactionByEmoji(user, snakeGame.emojis[2]);
    	verify(snakeGame, times(1)).changeHeadDirection();
    	assertEquals(1, snakeGame.getDirection());
    }
    
    @Test
    void shouldNotMoveSnakeHead() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, 4));
    	snakeGame.setHead(head);
    	snakeGame.setDirection(5);
    	
    	//When
    	snakeGame.moveHead();
    	
    	//Then
    	verify(head, never()).setXPos(anyInt());
    	verify(head, never()).setYPos(anyInt());
    }
    
    @Test
    void checkForPlayerReactShouldHandleInterruptedException() {
    	//Given
    	InterruptedException ie = Mockito.spy(new InterruptedException());
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(6, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(5, 4));
    	testSegments.add(new SnakeSegment(4, 4));
    	
    	
    	try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(optionalReaction);
        	when(reaction.getUsers()).thenReturn(completableUserList);
			when(completableUserList.get()).thenThrow(ie);

			when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
        	when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	snakeGame.setup();
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setPlayer(user);
    	snakeGame.setTimer(timer);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	snakeGame.setDirection(2);
    	
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(sentMessage, never()).removeReactionByEmoji(user, snakeGame.emojis[0]);
    	verify(snakeGame, times(1)).checkForPlayerReact(0);
    	assertEquals(2, snakeGame.getDirection());
    	verify(ie, times(1)).printStackTrace();
    }
    
    @Test
    void checkHeadPosShouldReturnFalseBecauseHeadTooFarRight() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(8, 4));
    	snakeGame.setHead(head);
    	
    	//When
    	boolean check = snakeGame.checkHeadPos();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void checkHeadPosShouldReturnFalseBecauseHeadTooFarLeft() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(-1, 4));
    	snakeGame.setHead(head);
    	
    	//When
    	boolean check = snakeGame.checkHeadPos();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void checkHeadPosShouldReturnFalseBecauseHeadTooFarUp() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, -1));
    	snakeGame.setHead(head);
    	
    	//When
    	boolean check = snakeGame.checkHeadPos();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void checkHeadPosShouldReturnFalseBecauseHeadTooFarDown() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, 8));
    	snakeGame.setHead(head);
    	
    	//When
    	boolean check = snakeGame.checkHeadPos();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void checkHeadPosShouldReturnFalseBecauseHeadCollidesWithBody() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, 4));
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(4, 4));
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	
    	//When
    	boolean check = snakeGame.checkHeadPos();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void checkIfEatFoodShouldReturnTrueIfFoodEaten() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, 4));
    	snakeGame.setHead(head);
    	snakeGame.setFoodX(4);
    	snakeGame.setFoodY(4);
    	
    	//When
    	boolean check = snakeGame.checkIfEatFood();
    	
    	//Then
    	assertEquals(check, true);
    }
    
    @Test
    void checkIfEatFoodShouldReturnFalseIfOnlyXValuesSame() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, 4));
    	snakeGame.setHead(head);
    	snakeGame.setFoodX(4);
    	snakeGame.setFoodY(5);
    	
    	//When
    	boolean check = snakeGame.checkIfEatFood();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void checkIfEatFoodShouldReturnFalseIfOnlyYValuesSame() {
    	//Given
    	SnakeSegment head = Mockito.spy(new SnakeSegment(4, 4));
    	snakeGame.setHead(head);
    	snakeGame.setFoodX(5);
    	snakeGame.setFoodY(4);
    	
    	//When
    	boolean check = snakeGame.checkIfEatFood();
    	
    	//Then
    	assertEquals(check, false);
    }
    
    @Test
    void ifCheckEatFoodReturnsTrueSnakeSegmentShouldBeAddedAndNewFoodSpawned() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = Mockito.spy(new ArrayList<SnakeSegment>());
    	SnakeSegment head = new SnakeSegment(6, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(5, 4));
    	testSegments.add(new SnakeSegment(4, 4));
    	snakeGame.setFoodX(6);
    	snakeGame.setFoodY(4);
    	snakeGame.setDirection(2);
    	snakeGame.setTimer(timer);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	
		try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
	    	when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
			when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(snakeGame, times(1)).checkIfEatFood();
    	//Four times b/c of the three adds that occur in the given section of this test
    	verify(testSegments, times(4)).add(any());
    	assertEquals(testSegments.get(testSegments.size() - 1).getXPos(), 4);
    	assertEquals(testSegments.get(testSegments.size() - 1).getYPos(), 4);
    	verify(snakeGame, times(1)).spawnFood();
    }
    
    @Test
    void ifCheckHeadPosReturnsFalseItShouldEndGame() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = Mockito.spy(new ArrayList<SnakeSegment>());
    	SnakeSegment head = new SnakeSegment(4, 4);
    	testSegments.add(head);
    	testSegments.add(new SnakeSegment(4, 4));
    	// Food positions set to -1 to ensure no interference from food
    	snakeGame.setFoodX(-1);
    	snakeGame.setFoodY(-1);
    	// Direction set to -1 so snake doesn't move
    	snakeGame.setDirection(-1);
    	snakeGame.setTimer(timer);
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	snakeGame.setHead(head);
    	snakeGame.setSnakeSegementsArray(testSegments);
    	
		try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
	    	when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
			when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(snakeGame, times(1)).stopGame();
    	verify(textChannel, times(1)).sendMessage("Ouch! Game Over!");
    	assertEquals(snakeGame.getGameboard()[4][4], ":red_square:");
    }
    
    @Test
    void ifSnakeMaxLengthThenPlayerShouldWinGame() {
    	//Given
    	when(actionEvent.getSource()).thenReturn(timer);
    	
    	ArrayList<SnakeSegment> testSegments = new ArrayList<SnakeSegment>();
    	SnakeSegment head = new SnakeSegment(1, 0);
    	snakeGame.setHead(head);
    	testSegments.add(head);
    	for(int xPos = 0; xPos < 8; xPos++) {
    		for(int yPos = 0; yPos < 8; yPos++) {
    			if(!((xPos == 0 || xPos == 1) && yPos == 0)) {
    				testSegments.add(new SnakeSegment(xPos, yPos));
    			}
    		}
    	}
    	snakeGame.setSnakeSegementsArray(testSegments);
    	// Food at position (1,0), snake eats it and gains max length of 64, then moves left
    	snakeGame.setFoodX(1);
    	snakeGame.setFoodY(0);
    	snakeGame.setDirection(4);
    	snakeGame.setTimer(timer);
    	snakeGame.setChannel(textChannel);
    	snakeGame.setGameMessage(sentMessage);
    	
		try {
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[0])).thenReturn(falseOptionalReaction);
	    	when(sentMessage.getReactionByEmoji(snakeGame.emojis[1])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[2])).thenReturn(falseOptionalReaction);
			when(sentMessage.getReactionByEmoji(snakeGame.emojis[3])).thenReturn(falseOptionalReaction);
			when(falseReaction.getUsers()).thenReturn(falseCompletableUserList);
			when(falseCompletableUserList.get()).thenReturn(falseUserList);
			when(falseUserList.contains(user)).thenReturn(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(snakeGame, times(1)).updateSnakePositionOnBoard();
    	verify(snakeGame, times(1)).stopGame();
    	verify(textChannel, times(1)).sendMessage("You... won. How?");
    }
    
    @Test
    void actionPerformedShouldNotDoAnythingIfSourceNotTimer() {
    	//Given
    	snakeGame.setTimer(timer);
    	when(actionEvent.getSource()).thenReturn(fakeTimer);
    	
    	//When
    	snakeGame.actionPerformed(actionEvent);
    	
    	//Then
    	verify(snakeGame, never()).checkIfEatFood();
    	verify(snakeGame, never()).changeHeadDirection();
    	verify(snakeGame, never()).moveHead();
    }
}
