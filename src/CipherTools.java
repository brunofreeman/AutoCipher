import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;

public class CipherTools {

	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static char matchCase(char toMatch, char ref) {
		int unicode = (int) ref;
		if (unicode >= 65 && unicode <= 90) {
			return Character.toUpperCase(toMatch);
		} else if (unicode >= 97 && unicode <= 122) {
			return Character.toLowerCase(toMatch);
		}
		return toMatch;
	}

	public static int mod(int dividend, int divisor) {
		return ((dividend % divisor) + divisor) % divisor;
	}

	public static int modMultiplicativeInverse(int dividend, int divisor) {
		for (int i = 1; i < divisor; i++) {
			if (mod(dividend * i, divisor) == 1) {
				return i;
			}
		}

		return -1;
	}

	public static int indexOf(int[] array, int target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target) {
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

	public static void validateKey(String key) throws IllegalArgumentException {
		Pattern p = Pattern.compile("^$|(?![A-Z]).");
		Matcher m = p.matcher(key);
		if (m.find()) {
			throw new IllegalArgumentException();
		}
	}
    	
	public static String atbashEncrypt(String message) {
		String messageUp = message.toUpperCase();
        String atbash = "";

        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                atbash += matchCase(ALPHABET.charAt(25 - ALPHABET.indexOf(messageUp.charAt(i))), message.charAt(i));
            } else {
                atbash += message.charAt(i);
            }
        }

        return atbash;
	}

	public static String atbashDecrypt(String atbash) {
		return atbashEncrypt(atbash);
	}

	public static String ceasarEncrypt(String message, int shift) {
		String messageUp = message.toUpperCase();
		shift = mod(shift, 26);
		String ceasar = "";

		for (int i = 0; i < message.length(); i++) {
			if (Character.isLetter(message.charAt(i))) {
				ceasar += matchCase(ALPHABET.charAt(mod(ALPHABET.indexOf(messageUp.charAt(i)) + shift, 26)), message.charAt(i));
			} else {
				ceasar += message.charAt(i);
			}
		}

		return ceasar;
	}

	public static String ceasarDecrypt(String ceasar, int shift) {
		return ceasarEncrypt(ceasar, -1 * shift);
	}

	public static String a1z26Encrypt(String message) {
		message = message.toUpperCase();
		String a1z26 = "";

		for (int i = 0; i < message.length(); i++) {
			if (Character.isLetter(message.charAt(i))) {	
				a1z26 += (ALPHABET.indexOf(message.charAt(i)) + 1) ;
			} else {
				a1z26 += message.charAt(i);
			}
			if (i < message.length() - 1 && Character.isLetter(message.charAt(i + 1))) {
				a1z26 += "-";
			}
		}

		a1z26 = a1z26.replaceAll(" -", " ");
		return a1z26;
	}

	public static String a1z26Decrypt(String a1z26) {
		a1z26 = a1z26.replaceAll("(?![a-zA-Z0-9]).", "-$0-");
		String[] parts = a1z26.split("-");
		String message = "";

		for (int i = 0; i < parts.length; i++) {
			try {
				int num = Integer.parseInt(parts[i]);
				if (num < 1 || num > 26) {
					throw new Exception();
				}
				message += ALPHABET.charAt(num - 1);
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
		validateKey(key);
		int keyIndex = 0;

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) {
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
			int completeCycle = (2 * rails) - 2;
			int[] lettersPerRail = new int[rails];
			lettersPerRail[0] = input.length() / completeCycle;
			lettersPerRail[rails - 1] = lettersPerRail[0];

			for (int i = 1; i < rails - 1; i++) {
				lettersPerRail[i] = 2 * lettersPerRail[0];
			}

			for (int i = 0; i < input.length() % completeCycle; i++) {
				lettersPerRail[i]++;
			}

			if (encrypt) {
				int[][] lettersToSameRail = new int[rails][2]; //0 is down, 1 is up (e.g. [2][0] would give the number of letters until you get to the third rail again when you are currently going down)
				lettersToSameRail[0][0] = completeCycle;
				lettersToSameRail[0][1] = completeCycle;
				lettersToSameRail[rails - 1][0] = completeCycle;
				lettersToSameRail[rails - 1][1] = completeCycle;

				for (int i = 1; i < (int) Math.ceil((rails - 2) / 2.0) + 1; i++) { //Math.ceil((rails - 2) / 2.0) will yield the half the number of middle rows (plus the absolute middle if there is one), only half is needed because symmetry can be used for half
					if (i == (int) Math.ceil((rails - 2) / 2.0) && rails % 2 == 1) {
						lettersToSameRail[i][0] = completeCycle / 2;
						lettersToSameRail[i][1] = completeCycle / 2;
					} else {
						lettersToSameRail[i][0] = completeCycle - (2 * i);
						lettersToSameRail[i][1] = completeCycle - lettersToSameRail[i][0];
						lettersToSameRail[rails - 1 - i][1] = lettersToSameRail[i][0];
						lettersToSameRail[rails - 1 - i][0] = lettersToSameRail[i][1];
					}
				}

				String railFence = "";

				for (int i = 0; i < rails; i++) {
					for (int j = 0; j < lettersPerRail[i]; j++) {
						int totalLetters = i;
						for (int k = 0; k < j; k++) {
							totalLetters += lettersToSameRail[i][k % 2 == 0 ? 0 : 1];
						}
						railFence += input.charAt(totalLetters);
					}
				}

				return railFence;
			} else {
				int[] railCounter = new int[rails];
				String message = "";

				for (int i = 0; i < input.length(); i++) {
					int railOn = i % completeCycle;
					railOn = railOn < rails ? railOn : completeCycle - railOn;
					int lettersIn = railCounter[railOn]++;
					for (int j = 0; j < railOn; j++) {
						lettersIn += lettersPerRail[j];
					}
					message += input.charAt(lettersIn);
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
		validateKey(key);
		char[][] matrix = new char[key.length()][(int) Math.ceil((double) message.length() / key.length())]; //[col][row]

		for (int i = 0; i < message.length(); i++) {
			matrix[i % key.length()][i / key.length()] = message.charAt(i);
		}

		String columnar = "";

		for (int i = 0; i < ALPHABET.length(); i++) {
			for (int j = 0; j < key.length(); j++) {
				if (key.charAt(j) == ALPHABET.charAt(i)) {
					for (int k = 0; k < matrix[j].length; k++) {
						columnar += matrix[j][k];
					}
				}
			}
		}

		return columnar;
	}

	public static String columnarDecrypt(String columnar, String key) throws IllegalArgumentException {
		key = key.trim().toUpperCase();
		validateKey(key);
		int[] lettersPerColumn = new int[key.length()];

		for (int i = 0; i < lettersPerColumn.length; i++) {
			lettersPerColumn[i] = columnar.length() / key.length();
		}

		for (int i = 0; i < columnar.length() % key.length(); i++) {
			lettersPerColumn[i]++;
		}

		int[] order = new int[key.length()];
		int count = 0;

		for (int i = 0; i < ALPHABET.length(); i++) {
			for (int j = 0; j < key.length(); j++) {
				if (key.charAt(j) == ALPHABET.charAt(i)) {
					order[j] = count++;
				}
			}
		}

		String message = "";

		for (int i = 0; i < columnar.length(); i++) {
			int totalLetters = i / key.length();
			for (int j = 0; j < order[i % key.length()]; j++) {
				totalLetters += lettersPerColumn[indexOf(order, j)];
			}
			message += columnar.charAt(totalLetters);
		}

		return message;
	}

	private static String affine(String input, int step, int shift, boolean encrypt) throws IllegalArgumentException {
		step = mod(step, 26);
		shift = mod(shift, 26);
		if (!relativelyPrime(step, 26)) {
			throw new IllegalArgumentException();
		}
		String result = "";

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) {
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
}