

import com.esri.mo2.ui.bean.Legend;

public class Flash extends Thread {
	Legend legend;
	Flash(Legend legendin) {
		legend = legendin;
	}
	public void run() {
		for (int i=0;i<12;i++) {
			try {
				Thread.sleep(500);
				legend.toggleSelected();
			} catch (Exception e) {}
		}
	}
}