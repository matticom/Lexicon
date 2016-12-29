package AssignmentWindowComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


public class TechnicalTermJTable extends JTable {

	    private static final Color EVEN_ROW_COLOR = Color.DARK_GRAY;
	    private static final Color TABLE_GRID_COLOR = Color.GRAY;

	    public TechnicalTermJTable(AssignmentTableModel assignmentTableModel) {
	        super(assignmentTableModel);
	        initialize();
	    }

	    private void initialize() {
	        
//	    	setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    	setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        getTableHeader().setBackground(EVEN_ROW_COLOR);
	        getTableHeader().setAlignmentY(LEFT_ALIGNMENT);
	        JTableHeader header = this.getTableHeader();
	        header.setDefaultRenderer(new HeaderRenderer(this));
	        setBackground(TABLE_GRID_COLOR);
	        getTableHeader().setReorderingAllowed(false);
	        setOpaque(false);
	        setGridColor(TABLE_GRID_COLOR);
	        setIntercellSpacing(new Dimension(0, 0));
	        // turn off grid painting as we'll handle this manually in order to paint
	        // grid lines over the entire viewport.
	        setShowGrid(false);
	    }
	    

	    @Override
	    public Component prepareRenderer(TableCellRenderer renderer, int row,
	                                     int column) {
	        Component component = super.prepareRenderer(renderer, row, column);
	        // if the rendere is a JComponent and the given row isn't part of a
	        // selection, make the renderer non-opaque so that striped rows show
	        // through.
	        if ( !isRowSelected( row ) )
		      {
	        	component.setBackground( row % 2 == 0 ? getBackground() : EVEN_ROW_COLOR );
		      }
	        
	        return component;
	    }
	    
	      
	    
	    private static class HeaderRenderer implements TableCellRenderer {

	        DefaultTableCellRenderer renderer;

	        public HeaderRenderer(JTable table) {
	            renderer = (DefaultTableCellRenderer)
	                table.getTableHeader().getDefaultRenderer();
	            renderer.setHorizontalAlignment(JLabel.LEFT);
	        }

	        @Override
	        public Component getTableCellRendererComponent(
	            JTable table, Object value, boolean isSelected,
	            boolean hasFocus, int row, int col) {
	            return renderer.getTableCellRendererComponent(
	                table, value, isSelected, hasFocus, row, col);
	        }
	    }
	
}
