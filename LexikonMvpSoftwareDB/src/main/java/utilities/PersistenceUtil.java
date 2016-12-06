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
		name = name.replaceAll("á", "a");
		name = name.replaceAll("é", "e");
		name = name.replaceAll("í", "i");
		name = name.replaceAll("ó", "o");
		name = name.replaceAll("ú", "u");
		name = name.replaceAll("ñ", "n");
		name = name.replaceAll("Á", "A");
		name = name.replaceAll("É", "E");
		name = name.replaceAll("Í", "I");
		name = name.replaceAll("Ó", "O");
		name = name.replaceAll("Ú", "U");
		name = name.replaceAll("Ñ", "N");
		name = name.replaceAll("ä", "ae");
		name = name.replaceAll("ü", "ue");
		name = name.replaceAll("ö", "oe");
		name = name.replaceAll("Ä", "Ae");
		name = name.replaceAll("Ü", "Ue");
		name = name.replaceAll("Ö", "Oe");
		name = name.replaceAll("ß", "ss");
		
		return name;
	}
}
