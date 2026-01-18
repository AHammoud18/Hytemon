package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.engines.AsconPermutationFriend;

abstract class AsconBaseDigest extends BufferBaseDigest {
  AsconPermutationFriend.AsconPermutation p = AsconPermutationFriend.getAsconPermutation(ISAPDigest.Friend.getFriend(Friend.INSTANCE));
  
  protected int ASCON_PB_ROUNDS = 12;
  
  protected AsconBaseDigest() {
    super(BufferBaseDigest.ProcessingBufferType.Immediate, 8);
  }
  
  protected abstract long pad(int paramInt);
  
  protected abstract long loadBytes(byte[] paramArrayOfbyte, int paramInt);
  
  protected abstract long loadBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  protected abstract void setBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt);
  
  protected abstract void setBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  protected void processBytes(byte[] paramArrayOfbyte, int paramInt) {
    this.p.x0 ^= loadBytes(paramArrayOfbyte, paramInt);
    this.p.p(this.ASCON_PB_ROUNDS);
  }
  
  protected void finish(byte[] paramArrayOfbyte, int paramInt) {
    padAndAbsorb();
    squeeze(paramArrayOfbyte, paramInt, this.DigestSize);
  }
  
  protected void padAndAbsorb() {
    this.p.x0 ^= loadBytes(this.m_buf, 0, this.m_bufPos) ^ pad(this.m_bufPos);
    this.p.p(12);
  }
  
  protected void squeeze(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    while (paramInt2 > this.BlockSize) {
      setBytes(this.p.x0, paramArrayOfbyte, paramInt1);
      this.p.p(this.ASCON_PB_ROUNDS);
      paramInt1 += this.BlockSize;
      paramInt2 -= this.BlockSize;
    } 
    setBytes(this.p.x0, paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected int hash(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    ensureSufficientOutputBuffer(paramArrayOfbyte, paramInt1, paramInt2);
    padAndAbsorb();
    squeeze(paramArrayOfbyte, paramInt1, paramInt2);
    return paramInt2;
  }
  
  protected void ensureSufficientOutputBuffer(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramInt1 + paramInt2 > paramArrayOfbyte.length)
      throw new OutputLengthException("output buffer is too short"); 
  }
  
  public static class Friend {
    private static final Friend INSTANCE = new Friend();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\digests\AsconBaseDigest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */