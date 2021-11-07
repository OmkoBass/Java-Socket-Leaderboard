import Models.Contestant;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Main extends Frame {
    private static final int port = 6666;
    private static final Color green = new Color(0x37B24D);
    private static final Color red = new Color(0xF03E3E);
    private static final Font titleFont = new Font("Serif", Font.PLAIN, 32);
    private static final Font mainFont = new Font("Serif", Font.PLAIN, 18);

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();

        // Main panel on the frame
        JPanel jPanelLeaderboards = new JPanel();
        jPanelLeaderboards.setLayout(new BoxLayout(jPanelLeaderboards, BoxLayout.Y_AXIS));

        // Panel for scrolling
        // It has a scrollPane
        JPanel jPanelScrollPane = new JPanel();
        jPanelScrollPane.setLayout(new BoxLayout(jPanelScrollPane, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(jPanelScrollPane);

        JLabel jLabelLeaderboards = new JLabel("Leaderboards");
        jLabelLeaderboards.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabelLeaderboards.setFont(titleFont);

        JLabel jLabelStart = new JLabel("Connection started.");
        jLabelStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabelStart.setForeground(green);
        jLabelStart.setFont(mainFont);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setTitle("Leaderboards");

        frame.add(jPanelLeaderboards);
        jPanelLeaderboards.add(jLabelLeaderboards);
        jPanelLeaderboards.add(jLabelStart);
        jPanelLeaderboards.add(scrollPane);

        frame.setVisible(true);

        Socket clientSocket = null;

        ObjectInputStream objectInputStream;

        try {
            clientSocket = new Socket("localhost", port);

            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            Contestant contestant;

            // While we have contestants in the input stream
            // it will get them, create a jLabel and
            // add it to the scrollPane
            while ((contestant = (Contestant) objectInputStream.readObject()) != null) {
                System.out.println(contestant);

                JLabel jLabelContestant = new JLabel(contestant.toString());
                jLabelContestant.setAlignmentX(Component.CENTER_ALIGNMENT);
                jLabelContestant.setFont(mainFont);

                jPanelScrollPane.add(jLabelContestant);
                jPanelLeaderboards.revalidate();
            }
        } catch(Exception e) {
            if(clientSocket != null) {
                clientSocket.close();
            }

            e.printStackTrace();
        } finally {
            if(clientSocket != null) {
                clientSocket.close();
            }

            jLabelStart.setText("Connection is cut off.");
            jLabelStart.setFont(mainFont);
            jLabelStart.setForeground(red);
            jPanelLeaderboards.revalidate();
        }
    }
}
