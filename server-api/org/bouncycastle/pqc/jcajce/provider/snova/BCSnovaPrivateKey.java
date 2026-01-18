package org.bouncycastle.pqc.jcajce.provider.snova;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.snova.SnovaPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.util.PrivateKeyFactory;
import org.bouncycastle.pqc.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.pqc.jcajce.interfaces.SnovaKey;
import org.bouncycastle.pqc.jcajce.spec.SnovaParameterSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class BCSnovaPrivateKey implements PrivateKey, SnovaKey {
  private static final long serialVersionUID = 1L;
  
  private transient SnovaPrivateKeyParameters params;
  
  private transient ASN1Set attributes;
  
  public BCSnovaPrivateKey(SnovaPrivateKeyParameters paramSnovaPrivateKeyParameters) {
    this.params = paramSnovaPrivateKeyParameters;
  }
  
  public BCSnovaPrivateKey(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    init(paramPrivateKeyInfo);
  }
  
  private void init(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    this.attributes = paramPrivateKeyInfo.getAttributes();
    this.params = (SnovaPrivateKeyParameters)PrivateKeyFactory.createKey(paramPrivateKeyInfo);
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof BCSnovaPrivateKey) {
      BCSnovaPrivateKey bCSnovaPrivateKey = (BCSnovaPrivateKey)paramObject;
      return Arrays.areEqual(this.params.getEncoded(), bCSnovaPrivateKey.params.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.params.getEncoded());
  }
  
  public final String getAlgorithm() {
    return Strings.toUpperCase(this.params.getParameters().getName());
  }
  
  public byte[] getEncoded() {
    try {
      PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo((AsymmetricKeyParameter)this.params, this.attributes);
      return privateKeyInfo.getEncoded();
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public SnovaParameterSpec getParameterSpec() {
    return SnovaParameterSpec.fromName(this.params.getParameters().getName());
  }
  
  public String getFormat() {
    return "PKCS#8";
  }
  
  SnovaPrivateKeyParameters getKeyParams() {
    return this.params;
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    byte[] arrayOfByte = (byte[])paramObjectInputStream.readObject();
    init(PrivateKeyInfo.getInstance(arrayOfByte));
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(getEncoded());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\snova\BCSnovaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */