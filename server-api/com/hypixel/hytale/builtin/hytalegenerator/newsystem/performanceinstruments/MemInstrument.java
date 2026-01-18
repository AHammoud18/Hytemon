/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public interface MemInstrument {
/*    */   public static final long BYTES_IN_MEGABYTES = 1000000L;
/*    */   public static final long INT_SIZE = 4L;
/*    */   public static final long DOUBLE_SIZE = 8L;
/*    */   public static final long BOOLEAN_SIZE = 1L;
/*    */   public static final long OBJECT_REFERENCE_SIZE = 8L;
/*    */   public static final long OBJECT_HEADER_SIZE = 16L;
/*    */   public static final long ARRAY_HEADER_SIZE = 16L;
/*    */   public static final long CLASS_OBJECT_SIZE = 128L;
/*    */   public static final long ARRAYLIST_OBJECT_SIZE = 24L;
/*    */   public static final long VECTOR3I_SIZE = 28L;
/*    */   public static final long VECTOR3D_SIZE = 40L;
/*    */   public static final long HASHMAP_ENTRY_SIZE = 32L;
/*    */   
/*    */   @Nonnull
/*    */   Report getMemoryUsage();
/*    */   
/*    */   public static final class Report extends Record {
/*    */     public long size_bytes() {
/* 23 */       return this.size_bytes;
/*    */     } private final long size_bytes; public Report(long size_bytes) {
/* 25 */       assert size_bytes >= 0L;
/*    */       this.size_bytes = size_bytes;
/*    */     }
/*    */     
/*    */     public final boolean equals(Object o) {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/performanceinstruments/MemInstrument$Report;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/performanceinstruments/MemInstrument$Report;
/*    */       //   0	8	1	o	Ljava/lang/Object;
/*    */     }
/*    */     
/*    */     public final int hashCode() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/performanceinstruments/MemInstrument$Report;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/performanceinstruments/MemInstrument$Report;
/*    */     }
/*    */     
/*    */     public final String toString() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/performanceinstruments/MemInstrument$Report;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/performanceinstruments/MemInstrument$Report;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\performanceinstruments\MemInstrument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */