/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockSetTickingCommand
/*    */   extends SimpleBlockCommand {
/*    */   public BlockSetTickingCommand() {
/* 12 */     super("setticking", "server.commands.block.setticking.desc");
/* 13 */     setPermissionGroup(null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeWithBlock(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, int x, int y, int z) {
/* 18 */     CommandSender sender = context.sender();
/*    */     
/* 20 */     chunk.setTicking(x, y, z, true);
/* 21 */     sender.sendMessage(Message.translation("server.commands.block.setticking.success")
/* 22 */         .param("x", x).param("y", y).param("z", z));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockSetTickingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */