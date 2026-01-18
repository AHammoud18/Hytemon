/*    */ package com.hypixel.hytale.server.spawning.suppression.component;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SuppressionSpan
/*    */ {
/*    */   private final UUID suppressorId;
/*    */   private final int minY;
/*    */   private final int maxY;
/*    */   @Nullable
/*    */   private final IntSet suppressedRoles;
/*    */   
/*    */   public SuppressionSpan(UUID suppressorId, int minY, int maxY, @Nullable IntSet suppressedRoles) {
/* 73 */     this.suppressorId = suppressorId;
/* 74 */     this.minY = minY;
/* 75 */     this.maxY = maxY;
/* 76 */     this.suppressedRoles = (suppressedRoles != null) ? IntSets.unmodifiable(suppressedRoles) : null;
/*    */   }
/*    */   
/*    */   public UUID getSuppressorId() {
/* 80 */     return this.suppressorId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public IntSet getSuppressedRoles() {
/* 85 */     return this.suppressedRoles;
/*    */   }
/*    */   
/*    */   public int getMinY() {
/* 89 */     return this.minY;
/*    */   }
/*    */   
/*    */   public int getMaxY() {
/* 93 */     return this.maxY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean includesRole(int roleIndex) {
/* 98 */     return (this.suppressedRoles == null || this.suppressedRoles.contains(roleIndex));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\component\ChunkSuppressionEntry$SuppressionSpan.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */