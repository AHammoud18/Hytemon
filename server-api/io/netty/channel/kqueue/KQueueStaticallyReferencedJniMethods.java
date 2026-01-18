package io.netty.channel.kqueue;

final class KQueueStaticallyReferencedJniMethods {
  static native short evAdd();
  
  static native short evEnable();
  
  static native short evDisable();
  
  static native short evDelete();
  
  static native short evClear();
  
  static native short evEOF();
  
  static native short evError();
  
  static native short noteReadClosed();
  
  static native short noteConnReset();
  
  static native short noteDisconnected();
  
  static native short evfiltRead();
  
  static native short evfiltWrite();
  
  static native short evfiltUser();
  
  static native short evfiltSock();
  
  static native int connectResumeOnReadWrite();
  
  static native int connectDataIdempotent();
  
  static native int fastOpenClient();
  
  static native int fastOpenServer();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueStaticallyReferencedJniMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */