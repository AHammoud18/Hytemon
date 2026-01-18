package META-INF.versions.9.org.bouncycastle.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.jcajce.spec.MLKEMParameterSpec;
import org.bouncycastle.util.Arrays;

public class MLKEMPrivateKeySpec implements KeySpec {
  private final byte[] data;
  
  private final byte[] publicData;
  
  private final MLKEMParameterSpec params;
  
  private final boolean isSeed;
  
  public MLKEMPrivateKeySpec(MLKEMParameterSpec paramMLKEMParameterSpec, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length != 64)
      throw new IllegalArgumentException("incorrect length for seed"); 
    this.isSeed = true;
    this.params = paramMLKEMParameterSpec;
    this.data = Arrays.clone(paramArrayOfbyte);
    this.publicData = null;
  }
  
  public MLKEMPrivateKeySpec(MLKEMParameterSpec paramMLKEMParameterSpec, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.isSeed = false;
    this.params = paramMLKEMParameterSpec;
    this.data = Arrays.clone(paramArrayOfbyte1);
    this.publicData = Arrays.clone(paramArrayOfbyte2);
  }
  
  public boolean isSeed() {
    return this.isSeed;
  }
  
  public MLKEMParameterSpec getParameterSpec() {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajce\spec\MLKEMPrivateKeySpec.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */