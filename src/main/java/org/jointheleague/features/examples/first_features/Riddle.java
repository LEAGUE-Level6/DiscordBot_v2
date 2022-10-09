package org.jointheleague.features.examples.first_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Riddle extends Feature implements ActionListener {
  MessageCreateEvent answeredEvent;
    String a= "Which rule can be easily used in some cases to derive the instantaneous slope of a graph without finding the difference quotient?";
    String a1 = "Power Rule";
    String b= "What is the nearest synonym of 'sagacity'";
    String b1= "Wisdom";
    String c= "What is the molecular geometry of methane?";
    String c1= "Tetrahedral";
    String d= "The 1896 case that defined segregation to be constitutional was between Plessy and whom?";
    String d1 ="Ferguson";
    String e= "The reactants of a redox reaction include the reducing agent and the ________ agent";
    String e1= "oxidizing";
    String f = "The system used to operate air suspension and other compressed air models";
    String f1= "Pneumatics";
    String g = "What is the value of: sin^2(1)+sin^2(2)...sin^2(89)+sin^2(90)";
    String g1= "45.5";
    String h= "Which form is used to conjugate for habitual actions in Spanish?";
    String h1= "Imperfect preterite";
    String i= "What is the last step of cellular respiration?";
    String i1= "Electron Transport Chain";
    String j= "The name for the equation: PV=nRT";
    String j1= "Ideal Gas Law";
    public final String COMMAND1 = "!riddle1";
    public final String COMMAND1A = "!answer1";
    public final String COMMAND2 = "!riddle2";
    public final String COMMAND2A = "!answer2";
    public final String COMMAND3 = "!riddle3";
    public final String COMMAND3A = "!answer3";
    public final String COMMAND4 = "!riddle4";
    public final String COMMAND4A = "!answer4";
    public final String COMMAND5 = "!riddle5";
    public final String COMMAND5A = "!answer5";
    public final String COMMAND6 = "!riddle6";
    public final String COMMAND6A = "!answer6";
    public final String COMMAND7 = "!riddle7";
    public final String COMMAND7A = "!answer7";
    public final String COMMAND8 = "!riddle8";
    public final String COMMAND8A = "!answer8";
    public final String COMMAND9 = "!riddle9";
    public final String COMMAND9A = "!answer9";
    public final String COMMAND0 = "!riddle0";
    public final String COMMAND0A = "!answer0";
    public final String SCORE = "!score";
    int score=0;
    Timer qTimer = new Timer(6000, this);
    public Riddle(String channelName ) {
        super(channelName);
    }


    @Override
    public void handle(MessageCreateEvent event) throws APIException, InterruptedException {

     if(event.getMessageContent().contains(COMMAND1)) {
         event.getChannel().sendMessage(a);
     }
     else if(event.getMessageContent().contains(COMMAND1A)){
            question1(event);
        }
     else if(event.getMessageContent().contains(COMMAND2)) {
         event.getChannel().sendMessage(b);
     }
     else if(event.getMessageContent().contains(COMMAND2A)) {
         question2(event);
     }
     else if(event.getMessageContent().contains(COMMAND3)) {
         event.getChannel().sendMessage(c);
     }
     else if(event.getMessageContent().contains(COMMAND3A)) {
         question3(event);
     }
        else if(event.getMessageContent().contains(COMMAND4)) {
            event.getChannel().sendMessage(d);
        }
        else if(event.getMessageContent().contains(COMMAND4A)){
            question4(event);
        }
        else if(event.getMessageContent().contains(COMMAND5)) {
            event.getChannel().sendMessage(e);
        }
        else if(event.getMessageContent().contains(COMMAND5A)) {
            question5(event);
        }
        else if(event.getMessageContent().contains(COMMAND6)) {
            event.getChannel().sendMessage(f);
        }
        else if(event.getMessageContent().contains(COMMAND6A)) {
            question6(event);
        }
        else if(event.getMessageContent().contains(COMMAND7)) {
            event.getChannel().sendMessage(g);
        }
        else if(event.getMessageContent().contains(COMMAND7A)){
            question7(event);
        }
        else if(event.getMessageContent().contains(COMMAND8)) {
            event.getChannel().sendMessage(h);
        }
        else if(event.getMessageContent().contains(COMMAND8A)) {
            question8(event);
        }
        else if(event.getMessageContent().contains(COMMAND9)) {
            event.getChannel().sendMessage(i);
        }
        else if(event.getMessageContent().contains(COMMAND9A)) {
            question9(event);
        }
     else if(event.getMessageContent().contains(COMMAND0)) {
         event.getChannel().sendMessage(j);
     }
     else if(event.getMessageContent().contains(COMMAND0A)) {
         question0(event);
     }
     else if(event.getMessageContent().contains(SCORE)){
         event.getChannel().sendMessage(String.valueOf(score));
     }
    }
    public void question1 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
            String messageContent = event.getMessageContent();
            if (messageContent.contains(COMMAND1A)) {
                System.out.println(messageContent.substring(9));
                if (messageContent.substring(9).equals(a1)){
                    score = score + 1;
                    event.getChannel().sendMessage("Correct");
                } else {
                    score = score - 1;
                    event.getChannel().sendMessage("Incorrect");
                }
            }
    }
    public void question2 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND2A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(b1)) {
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question3(MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND3A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(c1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question4 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND4A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(d1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question5 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND5A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(e1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question6 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND6A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(f1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question7 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND7A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(g1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question8 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND8A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(h1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question9 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND9A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(i1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }
    public void question0 (MessageCreateEvent event) throws InterruptedException {
        answeredEvent = event;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND0A)) {
            System.out.println(messageContent.substring(9));
            if (messageContent.substring(9).equals(j1)){
                score = score + 1;
                event.getChannel().sendMessage("Correct");
            } else {
                score = score - 1;
                event.getChannel().sendMessage("Incorrect");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        qTimer.stop();
            answeredEvent.getChannel().sendMessage("You ran out of time");


    }
}
