package META-INF.versions.9.org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.interfaces.BCKey;
import org.bouncycastle.jcajce.interfaces.MLKEMPublicKey;
import org.bouncycastle.jcajce.spec.MLKEMParameterSpec;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.PublicKeyFactory;
import org.bouncycastle.pqc.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class BCMLKEMPublicKey implements MLKEMPublicKey, BCKey {
  private static final long serialVersionUID = 1L;
  
  private transient MLKEMPublicKeyParameters params;
  
  private transient String algorithm;
  
  public BCMLKEMPublicKey(MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters) {
    init(paramMLKEMPublicKeyParameters);
  }
  
  public BCMLKEMPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    init(paramSubjectPublicKeyInfo);
  }
  
  private void init(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    this.params = (MLKEMPublicKeyParameters)PublicKeyFactory.createKey(paramSubjectPublicKeyInfo);
    this.algorithm = Strings.toUpperCase(MLKEMParameterSpec.fromName(this.params.getParameters().getName()).getName());
  }
  
  private void init(MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters) {
    this.params = paramMLKEMPublicKeyParameters;
    this.algorithm = Strings.toUpperCase(MLKEMParameterSpec.fromName(paramMLKEMPublicKeyParameters.getParameters().getName()).getName());
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPublicKey) {
      org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPublicKey bCMLKEMPublicKey = (org.bouncycastle.jcajce.provider.asymmetric.mlkem.BCMLKEMPublicKey)paramObject;
      return Arrays.areEqual(getEncoded(), bCMLKEMPublicKey.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(getEncoded());
  }
  
  public final String getAlgorithm() {
    return this.algorithm;
  }
  
  public byte[] getPublicData() {
    return this.params.getEncoded();
  }
  
  public byte[] getEncoded() {
    try {
      SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo((AsymmetricKeyParameter)this.params);
      return subjectPublicKeyInfo.getEncoded();
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public String getFormat() {
    return "X.509";
  }
  
  public MLKEMParameterSpec getParameterSpec() {
    return MLKEMParameterSpec.fromName(this.params.getParameters().getName());
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    byte[] arrayOfByte = this.params.getEncoded();
    stringBuilder.append(getAlgorithm()).append(" ").append("Public Key").append(" [").append((new Fingerprint(arrayOfByte)).toString()).append("]").append(str).append("    public data: ").append(Hex.toHexString(arrayOfByte)).append(str);
    return stringBuilder.toString();
  }
  
  MLKEMPublicKeyParameters getKeyParams() {
    return this.params;
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    byte[] arrayOfByte = (byte[])paramObjectInputStream.readObject();
    init(SubjectPublicKeyInfo.getInstance(arrayOfByte));
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(getEncoded());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajce\provider\asymmetric\mlkem\BCMLKEMPublicKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */