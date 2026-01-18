package org.bouncycastle.jcajce.spec;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Arrays;

public class KEMKDFSpec {
  private final String keyAlgorithmName;
  
  private final int keySizeInBits;
  
  private final AlgorithmIdentifier kdfAlgorithm;
  
  private final byte[] otherInfo;
  
  protected KEMKDFSpec(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte, String paramString, int paramInt) {
    this.keyAlgorithmName = paramString;
    this.keySizeInBits = paramInt;
    this.kdfAlgorithm = paramAlgorithmIdentifier;
    this.otherInfo = paramArrayOfbyte;
  }
  
  public String getKeyAlgorithmName() {
    return this.keyAlgorithmName;
  }
  
  public int getKeySize() {
    return this.keySizeInBits;
  }
  
  public AlgorithmIdentifier getKdfAlgorithm() {
    return this.kdfAlgorithm;
  }
  
  public byte[] getOtherInfo() {
    return Arrays.clone(this.otherInfo);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\KEMKDFSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */