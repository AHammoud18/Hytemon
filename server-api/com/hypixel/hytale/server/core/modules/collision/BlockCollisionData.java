/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.BlockMaterial;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCollisionData
/*    */   extends BoxCollisionData
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public int z;
/*    */   public int blockId;
/*    */   public int rotation;
/*    */   @Nullable
/*    */   public BlockType blockType;
/*    */   @Nullable
/*    */   public BlockMaterial blockMaterial;
/*    */   public int detailBoxIndex;
/*    */   public boolean willDamage;
/*    */   public int fluidId;
/*    */   @Nullable
/*    */   public Fluid fluid;
/*    */   public boolean touching;
/*    */   public boolean overlapping;
/*    */   
/*    */   public void setBlockData(@Nonnull CollisionConfig collisionConfig) {
/* 35 */     this.x = collisionConfig.blockX;
/* 36 */     this.y = collisionConfig.blockY;
/* 37 */     this.z = collisionConfig.blockZ;
/* 38 */     this.blockId = collisionConfig.blockId;
/* 39 */     this.rotation = collisionConfig.rotation;
/* 40 */     this.blockType = collisionConfig.blockType;
/* 41 */     this.blockMaterial = collisionConfig.blockMaterial;
/* 42 */     this.willDamage = ((collisionConfig.blockMaterialMask & 0x10) != 0);
/*    */     
/* 44 */     this.fluidId = collisionConfig.fluidId;
/* 45 */     this.fluid = collisionConfig.fluid;
/*    */   }
/*    */   
/*    */   public void setDetailBoxIndex(int detailBoxIndex) {
/* 49 */     this.detailBoxIndex = detailBoxIndex;
/*    */   }
/*    */   
/*    */   public void setTouchingOverlapping(boolean touching, boolean overlapping) {
/* 53 */     this.touching = touching;
/* 54 */     this.overlapping = overlapping;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 58 */     this.blockType = null;
/* 59 */     this.blockMaterial = null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 65 */     return "BlockCollisionData{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", blockId=" + this.blockId + ", blockType=" + String.valueOf(this.blockType) + ", blockMaterial=" + String.valueOf(this.blockMaterial) + ", collisionPoint=" + String.valueOf(this.collisionPoint) + ", collisionNormal=" + String.valueOf(this.collisionNormal) + ", collisionStart=" + this.collisionStart + ", collisionEnd=" + this.collisionEnd + ", detailBoxIndex=" + this.detailBoxIndex + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BlockCollisionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */