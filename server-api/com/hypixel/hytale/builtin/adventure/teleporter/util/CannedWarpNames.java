/*    */ package com.hypixel.hytale.builtin.adventure.teleporter.util;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.teleporter.component.Teleporter;
/*    */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*    */ import com.hypixel.hytale.builtin.teleport.Warp;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.wordlist.WordList;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.HashSet;
/*    */ import java.util.Random;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public final class CannedWarpNames
/*    */ {
/*    */   @Nullable
/*    */   public static String generateCannedWarpName(Ref<ChunkStore> blockRef, String language) {
/* 24 */     String translationKey = generateCannedWarpNameKey(blockRef, language);
/* 25 */     if (translationKey == null) return null; 
/* 26 */     return I18nModule.get().getMessage(language, translationKey);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static String generateCannedWarpNameKey(Ref<ChunkStore> blockRef, String language) {
/* 31 */     Store<ChunkStore> store = blockRef.getStore();
/* 32 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*    */     
/* 34 */     BlockModule.BlockStateInfo blockStateInfo = (BlockModule.BlockStateInfo)store.getComponent(blockRef, BlockModule.BlockStateInfo.getComponentType());
/* 35 */     Random random = (blockStateInfo == null) ? new Random() : new Random(blockStateInfo.getIndex());
/*    */     
/* 37 */     Teleporter teleporter = (Teleporter)store.getComponent(blockRef, Teleporter.getComponentType());
/* 38 */     String wordListKey = (teleporter == null) ? null : teleporter.getWarpNameWordListKey();
/*    */     
/* 40 */     Set<String> existingNames = getWarpNamesInWorld(world);
/* 41 */     if (teleporter != null) {
/* 42 */       String ownName = teleporter.getOwnedWarp();
/* 43 */       if (ownName != null && !teleporter.isCustomName()) {
/* 44 */         existingNames.remove(ownName);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     return WordList.getWordList(wordListKey).pickTranslationKey(random, existingNames, language);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private static Set<String> getWarpNamesInWorld(World world) {
/* 53 */     Set<String> existingNames = new HashSet<>();
/* 54 */     for (Warp warp : TeleportPlugin.get().getWarps().values()) {
/* 55 */       if (warp.getWorld().equals(world.getName())) {
/* 56 */         existingNames.add(warp.getId());
/*    */       }
/*    */     } 
/* 59 */     return existingNames;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\teleporte\\util\CannedWarpNames.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */