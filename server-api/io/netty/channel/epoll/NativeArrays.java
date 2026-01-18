/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.channel.unix.IovArray;
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
/*    */ final class NativeArrays
/*    */ {
/*    */   private IovArray iovArray;
/*    */   private NativeDatagramPacketArray datagramPacketArray;
/*    */   
/*    */   IovArray cleanIovArray() {
/* 30 */     if (this.iovArray == null) {
/* 31 */       this.iovArray = new IovArray();
/*    */     } else {
/* 33 */       this.iovArray.clear();
/*    */     } 
/* 35 */     return this.iovArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   NativeDatagramPacketArray cleanDatagramPacketArray() {
/* 42 */     if (this.datagramPacketArray == null) {
/* 43 */       this.datagramPacketArray = new NativeDatagramPacketArray();
/*    */     } else {
/* 45 */       this.datagramPacketArray.clear();
/*    */     } 
/* 47 */     return this.datagramPacketArray;
/*    */   }
/*    */ 
/*    */   
/*    */   void free() {
/* 52 */     if (this.iovArray != null) {
/* 53 */       this.iovArray.release();
/* 54 */       this.iovArray = null;
/*    */     } 
/* 56 */     if (this.datagramPacketArray != null) {
/* 57 */       this.datagramPacketArray.release();
/* 58 */       this.datagramPacketArray = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\NativeArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */