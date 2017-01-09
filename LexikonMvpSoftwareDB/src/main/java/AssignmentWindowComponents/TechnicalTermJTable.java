package AssignmentWindowComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import utilities.WinUtil;

public class TechnicalTermJTable extends JTable {

	private static final Color EVEN_ROW_COLOR = Color.DARK_GRAY;

	public TechnicalTermJTable(AssignmentTableModel assignmentTableModel) {
		super(assignmentTableModel);
		initialize();
	}

	private void initialize() {

		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getTableHeader().setBackground(WinUtil.LIGHT_BLACK);
		getTableHeader().setAlignmentY(LEFT_ALIGNMENT);
		getTableHeader().setForeground(Color.WHITE);
		getTableHeader().setFont(getTableHeader().getFont().deriveFont(Font.BOLD, 14));

		JTableHeader header = this.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(this));

		setBackground(WinUtil.ULTRA_DARK_GRAY);
		getTableHeader().setReorderingAllowed(false);
		setOpaque(false);
		setGridColor(Color.WHITE);
		setIntercellSpacing(new Dimension(0, 0));

		setShowGrid(true);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

		Component component = super.prepareRenderer(renderer, row, column);

		if (!isRowSelected(row)) {
			component.setBackground(row % 2 == 0 ? getBackground() : EVEN_ROW_COLOR);
			component.setForeground(WinUtil.DARK_WHITE);
		}
		return component;
	}

	private static class HeaderRenderer implements TableCellRenderer {

		DefaultTableCellRenderer renderer;

		public HeaderRenderer(JTable table) {
			renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
			renderer.setHorizontalAlignment(JLabel.LEFT);
			renderer.setBorder(new EmptyBorder(0, 0, 0, 0));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		}
	}
}
