/*    */ package com.hypixel.hytale.server.core.prefab;
/*    */ 
/*    */ public final class PrefabEntry extends Record {
/*    */   @Nonnull
/*    */   private final Path path;
/*    */   @Nonnull
/*    */   private final Path relativePath;
/*    */   @Nullable
/*    */   private final AssetPack pack;
/*    */   @Nonnull
/*    */   private final String displayName;
/*    */   
/* 13 */   public PrefabEntry(@Nonnull Path path, @Nonnull Path relativePath, @Nullable AssetPack pack, @Nonnull String displayName) { this.path = path; this.relativePath = relativePath; this.pack = pack; this.displayName = displayName; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/prefab/PrefabEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/PrefabEntry; } @Nonnull public Path path() { return this.path; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/prefab/PrefabEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/PrefabEntry; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/prefab/PrefabEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/prefab/PrefabEntry;
/* 13 */     //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Path relativePath() { return this.relativePath; } @Nullable public AssetPack pack() { return this.pack; } @Nonnull public String displayName() { return this.displayName; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabEntry(@Nonnull Path path, @Nonnull Path relativePath, @Nullable AssetPack pack) {
/* 20 */     this(path, relativePath, pack, buildDisplayName(relativePath, pack));
/*    */   }
/*    */   
/*    */   public boolean isFromBasePack() {
/* 24 */     return (this.pack != null && this.pack.equals(AssetModule.get().getBaseAssetPack()));
/*    */   }
/*    */   
/*    */   public boolean isFromAssetPack() {
/* 28 */     return (this.pack != null);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getPackName() {
/* 33 */     return (this.pack != null) ? this.pack.getName() : "Server";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getFileName() {
/* 38 */     return this.path.getFileName().toString();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getDisplayNameWithPack() {
/* 43 */     if (this.pack == null || isFromBasePack()) {
/* 44 */       return getFileName();
/*    */     }
/* 46 */     return "[" + this.pack.getName() + "] " + getFileName();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private static String buildDisplayName(@Nonnull Path relativePath, @Nullable AssetPack pack) {
/* 51 */     String fileName = relativePath.getFileName().toString();
/* 52 */     if (pack == null || pack.equals(AssetModule.get().getBaseAssetPack())) {
/* 53 */       return fileName;
/*    */     }
/* 55 */     return "[" + pack.getName() + "] " + fileName;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\PrefabEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */