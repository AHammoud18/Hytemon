/*    */ package io.netty.channel.kqueue;
/*    */ 
/*    */ import io.netty.channel.ChannelOption;
/*    */ import io.netty.channel.unix.UnixChannelOption;
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
/*    */ public final class KQueueChannelOption<T>
/*    */   extends UnixChannelOption<T>
/*    */ {
/* 23 */   public static final ChannelOption<Integer> SO_SNDLOWAT = valueOf(KQueueChannelOption.class, "SO_SNDLOWAT");
/* 24 */   public static final ChannelOption<Boolean> TCP_NOPUSH = valueOf(KQueueChannelOption.class, "TCP_NOPUSH");
/*    */   
/* 26 */   public static final ChannelOption<AcceptFilter> SO_ACCEPTFILTER = valueOf(KQueueChannelOption.class, "SO_ACCEPTFILTER");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 33 */   public static final ChannelOption<Boolean> RCV_ALLOC_TRANSPORT_PROVIDES_GUESS = valueOf(KQueueChannelOption.class, "RCV_ALLOC_TRANSPORT_PROVIDES_GUESS");
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueChannelOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */