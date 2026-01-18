package META-INF.versions.9.org.bouncycastle.pqc.crypto.slhdsa;

import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAEngine;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAEngineProvider;

public class SLHDSAParameters {
  public static final int TYPE_PURE = 0;
  
  public static final int TYPE_SHA2_256 = 1;
  
  public static final int TYPE_SHA2_512 = 2;
  
  public static final int TYPE_SHAKE128 = 3;
  
  public static final int TYPE_SHAKE256 = 4;
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_128f = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-128f", (SLHDSAEngineProvider)new Sha2EngineProvider(16, 16, 22, 6, 33, 66), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_128s = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-128s", (SLHDSAEngineProvider)new Sha2EngineProvider(16, 16, 7, 12, 14, 63), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_192f = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-192f", (SLHDSAEngineProvider)new Sha2EngineProvider(24, 16, 22, 8, 33, 66), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_192s = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-192s", (SLHDSAEngineProvider)new Sha2EngineProvider(24, 16, 7, 14, 17, 63), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_256f = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-256f", (SLHDSAEngineProvider)new Sha2EngineProvider(32, 16, 17, 9, 35, 68), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_256s = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-256s", (SLHDSAEngineProvider)new Sha2EngineProvider(32, 16, 8, 14, 22, 64), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_128f = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-128f", (SLHDSAEngineProvider)new Shake256EngineProvider(16, 16, 22, 6, 33, 66), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_128s = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-128s", (SLHDSAEngineProvider)new Shake256EngineProvider(16, 16, 7, 12, 14, 63), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_192f = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-192f", (SLHDSAEngineProvider)new Shake256EngineProvider(24, 16, 22, 8, 33, 66), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_192s = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-192s", (SLHDSAEngineProvider)new Shake256EngineProvider(24, 16, 7, 14, 17, 63), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_256f = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-256f", (SLHDSAEngineProvider)new Shake256EngineProvider(32, 16, 17, 9, 35, 68), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_256s = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-256s", (SLHDSAEngineProvider)new Shake256EngineProvider(32, 16, 8, 14, 22, 64), 0);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_128f_with_sha256 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-128f-with-sha256", (SLHDSAEngineProvider)new Sha2EngineProvider(16, 16, 22, 6, 33, 66), 1);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_128s_with_sha256 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-128s-with-sha256", (SLHDSAEngineProvider)new Sha2EngineProvider(16, 16, 7, 12, 14, 63), 1);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_192f_with_sha512 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-192f-with-sha512", (SLHDSAEngineProvider)new Sha2EngineProvider(24, 16, 22, 8, 33, 66), 2);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_192s_with_sha512 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-192s-with-sha512", (SLHDSAEngineProvider)new Sha2EngineProvider(24, 16, 7, 14, 17, 63), 2);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_256f_with_sha512 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-256f-with-sha512", (SLHDSAEngineProvider)new Sha2EngineProvider(32, 16, 17, 9, 35, 68), 2);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters sha2_256s_with_sha512 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("sha2-256s-with-sha512", (SLHDSAEngineProvider)new Sha2EngineProvider(32, 16, 8, 14, 22, 64), 2);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_128f_with_shake128 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-128f-with-shake128", (SLHDSAEngineProvider)new Shake256EngineProvider(16, 16, 22, 6, 33, 66), 3);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_128s_with_shake128 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-128s-with-shake128", (SLHDSAEngineProvider)new Shake256EngineProvider(16, 16, 7, 12, 14, 63), 3);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_192f_with_shake256 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-192f-with-shake256", (SLHDSAEngineProvider)new Shake256EngineProvider(24, 16, 22, 8, 33, 66), 4);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_192s_with_shake256 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-192s-with-shake256", (SLHDSAEngineProvider)new Shake256EngineProvider(24, 16, 7, 14, 17, 63), 4);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_256f_with_shake256 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-256f-with-shake256", (SLHDSAEngineProvider)new Shake256EngineProvider(32, 16, 17, 9, 35, 68), 4);
  
  public static final org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters shake_256s_with_shake256 = new org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters("shake-256s-with-shake256", (SLHDSAEngineProvider)new Shake256EngineProvider(32, 16, 8, 14, 22, 64), 4);
  
  private final String name;
  
  private final SLHDSAEngineProvider engineProvider;
  
  private final int preHashDigest;
  
  private SLHDSAParameters(String paramString, SLHDSAEngineProvider paramSLHDSAEngineProvider, int paramInt) {
    this.name = paramString;
    this.engineProvider = paramSLHDSAEngineProvider;
    this.preHashDigest = paramInt;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getType() {
    return this.preHashDigest;
  }
  
  public int getN() {
    return this.engineProvider.getN();
  }
  
  SLHDSAEngine getEngine() {
    return this.engineProvider.get();
  }
  
  public boolean isPreHash() {
    return (this.preHashDigest != 0);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */