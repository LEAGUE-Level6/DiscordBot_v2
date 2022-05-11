package org.jointheleague.discord_bot;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Timer;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.LowLevelComponent;

//This program should be combined with the DiscordBotOld

class Cons {
	boolean Left, Right, Up, Down;
	Cons(HashMap<String, Boolean> Cons) {
		this.Left = Cons.get("Left");
		this.Right = Cons.get("Right");
		this.Up = Cons.get("Up");
		this.Down = Cons.get("Down");
	}
}
class Enemy {
	int X, Y;
	Enemy() {
		X = (int) Math.round(Math.random() * 999);
		Y = (int) Math.round(Math.random() * 499);
		int Choice = (int) Math.round(Math.random() * 3);
		if (Choice == 1) {
			X = 0;
		} else if (Choice == 2) {
			X = 999;
		} else if (Choice == 3) {
			Y = 499;
		} else {
			Y = 0;
		}
	}
	void Tick() {
		if (Math.random() > 0.5) {
			X += 10;
		} else {
			X -= 10;
		}
		if (Math.random() > 0.5) {
			Y += 10;
		} else {
			Y -= 10;
		}
		if (X < 0) {
			X = 990;
		} else if (X > 990) {
			X = 0;
		}
		if (Y < 0) {
			Y = 490;
		} else if (Y > 490) {
			Y = 0;
		}
	}
}
//This game is very confusing on how to play, you should give instructions on what the game is.
class Game implements ActionListener {
	DiscordApi API;
	Message Msg, OldMsg;
	BufferedImage Img;
	Graphics2D Canv;
	TextChannel Chan;
	HashMap<String, Boolean> ConsDown = new HashMap<String, Boolean>();
	Timer Timer;
	Game(DiscordApi API, Message Msg) {
		this.API = API;
		this.OldMsg = Msg;
		this.Img = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
		this.Canv = Img.createGraphics();
		this.Chan = Msg.getChannel();
		Timer = new Timer(1000, this);//You should increase the time delay slightly as you get packets that go too fast causing discord to refuse some of them for a bit.
		Timer.start();
		ConsDown.put("Left", false);
		ConsDown.put("Right", false);
		ConsDown.put("Up", false);
		ConsDown.put("Down", false);
		API.addMessageComponentCreateListener(Evt -> {
			String ID = Evt.getMessageComponentInteraction().getCustomId();
			ConsDown.put(ID, !ConsDown.get(ID));
		});
	}
	int PlayerX = 500;
	int PlayerY = 250;
	List<Enemy> Enemies = new ArrayList<Enemy>();
	@Override
	public void actionPerformed(ActionEvent Evt) {
		Cons Cons = new Cons(ConsDown);
		this.Canv.clearRect(0, 0, 1000, 500);
		if (Cons.Left) PlayerX -= 10;
		if (Cons.Right) PlayerX += 10;
		if (Cons.Up) PlayerY -= 10;
		if (Cons.Down) PlayerY += 10;
		if (PlayerX < 0) {
			PlayerX = 970;
		} else if (PlayerX > 970) {
			PlayerX = 0;
		}
		if (PlayerY < 0) {
			PlayerY = 470;
		} else if (PlayerY > 470) {
			PlayerY = 0;
		}
		this.Canv.setColor(Color.GREEN);
		this.Canv.fillRect(PlayerX, PlayerY, 30, 30);
		if (Enemies.size() < 20) {
			Enemies.add(new Enemy());
		}
		this.Canv.setColor(Color.RED);
		Enemies.forEach(Enemy -> {
			Enemy.Tick();
			this.Canv.fillRect(Enemy.X, Enemy.Y, 10, 10);
			if (PlayerX < Enemy.X + 10 && PlayerX + 30 > Enemy.X && PlayerY < Enemy.Y + 10 && PlayerY + 30 > Enemy.X) {
				this.Canv.clearRect(0, 0, 1000, 500);
				this.Canv.drawString("YOU DIED!!!", 10, 10);//You should center this, also it would be nice to have a score
				Timer.stop();
			}
		});
		try {
			MessageBuilder Builder = new MessageBuilder().addAttachment(this.Img, "Image.png");
			List<LowLevelComponent> Buttons = new ArrayList<LowLevelComponent>();
			ConsDown.keySet().forEach(Con -> {
				Buttons.add(ConsDown.get(Con) ? Button.primary(Con, Con) : Button.secondary(Con, Con));
			});
			Message ActualOldMsg = OldMsg;
			OldMsg = Builder.addComponents(ActionRow.of(Buttons)).send(Chan).get();
			ActualOldMsg.delete();
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
				catch (Exception Exc) {Exc.printStackTrace();}//You should notify the user that there was an error
			}
		});
	}
}
