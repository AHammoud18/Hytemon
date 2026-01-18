/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.Axis;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EnumArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class RotateAxisVariant
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 58 */   private static final Message MESSAGE_BUILDER_TOOLS_ROTATE_ANGLE_USAGE = Message.translation("server.builderTools.rotate.angleUsage");
/*    */   
/*    */   @Nonnull
/* 61 */   private final RequiredArg<Integer> angleArg = withRequiredArg("angle", "server.commands.rotate.angle.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */   
/*    */   @Nonnull
/* 64 */   private final DefaultArg<Axis> axisArg = withDefaultArg("axis", "server.commands.rotate.axis.desc", (ArgumentType)new EnumArgumentType("server.commands.parsing.argtype.axis.name", Axis.class), Axis.Y, "Y");
/*    */ 
/*    */   
/*    */   RotateAxisVariant() {
/* 68 */     super("server.commands.rotate.axis.variant.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 77 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 78 */     assert playerComponent != null;
/*    */     
/* 80 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 82 */     int angle = ((Integer)this.angleArg.get(context)).intValue();
/* 83 */     Axis axis = (Axis)this.axisArg.get(context);
/*    */     
/* 85 */     if (angle % 90 != 0) {
/* 86 */       context.sendMessage(MESSAGE_BUILDER_TOOLS_ROTATE_ANGLE_USAGE);
/*    */       
/*    */       return;
/*    */     } 
/* 90 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.rotate(r, axis, angle, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\RotateCommand$RotateAxisVariant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */