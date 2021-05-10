package metcs622.DBLPChatBot;

import XMLHandling.ParserForChatBot;
import XMLHandling.SQLQuery;

import java.sql.SQLException;
import java.util.ArrayList;

import static XMLHandling.BruteForceSearch.bruteForceSearching;
import static XMLHandling.LuceneSearch.luceneIndexer;
import static XMLHandling.MongoSearch.mongoQuery;

public class SearchUtil {

    static String result;
    static ArrayList<String> history = new ArrayList<>();

    public static String search(String method, String query) {

        //ParserForChatBot.parser("dblpSmall.xml", "inproceedings");

        switch (method) {
            case "brute force" -> result = bruteForceSearching(query);
            case "lucene" -> result = luceneIndexer(query);
            case "mongo" -> result = mongoQuery(query);
            case "mysql" -> {
                try {
                    result = SQLQuery.query(query);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        String[] resultData = result.split(" ");
        String historyResult = (method + ": " + resultData[1].toUpperCase() + ", " + resultData[4] + " " + resultData[5] + " in " + resultData[7] + " ms \n");
        history.add(historyResult);

        return result;
    }
}
