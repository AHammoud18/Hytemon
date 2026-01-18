package META-INF.versions.9.org.bouncycastle.asn1.x9;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;

public class X9IntegerConverter {
  public int getByteLength(ECCurve paramECCurve) {
    return paramECCurve.getFieldElementEncodingLength();
  }
  
  public int getByteLength(ECFieldElement paramECFieldElement) {
    return paramECFieldElement.getEncodedLength();
  }
  
  public byte[] integerToBytes(BigInteger paramBigInteger, int paramInt) {
    byte[] arrayOfByte = paramBigInteger.toByteArray();
    if (paramInt < arrayOfByte.length) {
      byte[] arrayOfByte1 = new byte[paramInt];
      System.arraycopy(arrayOfByte, arrayOfByte.length - arrayOfByte1.length, arrayOfByte1, 0, arrayOfByte1.length);
      return arrayOfByte1;
    } 
    if (paramInt > arrayOfByte.length) {
      byte[] arrayOfByte1 = new byte[paramInt];
      System.arraycopy(arrayOfByte, 0, arrayOfByte1, arrayOfByte1.length - arrayOfByte.length, arrayOfByte.length);
      return arrayOfByte1;
    } 
    return arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\asn1\x9\X9IntegerConverter.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */