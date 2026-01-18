/*    */ package com.hypixel.hytale.builtin.asseteditor;
/*    */ 
/*    */ public final class AssetPath extends Record {
/*    */   @Nonnull
/*    */   private final String packId;
/*    */   @Nonnull
/*    */   private final Path path;
/*    */   
/*  9 */   public AssetPath(@Nonnull String packId, @Nonnull Path path) { this.packId = packId; this.path = path; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/asseteditor/AssetPath;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/asseteditor/AssetPath; } @Nonnull public String packId() { return this.packId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/asseteditor/AssetPath;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/asseteditor/AssetPath; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/asseteditor/AssetPath;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/asseteditor/AssetPath;
/*  9 */     //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Path path() { return this.path; }
/* 10 */    public static final AssetPath EMPTY_PATH = new AssetPath("", Path.of("", new String[0]));
/*    */   
/*    */   public AssetPath(com.hypixel.hytale.protocol.packets.asseteditor.AssetPath assetPath) {
/* 13 */     this(assetPath.pack, Path.of(assetPath.path, new String[0]));
/*    */   }
/*    */   
/*    */   public com.hypixel.hytale.protocol.packets.asseteditor.AssetPath toPacket() {
/* 17 */     return new com.hypixel.hytale.protocol.packets.asseteditor.AssetPath(this.packId, PathUtil.toUnixPathString(this.path));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\AssetPath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */