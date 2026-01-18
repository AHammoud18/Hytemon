package org.bouncycastle.pqc.crypto.mldsa;

class PolyVecMatrix {
  private final PolyVecL[] matrix;
  
  PolyVecMatrix(MLDSAEngine paramMLDSAEngine) {
    int i = paramMLDSAEngine.getDilithiumK();
    this.matrix = new PolyVecL[i];
    for (byte b = 0; b < i; b++)
      this.matrix[b] = new PolyVecL(paramMLDSAEngine); 
  }
  
  public void pointwiseMontgomery(PolyVecK paramPolyVecK, PolyVecL paramPolyVecL) {
    for (byte b = 0; b < this.matrix.length; b++)
      paramPolyVecK.getVectorIndex(b).pointwiseAccountMontgomery(this.matrix[b], paramPolyVecL); 
  }
  
  public void expandMatrix(byte[] paramArrayOfbyte) {
    for (byte b = 0; b < this.matrix.length; b++)
      this.matrix[b].uniformBlocks(paramArrayOfbyte, b << 8); 
  }
  
  private String addString() {
    null = "[";
    for (byte b = 0; b < this.matrix.length; b++) {
      null = null + "Outer Matrix " + b + " [";
      null = null + this.matrix[b].toString();
      if (b == this.matrix.length - 1) {
        null = null + "]\n";
      } else {
        null = null + "],\n";
      } 
    } 
    return null + "]\n";
  }
  
  public String toString(String paramString) {
    return paramString.concat(": \n" + addString());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\PolyVecMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */