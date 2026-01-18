/*     */ package com.hypixel.hytale.builtin.adventure.camera.command;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
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
/*     */ public class DamageCommand
/*     */   extends AbstractTargetPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  55 */   protected static final ArgumentType<DamageCause> DAMAGE_CAUSE_ARGUMENT_TYPE = (ArgumentType<DamageCause>)new AssetArgumentType("DamageCause", DamageCause.class, "");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  61 */   protected final OptionalArg<CameraEffect> effectArg = withOptionalArg("effect", "server.commands.camshake.effect.desc", CameraEffectCommand.CAMERA_EFFECT_ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  67 */   protected final RequiredArg<DamageCause> causeArg = withRequiredArg("cause", "server.commands.camshake.damage.cause.desc", DAMAGE_CAUSE_ARGUMENT_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  73 */   protected final RequiredArg<Float> damageArg = withRequiredArg("amount", "server.commands.camshake.damage.amount.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageCommand() {
/*  79 */     super("damage", "server.commands.camshake.damage.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@NonNullDecl CommandContext context, @NullableDecl Ref<EntityStore> sourceRef, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world, @NonNullDecl Store<EntityStore> store) {
/*  84 */     DamageCause damageCause = (DamageCause)context.get((Argument)this.causeArg);
/*  85 */     float damageAmount = ((Float)context.get((Argument)this.damageArg)).floatValue();
/*     */     
/*  87 */     Damage.CommandSource damageSource = new Damage.CommandSource(context.sender(), getName());
/*  88 */     Damage damageEvent = new Damage((Damage.Source)damageSource, damageCause, damageAmount);
/*     */ 
/*     */     
/*  91 */     String cameraEffectId = "Default";
/*  92 */     if (this.effectArg.provided(context)) {
/*  93 */       cameraEffectId = ((CameraEffect)context.get((Argument)this.effectArg)).getId();
/*     */       
/*  95 */       Damage.CameraEffect damageEffect = new Damage.CameraEffect(CameraEffect.getAssetMap().getIndex(cameraEffectId));
/*  96 */       damageEvent.getMetaStore().putMetaObject(Damage.CAMERA_EFFECT, damageEffect);
/*     */     } 
/*     */     
/*  99 */     DamageSystems.executeDamage(ref, (ComponentAccessor)store, damageEvent);
/*     */     
/* 101 */     context.sendMessage(Message.translation("server.commands.camshake.damage.success")
/* 102 */         .param("effect", cameraEffectId)
/* 103 */         .param("cause", damageCause.getId())
/* 104 */         .param("amount", damageAmount));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\command\CameraEffectCommand$DamageCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */