package utilities;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagLayoutUtilities {
	
	private GridBagLayoutUtilities() {
	}
	
	private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy) {
        addGB(parentContainer, guiElement, gridx, gridy, 1, 1, GridBagConstraints.NONE, 0.0, 0.0, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0);
    }
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, int gridwidth) {
        addGB(parentContainer, guiElement, gridx, gridy, gridwidth, 1, GridBagConstraints.NONE, 0.0, 0.0, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0);
    }
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, int gridwidth, int gridheight) {
        addGB(parentContainer, guiElement, gridx, gridy, gridwidth, gridheight, GridBagConstraints.NONE, 0.0, 0.0, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0);
    }
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, int gridwidth, int gridheight, int fill) {
        addGB(parentContainer, guiElement, gridx, gridy, gridwidth, gridheight, fill, 0.0, 0.0, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0);
    }
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, double weightx, double weighty) {
        addGB(parentContainer, guiElement, gridx, gridy, 1, 1, GridBagConstraints.NONE, weightx, weighty, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0);
    } 
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, double weightx, double weighty, int ipadx, int ipady) {
        addGB(parentContainer, guiElement, gridx, gridy, 1, 1, GridBagConstraints.NONE, weightx, weighty, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), ipadx, ipady);
    }
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, double weightx, double weighty, int anchor) {
        addGB(parentContainer, guiElement, gridx, gridy, 1, 1, GridBagConstraints.NONE, weightx, weighty, anchor, new Insets(0, 0, 0, 0), 0, 0);
    }
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, double weightx, double weighty, int anchor, Insets insets) {
        addGB(parentContainer, guiElement, gridx, gridy, 1, 1, GridBagConstraints.NONE, weightx, weighty, anchor, insets, 0, 0);
    }
 
    private static void addGB(Container parentContainer, Component guiElement, int gridx, int gridy, int gridwidth, int gridheight,
            int fill, double weightx, double weighty, int anchor, Insets insets,
            int ipadx, int ipady) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.anchor = anchor;
        constraints.insets = insets;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        parentContainer.add(guiElement, constraints);
    }
}
