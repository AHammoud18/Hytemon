package org.bouncycastle.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.util.Arrays;

public class MLKEMPublicKeySpec implements KeySpec {
  private final MLKEMParameterSpec params;
  
  private final byte[] publicData;
  
  public MLKEMPublicKeySpec(MLKEMParameterSpec paramMLKEMParameterSpec, byte[] paramArrayOfbyte) {
    this.params = paramMLKEMParameterSpec;
    this.publicData = Arrays.clone(paramArrayOfbyte);
  }
  
  public MLKEMParameterSpec getParameterSpec() {
    return this.params;
  }
  
  public byte[] getPublicData() {
    return Arrays.clone(this.publicData);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\MLKEMPublicKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */