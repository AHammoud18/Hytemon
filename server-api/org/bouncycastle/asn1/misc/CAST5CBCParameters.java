package org.bouncycastle.asn1.misc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class CAST5CBCParameters extends ASN1Object {
  ASN1Integer keyLength;
  
  ASN1OctetString iv;
  
  public static CAST5CBCParameters getInstance(Object paramObject) {
    return (paramObject instanceof CAST5CBCParameters) ? (CAST5CBCParameters)paramObject : ((paramObject != null) ? new CAST5CBCParameters(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public CAST5CBCParameters(byte[] paramArrayOfbyte, int paramInt) {
    this.iv = (ASN1OctetString)new DEROctetString(Arrays.clone(paramArrayOfbyte));
    this.keyLength = new ASN1Integer(paramInt);
  }
  
  private CAST5CBCParameters(ASN1Sequence paramASN1Sequence) {
    this.iv = (ASN1OctetString)paramASN1Sequence.getObjectAt(0);
    this.keyLength = (ASN1Integer)paramASN1Sequence.getObjectAt(1);
  }
  
  public byte[] getIV() {
    return Arrays.clone(this.iv.getOctets());
  }
  
  public int getKeyLength() {
    return this.keyLength.intValueExact();
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)this.iv, (ASN1Encodable)this.keyLength);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\misc\CAST5CBCParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */