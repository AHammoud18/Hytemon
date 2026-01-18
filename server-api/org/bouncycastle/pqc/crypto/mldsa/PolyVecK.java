package org.bouncycastle.pqc.crypto.mldsa;

class PolyVecK {
  private final Poly[] vec;
  
  PolyVecK(MLDSAEngine paramMLDSAEngine) {
    int i = paramMLDSAEngine.getDilithiumK();
    this.vec = new Poly[i];
    for (byte b = 0; b < i; b++)
      this.vec[b] = new Poly(paramMLDSAEngine); 
  }
  
  Poly getVectorIndex(int paramInt) {
    return this.vec[paramInt];
  }
  
  void setVectorIndex(int paramInt, Poly paramPoly) {
    this.vec[paramInt] = paramPoly;
  }
  
  public void uniformEta(byte[] paramArrayOfbyte, short paramShort) {
    short s = paramShort;
    for (byte b = 0; b < this.vec.length; b++) {
      s = (short)(s + 1);
      this.vec[b].uniformEta(paramArrayOfbyte, s);
    } 
  }
  
  public void reduce() {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).reduce(); 
  }
  
  public void invNttToMont() {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).invNttToMont(); 
  }
  
  public void addPolyVecK(PolyVecK paramPolyVecK) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).addPoly(paramPolyVecK.getVectorIndex(b)); 
  }
  
  public void conditionalAddQ() {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).conditionalAddQ(); 
  }
  
  public void power2Round(PolyVecK paramPolyVecK) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).power2Round(paramPolyVecK.getVectorIndex(b)); 
  }
  
  public void polyVecNtt() {
    for (byte b = 0; b < this.vec.length; b++)
      this.vec[b].polyNtt(); 
  }
  
  public void decompose(PolyVecK paramPolyVecK) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).decompose(paramPolyVecK.getVectorIndex(b)); 
  }
  
  public void packW1(MLDSAEngine paramMLDSAEngine, byte[] paramArrayOfbyte, int paramInt) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).packW1(paramArrayOfbyte, paramInt + b * paramMLDSAEngine.getDilithiumPolyW1PackedBytes()); 
  }
  
  public void pointwisePolyMontgomery(Poly paramPoly, PolyVecK paramPolyVecK) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).pointwiseMontgomery(paramPoly, paramPolyVecK.getVectorIndex(b)); 
  }
  
  public void subtract(PolyVecK paramPolyVecK) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).subtract(paramPolyVecK.getVectorIndex(b)); 
  }
  
  public boolean checkNorm(int paramInt) {
    for (byte b = 0; b < this.vec.length; b++) {
      if (getVectorIndex(b).checkNorm(paramInt))
        return true; 
    } 
    return false;
  }
  
  public int makeHint(PolyVecK paramPolyVecK1, PolyVecK paramPolyVecK2) {
    int i = 0;
    for (byte b = 0; b < this.vec.length; b++)
      i += getVectorIndex(b).polyMakeHint(paramPolyVecK1.getVectorIndex(b), paramPolyVecK2.getVectorIndex(b)); 
    return i;
  }
  
  public void useHint(PolyVecK paramPolyVecK1, PolyVecK paramPolyVecK2) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).polyUseHint(paramPolyVecK1.getVectorIndex(b), paramPolyVecK2.getVectorIndex(b)); 
  }
  
  public void shiftLeft() {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).shiftLeft(); 
  }
  
  public String toString() {
    null = "[";
    for (byte b = 0; b < this.vec.length; b++) {
      null = null + b + " " + getVectorIndex(b).toString();
      if (b != this.vec.length - 1)
        null = null + ",\n"; 
    } 
    return null + "]";
  }
  
  public String toString(String paramString) {
    return paramString + ": " + toString();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\PolyVecK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */