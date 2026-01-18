package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf.scrypt;

import java.security.InvalidAlgorithmParameterException;
import javax.crypto.KDFParameters;
import org.bouncycastle.jcajce.provider.kdf.scrypt.ScryptSpi;

public class ScryptWithUTF8 extends ScryptSpi {
  public ScryptWithUTF8(KDFParameters paramKDFParameters) throws InvalidAlgorithmParameterException {
    super(paramKDFParameters);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\scrypt\ScryptSpi$ScryptWithUTF8.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */