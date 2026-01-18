/*    */ package com.hypixel.hytale.server.core.command.commands.debug.component.hitboxcollision;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollision;
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
/*    */ public class HitboxCollisionRemoveEntityCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 39 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 40 */     withRequiredArg("entity", "server.commands.hitboxcollision.remove.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HitboxCollisionRemoveEntityCommand() {
/* 46 */     super("entity", "server.commands.hitboxcollision.remove.entity.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 51 */     Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 52 */     if (entityRef == null || !entityRef.isValid()) {
/* 53 */       context.sendMessage(HitboxCollisionRemoveCommand.MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     store.tryRemoveComponent(entityRef, HitboxCollision.getComponentType());
/* 58 */     context.sendMessage(Message.translation("server.commands.hitboxcollision.remove.success"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\component\hitboxcollision\HitboxCollisionRemoveCommand$HitboxCollisionRemoveEntityCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */