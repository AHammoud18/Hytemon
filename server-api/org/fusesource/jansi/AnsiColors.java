/*    */ package org.fusesource.jansi;
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
/*    */ public enum AnsiColors
/*    */ {
/* 24 */   Colors16("16 colors"),
/* 25 */   Colors256("256 colors"),
/* 26 */   TrueColor("24-bit colors");
/*    */   
/*    */   private final String description;
/*    */   
/*    */   AnsiColors(String description) {
/* 31 */     this.description = description;
/*    */   }
/*    */   
/*    */   String getDescription() {
/* 35 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\AnsiColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */