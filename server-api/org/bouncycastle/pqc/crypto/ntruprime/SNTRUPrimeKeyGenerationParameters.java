package org.bouncycastle.pqc.crypto.ntruprime;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class SNTRUPrimeKeyGenerationParameters extends KeyGenerationParameters {
  private final SNTRUPrimeParameters sntrupParams;
  
  public SNTRUPrimeKeyGenerationParameters(SecureRandom paramSecureRandom, SNTRUPrimeParameters paramSNTRUPrimeParameters) {
    super((null != paramSecureRandom) ? paramSecureRandom : CryptoServicesRegistrar.getSecureRandom(), 256);
    this.sntrupParams = paramSNTRUPrimeParameters;
  }
  
  public SNTRUPrimeParameters getSntrupParams() {
    return this.sntrupParams;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntruprime\SNTRUPrimeKeyGenerationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */