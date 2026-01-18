/*     */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  38 */   private final OptionalArg<Integer> spawnIndexArg = withOptionalArg("spawnIndex", "server.commands.spawn.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnCommand() {
/*  44 */     super("spawn", "server.commands.spawn.desc");
/*  45 */     requirePermission(HytalePermissions.fromCommand("spawn.self"));
/*  46 */     addUsageVariant((AbstractCommand)new SpawnOtherCommand());
/*  47 */     addSubCommand((AbstractCommand)new SpawnSetCommand());
/*  48 */     addSubCommand((AbstractCommand)new SpawnSetDefaultCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  57 */     Transform spawn = resolveSpawn(context, world, playerRef, this.spawnIndexArg);
/*     */     
/*  59 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  60 */     assert transformComponent != null;
/*     */     
/*  62 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  63 */     assert headRotationComponent != null;
/*     */     
/*  65 */     Vector3f previousBodyRotation = transformComponent.getRotation().clone();
/*  66 */     Vector3d previousPos = transformComponent.getPosition().clone();
/*  67 */     Vector3f previousRotation = headRotationComponent.getRotation().clone();
/*     */     
/*  69 */     TeleportHistory teleportHistoryComponent = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/*  70 */     teleportHistoryComponent.append(world, previousPos, previousRotation, "World " + world
/*  71 */         .getName() + "'s spawn");
/*     */ 
/*     */     
/*  74 */     Vector3f spawnRotation = spawn.getRotation().clone();
/*  75 */     spawn.setRotation(new Vector3f(previousBodyRotation.getPitch(), spawnRotation.getYaw(), previousBodyRotation.getRoll()));
/*     */     
/*  77 */     Teleport teleport = (new Teleport(world, spawn)).withHeadRotation(spawnRotation);
/*  78 */     store.addComponent(ref, Teleport.getComponentType(), (Component)teleport);
/*     */     
/*  80 */     Vector3d position = spawn.getPosition();
/*  81 */     context.sendMessage(Message.translation("server.commands.spawn.teleported")
/*  82 */         .param("x", position.getX())
/*  83 */         .param("y", position.getY())
/*  84 */         .param("z", position.getZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Transform resolveSpawn(@Nonnull CommandContext context, @Nonnull World world, @Nonnull PlayerRef playerRef, @Nonnull OptionalArg<Integer> spawnIndexArg) {
/*  94 */     ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
/*     */     
/*  96 */     if (spawnIndexArg.provided(context)) {
/*  97 */       int spawnIndex = ((Integer)spawnIndexArg.get(context)).intValue();
/*  98 */       Transform[] spawnPoints = spawnProvider.getSpawnPoints();
/*  99 */       if (spawnIndex < 0 || spawnIndex >= spawnPoints.length) {
/* 100 */         int maxIndex = spawnPoints.length - 1;
/* 101 */         context.sendMessage(Message.translation("server.commands.spawn.indexNotFound")
/* 102 */             .param("maxIndex", maxIndex));
/* 103 */         throw new GeneralCommandException(Message.translation("server.commands.errors.spawnIndexOutOfRange")
/* 104 */             .param("index", spawnIndex)
/* 105 */             .param("maxIndex", maxIndex));
/*     */       } 
/* 107 */       return spawnPoints[spawnIndex];
/*     */     } 
/* 109 */     return spawnProvider.getSpawnPoint(world, playerRef.getUuid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SpawnOtherCommand
/*     */     extends CommandBase
/*     */   {
/* 118 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 124 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 130 */     private final OptionalArg<Integer> spawnIndexArg = withOptionalArg("spawnIndex", "server.commands.spawn.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SpawnOtherCommand() {
/* 136 */       super("server.commands.spawn.other.desc");
/* 137 */       requirePermission(HytalePermissions.fromCommand("spawn.other"));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 142 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 143 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */       
/* 145 */       if (ref == null || !ref.isValid()) {
/* 146 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */         
/*     */         return;
/*     */       } 
/* 150 */       Store<EntityStore> store = ref.getStore();
/* 151 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 153 */       world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */               return;
/*     */             } 
/*     */             PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */             assert playerRefComponent != null;
/*     */             Transform spawn = SpawnCommand.resolveSpawn(context, world, targetPlayerRef, this.spawnIndexArg);
/*     */             TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*     */             assert transformComponent != null;
/*     */             HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*     */             assert headRotationComponent != null;
/*     */             Vector3f previousBodyRotation = transformComponent.getRotation().clone();
/*     */             Vector3d previousPos = transformComponent.getPosition().clone();
/*     */             Vector3f previousRotation = headRotationComponent.getRotation().clone();
/*     */             TeleportHistory teleportHistoryComponent = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/*     */             teleportHistoryComponent.append(world, previousPos, previousRotation, "World " + world.getName() + "'s spawn");
/*     */             Vector3f spawnRotation = spawn.getRotation().clone();
/*     */             spawn.setRotation(new Vector3f(previousBodyRotation.getPitch(), spawnRotation.getYaw(), previousBodyRotation.getRoll()));
/*     */             Teleport teleport = (new Teleport(world, spawn)).withHeadRotation(spawnRotation);
/*     */             store.addComponent(ref, Teleport.getComponentType(), (Component)teleport);
/*     */             Vector3d position = spawn.getPosition();
/*     */             context.sendMessage(Message.translation("server.commands.spawn.teleportedOther").param("username", targetPlayerRef.getUsername()).param("x", position.getX()).param("y", position.getY()).param("z", position.getZ()));
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\SpawnCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */