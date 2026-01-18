/*     */ package com.hypixel.hytale.server.spawning.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*     */ import com.hypixel.hytale.server.spawning.beacons.SpawnBeacon;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
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
/*     */ class Add
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  63 */   private final RequiredArg<BeaconNPCSpawn> beaconArg = withRequiredArg("beacon", "server.commands.spawning.beacons.add.arg.beacon.desc", (ArgumentType)SpawnBeaconsCommand.BEACON_SPAWN_ASSET_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  69 */   private final FlagArg manualArg = withFlagArg("manual", "server.commands.spawning.beacons.add.manual.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Add() {
/*  75 */     super("add", "server.commands.spawning.beacons.add.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  80 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  81 */     assert transformComponent != null;
/*     */     
/*  83 */     Vector3f rotation = transformComponent.getRotation();
/*  84 */     Vector3d position = transformComponent.getPosition();
/*  85 */     BeaconNPCSpawn beacon = (BeaconNPCSpawn)this.beaconArg.get(context);
/*  86 */     BeaconSpawnWrapper wrapper = SpawningPlugin.get().getBeaconSpawnWrapper(BeaconNPCSpawn.getAssetMap().getIndex(beacon.getId()));
/*     */     
/*  88 */     if (((Boolean)this.manualArg.get(context)).booleanValue()) {
/*     */       Model model;
/*  90 */       SpawnBeacon entity = new SpawnBeacon();
/*  91 */       entity.setSpawnWrapper(wrapper);
/*     */       
/*  93 */       BeaconNPCSpawn spawn = (BeaconNPCSpawn)wrapper.getSpawn();
/*     */       
/*  95 */       String modelName = spawn.getModel();
/*  96 */       ModelAsset modelAsset = null;
/*  97 */       if (modelName != null && !modelName.isEmpty()) {
/*  98 */         modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(modelName);
/*     */       }
/*     */       
/* 101 */       if (modelAsset == null) {
/* 102 */         model = SpawningPlugin.get().getSpawnMarkerModel();
/*     */       } else {
/* 104 */         model = Model.createUnitScaleModel(modelAsset);
/*     */       } 
/*     */       
/* 107 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 108 */       holder.addComponent(SpawnBeacon.getComponentType(), (Component)entity);
/* 109 */       holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position, rotation));
/* 110 */       holder.ensureComponent(UUIDComponent.getComponentType());
/* 111 */       holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 112 */       holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*     */       
/* 114 */       Message displayNameMessage = Message.raw(spawn.getId());
/* 115 */       holder.addComponent(DisplayNameComponent.getComponentType(), (Component)new DisplayNameComponent(displayNameMessage));
/* 116 */       holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(spawn.getId()));
/*     */       
/* 118 */       store.addEntity(holder, AddReason.SPAWN);
/*     */     } else {
/* 120 */       LegacySpawnBeaconEntity.create(wrapper, position, rotation, (ComponentAccessor)store);
/*     */     } 
/*     */     
/* 123 */     context.sendMessage(Message.translation("server.commands.spawning.beacons.add.added")
/* 124 */         .param("beaconId", beacon.getId()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnBeaconsCommand$Add.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */