package hr.nikola.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProgressBarDemo extends JFrame {

    JTextField fileName;
    JButton open,clear;
    JTextArea displayData;
    JProgressBar progressBar;
    JPanel topPanel,buttonsPanel,filePanel;
    File file;
    DataInputStream dataInputStream;
    
    
    ProgressBarDemo(){
        super("ProgressBar Demo");
        
        displayData = new JTextArea();
        fileName = new JTextField(30);
        
        open = new JButton("Open");
        clear = new JButton("Clear");
        
        progressBar = new JProgressBar();
        
        topPanel = new JPanel();
        buttonsPanel = new JPanel();
        filePanel = new JPanel();
        
        topPanel.setLayout(new GridLayout(2,1));
        filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        filePanel.add(new JLabel("File Name"));
        filePanel.add(fileName);
        
        buttonsPanel.add(open);
        buttonsPanel.add(clear);
        
        topPanel.add(filePanel);
        topPanel.add(buttonsPanel);
        
        getContentPane().add(topPanel,BorderLayout.NORTH);
        getContentPane().add(progressBar,BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(displayData),BorderLayout.CENTER);
        // Adding Listeners
        
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });
        
        clear.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayData.setText("");
                progressBar.setMinimum(0);
                progressBar.setMaximum(0);
            }
        });
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        setSize(400, 300);
        
    }
    
    public void loadFile(){
        LoadingThread thread = new LoadingThread();
        thread.start();
    }
    
    public static void main(String[] args) {
        new ProgressBarDemo();
    }
    
    class LoadingThread extends Thread {
        String text ="";
        int ch;
        int fileSize;
        int completedStatus;
        
        public LoadingThread() {
            try {
                displayData.setText("");
                file = new File(fileName.getText());
                dataInputStream = new DataInputStream(new FileInputStream(file));
                fileSize = (int)file.length();
                progressBar.setMinimum(0);
                progressBar.setMaximum(fileSize);
                completedStatus = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void run() {
            try {
                while ((ch = dataInputStream.read()) != -1) {
                    String text = ((char) ch +"");
                    displayData.append(text);
                    progressBar.setValue(++completedStatus);
                    progressBar.setStringPainted(true);
                    progressBar.setString("Opening...");
                    Thread.sleep(100);
                }
                progressBar.setValue(0);
                progressBar.setString("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
