package org.bouncycastle.asn1.bc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class ObjectStore extends ASN1Object {
  private final ASN1Encodable storeData;
  
  private final ObjectStoreIntegrityCheck integrityCheck;
  
  public ObjectStore(ObjectStoreData paramObjectStoreData, ObjectStoreIntegrityCheck paramObjectStoreIntegrityCheck) {
    this.storeData = (ASN1Encodable)paramObjectStoreData;
    this.integrityCheck = paramObjectStoreIntegrityCheck;
  }
  
  public ObjectStore(EncryptedObjectStoreData paramEncryptedObjectStoreData, ObjectStoreIntegrityCheck paramObjectStoreIntegrityCheck) {
    this.storeData = (ASN1Encodable)paramEncryptedObjectStoreData;
    this.integrityCheck = paramObjectStoreIntegrityCheck;
  }
  
  private ObjectStore(ASN1Sequence paramASN1Sequence) {
    if (paramASN1Sequence.size() != 2)
      throw new IllegalArgumentException("malformed sequence"); 
    ASN1Encodable aSN1Encodable = paramASN1Sequence.getObjectAt(0);
    if (aSN1Encodable instanceof EncryptedObjectStoreData) {
      this.storeData = aSN1Encodable;
    } else if (aSN1Encodable instanceof ObjectStoreData) {
      this.storeData = aSN1Encodable;
    } else {
      ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(aSN1Encodable);
      if (aSN1Sequence.size() == 2) {
        this.storeData = (ASN1Encodable)EncryptedObjectStoreData.getInstance(aSN1Sequence);
      } else {
        this.storeData = (ASN1Encodable)ObjectStoreData.getInstance(aSN1Sequence);
      } 
    } 
    this.integrityCheck = ObjectStoreIntegrityCheck.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static ObjectStore getInstance(Object paramObject) {
    return (paramObject instanceof ObjectStore) ? (ObjectStore)paramObject : ((paramObject != null) ? new ObjectStore(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public ObjectStoreIntegrityCheck getIntegrityCheck() {
    return this.integrityCheck;
  }
  
  public ASN1Encodable getStoreData() {
    return this.storeData;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence(this.storeData, (ASN1Encodable)this.integrityCheck);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\bc\ObjectStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */