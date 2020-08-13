/**
 * Password Generator
 * An instance of this class is used to generate a password.
 * Where you can define the length, whether to use symbols, and a multiplier.
 */
package com.danield.PasswordGenerator;
import java.util.*; // Random, List(ArrayList,LinkedList,Vector,Stack)
import java.lang.Character;

/**
 * An instance of this class is used to generate a password.
 * Where you can define the length, whether to use symbols, and a multiplier.
 * @author Daniel D
 * @version 0.1
 */
public class PwGen {
	
	private final String errmsgPwToShort = "Choose a password length >= 4";
	private final String lowerLetters = "abcdefghijklmnopqrstuvwxyz";
	private final String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String numbers = "0123456789";
	private final String symbols = ".,-_+*#$/%§!?=";
	private final int shuffTimes = 200; // used by method ShuffleStrings()
	private boolean bSymbols;
	private int length;
	private Random rnd = new Random();
	
	/**
	 * Default set up
	 */
	public PwGen() {
		length = 10;
		bSymbols = true;
	}
	
	/**
	 * Custom set up
	 * @param length
	 * @param symbols
	 */
	public PwGen(int length, boolean symbols) {
		this.length = length;
		bSymbols = symbols;
	}
	
	/**
	 * Generate one password
	 * @return string: password
	 */
	public String Generate() {
		if (this.length < 4)
			return errmsgPwToShort;
		return CalcPw();
	}
	
	/**
	 * Generate one password
	 * @param length
	 * @return string: password
	 */
	public String Generate(int length) {
		if (length < 4)
			return errmsgPwToShort;
		this.length = length;
		return CalcPw();
	}
	
	/**
	 * Generate one password
	 * @param length
	 * @param symbols
	 * @return string: password
	 */
	public String Generate(int length, boolean symbols) {
		if (length < 4)
			return errmsgPwToShort;
		this.length = length;
		bSymbols = symbols;
		return CalcPw();
	}
	
	/**
	 * Set password length
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Whether or not to use symbols
	 * @param symbols
	 */
	public void setSymbols(boolean symbols) {
		bSymbols = symbols;
	}
	
	//########################################PRIVATE METHODS########################################
	
	/**
	 * Swaps two positions in a string
	 * @param str
	 * @param i0
	 * @param i1
	 * @return string: swapped string
	 */
	private String Swap(String str, int i0, int i1) {
		char[] ca = str.toCharArray();
		char tmp = ca[i0];
		ca[i0] = ca[i1];
		ca[i1] = tmp;
		return new String(ca);
	}
	
	/**
	 * Make sure that the password contains at least 1 lower 1 upper 1 number (1 symbol)
	 * @param pw
	 * @return boolean: true or false
	 */
	private boolean ValidatePassword(String pw) {
		boolean lower = false;
		boolean upper = false;
		boolean digit = false;
		boolean symbol = false;
		char[] tmp = pw.toCharArray();
		
		// check
		for (int i = 0; i < tmp.length; i++) {
			if (!digit && Character.isDigit(tmp[i])) { digit = true; continue; }
			if (!lower || !upper && Character.isLetter(tmp[i])) {
				if (!lower && Character.isLowerCase(tmp[i])) { lower = true; continue; }
				if (!upper && Character.isUpperCase(tmp[i])) { upper = true; continue; }
			}
			if (bSymbols && !symbol && !Character.isLetter(tmp[i]) && !Character.isDigit(tmp[i])) { symbol = true; }
		}
		
		// return
		if (lower && upper && digit) {
			if (bSymbols && symbol) { return true; }
			else if (bSymbols && !symbol) { return false; }
			else { return true; }
		}
		return false;
	}
	
	/**
	 * Password Generator
	 * @return string: password
	 */
	private String CalcPw() {
		char[] characters = ShuffleStrings().toCharArray();
		String password = "";
		
		// Generate random numbers
		List<Integer> rndNums = new ArrayList<Integer>();
		while (rndNums.size() != length)
			rndNums.add(rnd.nextInt(characters.length));
		
		// Generate one password
		while (password.length() != length) {
			password += characters[rndNums.get(0)];
			rndNums.remove(0);
		}
		
		// Make sure to return a valid password
		if (!ValidatePassword(password)) { return CalcPw(); }
		return password;
	}
	
	/**
	 * Combine multiple strings into one large string,
	 * then shuffle positions "shuffTimes" times.
	 * This method is supposed to add extra randomness to the Generator
	 * @return string: shuffled string
	 */
	private String ShuffleStrings() {
		String shuffStr = lowerLetters + upperLetters + numbers;
		if (bSymbols)
			shuffStr += symbols;
		
		// Generate random numbers
		List<Integer> rndNums = new ArrayList<Integer>();
		while (rndNums.size() < shuffTimes)
			rndNums.add(rnd.nextInt(shuffStr.length()-1));
		
		// Swap 2 chars
		while (rndNums.size() >= 2) {
			shuffStr = Swap(shuffStr, rndNums.get(0), rndNums.get(1));
			rndNums.remove(0);
			rndNums.remove(0);
		}
		
		return shuffStr;
	}
}
