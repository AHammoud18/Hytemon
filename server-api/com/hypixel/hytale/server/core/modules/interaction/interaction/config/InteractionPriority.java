/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.PrioritySlot;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class InteractionPriority extends Record implements NetworkSerializable<InteractionPriority> {
/*    */   @Nullable
/*    */   private final Map<PrioritySlot, Integer> values;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/InteractionPriority;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/InteractionPriority;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/InteractionPriority;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/InteractionPriority;
/*    */   }
/*    */   
/* 18 */   public InteractionPriority(@Nullable Map<PrioritySlot, Integer> values) { this.values = values; } @Nullable public Map<PrioritySlot, Integer> values() { return this.values; }
/*    */   public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/InteractionPriority;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/config/InteractionPriority;
/* 19 */     //   0	8	1	o	Ljava/lang/Object; } public static final Codec<InteractionPriority> CODEC = new InteractionPriorityCodec();
/*    */   
/*    */   public InteractionPriority(int defaultValue) {
/* 22 */     this((defaultValue != 0) ? new EnumMap<>(Map.of(PrioritySlot.Default, Integer.valueOf(defaultValue))) : null);
/*    */   }
/*    */   
/*    */   public int getPriority(PrioritySlot slot) {
/* 26 */     if (this.values == null) return 0;
/*    */     
/* 28 */     Integer value = this.values.get(slot);
/* 29 */     if (value != null) return value.intValue();
/*    */     
/* 31 */     Integer defaultValue = this.values.get(PrioritySlot.Default);
/* 32 */     return (defaultValue != null) ? defaultValue.intValue() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public InteractionPriority toPacket() {
/* 38 */     InteractionPriority packet = new InteractionPriority();
/* 39 */     if (this.values != null && !this.values.isEmpty()) packet.values = new EnumMap<>(this.values); 
/* 40 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionPriority.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */