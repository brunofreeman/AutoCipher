import cipher_tools as ctools

def run_atbash():
    t = raw_input("Enter text to encrypt/decrypt with/from Atbash: ")
    print("Result: %s" % ctools.atbash_encrypt(t))

r = True

while (r):
    print("1) Atbash\n2) Exit")
    c = raw_input("Choice: ")
    if c == "1":
        run_atbash()
    elif c == "2":
        r = False
    else:
        print("Invalid selection.")