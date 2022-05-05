package org.jointheleague.features.student.grace04.tetra;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TetrAPI extends Feature {
    public final String COMMAND = "!tetr";

    private WebClient webClient;
    private static final String baseUrl = "https://ch.tetr.io/api/users/";

    //private String apiKey = "0044c0e3c7ec461fa85dd894f7a760d4";

    public TetrAPI(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(COMMAND, "Find a tetr.io user");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event){

        String messageContent = event.getMessageContent();
        String[] messageArr = messageContent.split(" ");
        if (messageContent.equals(COMMAND)) {
            event.getChannel().sendMessage("Use the command '!tetr [username]' to find a user");
        }
        if(messageArr[0].equals(COMMAND) && messageArr.length > 1 && messageArr[1]!= null){
            String toPrint = findUser(messageArr[1]);
            event.getChannel().sendMessage(toPrint);
        }
    }

    public String getUserFromName(String name) {
        Mono<String> tetrWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        //.queryParam(":user", name)
                        .path(":user/"+ name)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        return tetrWrapperMono.block();
    }

    public String findUser(String name){
        String tw = getUserFromName(name);
        System.out.println(tw);

        /*System.out.println(tw.getSuccess());
        System.out.println(tw.getError());

        Tetr data = tw.getData();

        User user = data.getUser();

        String _id = user.get_id();
        String username = user.getUsername();
        String role = user.getRole();
        float xp = user.getXp();

        League league = user.getLeague();

        int gamesplayed = league.getGamesplayed();
        int gameswon = league.getGameswon();
        float rating = league.getRating();
        String rank = league.getRank();
        int standing = league.getStanding();
        float percentile = league.getPercentile();

        //Create the message
        String message =
                username + " (" + _id + ") [" + role + "] XP = " + xp + " :\n"
                        + "Games: " + gameswon + "/" + gamesplayed + "\n"
                        + rating + " - " + rank + " - " + standing + " - " + percentile;

        //Send the message

         */
        String message = "";
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
