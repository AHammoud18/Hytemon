/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
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
/*     */ public class FlipCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public FlipCommand() {
/*  31 */     super("flip", "server.commands.flip.desc");
/*  32 */     setPermissionGroup(GameMode.Creative);
/*  33 */     addUsageVariant((AbstractCommand)new FlipWithDirectionCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  42 */     executeFlip(store, ref, playerRef, (RelativeDirection)null);
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
/*     */   private static void executeFlip(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nullable RelativeDirection direction) {
/*     */     Axis axis;
/*  57 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  58 */     assert playerComponent != null;
/*     */     
/*  60 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/*  62 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  63 */     if (headRotationComponent == null) {
/*     */       return;
/*     */     }
/*  66 */     if (direction != null) {
/*  67 */       axis = RelativeDirection.toAxis(direction, headRotationComponent);
/*     */     } else {
/*  69 */       axis = headRotationComponent.getAxis();
/*     */     } 
/*     */     
/*  72 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.flip(r, axis, componentAccessor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FlipWithDirectionCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  85 */     private final RequiredArg<RelativeDirection> directionArg = withRequiredArg("direction", "server.commands.flip.direction.desc", (ArgumentType)RelativeDirection.ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FlipWithDirectionCommand() {
/*  91 */       super("server.commands.flip.desc");
/*  92 */       setPermissionGroup(GameMode.Creative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 101 */       FlipCommand.executeFlip(store, ref, playerRef, (RelativeDirection)this.directionArg.get(context));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\FlipCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */