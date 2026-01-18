package org.bouncycastle.crypto.params;

import org.bouncycastle.math.ec.ECPoint;

public class ECPublicKeyParameters extends ECKeyParameters {
  private final ECPoint q;
  
  public ECPublicKeyParameters(ECPoint paramECPoint, ECDomainParameters paramECDomainParameters) {
    super(false, paramECDomainParameters);
    this.q = paramECDomainParameters.validatePublicPoint(paramECPoint);
  }
  
  public ECPoint getQ() {
    return this.q;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\ECPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */