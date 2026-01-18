/*    */ package com.hypixel.hytale.builtin.beds.sleep.systems.world;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.components.PlayerSleep;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.components.PlayerSomnolence;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.resources.WorldSleep;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.resources.WorldSlumber;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.resources.WorldSomnolence;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class UpdateWorldSlumberSystem extends TickingSystem<EntityStore> {
/*    */   public void tick(float dt, int systemIndex, @NonNullDecl Store<EntityStore> store) {
/*    */     WorldSlumber slumber;
/* 25 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 27 */     WorldSomnolence worldSomnolence = (WorldSomnolence)store.getResource(WorldSomnolence.getResourceType());
/* 28 */     WorldSleep worldSleep = worldSomnolence.getState();
/*    */     
/* 30 */     if (worldSleep instanceof WorldSlumber) { slumber = (WorldSlumber)worldSleep; }
/*    */     else
/*    */     { return; }
/*    */     
/* 34 */     slumber.incProgressSeconds(dt);
/* 35 */     boolean sleepingIsOver = (slumber.getProgressSeconds() >= slumber.getIrlDurationSeconds() || isSomeoneAwake((ComponentAccessor<EntityStore>)store));
/* 36 */     if (!sleepingIsOver) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     worldSomnolence.setState((WorldSleep)WorldSleep.Awake.INSTANCE);
/*    */     
/* 42 */     WorldTimeResource timeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/* 43 */     Instant wakeUpTime = computeWakeupTime(slumber);
/* 44 */     timeResource.setGameTime(wakeUpTime, world, store);
/*    */     
/* 46 */     store.forEachEntityParallel((Query)PlayerSomnolence.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*    */           PlayerSomnolence somnolence = (PlayerSomnolence)archetypeChunk.getComponent(index, PlayerSomnolence.getComponentType());
/*    */           if (somnolence.getSleepState() instanceof PlayerSleep.Slumber) {
/*    */             Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */             commandBuffer.putComponent(ref, PlayerSomnolence.getComponentType(), (Component)PlayerSleep.MorningWakeUp.createComponent(timeResource));
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   private static Instant computeWakeupTime(WorldSlumber slumber) {
/* 56 */     float progress = slumber.getProgressSeconds() / slumber.getIrlDurationSeconds();
/* 57 */     long totalNanos = Duration.between(slumber.getStartInstant(), slumber.getTargetInstant()).toNanos();
/* 58 */     long progressNanos = (long)((float)totalNanos * progress);
/*    */     
/* 60 */     return slumber.getStartInstant().plusNanos(progressNanos);
/*    */   }
/*    */   
/*    */   private static boolean isSomeoneAwake(ComponentAccessor<EntityStore> store) {
/* 64 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 65 */     Collection<PlayerRef> playerRefs = world.getPlayerRefs();
/* 66 */     if (playerRefs.isEmpty()) return false;
/*    */     
/* 68 */     Iterator<PlayerRef> iterator = playerRefs.iterator(); if (iterator.hasNext()) { PlayerRef playerRef = iterator.next();
/* 69 */       PlayerSomnolence somnolence = (PlayerSomnolence)store.getComponent(playerRef.getReference(), PlayerSomnolence.getComponentType());
/* 70 */       if (somnolence == null) return true; 
/* 71 */       PlayerSleep sleepState = somnolence.getSleepState();
/* 72 */       return sleepState instanceof PlayerSleep.FullyAwake; }
/*    */     
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\systems\world\UpdateWorldSlumberSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */