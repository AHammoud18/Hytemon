/*    */ package com.hypixel.hytale.server.core.modules.blockhealth;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FragileBlock
/*    */   implements Cloneable {
/*    */   private float durationSeconds;
/*    */   
/*    */   public FragileBlock(float durationSeconds) {
/* 11 */     this.durationSeconds = durationSeconds;
/*    */   }
/*    */ 
/*    */   
/*    */   public FragileBlock() {}
/*    */ 
/*    */   
/*    */   public float getDurationSeconds() {
/* 19 */     return this.durationSeconds;
/*    */   }
/*    */   
/*    */   public void setDurationSeconds(float durationSeconds) {
/* 23 */     this.durationSeconds = durationSeconds;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserialize(@Nonnull ByteBuf buf, byte version) {
/* 28 */     this.durationSeconds = buf.readFloat();
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 32 */     buf.writeFloat(this.durationSeconds);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected FragileBlock clone() {
/* 38 */     return new FragileBlock(this.durationSeconds);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 44 */     return "FragileBlock{durationSeconds=" + this.durationSeconds + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockhealth\FragileBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */