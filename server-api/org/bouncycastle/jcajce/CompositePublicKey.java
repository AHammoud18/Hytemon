package org.bouncycastle.jcajce;

import java.io.IOException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.internal.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.internal.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeIndex;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyFactorySpi;
import org.bouncycastle.pqc.crypto.util.PublicKeyFactory;
import org.bouncycastle.pqc.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.util.Arrays;

public class CompositePublicKey implements PublicKey {
  private final List<PublicKey> keys;
  
  private final List<Provider> providers;
  
  private final AlgorithmIdentifier algorithmIdentifier;
  
  public static Builder builder(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return new Builder(new AlgorithmIdentifier(paramASN1ObjectIdentifier));
  }
  
  public static Builder builder(String paramString) {
    return builder(CompositeUtil.getOid(paramString));
  }
  
  public CompositePublicKey(PublicKey... paramVarArgs) {
    this(MiscObjectIdentifiers.id_composite_key, paramVarArgs);
  }
  
  public CompositePublicKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier, PublicKey... paramVarArgs) {
    this(new AlgorithmIdentifier(paramASN1ObjectIdentifier), paramVarArgs);
  }
  
  public CompositePublicKey(AlgorithmIdentifier paramAlgorithmIdentifier, PublicKey... paramVarArgs) {
    this.algorithmIdentifier = paramAlgorithmIdentifier;
    if (paramVarArgs == null || paramVarArgs.length == 0)
      throw new IllegalArgumentException("at least one public key must be provided for the composite public key"); 
    ArrayList<PublicKey> arrayList = new ArrayList(paramVarArgs.length);
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayList.add(paramVarArgs[b]); 
    this.keys = Collections.unmodifiableList(arrayList);
    this.providers = null;
  }
  
  public CompositePublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) {
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
    CompositePublicKey compositePublicKey = null;
    try {
      if (!CompositeIndex.isAlgorithmSupported(aSN1ObjectIdentifier))
        throw new IllegalStateException("unable to create CompositePublicKey from SubjectPublicKeyInfo"); 
      KeyFactorySpi keyFactorySpi = new KeyFactorySpi();
      compositePublicKey = (CompositePublicKey)keyFactorySpi.generatePublic(paramSubjectPublicKeyInfo);
      if (compositePublicKey == null)
        throw new IllegalStateException("unable to create CompositePublicKey from SubjectPublicKeyInfo"); 
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException.getMessage(), iOException);
    } 
    this.keys = compositePublicKey.getPublicKeys();
    this.algorithmIdentifier = compositePublicKey.getAlgorithmIdentifier();
    this.providers = null;
  }
  
  private CompositePublicKey(AlgorithmIdentifier paramAlgorithmIdentifier, PublicKey[] paramArrayOfPublicKey, Provider[] paramArrayOfProvider) {
    this.algorithmIdentifier = paramAlgorithmIdentifier;
    if (paramArrayOfPublicKey.length != 2)
      throw new IllegalArgumentException("two keys required for composite private key"); 
    ArrayList<PublicKey> arrayList = new ArrayList(paramArrayOfPublicKey.length);
    if (paramArrayOfProvider == null) {
      for (byte b = 0; b < paramArrayOfPublicKey.length; b++)
        arrayList.add(paramArrayOfPublicKey[b]); 
      this.providers = null;
    } else {
      ArrayList<Provider> arrayList1 = new ArrayList(paramArrayOfProvider.length);
      for (byte b = 0; b < paramArrayOfPublicKey.length; b++) {
        arrayList1.add(paramArrayOfProvider[b]);
        arrayList.add(paramArrayOfPublicKey[b]);
      } 
      this.providers = Collections.unmodifiableList(arrayList1);
    } 
    this.keys = Collections.unmodifiableList(arrayList);
  }
  
  public List<PublicKey> getPublicKeys() {
    return this.keys;
  }
  
  public List<Provider> getProviders() {
    return this.providers;
  }
  
  public String getAlgorithm() {
    return CompositeIndex.getAlgorithmName(this.algorithmIdentifier.getAlgorithm());
  }
  
  public AlgorithmIdentifier getAlgorithmIdentifier() {
    return this.algorithmIdentifier;
  }
  
  public String getFormat() {
    return "X.509";
  }
  
  public byte[] getEncoded() {
    if (this.algorithmIdentifier.getAlgorithm().on(IANAObjectIdentifiers.id_alg))
      try {
        byte[] arrayOfByte1 = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(PublicKeyFactory.createKey(((PublicKey)this.keys.get(0)).getEncoded())).getPublicKeyData().getBytes();
        byte[] arrayOfByte2 = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(PublicKeyFactory.createKey(((PublicKey)this.keys.get(1)).getEncoded())).getPublicKeyData().getBytes();
        return (new SubjectPublicKeyInfo(getAlgorithmIdentifier(), Arrays.concatenate(arrayOfByte1, arrayOfByte2))).getEncoded();
      } catch (IOException iOException) {
        throw new IllegalStateException("unable to encode composite public key: " + iOException.getMessage());
      }  
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
    for (byte b = 0; b < this.keys.size(); b++) {
      if (this.algorithmIdentifier.getAlgorithm().equals((ASN1Primitive)MiscObjectIdentifiers.id_composite_key)) {
        aSN1EncodableVector.add((ASN1Encodable)SubjectPublicKeyInfo.getInstance(((PublicKey)this.keys.get(b)).getEncoded()));
      } else {
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(((PublicKey)this.keys.get(b)).getEncoded());
        aSN1EncodableVector.add((ASN1Encodable)subjectPublicKeyInfo.getPublicKeyData());
      } 
    } 
    try {
      return (new SubjectPublicKeyInfo(this.algorithmIdentifier, (ASN1Encodable)new DERSequence(aSN1EncodableVector))).getEncoded("DER");
    } catch (IOException iOException) {
      throw new IllegalStateException("unable to encode composite public key: " + iOException.getMessage());
    } 
  }
  
  public int hashCode() {
    return this.keys.hashCode();
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof CompositePublicKey) {
      boolean bool = true;
      CompositePublicKey compositePublicKey = (CompositePublicKey)paramObject;
      if (!compositePublicKey.getAlgorithmIdentifier().equals(this.algorithmIdentifier) || !this.keys.equals(compositePublicKey.keys))
        bool = false; 
      return bool;
    } 
    return false;
  }
  
  public static class Builder {
    private final AlgorithmIdentifier algorithmIdentifier;
    
    private final PublicKey[] keys = new PublicKey[2];
    
    private final Provider[] providers = new Provider[2];
    
    private int count = 0;
    
    private Builder(AlgorithmIdentifier param1AlgorithmIdentifier) {
      this.algorithmIdentifier = param1AlgorithmIdentifier;
    }
    
    public Builder addPublicKey(PublicKey param1PublicKey) {
      return addPublicKey(param1PublicKey, (Provider)null);
    }
    
    public Builder addPublicKey(PublicKey param1PublicKey, String param1String) {
      return addPublicKey(param1PublicKey, Security.getProvider(param1String));
    }
    
    public Builder addPublicKey(PublicKey param1PublicKey, Provider param1Provider) {
      if (this.count == this.keys.length)
        throw new IllegalStateException("only " + this.keys.length + " allowed in composite"); 
      this.keys[this.count] = param1PublicKey;
      this.providers[this.count++] = param1Provider;
      return this;
    }
    
    public CompositePublicKey build() {
      return (this.providers[0] == null && this.providers[1] == null) ? new CompositePublicKey(this.algorithmIdentifier, this.keys, null) : new CompositePublicKey(this.algorithmIdentifier, this.keys, this.providers);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\CompositePublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */