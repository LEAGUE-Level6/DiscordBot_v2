package org.jointheleague.features.student.grace04.menu;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class MenuItemAPI extends Feature {
    public final String COMMAND = "!menuitem";

    private WebClient webClient;
    private static final String baseUrl = "https://api.spoonacular.com/food/menuItems/search";

    private String apiKey = "0044c0e3c7ec461fa85dd894f7a760d4";

    public MenuItemAPI(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(COMMAND, "Find a menu item");

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
            event.getChannel().sendMessage("Use the command '!menuitem [item]' to find a menu item");
        }
        if(messageArr[0].equals(COMMAND) && messageArr.length > 1 && messageArr[1]!= null){
            String toPrint = findMenuItem(messageArr[1]);
            event.getChannel().sendMessage(toPrint);
        }
    }

    public MenuItemWrapper getMenuItemFromName(String name) {
        Mono<MenuItemWrapper> recipeWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apiKey", apiKey)
                        .queryParam("query", name)
                        .queryParam("sort", "popularity")
                        .build())
                .retrieve()
                .bodyToMono(MenuItemWrapper.class);

        return recipeWrapperMono.block();
    }

    public String findMenuItem(String name){

        MenuItemWrapper rw = getMenuItemFromName(name);

        MenuItem[] menuItems = rw.getMenuItems();

        MenuItem newMenuItem = menuItems[0];

        String title = newMenuItem.getTitle();
        String restaurantChain = newMenuItem.getRestaurantChain();
        String image = newMenuItem.getImage();

        //Create the message
        String message = title + " - " + restaurantChain + "\n" + image + "\n";

        //Send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
