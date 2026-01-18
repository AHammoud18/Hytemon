/*    */ package com.hypixel.hytale.server.worldgen.loader.prefab;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.loader.util.FileMaskCache;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*    */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPlacementMaskRegistry
/*    */   extends FileMaskCache<BlockPlacementMask>
/*    */ {
/*    */   @Nonnull
/* 23 */   private final Map<BlockPlacementMask, BlockPlacementMask> masks = new HashMap<>(); @Nonnull
/* 24 */   private final Map<BlockPlacementMask.BlockArrayEntry, BlockPlacementMask.BlockArrayEntry> entries = new HashMap<>();
/* 25 */   private BlockPlacementMask tempMask = new BlockPlacementMask();
/* 26 */   private BlockPlacementMask.BlockArrayEntry tempEntry = new BlockPlacementMask.BlockArrayEntry();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockPlacementMask retainOrAllocateMask(BlockPlacementMask.IMask defaultMask, Long2ObjectMap<BlockPlacementMask.Mask> specificMasks) {
/* 31 */     BlockPlacementMask mask = this.tempMask;
/* 32 */     mask.set(defaultMask, specificMasks);
/*    */     
/* 34 */     BlockPlacementMask old = this.masks.putIfAbsent(mask, mask);
/* 35 */     if (old != null) return old;
/*    */     
/* 37 */     this.tempMask = new BlockPlacementMask();
/* 38 */     return mask;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BlockPlacementMask.BlockArrayEntry retainOrAllocateEntry(ResolvedBlockArray blocks, boolean replace) {
/* 43 */     BlockPlacementMask.BlockArrayEntry entry = this.tempEntry;
/* 44 */     entry.set(blocks, replace);
/*    */     
/* 46 */     BlockPlacementMask.BlockArrayEntry old = this.entries.putIfAbsent(entry, entry);
/* 47 */     if (old != null) return old;
/*    */     
/* 49 */     this.tempEntry = new BlockPlacementMask.BlockArrayEntry();
/* 50 */     return entry;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\prefab\BlockPlacementMaskRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */