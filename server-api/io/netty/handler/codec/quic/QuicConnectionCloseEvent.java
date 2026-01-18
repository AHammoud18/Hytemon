/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public final class QuicConnectionCloseEvent
/*    */   implements QuicEvent
/*    */ {
/*    */   final boolean applicationClose;
/*    */   final int error;
/*    */   final byte[] reason;
/*    */   
/*    */   QuicConnectionCloseEvent(boolean applicationClose, int error, byte[] reason) {
/* 32 */     this.applicationClose = applicationClose;
/* 33 */     this.error = error;
/* 34 */     this.reason = reason;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isApplicationClose() {
/* 43 */     return this.applicationClose;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int error() {
/* 52 */     return this.error;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTlsError() {
/* 61 */     return (!this.applicationClose && this.error >= 256);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] reason() {
/* 70 */     return (byte[])this.reason.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return "QuicConnectionCloseEvent{applicationClose=" + this.applicationClose + ", error=" + this.error + ", reason=" + 
/*    */ 
/*    */       
/* 78 */       Arrays.toString(this.reason) + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int extractTlsError(int error) {
/* 90 */     int tlsError = error - 256;
/* 91 */     if (tlsError < 0) {
/* 92 */       return -1;
/*    */     }
/* 94 */     return tlsError;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicConnectionCloseEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */