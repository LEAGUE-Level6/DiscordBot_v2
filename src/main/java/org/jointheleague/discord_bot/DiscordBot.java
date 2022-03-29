package org.jointheleague.discord_bot;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
class Game implements ActionListener {
	Message Message;
	TextChannel Channel;
	MessageBuilder Builder;
	BufferedImage Image;
	Graphics2D Canvas;
	int X;
	Game(Message Message) {
		this.Message = Message;
		this.Channel = this.Message.getChannel();
		this.Image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
		this.Canvas = this.Image.createGraphics();
		new Timer(1000, this).start();
	}
	@Override
	public void actionPerformed(ActionEvent Event) {
		X++;
		this.Canvas.setColor(Color.BLACK);
		this.Canvas.fillRect(0, 0, 1000, 500);
		this.Canvas.setColor(Color.RED);
		this.Canvas.fillRect(X * 10, 10, 10, 10);
		this.Builder = new MessageBuilder();
		this.Builder.addAttachment(this.Image, "Frame.png");
		this.Message.delete();
		this.Builder.send(this.Channel);
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
				try {
					new Game(Message.getChannel().sendMessage("Loading...").get());
				} catch (Exception E) {
					E.printStackTrace();
				}
			}
		});
	}
}
