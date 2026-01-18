/*    */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CustomTemplateConnectedBlockPattern
/*    */ {
/* 15 */   public static final CodecMapCodec<CustomTemplateConnectedBlockPattern> CODEC = new CodecMapCodec("Type");
/*    */   
/*    */   public abstract Optional<ConnectedBlocksUtil.ConnectedBlockResult> getConnectedBlockTypeKey(String paramString, @Nonnull World paramWorld, @Nonnull Vector3i paramVector3i1, @Nonnull CustomTemplateConnectedBlockRuleSet paramCustomTemplateConnectedBlockRuleSet, @Nonnull BlockType paramBlockType, int paramInt, @Nonnull Vector3i paramVector3i2, boolean paramBoolean);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\CustomTemplateConnectedBlockPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */