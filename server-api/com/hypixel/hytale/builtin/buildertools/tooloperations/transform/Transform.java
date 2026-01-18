/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations.transform;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ 
/*    */ public interface Transform {
/*    */   public static final Transform NONE = vec -> {
/*    */     
/*    */     };
/*    */   
/*    */   default Transform then(Transform next) {
/* 11 */     return Composite.of(this, next);
/*    */   }
/*    */   
/*    */   void apply(Vector3i paramVector3i);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\transform\Transform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */