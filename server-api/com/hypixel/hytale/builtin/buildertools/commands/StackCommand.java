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
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
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
/*     */ public class StackCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  35 */   private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.stack.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  41 */   private final OptionalArg<Integer> spacingArg = withOptionalArg("spacing", "server.commands.stack.spacing.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackCommand() {
/*  47 */     super("stack", "server.commands.stack.desc");
/*  48 */     setPermissionGroup(GameMode.Creative);
/*  49 */     addUsageVariant((AbstractCommand)new StackWithCountCommand());
/*  50 */     addUsageVariant((AbstractCommand)new StackWithDirectionAndCountCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  59 */     executeStack(store, ref, (RelativeDirection)null, 1, ((Boolean)this.emptyFlag.get(context)).booleanValue(), this.spacingArg.provided(context) ? ((Integer)this.spacingArg.get(context)).intValue() : 0);
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
/*     */   private static void executeStack(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nullable RelativeDirection direction, int count, boolean empty, int spacing) {
/*  78 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  79 */     assert playerComponent != null;
/*     */     
/*  81 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*  82 */     assert playerRefComponent != null;
/*     */     
/*  84 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/*  86 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  87 */     assert headRotationComponent != null;
/*     */     
/*  89 */     Vector3i directionVector = RelativeDirection.toDirectionVector(direction, headRotationComponent);
/*     */     
/*  91 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRefComponent, (r, s, componentAccessor) -> s.stack(r, directionVector, count, empty, spacing, componentAccessor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StackWithCountCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 104 */     private final RequiredArg<Integer> countArg = withRequiredArg("count", "server.commands.stack.count.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 110 */     private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.stack.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 116 */     private final OptionalArg<Integer> spacingArg = withOptionalArg("spacing", "server.commands.stack.spacing.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StackWithCountCommand() {
/* 122 */       super("server.commands.stack.desc");
/* 123 */       setPermissionGroup(GameMode.Creative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 132 */       StackCommand.executeStack(store, ref, (RelativeDirection)null, ((Integer)this.countArg
/*     */ 
/*     */           
/* 135 */           .get(context)).intValue(), ((Boolean)this.emptyFlag
/* 136 */           .get(context)).booleanValue(), 
/* 137 */           this.spacingArg.provided(context) ? ((Integer)this.spacingArg.get(context)).intValue() : 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StackWithDirectionAndCountCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 150 */     private final RequiredArg<RelativeDirection> directionArg = withRequiredArg("direction", "server.commands.stack.direction.desc", (ArgumentType)RelativeDirection.ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 156 */     private final RequiredArg<Integer> countArg = withRequiredArg("count", "server.commands.stack.count.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 162 */     private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.stack.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 168 */     private final OptionalArg<Integer> spacingArg = withOptionalArg("spacing", "server.commands.stack.spacing.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StackWithDirectionAndCountCommand() {
/* 174 */       super("server.commands.stack.desc");
/* 175 */       setPermissionGroup(GameMode.Creative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 184 */       StackCommand.executeStack(store, ref, (RelativeDirection)this.directionArg
/*     */           
/* 186 */           .get(context), ((Integer)this.countArg
/* 187 */           .get(context)).intValue(), ((Boolean)this.emptyFlag
/* 188 */           .get(context)).booleanValue(), 
/* 189 */           this.spacingArg.provided(context) ? ((Integer)this.spacingArg.get(context)).intValue() : 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\StackCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */