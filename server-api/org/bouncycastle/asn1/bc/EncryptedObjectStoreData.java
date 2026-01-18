package org.bouncycastle.asn1.bc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Arrays;

public class EncryptedObjectStoreData extends ASN1Object {
  private final AlgorithmIdentifier encryptionAlgorithm;
  
  private final ASN1OctetString encryptedContent;
  
  public EncryptedObjectStoreData(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) {
    this.encryptionAlgorithm = paramAlgorithmIdentifier;
    this.encryptedContent = (ASN1OctetString)new DEROctetString(Arrays.clone(paramArrayOfbyte));
  }
  
  private EncryptedObjectStoreData(ASN1Sequence paramASN1Sequence) {
    this.encryptionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.encryptedContent = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static EncryptedObjectStoreData getInstance(Object paramObject) {
    return (paramObject instanceof EncryptedObjectStoreData) ? (EncryptedObjectStoreData)paramObject : ((paramObject != null) ? new EncryptedObjectStoreData(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public ASN1OctetString getEncryptedContent() {
    return this.encryptedContent;
  }
  
  public AlgorithmIdentifier getEncryptionAlgorithm() {
    return this.encryptionAlgorithm;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)this.encryptionAlgorithm, (ASN1Encodable)this.encryptedContent);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\bc\EncryptedObjectStoreData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */