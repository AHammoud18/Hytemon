/*    */ package com.hypixel.hytale.assetstore.iterator;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.JsonAsset;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import java.io.Closeable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AssetStoreIterator
/*    */   implements Iterator<AssetStore<?, ?, ?>>, Closeable {
/*    */   @Nonnull
/*    */   private final List<AssetStore<?, ?, ?>> list;
/*    */   
/*    */   public AssetStoreIterator(@Nonnull Collection<AssetStore<?, ?, ?>> values) {
/* 22 */     this.list = new ArrayList<>(values);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 27 */     return !this.list.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AssetStore<?, ?, ?> next() {
/* 33 */     for (Iterator<AssetStore<?, ?, ?>> iterator = this.list.iterator(); iterator.hasNext(); ) {
/* 34 */       AssetStore<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>, ? extends AssetMap<?, ? extends JsonAssetWithMap<?, ?>>> assetStore = (AssetStore<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>, ? extends AssetMap<?, ? extends JsonAssetWithMap<?, ?>>>)iterator.next();
/* 35 */       if (isWaitingForDependencies(assetStore))
/* 36 */         continue;  iterator.remove();
/*    */       
/* 38 */       return assetStore;
/*    */     } 
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public int size() {
/* 44 */     return this.list.size();
/*    */   }
/*    */   
/*    */   public boolean isWaitingForDependencies(@Nonnull AssetStore<?, ?, ?> assetStore) {
/* 48 */     for (Class<? extends JsonAsset<?>> aClass : (Iterable<Class<? extends JsonAsset<?>>>)assetStore.getLoadsAfter()) {
/*    */       
/* 50 */       AssetStore otherStore = AssetRegistry.getAssetStore(aClass);
/* 51 */       if (otherStore == null) throw new IllegalArgumentException("Unable to find asset store: " + String.valueOf(aClass)); 
/* 52 */       if (this.list.contains(otherStore)) return true; 
/*    */     } 
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isBeingWaitedFor(@Nonnull AssetStore<?, ?, ?> assetStore) {
/* 58 */     Class<? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>> assetClass = assetStore.getAssetClass();
/* 59 */     for (AssetStore<?, ?, ?> store : this.list) {
/* 60 */       if (store.getLoadsAfter().contains(assetClass)) return true; 
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\iterator\AssetStoreIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */