package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import client.AuthorManuscriptListTableView.MyTableModel;
import model.Conference;
import model.Manuscript;
import model.Reviewer;
import model.User;

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

	private JButton assignReviewerBtn;
	private JButton submitRecommendationBtn;


	/** used to disable action buttons until a table row is selected.**/
	private boolean isManuscriptSelected = false;

	/**the manuscript that will be passed ton controller*/
	private Manuscript myCurrentlySelectedManuscript;

	/**the Conference for this SPC */
	private Conference myConference;

	public static final String ASSIGN_REVIEWER = "ASSIGN_REVIEWER";
	public static final String SUBMIT_RECOMMENDATION = "SUBMIT_RECOMMENDATION";

	private ArrayList<Manuscript> myManuscriptList;


	/**
	 *  constructor
	 */
	public SPCHomeView(ArrayList<Manuscript> theManuscriptList ,Conference theConference){
		myManuscriptList = theManuscriptList;
		myPanel = new JPanel(new BorderLayout());
		myConference = theConference;


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

					if (myConference.getManuscriptDeadline().before(new Date())) {
						assignReviewerBtn.setEnabled(false);	
						assignReviewerBtn.setToolTipText("DeadLine for " + selectedManu.getTitle() + "has not expired");
					} else {
						assignReviewerBtn.setEnabled(true);		
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

		// add buttons to btn panel
		// by default buttons are disabled until a row is selected
		EmptyBorder btnBorders = new EmptyBorder(10, 5, 10, 5);

		this.assignReviewerBtn = new JButton("Assign Reviewer...");
		assignReviewerBtn.setEnabled(false);
		this.assignReviewerBtn.addActionListener(this);
		this.assignReviewerBtn.setActionCommand(this.ASSIGN_REVIEWER);

		this.submitRecommendationBtn = new JButton("Submit Recommendation");
		this.submitRecommendationBtn.setEnabled(false);
		this.submitRecommendationBtn.addActionListener(this);
		this.submitRecommendationBtn.setActionCommand(this.SUBMIT_RECOMMENDATION);

		myButtonPanel.add(assignReviewerBtn);
		myButtonPanel.add(submitRecommendationBtn);


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
			//				System.out.println("ManuscriptListTableView#SubmitManuscriptButton");
			setChanged();
			notifyObservers(myCurrentlySelectedManuscript);
			setChanged();
			notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.ASSIGN_REVIEWERS);
			break;
		case SUBMIT_RECOMMENDATION:
			//				System.out.println(this.myCurrentlySelectedManuscript.getTitle());
			setChanged();
			notifyObservers(myCurrentlySelectedManuscript);
			setChanged();
			notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.SUBMIT_RECOMMENDATION);
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
		 * @return
		 */
		private Object[][] generateDataArray(ArrayList<Manuscript> theManuscriptList) {
			Object[][] returnList = new Object[theManuscriptList.size()][4];

			for(int i = 0; i < theManuscriptList.size(); i++) {
				returnList[i][0] =  theManuscriptList.get(i).getTitle();
				//if ManDL is after today, it's still open and no action can be taken
				//				if (myConference.getManuscriptDeadline().after(new Date())){
				//					returnList[i][1] = "Manuscript submission is still open";
				//					returnList[i][2] = "Manuscript submission is still open";
				//					returnList[i][3] = "Manuscript submission is still open";
				//					
				//				} else {
				returnList[i][1] =  theManuscriptList.get(i).getReviewerList().size();
				returnList[i][2] =  theManuscriptList.get(i).getReviews().size();

				if (theManuscriptList.get(i).isrecommendationAssigned()){
					returnList[i][3] =  "Recommendation submitted";
				} else if (theManuscriptList.get(i).getReviews().size()>=3){
					returnList[i][3] = "Submit Recommendation";
				} else if (theManuscriptList.get(i).getReviews().size()<3){
					returnList[i][3] = "Awaiting Reviews";
				}

				//				returnList[i][4] = new String ("" +myConference.getManuscriptDeadline());
				//				}

			}

			return returnList;
		}
	}



}
