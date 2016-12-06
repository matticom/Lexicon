package utilities;

public class PersistenceUtil {
	
	/**
	 * <li><b><i>convertSpecialChar</i></b> <br>
	 * <br>
	 * public static String convertSpecialChar(String name) <br>
	 * <br>
	 * Ersetzt alle spracheigenen (deutsche, spanische) Umlaute in einer Zeichenkette<br>
	 * <br>
	 *
	 * @param name
	 *            - die zu konvertierende Zeichenkette
	 *            
	 * @return String
	 *     		  - konvertierte Zeichenkette     
	 */		
	public static String convertSpecialChar(String name)
	{
		name = name.replaceAll("�", "a");
		name = name.replaceAll("�", "e");
		name = name.replaceAll("�", "i");
		name = name.replaceAll("�", "o");
		name = name.replaceAll("�", "u");
		name = name.replaceAll("�", "n");
		name = name.replaceAll("�", "A");
		name = name.replaceAll("�", "E");
		name = name.replaceAll("�", "I");
		name = name.replaceAll("�", "O");
		name = name.replaceAll("�", "U");
		name = name.replaceAll("�", "N");
		name = name.replaceAll("�", "ae");
		name = name.replaceAll("�", "ue");
		name = name.replaceAll("�", "oe");
		name = name.replaceAll("�", "Ae");
		name = name.replaceAll("�", "Ue");
		name = name.replaceAll("�", "Oe");
		name = name.replaceAll("�", "ss");
		
		return name;
	}
}
