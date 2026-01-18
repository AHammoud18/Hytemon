/*    */ package com.hypixel.hytale.server.core.asset.type.trail.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.ColorAlpha;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ public class Edge
/*    */ {
/*    */   public static final BuilderCodec<Edge> CODEC;
/*    */   private float width;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Edge.class, Edge::new).appendInherited(new KeyedCodec("Width", (Codec)Codec.DOUBLE), (edge, d) -> edge.width = d.floatValue(), edge -> Double.valueOf(edge.width), (edge, parent) -> edge.width = parent.width).add()).appendInherited(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR_AlPHA), (edge, o) -> edge.color = o, edge -> edge.color, (edge, parent) -> edge.color = parent.color).add()).build();
/*    */   }
/*    */   
/* 31 */   private ColorAlpha color = new ColorAlpha((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/*    */   
/*    */   @Nonnull
/*    */   public com.hypixel.hytale.protocol.Edge toPacket() {
/* 35 */     com.hypixel.hytale.protocol.Edge packet = new com.hypixel.hytale.protocol.Edge();
/*    */     
/* 37 */     packet.color = this.color;
/* 38 */     packet.width = this.width;
/*    */     
/* 40 */     return packet;
/*    */   }
/*    */   
/*    */   public float getWidth() {
/* 44 */     return this.width;
/*    */   }
/*    */   
/*    */   public ColorAlpha getColor() {
/* 48 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 54 */     return "Edge{width=" + this.width + ", color='" + String.valueOf(this.color) + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\trail\config\Edge.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */