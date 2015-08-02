

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class RoadMap extends JFrame implements ActionListener {
	public RoadMap() {
		JButton ok = new JButton("OK");
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		JLabel centerlabel = new JLabel();
		JLabel img = new JLabel();
		
		setBounds(50,50,800,600);
		setTitle("Roman Roads Map");
		
		ok.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ae) {
			
			setVisible(false);
			
		}});
		
		
		String s = "<HTML> <H1>Roman Road Map</H1><BR>"; 
		
		ImageIcon imgThisImg = new ImageIcon("Images/road.jpg");
		
		img.setIcon(imgThisImg);
		centerlabel.setHorizontalAlignment(JLabel.CENTER);
		centerlabel.setText(s);
		panel1.add(centerlabel);
		
		panel3.add(img);
		panel2.add(ok);
		
		getContentPane().add(panel2,BorderLayout.SOUTH);
		getContentPane().add(panel3,BorderLayout.CENTER);
		getContentPane().add(panel1,BorderLayout.NORTH);
		
	}
	
	public void actionPerformed(ActionEvent e) {this.setVisible(false);
	}
}

