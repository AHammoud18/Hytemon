package org.bouncycastle.pqc.crypto.mayo;

import org.bouncycastle.util.GF16;

class GF16Utils {
  static final long NIBBLE_MASK_MSB = 8608480567731124087L;
  
  static final long MASK_MSB = -8608480567731124088L;
  
  static final long MASK_LSB = 1229782938247303441L;
  
  static final long NIBBLE_MASK_LSB = -1229782938247303442L;
  
  static void mVecMulAdd(int paramInt1, long[] paramArrayOflong1, int paramInt2, int paramInt3, long[] paramArrayOflong2, int paramInt4) {
    long l1 = paramInt3 & 0xFFFFFFFFL;
    long l2 = l1 & 0x1L;
    long l3 = l1 >>> 1L & 0x1L;
    long l4 = l1 >>> 2L & 0x1L;
    long l5 = l1 >>> 3L & 0x1L;
    for (byte b = 0; b < paramInt1; b++) {
      long l6 = paramArrayOflong1[paramInt2++];
      long l7 = l6 & -l2;
      long l8 = l6 & 0x8888888888888888L;
      l6 &= 0x7777777777777777L;
      long l9 = l8 >>> 3L;
      l6 = l6 << 1L ^ l9 + (l9 << 1L);
      l7 ^= l6 & -l3;
      l8 = l6 & 0x8888888888888888L;
      l6 &= 0x7777777777777777L;
      l9 = l8 >>> 3L;
      l6 = l6 << 1L ^ l9 + (l9 << 1L);
      l7 ^= l6 & -l4;
      l8 = l6 & 0x8888888888888888L;
      l6 &= 0x7777777777777777L;
      l9 = l8 >>> 3L;
      l6 = l6 << 1L ^ l9 + (l9 << 1L);
      paramArrayOflong2[paramInt4++] = paramArrayOflong2[paramInt4++] ^ l7 ^ l6 & -l5;
    } 
  }
  
  static void mulAddMUpperTriangularMatXMat(int paramInt1, long[] paramArrayOflong1, byte[] paramArrayOfbyte, long[] paramArrayOflong2, int paramInt2, int paramInt3, int paramInt4) {
    int i = 0;
    int j = paramInt4 * paramInt1;
    byte b = 0;
    int k = 0;
    int m;
    for (m = 0; b < paramInt3; m += j) {
      byte b1 = b;
      int n;
      for (n = k; b1 < paramInt3; n += paramInt4) {
        byte b2 = 0;
        int i1;
        for (i1 = 0; b2 < paramInt4; i1 += paramInt1) {
          mVecMulAdd(paramInt1, paramArrayOflong1, i, paramArrayOfbyte[n + b2], paramArrayOflong2, paramInt2 + m + i1);
          b2++;
        } 
        i += paramInt1;
        b1++;
      } 
      b++;
      k += paramInt4;
    } 
  }
  
  static void mulAddMatTransXMMat(int paramInt1, byte[] paramArrayOfbyte, long[] paramArrayOflong1, int paramInt2, long[] paramArrayOflong2, int paramInt3, int paramInt4) {
    int i = paramInt4 * paramInt1;
    byte b = 0;
    int j;
    for (j = 0; b < paramInt4; j += i) {
      byte b1 = 0;
      int k = 0;
      int m;
      for (m = 0; b1 < paramInt3; m += i) {
        byte b2 = paramArrayOfbyte[k + b];
        byte b3 = 0;
        int n;
        for (n = 0; b3 < paramInt4; n += paramInt1) {
          mVecMulAdd(paramInt1, paramArrayOflong1, paramInt2 + m + n, b2, paramArrayOflong2, j + n);
          b3++;
        } 
        b1++;
        k += paramInt4;
      } 
      b++;
    } 
  }
  
  static void mulAddMatXMMat(int paramInt1, byte[] paramArrayOfbyte, long[] paramArrayOflong1, long[] paramArrayOflong2, int paramInt2, int paramInt3) {
    int i = paramInt1 * paramInt2;
    byte b = 0;
    int j = 0;
    int k;
    for (k = 0; b < paramInt2; k += i) {
      byte b1 = 0;
      int m;
      for (m = 0; b1 < paramInt3; m += i) {
        byte b2 = paramArrayOfbyte[j + b1];
        byte b3 = 0;
        int n;
        for (n = 0; b3 < paramInt2; n += paramInt1) {
          mVecMulAdd(paramInt1, paramArrayOflong1, m + n, b2, paramArrayOflong2, k + n);
          b3++;
        } 
        b1++;
      } 
      b++;
      j += paramInt3;
    } 
  }
  
  static void mulAddMatXMMat(int paramInt1, byte[] paramArrayOfbyte, long[] paramArrayOflong1, int paramInt2, long[] paramArrayOflong2, int paramInt3, int paramInt4, int paramInt5) {
    int i = paramInt1 * paramInt5;
    byte b = 0;
    int j = 0;
    int k;
    for (k = 0; b < paramInt3; k += paramInt4) {
      byte b1 = 0;
      int m;
      for (m = 0; b1 < paramInt4; m += i) {
        byte b2 = paramArrayOfbyte[k + b1];
        byte b3 = 0;
        int n;
        for (n = 0; b3 < paramInt5; n += paramInt1) {
          mVecMulAdd(paramInt1, paramArrayOflong1, m + n + paramInt2, b2, paramArrayOflong2, j + n);
          b3++;
        } 
        b1++;
      } 
      b++;
      j += i;
    } 
  }
  
  static void mulAddMUpperTriangularMatXMatTrans(int paramInt1, long[] paramArrayOflong1, byte[] paramArrayOfbyte, long[] paramArrayOflong2, int paramInt2, int paramInt3) {
    int i = 0;
    int j = paramInt1 * paramInt3;
    byte b = 0;
    int k;
    for (k = 0; b < paramInt2; k += j) {
      for (byte b1 = b; b1 < paramInt2; b1++) {
        byte b2 = 0;
        int m = 0;
        int n;
        for (n = 0; b2 < paramInt3; n += paramInt1) {
          mVecMulAdd(paramInt1, paramArrayOflong1, i, paramArrayOfbyte[m + b1], paramArrayOflong2, k + n);
          b2++;
          m += paramInt2;
        } 
        i += paramInt1;
      } 
      b++;
    } 
  }
  
  static long mulFx8(byte paramByte, long paramLong) {
    int i = paramByte & 0xFF;
    long l1 = -(i & 0x1) & paramLong ^ -(i >> 1 & 0x1) & paramLong << 1L ^ -(i >> 2 & 0x1) & paramLong << 2L ^ -(i >> 3 & 0x1) & paramLong << 3L;
    long l2 = l1 & 0xF0F0F0F0F0F0F0F0L;
    return (l1 ^ l2 >>> 4L ^ l2 >>> 3L) & 0xF0F0F0F0F0F0F0FL;
  }
  
  static void matMul(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) {
    byte b1 = 0;
    byte b2 = 0;
    byte b3 = 0;
    while (b1 < paramInt3) {
      byte b = 0;
      for (byte b4 = 0; b4 < paramInt2; b4++)
        b = (byte)(b ^ GF16.mul(paramArrayOfbyte1[b2++], paramArrayOfbyte2[paramInt1 + b4])); 
      paramArrayOfbyte3[b3++] = b;
      b1++;
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mayo\GF16Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */