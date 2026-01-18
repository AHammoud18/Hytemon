/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations.transform;
/*    */ 
/*    */ import com.hypixel.hytale.math.Axis;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.Rotation;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BrushAxis;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Rotate
/*    */   implements Transform {
/* 11 */   public static final Transform X_90 = new Rotate(Axis.X, 90);
/* 12 */   public static final Transform X_180 = new Rotate(Axis.X, 180);
/* 13 */   public static final Transform X_270 = new Rotate(Axis.X, 270);
/* 14 */   public static final Transform Y_90 = new Rotate(Axis.Y, 90);
/* 15 */   public static final Transform Y_180 = new Rotate(Axis.Y, 180);
/* 16 */   public static final Transform Y_270 = new Rotate(Axis.Y, 270);
/* 17 */   public static final Transform Z_90 = new Rotate(Axis.Z, 90);
/* 18 */   public static final Transform Z_180 = new Rotate(Axis.Z, 180);
/* 19 */   public static final Transform Z_270 = new Rotate(Axis.Z, 270);
/*    */   
/* 21 */   public static final Transform FACING_NORTH = X_90;
/* 22 */   public static final Transform FACING_EAST = Z_90;
/* 23 */   public static final Transform FACING_SOUTH = X_90.then(Y_180);
/* 24 */   public static final Transform FACING_WEST = Z_90.then(Y_180);
/*    */   
/*    */   private final Axis axis;
/*    */   private final int rotations;
/*    */   
/*    */   public Rotate(Axis axis) {
/* 30 */     this(axis, 90);
/*    */   }
/*    */   
/*    */   public Rotate(Axis axis, int angle) {
/* 34 */     angle = Math.floorMod(angle, 360);
/* 35 */     int rotations = angle / 90;
/* 36 */     this.axis = axis;
/* 37 */     this.rotations = rotations;
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply(@Nonnull Vector3i vector3i) {
/* 42 */     if (this.rotations == 1) {
/* 43 */       this.axis.rotate(vector3i);
/* 44 */     } else if (this.rotations > 1) {
/* 45 */       for (int i = 0; i < this.rotations; i++) {
/* 46 */         this.axis.rotate(vector3i);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 54 */     return "Rotate{axis=" + String.valueOf(this.axis) + ", rotations=" + this.rotations + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Transform forDirection(@Nonnull Vector3i direction, Rotation angle) {
/* 61 */     if (direction.getX() < 0)
/* 62 */       return selectRotation(angle, FACING_WEST, FACING_NORTH, FACING_EAST, FACING_SOUTH); 
/* 63 */     if (direction.getX() > 0)
/* 64 */       return selectRotation(angle, FACING_EAST, FACING_SOUTH, FACING_WEST, FACING_NORTH); 
/* 65 */     if (direction.getZ() < 0)
/* 66 */       return selectRotation(angle, FACING_NORTH, FACING_EAST, FACING_SOUTH, FACING_WEST); 
/* 67 */     if (direction.getZ() > 0) {
/* 68 */       return selectRotation(angle, FACING_SOUTH, FACING_WEST, FACING_NORTH, FACING_EAST);
/*    */     }
/* 70 */     return NONE;
/*    */   }
/*    */   
/*    */   public static Transform forAxisAndAngle(BrushAxis axis, Rotation angle) {
/* 74 */     if (axis == BrushAxis.X)
/* 75 */       return selectRotation(angle, NONE, X_90, X_180, X_270); 
/* 76 */     if (axis == BrushAxis.Y)
/* 77 */       return selectRotation(angle, NONE, Y_90, Y_180, Y_270); 
/* 78 */     if (axis == BrushAxis.Z) {
/* 79 */       return selectRotation(angle, NONE, Z_90, Z_180, Z_270);
/*    */     }
/* 81 */     return NONE;
/*    */   }
/*    */   
/*    */   private static Transform selectRotation(Rotation angle, Transform rotate0, Transform rotate90, Transform rotate180, Transform rotate270) {
/* 85 */     if (angle == Rotation.Ninety)
/* 86 */       return rotate90; 
/* 87 */     if (angle == Rotation.OneEighty)
/* 88 */       return rotate180; 
/* 89 */     if (angle == Rotation.TwoSeventy) {
/* 90 */       return rotate270;
/*    */     }
/* 92 */     return rotate0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\transform\Rotate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */