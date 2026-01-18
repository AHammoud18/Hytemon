/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntPair;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConnectedBlockResult
/*     */ {
/*     */   private final String blockTypeKey;
/*     */   private final int rotationIndex;
/* 212 */   private final Map<Vector3i, ObjectIntPair<String>> additionalConnectedBlocks = (Map<Vector3i, ObjectIntPair<String>>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public ConnectedBlockResult(@Nonnull String blockTypeKey, int rotationIndex) {
/* 215 */     this.blockTypeKey = blockTypeKey;
/* 216 */     this.rotationIndex = rotationIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String blockTypeKey() {
/* 221 */     return this.blockTypeKey;
/*     */   }
/*     */   
/*     */   public int rotationIndex() {
/* 225 */     return this.rotationIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Vector3i, ObjectIntPair<String>> getAdditionalConnectedBlocks() {
/* 230 */     return this.additionalConnectedBlocks;
/*     */   }
/*     */   
/*     */   public void addAdditionalBlock(@Nonnull Vector3i offset, @Nonnull String blockTypeKey, int rotationIndex) {
/* 234 */     this.additionalConnectedBlocks.put(offset, ObjectIntPair.of(blockTypeKey, rotationIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 239 */     if (obj == this) return true; 
/* 240 */     if (obj == null || obj.getClass() != getClass()) return false; 
/* 241 */     ConnectedBlockResult that = (ConnectedBlockResult)obj;
/* 242 */     return (Objects.equals(this.blockTypeKey, that.blockTypeKey) && this.rotationIndex == that.rotationIndex && 
/*     */       
/* 244 */       Objects.equals(this.additionalConnectedBlocks, that.additionalConnectedBlocks));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 249 */     return Objects.hash(new Object[] { this.blockTypeKey, Integer.valueOf(this.rotationIndex), this.additionalConnectedBlocks });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 254 */     return "ConnectedBlockResult[blockTypeKey=" + this.blockTypeKey + ", rotationIndex=" + this.rotationIndex + ", additionalConnectedBlocks=" + String.valueOf(this.additionalConnectedBlocks) + "]";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\ConnectedBlocksUtil$ConnectedBlockResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */