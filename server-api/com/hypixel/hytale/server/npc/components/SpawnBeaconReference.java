/*    */ package com.hypixel.hytale.server.npc.components;
/*    */ 
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
/*    */ public class SpawnBeaconReference
/*    */   extends SpawnReference
/*    */ {
/* 16 */   public static final BuilderCodec<SpawnBeaconReference> CODEC = BuilderCodec.builder(SpawnBeaconReference.class, SpawnBeaconReference::new, BASE_CODEC)
/* 17 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, SpawnBeaconReference> getComponentType() {
/* 20 */     return SpawningPlugin.get().getSpawnBeaconReferenceComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 26 */     SpawnBeaconReference reference = new SpawnBeaconReference();
/* 27 */     reference.reference = this.reference;
/* 28 */     return reference;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\SpawnBeaconReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */