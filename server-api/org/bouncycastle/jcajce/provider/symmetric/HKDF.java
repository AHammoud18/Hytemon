package org.bouncycastle.jcajce.provider.symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jcajce.spec.HKDFParameterSpec;

public class HKDF {
  public static class HKDFBase extends BaseSecretKeyFactory {
    protected String algName;
    
    protected HKDFBytesGenerator hkdf;
    
    public HKDFBase(String param1String, Digest param1Digest, ASN1ObjectIdentifier param1ASN1ObjectIdentifier) {
      super(param1String, param1ASN1ObjectIdentifier);
      this.algName = param1String;
      this.hkdf = new HKDFBytesGenerator(param1Digest);
    }
    
    protected SecretKey engineGenerateSecret(KeySpec param1KeySpec) throws InvalidKeySpecException {
      if (!(param1KeySpec instanceof HKDFParameterSpec))
        throw new InvalidKeySpecException("invalid KeySpec: expected HKDFParameterSpec, but got " + param1KeySpec.getClass().getName()); 
      HKDFParameterSpec hKDFParameterSpec = (HKDFParameterSpec)param1KeySpec;
      int i = hKDFParameterSpec.getOutputLength();
      this.hkdf.init((DerivationParameters)new HKDFParameters(hKDFParameterSpec.getIKM(), hKDFParameterSpec.getSalt(), hKDFParameterSpec.getInfo()));
      byte[] arrayOfByte = new byte[i];
      this.hkdf.generateBytes(arrayOfByte, 0, i);
      KeyParameter keyParameter = new KeyParameter(arrayOfByte);
      return (SecretKey)new BCPBEKey(this.algName, (CipherParameters)keyParameter);
    }
  }
  
  public static class HKDFwithSHA256 extends HKDFBase {
    public HKDFwithSHA256() throws InvalidAlgorithmParameterException {
      super("HKDF-SHA256", (Digest)new SHA256Digest(), PKCSObjectIdentifiers.id_alg_hkdf_with_sha256);
    }
  }
  
  public static class HKDFwithSHA384 extends HKDFBase {
    public HKDFwithSHA384() throws InvalidAlgorithmParameterException {
      super("HKDF-SHA384", (Digest)new SHA384Digest(), PKCSObjectIdentifiers.id_alg_hkdf_with_sha384);
    }
  }
  
  public static class HKDFwithSHA512 extends HKDFBase {
    public HKDFwithSHA512() throws InvalidAlgorithmParameterException {
      super("HKDF-SHA512", (Digest)new SHA512Digest(), PKCSObjectIdentifiers.id_alg_hkdf_with_sha512);
    }
  }
  
  public static class Mappings extends AlgorithmProvider {
    private static final String PREFIX = HKDF.class.getName();
    
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("SecretKeyFactory.HKDF-SHA256", PREFIX + "$HKDFwithSHA256");
      param1ConfigurableProvider.addAlgorithm("SecretKeyFactory.HKDF-SHA384", PREFIX + "$HKDFwithSHA384");
      param1ConfigurableProvider.addAlgorithm("SecretKeyFactory.HKDF-SHA512", PREFIX + "$HKDFwithSHA512");
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\symmetric\HKDF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */