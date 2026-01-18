/*     */ package com.hypixel.hytale.server.core.meta;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ArrayMetaStore<K>
/*     */   extends AbstractMetaStore<K> {
/*  10 */   private static final Object NO_ENTRY = new Object();
/*     */   
/*     */   private Object[] array;
/*     */   
/*     */   public ArrayMetaStore(K parent, IMetaRegistry<K> registry) {
/*  15 */     this(parent, registry, false);
/*     */   }
/*     */   
/*     */   public ArrayMetaStore(K parent, IMetaRegistry<K> registry, boolean bypassEncodedCache) {
/*  19 */     super(parent, registry, bypassEncodedCache);
/*  20 */     this.array = ArrayUtil.emptyArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T get0(@Nonnull MetaKey<T> key) {
/*  26 */     return (T)this.array[key.getId()];
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getMetaObject(@Nonnull MetaKey<T> key) {
/*  31 */     int id = key.getId();
/*  32 */     if (id >= this.array.length) {
/*  33 */       T t = (T)decodeOrNewMetaObject(key);
/*  34 */       resizeArray(t, id);
/*  35 */       return t;
/*     */     } 
/*  37 */     T obj = get0(key);
/*  38 */     if (obj == NO_ENTRY) {
/*  39 */       this.array[id] = obj = (T)decodeOrNewMetaObject(key);
/*     */     }
/*  41 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getIfPresentMetaObject(@Nonnull MetaKey<T> key) {
/*  48 */     if (key.getId() >= this.array.length) return null; 
/*  49 */     T oldObj = get0(key);
/*  50 */     return (oldObj != NO_ENTRY) ? oldObj : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T putMetaObject(@Nonnull MetaKey<T> key, T obj) {
/*  56 */     markMetaStoreDirty();
/*  57 */     int id = key.getId();
/*  58 */     if (id >= this.array.length) {
/*  59 */       resizeArray(obj, id);
/*  60 */       return null;
/*     */     } 
/*     */     
/*  63 */     T oldObj = (T)this.array[id];
/*  64 */     this.array[id] = obj;
/*  65 */     return (oldObj != NO_ENTRY) ? oldObj : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T removeMetaObject(@Nonnull MetaKey<T> key) {
/*  72 */     if (key.getId() >= this.array.length) return null; 
/*  73 */     markMetaStoreDirty();
/*     */     
/*  75 */     T oldObj = (T)this.array[key.getId()];
/*  76 */     this.array[key.getId()] = NO_ENTRY;
/*  77 */     return (oldObj != NO_ENTRY) ? oldObj : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T removeSerializedMetaObject(@Nonnull MetaKey<T> key) {
/*  83 */     if (key.getId() >= this.array.length && key instanceof PersistentMetaKey) {
/*  84 */       tryDecodeUnknownKey((PersistentMetaKey)key);
/*     */     }
/*  86 */     return removeMetaObject(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMetaObject(@Nonnull MetaKey<?> key) {
/*  91 */     int id = key.getId();
/*  92 */     if (id >= this.array.length) return false; 
/*  93 */     return (this.array[id] != NO_ENTRY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachMetaObject(@Nonnull IMetaStore.MetaEntryConsumer consumer) {
/*  98 */     for (int i = 0; i < this.array.length; i++) {
/*  99 */       Object o = this.array[i];
/* 100 */       if (o != NO_ENTRY)
/* 101 */         consumer.accept(i, o); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> void resizeArray(T obj, int id) {
/* 106 */     Object[] arr = new Object[id + 1];
/* 107 */     Arrays.fill(arr, this.array.length, arr.length, NO_ENTRY);
/* 108 */     System.arraycopy(this.array, 0, arr, 0, this.array.length);
/* 109 */     arr[id] = obj;
/* 110 */     this.array = arr;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\ArrayMetaStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */