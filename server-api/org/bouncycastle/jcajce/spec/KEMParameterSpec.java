package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KEMParameterSpec extends KTSParameterSpec {
  public KEMParameterSpec(String paramString) {
    this(paramString, 256);
  }
  
  public KEMParameterSpec(String paramString, int paramInt) {
    super(paramString, paramInt, (AlgorithmParameterSpec)null, (AlgorithmIdentifier)null, (byte[])null);
  }
  
  public int getKeySizeInBits() {
    return getKeySize();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\KEMParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */