/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.channel.ChannelOption;
/*    */ import io.netty.channel.unix.UnixChannelOption;
/*    */ import java.net.InetAddress;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EpollChannelOption<T>
/*    */   extends UnixChannelOption<T>
/*    */ {
/* 25 */   public static final ChannelOption<Boolean> TCP_CORK = valueOf(EpollChannelOption.class, "TCP_CORK");
/* 26 */   public static final ChannelOption<Long> TCP_NOTSENT_LOWAT = valueOf(EpollChannelOption.class, "TCP_NOTSENT_LOWAT");
/* 27 */   public static final ChannelOption<Integer> TCP_KEEPIDLE = valueOf(EpollChannelOption.class, "TCP_KEEPIDLE");
/* 28 */   public static final ChannelOption<Integer> TCP_KEEPINTVL = valueOf(EpollChannelOption.class, "TCP_KEEPINTVL");
/* 29 */   public static final ChannelOption<Integer> TCP_KEEPCNT = valueOf(EpollChannelOption.class, "TCP_KEEPCNT");
/*    */   
/* 31 */   public static final ChannelOption<Integer> TCP_USER_TIMEOUT = valueOf(EpollChannelOption.class, "TCP_USER_TIMEOUT");
/* 32 */   public static final ChannelOption<Boolean> IP_FREEBIND = valueOf("IP_FREEBIND");
/* 33 */   public static final ChannelOption<Boolean> IP_BIND_ADDRESS_NO_PORT = valueOf("IP_BIND_ADDRESS_NO_PORT");
/* 34 */   public static final ChannelOption<Boolean> IP_MULTICAST_ALL = valueOf("IP_MULTICAST_ALL");
/* 35 */   public static final ChannelOption<Boolean> IP_TRANSPARENT = valueOf("IP_TRANSPARENT");
/* 36 */   public static final ChannelOption<Boolean> IP_RECVORIGDSTADDR = valueOf("IP_RECVORIGDSTADDR");
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 41 */   public static final ChannelOption<Integer> TCP_FASTOPEN = ChannelOption.TCP_FASTOPEN;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 47 */   public static final ChannelOption<Boolean> TCP_FASTOPEN_CONNECT = ChannelOption.TCP_FASTOPEN_CONNECT;
/*    */   
/* 49 */   public static final ChannelOption<Integer> TCP_DEFER_ACCEPT = ChannelOption.valueOf(EpollChannelOption.class, "TCP_DEFER_ACCEPT");
/* 50 */   public static final ChannelOption<Boolean> TCP_QUICKACK = valueOf(EpollChannelOption.class, "TCP_QUICKACK");
/* 51 */   public static final ChannelOption<Integer> SO_BUSY_POLL = valueOf(EpollChannelOption.class, "SO_BUSY_POLL");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 58 */   public static final ChannelOption<EpollMode> EPOLL_MODE = ChannelOption.valueOf(EpollChannelOption.class, "EPOLL_MODE");
/*    */   
/* 60 */   public static final ChannelOption<Map<InetAddress, byte[]>> TCP_MD5SIG = valueOf("TCP_MD5SIG");
/*    */   
/* 62 */   public static final ChannelOption<Integer> MAX_DATAGRAM_PAYLOAD_SIZE = valueOf("MAX_DATAGRAM_PAYLOAD_SIZE");
/* 63 */   public static final ChannelOption<Boolean> UDP_GRO = valueOf("UDP_GRO");
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollChannelOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */