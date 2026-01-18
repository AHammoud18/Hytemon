/*    */ package com.hypixel.hytale.assetstore.iterator;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.JsonAsset;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CircularDependencyException extends RuntimeException {
/*    */   public CircularDependencyException(@Nonnull Collection<AssetStore<?, ?, ?>> values, @Nonnull AssetStoreIterator iterator) {
/* 11 */     super(makeMessage(values, iterator));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected static String makeMessage(@Nonnull Collection<AssetStore<?, ?, ?>> values, @Nonnull AssetStoreIterator iterator) {
/* 16 */     StringBuilder sb = new StringBuilder("Failed to process any stores there must be a circular dependency! " + String.valueOf(values) + ", " + iterator.size() + "\nWaiting for Asset Stores:\n");
/* 17 */     for (AssetStore<?, ?, ?> store : values) {
/* 18 */       if (iterator.isWaitingForDependencies(store)) {
/* 19 */         sb.append(store.getAssetClass()).append("\n");
/* 20 */         for (Class<? extends JsonAsset<?>> aClass : (Iterable<Class<? extends JsonAsset<?>>>)store.getLoadsAfter()) {
/*    */           
/* 22 */           AssetStore<?, ?, ?> otherStore = AssetRegistry.getAssetStore(aClass);
/* 23 */           if (otherStore == null) throw new IllegalArgumentException("Unable to find asset store: " + String.valueOf(aClass)); 
/* 24 */           if (iterator.isWaitingForDependencies(otherStore)) {
/* 25 */             sb.append("\t- ").append(otherStore.getAssetClass()).append("\n");
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 30 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\iterator\CircularDependencyException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */