package org.bouncycastle.pqc.jcajce.provider;

import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi;

public class Kyber {
  private static final String PREFIX = "org.bouncycastle.pqc.jcajce.provider.kyber.";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("KeyFactory.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi");
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi$Kyber512", NISTObjectIdentifiers.id_alg_ml_kem_512, (AsymmetricKeyInfoConverter)new KyberKeyFactorySpi.Kyber512());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi$Kyber768", NISTObjectIdentifiers.id_alg_ml_kem_768, (AsymmetricKeyInfoConverter)new KyberKeyFactorySpi.Kyber768());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi$Kyber1024", NISTObjectIdentifiers.id_alg_ml_kem_1024, (AsymmetricKeyInfoConverter)new KyberKeyFactorySpi.Kyber1024());
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.KYBER512", "ML-KEM-512");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.KYBER768", "ML-KEM-768");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.KYBER1024", "ML-KEM-1024");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-KEM", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-KEM-512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi$Kyber512");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-KEM-768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi$Kyber768");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-KEM-1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi$Kyber1024");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.KYBER", "ML-KEM");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.KYBER512", "ML-KEM-512");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.KYBER768", "ML-KEM-768");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.KYBER1024", "ML-KEM-1024");
      param1ConfigurableProvider.addAlgorithm("KeyGenerator.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi");
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi$Kyber512", NISTObjectIdentifiers.id_alg_ml_kem_512);
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi$Kyber768", NISTObjectIdentifiers.id_alg_ml_kem_768);
      addKeyGeneratorAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi$Kyber1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.KYBER512", "ML-KEM-512");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.KYBER768", "ML-KEM-768");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.KYBER1024", "ML-KEM-1024");
      KyberKeyFactorySpi kyberKeyFactorySpi = new KyberKeyFactorySpi();
      param1ConfigurableProvider.addAlgorithm("Cipher.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Base");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher." + BCObjectIdentifiers.pqc_kem_kyber, "KYBER");
      addCipherAlgorithm(param1ConfigurableProvider, "ML-KEM-512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Kyber512", NISTObjectIdentifiers.id_alg_ml_kem_512);
      addCipherAlgorithm(param1ConfigurableProvider, "ML-KEM-768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Kyber768", NISTObjectIdentifiers.id_alg_ml_kem_768);
      addCipherAlgorithm(param1ConfigurableProvider, "ML-KEM-1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Kyber1024", NISTObjectIdentifiers.id_alg_ml_kem_1024);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.KYBER512", "ML-KEM-512");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.KYBER768", "ML-KEM-768");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.KYBER1024", "ML-KEM-1024");
      registerOid(param1ConfigurableProvider, BCObjectIdentifiers.pqc_kem_kyber, "KYBER", (AsymmetricKeyInfoConverter)kyberKeyFactorySpi);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\Kyber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */