package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.mysql.jdbc.ResultSet;

public class RepoDetails extends JFrame {
	 ConnectionSet  cn = new ConnectionSet();
	 String title = "";
	 ResultSet rsCatId = null;
	 File dirName = null;
	 JPanel panel2;
	 String name = "";
	 String category = "";
	 String date = "";
	 String filepath = "";
	 String dir ="";
	 
	 
	public RepoDetails(int i) {
		setVisible(true);
		setLayout (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));  
		setSize(1000,700);
   		setLocation(150, 25);
   	 try{
		 rsCatId = displayById(i);
		 if(rsCatId.next()){

			 do{
				System.out.println(rsCatId.getString(2));
				title = rsCatId.getString(2);
				dirName = new File("c:/Apache24/htdocs/cso/"+rsCatId.getString(3));
				dir = rsCatId.getString(3);
				name =rsCatId.getString(4);
				category =rsCatId.getString(5);
				date= rsCatId.getString(6);
				 
			 }while(rsCatId.next());
		 }
		 }catch (SQLException e2) {
				// TODO: handle exception
			 e2.printStackTrace();
			}
   	 System.out.println(dirName.toString());
   		setTitle("Resporatory Details : "+title.toUpperCase());
   		//
   		//menu bar open
   		//
   		JPanel menuBar = new JPanel();
   		menuBar.setLayout(null);
  		menuBar.setBorder(new EtchedBorder()); 
  		menuBar.setPreferredSize(new Dimension(getWidth(),60));
  		
  		JLabel uname = new JLabel();
  		uname.setText("Name : "+name.toUpperCase());
  		uname.setBounds(22, 5, 200, 20);
  		menuBar.add(uname);
  		
  		JLabel cat = new JLabel();
  		cat.setText("Category : "+category.toUpperCase());
  		cat.setBounds(222, 5, 200, 20);
  		menuBar.add(cat);
  		
  		JLabel datex = new JLabel();
  		datex.setText("Date : "+date);
  		datex.setBounds(22, 25, 200, 20);
  		menuBar.add(datex);
  		
  		JLabel rname = new JLabel();
  		rname.setText("Resporatory Name : "+title.toUpperCase());
  		rname.setBounds(222, 25, 200, 20);
  		menuBar.add(rname);
  		
  		JButton share = new JButton();
  		share.setText("Share");
  		share.setBounds(400, 5, 100, 20);
  		menuBar.add(share);
  		
