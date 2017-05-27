package client;

/*
 * TableDemo.java requires no other files.
 */
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import model.Manuscript;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
public class ManuscriptListTableView extends Observable {
    private boolean DEBUG = false;
    private JPanel myPanel;
    private ArrayList<Manuscript> myCurrentManuscriptList;
 
    public ManuscriptListTableView(ArrayList<Manuscript> theManuscriptList) {
    	myCurrentManuscriptList = theManuscriptList;
    	myPanel = new JPanel(new GridLayout(1, 0));
 
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
				// Only manuscript row selection should set currentManuscript
				if (!e.getValueIsAdjusting()) {
					System.out.println(myCurrentManuscriptList.get(table.getSelectedRow()).getTitle() + " is Selected");
			    }
				
			}
			
		});
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        myPanel.add(scrollPane);
        myPanel.setOpaque(true);
    }
    
    public JPanel getMyPanel() {
    	return this.myPanel;
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
                                        "Delete",
                                        "Download"};
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
        	Object[][] returnList = new Object[theManuscriptList.size()][5];
        	
        	for(int i = 0; i < theManuscriptList.size(); i++) {
        		returnList[i][0] =  theManuscriptList.get(i).getTitle();
        		returnList[i][1] =  theManuscriptList.get(i).getSubmissionDate();
        		returnList[i][2] =  theManuscriptList.get(i).getAuthorEmails().get(0);
        		returnList[i][3] =  "Delete Btn";
        		returnList[i][4] =  "Download Btn";
        	}
        	
        	return returnList;
        }
    }
}