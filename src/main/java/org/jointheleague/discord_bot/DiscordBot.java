package org.jointheleague.discord_bot;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
class Game implements ActionListener {
	DiscordApi API;
	Message Msg, OldMsg;
	BufferedImage Img;
	Graphics2D Canv;
	TextChannel Chan;
	HashMap<String, String> Inputs = new HashMap<String, String>();
	Game(DiscordApi API, Message Msg) {
		this.API = API;
		this.OldMsg = Msg;
		this.Img = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
		this.Canv = Img.createGraphics();
		this.Chan = Msg.getChannel();
		
		new Timer(1000, this).start();
		API.addMessageComponentCreateListener(Evt -> {
			MessageComponentInteraction Interaction = Evt.getMessageComponentInteraction();
			String ID = Interaction.getCustomId();
			Interaction.createImmediateResponder().respond();
		});
	}
	@Override
	public void actionPerformed(ActionEvent Evt) {
		OldMsg.delete();
		
		this.Canv.fillRect((int) Math.round(Math.random() * 500) + 250, (int) Math.round(Math.random() * 250) + 125, 10, 10);
		try {
			OldMsg = new MessageBuilder()
			.addAttachment(this.Img, "Image.png")
			.addComponents(ActionRow.of(Button.secondary("Left", "Left"),
			Button.secondary("Down", "Down"),
			Button.secondary("Right", "Right"),
			Button.secondary("Up", "Up"),
			Button.danger("Stop", "Stop")))
			.send(Chan).get();
		} catch (Exception Exc) {Exc.printStackTrace();}
	}
}
public class DiscordBot {
	DiscordApi API;
	public DiscordBot(String Token) {
		this.API = new DiscordApiBuilder().setToken(Token).login().join();
	}
	public void Connect() {
		this.API.addMessageCreateListener(Message -> {
			if (Message.getMessageContent().equals("BBot: Test")) {
				try {new Game(this.API, Message.getChannel().sendMessage("Loading...").get());}
				catch (Exception Exc) {Exc.printStackTrace();}
			}
		});
	}
}
