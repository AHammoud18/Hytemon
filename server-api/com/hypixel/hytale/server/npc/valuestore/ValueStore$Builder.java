/*    */ package com.hypixel.hytale.server.npc.valuestore;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.SlotMapper;
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
/*    */ public class Builder
/*    */ {
/* 73 */   private final SlotMapper stringSlots = new SlotMapper();
/* 74 */   private final SlotMapper intSlots = new SlotMapper();
/* 75 */   private final SlotMapper doubleSlots = new SlotMapper();
/*    */   
/*    */   public int getStringSlot(String name) {
/* 78 */     return this.stringSlots.getSlot(name);
/*    */   }
/*    */   
/*    */   public int getIntSlot(String name) {
/* 82 */     return this.intSlots.getSlot(name);
/*    */   }
/*    */   
/*    */   public int getDoubleSlot(String name) {
/* 86 */     return this.doubleSlots.getSlot(name);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ValueStore build() {
/* 91 */     return new ValueStore(this.stringSlots.slotCount(), this.intSlots.slotCount(), this.doubleSlots.slotCount());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\valuestore\ValueStore$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */