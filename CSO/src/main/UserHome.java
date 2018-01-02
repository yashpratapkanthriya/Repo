package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.mysql.jdbc.ResultSet;



public class UserHome extends JFrame {
	
	JLabel name;
	 ConnectionSet  cn = new ConnectionSet();
	
	public UserHome(int id , String name){
		setVisible(true);
		setLayout(null);
		setSize(1000,700);
   		setLocation(150, 25);
   		setTitle(name.toUpperCase());
   		
  		setLayout (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));  
   	//	setLayout (new FlowLayout(FlowLayout.LEFT));
   		
   		/*BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
   		BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
   		panel1.setLayout(layout1);
  		panel2.setLayout(layout2);*/
  		
     
     JPanel panel1 = new JPanel();
     panel1.setBorder(new EtchedBorder()); 
     int w =getWidth();
     int h = getHeight();
     System.out.println(w+"  "+h);
     panel1.setPreferredSize(new Dimension(w, 40));
     JButton profile = new JButton();
     profile.setText(name);
     panel1.add(profile);
     JButton sout = new JButton();
     sout.setText("Sign Out");
     panel1.add(sout);
     
     profile.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new Profile(id);
		}
	});
     
     sout.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
	});
    
     
     JPanel panel2 = new JPanel();
     panel2.setPreferredSize(new Dimension(w, (h-40)));
     panel2.setBorder(new EtchedBorder()); 
     BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
	 panel2.setLayout(layout2); 
	 System.out.println(panel2.getSize()+",,,"+w+"  "+h);
     // file bar
     JPanel fileBar = new JPanel();
     fileBar.setPreferredSize(new Dimension(w,40));
     fileBar.setBorder(new EtchedBorder());
     
     JButton add = new JButton();
     add.setText("Add Resporatory");
     fileBar.add(add);
     
     add.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// go to new frame to uplaod file
			new AddRepo(id);
		}
	});
     
     // file bar close
     JPanel tree = new JPanel();
     tree.setPreferredSize(new Dimension(w,660));
     tree.setBorder(new EtchedBorder());
     BoxLayout layout3 = new BoxLayout(tree, BoxLayout.X_AXIS);
	 tree.setLayout(layout3); 
    
     panel2.add(fileBar);
     panel2.add(tree);     
     
     JPanel listCat = new JPanel();
    listCat.setBorder(new EtchedBorder());
    listCat.setPreferredSize(new Dimension(300, 660));
    listCat.setLayout(null);
    
    JLabel lc_title = new JLabel();
    lc_title.setText("Categories");
    lc_title.setBounds(40, 10, 100, 40);
    lc_title.setFont(new Font("serif", Font.BOLD, 20));
    listCat.add(lc_title);
    // code to add data in list
    DefaultListModel<String> lCat = new DefaultListModel<>(); 
    lCat.add(0, "All");
    ResultSet rsCat = displayAllCat();
    try {
		if(rsCat.next()){
			do{
				System.out.println(rsCat.getInt(1)+" , "+ rsCat.getString(2));
				
				lCat.add(rsCat.getInt(1), rsCat.getString(2));
			}while(rsCat.next());
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    JList<String> list1 = new JList<>(lCat);
    list1.setBounds(40,60,200,h);  
    listCat.add(list1);
    
    
   
    
    
    JPanel dirPanel = new JPanel();
    dirPanel.setBorder(new EtchedBorder());
    dirPanel.setPreferredSize(new Dimension(w-300, 660));
    
    String column[]={"S. No.","NAME","CATEGORY","DATE","ACTION"};  
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(column);
    JTable table = new JTable();
    table.setModel(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setFillsViewportHeight(true);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    /** to display default data**/
    ResultSet rsCatId = displayAllRepo(); 
	 try{
		 if(rsCatId.next()){
			 int i =1;
			 do{
				// System.out.println(rsCatId.getString(2));
				 model.addRow(new Object[]{i++, rsCatId.getString(2), rsCatId.getString(5),rsCatId.getString(6),"Open"});
				 ButtonColumn buttonColumn = new ButtonColumn(table,null , 4 ,rsCatId.getInt(1));
			 }while(rsCatId.next());
		 } 
	 }catch (SQLException ex) {
		ex.printStackTrace();
	}
    /** list item click action  **/
    list1.addListSelectionListener(new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			int rowCount = model.getRowCount();
			//Remove rows one by one from the end of the table
			for (int i = rowCount - 1; i >= 0; i--) {
			    model.removeRow(i);
			}
			
		
			 if (!e.getValueIsAdjusting()) {
				 System.out.println(list1.getSelectedIndex());
				 if(list1.getSelectedIndex() != 0){
					 try{
				 ResultSet rsCatId = displayById(list1.getSelectedIndex());
				 if(rsCatId.next()){
					 int i =1;
					 do{
						// System.out.println(rsCatId.getString(2));
						 model.addRow(new Object[]{i++, rsCatId.getString(2), rsCatId.getString(5),rsCatId.getString(6),"Open"});
						 ButtonColumn buttonColumn = new ButtonColumn(table,null , 4 ,rsCatId.getInt(1) );
					 }while(rsCatId.next());
				 }
				 }catch (SQLException e2) {
						// TODO: handle exception
					}
				 }else {
					 ResultSet rsCatId = displayAllRepo(); 
					 try{
						 if(rsCatId.next()){
							 int i =1;
							 do{
								// System.out.println(rsCatId.getString(2));
								 model.addRow(new Object[]{i++, rsCatId.getString(2), rsCatId.getString(5),rsCatId.getString(6),"Open"});
								 ButtonColumn buttonColumn = new ButtonColumn(table,null , 4 ,rsCatId.getInt(1) );
								 buttonColumn.setMnemonic(KeyEvent.VK_D);
							 }while(rsCatId.next());
						 } 
					 }catch (SQLException ex) {
						ex.printStackTrace();
					}
				 }
			} 
		}
			 
	});
    dirPanel.add(scroll);
    
    tree.add(listCat);
    tree.add(dirPanel);
     add(panel1);
     add(panel2);
    
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
	
	public  ResultSet displayAllRepo(){
		try {
			
			if(cn.ConnectionSetup()){
				
				ResultSet rs =cn.queryRetrive("select * from repo");
				return (rs);
		}
	} catch (Exception e) {
		System.out.print(e);
		}
	return null;	
	}
	
	public ResultSet displayById(int i){
		try {
			if(cn.ConnectionSetup()){
		ResultSet	rs=	cn.queryRetrive("select * from repo where category ="+i);
				return (rs);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String  name = "";
		int id = 0;
	UserHome l=	new UserHome(id,name);
    l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    l.setVisible(true);
	}

}
