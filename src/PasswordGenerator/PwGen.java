package PasswordGenerator;
import java.util.*; // Random, List(ArrayList,LinkedList,Vector,Stack)
import java.lang.Character;

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
	
	// Default set up
	public PwGen() {
		this.length = 10;
		this.bSymbols = true;
	}
	
	// Custom set up
	public PwGen(int length, boolean symbols) {
		this.length = length;
		this.bSymbols = symbols;
	}
	
	// Generate one password
	public String Generate() {
		if (this.length < 4)
			return errmsgPwToShort;
		return CalcPw();
	}
	
	// Generate one password
	public String Generate(int length) {
		if (length < 4)
			return errmsgPwToShort;
		this.length = length;
		return CalcPw();
	}
	
	// Generate one password
	public String Generate(int length, boolean symbols) {
		if (length < 4)
			return errmsgPwToShort;
		this.length = length;
		this.bSymbols = symbols;
		return CalcPw();
	}
	
	// Set password length
	public void setLength(int length) {
		this.length = length;
	}
	
	// Whether or not to use symbols
	public void setSymbols(boolean symbols) {
		this.bSymbols = symbols;
	}
	
	//########################################PRIVATE METHODS########################################
	
	// Swaps two positions in a string
	private String Swap(String str, int i0, int i1) {
		char[] ca = str.toCharArray();
		char tmp = ca[i0];
		ca[i0] = ca[i1];
		ca[i1] = tmp;
		return new String(ca);
	}
	
	// Make sure that the password contains at least 1 lower 1 upper 1 number (1 symbol)
	private boolean ValidatePassword(String pw) {
		boolean lowerLetter = false;
		boolean upperLetter = false;
		boolean digit = false;
		boolean symbol = false;
		char[] tmp = pw.toCharArray();
		
		// check
		for (int i = 0; i < tmp.length; i++) {
			if (!digit && Character.isDigit(tmp[i])) { digit = true; continue; }
			if (!lowerLetter || !upperLetter && Character.isLetter(tmp[i])) {
				if (!lowerLetter && Character.isLowerCase(tmp[i])) { lowerLetter = true; continue; }
				if (!upperLetter && Character.isUpperCase(tmp[i])) { upperLetter = true; continue; }
			}
			if (this.bSymbols && !symbol && !Character.isLetter(tmp[i]) && !Character.isDigit(tmp[i])) { symbol = true; }
		}
		
		// return
		if (lowerLetter && upperLetter && digit) {
			if (this.bSymbols && symbol) { return true; }
			else if (this.bSymbols && !symbol) { return false; }
			else { return true; }
		}
		return false;
	}
	
	// Password Generator
	private String CalcPw() {
		char[] characters = ShuffleStrings().toCharArray();
		String password = "";
		
		// Generate random numbers
		List<Integer> rndNums = new ArrayList<Integer>();
		while (rndNums.size() != this.length)
			rndNums.add(rnd.nextInt(characters.length));
		
		// Generate one password
		while (password.length() != this.length) {
			password += characters[rndNums.get(0)];
			rndNums.remove(0);
		}
		
		// Make sure to return a valid password
		if (!ValidatePassword(password)) { return CalcPw(); }
		return password;
	}
	
	// Combine multiple strings into one large str, then shuffle positions "shuffTimes" times
	// This method is supposed to add extra randomness to the Generator
	private String ShuffleStrings() {
		String shuffStr = this.lowerLetters + this.upperLetters + this.numbers;
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
