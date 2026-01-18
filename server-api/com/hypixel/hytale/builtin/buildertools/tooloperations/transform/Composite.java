/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations.transform;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Composite
/*    */   implements Transform {
/*    */   private final Transform first;
/*    */   private final Transform second;
/*    */   
/*    */   private Composite(Transform first, Transform second) {
/* 12 */     this.first = first;
/* 13 */     this.second = second;
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply(Vector3i vector3i) {
/* 18 */     this.first.apply(vector3i);
/* 19 */     this.second.apply(vector3i);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 25 */     return "Composite{first=" + String.valueOf(this.first) + ", second=" + String.valueOf(this.second) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Transform of(Transform first, Transform second) {
/* 43 */     if (first == NONE && second == NONE)
/* 44 */       return NONE; 
/* 45 */     if (first == NONE)
/* 46 */       return second; 
/* 47 */     if (second == NONE) {
/* 48 */       return first;
/*    */     }
/* 50 */     return new Composite(first, second);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\transform\Composite.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */