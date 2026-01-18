/*     */ package com.hypixel.hytale.builtin.adventure.camera.command;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*     */ public class DebugCommand
/*     */   extends AbstractTargetPlayerCommand
/*     */ {
/*     */   private static final String MESSAGE_SUCCESS = "server.commands.camshake.debug.success";
/*     */   @Nonnull
/* 118 */   protected final RequiredArg<CameraEffect> effectArg = withRequiredArg("effect", "server.commands.camshake.effect.desc", CameraEffectCommand.CAMERA_EFFECT_ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 124 */   protected final RequiredArg<Float> intensityArg = withRequiredArg("intensity", "server.commands.camshake.debug.intensity.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DebugCommand() {
/* 130 */     super("debug", "server.commands.camshake.debug.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@NonNullDecl CommandContext context, @NullableDecl Ref<EntityStore> sourceRef, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world, @NonNullDecl Store<EntityStore> store) {
/* 135 */     CameraEffect cameraEffect = (CameraEffect)context.get((Argument)this.effectArg);
/* 136 */     float intensity = ((Float)context.get((Argument)this.intensityArg)).floatValue();
/*     */     
/* 138 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 139 */     assert playerRefComponent != null;
/*     */     
/* 141 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)cameraEffect.createCameraShakePacket(intensity));
/*     */     
/* 143 */     context.sendMessage(Message.translation("server.commands.camshake.debug.success")
/* 144 */         .param("effect", cameraEffect.getId())
/* 145 */         .param("intensity", intensity));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\command\CameraEffectCommand$DebugCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */