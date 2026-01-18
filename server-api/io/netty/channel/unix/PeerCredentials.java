/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
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
/*    */ 
/*    */ public final class PeerCredentials
/*    */ {
/*    */   private final int pid;
/*    */   private final int uid;
/*    */   private final int[] gids;
/*    */   
/*    */   PeerCredentials(int p, int u, int... gids) {
/* 38 */     this.pid = p;
/* 39 */     this.uid = u;
/* 40 */     this.gids = (gids == null) ? EmptyArrays.EMPTY_INTS : gids;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int pid() {
/* 50 */     return this.pid;
/*    */   }
/*    */   
/*    */   public int uid() {
/* 54 */     return this.uid;
/*    */   }
/*    */   
/*    */   public int[] gids() {
/* 58 */     return (int[])this.gids.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     StringBuilder sb = new StringBuilder(128);
/* 64 */     sb.append("UserCredentials[pid=").append(this.pid).append("; uid=").append(this.uid).append("; gids=[");
/* 65 */     if (this.gids.length > 0) {
/* 66 */       sb.append(this.gids[0]);
/* 67 */       for (int i = 1; i < this.gids.length; i++) {
/* 68 */         sb.append(", ").append(this.gids[i]);
/*    */       }
/*    */     } 
/* 71 */     sb.append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\PeerCredentials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */