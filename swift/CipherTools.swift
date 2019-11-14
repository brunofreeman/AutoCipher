#!/usr/bin/swift

let alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

func matchCase(x: Character, ref: Character) -> Character { //not working
    //return NSPredicate(format:"SELF MATCHES %@", "[a-z]").evaluateWithObject(ref) ? x.lowercased() : x.uppercased()
    return x
}

func charAt(s: String, i: Int) -> Character {
    return s[s.index(s.startIndex, offsetBy: i)]
}

func atbashEncrypt(pt: String) -> String { //not working
    let ptUp = pt.uppercased()
    var ct = ""
    for i in 0..<pt.count {
        //ct += alphabet.contains(ptUp[i]) ? matchCase(x: alphabet[alphabet.count - 1 - alphabet.distance(from: alphabet.startIndex, to: alphabet.range(of: pt[i])!.lowerBound)], ref: pt[i]) : pt[i]
        if alphabet.contains(charAt(s: ptUp, i: i)) {
            //let aIndex = distance(alphabet.startIndex, alphabet.index(of: charAt(s: ptUp, i: i)))
            //let aIndex = alphabet.startIndex.distanceTo(alphabet.index(of: charAt(s: ptUp, i: i)))
            let aIndex = 0
            let index = alphabet.count - 1 - aIndex
            ct += String(matchCase(x: charAt(s: alphabet, i: index), ref: charAt(s: pt, i: i)))
        } else {
            ct += String(charAt(s: pt, i: i))
        }
    }
    return ct
}

func atbashDecrypt(ct: String) -> String {
    return atbashEncrypt(pt: ct)
}

print(atbashEncrypt(pt: "abc DEf!3Rr"))