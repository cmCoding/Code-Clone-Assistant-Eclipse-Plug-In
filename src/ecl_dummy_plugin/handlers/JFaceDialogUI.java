package ecl_dummy_plugin.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


public class JFaceDialogUI extends Dialog {
	private ArrayList<Clone> clones;
	private ArrayList<CloneFamily> cloneFamilies;
	private Text txtenterDatabaseFilepath;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public JFaceDialogUI(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(false);
		setShellStyle(SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label text_label = new Label(container, SWT.NONE);
		text_label.setBounds(10, 10, 113, 16);
		text_label.setText("Database Filepath: ");
		
		
		Tree tree = new Tree(container, SWT.BORDER);
		tree.setBounds(10, 40, 400, 230);
		tree.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setWidth(400);
		treeColumn.setText("Family:");
		Text dispText = new Text(container, SWT.BORDER | SWT.WRAP);
		dispText.setForeground(SWTResourceManager.getColor(255, 255, 255));
		dispText.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		dispText.setEditable(false);
		dispText.setLocation(416, 40);
		dispText.setSize(358, 230);
		dispText.setText("Welcome to Code Clone Assistant!\r\nA tree depiction of the code clones in your repository can be seen to the left.");
        
		Button button = new Button(container, SWT.PUSH);
		button.setEnabled(false);
		button.setToolTipText("Display selected tree item details");
		button.setBounds(10, 276, 87, 21);
        button.setText("View Details");
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // This is just here as filler functionality.
            	TreeItem selectedItem = tree.getSelection()[0];
            	
            	// Consider making this its own method: void printSummary(TreeItem selectedItem){...}
            	int found_int = 0;
            	if (selectedItem.getText().contains("Clone Family: ")) {
            		found_int = Integer.parseInt(selectedItem.getText().replace("Clone Family: ", ""));
            		dispText.setText(cloneFamilies.get(found_int-1).toString());
            		}
            	else {
            		found_int = Integer.parseInt(selectedItem.getText().replace("Clone #", ""));
            		dispText.setText(clones.get(found_int).toString());
            	}
            	
                
            }
        });
        
        Button btnHighlight = new Button(container, SWT.NONE);
        btnHighlight.setEnabled(false);
        btnHighlight.setToolTipText("Highlight the selected clone");
        btnHighlight.setText("Highlight");
        btnHighlight.setBounds(103, 276, 87, 21);
        btnHighlight.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                
            	dispText.setText("Highlight feature later...");
            	}
        });
        
        txtenterDatabaseFilepath = new Text(container, SWT.BORDER);
        txtenterDatabaseFilepath.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
        txtenterDatabaseFilepath.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
        txtenterDatabaseFilepath.setBounds(131, 7, 400, 21);
        
        Button btnLoadDatabase = new Button(container, SWT.NONE);        
        btnLoadDatabase.setBounds(548, 5, 113, 25);
        btnLoadDatabase.setText("Load Database");
        btnLoadDatabase.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		Connect obj = new Connect();
        		Connection dbCon = obj.connect(txtenterDatabaseFilepath.getText());        		
        		try {        		
        			//CLONE TABLE        		
        			//Individual Clone Records Query stored in result set
        			ResultSet recordsClones = obj.getRecords(dbCon, "SELECT * FROM clone");
        			//Store the result set in an arraylist of clone objects
        			clones = obj.placeClones(recordsClones);        		
        		
        			//CLONE FAMILY TABLE
        			ResultSet recordsCloneFamily = obj.getRecords(dbCon, "SELECT * FROM clone__family");
        			cloneFamilies = obj.placeCloneFamily(recordsCloneFamily);
        		
        			dbCon.close();
 
        			// Populate Tree
            		populateTree(tree);
            		dispText.setText("Clone tree successfully loaded.\r\nSelect an entry on the tree, then press the \"View Details\" button to view its attributes.");
            		// Enable buttons
            		 btnHighlight.setEnabled(true);
            		 button.setEnabled(true);
        		
        		} catch (Exception er) {
        			er.printStackTrace();
        			dispText.setText("Failed to locate database file. Please ensure the filepath is correct.");        			
        		}
        		
        	}
        });
        
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, "Close", true);
	}
	
	@Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Dummy Plug-In");
    }
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(800, 400);
	}
	
	protected void populateTree(Tree tree) {
		// First clear the tree.
		tree.removeAll();
		//Populating Dialog
		TreeItem [] familyColumn = new TreeItem[cloneFamilies.size()];
		
		for (int i = 0; i < cloneFamilies.size(); i++) {
			familyColumn[i] = new TreeItem(tree,SWT.NONE);
			familyColumn[i].setText("Clone Family: " + cloneFamilies.get(i).getID());
		}
		
		TreeItem [] cloneColumn = new TreeItem[clones.size()];

		Clone tempClone = null;
		for (int j = 0; j < clones.size(); j++){
			tempClone = clones.get(j);
			cloneColumn[j] = new TreeItem(familyColumn[tempClone.getClassId()-1], SWT.NONE);
			cloneColumn[j].setText("Clone #" + (j+1));
						
		}
	}
}
