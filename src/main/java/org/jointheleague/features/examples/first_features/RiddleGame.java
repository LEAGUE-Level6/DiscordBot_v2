package org.jointheleague.features.examples.first_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;

public class RiddleGame extends Feature  {
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
        public final String COMMAND = "!ridle";
    //    public final String
        public RiddleGame(String channelName) {
        super(channelName);
    }

    @Override
        public void handle(MessageCreateEvent event) throws APIException, InterruptedException {
        int score = 0;
        int question = 0;
        String messageContent = event.getMessageContent();
        if (messageContent.contains(COMMAND)) {
            event.getChannel().sendMessage("We will start off easy, but you must choose a number between 1 and 10" );
            String guessMessage1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
            int guessMessag1 = Integer.parseInt(guessMessage1);
            if(guessMessag1==1&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(a);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(a1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;
                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");

                }

            }
            else if(guessMessag1==2&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(b);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(b1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==3&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(c);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(c1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==4&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(d);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(d1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==5&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(e);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(e1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==6&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(f);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(f1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==7&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(g);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(g1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==8&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(h);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(h1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==9&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(i);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(i1)){
                    score=score+10;

                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;

                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
            else if(guessMessag1==10&&messageContent.contains(COMMAND)){
                event.getChannel().sendMessage(j);
                java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (answer1.equals(j1)){
                    score=score+10;
                    question=question+1;
                    event.getChannel().sendMessage("Correct! I guess you were just lucky that time");
                } else{
                    score=score-5;
                    event.getChannel().sendMessage("Hmmm, that's wrong. Don't worry, you will have a shot at redemption");
                }
            }
           if(question==1) {
               event.getChannel().sendMessage("Now choose a new number between 1 and 10, but it CANNOT be the same number as last time");
               String guessMessage2 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
               int guessMessag2 = Integer.parseInt(guessMessage2);
               if (guessMessag2 == 1&&messageContent.contains(COMMAND)) {

                   event.getChannel().sendMessage(a);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(a1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;

                       event.getChannel().sendMessage("Incorrect!");
                   }

               } else if (guessMessag2 == 2&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(b);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(b1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 3&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(c);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(c1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 4&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(d);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(d1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 5&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(e);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(e1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 6&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(f);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(f1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct! ");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 7&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(g);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(g1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 8&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(h);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(h1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct! ");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 9&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(i);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(i1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       question=question+1;
                       event.getChannel().sendMessage("Incorrect!");
                   }
               } else if (guessMessag2 == 10&&messageContent.contains(COMMAND)) {
                   event.getChannel().sendMessage(j);
                   java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                   String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                   if (answer1.equals(j1)) {
                       score = score + 10;
                       question=question+1;
                       event.getChannel().sendMessage("Correct!");
                   } else {
                       score = score - 5;
                       event.getChannel().sendMessage("Incorrect!");
                       question=question+1;
                   }
               }
           }
            if(question==2) {
                event.getChannel().sendMessage("Choose a new number between 1 and 10, but it CANNOT be the same number as last time");
                String guessMessage3 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                int guessMessag3 = Integer.parseInt(guessMessage3);
                if (guessMessag3 == 1&&messageContent.contains(COMMAND)) {

                    event.getChannel().sendMessage(a);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(a1)&&guessMessage3.contains(COMMAND)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }

                } else if (guessMessag3 == 2&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(b);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(b1)&&guessMessage3.contains(COMMAND)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else if (guessMessage3.contains(COMMAND)) {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 3&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(c);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(c1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 4&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(d);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(d1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 5&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(e);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(e1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 6&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(f);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(f1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct! ");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 7&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(g);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(g1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 8&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(h);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(h1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct! ");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 9&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(i);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(i1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                } else if (guessMessag3 == 10&&messageContent.contains(COMMAND)) {
                    event.getChannel().sendMessage(j);
                    java.util.concurrent.TimeUnit.SECONDS.sleep (3);
                    String answer1 = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                    if (answer1.equals(j1)) {
                        score = score + 10;
                        event.getChannel().sendMessage("Correct!");
                    } else {
                        score = score - 5;
                        event.getChannel().sendMessage("Incorrect!");
                    }
                }
            }
            event.getChannel().sendMessage("Your score is: "+ score);


        }
    }

    }

