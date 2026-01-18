/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.parameter.DateTimeFormat;
/*     */ import com.google.common.flogger.parameter.Parameter;
/*     */ import com.google.common.flogger.parameter.ParameterVisitor;
/*     */ import com.google.common.flogger.parser.MessageBuilder;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.Formattable;
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
/*     */ public class BaseMessageFormatter
/*     */   extends MessageBuilder<StringBuilder>
/*     */   implements ParameterVisitor
/*     */ {
/*     */   private static final String MISSING_ARGUMENT_MESSAGE = "[ERROR: MISSING LOG ARGUMENT]";
/*     */   private static final String EXTRA_ARGUMENT_MESSAGE = " [ERROR: UNUSED LOG ARGUMENTS]";
/*     */   protected final Object[] args;
/*     */   protected final StringBuilder out;
/*     */   
/*     */   public static StringBuilder appendFormattedMessage(LogData data, StringBuilder out) {
/*  57 */     if (data.getTemplateContext() != null) {
/*     */       
/*  59 */       BaseMessageFormatter formatter = new BaseMessageFormatter(data.getTemplateContext(), data.getArguments(), out);
/*  60 */       out = (StringBuilder)formatter.build();
/*  61 */       if ((data.getArguments()).length > formatter.getExpectedArgumentCount())
/*     */       {
/*  63 */         out.append(" [ERROR: UNUSED LOG ARGUMENTS]");
/*     */       }
/*     */     } else {
/*  66 */       out.append(MessageUtils.safeToString(data.getLiteralArgument()));
/*     */     } 
/*  68 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private int literalStart = 0;
/*     */   
/*     */   protected BaseMessageFormatter(TemplateContext context, Object[] args, StringBuilder out) {
/*  79 */     super(context);
/*  80 */     this.args = (Object[])Checks.checkNotNull(args, "arguments");
/*  81 */     this.out = (StringBuilder)Checks.checkNotNull(out, "buffer");
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
/*     */   private static void appendFormatted(StringBuilder out, Object value, FormatChar format, FormatOptions options) {
/* 100 */     switch (format) {
/*     */       
/*     */       case STRING:
/* 103 */         if (!(value instanceof Formattable)) {
/* 104 */           if (options.isDefault()) {
/*     */             
/* 106 */             out.append(MessageUtils.safeToString(value));
/*     */             
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         } 
/* 112 */         MessageUtils.safeFormatTo((Formattable)value, out, options);
/*     */         return;
/*     */ 
/*     */       
/*     */       case DECIMAL:
/*     */       case BOOLEAN:
/* 118 */         if (options.isDefault()) {
/* 119 */           out.append(value);
/*     */           return;
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case HEX:
/* 127 */         if (options.filter(128, false, false).equals(options)) {
/*     */           
/* 129 */           MessageUtils.appendHex(out, (Number)value, options);
/*     */           return;
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case CHAR:
/* 137 */         if (options.isDefault()) {
/* 138 */           if (value instanceof Character) {
/* 139 */             out.append(value);
/*     */             
/*     */             return;
/*     */           } 
/* 143 */           int codePoint = ((Number)value).intValue();
/* 144 */           if (codePoint >>> 16 == 0) {
/* 145 */             out.append((char)codePoint);
/*     */             return;
/*     */           } 
/* 148 */           out.append(Character.toChars(codePoint));
/*     */           return;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     String formatString = format.getDefaultFormatString();
/* 158 */     if (!options.isDefault()) {
/* 159 */       char chr = format.getChar();
/* 160 */       if (options.shouldUpperCase())
/*     */       {
/* 162 */         chr = (char)(chr & 0xFFDF);
/*     */       }
/* 164 */       formatString = options.appendPrintfOptions(new StringBuilder("%")).append(chr).toString();
/*     */     } 
/* 166 */     out.append(String.format(MessageUtils.FORMAT_LOCALE, formatString, new Object[] { value }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addParameterImpl(int termStart, int termEnd, Parameter param) {
/* 171 */     getParser().unescape(this.out, getMessage(), this.literalStart, termStart);
/* 172 */     param.accept(this, this.args);
/* 173 */     this.literalStart = termEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder buildImpl() {
/* 178 */     getParser().unescape(this.out, getMessage(), this.literalStart, getMessage().length());
/* 179 */     return this.out;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(Object value, FormatChar format, FormatOptions options) {
/* 184 */     if (format.getType().canFormat(value)) {
/* 185 */       appendFormatted(this.out, value, format, options);
/*     */     } else {
/* 187 */       appendInvalid(this.out, value, format.getDefaultFormatString());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDateTime(Object value, DateTimeFormat format, FormatOptions options) {
/* 193 */     if (value instanceof java.util.Date || value instanceof java.util.Calendar || value instanceof Long) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       String formatString = options.appendPrintfOptions(new StringBuilder("%")).append(options.shouldUpperCase() ? 84 : 116).append(format.getChar()).toString();
/* 200 */       this.out.append(String.format(MessageUtils.FORMAT_LOCALE, formatString, new Object[] { value }));
/*     */     } else {
/* 202 */       appendInvalid(this.out, value, "%t" + format.getChar());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitPreformatted(Object value, String formatted) {
/* 209 */     this.out.append(formatted);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMissing() {
/* 214 */     this.out.append("[ERROR: MISSING LOG ARGUMENT]");
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNull() {
/* 219 */     this.out.append("null");
/*     */   }
/*     */   
/*     */   private static void appendInvalid(StringBuilder out, Object value, String formatString) {
/* 223 */     out.append("[INVALID: format=")
/* 224 */       .append(formatString)
/* 225 */       .append(", type=")
/* 226 */       .append(value.getClass().getCanonicalName())
/* 227 */       .append(", value=")
/* 228 */       .append(MessageUtils.safeToString(value))
/* 229 */       .append("]");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\BaseMessageFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */