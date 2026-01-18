/*     */ package com.hypixel.hytale.server.core.modules.entity.tracker;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.receiver.IPacketReceiver;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class EntityViewer
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   public int viewRadiusBlocks;
/*     */   public IPacketReceiver packetReceiver;
/*     */   public Set<Ref<EntityStore>> visible;
/*     */   public Map<Ref<EntityStore>, EntityTrackerSystems.EntityUpdate> updates;
/*     */   public Object2IntMap<Ref<EntityStore>> sent;
/*     */   public int lodExcludedCount;
/*     */   public int hiddenCount;
/*     */   
/*     */   public static ComponentType<EntityStore, EntityViewer> getComponentType() {
/* 210 */     return EntityModule.get().getEntityViewerComponentType();
/*     */   }
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
/*     */   public EntityViewer(int viewRadiusBlocks, IPacketReceiver packetReceiver) {
/* 251 */     this.viewRadiusBlocks = viewRadiusBlocks;
/* 252 */     this.packetReceiver = packetReceiver;
/*     */     
/* 254 */     this.visible = (Set<Ref<EntityStore>>)new ObjectOpenHashSet();
/* 255 */     this.updates = new ConcurrentHashMap<>();
/* 256 */     this.sent = (Object2IntMap<Ref<EntityStore>>)new Object2IntOpenHashMap();
/* 257 */     this.sent.defaultReturnValue(-1);
/*     */   }
/*     */   
/*     */   public EntityViewer(@Nonnull EntityViewer other) {
/* 261 */     this.viewRadiusBlocks = other.viewRadiusBlocks;
/* 262 */     this.packetReceiver = other.packetReceiver;
/* 263 */     this.visible = new HashSet<>(other.visible);
/* 264 */     this.updates = new ConcurrentHashMap<>(other.updates.size());
/* 265 */     for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityUpdate> entry : other.updates.entrySet()) {
/* 266 */       this.updates.put(entry.getKey(), ((EntityTrackerSystems.EntityUpdate)entry.getValue()).clone());
/*     */     }
/* 268 */     this.sent = (Object2IntMap<Ref<EntityStore>>)new Object2IntOpenHashMap(other.sent);
/* 269 */     this.sent.defaultReturnValue(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 275 */     return new EntityViewer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueRemove(Ref<EntityStore> ref, ComponentUpdateType type) {
/* 285 */     if (!this.visible.contains(ref)) throw new IllegalArgumentException("Entity is not visible!"); 
/* 286 */     ((EntityTrackerSystems.EntityUpdate)this.updates.computeIfAbsent(ref, k -> new EntityTrackerSystems.EntityUpdate())).queueRemove(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueUpdate(Ref<EntityStore> ref, ComponentUpdate update) {
/* 296 */     if (!this.visible.contains(ref)) throw new IllegalArgumentException("Entity is not visible!"); 
/* 297 */     ((EntityTrackerSystems.EntityUpdate)this.updates.computeIfAbsent(ref, k -> new EntityTrackerSystems.EntityUpdate())).queueUpdate(update);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\tracker\EntityTrackerSystems$EntityViewer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */