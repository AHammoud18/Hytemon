/*    */ package com.hypixel.hytale.server.core.universe.world.chunk;
/*    */ import com.hypixel.hytale.math.Axis;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFlipType;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.VariantRotation;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BlockRotationUtil {
/*    */   @Nullable
/*    */   public static RotationTuple getFlipped(@Nonnull RotationTuple blockRotation, @Nullable BlockFlipType flipType, @Nonnull Axis axis, @Nonnull VariantRotation variantRotation) {
/* 13 */     Rotation rotationYaw = blockRotation.yaw();
/* 14 */     Rotation rotationPitch = blockRotation.pitch();
/* 15 */     Rotation rotationRoll = blockRotation.roll();
/*    */     
/* 17 */     if (flipType != null) {
/* 18 */       rotationYaw = flipType.flipYaw(rotationYaw, axis);
/*    */     }
/*    */     
/* 21 */     boolean preventPitchRotation = (axis != Axis.Y);
/* 22 */     return get(rotationYaw, rotationPitch, rotationRoll, axis, Rotation.OneEighty, variantRotation, preventPitchRotation);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static RotationTuple getRotated(@Nonnull RotationTuple blockRotation, @Nonnull Axis axis, Rotation rotation, @Nonnull VariantRotation variantRotation) {
/* 27 */     return get(blockRotation.yaw(), blockRotation.pitch(), blockRotation.roll(), axis, rotation, variantRotation, false);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private static RotationTuple get(@Nonnull Rotation rotationYaw, @Nonnull Rotation rotationPitch, @Nonnull Rotation rotationRoll, @Nonnull Axis axis, Rotation rotation, @Nonnull VariantRotation variantRotation, boolean preventPitchRotation) {
/* 33 */     RotationTuple rotateX, rotateZ, rotationPair = null;
/* 34 */     switch (axis) {
/*    */       case X:
/* 36 */         rotateX = variantRotation.rotateX(RotationTuple.of(rotationYaw, rotationPitch), rotation);
/* 37 */         rotationPair = variantRotation.verify(rotateX);
/*    */         break;
/*    */       case Y:
/* 40 */         rotationPair = variantRotation.verify(RotationTuple.of(rotationYaw.add(rotation), rotationPitch));
/*    */         break;
/*    */       case Z:
/* 43 */         rotateZ = variantRotation.rotateZ(RotationTuple.of(rotationYaw, rotationPitch), rotation);
/* 44 */         rotationPair = variantRotation.verify(rotateZ);
/*    */         break;
/*    */     } 
/*    */ 
/*    */     
/* 49 */     if (rotationPair == null) return null;
/*    */ 
/*    */     
/* 52 */     if (preventPitchRotation) {
/* 53 */       rotationPair = RotationTuple.of(rotationPair.yaw(), rotationPitch);
/*    */     }
/*    */     
/* 56 */     return rotationPair;
/*    */   }
/*    */   
/*    */   public static int getFlippedFiller(int filler, @Nonnull Axis axis) {
/* 60 */     return getRotatedFiller(filler, axis, Rotation.OneEighty);
/*    */   }
/*    */   
/*    */   public static int getRotatedFiller(int filler, @Nonnull Axis axis, Rotation rotation) {
/* 64 */     switch (axis) { default: throw new MatchException(null, null);case X: case Y: case Z: break; }  return 
/*    */ 
/*    */       
/* 67 */       rotation.rotateZ(filler);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\BlockRotationUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */