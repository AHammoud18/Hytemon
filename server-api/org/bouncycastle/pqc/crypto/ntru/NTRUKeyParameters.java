package org.bouncycastle.pqc.crypto.ntru;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public abstract class NTRUKeyParameters extends AsymmetricKeyParameter {
  private final NTRUParameters params;
  
  NTRUKeyParameters(boolean paramBoolean, NTRUParameters paramNTRUParameters) {
    super(paramBoolean);
    this.params = paramNTRUParameters;
  }
  
  public NTRUParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\NTRUKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */