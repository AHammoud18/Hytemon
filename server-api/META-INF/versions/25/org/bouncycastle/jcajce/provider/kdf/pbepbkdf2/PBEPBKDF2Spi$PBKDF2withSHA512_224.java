package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf.pbepbkdf2;

import java.security.InvalidAlgorithmParameterException;
import javax.crypto.KDFParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA512tDigest;
import org.bouncycastle.jcajce.provider.kdf.pbepbkdf2.PBEPBKDF2Spi;

public class PBKDF2withSHA512_224 extends PBEPBKDF2Spi {
  public PBKDF2withSHA512_224(KDFParameters paramKDFParameters) throws InvalidAlgorithmParameterException {
    super(paramKDFParameters, (Digest)new SHA512tDigest(224));
  }
  
  public PBKDF2withSHA512_224() throws InvalidAlgorithmParameterException {
    this(null);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\pbepbkdf2\PBEPBKDF2Spi$PBKDF2withSHA512_224.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */