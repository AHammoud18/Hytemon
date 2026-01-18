package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public interface FileRegion extends ReferenceCounted {
  long position();
  
  @Deprecated
  long transfered();
  
  long transferred();
  
  long count();
  
  long transferTo(WritableByteChannel paramWritableByteChannel, long paramLong) throws IOException;
  
  FileRegion retain();
  
  FileRegion retain(int paramInt);
  
  FileRegion touch();
  
  FileRegion touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\FileRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */