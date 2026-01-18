/*     */ package com.hypixel.hytale.server.core.modules.entity.tracker;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityUpdate
/*     */ {
/*     */   @Nonnull
/* 147 */   private final StampedLock removeLock = new StampedLock();
/*     */   
/*     */   @Nonnull
/*     */   private final EnumSet<ComponentUpdateType> removed;
/*     */   
/*     */   @Nonnull
/* 153 */   private final StampedLock updatesLock = new StampedLock();
/*     */   
/*     */   @Nonnull
/*     */   private final List<ComponentUpdate> updates;
/*     */ 
/*     */   
/*     */   public EntityUpdate() {
/* 160 */     this.removed = EnumSet.noneOf(ComponentUpdateType.class);
/* 161 */     this.updates = (List<ComponentUpdate>)new ObjectArrayList();
/*     */   }
/*     */   
/*     */   public EntityUpdate(@Nonnull EntityUpdate other) {
/* 165 */     this.removed = EnumSet.copyOf(other.removed);
/* 166 */     this.updates = (List<ComponentUpdate>)new ObjectArrayList(other.updates);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityUpdate clone() {
/* 172 */     return new EntityUpdate(this);
/*     */   }
/*     */   
/*     */   public void queueRemove(@Nonnull ComponentUpdateType type) {
/* 176 */     long stamp = this.removeLock.writeLock();
/*     */     try {
/* 178 */       this.removed.add(type);
/*     */     } finally {
/* 180 */       this.removeLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void queueUpdate(@Nonnull ComponentUpdate update) {
/* 185 */     long stamp = this.updatesLock.writeLock();
/*     */     try {
/* 187 */       this.updates.add(update);
/*     */     } finally {
/* 189 */       this.updatesLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ComponentUpdateType[] toRemovedArray() {
/* 195 */     return this.removed.isEmpty() ? null : (ComponentUpdateType[])this.removed.toArray(x$0 -> new ComponentUpdateType[x$0]);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ComponentUpdate[] toUpdatesArray() {
/* 200 */     return this.updates.isEmpty() ? null : (ComponentUpdate[])this.updates.toArray(x$0 -> new ComponentUpdate[x$0]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\tracker\EntityTrackerSystems$EntityUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */