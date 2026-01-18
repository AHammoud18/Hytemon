/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations.transform;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Translate
/*    */   implements Transform {
/*    */   private final int x;
/*    */   
/*    */   private Translate(int x, int y, int z) {
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.z = z;
/*    */   }
/*    */   private final int y; private final int z;
/*    */   
/*    */   public void apply(@Nonnull Vector3i vector3i) {
/* 18 */     vector3i.add(this.x, this.y, this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 24 */     return "Translate{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Transform of(@Nonnull Vector3i vector) {
/* 33 */     return of(vector.getX(), vector.getY(), vector.getZ());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Transform of(int x, int y, int z) {
/* 38 */     if (x == 0 && y == 0 && z == 0) {
/* 39 */       return NONE;
/*    */     }
/* 41 */     return new Translate(x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\transform\Translate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */