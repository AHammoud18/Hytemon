/*     */ package com.google.common.flogger.parser;
/*     */ 
/*     */ import com.google.common.flogger.backend.TemplateContext;
/*     */ import com.google.common.flogger.parameter.Parameter;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageBuilder<T>
/*     */ {
/*     */   private final TemplateContext context;
/*  36 */   private int pmask = 0;
/*     */ 
/*     */   
/*  39 */   private int maxIndex = -1;
/*     */   
/*     */   public MessageBuilder(TemplateContext context) {
/*  42 */     this.context = (TemplateContext)Checks.checkNotNull(context, "context");
/*     */   }
/*     */ 
/*     */   
/*     */   public final MessageParser getParser() {
/*  47 */     return this.context.getParser();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getMessage() {
/*  52 */     return this.context.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getExpectedArgumentCount() {
/*  60 */     return this.maxIndex + 1;
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
/*     */   public final void addParameter(int termStart, int termEnd, Parameter param) {
/*  78 */     if (param.getIndex() < 32) {
/*  79 */       this.pmask |= 1 << param.getIndex();
/*     */     }
/*  81 */     this.maxIndex = Math.max(this.maxIndex, param.getIndex());
/*  82 */     addParameterImpl(termStart, termEnd, param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void addParameterImpl(int paramInt1, int paramInt2, Parameter paramParameter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T buildImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T build() {
/* 111 */     getParser().parseImpl(this);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if ((this.pmask & this.pmask + 1) != 0 || (this.maxIndex > 31 && this.pmask != -1)) {
/* 117 */       int firstMissing = Integer.numberOfTrailingZeros(this.pmask ^ 0xFFFFFFFF);
/* 118 */       throw ParseException.generic(
/* 119 */           String.format("unreferenced arguments [first missing index=%d]", new Object[] { Integer.valueOf(firstMissing)
/* 120 */             }), getMessage());
/*     */     } 
/* 122 */     return buildImpl();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parser\MessageBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */