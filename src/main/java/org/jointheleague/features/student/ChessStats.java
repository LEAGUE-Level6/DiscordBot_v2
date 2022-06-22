package org.jointheleague.features.student;

import javax.annotation.Generated;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import reactor.core.publisher.Mono;

public class ChessStats extends Feature {

	//ability for user to choose either player INFO or STATS, then username, ALL stats are printed out in nested format
	
	
	
    public final String COMMAND = "!chess";
    private String lastCommand = "";

    public ChessStats(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Get stats about players on Chess.com using !chess"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	
        	String split[] = messageContent.split(" ");
        	if(split.length != 3) {
        		event.getChannel().sendMessage("Use commands \"!chess URL\", \"!chess rapid\", \"!chess bullet\", and \"!chess blitz\" all followed by a player username!");
        	}else {
        		if(split[1].equalsIgnoreCase("URL")) {
        			final String URL = "https://api.chess.com/pub/player/" + split[2].toLowerCase();
        			WebClient w = WebClient
            				.builder()
            				.baseUrl(URL)
            				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
            		PlayerInfo p = w.get().retrieve().bodyToMono(PlayerInfo.class).block();
            		event.getChannel().sendMessage(split[2] + "'s URL: " + p.getUrl());
            		
        		}else {
        			lastCommand = messageContent;
        			final String URL = "https://api.chess.com/pub/player/" + split[2].toLowerCase() + "/stats";
        			WebClient w = WebClient
            				.builder()
            				.baseUrl(URL)
            				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
        			String info = w.get().retrieve().bodyToMono(String.class).block();
        			
        			if(split[1].equalsIgnoreCase("rapid")) {
        				event.getChannel().sendMessage(printInfo(info, "rapid"));
        			}else if(split[1].equalsIgnoreCase("bullet")) {
        				event.getChannel().sendMessage(printInfo(info, "bullet"));
        			}else if(split[1].equalsIgnoreCase("blitz")) {
        				event.getChannel().sendMessage(printInfo(info, "blitz"));
        			}else {
        				event.getChannel().sendMessage("Invalid mode!");
        			}
        		}
        	}
        }else if(messageContent.startsWith("!game")) {
        	String split[] = lastCommand.split(" ");
        	if(split[1].equals("blitz")) {
        		final String URL = "https://api.chess.com/pub/player/" + split[2].toLowerCase() + "/stats";
    			WebClient w = WebClient
        				.builder()
        				.baseUrl(URL)
        				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    			String info = w.get().retrieve().bodyToMono(String.class).block();
    			
    			
    			event.getChannel().sendMessage(split[2] + "'s 'best' blitz game: " + printGameLink(info));
        	}else if(split[1].equals("rapid")) {
        		final String URL = "https://api.chess.com/pub/player/" + split[2].toLowerCase() + "/stats";
    			WebClient w = WebClient
        				.builder()
        				.baseUrl(URL)
        				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    			String info = w.get().retrieve().bodyToMono(String.class).block();
    			
    			
    			event.getChannel().sendMessage(split[2] + "'s 'best' rapid game: " + printGameLink(info));
        	}else if(split[1].equals("bullet")) {
        		final String URL = "https://api.chess.com/pub/player/" + split[2].toLowerCase() + "/stats";
    			WebClient w = WebClient
        				.builder()
        				.baseUrl(URL)
        				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    			String info = w.get().retrieve().bodyToMono(String.class).block();
    			
    			
    			event.getChannel().sendMessage(split[2] + "'s 'best' bullet game: " + printGameLink(info));

        	}
        }
    }
    
    private String printInfo(String s, String mode) {
    	String result;
    	try {
    	int beginIndex = s.indexOf(mode);
		int endIndex = s.indexOf("}}", beginIndex);
		String specific = s.substring(beginIndex, endIndex);
		
		result = mode + ":"
				+ "\n Current:" + specific.split(":")[3].split(",")[0]
				+ "\n Best: " + specific.split(":")[7].split(",")[0]
				+ "\n Record: "
				+ "\n  W: " + specific.split(":")[12].split(",")[0]
				+ "\n  L: " + specific.split(":")[13].split(",")[0]
				+ "\n  D: " + specific.split(":")[14].split(",")[0].replaceAll("}", "")
				+ "\nTo see their 'best' " + mode + " game, type !game";
    	}catch(Exception e) {
    		result = "Not enough player info!";
    	}
		return result;
    }
    private String printGameLink(String info) {
    	String result = "";
    	try {
    	int beginIndex = info.indexOf("bullet");
		int endIndex = info.indexOf("}}", beginIndex);
		String specific = info.substring(beginIndex, endIndex);
		
		result = specific.split(":")[9].replaceAll("\"", "") + ":" + specific.split(":")[10].split("}")[0].replaceAll("\"", "");
    	}catch(Exception e) {
    		result = "Not enough player info!";
    	}
    	
    	
    	return result;
    }

}

@Generated("jsonschema2pojo")
class PlayerInfo {

	@SerializedName("player_id")
	@Expose
	private Integer playerId;
	@SerializedName("@id")
	@Expose
	private String id;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("username")
	@Expose
	private String username;
	@SerializedName("followers")
	@Expose
	private Integer followers;
	@SerializedName("country")
	@Expose
	private String country;
	@SerializedName("last_online")
	@Expose
	private Integer lastOnline;
	@SerializedName("joined")
	@Expose
	private Integer joined;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("is_streamer")
	@Expose
	private Boolean isStreamer;
	@SerializedName("verified")
	@Expose
	private Boolean verified;

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getFollowers() {
		return followers;
	}

	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getLastOnline() {
		return lastOnline;
	}

	public void setLastOnline(Integer lastOnline) {
		this.lastOnline = lastOnline;
	}

	public Integer getJoined() {
		return joined;
	}

	public void setJoined(Integer joined) {
		this.joined = joined;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsStreamer() {
		return isStreamer;
	}

	public void setIsStreamer(Boolean isStreamer) {
		this.isStreamer = isStreamer;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

}

