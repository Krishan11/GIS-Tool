

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.esri.mo2.cs.geom.Envelope;
import com.esri.mo2.cs.geom.Point;
import com.esri.mo2.data.feat.BaseQueryFilter;
import com.esri.mo2.data.feat.Feature;
import com.esri.mo2.data.feat.FeatureClass;
import com.esri.mo2.data.feat.Fields;
import com.esri.mo2.data.feat.SelectionSet;
import com.esri.mo2.file.shp.ShapefileFolder;
import com.esri.mo2.file.shp.ShapefileWriter;
import com.esri.mo2.map.dpy.FeatureLayer;
import com.esri.mo2.map.dpy.Layerset;
import com.esri.mo2.map.draw.SimpleMarkerSymbol;
import com.esri.mo2.ui.bean.AcetateLayer;
import com.esri.mo2.ui.bean.Layer;
import com.esri.mo2.ui.bean.Legend;
import com.esri.mo2.ui.bean.Map;
import com.esri.mo2.ui.bean.Toc;
import com.esri.mo2.ui.bean.TocAdapter;
import com.esri.mo2.ui.bean.TocEvent;
import com.esri.mo2.ui.bean.Tool;
import com.esri.mo2.ui.ren.LayerProperties;
import com.esri.mo2.ui.tb.SelectionToolBar;
import com.esri.mo2.ui.tb.ZoomPanToolBar;

class RomanEmpire extends JFrame {
	static Map map = new Map();
	boolean helpOn = false;
	static boolean fullMap = true;
	Layer layer = new Layer();
	Layer layer2 = new Layer();
	Layer layer5 = new Layer();
	Layer layer3 = null;
	static com.esri.mo2.map.dpy.Layer layer4;
	static com.esri.mo2.map.dpy.Layer activeLayer;
	int activeLayerIndex;
	boolean lighteningOn = false;
	
	Toc toc = new Toc();
	JMenuBar mbar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu theme = new JMenu("Theme");
	JMenu layercontrol = new JMenu("LayerControl");
	JMenu help = new JMenu("Help");
	JMenu culture = new JMenu("Culture");
	
	JMenuItem attribitem = new JMenuItem("Open Attribute Table",new ImageIcon("Images/tableview.gif"));
	JMenuItem createlayeritem  = new JMenuItem("Create Layer from Selection",new ImageIcon("Images/icons/Icon0915b.jpg"));
	static JMenuItem promoteitem = new JMenuItem("Promote Selected Layer",new ImageIcon("Images/icons/promote.jpg"));
	JMenuItem demoteitem = new JMenuItem("Demote Selected Layer",new ImageIcon("Images/icons/demote.jpg"));
	JMenuItem printitem = new JMenuItem("Print",new ImageIcon("Images/icons/print.gif"));
	JMenuItem addlyritem = new JMenuItem("Add Layer",new ImageIcon("Images/icons/addtheme.gif"));
	JMenuItem remlyritem = new JMenuItem("Remove Layer",new ImageIcon("Images/icons/delete.gif"));
	JMenuItem propsitem = new JMenuItem("Legend Editor",new ImageIcon("Images/icons/properties.gif"));
	JMenuItem roaditem = new JMenuItem("Roman Roads",new ImageIcon(""));
	JMenuItem roadmap = new JMenuItem("Roman Roads Map",new ImageIcon(""));
	JMenuItem lawitem = new JMenuItem("Roman Laws",new ImageIcon(""));
	JMenuItem aqueductitem = new JMenuItem("Roman Aqueducts",new ImageIcon(""));
	JMenuItem drainitem = new JMenuItem("Roman Drains",new ImageIcon(""));
	JMenuItem helpitem = new JMenuItem("Help Topics",new ImageIcon("Images/help1.gif"));
	JMenuItem aboutitem = new JMenuItem("About Us",new ImageIcon("Images/water_icon.jpg"));
	JMenuItem contactitem = new JMenuItem("Contact Us",new ImageIcon("Images/emailicon.jpg"));
	
	HelpWindow helpWindow;
	
	String s5 = "ShapeFiles/country.shp";
	String datapathname = "";
	String legendname = "";
	
	ZoomPanToolBar zptb = new ZoomPanToolBar();
	
	static SelectionToolBar stb = new SelectionToolBar();
	
	JLabel statusLabel = new JLabel("status bar    LOC");
	
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");
	
	JPanel myjp = new JPanel();
	JPanel myjp2 = new JPanel();
	
	JToolBar jtb = new JToolBar();
	
	TocAdapter mytocadapter;
	ComponentListener complistener;
	ActionListener lis;
	ActionListener layerlis;
	ActionListener layercontrollis;
	ActionListener actlis;
	ActionListener helplis;
	ActionListener culturelis;
	ActionListener lisxy;
	ActionListener lislight;
	
	JButton print = new JButton(new ImageIcon("Images/print.gif"));
	JButton addlyrjb = new JButton(new ImageIcon("Images/addtheme.gif"));
	JButton ptrjb = new JButton(new ImageIcon("Images/pointer.gif"));
	JButton drawPtjb = new JButton(new ImageIcon("Images/draw_point_1.gif"));
	JButton helptopicjb = new JButton(new ImageIcon("Images/helptopic.gif"));
	JButton addxytheme = new JButton("XY");
	
	JButton addlight = new JButton(new ImageIcon("Images/hotlink.gif"));
	
	Arrow arrow = new Arrow();
	DrawPoint drawPt = new DrawPoint();
	
	
	Legend legend;
	Legend legend2;
	
	static Envelope env;
    
    public void lighteningBolt(com.esri.mo2.cs.geom.Point worldPoint)
    {
    	AttrTab attrtab ;
    	try
    	{
    		attrtab = new AttrTab();
    		if (map.getLayerCount() > 0)
    		{
    			double xMouse = Double.parseDouble(df.format(worldPoint.getX()));
    			double yMouse = Double.parseDouble(df.format(worldPoint.getY()));
    			int rows = attrtab.getRowCount();
    			int cols = attrtab.getColumnCount();
    			boolean flag = true; //for stopping the loop
    			int i = 0;
//  			matching the item selected
    			for( i = 0 ; i < rows && flag ;i++)
    			{
    				double xOriginal = Double.parseDouble((String)attrtab.getValueAt(i,4));
    				double yOriginal = Double.parseDouble((String)attrtab.getValueAt(i,5));
    				if( xOriginal - 0.5 < xMouse && xMouse < xOriginal + 0.5 && yOriginal - 0.5 < yMouse && yMouse < yOriginal + 0.5)
    				{
    					flag = false;
    					String waterName = (String)attrtab.getValueAt(i,2);
    					String imageName = (String)attrtab.getValueAt(i,3);
    					BrowserLaunch.openURL(imageName);
    				}
    			}
    		}
    	}catch (IOException ex) {    }
    }
    
    
    public RomanEmpire() throws ParseException{
    	
    	super("Roman Empire ");
    	
    	this.setSize(800,600);
    	
    	zptb.setMap(map);
    	stb.setMap(map);
    	setJMenuBar(mbar);
    	addlight.setEnabled(false);

    ActionListener lisZoom = new ActionListener() {
    	public void actionPerformed(ActionEvent ae){
    		if(helpOn)
    			helpWindow = new HelpWindow("<HTML><H1>Zoom Out:</H1><BR>" +
    					"Zoom Tool is much more than a simple magnifier. This tool takes <BR>" +
    					"things a few steps beyond the simple magnifier. Zoom Tool allows<BR>" +
    					"you to zoom in to the map<BR><BR>" +
    					"The Zoom Out tool can be used either by clicking on the map or by<BR>" +
    					"clicking and dragging to create a selected zoom area.<BR><BR>" + 
    					"To use the Zoom Out tool to create a selected zoom area:<BR><BR>" +
    					"1. Click the �Zoom Out� button on the Toolbar, if it is not active.<BR>" +
    					"2. Select a region to view  by clicking and dragging mouse. The<BR>" +
    					"view refreshes so that the area you selected zooms (out) the selected<BR> " +
    					"area.<BR>" +
    					"3. To make the cursor normal, simply click on the arrow icon in<BR>" +
    			" the toolbar(third icon from the left).");
    		
    		else
    			fullMap = false;
    	}};

              ActionListener liszoomout = new ActionListener() {
            	  public void actionPerformed(ActionEvent ae){
            		  if(helpOn)
            			  helpWindow = new HelpWindow("<HTML><H1>Zoom In:</H1><BR>" +
            					  "Zoom Tool is much more than a simple magnifier. This tool takes <BR>" +
            					  "things a few steps beyond the simple magnifier. Zoom Tool allows<BR>" +
            					  "you to zoom in to the map<BR><BR>" +
            					  "The Zoom In tool can be used either by clicking on the map or by<BR>" +
            					  "clicking and dragging to create a selected zoom area.<BR><BR>" + 
            					  "To use the Zoom In tool to create a selected zoom area:<BR><BR>" +
            					  "1. Click the �Zoom In� button on the Toolbar, if it is not active.<BR>" +
            					  "2. Select a region to view  by clicking and dragging mouse. The<BR>" +
            					  "view refreshes so that the area you selected zooms (in) the selected<BR> " +
            					  "area.<BR>" +
            					  "3. To make the cursor normal, simply click on the arrow icon in<BR>" +
            			  " the toolbar(third icon from the left).");            	  
            		  else
            			  fullMap = false;}};

            ActionListener mpan = new ActionListener() {
            	public void actionPerformed(ActionEvent ae){
            		if(helpOn)
            			helpWindow = new HelpWindow("<HTML><H1>Pan:</H1><BR>" +
            					"The Pan Tool allows the user to interactively move the map without<BR>" +
            					"changing the map scale.<BR><BR>" +
            					"To use the Pan tool:<BR><BR>" +
            					"1. Click on the Pan Tool to select it, the cursor will change to hand.<BR>" +
            					"like icon.<BR>" +
            					"2. Click and hold the left mouse button anywhere on the map and then<BR> " +
            					"   drag the mouse. <BR>" +
            					"3. To stop panning (dragging) lift your finger from the mouse button.<BR> " +
            					"4. To make the cursor normal, simply click on the arrow icon in<BR>" +
            			" the toolbar(third icon from the left)."); 
            		else
            			fullMap = false;}};

            ActionListener mpandirection = new ActionListener() {
            	public void actionPerformed(ActionEvent ae){
            		if(helpOn)
            			helpWindow = new HelpWindow("<HTML><H1>Pan One Direction:</H1><BR>" +
            					"The Pan Direction Tool allows the user to interactively move the map,<BR>" +
            					" without<BR>" +
            					"changing the map scale, in different directions(north, south, east, west).<BR><BR>" +
            					"To use the Pan Direction tool:<BR><BR>" +
            					"1. Click on the Pan Direction Tool and click on the arrows of the direction<BR>" +
            			" in which you want to move the map.<BR>");
            		
            		else
            			fullMap = false;}};

            ActionListener maddlayer = new ActionListener() {
            	public void actionPerformed(ActionEvent ae){
            		if(helpOn)
            			helpWindow = new HelpWindow("<HTML><H1>Add Layer:</H1><BR>" +
            					"Geographic data is represented on a map as a layer. <BR>" +
            					"A layer might represent a particular type of feature,<BR>" +
            					"such as lakes, highways, etc. A layer defines how to <BR>" +
            					"display the geographic data it references and where that<BR> " +
            					"data is located in your database. A layer references the data <BR>" +
            					"stored in geodatabases, shapefiles, and so on, rather than <BR>" +
            					"actually storing the geographic data.<BR><BR>" +
            					"To Add a Layer to the map:<BR><BR>" +
            					"1. Click the Add Layer Icon on the tool bar or go to<BR>" +
            					" File menu -> Add Layer.<BR>" +
            					"2. In Shape file name field, navigate to the folder that <BR>" +
            					"contains the layer.For Example:C:->ESRI->MOJ23->Samples->Data-><BR>" +
            					"USA->uslakes.shp<BR>" +
            					"3. Click the layer and click open.<BR>" +
            					"4. In Select a layer window, click Ok. The new layer appears on<BR> " +
            			"your map.<BR><BR>");
            		else
            			
            			fullMap = true;}};

            ActionListener lisFullExt = new ActionListener() {
            	public void actionPerformed(ActionEvent ae){
            		if(helpOn)
            			helpWindow = new HelpWindow("<HTML><H1>Zoom To Full Extent:</H1><BR>" +
            					"The Zoom Map to Full Extent tool allows users to quickly<BR>" +
            					"zoom the map back to its original default extent.<BR><BR>" +
            					"To use Zoom Map to Full Extent tool:<BR><BR>" +
            			"1. Click the globe like icon on the toolbar.<BR>");
            		
            		else
            			
            			fullMap = true;}};

            ActionListener lisprint  = new ActionListener(){
            	public void actionPerformed(ActionEvent ae){
            		if(helpOn)
            			helpWindow = new HelpWindow("<HTML><H1>Print:</H1><BR>" +
            					"Print Tool is a printer like icon on the toolbar that allows  <BR>" +
            					"the user to print the map.<BR><BR>" +
            					"To use the Print tool:<BR><BR>" +
            					"1. Click the printer like icon on the toolbar or go to File -> Print.<BR>" +
            			"2. Click Ok to print.<BR>");
            		else
            			fullMap = true;
            	}};

   
   JButton zoomInButton = (JButton)zptb.getActionComponent("ZoomIn");
   JButton zoomoutButton = (JButton)zptb.getActionComponent("ZoomOut");
   JButton panButton = (JButton)zptb.getActionComponent("Pan");
   JButton pandButton = (JButton)zptb.getActionComponent("PanOneDirection");
   JButton zoomFullExtentButton = (JButton)zptb.getActionComponent("ZoomToFullExtent");
   JButton zoomToSelectedLayerButton = (JButton)zptb.getActionComponent("ZoomToSelectedLayer");
   
   
   zoomInButton.addActionListener(lisZoom);
   zoomoutButton.addActionListener(liszoomout);
   panButton.addActionListener(mpan);
   pandButton.addActionListener(mpandirection);
   addlyrjb.addActionListener(maddlayer);
   zoomFullExtentButton.addActionListener(lisFullExt);
   zoomToSelectedLayerButton.addActionListener(lis);
   print.addActionListener(lisprint);
   addxytheme.addActionListener(lisxy);
   addlight.addActionListener(lislight);

   
        complistener = new ComponentAdapter () {
        	public void componentResized(ComponentEvent ce) {
        		if(fullMap) {
        			map.setExtent(env);
        			map.zoom(1.0);
        			map.redraw();
        		}
        	}
        };

        addComponentListener(complistener);

        lis = new ActionListener() {public void actionPerformed(ActionEvent ae){
        	if(!helpOn)
        	{
        		
        		Object source = ae.getSource();
        		if (source == print || source instanceof JMenuItem ) {
        			
        			com.esri.mo2.ui.bean.Print mapPrint = new com.esri.mo2.ui.bean.Print();
        			mapPrint.setMap(map);
        			mapPrint.doPrint();
        		}
        		else if (source == ptrjb) {
        			lighteningOn = false;
        			map.setSelectedTool(arrow);
        		}
        		else if (source == drawPtjb) {
        			map.setSelectedTool(drawPt);
        		}
        		else {
        			try {
        				AddLyrDialog aldlg=  new AddLyrDialog();
        				aldlg.setMap(map);
        				aldlg.setVisible(true);
        			} catch(IOException e){}
        		}}
        }};
        
        layercontrollis = new ActionListener() {public void
        	actionPerformed(ActionEvent ae){
        	
        	
        	String source = ae.getActionCommand();
        	System.out.println(activeLayerIndex+" active index");
        	if (source .equals("Promote Selected Layer"))
        		map.getLayerset().moveLayer(activeLayerIndex,++activeLayerIndex);
        	else
        		map.getLayerset().moveLayer(activeLayerIndex,--activeLayerIndex);
        	enableDisableButtons();
        	map.redraw();
        }};
        addxytheme.addActionListener(new ActionListener()
        		{
        	public void actionPerformed(ActionEvent ae)
        	{ 
        		String source = ae.getActionCommand();
        		addlight.setEnabled(true);
        	}
        		});
    lisxy = new ActionListener() {public void
    	actionPerformed(ActionEvent ae){
    	String source = ae.getActionCommand();
    	try {
    		Addxy axy = new Addxy();
    	} catch (IOException ex) {
    	}
    }
    
    };
    
    
        addlight.addActionListener(new ActionListener()
        		{
        	public void actionPerformed(ActionEvent ae)
        	{
        		String source = ae.getActionCommand();
        	}
        		});

        helptopicjb.addActionListener(new ActionListener()
        		{
        	public void actionPerformed(ActionEvent ae)
        	{
        		helpOn = true;
        	}
        		});
    culturelis = new ActionListener(){public void
    	actionPerformed(ActionEvent ae){
    	String source = ae.getActionCommand();
    	if (source .equals("Roman Roads") )
    	{
    		RomanRoads rr = new RomanRoads();
    		rr.setVisible(true);
    	}
    	if (source .equals("Roman Roads Map") )
    	{
    		RoadMap rm = new RoadMap();
    		rm.setVisible(true);
    	}
    	
    	if (source .equals("Roman Drains"))
    	{
    		RomanDrains rd = new RomanDrains();
    		rd.setVisible(true);
    	}
    	
    	if (source .equals("Roman Aqueducts"))
    	{
    		RomanAqueducts ra = new RomanAqueducts();
    		ra.setVisible(true);
    	}
    	if (source .equals("Roman Laws"))
    	{
    		RomanLaw rl = new RomanLaw();
    		rl.setVisible(true);
    	}
    	
    }};
    
    helplis = new ActionListener() {public void
    	actionPerformed(ActionEvent ae){
    	String source = ae.getActionCommand();
    	if (source .equals("About Us") )
    	{
    		AboutUs abu = new AboutUs();
    		abu.setVisible(true);
    	}
    	
    	if (source .equals("Contact Us"))
    	{
    		ContactUs cu = new ContactUs();
    		cu.setVisible(true);
    	}
    	
    	if (source .equals("Help Topics"))
    	{
    		HelpTopics ht = new HelpTopics();
    		ht.setVisible(true);
    	}
    	
    }};
        addlight.addActionListener(new ActionListener()
        		{
        	public void actionPerformed(ActionEvent ae)
        	{
        		try
        		{
        			activeLayer = legend.getLayer();
        			lighteningOn = true;
        			map.setSelectedTool(new Arrow());
        			
        		}catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}
        		});

    layerlis = new ActionListener() {public void actionPerformed(ActionEvent ae){
    	if(!helpOn)
    	{
    		Object source = ae.getSource();
    		if (source instanceof JMenuItem) {
    			String arg = ae.getActionCommand();
    			if(arg .equals("Add Layer")) {
    				try {
    					AddLyrDialog aldlg = new AddLyrDialog();
    					aldlg.setMap(map);
    					aldlg.setVisible(true);
    				} catch(IOException e){}
    			}
    			else if(arg == "Remove Layer") {
    				try {
    					
    					com.esri.mo2.map.dpy.Layer dpylayer =
    						legend.getLayer();
    					
    					map.getLayerset().removeLayer(dpylayer);
    					addlight.setEnabled(false);
    					map.redraw();
    					remlyritem.setEnabled(false);
    					propsitem.setEnabled(false);
    					attribitem.setEnabled(false);
    					promoteitem.setEnabled(false);
    					demoteitem.setEnabled(false);
    					
    					stb.setSelectedLayer(null);
    				} catch(Exception e) {}
    			}
    			else if(arg == "Legend Editor") {
    				LayerProperties lp = new LayerProperties();
    				lp.setLegend(legend);
    				lp.setSelectedTabIndex(0);
    				lp.setVisible(true);
    			}
    			else if (arg == "Open Attribute Table") {
    				try {
    					layer4 = legend.getLayer();
    					AttrTab attrtab = new AttrTab();
    					attrtab.setVisible(true);
    				} catch(IOException ioe){}
    				
    			}
    			
    			else if (arg=="Create Layer from Selection") {
    				com.esri.mo2.map.draw.BaseSimpleRenderer sbr = new
    				com.esri.mo2.map.draw.BaseSimpleRenderer();
    				com.esri.mo2.map.draw.SimpleFillSymbol sfs = new
    				com.esri.mo2.map.draw.SimpleFillSymbol();
    				sfs.setSymbolColor(new Color(255,255,0));
    				sfs.setType(com.esri.mo2.map.draw.SimpleFillSymbol.FILLTYPE_SOLID);
    				sfs.setBoundary(true);
    				layer4 = legend.getLayer();
    				FeatureLayer flayer2 = (FeatureLayer)layer4;

              System.out.println("has selected" + flayer2.hasSelection());
              if (flayer2.hasSelection()) {
            	  SelectionSet selectset = flayer2.getSelectionSet();
            	  FeatureLayer selectedlayer = flayer2.createSelectionLayer(selectset);
            	  sbr.setLayer(selectedlayer);
            	  sbr.setSymbol(sfs);
            	  selectedlayer.setRenderer(sbr);
            	  Layerset layerset = map.getLayerset();
            	  layerset.addLayer(selectedlayer);
            	  if(stb.getSelectedLayers() != null)
            		  promoteitem.setEnabled(true);
            	  try {
            		  legend2 = toc.findLegend(selectedlayer);
            	  } catch (Exception e) {}
            	  
            	  CreateShapeDialog csd = new CreateShapeDialog(selectedlayer);
            	  csd.setVisible(true);
            	  Flash flash = new Flash(legend2);
            	  flash.start();
            	  map.redraw();
              }
          }
              }}
        	
        }};




    toc.setMap(map);
    
    mytocadapter = new TocAdapter() {
    	public void click(TocEvent e) {
    		
    		System.out.println(activeLayerIndex+ "dex");
    		
    		legend = e.getLegend();
    		activeLayer = legend.getLayer();
    		
    		stb.setSelectedLayer((e.getLegend()).getLayer());
    		zptb.setSelectedLayer(activeLayer);
    		activeLayerIndex = map.getLayerset().indexOf(activeLayer);
    		
    		System.out.println(activeLayerIndex + "active ndex");
    		
    		remlyritem.setEnabled(true);
    		propsitem.setEnabled(true);
    		attribitem.setEnabled(true);
    		addlight.setEnabled(true);
    		enableDisableButtons();
    		
    	}
    };
    map.addMouseListener(new MouseAdapter()
    		{
    	public void mouseClicked(MouseEvent me)
    	{
    		
    		if(lighteningOn)
    		{
    			com.esri.mo2.cs.geom.Point worldPoint = map.transformPixelToWorld(me.getX(),me.getY());
    			lighteningBolt(worldPoint);
    		}
    	}});

    map.addMouseMotionListener(new MouseMotionAdapter() {
    	public void mouseMoved(MouseEvent me) {
    		com.esri.mo2.cs.geom.Point worldPoint = null;
    		if (map.getLayerCount() > 0) {
    			worldPoint = map.transformPixelToWorld(me.getX(),me.getY());
    			String s = "X:"+df.format(worldPoint.getX())+" "+
    			"Y:"+df.format(worldPoint.getY());
    			statusLabel.setText(s);
    		}
    		else
    			statusLabel.setText("X:0.000 Y:0.000");
    	}
    });

    toc.addTocListener(mytocadapter);


    remlyritem.setEnabled(false);
    propsitem.setEnabled(false);
    attribitem.setEnabled(false);
    promoteitem.setEnabled(false);
    demoteitem.setEnabled(false);
    
    printitem.addActionListener(lis);
    addlyritem.addActionListener(layerlis);
    remlyritem.addActionListener(layerlis);
    propsitem.addActionListener(layerlis);
    attribitem.addActionListener(layerlis);
    
    createlayeritem.addActionListener(layerlis);
    promoteitem.addActionListener(layercontrollis);
    demoteitem.addActionListener(layercontrollis);
    roaditem.addActionListener(culturelis);
    roadmap.addActionListener(culturelis);
    drainitem.addActionListener(culturelis);
    lawitem.addActionListener(culturelis);
    aqueductitem.addActionListener(culturelis);
    helpitem.addActionListener(helplis);
    aboutitem.addActionListener(helplis);
    contactitem.addActionListener(helplis);
    addxytheme.addActionListener(lisxy);
    addlight.addActionListener(lislight);
    
    
    file.add(addlyritem);
    file.add(printitem);
    file.add(remlyritem);
    file.add(propsitem);
    
    theme.add(attribitem);
    theme.add(createlayeritem);
    layercontrol.add(promoteitem);
    layercontrol.add(demoteitem);
    
    help.add(helpitem);
    help.add(aboutitem);
    help.add(contactitem);
    culture.add(roaditem);
    culture.add(roadmap);
    culture.add(drainitem);
    culture.add(lawitem);
    culture.add(aqueductitem);
    mbar.add(file);
    mbar.add(theme);
    mbar.add(layercontrol);
    mbar.add(help);
    mbar.add(culture);
    
    print.addActionListener(lis);
    print.setToolTipText("print map");
    addlyrjb.addActionListener(lis);
    addlyrjb.setToolTipText("add layer");
    ptrjb.addActionListener(lis);
    print.setToolTipText("printer");
    drawPtjb.addActionListener(lis);
    drawPtjb.setToolTipText("draw a point on the map");
    
    
    jtb.add(print);
    jtb.add(addlyrjb);
    jtb.add(ptrjb);
    jtb.add(drawPtjb);
    jtb.add(helptopicjb);
    
    
    jtb.add(zptb); jtb.add(stb);
    myjp2.add(statusLabel);
    jtb.add(addxytheme);
    jtb.add(addlight);
    myjp.add(jtb);
    
    
    getContentPane().add(map, BorderLayout.CENTER);
    getContentPane().add(myjp,BorderLayout.NORTH);
    getContentPane().add(myjp2,BorderLayout.SOUTH);
    
   addShapefileToMap(layer5,s5);

    getContentPane().add(toc, BorderLayout.WEST);

  }

  private void addShapefileToMap(Layer layer,String s) {
	  String datapath = s;
	  layer.setDataset("0;"+datapath);
	  map.add(layer);
  }
  
  public static void main(String[] args) throws ParseException {
	  RomanEmpire usb = new RomanEmpire();
	  usb.addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent e) {
			  System.out.println("Roman Empire");
			  System.exit(0);
		  }
	  });
	  usb.setVisible(true);
	  env = map.getExtent();
	  
  }
  private void enableDisableButtons() {
	  int layerCount = map.getLayerset().getSize();
	  if (layerCount < 2) {
		  promoteitem.setEnabled(false);
		  demoteitem.setEnabled(false);
	  }
	  else if (activeLayerIndex == 0) {
		  demoteitem.setEnabled(false);
		  promoteitem.setEnabled(true);
	  }
	  else if (activeLayerIndex == layerCount - 1) {
		  promoteitem.setEnabled(false);
		  demoteitem.setEnabled(true);
	  }
	  else {
		  promoteitem.setEnabled(true);
		  demoteitem.setEnabled(true);
	  }
  }
  class HelpWindow extends JFrame implements ActionListener {
	  public void actionPerformed(ActionEvent ae) {
		  this.setVisible(false);
		  helpOn = false;
	  }
      HelpWindow(String s)
      {
    	  
    	  JButton ok = new JButton("OK");
    	  
    	  JPanel panel1 = new JPanel();
    	  JPanel panel2 = new JPanel();
    	  JLabel help = new JLabel(s);
    	  setBounds(200,100,500,500);
    	  setTitle("Help");
    	  addWindowListener(new WindowAdapter() {
    		  public void windowClosing(WindowEvent e) {
    			  setVisible(false);
    		  } });
    	  ok.addActionListener(this);
    	  help.setHorizontalAlignment(JLabel.CENTER);
    	  help.setText(s);
    	  panel1.add(help);
    	  panel2.add(ok);
    	  
    	  getContentPane().add(panel2,BorderLayout.SOUTH);
    	  getContentPane().add(panel1,BorderLayout.CENTER);
    	  this.setVisible(true);
    	  
      }

}

}