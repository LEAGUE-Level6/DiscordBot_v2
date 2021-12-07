package org.jointheleague.discord_bot;

import org.javacord.api.event.message.*;

public class ReactionUtils {
	static void ThumbsUp(MessageCreateEvent E) {
		E.addReactionToMessage("👍");
	}
	static void ThumbsDown(MessageCreateEvent E) {
		E.addReactionsToMessage("👎");
	}
	static void Laugh(MessageCreateEvent E) {
		E.addReactionsToMessage("😂");
	}
}
