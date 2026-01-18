/*    */ package com.hypixel.hytale.server.spawning.local;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalSpawnBeacon
/*    */   implements Component<EntityStore>
/*    */ {
/* 19 */   public static final BuilderCodec<LocalSpawnBeacon> CODEC = BuilderCodec.builder(LocalSpawnBeacon.class, LocalSpawnBeacon::new)
/* 20 */     .build();
/*    */   
/*    */   public static ComponentType<EntityStore, LocalSpawnBeacon> getComponentType() {
/* 23 */     return SpawningPlugin.get().getLocalSpawnBeaconComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 30 */     return new LocalSpawnBeacon();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\local\LocalSpawnBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */