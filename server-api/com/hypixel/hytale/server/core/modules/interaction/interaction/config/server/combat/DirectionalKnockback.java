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
/*    */ public class DirectionalKnockback
/*    */   extends Knockback
/*    */ {
/*    */   public static final BuilderCodec<DirectionalKnockback> CODEC;
/*    */   protected float relativeX;
/*    */   protected float velocityY;
/*    */   protected float relativeZ;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DirectionalKnockback.class, DirectionalKnockback::new, Knockback.BASE_CODEC).append(new KeyedCodec("RelativeX", (Codec)Codec.DOUBLE), (knockbackAttachment, d) -> knockbackAttachment.relativeX = d.floatValue(), knockbackAttachment -> Double.valueOf(knockbackAttachment.relativeX)).add()).append(new KeyedCodec("VelocityY", (Codec)Codec.DOUBLE), (knockbackAttachment, d) -> knockbackAttachment.velocityY = d.floatValue(), knockbackAttachment -> Double.valueOf(knockbackAttachment.velocityY)).add()).append(new KeyedCodec("RelativeZ", (Codec)Codec.DOUBLE), (knockbackAttachment, d) -> knockbackAttachment.relativeZ = d.floatValue(), knockbackAttachment -> Double.valueOf(knockbackAttachment.relativeZ)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d calculateVector(@Nonnull Vector3d source, float yaw, @Nonnull Vector3d target) {
/* 39 */     Vector3d vector = source.clone().subtract(target);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     if (vector.squaredLength() <= 1.0E-8D) {
/* 45 */       Vector3d lookVector = new Vector3d(0.0D, 0.0D, -1.0D);
/* 46 */       lookVector.rotateY(yaw);
/* 47 */       vector.assign(lookVector);
/*    */     } else {
/* 49 */       vector.normalize();
/*    */     } 
/*    */     
/* 52 */     if (this.relativeX != 0.0F || this.relativeZ != 0.0F) {
/* 53 */       Vector3d rotation = new Vector3d(this.relativeX, 0.0D, this.relativeZ);
/* 54 */       rotation.rotateY(yaw);
/* 55 */       vector.add(rotation);
/*    */     } 
/*    */     
/* 58 */     double x = vector.getX() * this.force;
/* 59 */     double z = vector.getZ() * this.force;
/* 60 */     double y = this.velocityY;
/*    */     
/* 62 */     return new Vector3d(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 68 */     return "DirectionalKnockback{relativeX=" + this.relativeX + ", relativeZ=" + this.relativeZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\combat\DirectionalKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */