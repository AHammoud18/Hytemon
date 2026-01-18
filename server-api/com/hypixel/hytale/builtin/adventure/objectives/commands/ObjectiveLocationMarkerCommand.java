/*     */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLocationMarkerAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarker;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ObjectiveLocationMarkerCommand extends AbstractCommandCollection {
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_NOT_FOUND = Message.translation("server.commands.objective.locationMarker.notFound");
/*     */   @Nonnull
/*  35 */   private static final Message MESSAGE_GENERAL_FAILED_DID_YOU_MEAN = Message.translation("server.general.failed.didYouMean");
/*     */   @Nonnull
/*  37 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_ADDED = Message.translation("server.commands.objective.locationMarker.added");
/*     */   @Nonnull
/*  39 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_ENABLED = Message.translation("server.commands.objective.locationMarker.enabled");
/*     */   @Nonnull
/*  41 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_DISABLED = Message.translation("server.commands.objective.locationMarker.disabled");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectiveLocationMarkerCommand() {
/*  47 */     super("locationmarker", "server.commands.objective.locationMarker");
/*  48 */     addAliases(new String[] { "marker" });
/*  49 */     addSubCommand((AbstractCommand)new AddLocationMarkerCommand());
/*  50 */     addSubCommand((AbstractCommand)new EnableLocationMarkerCommand());
/*  51 */     addSubCommand((AbstractCommand)new DisableLocationMarkerCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AddLocationMarkerCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  62 */     private final RequiredArg<String> locationMarkerArg = withRequiredArg("locationMarkerId", "server.commands.objective.locationMarker.add.arg.locationMarkerId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AddLocationMarkerCommand() {
/*  68 */       super("add", "server.commands.objective.locationMarker.add");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  79 */       Ref<EntityStore> playerReference = playerRef.getReference();
/*  80 */       TransformComponent playerTransformComponent = (TransformComponent)store.getComponent(playerReference, TransformComponent.getComponentType());
/*  81 */       assert playerTransformComponent != null;
/*     */       
/*  83 */       String objectiveLocationMarkerId = (String)this.locationMarkerArg.get(context);
/*  84 */       if (ObjectiveLocationMarkerAsset.getAssetMap().getAsset(objectiveLocationMarkerId) == null) {
/*  85 */         context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_NOT_FOUND.param("id", objectiveLocationMarkerId));
/*  86 */         context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_GENERAL_FAILED_DID_YOU_MEAN
/*  87 */             .param("choices", StringUtil.sortByFuzzyDistance(objectiveLocationMarkerId, ObjectiveLocationMarkerAsset.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*  92 */       holder.addComponent(ObjectiveLocationMarker.getComponentType(), (Component)new ObjectiveLocationMarker(objectiveLocationMarkerId));
/*     */       
/*  94 */       Model model = ObjectivePlugin.get().getObjectiveLocationMarkerModel();
/*  95 */       holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*  96 */       holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*  97 */       holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(objectiveLocationMarkerId));
/*     */       
/*  99 */       TransformComponent transform = new TransformComponent(playerTransformComponent.getPosition(), playerTransformComponent.getRotation());
/* 100 */       holder.addComponent(TransformComponent.getComponentType(), (Component)transform);
/* 101 */       holder.ensureComponent(UUIDComponent.getComponentType());
/* 102 */       holder.ensureComponent(Intangible.getComponentType());
/* 103 */       holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/* 104 */       store.addEntity(holder, AddReason.SPAWN);
/* 105 */       context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_ADDED
/* 106 */           .param("id", objectiveLocationMarkerId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EnableLocationMarkerCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public EnableLocationMarkerCommand() {
/* 118 */       super("enable", "server.commands.objective.locationMarker.enable");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 123 */       WorldConfig worldConfig = world.getWorldConfig();
/* 124 */       worldConfig.setObjectiveMarkersEnabled(true);
/* 125 */       worldConfig.markChanged();
/* 126 */       context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_ENABLED
/* 127 */           .param("worldName", world.getName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DisableLocationMarkerCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public DisableLocationMarkerCommand() {
/* 139 */       super("disable", "server.commands.objective.locationMarker.disable");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 144 */       WorldConfig worldConfig = world.getWorldConfig();
/* 145 */       worldConfig.setObjectiveMarkersEnabled(false);
/* 146 */       worldConfig.markChanged();
/* 147 */       context.sendMessage(ObjectiveLocationMarkerCommand.MESSAGE_COMMANDS_OBJECTIVE_LOCATION_MARKER_DISABLED
/* 148 */           .param("worldName", world.getName()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveLocationMarkerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */