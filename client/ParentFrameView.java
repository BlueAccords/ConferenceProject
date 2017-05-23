package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;


public class ParentFrameView extends Observable implements Observer {
	private static final long serialVersionUID = 6981714533618801412L;

	private JFrame myFrame;
	private Map<String, JPanel> myPanelList;
	private JPanel cardPanel, jp1, jp2, buttonPanel;
	private JLabel jl1, jl2;
	private JButton btn1, btn2;
	private CardLayout cardLayout;

	
	ParentFrameView(String theTitle, int theX, int theY) {
		this.myFrame = new JFrame(theTitle);

		myFrame.setSize(theX, theY);
		myFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
		myPanelList = new TreeMap<String, JPanel>();
		
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());

        myFrame.getContentPane().add(cardPanel, BorderLayout.CENTER);

//        LoginView loginView = new LoginView();
//        JPanel loginPanel = loginView.getPanel();
//        addPanel(loginPanel, "loginPanel");
        //cardPanel.add(loginPanel, "1");


        /*
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

		*/

    }
	
	/**
	 * Returns the class's JFrame
	 * @return JFrame belonging to class
	 */
	public JFrame getJFrame() {
		return this.myFrame;
	}
	

	/**
	 * This method will add a panel to the JFrame as well ass the panel and its associated name
	 * to the parent view's list of panels which will allow switching to the JPanel.
	 * 
	 * PreConditions:
	 * 	thePanel and thePanelName must be non-null 
	 * @param thePanel The JPanel to add to the parent Jframe's list of panels that it can switch between.
	 * @param thePanelName The String name that will be used to allow switching to the given JPanel
	 */
	public void addPanel(JPanel thePanel, String thePanelName) {
		if(this.myPanelList.containsKey(thePanelName)) {
			throw new IllegalArgumentException("Panel with that panel name has already been added to the JFrame");
		}
		
		this.myPanelList.put(thePanelName, thePanel);
        cardPanel.add(thePanel, thePanelName);
        switchToPanel(thePanelName);
        
        cardPanel.revalidate();
        cardPanel.repaint();
	}
	
	/**
	 * This method will switch the view to the panel that belongs to the passed in panel name.
	 * @param thePanelName
	 */
	public void switchToPanel(String thePanelName) {
		JPanel panelToSwitchTo = this.myPanelList.get(thePanelName);
		
		if(panelToSwitchTo == null) {
			throw new IllegalArgumentException("Panel Name argument does not belong to a valid JPanel");
		}
		
		CardLayout cl = (CardLayout) cardPanel.getLayout();
    	cl.show(cardPanel, thePanelName);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("UI Parent Frame was notified of update");
		if(arg instanceof Integer) {
			setChanged();
			notifyObservers((Integer) arg);
		} else if(arg instanceof String) {
			setChanged();
			notifyObservers((String) arg);
		} else if(o instanceof ConferenceListView) { //Coming from ConferenceListView
			if (arg instanceof String) {
				setChanged();
				notifyObservers((String) arg);
			} else {
				System.out.println("Received a Conference");
				setChanged();
				notifyObservers((Conference) arg);
			}
		}
		
	}
	
}
