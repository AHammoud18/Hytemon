/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockSetCommand extends SimpleBlockCommand {
/* 15 */   private final RequiredArg<BlockType> blockArg = withRequiredArg("block", "server.commands.block.set.arg.block", (ArgumentType)ArgTypes.BLOCK_TYPE_ASSET);
/*    */   
/*    */   public BlockSetCommand() {
/* 18 */     super("set", "server.commands.block.set.desc");
/* 19 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeWithBlock(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, int x, int y, int z) {
/* 24 */     CommandSender sender = context.sender();
/*    */     
/* 26 */     BlockType blockType = (BlockType)context.get((Argument)this.blockArg);
/*    */     
/* 28 */     chunk.setBlock(x, y, z, blockType);
/* 29 */     sender.sendMessage(Message.translation("server.commands.block.set.success")
/* 30 */         .param("x", x).param("y", y).param("z", z)
/* 31 */         .param("id", blockType.getId().toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */