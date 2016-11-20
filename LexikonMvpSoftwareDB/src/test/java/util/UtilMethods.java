package util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.ITable;

import transferObjects.TranslationDataset;

public class UtilMethods {

	public static String[] convertITableToArray(ITable actualTable, String attribute) throws Exception {

		int rows = actualTable.getRowCount();
		String[] actualArray = new String[rows];
		for (int row = 0; row < rows; row++) {
			actualArray[row] = (String) actualTable.getValue(row, attribute);
		}
		return actualArray;
	}

	public static TranslationDataset flexibleLastTranslationSetOfITable(ITable actualTable, int substractFormLastRow) throws Exception {

		int lastRow = actualTable.getRowCount()-1;
		
		TranslationDataset translation = new TranslationDataset((String) actualTable.getValue(lastRow-substractFormLastRow, "TRANSLATIONS_NAME"),
																(String) actualTable.getValue(lastRow-substractFormLastRow, "TRANSLATIONS_DESCRIPTION"),
																(int) actualTable.getValue(lastRow-substractFormLastRow, "LANGUAGES_LANGUAGES_ID"),
																(int) actualTable.getValue(lastRow-substractFormLastRow, "TERM_TERM_ID"));
		return translation;
	}
		
	public static int flexibleLastTechnicalTermSpecialtyOfITable(ITable actualTable, int substractFormLastRow) throws Exception {

		int lastRow = actualTable.getRowCount()-1;
		return (int) actualTable.getValue(lastRow-substractFormLastRow, "SPECIALTY_TERM_ID");
	}

	public static String[] convertValuesFromObjectClasstypeAndPropertyFromListToArray(List list, Class className, String propertyName) throws Exception {

		Class c = Class.forName(className.getName());
		Class[] paramTypes = {};
		// paramTypes[0]=String.class;
		Method m = c.getDeclaredMethod(propertyName, paramTypes);

		int rows = list.size();
		String[] actualArray = new String[rows];
		for (int row = 0; row < rows; row++) {
			actualArray[row] = (String) m.invoke(list.get(row));
		}
		return actualArray;
	}

	public static String flexibleLastStringValueOfList(List list, Class className, String propertyName, int substractFormLastRow) throws Exception {

		Class c = Class.forName(className.getName());
		Class[] paramTypes = {};
		Method m = c.getDeclaredMethod(propertyName, paramTypes);

		int lastRow = list.size()-1;
		return (String) m.invoke(list.get(lastRow-substractFormLastRow));
	}

	public static int flexibleLastIntegerValueOfList(List list, Class className, String propertyName, int substractFormLastRow) throws Exception {

		Class c = Class.forName(className.getName());
		Class[] paramTypes = {};
		Method m = c.getDeclaredMethod(propertyName, paramTypes);

		int lastRow = list.size()-1;
		return (int) m.invoke(list.get(lastRow-substractFormLastRow));
	}

}
