package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.interfaces.BCKey;
import org.bouncycastle.jcajce.interfaces.MLDSAPublicKey;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.PublicKeyFactory;
import org.bouncycastle.pqc.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class BCMLDSAPublicKey implements MLDSAPublicKey, BCKey {
  private static final long serialVersionUID = 1L;
  
  private transient MLDSAPublicKeyParameters params;
  
  private transient String algorithm;
  
  public BCMLDSAPublicKey(MLDSAPublicKeyParameters paramMLDSAPublicKeyParameters) {
    this.params = paramMLDSAPublicKeyParameters;
    this.algorithm = Strings.toUpperCase(MLDSAParameterSpec.fromName(paramMLDSAPublicKeyParameters.getParameters().getName()).getName());
  }
  
  public BCMLDSAPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    init(paramSubjectPublicKeyInfo);
  }
  
  private void init(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    this.params = (MLDSAPublicKeyParameters)PublicKeyFactory.createKey(paramSubjectPublicKeyInfo);
    this.algorithm = Strings.toUpperCase(MLDSAParameterSpec.fromName(this.params.getParameters().getName()).getName());
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof BCMLDSAPublicKey) {
      BCMLDSAPublicKey bCMLDSAPublicKey = (BCMLDSAPublicKey)paramObject;
      return Arrays.areEqual(this.params.getEncoded(), bCMLDSAPublicKey.params.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.params.getEncoded());
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
  
  public MLDSAParameterSpec getParameterSpec() {
    return MLDSAParameterSpec.fromName(this.params.getParameters().getName());
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    byte[] arrayOfByte = this.params.getEncoded();
    stringBuilder.append(getAlgorithm()).append(" ").append("Public Key").append(" [").append((new Fingerprint(arrayOfByte)).toString()).append("]").append(str).append("    public data: ").append(Hex.toHexString(arrayOfByte)).append(str);
    return stringBuilder.toString();
  }
  
  MLDSAPublicKeyParameters getKeyParams() {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\BCMLDSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */