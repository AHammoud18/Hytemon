/*     */ package com.hypixel.hytale.builtin.adventure.camera.command;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraEffectCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   @Nonnull
/*  35 */   protected static final ArgumentType<CameraEffect> CAMERA_EFFECT_ARGUMENT_TYPE = (ArgumentType<CameraEffect>)new AssetArgumentType("CameraEffect", CameraEffect.class, "");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraEffectCommand() {
/*  41 */     super("camshake", "server.commands.camshake.desc");
/*  42 */     addSubCommand((AbstractCommand)new DamageCommand());
/*  43 */     addSubCommand((AbstractCommand)new DebugCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class DamageCommand
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  55 */     protected static final ArgumentType<DamageCause> DAMAGE_CAUSE_ARGUMENT_TYPE = (ArgumentType<DamageCause>)new AssetArgumentType("DamageCause", DamageCause.class, "");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  61 */     protected final OptionalArg<CameraEffect> effectArg = withOptionalArg("effect", "server.commands.camshake.effect.desc", CameraEffectCommand.CAMERA_EFFECT_ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  67 */     protected final RequiredArg<DamageCause> causeArg = withRequiredArg("cause", "server.commands.camshake.damage.cause.desc", DAMAGE_CAUSE_ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  73 */     protected final RequiredArg<Float> damageArg = withRequiredArg("amount", "server.commands.camshake.damage.amount.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DamageCommand() {
/*  79 */       super("damage", "server.commands.camshake.damage.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@NonNullDecl CommandContext context, @NullableDecl Ref<EntityStore> sourceRef, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world, @NonNullDecl Store<EntityStore> store) {
/*  84 */       DamageCause damageCause = (DamageCause)context.get((Argument)this.causeArg);
/*  85 */       float damageAmount = ((Float)context.get((Argument)this.damageArg)).floatValue();
/*     */       
/*  87 */       Damage.CommandSource damageSource = new Damage.CommandSource(context.sender(), getName());
/*  88 */       Damage damageEvent = new Damage((Damage.Source)damageSource, damageCause, damageAmount);
/*     */ 
/*     */       
/*  91 */       String cameraEffectId = "Default";
/*  92 */       if (this.effectArg.provided(context)) {
/*  93 */         cameraEffectId = ((CameraEffect)context.get((Argument)this.effectArg)).getId();
/*     */         
/*  95 */         Damage.CameraEffect damageEffect = new Damage.CameraEffect(CameraEffect.getAssetMap().getIndex(cameraEffectId));
/*  96 */         damageEvent.getMetaStore().putMetaObject(Damage.CAMERA_EFFECT, damageEffect);
/*     */       } 
/*     */       
/*  99 */       DamageSystems.executeDamage(ref, (ComponentAccessor)store, damageEvent);
/*     */       
/* 101 */       context.sendMessage(Message.translation("server.commands.camshake.damage.success")
/* 102 */           .param("effect", cameraEffectId)
/* 103 */           .param("cause", damageCause.getId())
/* 104 */           .param("amount", damageAmount));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class DebugCommand
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     private static final String MESSAGE_SUCCESS = "server.commands.camshake.debug.success";
/*     */ 
/*     */     
/*     */     @Nonnull
/* 118 */     protected final RequiredArg<CameraEffect> effectArg = withRequiredArg("effect", "server.commands.camshake.effect.desc", CameraEffectCommand.CAMERA_EFFECT_ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 124 */     protected final RequiredArg<Float> intensityArg = withRequiredArg("intensity", "server.commands.camshake.debug.intensity.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DebugCommand() {
/* 130 */       super("debug", "server.commands.camshake.debug.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@NonNullDecl CommandContext context, @NullableDecl Ref<EntityStore> sourceRef, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world, @NonNullDecl Store<EntityStore> store) {
/* 135 */       CameraEffect cameraEffect = (CameraEffect)context.get((Argument)this.effectArg);
/* 136 */       float intensity = ((Float)context.get((Argument)this.intensityArg)).floatValue();
/*     */       
/* 138 */       PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 139 */       assert playerRefComponent != null;
/*     */       
/* 141 */       playerRefComponent.getPacketHandler().writeNoCache((Packet)cameraEffect.createCameraShakePacket(intensity));
/*     */       
/* 143 */       context.sendMessage(Message.translation("server.commands.camshake.debug.success")
/* 144 */           .param("effect", cameraEffect.getId())
/* 145 */           .param("intensity", intensity));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\command\CameraEffectCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */