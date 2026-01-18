package org.bouncycastle.jcajce.provider.asymmetric.slhdsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.interfaces.BCKey;
import org.bouncycastle.jcajce.interfaces.SLHDSAPrivateKey;
import org.bouncycastle.jcajce.interfaces.SLHDSAPublicKey;
import org.bouncycastle.jcajce.spec.SLHDSAParameterSpec;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.PrivateKeyFactory;
import org.bouncycastle.pqc.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class BCSLHDSAPrivateKey implements SLHDSAPrivateKey, BCKey {
  private static final long serialVersionUID = 1L;
  
  private transient SLHDSAPrivateKeyParameters params;
  
  private transient ASN1Set attributes;
  
  public BCSLHDSAPrivateKey(SLHDSAPrivateKeyParameters paramSLHDSAPrivateKeyParameters) {
    this.params = paramSLHDSAPrivateKeyParameters;
  }
  
  public BCSLHDSAPrivateKey(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    init(paramPrivateKeyInfo);
  }
  
  private void init(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    this.attributes = paramPrivateKeyInfo.getAttributes();
    this.params = (SLHDSAPrivateKeyParameters)PrivateKeyFactory.createKey(paramPrivateKeyInfo);
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof BCSLHDSAPrivateKey) {
      BCSLHDSAPrivateKey bCSLHDSAPrivateKey = (BCSLHDSAPrivateKey)paramObject;
      return Arrays.areEqual(this.params.getEncoded(), bCSLHDSAPrivateKey.params.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.params.getEncoded());
  }
  
  public final String getAlgorithm() {
    return "SLH-DSA-" + Strings.toUpperCase(this.params.getParameters().getName());
  }
  
  public byte[] getEncoded() {
    try {
      PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo((AsymmetricKeyParameter)this.params, this.attributes);
      return privateKeyInfo.getEncoded();
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public SLHDSAPublicKey getPublicKey() {
    return new BCSLHDSAPublicKey(new SLHDSAPublicKeyParameters(this.params.getParameters(), this.params.getPublicKey()));
  }
  
  public SLHDSAParameterSpec getParameterSpec() {
    return SLHDSAParameterSpec.fromName(this.params.getParameters().getName());
  }
  
  public String getFormat() {
    return "PKCS#8";
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    byte[] arrayOfByte = this.params.getPublicKey();
    stringBuilder.append(getAlgorithm()).append(" ").append("Private Key").append(" [").append((new Fingerprint(arrayOfByte)).toString()).append("]").append(str).append("    public data: ").append(Hex.toHexString(arrayOfByte)).append(str);
    return stringBuilder.toString();
  }
  
  SLHDSAPrivateKeyParameters getKeyParams() {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\slhdsa\BCSLHDSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */