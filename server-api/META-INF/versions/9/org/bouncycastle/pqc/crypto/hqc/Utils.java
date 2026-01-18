package META-INF.versions.9.org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.util.Pack;

class Utils {
  static void fromLongArrayToByteArray(byte[] paramArrayOfbyte, long[] paramArrayOflong) {
    int i = paramArrayOfbyte.length / 8;
    int j;
    for (j = 0; j != i; j++)
      Pack.longToLittleEndian(paramArrayOflong[j], paramArrayOfbyte, j * 8); 
    if (paramArrayOfbyte.length % 8 != 0) {
      j = i * 8;
      byte b = 0;
      while (j < paramArrayOfbyte.length)
        paramArrayOfbyte[j++] = (byte)(int)(paramArrayOflong[i] >>> b++ * 8); 
    } 
  }
  
  static void fromLongArrayToByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, long[] paramArrayOflong) {
    int i = paramInt2 >> 3;
    int j;
    for (j = 0; j != i; j++) {
      Pack.longToLittleEndian(paramArrayOflong[j], paramArrayOfbyte, paramInt1);
      paramInt1 += 8;
    } 
    if ((paramInt2 & 0x7) != 0) {
      j = 0;
      while (paramInt1 < paramArrayOfbyte.length)
        paramArrayOfbyte[paramInt1++] = (byte)(int)(paramArrayOflong[i] >>> j++ * 8); 
    } 
  }
  
  static long bitMask(long paramLong1, long paramLong2) {
    return (1L << (int)(paramLong1 % paramLong2)) - 1L;
  }
  
  static void fromByteArrayToLongArray(long[] paramArrayOflong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = paramArrayOfbyte;
    if (paramInt2 % 8 != 0) {
      arrayOfByte = new byte[(paramInt2 + 7) / 8 * 8];
      System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
      paramInt1 = 0;
    } 
    int i = Math.min(paramArrayOflong.length, paramInt2 + 7 >>> 3);
    for (byte b = 0; b < i; b++) {
      paramArrayOflong[b] = Pack.littleEndianToLong(arrayOfByte, paramInt1);
      paramInt1 += 8;
    } 
  }
  
  static void fromByte32ArrayToLongArray(long[] paramArrayOflong, int[] paramArrayOfint) {
    for (byte b = 0; b != paramArrayOfint.length; b += 2) {
      paramArrayOflong[b / 2] = paramArrayOfint[b] & 0xFFFFFFFFL;
      paramArrayOflong[b / 2] = paramArrayOflong[b / 2] | paramArrayOfint[b + 1] << 32L;
    } 
  }
  
  static void fromLongArrayToByte32Array(int[] paramArrayOfint, long[] paramArrayOflong) {
    for (byte b = 0; b != paramArrayOflong.length; b++) {
      paramArrayOfint[2 * b] = (int)paramArrayOflong[b];
      paramArrayOfint[2 * b + 1] = (int)(paramArrayOflong[b] >> 32L);
    } 
  }
  
  static void copyBytes(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3) {
    System.arraycopy(paramArrayOfint1, paramInt1, paramArrayOfint2, paramInt2, paramInt3 / 2);
  }
  
  static int getByteSizeFromBitSize(int paramInt) {
    return (paramInt + 7) / 8;
  }
  
  static int getByte64SizeFromBitSize(int paramInt) {
    return (paramInt + 63) / 64;
  }
  
  static int toUnsigned8bits(int paramInt) {
    return paramInt & 0xFF;
  }
  
  static int toUnsigned16Bits(int paramInt) {
    return paramInt & 0xFFFF;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\hqc\Utils.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */