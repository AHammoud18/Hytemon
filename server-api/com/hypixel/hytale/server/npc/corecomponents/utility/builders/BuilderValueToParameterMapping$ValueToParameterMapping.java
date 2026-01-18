/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility.builders;
/*     */ 
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class ValueToParameterMapping
/*     */ {
/*     */   private final ValueStore.Type type;
/*     */   private int fromValueSlot;
/*     */   private int toParameterSlot;
/*     */   private String toParameterSlotName;
/*     */   
/*     */   private ValueToParameterMapping(@Nonnull BuilderValueToParameterMapping builder, @Nullable BuilderSupport support) {
/*  98 */     this.type = builder.getType();
/*     */     
/* 100 */     if (support != null) {
/* 101 */       this.fromValueSlot = builder.getFromSlot(support);
/* 102 */       this.toParameterSlot = support.getParameterSlot(builder.getToParameter());
/*     */     } else {
/* 104 */       this.toParameterSlotName = builder.getToParameter();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ValueStore.Type getType() {
/* 109 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getFromValueSlot() {
/* 113 */     return this.fromValueSlot;
/*     */   }
/*     */   
/*     */   public int getToParameterSlot() {
/* 117 */     return this.toParameterSlot;
/*     */   }
/*     */   
/*     */   public String getToParameterSlotName() {
/* 121 */     return this.toParameterSlotName;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\builders\BuilderValueToParameterMapping$ValueToParameterMapping.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */