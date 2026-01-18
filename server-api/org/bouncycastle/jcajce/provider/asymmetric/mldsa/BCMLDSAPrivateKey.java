package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.interfaces.BCKey;
import org.bouncycastle.jcajce.interfaces.MLDSAPrivateKey;
import org.bouncycastle.jcajce.interfaces.MLDSAPublicKey;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.PrivateKeyFactory;
import org.bouncycastle.pqc.jcajce.provider.util.KeyUtil;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class BCMLDSAPrivateKey implements MLDSAPrivateKey, BCKey {
  private static final long serialVersionUID = 1L;
  
  private transient MLDSAPrivateKeyParameters params;
  
  private transient String algorithm;
  
  private transient byte[] encoding;
  
  private transient ASN1Set attributes;
  
  public BCMLDSAPrivateKey(MLDSAPrivateKeyParameters paramMLDSAPrivateKeyParameters) {
    this.params = paramMLDSAPrivateKeyParameters;
    this.algorithm = Strings.toUpperCase(MLDSAParameterSpec.fromName(paramMLDSAPrivateKeyParameters.getParameters().getName()).getName());
  }
  
  public BCMLDSAPrivateKey(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    init(paramPrivateKeyInfo);
  }
  
  private void init(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    this.encoding = paramPrivateKeyInfo.getEncoded();
    init((MLDSAPrivateKeyParameters)PrivateKeyFactory.createKey(paramPrivateKeyInfo), paramPrivateKeyInfo.getAttributes());
  }
  
  private void init(MLDSAPrivateKeyParameters paramMLDSAPrivateKeyParameters, ASN1Set paramASN1Set) {
    this.attributes = paramASN1Set;
    this.params = paramMLDSAPrivateKeyParameters;
    this.algorithm = Strings.toUpperCase(MLDSAParameterSpec.fromName(paramMLDSAPrivateKeyParameters.getParameters().getName()).getName());
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof BCMLDSAPrivateKey) {
      BCMLDSAPrivateKey bCMLDSAPrivateKey = (BCMLDSAPrivateKey)paramObject;
      return Arrays.areEqual(this.params.getEncoded(), bCMLDSAPrivateKey.params.getEncoded());
    } 
    return false;
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.params.getEncoded());
  }
  
  public final String getAlgorithm() {
    return this.algorithm;
  }
  
  public MLDSAPrivateKey getPrivateKey(boolean paramBoolean) {
    if (paramBoolean) {
      byte[] arrayOfByte = this.params.getSeed();
      if (arrayOfByte != null)
        return new BCMLDSAPrivateKey(this.params.getParametersWithFormat(1)); 
    } 
    return new BCMLDSAPrivateKey(this.params.getParametersWithFormat(2));
  }
  
  public byte[] getEncoded() {
    if (this.encoding == null)
      this.encoding = KeyUtil.getEncodedPrivateKeyInfo((AsymmetricKeyParameter)this.params, this.attributes); 
    return Arrays.clone(this.encoding);
  }
  
  public MLDSAPublicKey getPublicKey() {
    MLDSAPublicKeyParameters mLDSAPublicKeyParameters = this.params.getPublicKeyParameters();
    return (mLDSAPublicKeyParameters == null) ? null : new BCMLDSAPublicKey(mLDSAPublicKeyParameters);
  }
  
  public byte[] getPrivateData() {
    return this.params.getEncoded();
  }
  
  public byte[] getSeed() {
    return this.params.getSeed();
  }
  
  public MLDSAParameterSpec getParameterSpec() {
    return MLDSAParameterSpec.fromName(this.params.getParameters().getName());
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
  
  MLDSAPrivateKeyParameters getKeyParams() {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\BCMLDSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */