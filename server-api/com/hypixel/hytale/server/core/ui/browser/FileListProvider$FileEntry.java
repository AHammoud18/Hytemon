/*    */ package com.hypixel.hytale.server.core.ui.browser;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class FileEntry extends Record {
/*    */   @Nonnull
/*    */   private final String name;
/*    */   @Nonnull
/*    */   private final String displayName;
/*    */   private final boolean isDirectory;
/*    */   private final int matchScore;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/ui/browser/FileListProvider$FileEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileListProvider$FileEntry;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/ui/browser/FileListProvider$FileEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileListProvider$FileEntry;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/ui/browser/FileListProvider$FileEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileListProvider$FileEntry;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public FileEntry(@Nonnull String name, @Nonnull String displayName, boolean isDirectory, int matchScore)
/*    */   {
/* 27 */     this.name = name; this.displayName = displayName; this.isDirectory = isDirectory; this.matchScore = matchScore; } @Nonnull public String name() { return this.name; } @Nonnull public String displayName() { return this.displayName; } public boolean isDirectory() { return this.isDirectory; } public int matchScore() { return this.matchScore; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FileEntry(@Nonnull String name, boolean isDirectory) {
/* 34 */     this(name, name, isDirectory, 0);
/*    */   }
/*    */   
/*    */   public FileEntry(@Nonnull String name, @Nonnull String displayName, boolean isDirectory) {
/* 38 */     this(name, displayName, isDirectory, 0);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\browser\FileListProvider$FileEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */