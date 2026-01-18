/*    */ package org.jline.style;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import org.jline.utils.StyleResolver;
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
/*    */ public class StyleResolver
/*    */   extends StyleResolver
/*    */ {
/*    */   private final StyleSource source;
/*    */   private final String group;
/*    */   
/*    */   public StyleResolver(StyleSource source, String group) {
/* 67 */     super(s -> source.get(group, s));
/* 68 */     this.source = Objects.<StyleSource>requireNonNull(source);
/* 69 */     this.group = Objects.<String>requireNonNull(group);
/*    */   }
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
/*    */   public StyleSource getSource() {
/* 82 */     return this.source;
/*    */   }
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
/*    */   public String getGroup() {
/* 95 */     return this.group;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyleResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */