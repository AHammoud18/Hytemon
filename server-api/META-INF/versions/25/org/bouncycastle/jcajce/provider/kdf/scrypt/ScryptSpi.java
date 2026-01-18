package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf.scrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KDFParameters;
import javax.crypto.KDFSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.PasswordConverter;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import org.bouncycastle.util.Arrays;

class ScryptSpi extends KDFSpi {
  protected ScryptSpi(KDFParameters paramKDFParameters) throws InvalidAlgorithmParameterException {
    super(requireNull(paramKDFParameters, "Scrypt does not support parameters"));
  }
  
  protected KDFParameters engineGetParameters() {
    return null;
  }
  
  protected SecretKey engineDeriveKey(String paramString, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
    byte[] arrayOfByte = engineDeriveData(paramAlgorithmParameterSpec);
    return new SecretKeySpec(arrayOfByte, paramString);
  }
  
  protected byte[] engineDeriveData(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    if (!(paramAlgorithmParameterSpec instanceof org.bouncycastle.jcajce.spec.ScryptParameterSpec))
      throw new InvalidAlgorithmParameterException("SCrypt requires an SCryptParameterSpec as derivation parameters"); 
    ScryptKeySpec scryptKeySpec = (ScryptKeySpec)paramAlgorithmParameterSpec;
    char[] arrayOfChar = scryptKeySpec.getPassword();
    byte[] arrayOfByte1 = scryptKeySpec.getSalt();
    int i = scryptKeySpec.getCostParameter();
    int j = scryptKeySpec.getBlockSize();
    int k = scryptKeySpec.getParallelizationParameter();
    int m = scryptKeySpec.getKeyLength();
    if (arrayOfByte1 == null)
      throw new InvalidAlgorithmParameterException("Salt S must be provided."); 
    if (i <= 1)
      throw new InvalidAlgorithmParameterException("Cost parameter N must be > 1."); 
    if (m <= 0)
      throw new InvalidAlgorithmParameterException("positive key length required: " + m); 
    byte[] arrayOfByte2 = SCrypt.generate(PasswordConverter.UTF8.convert(arrayOfChar), arrayOfByte1, i, j, k, m / 8);
    Arrays.clear(arrayOfChar);
    return arrayOfByte2;
  }
  
  private static KDFParameters requireNull(KDFParameters paramKDFParameters, String paramString) throws InvalidAlgorithmParameterException {
    if (paramKDFParameters != null)
      throw new InvalidAlgorithmParameterException(paramString); 
    return null;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\scrypt\ScryptSpi.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */