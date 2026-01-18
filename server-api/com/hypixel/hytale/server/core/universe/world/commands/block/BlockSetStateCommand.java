/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockSetStateCommand extends SimpleBlockCommand {
/* 13 */   private final RequiredArg<String> stateArg = withRequiredArg("state", "", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   public BlockSetStateCommand() {
/* 16 */     super("setstate", "");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeWithBlock(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, int x, int y, int z) {
/* 21 */     int blockId = chunk.getBlock(x, y, z);
/* 22 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*    */     
/* 24 */     String state = (String)context.get((Argument)this.stateArg);
/* 25 */     chunk.setBlockInteractionState(x, y, z, blockType, state, true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockSetStateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */