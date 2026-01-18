package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.util.Arrays;

public class ParametersWithContext implements CipherParameters {
  private CipherParameters parameters;
  
  private byte[] context;
  
  public ParametersWithContext(CipherParameters paramCipherParameters, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      throw new NullPointerException("'context' cannot be null"); 
    this.parameters = paramCipherParameters;
    this.context = Arrays.clone(paramArrayOfbyte);
  }
  
  public void copyContextTo(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.context.length != paramInt2)
      throw new IllegalArgumentException("len"); 
    System.arraycopy(this.context, 0, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] getContext() {
    return Arrays.clone(this.context);
  }
  
  public int getContextLength() {
    return this.context.length;
  }
  
  public CipherParameters getParameters() {
    return this.parameters;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\params\ParametersWithContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */