/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeDirection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MoveCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  34 */   private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.move.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  40 */   private final FlagArg entitiesFlag = withFlagArg("entities", "server.commands.move.entities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MoveCommand() {
/*  46 */     super("move", "server.commands.move.desc");
/*  47 */     setPermissionGroup(GameMode.Creative);
/*  48 */     addUsageVariant((AbstractCommand)new MoveWithDistanceCommand());
/*  49 */     addUsageVariant((AbstractCommand)new MoveWithDirectionAndDistanceCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  58 */     executeMove(store, ref, playerRef, (RelativeDirection)null, 1, ((Boolean)this.emptyFlag.get(context)).booleanValue(), ((Boolean)this.entitiesFlag.get(context)).booleanValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void executeMove(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nullable RelativeDirection direction, int distance, boolean empty, boolean entities) {
/*  79 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  80 */     assert playerComponent != null;
/*     */     
/*  82 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/*  84 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  85 */     assert headRotationComponent != null;
/*     */     
/*  87 */     Vector3i directionVector = RelativeDirection.toDirectionVector(direction, headRotationComponent).scale(distance);
/*     */     
/*  89 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.move(r, directionVector, empty, entities, componentAccessor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MoveWithDistanceCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 102 */     private final RequiredArg<Integer> distanceArg = withRequiredArg("distance", "server.commands.move.distance.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 108 */     private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.move.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 114 */     private final FlagArg entitiesFlag = withFlagArg("entities", "server.commands.move.entities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MoveWithDistanceCommand() {
/* 120 */       super("server.commands.move.desc");
/* 121 */       setPermissionGroup(GameMode.Creative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 130 */       MoveCommand.executeMove(store, ref, playerRef, (RelativeDirection)null, ((Integer)this.distanceArg
/*     */ 
/*     */ 
/*     */           
/* 134 */           .get(context)).intValue(), ((Boolean)this.emptyFlag
/* 135 */           .get(context)).booleanValue(), ((Boolean)this.entitiesFlag
/* 136 */           .get(context)).booleanValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MoveWithDirectionAndDistanceCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 149 */     private final RequiredArg<RelativeDirection> directionArg = withRequiredArg("direction", "server.commands.move.direction.desc", (ArgumentType)RelativeDirection.ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 155 */     private final RequiredArg<Integer> distanceArg = withRequiredArg("distance", "server.commands.move.distance.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 161 */     private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.move.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 167 */     private final FlagArg entitiesFlag = withFlagArg("entities", "server.commands.move.entities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MoveWithDirectionAndDistanceCommand() {
/* 173 */       super("server.commands.move.desc");
/* 174 */       setPermissionGroup(GameMode.Creative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 183 */       MoveCommand.executeMove(store, ref, playerRef, (RelativeDirection)this.directionArg
/*     */ 
/*     */           
/* 186 */           .get(context), ((Integer)this.distanceArg
/* 187 */           .get(context)).intValue(), ((Boolean)this.emptyFlag
/* 188 */           .get(context)).booleanValue(), ((Boolean)this.entitiesFlag
/* 189 */           .get(context)).booleanValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\MoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */