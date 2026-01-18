/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ForceKnockback
/*    */   extends Knockback
/*    */ {
/*    */   public static final BuilderCodec<ForceKnockback> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ForceKnockback.class, ForceKnockback::new, Knockback.BASE_CODEC).appendInherited(new KeyedCodec("Direction", (Codec)Vector3d.CODEC), (o, i) -> o.direction = i, o -> o.direction, (o, p) -> o.direction = p.direction).addValidator(Validators.nonNull()).add()).afterDecode(i -> i.direction.normalize())).build();
/*    */   }
/* 23 */   private Vector3d direction = Vector3d.UP;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d calculateVector(Vector3d source, float yaw, Vector3d target) {
/* 28 */     Vector3d vel = this.direction.clone();
/* 29 */     vel.rotateY(yaw);
/* 30 */     vel.scale(this.force);
/* 31 */     return vel;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 37 */     return "ForceKnockback{direction=" + String.valueOf(this.direction) + "} " + super
/*    */       
/* 39 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\combat\ForceKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */