package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import org.bouncycastle.crypto.params.HKDFParameters;

public class HKDFParameterSpec implements KeySpec, AlgorithmParameterSpec {
  private final HKDFParameters hkdfParameters;
  
  private final int outputLength;
  
  public HKDFParameterSpec(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) {
    this.hkdfParameters = new HKDFParameters(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3);
    this.outputLength = paramInt;
  }
  
  public byte[] getIKM() {
    return this.hkdfParameters.getIKM();
  }
  
  public boolean skipExtract() {
    return this.hkdfParameters.skipExtract();
  }
  
  public byte[] getSalt() {
    return this.hkdfParameters.getSalt();
  }
  
  public byte[] getInfo() {
    return this.hkdfParameters.getInfo();
  }
  
  public int getOutputLength() {
    return this.outputLength;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\HKDFParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */