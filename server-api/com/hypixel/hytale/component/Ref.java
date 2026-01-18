/*    */ package com.hypixel.hytale.component;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ref<ECS_TYPE>
/*    */ {
/* 10 */   public static final Ref<?>[] EMPTY_ARRAY = (Ref<?>[])new Ref[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final Store<ECS_TYPE> store;
/*    */   
/*    */   private volatile int index;
/*    */   
/*    */   private volatile transient int hashCode;
/*    */   
/*    */   private volatile Throwable invalidatedBy;
/*    */ 
/*    */   
/*    */   public Ref(@Nonnull Store<ECS_TYPE> store) {
/* 24 */     this(store, -2147483648);
/*    */   }
/*    */   
/*    */   public Ref(@Nonnull Store<ECS_TYPE> store, int index) {
/* 28 */     this.store = store;
/* 29 */     this.index = index;
/* 30 */     this.hashCode = hashCode0();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Store<ECS_TYPE> getStore() {
/* 35 */     return this.store;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 39 */     return this.index;
/*    */   }
/*    */   
/*    */   void setIndex(int index) {
/* 43 */     this.index = index;
/*    */   }
/*    */   
/*    */   void invalidate() {
/* 47 */     this.index = Integer.MIN_VALUE;
/* 48 */     this.hashCode = hashCode0();
/* 49 */     this.invalidatedBy = new Throwable();
/*    */   }
/*    */   
/*    */   void invalidate(@Nullable Throwable invalidatedBy) {
/* 53 */     this.index = Integer.MIN_VALUE;
/* 54 */     this.hashCode = hashCode0();
/* 55 */     this.invalidatedBy = (invalidatedBy != null) ? invalidatedBy : new Throwable();
/*    */   }
/*    */   
/*    */   public void validate() {
/* 59 */     if (this.index == Integer.MIN_VALUE) throw new IllegalStateException("Invalid entity reference!", this.invalidatedBy); 
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 63 */     return (this.index != Integer.MIN_VALUE);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 68 */     if (this == o) return true; 
/* 69 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 71 */     Ref<?> ref = (Ref)o;
/*    */     
/* 73 */     if (this.index != ref.index) return false; 
/* 74 */     return this.store.equals(ref.store);
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Ref<ECS_TYPE> o) {
/* 78 */     return (this == o || (o != null && this.index == o.index && this.store.equals(o.store)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     return this.hashCode;
/*    */   }
/*    */   
/*    */   public int hashCode0() {
/* 87 */     int result = this.store.hashCode();
/* 88 */     result = 31 * result + this.index;
/* 89 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 96 */     return "Ref{store=" + String.valueOf(this.store.getClass()) + "@" + this.store.hashCode() + ", index=" + this.index + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\Ref.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */