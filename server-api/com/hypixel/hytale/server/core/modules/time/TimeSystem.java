/*    */ package com.hypixel.hytale.server.core.modules.time;
/*    */ 
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.temporal.ChronoUnit;
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
/*    */ public class TimeSystem
/*    */   extends TickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ResourceType<EntityStore, TimeResource> timeResourceType;
/*    */   
/*    */   public TimeSystem(@Nonnull ResourceType<EntityStore, TimeResource> timeResourceType) {
/* 30 */     this.timeResourceType = timeResourceType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 36 */     long nanos = (long)(1.0E9F * dt);
/* 37 */     ((TimeResource)store.getResource(this.timeResourceType)).add(nanos, ChronoUnit.NANOS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\TimeSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */