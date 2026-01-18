/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.entity.Frozen;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import com.hypixel.hytale.server.npc.components.StepComponent;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCStepCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/*  36 */   private final FlagArg allArg = withFlagArg("all", "server.commands.npc.step.all");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  41 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/*  42 */     withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  47 */   private final OptionalArg<Float> dtArg = (OptionalArg<Float>)
/*  48 */     withOptionalArg("dt", "server.commands.npc.step.dt.desc", (ArgumentType)ArgTypes.FLOAT)
/*  49 */     .addValidator(Validators.greaterThan(Float.valueOf(0.0F)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCStepCommand() {
/*  55 */     super("step", "server.commands.npc.step.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  60 */     float dt = this.dtArg.provided(context) ? ((Float)this.dtArg.get(context)).floatValue() : (1.0F / world.getTps());
/*     */     
/*  62 */     if (((Boolean)this.allArg.get(context)).booleanValue()) {
/*  63 */       store.forEachEntityParallel((Query)NPCEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*     */             commandBuffer.ensureComponent(archetypeChunk.getReferenceTo(index), Frozen.getComponentType());
/*     */             
/*     */             commandBuffer.addComponent(archetypeChunk.getReferenceTo(index), StepComponent.getComponentType(), (Component)new StepComponent(dt));
/*     */           });
/*     */       return;
/*     */     } 
/*  70 */     NPCEntity npc = getNPC(context, store);
/*  71 */     if (npc == null) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     Ref<EntityStore> ref = npc.getReference();
/*  76 */     if (ref == null || !ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     store.ensureComponent(ref, Frozen.getComponentType());
/*  81 */     store.addComponent(ref, StepComponent.getComponentType(), (Component)new StepComponent(dt));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private NPCEntity getNPC(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store) {
/*     */     Ref<EntityStore> ref;
/*  88 */     if (this.entityArg.provided(context)) {
/*  89 */       ref = this.entityArg.get((ComponentAccessor)store, context);
/*  90 */     } else if (context.isPlayer()) {
/*  91 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  92 */       if (playerRef == null || !playerRef.isValid()) {
/*  93 */         context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/*  94 */             .param("option", "entity"));
/*  95 */         return null;
/*     */       } 
/*  97 */       ref = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/*  98 */       if (ref == null) {
/*  99 */         context.sendMessage(Message.translation("server.commands.errors.no_entity_in_view")
/* 100 */             .param("option", "entity"));
/* 101 */         return null;
/*     */       } 
/*     */     } else {
/* 104 */       context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/* 105 */           .param("option", "entity"));
/* 106 */       return null;
/*     */     } 
/*     */     
/* 109 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 110 */     if (npcComponent == null) {
/* 111 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 112 */       assert uuidComponent != null;
/* 113 */       UUID uuid = uuidComponent.getUuid();
/* 114 */       context.sendMessage(Message.translation("server.commands.errors.not_npc").param("uuid", uuid.toString()));
/* 115 */       return null;
/*     */     } 
/*     */     
/* 118 */     return npcComponent;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCStepCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */