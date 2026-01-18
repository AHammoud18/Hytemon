/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.Recycler;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import io.netty.util.concurrent.Promise;
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
/*    */ public final class PendingWrite
/*    */ {
/* 27 */   private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>()
/*    */     {
/*    */       protected PendingWrite newObject(Recycler.Handle<PendingWrite> handle)
/*    */       {
/* 31 */         return new PendingWrite((ObjectPool.Handle)handle);
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final ObjectPool.Handle<PendingWrite> handle;
/*    */   
/*    */   public static PendingWrite newInstance(Object msg, Promise<Void> promise) {
/* 39 */     PendingWrite pending = (PendingWrite)RECYCLER.get();
/* 40 */     pending.msg = msg;
/* 41 */     pending.promise = promise;
/* 42 */     return pending;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object msg;
/*    */   private Promise<Void> promise;
/*    */   
/*    */   private PendingWrite(ObjectPool.Handle<PendingWrite> handle) {
/* 50 */     this.handle = handle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean recycle() {
/* 57 */     this.msg = null;
/* 58 */     this.promise = null;
/* 59 */     this.handle.recycle(this);
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean failAndRecycle(Throwable cause) {
/* 67 */     ReferenceCountUtil.release(this.msg);
/* 68 */     if (this.promise != null) {
/* 69 */       this.promise.setFailure(cause);
/*    */     }
/* 71 */     return recycle();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean successAndRecycle() {
/* 78 */     if (this.promise != null) {
/* 79 */       this.promise.setSuccess(null);
/*    */     }
/* 81 */     return recycle();
/*    */   }
/*    */   
/*    */   public Object msg() {
/* 85 */     return this.msg;
/*    */   }
/*    */   
/*    */   public Promise<Void> promise() {
/* 89 */     return this.promise;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Promise<Void> recycleAndGet() {
/* 96 */     Promise<Void> promise = this.promise;
/* 97 */     recycle();
/* 98 */     return promise;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\PendingWrite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */