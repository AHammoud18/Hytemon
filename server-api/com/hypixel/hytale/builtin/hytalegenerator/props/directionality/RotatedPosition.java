/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class RotatedPosition
/*    */ {
/*    */   public final int x;
/*    */   public final int y;
/*    */   
/*    */   public RotatedPosition(int x, int y, int z, @Nonnull PrefabRotation rotation) {
/* 14 */     this.x = x;
/* 15 */     this.y = y;
/* 16 */     this.z = z;
/* 17 */     this.rotation = rotation;
/*    */   } public final int z; @Nonnull
/*    */   public final PrefabRotation rotation;
/*    */   @Nonnull
/*    */   public RotatedPosition getRelativeTo(@Nonnull RotatedPosition other) {
/* 22 */     Vector3i vec = new Vector3i(this.x, this.y, this.z);
/* 23 */     other.rotation.rotate(vec);
/* 24 */     int x = vec.x + other.x;
/* 25 */     int y = vec.y + other.y;
/* 26 */     int z = vec.z + other.z;
/* 27 */     PrefabRotation rotation = this.rotation.add(other.rotation);
/* 28 */     return new RotatedPosition(x, y, z, rotation);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i toVector3i() {
/* 33 */     return new Vector3i(this.x, this.y, this.z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\directionality\RotatedPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */