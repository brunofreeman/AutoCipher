alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

def match_case(x, ref):
	return x.upper() if ref.isupper() else x.lower()

def atbash_encrypt(pt):
	pt_up = pt.upper()
	ct = ""
	for i in range(0, len(pt)):
		ct += match_case(alphabet[len(alphabet) - 1 - alphabet.index(pt_up[i])], pt[i]) if pt_up[i] in alphabet else pt[i]
	return ct

def atbash_decrypt(ct):
	return atbash_encrypt(ct)
	