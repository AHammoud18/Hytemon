package org.bouncycastle.crypto.params;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class X448KeyGenerationParameters extends KeyGenerationParameters {
  public X448KeyGenerationParameters(SecureRandom paramSecureRandom) {
    super(paramSecureRandom, 448);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\X448KeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */