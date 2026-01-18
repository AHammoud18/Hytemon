/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.Recycler;
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
/*    */ public abstract class ObjectPool<T>
/*    */ {
/*    */   @Deprecated
/*    */   public abstract T get();
/*    */   
/*    */   @Deprecated
/*    */   public static <T> ObjectPool<T> newPool(ObjectCreator<T> creator) {
/* 78 */     return new RecyclerObjectPool<>(ObjectUtil.<ObjectCreator<T>>checkNotNull(creator, "creator"));
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   private static final class RecyclerObjectPool<T>
/*    */     extends ObjectPool<T>
/*    */   {
/* 86 */     private final Recycler<T> recycler = new Recycler<T>()
/*    */       {
/*    */         protected T newObject(Recycler.Handle<T> handle) {
/* 89 */           return creator.newObject((ObjectPool.Handle<T>)handle);
/*    */         }
/*    */       };
/*    */     
/*    */     RecyclerObjectPool(final ObjectPool.ObjectCreator<T> creator) {}
/*    */     
/*    */     public T get() {
/* 96 */       return (T)this.recycler.get();
/*    */     }
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static interface ObjectCreator<T> {
/*    */     T newObject(ObjectPool.Handle<T> param1Handle);
/*    */   }
/*    */   
/*    */   public static interface Handle<T> {
/*    */     void recycle(T param1T);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ObjectPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */