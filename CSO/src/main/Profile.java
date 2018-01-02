package main;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Profile extends JFrame {
	
	Profile(int id){
		setVisible(true);
		setSize(1000,700);
   		setLocation(150, 25);
   		setTitle("User Profile");
   		
  		setLayout (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));  
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int id = 0;
		new Profile(id);
	}

}
