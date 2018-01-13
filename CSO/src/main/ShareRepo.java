package main;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.mysql.jdbc.ResultSet;


public class ShareRepo extends JFrame{
	 	ConnectionSet  cn = new ConnectionSet();
	 	final String username = "****@gmail.com";
		final String password = "******";
		String email = "";
		String to ="";
		String from = "";
		String file = "";
		String subject = "";
		String Msg = "";
		JTextField toadd , subtext;
		
		
		ShareRepo(String name , String filepath ,String title , String dir){
			setVisible(true);
			setSize(600,300);
	   		setLocation(150, 25);
	   		
	   		
	   		try{
	   		ResultSet rsCatId = displayById(name);
	   		 if(rsCatId.next()){

	   			 do{
	   				System.out.println("share "+rsCatId.getString(3)+ " path "+ filepath);
	   				email = rsCatId.getString(3) ;
	   			 
	   			 }while(rsCatId.next());
	   		 }
	   		 }catch (SQLException e2) {
	   				// TODO: handle exception
	   			}
	   		
	   		setTitle("Share Resporatory");
	   		setLayout (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));  
	   		JPanel Container = new JPanel();
	   		Container.setLayout(new BoxLayout(Container, BoxLayout.Y_AXIS));
	   		Container.setBorder(new EtchedBorder());
	   		add(Container);
	   		
	   		JPanel fromL = new JPanel();
	   		fromL.setLayout(null);
	   		fromL.setBorder(new EtchedBorder());
	   		fromL.setPreferredSize(new Dimension(getWidth(), 40));
	   		Container.add(fromL);
	   		JLabel fromName = new JLabel();
	   		fromName.setBounds(2, 5, 500, 20);
	   		fromName.setText("From : "+name+" ("+email+")");
	   		fromL.add(fromName);
	   		
	   		JPanel toL = new JPanel();
	   		toL.setLayout(null);
	   		toL.setBorder(new EtchedBorder());
	   		toL.setPreferredSize(new Dimension(getWidth(), 40));
	   		Container.add(toL);
	   		JLabel toName = new JLabel();
	   		toName.setBounds(2, 5, 30, 20);
	   		toName.setText("To : ");
	   		toL.add(toName);
	   		toadd = new JTextField();
	   		toadd.setBounds(100, 5, 200, 20);
	   		to = toadd.getText();
	   		toL.add(toadd);
	   		
	   		JPanel attachL = new JPanel();
	   		attachL.setLayout(null);
	   		attachL.setBorder(new EtchedBorder());
	   		attachL.setPreferredSize(new Dimension(getWidth(), 40));
	   		Container.add(attachL);
	   		JLabel attch = new JLabel();
	   		attch.setBounds(2, 5, 100, 20);
	   		attch.setText("Subject : ");
	   		attachL.add(attch);
	   		
	   		subtext = new JTextField();
	   		subtext.setBounds(100, 5, 200, 20);
	   		attachL.add(subtext);
	   		
	   		JPanel msgL = new JPanel();
	   		msgL.setLayout(null);
	   		msgL.setBorder(new EtchedBorder());
	   		msgL.setPreferredSize(new Dimension(getWidth(), 40));
	   		Container.add(msgL);
	   		JLabel msg = new JLabel();
	   		msg.setBounds(2, 5, 100, 20);
	   		msg.setText("Message : ");
	   		msgL.add(msg);
	   		
	   		JTextArea msgp = new JTextArea();
	   		msgp.setBounds(100, 5, 400, 200);
	   		Msg = msgp.getText();
	   		msgL.add(msgp);
	   		
	   		
	   		JPanel btnLay = new JPanel();
	   		btnLay.setLayout(null);
	   		btnLay.setBorder(new EtchedBorder());
	   		btnLay.setPreferredSize(new Dimension(getWidth(), 40));
	   		Container.add(btnLay);
	   		JButton btnS = new JButton();
	   		btnS.setBounds(2, 5, 100, 20);
	   		btnS.setText("Send");
	   		btnLay.add(btnS);
	   		JButton btnC = new JButton();
	   		btnC.setBounds(220, 5, 100, 20);
	   		btnC.setText("Cancel");
	   		btnLay.add(btnC);
	   		
	   		btnS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					 String dirx = filepath+"\\"+dir+title+".zip";
					File zip = new File(dirx);
					zip.delete();
					try {
						System.out.println("to "+toadd.getText());
				
				// URI a =new URI(dirx);
				 Msg = Msg +"\n Link to Download File : "+dirx;
						sendMail(username, password, toadd.getText(), email,dirx,subtext.getText(),Msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
			});
	   		
	   		btnC.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(false);
				}
			});
	   		
		}
		
		public ResultSet displayById(String i){
			try {
				if(cn.ConnectionSetup()){
			ResultSet	rs=	cn.queryRetrive("select * from user where username ='"+i+"'");
					return (rs);
				}
			} catch (Exception e) {
				System.out.print(e);
			}
			return null;
		}
	
	  public void sendMail(String username, String password , String to , String From , String File , String subject , String msg) throws IOException{
	    	// setting gmail smtp properties
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			// check the authentication
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(From));

				// recipients email address
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

				// add the Subject of email
				message.setSubject(subject);

				Multipart multipart = new MimeMultipart();

				// add the body message
				BodyPart bodyPart = new MimeBodyPart();
				bodyPart.setText(msg);
				multipart.addBodyPart(bodyPart);

				// attach the file
				/*MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.attachFile(new File(File));
				multipart.addBodyPart(mimeBodyPart);*/

				message.setContent(multipart);

				Transport.send(message);

				System.out.println("Email Sent Successfully");
				setVisible(false);
			} catch (MessagingException e) {
				e.printStackTrace();

			}
	    }

	    public static void main(String[] args) throws IOException {
			String name = "";
			String filepath = "";
			String title = "";
			String dir = "";
			
			new ShareRepo(name,filepath, title,dir).setVisible(true);
		}
}
