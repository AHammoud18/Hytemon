/*    */ package com.hypixel.hytale.server.core.modules.entity.player;public final class PlayerCreativeSettings extends Record { private final boolean allowNPCDetection; private final boolean respondToHit;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerCreativeSettings;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerCreativeSettings;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerCreativeSettings;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerCreativeSettings;
/*    */   }
/*    */   
/* 11 */   public PlayerCreativeSettings(boolean allowNPCDetection, boolean respondToHit) { this.allowNPCDetection = allowNPCDetection; this.respondToHit = respondToHit; } public boolean allowNPCDetection() { return this.allowNPCDetection; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerCreativeSettings;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerCreativeSettings;
/* 11 */     //   0	8	1	o	Ljava/lang/Object; } public boolean respondToHit() { return this.respondToHit; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerCreativeSettings() {
/* 19 */     this(false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PlayerCreativeSettings clone() {
/* 27 */     return new PlayerCreativeSettings(this.allowNPCDetection, this.respondToHit);
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerCreativeSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */