/*    */ package com.hypixel.hytale.builtin.portals.components.voidevent;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.PortalsPlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class VoidSpawner
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, VoidSpawner> getComponentType() {
/* 16 */     return PortalsPlugin.getInstance().getVoidPortalComponentType();
/*    */   }
/*    */   
/* 19 */   private List<UUID> spawnBeaconUuids = (List<UUID>)new ObjectArrayList();
/*    */   
/*    */   public List<UUID> getSpawnBeaconUuids() {
/* 22 */     return this.spawnBeaconUuids;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component<EntityStore> clone() {
/* 28 */     VoidSpawner clone = new VoidSpawner();
/* 29 */     clone.spawnBeaconUuids = this.spawnBeaconUuids;
/* 30 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\voidevent\VoidSpawner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */