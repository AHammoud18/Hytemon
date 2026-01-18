/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RotateArbitraryVariant
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 103 */   private final RequiredArg<Float> yawArg = withRequiredArg("yaw", "server.commands.rotate.yaw.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */   
/*     */   @Nonnull
/* 106 */   private final RequiredArg<Float> pitchArg = withRequiredArg("pitch", "server.commands.rotate.pitch.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */   
/*     */   @Nonnull
/* 109 */   private final RequiredArg<Float> rollArg = withRequiredArg("roll", "server.commands.rotate.roll.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */   
/*     */   RotateArbitraryVariant() {
/* 112 */     super("server.commands.rotate.arbitrary.variant.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 121 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 122 */     assert playerComponent != null;
/*     */     
/* 124 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/* 126 */     float yaw = ((Float)this.yawArg.get(context)).floatValue();
/* 127 */     float pitch = ((Float)this.pitchArg.get(context)).floatValue();
/* 128 */     float roll = ((Float)this.rollArg.get(context)).floatValue();
/*     */ 
/*     */     
/* 131 */     boolean isSimple90Degree = (yaw % 90.0F == 0.0F && pitch % 90.0F == 0.0F && roll % 90.0F == 0.0F);
/*     */     
/* 133 */     if (isSimple90Degree && pitch == 0.0F && roll == 0.0F) {
/*     */       
/* 135 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.rotate(r, Axis.Y, (int)yaw, componentAccessor));
/*     */     }
/*     */     else {
/*     */       
/* 139 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.rotateArbitrary(r, yaw, pitch, roll, componentAccessor));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\RotateCommand$RotateArbitraryVariant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */