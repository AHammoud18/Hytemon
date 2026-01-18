package org.bouncycastle.asn1.cmc;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.GeneralName;

public class GetCert extends ASN1Object {
  private final GeneralName issuerName;
  
  private final BigInteger serialNumber;
  
  private GetCert(ASN1Sequence paramASN1Sequence) {
    if (paramASN1Sequence.size() != 2)
      throw new IllegalArgumentException("incorrect sequence size"); 
    this.issuerName = GeneralName.getInstance(paramASN1Sequence.getObjectAt(0));
    this.serialNumber = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1)).getValue();
  }
  
  public GetCert(GeneralName paramGeneralName, BigInteger paramBigInteger) {
    this.issuerName = paramGeneralName;
    this.serialNumber = paramBigInteger;
  }
  
  public static GetCert getInstance(Object paramObject) {
    return (paramObject instanceof GetCert) ? (GetCert)paramObject : ((paramObject != null) ? new GetCert(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public GeneralName getIssuerName() {
    return this.issuerName;
  }
  
  public BigInteger getSerialNumber() {
    return this.serialNumber;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)this.issuerName, (ASN1Encodable)new ASN1Integer(this.serialNumber));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\cmc\GetCert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */