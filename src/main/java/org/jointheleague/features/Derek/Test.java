package org.jointheleague.features.Derek;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.first_features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Test extends Feature {
    int tries = 0;
    String type;
    int times = 0;
    public final String COMMAND = "!school";
    int stage = 10;
    double correctAnswer;
    String correctAnswerS;
    boolean inGame = false;
    public Test(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "The place you don't want to go to. SO I BROUGHT IT TO YOU. :)\nTo respond to a question, type !school answer"
        );
    }
    public double addition(double num1, double num2){
        type="add";
        return num1+num2;
    }

    public double subtraction(double num1, double num2){
        type="sub";
        return num1-num2;
    }

    public double multiplication(double num1, double num2){
        type="mult";
        return num1*num2;
    }

    public double division(double num1, double num2){
        type="div";
        return num1/num2;
    }

    public Double fakeAnswer(Random r){
        double temp = correctAnswer;
        boolean temp1 = r.nextBoolean();
        if (temp1==true){
            temp+=(correctAnswer*(r.nextDouble()/stage));
        }else{
            temp-=(correctAnswer*(r.nextDouble()/stage));
        }
        if (!type.equals("div")){
            String s1 = String.format("%.0f",temp);
            temp = Double.parseDouble(s1);
        }
        return temp;
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)&&inGame==false) {
            event.getChannel().sendMessage("Commencing frustration and despair");
            Random r = new Random();
            double num1 = (double)r.nextInt(4000);
            String s1 = String.format("%.0f",num1);
            num1 = Double.parseDouble(s1);
            if (r.nextInt(2)==0){
                num1=num1*-1;
            }
            double num2 = (double)r.nextInt(4000);
            String s2 = String.format("%.0f",num2);
            num2 = Double.parseDouble(s2);
            if (r.nextInt(2)==0){
                num2=num2*-1;
            }
            int temp = r.nextInt(4);
            if (temp==0){
                correctAnswer=addition(num1,num2);
                event.getChannel().sendMessage("What is "+(int)num1+" + "+(int)num2+"?");
            }else if (temp==1){
                correctAnswer=subtraction(num1,num2);
                event.getChannel().sendMessage("What is "+(int)num1+" - "+(int)num2+"?");
            }else if (temp==2){
                correctAnswer=multiplication(num1,num2);
                event.getChannel().sendMessage("What is "+(int)num1+" * "+(int)num2+"?");
            }else if (temp==3){
                correctAnswer=division(num1,num2);
                event.getChannel().sendMessage("What is "+(int)num1+" / "+(int)num2+"?");
            }
            temp=r.nextInt(4);
            if (temp==0){
                event.getChannel().sendMessage("A: "+correctAnswer+"\nB: "+fakeAnswer(r)+"\nC: "+fakeAnswer(r)+"\nD: "+fakeAnswer(r));
                correctAnswerS="A";
            }else if (temp==1){
                event.getChannel().sendMessage("A: "+fakeAnswer(r)+"\nB: "+correctAnswer+"\nC: "+fakeAnswer(r)+"\nD: "+fakeAnswer(r));
                correctAnswerS="B";
            }else if (temp==2){
                event.getChannel().sendMessage("A: "+fakeAnswer(r)+"\nB: "+fakeAnswer(r)+"\nC: "+correctAnswer+"\nD: "+fakeAnswer(r));
                correctAnswerS="C";
            }else if (temp==3){
                event.getChannel().sendMessage("A: "+fakeAnswer(r)+"\nB: "+fakeAnswer(r)+"\nC: "+fakeAnswer(r)+"\nD: "+correctAnswer);
                correctAnswerS="D";
            }
            inGame=true;
        }else if(messageContent.startsWith(COMMAND)&&inGame==true){
            String[] array = messageContent.split(" ");
            String temp=array[1];
            if (correctAnswerS.equalsIgnoreCase(temp)){
                tries=0;
                times++;
                if (times<=3) {
                    event.getChannel().sendMessage("Good job, mortal.");
                }else if(times>3&&times<10){
                    event.getChannel().sendMessage("I'm impressed.");
                    stage=100;
                }else{
                    event.getChannel().sendMessage("Just stop.");
                    stage=1000;
                }
                inGame=false;
            }else if (tries<3){
                tries++;
                event.getChannel().sendMessage("Wrong. Try again.");
            }else if (tries==3){
                event.getChannel().sendMessage("Haha go back to the start.");
                times = 0;
                tries = 0;
            }
        }
    }

}
