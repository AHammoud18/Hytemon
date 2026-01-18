/*    */ package com.hypixel.hytale.server.worldgen.zone;public final class ZoneDiscoveryConfig extends Record { private final boolean display;
/*    */   private final String zone;
/*    */   @Nullable
/*    */   private final String soundEventId;
/*    */   @Nullable
/*    */   private final String icon;
/*    */   private final boolean major;
/*    */   private final float duration;
/*    */   private final float fadeInDuration;
/*    */   private final float fadeOutDuration;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/zone/ZoneDiscoveryConfig;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/ZoneDiscoveryConfig;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/zone/ZoneDiscoveryConfig;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/zone/ZoneDiscoveryConfig;
/*    */   }
/*    */   
/* 20 */   public ZoneDiscoveryConfig(boolean display, String zone, @Nullable String soundEventId, @Nullable String icon, boolean major, float duration, float fadeInDuration, float fadeOutDuration) { this.display = display; this.zone = zone; this.soundEventId = soundEventId; this.icon = icon; this.major = major; this.duration = duration; this.fadeInDuration = fadeInDuration; this.fadeOutDuration = fadeOutDuration; } public boolean display() { return this.display; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/zone/ZoneDiscoveryConfig;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/zone/ZoneDiscoveryConfig;
/* 20 */     //   0	8	1	o	Ljava/lang/Object; } public String zone() { return this.zone; } @Nullable public String soundEventId() { return this.soundEventId; } @Nullable public String icon() { return this.icon; } public boolean major() { return this.major; } public float duration() { return this.duration; } public float fadeInDuration() { return this.fadeInDuration; } public float fadeOutDuration() { return this.fadeOutDuration; }
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
/*    */   @Nonnull
/* 35 */   public static final ZoneDiscoveryConfig DEFAULT = new ZoneDiscoveryConfig(false, "Void", null, null, true, 4.0F, 1.5F, 1.5F);
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
/*    */   @Nonnull
/*    */   public static ZoneDiscoveryConfig of(@Nullable Boolean display, @Nullable String zone, @Nullable String soundEventId, @Nullable String icon, @Nullable Boolean major, @Nullable Float duration, @Nullable Float fadeInDuration, @Nullable Float fadeOutDuration) {
/* 70 */     return new ZoneDiscoveryConfig(
/* 71 */         (display != null) ? display.booleanValue() : false, 
/* 72 */         (zone != null) ? zone : "Void", soundEventId, icon, 
/*    */ 
/*    */         
/* 75 */         (major != null) ? major.booleanValue() : true, 
/* 76 */         (duration != null) ? duration.floatValue() : 4.0F, 
/* 77 */         (fadeInDuration != null) ? fadeInDuration.floatValue() : 1.5F, 
/* 78 */         (fadeOutDuration != null) ? fadeOutDuration.floatValue() : 1.5F);
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\ZoneDiscoveryConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */