/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ import java.util.Objects;
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
/*    */ public final class QuicStreamAddress
/*    */   extends SocketAddress
/*    */ {
/*    */   private final long streamId;
/*    */   
/*    */   public QuicStreamAddress(long streamId) {
/* 29 */     this.streamId = streamId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long streamId() {
/* 38 */     return this.streamId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 43 */     if (!(o instanceof QuicStreamAddress)) {
/* 44 */       return false;
/*    */     }
/* 46 */     QuicStreamAddress that = (QuicStreamAddress)o;
/* 47 */     return (this.streamId == that.streamId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     return Objects.hash(new Object[] { Long.valueOf(this.streamId) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "QuicStreamAddress{streamId=" + this.streamId + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */