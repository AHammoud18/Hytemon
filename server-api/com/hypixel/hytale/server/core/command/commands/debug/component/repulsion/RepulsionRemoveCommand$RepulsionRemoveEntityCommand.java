/*    */ package com.hypixel.hytale.server.core.command.commands.debug.component.repulsion;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.repulsion.Repulsion;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RepulsionRemoveEntityCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 44 */   private static final Message MESSAGE_COMMANDS_REPULSION_REMOVE_SUCCESS = Message.translation("server.commands.repulsion.remove.success");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 49 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 50 */     withRequiredArg("entity", "server.commands.repulsion.remove.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RepulsionRemoveEntityCommand() {
/* 56 */     super("entity", "server.commands.repulsion.remove.entity.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 61 */     Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 62 */     if (entityRef == null || !entityRef.isValid()) {
/* 63 */       context.sendMessage(RepulsionRemoveCommand.MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 67 */     store.tryRemoveComponent(entityRef, Repulsion.getComponentType());
/* 68 */     context.sendMessage(MESSAGE_COMMANDS_REPULSION_REMOVE_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\component\repulsion\RepulsionRemoveCommand$RepulsionRemoveEntityCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */