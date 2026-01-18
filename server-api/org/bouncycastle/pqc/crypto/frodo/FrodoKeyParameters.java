package org.bouncycastle.pqc.crypto.frodo;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class FrodoKeyParameters extends AsymmetricKeyParameter {
  private FrodoParameters params;
  
  public FrodoKeyParameters(boolean paramBoolean, FrodoParameters paramFrodoParameters) {
    super(paramBoolean);
    this.params = paramFrodoParameters;
  }
  
  public FrodoParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\frodo\FrodoKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */