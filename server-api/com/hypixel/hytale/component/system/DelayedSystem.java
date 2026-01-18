/*    */ package com.hypixel.hytale.component.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*    */ import java.util.function.Supplier;
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
/*    */ public abstract class DelayedSystem<ECS_TYPE>
/*    */   extends TickingSystem<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/* 22 */   private final ResourceType<ECS_TYPE, Data<ECS_TYPE>> resourceType = registerResource(Data.class, Data::new);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final float intervalSec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DelayedSystem(float intervalSec) {
/* 35 */     this.intervalSec = intervalSec;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResourceType<ECS_TYPE, Data<ECS_TYPE>> getResourceType() {
/* 43 */     return this.resourceType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getIntervalSec() {
/* 50 */     return this.intervalSec;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ECS_TYPE> store) {
/* 55 */     Data<ECS_TYPE> data = (Data<ECS_TYPE>)store.getResource(this.resourceType);
/*    */     
/* 57 */     data.dt += dt;
/* 58 */     if (data.dt >= this.intervalSec) {
/* 59 */       float fullDeltaTime = data.dt;
/* 60 */       data.dt = 0.0F;
/* 61 */       delayedTick(fullDeltaTime, systemIndex, store);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void delayedTick(float paramFloat, int paramInt, @Nonnull Store<ECS_TYPE> paramStore);
/*    */ 
/*    */ 
/*    */   
/*    */   private static class Data<ECS_TYPE>
/*    */     implements Resource<ECS_TYPE>
/*    */   {
/*    */     private float dt;
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Resource<ECS_TYPE> clone() {
/* 80 */       Data<ECS_TYPE> data = new Data();
/* 81 */       data.dt = this.dt;
/* 82 */       return data;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\DelayedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */