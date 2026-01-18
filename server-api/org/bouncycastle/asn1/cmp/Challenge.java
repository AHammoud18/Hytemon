package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;

public class Challenge extends ASN1Object {
  private final AlgorithmIdentifier owf;
  
  private final ASN1OctetString witness;
  
  private final ASN1OctetString challenge;
  
  private final EnvelopedData encryptedRand;
  
  private Challenge(ASN1Sequence paramASN1Sequence) {
    byte b = 0;
    if (paramASN1Sequence.getObjectAt(0).toASN1Primitive() instanceof ASN1Sequence) {
      this.owf = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(b++));
    } else {
      this.owf = null;
    } 
    this.witness = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(b++));
    this.challenge = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(b++));
    if (paramASN1Sequence.size() > b) {
      if ((this.challenge.getOctets()).length != 0)
        throw new IllegalArgumentException("ambigous challenge"); 
      this.encryptedRand = EnvelopedData.getInstance(ASN1TaggedObject.getInstance(paramASN1Sequence.getObjectAt(b)), true);
    } else {
      this.encryptedRand = null;
    } 
  }
  
  public Challenge(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this((AlgorithmIdentifier)null, paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  public Challenge(byte[] paramArrayOfbyte, EnvelopedData paramEnvelopedData) {
    this((AlgorithmIdentifier)null, paramArrayOfbyte, paramEnvelopedData);
  }
  
  public Challenge(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.owf = paramAlgorithmIdentifier;
    this.witness = (ASN1OctetString)new DEROctetString(paramArrayOfbyte1);
    this.challenge = (ASN1OctetString)new DEROctetString(paramArrayOfbyte2);
    this.encryptedRand = null;
  }
  
  public Challenge(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte, EnvelopedData paramEnvelopedData) {
    this.owf = paramAlgorithmIdentifier;
    this.witness = (ASN1OctetString)new DEROctetString(paramArrayOfbyte);
    this.challenge = (ASN1OctetString)new DEROctetString(new byte[0]);
    this.encryptedRand = paramEnvelopedData;
  }
  
  public static Challenge getInstance(Object paramObject) {
    return (paramObject instanceof Challenge) ? (Challenge)paramObject : ((paramObject != null) ? new Challenge(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public AlgorithmIdentifier getOwf() {
    return this.owf;
  }
  
  public byte[] getWitness() {
    return this.witness.getOctets();
  }
  
  public boolean isEncryptedRand() {
    return (this.encryptedRand != null);
  }
  
  public byte[] getChallenge() {
    return this.challenge.getOctets();
  }
  
  public EnvelopedData getEncryptedRand() {
    return this.encryptedRand;
  }
  
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
    aSN1EncodableVector.addOptional((ASN1Encodable)this.owf);
    aSN1EncodableVector.add((ASN1Encodable)this.witness);
    aSN1EncodableVector.add((ASN1Encodable)this.challenge);
    if (this.encryptedRand != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(0, (ASN1Encodable)this.encryptedRand)); 
    return (ASN1Primitive)new DERSequence(aSN1EncodableVector);
  }
  
  public static class Rand extends ASN1Object {
    private final ASN1Integer integer;
    
    private final GeneralName sender;
    
    public Rand(byte[] param1ArrayOfbyte, GeneralName param1GeneralName) {
      this(new ASN1Integer(param1ArrayOfbyte), param1GeneralName);
    }
    
    public Rand(ASN1Integer param1ASN1Integer, GeneralName param1GeneralName) {
      this.integer = param1ASN1Integer;
      this.sender = param1GeneralName;
    }
    
    private Rand(ASN1Sequence param1ASN1Sequence) {
      if (param1ASN1Sequence.size() != 2)
        throw new IllegalArgumentException("expected sequence size of 2"); 
      this.integer = ASN1Integer.getInstance(param1ASN1Sequence.getObjectAt(0));
      this.sender = GeneralName.getInstance(param1ASN1Sequence.getObjectAt(1));
    }
    
    public static Rand getInstance(Object param1Object) {
      return (param1Object instanceof Rand) ? (Rand)param1Object : ((param1Object != null) ? new Rand(ASN1Sequence.getInstance(param1Object)) : null);
    }
    
    public ASN1Integer getInt() {
      return this.integer;
    }
    
    public GeneralName getSender() {
      return this.sender;
    }
    
    public ASN1Primitive toASN1Primitive() {
      return (ASN1Primitive)new DERSequence(new ASN1Encodable[] { (ASN1Encodable)this.integer, (ASN1Encodable)this.sender });
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\cmp\Challenge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */