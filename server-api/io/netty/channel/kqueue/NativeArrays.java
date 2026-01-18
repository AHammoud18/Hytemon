/*    */ package io.netty.channel.kqueue;
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
/*    */   
/*    */   IovArray cleanIovArray() {
/* 29 */     if (this.iovArray == null) {
/* 30 */       this.iovArray = new IovArray();
/*    */     } else {
/* 32 */       this.iovArray.clear();
/*    */     } 
/* 34 */     return this.iovArray;
/*    */   }
/*    */ 
/*    */   
/*    */   void free() {
/* 39 */     if (this.iovArray != null) {
/* 40 */       this.iovArray.release();
/* 41 */       this.iovArray = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\NativeArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */