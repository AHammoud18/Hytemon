package org.bouncycastle.crypto.agreement.ecjpake;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint;

public class ECSchnorrZKP {
  private final ECPoint V;
  
  private final BigInteger r;
  
  ECSchnorrZKP(ECPoint paramECPoint, BigInteger paramBigInteger) {
    this.V = paramECPoint;
    this.r = paramBigInteger;
  }
  
  public ECPoint getV() {
    return this.V;
  }
  
  public BigInteger getr() {
    return this.r;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECSchnorrZKP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */