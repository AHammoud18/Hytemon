/*    */ package com.hypixel.hytale.server.core.modules.entity.hitboxcollision;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class HitboxCollision
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, HitboxCollision> getComponentType() {
/* 16 */     return EntityModule.get().getHitboxCollisionComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<HitboxCollision> CODEC;
/*    */   
/*    */   private int hitboxCollisionConfigIndex;
/*    */ 
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(HitboxCollision.class, HitboxCollision::new).append(new KeyedCodec("HitboxCollisionConfigIndex", (Codec)Codec.INTEGER), (hitboxCollision, integer) -> hitboxCollision.hitboxCollisionConfigIndex = integer.intValue(), hitboxCollision -> Integer.valueOf(hitboxCollision.hitboxCollisionConfigIndex)).add()).build();
/*    */   }
/*    */   
/*    */   private boolean isNetworkOutdated = true;
/*    */   
/*    */   public HitboxCollision(@Nonnull HitboxCollisionConfig hitboxCollisionConfig) {
/* 32 */     this.hitboxCollisionConfigIndex = HitboxCollisionConfig.getAssetMap().getIndexOrDefault(hitboxCollisionConfig.getId(), -1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHitboxCollisionConfigIndex() {
/* 39 */     return this.hitboxCollisionConfigIndex;
/*    */   }
/*    */   
/*    */   public void setHitboxCollisionConfigIndex(int hitboxCollisionConfigIndex) {
/* 43 */     this.hitboxCollisionConfigIndex = hitboxCollisionConfigIndex;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 47 */     boolean temp = this.isNetworkOutdated;
/* 48 */     this.isNetworkOutdated = false;
/* 49 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 55 */     HitboxCollision component = new HitboxCollision();
/* 56 */     component.hitboxCollisionConfigIndex = this.hitboxCollisionConfigIndex;
/* 57 */     return component;
/*    */   }
/*    */   
/*    */   protected HitboxCollision() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\hitboxcollision\HitboxCollision.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */