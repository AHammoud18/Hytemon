package org.bouncycastle.asn1.smime;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;

public class SMIMECapabilityVector {
  private ASN1EncodableVector capabilities = new ASN1EncodableVector();
  
  public void addCapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    this.capabilities.add((ASN1Encodable)new DERSequence((ASN1Encodable)paramASN1ObjectIdentifier));
  }
  
  public void addCapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt) {
    this.capabilities.add((ASN1Encodable)new DERSequence((ASN1Encodable)paramASN1ObjectIdentifier, (ASN1Encodable)new ASN1Integer(paramInt)));
  }
  
  public void addCapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable) {
    this.capabilities.add((ASN1Encodable)new DERSequence((ASN1Encodable)paramASN1ObjectIdentifier, paramASN1Encodable));
  }
  
  public ASN1EncodableVector toASN1EncodableVector() {
    return this.capabilities;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\smime\SMIMECapabilityVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */