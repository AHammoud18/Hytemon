package org.bouncycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.SAKKEPublicKeyParameters;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class SAKKEKEMSGenerator implements EncapsulatedSecretGenerator {
  private final SecureRandom random;
  
  public SAKKEKEMSGenerator(SecureRandom paramSecureRandom) {
    this.random = paramSecureRandom;
  }
  
  public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    ECPoint eCPoint3;
    SAKKEPublicKeyParameters sAKKEPublicKeyParameters = (SAKKEPublicKeyParameters)paramAsymmetricKeyParameter;
    ECPoint eCPoint1 = sAKKEPublicKeyParameters.getZ();
    BigInteger bigInteger1 = sAKKEPublicKeyParameters.getIdentifier();
    BigInteger bigInteger2 = sAKKEPublicKeyParameters.getPrime();
    BigInteger bigInteger3 = sAKKEPublicKeyParameters.getQ();
    BigInteger bigInteger4 = sAKKEPublicKeyParameters.getG();
    int i = sAKKEPublicKeyParameters.getN();
    ECCurve eCCurve = sAKKEPublicKeyParameters.getCurve();
    ECPoint eCPoint2 = sAKKEPublicKeyParameters.getPoint();
    Digest digest = sAKKEPublicKeyParameters.getDigest();
    BigInteger bigInteger5 = BigIntegers.createRandomBigInteger(i, this.random);
    BigInteger bigInteger6 = hashToIntegerRange(Arrays.concatenate(bigInteger5.toByteArray(), bigInteger1.toByteArray()), bigInteger3, digest);
    BigInteger bigInteger7 = eCCurve.getOrder();
    if (bigInteger7 == null) {
      eCPoint3 = eCPoint2.multiply(bigInteger1).add(eCPoint1).multiply(bigInteger6).normalize();
    } else {
      BigInteger bigInteger = bigInteger1.multiply(bigInteger6).mod(bigInteger7);
      eCPoint3 = ECAlgorithms.sumOfTwoMultiplies(eCPoint2, bigInteger, eCPoint1, bigInteger6).normalize();
    } 
    BigInteger bigInteger8 = BigInteger.ONE;
    BigInteger bigInteger9 = bigInteger4;
    BigInteger bigInteger10 = BigInteger.ONE;
    BigInteger bigInteger11 = bigInteger4;
    ECPoint eCPoint4 = eCCurve.createPoint(bigInteger10, bigInteger11);
    for (int j = bigInteger6.bitLength() - 2; j >= 0; j--) {
      BigInteger[] arrayOfBigInteger = SAKKEKEMExtractor.fp2PointSquare(bigInteger10, bigInteger11, bigInteger2);
      eCPoint4 = eCPoint4.timesPow2(2);
      bigInteger10 = arrayOfBigInteger[0];
      bigInteger11 = arrayOfBigInteger[1];
      if (bigInteger6.testBit(j)) {
        arrayOfBigInteger = SAKKEKEMExtractor.fp2Multiply(bigInteger10, bigInteger11, bigInteger8, bigInteger9, bigInteger2);
        bigInteger10 = arrayOfBigInteger[0];
        bigInteger11 = arrayOfBigInteger[1];
      } 
    } 
    BigInteger bigInteger12 = BigIntegers.modOddInverse(bigInteger2, bigInteger10);
    BigInteger bigInteger13 = bigInteger11.multiply(bigInteger12).mod(bigInteger2);
    BigInteger bigInteger14 = hashToIntegerRange(bigInteger13.toByteArray(), BigInteger.ONE.shiftLeft(i), digest);
    BigInteger bigInteger15 = bigInteger5.xor(bigInteger14);
    byte[] arrayOfByte = Arrays.concatenate(eCPoint3.getEncoded(false), BigIntegers.asUnsignedByteArray(16, bigInteger15));
    return new SecretWithEncapsulationImpl(BigIntegers.asUnsignedByteArray(i / 8, bigInteger5), arrayOfByte);
  }
  
  static BigInteger hashToIntegerRange(byte[] paramArrayOfbyte, BigInteger paramBigInteger, Digest paramDigest) {
    byte[] arrayOfByte1 = new byte[paramDigest.getDigestSize()];
    paramDigest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    paramDigest.doFinal(arrayOfByte1, 0);
    byte[] arrayOfByte2 = new byte[paramDigest.getDigestSize()];
    int i = paramBigInteger.bitLength() >> 8;
    BigInteger bigInteger = BigInteger.ZERO;
    byte[] arrayOfByte3 = new byte[paramDigest.getDigestSize()];
    for (byte b = 0; b <= i; b++) {
      paramDigest.update(arrayOfByte2, 0, arrayOfByte2.length);
      paramDigest.doFinal(arrayOfByte2, 0);
      paramDigest.update(arrayOfByte2, 0, arrayOfByte2.length);
      paramDigest.update(arrayOfByte1, 0, arrayOfByte1.length);
      paramDigest.doFinal(arrayOfByte3, 0);
      bigInteger = bigInteger.shiftLeft(arrayOfByte3.length * 8).add(new BigInteger(1, arrayOfByte3));
    } 
    return bigInteger.mod(paramBigInteger);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\kems\SAKKEKEMSGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */