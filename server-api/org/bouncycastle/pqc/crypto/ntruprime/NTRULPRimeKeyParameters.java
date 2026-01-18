package org.bouncycastle.pqc.crypto.ntruprime;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class NTRULPRimeKeyParameters extends AsymmetricKeyParameter {
  private final NTRULPRimeParameters params;
  
  public NTRULPRimeKeyParameters(boolean paramBoolean, NTRULPRimeParameters paramNTRULPRimeParameters) {
    super(paramBoolean);
    this.params = paramNTRULPRimeParameters;
  }
  
  public NTRULPRimeParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntruprime\NTRULPRimeKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */