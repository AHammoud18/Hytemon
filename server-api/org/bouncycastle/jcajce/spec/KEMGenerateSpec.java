package org.bouncycastle.jcajce.spec;

import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.util.Arrays;

public class KEMGenerateSpec extends KEMKDFSpec implements AlgorithmParameterSpec {
  private static final byte[] EMPTY_OTHER_INFO = new byte[0];
  
  private static AlgorithmIdentifier DefKdf = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, (ASN1Encodable)new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
  
  private final PublicKey publicKey;
  
  private KEMGenerateSpec(PublicKey paramPublicKey, String paramString, int paramInt, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) {
    super(paramAlgorithmIdentifier, paramArrayOfbyte, paramString, paramInt);
    this.publicKey = paramPublicKey;
  }
  
  public KEMGenerateSpec(PublicKey paramPublicKey, String paramString) {
    this(paramPublicKey, paramString, 256, DefKdf, EMPTY_OTHER_INFO);
  }
  
  public KEMGenerateSpec(PublicKey paramPublicKey, String paramString, int paramInt) {
    this(paramPublicKey, paramString, paramInt, DefKdf, EMPTY_OTHER_INFO);
  }
  
  public PublicKey getPublicKey() {
    return this.publicKey;
  }
  
  public static final class Builder {
    private final PublicKey publicKey;
    
    private final String algorithmName;
    
    private final int keySizeInBits;
    
    private AlgorithmIdentifier kdfAlgorithm;
    
    private byte[] otherInfo;
    
    public Builder(PublicKey param1PublicKey, String param1String, int param1Int) {
      this.publicKey = param1PublicKey;
      this.algorithmName = param1String;
      this.keySizeInBits = param1Int;
      this.kdfAlgorithm = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, (ASN1Encodable)new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
      this.otherInfo = KEMGenerateSpec.EMPTY_OTHER_INFO;
    }
    
    public Builder withNoKdf() {
      this.kdfAlgorithm = null;
      return this;
    }
    
    public Builder withKdfAlgorithm(AlgorithmIdentifier param1AlgorithmIdentifier) {
      this.kdfAlgorithm = param1AlgorithmIdentifier;
      return this;
    }
    
    public Builder withOtherInfo(byte[] param1ArrayOfbyte) {
      this.otherInfo = (param1ArrayOfbyte == null) ? KEMGenerateSpec.EMPTY_OTHER_INFO : Arrays.clone(param1ArrayOfbyte);
      return this;
    }
    
    public KEMGenerateSpec build() {
      return new KEMGenerateSpec(this.publicKey, this.algorithmName, this.keySizeInBits, this.kdfAlgorithm, this.otherInfo);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\spec\KEMGenerateSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */