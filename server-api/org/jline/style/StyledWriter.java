/*     */ package org.jline.style;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.utils.AttributedString;
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
/*     */ public class StyledWriter
/*     */   extends PrintWriter
/*     */ {
/*     */   private final Terminal terminal;
/*     */   private final StyleExpression expression;
/*     */   
/*     */   public StyledWriter(Writer out, Terminal terminal, StyleResolver resolver, boolean autoFlush) {
/*  71 */     super(out, autoFlush);
/*  72 */     this.terminal = Objects.<Terminal>requireNonNull(terminal);
/*  73 */     this.expression = new StyleExpression(resolver);
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
/*     */   public StyledWriter(OutputStream out, Terminal terminal, StyleResolver resolver, boolean autoFlush) {
/*  92 */     super(out, autoFlush);
/*  93 */     this.terminal = Objects.<Terminal>requireNonNull(terminal);
/*  94 */     this.expression = new StyleExpression(resolver);
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
/*     */   public void write(@Nonnull String value) {
/* 111 */     AttributedString result = this.expression.evaluate(value);
/* 112 */     super.write(result.toAnsi(this.terminal));
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
/*     */   public PrintWriter format(@Nonnull String format, Object... args) {
/* 134 */     print(String.format(format, args));
/* 135 */     return this;
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
/*     */   public PrintWriter format(Locale locale, @Nonnull String format, Object... args) {
/* 156 */     print(String.format(locale, format, args));
/* 157 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyledWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */