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
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					return true;
				default:
					System.out.print("Invalid selection.\nChoice: ");
					String newChoice = input.nextLine().trim();
					executeChoice(newChoice);
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
					executeAtbashChoice(newChoice);
		}
		return false;
	}
}