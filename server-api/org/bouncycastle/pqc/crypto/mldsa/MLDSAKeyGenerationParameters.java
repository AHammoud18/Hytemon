package org.bouncycastle.pqc.crypto.mldsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class MLDSAKeyGenerationParameters extends KeyGenerationParameters {
  private final MLDSAParameters params;
  
  public MLDSAKeyGenerationParameters(SecureRandom paramSecureRandom, MLDSAParameters paramMLDSAParameters) {
    super(paramSecureRandom, 256);
    this.params = paramMLDSAParameters;
  }
  
  public MLDSAParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\MLDSAKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */