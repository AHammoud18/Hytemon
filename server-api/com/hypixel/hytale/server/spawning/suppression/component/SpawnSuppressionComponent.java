/*    */ package com.hypixel.hytale.server.spawning.suppression.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
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
/*    */ public class SpawnSuppressionComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<SpawnSuppressionComponent> CODEC;
/*    */   private String spawnSuppression;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SpawnSuppressionComponent.class, SpawnSuppressionComponent::new).append(new KeyedCodec("SpawnSuppression", (Codec)Codec.STRING), (suppressor, s) -> suppressor.spawnSuppression = s, suppressor -> suppressor.spawnSuppression).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, SpawnSuppressionComponent> getComponentType() {
/* 30 */     return SpawningPlugin.get().getSpawnSuppressorComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnSuppressionComponent(String spawnSuppression) {
/* 36 */     this.spawnSuppression = spawnSuppression;
/*    */   }
/*    */ 
/*    */   
/*    */   private SpawnSuppressionComponent() {}
/*    */   
/*    */   public String getSpawnSuppression() {
/* 43 */     return this.spawnSuppression;
/*    */   }
/*    */   
/*    */   public void setSpawnSuppression(String spawnSuppression) {
/* 47 */     this.spawnSuppression = spawnSuppression;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 54 */     SpawnSuppressionComponent suppressor = new SpawnSuppressionComponent();
/* 55 */     suppressor.spawnSuppression = this.spawnSuppression;
/* 56 */     return suppressor;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\component\SpawnSuppressionComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */