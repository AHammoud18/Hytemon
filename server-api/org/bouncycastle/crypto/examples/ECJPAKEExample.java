package org.bouncycastle.crypto.examples;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.SavableDigest;
import org.bouncycastle.crypto.agreement.ecjpake.ECJPAKECurve;
import org.bouncycastle.crypto.agreement.ecjpake.ECJPAKECurves;
import org.bouncycastle.crypto.agreement.ecjpake.ECJPAKEParticipant;
import org.bouncycastle.crypto.agreement.ecjpake.ECJPAKERound1Payload;
import org.bouncycastle.crypto.agreement.ecjpake.ECJPAKERound2Payload;
import org.bouncycastle.crypto.agreement.ecjpake.ECJPAKERound3Payload;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.math.ec.ECPoint;

public class ECJPAKEExample {
  public static void main(String[] paramArrayOfString) throws CryptoException {
    ECJPAKECurve eCJPAKECurve = ECJPAKECurves.NIST_P256;
    BigInteger bigInteger1 = eCJPAKECurve.getA();
    BigInteger bigInteger2 = eCJPAKECurve.getB();
    ECPoint eCPoint = eCJPAKECurve.getG();
    BigInteger bigInteger3 = eCJPAKECurve.getH();
    BigInteger bigInteger4 = eCJPAKECurve.getN();
    BigInteger bigInteger5 = eCJPAKECurve.getQ();
    String str1 = "password";
    String str2 = "password";
    System.out.println("********* Initialization **********");
    System.out.println("Public parameters for the elliptic curve over prime field:");
    System.out.println("Curve param a (" + bigInteger1.bitLength() + " bits): " + bigInteger1.toString(16));
    System.out.println("Curve param b (" + bigInteger2.bitLength() + " bits): " + bigInteger2.toString(16));
    System.out.println("Co-factor h (" + bigInteger3.bitLength() + " bits): " + bigInteger3.toString(16));
    System.out.println("Base point G (" + (eCPoint.getEncoded(true)).length + " bytes): " + (new BigInteger(eCPoint.getEncoded(true))).toString(16));
    System.out.println("X coord of G (G not normalised) (" + eCPoint.getXCoord().toBigInteger().bitLength() + " bits): " + eCPoint.getXCoord().toBigInteger().toString(16));
    System.out.println("y coord of G (G not normalised) (" + eCPoint.getYCoord().toBigInteger().bitLength() + " bits): " + eCPoint.getYCoord().toBigInteger().toString(16));
    System.out.println("Order of the base point n (" + bigInteger4.bitLength() + " bits): " + bigInteger4.toString(16));
    System.out.println("Prime field q (" + bigInteger5.bitLength() + " bits): " + bigInteger5.toString(16));
    System.out.println("");
    System.out.println("(Secret passwords used by Alice and Bob: \"" + str1 + "\" and \"" + str2 + "\")\n");
    SavableDigest savableDigest = SHA256Digest.newInstance();
    SecureRandom secureRandom = new SecureRandom();
    ECJPAKEParticipant eCJPAKEParticipant1 = new ECJPAKEParticipant("alice", str1.toCharArray(), eCJPAKECurve, (Digest)savableDigest, secureRandom);
    ECJPAKEParticipant eCJPAKEParticipant2 = new ECJPAKEParticipant("bob", str2.toCharArray(), eCJPAKECurve, (Digest)savableDigest, secureRandom);
    ECJPAKERound1Payload eCJPAKERound1Payload1 = eCJPAKEParticipant1.createRound1PayloadToSend();
    ECJPAKERound1Payload eCJPAKERound1Payload2 = eCJPAKEParticipant2.createRound1PayloadToSend();
    System.out.println("************ Round 1 **************");
    System.out.println("Alice sends to Bob: ");
    System.out.println("g^{x1}=" + (new BigInteger(eCJPAKERound1Payload1.getGx1().getEncoded(true))).toString(16));
    System.out.println("g^{x2}=" + (new BigInteger(eCJPAKERound1Payload1.getGx2().getEncoded(true))).toString(16));
    System.out.println("KP{x1}: {V=" + (new BigInteger(eCJPAKERound1Payload1.getKnowledgeProofForX1().getV().getEncoded(true))).toString(16) + "; r=" + eCJPAKERound1Payload1.getKnowledgeProofForX1().getr().toString(16) + "}");
    System.out.println("KP{x2}: {V=" + (new BigInteger(eCJPAKERound1Payload1.getKnowledgeProofForX2().getV().getEncoded(true))).toString(16) + "; r=" + eCJPAKERound1Payload1.getKnowledgeProofForX2().getr().toString(16) + "}");
    System.out.println("");
    System.out.println("Bob sends to Alice: ");
    System.out.println("g^{x3}=" + (new BigInteger(eCJPAKERound1Payload2.getGx1().getEncoded(true))).toString(16));
    System.out.println("g^{x4}=" + (new BigInteger(eCJPAKERound1Payload2.getGx2().getEncoded(true))).toString(16));
    System.out.println("KP{x3}: {V=" + (new BigInteger(eCJPAKERound1Payload2.getKnowledgeProofForX1().getV().getEncoded(true))).toString(16) + "; r=" + eCJPAKERound1Payload2.getKnowledgeProofForX1().getr().toString(16) + "}");
    System.out.println("KP{x4}: {V=" + (new BigInteger(eCJPAKERound1Payload2.getKnowledgeProofForX2().getV().getEncoded(true))).toString(16) + "; r=" + eCJPAKERound1Payload2.getKnowledgeProofForX2().getr().toString(16) + "}");
    System.out.println("");
    eCJPAKEParticipant1.validateRound1PayloadReceived(eCJPAKERound1Payload2);
    System.out.println("Alice checks g^{x4}!=1: OK");
    System.out.println("Alice checks KP{x3}: OK");
    System.out.println("Alice checks KP{x4}: OK");
    System.out.println("");
    eCJPAKEParticipant2.validateRound1PayloadReceived(eCJPAKERound1Payload1);
    System.out.println("Bob checks g^{x2}!=1: OK");
    System.out.println("Bob checks KP{x1},: OK");
    System.out.println("Bob checks KP{x2},: OK");
    System.out.println("");
    ECJPAKERound2Payload eCJPAKERound2Payload1 = eCJPAKEParticipant1.createRound2PayloadToSend();
    ECJPAKERound2Payload eCJPAKERound2Payload2 = eCJPAKEParticipant2.createRound2PayloadToSend();
    System.out.println("************ Round 2 **************");
    System.out.println("Alice sends to Bob: ");
    System.out.println("A=" + (new BigInteger(eCJPAKERound2Payload1.getA().getEncoded(true))).toString(16));
    System.out.println("KP{x2*s}: {V=" + (new BigInteger(eCJPAKERound2Payload1.getKnowledgeProofForX2s().getV().getEncoded(true))).toString(16) + ", r=" + eCJPAKERound2Payload1.getKnowledgeProofForX2s().getr().toString(16) + "}");
    System.out.println("");
    System.out.println("Bob sends to Alice");
    System.out.println("B=" + (new BigInteger(eCJPAKERound2Payload2.getA().getEncoded(true))).toString(16));
    System.out.println("KP{x4*s}: {V=" + (new BigInteger(eCJPAKERound2Payload2.getKnowledgeProofForX2s().getV().getEncoded(true))).toString(16) + ", r=" + eCJPAKERound2Payload2.getKnowledgeProofForX2s().getr().toString(16) + "}");
    System.out.println("");
    eCJPAKEParticipant1.validateRound2PayloadReceived(eCJPAKERound2Payload2);
    System.out.println("Alice checks KP{x4*s}: OK\n");
    eCJPAKEParticipant2.validateRound2PayloadReceived(eCJPAKERound2Payload1);
    System.out.println("Bob checks KP{x2*s}: OK\n");
    BigInteger bigInteger6 = eCJPAKEParticipant1.calculateKeyingMaterial();
    BigInteger bigInteger7 = eCJPAKEParticipant2.calculateKeyingMaterial();
    System.out.println("********* After round 2 ***********");
    System.out.println("Alice computes key material \t K=" + bigInteger6.toString(16));
    System.out.println("Bob computes key material \t K=" + bigInteger7.toString(16));
    System.out.println();
    BigInteger bigInteger8 = deriveSessionKey(bigInteger6);
    BigInteger bigInteger9 = deriveSessionKey(bigInteger7);
    ECJPAKERound3Payload eCJPAKERound3Payload1 = eCJPAKEParticipant1.createRound3PayloadToSend(bigInteger6);
    ECJPAKERound3Payload eCJPAKERound3Payload2 = eCJPAKEParticipant2.createRound3PayloadToSend(bigInteger7);
    System.out.println("************ Round 3 **************");
    System.out.println("Alice sends to Bob: ");
    System.out.println("MacTag=" + eCJPAKERound3Payload1.getMacTag().toString(16));
    System.out.println("");
    System.out.println("Bob sends to Alice: ");
    System.out.println("MacTag=" + eCJPAKERound3Payload2.getMacTag().toString(16));
    System.out.println("");
    eCJPAKEParticipant1.validateRound3PayloadReceived(eCJPAKERound3Payload2, bigInteger6);
    System.out.println("Alice checks MacTag: OK\n");
    eCJPAKEParticipant2.validateRound3PayloadReceived(eCJPAKERound3Payload1, bigInteger7);
    System.out.println("Bob checks MacTag: OK\n");
    System.out.println();
    System.out.println("MacTags validated, therefore the keying material matches.");
  }
  
  private static BigInteger deriveSessionKey(BigInteger paramBigInteger) {
    SavableDigest savableDigest = SHA256Digest.newInstance();
    byte[] arrayOfByte1 = paramBigInteger.toByteArray();
    byte[] arrayOfByte2 = new byte[savableDigest.getDigestSize()];
    savableDigest.update(arrayOfByte1, 0, arrayOfByte1.length);
    savableDigest.doFinal(arrayOfByte2, 0);
    return new BigInteger(arrayOfByte2);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\examples\ECJPAKEExample.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */