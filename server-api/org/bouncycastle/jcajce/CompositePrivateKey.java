package org.bouncycastle.jcajce;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.internal.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.internal.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.jcajce.interfaces.MLDSAPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeIndex;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyFactorySpi;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Exceptions;

public class CompositePrivateKey implements PrivateKey {
  private final List<PrivateKey> keys;
  
  private final List<Provider> providers;
  
  private AlgorithmIdentifier algorithmIdentifier;
  
  public static Builder builder(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return new Builder(new AlgorithmIdentifier(paramASN1ObjectIdentifier));
  }
  
  public static Builder builder(String paramString) {
    return builder(CompositeUtil.getOid(paramString));
  }
  
  public CompositePrivateKey(PrivateKey... paramVarArgs) {
    this(MiscObjectIdentifiers.id_composite_key, paramVarArgs);
  }
  
  public CompositePrivateKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier, PrivateKey... paramVarArgs) {
    this(new AlgorithmIdentifier(paramASN1ObjectIdentifier), paramVarArgs);
  }
  
  public CompositePrivateKey(AlgorithmIdentifier paramAlgorithmIdentifier, PrivateKey... paramVarArgs) {
    this.algorithmIdentifier = paramAlgorithmIdentifier;
    if (paramVarArgs == null || paramVarArgs.length == 0)
      throw new IllegalArgumentException("at least one private key must be provided for the composite private key"); 
    ArrayList<PrivateKey> arrayList = new ArrayList(paramVarArgs.length);
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayList.add(processKey(paramVarArgs[b])); 
    this.keys = Collections.unmodifiableList(arrayList);
    this.providers = null;
  }
  
  private PrivateKey processKey(PrivateKey paramPrivateKey) {
    if (paramPrivateKey instanceof MLDSAPrivateKey)
      try {
        return (PrivateKey)((MLDSAPrivateKey)paramPrivateKey).getPrivateKey(true);
      } catch (Exception exception) {
        return paramPrivateKey;
      }  
    return paramPrivateKey;
  }
  
  private CompositePrivateKey(AlgorithmIdentifier paramAlgorithmIdentifier, PrivateKey[] paramArrayOfPrivateKey, Provider[] paramArrayOfProvider) {
    this.algorithmIdentifier = paramAlgorithmIdentifier;
    if (paramArrayOfPrivateKey.length != 2)
      throw new IllegalArgumentException("two keys required for composite private key"); 
    ArrayList<PrivateKey> arrayList = new ArrayList(paramArrayOfPrivateKey.length);
    if (paramArrayOfProvider == null) {
      for (byte b = 0; b < paramArrayOfPrivateKey.length; b++)
        arrayList.add(processKey(paramArrayOfPrivateKey[b])); 
      this.providers = null;
    } else {
      ArrayList<Provider> arrayList1 = new ArrayList(paramArrayOfProvider.length);
      for (byte b = 0; b < paramArrayOfPrivateKey.length; b++) {
        arrayList1.add(paramArrayOfProvider[b]);
        arrayList.add(processKey(paramArrayOfPrivateKey[b]));
      } 
      this.providers = Collections.unmodifiableList(arrayList1);
    } 
    this.keys = Collections.unmodifiableList(arrayList);
  }
  
  public CompositePrivateKey(PrivateKeyInfo paramPrivateKeyInfo) {
    CompositePrivateKey compositePrivateKey = null;
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
    try {
      if (!CompositeIndex.isAlgorithmSupported(aSN1ObjectIdentifier))
        throw new IllegalStateException("Unable to create CompositePrivateKey from PrivateKeyInfo"); 
      KeyFactorySpi keyFactorySpi = new KeyFactorySpi();
      compositePrivateKey = (CompositePrivateKey)keyFactorySpi.generatePrivate(paramPrivateKeyInfo);
      if (compositePrivateKey == null)
        throw new IllegalStateException("Unable to create CompositePrivateKey from PrivateKeyInfo"); 
    } catch (IOException iOException) {
      throw Exceptions.illegalStateException(iOException.getMessage(), iOException);
    } 
    this.keys = compositePrivateKey.getPrivateKeys();
    this.providers = null;
    this.algorithmIdentifier = compositePrivateKey.getAlgorithmIdentifier();
  }
  
  public List<PrivateKey> getPrivateKeys() {
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
    return "PKCS#8";
  }
  
  public byte[] getEncoded() {
    if (this.algorithmIdentifier.getAlgorithm().on(IANAObjectIdentifiers.id_alg))
      try {
        byte[] arrayOfByte1 = ((MLDSAPrivateKey)this.keys.get(0)).getSeed();
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo(PrivateKeyFactory.createKey(((PrivateKey)this.keys.get(1)).getEncoded()));
        byte[] arrayOfByte2 = privateKeyInfo.getPrivateKey().getOctets();
        if (((PrivateKey)this.keys.get(1)).getAlgorithm().contains("Ed")) {
          arrayOfByte2 = ASN1OctetString.getInstance(arrayOfByte2).getOctets();
        } else if (((PrivateKey)this.keys.get(1)).getAlgorithm().contains("EC")) {
          ECPrivateKey eCPrivateKey = ECPrivateKey.getInstance(arrayOfByte2);
          arrayOfByte2 = (new ECPrivateKey(ECNamedCurveTable.getByOID(ASN1ObjectIdentifier.getInstance(eCPrivateKey.getParametersObject())).getCurve().getFieldSize(), eCPrivateKey.getKey(), (ASN1Encodable)eCPrivateKey.getParametersObject())).getEncoded();
        } 
        return (new PrivateKeyInfo(this.algorithmIdentifier, Arrays.concatenate(arrayOfByte1, arrayOfByte2))).getEncoded();
      } catch (IOException iOException) {
        throw new IllegalStateException("unable to encode composite public key: " + iOException.getMessage());
      }  
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
    if (this.algorithmIdentifier.getAlgorithm().equals((ASN1Primitive)MiscObjectIdentifiers.id_composite_key)) {
      for (byte b1 = 0; b1 < this.keys.size(); b1++) {
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(((PrivateKey)this.keys.get(b1)).getEncoded());
        aSN1EncodableVector.add((ASN1Encodable)privateKeyInfo);
      } 
      try {
        return (new PrivateKeyInfo(this.algorithmIdentifier, (ASN1Encodable)new DERSequence(aSN1EncodableVector))).getEncoded("DER");
      } catch (IOException iOException) {
        throw new IllegalStateException("unable to encode composite private key: " + iOException.getMessage());
      } 
    } 
    byte[] arrayOfByte = null;
    for (byte b = 0; b < this.keys.size(); b++) {
      PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(((PrivateKey)this.keys.get(b)).getEncoded());
      arrayOfByte = Arrays.concatenate(arrayOfByte, privateKeyInfo.getPrivateKey().getOctets());
    } 
    try {
      return (new PrivateKeyInfo(this.algorithmIdentifier, arrayOfByte)).getEncoded("DER");
    } catch (IOException iOException) {
      throw new IllegalStateException("unable to encode composite private key: " + iOException.getMessage());
    } 
  }
  
  public int hashCode() {
    return this.keys.hashCode();
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof CompositePrivateKey) {
      boolean bool = true;
      CompositePrivateKey compositePrivateKey = (CompositePrivateKey)paramObject;
      if (!compositePrivateKey.getAlgorithmIdentifier().equals(this.algorithmIdentifier) || !this.keys.equals(compositePrivateKey.keys))
        bool = false; 
      return bool;
    } 
    return false;
  }
  
  public static class Builder {
    private final AlgorithmIdentifier algorithmIdentifier;
    
    private final PrivateKey[] keys = new PrivateKey[2];
    
    private final Provider[] providers = new Provider[2];
    
    private int count = 0;
    
    private Builder(AlgorithmIdentifier param1AlgorithmIdentifier) {
      this.algorithmIdentifier = param1AlgorithmIdentifier;
    }
    
    public Builder addPrivateKey(PrivateKey param1PrivateKey) {
      return addPrivateKey(param1PrivateKey, (Provider)null);
    }
    
    public Builder addPrivateKey(PrivateKey param1PrivateKey, String param1String) {
      return addPrivateKey(param1PrivateKey, Security.getProvider(param1String));
    }
    
    public Builder addPrivateKey(PrivateKey param1PrivateKey, Provider param1Provider) {
      if (this.count == this.keys.length)
        throw new IllegalStateException("only " + this.keys.length + " allowed in composite"); 
      this.keys[this.count] = param1PrivateKey;
      this.providers[this.count++] = param1Provider;
      return this;
    }
    
    public CompositePrivateKey build() {
      return (this.providers[0] == null && this.providers[1] == null) ? new CompositePrivateKey(this.algorithmIdentifier, this.keys, null) : new CompositePrivateKey(this.algorithmIdentifier, this.keys, this.providers);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\CompositePrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */