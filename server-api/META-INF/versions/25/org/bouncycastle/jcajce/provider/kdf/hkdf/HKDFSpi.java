package META-INF.versions.25.org.bouncycastle.jcajce.provider.kdf.hkdf;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.List;
import javax.crypto.KDFParameters;
import javax.crypto.KDFSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.jcajce.spec.HKDFParameterSpec;

class HKDFSpi extends KDFSpi {
  protected HKDFBytesGenerator hkdf;
  
  public HKDFSpi(KDFParameters paramKDFParameters, Digest paramDigest) throws InvalidAlgorithmParameterException {
    super(requireNull(paramKDFParameters, "HKDF does not support parameters"));
    this.hkdf = new HKDFBytesGenerator(paramDigest);
  }
  
  protected KDFParameters engineGetParameters() {
    return null;
  }
  
  protected SecretKey engineDeriveKey(String paramString, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
    byte[] arrayOfByte = engineDeriveData(paramAlgorithmParameterSpec);
    return new SecretKeySpec(arrayOfByte, paramString);
  }
  
  protected byte[] engineDeriveData(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramAlgorithmParameterSpec == null || (!(paramAlgorithmParameterSpec instanceof HKDFParameterSpec) && !(paramAlgorithmParameterSpec instanceof HKDFParameterSpec)))
      throw new InvalidAlgorithmParameterException("Invalid AlgorithmParameterSpec provided"); 
    HKDFParameters hKDFParameters = null;
    int i = 0;
    if (paramAlgorithmParameterSpec instanceof HKDFParameterSpec.ExtractThenExpand) {
      HKDFParameterSpec.ExtractThenExpand extractThenExpand = (HKDFParameterSpec.ExtractThenExpand)paramAlgorithmParameterSpec;
      List<SecretKey> list1 = extractThenExpand.ikms();
      List<SecretKey> list2 = extractThenExpand.salts();
      hKDFParameters = new HKDFParameters(((SecretKey)list1.get(0)).getEncoded(), ((SecretKey)list2.get(0)).getEncoded(), extractThenExpand.info());
      i = extractThenExpand.length();
      this.hkdf.init((DerivationParameters)hKDFParameters);
      byte[] arrayOfByte = new byte[i];
      this.hkdf.generateBytes(arrayOfByte, 0, i);
      return arrayOfByte;
    } 
    if (paramAlgorithmParameterSpec instanceof HKDFParameterSpec.Extract) {
      HKDFParameterSpec.Extract extract = (HKDFParameterSpec.Extract)paramAlgorithmParameterSpec;
      List<SecretKey> list1 = extract.ikms();
      List<SecretKey> list2 = extract.salts();
      return this.hkdf.extractPRK(((SecretKey)list2.get(0)).getEncoded(), ((SecretKey)list1.get(0)).getEncoded());
    } 
    if (paramAlgorithmParameterSpec instanceof HKDFParameterSpec) {
      HKDFParameterSpec hKDFParameterSpec = (HKDFParameterSpec)paramAlgorithmParameterSpec;
      hKDFParameters = new HKDFParameters(hKDFParameterSpec.getIKM(), hKDFParameterSpec.getSalt(), hKDFParameterSpec.getInfo());
      i = hKDFParameterSpec.getOutputLength();
      this.hkdf.init((DerivationParameters)hKDFParameters);
      byte[] arrayOfByte = new byte[i];
      this.hkdf.generateBytes(arrayOfByte, 0, i);
      return arrayOfByte;
    } 
    throw new InvalidAlgorithmParameterException("invalid HKDFParameterSpec provided");
  }
  
  private static KDFParameters requireNull(KDFParameters paramKDFParameters, String paramString) throws InvalidAlgorithmParameterException {
    if (paramKDFParameters != null)
      throw new InvalidAlgorithmParameterException(paramString); 
    return null;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\25\org\bouncycastle\jcajce\provider\kdf\hkdf\HKDFSpi.class
 * Java compiler version: 25 (69.0)
 * JD-Core Version:       1.1.3
 */