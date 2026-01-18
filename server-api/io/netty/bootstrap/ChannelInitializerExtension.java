/*    */ package io.netty.bootstrap;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ServerChannel;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ChannelInitializerExtension
/*    */ {
/*    */   public static final String EXTENSIONS_SYSTEM_PROPERTY = "io.netty.bootstrap.extensions";
/*    */   
/*    */   public double priority() {
/* 75 */     return 0.0D;
/*    */   }
/*    */   
/*    */   public void postInitializeClientChannel(Channel channel) {}
/*    */   
/*    */   public void postInitializeServerListenerChannel(ServerChannel channel) {}
/*    */   
/*    */   public void postInitializeServerChildChannel(Channel channel) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\ChannelInitializerExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */