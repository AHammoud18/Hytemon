/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ import com.hypixel.hytale.builtin.path.path.TransientPathDefinition;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.IPath;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.SimplePathWaypoint;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionMakePath;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionMakePath extends ActionBase {
/*    */   @Nullable
/*    */   protected final TransientPathDefinition pathDefinition;
/*    */   
/*    */   public ActionMakePath(@Nonnull BuilderActionMakePath builder, @Nonnull BuilderSupport support) {
/* 25 */     super((BuilderActionBase)builder);
/* 26 */     this.pathDefinition = builder.getPath(support);
/*    */   }
/*    */   protected boolean built;
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 31 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && !this.built);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 36 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 38 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 39 */     assert transformComponent != null;
/*    */     
/* 41 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 42 */     assert headRotationComponent != null;
/*    */     
/* 44 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 45 */     assert npcComponent != null;
/*    */     
/* 47 */     IPath<SimplePathWaypoint> path = this.pathDefinition.buildPath(transformComponent.getPosition(), headRotationComponent.getRotation());
/* 48 */     npcComponent.getPathManager().setTransientPath(path);
/*    */     
/* 50 */     this.built = true;
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionMakePath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */