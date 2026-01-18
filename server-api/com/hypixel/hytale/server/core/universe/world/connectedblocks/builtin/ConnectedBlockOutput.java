/*    */ package com.hypixel.hytale.server.core.universe.world.connectedblocks.builtin;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import java.util.function.Supplier;
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
/*    */ public class ConnectedBlockOutput
/*    */ {
/*    */   public static final BuilderCodec<ConnectedBlockOutput> CODEC;
/*    */   protected String state;
/*    */   protected String blockTypeKey;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ConnectedBlockOutput.class, ConnectedBlockOutput::new).append(new KeyedCodec("State", (Codec)Codec.STRING), (output, state) -> output.state = state, output -> output.state).documentation("An optional state definition to apply to the base block type").add()).append(new KeyedCodec("Block", (Codec)Codec.STRING), (output, blockTypeKey) -> output.blockTypeKey = blockTypeKey, output -> output.blockTypeKey).documentation("An optional block ID to use instead of the base block type").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int resolve(BlockType baseBlockType, BlockTypeAssetMap<String, BlockType> assetMap) {
/* 35 */     String blockTypeKey = this.blockTypeKey;
/*    */     
/* 37 */     if (blockTypeKey == null) {
/* 38 */       blockTypeKey = baseBlockType.getId();
/*    */     }
/*    */     
/* 41 */     BlockType blockType = (BlockType)assetMap.getAsset(blockTypeKey);
/*    */     
/* 43 */     if (blockType == null) {
/* 44 */       return -1;
/*    */     }
/* 46 */     if (this.state != null) {
/* 47 */       String baseKey = blockType.getDefaultStateKey();
/* 48 */       BlockType baseBlock = (baseKey == null) ? blockType : (BlockType)BlockType.getAssetMap().getAsset(baseKey);
/*    */       
/* 50 */       if ("default".equals(this.state)) {
/* 51 */         blockTypeKey = baseBlock.getId();
/*    */       } else {
/* 53 */         blockTypeKey = baseBlock.getBlockKeyForState(this.state);
/*    */       } 
/*    */       
/* 56 */       if (blockTypeKey == null) {
/* 57 */         return -1;
/*    */       }
/*    */     } 
/* 60 */     int index = assetMap.getIndex(blockTypeKey);
/* 61 */     if (index == Integer.MIN_VALUE) {
/* 62 */       return -1;
/*    */     }
/* 64 */     this.blockTypeKey = blockTypeKey;
/* 65 */     return index;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\builtin\ConnectedBlockOutput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */