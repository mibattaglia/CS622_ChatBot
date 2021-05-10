package XMLHandling;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;

public class LuceneSearch {

    static int hitsPerPage = 0;
    static int intTime;
    static String term;

    public static void main(String[] args){
        //execute search based on querystr and hitsPerPage
        //String result = luceneIndexer("search 'wearable' from 2015 to 2020 with 100 hits");
        String result = luceneIndexer("search 'wearable' from 2014 to 2020 with 150 hits");
        System.out.println(result);
    }

    public static String luceneIndexer(String query){
        try {
            ArrayList<String> yearArray = new ArrayList<>();
            SelfQuery selfQuery = new SelfQuery(query);
            term = selfQuery.getTerm();
            hitsPerPage = selfQuery.getHits();

            SelfQuery.yearUtility(query, yearArray, selfQuery);

            //ParserForChatBot.parser("dblpSmall.xml", "inproceedings");
            StandardAnalyzer analyzer = new StandardAnalyzer();

            Directory index = new ByteBuffersDirectory(); //index creation

            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter w = new IndexWriter(index, config);

            //add each element of arraylist to Lucene Index
            for (String temp : ParserForChatBot.myArrayList) {
                for (String s : yearArray) {
                    if (temp.contains(s)) {
                        addDoc(w, temp);
                    }
                }
            }
            w.close();

            // the QueryParer arg specifies the default field to use
            // when no field is explicitly specified in the query.
            Query q = new QueryParser("data", analyzer).parse(term);

            // 3. search
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            // 4. display results
            int hitCount = 0;
            System.out.println("Found " + hits.length + " hits.");
            long startTime = System.currentTimeMillis();
            for(int i=0; i<hits.length; ++i) {
                int docId = hits[i].doc;
                org.apache.lucene.document.Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". DATA FOUND--- " + d.get("data"));
            }

            reader.close(); //reader closed when document no longer needs to be accessed
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            intTime = (int) totalTime;

            //ParserForChatBot.myArrayList.clear();
            return ("For " + term + " I found: " + hits.length + " article(s) in " + intTime + " ms \n");
        } catch(IOException | ParseException ex){
            ex.printStackTrace();
        }
        return "Error";
    }

    private static void addDoc(IndexWriter w, String nodeName) throws IOException {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new TextField("data", nodeName, Field.Store.YES));
        w.addDocument(doc);
    }
}
