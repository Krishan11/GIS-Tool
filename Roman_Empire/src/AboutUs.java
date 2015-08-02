

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class AboutUs extends JFrame implements ActionListener {
	
	public AboutUs() {
		JButton ok = new JButton("OK");
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel centerlabel = new JLabel();
		setBounds(200,100,300,300);
		setTitle("About Us");
		
		ok.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ae) {
			
			setVisible(false);
			
		}});
		
		String s = "<HTML> <H1>About Us:</H1><BR>" +
		"History Of Roman Empire<BR>" +
		"Copyright(c) 2012-2013 <BR>" +
		"Version 1.1<BR><BR>" +
		"Created By: Krishan Sharma,<BR>" +
		"San Diego State University<BR><BR>" +
		"All Rights Reserved.<BR>" ;
		
		
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

