package org.bouncycastle.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.util.Arrays;

public class MLDSAPrivateKeySpec implements KeySpec {
  private final byte[] data;
  
  private final byte[] publicData;
  
  private final MLDSAParameterSpec params;
  
  private final boolean isSeed;
  
  public MLDSAPrivateKeySpec(MLDSAParameterSpec paramMLDSAParameterSpec, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length != 32)
      throw new IllegalArgumentException("incorrect length for seed"); 
    this.isSeed = true;
    this.params = paramMLDSAParameterSpec;
    this.data = Arrays.clone(paramArrayOfbyte);
    this.publicData = null;
  }
  
  public MLDSAPrivateKeySpec(MLDSAParameterSpec paramMLDSAParameterSpec, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.isSeed = false;
    this.params = paramMLDSAParameterSpec;
    this.data = Arrays.clone(paramArrayOfbyte1);
    this.publicData = Arrays.clone(paramArrayOfbyte2);
  }
  
  public boolean isSeed() {
    return this.isSeed;
  }
  
  public MLDSAParameterSpec getParameterSpec() {
    return this.params;
  }
  
  public byte[] getSeed() {
    if (isSeed())
      return Arrays.clone(this.data); 
    throw new IllegalStateException("KeySpec represents long form");
  }
  
  public byte[] getPrivateData() {
    if (!isSeed())
      return Arrays.clone(this.data); 
    throw new IllegalStateException("KeySpec represents seed");
  }
  
  public byte[] getPublicData() {
    if (!isSeed())
      return Arrays.clone(this.publicData); 
    throw new IllegalStateException("KeySpec represents long form");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\MLDSAPrivateKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */