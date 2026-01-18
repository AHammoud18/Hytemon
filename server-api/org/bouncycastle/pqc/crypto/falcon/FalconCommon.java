package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.crypto.digests.SHAKEDigest;

class FalconCommon {
  static final int[] l2bound = new int[] { 
      0, 101498, 208714, 428865, 892039, 1852696, 3842630, 7959734, 16468416, 34034726, 
      70265242 };
  
  static void hash_to_point_vartime(SHAKEDigest paramSHAKEDigest, short[] paramArrayOfshort, int paramInt) {
    byte b = 0;
    int i = 1 << paramInt;
    byte[] arrayOfByte = new byte[2];
    while (i > 0) {
      paramSHAKEDigest.doOutput(arrayOfByte, 0, 2);
      int j = (arrayOfByte[0] & 0xFF) << 8 | arrayOfByte[1] & 0xFF;
      if (j < 61445) {
        j %= 12289;
        paramArrayOfshort[b++] = (short)j;
        i--;
      } 
    } 
  }
  
  static int is_short(short[] paramArrayOfshort1, int paramInt1, short[] paramArrayOfshort2, int paramInt2) {
    int i = 1 << paramInt2;
    int j = 0;
    int k = 0;
    for (byte b = 0; b < i; b++) {
      short s = paramArrayOfshort1[paramInt1 + b];
      j += s * s;
      k |= j;
      s = paramArrayOfshort2[b];
      j += s * s;
      k |= j;
    } 
    j |= -(k >>> 31);
    return ((j & 0xFFFFFFFFL) <= l2bound[paramInt2]) ? 1 : 0;
  }
  
  static int is_short_half(int paramInt1, short[] paramArrayOfshort, int paramInt2) {
    int i = 1 << paramInt2;
    int j = -(paramInt1 >>> 31);
    for (byte b = 0; b < i; b++) {
      short s = paramArrayOfshort[b];
      paramInt1 += s * s;
      j |= paramInt1;
    } 
    paramInt1 |= -(j >>> 31);
    return ((paramInt1 & 0xFFFFFFFFL) <= l2bound[paramInt2]) ? 1 : 0;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */