package META-INF.versions.9.org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.pqc.crypto.hqc.Utils;
import org.bouncycastle.util.Arrays;

class ReedMuller {
  static void encodeSub(Codeword paramCodeword, int paramInt) {
    int i = Bit0Mask(paramInt >> 7);
    i ^= Bit0Mask(paramInt) & 0xAAAAAAAA;
    i ^= Bit0Mask(paramInt >> 1) & 0xCCCCCCCC;
    i ^= Bit0Mask(paramInt >> 2) & 0xF0F0F0F0;
    i ^= Bit0Mask(paramInt >> 3) & 0xFF00FF00;
    i ^= Bit0Mask(paramInt >> 4) & 0xFFFF0000;
    paramCodeword.type32[0] = i;
    i ^= Bit0Mask(paramInt >> 5);
    paramCodeword.type32[1] = i;
    i ^= Bit0Mask(paramInt >> 6);
    paramCodeword.type32[3] = i;
    i ^= Bit0Mask(paramInt >> 5);
    paramCodeword.type32[2] = i;
  }
  
  private static void hadamardTransform(int[] paramArrayOfint1, int[] paramArrayOfint2) {
    int[] arrayOfInt1 = Arrays.clone(paramArrayOfint1);
    int[] arrayOfInt2 = Arrays.clone(paramArrayOfint2);
    for (byte b = 0; b < 7; b++) {
      for (byte b1 = 0; b1 < 64; b1++) {
        arrayOfInt2[b1] = arrayOfInt1[2 * b1] + arrayOfInt1[2 * b1 + 1];
        arrayOfInt2[b1 + 64] = arrayOfInt1[2 * b1] - arrayOfInt1[2 * b1 + 1];
      } 
      int[] arrayOfInt = arrayOfInt1;
      arrayOfInt1 = arrayOfInt2;
      arrayOfInt2 = arrayOfInt;
    } 
    System.arraycopy(arrayOfInt2, 0, paramArrayOfint1, 0, paramArrayOfint1.length);
    System.arraycopy(arrayOfInt1, 0, paramArrayOfint2, 0, paramArrayOfint2.length);
  }
  
  private static void expandThenSum(int[] paramArrayOfint, Codeword[] paramArrayOfCodeword, int paramInt1, int paramInt2) {
    byte b;
    for (b = 0; b < 4; b++) {
      for (byte b1 = 0; b1 < 32; b1++)
        paramArrayOfint[b * 32 + b1] = (paramArrayOfCodeword[paramInt1]).type32[b] >> b1 & 0x1; 
    } 
    for (b = 1; b < paramInt2; b++) {
      for (byte b1 = 0; b1 < 4; b1++) {
        for (byte b2 = 0; b2 < 32; b2++)
          paramArrayOfint[b1 * 32 + b2] = paramArrayOfint[b1 * 32 + b2] + ((paramArrayOfCodeword[b + paramInt1]).type32[b1] >> b2 & 0x1); 
      } 
    } 
  }
  
  private static int findPeaks(int[] paramArrayOfint) {
    int i = 0;
    boolean bool = false;
    int j = 0;
    byte b;
    for (b = 0; b < ''; b++) {
      int k = paramArrayOfint[b];
      int m = (k > 0) ? -1 : 0;
      int n = m & k | (m ^ 0xFFFFFFFF) & -k;
      bool = (n > i) ? k : bool;
      j = (n > i) ? b : j;
      i = Math.max(n, i);
    } 
    b = bool ? 1 : 0;
    j |= 128 * b;
    return j;
  }
  
  private static int Bit0Mask(int paramInt) {
    return -(paramInt & 0x1);
  }
  
  public static void encode(long[] paramArrayOflong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = Arrays.clone(paramArrayOfbyte);
    Codeword[] arrayOfCodeword = new Codeword[paramInt1 * paramInt2];
    byte b;
    for (b = 0; b < arrayOfCodeword.length; b++)
      arrayOfCodeword[b] = new Codeword(); 
    for (b = 0; b < paramInt1; b++) {
      int i = b * paramInt2;
      encodeSub(arrayOfCodeword[i], arrayOfByte[b]);
      for (byte b1 = 1; b1 < paramInt2; b1++)
        arrayOfCodeword[i + b1] = arrayOfCodeword[i]; 
    } 
    CopyCWD(paramArrayOflong, arrayOfCodeword);
  }
  
  private static void CopyCWD(long[] paramArrayOflong, Codeword[] paramArrayOfCodeword) {
    int[] arrayOfInt = new int[paramArrayOfCodeword.length * 4];
    boolean bool = false;
    for (byte b = 0; b < paramArrayOfCodeword.length; b++) {
      System.arraycopy((paramArrayOfCodeword[b]).type32, 0, arrayOfInt, bool, (paramArrayOfCodeword[b]).type32.length);
      bool += true;
    } 
    Utils.fromByte32ArrayToLongArray(paramArrayOflong, arrayOfInt);
  }
  
  public static void decode(byte[] paramArrayOfbyte, long[] paramArrayOflong, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = Arrays.clone(paramArrayOfbyte);
    Codeword[] arrayOfCodeword = new Codeword[paramArrayOflong.length / 2];
    int[] arrayOfInt1 = new int[paramArrayOflong.length * 2];
    Utils.fromLongArrayToByte32Array(arrayOfInt1, paramArrayOflong);
    for (byte b1 = 0; b1 < arrayOfCodeword.length; b1++) {
      arrayOfCodeword[b1] = new Codeword();
      System.arraycopy(arrayOfInt1, b1 * 4, (arrayOfCodeword[b1]).type32, 0, 4);
    } 
    int[] arrayOfInt2 = new int[128];
    int[] arrayOfInt3 = new int[128];
    for (byte b2 = 0; b2 < paramInt1; b2++) {
      expandThenSum(arrayOfInt2, arrayOfCodeword, b2 * paramInt2, paramInt2);
      hadamardTransform(arrayOfInt2, arrayOfInt3);
      arrayOfInt3[0] = arrayOfInt3[0] - 64 * paramInt2;
      arrayOfByte[b2] = (byte)findPeaks(arrayOfInt3);
    } 
    CopyCWD(paramArrayOflong, arrayOfCodeword);
    System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\hqc\ReedMuller.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */