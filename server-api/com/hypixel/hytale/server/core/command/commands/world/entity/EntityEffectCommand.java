/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
/*    */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityEffectCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final RequiredArg<EntityEffect> effectArg = withRequiredArg("effect", "server.commands.entity.effect.effect.desc", (ArgumentType)ArgTypes.EFFECT_ASSET);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   private final DefaultArg<Float> durationArg = (DefaultArg<Float>)
/* 36 */     withDefaultArg("duration", "server.commands.entity.effect.duration.desc", (ArgumentType)ArgTypes.FLOAT, Float.valueOf(100.0F), "server.commands.entity.effect.duration.default")
/* 37 */     .addValidator(Validators.greaterThan(Float.valueOf(0.0F)));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityEffectCommand() {
/* 43 */     super("effect", "server.commands.entity.effect.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 48 */     EntityEffect entityEffect = (EntityEffect)this.effectArg.get(context);
/* 49 */     float duration = ((Float)this.durationArg.get(context)).floatValue();
/*    */     
/* 51 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = entities.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> entityRef = objectListIterator.next();
/* 52 */       EffectControllerComponent effectControllerComponent = (EffectControllerComponent)store.getComponent(entityRef, EffectControllerComponent.getComponentType());
/* 53 */       if (effectControllerComponent != null)
/* 54 */         effectControllerComponent.addEffect(entityRef, entityEffect, duration, OverlapBehavior.OVERWRITE, (ComponentAccessor)store);  }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityEffectCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */