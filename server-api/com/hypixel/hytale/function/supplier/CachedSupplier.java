/*    */ package com.hypixel.hytale.function.supplier;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CachedSupplier<T> implements Supplier<T> {
/*    */   private final Supplier<T> delegate;
/*    */   private volatile transient boolean initialized;
/*    */   @Nullable
/*    */   private transient T value;
/*    */   
/*    */   public CachedSupplier(Supplier<T> delegate) {
/* 13 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public T get() {
/* 19 */     if (!this.initialized) {
/* 20 */       synchronized (this) {
/* 21 */         if (!this.initialized) {
/* 22 */           T t = this.delegate.get();
/* 23 */           this.value = t;
/* 24 */           this.initialized = true;
/* 25 */           return t;
/*    */         } 
/*    */       } 
/*    */     }
/* 29 */     return this.value;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getValue() {
/* 34 */     return this.value;
/*    */   }
/*    */   
/*    */   public void invalidate() {
/* 38 */     if (this.initialized)
/* 39 */       synchronized (this) {
/* 40 */         if (this.initialized) {
/* 41 */           this.value = null;
/* 42 */           this.initialized = false;
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\function\supplier\CachedSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */