/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityCountCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public EntityCountCommand() {
/* 20 */     super("count", "server.commands.entity.count.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 25 */     context.sendMessage(Message.translation("server.commands.entity.count.count")
/* 26 */         .param("count", store.getEntityCount()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityCountCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */