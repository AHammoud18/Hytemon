/*     */ package com.hypixel.hytale.server.spawning.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
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
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SpawnMarkersCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   @Nonnull
/*  39 */   private static final AssetArgumentType<SpawnMarker, ?> SPAWN_MARKER_ASSET_TYPE = new AssetArgumentType("server.commands.spawning.markers.arg.marker.name", SpawnMarker.class, "server.commands.spawning.markers.arg.marker.usage");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnMarkersCommand() {
/*  47 */     super("markers", "server.commands.spawning.markers.desc");
/*  48 */     addSubCommand((AbstractCommand)new EnableCommand());
/*  49 */     addSubCommand((AbstractCommand)new DisableCommand());
/*  50 */     addSubCommand((AbstractCommand)new Add());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EnableCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public EnableCommand() {
/*  62 */       super("enable", "server.commands.spawning.markers.enable.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  67 */       WorldConfig worldConfig = world.getWorldConfig();
/*  68 */       worldConfig.setIsSpawnMarkersEnabled(true);
/*  69 */       worldConfig.markChanged();
/*  70 */       context.sendMessage(Message.translation("server.commands.spawning.markers.enabled").param("worldName", world.getName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DisableCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public DisableCommand() {
/*  84 */       super("disable", "server.commands.spawning.markers.disable.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  89 */       WorldConfig worldConfig = world.getWorldConfig();
/*  90 */       worldConfig.setIsSpawnMarkersEnabled(false);
/*  91 */       worldConfig.markChanged();
/*  92 */       context.sendMessage(Message.translation("server.commands.spawning.markers.disabled")
/*  93 */           .param("worldName", world.getName()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Add
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 102 */     private static final Message COMMANDS_ERRORS_PLAYER_ONLY = Message.translation("server.commands.errors.playerOnly");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 108 */     private final RequiredArg<SpawnMarker> markerArg = withRequiredArg("marker", "server.commands.spawning.markers.add.arg.marker.desc", (ArgumentType)SpawnMarkersCommand.SPAWN_MARKER_ASSET_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 114 */     private final FlagArg flipArg = withFlagArg("flip", "server.commands.spawning.markers.add.flip.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Add() {
/* 120 */       super("add", "server.commands.spawning.markers.add.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 125 */       if (!context.isPlayer()) {
/* 126 */         throw new GeneralCommandException(COMMANDS_ERRORS_PLAYER_ONLY);
/*     */       }
/*     */       
/* 129 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 130 */       assert transformComponent != null;
/*     */       
/* 132 */       SpawnMarker marker = (SpawnMarker)this.markerArg.get(context);
/* 133 */       SpawnMarkerEntity spawnMarker = new SpawnMarkerEntity();
/* 134 */       spawnMarker.setSpawnMarker(marker);
/*     */       
/* 136 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 137 */       holder.addComponent(SpawnMarkerEntity.getComponentType(), (Component)spawnMarker);
/*     */       
/* 139 */       TransformComponent spawnerTransformComponent = transformComponent.clone();
/*     */       
/* 141 */       if (((Boolean)this.flipArg.get(context)).booleanValue()) {
/* 142 */         spawnerTransformComponent.getRotation().flipRotationOnAxis(Axis.Y);
/*     */       }
/*     */       
/* 145 */       holder.addComponent(TransformComponent.getComponentType(), (Component)spawnerTransformComponent);
/* 146 */       holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(marker.getId()));
/* 147 */       holder.ensureComponent(UUIDComponent.getComponentType());
/* 148 */       holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*     */       
/* 150 */       Model model = SpawnMarkerEntity.getModel(marker);
/* 151 */       holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 152 */       holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*     */       
/* 154 */       Ref<EntityStore> spawnMarkerRef = store.addEntity(holder, AddReason.SPAWN);
/* 155 */       if (spawnMarkerRef == null || !spawnMarkerRef.isValid()) {
/* 156 */         context.sendMessage(Message.translation("server.commands.markers.add.failed")
/* 157 */             .param("markerId", marker.getId()));
/*     */         
/*     */         return;
/*     */       } 
/* 161 */       context.sendMessage(Message.translation("server.commands.spawning.markers.add.added")
/* 162 */           .param("markerId", marker.getId()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnMarkersCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */