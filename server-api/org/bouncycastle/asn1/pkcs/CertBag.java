package org.bouncycastle.asn1.pkcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class CertBag extends ASN1Object {
  private ASN1ObjectIdentifier certId;
  
  private ASN1Encodable certValue;
  
  private CertBag(ASN1Sequence paramASN1Sequence) {
    this.certId = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.certValue = (ASN1Encodable)ASN1TaggedObject.getInstance(paramASN1Sequence.getObjectAt(1)).getExplicitBaseObject();
  }
  
  public static CertBag getInstance(Object paramObject) {
    return (paramObject instanceof CertBag) ? (CertBag)paramObject : ((paramObject != null) ? new CertBag(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public CertBag(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable) {
    this.certId = paramASN1ObjectIdentifier;
    this.certValue = paramASN1Encodable;
  }
  
  public ASN1ObjectIdentifier getCertId() {
    return this.certId;
  }
  
  public ASN1Encodable getCertValue() {
    return this.certValue;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)this.certId, (ASN1Encodable)new DERTaggedObject(0, this.certValue));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\pkcs\CertBag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */