package META-INF.versions.9.org.bouncycastle.pqc.jcajce.provider.kyber;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.PublicKeyFactory;
import org.bouncycastle.pqc.jcajce.interfaces.KyberPublicKey;
import org.bouncycastle.pqc.jcajce.provider.util.KeyUtil;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class BCKyberPublicKey implements KyberPublicKey {
  private static final long serialVersionUID = 1L;
  
  private transient MLKEMPublicKeyParameters params;
  
  private transient String algorithm;
  
  private transient byte[] encoding;
  
  public BCKyberPublicKey(MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters) {
    init(paramMLKEMPublicKeyParameters);
  }
  
  public BCKyberPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    init(paramSubjectPublicKeyInfo);
  }
  
  private void init(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    init((MLKEMPublicKeyParameters)PublicKeyFactory.createKey(paramSubjectPublicKeyInfo));
  }
  
  private void init(MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters) {
    this.params = paramMLKEMPublicKeyParameters;
    this.algorithm = Strings.toUpperCase(paramMLKEMPublicKeyParameters.getParameters().getName());
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPublicKey) {
      org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPublicKey bCKyberPublicKey = (org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPublicKey)paramObject;
      return Arrays.areEqual(getEncoded(), bCKyberPublicKey.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(getEncoded());
  }
  
  public final String getAlgorithm() {
    return this.algorithm;
  }
  
  public byte[] getEncoded() {
    if (this.encoding == null)
      this.encoding = KeyUtil.getEncodedSubjectPublicKeyInfo((AsymmetricKeyParameter)this.params); 
    return Arrays.clone(this.encoding);
  }
  
  public String getFormat() {
    return "X.509";
  }
  
  public KyberParameterSpec getParameterSpec() {
    return KyberParameterSpec.fromName(this.params.getParameters().getName());
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\provider\kyber\BCKyberPublicKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */