package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.TimeZone;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Reviewer;

/**
 * Displays a table of manuscripts to which the SubprogramChair has been assigned along with their status.
 * Table contains links to all other views the SPC needs.
 * 
 * @author Ryan Tran
 * @author Morgan Blackmore
 * @version 5/27/17
 * 
 *
 */
public class SPCHomeView extends Observable implements ActionListener{
	/**Base panel to display scroll pane**/
	private JPanel myPanel;
	/**Panel to display buttons. **/
	private JPanel myButtonPanel;
	/**Scroll Pane to hold Table**/
	private JScrollPane myManuscriptListScrollPane;

	private JLabel myConfTitleLabel;
	private JButton assignReviewerBtn;
	private JButton submitRecommendationBtn;
	private JButton seeAssignReviewerBtn;
	private JButton viewManuscriptAuthorsBtn;

	/**the manuscript that will be passed ton controller*/
	private Manuscript myCurrentlySelectedManuscript;

	/**the Conference for this SPC */
	private Conference myConference;

	public static final String ASSIGN_REVIEWER = "ASSIGN_REVIEWER";
	public static final String SUBMIT_RECOMMENDATION = "SUBMIT_RECOMMENDATION";
	public static final String SEE_ASSIGNED_REVIEWERS = "SEE_ASSIGNED_REVIEWERS";
	public static final String VIEW_MANUSCRIPT_AUTHORS = "VIEW_MANUSCRIPT_AUTHORS";

	private ArrayList<Manuscript> myManuscriptList;


	/**
	 *  constructor
	 */
	public SPCHomeView(ArrayList<Manuscript> theManuscriptList ,Conference theConference){
		myManuscriptList = theManuscriptList;
		myPanel = new JPanel(new BorderLayout());
		myConference = theConference;
		
		// construct header using conference title and deadline date
    	String confDeadlineDate = convertDateToExplicitFormat(myConference.getManuscriptDeadline());
    	String viewHeaderTitle = "<html><div style='text-align: center;'>"
    		+ "Your Assigned Manuscripts for <br>" + myConference.getConferenceName()
    		+ "<br>Submission Deadline: " + confDeadlineDate + "</html>";
    	myConfTitleLabel = new JLabel(viewHeaderTitle, SwingConstants.CENTER);
		myConfTitleLabel.setFont(new Font("Serif", Font.PLAIN, 26));
        myConfTitleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
    	myPanel.add(myConfTitleLabel, BorderLayout.NORTH);

		/**
		 * Set up and add Main Manuscript List Table
		 */
		JTable table = new JTable(new MyTableModel());
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);

		// This will only allow the user to select the entire row.
		table.setRowSelectionAllowed(true);

		// Set width of title column to be size of longest title
		int width = 0;
		for(int i = 0; i < myManuscriptList.size(); i++) {
			TableCellRenderer renderer = table.getCellRenderer(i, 0);
			Component comp = table.prepareRenderer(renderer, i, 0);
			width = Math.max (comp.getPreferredSize().width, width);
		}

		table.getColumnModel().getColumn(0).setPreferredWidth(width);

