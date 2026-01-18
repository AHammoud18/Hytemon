/*    */ package io.netty.channel.kqueue;
/*    */ 
/*    */ import io.netty.channel.IoOps;
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
/*    */ public final class KQueueIoOps
/*    */   implements IoOps
/*    */ {
/*    */   private final short filter;
/*    */   private final short flags;
/*    */   private final int fflags;
/*    */   private final long data;
/*    */   
/*    */   public static KQueueIoOps newOps(short filter, short flags, int fflags) {
/* 39 */     return new KQueueIoOps(filter, flags, fflags, 0L);
/*    */   }
/*    */   
/*    */   private KQueueIoOps(short filter, short flags, int fflags, long data) {
/* 43 */     this.filter = filter;
/* 44 */     this.flags = flags;
/* 45 */     this.fflags = fflags;
/* 46 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short filter() {
/* 55 */     return this.filter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short flags() {
/* 64 */     return this.flags;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int fflags() {
/* 73 */     return this.fflags;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long data() {
/* 82 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "KQueueIoOps{filter=" + this.filter + ", flags=" + this.flags + ", fflags=" + this.fflags + ", data=" + this.data + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueIoOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */