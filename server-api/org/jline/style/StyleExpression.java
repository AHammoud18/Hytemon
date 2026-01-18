/*     */ package org.jline.style;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.AttributedStringBuilder;
/*     */ import org.jline.utils.AttributedStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StyleExpression
/*     */ {
/*     */   private final StyleResolver resolver;
/*     */   
/*     */   public StyleExpression() {
/*  56 */     this(new StyleResolver(new NopStyleSource(), ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StyleExpression(StyleResolver resolver) {
/*  70 */     this.resolver = Objects.<StyleResolver>requireNonNull(resolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void evaluate(AttributedStringBuilder buff, String expression) {
/*  94 */     Objects.requireNonNull(buff);
/*  95 */     Objects.requireNonNull(expression);
/*     */     
/*  97 */     String translated = InterpolationHelper.substVars(expression, this::style, false);
/*  98 */     buff.appendAnsi(translated);
/*     */   }
/*     */   
/*     */   private String style(String key) {
/* 102 */     int idx = key.indexOf(' ');
/* 103 */     if (idx > 0) {
/* 104 */       String spec = key.substring(0, idx);
/* 105 */       String value = key.substring(idx + 1);
/* 106 */       AttributedStyle style = this.resolver.resolve(spec);
/* 107 */       return (new AttributedStringBuilder()).style(style).ansiAppend(value).toAnsi();
/*     */     } 
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedString evaluate(String expression) {
/* 132 */     AttributedStringBuilder buff = new AttributedStringBuilder();
/* 133 */     evaluate(buff, expression);
/* 134 */     return buff.toAttributedString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyleExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */