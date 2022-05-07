package feature3.Nathan;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class Feature3 extends Feature{
	public final String COMMAND = "!foodimage";

	private WebClient webClient;
    private static final String baseUrl = "https://foodish-api.herokuapp.com/api/";
    
    public Feature3(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "When the command !foodimage is entered, an image of a food item will appear and the user will guess what dish or food category it is."
        );
        
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
        	String foodURL=getCatFact();
            event.getChannel().sendMessage(foodURL);
        }
    }
    
    public String getCatFact() {

        //Make the request, accepting the response as a plain old java object you created
        Mono<CatWrapper> catWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(CatWrapper.class);

        //collect the response into a plain old java object
        CatWrapper catWrapper = catWrapperMono.block();

        //get the cat fact from the response
        String message = catWrapper.getData().get(0);

        //send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public void dataInterpretation(){
    	String dish="dessert";
    	
    }
}
