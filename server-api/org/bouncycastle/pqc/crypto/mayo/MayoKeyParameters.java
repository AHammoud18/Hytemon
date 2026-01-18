package org.bouncycastle.pqc.crypto.mayo;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class MayoKeyParameters extends AsymmetricKeyParameter {
  private final MayoParameters params;
  
  public MayoKeyParameters(boolean paramBoolean, MayoParameters paramMayoParameters) {
    super(paramBoolean);
    this.params = paramMayoParameters;
  }
  
  public MayoParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mayo\MayoKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */