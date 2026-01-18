package org.bouncycastle.jcajce.provider.asymmetric;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeIndex;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class CompositeSignatures {
  private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.";
  
  private static final Map<String, String> compositesAttributes = new HashMap<>();
  
  static {
    compositesAttributes.put("SupportedKeyClasses", "org.bouncycastle.jcajce.CompositePublicKey|org.bouncycastle.jcajce.CompositePrivateKey");
    compositesAttributes.put("SupportedKeyFormats", "PKCS#8|X.509");
  }
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("Signature.COMPOSITE", "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.SignatureSpi$COMPOSITE");
      for (ASN1ObjectIdentifier aSN1ObjectIdentifier : CompositeIndex.getSupportedIdentifiers()) {
        String str1 = CompositeIndex.getAlgorithmName(aSN1ObjectIdentifier);
        String str2 = str1.replace('-', '_');
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory", aSN1ObjectIdentifier, "COMPOSITE");
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory." + str1, "COMPOSITE");
        param1ConfigurableProvider.addAlgorithm("KeyPairGenerator." + str1, "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyPairGeneratorSpi$" + str2);
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator", aSN1ObjectIdentifier, str1);
        param1ConfigurableProvider.addAlgorithm("Signature." + str1, "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.SignatureSpi$" + str2);
        param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature", aSN1ObjectIdentifier, str1);
        param1ConfigurableProvider.addAlgorithm("Signature." + str1 + "-PREHASH", "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.SignatureSpi$" + str2 + "_PREHASH");
        param1ConfigurableProvider.addKeyInfoConverter(aSN1ObjectIdentifier, (AsymmetricKeyInfoConverter)new KeyFactorySpi());
      } 
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\CompositeSignatures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */