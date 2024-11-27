import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    private JFrame frame;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    JButton open = new JButton("Start - Choose Directory");
    JButton quitButton = new JButton("Quit");

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        frame = new JFrame("Recursive Lister");
        frame.setBounds(100, 100, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);

        scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                java.io.File workingDirectory = new java.io.File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(workingDirectory);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File dir = fileChooser.getSelectedFile();
                    textArea.setText("");
                    listFilesRecursively(dir, "");
                }
            }
        });

        quitButton.addActionListener(e -> System.exit(0));

        frame.getContentPane().add(open, BorderLayout.NORTH);
        frame.getContentPane().add(quitButton, BorderLayout.SOUTH);
    }

    private void listFilesRecursively(File dir, String indent) {
        if (dir == null || !dir.exists()) return;

        if (dir.isFile()) {
            textArea.append(indent + dir.getName() + "\n");
        } else {
            textArea.append(indent + "[" + dir.getName() + "]\n");
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    listFilesRecursively(file, indent + "  ");
                }
            }
        }
    }
}