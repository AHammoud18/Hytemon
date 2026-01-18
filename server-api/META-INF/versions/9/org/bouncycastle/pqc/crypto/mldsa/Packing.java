package META-INF.versions.9.org.bouncycastle.pqc.crypto.mldsa;

import org.bouncycastle.pqc.crypto.mldsa.MLDSAEngine;
import org.bouncycastle.pqc.crypto.mldsa.PolyVecK;
import org.bouncycastle.pqc.crypto.mldsa.PolyVecL;
import org.bouncycastle.util.Arrays;

class Packing {
  static byte[] packPublicKey(PolyVecK paramPolyVecK, MLDSAEngine paramMLDSAEngine) {
    byte[] arrayOfByte = new byte[paramMLDSAEngine.getCryptoPublicKeyBytes() - 32];
    for (byte b = 0; b < paramMLDSAEngine.getDilithiumK(); b++)
      System.arraycopy(paramPolyVecK.getVectorIndex(b).polyt1Pack(), 0, arrayOfByte, b * 320, 320); 
    return arrayOfByte;
  }
  
  static PolyVecK unpackPublicKey(PolyVecK paramPolyVecK, byte[] paramArrayOfbyte, MLDSAEngine paramMLDSAEngine) {
    for (byte b = 0; b < paramMLDSAEngine.getDilithiumK(); b++)
      paramPolyVecK.getVectorIndex(b).polyt1Unpack(Arrays.copyOfRange(paramArrayOfbyte, b * 320, (b + 1) * 320)); 
    return paramPolyVecK;
  }
  
  static byte[][] packSecretKey(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, PolyVecK paramPolyVecK1, PolyVecL paramPolyVecL, PolyVecK paramPolyVecK2, MLDSAEngine paramMLDSAEngine) {
    byte[][] arrayOfByte = new byte[6][];
    arrayOfByte[0] = paramArrayOfbyte1;
    arrayOfByte[1] = paramArrayOfbyte3;
    arrayOfByte[2] = paramArrayOfbyte2;
    arrayOfByte[3] = new byte[paramMLDSAEngine.getDilithiumL() * paramMLDSAEngine.getDilithiumPolyEtaPackedBytes()];
    byte b;
    for (b = 0; b < paramMLDSAEngine.getDilithiumL(); b++)
      paramPolyVecL.getVectorIndex(b).polyEtaPack(arrayOfByte[3], b * paramMLDSAEngine.getDilithiumPolyEtaPackedBytes()); 
    arrayOfByte[4] = new byte[paramMLDSAEngine.getDilithiumK() * paramMLDSAEngine.getDilithiumPolyEtaPackedBytes()];
    for (b = 0; b < paramMLDSAEngine.getDilithiumK(); b++)
      paramPolyVecK2.getVectorIndex(b).polyEtaPack(arrayOfByte[4], b * paramMLDSAEngine.getDilithiumPolyEtaPackedBytes()); 
    arrayOfByte[5] = new byte[paramMLDSAEngine.getDilithiumK() * 416];
    for (b = 0; b < paramMLDSAEngine.getDilithiumK(); b++)
      paramPolyVecK1.getVectorIndex(b).polyt0Pack(arrayOfByte[5], b * 416); 
    return arrayOfByte;
  }
  
  static void unpackSecretKey(PolyVecK paramPolyVecK1, PolyVecL paramPolyVecL, PolyVecK paramPolyVecK2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, MLDSAEngine paramMLDSAEngine) {
    byte b;
    for (b = 0; b < paramMLDSAEngine.getDilithiumL(); b++)
      paramPolyVecL.getVectorIndex(b).polyEtaUnpack(paramArrayOfbyte2, b * paramMLDSAEngine.getDilithiumPolyEtaPackedBytes()); 
    for (b = 0; b < paramMLDSAEngine.getDilithiumK(); b++)
      paramPolyVecK2.getVectorIndex(b).polyEtaUnpack(paramArrayOfbyte3, b * paramMLDSAEngine.getDilithiumPolyEtaPackedBytes()); 
    for (b = 0; b < paramMLDSAEngine.getDilithiumK(); b++)
      paramPolyVecK1.getVectorIndex(b).polyt0Unpack(paramArrayOfbyte1, b * 416); 
  }
  
  static void packSignature(byte[] paramArrayOfbyte, PolyVecL paramPolyVecL, PolyVecK paramPolyVecK, MLDSAEngine paramMLDSAEngine) {
    int i = paramMLDSAEngine.getDilithiumCTilde();
    byte b1;
    for (b1 = 0; b1 < paramMLDSAEngine.getDilithiumL(); b1++) {
      paramPolyVecL.getVectorIndex(b1).zPack(paramArrayOfbyte, i);
      i += paramMLDSAEngine.getDilithiumPolyZPackedBytes();
    } 
    for (b1 = 0; b1 < paramMLDSAEngine.getDilithiumOmega() + paramMLDSAEngine.getDilithiumK(); b1++)
      paramArrayOfbyte[i + b1] = 0; 
    b1 = 0;
    for (byte b2 = 0; b2 < paramMLDSAEngine.getDilithiumK(); b2++) {
      for (byte b = 0; b < 'Ā'; b++) {
        if (paramPolyVecK.getVectorIndex(b2).getCoeffIndex(b) != 0)
          paramArrayOfbyte[i + b1++] = (byte)b; 
      } 
      paramArrayOfbyte[i + paramMLDSAEngine.getDilithiumOmega() + b2] = (byte)b1;
    } 
  }
  
  static boolean unpackSignature(PolyVecL paramPolyVecL, PolyVecK paramPolyVecK, byte[] paramArrayOfbyte, MLDSAEngine paramMLDSAEngine) {
    int i = paramMLDSAEngine.getDilithiumCTilde();
    byte b;
    for (b = 0; b < paramMLDSAEngine.getDilithiumL(); b++)
      paramPolyVecL.getVectorIndex(b).zUnpack(Arrays.copyOfRange(paramArrayOfbyte, i + b * paramMLDSAEngine.getDilithiumPolyZPackedBytes(), i + (b + 1) * paramMLDSAEngine.getDilithiumPolyZPackedBytes())); 
    i += paramMLDSAEngine.getDilithiumL() * paramMLDSAEngine.getDilithiumPolyZPackedBytes();
    byte b2 = 0;
    for (b = 0; b < paramMLDSAEngine.getDilithiumK(); b++) {
      byte b3;
      for (b3 = 0; b3 < 256; b3++)
        paramPolyVecK.getVectorIndex(b).setCoeffIndex(b3, 0); 
      if ((paramArrayOfbyte[i + paramMLDSAEngine.getDilithiumOmega() + b] & 0xFF) < b2 || (paramArrayOfbyte[i + paramMLDSAEngine.getDilithiumOmega() + b] & 0xFF) > paramMLDSAEngine.getDilithiumOmega())
        return false; 
      for (b3 = b2; b3 < (paramArrayOfbyte[i + paramMLDSAEngine.getDilithiumOmega() + b] & 0xFF); b3++) {
        if (b3 > b2 && (paramArrayOfbyte[i + b3] & 0xFF) <= (paramArrayOfbyte[i + b3 - 1] & 0xFF))
          return false; 
        paramPolyVecK.getVectorIndex(b).setCoeffIndex(paramArrayOfbyte[i + b3] & 0xFF, 1);
      } 
      b2 = paramArrayOfbyte[i + paramMLDSAEngine.getDilithiumOmega() + b];
    } 
    for (byte b1 = b2; b1 < paramMLDSAEngine.getDilithiumOmega(); b1++) {
      if ((paramArrayOfbyte[i + b1] & 0xFF) != 0)
        return false; 
    } 
    return true;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mldsa\Packing.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */