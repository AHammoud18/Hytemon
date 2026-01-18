/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.resources;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.NPCObjectivesPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.transaction.KillTaskTransaction;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class KillTrackerResource
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, KillTrackerResource> getResourceType() {
/* 17 */     return NPCObjectivesPlugin.get().getKillTrackerResourceType();
/*    */   }
/*    */   
/* 20 */   private final List<KillTaskTransaction> killTasks = (List<KillTaskTransaction>)new ObjectArrayList();
/*    */   
/*    */   public void watch(KillTaskTransaction task) {
/* 23 */     this.killTasks.add(task);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unwatch(KillTaskTransaction task) {
/* 28 */     this.killTasks.remove(task);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<KillTaskTransaction> getKillTasks() {
/* 33 */     return this.killTasks;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 39 */     return new KillTrackerResource();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\resources\KillTrackerResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */