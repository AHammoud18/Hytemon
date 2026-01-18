/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentInfo
/*    */ {
/*    */   private final String name;
/*    */   private final int index;
/*    */   private final int nestingDepth;
/* 17 */   private final List<String> fields = (List<String>)new ObjectArrayList();
/*    */   
/*    */   public ComponentInfo(String name, int index, int nestingDepth) {
/* 20 */     this.name = name;
/* 21 */     this.index = index;
/* 22 */     this.nestingDepth = nestingDepth;
/*    */   }
/*    */   
/*    */   public void addField(String field) {
/* 26 */     this.fields.add(field);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder(" ".repeat(this.nestingDepth));
/* 33 */     if (this.index > -1) sb.append("[").append(this.index).append("] "); 
/* 34 */     sb.append(this.name);
/* 35 */     String fieldIndent = " ".repeat(this.nestingDepth + 1);
/* 36 */     for (String field : this.fields) {
/* 37 */       sb.append('\n').append(fieldIndent).append(field);
/*    */     }
/* 39 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 43 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 47 */     return this.index;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<String> getFields() {
/* 52 */     return this.fields;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\ComponentInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */