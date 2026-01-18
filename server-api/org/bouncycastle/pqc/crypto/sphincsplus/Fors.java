package org.bouncycastle.pqc.crypto.sphincsplus;

import java.util.LinkedList;
import org.bouncycastle.util.Arrays;

class Fors {
  SPHINCSPlusEngine engine;
  
  public Fors(SPHINCSPlusEngine paramSPHINCSPlusEngine) {
    this.engine = paramSPHINCSPlusEngine;
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
    int[] arrayOfInt = message_to_idxs(paramArrayOfbyte1, this.engine.K, this.engine.A);
    SIG_FORS[] arrayOfSIG_FORS = new SIG_FORS[this.engine.K];
    int i = this.engine.T;
    for (byte b = 0; b < this.engine.K; b++) {
      int j = arrayOfInt[b];
      aDRS.setTypeAndClear(6);
      aDRS.setKeyPairAddress(paramADRS.getKeyPairAddress());
      aDRS.setTreeHeight(0);
      aDRS.setTreeIndex(b * i + j);
      byte[] arrayOfByte = this.engine.PRF(paramArrayOfbyte3, paramArrayOfbyte2, aDRS);
      aDRS.changeType(3);
      byte[][] arrayOfByte1 = new byte[this.engine.A][];
      for (byte b1 = 0; b1 < this.engine.A; b1++) {
        int k = j / (1 << b1) ^ 0x1;
        arrayOfByte1[b1] = treehash(paramArrayOfbyte2, b * i + k * (1 << b1), b1, paramArrayOfbyte3, aDRS);
      } 
      arrayOfSIG_FORS[b] = new SIG_FORS(arrayOfByte, arrayOfByte1);
    } 
    return arrayOfSIG_FORS;
  }
  
  public byte[] pkFromSig(SIG_FORS[] paramArrayOfSIG_FORS, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, ADRS paramADRS) {
    byte[][] arrayOfByte1 = new byte[2][];
    byte[][] arrayOfByte2 = new byte[this.engine.K][];
    int i = this.engine.T;
    int[] arrayOfInt = message_to_idxs(paramArrayOfbyte1, this.engine.K, this.engine.A);
    for (byte b = 0; b < this.engine.K; b++) {
      int j = arrayOfInt[b];
      byte[] arrayOfByte = paramArrayOfSIG_FORS[b].getSK();
      paramADRS.setTreeHeight(0);
      paramADRS.setTreeIndex(b * i + j);
      arrayOfByte1[0] = this.engine.F(paramArrayOfbyte2, paramADRS, arrayOfByte);
      byte[][] arrayOfByte3 = paramArrayOfSIG_FORS[b].getAuthPath();
      paramADRS.setTreeIndex(b * i + j);
      for (byte b1 = 0; b1 < this.engine.A; b1++) {
        paramADRS.setTreeHeight(b1 + 1);
        if (j / (1 << b1) % 2 == 0) {
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
  
  static int[] message_to_idxs(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte b1 = 0;
    int[] arrayOfInt = new int[paramInt1];
    for (byte b2 = 0; b2 < paramInt1; b2++) {
      arrayOfInt[b2] = 0;
      for (byte b = 0; b < paramInt2; b++) {
        arrayOfInt[b2] = arrayOfInt[b2] ^ (paramArrayOfbyte[b1 >> 3] >> (b1 & 0x7) & 0x1) << b;
        b1++;
      } 
    } 
    return arrayOfInt;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\sphincsplus\Fors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */