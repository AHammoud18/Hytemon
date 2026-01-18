package META-INF.versions.9.org.bouncycastle.pqc.crypto.mlkem;

import org.bouncycastle.pqc.crypto.KEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMEngine;

public class MLKEMParameters implements KEMParameters {
  public static final org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters ml_kem_512 = new org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters("ML-KEM-512", 2, 256);
  
  public static final org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters ml_kem_768 = new org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters("ML-KEM-768", 3, 256);
  
  public static final org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters ml_kem_1024 = new org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters("ML-KEM-1024", 4, 256);
  
  private final String name;
  
  private final int k;
  
  private final int sessionKeySize;
  
  private MLKEMParameters(String paramString, int paramInt1, int paramInt2) {
    this.name = paramString;
    this.k = paramInt1;
    this.sessionKeySize = paramInt2;
  }
  
  public String getName() {
    return this.name;
  }
  
  public MLKEMEngine getEngine() {
    return new MLKEMEngine(this.k);
  }
  
  public int getSessionKeySize() {
    return this.sessionKeySize;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mlkem\MLKEMParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */