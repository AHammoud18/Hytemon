package org.bouncycastle.pqc.crypto.slhdsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class SLHDSAKeyGenerationParameters extends KeyGenerationParameters {
  private final SLHDSAParameters parameters;
  
  public SLHDSAKeyGenerationParameters(SecureRandom paramSecureRandom, SLHDSAParameters paramSLHDSAParameters) {
    super(paramSecureRandom, -1);
    this.parameters = paramSLHDSAParameters;
  }
  
  SLHDSAParameters getParameters() {
    return this.parameters;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */