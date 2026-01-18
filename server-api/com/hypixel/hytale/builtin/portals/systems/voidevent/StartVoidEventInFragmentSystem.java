/*    */ package com.hypixel.hytale.builtin.portals.systems.voidevent;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidEvent;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.DelayedSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class StartVoidEventInFragmentSystem extends DelayedSystem<EntityStore> {
/*    */   public StartVoidEventInFragmentSystem() {
/* 18 */     super(1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void delayedTick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 23 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 24 */     if (!portalWorld.exists()) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     if (!portalWorld.getPortalType().isVoidInvasionEnabled()) {
/*    */       return;
/*    */     }
/*    */     
/* 32 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 34 */     VoidEventConfig voidEventConfig = portalWorld.getVoidEventConfig();
/*    */     
/* 36 */     int timeLimitSeconds = portalWorld.getTimeLimitSeconds();
/* 37 */     int shouldStartAfter = voidEventConfig.getShouldStartAfterSeconds(timeLimitSeconds);
/*    */     
/* 39 */     int elapsedSeconds = (int)Math.ceil(portalWorld.getElapsedSeconds(world));
/*    */     
/* 41 */     Ref<EntityStore> voidEventRef = portalWorld.getVoidEventRef();
/* 42 */     boolean exists = (voidEventRef != null);
/* 43 */     boolean shouldExist = (elapsedSeconds >= shouldStartAfter);
/*    */     
/* 45 */     if (exists && !shouldExist) {
/* 46 */       store.removeEntity(voidEventRef, RemoveReason.REMOVE);
/*    */     }
/* 48 */     if (shouldExist && !exists) {
/* 49 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 50 */       holder.addComponent(VoidEvent.getComponentType(), (Component)new VoidEvent());
/* 51 */       Ref<EntityStore> spawnedEventRef = store.addEntity(holder, AddReason.SPAWN);
/*    */       
/* 53 */       portalWorld.setVoidEventRef(spawnedEventRef);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\voidevent\StartVoidEventInFragmentSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */