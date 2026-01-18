/*     */ package com.hypixel.hytale.assetstore;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.event.RegisterAssetStoreEvent;
/*     */ import com.hypixel.hytale.assetstore.event.RemoveAssetStoreEvent;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetRegistry
/*     */ {
/*  24 */   public static final ReadWriteLock ASSET_LOCK = new ReentrantReadWriteLock();
/*     */   
/*     */   public static boolean HAS_INIT = false;
/*     */   
/*     */   public static final int TAG_NOT_FOUND = -2147483648;
/*  29 */   private static final Map<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>> storeMap = new HashMap<>();
/*  30 */   private static final Map<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>> storeMapUnmodifiable = Collections.unmodifiableMap(storeMap);
/*     */   
/*  32 */   private static final AtomicInteger NEXT_TAG_INDEX = new AtomicInteger();
/*  33 */   private static final StampedLock TAG_LOCK = new StampedLock();
/*  34 */   private static final Object2IntMap<String> TAG_MAP = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*  35 */   private static final Object2IntMap<String> CLIENT_TAG_MAP = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */   static {
/*  37 */     TAG_MAP.defaultReturnValue(-2147483648);
/*  38 */     CLIENT_TAG_MAP.defaultReturnValue(-2147483648);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>> getStoreMap() {
/*  43 */     return storeMapUnmodifiable;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>> AssetStore<K, T, M> getAssetStore(Class<T> tClass) {
/*  48 */     return (AssetStore<K, T, M>)storeMap.get(tClass);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>, S extends AssetStore<K, T, M>> S register(@Nonnull S assetStore) {
/*  53 */     ASSET_LOCK.writeLock().lock();
/*     */     try {
/*  55 */       if (storeMap.putIfAbsent(assetStore.getAssetClass(), (AssetStore<?, ?, ?>)assetStore) != null) {
/*  56 */         throw new IllegalArgumentException("Asset Store already exists for " + String.valueOf(assetStore.getAssetClass()));
/*     */       }
/*     */     } finally {
/*  59 */       ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*  61 */     IEventDispatcher<RegisterAssetStoreEvent, RegisterAssetStoreEvent> dispatch = assetStore.getEventBus().dispatchFor(RegisterAssetStoreEvent.class);
/*  62 */     if (dispatch.hasListener()) dispatch.dispatch((IBaseEvent)new RegisterAssetStoreEvent((AssetStore)assetStore)); 
/*  63 */     return assetStore;
/*     */   }
/*     */   
/*     */   public static <K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>, S extends AssetStore<K, T, M>> void unregister(@Nonnull S assetStore) {
/*  67 */     ASSET_LOCK.writeLock().lock();
/*     */     try {
/*  69 */       storeMap.remove(assetStore.getAssetClass());
/*     */     } finally {
/*  71 */       ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*  73 */     IEventDispatcher<RemoveAssetStoreEvent, RemoveAssetStoreEvent> dispatch = assetStore.getEventBus().dispatchFor(RemoveAssetStoreEvent.class);
/*  74 */     if (dispatch.hasListener()) dispatch.dispatch((IBaseEvent)new RemoveAssetStoreEvent((AssetStore)assetStore)); 
/*     */   }
/*     */   
/*     */   public static int getTagIndex(@Nonnull String tag) {
/*  78 */     if (tag == null) throw new IllegalArgumentException("tag can't be null!"); 
/*  79 */     long stamp = TAG_LOCK.readLock();
/*     */     try {
/*  81 */       return TAG_MAP.getInt(tag);
/*     */     } finally {
/*  83 */       TAG_LOCK.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getOrCreateTagIndex(@Nonnull String tag) {
/*  88 */     if (tag == null) throw new IllegalArgumentException("tag can't be null!"); 
/*  89 */     long stamp = TAG_LOCK.writeLock();
/*     */     try {
/*  91 */       return TAG_MAP.computeIfAbsent(tag.intern(), k -> NEXT_TAG_INDEX.getAndIncrement());
/*     */     } finally {
/*  93 */       TAG_LOCK.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean registerClientTag(@Nonnull String tag) {
/* 101 */     if (tag == null) throw new IllegalArgumentException("tag can't be null!"); 
/* 102 */     long stamp = TAG_LOCK.writeLock();
/*     */     try {
/* 104 */       return (CLIENT_TAG_MAP.put(tag, TAG_MAP.computeIfAbsent(tag, k -> NEXT_TAG_INDEX.getAndIncrement())) == Integer.MIN_VALUE);
/*     */     } finally {
/* 106 */       TAG_LOCK.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Object2IntMap<String> getClientTags() {
/* 112 */     long stamp = TAG_LOCK.readLock();
/*     */     try {
/* 114 */       return (Object2IntMap<String>)new Object2IntOpenHashMap(CLIENT_TAG_MAP);
/*     */     } finally {
/* 116 */       TAG_LOCK.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */