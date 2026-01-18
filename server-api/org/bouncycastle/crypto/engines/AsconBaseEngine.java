package org.bouncycastle.crypto.engines;

abstract class AsconBaseEngine extends AEADBaseEngine {
  protected int nr;
  
  protected long K0;
  
  protected long K1;
  
  protected long N0;
  
  protected long N1;
  
  protected long ASCON_IV;
  
  AsconPermutationFriend.AsconPermutation p = new AsconPermutationFriend.AsconPermutation();
  
  protected long dsep;
  
  protected abstract long pad(int paramInt);
  
  protected abstract long loadBytes(byte[] paramArrayOfbyte, int paramInt);
  
  protected abstract void setBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt);
  
  protected abstract void ascon_aeadinit();
  
  protected void finishAAD(AEADBaseEngine.State paramState, boolean paramBoolean) {
    switch (this.m_state.ord) {
      case 2:
      case 6:
        processFinalAAD();
        this.p.p(this.nr);
        break;
    } 
    this.p.x4 ^= this.dsep;
    this.m_aadPos = 0;
    this.m_state = paramState;
  }
  
  protected abstract void processFinalDecrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2);
  
  protected abstract void processFinalEncrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2);
  
  protected void processBufferAAD(byte[] paramArrayOfbyte, int paramInt) {
    this.p.x0 ^= loadBytes(paramArrayOfbyte, paramInt);
    if (this.BlockSize == 16)
      this.p.x1 ^= loadBytes(paramArrayOfbyte, 8 + paramInt); 
    this.p.p(this.nr);
  }
  
  protected void processFinalBlock(byte[] paramArrayOfbyte, int paramInt) {
    if (this.forEncryption) {
      processFinalEncrypt(this.m_buf, this.m_bufPos, paramArrayOfbyte, paramInt);
    } else {
      processFinalDecrypt(this.m_buf, this.m_bufPos, paramArrayOfbyte, paramInt);
    } 
    setBytes(this.p.x3, this.mac, 0);
    setBytes(this.p.x4, this.mac, 8);
  }
  
  protected void processBufferDecrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) {
    long l = loadBytes(paramArrayOfbyte1, paramInt1);
    setBytes(this.p.x0 ^ l, paramArrayOfbyte2, paramInt2);
    this.p.x0 = l;
    if (this.BlockSize == 16) {
      long l1 = loadBytes(paramArrayOfbyte1, paramInt1 + 8);
      setBytes(this.p.x1 ^ l1, paramArrayOfbyte2, paramInt2 + 8);
      this.p.x1 = l1;
    } 
    this.p.p(this.nr);
  }
  
  protected void processBufferEncrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) {
    this.p.x0 ^= loadBytes(paramArrayOfbyte1, paramInt1);
    setBytes(this.p.x0, paramArrayOfbyte2, paramInt2);
    if (this.BlockSize == 16) {
      this.p.x1 ^= loadBytes(paramArrayOfbyte1, paramInt1 + 8);
      setBytes(this.p.x1, paramArrayOfbyte2, paramInt2 + 8);
    } 
    this.p.p(this.nr);
  }
  
  protected void reset(boolean paramBoolean) {
    super.reset(paramBoolean);
    ascon_aeadinit();
  }
  
  public abstract String getAlgorithmVersion();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\engines\AsconBaseEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */