import org.jointheleague.discord_bot.DiscordBot;

public class Launcher {
    public static void main(String[] args) {

        //Initialize variables
        String channelName = "ozan";//System.getenv("CHANNEL_NAME");
        String discordToken = "ODgwNjE4ODQ3OTg2NjQ3MDYx.YSg6Ng.MxiBit4zsaXelq5QokLODhU3PeM";//System.getenv("DISCORD_TOKEN"); 
        boolean printDiscordInvite = true;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, channelName);
        discordBot.connect(printDiscordInvite);

    }
}
