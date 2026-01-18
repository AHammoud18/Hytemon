/*     */ package com.hypixel.hytale.server.core.modules.entity.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.entity.EntitySnapshot;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
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
/*     */ public class SnapshotBuffer
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   private EntitySnapshot[] snapshots;
/*     */   
/*     */   public static ComponentType<EntityStore, SnapshotBuffer> getComponentType() {
/*  38 */     return EntityModule.get().getSnapshotBufferComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private int currentTickIndex = Integer.MIN_VALUE;
/*  47 */   private int oldestTickIndex = Integer.MIN_VALUE;
/*  48 */   private int currentIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntitySnapshot getSnapshotClamped(int tickIndex) {
/*  67 */     if (this.currentIndex == -1) throw new IllegalStateException("Snapshots not initialized");
/*     */     
/*  69 */     int relIndex = tickIndex - this.currentTickIndex;
/*  70 */     int maxRel = this.oldestTickIndex - this.currentTickIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (relIndex > 0) throw new IllegalArgumentException("Tick index is in the future"); 
/*  79 */     if (relIndex < maxRel) {
/*  80 */       relIndex = maxRel;
/*     */     }
/*     */     
/*  83 */     return getSnapshotRelative(relIndex);
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
/*     */   @Nullable
/*     */   public EntitySnapshot getSnapshot(int tickIndex) {
/*  97 */     if (this.currentIndex == -1) return null;
/*     */     
/*  99 */     int relIndex = tickIndex - this.currentTickIndex;
/* 100 */     int maxRel = this.oldestTickIndex - this.currentTickIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (relIndex > 0) throw new IllegalArgumentException("Tick index is in the future"); 
/* 109 */     if (relIndex < maxRel) return null;
/*     */     
/* 111 */     return getSnapshotRelative(relIndex);
/*     */   }
/*     */   
/*     */   private EntitySnapshot getSnapshotRelative(int relIndex) {
/* 115 */     int index = this.currentIndex + relIndex;
/* 116 */     index = (this.snapshots.length + index) % this.snapshots.length;
/* 117 */     return this.snapshots[index];
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
/*     */   public void storeSnapshot(int tickIndex, @Nonnull Vector3d position, @Nonnull Vector3f bodyRotation) {
/* 137 */     if (this.currentIndex != -1 && this.currentTickIndex != tickIndex - 1) {
/* 138 */       this.currentIndex = -1;
/* 139 */       this.currentTickIndex = Integer.MIN_VALUE;
/* 140 */       this.oldestTickIndex = Integer.MIN_VALUE;
/*     */     } 
/*     */     
/* 143 */     if (this.currentIndex == -1) this.oldestTickIndex = tickIndex; 
/* 144 */     this.currentTickIndex = tickIndex;
/*     */     
/* 146 */     this.currentIndex++;
/* 147 */     this.currentIndex %= this.snapshots.length;
/*     */     
/* 149 */     int maxRel = this.currentTickIndex - this.oldestTickIndex;
/* 150 */     if (maxRel >= this.snapshots.length) {
/* 151 */       this.oldestTickIndex++;
/*     */     }
/*     */     
/* 154 */     EntitySnapshot snapshot = this.snapshots[this.currentIndex];
/* 155 */     snapshot.init(position, bodyRotation);
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
/*     */   public void resize(int newLength) {
/* 168 */     if (newLength <= 0) throw new IllegalArgumentException("New size is too small: " + newLength); 
/* 169 */     if (this.snapshots != null && newLength == this.snapshots.length) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     if (this.snapshots == null) {
/* 174 */       this.snapshots = new EntitySnapshot[newLength];
/* 175 */       for (int i = 0; i < this.snapshots.length; i++) {
/* 176 */         this.snapshots[i] = new EntitySnapshot();
/*     */       }
/*     */     } else {
/* 179 */       int oldLength = this.snapshots.length;
/* 180 */       this.snapshots = Arrays.<EntitySnapshot>copyOf(this.snapshots, newLength);
/* 181 */       for (int i = oldLength; i < newLength; i++) {
/* 182 */         this.snapshots[i] = new EntitySnapshot();
/*     */       }
/*     */     } 
/*     */     
/* 186 */     this.currentIndex = -1;
/* 187 */     this.currentTickIndex = Integer.MIN_VALUE;
/* 188 */     this.oldestTickIndex = Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 192 */     return (this.currentIndex != -1);
/*     */   }
/*     */   
/*     */   public int getCurrentTickIndex() {
/* 196 */     return this.currentTickIndex;
/*     */   }
/*     */   
/*     */   public int getOldestTickIndex() {
/* 200 */     return this.oldestTickIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 206 */     SnapshotBuffer buffer = new SnapshotBuffer();
/* 207 */     if (this.snapshots == null || this.currentIndex == -1) return buffer; 
/* 208 */     buffer.resize(this.snapshots.length);
/* 209 */     for (int i = this.oldestTickIndex; i <= this.currentTickIndex; i++) {
/* 210 */       EntitySnapshot snap = getSnapshot(i);
/* 211 */       buffer.storeSnapshot(i, snap.getPosition(), snap.getBodyRotation());
/*     */     } 
/* 213 */     return buffer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\SnapshotBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */