package META-INF.versions.9.org.bouncycastle.pqc.crypto.snova;

import org.bouncycastle.util.GF16;

class GF16Utils {
  private static final int GF16_MASK = 585;
  
  static void encodeMergeInHalf(byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {
    int i = paramInt + 1 >>> 1;
    byte b = 0;
    while (b < paramInt / 2) {
      paramArrayOfbyte2[b] = (byte)(paramArrayOfbyte1[b] | paramArrayOfbyte1[i] << 4);
      b++;
      i++;
    } 
    if ((paramInt & 0x1) == 1)
      paramArrayOfbyte2[b] = paramArrayOfbyte1[b]; 
  }
  
  static void decodeMergeInHalf(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
    int i = paramInt + 1 >>> 1;
    for (byte b = 0; b < i; b++) {
      paramArrayOfbyte2[b] = (byte)(paramArrayOfbyte1[b] & 0xF);
      paramArrayOfbyte2[b + i] = (byte)(paramArrayOfbyte1[b] >>> 4 & 0xF);
    } 
  }
  
  static void gf16mTranMulMul(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6, byte[] paramArrayOfbyte7, byte[] paramArrayOfbyte8, int paramInt2) {
    byte b1 = 0;
    int i = 0;
    byte b2 = 0;
    while (b1 < paramInt2) {
      byte b;
      for (b = 0; b < paramInt2; b++) {
        byte b3 = 0;
        byte b4 = 0;
        int k = paramInt1 + b;
        int m;
        for (m = b1; b4 < paramInt2; m += paramInt2) {
          b3 = (byte)(b3 ^ GF16.mul(paramArrayOfbyte1[k], paramArrayOfbyte4[m]));
          b4++;
          k += paramInt2;
        } 
        paramArrayOfbyte6[b] = b3;
      } 
      b = 0;
      int j;
      for (j = 0; b < paramInt2; j += paramInt2) {
        byte b3 = 0;
        for (byte b4 = 0; b4 < paramInt2; b4++)
          b3 = (byte)(b3 ^ GF16.mul(paramArrayOfbyte2[j + b4], paramArrayOfbyte6[b4])); 
        paramArrayOfbyte7[b1 + j] = b3;
        b++;
      } 
      for (b = 0; b < paramInt2; b++)
        paramArrayOfbyte6[b] = GF16.innerProduct(paramArrayOfbyte5, i, paramArrayOfbyte1, paramInt1 + b, paramInt2); 
      for (b = 0; b < paramInt2; b++)
        paramArrayOfbyte8[b2++] = GF16.innerProduct(paramArrayOfbyte6, 0, paramArrayOfbyte3, b, paramInt2); 
      b1++;
      i += paramInt2;
    } 
  }
  
  static void gf16mMulMul(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, int paramInt) {
    byte b1 = 0;
    int i = 0;
    byte b2 = 0;
    while (b1 < paramInt) {
      byte b;
      for (b = 0; b < paramInt; b++)
        paramArrayOfbyte4[b] = GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b, paramInt); 
      for (b = 0; b < paramInt; b++)
        paramArrayOfbyte5[b2++] = GF16.innerProduct(paramArrayOfbyte4, 0, paramArrayOfbyte3, b, paramInt); 
      b1++;
      i += paramInt;
    } 
  }
  
  static void gf16mMul(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) {
    byte b1 = 0;
    int i = 0;
    byte b2 = 0;
    while (b1 < paramInt) {
      for (byte b = 0; b < paramInt; b++)
        paramArrayOfbyte3[b2++] = GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b, paramInt); 
      b1++;
      i += paramInt;
    } 
  }
  
  static void gf16mMulMulTo(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, int paramInt) {
    byte b1 = 0;
    int i = 0;
    byte b2 = 0;
    while (b1 < paramInt) {
      byte b;
      for (b = 0; b < paramInt; b++)
        paramArrayOfbyte4[b] = GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b, paramInt); 
      for (b = 0; b < paramInt; b++)
        paramArrayOfbyte5[b2++] = (byte)(paramArrayOfbyte5[b2++] ^ GF16.innerProduct(paramArrayOfbyte4, 0, paramArrayOfbyte3, b, paramInt)); 
      b1++;
      i += paramInt;
    } 
  }
  
  static void gf16mMulTo(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) {
    byte b1 = 0;
    int i = 0;
    byte b2 = 0;
    while (b1 < paramInt) {
      for (byte b = 0; b < paramInt; b++)
        paramArrayOfbyte3[b2++] = (byte)(paramArrayOfbyte3[b2++] ^ GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b, paramInt)); 
      b1++;
      i += paramInt;
    } 
  }
  
  static void gf16mMulToTo(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, int paramInt) {
    byte b1 = 0;
    int i = 0;
    byte b2 = 0;
    while (b1 < paramInt) {
      for (byte b = 0; b < paramInt; b++) {
        paramArrayOfbyte4[b2] = (byte)(paramArrayOfbyte4[b2] ^ GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b, paramInt));
        paramArrayOfbyte5[b2++] = (byte)(paramArrayOfbyte5[b2++] ^ GF16.innerProduct(paramArrayOfbyte2, i, paramArrayOfbyte3, b, paramInt));
      } 
      b1++;
      i += paramInt;
    } 
  }
  
  static void gf16mMulTo(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2) {
    byte b = 0;
    int i;
    for (i = 0; b < paramInt2; i += paramInt2) {
      for (byte b1 = 0; b1 < paramInt2; b1++)
        paramArrayOfbyte3[paramInt1++] = (byte)(paramArrayOfbyte3[paramInt1++] ^ GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b1, paramInt2)); 
      b++;
    } 
  }
  
  static void gf16mMulTo(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, int paramInt1, int paramInt2) {
    byte b = 0;
    int i;
    for (i = 0; b < paramInt2; i += paramInt2) {
      for (byte b1 = 0; b1 < paramInt2; b1++)
        paramArrayOfbyte5[paramInt1++] = (byte)(paramArrayOfbyte5[paramInt1++] ^ GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, b1, paramInt2) ^ GF16.innerProduct(paramArrayOfbyte3, i, paramArrayOfbyte4, b1, paramInt2)); 
      b++;
    } 
  }
  
  static void gf16mMulTo(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) {
    byte b = 0;
    int i;
    for (i = 0; b < paramInt3; i += paramInt3) {
      for (byte b1 = 0; b1 < paramInt3; b1++)
        paramArrayOfbyte3[paramInt2++] = (byte)(paramArrayOfbyte3[paramInt2++] ^ GF16.innerProduct(paramArrayOfbyte1, i, paramArrayOfbyte2, paramInt1 + b1, paramInt3)); 
      b++;
    } 
  }
  
  static int gf16FromNibble(int paramInt) {
    int i = paramInt | paramInt << 4;
    return i & 0x41 | i << 2 & 0x208;
  }
  
  static int ctGF16IsNotZero(byte paramByte) {
    int i = paramByte & 0xFF;
    return (i | i >>> 1 | i >>> 2 | i >>> 3) & 0x1;
  }
  
  private static int gf16Reduce(int paramInt) {
    int i = paramInt & 0x49249249;
    int j = paramInt >>> 12;
    i ^= j ^ j << 3;
    j = i >>> 12;
    i ^= j ^ j << 3;
    j = i >>> 12;
    i ^= j ^ j << 3;
    return i & 0x249;
  }
  
  static byte gf16ToNibble(int paramInt) {
    int i = gf16Reduce(paramInt);
    i |= i >>> 4;
    return (byte)(i & 0x5 | i >>> 2 & 0xA);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\snova\GF16Utils.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */