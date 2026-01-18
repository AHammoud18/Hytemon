/*     */ package com.hypixel.hytale.component;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeakComponentReference<ECS_TYPE, T extends Component<ECS_TYPE>>
/*     */ {
/*     */   @Nonnull
/*     */   private final Store<ECS_TYPE> store;
/*     */   @Nonnull
/*     */   private final ComponentType<ECS_TYPE, T> type;
/*     */   @Nullable
/*     */   private Ref<ECS_TYPE> ref;
/*     */   private WeakReference<T> reference;
/*     */   
/*     */   WeakComponentReference(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentType<ECS_TYPE, T> type, @Nonnull Ref<ECS_TYPE> ref, @Nonnull T data) {
/*  53 */     this.store = store;
/*  54 */     this.type = type;
/*  55 */     this.ref = ref;
/*  56 */     this.reference = new WeakReference<>(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get() {
/*  66 */     Component component = (Component)this.reference.get();
/*  67 */     if (component != null) return (T)component; 
/*  68 */     if (this.ref == null) return null; 
/*  69 */     component = this.store.getComponent(this.ref, this.type);
/*  70 */     if (component != null) this.reference = new WeakReference<>((T)component); 
/*  71 */     return (T)component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Store<ECS_TYPE> getStore() {
/*  79 */     return this.store;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<ECS_TYPE, T> getType() {
/*  87 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<ECS_TYPE> getEntityReference() {
/*  95 */     return this.ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void invalidate() {
/* 102 */     this.ref = null;
/* 103 */     this.reference.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 108 */     if (this == o) return true; 
/* 109 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 111 */     WeakComponentReference<?, ?> that = (WeakComponentReference<?, ?>)o;
/*     */     
/* 113 */     if (!this.store.equals(that.store)) return false; 
/* 114 */     if (!this.type.equals(that.type)) return false; 
/* 115 */     return Objects.equals(this.ref, that.ref);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     int result = this.store.hashCode();
/* 121 */     result = 31 * result + this.type.hashCode();
/* 122 */     result = 31 * result + ((this.ref != null) ? this.ref.hashCode() : 0);
/* 123 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 129 */     return "WeakComponentReference{store=" + String.valueOf(this.store) + ", type=" + String.valueOf(this.type) + ", entity=" + String.valueOf(this.ref) + ", reference=" + String.valueOf(this.reference) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\WeakComponentReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */