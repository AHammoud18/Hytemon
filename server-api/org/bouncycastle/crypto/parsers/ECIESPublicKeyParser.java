package org.bouncycastle.crypto.parsers;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.crypto.KeyParser;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.io.Streams;

public class ECIESPublicKeyParser implements KeyParser {
  private ECDomainParameters ecParams;
  
  public ECIESPublicKeyParser(ECDomainParameters paramECDomainParameters) {
    this.ecParams = paramECDomainParameters;
  }
  
  public AsymmetricKeyParameter readKey(InputStream paramInputStream) throws IOException {
    boolean bool;
    int i = paramInputStream.read();
    if (i < 0)
      throw new EOFException(); 
    switch (i) {
      case 0:
        throw new IOException("Sender's public key invalid.");
      case 2:
      case 3:
        bool = true;
        break;
      case 4:
      case 6:
      case 7:
        bool = false;
        break;
      default:
        throw new IOException("Sender's public key has invalid point encoding 0x" + Integer.toString(i, 16));
    } 
    ECCurve eCCurve = this.ecParams.getCurve();
    int j = eCCurve.getAffinePointEncodingLength(bool);
    byte[] arrayOfByte = new byte[j];
    arrayOfByte[0] = (byte)i;
    int k = j - 1;
    if (Streams.readFully(paramInputStream, arrayOfByte, 1, k) != k)
      throw new EOFException(); 
    return (AsymmetricKeyParameter)new ECPublicKeyParameters(eCCurve.decodePoint(arrayOfByte), this.ecParams);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\parsers\ECIESPublicKeyParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */