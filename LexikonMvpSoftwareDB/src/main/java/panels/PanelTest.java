package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import eventHandling.PanelEventTransferObject;

public class PanelTest extends MyPanel {

	private JPanel 	container, start, links, rechts;
	
	
	
	public PanelTest() {
		
		super(null, 0.8);
		test();
	}

	public void test() {
		container = new JPanel(new BorderLayout());
		
		start = new JPanel(); 
		start.setPreferredSize(new Dimension(1300, 200));
		start.setBackground(Color.CYAN);
		container.setMinimumSize(new Dimension(300, 100));
		container.add(start, BorderLayout.PAGE_START);
		
		links = new JPanel();
		links.setPreferredSize(new Dimension(200, 400));
		links.setBackground(Color.YELLOW);
		container.add(links, BorderLayout.LINE_START);
		
		rechts = new JPanel(); 
		rechts.setPreferredSize(new Dimension(600, 2000));
		rechts.setBackground(Color.GREEN);
		container.add(rechts, BorderLayout.LINE_END);
		
		this.getViewport().add(container);
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}
	
}
