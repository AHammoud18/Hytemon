/*    */ package com.hypixel.hytale.server.core.ui.browser;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.ui.LocalizableString;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public final class RootEntry
/*    */   extends Record
/*    */ {
/*    */   @Nonnull
/*    */   private final LocalizableString displayName;
/*    */   @Nonnull
/*    */   private final Path path;
/*    */   
/*    */   @Nonnull
/*    */   public Path path() {
/* 32 */     return this.path; } @Nonnull public LocalizableString displayName() { return this.displayName; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;
/* 32 */     //   0	8	1	o	Ljava/lang/Object; } public RootEntry(@Nonnull LocalizableString displayName, @Nonnull Path path) { this.displayName = displayName; this.path = path; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry; }
/*    */   public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 34 */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry; } public RootEntry(@Nonnull String displayName, @Nonnull Path path) { this(LocalizableString.fromString(displayName), path); }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\browser\FileBrowserConfig$RootEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */