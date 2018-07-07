import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CipherTools {

	public static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
    	
	public static String atbashEncrypt(String message) {
		String messageUp = message.toUpperCase();
        String atbash = "";

        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                atbash += matchCase(alphabet.charAt(25 - alphabet.indexOf(messageUp.charAt(i))), message.charAt(i));
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
				ceasar += matchCase(alphabet.charAt(mod(alphabet.indexOf(messageUp.charAt(i)) + shift, 26)), message.charAt(i));
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
				a1z26 += (alphabet.indexOf(message.charAt(i)) + 1) ;
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
				message += alphabet.charAt(num - 1);
			} catch (Exception e) {
				message += parts[i];
			}
		}

		return message;
	}

	private static String vigenere(String input, String key, boolean encrypt) {
		String inputUp = input.toUpperCase();
		String result = "";
		key = key.trim().toUpperCase();
		Pattern p = Pattern.compile("^$|(?![A-Z]).");
		Matcher m = p.matcher(key);
		if (m.find()) {
			throw new IllegalArgumentException();
		}
		int keyIndex = 0;

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) {
				result += encrypt ? ceasarEncrypt(Character.toString(input.charAt(i)), alphabet.indexOf(key.charAt(keyIndex++))) : ceasarDecrypt(Character.toString(input.charAt(i)), alphabet.indexOf(key.charAt(keyIndex++)));
				if (keyIndex >= key.length()) {
					keyIndex = 0;
				}
			} else {
				result += input.charAt(i);
			}
		}

		return result;
	}

	public static String vigenereEncrypt(String message, String key) {
		return vigenere(message, key, true);
	}

	public static String vigenereDecrypt(String vigenere, String key) {
		return vigenere(vigenere, key, false);
	}
}