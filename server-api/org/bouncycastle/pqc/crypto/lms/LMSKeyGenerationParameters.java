package org.bouncycastle.pqc.crypto.lms;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class LMSKeyGenerationParameters extends KeyGenerationParameters {
  private final LMSParameters lmsParameters;
  
  public LMSKeyGenerationParameters(LMSParameters paramLMSParameters, SecureRandom paramSecureRandom) {
    super(paramSecureRandom, LmsUtils.calculateStrength(paramLMSParameters));
    this.lmsParameters = paramLMSParameters;
  }
  
  public LMSParameters getParameters() {
    return this.lmsParameters;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\lms\LMSKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */