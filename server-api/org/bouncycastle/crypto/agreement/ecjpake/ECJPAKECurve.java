package org.bouncycastle.crypto.agreement.ecjpake;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class ECJPAKECurve {
  private final ECCurve.AbstractFp curve;
  
  private final ECPoint g;
  
  public ECJPAKECurve(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4, BigInteger paramBigInteger5, BigInteger paramBigInteger6, BigInteger paramBigInteger7) {
    ECJPAKEUtil.validateNotNull(paramBigInteger2, "a");
    ECJPAKEUtil.validateNotNull(paramBigInteger3, "b");
    ECJPAKEUtil.validateNotNull(paramBigInteger1, "q");
    ECJPAKEUtil.validateNotNull(paramBigInteger4, "n");
    ECJPAKEUtil.validateNotNull(paramBigInteger5, "h");
    ECJPAKEUtil.validateNotNull(paramBigInteger6, "g_x");
    ECJPAKEUtil.validateNotNull(paramBigInteger7, "g_y");
    if (!paramBigInteger1.isProbablePrime(20))
      throw new IllegalArgumentException("Field size q must be prime"); 
    if (paramBigInteger2.compareTo(BigInteger.ZERO) < 0 || paramBigInteger2.compareTo(paramBigInteger1) >= 0)
      throw new IllegalArgumentException("The parameter 'a' is not in the field [0, q-1]"); 
    if (paramBigInteger3.compareTo(BigInteger.ZERO) < 0 || paramBigInteger3.compareTo(paramBigInteger1) >= 0)
      throw new IllegalArgumentException("The parameter 'b' is not in the field [0, q-1]"); 
    BigInteger bigInteger = calculateDeterminant(paramBigInteger1, paramBigInteger2, paramBigInteger3);
    if (bigInteger.equals(BigInteger.ZERO))
      throw new IllegalArgumentException("The curve is singular, i.e the discriminant is equal to 0 mod q."); 
    if (!paramBigInteger4.isProbablePrime(20))
      throw new IllegalArgumentException("The order n must be prime"); 
    this.curve = (ECCurve.AbstractFp)new ECCurve.Fp(paramBigInteger1, paramBigInteger2, paramBigInteger3, paramBigInteger4, paramBigInteger5);
    this.g = this.curve.validatePoint(paramBigInteger6, paramBigInteger7);
  }
  
  ECJPAKECurve(ECCurve.AbstractFp paramAbstractFp, ECPoint paramECPoint) {
    ECJPAKEUtil.validateNotNull(paramAbstractFp, "curve");
    ECJPAKEUtil.validateNotNull(paramECPoint, "g");
    ECJPAKEUtil.validateNotNull(paramAbstractFp.getOrder(), "n");
    ECJPAKEUtil.validateNotNull(paramAbstractFp.getCofactor(), "h");
    this.curve = paramAbstractFp;
    this.g = paramECPoint;
  }
  
  public ECCurve.AbstractFp getCurve() {
    return this.curve;
  }
  
  public ECPoint getG() {
    return this.g;
  }
  
  public BigInteger getA() {
    return this.curve.getA().toBigInteger();
  }
  
  public BigInteger getB() {
    return this.curve.getB().toBigInteger();
  }
  
  public BigInteger getN() {
    return this.curve.getOrder();
  }
  
  public BigInteger getH() {
    return this.curve.getCofactor();
  }
  
  public BigInteger getQ() {
    return this.curve.getQ();
  }
  
  private static BigInteger calculateDeterminant(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) {
    BigInteger bigInteger1 = paramBigInteger2.multiply(paramBigInteger2).mod(paramBigInteger1).multiply(paramBigInteger2).mod(paramBigInteger1).shiftLeft(2);
    BigInteger bigInteger2 = paramBigInteger3.multiply(paramBigInteger3).mod(paramBigInteger1).multiply(BigInteger.valueOf(27L));
    return bigInteger1.add(bigInteger2).mod(paramBigInteger1);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKECurve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */