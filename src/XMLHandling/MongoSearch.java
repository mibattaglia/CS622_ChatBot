package XMLHandling;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class MongoSearch {
    static final MongoClientURI uri = new MongoClientURI(
            "mongodb://michael:4uIP3UT0O14P@cluster0-shard-00-00.xwp2d.mongodb.net:27017," +
                    "cluster0-shard-00-01.xwp2d.mongodb.net:27017,cluster0-shard-00-02.xwp2d.mongodb.net:27017/" +
                    "metcs622?ssl=true&replicaSet=atlas-dfsz14-shard-0&authSource=admin&retryWrites=true&w=majority");

    static final MongoClient mongoClient = new MongoClient(uri);
    static final MongoDatabase database = mongoClient.getDatabase("metcs622");
    static final MongoCollection<Document> collection = database.getCollection("dblpCollection");

    static int hitsPerPage;
    static String term;
    static int intTime;


    public static void main(String[] args) {
        System.out.println(mongoQuery("search 'wearable' in 2020 with 100 hits"));
    }

    public static String mongoQuery (String userQuery) {
        ArrayList<String> yearArray = new ArrayList<>();
        SelfQuery selfQuery = new SelfQuery(userQuery);
        term = selfQuery.getTerm();
        hitsPerPage = selfQuery.getHits();

        SelfQuery.yearUtility(userQuery, yearArray, selfQuery);

        int hits = 0;

        long startTime = System.currentTimeMillis();
        BasicDBObject query = new BasicDBObject();
        query.put("title", new BasicDBObject("$regex", term));
        for (String curYear : yearArray) {

            query.append("year", new BasicDBObject("$regex", curYear));

            Iterator<Document> it = collection.find(query).iterator();

            while (it.hasNext() && hits < hitsPerPage) {
                System.out.println(it.next());
                hits = hits + 1;
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        intTime = (int) totalTime;

        return ("For " + term + " I found: " + hits + " article(s) in " + intTime + " ms \n");
    }
}






















