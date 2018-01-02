package main;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.ResultSet;



public class HomePage extends JFrame implements ActionListener{
		JButton b1,b2;
		JTextField username; JPasswordField password;
		JLabel u,p,t;
		String s1,s2;
		 ConnectionSet  cn = new ConnectionSet();
		
		HomePage(){
			setTitle("Login");
			setSize(680,650);
			setLocation(300, 25);
			setVisible(true);
			setLayout(null);
			Container c = getContentPane();
			//c.setBackground(Color.red);
			t= new JLabel("Welcome to Case Study Organizer");
			t.setFont(new Font("serif", Font.BOLD+Font.ITALIC, 30));
			u = new JLabel("Username");
			p = new JLabel("Password");
			u.setBounds(100,170,120,40);
			p.setBounds(100,280,120,40);
			u.setFont(new Font("Courier", Font.BOLD, 25));
			p.setFont(new Font("Courier", Font.BOLD, 25));
			t.setBounds(110,0,600,80);
			username= new JTextField();
			username.setBounds(320,170,200,40);
			password = new JPasswordField();
			password.setEchoChar('*');
			password.setBounds(320,280,200,40);
			b1 = new JButton("Login");
			b2 = new JButton("Register");
			b1.setBounds(160,400,120,50);
			b2.setBounds(380,400,120,50);
			username.addActionListener(this);
			password.addActionListener(this);
			b1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					System.out.println(username.getText());
					ResultSet rsu = displayById(username.getText());
					try {
						if(rsu.next()){
						
								System.out.println(password.getText() +" "+rsu.getString(5));
							if(password.getText().equals(rsu.getString(5))){
								new UserHome(rsu.getInt(1),rsu.getString(2));
								setVisible(false);
							}else{
								JOptionPane.showMessageDialog(null,"Incorrect Username or Password");
							}
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			b2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					{ 
						new Register();
						setVisible(false);
						
					}
					
				}
			});
			c.add(u);
			c.add(p);
			c.add(t);
			c.add(b1);
			c.add(b2);
			c.add(username);
			c.add(password);
		}
		
		public ResultSet displayById(String i){
			try {
				if(cn.ConnectionSetup()){
			ResultSet rs=	cn.queryRetrive("select * from user where username='"+i+"'");
					return (rs);
				}
			} catch (Exception e) {
				System.out.print(e);
			}
			return null;
		}


		public void actionPerformed(ActionEvent ae){
			s1=username.getText();
			s2=password.getText();
			
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HomePage l = new HomePage();
		 l.setVisible(true);
		 l.setTitle("Login Form");
		 l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 l.setBounds(300,50,700,600); 
	}

}
