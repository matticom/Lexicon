package windows;

import java.util.List;

import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;

import javax.swing.table.AbstractTableModel;

public class AssignmentTableModel extends AbstractTableModel {

	private final int GERMAN = 1;
	private final int SPANISH = 2;
	
	private List<TechnicalTerm> technicalTermList;
	private int numberOfRows; 
	private AssignmentTableRowObject[] tableRowObjectArray; 
	private int languageId;
	
		
	public AssignmentTableModel(List<TechnicalTerm> technicalTermList, int languageId) {
		
		this.languageId = languageId;
		this.technicalTermList = technicalTermList;
		numberOfRows = technicalTermList.size();
		tableRowObjectArray = new AssignmentTableRowObject[numberOfRows];
		tableRowObjectArray = convertTechnicalTermListToArray(technicalTermList, tableRowObjectArray);
		System.out.println("fertig!");
	}

	private AssignmentTableRowObject[] convertTechnicalTermListToArray(List<TechnicalTerm> technicalTermList, AssignmentTableRowObject[] tableRowObjectArray) {
				
		TechnicalTerm technicalTerm;
		Specialty specialty;

		for (int row = 0; row < numberOfRows; row++) {
			
			try {
				technicalTerm = technicalTermList.get(row);
				specialty = technicalTerm.getSpecialty();
				
				AssignmentTableRowObject tableRowObject = new AssignmentTableRowObject(technicalTerm.getId(), specialty.getId());
				
				copyNamesFromTerm(technicalTerm, tableRowObject);
				copyNamesFromTerm(specialty, tableRowObject);
				
				tableRowObjectArray[row] = tableRowObject;
				
			} catch (Exception e) {
				System.out.println("Something goes wrong...");
				e.printStackTrace();
			}
		}
		return tableRowObjectArray;
	}
	
	private void copyNamesFromTerm(Term term, AssignmentTableRowObject tableRowObject) {
		
		for (Translations translation : term.getTranslationList()) {
			
			if (translation.getLanguages().getId() == GERMAN) {
				if (term instanceof Specialty) {
					tableRowObject.setSpecialtyGermanName(translation.getName());
				}
				if (term instanceof TechnicalTerm) {
					tableRowObject.setTechnicalTermGermanName(translation.getName());
				}
			}
			
			if (translation.getLanguages().getId() == SPANISH) {
				if (term instanceof Specialty) {
					tableRowObject.setSpecialtySpanishName(translation.getName());
				}
				if (term instanceof TechnicalTerm) {
					tableRowObject.setTechnicalTermSpanishName(translation.getName());
				}
			}
			
			if (translation.getLanguages().getId() != SPANISH && translation.getLanguages().getId() != GERMAN) {
				throw new RuntimeException("SpracheId ist unbekannt!");
			}
		}
	}

	
	@Override
	public int getColumnCount() {
		
		return 2;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return numberOfRows;
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		
		Object value = null;
		
		if (languageId == GERMAN) {
			if (colIndex == 0) {
				value = tableRowObjectArray[rowIndex].getTechnicalTermGermanName();
			}
			if (colIndex == 1) {
				value = tableRowObjectArray[rowIndex].getSpecialtyGermanName();
			}
		}
		
		if (languageId == SPANISH) {
			if (colIndex == 0) {
				value = tableRowObjectArray[rowIndex].getTechnicalTermSpanishName();
			}
			if (colIndex == 1) {
				value = tableRowObjectArray[rowIndex].getSpecialtySpanishName();
			}
		}
		return value;
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int colIndex)
	{
		if (languageId == GERMAN) {
			if (colIndex == 0) {
				tableRowObjectArray[rowIndex].setTechnicalTermGermanName((String)value);
			}
			if (colIndex == 1) {
				tableRowObjectArray[rowIndex].setSpecialtyGermanName((String)value);
			}
		}
		
		if (languageId == SPANISH) {
			if (colIndex == 0) {
				tableRowObjectArray[rowIndex].setTechnicalTermSpanishName((String)value);
			}
			if (colIndex == 1) {
				tableRowObjectArray[rowIndex].setSpecialtySpanishName((String)value);
			}
		}
		
		fireTableCellUpdated(rowIndex, colIndex);
	}
	

}
