package hust.soict.hedspi.graph;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


@SuppressWarnings("unused")
public class Jframe extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JFrame frameAbout, frameHelp;
	private JTable tableMatrix;
	private JFrame NewFrame=new JFrame();
	private JPanel GraphPanel = new JPanel();
	private JButton btnOpen, btnSave,btnNew,btnZoomin,btnZoomout;
	private JButton btnRun,btnCreate;
	private JScrollPane scroll;
	private JTextArea textLog;
	private JTextField userCountPoint;  // save number of RandomGraph's Point
	private Image image; // graph image 
	private JComboBox<String> cbbBeginPoint = new JComboBox<String>();
	private JComboBox<String> cbbEndPoint = new JComboBox<String>();
	private JComboBox<String> cbbPathList=new JComboBox<String>();
	private ArrayList<ArrayList<Integer>> PathSimulation=new ArrayList<>();//Save simulation edge which signed by red color
	private ArrayList<Integer> pathVisit=new ArrayList<>();    // save Visisted node
	private ArrayList<JCheckBox> BoxList=new ArrayList<JCheckBox>(); 
	private ArrayList<Integer> Checkboxindex=new ArrayList<>();
	private ArrayList<String> RandomPoint; 
	private ArrayList<String> Point=new ArrayList<>(); 
	private int indexBeginPoint = 0, indexEndPoint = 0,indexPathList=0;
	private int WIDTH_SELECT, HEIGHT_SELECT;
	private int Checkbox_past=0;
    private int[][] randomInput;  
    private int[][] matrix;
    private int[] pastE; // Save Edge deleted after back function
    private int[] ocFre;  // save occurrence frequency of Node in a simulation path
    private final int MaxEcount=5000; // max Edge count
    private String path=new String();// Save log data
    private Random ran=new Random();
    private MyGraph mygraph =new MyGraph();
    private BFS bfs;
    private DFSCycle dfs;
	private long maxMemory=0;
	public Jframe(String title) {
		setTitle(title);
		setLayout(new BorderLayout(5, 5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// addMenu
		add(creatMenu(), BorderLayout.PAGE_START);
		// add content
		add(creatSelectPanel(), BorderLayout.WEST);
		add(creatPaintPanel(), BorderLayout.CENTER);
		add(creatLogPanel(), BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	} 
	@SuppressWarnings("deprecation")
	private JMenuBar creatMenu() {

		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		// menuFile.add(menuFileNew);
		menuFile.add(createMenuItem("New", KeyEvent.VK_N, Event.CTRL_MASK));
		menuFile.add(createMenuItem("Open", KeyEvent.VK_O, Event.CTRL_MASK));
		menuFile.add(createMenuItem("Save", KeyEvent.VK_S, Event.CTRL_MASK));
		menuFile.addSeparator();
		menuFile.add(createMenuItem("Exit", KeyEvent.VK_X, Event.CTRL_MASK));

		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(createMenuItem("Help", KeyEvent.VK_H, Event.CTRL_MASK));
		menuHelp.add(createMenuItem("About", KeyEvent.VK_A, Event.CTRL_MASK));
        
		JMenu menuEdit=new JMenu("Edit");
	    menuEdit.setMnemonic(KeyEvent.VK_E);
		
		JMenu menuColor=new JMenu("Color");
	    menuColor.setMnemonic(KeyEvent.VK_C);
	    menuColor.add(createMenuItem("Background color", KeyEvent.VK_1, Event.CTRL_MASK));
	    menuColor.add(createMenuItem("Point color", KeyEvent.VK_2, Event.CTRL_MASK));
	    menuColor.add(createMenuItem("Edge color", KeyEvent.VK_3, Event.CTRL_MASK));
	    menuColor.add(createMenuItem("Font color", KeyEvent.VK_4, Event.CTRL_MASK));
	    
	    JMenu menuShape=new JMenu("Shape");
	    menuShape.setMnemonic(KeyEvent.VK_5);
	    menuShape.add(createMenuItem("Point Shape", KeyEvent.VK_6, Event.CTRL_MASK));
	    menuShape.add(createMenuItem("Edge Shape", KeyEvent.VK_7, Event.CTRL_MASK));
	    menuShape.add(createMenuItem("Arrow Shape", KeyEvent.VK_6, Event.CTRL_MASK));
	    menuShape.add(createMenuItem("Spline", KeyEvent.VK_7, Event.CTRL_MASK));	
	    
	    JMenu menuLayout=new JMenu("Layout");
	    menuLayout.setMnemonic(KeyEvent.VK_L);
	    menuLayout.add(createMenuItem("Neato", KeyEvent.VK_8, Event.CTRL_MASK));
	    menuLayout.add(createMenuItem("Dot", KeyEvent.VK_9, Event.CTRL_MASK));
	    
	    JMenu menuRankdir=new JMenu("Rankdir");
	    menuRankdir.setMnemonic(KeyEvent.VK_R);
	    menuRankdir.add(createMenuItem("BT", KeyEvent.VK_F1, Event.CTRL_MASK));
	    menuRankdir.add(createMenuItem("TB", KeyEvent.VK_F2, Event.CTRL_MASK));
	    menuRankdir.add(createMenuItem("LR", KeyEvent.VK_F3, Event.CTRL_MASK));
	    menuRankdir.add(createMenuItem("RL", KeyEvent.VK_F4, Event.CTRL_MASK));
	   
	    menuEdit.add(menuColor);
	    menuEdit.add(menuShape);
	    menuEdit.add(menuLayout);
	    menuEdit.add(menuRankdir);
	    menuEdit.add(createMenuItem("Default Setting", KeyEvent.VK_D, Event.CTRL_MASK));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		menuBar.add(menuEdit);
		return menuBar;
	}
	private JPanel creatSelectPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new GridLayout(4, 1, 5, 5));
		JPanel panelBottom = new JPanel(new BorderLayout());

		JPanel panelRandomGraphTemp = new JPanel(new GridLayout(1, 2, 5, 5));
		JLabel CountPointLabel=new JLabel("CountPoint");
	    userCountPoint=new JTextField();
	    panelRandomGraphTemp.add(CountPointLabel);
	    panelRandomGraphTemp.add(userCountPoint);
		panelRandomGraphTemp.setBorder(new EmptyBorder(0, 10, 0, 5));
		
		JPanel panelCreateTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelCreateTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelCreateTemp.add(btnCreate= createButton("Create"));
		JPanel panelCreate = new JPanel(new BorderLayout());
		panelCreate.setBorder(new TitledBorder("Create"));
		panelCreate.add(panelCreateTemp);
		
		JPanel panelRandomGraph = new JPanel(new BorderLayout());
		panelRandomGraph.setBorder(new TitledBorder("Random Graph"));
		panelRandomGraph.add(panelRandomGraphTemp);

		JPanel panelSelectPointTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelSelectPointTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelSelectPointTemp.add(cbbBeginPoint = createComboxBox("Begin"));
		panelSelectPointTemp.add(cbbEndPoint = createComboxBox("End"));
		JPanel panelSelectPoint = new JPanel(new BorderLayout());
		panelSelectPoint.setBorder(new TitledBorder("Point"));
		panelSelectPoint.add(panelSelectPointTemp);
		
		JPanel panelRunTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelRunTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelRunTemp.add(btnRun= createButton("Run"));
		JPanel panelRun = new JPanel(new BorderLayout());
		panelRun.setBorder(new TitledBorder("Run"));
		panelRun.add(panelRunTemp);

		panelTop.add(panelRandomGraph);
		panelTop.add(panelCreate);
		panelTop.add(panelSelectPoint);
		panelTop.add(panelRun);

		JScrollPane scroll = new JScrollPane(tableMatrix = createTable());
		scroll.setPreferredSize(panelTop.getPreferredSize());
		panelBottom.add(scroll);

		panel.add(panelTop, BorderLayout.PAGE_START);
		panel.add(panelBottom, BorderLayout.CENTER);
		panel.setBorder(new EmptyBorder(0, 5, 0, 0));
		WIDTH_SELECT = (int) panel.getPreferredSize().getWidth();
		HEIGHT_SELECT = (int) panel.getPreferredSize().getHeight();
		return panel;
	}
	private JPanel creatPaintPanel() {
		GraphPanel.setLayout(new BoxLayout(GraphPanel, BoxLayout.Y_AXIS));
		GraphPanel.setBorder(new TitledBorder(""));
		GraphPanel.setBackground(null);
		Icon icon;
		// String link = File.separator + "icon" + File.separator;
		String link = "/icon/";

		icon = getIcon(link + "iconOpen.png");
		GraphPanel.add(btnOpen = createButtonImage(icon, "Open graph"));
 		
		icon = getIcon(link + "iconSave.png");
		GraphPanel.add(btnSave = createButtonImage(icon, "Save graph"));
	       
		icon = getIcon(link + "iconZoomin.png");
		GraphPanel.add(btnZoomin = createButtonImage(icon, "Zoom in"));
			
		icon = getIcon(link + "iconZoomout.png");
		GraphPanel.add(btnZoomout = createButtonImage(icon, "Zoom out"));
		
		icon = getIcon(link + "iconNew.png");
		GraphPanel.add(btnNew = createButtonImage(icon, "New graph"));

        
		JPanel panel = new JPanel();
		scroll = new JScrollPane(mygraph);
		scroll.setPreferredSize(panel.getPreferredSize());
		panel.setLayout(new BorderLayout());
		panel.add(GraphPanel, BorderLayout.WEST);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}
	private JMenuItem createMenuItem(String title, int keyEvent, int event) {
		JMenuItem mi = new JMenuItem(title);
		mi.setMnemonic(keyEvent);
		mi.setAccelerator(KeyStroke.getKeyStroke(keyEvent, event));
		mi.addActionListener(this);
		return mi;
	}
	private JButton createButton(String lable) {
		JButton btn = new JButton(lable);
		btn.addActionListener(this);
		return btn;
	}
	// create buttonImage and add to panel
	private JButton createButtonImage(Icon icon, String toolTip) {
		JButton btn = new JButton(icon);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.addActionListener(this);
		btn.setToolTipText(toolTip);
		return btn;
	}

		// create comboBox and add to panel
	private JComboBox<String> createComboxBox(String title) {
		String list[] = { title };
		JComboBox<String> cbb = new JComboBox<String>(list);
		cbb.addActionListener(this);
		cbb.setEditable(false);
		cbb.setMaximumRowCount(5);
		return cbb;
	}
	private JTable createTable() {
		JTable table = new JTable();
		return table;
	}
	private JPanel creatLogPanel() {
		textLog = new JTextArea("Path: ");
		textLog.setRows(3);
		textLog.setEditable(false);
		JScrollPane scrollPath = new JScrollPane(textLog);


		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Log"));
		panel.add(scrollPath, BorderLayout.PAGE_START);
		panel.setPreferredSize(new Dimension(WIDTH_SELECT * 15/ 2,
		    HEIGHT_SELECT /5));
			return panel;
	}
	 private void CreateRandomGraph() throws IOException{
			String Count=userCountPoint.getText();
			int count=0;
			int Ecount=0;
			try {
			    count=Integer.parseInt(Count);
			    if(count<=0) {
			    	JOptionPane.showMessageDialog(null,"The number of Points must be greater than 0");
			    }
			    else if(count>1000) {
			    	JOptionPane.showMessageDialog(null,"The number of Points must be less than 1000");
			    }
			    else {
				int col=0;
				int row=0;
				ArrayList<Integer> colPoint=new ArrayList<Integer>();
				ArrayList<Integer> rowPoint=new ArrayList<Integer>();
				ArrayList<ArrayList<Integer>> RandomOutput=new ArrayList<ArrayList<Integer>>();
				RandomPoint=new ArrayList<String>();
				randomInput=new int[count][count+1];
				for(int i=1;i<=count;i++) {
					RandomPoint.add(Integer.toString(i));
				}
				col=ran.nextInt(count)+1;			
				colPoint=randomArr(count,col,colPoint);
				
				for(int i=0;i<colPoint.size();i++) {
					rowPoint.add(colPoint.get(i));
					row=ran.nextInt(count)+1;
					if(Ecount+row<=MaxEcount) {
                    Ecount+=row;
					rowPoint=randomArr(count,row,rowPoint);
					RandomOutput.add(rowPoint);
					rowPoint=new ArrayList<Integer>();
					}
					else {
						break;
					}
				}
				if(Ecount<=1000) {
					setGraphType(0);
				}
				else {
					setGraphType(1);
				}
				colPoint=new ArrayList<Integer>();
				int idx=0;
				while(RandomOutput.size()>0) {
				        
						for(int i=0;i<RandomOutput.get(0).size();i++) {
							randomInput[idx][i]=RandomOutput.get(0).get(i);
						}
						idx++;
						RandomOutput.remove(0);
						
				} 				
				actionNew();
				mygraph.getData(RandomPoint,randomInput);
				mygraph.setEdgeCount(Ecount);
				mygraph.MakeGraph();
				updateData();
				loadMatrix();
			    updateListPoint();
			    }	
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"Invalid type!");	
			}
		}
	 private void CreateCheck() {
		 CreateNewFrame("Finding Type");
		 JPanel panelcheckbox=new JPanel();
		 panelcheckbox.setBorder(BorderFactory.createTitledBorder("choose type!"));
		 panelcheckbox.setPreferredSize(new Dimension(WIDTH_SELECT, HEIGHT_SELECT/2));
		 panelcheckbox.setLayout(new GridLayout(4,1,10,10));
		 JCheckBox checkbox=new JCheckBox("Simulation");
 		 panelcheckbox.add(checkbox);
 		 checkbox.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		NewFrame.dispose();
    		    clearSimulateData();
        		setSimulationData();
        		bfs=new BFS(matrix,indexBeginPoint,indexEndPoint);
    			if(bfs.FindMinWay(indexBeginPoint).size()==0) {
    				JOptionPane.showMessageDialog(null,
    						"No Way!",
    						"Error", JOptionPane.WARNING_MESSAGE);
    			}
    			else {
    		    try {
					updateGraph();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    			int[] possible;
    			ocFre=new int[Point.size()];
    			possible=bfs.possible;
    			Checkbox_past=indexBeginPoint;
    			path+=Point.get(indexBeginPoint);
    			ocFre[indexBeginPoint]+=1;
    			pathVisit.add(indexBeginPoint);
    			createCheckbox(indexBeginPoint,possible);
    			}			
        	}
         });
 		 checkbox=new JCheckBox("Find All");
		 panelcheckbox.add(checkbox);
		 checkbox.addItemListener(new ItemListener() {
       	   public void itemStateChanged(ItemEvent e) {
       		    NewFrame.dispose();
       		    clearSimulateData();
 		        try {
					updateGraph();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
       		   createCheckbox2();
       	   }
        });
		 NewFrame.add(panelcheckbox);
		NewFrame.validate();
		NewFrame.repaint();
		NewFrame.pack();
		NewFrame.setVisible(true);
		 
	 }
	 private void createCheckbox2() {
		 JPanel panelcheckbox=new JPanel();
	     panelcheckbox.setBorder(BorderFactory.createTitledBorder("Choose Way!"));
	     panelcheckbox.setLayout(new GridLayout(4,1,10,10));	
	     double EdgeCountpercent=mygraph.getEcount()*100.00/(Point.size()*Point.size());
	     if(EdgeCountpercent>30) {
	     dfs=new DFSCycle(matrix,indexBeginPoint,indexEndPoint);
	     dfs.doDFS(0);
	     }
	     else {
	    	 bfs=new BFS(matrix,indexBeginPoint,indexEndPoint);
	    	 dfs=new DFSCycle(matrix,indexBeginPoint,indexEndPoint);
	    	 dfs.setPointPossible(bfs.getPossiblePoint());
	    	 dfs.doDFS(1);
	     }
	     String Path[]=new String[dfs.getPath().size()];
	     if(dfs.getPath().size()==0) {
		    	JOptionPane.showMessageDialog(null,
						"No Way!",
						"Error", JOptionPane.WARNING_MESSAGE);
		  }
	     else {
	    	 for(int i=0;i<dfs.getPath().size();i++) {
	    		 String temp=new String();
	    		 int count=i+1;
	    		 temp+="Count "+count+": ";
	    		 for(int j=0;j<dfs.getPath().get(i).size();j++) {
	    			 temp+=(Point.get(dfs.getPath().get(i).get(j)))+" ";
	    		 }
	    		Path[i]=String.valueOf(temp);
	    	 }
	    	 /*
	    	 String input = (String) JOptionPane.showInputDialog(null, "Choose path!",
	     	        "Open File", JOptionPane.QUESTION_MESSAGE, null, 
	     	        Path, 
	     	        Path[1]); 
	    	 
	    	 */
	    	 cbbPathList.setModel(new DefaultComboBoxModel<String>(Path));
	    	 cbbPathList.setMaximumRowCount(4);	 
	    	 cbbPathList.setPreferredSize(new Dimension(WIDTH_SELECT,HEIGHT_SELECT/10));
	    	 JButton but=new JButton("OK");
	    	 but.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					clearSimulateData();
					indexPathList = cbbPathList.getSelectedIndex();
	 		        try {
						updateGraph();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					addSimulation(dfs.getPath().get(indexPathList));
					updateSimulationData(PathSimulation);
     			    try {
     			    	updateGraph();
						loadPath();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}	    		 
	    	 });
	    	 panelcheckbox.add(cbbPathList);
	    	 panelcheckbox.add(but);
	         JScrollPane scroll=new JScrollPane(panelcheckbox);
	         scroll.setPreferredSize(new Dimension(WIDTH_SELECT*2, HEIGHT_SELECT/2));
		     CreateNewFrame("Finding");
	        NewFrame.add(scroll);
			NewFrame.validate();
			NewFrame.repaint();
			NewFrame.pack();
			NewFrame.setVisible(true);     
			
	     }
	 }
	 public void addSimulation(ArrayList<Integer> input) {
		 path+=Point.get(indexBeginPoint);
		 for(int i=0;i<input.size()-1;i++) {
			ArrayList<Integer> temp=new ArrayList<>();
     		temp.add(input.get(i));
     		temp.add(input.get(i+1));
     		path+="->"+Point.get(input.get(i+1));
     		PathSimulation.add(temp);
		 }
		
	 }
	 private void createCheckbox(int idx,int[] possible) {
		    CreateNewFrame("Finding");
	    	JPanel panelcheckbox=new JPanel();
	    	panelcheckbox.setBorder(BorderFactory.createTitledBorder("The adjacent vertices can reach the destination!"));
	    	panelcheckbox.setLayout(new BoxLayout(panelcheckbox,BoxLayout.Y_AXIS));
	    	int near[]=bfs.getMinNearBy(idx);
	        for(int i=0;i<Point.size();i++) {
	        	if(matrix[i][idx]==1&&possible[i]==1) {
	        		JCheckBox checkbox=new JCheckBox(Point.get(i));
	        		if(near[i]!=0) {
	        			checkbox.setBackground(Color.red);
	        		}
	        		panelcheckbox.add(checkbox);
	        		BoxList.add(checkbox);
	        		Checkboxindex.add(i);
	        		checkbox.addItemListener(new ItemListener() {
		            	public void itemStateChanged(ItemEvent e) {
		            		NewFrame.dispose();
		            		ArrayList<Integer> temp=new ArrayList<>();
		            		temp.add(Checkbox_past);
		            		temp.add(Checkboxindex.get(BoxList.indexOf(checkbox)));
		            		if(PathSimulation.size()>0) {
		            		if(PathSimulation.get(PathSimulation.size()-1)!=temp) {
		            			PathSimulation.add(temp);
		            		}
		            		}
		            		else {
		            			PathSimulation.add(temp);
		            		}
		            		path+="->"+Point.get(Checkboxindex.get(BoxList.indexOf(checkbox)));
		            		ocFre[Checkboxindex.get(BoxList.indexOf(checkbox))]+=1;
		            		pathVisit.add(Checkboxindex.get(BoxList.indexOf(checkbox)));
		            		Checkbox_past=Checkboxindex.get(BoxList.indexOf(checkbox));
		            		updateSimulationData(PathSimulation);
		     			    try {
		     			    	updateGraph();
								loadPath();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
                            if(Checkboxindex.get(BoxList.indexOf(checkbox))!=indexEndPoint) {
		            		  createCheckbox(Checkboxindex.get(BoxList.indexOf(checkbox)),possible);
		            		}
                            else {
                            }
		            	}
		            });
	        	}
	        	
	        }
	        JCheckBox checkbox=new JCheckBox("Reach the destination immediately(Min way)");
	        panelcheckbox.add(checkbox);
	        checkbox.addItemListener(new ItemListener() {
	        	public void itemStateChanged(ItemEvent e) {
	        		NewFrame.dispose();
	        		ArrayList<Integer> min=new ArrayList<>();
	        		min=bfs.FindMinWay(Checkbox_past);
	        		for(int i=0;i<min.size()-1;i++) {
	        			ArrayList<Integer> temp=new ArrayList<>();
	            		temp.add(min.get(i));
	            		temp.add(min.get(i+1));
	            		PathSimulation.add(temp);
	            		path+="->"+Point.get(min.get(i+1));
	            		pathVisit.add(min.get(i+1));
	        		}
	        		updateSimulationData(PathSimulation);
	 			    try {
	 			    	updateGraph();
						loadPath();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	        	}
	        });
	        if(PathSimulation.size()>0) {
	        checkbox=new JCheckBox("Back");
	        panelcheckbox.add(checkbox);
	        checkbox.addItemListener(new ItemListener() {
	        	public void itemStateChanged(ItemEvent e) {
	        		NewFrame.dispose();
	        		path=path.substring(0,path.length()-2-Point.get(pathVisit.get(pathVisit.size()-1)).length());
	        		PathSimulation.remove(PathSimulation.size()-1);
	        		updateSimulationData(PathSimulation);
	        		pastE=new int[2];
	        		pastE[0]= pathVisit.get(pathVisit.size()-2); 
	        		pastE[1]= pathVisit.get(pathVisit.size()-1);
	        		mygraph.setpathEdge(pastE);
	        		pathVisit.remove(pathVisit.size()-1);
	        		Checkbox_past=pathVisit.get(pathVisit.size()-1);
	 			    try {
	 			    	updateGraph();
						loadPath();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	 			   createCheckbox(pathVisit.get(pathVisit.size()-1),possible);
	 			    

	        	}
	        });	
	        checkbox=new JCheckBox("Back Cycle");
	        panelcheckbox.add(checkbox);
	        checkbox.addItemListener(new ItemListener() {
	        	public void itemStateChanged(ItemEvent e) {
	        		NewFrame.dispose();
	        	    while(pathVisit.size()>1) {
	        	    	if(ocFre[pathVisit.get(pathVisit.size()-1)]>1) {
	        	    		path=path.substring(0,path.length()-2-Point.get(pathVisit.get(pathVisit.size()-1)).length());
	    	        		PathSimulation.remove(PathSimulation.size()-1);
	    	        		ocFre[pathVisit.get(pathVisit.size()-1)]-=1;
	    	        		pastE=new int[2];
	    	        		pastE[0]= pathVisit.get(pathVisit.size()-2); 
	    	        		pastE[1]= pathVisit.get(pathVisit.size()-1);
	    	        		pathVisit.remove(pathVisit.size()-1);
	    	        		Checkbox_past=pathVisit.get(pathVisit.size()-1);
	    	        		mygraph.setpathEdge(pastE);
	        	    	}
	        	    	if(ocFre[pathVisit.get(pathVisit.size()-1)]<=1) {
	        	    		break;
	        	    	}
	        	    }
	 			    try {
	 			    	updateGraph();
						loadPath();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	 			   createCheckbox(pathVisit.get(pathVisit.size()-1),possible);
	 			    

	        	}
	        });	
	        }
	        JScrollPane scroll=new JScrollPane(panelcheckbox);
	        scroll.setPreferredSize(new Dimension(WIDTH_SELECT*2, HEIGHT_SELECT));
	        NewFrame.add(scroll);
			NewFrame.validate();
			NewFrame.repaint();
			NewFrame.pack();
			NewFrame.setVisible(true);       
	    }
	    private void CreateNewFrame(String title) {
	    	NewFrame=new JFrame(title);
	    	NewFrame.setLocationRelativeTo(null);
	    	NewFrame.setLocation(100,200);
	    	NewFrame.pack();
	    	NewFrame.setVisible(true);
			
		}	 
	    private void createChooseShapeLabel(String title,String[] shapeLink,String[] shapeName,int[] layout,int dim[],int type) {
	    	CreateNewFrame(title);
	    	JPanel newpanel=new JPanel();
	    	newpanel.setLayout(new GridLayout(layout[0],layout[1],5,5));
	    	JButton but;
	    	Icon icon;
	    	String link = "/icon/";
            String ShapeLink[]= shapeLink;
            String ShapeName[]= shapeName;
			for(int i=0;i<ShapeLink.length;i++) {
			      icon=getIcon(link+ShapeLink[i]);
		    //        System.out.println("done");
			      but= createButtonImage(icon, "Shape");
			      String shape=ShapeName[i];	      
			      but.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(type==0) {
						    changePointShape(shape);
						}
						if(type==1) {
							changeEdgeShape(shape);
						}
						if(type==2) {
							changeArrowheadShape(shape);
						}
						if(type==3) {
							changeSplinesShape(shape);
						}
					}
			    	  
			      });
			      newpanel.add(but);
			}
			JScrollPane scroll = new JScrollPane(newpanel);
			scroll.setPreferredSize(new Dimension(dim[0],dim[1]));
			NewFrame.add(scroll);
			NewFrame.validate();
			NewFrame.repaint();
			NewFrame.pack();
			NewFrame.setVisible(true);
	    }
	    private void createPointShapeLabel() {
            String ShapeLink[]= {"circle.gif","ellipse.gif","box.gif","box3d.gif","cylinder.gif"
            		,"diamond.gif","folder.gif","note.gif","plaintext.gif","triangle.gif","none.gif","point.gif"};
            String ShapeName[]= {"circle","ellipse","box","box3d","cylinder"
            		,"diamond","folder","note","plaintext","triangle","none","point"};
			int dim[] = {400,400};
			int layout[]= {4,3};
			createChooseShapeLabel("Point Shape",ShapeLink,ShapeName,layout,dim,0);
	    }
	    private void createEdgeShapeLabel() {
            String ShapeLink[]= {"e_bold.png","e_dashed.png","e_dotted.png","e_solid.png"};
            String ShapeName[]= {"bold","dashed","dotted","solid"};
			int dim[] = {500,200};
			int layout[]= {2,2};
			createChooseShapeLabel("Edge Shape",ShapeLink,ShapeName,layout,dim,1);
	    }
	    private void createArrowShapeLabel() {
	    	String ShapeLink[]= {"a_empty.gif","a_box.gif","a_obox.gif","a_open.gif","a_dot.gif","a_diamond.gif",
            		"a_normal.gif","a_ediamond.gif","a_none.gif","a_odot.gif"};
            String ShapeName[]= {"empty","box","obox","open","dot","diamond",
            		"normal","ediamond","none","obox"};
			int dim[] = {400,200};
			int layout[]= {5,2};
			createChooseShapeLabel("Arrow Shape",ShapeLink,ShapeName,layout,dim,2);
	    }
	    private void createSplineLabel() {
	    	String ShapeLink[]= {"spline_curved.png","spline_line.png","spline_none.png","spline_polyline.png",
	    			"spline_spline.png","spline_ortho.png"};
            String ShapeName[]= {"curved","line","none","polyline","spline","ortho"};
			int dim[] = {400,400};
			int layout[]= {6,1};
			createChooseShapeLabel("Spline",ShapeLink,ShapeName,layout,dim,3);
	    }
	private ImageIcon getIcon(String link) {
		ImageIcon imgicon=new ImageIcon();
		try {
		imgicon=new ImageIcon(getClass().getResource(link));
		}
		catch(NullPointerException ex){
			JOptionPane.showMessageDialog(null,
					"Please install/reInstall \"icon\" file to (yourProjectfile)/bin",
					"Error", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		return imgicon;
	} 
    private ArrayList<Integer> randomArr(int max,int count,ArrayList<Integer> arr) {
    	ArrayList<Integer> temp=new ArrayList<Integer>();
    	int Temp=0;
    	for(int i=1;i<=max;i++) {
    		temp.add(i);
    	}
    	for(int i=0;i<count;i++) {
    		Temp=ran.nextInt(temp.size());
    		arr.add(temp.get(Temp));
    		temp.remove(Temp);
    	}
    	return arr;
    }
    private void clearSimulateData() {
    	PathSimulation=new ArrayList<>();
    	mygraph.resetSimulation();
    	path=new String();
    	pathVisit=new ArrayList<>();
    	 pastE=null;
    }
	private void clearMatrix() {
		tableMatrix.setModel(new DefaultTableModel());
	}
	private void clearCountPoint() {
		userCountPoint.setText("");
	}
	private void clearPath() {
		textLog.setText("Path : ");
		path=new String();
		pathVisit=new ArrayList<>();
	}
	private void updateData() {
		this.Point=mygraph.getPoint();
		this.matrix=mygraph.getOutput_matrix();
		this.image=mygraph.getImage();
	}
	private void updateGraph() throws IOException {
		mygraph.resetGraph();
		mygraph.MakeGraph();
	}
	private void actionNew() {
		mygraph.resetGraph();
		mygraph.resetData();
		updateData();
		clearPath();
		clearMatrix();
		clearCountPoint();
		clearSimulateData();
		updateListPoint();
		
	} 
	private void enableButton(JButton button) {
		button.setEnabled(true);
	}
	private void actionSave() throws IOException {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save graph");
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		          "PNG Images", "png");
		fc.setFileFilter(filter);
	    int select = fc.showSaveDialog(this);
	    if (select == 0) {
		    File saveFile = fc.getSelectedFile();
		    updateData();
		     BufferedImage bimage = new BufferedImage(image.getWidth(null),
		    		image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		    Graphics2D bGr = bimage.createGraphics();
			bGr.drawImage(image, 0, 0, null);
			bGr.dispose();
		    try {
		        ImageIO.write(bimage, "png", saveFile);
		    } catch (IOException e) {
		            e.printStackTrace();
		     }
		     
		}
	}
	private void actionZoomin() throws IOException {
		double graphWidth=mygraph.getImageWidth()*1.0/100;
		double graphHeight=mygraph.getImageHeight()*1.0/100;
		double k=1.5;
		mygraph.Resize(graphWidth*k,graphHeight*k);
		updateGraph();
	}
    private void actionZoomout() throws IOException {
    	double graphWidth=mygraph.getImageWidth()*1.0/100;
    	double graphHeight=mygraph.getImageHeight()*1.0/100;
        double k=0.75;
		graphHeight*=k;
		graphWidth*=k;
		if(graphHeight<1) {
			graphHeight=1;
		}
		if(graphWidth<1) {
			graphWidth=1;
		}
    	mygraph.Resize(graphWidth,graphHeight);
		updateGraph();
	}
    
    private void actionChoosePoint() {
       	indexBeginPoint = cbbBeginPoint.getSelectedIndex()-1;
        indexEndPoint = cbbEndPoint.getSelectedIndex()-1;
	}
    private void actionOpen() throws IOException {
    	 String[] choices = { "Matrix","List"};
    	 String input = (String) JOptionPane.showInputDialog(null, "Choose type of input!",
    	        "Open File", JOptionPane.QUESTION_MESSAGE, null, 
    	        choices, 
    	        choices[1]); 
    	 if(input!=null) {
         if(input.compareTo("List")==0) {
        	 OpenListFile();
         }
         else {
        	 OpenMatrixFile();
         }
    	 }
	}
    private void ActionRun() throws IOException {
	    int size=Point.size();
		if (size < 1 || indexBeginPoint == -1 || indexEndPoint == -1) {
			JOptionPane.showMessageDialog(null,
					"Error chose points or don't Update graph to chose points",
					"Error", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		else {
	     CreateCheck();
		}			
   }
    private void OpenMatrixFile() throws IOException {
    	JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open graph");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		int select = fc.showOpenDialog(this);
		if (select == 0) {
			String path = fc.getSelectedFile().getPath();
			String extension=path.substring(path.lastIndexOf(".")+1,path.length());
			if(extension.compareTo("txt")==0) {
			    actionNew();
			    int check=mygraph.getDataFromMatrixFile(path);
			    updateData();
			    if(check==0) {
			    	if(Point.size()<1000) {
				  if(mygraph.getEcount()>1000) {
				    	setGraphType(1);
				  }
				  else {
				    	setGraphType(0);
				  }
			    mygraph.MakeGraph();
			    loadMatrix();
		        updateListPoint();
			    }
			    }
			    else {
			    	JOptionPane.showMessageDialog(null,"The number of Points must be less than 1000");
			    }
		    }
			else {
				JOptionPane.showMessageDialog(null,"Invalid File format!");
			}
		}
    }
    private void OpenListFile() throws IOException {
    	JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open graph");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		int select = fc.showOpenDialog(this);
		if (select == 0) {
			String path = fc.getSelectedFile().getPath();
			String extension=path.substring(path.lastIndexOf(".")+1,path.length());
			if(extension.compareTo("txt")==0) {
			    actionNew();
			    mygraph.getDataFromListFile(path);
			    updateData();
			    if(Point.size()<1000) {
			    if(mygraph.getEcount()>1000) {
			    	setGraphType(1);
			    }
			    else {
			    	setGraphType(0);
			    }
			    mygraph.MakeGraph();
			    loadMatrix();
		        updateListPoint();
			    }
			    else {
			    	JOptionPane.showMessageDialog(null,"The number of Points must be less than 100");
			    }
		    }
			else {
				JOptionPane.showMessageDialog(null,"Invalid File format!");
			}
		}
    }
    
    private void setSimulationData() {
    	mygraph.setSimulation();
    }
    
    private void updateSimulationData(ArrayList<ArrayList<Integer>> input) {
    	mygraph.updateSimulationData(input);
    }
    private void setGraphType(int type) {
    	mygraph.setGraphType(type);
    }
	private void loadPath() {
		textLog.setText(path);
	}
	private void loadMatrix() {
		final int width = 35;
		final int col = WIDTH_SELECT / width - 1;
		int[][] data_int=matrix;
		String data[][]=new String[data_int.length][data_int.length];
		String head[]=new String[Point.size()];
		for(int i=0;i<Point.size();i++) {
			head[i]=Point.get(i);
		}
		for(int i=0;i<data_int.length;i++) {
			for(int j=0;j<data_int[i].length;j++) {
				data[i][j]=String.valueOf(data_int[i][j]);
			}
		}
		DefaultTableModel model = new DefaultTableModel(data, head);
		tableMatrix.setModel(model);
		if (tableMatrix.getColumnCount() > col) {
			for (int i = 0; i < head.length; i++) {
				TableColumn tc = tableMatrix.getColumnModel().getColumn(i);
				tc.setPreferredWidth(width);
			}
			tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		} else {
			tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
	}
	private void updateListPoint() {
		int size =Point.size();
		String listPoint[] = new String[size+1];
		listPoint[0] = "Begin";
		for (int i = 1; i <listPoint.length; i++) {
			listPoint[i] = Point.get(i-1);
		}

		cbbBeginPoint.setModel(new DefaultComboBoxModel<String>(listPoint));
		cbbBeginPoint.setMaximumRowCount(5);

		if (size > 1) {
			listPoint = new String[size+1];
			listPoint[0] = "End";
			for (int i = 1; i <listPoint.length; i++) {
				listPoint[i] =Point.get(i-1);
			}
		} else {
			listPoint = new String[1];
			listPoint[0] = "End";
		}

		cbbEndPoint.setModel(new DefaultComboBoxModel<String>(listPoint));
		cbbEndPoint.setMaximumRowCount(5);
	}	
	private Color ChooseColor(Color ColorDefault) {
		Color color;
		color =JColorChooser.showDialog(this, 
                "Select a color", ColorDefault);
		return color;
	}
	private void changeBackgroundColor() {
		Color color=ChooseColor(Color.white	);
		if(color!=null) {
			mygraph.setBackgroundColor(color);  
		}
	}
	private void changeEdgeColor() {
		Color color=ChooseColor(Color.black);
		if(color!=null) {
			mygraph.setEdgeColor(color);
		}
	}
	private void changePointColor() {
		Color color=ChooseColor(Color.black);
		if(color!=null) {
		    mygraph.setPointColor(color); 
		}
	}
	private void changeFontColor() {
		Color color=ChooseColor(Color.black);
		if(color!=null) {
		    mygraph.setFontColor(color); 
		}
	}
	private void changePointShape(String shape) {
		mygraph.setPointShape(shape);
	}
	private void changeEdgeShape(String shape) {
		mygraph.setEdgeShape(shape);
	}
	private void changeArrowheadShape(String shape) {
		mygraph.setArowhead(shape);
	}
	private void changeSplinesShape(String shape) {
		if(mygraph.getGraphType()==0) {
		  mygraph.setSpline(shape);
		}
		else {
			JOptionPane.showMessageDialog(null,"Cannot use this function !");
		}
	}
	private void changeLayout(String layout) {
		if(mygraph.getGraphType()==0) {
		  mygraph.setLayout(layout);
		}
		else {
			JOptionPane.showMessageDialog(null,"Cannot use this function !");
		}
	};
	private void changeRankdir(String dir) {
		  mygraph.setRankdir(dir);
	};
    private void SetDefaultSetting() {
    	if(mygraph.getGraphType()==0) {
    	  mygraph.setDefault();
    	}
    	else {
			JOptionPane.showMessageDialog(null,"Cannot use this function !");
		}
    }
	private void showHelp() {
		if (frameHelp == null) {
			frameHelp = new HelpAndAbout(0, "Help");
		}
		frameHelp.setVisible(true);
	}

	private void showAbout() {
		if (frameAbout == null) {
			frameAbout = new HelpAndAbout(1, "About");
		}
		frameAbout.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// select button in paint
		if (e.getSource() == btnNew) {
			actionNew();
			NewFrame.dispose();
		}
		if (e.getSource() == btnZoomin) {
			try {
				actionZoomin();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == btnZoomout) {
			try {
				actionZoomout();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// select point
		if (e.getSource() == cbbBeginPoint || e.getSource() == cbbEndPoint) {
			actionChoosePoint();
		}
		if (e.getSource() == btnRun) {
			if(!NewFrame.isShowing()) {
			try {
				ActionRun();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		}
		if (e.getSource() == btnCreate) {
			if(!NewFrame.isShowing()) {
			try {
				CreateRandomGraph();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		}
		// select menu bar
		if (command == "New") {
			actionNew();
			NewFrame.dispose();
		}
		if (command == "Open" || e.getSource() == btnOpen) {
			if(!NewFrame.isShowing()) {
			try {
				actionOpen();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		}
		if (command == "Save" || e.getSource() == btnSave) {
			try {
				actionSave();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (command == "Background color") {
			changeBackgroundColor();
		}
		if (command == "Point color") {
			changePointColor();
		}
		if (command == "Edge color") {
			changeEdgeColor();
		}
		if (command == "Font color") {
			changeFontColor();
		}
	    if (command == "Default Setting") {
	    	SetDefaultSetting();
		}
		if (command == "Point Shape") {
			createPointShapeLabel();
		}
		if (command == "Edge Shape") {
			createEdgeShapeLabel();
		}
		if (command == "Arrow Shape") {
			createArrowShapeLabel();
		}
		if (command == "Spline") {
			createSplineLabel();
		}
		if (command == "Neato") {
			changeLayout("neato");
		}
		if (command == "Dot") {
			changeLayout("dot");
		}
		if (command == "TB") {
			changeRankdir("TB");
		}
		if (command == "BT") {
			changeRankdir("BT");
		}
		if (command == "LR") {
			changeRankdir("LR");
		}
		if (command == "RL") {
			changeRankdir("RL");
		}
		if (command == "Exit") {
			System.exit(0);
		}
		if (command == "About") {
			showAbout();
		}
		if (command == "Help") {
			showHelp();
		}

	}
		
	
}

