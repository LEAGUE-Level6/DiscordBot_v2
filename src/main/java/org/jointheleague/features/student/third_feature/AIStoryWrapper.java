package org.jointheleague.features.student.third_feature;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class AIStoryWrapper {
	private String story;

    public AIStoryWrapper() {}

    public AIStoryWrapper(String story) {
        this.story = story;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
