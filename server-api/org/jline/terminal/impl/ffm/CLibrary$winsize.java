/*    */ package org.jline.terminal.impl.ffm;
/*    */ 
/*    */ import java.lang.foreign.Arena;
/*    */ import java.lang.foreign.GroupLayout;
/*    */ import java.lang.foreign.MemoryLayout;
/*    */ import java.lang.foreign.MemorySegment;
/*    */ import java.lang.foreign.ValueLayout;
/*    */ import java.lang.invoke.VarHandle;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class winsize
/*    */ {
/* 51 */   static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_SHORT
/* 52 */         .withName("ws_row"), ValueLayout.JAVA_SHORT
/* 53 */         .withName("ws_col"), ValueLayout.JAVA_SHORT, ValueLayout.JAVA_SHORT });
/*    */   
/*    */   private static final VarHandle ws_col;
/* 56 */   private static final VarHandle ws_row = FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("ws_row") }); static {
/* 57 */     ws_col = FfmTerminalProvider.lookupVarHandle(LAYOUT, new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement("ws_col") });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   private final MemorySegment seg = Arena.ofAuto().allocate(LAYOUT);
/*    */ 
/*    */   
/*    */   winsize(short ws_col, short ws_row) {
/* 67 */     this();
/* 68 */     ws_col(ws_col);
/* 69 */     ws_row(ws_row);
/*    */   }
/*    */   
/*    */   MemorySegment segment() {
/* 73 */     return this.seg;
/*    */   }
/*    */   
/*    */   short ws_col() {
/* 77 */     return ws_col.get(this.seg);
/*    */   }
/*    */   
/*    */   void ws_col(short col) {
/* 81 */     ws_col.set(this.seg, col);
/*    */   }
/*    */   
/*    */   short ws_row() {
/* 85 */     return ws_row.get(this.seg);
/*    */   }
/*    */   
/*    */   void ws_row(short row) {
/* 89 */     ws_row.set(this.seg, row);
/*    */   }
/*    */   
/*    */   winsize() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\CLibrary$winsize.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */