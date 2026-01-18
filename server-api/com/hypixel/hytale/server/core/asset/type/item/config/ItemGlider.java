/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.ItemGlider;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemGlider
/*    */   implements NetworkSerializable<ItemGlider>
/*    */ {
/*    */   public static final BuilderCodec<ItemGlider> CODEC;
/*    */   protected float terminalVelocity;
/*    */   protected float fallSpeedMultiplier;
/*    */   protected float horizontalSpeedMultiplier;
/*    */   protected float speed;
/*    */   
/*    */   static {
/* 45 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemGlider.class, ItemGlider::new).appendInherited(new KeyedCodec("TerminalVelocity", (Codec)Codec.FLOAT), (o, i) -> o.terminalVelocity = i.floatValue(), o -> Float.valueOf(o.terminalVelocity), (o, p) -> o.terminalVelocity = p.terminalVelocity).documentation("The maximum speed the player can fall while gliding.").add()).appendInherited(new KeyedCodec("FallSpeedMultiplier", (Codec)Codec.FLOAT), (o, i) -> o.fallSpeedMultiplier = i.floatValue(), o -> Float.valueOf(o.fallSpeedMultiplier), (o, p) -> o.fallSpeedMultiplier = p.fallSpeedMultiplier).documentation("The rate at which the fall speed is incremented.").add()).appendInherited(new KeyedCodec("HorizontalSpeedMultiplier", (Codec)Codec.FLOAT), (o, i) -> o.horizontalSpeedMultiplier = i.floatValue(), o -> Float.valueOf(o.horizontalSpeedMultiplier), (o, p) -> o.horizontalSpeedMultiplier = p.horizontalSpeedMultiplier).documentation("The rate at which the horizontal move speed is incremented.").add()).appendInherited(new KeyedCodec("Speed", (Codec)Codec.FLOAT), (o, i) -> o.speed = i.floatValue(), o -> Float.valueOf(o.speed), (o, p) -> o.speed = p.speed).documentation("The horizontal movement speed of the glider.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getTerminalVelocity() {
/* 52 */     return this.terminalVelocity;
/* 53 */   } public float getFallSpeedMultiplier() { return this.fallSpeedMultiplier; }
/* 54 */   public float getHorizontalSpeedMultiplier() { return this.horizontalSpeedMultiplier; } public float getSpeed() {
/* 55 */     return this.speed;
/*    */   }
/*    */   
/*    */   public ItemGlider toPacket() {
/* 59 */     ItemGlider packet = new ItemGlider();
/* 60 */     packet.terminalVelocity = this.terminalVelocity;
/* 61 */     packet.fallSpeedMultiplier = this.fallSpeedMultiplier;
/* 62 */     packet.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 63 */     packet.speed = this.speed;
/* 64 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemGlider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */