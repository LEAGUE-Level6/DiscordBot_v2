package org.jointheleague.features.help_embed.plain_old_java_objects.help_embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class HelpEmbed {
	
	private final Color color = Color.green;

	private final String command;

	private final String description;

	public HelpEmbed(String command, String description){
		this.command = command;
		this.description = description; 
	}

	public Color getColor() {
		return color;
	}

	public String getTitle() {
		return command;
	}

	public String getDescription() {
		return description;
	}
	
	public MessageEmbed getEmbed() {
		MessageEmbed embed = new EmbedBuilder()
			.setColor(this.color)
			.setTitle(this.command)
			.setDescription(this.description)
			.build();
		return embed;
	}
	
}
