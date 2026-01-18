package META-INF.versions.9.org.bouncycastle.pqc.crypto.mldsa;

import org.bouncycastle.pqc.crypto.mldsa.MLDSAEngine;
import org.bouncycastle.pqc.crypto.mldsa.Poly;

class PolyVecL {
  private final Poly[] vec;
  
  PolyVecL(MLDSAEngine paramMLDSAEngine) {
    int i = paramMLDSAEngine.getDilithiumL();
    this.vec = new Poly[i];
    for (byte b = 0; b < i; b++)
      this.vec[b] = new Poly(paramMLDSAEngine); 
  }
  
  public PolyVecL() throws Exception {
    throw new Exception("Requires Parameter");
  }
  
  public Poly getVectorIndex(int paramInt) {
    return this.vec[paramInt];
  }
  
  void uniformBlocks(byte[] paramArrayOfbyte, int paramInt) {
    for (byte b = 0; b < this.vec.length; b++)
      this.vec[b].uniformBlocks(paramArrayOfbyte, (short)(paramInt + b)); 
  }
  
  public void uniformEta(byte[] paramArrayOfbyte, short paramShort) {
    short s = paramShort;
    for (byte b = 0; b < this.vec.length; b++) {
      s = (short)(s + 1);
      getVectorIndex(b).uniformEta(paramArrayOfbyte, s);
    } 
  }
  
  void copyTo(org.bouncycastle.pqc.crypto.mldsa.PolyVecL paramPolyVecL) {
    for (byte b = 0; b < this.vec.length; b++)
      this.vec[b].copyTo(paramPolyVecL.vec[b]); 
  }
  
  public void polyVecNtt() {
    for (byte b = 0; b < this.vec.length; b++)
      this.vec[b].polyNtt(); 
  }
  
  public void uniformGamma1(byte[] paramArrayOfbyte, short paramShort) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).uniformGamma1(paramArrayOfbyte, (short)(this.vec.length * paramShort + b)); 
  }
  
  public void pointwisePolyMontgomery(Poly paramPoly, org.bouncycastle.pqc.crypto.mldsa.PolyVecL paramPolyVecL) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).pointwiseMontgomery(paramPoly, paramPolyVecL.getVectorIndex(b)); 
  }
  
  public void invNttToMont() {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).invNttToMont(); 
  }
  
  public void addPolyVecL(org.bouncycastle.pqc.crypto.mldsa.PolyVecL paramPolyVecL) {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).addPoly(paramPolyVecL.getVectorIndex(b)); 
  }
  
  public void reduce() {
    for (byte b = 0; b < this.vec.length; b++)
      getVectorIndex(b).reduce(); 
  }
  
  public boolean checkNorm(int paramInt) {
    for (byte b = 0; b < this.vec.length; b++) {
      if (getVectorIndex(b).checkNorm(paramInt))
        return true; 
    } 
    return false;
  }
  
  public String toString() {
    null = "\n[";
    for (byte b = 0; b < this.vec.length; b++) {
      null = null + "Inner Matrix " + null + " " + b;
      if (b != this.vec.length - 1)
        null = null + ",\n"; 
    } 
    return null + "]";
  }
  
  public String toString(String paramString) {
    return paramString + ": " + paramString;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mldsa\PolyVecL.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */