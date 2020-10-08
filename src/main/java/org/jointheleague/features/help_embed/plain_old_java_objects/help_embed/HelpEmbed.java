package org.jointheleague.features.help_embed.plain_old_java_objects.help_embed;

import org.javacord.api.entity.message.embed.EmbedBuilder;
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
	
	public EmbedBuilder getEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(this.color);
		embed.setTitle(this.command);
		embed.setDescription(this.description);
		return embed;
	}
	
}
