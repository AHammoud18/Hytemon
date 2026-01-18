/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotMapper
/*    */ {
/*    */   public static final int NO_SLOT = -2147483648;
/* 17 */   private final Object2IntMap<String> mappings = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*    */   @Nullable
/*    */   private final Int2ObjectMap<String> nameMap;
/*    */   private int nextSlot;
/*    */   
/*    */   public SlotMapper() {
/* 23 */     this(false);
/*    */   }
/*    */   
/*    */   public SlotMapper(boolean trackNames) {
/* 27 */     this.nameMap = trackNames ? (Int2ObjectMap<String>)new Int2ObjectOpenHashMap() : null;
/* 28 */     this.mappings.defaultReturnValue(-2147483648);
/*    */   }
/*    */   
/*    */   public int getSlot(String name) {
/* 32 */     int slot = this.mappings.getInt(name);
/* 33 */     if (slot == Integer.MIN_VALUE) {
/* 34 */       slot = this.nextSlot++;
/* 35 */       this.mappings.put(name, slot);
/* 36 */       if (this.nameMap != null) this.nameMap.put(slot, name); 
/*    */     } 
/* 38 */     return slot;
/*    */   }
/*    */   
/*    */   public int slotCount() {
/* 42 */     return this.mappings.size();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Object2IntMap<String> getSlotMappings() {
/* 47 */     if (this.mappings.isEmpty()) return null; 
/* 48 */     return this.mappings;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Int2ObjectMap<String> getNameMap() {
/* 53 */     return this.nameMap;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\SlotMapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */