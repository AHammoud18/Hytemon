package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class Validity extends ASN1Object {
  private final Time notBefore;
  
  private final Time notAfter;
  
  public static Validity getInstance(Object paramObject) {
    return (paramObject instanceof Validity) ? (Validity)paramObject : ((paramObject != null) ? new Validity(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public static Validity getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return new Validity(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  private Validity(ASN1Sequence paramASN1Sequence) {
    int i = paramASN1Sequence.size();
    if (i != 2)
      throw new IllegalArgumentException("Bad sequence size: " + i); 
    this.notBefore = Time.getInstance(paramASN1Sequence.getObjectAt(0));
    this.notAfter = Time.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public Validity(Time paramTime1, Time paramTime2) {
    if (paramTime1 == null)
      throw new NullPointerException("'notBefore' cannot be null"); 
    if (paramTime2 == null)
      throw new NullPointerException("'notAfter' cannot be null"); 
    this.notBefore = paramTime1;
    this.notAfter = paramTime2;
  }
  
  public Time getNotBefore() {
    return this.notBefore;
  }
  
  public Time getNotAfter() {
    return this.notAfter;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)this.notBefore, (ASN1Encodable)this.notAfter);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\Validity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */