package org.bouncycastle.jcajce.spec;

import java.security.spec.EncodedKeySpec;

public class RawEncodedKeySpec extends EncodedKeySpec {
  public RawEncodedKeySpec(byte[] paramArrayOfbyte) {
    super(paramArrayOfbyte);
  }
  
  public String getFormat() {
    return "RAW";
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\RawEncodedKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */