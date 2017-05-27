package client;

/*
 * TableDemo.java requires no other files.
 */
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import model.Manuscript;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;

public class ManuscriptListTableView extends Observable {
    private boolean DEBUG = true;
    private JPanel myPanel;
 
    public ManuscriptListTableView(ArrayList<Manuscript> theManuscriptList) {
    	myPanel = new JPanel(new GridLayout(1, 0));
 
        JTable table = new JTable(new MyTableModel(theManuscriptList));
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
       
        // Set width of title column to be size of longest title
        int width = 0;
        for(int i = 0; i < theManuscriptList.size(); i++) {
        	TableCellRenderer renderer = table.getCellRenderer(i, 0);
			Component comp = table.prepareRenderer(renderer, i, 0);
			width = Math.max (comp.getPreferredSize().width, width);
        }

		table.getColumnModel().getColumn(0).setPreferredWidth(width);
		

 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        myPanel.add(scrollPane);
        myPanel.setOpaque(true);
    }
    
    public JPanel getMyPanel() {
    	return this.myPanel;
    }
 
    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Title",
                                        "Date Submitted",
                                        "Authors",
                                        "Delete",
                                        "Download"};
        
        private Object[][] data;
        public MyTableModel(ArrayList<Manuscript> theManuscriptList) {
        	data = generateDataArray(theManuscriptList);
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
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
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
        		returnList[i][2] =  theManuscriptList.get(i).getAuthors().get(0).getEmail();
        		returnList[i][3] =  "Delete Btn";
        		returnList[i][4] =  "Download Btn";
        	}
        	
        	return returnList;
        }
    }
}