/*     */ package com.nimbusds.jose.shaded.gson;
/*     */ 
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormattingStyle
/*     */ {
/*     */   private final String newline;
/*     */   private final String indent;
/*     */   private final boolean spaceAfterSeparators;
/*  53 */   public static final FormattingStyle COMPACT = new FormattingStyle("", "", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final FormattingStyle PRETTY = new FormattingStyle("\n", "  ", true);
/*     */   
/*     */   private FormattingStyle(String newline, String indent, boolean spaceAfterSeparators) {
/*  67 */     Objects.requireNonNull(newline, "newline == null");
/*  68 */     Objects.requireNonNull(indent, "indent == null");
/*  69 */     if (!newline.matches("[\r\n]*")) {
/*  70 */       throw new IllegalArgumentException("Only combinations of \\n and \\r are allowed in newline.");
/*     */     }
/*     */     
/*  73 */     if (!indent.matches("[ \t]*")) {
/*  74 */       throw new IllegalArgumentException("Only combinations of spaces and tabs are allowed in indent.");
/*     */     }
/*     */     
/*  77 */     this.newline = newline;
/*  78 */     this.indent = indent;
/*  79 */     this.spaceAfterSeparators = spaceAfterSeparators;
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
/*     */   public FormattingStyle withNewline(String newline) {
/*  96 */     return new FormattingStyle(newline, this.indent, this.spaceAfterSeparators);
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
/*     */   public FormattingStyle withIndent(String indent) {
/* 109 */     return new FormattingStyle(this.newline, indent, this.spaceAfterSeparators);
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
/*     */   public FormattingStyle withSpaceAfterSeparators(boolean spaceAfterSeparators) {
/* 125 */     return new FormattingStyle(this.newline, this.indent, spaceAfterSeparators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewline() {
/* 135 */     return this.newline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndent() {
/* 145 */     return this.indent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesSpaceAfterSeparators() {
/* 154 */     return this.spaceAfterSeparators;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\FormattingStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */