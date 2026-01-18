/*    */ package com.hypixel.hytale.builtin.adventure.memories.component;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.memories.memories.Memory;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class PlayerMemories
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<PlayerMemories> CODEC;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerMemories.class, PlayerMemories::new).append(new KeyedCodec("Capacity", (Codec)Codec.INTEGER), (playerMemories, integer) -> playerMemories.memoriesCapacity = integer.intValue(), playerMemories -> Integer.valueOf(playerMemories.memoriesCapacity)).add()).append(new KeyedCodec("Memories", (Codec)new ArrayCodec((Codec)Memory.CODEC, x$0 -> new Memory[x$0])), (playerMemories, memories) -> { if (memories == null) return;  Collections.addAll(playerMemories.memories, memories); }playerMemories -> (Memory[])playerMemories.memories.toArray(())).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, PlayerMemories> getComponentType() {
/* 42 */     return MemoriesPlugin.get().getPlayerMemoriesComponentType();
/*    */   }
/*    */ 
/*    */   
/* 46 */   private final Set<Memory> memories = new LinkedHashSet<>();
/*    */   
/*    */   private int memoriesCapacity;
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 52 */     PlayerMemories playerMemories = new PlayerMemories();
/* 53 */     playerMemories.memories.addAll(this.memories);
/* 54 */     playerMemories.memoriesCapacity = this.memoriesCapacity;
/* 55 */     return playerMemories;
/*    */   }
/*    */   
/*    */   public int getMemoriesCapacity() {
/* 59 */     return this.memoriesCapacity;
/*    */   }
/*    */   
/*    */   public void setMemoriesCapacity(int memoriesCapacity) {
/* 63 */     this.memoriesCapacity = memoriesCapacity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean recordMemory(Memory memory) {
/* 73 */     if (this.memories.size() >= this.memoriesCapacity) return false; 
/* 74 */     return this.memories.add(memory);
/*    */   }
/*    */   
/*    */   public boolean hasMemories() {
/* 78 */     return !this.memories.isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean takeMemories(@Nonnull Set<Memory> outMemories) {
/* 86 */     boolean result = outMemories.addAll(this.memories);
/* 87 */     this.memories.clear();
/* 88 */     return result;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Set<Memory> getRecordedMemories() {
/* 93 */     return Collections.unmodifiableSet(this.memories);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\component\PlayerMemories.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */