package org.bouncycastle.pkcs.bc;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PBMAC1Params;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS12MacCalculatorBuilder;

public class BcPKCS12PBMac1CalculatorBuilder implements PKCS12MacCalculatorBuilder {
  private final PBMAC1Params pbmac1Params;
  
  private PBKDF2Params pbkdf2Params = null;
  
  public BcPKCS12PBMac1CalculatorBuilder(PBMAC1Params paramPBMAC1Params) throws IOException {
    this.pbmac1Params = paramPBMAC1Params;
    if (PKCSObjectIdentifiers.id_PBKDF2.equals((ASN1Primitive)paramPBMAC1Params.getKeyDerivationFunc().getAlgorithm())) {
      this.pbkdf2Params = PBKDF2Params.getInstance(paramPBMAC1Params.getKeyDerivationFunc().getParameters());
      if (this.pbkdf2Params.getKeyLength() == null)
        throw new IOException("Key length must be present when using PBMAC1."); 
    } else {
      throw new IllegalArgumentException("unrecognised PBKDF");
    } 
  }
  
  public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
    return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBMAC1, (ASN1Encodable)this.pbmac1Params);
  }
  
  public MacCalculator build(char[] paramArrayOfchar) throws OperatorCreationException {
    return PKCS12PBEUtils.createPBMac1Calculator(this.pbmac1Params, this.pbkdf2Params, paramArrayOfchar);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pkcs\bc\BcPKCS12PBMac1CalculatorBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */