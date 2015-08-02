

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.esri.mo2.ui.bean.Map;

public class AddLyrDialog extends JDialog {
	Map map;
	ActionListener lis;
	JButton ok = new JButton("OK");
	JButton cancel = new JButton("Cancel");
	JPanel panel1 = new JPanel();
	com.esri.mo2.ui.bean.CustomDatasetEditor cus = new com.esri.mo2.ui.bean.
	CustomDatasetEditor();
	AddLyrDialog() throws IOException {
		setBounds(50,50,520,430);
		setTitle("Select a theme/layer");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		
		lis = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Object source = ae.getSource();
				if (source == cancel)
					setVisible(false);
				else {
					try {
						setVisible(false);
						map.getLayerset().addLayer(cus.getLayer());
						map.redraw();
						if (RomanEmpire.stb.getSelectedLayers() != null)
							RomanEmpire.promoteitem.setEnabled(true);
						
					} catch(IOException e){}
				}
			}
		};
		ok.addActionListener(lis);
		cancel.addActionListener(lis);
		getContentPane().add(cus,BorderLayout.CENTER);
		panel1.add(ok);
		panel1.add(cancel);
		getContentPane().add(panel1,BorderLayout.SOUTH);
	}
	public void setMap(com.esri.mo2.ui.bean.Map map1){
		map = map1;
	}
}
