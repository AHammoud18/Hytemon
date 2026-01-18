package org.bouncycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class RainbowKeyGenerationParameters extends KeyGenerationParameters {
  private RainbowParameters params;
  
  public RainbowKeyGenerationParameters(SecureRandom paramSecureRandom, RainbowParameters paramRainbowParameters) {
    super(paramSecureRandom, 256);
    this.params = paramRainbowParameters;
  }
  
  public RainbowParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\rainbow\RainbowKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */