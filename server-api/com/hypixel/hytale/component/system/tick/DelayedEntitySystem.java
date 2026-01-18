/*    */ package com.hypixel.hytale.component.system.tick;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DelayedEntitySystem<ECS_TYPE>
/*    */   extends EntityTickingSystem<ECS_TYPE>
/*    */ {
/* 14 */   private final ResourceType<ECS_TYPE, Data<ECS_TYPE>> resourceType = registerResource(Data.class, Data::new);
/*    */   private final float intervalSec;
/*    */   
/*    */   public DelayedEntitySystem(float intervalSec) {
/* 18 */     this.intervalSec = intervalSec;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ResourceType<ECS_TYPE, Data<ECS_TYPE>> getResourceType() {
/* 23 */     return this.resourceType;
/*    */   }
/*    */   
/*    */   public float getIntervalSec() {
/* 27 */     return this.intervalSec;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ECS_TYPE> store) {
/* 32 */     Data<ECS_TYPE> data = (Data<ECS_TYPE>)store.getResource(this.resourceType);
/*    */     
/* 34 */     data.dt += dt;
/* 35 */     if (data.dt >= this.intervalSec) {
/* 36 */       float fullDt = data.dt;
/* 37 */       data.dt = 0.0F;
/*    */       
/* 39 */       super.tick(fullDt, systemIndex, store);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static class Data<ECS_TYPE>
/*    */     implements Resource<ECS_TYPE> {
/*    */     private float dt;
/*    */     
/*    */     @Nonnull
/*    */     public Resource<ECS_TYPE> clone() {
/* 49 */       Data<ECS_TYPE> data = new Data();
/* 50 */       data.dt = this.dt;
/* 51 */       return data;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\DelayedEntitySystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */