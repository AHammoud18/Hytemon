/*    */ package com.hypixel.hytale.server.worldgen.cave.element;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*    */ import com.hypixel.hytale.server.worldgen.util.bounds.WorldBounds;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CavePrefab
/*    */   implements CaveElement
/*    */ {
/*    */   @Nonnull
/*    */   private final WorldGenPrefabSupplier prefabSupplier;
/*    */   @Nonnull
/*    */   private final PrefabRotation rotation;
/*    */   private final IIntCondition biomeMask;
/*    */   private final BlockMaskCondition blockMask;
/*    */   @Nonnull
/*    */   private final IWorldBounds bounds;
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final int z;
/*    */   
/*    */   public CavePrefab(@Nonnull WorldGenPrefabSupplier prefabSupplier, @Nonnull PrefabRotation rotation, IIntCondition biomeMask, BlockMaskCondition blockMask, int x, int y, int z) {
/* 29 */     this.prefabSupplier = prefabSupplier;
/* 30 */     this.rotation = rotation;
/* 31 */     this.biomeMask = biomeMask;
/* 32 */     this.blockMask = blockMask;
/* 33 */     this.x = x;
/* 34 */     this.y = y;
/* 35 */     this.z = z;
/*    */     
/* 37 */     IPrefabBuffer prefab = prefabSupplier.get();
/* 38 */     this
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 44 */       .bounds = (IWorldBounds)new WorldBounds(MathUtil.floor((x + prefab.getMinX(rotation))), MathUtil.floor((y + prefab.getMinY())), MathUtil.floor((z + prefab.getMinZ(rotation))), MathUtil.ceil((x + prefab.getMaxX(rotation))), MathUtil.ceil((y + prefab.getMaxY())), MathUtil.ceil((z + prefab.getMaxZ(rotation))));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldGenPrefabSupplier getPrefab() {
/* 50 */     return this.prefabSupplier;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PrefabRotation getRotation() {
/* 55 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public IIntCondition getBiomeMask() {
/* 59 */     return this.biomeMask;
/*    */   }
/*    */   
/*    */   public BlockMaskCondition getConfiguration() {
/* 63 */     return this.blockMask;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 67 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 71 */     return this.y;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 75 */     return this.z;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IWorldBounds getBounds() {
/* 81 */     return this.bounds;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\element\CavePrefab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */