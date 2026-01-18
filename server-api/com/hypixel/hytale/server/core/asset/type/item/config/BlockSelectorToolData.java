/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.BlockSelectorToolData;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class BlockSelectorToolData
/*    */   implements NetworkSerializable<BlockSelectorToolData>
/*    */ {
/*    */   public static final BuilderCodec<BlockSelectorToolData> CODEC;
/*    */   protected double durabilityLossOnUse;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockSelectorToolData.class, BlockSelectorToolData::new).append(new KeyedCodec("DurabilityLossOnUse", (Codec)Codec.DOUBLE), (data, x) -> data.durabilityLossOnUse = x.floatValue(), data -> Double.valueOf(data.durabilityLossOnUse)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockSelectorToolData toPacket() {
/* 29 */     BlockSelectorToolData packet = new BlockSelectorToolData();
/* 30 */     packet.durabilityLossOnUse = (float)this.durabilityLossOnUse;
/* 31 */     return packet;
/*    */   }
/*    */   
/*    */   public double getDurabilityLossOnUse() {
/* 35 */     return this.durabilityLossOnUse;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\BlockSelectorToolData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */