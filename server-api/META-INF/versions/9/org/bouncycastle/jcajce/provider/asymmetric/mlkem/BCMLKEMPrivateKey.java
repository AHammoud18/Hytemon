package META-INF.versions.9.org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.interfaces.BCKey;
import org.bouncycastle.jcajce.interfaces.MLKEMPrivateKey;
import org.bouncycastle.jcajce.interfaces.MLKEMPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPublicKey;
import org.bouncycastle.jcajce.spec.MLKEMParameterSpec;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.util.PrivateKeyFactory;
import org.bouncycastle.pqc.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class BCMLKEMPrivateKey implements MLKEMPrivateKey, BCKey {
  private static final long serialVersionUID = 1L;
  
  private transient MLKEMPrivateKeyParameters params;
  
  private transient String algorithm;
  
  private transient ASN1Set attributes;
  
  private transient byte[] priorEncoding;
  
  public BCMLKEMPrivateKey(MLKEMPrivateKeyParameters paramMLKEMPrivateKeyParameters) {
    this.params = paramMLKEMPrivateKeyParameters;
    this.algorithm = Strings.toUpperCase(paramMLKEMPrivateKeyParameters.getParameters().getName());
  }
  
  public BCMLKEMPrivateKey(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    init(paramPrivateKeyInfo);
  }
  
  private void init(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    this.attributes = paramPrivateKeyInfo.getAttributes();
    this.priorEncoding = paramPrivateKeyInfo.getEncoded();
    this.params = (MLKEMPrivateKeyParameters)PrivateKeyFactory.createKey(paramPrivateKeyInfo);
    this.algorithm = Strings.toUpperCase(MLKEMParameterSpec.fromName(this.params.getParameters().getName()).getName());
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPrivateKey) {
      org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPrivateKey bCMLKEMPrivateKey = (org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPrivateKey)paramObject;
      return Arrays.areEqual(this.params.getEncoded(), bCMLKEMPrivateKey.params.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.params.getEncoded());
  }
  
  public final String getAlgorithm() {
    return this.algorithm;
  }
  
  public byte[] getEncoded() {
    try {
      if (this.priorEncoding != null)
        return this.priorEncoding; 
      PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo((AsymmetricKeyParameter)this.params, this.attributes);
      return privateKeyInfo.getEncoded();
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public MLKEMPublicKey getPublicKey() {
    return (MLKEMPublicKey)new BCMLKEMPublicKey(this.params.getPublicKeyParameters());
  }
  
  public byte[] getPrivateData() {
    return this.params.getEncoded();
  }
  
  public byte[] getSeed() {
    return this.params.getSeed();
  }
  
  public MLKEMPrivateKey getPrivateKey(boolean paramBoolean) {
    if (paramBoolean) {
      byte[] arrayOfByte = this.params.getSeed();
      if (arrayOfByte != null)
        return new org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPrivateKey(this.params.getParametersWithFormat(1)); 
    } 
    return new org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPrivateKey(this.params.getParametersWithFormat(2));
  }
  
  public MLKEMParameterSpec getParameterSpec() {
    return MLKEMParameterSpec.fromName(this.params.getParameters().getName());
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
  
  MLKEMPrivateKeyParameters getKeyParams() {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajce\provider\asymmetric\mlkem\BCMLKEMPrivateKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */