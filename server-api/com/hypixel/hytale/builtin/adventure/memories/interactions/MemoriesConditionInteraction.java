/*     */ package com.hypixel.hytale.builtin.adventure.memories.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Int2ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.StringTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemoriesConditionInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<MemoriesConditionInteraction> CODEC;
/*     */   
/*     */   static {
/*  66 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MemoriesConditionInteraction.class, MemoriesConditionInteraction::new, ABSTRACT_CODEC).appendInherited(new KeyedCodec("Next", (Codec)new Int2ObjectMapCodec(Interaction.CHILD_ASSET_CODEC, it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap::new)), (o, v) -> o.next = v, o -> o.next, (o, p) -> o.next = p.next).documentation("The interaction to run if the player's memories level matches the key.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Failed", Interaction.CHILD_ASSET_CODEC), (o, v) -> o.failed = v, o -> o.failed, (o, p) -> o.failed = p.failed).documentation("The interaction to run if the player's memories level does not match any key.").add()).afterDecode(o -> { o.levelToLabel.defaultReturnValue(-1); o.sortedKeys = o.next.keySet().toIntArray(); Arrays.sort(o.sortedKeys); o.levelToLabel.clear(); for (int i = 0; i < o.sortedKeys.length; i++) o.levelToLabel.put(o.sortedKeys[i], i);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  72 */   private static final StringTag TAG_FAILED = StringTag.of("Failed");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  78 */   private Int2ObjectMap<String> next = Int2ObjectMaps.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int[] sortedKeys;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  88 */   private final Int2IntOpenHashMap levelToLabel = new Int2IntOpenHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String failed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  99 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 100 */     assert commandBuffer != null;
/*     */     
/* 102 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 103 */     int memoriesLevel = MemoriesPlugin.get().getMemoriesLevel(world.getGameplayConfig());
/*     */ 
/*     */     
/* 106 */     (context.getState()).chainingIndex = memoriesLevel;
/*     */     
/* 108 */     int labelIndex = this.levelToLabel.get(memoriesLevel);
/* 109 */     if (labelIndex == -1) {
/* 110 */       labelIndex = this.sortedKeys.length;
/* 111 */       (context.getState()).state = InteractionState.Failed;
/*     */     } else {
/* 113 */       (context.getState()).state = InteractionState.Finished;
/*     */     } 
/*     */     
/* 116 */     context.jump(context.getLabel(labelIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 121 */     int memoriesLevel = (context.getServerState()).chainingIndex;
/*     */     
/* 123 */     int labelIndex = this.levelToLabel.get(memoriesLevel);
/* 124 */     if (labelIndex == -1) {
/* 125 */       labelIndex = this.sortedKeys.length;
/* 126 */       (context.getState()).state = InteractionState.Failed;
/*     */     } else {
/* 128 */       (context.getState()).state = InteractionState.Finished;
/*     */     } 
/*     */     
/* 131 */     context.jump(context.getLabel(labelIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 136 */     Label end = builder.createUnresolvedLabel();
/* 137 */     Label[] labels = new Label[this.next.size() + 1];
/*     */     int i;
/* 139 */     for (i = 0; i < labels.length; i++) {
/* 140 */       labels[i] = builder.createUnresolvedLabel();
/*     */     }
/*     */     
/* 143 */     builder.addOperation((Operation)this, labels);
/* 144 */     builder.jump(end);
/*     */     
/* 146 */     for (i = 0; i < this.sortedKeys.length; i++) {
/* 147 */       int key = this.sortedKeys[i];
/* 148 */       builder.resolveLabel(labels[i]);
/* 149 */       Interaction interaction = Interaction.getInteractionOrUnknown((String)this.next.get(key));
/* 150 */       interaction.compile(builder);
/* 151 */       builder.jump(end);
/*     */     } 
/*     */ 
/*     */     
/* 155 */     int failedIndex = this.sortedKeys.length;
/* 156 */     builder.resolveLabel(labels[failedIndex]);
/*     */     
/* 158 */     if (this.failed != null) {
/* 159 */       Interaction interaction = Interaction.getInteractionOrUnknown(this.failed);
/* 160 */       interaction.compile(builder);
/*     */     } 
/*     */     
/* 163 */     builder.resolveLabel(end);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 169 */     return (Interaction)new com.hypixel.hytale.protocol.MemoriesConditionInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 174 */     super.configurePacket(packet);
/* 175 */     com.hypixel.hytale.protocol.MemoriesConditionInteraction p = (com.hypixel.hytale.protocol.MemoriesConditionInteraction)packet;
/* 176 */     p.memoriesNext = (Map)new Int2IntOpenHashMap(this.next.size());
/*     */     
/* 178 */     for (ObjectIterator<Int2ObjectMap.Entry<String>> objectIterator = this.next.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<String> e = objectIterator.next();
/* 179 */       p.memoriesNext.put(Integer.valueOf(e.getIntKey()), Integer.valueOf(Interaction.getInteractionIdOrUnknown((String)e.getValue()))); }
/*     */ 
/*     */     
/* 182 */     p.failed = Interaction.getInteractionIdOrUnknown(this.failed);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 187 */     if (this.next != null) {
/* 188 */       for (ObjectIterator<Int2ObjectMap.Entry<String>> objectIterator = this.next.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<String> entry = objectIterator.next();
/* 189 */         if (InteractionManager.walkInteraction(collector, context, new MemoriesTag(entry.getIntKey()), (String)entry.getValue())) {
/* 190 */           return true;
/*     */         } }
/*     */     
/*     */     }
/*     */     
/* 195 */     if (this.failed != null) {
/* 196 */       return InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_FAILED, this.failed);
/*     */     }
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/* 208 */     return WaitForDataFrom.Server;
/*     */   }
/*     */   private static final class MemoriesTag extends Record implements CollectorTag { private final int memoryLevel;
/* 211 */     private MemoriesTag(int memoryLevel) { this.memoryLevel = memoryLevel; } public int memoryLevel() { return this.memoryLevel; }
/*     */ 
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/adventure/memories/interactions/MemoriesConditionInteraction$MemoriesTag;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #211	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/adventure/memories/interactions/MemoriesConditionInteraction$MemoriesTag;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/adventure/memories/interactions/MemoriesConditionInteraction$MemoriesTag;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #211	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/adventure/memories/interactions/MemoriesConditionInteraction$MemoriesTag;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/adventure/memories/interactions/MemoriesConditionInteraction$MemoriesTag;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #211	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/adventure/memories/interactions/MemoriesConditionInteraction$MemoriesTag;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\interactions\MemoriesConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */