/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

public class RecClass {
	
	public static boolean isTheSame(String str) {
		if (str.length() == 1)
			return true;
		if (str.charAt(str.length() - 1) != str.charAt(str.length() - 2)) 
			return false;
		
		return isTheSame(str.substring(0, str.length() - 1));
	}
	
	public static int numOfMod3(int[] arr, int size) {
		if (arr[size - 1] % 3 == 0)
			return (1 < size) ? 1 + numOfMod3(arr, --size) : 1;
		else
			return (1 < size) ? numOfMod3(arr, --size) : 0;				
	}
	
	public static void powerSet(int n) {
		String str = powerSetUtil(n);
		str = str.replace(",}", "}");
		System.out.println(str);
	}
	
	public static String powerSetUtil(int n) {
		if (n == 0)
			return "{}\n";
		
		String str = powerSetUtil(n - 1);
		str += str.replaceAll("}", n + ",}");
		return str;
	}
}
