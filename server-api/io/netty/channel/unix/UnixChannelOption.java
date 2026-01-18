/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.channel.ChannelOption;
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
/*    */ public class UnixChannelOption<T>
/*    */   extends ChannelOption<T>
/*    */ {
/* 21 */   public static final ChannelOption<Boolean> SO_REUSEPORT = valueOf(UnixChannelOption.class, "SO_REUSEPORT");
/*    */   
/* 23 */   public static final ChannelOption<DomainSocketReadMode> DOMAIN_SOCKET_READ_MODE = ChannelOption.valueOf(UnixChannelOption.class, "DOMAIN_SOCKET_READ_MODE");
/*    */ 
/*    */   
/*    */   protected UnixChannelOption() {
/* 27 */     super(null);
/*    */   }
/*    */   
/*    */   UnixChannelOption(String name) {
/* 31 */     super(name);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\UnixChannelOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */