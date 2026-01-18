/*    */ package com.hypixel.hytale.server.core.prefab.selection.mask;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MultiBlockMask
/*    */   extends BlockMask
/*    */ {
/*    */   private static final String BLOCK_MASK_SEPARATOR = ";";
/*    */   private final BlockMask[] masks;
/*    */   
/*    */   public MultiBlockMask(BlockMask[] masks) {
/* 14 */     super(BlockFilter.EMPTY_ARRAY);
/* 15 */     this.masks = masks;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExcluded(@Nonnull ChunkAccessor accessor, int x, int y, int z, Vector3i min, Vector3i max, int blockId) {
/* 20 */     return isExcluded(accessor, x, y, z, min, max, blockId, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExcluded(@Nonnull ChunkAccessor accessor, int x, int y, int z, Vector3i min, Vector3i max, int blockId, int fluidId) {
/* 25 */     boolean excluded = false;
/* 26 */     for (BlockMask mask : this.masks) {
/* 27 */       if (mask.isExcluded(accessor, x, y, z, min, max, blockId, fluidId)) {
/* 28 */         excluded = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 32 */     return (isInverted() != excluded);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 38 */     if (this.masks.length == 0) return "-"; 
/* 39 */     String base = joinElements(";", (Object[])this.masks);
/* 40 */     return isInverted() ? ("!" + base) : base;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String informativeToString() {
/* 46 */     if (this.masks.length == 0) return "-"; 
/* 47 */     StringBuilder builder = new StringBuilder();
/* 48 */     if (isInverted()) builder.append("NOT("); 
/* 49 */     for (int i = 0; i < this.masks.length; i++) {
/* 50 */       BlockMask mask = this.masks[i];
/* 51 */       builder.append(mask.informativeToString());
/* 52 */       if (i != this.masks.length - 1) builder.append(" AND "); 
/*    */     } 
/* 54 */     if (isInverted()) builder.append(")"); 
/* 55 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\mask\MultiBlockMask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */