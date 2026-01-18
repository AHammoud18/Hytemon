package org.bouncycastle.pqc.crypto.mlkem;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class MLKEMKeyParameters extends AsymmetricKeyParameter {
  private MLKEMParameters params;
  
  public MLKEMKeyParameters(boolean paramBoolean, MLKEMParameters paramMLKEMParameters) {
    super(paramBoolean);
    this.params = paramMLKEMParameters;
  }
  
  public MLKEMParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mlkem\MLKEMKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */