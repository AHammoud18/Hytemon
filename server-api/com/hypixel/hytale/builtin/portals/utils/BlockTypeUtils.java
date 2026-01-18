/*    */ package com.hypixel.hytale.builtin.portals.utils;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
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
/*    */ public final class BlockTypeUtils
/*    */ {
/*    */   public static BlockType getBlockForState(BlockType blockType, String state) {
/* 17 */     String baseKey = blockType.getDefaultStateKey();
/*    */ 
/*    */     
/* 20 */     BlockType baseBlock = (baseKey == null) ? blockType : (BlockType)BlockType.getAssetMap().getAsset(baseKey);
/*    */     
/* 22 */     if ("default".equals(state)) {
/* 23 */       return baseBlock;
/*    */     }
/*    */     
/* 26 */     return baseBlock.getBlockForState(state);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\BlockTypeUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */