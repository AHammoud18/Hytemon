/*    */ package com.hypixel.hytale.server.core.ui.builder;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class EventData extends Record {
/*    */   private final Map<String, String> events;
/*    */   
/*  9 */   public EventData(Map<String, String> events) { this.events = events; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/ui/builder/EventData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/builder/EventData; } public Map<String, String> events() { return this.events; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/ui/builder/EventData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/builder/EventData; }
/*    */   public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/ui/builder/EventData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/ui/builder/EventData;
/* 11 */     //   0	8	1	o	Ljava/lang/Object; } public EventData() { this((Map<String, String>)new Object2ObjectOpenHashMap()); }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EventData append(String key, String value) {
/* 16 */     return put(key, value);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public <T extends Enum<T>> EventData append(String key, @Nonnull T enumValue) {
/* 21 */     return put(key, enumValue.name());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public EventData put(String key, String value) {
/* 26 */     this.events.put(key, value);
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static EventData of(@Nonnull String key, @Nonnull String value) {
/* 32 */     HashMap<String, String> map = new HashMap<>();
/* 33 */     map.put(key, value);
/* 34 */     return new EventData(map);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\builder\EventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */