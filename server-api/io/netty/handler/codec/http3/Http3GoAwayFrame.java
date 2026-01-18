/*    */ package io.netty.handler.codec.http3;
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
/*    */ public interface Http3GoAwayFrame
/*    */   extends Http3ControlStreamFrame
/*    */ {
/*    */   default long type() {
/* 25 */     return 7L;
/*    */   }
/*    */   
/*    */   long id();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3GoAwayFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */