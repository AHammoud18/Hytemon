/*    */ package com.hypixel.hytale.server.spawning.suppression.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSuppressionEntry
/*    */   implements Component<ChunkStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final List<SuppressionSpan> suppressionSpans;
/*    */   
/*    */   public static ComponentType<ChunkStore, ChunkSuppressionEntry> getComponentType() {
/* 24 */     return SpawningPlugin.get().getChunkSuppressionEntryComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkSuppressionEntry(@Nonnull List<SuppressionSpan> suppressionSpans) {
/* 31 */     this.suppressionSpans = Collections.unmodifiableList(suppressionSpans);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<SuppressionSpan> getSuppressionSpans() {
/* 36 */     return this.suppressionSpans;
/*    */   }
/*    */   
/*    */   public boolean containsOnly(UUID suppressorId) {
/* 40 */     return (this.suppressionSpans.size() == 1 && ((SuppressionSpan)this.suppressionSpans.getFirst()).getSuppressorId().equals(suppressorId));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuppressingRoleAt(int roleIndex, int yPosition) {
/* 45 */     for (int i = 0; i < this.suppressionSpans.size(); i++) {
/* 46 */       SuppressionSpan span = this.suppressionSpans.get(i);
/*    */       
/* 48 */       if (span.minY > yPosition) return false;
/*    */       
/* 50 */       if (span.maxY >= yPosition)
/*    */       {
/*    */         
/* 53 */         if (span.includesRole(roleIndex)) return true;  } 
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<ChunkStore> clone() {
/* 62 */     return new ChunkSuppressionEntry(this.suppressionSpans);
/*    */   }
/*    */   
/*    */   public static class SuppressionSpan {
/*    */     private final UUID suppressorId;
/*    */     private final int minY;
/*    */     private final int maxY;
/*    */     @Nullable
/*    */     private final IntSet suppressedRoles;
/*    */     
/*    */     public SuppressionSpan(UUID suppressorId, int minY, int maxY, @Nullable IntSet suppressedRoles) {
/* 73 */       this.suppressorId = suppressorId;
/* 74 */       this.minY = minY;
/* 75 */       this.maxY = maxY;
/* 76 */       this.suppressedRoles = (suppressedRoles != null) ? IntSets.unmodifiable(suppressedRoles) : null;
/*    */     }
/*    */     
/*    */     public UUID getSuppressorId() {
/* 80 */       return this.suppressorId;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public IntSet getSuppressedRoles() {
/* 85 */       return this.suppressedRoles;
/*    */     }
/*    */     
/*    */     public int getMinY() {
/* 89 */       return this.minY;
/*    */     }
/*    */     
/*    */     public int getMaxY() {
/* 93 */       return this.maxY;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean includesRole(int roleIndex) {
/* 98 */       return (this.suppressedRoles == null || this.suppressedRoles.contains(roleIndex));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\component\ChunkSuppressionEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */