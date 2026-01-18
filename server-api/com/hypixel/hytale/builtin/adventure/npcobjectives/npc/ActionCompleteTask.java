/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.NPCObjectivesPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.npc.builders.BuilderActionCompleteTask;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.ActionPlayAnimation;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionPlayAnimation;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionCompleteTask
/*    */   extends ActionPlayAnimation {
/*    */   public ActionCompleteTask(@Nonnull BuilderActionCompleteTask builder, @Nonnull BuilderSupport support) {
/* 22 */     super((BuilderActionPlayAnimation)builder, support);
/* 23 */     this.playAnimation = builder.isPlayAnimation(support);
/*    */   }
/*    */   protected final boolean playAnimation;
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 28 */     Ref<EntityStore> target = role.getStateSupport().getInteractionIterationTarget();
/* 29 */     boolean targetExists = (target != null && !store.getArchetype(target).contains(DeathComponent.getComponentType()));
/* 30 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && targetExists);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 35 */     UUIDComponent parentUuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 36 */     assert parentUuidComponent != null;
/*    */     
/* 38 */     Ref<EntityStore> targetPlayerReference = role.getStateSupport().getInteractionIterationTarget();
/* 39 */     if (targetPlayerReference == null) return false;
/*    */     
/* 41 */     PlayerRef targetPlayerRefComponent = (PlayerRef)store.getComponent(targetPlayerReference, PlayerRef.getComponentType());
/* 42 */     if (targetPlayerRefComponent == null) return false;
/*    */     
/* 44 */     List<String> activeTasks = role.getEntitySupport().getTargetPlayerActiveTasks();
/*    */     
/* 46 */     String animation = null;
/* 47 */     if (activeTasks != null) {
/* 48 */       for (int i = 0; i < activeTasks.size(); i++) {
/* 49 */         animation = NPCObjectivesPlugin.updateTaskCompletion(store, targetPlayerReference, targetPlayerRefComponent, parentUuidComponent.getUuid(), activeTasks.get(i));
/*    */       }
/*    */     }
/*    */     
/* 53 */     if (this.playAnimation && animation != null) {
/* 54 */       setAnimationId(animation);
/* 55 */       super.execute(ref, role, sensorInfo, dt, store);
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\npc\ActionCompleteTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */