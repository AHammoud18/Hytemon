package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf.hkdf;

import java.security.InvalidAlgorithmParameterException;
import javax.crypto.KDFParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.jcajce.provider.kdf.hkdf.HKDFSpi;

public class HKDFwithSHA384 extends HKDFSpi {
  public HKDFwithSHA384(KDFParameters paramKDFParameters) throws InvalidAlgorithmParameterException {
    super(paramKDFParameters, (Digest)new SHA384Digest());
  }
  
  public HKDFwithSHA384() throws InvalidAlgorithmParameterException {
    this(null);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\hkdf\HKDFSpi$HKDFwithSHA384.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */