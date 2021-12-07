package org.jointheleague.features.student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.student.plain_old_java_objects.dog_facts_api.DogFactsApiWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DogFactsApi extends Feature {

    public final String COMMAND = "!dogFacts";

    private WebClient webClient;
    private static final String baseUrl = "https://dogfacts-api.herokuapp.com/api/v1/resources/dogs";

    public DogFactsApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Gets facts about dogs! Usage:\n"
        		+ "!dogFacts [numOfFacts] (without the [])\n"
        		+ "!dogFacts\n"
        		+ "!dogFacts all (note there are a lot of facts, maybe try next command)\n"
        		+ "!dogFacts all txt\n"
        		+ "!dogFacts keyword [word] (without the [])\n"
        		+ "!dogFacts keyword [word],[word2],[word3],[etc] (without the [], note: use comma to seperate search keywords, not spaces)\n");

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	String otherWord = messageContent.replaceAll(" ", "").replace(COMMAND, "").toLowerCase();
        	int numberOfFacts = 2; // not 1
        	boolean txtMode = false;
        	String keyword = "";
        	String kws[] = {""};
        	if (otherWord.contains("keyword")) {
        		keyword = otherWord.replaceAll("keyword", "");
        		kws = keyword.split(",");
        		this.webClient = WebClient
                        .builder()
                        .baseUrl(baseUrl)
                        .build();
        	}
        	else if (otherWord.contains("all")) {
        		this.webClient = WebClient
                        .builder()
                        .baseUrl(baseUrl)
                        .build();
        		if (otherWord.contains("txt")) {
        			txtMode = true;
        		}
        	}
        	else {
        		numberOfFacts = 1;
        		try {
        			numberOfFacts = Integer.parseInt(otherWord);
        		}
        		catch (NumberFormatException e) {} // leave as 1
        		if (numberOfFacts < 1) {
        			numberOfFacts = 1;
        		}
        		this.webClient = WebClient
                    .builder()
                    .baseUrl(baseUrl + "?number=" + numberOfFacts)
                    .build();
        	}
            getDogFact(numberOfFacts, event, txtMode, kws);
        }
    }

    public void getDogFact(int numberOfFacts, MessageCreateEvent event, boolean txtMode, String[] kws) {

        Mono<DogFactsApiWrapper[]> dfMono = webClient.get()
                .retrieve()
                .bodyToMono(DogFactsApiWrapper[].class);

        DogFactsApiWrapper[] dfw = dfMono.block();

        String message;
        
        if (numberOfFacts == 1) {
        	message = dfw[0].getFact() + "\n";
            event.getChannel().sendMessage(message);
        }
        else {
        	if (txtMode) {
        		try {
        			File f = new File("dogFacts.txt");
        			f.createNewFile();
        			
        			FileWriter writer = new FileWriter("dogFacts.txt", false);
        			message = "Dog Facts:\n";
                	for (int i = 0; i < dfw.length; i++) {
                		int factNum = i + 1;
                		message =  message + factNum + ") " + dfw[i].getFact() + "\n";
                	}
                	writer.write(message);
        			writer.close();
        			
        			File dogFactsFile = new File("dogFacts.txt"); 
        			event.getChannel().sendMessage(dogFactsFile);
        			try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						dogFactsFile.delete(); // prioritize deleting the file
					}
        			dogFactsFile.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else {
//        		message = "Dog Facts:\n";
        		if (kws[0].equals("")) {
        			message = "Dog Facts:\n";
        		}
        		else {
        			message = "Dog Facts containing";
        			for (int i = 0; i < kws.length; i++) {
        				message += " '" + kws[i] + "'";
        			}
        			message += ":\n";
        		}
        		int factNumAlso = 1;
                event.getChannel().sendMessage(message);
            	for (int i = 0; i < dfw.length; i++) {
            		if (!kws[0].equals("")) {
            			boolean keywordIn = false;
            			for (int j = 0; j< kws.length; j++) {
            				if (dfw[i].getFact().toLowerCase().contains(kws[j])) {
            					keywordIn = true;
            				}
            			}
            			if (keywordIn) {
            				String line = factNumAlso + ") " + dfw[i].getFact() + "\n";
                            event.getChannel().sendMessage(line);
                            factNumAlso++;
            			}
            		}
            		else {
            			int factNum = i + 1;
                		String line = factNum + ") " + dfw[i].getFact() + "\n";
                        event.getChannel().sendMessage(line);
            		}
            	}
        	}
        }
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

