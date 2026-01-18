/*    */ package com.hypixel.hytale.server.spawning.util;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.BitSetUtil;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import java.util.BitSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkColumnMask
/*    */ {
/*    */   public static final int COLUMNS = 1024;
/* 17 */   private final BitSet columns = new BitSet(1024);
/*    */   
/*    */   public void copyFrom(@Nonnull ChunkColumnMask src) {
/* 20 */     BitSetUtil.copyValues(src.columns, this.columns);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 24 */     return this.columns.isEmpty();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 28 */     this.columns.clear();
/*    */   }
/*    */   
/*    */   public void set() {
/* 32 */     this.columns.set(0, 1024);
/*    */   }
/*    */   
/*    */   public boolean get(int x, int z) {
/* 36 */     return this.columns.get(ChunkUtil.indexColumn(x, z));
/*    */   }
/*    */   
/*    */   public void set(int x, int z) {
/* 40 */     this.columns.set(ChunkUtil.indexColumn(x, z));
/*    */   }
/*    */   
/*    */   public void clear(int x, int z) {
/* 44 */     this.columns.clear(ChunkUtil.indexColumn(x, z));
/*    */   }
/*    */   
/*    */   public void clear(int index) {
/* 48 */     this.columns.clear(index & 0x3FF);
/*    */   }
/*    */   
/*    */   public void set(int x, int z, boolean value) {
/* 52 */     this.columns.set(ChunkUtil.indexColumn(x, z), value);
/*    */   }
/*    */   
/*    */   public boolean get(int index) {
/* 56 */     return this.columns.get(index & 0x3FF);
/*    */   }
/*    */   
/*    */   public void set(int bitIndex) {
/* 60 */     this.columns.set(bitIndex & 0x3FF);
/*    */   }
/*    */   
/*    */   public int nextSetBit(int fromIndex) {
/* 64 */     return this.columns.nextSetBit(fromIndex & 0x3FF);
/*    */   }
/*    */   
/*    */   public int nextClearBit(int fromIndex) {
/* 68 */     return this.columns.nextClearBit(fromIndex & 0x3FF);
/*    */   }
/*    */   
/*    */   public int previousSetBit(int fromIndex) {
/* 72 */     return this.columns.previousSetBit(fromIndex & 0x3FF);
/*    */   }
/*    */   
/*    */   public int previousClearBit(int fromIndex) {
/* 76 */     return this.columns.previousClearBit(fromIndex & 0x3FF);
/*    */   }
/*    */   
/*    */   public int cardinality() {
/* 80 */     return this.columns.cardinality();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawnin\\util\ChunkColumnMask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */