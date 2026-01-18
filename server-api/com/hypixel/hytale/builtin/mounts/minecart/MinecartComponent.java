/*    */ package com.hypixel.hytale.builtin.mounts.minecart;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.mounts.MountPlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class MinecartComponent
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, MinecartComponent> getComponentType() {
/* 16 */     return MountPlugin.getInstance().getMinecartComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<MinecartComponent> CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(MinecartComponent.class, MinecartComponent::new).append(new KeyedCodec("SourceItem", (Codec)Codec.STRING), (o, v) -> o.sourceItem = v, o -> o.sourceItem).add()).build();
/*    */   }
/* 28 */   private int numberOfHits = 0;
/*    */   
/*    */   private Instant lastHit;
/* 31 */   private String sourceItem = "Rail_Kart";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecartComponent(String sourceItem) {
/* 37 */     this.sourceItem = sourceItem;
/*    */   }
/*    */   
/*    */   public int getNumberOfHits() {
/* 41 */     return this.numberOfHits;
/*    */   }
/*    */   
/*    */   public void setNumberOfHits(int numberOfHits) {
/* 45 */     this.numberOfHits = numberOfHits;
/*    */   }
/*    */   
/*    */   public Instant getLastHit() {
/* 49 */     return this.lastHit;
/*    */   }
/*    */   
/*    */   public void setLastHit(Instant lastHit) {
/* 53 */     this.lastHit = lastHit;
/*    */   }
/*    */   
/*    */   public String getSourceItem() {
/* 57 */     return this.sourceItem;
/*    */   }
/*    */   
/*    */   public void setSourceItem(String sourceItem) {
/* 61 */     this.sourceItem = sourceItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 66 */     return new MinecartComponent(this.sourceItem);
/*    */   }
/*    */   
/*    */   private MinecartComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\minecart\MinecartComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */