package XMLHandling;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class ParserForChatBot {
    public static ArrayList<String> myArrayList = new ArrayList<>();

    public static void parser(String file, String nodeName) {
        try {
            //create document builder and parse the xml file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName(nodeName);
            System.out.println("Nodes found: " + nList.getLength());

            //creates a node list from the xml file with each child node from the main parent node
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) { //if node is element_node, convert entire node to element so we can read the content
                    Element tElement = (Element) nNode;
                    String title = tElement.getElementsByTagName("title").item(0).getTextContent(); //get content of title node
                    String year = tElement.getElementsByTagName("year").item(0).getTextContent(); //get content of year node
                    myArrayList.add("Title: " + title + "; Year: " + year); //add title and year text to the arraylist

                }
            }
        } catch (NullPointerException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
