package utilities;

import java.awt.*;
import static java.awt.GridBagConstraints.*;
import javax.swing.*;
 
public class GridBagLayoutExample extends JPanel {
 
    //GB arguments:
    private int gridx, gridy, gridwidth, gridheight, fill, anchor, ipadx, ipady;
    private double weightx, weighty;
    private Insets insets;
    // GB Insets:
    private int top, left, bottom, right;
    private final Insets insetsTop = new Insets(top = 5, left = 0, bottom = 15, right = 0);
    private final Insets insetsLabel = new Insets(top = 0, left = 10, bottom = 6, right = 5);
    private final Insets insetsText = new Insets(top = 0, left = 0, bottom = 6, right = 10);
    private final Insets insetsBottom = new Insets(top = 10, left = 0, bottom = 10, right = 0);
    //input fields:
    private JTextField name;
    private JTextField age;
    private JTextArea comment;
    private JButton btOK;
 
    public GridBagLayoutExample() {
        setLayout(new GridBagLayout());
        example();
    }
 
    private void example() {
        //header row:
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(new JLabel("Registration form"), gridx = 1, gridy = 1,
                gridwidth = 2, gridheight, fill,
                weightx = 0, weighty, anchor,
                insets = insetsTop);
        //name row (label):
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(new JLabel("Name"), gridx = 1, gridy = 2,
                gridwidth, gridheight, fill,
                weightx, weighty, anchor = LINE_START,
                insets = insetsLabel);
        //name row (textfield):
        name = new JTextField();
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(name, gridx = 2, gridy = 2,
                gridwidth, gridheight, fill = HORIZONTAL,
                weightx = 1.0, weighty, anchor,
                insets = insetsText);
        //age row (label):
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(new JLabel("Age"), gridx = 1, gridy = 3,
                gridwidth, gridheight, fill,
                weightx = 0, weighty, anchor = LINE_START,
                insets = insetsLabel);
        //age row (textfield):
        age = new JTextField(3);
        age.setMinimumSize(age.getPreferredSize());
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(age, gridx = 2, gridy = 3,
                gridwidth, gridheight, fill = HORIZONTAL,
                weightx, weighty, anchor = LINE_START,
                insets = insetsText);
        //comment row (label):
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(new JLabel("Comment"), gridx = 1, gridy = 4,
                gridwidth, gridheight, fill,
                weightx, weighty, anchor = FIRST_LINE_START,
                insets = insetsLabel);
        //comment row (textfield):
        comment = new JTextArea();
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(new JScrollPane(comment), gridx = 2, gridy = 4,
                gridwidth, gridheight, fill = BOTH,
                weightx = 0, weighty = 1.0, anchor,
                insets = insetsText);
        //trailer row:
        btOK = new JButton("OK");
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(btOK, gridx = 1, gridy = 5,
                gridwidth = 2, gridheight, fill,
                weightx, weighty, anchor,
                insets = insetsBottom);
    }
 
    // Convenience method, used to add components without internal padding:
    private void addGB(final Component component, final int gridx, final int gridy,
            final int gridwidth, final int gridheight,
            final int fill, final double weightx, final double weighty,
            final int anchor, final Insets insets) {
        addGB(component, gridx, gridy, gridwidth, gridheight, fill, weightx, weighty, anchor, insets, ipadx, ipady);
    }
 
    private void addGB(final Component component, final int gridx, final int gridy,
            final int gridwidth, final int gridheight,
            final int fill, final double weightx, final double weighty,
            final int anchor, final Insets insets,
            final int ipadx, final int ipady) {
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
        add(component, constraints);
    }
 
    private void setDefaultValuesGB() {
        // This method sets the default values for all GridBagConstraints,
        // so we don't have to specify them with each addGB(...)
        gridx = RELATIVE;
        gridy = RELATIVE;
        gridwidth = 1;
        gridheight = 1;
        fill = NONE;
        weightx = 0.0;
        weighty = 0.0;
        anchor = CENTER;
        insets = new Insets(0, 0, 0, 0);
        ipadx = 0;
        ipady = 0;
    }
 
    public static void main(final String... args) {
        SwingUtilities.invokeLater(new Runnable() {
 
            @Override
            public void run() {
                JFrame frame = new JFrame("GridBagLayout Example");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(450, 350);
                frame.setLocationRelativeTo(null);
                frame.setContentPane(new GridBagLayoutExample());
                frame.setVisible(true);
            }
        });
    }
}
