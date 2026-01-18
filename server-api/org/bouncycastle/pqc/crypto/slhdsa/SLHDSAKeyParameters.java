package org.bouncycastle.pqc.crypto.slhdsa;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class SLHDSAKeyParameters extends AsymmetricKeyParameter {
  private final SLHDSAParameters parameters;
  
  protected SLHDSAKeyParameters(boolean paramBoolean, SLHDSAParameters paramSLHDSAParameters) {
    super(paramBoolean);
    this.parameters = paramSLHDSAParameters;
  }
  
  public SLHDSAParameters getParameters() {
    return this.parameters;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */