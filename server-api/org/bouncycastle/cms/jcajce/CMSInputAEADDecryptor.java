package org.bouncycastle.cms.jcajce;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.InputStreamWithMAC;
import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.operator.InputAEADDecryptor;

class CMSInputAEADDecryptor implements InputAEADDecryptor {
  private final AlgorithmIdentifier contentEncryptionAlgorithm;
  
  private final Cipher dataCipher;
  
  private InputStream inputStream;
  
  CMSInputAEADDecryptor(AlgorithmIdentifier paramAlgorithmIdentifier, Cipher paramCipher) {
    this.contentEncryptionAlgorithm = paramAlgorithmIdentifier;
    this.dataCipher = paramCipher;
  }
  
  public AlgorithmIdentifier getAlgorithmIdentifier() {
    return this.contentEncryptionAlgorithm;
  }
  
  public InputStream getInputStream(InputStream paramInputStream) {
    this.inputStream = paramInputStream;
    return (InputStream)new CipherInputStream(paramInputStream, this.dataCipher);
  }
  
  public OutputStream getAADStream() {
    return checkForAEAD() ? new JceAADStream(this.dataCipher) : null;
  }
  
  public byte[] getMAC() {
    return (this.inputStream instanceof InputStreamWithMAC) ? ((InputStreamWithMAC)this.inputStream).getMAC() : null;
  }
  
  private static boolean checkForAEAD() {
    return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
          public Object run() {
            try {
              return Boolean.valueOf((Cipher.class.getMethod("updateAAD", new Class[] { byte[].class }) != null));
            } catch (Exception exception) {
              return Boolean.FALSE;
            } 
          }
        })).booleanValue();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\jcajce\CMSInputAEADDecryptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */