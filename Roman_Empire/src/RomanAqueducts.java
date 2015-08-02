

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class RomanAqueducts extends JFrame implements ActionListener {
	public RomanAqueducts() {
		JButton ok = new JButton("OK");
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		JLabel centerlabel = new JLabel();
		JLabel img = new JLabel();
		
		setBounds(50,50,800,600);
		setTitle("Roman Aqueducts");
		
		ok.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ae) {
			
			setVisible(false);
			
		}});
		
		
		String s = "<HTML> <H1>Roman Aqueducts:</H1><BR>" +
		"Along with roads, aqueducts are the other engineering marvel that the Romans are<BR>"
		+ "the most famous for. The thing about aqueducts is that they're long.One of the<BR>"
		+ "the difficulties of watering a large city is that once the city gets certaithe<BR>"
		+ "size, you really can't get clean water from anywhere near it. And though Romean<BR>"
		+ "sits on the Tiber, the river itself was polluted by another Roman engineering t<BR>"
		+ "achievement, their sewer system. To solve the problem, Roman engineers built   <BR>"
		+ "aqueducts -- networks of underground pipes, above-ground water lines and elegan<BR>"
		+ "bridges, all designed to channel water into the city from the surrounding areas";
		ImageIcon imgThisImg = new ImageIcon("Images/raqueduct.jpg");
		
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

