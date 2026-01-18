/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.InteractionConfiguration;
/*     */ import com.hypixel.hytale.protocol.InteractionPriority;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.PrioritySlot;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionConfiguration implements NetworkSerializable<InteractionConfiguration> {
/*  22 */   public static final InteractionConfiguration DEFAULT = new InteractionConfiguration();
/*  23 */   public static final InteractionConfiguration DEFAULT_WEAPON = new InteractionConfiguration(false);
/*     */   
/*  25 */   private static final Object2FloatMap<GameMode> DEFAULT_USE_DISTANCE = (Object2FloatMap<GameMode>)new Object2FloatOpenHashMap<GameMode>()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<InteractionConfiguration> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  68 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionConfiguration.class, InteractionConfiguration::new).appendInherited(new KeyedCodec("DisplayOutlines", (Codec)Codec.BOOLEAN), (o, i) -> o.displayOutlines = i.booleanValue(), o -> Boolean.valueOf(o.displayOutlines), (o, p) -> o.displayOutlines = p.displayOutlines).add()).appendInherited(new KeyedCodec("DebugOutlines", (Codec)Codec.BOOLEAN), (o, i) -> o.debugOutlines = i.booleanValue(), o -> Boolean.valueOf(o.debugOutlines), (o, p) -> o.debugOutlines = p.debugOutlines).add()).appendInherited(new KeyedCodec("UseDistance", (Codec)new EnumMapCodec(GameMode.class, (Codec)Codec.FLOAT, () -> new Object2FloatOpenHashMap(DEFAULT_USE_DISTANCE), false)), (o, i) -> o.useDistance = Object2FloatMaps.unmodifiable((Object2FloatMap)i), o -> o.useDistance, (o, p) -> o.useDistance = p.useDistance).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("AllEntities", (Codec)Codec.BOOLEAN), (o, i) -> o.allEntities = i.booleanValue(), o -> Boolean.valueOf(o.allEntities), (o, p) -> o.allEntities = p.allEntities).add()).appendInherited(new KeyedCodec("Priorities", (Codec)new EnumMapCodec(InteractionType.class, InteractionPriority.CODEC, Object2ObjectOpenHashMap::new, false)), (o, v) -> o.priorities = v, o -> o.priorities, (o, p) -> o.priorities = p.priorities).documentation("Configures the priority values for given interaction types on this item when two or more items are equipped.").add()).build();
/*     */   }
/*     */   protected boolean displayOutlines = true;
/*     */   protected boolean debugOutlines;
/*  72 */   protected Object2FloatMap<GameMode> useDistance = DEFAULT_USE_DISTANCE;
/*     */   
/*     */   protected boolean allEntities;
/*     */   
/*     */   @Nullable
/*     */   protected Map<InteractionType, InteractionPriority> priorities;
/*     */ 
/*     */   
/*     */   public InteractionConfiguration(boolean displayOutlines) {
/*  81 */     this.displayOutlines = displayOutlines;
/*     */   }
/*     */   
/*     */   public int getPriorityFor(InteractionType interactionType, PrioritySlot slot) {
/*  85 */     if (this.priorities == null) return 0;
/*     */     
/*  87 */     InteractionPriority priority = this.priorities.get(interactionType);
/*  88 */     if (priority == null) return 0;
/*     */     
/*  90 */     return priority.getPriority(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionConfiguration toPacket() {
/*  96 */     InteractionConfiguration packet = new InteractionConfiguration();
/*  97 */     packet.displayOutlines = this.displayOutlines;
/*  98 */     packet.debugOutlines = this.debugOutlines;
/*  99 */     packet.useDistance = (Map)this.useDistance;
/* 100 */     packet.allEntities = this.allEntities;
/*     */     
/* 102 */     if (this.priorities != null && !this.priorities.isEmpty()) {
/* 103 */       Object2ObjectOpenHashMap<InteractionType, InteractionPriority> packetPriorities = new Object2ObjectOpenHashMap();
/* 104 */       for (Map.Entry<InteractionType, InteractionPriority> entry : this.priorities.entrySet()) packetPriorities.put(entry.getKey(), ((InteractionPriority)entry.getValue()).toPacket()); 
/* 105 */       packet.priorities = (Map)packetPriorities;
/*     */     } 
/*     */     
/* 108 */     return packet;
/*     */   }
/*     */   
/*     */   public InteractionConfiguration() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionConfiguration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */