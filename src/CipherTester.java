import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CipherTester {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What cipher would you like to use?\n1) Atbash\n2) Ceasar\n3) A1Z26\n4) Vigen\u00E9re\n5) Rail Fence\n6) Columnar\n7) None, exit program\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeChoice(choice);
		}
	}

	private static String getKey() {
		String key = "";
		boolean valid = false;
		System.out.print("Enter the key: ");

		while (!valid) {
			key = input.nextLine().trim().toUpperCase();
			Pattern p = Pattern.compile("^$|(?![A-Z]).");
			Matcher m = p.matcher(key);
			if (m.find()) {	
				System.out.print("Invalid selection. Enter the key: ");
			} else {
				valid = true;
			}
		}

		return key;
	}

	private static int getInt(String message) {
		int integer = 0;
		boolean valid = false;
		System.out.print(message);

		while (!valid) {
			String intStr = input.nextLine().trim();
			Pattern p = Pattern.compile("^$|(?![0-9]).");
			Matcher m = p.matcher(intStr);
			if (m.find()) {	
				System.out.print("Invalid selection. " + message);
			} else {
				integer = Integer.parseInt(intStr);
				valid = true;
			}
		}

		return integer;
	}

	private static boolean executeChoice(String choice) {
		switch (choice) {
				case "1":
					testAtbash();
					break;
				case "2":
					testCeasar();
					break;
				case "3":
					testA1z26(); 
					break;
				case "4":
					testVigenere();
					break;
				case "5":
					testRailFence();
					break;
				case "6":
					testColumnar();
					break;
				case "7":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeChoice(newChoice);
		}
		return false;
	}

	private static void testAtbash() {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What would you like to do?\n1) Encrypt with Atbash\n2) Decrypt from Atbash\n3) Return to previous menu\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeAtbashChoice(choice);
		}
	}

	private static boolean executeAtbashChoice(String choice) {
		switch (choice) {
				case "1":
					System.out.print("Enter the message to encrypt with Atbash: ");
					System.out.println("Encrypted message: " + CipherTools.atbashEncrypt(input.nextLine()));
					break;
				case "2":
					System.out.print("Enter the message to decrypt from Atbash: ");
					System.out.println("Decrypted message: " + CipherTools.atbashDecrypt(input.nextLine()));
					break;
				case "3":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeAtbashChoice(newChoice);
		}
		return false;
	}

	private static void testCeasar() {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What would you like to do?\n1) Encrypt with Ceasar\n2) Decrypt from Ceasar\n3) Return to previous menu\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeCeasarChoice(choice);
		}
	}

	private static boolean executeCeasarChoice(String choice) {
		int shift = 0;
		switch (choice) {
				case "1":
					shift = CipherTools.mod(getInt("Enter the shift: "), 26);
					System.out.print("Enter the message to encrypt with Ceasar, shift of " + shift % 26 + ": ");
					System.out.println("Encrypted message: " + CipherTools.ceasarEncrypt(input.nextLine(), shift));
					break;
				case "2":
					shift = CipherTools.mod(getInt("Enter the shift: "), 26);
					System.out.print("Enter the message to decrypt from Ceasar, shift of " + shift % 26 + ": ");
					System.out.println("Decrypted message: " + CipherTools.ceasarDecrypt(input.nextLine(), shift));
					break;
				case "3":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeCeasarChoice(newChoice);
		}
		return false;
	}

	private static void testA1z26() {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What would you like to do?\n1) Encrypt with A1Z26\n2) Decrypt from A1Z26\n3) Return to previous menu\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeA1z26Choice(choice);
		}
	}

	private static boolean executeA1z26Choice(String choice) {
		switch (choice) {
				case "1":
					System.out.print("Enter the message to encrypt with A1Z26: ");
					System.out.println("Encrypted message: " + CipherTools.a1z26Encrypt(input.nextLine()));
					break;
				case "2":
					System.out.print("Enter the message to decrypt from A1Z26: ");
					System.out.println("Decrypted message: " + CipherTools.a1z26Decrypt(input.nextLine()));
					break;
				case "3":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeA1z26Choice(newChoice);
		}
		return false;
	}

	private static void testVigenere() {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What would you like to do?\n1) Encrypt with Vigen\u00E9re\n2) Decrypt from Vigen\u00E9re\n3) Return to previous menu\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeVigenereChoice(choice);
		}
	}

	private static boolean executeVigenereChoice(String choice) {
		String key = "";
		switch (choice) {
				case "1":
					key = getKey();
					System.out.print("Enter the message to encrypt with Vigen\u00E9re, key of \"" + key + "\": ");
					System.out.println("Encrypted message: " + CipherTools.vigenereEncrypt(input.nextLine(), key));
					break;
				case "2":
					key = getKey();
					System.out.print("Enter the message to decrypt from Vigen\u00E9re, key of \"" + key + "\": ");
					System.out.println("Decrypted message: " + CipherTools.vigenereDecrypt(input.nextLine(), key));
					break;
				case "3":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeVigenereChoice(newChoice);
		}
		return false;
	}

	private static void testRailFence() {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What would you like to do?\n1) Encrypt with Rail Fence\n2) Decrypt from Rail Fence\n3) Return to previous menu\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeRailFenceChoice(choice);
		}
	}

	private static boolean executeRailFenceChoice(String choice) {
		int rails = 0;
		switch (choice) {
				case "1":
					rails = getInt("Enter the number of rails: ");
					System.out.print("Enter the message to encrypt with Rail Fence, " + rails + " rails: ");
					System.out.println("Encrypted message: " + CipherTools.railFenceEncrypt(input.nextLine(), rails));
					break;
				case "2":
					rails = getInt("Enter the number of rails: ");
					System.out.print("Enter the message to decrypt from Rail Fence, " + rails + " rails: ");
					System.out.println("Decrypted message: " + CipherTools.railFenceDecrypt(input.nextLine(), rails));
					break;
				case "3":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeRailFenceChoice(newChoice);
		}
		return false;
	}

	private static void testColumnar() {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What would you like to do?\n1) Encrypt with Columnar\n2) Decrypt from Columnar\n3) Return to previous menu\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeColumnarChoice(choice);
		}
	}

	private static boolean executeColumnarChoice(String choice) {
		String key = "";
		int columnLength = 0;
		switch (choice) {
				case "1":
					key = getKey();
					System.out.print("Enter the message to encrypt with Columnar, key of \"" + key + "\": ");
					System.out.println("Encrypted message: " + CipherTools.columnarEncrypt(input.nextLine(), key));
					break;
				case "2":
					key = getKey();
					System.out.print("Enter the message to decrypt from Columnar, key of \"" + key + "\": ");
					System.out.println("Decrypted message: " + CipherTools.columnarDecrypt(input.nextLine(), key));
					break;
				case "3":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					return executeColumnarChoice(newChoice);
		}
		return false;
	}
}