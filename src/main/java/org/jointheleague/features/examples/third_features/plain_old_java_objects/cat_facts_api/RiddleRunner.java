package org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api;

import org.jointheleague.features.examples.third_features.Result;
import org.jointheleague.features.examples.third_features.RiddleApi;
 
public class RiddleRunner {
	 public static void main(String[] args) {
	        RiddleApi r = new RiddleApi(null);
	       Result riddle = r.getRiddle("daniel");
	       System.out.println(riddle);
	    }

}
