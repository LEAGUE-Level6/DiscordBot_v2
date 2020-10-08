package org.jointheleague.features.examples.third_features;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.api_example.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.api_example.Article;

import com.google.gson.Gson;

//this example uses the "News API"
//documentation for the API can be found here: https://newsapi.org/docs/get-started
public class ApiExample extends Feature {

		//API key is received through creating an account on the web site.
		//API may not require a key, or may require the key as a header as in PictureOf.java
		private final String apiKey = "59ac01326c584ac0a069a29798794bec";
		public final String COMMAND = "!apiExample";
		private final Gson gson = new Gson();
	  
		public ApiExample(String channelName) {
			super(channelName);
			helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a news story related to a topic (e.g. !apiExample cats)");
		}

		@Override
		public void handle(MessageCreateEvent event) {
			if(event.getMessageContent().contains(COMMAND)) {
				//remove the command so we are only left with the search term
				String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

				if (msg.equals("")) {
					event.getChannel().sendMessage("Please put a word after the command");
				} else {
					String definition = getNewsStoryByTopic(msg);
					event.getChannel().sendMessage(definition);
				}
				
			}
		}
		
		public String getNewsStoryByTopic(String topic) {
			
			//create the request URL (can be found in the documentation)
			String requestURL = "http://newsapi.org/v2/everything?" +
			          "q="+topic+"&" +
			          "sortBy=popularity&" +
			          "apiKey="+apiKey;
			 
			try {
				
				//the following code will probably be the same for your feature
				URL url = new URL(requestURL);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				JsonReader repoReader = Json.createReader(con.getInputStream());
			    JsonObject userJSON = ((JsonObject) repoReader.read());
			    con.disconnect();

			    /*
			    Turn the json response into a java object:

				The response received is in JSON, but we need to turn that into a java object in order
				manipulate it effectively.

				You will need to create a new package containing some java classes that represents the response in org.jointheleague.modules.pojo
				You can use a tools like jsonschema2pojo.com to help with that
				If using jsonschema2pojo.com, select Target Language = java, Source Type = JSON, Annotation Style = Gson
			     */

				//uncomment the next line to see the actual JSON response - this is what you will input into jsonschema2pojo.com
				//System.out.println(userJSON.toString());

				//deserialize the response into a java object using the classes you just created
			    ApiExampleWrapper apiExampleWrapper = gson.fromJson(userJSON.toString(), ApiExampleWrapper.class);

				//get the first article (these are just java objects now)
				Article article = apiExampleWrapper.getArticles().get(0);
				
				//get the title of the article 
				String articleTitle = article.getTitle();
				
				//get the content of the article 
				String articleContent = article.getContent();
				
				//create the message
				String message = articleTitle + " - " + articleContent;
				
				//send the message 
				return message;

			} catch (Exception e) {
				e.printStackTrace();
			}

			return "No news story found for the keyword: " + topic;
		}
		
		
	}

