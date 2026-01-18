/*    */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation.ReachLocationMarker;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation.ReachLocationMarkerAsset;
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ public class AddReachLocationMarkerCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 54 */   private final RequiredArg<String> reachLocationMarkerArg = withRequiredArg("reachLocationMarkerId", "server.commands.objective.reachLocationMarker.add.arg.reachLocationMarkerId.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AddReachLocationMarkerCommand() {
/* 60 */     super("add", "server.commands.objective.reachLocationMarker.add");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 71 */     String reachLocationMarkerId = (String)this.reachLocationMarkerArg.get(context);
/* 72 */     if (ReachLocationMarkerAsset.getAssetMap().getAsset(reachLocationMarkerId) == null) {
/* 73 */       context.sendMessage(ObjectiveReachLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_REACH_LOCATION_MARKER_NOT_FOUND.param("id", reachLocationMarkerId));
/* 74 */       context.sendMessage(ObjectiveReachLocationMarkerCommand.MESSAGE_GENERAL_FAILED_DID_YOU_MEAN
/* 75 */           .param("choices", StringUtil.sortByFuzzyDistance(reachLocationMarkerId, ReachLocationMarkerAsset.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 79 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 80 */     holder.addComponent(ReachLocationMarker.getComponentType(), (Component)new ReachLocationMarker(reachLocationMarkerId));
/*    */     
/* 82 */     Model model = ObjectivePlugin.get().getObjectiveLocationMarkerModel();
/* 83 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 84 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/* 85 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(reachLocationMarkerId));
/*    */     
/* 87 */     TransformComponent playerTransformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 88 */     assert playerTransformComponent != null;
/*    */     
/* 90 */     TransformComponent transform = new TransformComponent(playerTransformComponent.getPosition(), playerTransformComponent.getRotation());
/* 91 */     holder.addComponent(TransformComponent.getComponentType(), (Component)transform);
/* 92 */     holder.ensureComponent(UUIDComponent.getComponentType());
/* 93 */     holder.ensureComponent(Intangible.getComponentType());
/* 94 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/* 95 */     store.addEntity(holder, AddReason.SPAWN);
/* 96 */     context.sendMessage(ObjectiveReachLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_REACH_LOCATION_MARKER_ADDED.param("id", reachLocationMarkerId));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveReachLocationMarkerCommand$AddReachLocationMarkerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */