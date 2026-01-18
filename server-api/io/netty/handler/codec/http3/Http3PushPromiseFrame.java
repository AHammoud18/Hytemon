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
/*    */ public interface Http3PushPromiseFrame
/*    */   extends Http3RequestStreamFrame
/*    */ {
/*    */   default long type() {
/* 25 */     return 5L;
/*    */   }
/*    */   
/*    */   long id();
/*    */   
/*    */   Http3Headers headers();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3PushPromiseFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */