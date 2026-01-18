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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ interface Http3RequestStreamCodecState
/*    */ {
/* 27 */   public static final Http3RequestStreamCodecState NO_STATE = new Http3RequestStreamCodecState()
/*    */     {
/*    */       public boolean started() {
/* 30 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean receivedFinalHeaders() {
/* 35 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean terminated() {
/* 40 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean started();
/*    */   
/*    */   boolean receivedFinalHeaders();
/*    */   
/*    */   boolean terminated();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamCodecState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */