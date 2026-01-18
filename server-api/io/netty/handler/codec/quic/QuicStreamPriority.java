/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class QuicStreamPriority
/*    */ {
/*    */   private final int urgency;
/*    */   private final boolean incremental;
/*    */   
/*    */   public QuicStreamPriority(int urgency, boolean incremental) {
/* 37 */     this.urgency = ObjectUtil.checkInRange(urgency, 0, 127, "urgency");
/* 38 */     this.incremental = incremental;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int urgency() {
/* 47 */     return this.urgency;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isIncremental() {
/* 56 */     return this.incremental;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 61 */     if (this == o) {
/* 62 */       return true;
/*    */     }
/* 64 */     if (o == null || getClass() != o.getClass()) {
/* 65 */       return false;
/*    */     }
/* 67 */     QuicStreamPriority that = (QuicStreamPriority)o;
/* 68 */     return (this.urgency == that.urgency && this.incremental == that.incremental);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     return Objects.hash(new Object[] { Integer.valueOf(this.urgency), Boolean.valueOf(this.incremental) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return "QuicStreamPriority{urgency=" + this.urgency + ", incremental=" + this.incremental + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamPriority.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */