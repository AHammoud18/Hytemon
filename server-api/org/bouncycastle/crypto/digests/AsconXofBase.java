package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.Xof;

abstract class AsconXofBase extends AsconBaseDigest implements Xof {
  private boolean m_squeezing;
  
  private final byte[] buffer = new byte[this.BlockSize];
  
  private int bytesInBuffer;
  
  public void update(byte paramByte) {
    ensureNoAbsorbWhileSqueezing(this.m_squeezing);
    super.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    ensureNoAbsorbWhileSqueezing(this.m_squeezing);
    super.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public int doOutput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    ensureSufficientOutputBuffer(paramArrayOfbyte, paramInt1, paramInt2);
    int i = 0;
    if (this.bytesInBuffer != 0) {
      int k = this.BlockSize - this.bytesInBuffer;
      int m = Math.min(paramInt2, this.bytesInBuffer);
      System.arraycopy(this.buffer, k, paramArrayOfbyte, paramInt1, m);
      this.bytesInBuffer -= m;
      i += m;
    } 
    int j = paramInt2 - i;
    if (j >= this.BlockSize) {
      int k = j - j % this.BlockSize;
      i += hash(paramArrayOfbyte, paramInt1 + i, k);
    } 
    if (i < paramInt2) {
      hash(this.buffer, 0, this.BlockSize);
      int k = paramInt2 - i;
      System.arraycopy(this.buffer, 0, paramArrayOfbyte, paramInt1 + i, k);
      this.bytesInBuffer = this.buffer.length - k;
      i += k;
    } 
    return i;
  }
  
  public int doFinal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = doOutput(paramArrayOfbyte, paramInt1, paramInt2);
    reset();
    return i;
  }
  
  public void reset() {
    this.m_squeezing = false;
    this.bytesInBuffer = 0;
    super.reset();
  }
  
  protected void padAndAbsorb() {
    if (!this.m_squeezing) {
      this.m_squeezing = true;
      super.padAndAbsorb();
    } else {
      this.p.p(this.ASCON_PB_ROUNDS);
    } 
  }
  
  private void ensureNoAbsorbWhileSqueezing(boolean paramBoolean) {
    if (paramBoolean)
      throw new IllegalStateException("attempt to absorb while squeezing"); 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\digests\AsconXofBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */