package client;

import javax.swing.BoxLayout;
import javax.swing.JButton;

/*
 * TableDemo.java requires no other files.
 */
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import model.Manuscript;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Manuscript View that shows a list of an author's manuscripts.
 * Allows following Actions:
 * 	Add Manuscript
 * 	Remove Manuscript
 * 	View More Info(See all authors of manuscript + conference submitted to)
 * @author Ryan Tran
 *
 */
public class AuthorManuscriptListTableView extends Observable implements ActionListener {
    private boolean DEBUG = false;
    private JPanel myPanel;
    private JPanel myButtonPanel;
    private JScrollPane myManuscriptListScrollPane;
    
    private JButton myAddNewManuscriptBtn;
    private JButton myDeleteManuscriptBtn;
    private JButton myViewMoreInfoBtn;
    private JButton myDownloadBtn;
    
    /**
     *  used to disable action buttons until a table row is selected
     */
    private boolean myManuscriptRowIsSelected = false;
    
    /**
     * Used to store the currently selected manuscript from the manuscript table.
     * This is the manuscript that will be passed to the controller on add or delete
     */
    private Manuscript myCurrentlySelectedManuscript;
    
    public static final String ADD_NEW_MANUSCRIPT = "ADD_NEW_MANUSCRIPT";
    public static final String DELETE_MANSUCRIPT = "DELETE_MANUSCRIPT";
    public static final String VIEW_MORE_INFO = "VIEW_MORE_INFO";
    public static final String DOWNLOAD_MANUSCRIPT = "DOWNLOAD_MANUSCRIPT";

    private ArrayList<Manuscript> myCurrentManuscriptList;
 
    public AuthorManuscriptListTableView(ArrayList<Manuscript> theManuscriptList) {
    	myCurrentManuscriptList = theManuscriptList;
    	myPanel = new JPanel(new BorderLayout());
 
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
        for(int i = 0; i < myCurrentManuscriptList.size(); i++) {
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
					// Enable action buttons to be clickable if they are disabled.
					if(!myViewMoreInfoBtn.isEnabled()) {
						//myAddNewManuscriptBtn.setEnabled(true);
						myDeleteManuscriptBtn.setEnabled(true);
						myViewMoreInfoBtn.setEnabled(true);
						myDownloadBtn.setEnabled(true);
					}
					
					Manuscript selectedManu = myCurrentManuscriptList.get(table.getSelectedRow());
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
        
        this.myAddNewManuscriptBtn = new JButton("Add New Manuscript...");
        //this.myAddNewManuscriptBtn.setEnabled(false);
        this.myAddNewManuscriptBtn.addActionListener(this);
        this.myAddNewManuscriptBtn.setActionCommand(this.ADD_NEW_MANUSCRIPT);

        this.myDeleteManuscriptBtn = new JButton("Delete Manuscript");
        this.myDeleteManuscriptBtn.setEnabled(false);
		this.myDeleteManuscriptBtn.addActionListener(this);
		this.myDeleteManuscriptBtn.setActionCommand(this.DELETE_MANSUCRIPT);


        this.myViewMoreInfoBtn = new JButton("View More Info");
        this.myViewMoreInfoBtn.setEnabled(false);
        this.myViewMoreInfoBtn.addActionListener(this);
        this.myViewMoreInfoBtn.setActionCommand(this.VIEW_MORE_INFO);

		this.myDownloadBtn = new JButton("Download");
        this.myDownloadBtn.setEnabled(false);
        this.myDownloadBtn.addActionListener(this);
        this.myDownloadBtn.setActionCommand(this.DOWNLOAD_MANUSCRIPT);

        
        myButtonPanel.add(myAddNewManuscriptBtn);
        myButtonPanel.add(myDeleteManuscriptBtn);
        myButtonPanel.add(myViewMoreInfoBtn);
        myButtonPanel.add(myDownloadBtn);
        
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
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
			case ADD_NEW_MANUSCRIPT:
				System.out.println("ManuscriptListTableView#SubmitManuscriptButton");
				setChanged();
				notifyObservers(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);
				break;
			case DELETE_MANSUCRIPT:
				System.out.println(this.myCurrentlySelectedManuscript.getTitle());
				setChanged();
				notifyObservers(myCurrentlySelectedManuscript);
				break;
			case VIEW_MORE_INFO:
				System.out.println(this.myCurrentlySelectedManuscript.getTitle());
				break;
			case DOWNLOAD_MANUSCRIPT:
				System.out.println(this.myCurrentlySelectedManuscript.getTitle());
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
     * @version 5/27/17
     *
     */
    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Title",
                                        "Date Submitted",
                                        "Authors",
                                        "Num. of Reviewers Assigned"};
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
        	data = generateDataArray(myCurrentManuscriptList);
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
         * Column Headers are: Title, Submission Date, Authors, Delete Btn, Download Btn
         * @param theManuscriptList
         * @return
         */
        private Object[][] generateDataArray(ArrayList<Manuscript> theManuscriptList) {
        	Object[][] returnList = new Object[theManuscriptList.size()][4];
        	
        	for(int i = 0; i < theManuscriptList.size(); i++) {
        		returnList[i][0] =  theManuscriptList.get(i).getTitle();
        		returnList[i][1] =  theManuscriptList.get(i).getSubmissionDate();
        		returnList[i][2] =  theManuscriptList.get(i).getAuthorEmails().get(0);
        		returnList[i][3] =  "# of reviewers assigned";
        	}
        	
        	return returnList;
        }
    }
}