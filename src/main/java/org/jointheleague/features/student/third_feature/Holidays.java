package org.jointheleague.features.student.third_feature;
import java.io.IOException;

import com.sun.tools.doclets.internal.toolkit.Content;
import org.apache.http.client.fluent.*;
import org.omg.CORBA.Request;

public class Holidays {



        public static void main(String[] args) {
            Holidays();
        }

        private static void Holidays() {

            try {
    //https://app.abstractapi.com/api/holidays/tester
                Content content = Request.Get("https://holidays.abstractapi.com/v1/?api_key=736d5028a46c42ada682689ef387e002&country=US&year=2020&month=12&day=25")

                        .execute().returnContent();

                System.out.println(content);
            }
            catch (IOException error) { System.out.println(error); }
        }
    }
}
