package org.bouncycastle.crypto.kems;

import java.math.BigInteger;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.EncapsulatedSecretExtractor;
import org.bouncycastle.crypto.params.SAKKEPrivateKeyParameters;
import org.bouncycastle.crypto.params.SAKKEPublicKeyParameters;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class SAKKEKEMExtractor implements EncapsulatedSecretExtractor {
  private final ECCurve curve;
  
  private final BigInteger p;
  
  private final BigInteger q;
  
  private final ECPoint P;
  
  private final ECPoint Z_S;
  
  private final ECPoint K_bs;
  
  private final int n;
  
  private final BigInteger identifier;
  
  private final Digest digest;
  
  public SAKKEKEMExtractor(SAKKEPrivateKeyParameters paramSAKKEPrivateKeyParameters) {
    SAKKEPublicKeyParameters sAKKEPublicKeyParameters = paramSAKKEPrivateKeyParameters.getPublicParams();
    this.curve = sAKKEPublicKeyParameters.getCurve();
    this.q = sAKKEPublicKeyParameters.getQ();
    this.P = sAKKEPublicKeyParameters.getPoint();
    this.p = sAKKEPublicKeyParameters.getPrime();
    this.Z_S = sAKKEPublicKeyParameters.getZ();
    this.identifier = sAKKEPublicKeyParameters.getIdentifier();
    this.K_bs = this.P.multiply(this.identifier.add(paramSAKKEPrivateKeyParameters.getMasterSecret()).modInverse(this.q)).normalize();
    this.n = sAKKEPublicKeyParameters.getN();
    this.digest = sAKKEPublicKeyParameters.getDigest();
  }
  
  public byte[] extractSecret(byte[] paramArrayOfbyte) {
    ECPoint eCPoint1 = this.curve.decodePoint(Arrays.copyOfRange(paramArrayOfbyte, 0, 257));
    BigInteger bigInteger1 = BigIntegers.fromUnsignedByteArray(paramArrayOfbyte, 257, 16);
    BigInteger bigInteger2 = computePairing(eCPoint1, this.K_bs, this.p, this.q);
    BigInteger bigInteger3 = BigInteger.ONE.shiftLeft(this.n);
    BigInteger bigInteger4 = SAKKEKEMSGenerator.hashToIntegerRange(bigInteger2.toByteArray(), bigInteger3, this.digest);
    BigInteger bigInteger5 = bigInteger1.xor(bigInteger4).mod(this.p);
    BigInteger bigInteger6 = this.identifier;
    BigInteger bigInteger7 = SAKKEKEMSGenerator.hashToIntegerRange(Arrays.concatenate(bigInteger5.toByteArray(), bigInteger6.toByteArray()), this.q, this.digest);
    BigInteger bigInteger8 = this.curve.getOrder();
    if (bigInteger8 == null) {
      eCPoint2 = this.P.multiply(bigInteger6).add(this.Z_S).multiply(bigInteger7);
    } else {
      BigInteger bigInteger = bigInteger6.multiply(bigInteger7).mod(bigInteger8);
      eCPoint2 = ECAlgorithms.sumOfTwoMultiplies(this.P, bigInteger, this.Z_S, bigInteger7);
    } 
    ECPoint eCPoint2 = eCPoint2.subtract(eCPoint1);
    if (!eCPoint2.isInfinity())
      throw new IllegalStateException("Validation of R_bS failed"); 
    return BigIntegers.asUnsignedByteArray(this.n / 8, bigInteger5);
  }
  
  public int getEncapsulationLength() {
    return 273;
  }
  
  static BigInteger computePairing(ECPoint paramECPoint1, ECPoint paramECPoint2, BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
    BigInteger[] arrayOfBigInteger = { BigInteger.ONE, BigInteger.ZERO };
    ECPoint eCPoint = paramECPoint1;
    BigInteger bigInteger1 = paramBigInteger2.subtract(BigInteger.ONE);
    int i = bigInteger1.bitLength();
    BigInteger bigInteger2 = paramECPoint2.getAffineXCoord().toBigInteger();
    BigInteger bigInteger3 = paramECPoint2.getAffineYCoord().toBigInteger();
    BigInteger bigInteger4 = paramECPoint1.getAffineXCoord().toBigInteger();
    BigInteger bigInteger5 = paramECPoint1.getAffineYCoord().toBigInteger();
    BigInteger bigInteger6 = BigInteger.valueOf(3L);
    for (int j = i - 2; j >= 0; j--) {
      BigInteger bigInteger8 = eCPoint.getAffineXCoord().toBigInteger();
      BigInteger bigInteger9 = eCPoint.getAffineYCoord().toBigInteger();
      BigInteger bigInteger10 = bigInteger8.multiply(bigInteger8).mod(paramBigInteger1).subtract(BigInteger.ONE).multiply(bigInteger6).multiply(BigIntegers.modOddInverse(paramBigInteger1, bigInteger9.shiftLeft(1))).mod(paramBigInteger1);
      arrayOfBigInteger = fp2PointSquare(arrayOfBigInteger[0], arrayOfBigInteger[1], paramBigInteger1);
      arrayOfBigInteger = fp2Multiply(arrayOfBigInteger[0], arrayOfBigInteger[1], bigInteger10.multiply(bigInteger2.add(bigInteger8)).subtract(bigInteger9).mod(paramBigInteger1), bigInteger3, paramBigInteger1);
      eCPoint = eCPoint.twice().normalize();
      if (bigInteger1.testBit(j)) {
        bigInteger8 = eCPoint.getAffineXCoord().toBigInteger();
        bigInteger9 = eCPoint.getAffineYCoord().toBigInteger();
        bigInteger10 = bigInteger9.subtract(bigInteger5).multiply(BigIntegers.modOddInverse(paramBigInteger1, bigInteger8.subtract(bigInteger4))).mod(paramBigInteger1);
        arrayOfBigInteger = fp2Multiply(arrayOfBigInteger[0], arrayOfBigInteger[1], bigInteger10.multiply(bigInteger2.add(bigInteger8)).subtract(bigInteger9).mod(paramBigInteger1), bigInteger3, paramBigInteger1);
        if (j > 0)
          eCPoint = eCPoint.add(paramECPoint1).normalize(); 
      } 
    } 
    arrayOfBigInteger = fp2PointSquare(arrayOfBigInteger[0], arrayOfBigInteger[1], paramBigInteger1);
    arrayOfBigInteger = fp2PointSquare(arrayOfBigInteger[0], arrayOfBigInteger[1], paramBigInteger1);
    BigInteger bigInteger7 = BigIntegers.modOddInverse(paramBigInteger1, arrayOfBigInteger[0]);
    return arrayOfBigInteger[1].multiply(bigInteger7).mod(paramBigInteger1);
  }
  
  static BigInteger[] fp2Multiply(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4, BigInteger paramBigInteger5) {
    return new BigInteger[] { paramBigInteger1.multiply(paramBigInteger3).subtract(paramBigInteger2.multiply(paramBigInteger4)).mod(paramBigInteger5), paramBigInteger1.multiply(paramBigInteger4).add(paramBigInteger2.multiply(paramBigInteger3)).mod(paramBigInteger5) };
  }
  
  static BigInteger[] fp2PointSquare(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) {
    return new BigInteger[] { paramBigInteger1.add(paramBigInteger2).multiply(paramBigInteger1.subtract(paramBigInteger2)).mod(paramBigInteger3), paramBigInteger1.multiply(paramBigInteger2).shiftLeft(1).mod(paramBigInteger3) };
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\kems\SAKKEKEMExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */