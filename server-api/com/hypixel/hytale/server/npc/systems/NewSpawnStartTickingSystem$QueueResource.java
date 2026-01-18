/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QueueResource
/*     */   implements Resource<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static ResourceType<EntityStore, QueueResource> getResourceType() {
/*  91 */     return NPCPlugin.get().getNewSpawnStartTickingQueueResourceType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  97 */   private final ObjectList<Ref<EntityStore>> queue = (ObjectList<Ref<EntityStore>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Resource<EntityStore> clone() {
/* 104 */     QueueResource queue = new QueueResource();
/* 105 */     queue.queue.addAll(this.queue);
/* 106 */     return queue;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NewSpawnStartTickingSystem$QueueResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */