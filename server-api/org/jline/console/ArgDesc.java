/*    */ package org.jline.console;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jline.utils.AttributedString;
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
/*    */ public class ArgDesc
/*    */ {
/*    */   private final String name;
/*    */   private final List<AttributedString> description;
/*    */   
/*    */   public ArgDesc(String name) {
/* 34 */     this(name, new ArrayList<>());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArgDesc(String name, List<AttributedString> description) {
/* 45 */     if (name.contains("\t") || name.contains(" ")) {
/* 46 */       throw new IllegalArgumentException("Bad argument name: " + name);
/*    */     }
/* 48 */     this.name = name;
/* 49 */     this.description = new ArrayList<>(description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 58 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<AttributedString> getDescription() {
/* 67 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<ArgDesc> doArgNames(List<String> names) {
/* 78 */     List<ArgDesc> out = new ArrayList<>();
/* 79 */     for (String n : names) {
/* 80 */       out.add(new ArgDesc(n));
/*    */     }
/* 82 */     return out;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\ArgDesc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */