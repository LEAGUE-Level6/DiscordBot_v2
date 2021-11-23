import org.jointheleague.discord_bot.DiscordBot;

public class Launcher {
    public static void main(String[] args) {
        new DiscordBot(System.getenv("DISCORD_TOKEN")).Connect();
    }
}
