/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public final class TextFormatParseLocation
/*    */ {
/* 20 */   public static final TextFormatParseLocation EMPTY = new TextFormatParseLocation(-1, -1);
/*    */ 
/*    */   
/*    */   private final int line;
/*    */ 
/*    */   
/*    */   private final int column;
/*    */ 
/*    */   
/*    */   static TextFormatParseLocation create(int line, int column) {
/* 30 */     if (line == -1 && column == -1) {
/* 31 */       return EMPTY;
/*    */     }
/* 33 */     if (line < 0 || column < 0) {
/* 34 */       throw new IllegalArgumentException(
/* 35 */           String.format("line and column values must be >= 0: line %d, column: %d", new Object[] { Integer.valueOf(line), Integer.valueOf(column) }));
/*    */     }
/* 37 */     return new TextFormatParseLocation(line, column);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private TextFormatParseLocation(int line, int column) {
/* 44 */     this.line = line;
/* 45 */     this.column = column;
/*    */   }
/*    */   
/*    */   public int getLine() {
/* 49 */     return this.line;
/*    */   }
/*    */   
/*    */   public int getColumn() {
/* 53 */     return this.column;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return String.format("ParseLocation{line=%d, column=%d}", new Object[] { Integer.valueOf(this.line), Integer.valueOf(this.column) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 64 */     if (o == this) {
/* 65 */       return true;
/*    */     }
/* 67 */     if (!(o instanceof TextFormatParseLocation)) {
/* 68 */       return false;
/*    */     }
/* 70 */     TextFormatParseLocation that = (TextFormatParseLocation)o;
/* 71 */     return (this.line == that.getLine() && this.column == that.getColumn());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     int[] values = { this.line, this.column };
/* 77 */     return Arrays.hashCode(values);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\TextFormatParseLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */