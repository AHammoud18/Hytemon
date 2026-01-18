/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public abstract class ConstantPool<T extends Constant<T>>
/*     */ {
/*  33 */   private final ConcurrentMap<String, T> constants = new ConcurrentHashMap<>();
/*     */   
/*  35 */   private final AtomicInteger nextId = new AtomicInteger(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T valueOf(Class<?> firstNameComponent, String secondNameComponent) {
/*  41 */     return valueOf((
/*  42 */         (Class)ObjectUtil.checkNotNull(firstNameComponent, "firstNameComponent")).getName() + '#' + 
/*     */         
/*  44 */         (String)ObjectUtil.checkNotNull(secondNameComponent, "secondNameComponent"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T valueOf(String name) {
/*  56 */     return getOrCreate(ObjectUtil.checkNonEmpty(name, "name"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T getOrCreate(String name) {
/*  65 */     Constant constant = (Constant)this.constants.get(name);
/*  66 */     if (constant == null) {
/*  67 */       T tempConstant = newConstant(nextId(), name);
/*  68 */       constant = (Constant)this.constants.putIfAbsent(name, tempConstant);
/*  69 */       if (constant == null) {
/*  70 */         return tempConstant;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return (T)constant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists(String name) {
/*  81 */     return this.constants.containsKey(ObjectUtil.checkNonEmpty(name, "name"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T newInstance(String name) {
/*  89 */     return createOrThrow(ObjectUtil.checkNonEmpty(name, "name"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T createOrThrow(String name) {
/*  98 */     Constant constant = (Constant)this.constants.get(name);
/*  99 */     if (constant == null) {
/* 100 */       T tempConstant = newConstant(nextId(), name);
/* 101 */       constant = (Constant)this.constants.putIfAbsent(name, tempConstant);
/* 102 */       if (constant == null) {
/* 103 */         return tempConstant;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[] { name }));
/*     */   }
/*     */   
/*     */   protected abstract T newConstant(int paramInt, String paramString);
/*     */   
/*     */   @Deprecated
/*     */   public final int nextId() {
/* 114 */     return this.nextId.getAndIncrement();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ConstantPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */