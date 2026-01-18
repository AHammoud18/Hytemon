/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<PlayerCondition> CODEC;
/*    */   @Nullable
/*    */   private GameMode gameModeToCheck;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PlayerCondition.class, PlayerCondition::new, Condition.BASE_CODEC).append(new KeyedCodec("GameMode", (Codec)new EnumCodec(GameMode.class)), (condition, gameMode) -> condition.gameModeToCheck = gameMode, condition -> condition.gameModeToCheck).documentation("The game mode to check for. If null, the condition always passes.").add()).build();
/*    */   }
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
/*    */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/* 53 */     if (this.gameModeToCheck == null) {
/* 54 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 58 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 59 */     if (playerComponent == null) return false;
/*    */ 
/*    */     
/* 62 */     return (playerComponent.getGameMode() == this.gameModeToCheck);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 68 */     return "PlayerCondition{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\PlayerCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */