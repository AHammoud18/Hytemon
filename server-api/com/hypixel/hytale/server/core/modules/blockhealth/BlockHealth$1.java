/*    */ package com.hypixel.hytale.server.core.modules.blockhealth;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ class null
/*    */   extends BlockHealth
/*    */ {
/*    */   null(float health, Instant lastDamageGameTime) {
/* 10 */     super(health, lastDamageGameTime);
/*    */   }
/*    */   public void setHealth(float health) {
/* 13 */     throw new UnsupportedOperationException("NO_DAMAGE_INSTANCE is immutable!");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLastDamageGameTime(Instant lastDamageGameTime) {
/* 18 */     throw new UnsupportedOperationException("NO_DAMAGE_INSTANCE is immutable!");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockhealth\BlockHealth$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */