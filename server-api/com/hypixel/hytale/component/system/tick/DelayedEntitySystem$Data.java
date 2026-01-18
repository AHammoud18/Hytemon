/*    */ package com.hypixel.hytale.component.system.tick;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Data<ECS_TYPE>
/*    */   implements Resource<ECS_TYPE>
/*    */ {
/*    */   private float dt;
/*    */   
/*    */   @Nonnull
/*    */   public Resource<ECS_TYPE> clone() {
/* 49 */     Data<ECS_TYPE> data = new Data();
/* 50 */     data.dt = this.dt;
/* 51 */     return data;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\DelayedEntitySystem$Data.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */