package client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Displays panel for SubprogramChair to submit a recommendation for a manuscript 
 * 
 * @author Morgan Blackmore
 * @version 5/26/17
 *
 */
public class SPCSubmitRecommendationView extends Observable{

	/*
	 * see jfile chooser
	 * see file path
	 * submit button
	 * 
	 * display Success pop-up and return to SPCHomeView
	 */

	/** File to submit as recommendation.*/
	private File myRecommendation;
	/**
	 * default constructor
	 */
	public SPCSubmitRecommendationView(){
		myRecommendation = new File("");
		new JPanel(new GridLayout(7,0));
	}


	/**
	 * Creates a panel to display submit Recommendation options
	 * 
	 * @return JPanel myPanel
	 */

	public JPanel submitRecommendationView() {
		JPanel recViewPanel = new JPanel();
		recViewPanel.setLayout(new BoxLayout(recViewPanel, BoxLayout.Y_AXIS));

		//labels to guide user
		JLabel scorePrompt = new JLabel("What is your recommendation?");
		JLabel filePrompt = new JLabel("Select a file to upload");
		JLabel filePathPrompt = new JLabel("File path: ");
		
		JLabel filePathDisplay = new JLabel("No file selected");

		JSlider scaleSlider = new JSlider(0, 10, 5);
		scaleSlider.setMajorTickSpacing(1);
		scaleSlider.setPaintLabels(true);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setSnapToTicks(true);
		
		JFileChooser fileChooser = new JFileChooser();

		JButton submitBtn = new JButton("Submit");
		submitBtn.setActionCommand("Submit Recommendation");
		submitBtn.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				myRecommendation =  fileChooser.getSelectedFile();
				
				setChanged();
				notifyObservers(myRecommendation);  
				setChanged();
				notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.LIST_MANUSCRIPT_VIEW);

			}  
		});
		
		recViewPanel.add(scorePrompt);
		recViewPanel.add(scaleSlider);
		recViewPanel.add(filePrompt);
		recViewPanel.add(fileChooser);
		JPanel pathPanel = new JPanel(new FlowLayout());
		pathPanel.add(filePathPrompt);
		pathPanel.add(filePathDisplay);
		recViewPanel.add(pathPanel);
		recViewPanel.add(submitBtn);

		return recViewPanel;

	}//end method



}
