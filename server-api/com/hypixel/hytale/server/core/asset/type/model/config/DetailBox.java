/*    */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.DetailBox;
/*    */ import com.hypixel.hytale.protocol.Hitbox;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializers;
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
/*    */ public class DetailBox
/*    */   implements NetworkSerializable<DetailBox>
/*    */ {
/*    */   public static final BuilderCodec<DetailBox> CODEC;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DetailBox.class, DetailBox::new).appendInherited(new KeyedCodec("Offset", (Codec)Vector3d.CODEC), (o, i) -> o.offset = i, o -> o.offset, (o, p) -> o.offset = p.offset).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Box", Box.CODEC), (o, i) -> o.box = i, o -> o.box, (o, p) -> o.box = p.box).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/* 35 */   protected Vector3d offset = Vector3d.ZERO;
/* 36 */   protected Box box = Box.UNIT;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DetailBox(Vector3d offset, Box box) {
/* 42 */     this.offset = offset;
/* 43 */     this.box = box;
/*    */   }
/*    */   
/*    */   public DetailBox(DetailBox other) {
/* 47 */     this.offset.assign(other.offset);
/* 48 */     this.box.assign(other.box);
/*    */   }
/*    */   
/*    */   public Vector3d getOffset() {
/* 52 */     return this.offset;
/*    */   }
/*    */   
/*    */   public Box getBox() {
/* 56 */     return this.box;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DetailBox scaled(float scale) {
/* 65 */     return new DetailBox(this.offset
/* 66 */         .clone().scale(scale), this.box
/* 67 */         .clone().scale(scale));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public DetailBox toPacket() {
/* 74 */     return new DetailBox(new Vector3f((float)this.offset.x, (float)this.offset.y, (float)this.offset.z), (Hitbox)NetworkSerializers.BOX
/*    */         
/* 76 */         .toPacket(this.box));
/*    */   }
/*    */   
/*    */   public DetailBox() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\DetailBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */