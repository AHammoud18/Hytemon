package org.bouncycastle.tsp.ers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.io.Streams;

public class ERSInputStreamData extends ERSCachingData {
  private final File contentFile;
  
  private final byte[] contentBytes;
  
  public ERSInputStreamData(File paramFile) throws FileNotFoundException {
    if (paramFile.isDirectory())
      throw new IllegalArgumentException("directory not allowed"); 
    if (!paramFile.exists())
      throw new FileNotFoundException(paramFile + " not found"); 
    this.contentBytes = null;
    this.contentFile = paramFile;
  }
  
  public ERSInputStreamData(InputStream paramInputStream) {
    try {
      this.contentBytes = Streams.readAll(paramInputStream);
    } catch (IOException iOException) {
      throw ExpUtil.createIllegalState("unable to open content: " + iOException.getMessage(), iOException);
    } 
    this.contentFile = null;
  }
  
  protected byte[] calculateHash(DigestCalculator paramDigestCalculator, byte[] paramArrayOfbyte) {
    byte[] arrayOfByte;
    if (this.contentBytes != null) {
      arrayOfByte = ERSUtil.calculateDigest(paramDigestCalculator, this.contentBytes);
    } else {
      try {
        FileInputStream fileInputStream = new FileInputStream(this.contentFile);
        arrayOfByte = ERSUtil.calculateDigest(paramDigestCalculator, fileInputStream);
        fileInputStream.close();
      } catch (IOException iOException) {
        throw ExpUtil.createIllegalState("unable to open content: " + iOException.getMessage(), iOException);
      } 
    } 
    return (paramArrayOfbyte != null) ? ERSUtil.concatPreviousHashes(paramDigestCalculator, paramArrayOfbyte, arrayOfByte) : arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\tsp\ers\ERSInputStreamData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */