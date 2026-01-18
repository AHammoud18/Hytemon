package org.bouncycastle.asn1.cmc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class PendInfo extends ASN1Object {
  private final byte[] pendToken;
  
  private final ASN1GeneralizedTime pendTime;
  
  public PendInfo(byte[] paramArrayOfbyte, ASN1GeneralizedTime paramASN1GeneralizedTime) {
    this.pendToken = Arrays.clone(paramArrayOfbyte);
    this.pendTime = paramASN1GeneralizedTime;
  }
  
  private PendInfo(ASN1Sequence paramASN1Sequence) {
    if (paramASN1Sequence.size() != 2)
      throw new IllegalArgumentException("incorrect sequence size"); 
    this.pendToken = Arrays.clone(ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(0)).getOctets());
    this.pendTime = ASN1GeneralizedTime.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static PendInfo getInstance(Object paramObject) {
    return (paramObject instanceof PendInfo) ? (PendInfo)paramObject : ((paramObject != null) ? new PendInfo(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)new DEROctetString(this.pendToken), (ASN1Encodable)this.pendTime);
  }
  
  public byte[] getPendToken() {
    return Arrays.clone(this.pendToken);
  }
  
  public ASN1GeneralizedTime getPendTime() {
    return this.pendTime;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\cmc\PendInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */