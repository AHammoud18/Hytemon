package io.netty.channel.epoll;

final class NativeStaticallyReferencedJniMethods {
  static native int epollin();
  
  static native int epollout();
  
  static native int epollrdhup();
  
  static native int epollet();
  
  static native int epollerr();
  
  static native long ssizeMax();
  
  static native int tcpMd5SigMaxKeyLen();
  
  static native int iovMax();
  
  static native int uioMaxIov();
  
  static native boolean isSupportingSendmmsg();
  
  static native boolean isSupportingRecvmmsg();
  
  static native int tcpFastopenMode();
  
  static native String kernelVersion();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\NativeStaticallyReferencedJniMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */