package org.bouncycastle.pqc.jcajce.provider;

import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi;

public class HQC {
  private static final String PREFIX = "org.bouncycastle.pqc.jcajce.provider.hqc.";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("KeyFactory.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.HQC", "HQC");
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi$HQC128", BCObjectIdentifiers.hqc128, (AsymmetricKeyInfoConverter)new HQCKeyFactorySpi.HQC128());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi$HQC192", BCObjectIdentifiers.hqc192, (AsymmetricKeyInfoConverter)new HQCKeyFactorySpi.HQC192());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyFactorySpi$HQC256", BCObjectIdentifiers.hqc256, (AsymmetricKeyInfoConverter)new HQCKeyFactorySpi.HQC256());
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.HQC", "HQC");
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi$HQC128", BCObjectIdentifiers.hqc128);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi$HQC192", BCObjectIdentifiers.hqc192);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyPairGeneratorSpi$HQC256", BCObjectIdentifiers.hqc256);
      param1ConfigurableProvider.addAlgorithm("KeyGenerator.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi");
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi$HQC128", BCObjectIdentifiers.hqc128);
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi$HQC192", BCObjectIdentifiers.hqc192);
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCKeyGeneratorSpi$HQC256", BCObjectIdentifiers.hqc256);
      HQCKeyFactorySpi hQCKeyFactorySpi = new HQCKeyFactorySpi();
      param1ConfigurableProvider.addAlgorithm("Cipher.HQC", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$Base");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.HQC", "HQC");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher." + BCObjectIdentifiers.pqc_kem_hqc, "HQC");
      addCipherAlgorithm(param1ConfigurableProvider, "HQC128", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$HQC128", BCObjectIdentifiers.hqc128);
      addCipherAlgorithm(param1ConfigurableProvider, "HQC192", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$HQC192", BCObjectIdentifiers.hqc192);
      addCipherAlgorithm(param1ConfigurableProvider, "HQC256", "org.bouncycastle.pqc.jcajce.provider.hqc.HQCCipherSpi$HQC256", BCObjectIdentifiers.hqc256);
      registerOid(param1ConfigurableProvider, BCObjectIdentifiers.pqc_kem_hqc, "HQC", (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
      param1ConfigurableProvider.addKeyInfoConverter(BCObjectIdentifiers.hqc128, (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
      param1ConfigurableProvider.addKeyInfoConverter(BCObjectIdentifiers.hqc192, (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
      param1ConfigurableProvider.addKeyInfoConverter(BCObjectIdentifiers.hqc256, (AsymmetricKeyInfoConverter)hQCKeyFactorySpi);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\HQC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */