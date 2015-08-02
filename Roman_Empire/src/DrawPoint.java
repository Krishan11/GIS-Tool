

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.esri.mo2.cs.geom.Point;
import com.esri.mo2.map.draw.SimpleMarkerSymbol;
import com.esri.mo2.ui.bean.AcetateLayer;
import com.esri.mo2.ui.bean.Map;
import com.esri.mo2.ui.bean.Tool;

class DrawPoint extends Tool {
	Map map = RomanEmpire.map;
	SimpleMarkerSymbol sms = new SimpleMarkerSymbol();
	Point pt = new Point();
	AcetateLayer acetLayer = new AcetateLayer(){
		public void paintComponent(java.awt.Graphics g) {
			if (pt != null) {
				java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;
				g2d.setTransform(
						map.getWorldToPixelTransform().toAffine());
				g2d.setClip(map.getExtent());
				sms.draw(pt,g2d,"");
			}
		}
	};
	public DrawPoint () {
		sms.setType(SimpleMarkerSymbol.CIRCLE_MARKER);
		sms.setWidth(6);
		sms.setSymbolColor(Color.red);
		map.add(acetLayer);
	}
	public void mouseClicked(MouseEvent me) {
		pt = map.transformPixelToWorld(me.getX(),me.getY());
		acetLayer.repaint();
	}
}

