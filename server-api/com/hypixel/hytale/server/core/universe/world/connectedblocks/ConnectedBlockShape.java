/*    */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectedBlockShape
/*    */ {
/*    */   public static final BuilderCodec<ConnectedBlockShape> CODEC;
/*    */   private CustomTemplateConnectedBlockPattern[] patternsToMatchAnyOf;
/*    */   private ConnectedBlockFaceTags faceTags;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ConnectedBlockShape.class, ConnectedBlockShape::new).append(new KeyedCodec("PatternsToMatchAnyOf", (Codec)new ArrayCodec((Codec)CustomTemplateConnectedBlockPattern.CODEC, x$0 -> new CustomTemplateConnectedBlockPattern[x$0]), true), (o, patternsToMatchAnyOf) -> o.patternsToMatchAnyOf = patternsToMatchAnyOf, o -> o.patternsToMatchAnyOf).add()).append(new KeyedCodec("FaceTags", (Codec)ConnectedBlockFaceTags.CODEC, false), (o, faceTags) -> o.faceTags = faceTags, o -> o.faceTags).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomTemplateConnectedBlockPattern[] getPatternsToMatchAnyOf() {
/* 28 */     return this.patternsToMatchAnyOf;
/*    */   }
/*    */   
/*    */   public ConnectedBlockFaceTags getFaceTags() {
/* 32 */     return this.faceTags;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\ConnectedBlockShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */