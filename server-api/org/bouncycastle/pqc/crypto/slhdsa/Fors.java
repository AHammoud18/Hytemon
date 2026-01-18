package org.bouncycastle.pqc.crypto.slhdsa;

import java.math.BigInteger;
import java.util.LinkedList;
import org.bouncycastle.util.Arrays;

class Fors {
  SLHDSAEngine engine;
  
  public Fors(SLHDSAEngine paramSLHDSAEngine) {
    this.engine = paramSLHDSAEngine;
  }
  
  byte[] treehash(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, ADRS paramADRS) {
    if (paramInt1 >>> paramInt2 << paramInt2 != paramInt1)
      return null; 
    LinkedList<NodeEntry> linkedList = new LinkedList();
    ADRS aDRS = new ADRS(paramADRS);
    for (byte b = 0; b < 1 << paramInt2; b++) {
      aDRS.setTypeAndClear(6);
      aDRS.setKeyPairAddress(paramADRS.getKeyPairAddress());
      aDRS.setTreeHeight(0);
      aDRS.setTreeIndex(paramInt1 + b);
      byte[] arrayOfByte1 = this.engine.PRF(paramArrayOfbyte2, paramArrayOfbyte1, aDRS);
      aDRS.changeType(3);
      byte[] arrayOfByte2 = this.engine.F(paramArrayOfbyte2, aDRS, arrayOfByte1);
      aDRS.setTreeHeight(1);
      byte b1 = 1;
      int i = paramInt1 + b;
      while (!linkedList.isEmpty() && ((NodeEntry)linkedList.get(0)).nodeHeight == b1) {
        i = (i - 1) / 2;
        aDRS.setTreeIndex(i);
        NodeEntry nodeEntry = linkedList.remove(0);
        arrayOfByte2 = this.engine.H(paramArrayOfbyte2, aDRS, nodeEntry.nodeValue, arrayOfByte2);
        aDRS.setTreeHeight(++b1);
      } 
      linkedList.add(0, new NodeEntry(arrayOfByte2, b1));
    } 
    return ((NodeEntry)linkedList.get(0)).nodeValue;
  }
  
  public SIG_FORS[] sign(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, ADRS paramADRS) {
    ADRS aDRS = new ADRS(paramADRS);
    int[] arrayOfInt = base2B(paramArrayOfbyte1, this.engine.A, this.engine.K);
    SIG_FORS[] arrayOfSIG_FORS = new SIG_FORS[this.engine.K];
    for (byte b = 0; b < this.engine.K; b++) {
      int i = arrayOfInt[b];
      aDRS.setTypeAndClear(6);
      aDRS.setKeyPairAddress(paramADRS.getKeyPairAddress());
      aDRS.setTreeHeight(0);
      aDRS.setTreeIndex((b << this.engine.A) + i);
      byte[] arrayOfByte = this.engine.PRF(paramArrayOfbyte3, paramArrayOfbyte2, aDRS);
      aDRS.changeType(3);
      byte[][] arrayOfByte1 = new byte[this.engine.A][];
      for (byte b1 = 0; b1 < this.engine.A; b1++) {
        int j = i >>> b1 ^ 0x1;
        arrayOfByte1[b1] = treehash(paramArrayOfbyte2, (b << this.engine.A) + (j << b1), b1, paramArrayOfbyte3, aDRS);
      } 
      arrayOfSIG_FORS[b] = new SIG_FORS(arrayOfByte, arrayOfByte1);
    } 
    return arrayOfSIG_FORS;
  }
  
  public byte[] pkFromSig(SIG_FORS[] paramArrayOfSIG_FORS, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, ADRS paramADRS) {
    byte[][] arrayOfByte1 = new byte[2][];
    byte[][] arrayOfByte2 = new byte[this.engine.K][];
    int[] arrayOfInt = base2B(paramArrayOfbyte1, this.engine.A, this.engine.K);
    for (byte b = 0; b < this.engine.K; b++) {
      int i = arrayOfInt[b];
      byte[] arrayOfByte = paramArrayOfSIG_FORS[b].getSK();
      paramADRS.setTreeHeight(0);
      paramADRS.setTreeIndex((b << this.engine.A) + i);
      arrayOfByte1[0] = this.engine.F(paramArrayOfbyte2, paramADRS, arrayOfByte);
      byte[][] arrayOfByte3 = paramArrayOfSIG_FORS[b].getAuthPath();
      paramADRS.setTreeIndex((b << this.engine.A) + i);
      for (byte b1 = 0; b1 < this.engine.A; b1++) {
        paramADRS.setTreeHeight(b1 + 1);
        if ((i & 1 << b1) == 0) {
          paramADRS.setTreeIndex(paramADRS.getTreeIndex() / 2);
          arrayOfByte1[1] = this.engine.H(paramArrayOfbyte2, paramADRS, arrayOfByte1[0], arrayOfByte3[b1]);
        } else {
          paramADRS.setTreeIndex((paramADRS.getTreeIndex() - 1) / 2);
          arrayOfByte1[1] = this.engine.H(paramArrayOfbyte2, paramADRS, arrayOfByte3[b1], arrayOfByte1[0]);
        } 
        arrayOfByte1[0] = arrayOfByte1[1];
      } 
      arrayOfByte2[b] = arrayOfByte1[0];
    } 
    ADRS aDRS = new ADRS(paramADRS);
    aDRS.setTypeAndClear(4);
    aDRS.setKeyPairAddress(paramADRS.getKeyPairAddress());
    return this.engine.T_l(paramArrayOfbyte2, aDRS, Arrays.concatenate(arrayOfByte2));
  }
  
  static int[] base2B(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int[] arrayOfInt = new int[paramInt2];
    byte b1 = 0;
    int i = 0;
    BigInteger bigInteger = BigInteger.ZERO;
    for (byte b2 = 0; b2 < paramInt2; b2++) {
      while (i < paramInt1) {
        bigInteger = bigInteger.shiftLeft(8).add(BigInteger.valueOf((paramArrayOfbyte[b1] & 0xFF)));
        b1++;
        i += 8;
      } 
      i -= paramInt1;
      arrayOfInt[b2] = bigInteger.shiftRight(i).mod(BigInteger.valueOf(2L).pow(paramInt1)).intValue();
    } 
    return arrayOfInt;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\Fors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */