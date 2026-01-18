package org.bouncycastle.pqc.crypto.mlkem;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class MLKEMKeyGenerationParameters extends KeyGenerationParameters {
  private final MLKEMParameters params;
  
  public MLKEMKeyGenerationParameters(SecureRandom paramSecureRandom, MLKEMParameters paramMLKEMParameters) {
    super(paramSecureRandom, 256);
    this.params = paramMLKEMParameters;
  }
  
  public MLKEMParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mlkem\MLKEMKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */