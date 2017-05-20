package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Observable;
import java.util.Observer;


public class UI_ParentFrame_View extends Observable implements Observer {
	private static final long serialVersionUID = 6981714533618801412L;
	private JFrame myFrame;
	private JPanel cardPanel, jp1, jp2, buttonPanel;
	private JLabel jl1, jl2;
	private JButton btn1, btn2;
	private CardLayout cardLayout;

	
	UI_ParentFrame_View(String theTitle, int theX, int theY) {
		this.myFrame = new JFrame(theTitle);

		myFrame.setSize(theX, theY);
		myFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        cardPanel = new JPanel();
        buttonPanel = new JPanel();

        cardPanel.setLayout(new CardLayout());

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

        myFrame.getContentPane().add(cardPanel, BorderLayout.NORTH);
        myFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        btn1.addActionListener(e -> {
        	System.out.println("btn1 was hit");
        	CardLayout cl = (CardLayout) cardPanel.getLayout();
        	setChanged();
        	notifyObservers("test from ui frame");

        	cl.show(cardPanel, "1");
        });
        
        btn2.addActionListener(e -> {
        	System.out.println("btn2 was hit");
        	CardLayout cl = (CardLayout) cardPanel.getLayout();
        	cl.show(cardPanel, "2");
        });



    }
	
	/**
	 * Returns the class's JFrame
	 * @return JFrame belonging to class
	 */
	public JFrame getJFrame() {
		return this.myFrame;
	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println("UI Parent Frame was notified of update");
		
	}
}
