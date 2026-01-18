/*    */ package com.hypixel.hytale.server.npc.components;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.server.core.entity.reference.InvalidatablePersistentRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpawnReference
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   protected static final BuilderCodec<SpawnReference> BASE_CODEC;
/*    */   public static final float MARKER_LOST_TIMEOUT = 30.0F;
/*    */   
/*    */   static {
/* 20 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(SpawnReference.class).append(new KeyedCodec("SpawnMarker", (Codec)InvalidatablePersistentRef.CODEC), (reference, entityReference) -> reference.reference = entityReference, reference -> reference.reference).add()).build();
/*    */   }
/*    */ 
/*    */   
/* 24 */   protected InvalidatablePersistentRef reference = new InvalidatablePersistentRef();
/*    */   private float markerLostTimeoutCounter;
/*    */   
/*    */   public InvalidatablePersistentRef getReference() {
/* 28 */     return this.reference;
/*    */   }
/*    */   
/*    */   public boolean tickMarkerLostTimeoutCounter(float dt) {
/* 32 */     return ((this.markerLostTimeoutCounter -= dt) <= 0.0F);
/*    */   }
/*    */   
/*    */   public void refreshTimeoutCounter() {
/* 36 */     this.markerLostTimeoutCounter = 30.0F;
/*    */   }
/*    */   
/*    */   public abstract Component<EntityStore> clone();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\SpawnReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */