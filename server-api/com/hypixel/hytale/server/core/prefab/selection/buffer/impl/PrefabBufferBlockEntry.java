/*    */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabBufferBlockEntry
/*    */ {
/* 14 */   public static final PrefabBufferBlockEntry[] EMPTY_ARRAY = new PrefabBufferBlockEntry[0];
/*    */   
/*    */   public final int y;
/*    */   
/*    */   public String blockTypeKey;
/*    */   
/*    */   public int blockId;
/*    */   public float chance;
/*    */   @Nullable
/*    */   public Holder<ChunkStore> state;
/*    */   public int fluidId;
/*    */   public byte fluidLevel;
/*    */   public byte supportValue;
/*    */   public int filler;
/*    */   public int rotation;
/*    */   
/*    */   public PrefabBufferBlockEntry(int y) {
/* 31 */     this(y, 0, "Empty");
/*    */   }
/*    */   
/*    */   public PrefabBufferBlockEntry(int y, int blockId, String blockTypeKey) {
/* 35 */     this(y, blockId, blockTypeKey, 1.0F);
/*    */   }
/*    */   
/*    */   public PrefabBufferBlockEntry(int y, int blockId, String blockTypeKey, float chance) {
/* 39 */     this(y, blockId, blockTypeKey, chance, null, 0, (byte)0, (byte)0, 0, 0);
/*    */   }
/*    */   
/*    */   public PrefabBufferBlockEntry(int y, int blockId, String blockTypeKey, float chance, Holder<ChunkStore> state, int fluidId, byte fluidLevel, byte supportValue, int rotation, int filler) {
/* 43 */     this.y = y;
/* 44 */     this.blockId = blockId;
/* 45 */     this.blockTypeKey = blockTypeKey;
/* 46 */     this.chance = chance;
/* 47 */     this.state = state;
/* 48 */     this.fluidId = fluidId;
/* 49 */     this.fluidLevel = fluidLevel;
/* 50 */     this.supportValue = supportValue;
/* 51 */     this.rotation = rotation;
/* 52 */     this.filler = filler;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\PrefabBufferBlockEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */