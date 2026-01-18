/*     */ package com.hypixel.hytale.builtin.path.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.WorldPathBuilder;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.path.WorldPath;
/*     */ import com.hypixel.hytale.server.core.universe.world.path.WorldPathConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldPathBuilderCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public WorldPathBuilderCommand() {
/*  40 */     super("builder", "server.commands.worldpath.builder.desc");
/*  41 */     addSubCommand((AbstractCommand)new WorldPathBuilderStopCommand());
/*  42 */     addSubCommand((AbstractCommand)new WorldPathBuilderLoadCommand());
/*  43 */     addSubCommand((AbstractCommand)new WorldPathBuilderSimulateCommand());
/*  44 */     addSubCommand((AbstractCommand)new WorldPathBuilderClearCommand());
/*  45 */     addSubCommand((AbstractCommand)new WorldPathBuilderAddCommand());
/*  46 */     addSubCommand((AbstractCommand)new WorldPathBuilderSetCommand());
/*  47 */     addSubCommand((AbstractCommand)new WorldPathBuilderGotoCommand());
/*  48 */     addSubCommand((AbstractCommand)new WorldPathBuilderRemoveCommand());
/*  49 */     addSubCommand((AbstractCommand)new WorldPathBuilderSaveCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderStopCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public WorldPathBuilderStopCommand() {
/*  61 */       super("stop", "server.commands.worldpath.builder.stop.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  70 */       WorldPathBuilderCommand.removeBuilder(ref, store);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderLoadCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  83 */     private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.worldpath.builder.load.name.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderLoadCommand() {
/*  89 */       super("load", "server.commands.worldpath.builder.load.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  98 */       String name = (String)this.nameArg.get(context);
/*  99 */       WorldPath worldPath = world.getWorldPathConfig().getPath(name);
/*     */       
/* 101 */       if (worldPath == null) {
/* 102 */         context.sendMessage(Message.translation("server.universe.worldpath.noPathFound")
/* 103 */             .param("path", name));
/*     */         
/*     */         return;
/*     */       } 
/* 107 */       WorldPathBuilderCommand.putBuilder(ref, store, WorldPathBuilderCommand.createBuilder(ref, store, worldPath));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderSimulateCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public WorldPathBuilderSimulateCommand() {
/* 120 */       super("simulate", "server.commands.worldpath.builder.simulate.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 129 */       WorldPathBuilder builder = WorldPathBuilderCommand.getBuilder(ref, store);
/* 130 */       if (builder == null)
/*     */         return; 
/* 132 */       ObjectArrayList<Transform> waypoints = new ObjectArrayList(builder.getPath().getWaypoints());
/* 133 */       CompletableFuture<Void> future = new CompletableFuture<>();
/* 134 */       ScheduledFuture[] arrayOfScheduledFuture = new ScheduledFuture[1];
/* 135 */       arrayOfScheduledFuture[0] = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> { Transform transform = (Transform)waypoints.removeFirst(); if (transform == null) { future.complete(null); scheduledFuture[0].cancel(false); } else { world.execute(()); }  }1L, 1L, TimeUnit.SECONDS);
/*     */     }
/*     */   }
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
/*     */   private static class WorldPathBuilderClearCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 152 */     private static final Message MESSAGE_UNIVERSE_WORLD_PATH_POINTS_CLEARED = Message.translation("server.universe.worldpath.pointsCleared");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderClearCommand() {
/* 158 */       super("clear", "server.commands.worldpath.builder.clear.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 167 */       WorldPathBuilder builder = WorldPathBuilderCommand.getBuilder(ref, store);
/* 168 */       if (builder == null)
/*     */         return; 
/* 170 */       builder.getPath().getWaypoints().clear();
/* 171 */       context.sendMessage(MESSAGE_UNIVERSE_WORLD_PATH_POINTS_CLEARED);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderAddCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 180 */     private static final Message MESSAGE_UNIVERSE_WORLD_PATH_POINT_ADDED = Message.translation("server.universe.worldpath.pointAdded");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderAddCommand() {
/* 186 */       super("add", "server.commands.worldpath.builder.add.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 195 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 196 */       assert transformComponent != null;
/*     */       
/* 198 */       Transform transform = transformComponent.getTransform().clone();
/* 199 */       WorldPathBuilderCommand.getOrCreateBuilder(ref, store).getPath().getWaypoints().add(transform);
/* 200 */       context.sendMessage(MESSAGE_UNIVERSE_WORLD_PATH_POINT_ADDED);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderSetCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 209 */     private static final Message MESSAGE_UNIVERSE_WORLD_PATH_POINT_SET = Message.translation("server.universe.worldpath.pointSet");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 215 */     private final OptionalArg<Integer> indexArg = withOptionalArg("index", "server.commands.worldpath.builder.set.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderSetCommand() {
/* 221 */       super("set", "server.commands.worldpath.builder.set.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 230 */       WorldPathBuilder builder = WorldPathBuilderCommand.getBuilder(ref, store);
/* 231 */       if (builder == null)
/*     */         return; 
/* 233 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 234 */       assert transformComponent != null;
/*     */       
/* 236 */       WorldPath worldPath = builder.getPath();
/* 237 */       int index = this.indexArg.provided(context) ? ((Integer)this.indexArg.get(context)).intValue() : (worldPath.getWaypoints().size() - 1);
/* 238 */       worldPath.getWaypoints().set(index, transformComponent.getTransform().clone());
/* 239 */       context.sendMessage(MESSAGE_UNIVERSE_WORLD_PATH_POINT_SET);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderGotoCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 252 */     private final RequiredArg<Integer> indexArg = withRequiredArg("index", "server.commands.worldpath.builder.goto.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderGotoCommand() {
/* 258 */       super("goto", "server.commands.worldpath.builder.goto.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 267 */       WorldPathBuilder builder = WorldPathBuilderCommand.getBuilder(ref, store);
/* 268 */       if (builder == null)
/*     */         return; 
/* 270 */       Integer index = (Integer)this.indexArg.get(context);
/* 271 */       WorldPath worldPath = builder.getPath();
/* 272 */       store.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(null, worldPath.getWaypoints().get(index.intValue())));
/* 273 */       context.sendMessage(Message.translation("server.universe.worldpath.teleportedToPoint")
/* 274 */           .param("index", index.intValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderRemoveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 287 */     private final RequiredArg<Integer> indexArg = withRequiredArg("index", "server.commands.worldpath.builder.remove.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderRemoveCommand() {
/* 293 */       super("remove", "server.commands.worldpath.builder.remove.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 302 */       WorldPathBuilder builder = WorldPathBuilderCommand.getBuilder(ref, store);
/* 303 */       if (builder == null)
/*     */         return; 
/* 305 */       int index = ((Integer)this.indexArg.get(context)).intValue();
/* 306 */       builder.getPath().getWaypoints().remove(index);
/* 307 */       context.sendMessage(Message.translation("server.universe.worldpath.removedIndex").param("index", index));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WorldPathBuilderSaveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 316 */     private static final Message MESSAGE_UNIVERSE_WORLD_PATH_NO_POINTS_DEFINED = Message.translation("server.universe.worldpath.noPointsDefined");
/*     */     @Nonnull
/* 318 */     private static final Message MESSAGE_UNIVERSE_WORLD_PATH_SAVED = Message.translation("server.universe.worldpath.saved");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 324 */     private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.worldpath.builder.save.name.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldPathBuilderSaveCommand() {
/* 330 */       super("save", "server.commands.worldpath.builder.save.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 339 */       String name = (String)this.nameArg.get(context);
/* 340 */       WorldPath path = WorldPathBuilderCommand.removeBuilder(ref, store);
/*     */       
/* 342 */       if (path == null || path.getWaypoints().isEmpty()) {
/* 343 */         context.sendMessage(MESSAGE_UNIVERSE_WORLD_PATH_NO_POINTS_DEFINED);
/*     */         
/*     */         return;
/*     */       } 
/* 347 */       WorldPathConfig worldPathConfig = world.getWorldPathConfig();
/* 348 */       WorldPath worldPath = new WorldPath(name, path.getWaypoints());
/* 349 */       worldPathConfig.putPath(worldPath);
/* 350 */       worldPathConfig.save(world);
/*     */       
/* 352 */       context.sendMessage(MESSAGE_UNIVERSE_WORLD_PATH_SAVED);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static WorldPathBuilder createBuilder(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nullable WorldPath existing) {
/* 366 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 367 */     assert uuidComponent != null;
/*     */     
/* 369 */     String name = "Builder-" + String.valueOf(uuidComponent.getUuid());
/* 370 */     WorldPathBuilder builder = new WorldPathBuilder();
/* 371 */     if (existing == null) {
/* 372 */       builder.setPath(new WorldPath(name, (List)new ObjectArrayList()));
/*     */     } else {
/* 374 */       builder.setPath(new WorldPath(name, (List)new ObjectArrayList(existing.getWaypoints())));
/*     */     } 
/* 376 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static WorldPathBuilder getBuilder(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 388 */     return (WorldPathBuilder)store.getComponent(ref, WorldPathBuilder.getComponentType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static WorldPathBuilder getOrCreateBuilder(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 400 */     WorldPathBuilder builder = (WorldPathBuilder)store.getComponent(ref, WorldPathBuilder.getComponentType());
/* 401 */     if (builder != null) return builder;
/*     */     
/* 403 */     return putBuilder(ref, store, createBuilder(ref, store, (WorldPath)null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static WorldPath removeBuilder(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 415 */     WorldPathBuilder worldPath = (WorldPathBuilder)store.getComponent(ref, WorldPathBuilder.getComponentType());
/* 416 */     if (worldPath != null) {
/* 417 */       store.removeComponent(ref, WorldPathBuilder.getComponentType());
/* 418 */       return worldPath.getPath();
/*     */     } 
/*     */     
/* 421 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static WorldPathBuilder putBuilder(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WorldPathBuilder builder) {
/* 434 */     store.putComponent(ref, WorldPathBuilder.getComponentType(), (Component)builder);
/* 435 */     return builder;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\WorldPathBuilderCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */