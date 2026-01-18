/*    */ package com.hypixel.hytale.server.spawning.blockstates;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ public class SpawnMarkerBlockReference
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<SpawnMarkerBlockReference> CODEC;
/*    */   private Vector3i blockPosition;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SpawnMarkerBlockReference.class, SpawnMarkerBlockReference::new).append(new KeyedCodec("BlockPosition", (Codec)Vector3i.CODEC), (reference, o) -> reference.blockPosition = o, reference -> reference.blockPosition).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, SpawnMarkerBlockReference> getComponentType() {
/* 31 */     return SpawningPlugin.get().getSpawnMarkerBlockReferenceComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 36 */   private float originLostTimeout = 30.0F;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnMarkerBlockReference(Vector3i blockPosition) {
/* 42 */     this.blockPosition = blockPosition;
/*    */   }
/*    */   
/*    */   public Vector3i getBlockPosition() {
/* 46 */     return this.blockPosition;
/*    */   }
/*    */   
/*    */   public void refreshOriginLostTimeout() {
/* 50 */     this.originLostTimeout = 30.0F;
/*    */   }
/*    */   
/*    */   public boolean tickOriginLostTimeout(float dt) {
/* 54 */     return ((this.originLostTimeout -= dt) <= 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 61 */     SpawnMarkerBlockReference reference = new SpawnMarkerBlockReference();
/* 62 */     reference.blockPosition = this.blockPosition;
/* 63 */     return reference;
/*    */   }
/*    */   
/*    */   private SpawnMarkerBlockReference() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */