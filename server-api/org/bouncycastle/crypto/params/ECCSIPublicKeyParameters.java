package org.bouncycastle.crypto.params;

import org.bouncycastle.math.ec.ECPoint;

public class ECCSIPublicKeyParameters extends AsymmetricKeyParameter {
  private final ECPoint pvt;
  
  public ECCSIPublicKeyParameters(ECPoint paramECPoint) {
    super(false);
    this.pvt = paramECPoint;
  }
  
  public final ECPoint getPVT() {
    return this.pvt;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\ECCSIPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */