package org.jointheleague.features.examples.first_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;

public class Riddle extends Feature {
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
    public Riddle(String channelName) {
        super(channelName);
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException, InterruptedException {
        String messageContent = event.getMessageContent();
     if(event.getMessageContent().contains(COMMAND1)) {
         event.getChannel().sendMessage(a);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer = messageContent.replaceAll(" ", "").replace(COMMAND1, "");
         event.getChannel().sendMessage(question1(answer));
     } else if(event.getMessageContent().contains(COMMAND2)) {
         event.getChannel().sendMessage(b);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer2 = messageContent.replaceAll(" ", "").replace(COMMAND2, "");
         event.getChannel().sendMessage(question2(answer2));
     }
     else if(event.getMessageContent().contains(COMMAND3)) {
         event.getChannel().sendMessage(c);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer3 = messageContent.replaceAll(" ", "").replace(COMMAND3, "");
         event.getChannel().sendMessage(question3(answer3));
     }
     else if(event.getMessageContent().contains(COMMAND4)) {
         event.getChannel().sendMessage(d);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer4 = messageContent.replaceAll(" ", "").replace(COMMAND4, "");
         event.getChannel().sendMessage(question4(answer4));
     }
     else if(event.getMessageContent().contains(COMMAND5)) {
         event.getChannel().sendMessage(e);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer5 = messageContent.replaceAll(" ", "").replace(COMMAND5, "");
         event.getChannel().sendMessage(question5(answer5));
     }
     else if(event.getMessageContent().contains(COMMAND6)) {
         event.getChannel().sendMessage(f);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer6 = messageContent.replaceAll(" ", "").replace(COMMAND6, "");
         event.getChannel().sendMessage(question6(answer6));
     }
     else if(event.getMessageContent().contains(COMMAND7)) {
         event.getChannel().sendMessage(g);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer7 = messageContent.replaceAll(" ", "").replace(COMMAND7, "");
         event.getChannel().sendMessage(question7(answer7));
     }
     else if(event.getMessageContent().contains(COMMAND8)) {
         event.getChannel().sendMessage(h);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer8 = messageContent.replaceAll(" ", "").replace(COMMAND8, "");
         event.getChannel().sendMessage(question8(answer8));
     }
     else if(event.getMessageContent().contains(COMMAND9)) {
         event.getChannel().sendMessage(i);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer9 = messageContent.replaceAll(" ", "").replace(COMMAND9, "");
         event.getChannel().sendMessage(question9(answer9));
     }
     else if(event.getMessageContent().contains(COMMAND0)) {
         event.getChannel().sendMessage(j);
         java.util.concurrent.TimeUnit.SECONDS.sleep (3);

         String answer0 = messageContent.replaceAll(" ", "").replace(COMMAND0, "");
         event.getChannel().sendMessage(question0(answer0));
     } else if(event.getMessageContent().contains(SCORE)){
         event.getChannel().sendMessage(String.valueOf(score));
     }
    }
    public String question1 (String answer) {
        if (answer.equals(a1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question2 (String answer2) {
        if (answer2.equals(b1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question3(String answer3) {
        if (answer3.equals(c1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question4 (String answer4) {
        if (answer4.equals(d1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question5 (String answer5) {
        if (answer5.equals(e1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question6 (String answer6) {
        if (answer6.equals(f1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question7 (String answer7) {
        if (answer7.equals(g1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question8 (String answer8) {
        if (answer8.equals(h1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question9 (String answer9) {
        if (answer9.equals(i1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }
    public String question0 (String answer0) {
        if (answer0.equals(j1)) {
            score = score + 1;
            return "Correct";

        } else {
            score = score-1;
            return "Incorrect";
        }
    }

}
