/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package quotes;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;

public class App {

    public static void main(String[] args) throws Exception {

        System.out.println(getQuoteFromWeb());

        Quote newQuote = new Quote("Richard", "hello");
    }

    protected static String getRandomObject(Object[] objArray) {

        int size = objArray.length;

        int randomIndex = (int) Math.floor(Math.random() * size);

        return objArray[randomIndex].toString();
    }

    private static String getQuoteFromWeb() throws Exception {

        try {
            URL url = new URL("http://swquotesapi.digitaljedi.dk/api/SWQuote/RandomStarWarsQuote");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

            Gson gson = new Gson();

            SWQuote newQuote = gson.fromJson(reader, SWQuote.class);

            return newQuote.toString();

        } catch (IOException e) {
            System.out.println("Web connection failed, returning quote from local database.");
            return getRandomObject(
                    jsonToQuotes(
                            getQuoteData()
                    )
            );
        }
    }

    protected static Object[] jsonToQuotes(String string) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.fromJson(string, Quote[].class);
    }

    public static class Quote {
        private String author;
        private String text;

        public Quote(String author, String text) {
            this.author = author;
            this.text = text;
        }

        public String toString() {
            return String.format("%s: %s", this.author, this.text);
        }

    }

    public static class SWQuote {
        private String starWarsQuote;

        public SWQuote (String starWarsQuote) {
            this.starWarsQuote = starWarsQuote;
        }

        public String toString() {return String.format("%s", this.starWarsQuote);}

    }



    protected static String getQuoteData() throws Exception {
        File file = new File("./src/main/resources/recentquotes.json");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        StringBuilder outputString = new StringBuilder();
        while ((st = br.readLine()) != null) {
            outputString.append(st);
        }
        return outputString.toString();
    }
}
