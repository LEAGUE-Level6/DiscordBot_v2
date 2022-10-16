package org.jointheleague.features.examples.custom_features;

public class CreateMath {
    public final String COMMAND  = "!newMath";
    public CreateMath(String channelName){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter two numbers: ");
        int a = 0;
        int b = 0;
        int division = a/b;
        int multiply = a*b;
        int add = a+b;
        int sub = a-b;
    }
}
