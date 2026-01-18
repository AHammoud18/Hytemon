/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceSlotMapper<T>
/*    */   extends SlotMapper
/*    */ {
/* 13 */   private final List<T> list = (List<T>)new ObjectArrayList();
/*    */   
/*    */   private final Supplier<T> slotSupplier;
/*    */   
/*    */   public ReferenceSlotMapper(Supplier<T> slotSupplier) {
/* 18 */     this.slotSupplier = slotSupplier;
/*    */   }
/*    */   
/*    */   public ReferenceSlotMapper(Supplier<T> slotSupplier, boolean trackNames) {
/* 22 */     super(trackNames);
/* 23 */     this.slotSupplier = slotSupplier;
/*    */   }
/*    */   
/*    */   public T getReference(String name) {
/* 27 */     int slot = getSlot(name);
/* 28 */     if (slot < this.list.size()) return this.list.get(slot);
/*    */     
/* 30 */     T object = this.slotSupplier.get();
/* 31 */     this.list.add(object);
/* 32 */     return object;
/*    */   }
/*    */   
/*    */   public List<T> getReferenceList() {
/* 36 */     return this.list;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\ReferenceSlotMapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */