package org.jointheleague.features.abstract_classes;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import net.aksingh.owmjapis.api.APIException;

public abstract class ReactionFeature extends Feature implements ReactionAddListener {

	public ReactionFeature(String channelName) {
		super(channelName);
	}
    
    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        event.getServerTextChannel().ifPresent(e -> {
            if (e.getName().equals(channelName)) {
                try {
                    handleReaction(event);
                } catch (APIException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

	@Override
	public abstract void handle(MessageCreateEvent event) throws APIException;

	
	public abstract void handleReaction(ReactionAddEvent event) throws APIException;

}