  		share.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filepath = dirName.toString();
				System.out.println(dirName.toString()+"\\"+dir+title+".zip");
				try {
					new FolderZiper().zipFolder(dirName.toString(), dirName.toString()+"\\"+dir+title+".zip");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new ShareRepo(name,filepath,title , dir);
				
			}
		});
  		//
   		//menu bar close
   		//
  		
  		JPanel Container = new JPanel();
  		Container.setBorder(new EtchedBorder()); 
  		Container.setLayout (new BoxLayout(Container, BoxLayout.X_AXIS));  
  		Container.setPreferredSize(new Dimension(getWidth(),(getHeight()-20)));
   		
   		JPanel panel1 = new JPanel();
   		panel1.setLayout(null);
   		panel1.setPreferredSize(new Dimension(300, 500));
   		JLabel l = new JLabel();
   		l.setText("Directory :");
   		l.setBounds(10, 5, 100, 30);
   		panel1.add(l);
   		
   		panel2 = new JPanel();
   		panel2.setLayout(null);
   		panel2.setPreferredSize(new Dimension((getWidth()-300), (getHeight()-500)));
   		panel2.setBorder(new EtchedBorder());
   		JLabel l2 = new JLabel();
   		l2.setText("Description :");
   		l2.setBounds(10, 5, 100, 30);
   		panel2.add(l2);
   		
   		JTextPane text = new JTextPane();
   		text.setEditable(false);
   		//JLabel text= new JLabel();
   		text.setBorder(new EtchedBorder());
		// String file = openFile(path);
	     text.setText("");
	     JScrollPane scrollpanetext = new JScrollPane();
	     scrollpanetext.setBounds(10, 40,(getWidth()-330), (getHeight()-140)); // x,y,w,h
	     scrollpanetext.getViewport().add(text);
	     panel2.add(scrollpanetext);
   		
   		
	     Container.add(panel1);
	     Container.add(panel2);
   		 add(menuBar);
   		 add(Container);
   		
   		
   	 // Make a tree list with all the nodes, and make it a JTree
   	    JTree tree = new JTree(addNodes(null, dirName));
   	    
   	    // Add a listener
   	    tree.addTreeSelectionListener(new TreeSelectionListener() {
   	      public void valueChanged(TreeSelectionEvent e) {
   	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
   	        TreeNode pathx = node.getParent();
   	        String path = pathx+"\\"+node.toString();
   	       System.out.println("You selected " + path.toString());
   	     //  filepath = path;
   	        try {
				//openFile(path.toString());
				/* String file = openFile(path);
			     text.setText(""+file);*/
   	        	TextFromFile(text, path);
			     
			     
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   	      }
   	    });

   	    // Lastly, put the JTree into a JScrollPane.
   	    JScrollPane scrollpane = new JScrollPane();
   	    scrollpane.getViewport().add(tree);
   	    scrollpane.setBounds(20, 40, 260,  (getHeight()-140));
   	    panel1.add(scrollpane);
   	    
   	
   
   	  
	}
	
	public String openFile(String dir) throws IOException{
		 FileInputStream inMessage = new FileInputStream(dir);
         // Get the object of DataInputStream
         DataInputStream in = new DataInputStream(inMessage);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
         StringBuilder sb = new StringBuilder();
         //Read File Line By Line
         while ((strLine = br.readLine()) != null)   {
               // Print the content on the console
               System.out.println (strLine);
               sb.append(strLine);
         }
         
         return sb.toString();
        
	}
	
	 public static void TextFromFile(JTextPane tp , String dir)
     {
        try{
            //the file path
         //   String path = "C:\\Users\\samsng\\Desktop\\TextFile.txt";
            File file = new File(dir);
            FileReader fr = new FileReader(file);
            
            while(fr.read() != -1){
              tp.read(fr,null);
            }
            fr.close();
        } catch(Exception ex){
          ex.printStackTrace();
        }
     }
	
	public ResultSet displayById(int i){
		try {
			if(cn.ConnectionSetup()){
		ResultSet	rs=	cn.queryRetrive("select * from repo where id ="+i);
				return (rs);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return null;
	}
	/** Add nodes from under "dir" into curTop. Highly recursive. */
	  DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
	    String curPath = dir.getPath();
	    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
	    if (curTop != null) { // should only be null at root
	      curTop.add(curDir);
	    }
	    Vector ol = new Vector();
	    String[] tmp = dir.list();
	    for (int i = 0; i < tmp.length; i++)
	      ol.addElement(tmp[i]);
	    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
	    File f;
	    Vector files = new Vector();
	    // Make two passes, one for Dirs and one for Files. This is #1.
	    for (int i = 0; i < ol.size(); i++) {
	      String thisObject = (String) ol.elementAt(i);
	      String newPath;
	      if (curPath.equals("."))
	        newPath = thisObject;
	      else
	        newPath = curPath + File.separator + thisObject;
	      if ((f = new File(newPath)).isDirectory())
	        addNodes(curDir, f);
	      else
	        files.addElement(thisObject);
	    }
	    // Pass two: for files.
	    for (int fnum = 0; fnum < files.size(); fnum++)
	      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
	    return curDir;
	  }

	  public Dimension getMinimumSize() {
	    return new Dimension(200, 400);
	  }

	  public Dimension getPreferredSize() {
	    return new Dimension(200, 400);
	  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i =0;
	RepoDetails l=	new RepoDetails(i);
	l.setVisible(true);
	}

}
