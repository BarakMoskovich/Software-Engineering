/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

public class StringUtil {
	
	public static String getFirstLetters(String str) {
		char firstChar;
		String toReturn = "";
		String[] words = str.split(" ");
		boolean flag = true;
		
		for (String word : words) {
			firstChar = word.charAt(0);
			flag = true;
			
			for (int i = 0; i < toReturn.length(); i++)
				if (toReturn.toLowerCase().charAt(i) == firstChar || toReturn.toUpperCase().charAt(i) == firstChar)
					flag = false;
			
			if (flag)
				toReturn += firstChar;
		}
		
		return toReturn;
	}
	
	public static boolean isOnlyLetters(String str) {
		String abc = "abcdefghijklmnopqrstuvwxyz ";
        // String abc = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// or use regex [a-zA-Z ]
		// or Character.isLetter
		
		boolean flag= false;
		
		for (int i = 0; i < str.length(); i++) {
			flag = false;
			for (int j = 0; j < abc.length(); j++) {
				if (str.toLowerCase().charAt(i) == abc.charAt(j) || str.toUpperCase().charAt(i) == abc.charAt(j)) 
					flag = true;
			}
			if (!flag)
				return false;
		}
		
		return true;
	}
	
	public static boolean equalString(String str1, String str2) {
		if (str1.toLowerCase().equals(str2.toLowerCase()))
			return true;
		return false;
	}
}	
