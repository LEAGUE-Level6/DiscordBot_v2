package org.jointheleague.discord_bot;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.Timer;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.event.ListenerManager;
class Game implements ActionListener {
	MessageBuilder Builder;
	DiscordApi API;
	Message Message, OldMessage;
	TextChannel Channel;
	BufferedImage Image;
	Graphics2D Canvas;
	int X;
	Game(DiscordApi API, Message Message) {
		this.Builder = new MessageBuilder();
		this.API = API;
		this.Message = Message;
		this.Channel = this.Message.getChannel();
		this.Image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
		this.Canvas = this.Image.createGraphics();
		this.Builder.addAttachment(new File("Image.png"));
		this.Builder.addComponents(ActionRow.of(Button.primary("Start", "Start")));
		this.OldMessage = this.Message;
		try {
			this.Message = this.Builder.send(this.Channel).get();
		} catch (Exception E) {
			E.printStackTrace();
		}
		this.OldMessage.delete();
		final ListenerManager<MessageComponentCreateListener> Listener = this.API.addMessageComponentCreateListener(E -> {
			Listener.remove();
			new Timer(1000, this).start();
		});
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
		this.Builder.addComponents(ActionRow.of(Button.secondary("Left", "Left"), Button.secondary("Right", "Right"), Button.success("Fire", "Fire")));
		this.OldMessage = this.Message;
		try {
			this.Message = this.Builder.send(this.Channel).get();
		} catch (Exception E) {
			E.printStackTrace();
		}
		this.OldMessage.delete();
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
					new Game(this.API, Message.getChannel().sendMessage("Loading...").get());
				} catch (Exception E) {
					E.printStackTrace();
				}
			}
		});
	}
}
