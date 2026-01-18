/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle;
/*    */ 
/*    */ public final class TrackedBuffer extends Record implements MemInstrument {
/*    */   @Nonnull
/*    */   private final NBufferBundle.Tracker tracker;
/*    */   @Nonnull
/*    */   private final NBuffer buffer;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;
/*    */   }
/*    */   
/* 17 */   public TrackedBuffer(@Nonnull NBufferBundle.Tracker tracker, @Nonnull NBuffer buffer) { this.tracker = tracker; this.buffer = buffer; } @Nonnull public NBufferBundle.Tracker tracker() { return this.tracker; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;
/* 17 */     //   0	8	1	o	Ljava/lang/Object; } @Nonnull public NBuffer buffer() { return this.buffer; }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MemInstrument.Report getMemoryUsage() {
/* 23 */     long size_bytes = 16L + this.tracker.getMemoryUsage().size_bytes() + this.buffer.getMemoryUsage().size_bytes();
/* 24 */     return new MemInstrument.Report(size_bytes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\NBufferBundle$Grid$TrackedBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */