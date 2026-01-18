/*    */ package com.hypixel.hytale.server.core.modules.debug.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugShapeClearCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_DEBUG_SHAPE_CLEAR_SUCCESS = Message.translation("server.commands.debug.shape.clear.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DebugShapeClearCommand() {
/* 26 */     super("clear", "server.commands.debug.shape.clear.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 36 */     DebugUtils.clear(world);
/* 37 */     context.sendMessage(MESSAGE_COMMANDS_DEBUG_SHAPE_CLEAR_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\commands\DebugShapeClearCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */