import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.HashMap;

public class CipherTools {

	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //TO DO?: add more alphabets (e.g. ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789)
	public static final String ATBASH_DESCRIPTION = "The Atbash cipher replaces each letter in the plaintext with its inverse (e.g. \"A\" and \"Z\" turn into each other and \"B\" and \"Y\" turn into each other)."; //is equivalent to Affine with step and shift of 25
	public static final String CAESAR_DESCRIPTION = "The Caesar cipher shifts each letter in the plaintext by a certain amount (e.g. a shift of 3 would make \"A\" turn into \"D\" and \"Y\" turn into \"B\").";
	public static final String A1Z26_DESCRIPTION = "The A1Z26 cipher replaces each letter in the plaintext with its index surrounded by dashes (e.g. \"abc zyx\" becomes \"1-2-3 26-25-24\").";
	public static final String VIGENERE_DESCRIPTION = "The Vigen\u00E9re cipher is a series of cycling Caesar ciphers indicated by a key (e.g. with a key of \"ABYZ\", the 1st letter of the plaintext will be encrypted with a Caesar cipher with a shift of 0, the 2nd with a shift 1, the 3rd with a shift of 24, the 4th with a shift of 25, and then the cycle repeats).";
	public static final String RAIL_FENCE_DESCRIPTION = "The Rail Fence cipher scrambles the plaintext by writing the message in a zigzag pattern, going from left to right and starting at the top, across a specified number of rails. The ciphertext is then obtained by reading off each rail from left to right, starting with the top rail and ending with the bottom rail.";
	public static final String COLUMNAR_DESCRIPTION = "The Columnar cipher scrambles the plaintext by writing the message in rows of a length determined by a key. The ciphertext is obtained by reading columns from top to bottom in an order determined by the alphabetical order of the key, with ties going to the leftmost letter.";
	public static final String AFFINE_DESCRIPTION = "The Affine cipher replaces each letter in the plaintext using the function (ax+b) mod c, where a is an integer relatively prime to c, x is the index of the plaintext letter in the alphabet, b is an integer, and c is the length of the alphabet (usually 26).";
	public static final String QUAGMIRE_I_DESCRIPTION = "The Quagmire I cipher uses a key (cannot repeat letters), indicator key, and a letter the indicator key is under. A plaintext alphabet is created by writing the key followed by the letters, in alphabetical order, that do not appear in the key. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by shifting a normal alphabet so that the corresponding letter of the indicator key is at the same index of the letter that the indicator key was determined to be under in the plaintext alphabet and then filling in the rest of the alphabetical by taking a regular alphabet and shifting it until it matches the positioned letter from the indicator key. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.";
	public static final String QUAGMIRE_II_DESCRIPTION = "The Quagmire II cipher uses a key (cannot repeat letters), indicator key, and a letter the indicator key is under. The plaintext alphabet is simply the normal alphabet. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by writing the key followed by the letters, in alphabetical order, that do not appear in the key and then shifting that alphabet so that the corresponding letter of the indicator key is placed at the same index of the letter that the indicator key was determined to be under in the plaintext alphabet. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.";
	public static final String QUAGMIRE_III_DESCRIPTION = "The Quagmire III cipher uses a key (cannot repeat letters), indicator key, and a letter the indicator key is under. A plaintext alphabet is created by writing the key followed by the letters, in alphabetical order, that do not appear in the key. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by shifting the plaintext alphabet so that the corresponding letter of the indicator key is placed at the same index of the letter that the indicator key was determined to be under in the plaintext alphabet. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.";
	public static final String QUAGMIRE_IV_DESCRIPTION = "The Quagmire IV cipher uses a plaintext key (cannot repeat letters), ciphertext key (cannot repeat letters), indicator key, and a letter the indicator key is under. A plaintext alphabet is created by writing the plaintext key followed by the letters, in alphabetical order, that do not appear in the key. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by writing the ciphertext key followed by the letters, in alphabetical order, that do not appear in the key and then shifting that alphabet so that the corresponding letter of the indicator key is placed at the same index of the letter that the indicator key was determined to be under in the plaintext alphabet. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.";
	public static final String BACONIAN_DESCRIPTION = "The Baconian cipher replaces each letter in the plaintext with a specific five-letter combination of As and Bs (e.g. \"A\" becomes \"AAAAA\" and \"Z\" becomes \"BBAAB\").";

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
                atbash += matchCase(ALPHABET.charAt(ALPHABET.length() - 1 - ALPHABET.indexOf(messageUp.charAt(i))), message.charAt(i)); //if letter, find inverse and add
            } else {
                atbash += message.charAt(i);
            }
        }

        return atbash;
	}

	public static String atbashDecrypt(String atbash) {
		return atbashEncrypt(atbash); //atbash encryption and decryption is the same procedure
	}

	public static String caesarEncrypt(String message, int shift) {
		String messageUp = message.toUpperCase();
		shift = mod(shift, ALPHABET.length()); //if shift is negative or over alphabet length, normalize it
		String caesar = "";

		for (int i = 0; i < message.length(); i++) {
			if (Character.isLetter(message.charAt(i))) {
				caesar += matchCase(ALPHABET.charAt(mod(ALPHABET.indexOf(messageUp.charAt(i)) + shift, ALPHABET.length())), message.charAt(i)); //if letter, shift and add
			} else {
				caesar += message.charAt(i);
			}
		}

		return caesar;
	}

	public static String caesarDecrypt(String caesar, int shift) {
		return caesarEncrypt(caesar, -1 * shift); //caesar decryption is an encryption with the negative of the original shift
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
				int num = Integer.parseInt(parts[i]); //throws error when not number, go to exception
				if (num < 1 || num > ALPHABET.length()) {
					throw new Exception();
				}
				message += ALPHABET.charAt(num - 1); //convert numbers back to letters
			} catch (Exception e) { //MAKE SPECIFIC
				message += parts[i];
			}
		}

		return message;
	}

	private static String vigenere(String input, String key, boolean encrypt) throws IllegalArgumentException {
		String inputUp = input.toUpperCase();
		String output = "";
		key = key.trim().toUpperCase();
		if (!validKey(key)) {
			throw new IllegalArgumentException();
		}
		int keyIndex = 0;

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) { //if letter, check encrypt and do a appropriate caesar encryption/decryption for letter of the key
				output += encrypt ? caesarEncrypt(Character.toString(input.charAt(i)), ALPHABET.indexOf(key.charAt(keyIndex++))) : caesarDecrypt(Character.toString(input.charAt(i)), ALPHABET.indexOf(key.charAt(keyIndex++)));
				if (keyIndex >= key.length()) {
					keyIndex = 0;
				}
			} else {
				output += input.charAt(i);
			}
		}

		return output;
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

		for (int i = 0; i < ALPHABET.length(); i++) {
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

		for (int i = 0; i < ALPHABET.length(); i++) {
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
		step = mod(step, ALPHABET.length()); //normalize
		shift = mod(shift, ALPHABET.length()); //normalize
		if (!relativelyPrime(step, ALPHABET.length())) {
			throw new IllegalArgumentException();
		}
		String output = "";

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) { //uses formula based on if encrypting or decrypting, uses modulus and shift to essentially do a caesar and then do even jumps between letters
				output += matchCase(encrypt ? ALPHABET.charAt(mod(step * ALPHABET.indexOf(input.toUpperCase().charAt(i)) + shift, ALPHABET.length())) : ALPHABET.charAt(mod(modMultiplicativeInverse(step, ALPHABET.length()) * (ALPHABET.indexOf(input.toUpperCase().charAt(i)) - shift), ALPHABET.length())), input.charAt(i));
			} else {
				output += input.charAt(i);
			}
		}

		return output;
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

	private static String quagmire(String input, String plaintextKey, String ciphertextKey, String indicator, char indicatorUnder, boolean encrypt) throws IllegalArgumentException {
		plaintextKey = plaintextKey.trim().toUpperCase();
		ciphertextKey = ciphertextKey.trim().toUpperCase();
		indicator = indicator.trim().toUpperCase();
		indicatorUnder = Character.toUpperCase(indicatorUnder);
		if (!validQuagmireKey(plaintextKey) || !validQuagmireKey(ciphertextKey) || !validKey(indicator) || !Character.isLetter(indicatorUnder)) {
			throw new IllegalArgumentException();
		}
		String plaintextAlphabet = plaintextKey;

		for (int i = 0; i < ALPHABET.length(); i++) { //adds the rest of the alphabet that does not appear in the plaintext key to the end of the key in order
			if (!plaintextAlphabet.contains(ALPHABET.substring(i, i + 1))) {
				plaintextAlphabet += ALPHABET.charAt(i);
			}
		}

		char[][] ciphertextAlphabets = new char[indicator.length()][ALPHABET.length()];

		for (int i = 0; i < indicator.length(); i++) { //uses the indicator to shift around a normal alphabet to make the cipher alphabets
			ciphertextAlphabets[i] = getCiphertextAlphabet(indicator.charAt(i), plaintextAlphabet.indexOf(indicatorUnder), ciphertextKey);
		}

		String output = "";

		for (int i = 0; i < input.length(); i++) {
			if (encrypt) {
				if (Character.isLetter(input.charAt(i))) { //if letter, cycling through letters of indicator, replace each letter of the original message with letter from appropriate cipher alphabet
					output += matchCase(ciphertextAlphabets[i % indicator.length()][plaintextAlphabet.indexOf(Character.toUpperCase(input.charAt(i)))], input.charAt(i));
				} else {
					output += input.charAt(i);
				}
			} else {
				if (Character.isLetter(input.charAt(i))) { //if letter, replace with letter from plaintext alphabet at same position of letteer from appropriate ciphertext alphabet
					output += matchCase(plaintextAlphabet.charAt(indexOf(ciphertextAlphabets[i % indicator.length()], Character.toUpperCase(input.charAt(i)))), input.charAt(i));
				} else {
					output += input.charAt(i);
				}
			}	
		}

		return output;
	}

	public static String quagmireIEncrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, key, "", indicator, indicatorUnder, true); //no ciphertext key
	}

	public static String quagmireIIEncrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, "", key, indicator, indicatorUnder, true); //no plaintext key
	}

	public static String quagmireIIIEncrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, key, key, indicator, indicatorUnder, true); //plaintext key and ciphertext key are the same
	}

	public static String quagmireIVEncrypt(String message, String plaintextKey, String ciphertextKey, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, plaintextKey, ciphertextKey, indicator, indicatorUnder, true); //plaintext key and ciphertext key are didfferent
	}

	public static String quagmireIDecrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, key, "", indicator, indicatorUnder, false); //no ciphertext key
	}

	public static String quagmireIIDecrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, "", key, indicator, indicatorUnder, false); //no plaintext key
	}

	public static String quagmireIIIDecrypt(String message, String key, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, key, key, indicator, indicatorUnder, false); //plaintext key and ciphertext key are the same
	}

	public static String quagmireIVDecrypt(String message, String plaintextKey, String ciphertextKey, String indicator, char indicatorUnder) throws IllegalArgumentException {
		return quagmire(message, plaintextKey, ciphertextKey, indicator, indicatorUnder, false); //plaintext key and ciphertext key are didfferent
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
		return indexOf(array, target) != -1;
	}

	public static char[] getCiphertextAlphabet(char ref, int refPos, String key) { //will start with key then rest of alphabet in order, shift to match ref and refPos
		char[] ciphertextAlphabet = new char[ALPHABET.length()];
		String alphabet = key;
		for (int i = 0; i < ALPHABET.length(); i++) {
			if (!alphabet.contains(ALPHABET.substring(i, i + 1))) {
				alphabet += ALPHABET.charAt(i);
			}
		}
		for (int i = 0; i < ALPHABET.length(); i++) {
			ciphertextAlphabet[i] = alphabet.charAt(mod(alphabet.indexOf(ref) - refPos + i, ALPHABET.length()));
		}
		return ciphertextAlphabet;
	}

	public static int indexOf(char[] array, char target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target) {
				return i;
			}
		}
		return -1;
	}

	public static String baconianEncrypt(String message) {
		HashMap<Character, String> baconianMap = new HashMap<>();
		baconianMap.put('A', "AAAAA");
		baconianMap.put('B', "AAAAB");
		baconianMap.put('C', "AAABA");
		baconianMap.put('D', "AAABB");
		baconianMap.put('E', "AABAA");
		baconianMap.put('F', "AABAB");
		baconianMap.put('G', "AABBA");
		baconianMap.put('H', "AABBB");
		baconianMap.put('I', "ABAAA");
		baconianMap.put('J', "ABAAB");
		baconianMap.put('K', "ABABA");
		baconianMap.put('L', "ABABB");
		baconianMap.put('M', "ABBAA");
		baconianMap.put('N', "ABBAB");
		baconianMap.put('O', "ABBBA");
		baconianMap.put('P', "ABBBB");
		baconianMap.put('Q', "BAAAA");
		baconianMap.put('R', "BAAAB");
		baconianMap.put('S', "BAABA");
		baconianMap.put('T', "BAABB");
		baconianMap.put('U', "BABAA");
		baconianMap.put('V', "BABAB");
		baconianMap.put('W', "BABBA");
		baconianMap.put('X', "BABBB");
		baconianMap.put('Y', "BBAAA");
		baconianMap.put('Z', "BBAAB");

        String baconian = "";

        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                String ciphertext = baconianMap.get(Character.toUpperCase(message.charAt(i))); //replace letter with A and B combo
                baconian += Character.isUpperCase(message.charAt(i)) ? ciphertext : ciphertext.toLowerCase(); //match case
            } else {
                baconian += message.charAt(i);
            }
        }

        return baconian;
	}

	public static String baconianDecrypt(String baconian) {
		HashMap<String, Character> baconianMap = new HashMap<>();
		baconianMap.put("AAAAA", 'A');
		baconianMap.put("AAAAB", 'B');
		baconianMap.put("AAABA", 'C');
		baconianMap.put("AAABB", 'D');
		baconianMap.put("AABAA", 'E');
		baconianMap.put("AABAB", 'F');
		baconianMap.put("AABBA", 'G');
		baconianMap.put("AABBB", 'H');
		baconianMap.put("ABAAA", 'I');
		baconianMap.put("ABAAB", 'J');
		baconianMap.put("ABABA", 'K');
		baconianMap.put("ABABB", 'L');
		baconianMap.put("ABBAA", 'M');
		baconianMap.put("ABBAB", 'N');
		baconianMap.put("ABBBA", 'O');
		baconianMap.put("ABBBB", 'P');
		baconianMap.put("BAAAA", 'Q');
		baconianMap.put("BAAAB", 'R');
		baconianMap.put("BAABA", 'S');
		baconianMap.put("BAABB", 'T');
		baconianMap.put("BABAA", 'U');
		baconianMap.put("BABAB", 'V');
		baconianMap.put("BABBA", 'W');
		baconianMap.put("BABBB", 'X');
		baconianMap.put("BBAAA", 'Y');
		baconianMap.put("BBAAB", 'Z');

		String message = "";

		for (int i = 0; i < baconian.length(); i++) {
			if (i <= baconian.length() - 5 && baconianMap.containsKey(baconian.substring(i, i + 5))) { //if the next letters are a uppercase Baconian code
					message += baconianMap.get(baconian.substring(i, i + 5)); //add appropriate character
					i += 4;
			} else if (i <= baconian.length() - 5 && baconianMap.containsKey(baconian.substring(i, i + 5).toUpperCase())) { //if the next letters are a lowercase Baconian code
					message += Character.toLowerCase(baconianMap.get(baconian.substring(i, i + 5).toUpperCase())); //add appropriate lowercase character
					i += 4;
			} else {
				message += baconian.charAt(i);
			}
		}

		return message;
	}
}