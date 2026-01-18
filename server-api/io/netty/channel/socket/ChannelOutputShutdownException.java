/*    */ package io.netty.channel.socket;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public final class ChannelOutputShutdownException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 6712549938359321378L;
/*    */   
/*    */   public ChannelOutputShutdownException(String msg) {
/* 28 */     super(msg);
/*    */   }
/*    */   
/*    */   public ChannelOutputShutdownException(String msg, Throwable cause) {
/* 32 */     super(msg, cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\ChannelOutputShutdownException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */