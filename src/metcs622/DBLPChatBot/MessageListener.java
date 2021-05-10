package metcs622.DBLPChatBot;

import XMLHandling.ParserForChatBot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static metcs622.DBLPChatBot.ChatBotGUI.*;

class sendMessageButtonListener implements ActionListener {

    static String method;

    public void actionPerformed(ActionEvent event) {

        if (messageBox.getText().length() > 1) {
            if (messageBox.getText().equals(".history")) {
                chatBox.append("\nSearch history: \n");
                for (String string : SearchUtil.history) {
                    chatBox.append(string);
                }
                messageBox.setText("");
            } else if (messageBox.getText().equals(".restart")) {
                chatBox.append("<Bot> Please enter a new search method \n");
                method = messageBox.getText().toLowerCase();
                messageBox.setText("");
                searchMethod(method);
            } else if ((    messageBox.getText().toLowerCase().contains("lucene")) ||
                            messageBox.getText().toLowerCase().contains("mysql") ||
                            messageBox.getText().toLowerCase().contains("brute force") ||
                            messageBox.getText().toLowerCase().contains("mongo")) {
                method = messageBox.getText().toLowerCase();
                chatBox.append("<" + username + ">  " + messageBox.getText() + "\n");
                messageBox.setText("");

                searchMethod(method);

                chatBox.append("<Bot> Please enter a 'search term', a year, and an upper bound on the results. " +
                                "Begin your query with the world \"search\" and follow these search guidelines: \n" +
                                "\t- Encase your search term in single quotes ' '\n" +
                                "\t- Choose an integer value to limit the amount of results returned \n" +
                                "\t- Search by particular year. Example: search 'wearable' in 2020 with 100 hits \n" +
                                "\t- Search by year range. Example: search 'gradient descent' from 2015 to 2020 with 75 hits \n");

            } else if (messageBox.getText().contains("search")) {
                String query = messageBox.getText();

                chatBox.append("<" + username + "> " + messageBox.getText() + "\n");
                messageBox.setText("");

                chatBox.append("<Bot> Great! Let me see what I can find for you \n");

                String res = SearchUtil.search(method, query);

                chatBox.append("<Bot> Here's what I found: \n\t");
                chatBox.append(res);
            } else {
                chatBox.append("<" + username + ">  " + messageBox.getText()
                        + "\n");

                chatBox.append("<Bot> Sorry I don't understand. Please follow the search guidelines and try again"
                        + "\n");
                messageBox.setText("");
            }
        }
        messageBox.requestFocusInWindow();
    }

    public void searchMethod(String search) {
        String botResponse = "<Bot> Great! You're searching using " + search + "\n";

        switch (search) {
            case "lucene", "mongo", "mysql", "brute force" -> chatBox.append(botResponse);
        }
    }
}