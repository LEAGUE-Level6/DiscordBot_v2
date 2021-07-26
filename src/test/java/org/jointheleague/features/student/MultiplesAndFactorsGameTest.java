package org.jointheleague.features.student;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import javax.swing.Timer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class MultiplesAndFactorsGameTest {
	
	private final String testChannelName = "test";
    private final MultiplesAndFactorsGame mfg = new MultiplesAndFactorsGame(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Spy
    private MultiplesAndFactorsGame mfgSpy = Mockito.spy(new MultiplesAndFactorsGame(testChannelName));

    @Mock
    private MessageCreateEvent messageCreateEvent;
    
    @Mock
    private MessageAuthor messageAuthor;

    @Mock
    private TextChannel textChannel;
    
    @Mock
    private Random rand;
    
    @Mock
    private Timer timer;

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
        String command = mfg.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!", command);
        assertEquals('!', command.charAt(0));
        assertNotEquals("!command", command);
        assertNotNull(command);
    }
    
    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = mfg.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = mfg.getHelpEmbed().getTitle();
        String command = mfg.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

    @Test
    void itShouldGenerateQuestion() {
    	//Given
    	mfg.setRandom(rand);
    	
    	//When
    	String question = mfg.generateQuestion();
    	
    	//Then
    	verify(rand, times(2)).nextInt(20);
    	assertEquals(question, "What is a number that is a multiple of " + mfg.getMultiple() + " and has a factor of " + mfg.getFactor() + "?");
    }
    
    @Test
	void itShouldStartGame() {
    	//Given
    	mfgSpy.setTextChannel(textChannel);
    	mfg.setRound(0);
    	
    	//When
    	mfgSpy.startGame();
    	
    	//Then
    	assertEquals(true, mfgSpy.getGameStarted());
    	assertEquals(30000, mfgSpy.getTimer().getDelay());
    	assertEquals(mfgSpy, mfgSpy.getTimer().getActionListeners()[0]);
    	assertEquals(1, mfgSpy.getRound());
    	assertEquals(true, mfgSpy.getTimer().isRunning());
    	verify(mfgSpy).generateQuestion();
    	verify(textChannel).sendMessage("Round 1: What is a number that is a multiple of " 
    												+ mfgSpy.getMultiple() 
    												+ " and has a factor of " 
    												+ mfgSpy.getFactor() 
    												+ "?");
    }
    
    @Test
    void roundIterationShouldMoveToNextRound() {
    	//Given
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.setTimer(timer);
    	mfgSpy.setRound(1);
    	
    	//When
    	mfgSpy.roundIteration();
    	
    	//Then
    	assertEquals(2, mfgSpy.getRound());
    	verify(mfgSpy).generateQuestion();
    	verify(textChannel).sendMessage("Round 2: What is a number that is a multiple of " 
				+ mfgSpy.getMultiple() 
				+ " and has a factor of " 
				+ mfgSpy.getFactor() 
				+ "?");
    	verify(timer).restart();
    }
    
    @Test
    void roundIterationShouldEndGame() {
    	//Given
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.setTimer(timer);
    	mfgSpy.setRound(5);
    	mfgSpy.setScore(5);
    	
    	//When
    	mfgSpy.roundIteration();
    	
    	//Then
    	//assertEquals(6, mfgSpy.getRound());
    	verify(textChannel).sendMessage("Game Over! Your score is: 5/5");
    	verify(mfgSpy).resetGameVariables();
    }
    
    @Test
    void checkAnswerShouldThrowIllegalArgumentExceptionWhenArgumentCannotBeParsedToInt() {
    	//Given
    	String input = "should throw Exception";
    	
    	//When
    	//Then
    	assertThrows(IllegalArgumentException.class, () -> mfg.checkAnswer(input));
    }
    
    @Test
    void checkAnswerShouldSendMessageAndReturnFalseIfAnswerIncorrect() {
    	//Given
    	mfg.setTextChannel(textChannel);
    	mfg.setFactor(5);
    	mfg.setMultiple(13);
    	
    	//When
    	
    	boolean result1 = mfg.checkAnswer("20");
    	boolean result2 = mfg.checkAnswer("26");
    	
    	//Then
    	assertEquals(false, result1);
    	assertEquals(false, result2);
    	verify(textChannel, times(2)).sendMessage("Your answer is incorrect. Suggested Answer: 65");
    }
    
    @Test
    void checkAnswerShouldIncrementScoreAndSendMessageAndReturnTrueIfAnswerCorrect() {
    	//Given
    	mfg.setTextChannel(textChannel);
    	mfg.setFactor(5);
    	mfg.setMultiple(13);
    	mfg.setScore(0);
    	
    	//When
    	boolean result = mfg.checkAnswer("65");
    	
    	//Then
    	verify(textChannel).sendMessage("Your answer is correct!");
    	assertEquals(1, mfg.getScore());
    	assertEquals(true, result);
    }
    
    @Test
    void itShouldStopGame() {
    	//Given
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.setTimer(timer);
    	
    	//When
    	mfgSpy.stopGame();
    	
    	//Then
    	verify(textChannel).sendMessage("Game Stopped");
    	verify(mfgSpy).resetGameVariables();
    }
    
    @Test
    void itShouldResetGameVariables() {
    	//Given
    	mfg.setTimer(timer);
    	mfg.setTextChannel(textChannel);
    	
    	//When
    	mfg.resetGameVariables();
    	
    	//Then
    	verify(timer).stop();
    	assertEquals(false, mfg.getGameStarted());
    	assertEquals(0, mfg.getRound());
    	assertEquals(0, mfg.getScore());
    	assertNull(mfg.getTimer());
    	assertNull(mfg.getTextChannel());
    }
    
    @Test 
    void itShouldNotHandleBotUsers() {
    	//Given
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(true);
    	
    	//When
    	mfg.handle(messageCreateEvent);
    	
    	//Then
    	verify(messageCreateEvent, never()).getMessageContent();
    }
    
    @Test
    void handleShouldNotStartGameWhenMessageHasNoCommand() {
    	//Given
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(false);
    	when(messageCreateEvent.getMessageContent()).thenReturn("DO NOT START");
    	mfgSpy.setGameStarted(false);
    	
    	//When
    	mfgSpy.handle(messageCreateEvent);
    	
    	//Then
    	verify(messageCreateEvent).getMessageContent();
    	verify(mfgSpy, never()).startGame();
    }
    
    @Test
    void handleShouldNotStartGameWhenGameAlreadyStarted() {
    	//Given
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(false);
    	when(messageCreateEvent.getMessageContent()).thenReturn(mfgSpy.COMMAND);
    	mfgSpy.setGameStarted(true);
    	mfgSpy.setRound(999);
    	
    	//When
    	mfgSpy.handle(messageCreateEvent);
    	
    	//Then
    	verify(messageCreateEvent).getMessageContent();
    	verify(mfgSpy, never()).startGame();
    }
    
    @Test
    void handleShouldStartGameWhenMessageHasCommandAndGameNotStarted() {
    	//Given
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(false);
    	when(messageCreateEvent.getMessageContent()).thenReturn(mfgSpy.COMMAND);
    	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
    	mfgSpy.setGameStarted(false);
    	
    	//When
    	mfgSpy.handle(messageCreateEvent);
    	
    	//Then
    	verify(messageCreateEvent).getMessageContent();
    	verify(messageCreateEvent).getChannel();
    	assertEquals(textChannel, mfgSpy.getTextChannel());
    	verify(mfgSpy).startGame();
    }
    
    @Test
    void handleShouldStopGameWhenMessagehasStopCommand() {
    	//Given
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(false);
    	when(messageCreateEvent.getMessageContent()).thenReturn("!stop");
    	when(messageCreateEvent.getChannel()).thenReturn(textChannel);
    	mfgSpy.setTimer(timer);
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.setGameStarted(true);
    	mfgSpy.setRound(3);
    	
    	//When
    	mfgSpy.handle(messageCreateEvent);
    	
    	//Then
    	verify(mfgSpy).stopGame();
    }
    
    @Test
    void handleShouldCatchIllegalArgumentExceptionWhenCheckAnswer() {
    	//Given
    	String message = "Exception";
    	
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(false);
    	when(messageCreateEvent.getMessageContent()).thenReturn(message);
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.startGame();
    	
    	//When
    	mfgSpy.handle(messageCreateEvent);
    	
    	//Then
    	verify(mfgSpy).checkAnswer(message);
    	verify(mfgSpy, never()).roundIteration();
    	verify(textChannel).sendMessage("That's not a valid number! Try again.");
    	
    }
    
    @Test
    void handleShouldDoRoundInteration() {
    	//Given
    	when(messageCreateEvent.getMessageAuthor()).thenReturn(messageAuthor);
    	when(messageAuthor.isBotUser()).thenReturn(false);
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.startGame();
    	
    	String message = Integer.toString(mfgSpy.getMultiple() * mfgSpy.getFactor());
    	when(messageCreateEvent.getMessageContent()).thenReturn(message);

    	//When
    	mfgSpy.handle(messageCreateEvent);
    	
    	//Then
    	verify(mfgSpy).checkAnswer(Integer.toString(mfgSpy.getMultiple() * mfgSpy.getFactor()));
    	verify(mfgSpy).roundIteration();
    }
    
    @Test
    void actionPerformedShouldTimeOutGameWhenTimerDelayFinishes() {
    	//Given
    	mfgSpy.setTextChannel(textChannel);
    	mfgSpy.startGame();
    	
    	//When
    	try {
			Thread.sleep(30000);
			
			//A little extra time
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Then
    	verify(textChannel).sendMessage("Game Timed Out: No response for 30 seconds.");
    	verify(mfgSpy).resetGameVariables();
    }
    
}
