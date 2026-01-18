/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.NonTicking;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Set;
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
/*     */ public class NewSpawnStartTickingSystem
/*     */   extends TickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, QueueResource> queueResourceType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NonTicking<EntityStore>> nonTickingComponentType;
/*     */   @Nonnull
/*  40 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, StepCleanupSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NewSpawnStartTickingSystem(@Nonnull ResourceType<EntityStore, QueueResource> queueResourceType) {
/*  48 */     this.queueResourceType = queueResourceType;
/*  49 */     this.nonTickingComponentType = EntityStore.REGISTRY.getNonTickingComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  55 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/*  60 */     ObjectList<Ref<EntityStore>> queue = ((QueueResource)store.getResource(this.queueResourceType)).queue;
/*  61 */     if (queue.isEmpty())
/*     */       return; 
/*  63 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = queue.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> reference = objectListIterator.next();
/*  64 */       if (!reference.isValid())
/*  65 */         continue;  store.removeComponent(reference, this.nonTickingComponentType); }
/*     */     
/*  67 */     queue.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void queueNewSpawn(@Nonnull Ref<EntityStore> reference, @Nonnull Store<EntityStore> store) {
/*  77 */     store.ensureComponent(reference, EntityStore.REGISTRY.getNonTickingComponentType());
/*  78 */     ((QueueResource)store.getResource(QueueResource.getResourceType())).queue.add(reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class QueueResource
/*     */     implements Resource<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     public static ResourceType<EntityStore, QueueResource> getResourceType() {
/*  91 */       return NPCPlugin.get().getNewSpawnStartTickingQueueResourceType();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  97 */     private final ObjectList<Ref<EntityStore>> queue = (ObjectList<Ref<EntityStore>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Resource<EntityStore> clone() {
/* 104 */       QueueResource queue = new QueueResource();
/* 105 */       queue.queue.addAll(this.queue);
/* 106 */       return queue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NewSpawnStartTickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */