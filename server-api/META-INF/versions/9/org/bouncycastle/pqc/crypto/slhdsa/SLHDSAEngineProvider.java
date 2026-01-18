package META-INF.versions.9.org.bouncycastle.pqc.crypto.slhdsa;

import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAEngine;

interface SLHDSAEngineProvider {
  int getN();
  
  SLHDSAEngine get();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAEngineProvider.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */