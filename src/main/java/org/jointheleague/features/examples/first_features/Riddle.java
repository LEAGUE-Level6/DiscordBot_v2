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
    public final String COMMAND2 = "!riddle2";
    public final String COMMAND3 = "!riddle3";
    public final String COMMAND4 = "!riddle4";
    public final String COMMAND5 = "!riddle5";
    public final String COMMAND6 = "!riddle6";
    public final String COMMAND7 = "!riddle7";
    public final String COMMAND8 = "!riddle8";
    public final String COMMAND9 = "!riddle9";
    public final String COMMAND0 = "!riddle0";
    public final String SCORE = "!score";
    int score=0;
    Timer qTimer = new Timer(3000, this);
    public Riddle(String channelName ) {
        super(channelName);
    }


    @Override
    public void handle(MessageCreateEvent event) throws APIException, InterruptedException {

     if(event.getMessageContent().contains(COMMAND1)) {
         event.getChannel().sendMessage(question1(event));
     } else if(event.getMessageContent().contains(COMMAND2)) {
         event.getChannel().sendMessage(question2(event));
     }
     else if(event.getMessageContent().contains(COMMAND3)) {
         event.getChannel().sendMessage(question3(event));
     }
     else if(event.getMessageContent().contains(COMMAND4)) {
         event.getChannel().sendMessage(question4(event));
     }
     else if(event.getMessageContent().contains(COMMAND5)) {
         event.getChannel().sendMessage(question5(event));
     }
     else if(event.getMessageContent().contains(COMMAND6)) {
         event.getChannel().sendMessage(question6(event));
     }
     else if(event.getMessageContent().contains(COMMAND7)) {
         event.getChannel().sendMessage(question7(event));
     }
     else if(event.getMessageContent().contains(COMMAND8)) {
         event.getChannel().sendMessage(question8(event));
     }
     else if(event.getMessageContent().contains(COMMAND9)) {
         event.getChannel().sendMessage(question9(event));
     }
     else if(event.getMessageContent().contains(COMMAND0)) {
         event.getChannel().sendMessage(question0(event));
     } else if(event.getMessageContent().contains(SCORE)){
         event.getChannel().sendMessage(String.valueOf(score));
     }
    }
    public String question1 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(a);

        String messageContent = event.getMessageContent();
        System.out.println(messageContent);
        //String answer = messageContent.replaceAll(" ", "").replace(COMMAND1, "");


        do {
            System.out.println(messageContent);
            if (messageContent.equals("!riddle1")){
                qTimer.start();
                answeredEvent=event;
            }

            if (messageContent.equalsIgnoreCase(a1)) {
                score = score + 1;
                return "Correct";

            } else {
                score = score - 1;
                return "Incorrect";
            }
        } while(messageContent!="!riddle1");

    }
    public String question2 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(b);
        java.util.concurrent.TimeUnit.SECONDS.sleep (3);
        String messageContent = event.getMessageContent();
        String answer2 = messageContent.replaceAll(" ", "").replace(COMMAND2, "");
        if (answer2.equals(b1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question3(MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(c);
        java.util.concurrent.TimeUnit.SECONDS.sleep (3);
        String messageContent = event.getMessageContent();
        String answer3 = messageContent.replaceAll(" ", "").replace(COMMAND3, "");
        if (answer3.equals(c1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question4 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(d);
        java.util.concurrent.TimeUnit.SECONDS.sleep (3);
        String messageContent = event.getMessageContent();
        String answer4 = messageContent.replaceAll(" ", "").replace(COMMAND4, "");
        if (answer4.equals(d1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question5 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(e);
        java.util.concurrent.TimeUnit.SECONDS.sleep (3);
        String messageContent = event.getMessageContent();
        String answer5 = messageContent.replaceAll(" ", "").replace(COMMAND5, "");
        if (answer5.equals(e1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question6 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(f);
        java.util.concurrent.TimeUnit.SECONDS.sleep (3);
        String messageContent = event.getMessageContent();
        String answer6 = messageContent.replaceAll(" ", "").replace(COMMAND6, "");
        if (answer6.equals(f1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question7 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(g);
        java.util.concurrent.TimeUnit.SECONDS.sleep (3);
        String messageContent = event.getMessageContent();
        String answer7 = messageContent.replaceAll(" ", "").replace(COMMAND7, "");
        if (answer7.equals(g1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question8 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(h);

        String messageContent = event.getMessageContent();
        if (messageContent != " ") {
            java.util.concurrent.TimeUnit.SECONDS.sleep(2);
    } else{
            String answer8 = messageContent.replaceAll(" ", "").replace(COMMAND8, "");
            if (answer8.equals(h1)) {
                score = score + 1;
                return "Correct";

            } else {
                score = score - 1;
                return "Incorrect";
            }
        }
        return "Incorrect";
    }
    public String question9 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(i);
        java.util.concurrent.TimeUnit.SECONDS.sleep (8);
        String messageContent = event.getMessageContent();
        String answer9 = messageContent.replaceAll(" ", "").replace(COMMAND9, "");
        if (answer9.equals(i1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question0 (MessageCreateEvent event) throws InterruptedException {
        event.getChannel().sendMessage(j);
        java.util.concurrent.TimeUnit.SECONDS.sleep (8);
        String messageContent = event.getMessageContent();
        String answer0 = messageContent.replaceAll(" ", "").replace(COMMAND0, "");
        if (answer0.equals(j1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            answeredEvent.getChannel().sendMessage("You ran out of time");


    }
}
