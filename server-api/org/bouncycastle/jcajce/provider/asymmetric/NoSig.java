package org.bouncycastle.jcajce.provider.asymmetric;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class NoSig {
  private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.NoSig$";
  
  public static class Mappings extends AsymmetricAlgorithmProvider {
    public void configure(ConfigurableProvider param1ConfigurableProvider) {
      param1ConfigurableProvider.addAlgorithm("Signature." + X509ObjectIdentifiers.id_alg_noSignature, "org.bouncycastle.jcajce.provider.asymmetric.NoSig$SigSpi");
      param1ConfigurableProvider.addAlgorithm("Signature." + X509ObjectIdentifiers.id_alg_unsigned, "org.bouncycastle.jcajce.provider.asymmetric.NoSig$SigSpi");
    }
  }
  
  public static class SigSpi extends SignatureSpi {
    protected void engineInitVerify(PublicKey param1PublicKey) throws InvalidKeyException {
      throw new InvalidKeyException("attempt to pass public key to NoSig");
    }
    
    protected void engineInitSign(PrivateKey param1PrivateKey) throws InvalidKeyException {
      throw new InvalidKeyException("attempt to pass private key to NoSig");
    }
    
    protected void engineUpdate(byte param1Byte) throws SignatureException {}
    
    protected void engineUpdate(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SignatureException {}
    
    protected byte[] engineSign() throws SignatureException {
      return new byte[0];
    }
    
    protected boolean engineVerify(byte[] param1ArrayOfbyte) throws SignatureException {
      return false;
    }
    
    protected void engineSetParameter(String param1String, Object param1Object) throws InvalidParameterException {}
    
    protected Object engineGetParameter(String param1String) throws InvalidParameterException {
      return null;
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\NoSig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */