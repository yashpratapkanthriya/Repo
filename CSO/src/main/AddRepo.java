package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.mysql.jdbc.ResultSet;

import net.codejava.swing.JFilePicker;

public class AddRepo extends JFrame  implements PropertyChangeListener{
	 ConnectionSet  cn = new ConnectionSet();
	   private JLabel labelURL = new JLabel("Upload URL: ");
	    private JTextField fieldURL = new JTextField(30);
	 
	    private JFilePicker filePicker = new JFilePicker("Choose a file: ",
	            "Browse");
	 
	    private JButton buttonUpload = new JButton("Upload");
	 
	    private JLabel labelProgress = new JLabel("Progress:");
	    private JProgressBar progressBar = new JProgressBar(0, 100);
	    String username = "";
	    JComboBox cb;
	AddRepo(int id){
		setVisible(true);
		setSize(600,400);
   		setLocation(150, 25);
   		setTitle("Add Resporatory");
  		setLayout (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));  
  		
  		try{
  			ResultSet rsu = displayByUserId(id);
  			if(rsu.next()){
  				System.out.println("add repo"+rsu.getString(2));
  				username = rsu.getString(2);
  			}
  		}catch (SQLException e) {
			e.printStackTrace();
		}
  		
  		JPanel container = new JPanel();
  		container.setLayout(null);
  		container.setBorder(new EtchedBorder());
  		add(container);
  		
  		JLabel name = new JLabel();
  		name.setText("Name : ");
  		name.setBounds(10, 10, 100, 20);
  		container.add(name);
  		
  		
  		
  		JTextField namef = new JTextField();
  		namef.setBounds(100, 10, 200, 20);
  		container.add(namef);
  		
  		JLabel cat = new JLabel();
  		cat.setText("Cateogry : ");
  		cat.setBounds(10, 30, 100, 20);
  		container.add(cat);
  		
  		 DefaultListModel<String> lCat = new DefaultListModel<>();
  		DefaultComboBoxModel l = new DefaultComboBoxModel<>();
  		
  		try{
  			ResultSet rsc = displayAllCat();
  			if(rsc.next()){
  				do{
  					//list1.add(rsc.getString(2));
  					l.addElement(rsc.getString(2));
  					
  				}while(rsc.next());
  			}
  		}catch (SQLException ea) {
			ea.printStackTrace();
		}
  		
  		  cb=new JComboBox();
  		 cb.setModel(l);
  		 cb.setBounds(100, 30, 100, 20);
  		 container.add(cb);
  		 
  		JLabel sfile = new JLabel();
  		sfile.setText("Select File : ");
  		sfile.setBounds(10, 50, 100, 20);
  		container.add(sfile);
  		
  		filePicker.setBounds(10, 70, 600, 30);
  		buttonUpload.setBounds(10, 100, 100, 20);
  		progressBar.setBounds(10, 130, 100, 20);
  		
  		container.add(filePicker);
  		container.add(buttonUpload);
  		container.add(progressBar);
  		
  		
  	// set up components
        filePicker.setMode(JFilePicker.MODE_OPEN);
        
        progressBar.setPreferredSize(new Dimension(200, 30));
        progressBar.setStringPainted(true);
        
        buttonUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	RepoModel r = new RepoModel();
            	r.setName(namef.getText());
            	r.setUsername(username);
            	r.setCateogry( cb.getItemAt(cb.getSelectedIndex()).toString());
            	String fname = namef.getText()+username.substring(1, 3);
            	r.setFoldername(fname);
            	r.setDate("2-01-2018");
            	
            	if(updateData(r)){
            	
                buttonUploadActionPerformed(event);
            	}
            }
        });
  		 		
	}
	
	public boolean updateData(RepoModel r){
		String q= "insert into repo (name,foldername,username,category,date) values ('"+r.getName()+"','"+r.getFoldername()+"','"+r.getUsername()+"','"+r.getCateogry()+"','"+r.getDate()+"')";
		if(cn.ConnectionSetup())
		{
			if(cn.queryUpdate(q)){
				System.out.println("Successfully Submit");
				/*new HomePage();
				setVisible(false);*/
		return true;
		  }else{
			  JOptionPane.showMessageDialog(this,"Error Inserting Data into Database",
					   "Error",JOptionPane.ERROR_MESSAGE);  
		  }
			}
		return false;
	}
	
	 private void buttonUploadActionPerformed(ActionEvent event) {
		 // TODO: change url according to need
	        String uploadURL = "http://192.168.1.102/cso/";
	        String filePath = filePicker.getSelectedFilePath();
	 
	        // validate input first
	        if (uploadURL.equals("")) {
	            JOptionPane.showMessageDialog(this, "Please enter upload URL!",
	                    "Error", JOptionPane.ERROR_MESSAGE);
	            fieldURL.requestFocus();
	            return;
	        }
	 
	        if (filePath.equals("")) {
	            JOptionPane.showMessageDialog(this,
	                    "Please choose a file to upload!", "Error",
	                    JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	 
	        try {
	            File uploadFile = new File(filePath);
	            progressBar.setValue(0);
	 
	            UploadTask task = new UploadTask(uploadURL, uploadFile);
	            task.addPropertyChangeListener(this);
	            task.execute();
	            
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this,
	                    "Error executing upload task: " + ex.getMessage(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	
	 @Override
	    public void propertyChange(PropertyChangeEvent evt) {
	        if ("progress" == evt.getPropertyName()) {
	            int progress = (Integer) evt.getNewValue();
	            progressBar.setValue(progress);
	        }
	    }

	public  ResultSet displayAllCat(){
		try {
			
			if(cn.ConnectionSetup()){
				
				ResultSet rs =cn.queryRetrive("select * from category");
				return (rs);
		}
	} catch (Exception e) {
		System.out.print(e);
		}
	return null;	
	}
	public ResultSet displayByUserId(int i){
		try {
			if(cn.ConnectionSetup()){
		ResultSet rs=	cn.queryRetrive("select * from user where id="+i);
				return (rs);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return null;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int id =0;
		new AddRepo(id).setVisible(true);
	}

}
