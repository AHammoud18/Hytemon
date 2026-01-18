package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.IssuerAndSerialNumber;

public class PrivateKeyPossessionStatement extends ASN1Object {
  private final IssuerAndSerialNumber signer;
  
  private final Certificate cert;
  
  public static PrivateKeyPossessionStatement getInstance(Object paramObject) {
    return (paramObject instanceof PrivateKeyPossessionStatement) ? (PrivateKeyPossessionStatement)paramObject : ((paramObject != null) ? new PrivateKeyPossessionStatement(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  private PrivateKeyPossessionStatement(ASN1Sequence paramASN1Sequence) {
    if (paramASN1Sequence.size() == 1) {
      this.signer = IssuerAndSerialNumber.getInstance(paramASN1Sequence.getObjectAt(0));
      this.cert = null;
    } else if (paramASN1Sequence.size() == 2) {
      this.signer = IssuerAndSerialNumber.getInstance(paramASN1Sequence.getObjectAt(0));
      this.cert = Certificate.getInstance(paramASN1Sequence.getObjectAt(1));
    } else {
      throw new IllegalArgumentException("unknown sequence in PrivateKeyStatement");
    } 
  }
  
  public PrivateKeyPossessionStatement(IssuerAndSerialNumber paramIssuerAndSerialNumber) {
    this.signer = paramIssuerAndSerialNumber;
    this.cert = null;
  }
  
  public PrivateKeyPossessionStatement(Certificate paramCertificate) {
    this.signer = new IssuerAndSerialNumber(paramCertificate.getIssuer(), paramCertificate.getSerialNumber().getValue());
    this.cert = paramCertificate;
  }
  
  public IssuerAndSerialNumber getSigner() {
    return this.signer;
  }
  
  public Certificate getCert() {
    return this.cert;
  }
  
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
    aSN1EncodableVector.add((ASN1Encodable)this.signer);
    if (this.cert != null)
      aSN1EncodableVector.add((ASN1Encodable)this.cert); 
    return (ASN1Primitive)new DERSequence(aSN1EncodableVector);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\PrivateKeyPossessionStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */