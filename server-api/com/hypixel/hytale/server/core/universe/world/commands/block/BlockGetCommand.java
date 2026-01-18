/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockGetCommand
/*    */   extends SimpleBlockCommand {
/*    */   public BlockGetCommand() {
/* 13 */     super("get", "server.commands.block.get.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeWithBlock(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, int x, int y, int z) {
/* 18 */     CommandSender sender = context.sender();
/*    */     
/* 20 */     int blockId = chunk.getBlock(x, y, z);
/* 21 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 22 */     int support = chunk.getSupportValue(x, y, z);
/* 23 */     sender.sendMessage(Message.translation("server.commands.block.get.info")
/* 24 */         .param("x", x).param("y", y).param("z", z)
/* 25 */         .param("id", blockType.getId().toString())
/* 26 */         .param("blockId", blockId)
/* 27 */         .param("support", support));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */