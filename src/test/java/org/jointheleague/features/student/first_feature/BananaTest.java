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

public class BananaTest {

	private final String testChannelName = "test";
    private final BananaSoftware banana = new BananaSoftware(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    public final String COMMAND = "banana";
    public final String password = "ON";

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
    void itShouldConfirmBananasExist() {

    	//Given
        HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana");
        banana.BananaMode=false;
        
        //When
        banana.handle(receivedMessage);

        //Then
        verify(receivedMessage, times(1)).sendResponse("Message received! BananaMode remains off.");
    
    }
    
    @Test
    void itShouldTurnOnBananaMode() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana on");
        banana.BananaMode=false;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("Message received! BananaMode is now on.");
        assertEquals(banana.BananaMode,true);
    	
    }
    
    @Test
    void alreadyOn() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana on");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("Message received! BananaMode is already on.");
        assertEquals(banana.BananaMode,true);
    }
    
    @Test void off(){
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana OFF");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("Got it. BananaMode is now off.");
        assertEquals(banana.BananaMode,false);
    }
    
    @Test void notEnoughNumbers(){
    	
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana add 5");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("So youre meant to *include* __both__ numbers in your argument");
        assertEquals(banana.BananaMode,true);
    }
    
    @Test
    void adding() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana add 5 6");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse(5+6+"");
        assertEquals(banana.BananaMode,true);
    }
    
    @Test void addingButDecimals() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana add 5.2 6");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("hey i dont think thats an integer");
        assertEquals(banana.BananaMode,true);
    }
    
    @Test void subtracting() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana subt 5 6");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse(5-6+"");
        assertEquals(banana.BananaMode,true);
    }
  
    @Test void subtractingButDecimals() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana subt 5.2 6");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("hey i dont think thats an integer");
        assertEquals(banana.BananaMode,true);
    }
    @Test void multiplying() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana mult 5 5");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse(5*5+"");
        assertEquals(banana.BananaMode,true);
    }

    @Test void multiplyingButDecimals() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana mult 5.2 6.5");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("hey i dont think thats an integer");
        assertEquals(banana.BananaMode,true);  
    }
    
    @Test 
    void noBanana(){
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("bonoonoo add 5");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, never()).sendResponse(anyString());;
        assertEquals(banana.BananaMode,true);
    }
    
    @Test 
    void noBanana2(){
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana a");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        assertEquals(banana.BananaMode,true);
    }
    
    @Test void dividing() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana divi 4 2");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse(4/2+"");
        assertEquals(banana.BananaMode,true);
    }

    @Test void dividingButDecimals() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana divi 5.2 6");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("hey i dont think thats an integer");
        assertEquals(banana.BananaMode,true);
    }
    @Test void dividingButZero() {
    	HelpEmbed helpEmbed = new HelpEmbed(banana.COMMAND, "test");
        when(receivedMessage.getMessageContent()).thenReturn("banana divi 5 0");
        banana.BananaMode=true;
        
        banana.handle(receivedMessage);
        
        verify(receivedMessage, times(1)).sendResponse("bruh");
        assertEquals(banana.BananaMode,true);
    }
	
}
