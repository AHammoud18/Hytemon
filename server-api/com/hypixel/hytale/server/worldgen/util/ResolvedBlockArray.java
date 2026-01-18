/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ResolvedBlockArray
/*    */ {
/* 16 */   public static final ResolvedBlockArray EMPTY = new ResolvedBlockArray(BlockFluidEntry.EMPTY_ARRAY);
/*    */   
/* 18 */   public static final Long2ObjectMap<ResolvedBlockArray> RESOLVED_BLOCKS = Long2ObjectMaps.synchronize((Long2ObjectMap)new Long2ObjectOpenHashMap());
/* 19 */   public static final Long2ObjectMap<ResolvedBlockArray> RESOLVED_BLOCKS_WITH_VARIANTS = Long2ObjectMaps.synchronize((Long2ObjectMap)new Long2ObjectOpenHashMap());
/*    */   
/*    */   @Nonnull
/*    */   private final LongSet entrySet;
/*    */   @Nonnull
/*    */   private final BlockFluidEntry[] entries;
/*    */   
/*    */   public ResolvedBlockArray(@Nonnull BlockFluidEntry[] entries) {
/* 27 */     this.entries = entries;
/* 28 */     this.entrySet = (LongSet)new LongOpenHashSet();
/* 29 */     for (BlockFluidEntry entry : entries) {
/* 30 */       this.entrySet.add(MathUtil.packLong(entry.blockId(), entry.fluidId()));
/*    */     }
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BlockFluidEntry[] getEntries() {
/* 36 */     return this.entries;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public LongSet getEntrySet() {
/* 41 */     return this.entrySet;
/*    */   }
/*    */   
/*    */   public int size() {
/* 45 */     return this.entries.length;
/*    */   }
/*    */   
/*    */   public boolean contains(int block, int fluidId) {
/* 49 */     return this.entrySet.contains(MathUtil.packLong(block, fluidId));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 54 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 56 */     ResolvedBlockArray that = (ResolvedBlockArray)o;
/* 57 */     return (this.entrySet.equals(that.entrySet) && Arrays.deepEquals((Object[])this.entries, (Object[])that.entries));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     int result = this.entrySet.hashCode();
/* 63 */     result = 31 * result + Arrays.hashCode((Object[])this.entries);
/* 64 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "ResolvedBlockArray{entrySet=" + String.valueOf(this.entrySet) + ", entries=" + 
/*    */       
/* 72 */       Arrays.toString((Object[])this.entries) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\ResolvedBlockArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */