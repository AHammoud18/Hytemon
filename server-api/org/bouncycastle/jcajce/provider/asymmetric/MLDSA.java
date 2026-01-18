package org.bouncycastle.jcajce.provider.asymmetric;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class MLDSA {
  private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.mldsa.";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("KeyFactory.ML-DSA", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$Pure");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.ML-DSA", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$Pure");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.MLDSA", "ML-DSA");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.MLDSA", "ML-DSA");
      param1ConfigurableProvider.addAlgorithm("KeyFactory.HASH-ML-DSA", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$Hash");
      param1ConfigurableProvider.addAlgorithm("KeyPairGenerator.HASH-ML-DSA", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$Hash");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory.SHA512WITHMLDSA", "HASH-ML-DSA");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.SHA512WITHMLDSA", "HASH-ML-DSA");
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-DSA-44", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$MLDSA44", NISTObjectIdentifiers.id_ml_dsa_44, (AsymmetricKeyInfoConverter)new MLDSAKeyFactorySpi.MLDSA44());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-DSA-65", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$MLDSA65", NISTObjectIdentifiers.id_ml_dsa_65, (AsymmetricKeyInfoConverter)new MLDSAKeyFactorySpi.MLDSA65());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-DSA-87", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$MLDSA87", NISTObjectIdentifiers.id_ml_dsa_87, (AsymmetricKeyInfoConverter)new MLDSAKeyFactorySpi.MLDSA87());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-DSA-44-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$HashMLDSA44", NISTObjectIdentifiers.id_hash_ml_dsa_44_with_sha512, (AsymmetricKeyInfoConverter)new MLDSAKeyFactorySpi.HashMLDSA44());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-DSA-65-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$HashMLDSA65", NISTObjectIdentifiers.id_hash_ml_dsa_65_with_sha512, (AsymmetricKeyInfoConverter)new MLDSAKeyFactorySpi.HashMLDSA65());
      addKeyFactoryAlgorithm(param1ConfigurableProvider, "ML-DSA-87-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyFactorySpi$HashMLDSA87", NISTObjectIdentifiers.id_hash_ml_dsa_87_with_sha512, (AsymmetricKeyInfoConverter)new MLDSAKeyFactorySpi.HashMLDSA87());
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-DSA-44", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$MLDSA44", NISTObjectIdentifiers.id_ml_dsa_44);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-DSA-65", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$MLDSA65", NISTObjectIdentifiers.id_ml_dsa_65);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-DSA-87", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$MLDSA87", NISTObjectIdentifiers.id_ml_dsa_87);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-DSA-44-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$MLDSA44withSHA512", NISTObjectIdentifiers.id_hash_ml_dsa_44_with_sha512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-DSA-65-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$MLDSA65withSHA512", NISTObjectIdentifiers.id_hash_ml_dsa_65_with_sha512);
      addKeyPairGeneratorAlgorithm(param1ConfigurableProvider, "ML-DSA-87-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.MLDSAKeyPairGeneratorSpi$MLDSA87withSHA512", NISTObjectIdentifiers.id_hash_ml_dsa_87_with_sha512);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.SignatureSpi$MLDSA", (ASN1ObjectIdentifier)null);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-44", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.SignatureSpi$MLDSA44", NISTObjectIdentifiers.id_ml_dsa_44);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-65", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.SignatureSpi$MLDSA65", NISTObjectIdentifiers.id_ml_dsa_65);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-87", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.SignatureSpi$MLDSA87", NISTObjectIdentifiers.id_ml_dsa_87);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.MLDSA", "ML-DSA");
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-CALCULATE-MU", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.SignatureSpi$MLDSACalcMu", (ASN1ObjectIdentifier)null);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.MLDSA-CALCULATE-MU", "ML-DSA-CALCULATE-MU");
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-EXTERNAL-MU", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.SignatureSpi$MLDSAExtMu", (ASN1ObjectIdentifier)null);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.MLDSA-EXTERNAL-MU", "ML-DSA-EXTERNAL-MU");
      addSignatureAlgorithm(param1ConfigurableProvider, "HASH-ML-DSA", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.HashSignatureSpi$MLDSA", (ASN1ObjectIdentifier)null);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-44-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.HashSignatureSpi$MLDSA44", NISTObjectIdentifiers.id_hash_ml_dsa_44_with_sha512);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-65-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.HashSignatureSpi$MLDSA65", NISTObjectIdentifiers.id_hash_ml_dsa_65_with_sha512);
      addSignatureAlgorithm(param1ConfigurableProvider, "ML-DSA-87-WITH-SHA512", "org.bouncycastle.jcajce.provider.asymmetric.mldsa.HashSignatureSpi$MLDSA87", NISTObjectIdentifiers.id_hash_ml_dsa_87_with_sha512);
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512WITHMLDSA", "HASH-ML-DSA");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512WITHMLDSA44", "ML-DSA-44-WITH-SHA512");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512WITHMLDSA65", "ML-DSA-65-WITH-SHA512");
      param1ConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512WITHMLDSA87", "ML-DSA-87-WITH-SHA512");
      MLDSAKeyFactorySpi.Hash hash = new MLDSAKeyFactorySpi.Hash();
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_ml_dsa_44, (AsymmetricKeyInfoConverter)hash);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_ml_dsa_65, (AsymmetricKeyInfoConverter)hash);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_ml_dsa_87, (AsymmetricKeyInfoConverter)hash);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_hash_ml_dsa_44_with_sha512, (AsymmetricKeyInfoConverter)hash);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_hash_ml_dsa_65_with_sha512, (AsymmetricKeyInfoConverter)hash);
      param1ConfigurableProvider.addKeyInfoConverter(NISTObjectIdentifiers.id_hash_ml_dsa_87_with_sha512, (AsymmetricKeyInfoConverter)hash);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\MLDSA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */