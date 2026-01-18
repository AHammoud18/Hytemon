/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PointKnockback
/*    */   extends Knockback
/*    */ {
/*    */   public static final BuilderCodec<PointKnockback> CODEC;
/*    */   protected float velocityY;
/*    */   protected int rotateY;
/*    */   protected int offsetX;
/*    */   protected int offsetZ;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PointKnockback.class, PointKnockback::new, Knockback.BASE_CODEC).append(new KeyedCodec("VelocityY", (Codec)Codec.DOUBLE), (knockbackAttachment, d) -> knockbackAttachment.velocityY = d.floatValue(), knockbackAttachment -> Double.valueOf(knockbackAttachment.velocityY)).add()).append(new KeyedCodec("RotateY", (Codec)Codec.INTEGER), (knockbackAttachment, i) -> knockbackAttachment.rotateY = i.intValue(), knockbackAttachment -> Integer.valueOf(knockbackAttachment.rotateY)).add()).append(new KeyedCodec("OffsetX", (Codec)Codec.INTEGER), (knockbackAttachment, i) -> knockbackAttachment.offsetX = i.intValue(), knockbackAttachment -> Integer.valueOf(knockbackAttachment.offsetX)).add()).append(new KeyedCodec("OffsetZ", (Codec)Codec.INTEGER), (knockbackAttachment, i) -> knockbackAttachment.offsetZ = i.intValue(), knockbackAttachment -> Integer.valueOf(knockbackAttachment.offsetZ)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d calculateVector(@Nonnull Vector3d source, float yaw, @Nonnull Vector3d target) {
/* 47 */     Vector3d from = source;
/* 48 */     if (this.offsetX != 0 || this.offsetZ != 0) {
/* 49 */       from = new Vector3d(this.offsetX, 0.0D, this.offsetZ);
/* 50 */       from.rotateY(yaw * 57.295776F);
/* 51 */       from.add(source);
/*    */     } 
/*    */     
/* 54 */     Vector3d vector = Vector3d.directionTo(from, target).normalize();
/* 55 */     if (this.rotateY != 0) vector.rotateY(this.rotateY);
/*    */     
/* 57 */     double x = vector.getX() * this.force;
/* 58 */     double z = vector.getZ() * this.force;
/* 59 */     double y = this.velocityY;
/*    */     
/* 61 */     return new Vector3d(x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\combat\PointKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */