package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.BigIntegers;

public class SAKKEPrivateKeyParameters extends AsymmetricKeyParameter {
  private static final BigInteger qMinOne = SAKKEPublicKeyParameters.q.subtract(BigInteger.ONE);
  
  private final SAKKEPublicKeyParameters publicParams;
  
  private final BigInteger z;
  
  public SAKKEPrivateKeyParameters(BigInteger paramBigInteger, SAKKEPublicKeyParameters paramSAKKEPublicKeyParameters) {
    super(true);
    this.z = paramBigInteger;
    this.publicParams = paramSAKKEPublicKeyParameters;
    ECPoint eCPoint = paramSAKKEPublicKeyParameters.getPoint().multiply(paramBigInteger).normalize();
    if (!eCPoint.equals(paramSAKKEPublicKeyParameters.getZ()))
      throw new IllegalStateException("public key and private key of SAKKE do not match"); 
  }
  
  public SAKKEPrivateKeyParameters(SecureRandom paramSecureRandom) {
    super(true);
    this.z = BigIntegers.createRandomInRange(BigIntegers.TWO, qMinOne, paramSecureRandom);
    BigInteger bigInteger = BigIntegers.createRandomInRange(BigIntegers.TWO, qMinOne, paramSecureRandom);
    this.publicParams = new SAKKEPublicKeyParameters(bigInteger, SAKKEPublicKeyParameters.P.multiply(this.z).normalize());
  }
  
  public SAKKEPublicKeyParameters getPublicParams() {
    return this.publicParams;
  }
  
  public BigInteger getMasterSecret() {
    return this.z;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\SAKKEPrivateKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */