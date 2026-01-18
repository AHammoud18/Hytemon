package org.bouncycastle.pqc.crypto.mayo;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class MayoKeyGenerationParameters extends KeyGenerationParameters {
  private final MayoParameters params;
  
  public MayoKeyGenerationParameters(SecureRandom paramSecureRandom, MayoParameters paramMayoParameters) {
    super(paramSecureRandom, 256);
    this.params = paramMayoParameters;
  }
  
  public MayoParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mayo\MayoKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */