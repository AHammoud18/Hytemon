package META-INF.versions.9.org.bouncycastle.pqc.crypto.snova;

import org.bouncycastle.pqc.crypto.snova.MapGroup1;
import org.bouncycastle.pqc.crypto.snova.MapGroup2;
import org.bouncycastle.pqc.crypto.snova.SnovaParameters;

class SnovaKeyElements {
  public final MapGroup1 map1;
  
  public final byte[][][] T12;
  
  public final MapGroup2 map2;
  
  public SnovaKeyElements(SnovaParameters paramSnovaParameters) {
    int i = paramSnovaParameters.getO();
    int j = paramSnovaParameters.getV();
    int k = paramSnovaParameters.getLsq();
    this.map1 = new MapGroup1(paramSnovaParameters);
    this.T12 = new byte[j][i][k];
    this.map2 = new MapGroup2(paramSnovaParameters);
  }
  
  static int copy3d(byte[][][] paramArrayOfbyte, byte[] paramArrayOfbyte1, int paramInt) {
    for (byte b = 0; b < paramArrayOfbyte.length; b++) {
      for (byte b1 = 0; b1 < (paramArrayOfbyte[b]).length; b1++) {
        System.arraycopy(paramArrayOfbyte[b][b1], 0, paramArrayOfbyte1, paramInt, (paramArrayOfbyte[b][b1]).length);
        paramInt += (paramArrayOfbyte[b][b1]).length;
      } 
    } 
    return paramInt;
  }
  
  static int copy4d(byte[][][][] paramArrayOfbyte, byte[] paramArrayOfbyte1, int paramInt) {
    for (byte b = 0; b < paramArrayOfbyte.length; b++)
      paramInt = copy3d(paramArrayOfbyte[b], paramArrayOfbyte1, paramInt); 
    return paramInt;
  }
  
  static int copy3d(byte[] paramArrayOfbyte, int paramInt, byte[][][] paramArrayOfbyte1) {
    for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
      for (byte b1 = 0; b1 < (paramArrayOfbyte1[b]).length; b1++) {
        System.arraycopy(paramArrayOfbyte, paramInt, paramArrayOfbyte1[b][b1], 0, (paramArrayOfbyte1[b][b1]).length);
        paramInt += (paramArrayOfbyte1[b][b1]).length;
      } 
    } 
    return paramInt;
  }
  
  static int copy4d(byte[] paramArrayOfbyte, int paramInt, byte[][][][] paramArrayOfbyte1) {
    for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
      for (byte b1 = 0; b1 < (paramArrayOfbyte1[b]).length; b1++) {
        for (byte b2 = 0; b2 < (paramArrayOfbyte1[b][b1]).length; b2++) {
          System.arraycopy(paramArrayOfbyte, paramInt, paramArrayOfbyte1[b][b1][b2], 0, (paramArrayOfbyte1[b][b1][b2]).length);
          paramInt += (paramArrayOfbyte1[b][b1][b2]).length;
        } 
      } 
    } 
    return paramInt;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\snova\SnovaKeyElements.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */