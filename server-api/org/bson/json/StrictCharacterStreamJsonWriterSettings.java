/*     */ package org.bson.json;
/*     */ 
/*     */ import org.bson.assertions.Assertions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StrictCharacterStreamJsonWriterSettings
/*     */ {
/*     */   private final boolean indent;
/*     */   private final String newLineCharacters;
/*     */   private final String indentCharacters;
/*     */   private final int maxLength;
/*     */   
/*     */   public static Builder builder() {
/*  40 */     return new Builder();
/*     */   }
/*     */   
/*     */   private StrictCharacterStreamJsonWriterSettings(Builder builder) {
/*  44 */     this.indent = builder.indent;
/*  45 */     this.newLineCharacters = (builder.newLineCharacters != null) ? builder.newLineCharacters : System.getProperty("line.separator");
/*  46 */     this.indentCharacters = builder.indentCharacters;
/*  47 */     this.maxLength = builder.maxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIndent() {
/*  57 */     return this.indent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewLineCharacters() {
/*  66 */     return this.newLineCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndentCharacters() {
/*  75 */     return this.indentCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLength() {
/*  85 */     return this.maxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private boolean indent;
/*     */ 
/*     */     
/*  95 */     private String newLineCharacters = System.getProperty("line.separator");
/*  96 */     private String indentCharacters = "  ";
/*     */ 
/*     */ 
/*     */     
/*     */     private int maxLength;
/*     */ 
/*     */ 
/*     */     
/*     */     public StrictCharacterStreamJsonWriterSettings build() {
/* 105 */       return new StrictCharacterStreamJsonWriterSettings(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder indent(boolean indent) {
/* 115 */       this.indent = indent;
/* 116 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder newLineCharacters(String newLineCharacters) {
/* 126 */       Assertions.notNull("newLineCharacters", newLineCharacters);
/* 127 */       this.newLineCharacters = newLineCharacters;
/* 128 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder indentCharacters(String indentCharacters) {
/* 138 */       Assertions.notNull("indentCharacters", indentCharacters);
/* 139 */       this.indentCharacters = indentCharacters;
/* 140 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder maxLength(int maxLength) {
/* 151 */       this.maxLength = maxLength;
/* 152 */       return this;
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\StrictCharacterStreamJsonWriterSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */