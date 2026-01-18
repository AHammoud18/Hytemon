package org.bouncycastle.util;

public class Bytes {
  public static final int BYTES = 1;
  
  public static final int SIZE = 8;
  
  public static void xor(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    for (byte b = 0; b < paramInt; b++)
      paramArrayOfbyte3[b] = (byte)(paramArrayOfbyte1[b] ^ paramArrayOfbyte2[b]); 
  }
  
  public static void xor(int paramInt1, byte[] paramArrayOfbyte1, int paramInt2, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt3) {
    for (byte b = 0; b < paramInt1; b++)
      paramArrayOfbyte3[paramInt3++] = (byte)(paramArrayOfbyte1[paramInt2++] ^ paramArrayOfbyte2[b]); 
  }
  
  public static void xor(int paramInt1, byte[] paramArrayOfbyte1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, byte[] paramArrayOfbyte3, int paramInt4) {
    for (byte b = 0; b < paramInt1; b++)
      paramArrayOfbyte3[paramInt4 + b] = (byte)(paramArrayOfbyte1[paramInt2 + b] ^ paramArrayOfbyte2[paramInt3 + b]); 
  }
  
  public static void xor(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) {
    for (byte b = 0; b < paramInt1; b++)
      paramArrayOfbyte3[paramInt2++] = (byte)(paramArrayOfbyte1[b] ^ paramArrayOfbyte2[b]); 
  }
  
  public static void xor(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, byte[] paramArrayOfbyte3, int paramInt3) {
    for (byte b = 0; b < paramInt1; b++)
      paramArrayOfbyte3[paramInt3++] = (byte)(paramArrayOfbyte1[b] ^ paramArrayOfbyte2[paramInt2++]); 
  }
  
  public static void xorTo(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    for (byte b = 0; b < paramInt; b++)
      paramArrayOfbyte2[b] = (byte)(paramArrayOfbyte2[b] ^ paramArrayOfbyte1[b]); 
  }
  
  public static void xorTo(int paramInt1, byte[] paramArrayOfbyte1, int paramInt2, byte[] paramArrayOfbyte2) {
    for (byte b = 0; b < paramInt1; b++)
      paramArrayOfbyte2[b] = (byte)(paramArrayOfbyte2[b] ^ paramArrayOfbyte1[paramInt2++]); 
  }
  
  public static void xorTo(int paramInt1, byte[] paramArrayOfbyte1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) {
    for (byte b = 0; b < paramInt1; b++)
      paramArrayOfbyte2[paramInt3 + b] = (byte)(paramArrayOfbyte2[paramInt3 + b] ^ paramArrayOfbyte1[paramInt2 + b]); 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastl\\util\Bytes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */