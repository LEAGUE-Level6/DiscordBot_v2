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
    public final String password = "calculate";
    
    public boolean BananaMode = false;
    
    static public Random r = new Random();
    
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        
        
        if(messageContent.toLowerCase().startsWith(COMMAND) && messageContent.toLowerCase().contains(password)) {
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
        	
        	event.sendResponse("Which one would you like to execute? [Add/Subt/Mult/Divi/Quit] Always start with the Banana keyword");
        	
        	Scanner s = new Scanner(System.in);

    			
    			event.sendResponse("Form: [Add/Subt/Mult/Divi/Quit] [Number 1] [Number 2]");
    			
    			String t = messageContent.substring(7); // good
    			String[] things = t.split(" ");
    			if(things.length != 3) event.sendResponse("So youre meant to *include* __both__ numbers in your argument");
    			
    			switch(things[0]) {
    			
    			case "Add":
    				String a; 
    				int b=0;
    				String c;
    				int d=0;
    				try {	
    					a = things[1];
    					b = Integer.parseInt(a);
    					c = things[2];
    					d = Integer.parseInt(c);
    				}
    				catch(Exception e){
    					event.sendResponse("hey i dont think thats an integer");
    				}
    				
    				event.sendResponse(b+d+"");
    				
    				break;
    				
    			/*case "Subt":
    				event.sendResponse("Enter first number:");
    				String z = s.nextLine();
    				int z_;
    				try {
    					z_ = Integer.parseInt(z);
    				}catch(Exception e){
    					event.sendResponse("Banana error!");
    					continue REDO;
    				}
    				event.sendResponse("Enter second number:");
    				String x = s.nextLine();
    				int x_;
    				try {
    					x_ = Integer.parseInt(x);
    				}catch(Exception e){
    					event.sendResponse("Banana error!");
    					continue REDO;
    				}
    				event.sendResponse(z_-x_+"");
    				break;
    				
    			case "Mult":
    				event.sendResponse("Enter first number:");
    				String GAHA = s.nextLine();
    				int GAH;
    				try {
    					GAH = Integer.parseInt(GAHA);
    				}catch(Exception e){
    					event.sendResponse("Banana error!");
    					continue REDO;
    				}
    				event.sendResponse("Enter second number:");
    				String DINGD = s.nextLine();
    				int DING;
    				try {
    					DING = Integer.parseInt(DINGD);
    				}catch(Exception e){
    					event.sendResponse("Banana error!");
    					continue REDO;
    				}
    				event.sendResponse(GAH*DING+"");
    				break;
    				
    			case "Divi":
    				
    				event.sendResponse("Enter first number:");
    				String aa = s.nextLine();
    				int bb;
    				try {
    					bb = Integer.parseInt(aa);
    				}catch(Exception e){
    					event.sendResponse("Banana error!");
    					continue REDO;
    				}
    				event.sendResponse("Enter second number:");
    				String cc = s.nextLine();
    				int dd;
    				try {
    					dd = Integer.parseInt(cc);
    				}catch(Exception e){
    					event.sendResponse("Banana error!");
    					continue REDO;
    				}
    				
    				
    				try {
    					event.sendResponse(bb/dd+"");
    				}catch(ArithmeticException ae) {
    					event.sendResponse("Banana error! Can you please not try and break fundamental principles??");
    				}
    				break;
    				*/
    			case "Quit":
    				BananaMode = false;
    				event.sendResponse("Got it. BananaMode is now off.");

    				
    			}
        	
    		}
    }
    
    public void startupSequence(ReceivedMessage event) {
    	final String banana =     "\n"
    			+ "   //\\\n"
    			+ "   V  \\\n"
    			+ "    \\  \\_\n"
    			+ "     \\,'.`-.\n"
    			+ "      |\\ `. `.       \n"
    			+ "      ( \\  `. `-.                        _,.-:\\\n"
    			+ "       \\ \\   `.  `-._             __..--' ,-';/\n"
    			+ "        \\ `.   `-.   `-..___..---'   _.-' ,'/\n"
    			+ "         `. `    `-._        __..--'    ,'\n"
    			+ "           `.       `--..''   _.-'    ,'\n"
    			+ "             `-._        _.-'      .-'\n"
    			+ "                 `\"\"\"---\"\"\"\n";
    	
    		event.sendResponse("Loading JavaBanana Architecture... ");
    		
    		try {
    			Thread.sleep(r.nextInt(3500)+500);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [|||          ] 20%");
    		
    		try {
    			Thread.sleep(r.nextInt(1500)+500);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [|||||        ] 40%");
    		
    		try {
    			Thread.sleep(r.nextInt(300)+100);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [|||||||      ] 60%");
    		
    		try {
    			Thread.sleep(300+r.nextInt(800));
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [|||||||||||  ] 80%");
    		
    		try {
    			Thread.sleep(500+r.nextInt(3000));
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		event.sendResponse("Loading... [|||||||||||||] 100%");
    		
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
    		event.sendResponse(banana);
    		
    		event.sendResponse("\n" + "JAVA BANANA SOFTWARE v1.0.0");
    		
    		event.sendResponse("\nThe JavaBanana Calculator has many operations to help you with math homework. Continue using the Banana keyword to continue.");
    }
    
}
