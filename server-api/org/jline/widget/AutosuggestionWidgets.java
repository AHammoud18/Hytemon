/*     */ package org.jline.widget;
/*     */ 
/*     */ import org.jline.reader.Buffer;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.impl.BufferImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutosuggestionWidgets
/*     */   extends Widgets
/*     */ {
/*     */   private boolean enabled = false;
/*     */   
/*     */   public AutosuggestionWidgets(LineReader reader) {
/*  36 */     super(reader);
/*  37 */     if (existsWidget("_autosuggest-forward-char")) {
/*  38 */       throw new IllegalStateException("AutosuggestionWidgets already created!");
/*     */     }
/*  40 */     addWidget("_autosuggest-forward-char", this::autosuggestForwardChar);
/*  41 */     addWidget("_autosuggest-end-of-line", this::autosuggestEndOfLine);
/*  42 */     addWidget("_autosuggest-forward-word", this::partialAccept);
/*  43 */     addWidget("autosuggest-toggle", this::toggleKeyBindings);
/*     */   }
/*     */   
/*     */   public void disable() {
/*  47 */     defaultBindings();
/*     */   }
/*     */   
/*     */   public void enable() {
/*  51 */     customBindings();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean partialAccept() {
/*  57 */     Buffer buffer = buffer();
/*  58 */     if (buffer.cursor() == buffer.length()) {
/*  59 */       int curPos = buffer.cursor();
/*  60 */       buffer.write(tailTip());
/*  61 */       buffer.cursor(curPos);
/*  62 */       replaceBuffer(buffer);
/*  63 */       callWidget("forward-word");
/*  64 */       BufferImpl bufferImpl = new BufferImpl();
/*  65 */       bufferImpl.write(buffer().substring(0, buffer().cursor()));
/*  66 */       replaceBuffer((Buffer)bufferImpl);
/*     */     } else {
/*  68 */       callWidget("forward-word");
/*     */     } 
/*  70 */     return true;
/*     */   }
/*     */   
/*     */   public boolean autosuggestForwardChar() {
/*  74 */     return accept("forward-char");
/*     */   }
/*     */   
/*     */   public boolean autosuggestEndOfLine() {
/*  78 */     return accept("end-of-line");
/*     */   }
/*     */   
/*     */   public boolean toggleKeyBindings() {
/*  82 */     if (this.enabled) {
/*  83 */       defaultBindings();
/*     */     } else {
/*  85 */       customBindings();
/*     */     } 
/*  87 */     return this.enabled;
/*     */   }
/*     */   
/*     */   private boolean accept(String widget) {
/*  91 */     Buffer buffer = buffer();
/*  92 */     if (buffer.cursor() == buffer.length()) {
/*  93 */       putString(tailTip());
/*     */     } else {
/*  95 */       callWidget(widget);
/*     */     } 
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void customBindings() {
/* 104 */     if (this.enabled) {
/*     */       return;
/*     */     }
/* 107 */     aliasWidget("_autosuggest-forward-char", "forward-char");
/* 108 */     aliasWidget("_autosuggest-end-of-line", "end-of-line");
/* 109 */     aliasWidget("_autosuggest-forward-word", "forward-word");
/* 110 */     this.enabled = true;
/* 111 */     setSuggestionType(LineReader.SuggestionType.HISTORY);
/*     */   }
/*     */   
/*     */   private void defaultBindings() {
/* 115 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 118 */     aliasWidget(".forward-char", "forward-char");
/* 119 */     aliasWidget(".end-of-line", "end-of-line");
/* 120 */     aliasWidget(".forward-word", "forward-word");
/* 121 */     this.enabled = false;
/* 122 */     setSuggestionType(LineReader.SuggestionType.NONE);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\widget\AutosuggestionWidgets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */