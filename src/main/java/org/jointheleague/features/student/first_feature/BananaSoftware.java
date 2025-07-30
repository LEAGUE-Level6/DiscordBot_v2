package org.jointheleague.features.student.first_feature;

import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;
import java.util.Scanner;

public class BananaSoftware extends FeatureTemplate {
	
    public BananaSoftware(String channelName) {
		super(channelName);
	}
    
	public final String COMMAND = "banana";
    public final String password = "ON";
    
    public boolean BananaMode = false;
    
    static public Random r = new Random();
    
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        
        
        if(messageContent.toLowerCase().startsWith(COMMAND) && messageContent.toUpperCase().contains(password)) {
        	if(BananaMode) {
        		event.sendResponse("Message received! BananaMode is already on.");
        	}
        	else {
        		event.sendResponse("Message received! BananaMode is now on.");
        		BananaMode = true;
            	startupSequence(event);
        	}
        }
        
        else if (messageContent.toLowerCase().startsWith(COMMAND) && !BananaMode) {
        	event.sendResponse("Message received! BananaMode remains off.");
        }
        
        else if(messageContent.toLowerCase().startsWith(COMMAND) && BananaMode) {

        	//Scanner s = new Scanner(System.in);
    			
    			String t = messageContent.substring(7); // good
    			String[] things = t.trim().split(" ");
    			System.out.println("****");
    			for(String i : things) {
    				System.out.println(i);
    			}
    			System.out.println("****");
    			if(things.length <3 && !things[0].toUpperCase().equals("OFF")) event.sendResponse("So youre meant to *include* __both__ numbers in your argument");
    			
    			switch(things[0].toLowerCase()) {
    			
    			case "add":
    				String a; 
    				int b=0;
    				String c;
    				int d=0;
    				try {	
    					a = things[1];
    					b = Integer.parseInt(a);
    					c = things[2];
    					d = Integer.parseInt(c);
    					event.sendResponse(b+d+"");
    				}
    				catch(Exception e){
    					event.sendResponse("hey i dont think thats an integer");
    				}
   
            		event.sendResponse("\n \nWhich one would you like to execute? [Add/Subt/Mult/Divi/OFF] Always start with the Banana keyword");
        			event.sendResponse("Form: [Add/Subt/Mult/Divi/OFF] [Number 1] [Number 2]");
    				
    				break;
    				
    			case "subt":
    				String aa; 
    				int bb=0;
    				String cc;
    				int dd=0;
    				try {	
    					aa = things[1];
    					bb = Integer.parseInt(aa);
    					cc = things[2];
    					dd = Integer.parseInt(cc);
    					event.sendResponse(bb-dd+"");
    				}
    				catch(Exception e){
    					event.sendResponse("hey i dont think thats an integer");
    				}
   
            		event.sendResponse("\n \nWhich one would you like to execute? [Add/Subt/Mult/Divi/OFF] Always start with the Banana keyword");
        			event.sendResponse("Form: [Add/Subt/Mult/Divi/OFF] [Number 1] [Number 2]");
    				
    				break;
    				
    			case "mult":
    				String aaa; 
    				int bbb=0;
    				String ccc;
    				int ddd=0;
    				try {	
    					aaa = things[1];
    					bbb = Integer.parseInt(aaa);
    					ccc = things[2];
    					ddd = Integer.parseInt(ccc);
    					event.sendResponse(bbb*ddd+"");
    				}
    				catch(Exception e){
    					event.sendResponse("hey i dont think thats an integer");
    				}
   
            		event.sendResponse("\n \nWhich one would you like to execute? [Add/Subt/Mult/Divi/OFF] Always start with the Banana keyword");
        			event.sendResponse("Form: [Add/Subt/Mult/Divi/OFF] [Number 1] [Number 2]");
    				
    				break;
    				
    			case "divi":
    				
    				String aaaa; 
    				int bbbb=0;
    				String cccc;
    				int dddd=0;
    				try {	
    					aaaa = things[1];
    					bbbb = Integer.parseInt(aaaa);
    					cccc = things[2];
    					dddd = Integer.parseInt(cccc);
    					event.sendResponse(bbbb/dddd+"");
    				}
    				catch(ArithmeticException ae) {
    					event.sendResponse("> Dividing by 0 is bad because it breaks the fundamental rules of arithmetic: there's no number you can multiply by 0 to get a nonzero result, so division by 0 has no meaningful answer. For example, if you try to divide 5 by 0, you're asking \"what number times 0 equals 5?\"—but anything times 0 is 0, not 5. This creates a contradiction, leading to undefined or infinite results, which can cause errors or crashes in math, computers, and science. --ChatGPT");
    				}
    				catch(Exception e){
    					event.sendResponse("hey i dont think thats an integer");
    				}
   
            		event.sendResponse("\n \nWhich one would you like to execute? [Add/Subt/Mult/Divi/OFF] Always start with the Banana keyword");
        			event.sendResponse("Form: [Add/Subt/Mult/Divi/OFF] [Number 1] [Number 2]");
    				
    				break;
    			case "off":
    				BananaMode = false;
    				event.sendResponse("Got it. BananaMode is now off.");

    				break;
    			}
        	
    		}
    }
    
    public void startupSequence(ReceivedMessage event) {
    	
    		event.sendResponse("Loading JavaBanana Architecture... ");
    		
    		try {
    			Thread.sleep(r.nextInt(3500)+500);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [---           ] 20%");
    		
    		try {
    			Thread.sleep(r.nextInt(1500)+500);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [------        ] 40%");
    		
    		try {
    			Thread.sleep(r.nextInt(300)+100);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [--------      ] 60%");
    		
    		try {
    			Thread.sleep(300+r.nextInt(800));
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [-----------   ] 80%");
    		
    		try {
    			Thread.sleep(500+r.nextInt(3000));
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [--------------] 100%");
    		
    		try {
    			Thread.sleep(500+r.nextInt(2000));
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		event.sendResponse("-------- \n\n");
    		
    		try {
    			Thread.sleep(200);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("\n" + "JAVA BANANA SOFTWARE v1.0.0");
    		
    		event.sendResponse("\nThe JavaBanana Calculator has many operations to help you with math homework. ");
    		event.sendResponse("\n \nWhich one would you like to execute? [Add/Subt/Mult/Divi/OFF] Always start with the Banana keyword");
			event.sendResponse("Form: [Add/Subt/Mult/Divi/OFF] [Number 1] [Number 2]");
    }
    
}
