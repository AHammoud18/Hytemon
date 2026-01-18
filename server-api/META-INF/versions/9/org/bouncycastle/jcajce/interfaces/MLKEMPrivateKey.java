package META-INF.versions.9.org.bouncycastle.jcajce.interfaces;

import java.security.PrivateKey;
import org.bouncycastle.jcajce.interfaces.MLKEMKey;
import org.bouncycastle.jcajce.interfaces.MLKEMPublicKey;

public interface MLKEMPrivateKey extends PrivateKey, MLKEMKey {
  MLKEMPublicKey getPublicKey();
  
  byte[] getPrivateData();
  
  byte[] getSeed();
  
  org.bouncycastle.jcajce.interfaces.MLKEMPrivateKey getPrivateKey(boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajce\interfaces\MLKEMPrivateKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */