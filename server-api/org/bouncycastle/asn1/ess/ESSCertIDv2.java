package org.bouncycastle.asn1.ess;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.util.Arrays;

public class ESSCertIDv2 extends ASN1Object {
  private static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
  
  private AlgorithmIdentifier hashAlgorithm;
  
  private ASN1OctetString certHash;
  
  private IssuerSerial issuerSerial;
  
  public static ESSCertIDv2 from(ESSCertID paramESSCertID) {
    AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
    return new ESSCertIDv2(algorithmIdentifier, paramESSCertID.getCertHashObject(), paramESSCertID.getIssuerSerial());
  }
  
  public static ESSCertIDv2 getInstance(Object paramObject) {
    return (paramObject instanceof ESSCertIDv2) ? (ESSCertIDv2)paramObject : ((paramObject != null) ? new ESSCertIDv2(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  private ESSCertIDv2(ASN1Sequence paramASN1Sequence) {
    if (paramASN1Sequence.size() > 3)
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size()); 
    byte b = 0;
    if (paramASN1Sequence.getObjectAt(0) instanceof ASN1OctetString) {
      this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    } else {
      this.hashAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(b++));
    } 
    this.certHash = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(b++));
    if (paramASN1Sequence.size() > b)
      this.issuerSerial = IssuerSerial.getInstance(paramASN1Sequence.getObjectAt(b)); 
  }
  
  public ESSCertIDv2(byte[] paramArrayOfbyte) {
    this((AlgorithmIdentifier)null, paramArrayOfbyte, (IssuerSerial)null);
  }
  
  public ESSCertIDv2(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) {
    this(paramAlgorithmIdentifier, paramArrayOfbyte, (IssuerSerial)null);
  }
  
  public ESSCertIDv2(byte[] paramArrayOfbyte, IssuerSerial paramIssuerSerial) {
    this((AlgorithmIdentifier)null, paramArrayOfbyte, paramIssuerSerial);
  }
  
  public ESSCertIDv2(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte, IssuerSerial paramIssuerSerial) {
    if (paramAlgorithmIdentifier == null)
      paramAlgorithmIdentifier = DEFAULT_HASH_ALGORITHM; 
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.certHash = (ASN1OctetString)new DEROctetString(Arrays.clone(paramArrayOfbyte));
    this.issuerSerial = paramIssuerSerial;
  }
  
  public ESSCertIDv2(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString, IssuerSerial paramIssuerSerial) {
    if (paramAlgorithmIdentifier == null)
      paramAlgorithmIdentifier = DEFAULT_HASH_ALGORITHM; 
    if (paramASN1OctetString == null)
      throw new NullPointerException("'certHash' cannot be null"); 
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.certHash = paramASN1OctetString;
    this.issuerSerial = paramIssuerSerial;
  }
  
  public AlgorithmIdentifier getHashAlgorithm() {
    return this.hashAlgorithm;
  }
  
  public ASN1OctetString getCertHashObject() {
    return this.certHash;
  }
  
  public byte[] getCertHash() {
    return Arrays.clone(this.certHash.getOctets());
  }
  
  public IssuerSerial getIssuerSerial() {
    return this.issuerSerial;
  }
  
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
    if (!DEFAULT_HASH_ALGORITHM.equals(this.hashAlgorithm))
      aSN1EncodableVector.add((ASN1Encodable)this.hashAlgorithm); 
    aSN1EncodableVector.add((ASN1Encodable)this.certHash);
    if (this.issuerSerial != null)
      aSN1EncodableVector.add((ASN1Encodable)this.issuerSerial); 
    return (ASN1Primitive)new DERSequence(aSN1EncodableVector);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\ess\ESSCertIDv2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */