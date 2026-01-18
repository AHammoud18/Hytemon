package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf.pbepbkdf2;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KDFParameters;
import javax.crypto.KDFSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.PasswordConverter;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

class PBEPBKDF2Spi extends KDFSpi {
  final PasswordConverter pwdConverter;
  
  final PKCS5S2ParametersGenerator generator;
  
  protected PBEPBKDF2Spi(KDFParameters paramKDFParameters) throws InvalidAlgorithmParameterException {
    this(paramKDFParameters, (Digest)new SHA1Digest(), PasswordConverter.UTF8);
  }
  
  protected PBEPBKDF2Spi(KDFParameters paramKDFParameters, Digest paramDigest) throws InvalidAlgorithmParameterException {
    this(paramKDFParameters, paramDigest, PasswordConverter.UTF8);
  }
  
  protected PBEPBKDF2Spi(KDFParameters paramKDFParameters, Digest paramDigest, PasswordConverter paramPasswordConverter) throws InvalidAlgorithmParameterException {
    super(requireNull(paramKDFParameters, "PBEPBKDF2 does not support parameters"));
    this.pwdConverter = paramPasswordConverter;
    this.generator = new PKCS5S2ParametersGenerator(paramDigest);
  }
  
  protected KDFParameters engineGetParameters() {
    return null;
  }
  
  protected SecretKey engineDeriveKey(String paramString, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
    byte[] arrayOfByte = engineDeriveData(paramAlgorithmParameterSpec);
    return new SecretKeySpec(arrayOfByte, paramString);
  }
  
  protected byte[] engineDeriveData(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    if (!(paramAlgorithmParameterSpec instanceof PBEKeySpec))
      throw new InvalidAlgorithmParameterException("Invalid AlgorithmParameterSpec provided"); 
    PBEKeySpec pBEKeySpec = (PBEKeySpec)paramAlgorithmParameterSpec;
    char[] arrayOfChar = pBEKeySpec.getPassword();
    byte[] arrayOfByte1 = pBEKeySpec.getSalt();
    int i = pBEKeySpec.getIterationCount();
    int j = pBEKeySpec.getKeyLength();
    if (arrayOfChar == null || arrayOfByte1 == null)
      throw new InvalidAlgorithmParameterException("Password and salt cannot be null"); 
    this.generator.init(this.pwdConverter.convert(arrayOfChar), arrayOfByte1, i);
    KeyParameter keyParameter = (KeyParameter)this.generator.generateDerivedParameters(j);
    byte[] arrayOfByte2 = keyParameter.getKey();
    Arrays.fill(arrayOfChar, false);
    return arrayOfByte2;
  }
  
  private static KDFParameters requireNull(KDFParameters paramKDFParameters, String paramString) throws InvalidAlgorithmParameterException {
    if (paramKDFParameters != null)
      throw new InvalidAlgorithmParameterException(paramString); 
    return null;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\pbepbkdf2\PBEPBKDF2Spi.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */