package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI_ParentFrame_View extends JFrame {
	private static final long serialVersionUID = 6981714533618801412L;

	private JPanel cardPanel, jp1, jp2, buttonPanel;
	private JLabel jl1, jl2;
	private JButton btn1, btn2;
	private CardLayout cardLayout;

	
	UI_ParentFrame_View(String theTitle, int theX, int theY) {
		super(theTitle);
		
		this.setSize(theX, theY);
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        cardPanel = new JPanel();
        buttonPanel = new JPanel();

        cardPanel.setLayout(cardLayout);

        jp1 = new JPanel();
        jp2 = new JPanel();

        jl1 = new JLabel("Card 1");
        jl2 = new JLabel("Card 2");

        jp1.add(jl1);
        jp2.add(jl2);

        cardPanel.add(jp1, "1");
        cardPanel.add(jp2, "2");

        btn1 = new JButton("Show Card 1");
        btn2 = new JButton("Show Card 2");

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);

        getContentPane().add(cardPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        btn1.addActionListener(e -> {
        	System.out.println("btn1 was hit");
        });


    }
}
