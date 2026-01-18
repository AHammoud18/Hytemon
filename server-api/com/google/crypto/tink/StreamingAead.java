package com.google.crypto.tink;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.GeneralSecurityException;

public interface StreamingAead {
  WritableByteChannel newEncryptingChannel(WritableByteChannel paramWritableByteChannel, byte[] paramArrayOfbyte) throws GeneralSecurityException, IOException;
  
  SeekableByteChannel newSeekableDecryptingChannel(SeekableByteChannel paramSeekableByteChannel, byte[] paramArrayOfbyte) throws GeneralSecurityException, IOException;
  
  ReadableByteChannel newDecryptingChannel(ReadableByteChannel paramReadableByteChannel, byte[] paramArrayOfbyte) throws GeneralSecurityException, IOException;
  
  OutputStream newEncryptingStream(OutputStream paramOutputStream, byte[] paramArrayOfbyte) throws GeneralSecurityException, IOException;
  
  InputStream newDecryptingStream(InputStream paramInputStream, byte[] paramArrayOfbyte) throws GeneralSecurityException, IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\StreamingAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */