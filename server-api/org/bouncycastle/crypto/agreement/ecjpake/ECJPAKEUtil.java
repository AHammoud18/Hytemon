package org.bouncycastle.crypto.agreement.ecjpake;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;

public class ECJPAKEUtil {
  static final BigInteger ZERO = BigInteger.valueOf(0L);
  
  static final BigInteger ONE = BigInteger.valueOf(1L);
  
  public static BigInteger generateX1(BigInteger paramBigInteger, SecureRandom paramSecureRandom) {
    BigInteger bigInteger1 = ONE;
    BigInteger bigInteger2 = paramBigInteger.subtract(ONE);
    return BigIntegers.createRandomInRange(bigInteger1, bigInteger2, paramSecureRandom);
  }
  
  public static BigInteger calculateS(BigInteger paramBigInteger, byte[] paramArrayOfbyte) throws CryptoException {
    BigInteger bigInteger = (new BigInteger(1, paramArrayOfbyte)).mod(paramBigInteger);
    if (bigInteger.signum() == 0)
      throw new CryptoException("MUST ensure s is not equal to 0 modulo n"); 
    return bigInteger;
  }
  
  public static BigInteger calculateS(BigInteger paramBigInteger, char[] paramArrayOfchar) throws CryptoException {
    return calculateS(paramBigInteger, Strings.toUTF8ByteArray(paramArrayOfchar));
  }
  
  public static ECPoint calculateGx(ECPoint paramECPoint, BigInteger paramBigInteger) {
    return paramECPoint.multiply(paramBigInteger);
  }
  
  public static ECPoint calculateGA(ECPoint paramECPoint1, ECPoint paramECPoint2, ECPoint paramECPoint3) {
    return paramECPoint1.add(paramECPoint2).add(paramECPoint3);
  }
  
  public static BigInteger calculateX2s(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) {
    return paramBigInteger2.multiply(paramBigInteger3).mod(paramBigInteger1);
  }
  
  public static ECPoint calculateA(ECPoint paramECPoint, BigInteger paramBigInteger) {
    return paramECPoint.multiply(paramBigInteger);
  }
  
  public static ECSchnorrZKP calculateZeroKnowledgeProof(ECPoint paramECPoint1, BigInteger paramBigInteger1, BigInteger paramBigInteger2, ECPoint paramECPoint2, Digest paramDigest, String paramString, SecureRandom paramSecureRandom) {
    BigInteger bigInteger1 = BigIntegers.createRandomInRange(BigInteger.ONE, paramBigInteger1.subtract(BigInteger.ONE), paramSecureRandom);
    ECPoint eCPoint = paramECPoint1.multiply(bigInteger1);
    BigInteger bigInteger2 = calculateHashForZeroKnowledgeProof(paramECPoint1, eCPoint, paramECPoint2, paramString, paramDigest);
    return new ECSchnorrZKP(eCPoint, bigInteger1.subtract(paramBigInteger2.multiply(bigInteger2)).mod(paramBigInteger1));
  }
  
  private static BigInteger calculateHashForZeroKnowledgeProof(ECPoint paramECPoint1, ECPoint paramECPoint2, ECPoint paramECPoint3, String paramString, Digest paramDigest) {
    paramDigest.reset();
    updateDigestIncludingSize(paramDigest, paramECPoint1);
    updateDigestIncludingSize(paramDigest, paramECPoint2);
    updateDigestIncludingSize(paramDigest, paramECPoint3);
    updateDigestIncludingSize(paramDigest, paramString);
    byte[] arrayOfByte = new byte[paramDigest.getDigestSize()];
    paramDigest.doFinal(arrayOfByte, 0);
    return new BigInteger(arrayOfByte);
  }
  
  private static void updateDigestIncludingSize(Digest paramDigest, ECPoint paramECPoint) {
    byte[] arrayOfByte = paramECPoint.getEncoded(true);
    paramDigest.update(intToByteArray(arrayOfByte.length), 0, 4);
    paramDigest.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  private static void updateDigestIncludingSize(Digest paramDigest, String paramString) {
    byte[] arrayOfByte = Strings.toUTF8ByteArray(paramString);
    paramDigest.update(intToByteArray(arrayOfByte.length), 0, 4);
    paramDigest.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  public static void validateZeroKnowledgeProof(ECPoint paramECPoint1, ECPoint paramECPoint2, ECSchnorrZKP paramECSchnorrZKP, BigInteger paramBigInteger1, BigInteger paramBigInteger2, ECCurve paramECCurve, BigInteger paramBigInteger3, String paramString, Digest paramDigest) throws CryptoException {
    ECPoint eCPoint1 = paramECSchnorrZKP.getV();
    BigInteger bigInteger1 = paramECSchnorrZKP.getr();
    BigInteger bigInteger2 = calculateHashForZeroKnowledgeProof(paramECPoint1, eCPoint1, paramECPoint2, paramString, paramDigest);
    if (paramECPoint2.isInfinity())
      throw new CryptoException("Zero-knowledge proof validation failed: X cannot equal infinity"); 
    ECPoint eCPoint2 = paramECPoint2.normalize();
    if (eCPoint2.getAffineXCoord().toBigInteger().signum() < 0 || eCPoint2.getAffineXCoord().toBigInteger().compareTo(paramBigInteger1) >= 0 || eCPoint2.getAffineYCoord().toBigInteger().signum() < 0 || eCPoint2.getAffineYCoord().toBigInteger().compareTo(paramBigInteger1) >= 0)
      throw new CryptoException("Zero-knowledge proof validation failed: x and y are not in the field"); 
    try {
      paramECCurve.decodePoint(paramECPoint2.getEncoded(true));
    } catch (Exception exception) {
      throw new CryptoException("Zero-knowledge proof validation failed: x does not lie on the curve", exception);
    } 
    if (paramECPoint2.multiply(paramBigInteger3).isInfinity())
      throw new CryptoException("Zero-knowledge proof validation failed: Nx cannot be infinity"); 
    if (!eCPoint1.equals(paramECPoint1.multiply(bigInteger1).add(paramECPoint2.multiply(bigInteger2.mod(paramBigInteger2)))))
      throw new CryptoException("Zero-knowledge proof validation failed: V must be a point on the curve"); 
  }
  
  public static void validateParticipantIdsDiffer(String paramString1, String paramString2) throws CryptoException {
    if (paramString1.equals(paramString2))
      throw new CryptoException("Both participants are using the same participantId (" + paramString1 + "). This is not allowed. Each participant must use a unique participantId."); 
  }
  
  public static void validateParticipantIdsEqual(String paramString1, String paramString2) throws CryptoException {
    if (!paramString1.equals(paramString2))
      throw new CryptoException("Received payload from incorrect partner (" + paramString2 + "). Expected to receive payload from " + paramString1 + "."); 
  }
  
  public static void validateNotNull(Object paramObject, String paramString) {
    if (paramObject == null)
      throw new NullPointerException(paramString + " must not be null"); 
  }
  
  public static BigInteger calculateKeyingMaterial(BigInteger paramBigInteger1, ECPoint paramECPoint1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, ECPoint paramECPoint2) {
    ECPoint eCPoint = paramECPoint2.subtract(paramECPoint1.multiply(paramBigInteger2.multiply(paramBigInteger3).mod(paramBigInteger1))).multiply(paramBigInteger2);
    eCPoint = eCPoint.normalize();
    return eCPoint.getAffineXCoord().toBigInteger();
  }
  
  public static BigInteger calculateMacTag(String paramString1, String paramString2, ECPoint paramECPoint1, ECPoint paramECPoint2, ECPoint paramECPoint3, ECPoint paramECPoint4, BigInteger paramBigInteger, Digest paramDigest) {
    byte[] arrayOfByte1 = calculateMacKey(paramBigInteger, paramDigest);
    HMac hMac = new HMac(paramDigest);
    byte[] arrayOfByte2 = new byte[hMac.getMacSize()];
    hMac.init((CipherParameters)new KeyParameter(arrayOfByte1));
    updateMac((Mac)hMac, "KC_1_U");
    updateMac((Mac)hMac, paramString1);
    updateMac((Mac)hMac, paramString2);
    updateMac((Mac)hMac, paramECPoint1);
    updateMac((Mac)hMac, paramECPoint2);
    updateMac((Mac)hMac, paramECPoint3);
    updateMac((Mac)hMac, paramECPoint4);
    hMac.doFinal(arrayOfByte2, 0);
    Arrays.fill(arrayOfByte1, (byte)0);
    return new BigInteger(arrayOfByte2);
  }
  
  private static byte[] calculateMacKey(BigInteger paramBigInteger, Digest paramDigest) {
    paramDigest.reset();
    updateDigest(paramDigest, paramBigInteger);
    updateDigest(paramDigest, "ECJPAKE_KC");
    byte[] arrayOfByte = new byte[paramDigest.getDigestSize()];
    paramDigest.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  public static void validateMacTag(String paramString1, String paramString2, ECPoint paramECPoint1, ECPoint paramECPoint2, ECPoint paramECPoint3, ECPoint paramECPoint4, BigInteger paramBigInteger1, Digest paramDigest, BigInteger paramBigInteger2) throws CryptoException {
    BigInteger bigInteger = calculateMacTag(paramString2, paramString1, paramECPoint3, paramECPoint4, paramECPoint1, paramECPoint2, paramBigInteger1, paramDigest);
    if (!bigInteger.equals(paramBigInteger2))
      throw new CryptoException("Partner MacTag validation failed. Therefore, the password, MAC, or digest algorithm of each participant does not match."); 
  }
  
  private static void updateMac(Mac paramMac, ECPoint paramECPoint) {
    byte[] arrayOfByte = paramECPoint.getEncoded(true);
    paramMac.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  private static void updateMac(Mac paramMac, String paramString) {
    byte[] arrayOfByte = Strings.toUTF8ByteArray(paramString);
    paramMac.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  private static void updateDigest(Digest paramDigest, ECPoint paramECPoint) {
    byte[] arrayOfByte = paramECPoint.getEncoded(true);
    paramDigest.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  private static void updateDigest(Digest paramDigest, String paramString) {
    byte[] arrayOfByte = Strings.toUTF8ByteArray(paramString);
    paramDigest.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  private static void updateDigest(Digest paramDigest, BigInteger paramBigInteger) {
    byte[] arrayOfByte = BigIntegers.asUnsignedByteArray(paramBigInteger);
    paramDigest.update(arrayOfByte, 0, arrayOfByte.length);
    Arrays.fill(arrayOfByte, (byte)0);
  }
  
  private static byte[] intToByteArray(int paramInt) {
    return new byte[] { (byte)(paramInt >>> 24), (byte)(paramInt >>> 16), (byte)(paramInt >>> 8), (byte)paramInt };
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKEUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */