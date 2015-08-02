

import javax.swing.*;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.*;
import com.esri.mo2.ui.bean.*; 
import com.esri.mo2.data.feat.*; 
import com.esri.mo2.map.dpy.BaseFeatureLayer;
import com.esri.mo2.map.draw.SimpleMarkerSymbol;
import com.esri.mo2.map.draw.BaseSimpleRenderer;
import com.esri.mo2.cs.geom.*;
import java.util.Vector;
import com.esri.mo2.map.dpy.FeatureLayer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.esri.mo2.file.shp.ShapefileWriter; 


public class Addxy extends JDialog {
	Map map;
	JFileChooser jfc = new JFileChooser();
	BasePointsArray bpa = new BasePointsArray();
	Vector s2 = new Vector();
	Vector s5 = new Vector();
	Vector xCoordinate = new Vector();
	Vector yCoordinate = new Vector();
	FeatureLayer XYlayer;
	
	
	Addxy() throws IOException {
		setBounds(50,50,520,430);
		File dirInit = new File("..");
              
		jfc.setCurrentDirectory(dirInit);
		jfc.showOpenDialog(this);
		try {
			File file  = jfc.getSelectedFile();
			FileReader fred = new FileReader(file);
			BufferedReader in = new BufferedReader(fred);
			String s ;
			double x,y;
			int n = 0;
			String str;
			while ((s = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s,",");
				str = st.nextToken();
				x = Double.parseDouble(str);
				xCoordinate.addElement(x+"");
				str = st.nextToken();
				y = Double.parseDouble(str);
				yCoordinate.addElement(y+"");
				bpa.insertPoint(n++,new com.esri.mo2.cs.geom.Point(x,y));
				str = st.nextToken();
				s2.addElement(str);
				str = st.nextToken();
				s5.addElement(str);
			}
		} catch (IOException e){ System.out.println("Error in Reading file in XY");}
		XYfeatureLayer xyfl = new XYfeatureLayer(bpa,map,s2,s5,xCoordinate,yCoordinate);
		XYlayer = xyfl;
		xyfl.setVisible(true);
		map = RomanEmpire.map;
		map.getLayerset().addLayer(xyfl);
		map.redraw();
		
	}
	public void setMap(com.esri.mo2.ui.bean.Map map1){
		map = map1;
	}
}
class XYfeatureLayer extends BaseFeatureLayer {
	BaseFields fields;
	private java.util.Vector featureVector;
	public XYfeatureLayer(BasePointsArray bpa,Map map,Vector s2, Vector s5,Vector xCoordinate,Vector yCoordinate) {
		createFeaturesAndFields(bpa,map,s2,s5,xCoordinate,yCoordinate);
		BaseFeatureClass bfc = getFeatureClass("RomanEmpire Layer",bpa);
		setFeatureClass(bfc);
		BaseSimpleRenderer srd = new BaseSimpleRenderer();
		SimpleMarkerSymbol sms= new SimpleMarkerSymbol();
		sms.setType(SimpleMarkerSymbol.CIRCLE_MARKER);
		sms.setSymbolColor(new Color(0,255,0));
		sms.setWidth(10);
		srd.setSymbol(sms);
		setRenderer(srd);
		XYLayerCapabilities lc = new XYLayerCapabilities();
		setCapabilities(lc);
	}
	private void createFeaturesAndFields(BasePointsArray bpa,Map map,Vector s2,Vector s5,Vector xCoordinate,Vector yCoordinate) {
		featureVector = new java.util.Vector();
		fields = new BaseFields();
		createDbfFields();
		for(int i=0;i<bpa.size();i++) {
			BaseFeature feature = new BaseFeature();  
			feature.setFields(fields);
			com.esri.mo2.cs.geom.Point p = new com.esri.mo2.cs.geom.Point(bpa.getPoint(i));
			feature.setValue(0,p);
			feature.setValue(1,new Integer(i)); 
			feature.setValue(2,(String)s2.elementAt(i));
			feature.setValue(3,(String)s5.elementAt(i));
			feature.setValue(4,(String)xCoordinate.elementAt(i));
			feature.setValue(5,(String)yCoordinate.elementAt(i));
			feature.setDataID(new BaseDataID("Roman XY Layer",i));
			featureVector.addElement(feature);
		}
	}
	
	private void createDbfFields() {
		fields.addField(new BaseField("#SHAPE#",Field.ESRI_SHAPE,0,0));
		fields.addField(new BaseField("ID",java.sql.Types.INTEGER,9,0));
		fields.addField(new BaseField("Name",java.sql.Types.VARCHAR,40,0));
		fields.addField(new BaseField("Image",java.sql.Types.VARCHAR,30,0));
		fields.addField(new BaseField("X Coordinate",java.sql.Types.VARCHAR,30,0));
		fields.addField(new BaseField("Y Coordinate",java.sql.Types.VARCHAR,30,0));
	}
	
	public BaseFeatureClass getFeatureClass(String name,BasePointsArray bpa){
		com.esri.mo2.map.mem.MemoryFeatureClass featClass = null;
		try {
			featClass = new com.esri.mo2.map.mem.MemoryFeatureClass(MapDataset.POINT,fields);
		} catch (IllegalArgumentException iae) {}
		featClass.setName(name);
		for (int i=0;i<bpa.size();i++) {
			featClass.addFeature((Feature) featureVector.elementAt(i));
		}
		return featClass;
	}
	private final class XYLayerCapabilities extends com.esri.mo2.map.dpy.LayerCapabilities {
		XYLayerCapabilities() {
			for (int i=0;i<this.size(); i++) {
				setAvailable(this.getCapabilityName(i),true);
				setEnablingAllowed(this.getCapabilityName(i),true);
				getCapability(i).setEnabled(true);
			}
		}
	}
}

