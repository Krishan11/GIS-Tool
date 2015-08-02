

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class AttrTab extends JDialog {
	JPanel panel1 = new JPanel();
	com.esri.mo2.map.dpy.Layer layer = RomanEmpire.activeLayer;
	JTable jtable = new JTable(new MyTableModel());
	JScrollPane scroll = new JScrollPane(jtable);
	
	public AttrTab() throws IOException {
		setBounds(70,70,450,350);
		setTitle("Attribute Table");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		scroll.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//      next line necessary for horiz scrollbar to work
		jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn tc = null;
		int numCols = jtable.getColumnCount();
//      jtable.setPreferredScrollableViewportSize(
//      new java.awt.Dimension(440,340));
		for (int j=0;j<numCols;j++) {
			tc = jtable.getColumnModel().getColumn(j);
			tc.setMinWidth(50);
		}
		getContentPane().add(scroll,BorderLayout.CENTER);
	}
	public int getColumnCount() {
		return jtable.getColumnCount() ;
	}
	public int getRowCount() {
		return jtable.getRowCount() ;
	}
	public String getColumnName(int colIndx) {
		return jtable.getColumnName(colIndx) ;
	}
	public Object getValueAt(int row, int col) {
		
		return jtable.getValueAt(row,col);
	}
	
}
