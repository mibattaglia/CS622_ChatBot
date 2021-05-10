package metcs622.DBLPChatBot;

import XMLHandling.ParserForChatBot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotGUI {

        static String appName = "DBLP Chatbot";
        ChatBotGUI mainGUI;
        static JFrame newFrame = new JFrame(appName);
        static JButton sendMessage;
        static JTextField messageBox;
        static JTextArea chatBox;
        static JTextField usernameChooser;
        static JFrame preFrame;
        static String username;

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ChatBotGUI mainGUI = new ChatBotGUI();
                    mainGUI.preDisplay();
                }
            });
        }

        public void preDisplay() {
            newFrame.setVisible(false);
            preFrame = new JFrame(appName);
            usernameChooser = new JTextField(15);
            JLabel chooseUsernameLabel = new JLabel("Your name:");
            JButton enterServer = new JButton("Continue");
            enterServer.addActionListener(new enterServerButtonListener());
            JPanel prePanel = new JPanel(new GridBagLayout());
            preFrame.getRootPane().setDefaultButton(enterServer);

            GridBagConstraints preRight = new GridBagConstraints();
            preRight.insets = new Insets(0, 0, 0, 10);
            preRight.anchor = GridBagConstraints.EAST;
            GridBagConstraints preLeft = new GridBagConstraints();
            preLeft.anchor = GridBagConstraints.WEST;
            preLeft.insets = new Insets(0, 10, 0, 10);
            preRight.fill = GridBagConstraints.HORIZONTAL;
            preRight.gridwidth = GridBagConstraints.REMAINDER;

            prePanel.add(chooseUsernameLabel, preLeft);
            prePanel.add(usernameChooser, preRight);
            preFrame.add(BorderLayout.CENTER, prePanel);
            preFrame.add(BorderLayout.SOUTH, enterServer);
            preFrame.setSize(300, 300);
            preFrame.setVisible(true);

        }

        public void display() {
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JPanel southPanel = new JPanel();
            southPanel.setBackground(Color.darkGray);
            southPanel.setLayout(new GridBagLayout());

            messageBox = new JTextField(30);
            messageBox.requestFocusInWindow();

            sendMessage = new JButton("Send Message");
            sendMessage.addActionListener(new sendMessageButtonListener());
            newFrame.getRootPane().setDefaultButton(sendMessage);

            chatBox = new JTextArea();
            chatBox.setEditable(false);
            chatBox.setLineWrap(true);

            mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

            GridBagConstraints left = new GridBagConstraints();
            left.anchor = GridBagConstraints.LINE_START;
            left.fill = GridBagConstraints.HORIZONTAL;
            left.weightx = 512.0D;
            left.weighty = 1.0D;

            GridBagConstraints right = new GridBagConstraints();
            right.insets = new Insets(0, 10, 0, 0);
            right.anchor = GridBagConstraints.LINE_END;
            right.fill = GridBagConstraints.NONE;
            right.weightx = 1.0D;
            right.weighty = 1.0D;

            southPanel.add(messageBox, left);
            southPanel.add(sendMessage, right);

            mainPanel.add(BorderLayout.SOUTH, southPanel);

            newFrame.add(mainPanel);
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setSize(470, 300);
            newFrame.setVisible(true);

            chatBox.append("<Bot> Hello " + username + " and welcome to the DBLP chatbot! Please choose a search method from the following options: \n" +
                    "\t-MongoDB \n" +
                    "\t-MySQL \n" +
                    "\t-Lucene \n" +
                    "\t-Brute force \n");

        }

        class enterServerButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                ParserForChatBot.parser("dblpSmall.xml", "inproceedings");
                username = usernameChooser.getText();
                if (username.length() < 1) {
                    System.out.println("No!");
                } else {
                    preFrame.setVisible(false);
                    display();
                }
            }
        }
    }

