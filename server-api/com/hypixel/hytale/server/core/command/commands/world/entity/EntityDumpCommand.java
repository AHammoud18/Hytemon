/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDumpCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/* 25 */   private static final Message MESSAGE_GENERAL_NO_ENTITY_IN_VIEW = Message.translation("server.general.noEntityInView");
/* 26 */   private static final Message MESSAGE_COMMANDS_ENTITY_DUMP_DUMP_DONE = Message.translation("server.commands.entity.dump.dumpDone");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 31 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 32 */     withOptionalArg("entity", "server.commands.entity.dump.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDumpCommand() {
/* 38 */     super("dump", "server.commands.entity.dump.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 43 */     Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 44 */     if (entityRef == null || !entityRef.isValid()) {
/* 45 */       context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW);
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     Holder<EntityStore> holder = store.copyEntity(entityRef);
/* 50 */     BsonDocument document = EntityStore.REGISTRY.serialize(holder);
/* 51 */     HytaleLogger.getLogger().at(Level.INFO).log("Entity: %s\n%s\n%s", entityRef, holder, BsonUtil.toJson(document));
/* 52 */     context.sendMessage(MESSAGE_COMMANDS_ENTITY_DUMP_DUMP_DONE);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityDumpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */