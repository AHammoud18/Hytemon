package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.CryptoServicePurpose;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;

public class CSHAKEDigest extends SHAKEDigest {
  private static final byte[] padding = new byte[100];
  
  private byte[] diff;
  
  public CSHAKEDigest(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this(paramInt, CryptoServicePurpose.ANY, paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  public CSHAKEDigest(int paramInt, CryptoServicePurpose paramCryptoServicePurpose, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    super(paramInt, paramCryptoServicePurpose);
    if ((paramArrayOfbyte1 == null || paramArrayOfbyte1.length == 0) && (paramArrayOfbyte2 == null || paramArrayOfbyte2.length == 0)) {
      this.diff = null;
    } else {
      this.diff = Arrays.concatenate(XofUtils.leftEncode((this.rate / 8)), encodeString(paramArrayOfbyte1), encodeString(paramArrayOfbyte2));
      diffPadAndAbsorb();
    } 
  }
  
  public CSHAKEDigest(CSHAKEDigest paramCSHAKEDigest) {
    super(paramCSHAKEDigest);
    this.diff = Arrays.clone(paramCSHAKEDigest.diff);
  }
  
  public CSHAKEDigest(byte[] paramArrayOfbyte) {
    super(paramArrayOfbyte);
    int i = this.state.length * 8 + this.dataQueue.length + 12 + 2;
    if (paramArrayOfbyte.length != i) {
      this.diff = new byte[paramArrayOfbyte.length - i];
      System.arraycopy(paramArrayOfbyte, i, this.diff, 0, this.diff.length);
    } else {
      this.diff = null;
    } 
  }
  
  private void copyIn(CSHAKEDigest paramCSHAKEDigest) {
    copyIn(paramCSHAKEDigest);
    this.diff = Arrays.clone(paramCSHAKEDigest.diff);
  }
  
  private void diffPadAndAbsorb() {
    int i = this.rate / 8;
    absorb(this.diff, 0, this.diff.length);
    int j = this.diff.length % i;
    if (j != 0) {
      int k;
      for (k = i - j; k > padding.length; k -= padding.length)
        absorb(padding, 0, padding.length); 
      absorb(padding, 0, k);
    } 
  }
  
  private byte[] encodeString(byte[] paramArrayOfbyte) {
    return (paramArrayOfbyte == null || paramArrayOfbyte.length == 0) ? XofUtils.leftEncode(0L) : Arrays.concatenate(XofUtils.leftEncode(paramArrayOfbyte.length * 8L), paramArrayOfbyte);
  }
  
  public String getAlgorithmName() {
    return "CSHAKE" + this.fixedOutputLength;
  }
  
  public int doOutput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.diff != null) {
      if (!this.squeezing)
        absorbBits(0, 2); 
      squeeze(paramArrayOfbyte, paramInt1, paramInt2 * 8L);
      return paramInt2;
    } 
    return super.doOutput(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void reset() {
    super.reset();
    if (this.diff != null)
      diffPadAndAbsorb(); 
  }
  
  public byte[] getEncodedState() {
    byte[] arrayOfByte;
    int i = this.state.length * 8 + this.dataQueue.length + 12 + 2;
    if (this.diff == null) {
      arrayOfByte = new byte[i];
      getEncodedState(arrayOfByte);
    } else {
      arrayOfByte = new byte[i + this.diff.length];
      getEncodedState(arrayOfByte);
      System.arraycopy(this.diff, 0, arrayOfByte, i, this.diff.length);
    } 
    return arrayOfByte;
  }
  
  public Memoable copy() {
    return (Memoable)new CSHAKEDigest(this);
  }
  
  public void reset(Memoable paramMemoable) {
    CSHAKEDigest cSHAKEDigest = (CSHAKEDigest)paramMemoable;
    copyIn(cSHAKEDigest);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\digests\CSHAKEDigest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */