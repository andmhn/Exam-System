package main.Instructor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.*;


public class ResultAnalysis extends JPanel{
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	int panelwidth = ((int)width)*3/4;
	int panelheight = (int)height;
	Instructor instructor;
	private static final long serialVersionUID = 1L;
	
	
	JPanel[] examListItem;
	int index;
	JLabel[] label1;
	JLabel[] label2;
	JLabel[] label3;
	JButton[] chooseExamButton;
	
	String exams[][];// Fetch exams from database
	int n;
	
	ResultAnalysis ob;
	
	ResultAnalysis(Instructor a) //throws IOException{
	{
		instructor=a;
		
		ob =this;
		
		setLayout(null);
		setBounds((int)(width/4),0,(int)((width*3)/4),(int)height);
		setBackground(Color.YELLOW);
				
		getExams();
		
		examListItem = new JPanel[n];
		
		label1 = new JLabel[n];
		label2 = new JLabel[n];
		label3 = new JLabel[n];
		
		chooseExamButton = new JButton[n];
		
		
		
		
		
		JLabel title = new JLabel("Exams");
		title.setBounds((int)(panelwidth*0.5)-50, (int)(panelheight*0.05), 200, 50);
        add(title);
        displayList();
        
        
        
	}
	void displayList()
	{
		index=0;
		/*Getting Bounds of the panel*/
		Rectangle bounds = getBounds();
		
		int x = bounds.x;
		int y = bounds.y;
		
		int yi = (int)(height*0.1);
		JPanel title = new JPanel();
		title.setLayout(null);
		title.setBackground(Color.WHITE);
		title.setBounds((int)(0.1*width), yi, 800, 50);
		
		JLabel[] heading = new JLabel[3];
		Font f=new Font("Century Gothic",Font.LAYOUT_LEFT_TO_RIGHT,15);
		heading[0] = new JLabel("Exam Id");
		heading[1] = new JLabel("Exam Name");
		heading[2] = new JLabel("Exam Date");		
		
		heading[0].setFont(f);
		heading[1].setFont(f);
		heading[2].setFont(f);
		
		Rectangle bounds2 = title.getBounds();
		//int x2 = bounds2.x;
		//int y2 = bounds2.y;
		int width2 = (int)bounds2.getWidth();
		int height2 = (int)bounds2.getHeight();
		yi+=50;
		heading[0].setBounds((int)(0.05*width2)-20, (int)(0.05*height2), 150, 50);
		heading[1].setBounds((int)(0.05*width2)+100, (int)(0.05*height2), 300, 50);
		heading[2].setBounds((int)(0.05*width2)+500, (int)(0.05*height2), 100, 50);
		title.add(heading[0]);
		title.add(heading[1]);
		title.add(heading[2]);
		add(title);
		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		  c.fill = GridBagConstraints.HORIZONTAL;
		  c.weightx = 0.5;
		  c.weighty=0;
		  c.gridheight=1;
		  c.gridwidth=1;
		  c.gridx = 0;
		  c.gridy = 0;
		
		yi=0;
		//container.setBounds((int)(0.1*width), yi, 800, 50);
		
		for(String[] exam : exams){
			
			examListItem[index] = new JPanel();
			
			examListItem[index].setLayout(null);
			if(index%2==0)
				examListItem[index].setBackground(Color.MAGENTA);
			else
				examListItem[index].setBackground(Color.PINK);
			examListItem[index].setPreferredSize(new Dimension(800,50));
			//adding the details of the exam to the examListItem
				label1[index] = new JLabel("E"+exam[0]);
				label2[index] = new JLabel(exam[1]);
				label3[index] = new JLabel(exam[2]);
				chooseExamButton[index] = new JButton();
				chooseExamButton[index].setBackground(Color.white);
				

			// Add buttons for Delete Exam , Add Exam , View Exam 
				
				
				try
				{
					BufferedImage bi = ImageIO.read(new File("images\\next.jpg"));
					chooseExamButton[index].setIcon(new ImageIcon(bi));
					 bi = ImageIO.read(new File("images\\delete.png"));
					
					
					
					
				}catch(Exception e){
					//do something here
				}
				/*Getting Bounds of the panel*/
				bounds2 = examListItem[index].getBounds();
				//int x2 = bounds2.x;
				//int y2 = bounds2.y;
				width2 = 800;
				height2 = 50;
				//int yi2 = (int)(height2*0.2);
				//setting bounds to the labels inside the list element
				label1[index].setBounds((int)(0.05*width2), (int)(0.05*height2), 50, 50);
				label2[index].setBounds((int)(0.05*width2)+100, (int)(0.05*height2), 300, 50);
				label3[index].setBounds((int)(0.05*width2)+500, (int)(0.05*height2), 100, 50);
				chooseExamButton[index].setBounds((int)(0.05*width2)+600+80, (int)(0.25*height2),30, 30);
				
				
				yi+=(50);
				//adding labels inside the examListItem
				examListItem[index].add(label1[index]);
				examListItem[index].add(label2[index]);
				examListItem[index].add(label3[index]);
				examListItem[index].add(chooseExamButton[index]);
				
				
				chooseExamButton[index].addActionListener(new vieweventAction());
			
				
			/*----------------------------------------------*/
			//adding examListItem to the Main ExamShowPanel
			//add(examListItem[index]);
			container.add(examListItem[index],c);
			c.gridy+=1;
			index++;
		}
		
		container.setBounds((int)(0.1*width), (int)(height*0.1)+50, 800, Math.min(50*10, yi));
		
		
		JScrollPane scrollPane = new JScrollPane(container);
        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds((int)(0.1*width), (int)(height*0.1)+50, 800, Math.min(50*10, yi));
        add(scrollPane);        
        
		
		//instructor.add(p);
		
		
		//p.setBounds(0,0,50*11,50*11);
		//container.add(p);
	
		
		
	}

	void getExams()
	{
		
/*			exams = new String[][]{{"E1","Avul Pakir Jainulabdeen Abdul Kalam Exam","25-04-2016"},
			{"E2","Samsung Placement Exam","31-04-2016"},
			{"E3","Google Mock Exam","30-03-2016"},
			{"E4","No description","20-01-2016"},
			{"E5","M.AN.I.T. Exam","12-12-2015"},
			{"E6","Rameswaram Ramanathapuram Exam","02-09-2016"},
			{"E7","SKG Exam","24-02-2016"},
			{"E8","JEE Exam","30-05-2015"},
			{"E9","AIEEE Exam","02-12-2015"},
			{"E10","AIPMT Exam","10-05-2016"},
			{"E11","TCS Recr. Exam","02-11-2015"},
			{"E12","Microsoft Intern Exam","04-06-2016"}
			};
			
		
	*/	
		String qry1,qry2 ;
		qry1 = "SELECT COUNT(eid) from exam";
		qry2 = "SELECT eid,ename,edate_time FROM exam";
		
		try {
			//ResultSet rs = dbc.executeQuery(qry1);
			
			Statement stmt =Instructor.dbc.conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry1);
			
			if(rs.next())
			{
				n = rs.getInt(1);
			}
			System.out.println("number Of Elements "+n);
			//stmt.close();
			exams = new String[n][3];
			rs =stmt.executeQuery(qry2);
			int i=0;
			while(rs.next())
			{
				int num = rs.getInt(1);
				String ename = rs.getString(2);
				String edate = rs.getString(3);
				exams[i][0]=""+num;
				exams[i][1]=ename;
				exams[i][2]=edate;
				i++;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//  n = number of exams  		
		
	}


	public class eventAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
						
		}
	}
	public class deleteeventAction implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
					
				int choice = JOptionPane.showConfirmDialog(ob,"Are You Sure You Want To Delete This Exam");
				if(choice == 0)
				{
					for(int i=0;i<index;i++)
					{
						if(e.getSource()==(Object)chooseExamButton[i])
						{
							String examid = label1[i].getText();
							String examname = label2[i].getText();
							String examdate = label3[i].getText();
							
							/*
							 
							 Delete Questions where eid = examid
							 
							 
							 */
							
						}
						
					}
					
					// Write Query for deleting the exam
				}
				
				}
				
		}
		public class vieweventAction implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("index : "+index);
				for(int i=0;i<index;i++)
				{
					if(e.getSource()==(Object)chooseExamButton[i])
					{
						String examid = label1[i].getText().substring(1);
						String examname = label2[i].getText();
						String examdate = label3[i].getText();
						ShowStudents ob = new ShowStudents(instructor,examid,examname,examdate);
						instructor.addRightPanel(ob);
					}
				}
			}
		}
}