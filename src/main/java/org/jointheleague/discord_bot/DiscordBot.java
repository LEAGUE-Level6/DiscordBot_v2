package org.jointheleague.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.NewsApi;
import org.jointheleague.features.help_embed.HelpListener;
import org.jointheleague.features.student.Hooray;
import org.jointheleague.features.student.TryAgain;
import org.jointheleague.features.student.Input;
import org.jointheleague.features.student.PriceNotifier;

public class DiscordBot {

	private String token;

	private String channelName;

	JDA api;

	HelpListener helpListener;

	String progressId="";
	Boolean ran = false;

	public DiscordBot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
		helpListener = new HelpListener(channelName);
	}

	public void connect(boolean printInvite) throws InterruptedException {

		api = JDABuilder.createDefault(token)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT) // enables explicit access to message.getContentDisplay()
				.build();
		api.awaitReady();

		//Print the URL to invite the bot
		//if (printInvite) {
			System.out.println("To authorize your bot, send your teacher this link: " + api.getInviteUrl()
					+"\n\tThis message can be disabled in org.jointheleague.Launcher.java");
			//api.getTextChannelsByName(channelName, true).forEach(e -> e.sendMessage(api.getInviteUrl()).submit());
		//}
		PriceNotifier pn = new PriceNotifier(channelName, this);
		//Send bot connected message in channel
		MessageCreateData botConnected = new MessageCreateBuilder()
				.addContent(api.getSelfUser().getName() + " has blessed you with his presence")
				.build();
		api.getTextChannelsByName(channelName, true).forEach(e -> {
			e.sendMessage(botConnected).submit().join();
			e.sendMessage("indexing...").submit();
			e.sendTyping();

		});
		pn.index();

		//add help listener to bot
		api.addEventListener(helpListener);

		//add features
		addFeature(new TryAgain(channelName));
		addFeature(new Hooray(channelName));
		addFeature(new Input(channelName));
		//addFeature(new CurrentTime(channelName));
		//addFeature(new HighLowGame(channelName));
		addFeature(new NewsApi(channelName));
		addFeature(pn);
						//addFeature(new CatFactsApi(channelName));
	}

	private void addFeature(Feature feature){
		api.addEventListener(feature);
		helpListener.addHelpEmbed(feature.getHelpEmbed());
	}
	public void sendMessage(String in){
		api.getTextChannelsByName(channelName, true).forEach(e -> {
			e.sendMessage(in).submit();
		//	e.getLatestMessageId()
		//	e.editMessageById()
	});
	}
	public void progressMsg(int in, int max) {
		if (!ran) {
			api.getTextChannelsByName(channelName, true).forEach(e -> {
				e.sendMessage("Indexing ?/?").submit();
				progressId=e.getLatestMessageId();
				System.out.println("message to edit: "+e.retrieveMessageById(progressId).complete().getContentDisplay());});
			ran=true;
		}else{
			api.getTextChannelsByName(channelName, true).forEach(e -> {
				try {
					e.editMessageById(progressId, ("Indexing " + in + "/" + max));
				}catch(Exception p){
					p.printStackTrace();
				}
				});
		}
	}
}
