package org.bouncycastle.pqc.crypto.mayo;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.MultiBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CTRModeCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

class Utils {
  public static void unpackMVecs(byte[] paramArrayOfbyte, int paramInt1, long[] paramArrayOflong, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt4 + 15 >> 4;
    int j = paramInt4 >> 1;
    int k = 8 - (i << 3) + j;
    int m = paramInt3 - 1;
    paramInt2 += m * i;
    for (paramInt1 += m * j; m >= 0; paramInt1 -= j) {
      byte b;
      for (b = 0; b < i - 1; b++)
        paramArrayOflong[paramInt2 + b] = Pack.littleEndianToLong(paramArrayOfbyte, paramInt1 + (b << 3)); 
      paramArrayOflong[paramInt2 + b] = Pack.littleEndianToLong(paramArrayOfbyte, paramInt1 + (b << 3), k);
      m--;
      paramInt2 -= i;
    } 
  }
  
  public static void packMVecs(long[] paramArrayOflong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt3 + 15 >> 4;
    int j = paramInt3 >> 1;
    int k = 8 - (i << 3) + j;
    byte b = 0;
    int m;
    for (m = 0; b < paramInt2; m += i) {
      byte b1;
      for (b1 = 0; b1 < i - 1; b1++)
        Pack.longToLittleEndian(paramArrayOflong[m + b1], paramArrayOfbyte, paramInt1 + (b1 << 3)); 
      Pack.longToLittleEndian(paramArrayOflong[m + b1], paramArrayOfbyte, paramInt1 + (b1 << 3), k);
      b++;
      paramInt1 += j;
    } 
  }
  
  public static void expandP1P2(MayoParameters paramMayoParameters, long[] paramArrayOflong, byte[] paramArrayOfbyte) {
    int i = paramMayoParameters.getP1Bytes() + paramMayoParameters.getP2Bytes();
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[16];
    MultiBlockCipher multiBlockCipher = AESEngine.newInstance();
    CTRModeCipher cTRModeCipher = SICBlockCipher.newInstance((BlockCipher)multiBlockCipher);
    ParametersWithIV parametersWithIV = new ParametersWithIV((CipherParameters)new KeyParameter(Arrays.copyOf(paramArrayOfbyte, paramMayoParameters.getPkSeedBytes())), arrayOfByte2);
    cTRModeCipher.init(true, (CipherParameters)parametersWithIV);
    int j = cTRModeCipher.getBlockSize();
    byte[] arrayOfByte3 = new byte[j];
    byte[] arrayOfByte4 = new byte[j];
    int k;
    for (k = 0; k + j <= i; k += j) {
      cTRModeCipher.processBlock(arrayOfByte3, 0, arrayOfByte4, 0);
      System.arraycopy(arrayOfByte4, 0, arrayOfByte1, k, j);
    } 
    if (k < i) {
      cTRModeCipher.processBlock(arrayOfByte3, 0, arrayOfByte4, 0);
      int n = i - k;
      System.arraycopy(arrayOfByte4, 0, arrayOfByte1, k, n);
    } 
    int m = (paramMayoParameters.getP1Limbs() + paramMayoParameters.getP2Limbs()) / paramMayoParameters.getMVecLimbs();
    unpackMVecs(arrayOfByte1, 0, paramArrayOflong, 0, m, paramMayoParameters.getM());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mayo\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */