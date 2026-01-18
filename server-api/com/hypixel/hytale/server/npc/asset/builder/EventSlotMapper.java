/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EventSlotMapper<EventType extends Enum<EventType>> {
/*    */   @Nonnull
/*    */   private final Map<EventType, IntSet> eventSets;
/*    */   @Nonnull
/*    */   private final Map<EventType, Int2IntMap> eventSlotMappings;
/* 18 */   private final Int2DoubleMap eventSlotRanges = (Int2DoubleMap)new Int2DoubleOpenHashMap();
/*    */   private int nextEventSlot;
/*    */   
/*    */   public EventSlotMapper(Class<EventType> classType, EventType[] types) {
/* 22 */     this.eventSets = new EnumMap<>(classType);
/* 23 */     this.eventSlotMappings = new EnumMap<>(classType);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Map<EventType, IntSet> getEventSets() {
/* 28 */     return this.eventSets;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Map<EventType, Int2IntMap> getEventSlotMappings() {
/* 33 */     return this.eventSlotMappings;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Int2DoubleMap getEventSlotRanges() {
/* 38 */     return this.eventSlotRanges;
/*    */   }
/*    */   
/*    */   public int getEventSlotCount() {
/* 42 */     return this.nextEventSlot;
/*    */   }
/*    */   
/*    */   public int getEventSlot(EventType type, int set, double maxRange) {
/* 46 */     ((IntSet)this.eventSets.computeIfAbsent(type, k -> new IntOpenHashSet())).add(set);
/* 47 */     Int2IntMap typeSlots = this.eventSlotMappings.computeIfAbsent(type, k -> {
/*    */           Int2IntOpenHashMap m = new Int2IntOpenHashMap();
/*    */           m.defaultReturnValue(-2147483648);
/*    */           return (Int2IntMap)m;
/*    */         });
/* 52 */     int slot = typeSlots.get(set);
/* 53 */     if (slot == Integer.MIN_VALUE) {
/* 54 */       slot = this.nextEventSlot++;
/* 55 */       typeSlots.put(set, slot);
/*    */     } 
/*    */     
/* 58 */     double currentRange = this.eventSlotRanges.getOrDefault(slot, Double.MIN_VALUE);
/* 59 */     if (currentRange == Double.MIN_VALUE || currentRange < maxRange) {
/* 60 */       this.eventSlotRanges.put(slot, maxRange);
/*    */     }
/* 62 */     return slot;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\EventSlotMapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */