package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener{
	 ConnectionSet  cn = new ConnectionSet();
	 JButton SUBMIT, back;
	  JPanel panel;
	  JLabel label1,label2, repass , header,name , register;
	  final JTextField  text1,text2,text3 , nameF , reg;
	   Register()
	   {	setVisible(true);
	   		setSize(680,650);
	   		setLocation(300, 25);
		 
		   header = new JLabel();
		   header.setText("User Registration Page \n");
		   header.setFont(new Font("serif", Font.BOLD+Font.ITALIC, 30));
		   header.setBounds(180,0,600,80);
		
		   name = new JLabel();
		   name.setText(" Name");
		   nameF= new JTextField(15);
		   name.setBounds(120,100,200,40);
		   nameF.setBounds(320,100,200,40);
		   
		   register = new JLabel();
		   register.setText(" Email");
		   reg= new JTextField(15);
		   register.setBounds(120,150,200,40);
		   reg.setBounds(320,150,200,40);
		   
	   label1 = new JLabel();
	   label1.setText("Username:");
	   text1 = new JTextField(15);
	   
	   label1.setBounds(120,200,200,40);
	   text1.setBounds(320,200,200,40);
	 
	   label2 = new JLabel();
	   label2.setText("Password:");
	   text2 = new JPasswordField(15);
	   
	   label2.setBounds(120,250,200,40);
	   text2.setBounds(320,250,200,40);
	   
	   repass = new JLabel();
	   repass.setText("Re-Password:");
	   text3 = new JPasswordField(15);
	   repass.setBounds(120,300,200,40);
	   text3.setBounds(320,300,200,40);
	   
	   text3.addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent key) {
			// TODO Auto-generated method stub
			String a;
			if(text3.getText().length() >5){
				if(text3.getText().equals(text2.getText())){
					SUBMIT.setEnabled(true);
				}
			}else {
				SUBMIT.setEnabled(false);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	  
	   SUBMIT=new JButton("SUBMIT");
	   SUBMIT.setEnabled(false);
	   SUBMIT.setBounds(110,400,200,40);
	   back=new JButton("Back");
	   back.setBounds(330,400,200,40);
	   
	   back.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new HomePage();
			setVisible(false);
		}
	});
	 
	   
	   panel=new JPanel();
	   panel.setLayout(null);
	   panel.add(header);
	   panel.add(name);
	   panel.add(nameF);
	   panel.add(label1);
	   panel.add(text1);
	   panel.add(label2);
	   panel.add(text2);
	   panel.add(repass);
	   panel.add(text3);
	   panel.add(SUBMIT);
	   panel.add(register);
	   panel.add(reg);
	   panel.add(back);
	   add(panel,BorderLayout.CENTER);
	   SUBMIT.addActionListener(this);
	   setTitle("Registration Form");
	   }
	  public void actionPerformed(ActionEvent ae)
	   {
	   String username=text1.getText();
	   String pass=text2.getText();
	   String name = nameF.getText();
	   String repass = text3.getText();
	   String email = reg.getText();
	   if(SUBMIT.isEnabled()){
		   try {
			registerAction(name,pass,username,email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   //NextPage page=new NextPage();
	  // page.setVisible(true);
	 //  JLabel label = new JLabel("Welcome:"+value1);
	  // page.getContentPane().add(label);
	   }
	   else{
	   System.out.println("enter the valid username and password");
	   JOptionPane.showMessageDialog(this,"Incorrect login or password",
	   "Error",JOptionPane.ERROR_MESSAGE);
	   }
	 }
	  
	  public boolean registerAction(String name , String pass, String username,String email) throws SQLException{
		  String q ="insert into user (username,email,name,pass) values ('"+username+"','"+email+"','"+name+"','"+pass+"')";
		  if(cn.ConnectionSetup())
			{
			  if(!userExit(username))
			  {
				if(cn.queryUpdate(q)){
					System.out.println("Successfully Submit");
					new HomePage();
					setVisible(false);
				return true;
			}
			  }else{
				  JOptionPane.showMessageDialog(this,"Username Already Exist",
						   "Error",JOptionPane.ERROR_MESSAGE);  
			  }
				}
		return false;
	  }
	  
	  public boolean userExit(String name) throws SQLException{
		  int  size = 0;
		  String sql = "select * from user where username = '"+name+"'";
		  if(cn.ConnectionSetup())
			{
			 ResultSet rs = cn.queryRetrive(sql);
			 if(rs.next()){
				 rs.last();
				  size = rs.getRow();
			 }
				if(size > 0){
					 System.out.println(size);
					// cn.close();
					return true;
				}
			}
		  return false;
	  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Register();
		
	}
}
