

import javax.swing.*;



import java.awt.event.*;
import java.awt.*;

public class HelpTopics extends JFrame implements ActionListener{
	public HelpTopics() {
		
		JButton ok = new JButton("OK");
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel centerlabel = new JLabel();
		setBounds(200,100,500,500);
		setTitle("Help Topics");
		
		ok.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ae) {
			
			setVisible(false);
			
		}});
		
		String s = "<HTML> <H1>Help Topics:</H1><BR>" +
		"1. Add Layer<BR>" +
		"     Click on Help Icon and then click Add Layer icon in the toolbar.<BR><BR>" +
		"2. Zoom In<BR>" +
		"     Click on Help Icon and then click Zoom In icon in the toolbar.<BR><BR>" +
		"3. Zoom Out<BR>" +
		"     Click on Help Icon and then click Zoom Out icon in the toolbar.<BR><BR>" +
		"4. Zoom To Full Extent<BR>"+
		"     Click on Help Icon and then click Zoom To Full Extent icon in the toolbar.<BR><BR>" +
		"5. PanOneDirection<BR>"+
		"     Click on Help Icon and then click PanOneDirection icon in the toolbar.<BR><BR>" +
		"6. Pan<BR>"+
		"     Click on Help Icon and then click Pan icon in the toolbar.<BR><BR>" +
		"7. Print<BR>" +
		"     Click on Help Icon and then click Print icon in the toolbar.<BR><BR>";
		centerlabel.setHorizontalAlignment(JLabel.CENTER);
		centerlabel.setText(s);
		panel1.add(centerlabel);
		panel2.add(ok);
		getContentPane().add(panel2,BorderLayout.SOUTH);
		getContentPane().add(panel1,BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {this.setVisible(false);
	}
}
