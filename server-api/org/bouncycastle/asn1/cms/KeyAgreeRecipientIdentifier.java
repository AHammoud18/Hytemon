package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1Util;
import org.bouncycastle.asn1.DERTaggedObject;

public class KeyAgreeRecipientIdentifier extends ASN1Object implements ASN1Choice {
  private IssuerAndSerialNumber issuerSerial;
  
  private RecipientKeyIdentifier rKeyID;
  
  public static KeyAgreeRecipientIdentifier getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    if (!paramBoolean)
      throw new IllegalArgumentException("choice item must be explicitly tagged"); 
    return getInstance(paramASN1TaggedObject.getExplicitBaseObject());
  }
  
  public static KeyAgreeRecipientIdentifier getInstance(Object paramObject) {
    if (paramObject == null || paramObject instanceof KeyAgreeRecipientIdentifier)
      return (KeyAgreeRecipientIdentifier)paramObject; 
    if (paramObject instanceof ASN1TaggedObject) {
      ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)paramObject;
      if (aSN1TaggedObject.hasContextTag(0))
        return new KeyAgreeRecipientIdentifier(RecipientKeyIdentifier.getInstance(aSN1TaggedObject, false)); 
      throw new IllegalArgumentException("Invalid KeyAgreeRecipientIdentifier tag: " + ASN1Util.getTagText(aSN1TaggedObject));
    } 
    return new KeyAgreeRecipientIdentifier(IssuerAndSerialNumber.getInstance(paramObject));
  }
  
  public KeyAgreeRecipientIdentifier(IssuerAndSerialNumber paramIssuerAndSerialNumber) {
    this.issuerSerial = paramIssuerAndSerialNumber;
    this.rKeyID = null;
  }
  
  public KeyAgreeRecipientIdentifier(RecipientKeyIdentifier paramRecipientKeyIdentifier) {
    this.issuerSerial = null;
    this.rKeyID = paramRecipientKeyIdentifier;
  }
  
  public IssuerAndSerialNumber getIssuerAndSerialNumber() {
    return this.issuerSerial;
  }
  
  public RecipientKeyIdentifier getRKeyID() {
    return this.rKeyID;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)((this.issuerSerial != null) ? this.issuerSerial.toASN1Primitive() : new DERTaggedObject(false, 0, (ASN1Encodable)this.rKeyID));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\cms\KeyAgreeRecipientIdentifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */