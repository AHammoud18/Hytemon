package META-INF.versions.9.org.bouncycastle.pqc.crypto.mldsa;

import java.security.SecureRandom;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAEngine;

public class MLDSAParameters {
  public static final int TYPE_PURE = 0;
  
  public static final int TYPE_SHA2_512 = 1;
  
  public static final org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters ml_dsa_44 = new org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters("ml-dsa-44", 2, 0);
  
  public static final org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters ml_dsa_65 = new org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters("ml-dsa-65", 3, 0);
  
  public static final org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters ml_dsa_87 = new org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters("ml-dsa-87", 5, 0);
  
  public static final org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters ml_dsa_44_with_sha512 = new org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters("ml-dsa-44-with-sha512", 2, 1);
  
  public static final org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters ml_dsa_65_with_sha512 = new org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters("ml-dsa-65-with-sha512", 3, 1);
  
  public static final org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters ml_dsa_87_with_sha512 = new org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters("ml-dsa-87-with-sha512", 5, 1);
  
  private final int k;
  
  private final String name;
  
  private final int preHashDigest;
  
  private MLDSAParameters(String paramString, int paramInt1, int paramInt2) {
    this.name = paramString;
    this.k = paramInt1;
    this.preHashDigest = paramInt2;
  }
  
  public boolean isPreHash() {
    return (this.preHashDigest != 0);
  }
  
  public int getType() {
    return this.preHashDigest;
  }
  
  MLDSAEngine getEngine(SecureRandom paramSecureRandom) {
    return new MLDSAEngine(this.k, paramSecureRandom);
  }
  
  public String getName() {
    return this.name;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mldsa\MLDSAParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */