package META-INF.versions.9.org.bouncycastle.util;

public class GF16 {
  private static final byte[] F_STAR = new byte[] { 
      1, 2, 4, 8, 3, 6, 12, 11, 5, 10, 
      7, 14, 15, 13, 9 };
  
  private static final byte[] MT4B = new byte[256];
  
  private static final byte[] INV4B = new byte[16];
  
  static byte mt(int paramInt1, int paramInt2) {
    return MT4B[paramInt1 << 4 ^ paramInt2];
  }
  
  public static byte mul(byte paramByte1, byte paramByte2) {
    return MT4B[paramByte1 << 4 | paramByte2];
  }
  
  public static int mul(int paramInt1, int paramInt2) {
    return MT4B[paramInt1 << 4 | paramInt2];
  }
  
  public static byte inv(byte paramByte) {
    return INV4B[paramByte & 0xF];
  }
  
  public static void decode(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
    byte b2 = 0;
    int i = paramInt >> 1;
    byte b1;
    for (b1 = 0; b1 < i; b1++) {
      paramArrayOfbyte2[b2++] = (byte)(paramArrayOfbyte1[b1] & 0xF);
      paramArrayOfbyte2[b2++] = (byte)(paramArrayOfbyte1[b1] >>> 4 & 0xF);
    } 
    if ((paramInt & 0x1) == 1)
      paramArrayOfbyte2[b2] = (byte)(paramArrayOfbyte1[b1] & 0xF); 
  }
  
  public static void decode(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
    int i = paramInt3 >> 1;
    for (byte b = 0; b < i; b++) {
      paramArrayOfbyte2[paramInt2++] = (byte)(paramArrayOfbyte1[paramInt1] & 0xF);
      paramArrayOfbyte2[paramInt2++] = (byte)(paramArrayOfbyte1[paramInt1++] >>> 4 & 0xF);
    } 
    if ((paramInt3 & 0x1) == 1)
      paramArrayOfbyte2[paramInt2] = (byte)(paramArrayOfbyte1[paramInt1] & 0xF); 
  }
  
  public static void encode(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
    byte b2 = 0;
    int i = paramInt >> 1;
    byte b1;
    for (b1 = 0; b1 < i; b1++) {
      int j = paramArrayOfbyte1[b2++] & 0xF;
      int k = (paramArrayOfbyte1[b2++] & 0xF) << 4;
      paramArrayOfbyte2[b1] = (byte)(j | k);
    } 
    if ((paramInt & 0x1) == 1)
      paramArrayOfbyte2[b1] = (byte)(paramArrayOfbyte1[b2] & 0xF); 
  }
  
  public static void encode(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) {
    byte b2 = 0;
    int i = paramInt2 >> 1;
    for (byte b1 = 0; b1 < i; b1++) {
      int j = paramArrayOfbyte1[b2++] & 0xF;
      int k = (paramArrayOfbyte1[b2++] & 0xF) << 4;
      paramArrayOfbyte2[paramInt1++] = (byte)(j | k);
    } 
    if ((paramInt2 & 0x1) == 1)
      paramArrayOfbyte2[paramInt1] = (byte)(paramArrayOfbyte1[b2] & 0xF); 
  }
  
  public static byte innerProduct(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
    byte b = 0;
    byte b1 = 0;
    while (b1 < paramInt3) {
      b = (byte)(b ^ mul(paramArrayOfbyte1[paramInt1++], paramArrayOfbyte2[paramInt2]));
      b1++;
      paramInt2 += paramInt3;
    } 
    return b;
  }
  
  static {
    byte b1;
    for (b1 = 0; b1 < 15; b1++) {
      for (byte b5 = 0; b5 < 15; b5++)
        MT4B[F_STAR[b1] << 4 ^ F_STAR[b5]] = F_STAR[(b1 + b5) % 15]; 
    } 
    b1 = F_STAR[1];
    byte b = F_STAR[14];
    byte b2 = 1;
    byte b3 = 1;
    INV4B[1] = 1;
    for (byte b4 = 0; b4 < 14; b4++) {
      b2 = mt(b2, b1);
      b3 = mt(b3, b);
      INV4B[b2] = (byte)b3;
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastl\\util\GF16.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */