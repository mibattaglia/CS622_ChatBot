package XMLHandling;


import java.sql.*;
import java.time.LocalDate;
import java.time.Month;

public class SQLQuery {

    static int hitsPerPage = 0;
    static String term;
    static int yearOne;
    static int delta;
    static int intTime;

    static final String dbUrl = "jdbc:mysql://localhost:3306/met622";
    static final String password = "password";
    static final String user = "root";
    public static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //conn.close();
        System.out.println(query("search 'a' from 2014 to 2020 with 150 hits"));
    }

    public static String query(String query) throws SQLException {
        Statement stmt = conn.createStatement();

        SelfQuery selfQuery = new SelfQuery(query);
        term = selfQuery.getTerm();
        String year = "";
        String secondYear = "";

        String[] queryData = query.split(" ");
        int len = queryData.length;

        if (query.contains("in")) {
            yearOne = Integer.parseInt(selfQuery.getSingleYear());
            hitsPerPage = Integer.parseInt(queryData[len - 2]);
        }

        if (query.contains("from")) {
            year = selfQuery.getYear();
            secondYear = selfQuery.getSecondYear();
            hitsPerPage = Integer.parseInt(queryData[len - 2]);

            yearOne = Integer.parseInt(year);
            int yearTwo = Integer.parseInt(secondYear);
            delta = yearTwo - yearOne;
        }

        LocalDate startYear = LocalDate.of(yearOne, Month.JANUARY, 1);
        LocalDate endYear = startYear.plusYears(delta);

        java.sql.Date sqlStart = Date.valueOf(startYear);
        java.sql.Date sqlEnd = Date.valueOf(endYear);

        long startTime = System.currentTimeMillis();

        ResultSet rs;
        if (query.contains("in")) {
            rs = stmt.executeQuery("select * from dblpData where title LIKE '%" + term + "%' AND year LIKE '%" + sqlStart + "%'");

        } else {
            rs = stmt.executeQuery("select * from dblpData where title LIKE '%" + term + "%' AND year BETWEEN '" + sqlStart + "' AND '" + sqlEnd + "' ");
        }
        System.out.println("\nDATA FOUND---");

        int hitsCount = 0;
        int i = 0;
        while (rs.next() && i < hitsPerPage) {
            hitsCount = hitsCount + 1;
            System.out.println("Row--- " + rs.getString(1) + " \tArticle Title--- " + rs.getString(2) + " \tYear--- " + rs.getDate(3));
            i++;
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        intTime = (int) totalTime;

        //conn.close();

        return ("For " + term + " I found: " + hitsCount + " article(s) in " + intTime + " ms \n");

    }
}