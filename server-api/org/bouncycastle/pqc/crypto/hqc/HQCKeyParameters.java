package org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class HQCKeyParameters extends AsymmetricKeyParameter {
  private final HQCParameters params;
  
  public HQCKeyParameters(boolean paramBoolean, HQCParameters paramHQCParameters) {
    super(paramBoolean);
    this.params = paramHQCParameters;
  }
  
  public HQCParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\hqc\HQCKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */