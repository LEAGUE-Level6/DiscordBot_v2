import org.jointheleague.discord_bot.DiscordBot;
import org.jointheleague.discord_bot.DiscordBotOld;
public class Launcher {
    public static void main(String[] args) {
        //new DiscordBotOld(System.getenv("DISCORD_TOKEN")).Connect();
    	new DiscordBot(System.getenv("DISCORD_TOKEN")).Connect();
    }
}