		// Setup Event listener for table row selection
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// On table row select set currently selected manuscript field to
				// selected manuscript
				if (!e.getValueIsAdjusting()) {
					Manuscript selectedManu = myManuscriptList.get(table.getSelectedRow());
					if (myConference.getManuscriptDeadline().after(new Date())) {
						assignReviewerBtn.setEnabled(false);	
						assignReviewerBtn.setToolTipText("Cannot assign reviewers until after manuscript submission deadline"
								+ "for the current conference has passed" );
					} else if (myConference.getEligibleReviewers(selectedManu).size() < 1) {
						assignReviewerBtn.setEnabled(false);	
						assignReviewerBtn.setToolTipText("No reviewers available for " + selectedManu.getTitle());
					} else {
						assignReviewerBtn.setEnabled(true);		
					}
					
					if (selectedManu.getReviewerList().size() > 0) {
						seeAssignReviewerBtn.setEnabled(true);
					} else {
						seeAssignReviewerBtn.setEnabled(false);
					}
					
					if (selectedManu.isrecommendationAssigned()) {
						submitRecommendationBtn.setEnabled(false);
						assignReviewerBtn.setEnabled(false);
						assignReviewerBtn.setToolTipText("Recommendation already submitted for " + selectedManu.getTitle());
						submitRecommendationBtn.setToolTipText("Recommendation already submitted for " + selectedManu.getTitle());
					} else if (selectedManu.isEligibleForRecommendation()) {
						submitRecommendationBtn.setEnabled(true); 
						submitRecommendationBtn.setToolTipText(null);
					} else {
						submitRecommendationBtn.setEnabled(false);
						submitRecommendationBtn.setToolTipText("Manuscript must have a minimum of "
								+ Manuscript.getSufficientReviews() + " Reviews submitted to make a Recommendation.");
					}
					

					setCurrentlySelectedManuscript(selectedManu);
					viewManuscriptAuthorsBtn.setEnabled(true);
					setChanged();
					notifyObservers(myCurrentlySelectedManuscript);
				}

			}

		});

		//Create the scroll pane and add the table to it.
		myManuscriptListScrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		myPanel.add(myManuscriptListScrollPane, BorderLayout.CENTER);


		/**
		 * Setup South Button Panel
		 */
		myButtonPanel = new JPanel();
		myButtonPanel.setLayout(new BoxLayout(myButtonPanel, BoxLayout.LINE_AXIS));
		myButtonPanel.setBorder(new EmptyBorder(50, 25, 50, 25));

		this.assignReviewerBtn = new JButton("Assign Reviewer...");
		assignReviewerBtn.setEnabled(false);
		this.assignReviewerBtn.addActionListener(this);
		this.assignReviewerBtn.setActionCommand(this.ASSIGN_REVIEWER);

		this.submitRecommendationBtn = new JButton("Submit Recommendation");
		this.submitRecommendationBtn.setEnabled(false);
		this.submitRecommendationBtn.addActionListener(this);
		this.submitRecommendationBtn.setActionCommand(this.SUBMIT_RECOMMENDATION);

		this.seeAssignReviewerBtn = new JButton("See Assigned Reviewers");
		this.seeAssignReviewerBtn.setEnabled(false);
		this.seeAssignReviewerBtn.addActionListener(this);
		this.seeAssignReviewerBtn.setActionCommand(this.SEE_ASSIGNED_REVIEWERS);
		
		this.viewManuscriptAuthorsBtn = new JButton("View Authors");
		this.viewManuscriptAuthorsBtn.setEnabled(false);
		this.viewManuscriptAuthorsBtn.addActionListener(this);
		this.viewManuscriptAuthorsBtn.setActionCommand(this.VIEW_MANUSCRIPT_AUTHORS);

		
		myButtonPanel.add(assignReviewerBtn);
		myButtonPanel.add(submitRecommendationBtn);
		myButtonPanel.add(seeAssignReviewerBtn);
		myButtonPanel.add(viewManuscriptAuthorsBtn);

		myPanel.add(myButtonPanel, BorderLayout.SOUTH);

		myPanel.setOpaque(true);
	}
	
	
	/**
	 * Returns the main JPanel for this view
	 * 
	 * PreConditions:
	 * 	JPanel must already have been instantiated
	 * @return a JPanel
	 * @author Ryan Tran
	 * @version 5/27/17
	 */
	public JPanel getMyPanel() {
		return this.myPanel;
	}
	
	/**
	 * Returns a string representing a date formatted to include GMT time zone
	 * day, month, and year. 
	 * PreConditions:
	 * 	theDate is non-null
	 * @param theDate
	 * @return A string representing theDate.
	 */
	private String convertDateToExplicitFormat(Date theDate) {
		//formatter = new SimpleDateFormat("dd/MM/yy", currentLocale);
		TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat formatter = 
		  new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'UTC' Z");
		formatter.setTimeZone(utcTimeZone);
		// GMT is equivalent to UTC
		formatter.setTimeZone(TimeZone.getTimeZone("Etc/GMT+12"));

		String result = formatter.format(theDate);
		
		return result;
	}

	/**
	 * Action listener for different buttons.
	 * 
	 * @author Morgan Blackmore
	 * @author Ryan Tran
	 * @version 5/27/17
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
		case ASSIGN_REVIEWER:
			setChanged();
			notifyObservers(myCurrentlySelectedManuscript);
			setChanged();
			notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.ASSIGN_REVIEWERS);
			break;
		case SUBMIT_RECOMMENDATION:
			setChanged();
			notifyObservers(myCurrentlySelectedManuscript);
			setChanged();
			notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.SUBMIT_RECOMMENDATION);
			break;
			
		case SEE_ASSIGNED_REVIEWERS:
			JPanel reviewerListPanel = new JPanel(new GridLayout(0,2));
			JLabel reviewerNameLabel = new JLabel("Reviewer UserName:    ");
			JLabel reviewerScoreLabel = new JLabel("Reviewer Score:");
			reviewerListPanel.add(reviewerNameLabel);
			reviewerListPanel.add(reviewerScoreLabel);
			ArrayList<Reviewer> reviewerList = myCurrentlySelectedManuscript.getReviewerList();
			
			for (int i = 0; i < reviewerList.size(); i++) {
				reviewerListPanel.add(new JLabel(reviewerList.get(i).getUser().getEmail()));
				if (reviewerList.get(i).getReviewerScore(myCurrentlySelectedManuscript) >= 0) { 
					reviewerListPanel.add(new JLabel("" + reviewerList.get(i).getReviewerScore(myCurrentlySelectedManuscript)));
				} else {
					reviewerListPanel.add(new JLabel("No score submitted"));
				}
			}
			
			JOptionPane.showMessageDialog(null, reviewerListPanel);
			break;
		case VIEW_MANUSCRIPT_AUTHORS:
			StringBuilder authorNames = new StringBuilder();
			for (Author author : myCurrentlySelectedManuscript.getAuthors()) {
				authorNames.append(author.getUser().getEmail() + "\n");
			}
			JOptionPane.showMessageDialog(null, authorNames.toString());
			break;
		}
	}
				

	/**
	 * Sets the view's currently selected manuscript field to the passed in manuscript.
	 * 
	 * Preconditions:
	 * 	Manuscript must be non-null
	 * 
	 * @author Ryan Tran
	 * @version 5/27/17
	 * @param theManuscript the manuscript to be set to the currently selected manuscript field
	 */
	private void setCurrentlySelectedManuscript(Manuscript theManuscript) {
		this.myCurrentlySelectedManuscript = theManuscript;
	}
	/**
	 * Abstract table model class that represents the table's data for the manuscript list table.
	 * This model will be added to the JTable inside of the parent JPanel for this class.
	 * This model has to be instantiated with a ManuscriptList.
	 * @author Ryan Tran
	 * @author Morgan Blackmore
	 * @version 5/27/17
	 *
	 */
	class MyTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6900612445865943992L;
		
		private String[] columnNames = {"Title",
				"Num Reviewers Assigned",
				"Num Reviews Submitted",
				"Recommendation Submitted"};
		/**
		 * 2D array of cell data for each row/column
		 */
		private Object[][] data;

		
		/**
		 * Constructor for table model that requires a list of manuscripts to populate
		 * the 2d data array.
		 * 
		 * @author Ryan Tran
		 */
		public MyTableModel() {
			data = generateDataArray(myManuscriptList);
		}

			
		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/
		 * editor for each cell.  If we didn't implement this method,
		 * then the last column would contain text ("true"/"false"),
		 * rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i=0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j=0; j < numCols; j++) {
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}

		/**
		 * This method will generate a 2D array list using the passed in manuscript list
		 * and populate the 2d array to fit a table format of rows and columns
		 * 
		 * @param theManuscriptList
		 * @return The 2d array of relevant SPC values to show in the table
		 */
		private Object[][] generateDataArray(ArrayList<Manuscript> theManuscriptList) {
			Object[][] returnList = new Object[theManuscriptList.size()][4];

			for(int i = 0; i < theManuscriptList.size(); i++) {
				returnList[i][0] =  theManuscriptList.get(i).getTitle();

				returnList[i][1] =  theManuscriptList.get(i).getReviewerList().size();
				returnList[i][2] =  theManuscriptList.get(i).getReviews().size();

				if (theManuscriptList.get(i).isrecommendationAssigned()){
					returnList[i][3] =  "Recommendation submitted";
				} else if (theManuscriptList.get(i).getReviews().size()>=3){
					returnList[i][3] = "Submit Recommendation";
				} else if (theManuscriptList.get(i).getReviews().size()<3){
					returnList[i][3] = "Awaiting Reviews";
				}
			}

			return returnList;
		}
	}



}
