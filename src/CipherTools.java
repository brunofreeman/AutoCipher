import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;

public class CipherTools {

	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //TO DO?: add more alphabets (e.g. ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789)

	public static char matchCase(char toMatch, char ref) { //converts toMatch to the same case as ref and returns toMatch
		int unicode = (int) ref;
		if (unicode >= 65 && unicode <= 90) {
			return Character.toUpperCase(toMatch);
		} else if (unicode >= 97 && unicode <= 122) {
			return Character.toLowerCase(toMatch);
		}
		return toMatch;
	}

	public static int mod(int dividend, int divisor) { //returns mathematical modulus (different from Java % operator in some cases)
		return ((dividend % divisor) + divisor) % divisor;
	}

	public static int modMultiplicativeInverse(int num, int divisor) { //returns x such that x makes num*x = 1 (mod divisor)
		for (int i = 1; i < divisor; i++) {
			if (mod(num * i, divisor) == 1) {
				return i;
			}
		}
		return -1;
	}

	public static int gcd(int a, int b) { //Euclid's algorithm
		if (b > a) {
			int temp = a;
			a = b;
			b = temp;
		}
		if (a % b == 0) {
			return b;
		} else {
			return gcd(a / b, a % b);
		}
	}

	public static boolean relativelyPrime(int a, int b) {
		return gcd(a, b) == 1;
	}

	public static boolean validKey(String key) { //checks if a potential key only contains uppercase letters
		Pattern p = Pattern.compile("^$|(?![A-Z]).");
		Matcher m = p.matcher(key);
		if (m.find()) {
			return false;
		}
		return true;
	}
    	
	public static String atbashEncrypt(String message) {
		String messageUp = message.toUpperCase();
        String atbash = "";

        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                atbash += matchCase(ALPHABET.charAt(25 - ALPHABET.indexOf(messageUp.charAt(i))), message.charAt(i)); //if letter, find inverse and add
            } else {
                atbash += message.charAt(i);
            }
        }

        return atbash;
	}

	public static String atbashDecrypt(String atbash) {
		return atbashEncrypt(atbash); //atbash encryption and decryption is the same procedure
	}

	public static String ceasarEncrypt(String message, int shift) {
		String messageUp = message.toUpperCase();
		shift = mod(shift, 26); //if shift is negative or over 25, normalize it
		String ceasar = "";

		for (int i = 0; i < message.length(); i++) {
			if (Character.isLetter(message.charAt(i))) {
				ceasar += matchCase(ALPHABET.charAt(mod(ALPHABET.indexOf(messageUp.charAt(i)) + shift, 26)), message.charAt(i)); //if letter, shift and add
			} else {
				ceasar += message.charAt(i);
			}
		}

		return ceasar;
	}

	public static String ceasarDecrypt(String ceasar, int shift) {
		return ceasarEncrypt(ceasar, -1 * shift); //ceasar decryption is an encryption with the negative of the original shift
	}

	public static String a1z26Encrypt(String message) {
		message = message.toUpperCase();
		String a1z26 = "";

		for (int i = 0; i < message.length(); i++) {
			if (Character.isLetter(message.charAt(i))) {	
				a1z26 += (ALPHABET.indexOf(message.charAt(i)) + 1); //if letter, convert to index plus one and add 
			} else {
				a1z26 += message.charAt(i);
			}
			if (i < message.length() - 1 && Character.isLetter(message.charAt(i + 1))) { //adds a dash between each character
				a1z26 += "-";
			}
		}

		a1z26 = a1z26.replaceAll(" -", " "); //fixes dash occurances where there should only be spaces
		return a1z26;
	}

	public static String a1z26Decrypt(String a1z26) {
		a1z26 = a1z26.replaceAll("(?![a-zA-Z0-9]).", "-$0-"); //adds two dashes around non-alphanumeric characters (necessary for instances like 1-2-3!)
		String[] parts = a1z26.split("-"); //splits into an array
		String message = "";

		for (int i = 0; i < parts.length; i++) {
			try {
				int num = Integer.parseInt(parts[i]); //could throw error
				if (num < 1 || num > 26) {
					throw new Exception();
				}
				message += ALPHABET.charAt(num - 1); //convert numbers back to letters
			} catch (Exception e) {
				message += parts[i];
			}
		}

		return message;
	}

	private static String vigenere(String input, String key, boolean encrypt) throws IllegalArgumentException {
		String inputUp = input.toUpperCase();
		String result = "";
		key = key.trim().toUpperCase();
		if (!validKey(key)) {
			throw new IllegalArgumentException();
		}
		int keyIndex = 0;

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) { //if letter, check encrypt and do a appropriate ceasar encryption/decryption for letter of the key
				result += encrypt ? ceasarEncrypt(Character.toString(input.charAt(i)), ALPHABET.indexOf(key.charAt(keyIndex++))) : ceasarDecrypt(Character.toString(input.charAt(i)), ALPHABET.indexOf(key.charAt(keyIndex++)));
				if (keyIndex >= key.length()) {
					keyIndex = 0;
				}
			} else {
				result += input.charAt(i);
			}
		}

		return result;
	}

	public static String vigenereEncrypt(String message, String key) throws IllegalArgumentException {
		return vigenere(message, key, true);
	}

	public static String vigenereDecrypt(String vigenere, String key) throws IllegalArgumentException {
		return vigenere(vigenere, key, false);
	}

	private static String railFence(String input, int rails, boolean encrypt) throws IllegalArgumentException {
		if (rails < 0) {
			throw new IllegalArgumentException();
		}
		if (rails == 1) {
			return input;
		} else {
			int completeCycle = (2 * rails) - 2; //letters before cycling back to same rail in same direction
			int[] lettersPerRail = new int[rails];
			lettersPerRail[0] = input.length() / completeCycle; //first and last rail are shorter
			lettersPerRail[rails - 1] = lettersPerRail[0];

			for (int i = 1; i < rails - 1; i++) {
				lettersPerRail[i] = 2 * lettersPerRail[0]; //each middle rail will be twice the length of the top and bottom
			}

			for (int i = 0; i < input.length() % completeCycle; i++) { //add on any extra letters (that did not make a full cycle)
				lettersPerRail[i]++;
			}

			if (encrypt) {
				int[][] lettersToSameRail = new int[rails][2]; //0 is down, 1 is up (e.g. [2][0] would give the number of letters until you get to the third rail again when you are currently going down)
				lettersToSameRail[0][0] = completeCycle;
				lettersToSameRail[0][1] = completeCycle;
				lettersToSameRail[rails - 1][0] = completeCycle;
				lettersToSameRail[rails - 1][1] = completeCycle;

				int halfMiddleRails = (int) Math.ceil((rails - 2) / 2.0); //Math.ceil((rails - 2) / 2.0) will yield the half the number of middle rails (plus the absolute middle if there is one), only half is needed because symmetry can be used for half
				for (int i = 1; i < halfMiddleRails + 1; i++) {
					if (i == halfMiddleRails && rails % 2 == 1) { //if the rail is in the absolute middle, it is half a cycle
						lettersToSameRail[i][0] = completeCycle / 2;
						lettersToSameRail[i][1] = completeCycle / 2;
					} else {
						lettersToSameRail[i][0] = completeCycle - (2 * i); //gets smaller as rail gets lower
						lettersToSameRail[i][1] = completeCycle - lettersToSameRail[i][0]; //going up and down add to full cycle
						lettersToSameRail[rails - 1 - i][1] = lettersToSameRail[i][0]; //symmetry
						lettersToSameRail[rails - 1 - i][0] = lettersToSameRail[i][1]; //symmetry
					}
				}

				String railFence = "";

				for (int i = 0; i < rails; i++) { //start at top rail and work way down
					for (int j = 0; j < lettersPerRail[i]; j++) { //iterate for every letter in the rail
						int originalMessageIndex = i;
						for (int k = 0; k < j; k++) {
							originalMessageIndex += lettersToSameRail[i][k % 2 == 0 ? 0 : 1]; //alternating between going up and down, add half cycles to get to next letter in rail
						}
						railFence += input.charAt(originalMessageIndex);
					}
				}

				return railFence;
			} else { //decrypting
				int[] railCounter = new int[rails]; //initializes to 0
				String message = "";

				for (int i = 0; i < input.length(); i++) {
					int railOn = i % completeCycle;
					railOn = railOn < rails ? railOn : completeCycle - railOn;
					int ciphertextIndex = railCounter[railOn]++; //looking for the n character on the rail, next time look for n+1
					for (int j = 0; j < railOn; j++) {
						ciphertextIndex += lettersPerRail[j]; //skip all the rails that come before it
					}
					message += input.charAt(ciphertextIndex);
				}

				return message;
			}
		}
	}

	public static String railFenceEncrypt(String message, int rails) throws IllegalArgumentException {
		return railFence(message, rails, true);
	}

	public static String railFenceDecrypt(String railFence, int rails) throws IllegalArgumentException {
		return railFence(railFence, rails, false);
	}

	public static String columnarEncrypt(String message, String key) throws IllegalArgumentException {
		key = key.trim().toUpperCase();
		if (!validKey(key)) {
			throw new IllegalArgumentException();
		}
		char[][] matrix = new char[key.length()][(int) Math.ceil((double) message.length() / key.length())]; //[col][row]

		for (int i = 0; i < message.length(); i++) {
			matrix[i % key.length()][i / key.length()] = message.charAt(i); //fill in matrix with message, going left to right, top to bottom
		}

		String columnar = "";

		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < key.length(); j++) {
				if (key.charAt(j) == ALPHABET.charAt(i)) { //goes to each column alphabetically, going with the leftmost in the key if there is a repeat
					for (int k = 0; k < matrix[j].length; k++) { //from top to bottom, add column to ciphertext
						columnar += matrix[j][k];
					}
				}
			}
		}

		return columnar;
	}

	public static String columnarDecrypt(String columnar, String key) throws IllegalArgumentException {
		key = key.trim().toUpperCase();
		if (!validKey(key)) {
			throw new IllegalArgumentException();
		}
		int[] lettersPerColumn = new int[key.length()];

		for (int i = 0; i < lettersPerColumn.length; i++) { //set base length of columns
			lettersPerColumn[i] = columnar.length() / key.length();
		}

		for (int i = 0; i < columnar.length() % key.length(); i++) { //add any remainder to starting columns
			lettersPerColumn[i]++;
		}

		int[] order = new int[key.length()];
		int count = 0;

		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < key.length(); j++) {
				if (key.charAt(j) == ALPHABET.charAt(i)) { //orders columns alphabetically, going with the leftmost in the key if there is a repeat
					order[j] = count++;
				}
			}
		}

		String message = "";

		for (int i = 0; i < columnar.length(); i++) {
			int ciphertextIndex = i / key.length(); //start at correct row
			for (int j = 0; j < order[i % key.length()]; j++) { //for each column the index is past
				ciphertextIndex += lettersPerColumn[indexOf(order, j)]; //add length of column
			}
			message += columnar.charAt(ciphertextIndex);
		}

		return message;
	}

	private static String affine(String input, int step, int shift, boolean encrypt) throws IllegalArgumentException {
		step = mod(step, 26); //normalize
		shift = mod(shift, 26); //normalize
		if (!relativelyPrime(step, 26)) {
			throw new IllegalArgumentException();
		}
		String result = "";

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) { //uses formula based on if encrypting or decrypting, uses modulus and shift to essentially do a ceasar and then do even jumps between letters
				result += matchCase(encrypt ? ALPHABET.charAt(mod(step * ALPHABET.indexOf(input.toUpperCase().charAt(i)) + shift, 26)) : ALPHABET.charAt(mod(modMultiplicativeInverse(step, 26) * (ALPHABET.indexOf(input.toUpperCase().charAt(i)) - shift), 26)), input.charAt(i));
			} else {
				result += result.charAt(i);
			}
		}

		return result;
	}

	public static String affineEncrypt(String message, int step, int shift) throws IllegalArgumentException {
		return affine(message, step, shift, true);
	}

	public static String affineDecrypt(String affine, int step, int shift) throws IllegalArgumentException {
		return affine(affine, step, shift, false);
	}

	public static int indexOf(int[] array, int target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target) {
				return i;
			}
		}

		return -1;
	}

	private static String quagmireEncrypt(String message, String plaintextKey, String ciphertextKey, String indicator, char indicatorUnder) throws IllegalArgumentException {
		plaintextKey = plaintextKey.trim().toUpperCase();
		ciphertextKey = ciphertextKey.trim().toUpperCase();
		indicator = indicator.trim().toUpperCase();
		indicatorUnder = Character.toUpperCase(indicatorUnder);
		if (!validQuagmireKey(plaintextKey) || !validQuagmireKey(ciphertextKey) || !validKey(indicator) || !Character.isLetter(indicatorUnder)) {
			throw new IllegalArgumentException();
		}
		String plaintextAlphabet = plaintextKey;

		for (int i = 0; i < 26; i++) { //adds the rest of the alphabet that does not appear in the plaintext key to the end of the key in order
			if (!plaintextAlphabet.contains(ALPHABET.substring(i, i + 1))) {
				plaintextAlphabet += ALPHABET.charAt(i);
			}
		}

		char[][] ciphertextAlphabets = new char[indicator.length()][26];

		for (int i = 0; i < indicator.length(); i++) { //uses the indicator to shift around a normal alphabet to make the cipher alphabets
			ciphertextAlphabets[i] = getCiphertextAlphabet(indicator.charAt(i), plaintextAlphabet.indexOf(indicatorUnder), ciphertextKey);
		}

		String quagmire  = "";

		for (int i = 0; i < message.length(); i++) {
			if (Character.isLetter(message.charAt(i))) { //if letter, cycling through letters of indicator, replace each letter of the original message with letter from appropriate cipher alphabet
				quagmire += matchCase(ciphertextAlphabets[i % indicator.length()][plaintextAlphabet.indexOf(Character.toUpperCase(message.charAt(i)))], message.charAt(i));
			} else {
				quagmire += message.charAt(i);
			}
		}

		return quagmire;
	}

	public static String quagmireIEncrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmireEncrypt(message, key, "", indicator, indicatorUnder); //no ciphertext key
	}

	public static String quagmireIIEncrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmireEncrypt(message, "", key, indicator, indicatorUnder); //no plaintext key
	}

	public static String quagmireIIIEncrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmireEncrypt(message, key, key, indicator, indicatorUnder); //plaintext key and ciphertext key are the same
	}

	public static String quagmireIVEncrypt(String message, String plaintextKey, String ciphertextKey, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmireEncrypt(message, plaintextKey, ciphertextKey, indicator, indicatorUnder); //plaintext key and ciphertext key are didfferent
	}

	public static boolean validQuagmireKey(String key) { //quagmire keys can't repeat letters
		char[] chars = key.toCharArray();
		boolean duplicates = false;

		for (int i = 0; i < chars.length; i++) {
			for (int j = i + 1; j < chars.length; j++) {
		    	if (chars[i] == chars[j]) {
		      		duplicates = true;
		    	}
		  	}
		}
		
		return key.equals("") || (!duplicates && validKey(key)); 	
	}

	public static boolean contains(char[] array, char target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target) {
				return true;
			}
		}

		return false;
	}

	public static char[] getCiphertextAlphabet(char ref, int refPos, String key) { //will start with key then rest of alphabet in order, shift to match ref and refPos
		char[] ciphertextAlphabet = new char[26];
		String alphabet = key;

		for (int i = 0; i < 26; i++) {
			if (!alphabet.contains(ALPHABET.substring(i, i + 1))) {
				alphabet += ALPHABET.charAt(i);
			}
		}

		for (int i = 0; i < 26; i++) {
			ciphertextAlphabet[i] = alphabet.charAt(mod(alphabet.indexOf(ref) - refPos + i, 26));
		}

		return ciphertextAlphabet;
	}
}