/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.Stack;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class SpatialQueryDebug
/*    */ {
/* 10 */   private final StringBuilder builder = new StringBuilder();
/* 11 */   private String indent = "";
/* 12 */   private Stack<String> scope = new Stack<>();
/*    */   
/*    */   public SpatialQueryDebug() {
/* 15 */     appendLine("SPATIAL QUERY DEBUG");
/*    */   }
/*    */ 
/*    */   
/*    */   public SpatialQueryDebug appendLine(String string) {
/* 20 */     HytaleLogger.getLogger().at(Level.INFO).log(this.indent + "| " + this.indent);
/* 21 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpatialQueryDebug indent(String scopeReason) {
/* 26 */     HytaleLogger.getLogger().at(Level.INFO).log(this.indent + "⮑ " + this.indent);
/* 27 */     this.indent += "  ";
/* 28 */     this.scope.add(scopeReason);
/* 29 */     return this;
/*    */   }
/*    */   
/*    */   public SpatialQueryDebug unindent() {
/* 33 */     if (this.indent.length() >= 2) {
/* 34 */       this.indent = this.indent.substring(0, this.indent.length() - 2);
/*    */     }
/* 36 */     if (!this.scope.isEmpty()) {
/* 37 */       String scopeReason = this.scope.pop();
/* 38 */       HytaleLogger.getLogger().at(Level.INFO).log(this.indent + "⮐ (DONE) " + this.indent);
/*    */     } 
/*    */     
/* 41 */     return this;
/*    */   }
/*    */   
/*    */   public String fmt(Vector3d point) {
/* 45 */     return "(" + String.format("%.1f", new Object[] { Double.valueOf(point.x) }) + ", " + String.format("%.1f", new Object[] { Double.valueOf(point.y) }) + ", " + String.format("%.1f", new Object[] { Double.valueOf(point.z) }) + ")";
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return this.builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\SpatialQueryDebug.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */