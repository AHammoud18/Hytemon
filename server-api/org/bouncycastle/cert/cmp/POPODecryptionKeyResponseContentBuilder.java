package org.bouncycastle.cert.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.POPODecKeyRespContent;

public class POPODecryptionKeyResponseContentBuilder {
  private ASN1EncodableVector v = new ASN1EncodableVector();
  
  public POPODecryptionKeyResponseContentBuilder addChallengeResponse(byte[] paramArrayOfbyte) {
    this.v.add((ASN1Encodable)new ASN1Integer(new BigInteger(paramArrayOfbyte)));
    return this;
  }
  
  public POPODecryptionKeyResponseContent build() {
    return new POPODecryptionKeyResponseContent(POPODecKeyRespContent.getInstance(new DERSequence(this.v)));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\cmp\POPODecryptionKeyResponseContentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */