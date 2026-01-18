/*    */ package com.hypixel.hytale.server.core.meta;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DynamicMetaStore<K>
/*    */   extends AbstractMetaStore<K>
/*    */ {
/*    */   public DynamicMetaStore(K parent, IMetaRegistry<K> registry) {
/* 14 */     this(parent, registry, false);
/*    */   } @Nonnull
/*    */   private final Int2ObjectMap<Object> map;
/*    */   public DynamicMetaStore(K parent, IMetaRegistry<K> registry, boolean bypassEncodedCache) {
/* 18 */     super(parent, registry, bypassEncodedCache);
/* 19 */     this.map = (Int2ObjectMap<Object>)new Int2ObjectOpenHashMap();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected <T> T get0(@Nonnull MetaKey<T> key) {
/* 25 */     return (T)this.map.get(key.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getMetaObject(@Nonnull MetaKey<T> key) {
/* 30 */     T o = get0(key);
/* 31 */     if (o == null) {
/* 32 */       this.map.put(key.getId(), o = (T)decodeOrNewMetaObject(key));
/*    */     }
/* 34 */     return o;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getIfPresentMetaObject(@Nonnull MetaKey<T> key) {
/* 39 */     return get0(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T putMetaObject(@Nonnull MetaKey<T> key, T obj) {
/* 44 */     markMetaStoreDirty();
/*    */     
/* 46 */     return (T)this.map.put(key.getId(), obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T removeMetaObject(@Nonnull MetaKey<T> key) {
/* 51 */     markMetaStoreDirty();
/*    */     
/* 53 */     return (T)this.map.remove(key.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T> T removeSerializedMetaObject(MetaKey<T> key) {
/* 59 */     markMetaStoreDirty();
/* 60 */     if (key instanceof PersistentMetaKey) tryDecodeUnknownKey((PersistentMetaKey)key); 
/* 61 */     return removeMetaObject(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasMetaObject(@Nonnull MetaKey<?> key) {
/* 66 */     return this.map.containsKey(key.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void forEachMetaObject(@Nonnull IMetaStore.MetaEntryConsumer consumer) {
/* 71 */     for (ObjectIterator<Int2ObjectMap.Entry> objectIterator = this.map.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry entry = objectIterator.next();
/* 72 */       consumer.accept(entry.getIntKey(), entry.getValue()); }
/*    */   
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public DynamicMetaStore<K> clone(K parent) {
/* 78 */     DynamicMetaStore<K> clone = new DynamicMetaStore(parent, this.registry);
/* 79 */     clone.map.putAll((Map)this.map);
/* 80 */     return clone;
/*    */   }
/*    */   
/*    */   public void copyFrom(@Nonnull DynamicMetaStore<K> other) {
/* 84 */     markMetaStoreDirty();
/* 85 */     if (this.registry != other.registry) throw new IllegalArgumentException("Wrong registry used in `copyFrom`."); 
/* 86 */     this.map.putAll((Map)other.map);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\DynamicMetaStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */