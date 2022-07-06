package ChessClasses;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "player_id", "@id", "url", "username", "followers", "country", "last_online", "joined", "status",
	"is_streamer", "verified" })
@Generated("jsonschema2pojo")
public class PlayerInfo {

@JsonProperty("player_id")
private Integer playerId;
@JsonProperty("@id")
private String id;
@JsonProperty("url")
private String url;
@JsonProperty("username")
private String username;
@JsonProperty("followers")
private Integer followers;
@JsonProperty("country")
private String country;
@JsonProperty("last_online")
private Integer lastOnline;
@JsonProperty("joined")
private Integer joined;
@JsonProperty("status")
private String status;
@JsonProperty("is_streamer")
private Boolean isStreamer;
@JsonProperty("verified")
private Boolean verified;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("player_id")
public Integer getPlayerId() {
	return playerId;
}

@JsonProperty("player_id")
public void setPlayerId(Integer playerId) {
	this.playerId = playerId;
}

@JsonProperty("@id")
public String getId() {
	return id;
}

@JsonProperty("@id")
public void setId(String id) {
	this.id = id;
}

@JsonProperty("url")
public String getUrl() {
	return url;
}

@JsonProperty("url")
public void setUrl(String url) {
	this.url = url;
}

@JsonProperty("username")
public String getUsername() {
	return username;
}

@JsonProperty("username")
public void setUsername(String username) {
	this.username = username;
}

@JsonProperty("followers")
public Integer getFollowers() {
	return followers;
}

@JsonProperty("followers")
public void setFollowers(Integer followers) {
	this.followers = followers;
}

@JsonProperty("country")
public String getCountry() {
	return country;
}

@JsonProperty("country")
public void setCountry(String country) {
	this.country = country;
}

@JsonProperty("last_online")
public Integer getLastOnline() {
	return lastOnline;
}

@JsonProperty("last_online")
public void setLastOnline(Integer lastOnline) {
	this.lastOnline = lastOnline;
}

@JsonProperty("joined")
public Integer getJoined() {
	return joined;
}

@JsonProperty("joined")
public void setJoined(Integer joined) {
	this.joined = joined;
}

@JsonProperty("status")
public String getStatus() {
	return status;
}

@JsonProperty("status")
public void setStatus(String status) {
	this.status = status;
}

@JsonProperty("is_streamer")
public Boolean getIsStreamer() {
	return isStreamer;
}

@JsonProperty("is_streamer")
public void setIsStreamer(Boolean isStreamer) {
	this.isStreamer = isStreamer;
}

@JsonProperty("verified")
public Boolean getVerified() {
	return verified;
}

@JsonProperty("verified")
public void setVerified(Boolean verified) {
	this.verified = verified;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
}

}
