/*     */ package com.hypixel.hytale.server.spawning.commands;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnsuppression.SpawnSuppression;
/*     */ import com.hypixel.hytale.server.spawning.suppression.SpawnSuppressorEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionComponent;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SpawnSuppressionCommand extends AbstractCommandCollection {
/*     */   @Nonnull
/*  41 */   private static final AssetArgumentType<SpawnSuppression, ?> SPAWN_SUPPRESSION_ASSET_TYPE = new AssetArgumentType("server.commands.spawning.suppression.arg.suppression.name", SpawnSuppression.class, "server.commands.spawning.suppression.arg.suppression.usage");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnSuppressionCommand() {
/*  49 */     super("suppression", "server.commands.spawning.suppression.desc");
/*  50 */     addSubCommand((AbstractCommand)new Dump());
/*  51 */     addSubCommand((AbstractCommand)new DumpAll());
/*  52 */     addSubCommand((AbstractCommand)new Add());
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Dump
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/*  60 */     private static final Message MESSAGE_COMMANDS_SPAWNING_SUPPRESSION_DUMP_DUMPED = Message.translation("server.commands.spawning.suppression.dump.dumped");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dump() {
/*  66 */       super("dump", "server.commands.spawning.suppression.dump.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  71 */       String dump = SpawnSuppressionCommand.dumpWorld(world);
/*  72 */       context.sendMessage(MESSAGE_COMMANDS_SPAWNING_SUPPRESSION_DUMP_DUMPED);
/*  73 */       ((HytaleLogger.Api)SpawningPlugin.get().getLogger().atInfo()).log(dump);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DumpAll
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/*  82 */     private static final Message MESSAGE_COMMANDS_SPAWNING_SUPPRESSION_DUMP_DUMPED = Message.translation("server.commands.spawning.suppression.dump.dumped");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DumpAll() {
/*  88 */       super("dumpall", "server.commands.spawning.suppression.dump.all.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  93 */       Universe.get().getWorlds().values().forEach(w -> ((HytaleLogger.Api)SpawningPlugin.get().getLogger().atInfo()).log(SpawnSuppressionCommand.dumpWorld(w)));
/*  94 */       context.sendMessage(MESSAGE_COMMANDS_SPAWNING_SUPPRESSION_DUMP_DUMPED);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String dumpWorld(@Nonnull World world) {
/* 100 */     SpawnSuppressionController spawnSuppressionController = (SpawnSuppressionController)world.getEntityStore().getStore().getResource(SpawnSuppressionController.getResourceType());
/* 101 */     StringBuilder sb = (new StringBuilder("World: ")).append(world.getName()).append("\n  Spawn Suppressors:");
/*     */     
/* 103 */     spawnSuppressionController.getSpawnSuppressorMap().values().forEach(entry -> sb.append("\n    ").append(entry.getSuppressionId()).append(": ").append(entry.getPosition()));
/*     */ 
/*     */ 
/*     */     
/* 107 */     sb.append("\n  Chunk Annotations:");
/*     */     
/* 109 */     spawnSuppressionController.getChunkSuppressionMap().forEach((index, entry) -> {
/*     */           sb.append("\n    Chunk ").append(index).append(": ");
/*     */ 
/*     */ 
/*     */           
/*     */           List<ChunkSuppressionEntry.SuppressionSpan> suppressionSpans = entry.getSuppressionSpans();
/*     */ 
/*     */ 
/*     */           
/*     */           suppressionSpans.forEach(());
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 123 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Add
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 135 */     private final RequiredArg<SpawnSuppression> suppressionArg = withRequiredArg("suppression", "server.commands.spawning.suppression.add.arg.suppression.desc", (ArgumentType)SpawnSuppressionCommand.SPAWN_SUPPRESSION_ASSET_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Add() {
/* 141 */       super("add", "server.commands.spawning.suppression.add.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 146 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 147 */       assert transformComponent != null;
/*     */       
/* 149 */       SpawnSuppression spawnSuppression = (SpawnSuppression)this.suppressionArg.get(context);
/* 150 */       Vector3f rotation = transformComponent.getRotation();
/* 151 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */       
/* 153 */       holder.addComponent(SpawnSuppressionComponent.getComponentType(), (Component)new SpawnSuppressionComponent(spawnSuppression.getId()));
/* 154 */       holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(transformComponent.getPosition(), rotation));
/* 155 */       holder.ensureComponent(UUIDComponent.getComponentType());
/* 156 */       holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*     */ 
/*     */       
/* 159 */       Model model = SpawningPlugin.get().getSpawnMarkerModel();
/* 160 */       holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 161 */       holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*     */       
/* 163 */       Nameplate nameplate = new Nameplate("SpawnSuppression: " + String.valueOf(spawnSuppression));
/* 164 */       holder.addComponent(Nameplate.getComponentType(), (Component)nameplate);
/* 165 */       store.addEntity(holder, AddReason.SPAWN);
/*     */       
/* 167 */       context.sendMessage(Message.translation("server.commands.spawning.suppression.add.added")
/* 168 */           .param("suppressionId", spawnSuppression.getId()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnSuppressionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */