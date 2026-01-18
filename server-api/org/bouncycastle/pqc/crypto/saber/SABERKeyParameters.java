package org.bouncycastle.pqc.crypto.saber;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class SABERKeyParameters extends AsymmetricKeyParameter {
  private SABERParameters params;
  
  public SABERKeyParameters(boolean paramBoolean, SABERParameters paramSABERParameters) {
    super(paramBoolean);
    this.params = paramSABERParameters;
  }
  
  public SABERParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\saber\SABERKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */