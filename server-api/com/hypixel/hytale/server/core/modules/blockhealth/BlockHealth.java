/*    */ package com.hypixel.hytale.server.core.modules.blockhealth;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockHealth
/*    */   implements Cloneable {
/* 10 */   public static final BlockHealth NO_DAMAGE_INSTANCE = new BlockHealth(1.0F, Instant.MIN)
/*    */     {
/*    */       public void setHealth(float health) {
/* 13 */         throw new UnsupportedOperationException("NO_DAMAGE_INSTANCE is immutable!");
/*    */       }
/*    */ 
/*    */       
/*    */       public void setLastDamageGameTime(Instant lastDamageGameTime) {
/* 18 */         throw new UnsupportedOperationException("NO_DAMAGE_INSTANCE is immutable!");
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */   
/*    */   private float health;
/*    */   
/*    */   private Instant lastDamageGameTime;
/*    */ 
/*    */   
/*    */   public BlockHealth() {
/* 30 */     this(1.0F, Instant.MIN);
/*    */   }
/*    */   
/*    */   public BlockHealth(float health, Instant lastDamageGameTime) {
/* 34 */     this.health = health;
/* 35 */     this.lastDamageGameTime = lastDamageGameTime;
/*    */   }
/*    */   
/*    */   public float getHealth() {
/* 39 */     return this.health;
/*    */   }
/*    */   
/*    */   public void setHealth(float health) {
/* 43 */     this.health = health;
/*    */   }
/*    */   
/*    */   public Instant getLastDamageGameTime() {
/* 47 */     return this.lastDamageGameTime;
/*    */   }
/*    */   
/*    */   public void setLastDamageGameTime(Instant lastDamageGameTime) {
/* 51 */     this.lastDamageGameTime = lastDamageGameTime;
/*    */   }
/*    */   
/*    */   public boolean isDestroyed() {
/* 55 */     return (MathUtil.closeToZero(this.health) || this.health < 0.0F);
/*    */   }
/*    */   
/*    */   public boolean isFullHealth() {
/* 59 */     return (this.health >= 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserialize(@Nonnull ByteBuf buf, byte version) {
/* 64 */     this.health = buf.readFloat();
/* 65 */     this.lastDamageGameTime = Instant.ofEpochMilli(buf.readLong());
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 69 */     buf.writeFloat(this.health);
/* 70 */     buf.writeLong(this.lastDamageGameTime.toEpochMilli());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected BlockHealth clone() {
/* 76 */     return new BlockHealth(this.health, this.lastDamageGameTime);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 82 */     return "BlockHealth{health=" + this.health + ", lastDamageGameTime=" + String.valueOf(this.lastDamageGameTime) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockhealth\BlockHealth.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */