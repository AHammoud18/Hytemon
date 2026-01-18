/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.task;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.task.CountObjectiveTask;
/*    */ import com.hypixel.hytale.builtin.tagset.TagSetPlugin;
/*    */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class KillObjectiveTask extends CountObjectiveTask implements KillTask {
/* 22 */   public static final BuilderCodec<KillObjectiveTask> CODEC = BuilderCodec.abstractBuilder(KillObjectiveTask.class, CountObjectiveTask.CODEC)
/* 23 */     .build();
/*    */   
/*    */   public KillObjectiveTask(@Nonnull KillObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/* 26 */     super((CountObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected KillObjectiveTask() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public KillObjectiveTaskAsset getAsset() {
/* 35 */     return (KillObjectiveTaskAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void checkKilledEntity(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef, @Nonnull Objective objective, @Nonnull NPCEntity npc, @Nonnull Damage info) {
/* 40 */     String key = getAsset().getNpcGroupId();
/* 41 */     int index = NPCGroup.getAssetMap().getIndex(key);
/*    */     
/* 43 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown npc group! " + key); 
/* 44 */     if (!TagSetPlugin.get(NPCGroup.class).tagInSet(index, npc.getNPCTypeIndex()))
/* 45 */       return;  if (!(info.getSource() instanceof Damage.EntitySource))
/*    */       return; 
/* 47 */     Ref<EntityStore> attackerEntityRef = ((Damage.EntitySource)info.getSource()).getRef();
/* 48 */     Entity attackerEntity = EntityUtils.getEntity(attackerEntityRef, (ComponentAccessor)attackerEntityRef.getStore());
/* 49 */     if (!(attackerEntity instanceof com.hypixel.hytale.server.core.entity.entities.Player))
/*    */       return; 
/* 51 */     UUIDComponent attackerUuidComponent = (UUIDComponent)store.getComponent(attackerEntityRef, UUIDComponent.getComponentType());
/* 52 */     assert attackerUuidComponent != null;
/*    */     
/* 54 */     if (!objective.getActivePlayerUUIDs().contains(attackerUuidComponent.getUuid()))
/*    */       return; 
/* 56 */     increaseTaskCompletion(store, npcRef, 1, objective);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\task\KillObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */