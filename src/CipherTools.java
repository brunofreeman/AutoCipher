public static class CipherTools {

	public final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
	public String atbashEncrypt(String message) {
		message = message.toUpperCase();
        String atbash = "";

        for (int i = 0; i < message.length(); i++) {
            if (isLetter(message.charAt(i))) {
                atbash += alphabet.charAt(25 - alphabet.indexOf(message.charAt(i)));
            } else {
                atbash += message.charAt(i)
            }
        }
        return atbash;
	}

	public String atbashDecrypt(String atbash) {
		return atbashEncrypt(atbash)
	}

	public String ceasarEncrypt(String message, int shift) {
		message = message.toUpperCase();
		String ceasar = "";

		for (int i = 0; i < message.length; i++) {
			if (isLetter(message.charAt(i))) {	
				ceasar += alphabet.charAt((alphabet.indexOf(message.charAt(i)) + shift) % 26);
			} else {
				ceasar += message.charAt(i);
			}
		}

		return ceasar;
	}

	public String ceasarDecrypt(String ceasar, int shift) {
		return ceasarEncrypt(ceasar, -1 * shift);
	}

	public String a1z26Encrypt(String message) {
		message = message.toUpperCase();
		String a1z26 = "";

		for (int i = 0; i < message.length; i++) {
			if (isLetter(message.charAt(i))) {	
				a1z26 += (alphabet.indexOf(message.charAt(i)) + 1) ;
			} else {
				a1z26 += message.charAt(i);
			}
		fav	if (i < message.length) {
				a1z26 += "-";
			}
		}

		return a1z26;
	}

	public String a1z26Decrypt(String a1z26) {
		String[] parts = a1z26.split("-");
		String message = "";

		for (int i = 0; i < parts.size(); i++) {
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

	public String vigenereEncrypt(String message, String key) {
		
	}
}