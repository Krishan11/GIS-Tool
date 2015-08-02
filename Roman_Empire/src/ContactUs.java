

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ContactUs extends JFrame implements ActionListener {
	public ContactUs() {
		JButton ok = new JButton("OK");
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel centerlabel = new JLabel();
		setBounds(200,100,300,300);
		setTitle("Contact Us");
		
		ok.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ae) {
			
			setVisible(false);
			
		}});
		
		
		String s = "<HTML> <H1>Contact Us:</H1><BR>" +
		"Krishan Sharma<BR>" +
		"MS Computer Science,<BR>" +
		"San Diego State University<BR>" +
		"5500 Campanile Dr,<BR>" +
		"San Diego, CA 92115<BR>" +
		"USA<BR><BR>" +
		
		"Email1 :  skrishan111@gmail.com<BR>";
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

