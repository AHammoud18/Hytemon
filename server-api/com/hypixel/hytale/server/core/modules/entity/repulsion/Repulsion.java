/*    */ package com.hypixel.hytale.server.core.modules.entity.repulsion;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
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
/*    */ public class Repulsion implements Component<EntityStore> {
/*    */   public static final BuilderCodec<Repulsion> CODEC;
/*    */   
/*    */   public static ComponentType<EntityStore, Repulsion> getComponentType() {
/* 18 */     return EntityModule.get().getRepulsionComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   protected AssetExtraInfo.Data data;
/*    */   
/*    */   private int repulsionConfigIndex;
/*    */ 
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Repulsion.class, Repulsion::new).append(new KeyedCodec("RepulsionConfigIndex", (Codec)Codec.INTEGER), (hitboxCollision, integer) -> hitboxCollision.repulsionConfigIndex = integer.intValue(), hitboxCollision -> Integer.valueOf(hitboxCollision.repulsionConfigIndex)).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isNetworkOutdated = true;
/*    */ 
/*    */   
/*    */   public Repulsion(@Nonnull RepulsionConfig repulsionConfig) {
/* 36 */     this.repulsionConfigIndex = RepulsionConfig.getAssetMap().getIndexOrDefault(repulsionConfig.getId(), -1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRepulsionConfigIndex() {
/* 43 */     return this.repulsionConfigIndex;
/*    */   }
/*    */   
/*    */   public void setRepulsionConfigIndex(int repulsionConfigIndex) {
/* 47 */     this.repulsionConfigIndex = repulsionConfigIndex;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 51 */     boolean temp = this.isNetworkOutdated;
/* 52 */     this.isNetworkOutdated = false;
/* 53 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 59 */     Repulsion component = new Repulsion();
/* 60 */     component.repulsionConfigIndex = this.repulsionConfigIndex;
/* 61 */     return component;
/*    */   }
/*    */   
/*    */   protected Repulsion() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\repulsion\Repulsion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */