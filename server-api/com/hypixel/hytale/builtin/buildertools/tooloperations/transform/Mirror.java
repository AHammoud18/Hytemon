/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations.transform;
/*    */ 
/*    */ import com.hypixel.hytale.math.Axis;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BrushAxis;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Mirror
/*    */   implements Transform {
/* 10 */   public static final Transform X = new Mirror(Axis.X);
/* 11 */   public static final Transform Y = new Mirror(Axis.Y);
/* 12 */   public static final Transform Z = new Mirror(Axis.Z);
/*    */   private final Axis axis;
/*    */   
/*    */   private Mirror(Axis axis) {
/* 16 */     this.axis = axis;
/*    */   }
/*    */   
/*    */   public void apply(@Nonnull Vector3i vector3i) {
/* 20 */     this.axis.flip(vector3i);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 26 */     return "Mirror{axis=" + String.valueOf(this.axis) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Transform forAxis(BrushAxis axis) {
/* 32 */     if (axis == BrushAxis.X)
/* 33 */       return X; 
/* 34 */     if (axis == BrushAxis.Y)
/* 35 */       return Y; 
/* 36 */     if (axis == BrushAxis.Z) {
/* 37 */       return Z;
/*    */     }
/* 39 */     return NONE;
/*    */   }
/*    */   
/*    */   public static Transform forDirection(@Nonnull Vector3i direction) {
/* 43 */     return forDirection(direction, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Transform forDirection(@Nonnull Vector3i direction, boolean negativeY) {
/* 54 */     if (direction.getX() != 0)
/* 55 */       return X; 
/* 56 */     if (direction.getZ() != 0)
/* 57 */       return Z; 
/* 58 */     if (direction.getY() > 0)
/* 59 */       return Y; 
/* 60 */     if (direction.getY() < 0 && negativeY) {
/* 61 */       return Y;
/*    */     }
/* 63 */     return NONE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\transform\Mirror.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */