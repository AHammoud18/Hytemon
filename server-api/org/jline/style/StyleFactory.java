/*     */ package org.jline.style;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import org.jline.utils.AttributedString;
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
/*     */ public class StyleFactory
/*     */ {
/*     */   private final StyleResolver resolver;
/*     */   
/*     */   public StyleFactory(StyleResolver resolver) {
/*  68 */     this.resolver = Objects.<StyleResolver>requireNonNull(resolver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedString style(String style, String value) {
/* 101 */     Objects.requireNonNull(value);
/* 102 */     AttributedStyle astyle = this.resolver.resolve(style);
/* 103 */     return new AttributedString(value, astyle);
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
/*     */ 
/*     */   
/*     */   public AttributedString style(String style, String format, Object... params) {
/* 129 */     Objects.requireNonNull(format);
/* 130 */     Objects.requireNonNull(params);
/*     */     
/* 132 */     return style(style, String.format(format, params));
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
/*     */   public AttributedString evaluate(String expression) {
/* 156 */     Objects.requireNonNull(expression);
/* 157 */     return (new StyleExpression(this.resolver)).evaluate(expression);
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
/*     */   
/*     */   public AttributedString evaluate(String format, Object... params) {
/* 182 */     Objects.requireNonNull(format);
/* 183 */     Objects.requireNonNull(params);
/*     */     
/* 185 */     return evaluate(String.format(format, params));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */