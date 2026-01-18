/*    */ package io.netty.channel;
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
/*    */ public class ChannelPipelineException
/*    */   extends ChannelException
/*    */ {
/*    */   private static final long serialVersionUID = 3379174210419885980L;
/*    */   
/*    */   public ChannelPipelineException() {}
/*    */   
/*    */   public ChannelPipelineException(String message, Throwable cause) {
/* 36 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelPipelineException(String message) {
/* 43 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelPipelineException(Throwable cause) {
/* 50 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelPipelineException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */