package XMLHandling;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelfQuery {

    private String query;
    private String[] queryData;
    private int len;
    private ArrayList<String> yearArray;

    public SelfQuery(String query) {
        this.query = query;
        this.queryData = query.split(" ");
        this.len = queryData.length;
    }

    public int getLen() {
        return len;
    }

    public String getTerm() {
        String term;
        Pattern p = Pattern.compile(".*'([^']*)'.*");
        Matcher m1 = p.matcher(query);
        if (m1.matches()) {
            term = m1.group(1);
        } else {
            term = queryData[1];
        }
        return term;
    }

    public String getSingleYear() {
        String year;
        year = queryData[len - 4];
        return year;
    }

    public String getYear() {
        String year;
        year = queryData[len - 6];
        return year;
    }

    public String getSecondYear() {
        String secondYear;
        secondYear = queryData[len - 4];
        return secondYear;
    }

    public int getHits() {
        int hits;
        hits = Integer.parseInt(queryData[len - 2]);
        return hits;
    }

    static void yearUtility(String userQuery, ArrayList<String> yearArray, SelfQuery selfQuery) {
        String year;
        String secondYear;
        if (userQuery.contains("in")) {
            year = selfQuery.getSingleYear();
            yearArray.add(year);
        }

        if (userQuery.contains("from")) {
            year = selfQuery.getYear();
            secondYear = selfQuery.getSecondYear();

            int yearOne = Integer.parseInt(year);
            int yearTwo = Integer.parseInt(secondYear);
            for (int i = 0; i < (yearTwo - yearOne) + 1; i++) {
                int thisYear = yearOne + i;
                yearArray.add(String.valueOf(thisYear));
            }
        }
    }
}
