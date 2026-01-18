package org.bouncycastle.pqc.jcajce.provider;

import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyFactorySpi;

public class Mayo {
  private static final String PREFIX = "org.bouncycastle.pqc.jcajce.provider.mayo.";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("KeyFactory.Mayo", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyFactorySpi");
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "MAYO_1", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyFactorySpi$Mayo1", BCObjectIdentifiers.mayo1, (AsymmetricKeyInfoConverter)new MayoKeyFactorySpi.Mayo1());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "MAYO_2", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyFactorySpi$Mayo2", BCObjectIdentifiers.mayo2, (AsymmetricKeyInfoConverter)new MayoKeyFactorySpi.Mayo2());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "MAYO_3", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyFactorySpi$Mayo3", BCObjectIdentifiers.mayo3, (AsymmetricKeyInfoConverter)new MayoKeyFactorySpi.Mayo3());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "MAYO_5", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyFactorySpi$Mayo5", BCObjectIdentifiers.mayo5, (AsymmetricKeyInfoConverter)new MayoKeyFactorySpi.Mayo5());
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.Mayo", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyPairGeneratorSpi");
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "MAYO_1", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyPairGeneratorSpi$Mayo1", BCObjectIdentifiers.mayo1);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "MAYO_2", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyPairGeneratorSpi$Mayo2", BCObjectIdentifiers.mayo2);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "MAYO_3", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyPairGeneratorSpi$Mayo3", BCObjectIdentifiers.mayo3);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "MAYO_5", "org.bouncycastle.pqc.jcajce.provider.mayo.MayoKeyPairGeneratorSpi$Mayo5", BCObjectIdentifiers.mayo5);
      addSignatureAlgorithm(param1ConfigurableProvider, "Mayo", "org.bouncycastle.pqc.jcajce.provider.mayo.SignatureSpi$Base", BCObjectIdentifiers.mayo);
      addSignatureAlgorithm(param1ConfigurableProvider, "MAYO_1", "org.bouncycastle.pqc.jcajce.provider.mayo.SignatureSpi$Mayo1", BCObjectIdentifiers.mayo1);
      addSignatureAlgorithm(param1ConfigurableProvider, "MAYO_2", "org.bouncycastle.pqc.jcajce.provider.mayo.SignatureSpi$Mayo2", BCObjectIdentifiers.mayo2);
      addSignatureAlgorithm(param1ConfigurableProvider, "MAYO_3", "org.bouncycastle.pqc.jcajce.provider.mayo.SignatureSpi$Mayo3", BCObjectIdentifiers.mayo3);
      addSignatureAlgorithm(param1ConfigurableProvider, "MAYO_5", "org.bouncycastle.pqc.jcajce.provider.mayo.SignatureSpi$Mayo5", BCObjectIdentifiers.mayo5);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\Mayo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */