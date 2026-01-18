package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.util.Pack;

class FalconRNG {
  byte[] bd = new byte[512];
  
  int ptr = 0;
  
  byte[] sd = new byte[256];
  
  void prng_init(SHAKEDigest paramSHAKEDigest) {
    paramSHAKEDigest.doOutput(this.sd, 0, 56);
    prng_refill();
  }
  
  void prng_refill() {
    int[] arrayOfInt1 = { 1634760805, 857760878, 2036477234, 1797285236 };
    long l = Pack.littleEndianToLong(this.sd, 48);
    int[] arrayOfInt2 = new int[16];
    for (byte b = 0; b < 8; b++) {
      System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, arrayOfInt1.length);
      Pack.littleEndianToInt(this.sd, 0, arrayOfInt2, 4, 12);
      arrayOfInt2[14] = arrayOfInt2[14] ^ (int)l;
      arrayOfInt2[15] = arrayOfInt2[15] ^ (int)(l >>> 32L);
      for (byte b2 = 0; b2 < 10; b2++) {
        QROUND(0, 4, 8, 12, arrayOfInt2);
        QROUND(1, 5, 9, 13, arrayOfInt2);
        QROUND(2, 6, 10, 14, arrayOfInt2);
        QROUND(3, 7, 11, 15, arrayOfInt2);
        QROUND(0, 5, 10, 15, arrayOfInt2);
        QROUND(1, 6, 11, 12, arrayOfInt2);
        QROUND(2, 7, 8, 13, arrayOfInt2);
        QROUND(3, 4, 9, 14, arrayOfInt2);
      } 
      byte b1;
      for (b1 = 0; b1 < 4; b1++)
        arrayOfInt2[b1] = arrayOfInt2[b1] + arrayOfInt1[b1]; 
      for (b1 = 4; b1 < 14; b1++)
        arrayOfInt2[b1] = arrayOfInt2[b1] + Pack.littleEndianToInt(this.sd, 4 * b1 - 16); 
      arrayOfInt2[14] = arrayOfInt2[14] + (Pack.littleEndianToInt(this.sd, 40) ^ (int)l);
      arrayOfInt2[15] = arrayOfInt2[15] + (Pack.littleEndianToInt(this.sd, 44) ^ (int)(l >>> 32L));
      l++;
      for (b1 = 0; b1 < 16; b1++)
        Pack.intToLittleEndian(arrayOfInt2[b1], this.bd, (b << 2) + (b1 << 5)); 
    } 
    Pack.longToLittleEndian(l, this.sd, 48);
    this.ptr = 0;
  }
  
  private void QROUND(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    paramArrayOfint[paramInt1] = paramArrayOfint[paramInt1] + paramArrayOfint[paramInt2];
    paramArrayOfint[paramInt4] = paramArrayOfint[paramInt4] ^ paramArrayOfint[paramInt1];
    paramArrayOfint[paramInt4] = paramArrayOfint[paramInt4] << 16 | paramArrayOfint[paramInt4] >>> 16;
    paramArrayOfint[paramInt3] = paramArrayOfint[paramInt3] + paramArrayOfint[paramInt4];
    paramArrayOfint[paramInt2] = paramArrayOfint[paramInt2] ^ paramArrayOfint[paramInt3];
    paramArrayOfint[paramInt2] = paramArrayOfint[paramInt2] << 12 | paramArrayOfint[paramInt2] >>> 20;
    paramArrayOfint[paramInt1] = paramArrayOfint[paramInt1] + paramArrayOfint[paramInt2];
    paramArrayOfint[paramInt4] = paramArrayOfint[paramInt4] ^ paramArrayOfint[paramInt1];
    paramArrayOfint[paramInt4] = paramArrayOfint[paramInt4] << 8 | paramArrayOfint[paramInt4] >>> 24;
    paramArrayOfint[paramInt3] = paramArrayOfint[paramInt3] + paramArrayOfint[paramInt4];
    paramArrayOfint[paramInt2] = paramArrayOfint[paramInt2] ^ paramArrayOfint[paramInt3];
    paramArrayOfint[paramInt2] = paramArrayOfint[paramInt2] << 7 | paramArrayOfint[paramInt2] >>> 25;
  }
  
  long prng_get_u64() {
    int i = this.ptr;
    if (i >= this.bd.length - 9) {
      prng_refill();
      i = 0;
    } 
    this.ptr = i + 8;
    return Pack.littleEndianToLong(this.bd, i);
  }
  
  byte prng_get_u8() {
    byte b = this.bd[this.ptr++];
    if (this.ptr == this.bd.length)
      prng_refill(); 
    return b;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconRNG.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */