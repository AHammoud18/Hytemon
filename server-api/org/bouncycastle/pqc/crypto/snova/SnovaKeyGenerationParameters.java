package org.bouncycastle.pqc.crypto.snova;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class SnovaKeyGenerationParameters extends KeyGenerationParameters {
  private final SnovaParameters params;
  
  public SnovaKeyGenerationParameters(SecureRandom paramSecureRandom, SnovaParameters paramSnovaParameters) {
    super(paramSecureRandom, -1);
    this.params = paramSnovaParameters;
  }
  
  public SnovaParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\snova\SnovaKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */