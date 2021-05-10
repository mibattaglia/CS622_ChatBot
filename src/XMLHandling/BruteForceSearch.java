package XMLHandling;

import java.util.ArrayList;
import java.util.List;

public class BruteForceSearch {

    static int hitsPerPage; // = 75;
    static String term;

    public static void main(String[] args) {
        //System.out.println(bruteForceSearching("search 'wearable' in 2020 with 100 hits"));
        System.out.println(bruteForceSearching("search 'wearable' from 2010 to 2020 with 100 hits"));

    }

    public static String bruteForceSearching(String query) {
        ArrayList<String> yearArray = new ArrayList<>();
        SelfQuery selfQuery = new SelfQuery(query);
        term = selfQuery.getTerm();
        hitsPerPage = selfQuery.getHits();

        SelfQuery.yearUtility(query, yearArray, selfQuery);

        System.out.println(term);
        System.out.println(yearArray);
        System.out.println(hitsPerPage);

        long startTime = System.currentTimeMillis();
        //ParserForChatBot.parser("dblpSmall.xml", "inproceedings");

        int hitCount = 0;

        List<String> listClone = new ArrayList<String>();
        for (String string : ParserForChatBot.myArrayList) {
            //if arraylist.get(i) matches querystr, add it to listClone
            if (string.contains(term)) {
                listClone.add(string);
            }
        }

        for (String s : listClone) {
            if (s.contains(term)) {
                for (String curYear : yearArray) {
                    if (s.contains(curYear)) {
                        if (hitCount < hitsPerPage) {
                            hitCount = hitCount + 1;
                        }
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int intTime = (int) totalTime;
        System.out.println("Time taken: " + intTime + " ms");

        //ParserForChatBot.myArrayList.clear();
        return ("For " + term + " I found: " + hitCount + " article(s) in " + intTime + " ms \n");
    }
}