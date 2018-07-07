import java.util.Scanner;

public class CipherTester {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		boolean terminated = false;
		while (!terminated) {
			System.out.print("What cipher would you like to test?\n1) Atbash\n2) Ceasar\n3) A1Z26\n4) Vigenere\n5) None, exit program\nChoice: ");
			String choice = input.nextLine().trim();
			terminated = executeChoice(choice);
		}
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
					break;
				case "5":
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
					System.out.println("Encrypted message: " + CipherTools.atbashEncrypt(input.nextLine().trim()));
					break;
				case "2":
					System.out.print("Enter the message to decrypt from Atbash: ");
					System.out.println("Decrypted message: " + CipherTools.atbashDecrypt(input.nextLine().trim()));
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
		boolean valid = false;
		int shift = 0;
		switch (choice) {
				case "1":
					while (!valid) {
						System.out.print("Enter the shift: ");
						try {
							shift = Integer.parseInt(input.nextLine().trim());
							valid = true;
						} catch (Exception e) {
							System.out.print("Invalid selection. Enter the shift: ");
						}	
					}
					System.out.print("Enter the message to encrypt with Ceasar, shift of " + shift + ": ");
					System.out.println("Encrypted message: " + CipherTools.ceasarEncrypt(input.nextLine().trim(), shift));
					break;
				case "2":
					while (!valid) {
						System.out.print("Enter the original shift: ");
						try {
							shift = Integer.parseInt(input.nextLine().trim());
							valid = true;
						} catch (Exception e) {
							System.out.print("Invalid selection. Enter the original shift: ");
						}	
					}
					System.out.print("Enter the message to decrypt from Ceasar, shift of " + shift + ": ");
					System.out.println("Decrypted message: " + CipherTools.ceasarDecrypt(input.nextLine().trim(), shift));
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
					System.out.println("Encrypted message: " + CipherTools.a1z26Encrypt(input.nextLine().trim()));
					break;
				case "2":
					System.out.print("Enter the message to decrypt from A1Z26: ");
					System.out.println("Decrypted message: " + CipherTools.a1z26Decrypt(input.nextLine().trim()));
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
}