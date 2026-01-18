/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.Axis;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EnumArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShiftCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 34 */   private final DefaultArg<Integer> distanceArg = withDefaultArg("distance", "server.commands.shift.distance.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "1");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final OptionalArg<Axis> axisArg = withOptionalArg("axis", "server.commands.shift.axis.desc", (ArgumentType)new EnumArgumentType("server.commands.parsing.argtype.axis.name", Axis.class));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShiftCommand() {
/* 46 */     super("shift", "server.commands.shift.desc");
/* 47 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*    */     Vector3i direction;
/* 56 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 57 */     assert playerComponent != null;
/*    */     
/* 59 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 61 */     Integer distance = (Integer)this.distanceArg.get(context);
/*    */     
/* 63 */     if (this.axisArg.provided(context)) {
/* 64 */       direction = ((Axis)this.axisArg.get(context)).getDirection().scale(distance.intValue());
/*    */     } else {
/* 66 */       HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 67 */       assert headRotationComponent != null;
/*    */       
/* 69 */       direction = headRotationComponent.getAxisDirection().scale(distance.intValue());
/*    */     } 
/*    */     
/* 72 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.shift(r, direction, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\ShiftCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */