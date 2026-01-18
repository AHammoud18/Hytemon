/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class QuicDatagramExtensionEvent
/*    */   implements QuicExtensionEvent
/*    */ {
/*    */   private final int maxLength;
/*    */   
/*    */   QuicDatagramExtensionEvent(int maxLength) {
/* 29 */     this.maxLength = ObjectUtil.checkPositiveOrZero(maxLength, "maxLength");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int maxLength() {
/* 39 */     return this.maxLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return "QuicDatagramExtensionEvent{maxLength=" + this.maxLength + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicDatagramExtensionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */