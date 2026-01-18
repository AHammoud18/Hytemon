/*     */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLocationMarkerAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarker;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddLocationMarkerCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  62 */   private final RequiredArg<String> locationMarkerArg = withRequiredArg("locationMarkerId", "server.commands.objective.locationMarker.add.arg.locationMarkerId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AddLocationMarkerCommand() {
/*  68 */     super("add", "server.commands.objective.locationMarker.add");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  79 */     Ref<EntityStore> playerReference = playerRef.getReference();
/*  80 */     TransformComponent playerTransformComponent = (TransformComponent)store.getComponent(playerReference, TransformComponent.getComponentType());
/*  81 */     assert playerTransformComponent != null;
/*     */     
/*  83 */     String objectiveLocationMarkerId = (String)this.locationMarkerArg.get(context);
/*  84 */     if (ObjectiveLocationMarkerAsset.getAssetMap().getAsset(objectiveLocationMarkerId) == null) {
/*  85 */       context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_NOT_FOUND.param("id", objectiveLocationMarkerId));
/*  86 */       context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_GENERAL_FAILED_DID_YOU_MEAN
/*  87 */           .param("choices", StringUtil.sortByFuzzyDistance(objectiveLocationMarkerId, ObjectiveLocationMarkerAsset.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*  92 */     holder.addComponent(ObjectiveLocationMarker.getComponentType(), (Component)new ObjectiveLocationMarker(objectiveLocationMarkerId));
/*     */     
/*  94 */     Model model = ObjectivePlugin.get().getObjectiveLocationMarkerModel();
/*  95 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*  96 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*  97 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(objectiveLocationMarkerId));
/*     */     
/*  99 */     TransformComponent transform = new TransformComponent(playerTransformComponent.getPosition(), playerTransformComponent.getRotation());
/* 100 */     holder.addComponent(TransformComponent.getComponentType(), (Component)transform);
/* 101 */     holder.ensureComponent(UUIDComponent.getComponentType());
/* 102 */     holder.ensureComponent(Intangible.getComponentType());
/* 103 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/* 104 */     store.addEntity(holder, AddReason.SPAWN);
/* 105 */     context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_ADDED
/* 106 */         .param("id", objectiveLocationMarkerId));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveLocationMarkerCommand$AddLocationMarkerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */