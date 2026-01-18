/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public abstract class NPCWorldCommandBase
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/*  37 */   protected static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG = Message.translation("server.commands.errors.playerOrArg")
/*  38 */     .param("option", "entity");
/*     */   @Nonnull
/*  40 */   protected static final Message MESSAGE_COMMANDS_ERRORS_NO_ENTITY_IN_VIEW = Message.translation("server.commands.errors.no_entity_in_view")
/*  41 */     .param("option", "entity");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   protected final EntityWrappedArg entityArg = (EntityWrappedArg)
/*  47 */     withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCWorldCommandBase(@Nonnull String name, @Nonnull String description) {
/*  56 */     super(name, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCWorldCommandBase(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/*  67 */     super(name, description, requiresConfirmation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCWorldCommandBase(@Nonnull String description) {
/*  76 */     super(description);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  81 */     NPCEntity npc = getNPC(context, store);
/*  82 */     if (npc == null) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     Ref<EntityStore> ref = npc.getReference();
/*  87 */     assert ref != null;
/*  88 */     assert ref.isValid();
/*     */     
/*  90 */     execute(context, npc, world, store, ref);
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
/*     */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nonnull NPCEntity paramNPCEntity, @Nonnull World paramWorld, @Nonnull Store<EntityStore> paramStore, @Nonnull Ref<EntityStore> paramRef);
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
/*     */   @Nullable
/*     */   private NPCEntity getNPC(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store) {
/*     */     Ref<EntityStore> ref;
/* 116 */     if (this.entityArg.provided(context)) {
/* 117 */       ref = this.entityArg.get((ComponentAccessor)store, context);
/* 118 */     } else if (context.isPlayer()) {
/*     */ 
/*     */       
/* 121 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/* 122 */       if (playerRef == null || !playerRef.isValid()) {
/* 123 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG);
/* 124 */         return null;
/*     */       } 
/*     */       
/* 127 */       ref = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/* 128 */       if (ref == null) {
/* 129 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_NO_ENTITY_IN_VIEW);
/* 130 */         return null;
/*     */       } 
/*     */     } else {
/* 133 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG);
/* 134 */       return null;
/*     */     } 
/*     */     
/* 137 */     if (ref == null) return null;
/*     */ 
/*     */     
/* 140 */     return ensureIsNPC(context, store, ref);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected static NPCEntity ensureIsNPC(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, Ref<EntityStore> ref) {
/* 145 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 146 */     if (npcComponent == null) {
/* 147 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 148 */       assert uuidComponent != null;
/* 149 */       UUID uuid = uuidComponent.getUuid();
/* 150 */       context.sendMessage(Message.translation("server.commands.errors.not_npc").param("uuid", uuid.toString()));
/* 151 */       return null;
/*     */     } 
/*     */     
/* 154 */     return npcComponent;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCWorldCommandBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */