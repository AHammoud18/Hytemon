/*    */ package com.hypixel.hytale.server.worldgen.prefab;
/*    */ public final class PrefabCategory extends Record { private final String name;
/*    */   private final int priority;
/*    */   public static final String FILENAME = "PrefabCategories.json";
/*    */   public static final int MIN_PRIORITY = -2147483648;
/*    */   public static final int MAX_PRIORITY = 2147483647;
/*    */   
/*  8 */   public PrefabCategory(String name, int priority) { this.name = name; this.priority = priority; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/prefab/PrefabCategory;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/prefab/PrefabCategory; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/prefab/PrefabCategory;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/prefab/PrefabCategory; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/prefab/PrefabCategory;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/prefab/PrefabCategory;
/*  8 */     //   0	8	1	o	Ljava/lang/Object; } public int priority() { return this.priority; }
/*    */ 
/*    */ 
/*    */   
/* 12 */   public static final PrefabCategory NONE = new PrefabCategory("None", -2147483648);
/* 13 */   public static final PrefabCategory UNIQUE = new PrefabCategory("Unique", 2147483647);
/*    */   
/*    */   public static void parse(@Nullable JsonElement json, BiConsumer<String, PrefabCategory> consumer) {
/* 16 */     if (json == null || !json.isJsonObject()) {
/*    */       return;
/*    */     }
/*    */     
/* 20 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)json.getAsJsonObject().entrySet()) {
/* 21 */       String name = entry.getKey();
/* 22 */       JsonElement value = entry.getValue();
/*    */       
/* 24 */       if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
/* 25 */         throw new Error(String.format("Invalid prefab category priority for '%s'. Must be an integer", new Object[] { name }));
/*    */       }
/*    */       
/* 28 */       consumer.accept(name, new PrefabCategory(name, value.getAsInt()));
/*    */     } 
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\PrefabCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */