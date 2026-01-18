package org.bouncycastle.pqc.crypto.snova;

class MapGroup2 {
  public final byte[][][][] f11;
  
  public final byte[][][][] f12;
  
  public final byte[][][][] f21;
  
  public MapGroup2(SnovaParameters paramSnovaParameters) {
    int i = paramSnovaParameters.getM();
    int j = paramSnovaParameters.getV();
    int k = paramSnovaParameters.getO();
    int m = paramSnovaParameters.getLsq();
    this.f11 = new byte[i][j][j][m];
    this.f12 = new byte[i][j][k][m];
    this.f21 = new byte[i][k][j][m];
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\snova\MapGroup2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */