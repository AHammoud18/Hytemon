/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class NPCCommandUtils
/*    */ {
/*    */   @Nullable
/*    */   public static Pair<Ref<EntityStore>, NPCEntity> getTargetNpc(@Nonnull CommandContext context, @Nonnull EntityWrappedArg arg, @Nonnull Store<EntityStore> store) {
/*    */     Ref<EntityStore> ref;
/* 35 */     if (arg.provided(context)) {
/* 36 */       ref = arg.get((ComponentAccessor)store, context);
/* 37 */     } else if (context.isPlayer()) {
/* 38 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/* 39 */       if (playerRef == null || !playerRef.isValid()) {
/* 40 */         context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/* 41 */             .param("option", "entity"));
/* 42 */         return null;
/*    */       } 
/* 44 */       ref = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/* 45 */       if (ref == null) {
/* 46 */         context.sendMessage(Message.translation("server.commands.errors.no_entity_in_view")
/* 47 */             .param("option", "entity"));
/* 48 */         return null;
/*    */       } 
/*    */     } else {
/* 51 */       context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/* 52 */           .param("option", "entity"));
/* 53 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 57 */     if (ref == null || !ref.isValid()) {
/* 58 */       return null;
/*    */     }
/*    */     
/* 61 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 62 */     if (npcComponent == null) {
/* 63 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 64 */       assert uuidComponent != null;
/* 65 */       UUID uuid = uuidComponent.getUuid();
/* 66 */       context.sendMessage(Message.translation("server.commands.errors.not_npc").param("uuid", uuid.toString()));
/* 67 */       return null;
/*    */     } 
/*    */     
/* 70 */     return Pair.of(ref, npcComponent);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCCommandUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */