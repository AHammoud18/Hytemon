/*    */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ConnectedBlockRuleSet
/*    */ {
/* 17 */   public static final CodecMapCodec<ConnectedBlockRuleSet> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean onlyUpdateOnPlacement();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract Optional<ConnectedBlocksUtil.ConnectedBlockResult> getConnectedBlockType(World paramWorld, Vector3i paramVector3i1, BlockType paramBlockType, int paramInt, Vector3i paramVector3i2, boolean paramBoolean);
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateCachedBlockTypes(BlockType blockType, BlockTypeAssetMap<String, BlockType> assetMap) {}
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public com.hypixel.hytale.protocol.ConnectedBlockRuleSet toPacket(BlockTypeAssetMap<String, BlockType> assetMap) {
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\ConnectedBlockRuleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */