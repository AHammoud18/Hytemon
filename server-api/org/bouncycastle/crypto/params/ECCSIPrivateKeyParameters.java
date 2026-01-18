package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class ECCSIPrivateKeyParameters extends AsymmetricKeyParameter {
  private final BigInteger ssk;
  
  private final ECCSIPublicKeyParameters pub;
  
  public ECCSIPrivateKeyParameters(BigInteger paramBigInteger, ECCSIPublicKeyParameters paramECCSIPublicKeyParameters) {
    super(true);
    this.ssk = paramBigInteger;
    this.pub = paramECCSIPublicKeyParameters;
  }
  
  public ECCSIPublicKeyParameters getPublicKeyParameters() {
    return this.pub;
  }
  
  public BigInteger getSSK() {
    return this.ssk;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\ECCSIPrivateKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */