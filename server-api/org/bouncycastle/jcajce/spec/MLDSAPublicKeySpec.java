package org.bouncycastle.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.util.Arrays;

public class MLDSAPublicKeySpec implements KeySpec {
  private final MLDSAParameterSpec params;
  
  private final byte[] publicData;
  
  public MLDSAPublicKeySpec(MLDSAParameterSpec paramMLDSAParameterSpec, byte[] paramArrayOfbyte) {
    this.params = paramMLDSAParameterSpec;
    this.publicData = Arrays.clone(paramArrayOfbyte);
  }
  
  public MLDSAParameterSpec getParameterSpec() {
    return this.params;
  }
  
  public byte[] getPublicData() {
    return Arrays.clone(this.publicData);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\MLDSAPublicKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */