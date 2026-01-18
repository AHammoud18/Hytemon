/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.WrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityWrappedArg
/*    */   extends WrappedArg<UUID>
/*    */ {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityWrappedArg(@Nonnull Argument<?, UUID> argument) {
/* 33 */     super(argument);
/*    */   }
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
/*    */   @Nullable
/*    */   public Ref<EntityStore> get(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull CommandContext context) {
/* 47 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 48 */     Ref<EntityStore> reference = getEntityDirectly(context, world);
/* 49 */     if (reference != null) return reference;
/*    */     
/* 51 */     Ref<EntityStore> playerRef = context.isPlayer() ? context.senderAsPlayerRef() : null;
/*    */     
/* 53 */     if (playerRef == null) {
/* 54 */       throw new GeneralCommandException(Message.translation("server.commands.errors.playerOrArg")
/* 55 */           .param("option", "entity"));
/*    */     }
/*    */     
/* 58 */     if (!playerRef.isValid()) {
/* 59 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */     }
/*    */     
/* 62 */     Ref<EntityStore> entityRef = TargetUtil.getTargetEntity(playerRef, componentAccessor);
/* 63 */     if (entityRef == null) {
/* 64 */       throw new GeneralCommandException(Message.translation("server.commands.errors.no_entity_in_view")
/* 65 */           .param("option", "entity"));
/*    */     }
/*    */     
/* 68 */     return entityRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getEntityDirectly(@Nonnull CommandContext context, @Nonnull World world) {
/* 81 */     if (!provided(context)) return null;
/*    */     
/* 83 */     UUID uuid = (UUID)get(context);
/* 84 */     Ref<EntityStore> reference = world.getEntityStore().getRefFromUUID(uuid);
/* 85 */     if (reference == null) {
/* 86 */       throw new GeneralCommandException(Message.translation("server.commands.errors.targetNotFound").param("uuid", uuid.toString()));
/*    */     }
/* 88 */     return reference;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\EntityWrappedArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */