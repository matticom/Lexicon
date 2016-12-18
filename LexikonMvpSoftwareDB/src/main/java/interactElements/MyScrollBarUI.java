package interactElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

import utilities.WinUtil;

public class MyScrollBarUI extends MetalScrollBarUI
{
	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds)
	{
		if (thumbBounds.isEmpty() || !scrollbar.isEnabled())
		{
			return;
		}

		int w = thumbBounds.width;
		int h = thumbBounds.height;

		g.translate(thumbBounds.x, thumbBounds.y);

		g.setColor(thumbDarkShadowColor);
		g.drawRect(0, 0, w - 1, h - 1);
		g.setColor(utilities.WinUtil.LIGHT_BLACK);
		g.fillRect(0, 0, w - 1, h - 1);

		g.setColor(new Color(184, 207, 229));
		g.drawLine(1, 1, 1, h - 2);
		g.drawLine(2, 1, w - 3, 1);

		g.setColor(thumbLightShadowColor);
		g.drawLine(2, h - 2, w - 2, h - 2);
		g.drawLine(w - 2, 1, w - 2, h - 3);

		g.translate(0 - thumbBounds.x, 0 - thumbBounds.y);
	}
	
	
}
