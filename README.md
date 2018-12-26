# AutoCipher
## Useful commands (execute from from root directory):
compile:
```
javac -d classes src\*.java
```
run:
```
java -cp classes CipherTester
```
## List of Ciphers
There are 12 so far with encryption and decryption functionality.

**Atbash:** The Atbash cipher replaces each letter in the plaintext with its inverse (e.g. "A" and "Z" turn into each other and "B" and "Y" turn into each other).

**Ceasar:** The Ceasar cipher shifts each letter in the plaintext by a certain amount (e.g. a shift of 3 would make "A" turn into "D" and "Y" turn into "B").

**A1Z26:** The A1Z26 cipher replaces each letter in the plaintext with its index surrounded by dashes (e.g. "abc zyx" becomes "1-2-3 26-25-24").

**Vigenère:** The Vigenère cipher is a series of cycling Ceasar ciphers indicated by a key (e.g. with a key of "ABYZ" the 1st letter of the plaintext will be encrypted with a Ceasar cipher with a shift of 0, the 2nd with a shift 1, the 3rd with a shift of 24, the fourth with a shift of 25, and then the cycle repeats).

**Rail Fence:** The Rail Fence cipher scrambles the plaintext by writing the message in a zigzag pattern across a specified number of rails, going from left to right and starting at the top. The ciphertext is obtained by reading off each rail from left to right, starting with the top rail and ending with the bottom rail.

**Columnar:** The Columnar cipher scrambles the plaintext by writing the message in rows of a length determined by a key. The ciphertext is obtained by reading columns from top to bottom in an order determined by the alphabetical order of the key, with ties going to the leftmost letter.

**Affine:** The Affine cipher replaces each letter in the plaintext using the function (ax+b) mod c, where a is an integer relatively prime to c, x is the index of the plaintext letter in the alphabet, b is an integer, and c is the length of the alphabet (usually 26).

**Quagmire I:** The Quagmire I cipher uses a key (cannot repeat letters), indicator key, and a letter the indicator key is under. A plaintext alphabet is created by writing the key followed by the letters, in alphabetical order, that do not appear in the key. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by placing the corresponding letter of the indicator key at the same index in the plaintext alphabet of the letter that the indicator key was determined to be under and then filling in the rest of the alphabetical by taking a regular alphabet and shifting it until it matches the positioned letter from the indicator key. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.

**Quagmire II:** The Quagmire II cipher uses a key (cannot repeat letters), indicator key, and a letter the indicator key is under. The plaintext alphabet is simply the normal alphabet. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by writing the key followed by the letters, in alphabetical order, that do not appear in the key and shifting the ciphertext alphabet so that the corresponding letter of the indicator key is placed at the same index in the plaintext alphabet of the letter that the indicator key was determined to be under. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.

**Quagmire III:** The Quagmire III cipher uses a key (cannot repeat letters), indicator key, and a letter the indicator key is under. A plaintext alphabet is created by writing the key followed by the letters, in alphabetical order, that do not appear in the key. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by shifting the plaintext alphabet so that the corresponding letter of the indicator key is placed at the same index in the plaintext alphabet of the letter that the indicator key was determined to be under. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.

**Quagmire IV:** The Quagmire IV cipher uses a plaintext key (cannot repeat letters), ciphertext key (cannot repeat letters), indicator key, and a letter the indicator key is under. A plaintext alphabet is created by writing the plaintext key followed by the letters, in alphabetical order, that do not appear in the key. A number, equal to the length of the indicator key, of ciphertext alphabets are then created by writing the ciphertext key followed by the letters, in alphabetical order, that do not appear in the key and shifting the ciphertext alphabet so that the corresponding letter of the indicator key is placed at the same index in the plaintext alphabet of the letter that the indicator key was determined to be under. Cycling through the ciphertext alphabets, each letter from the plaintext is then replaced with the letter in the same index of the current ciphertext alphabet as it is in the plaintext alphabet.

**Baconian:** The Baconian cipher replaces each letter in the plaintext with a certain five-letter combination of As and Bs (e.g. "A" becomes "AAAAA" and "Z" becomes "BBAAB")
## License
Licensed under the [MIT License](LICENSE).
