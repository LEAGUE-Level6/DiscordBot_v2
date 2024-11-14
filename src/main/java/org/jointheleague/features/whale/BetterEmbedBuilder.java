package org.jointheleague.features.whale;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class BetterEmbedBuilder extends EmbedBuilder{
	public String title;
@Override
public EmbedBuilder setTitle(String title) {
	// TODO Auto-generated method stub
	this.title = title;
	return super.setTitle(title);
}

}
