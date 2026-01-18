package org.bouncycastle.pqc.crypto.mldsa;

class Reduce {
  static int montgomeryReduce(long paramLong) {
    null = (int)(paramLong * 58728449L);
    return (int)(paramLong - null * 8380417L >>> 32L);
  }
  
  static int reduce32(int paramInt) {
    null = paramInt + 4194304 >> 23;
    return paramInt - null * 8380417;
  }
  
  static int conditionalAddQ(int paramInt) {
    paramInt += paramInt >> 31 & 0x7FE001;
    return paramInt;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\Reduce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */