/*     */ package org.bson;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public final class BsonRegularExpression
/*     */   extends BsonValue
/*     */ {
/*     */   private final String pattern;
/*     */   private final String options;
/*     */   
/*     */   public BsonRegularExpression(String pattern, String options) {
/*  40 */     this.pattern = (String)Assertions.notNull("pattern", pattern);
/*  41 */     this.options = (options == null) ? "" : sortOptionCharacters(options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonRegularExpression(String pattern) {
/*  50 */     this(pattern, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  55 */     return BsonType.REGULAR_EXPRESSION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/*  64 */     return this.pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOptions() {
/*  73 */     return this.options;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  78 */     if (this == o) {
/*  79 */       return true;
/*     */     }
/*  81 */     if (o == null || getClass() != o.getClass()) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     BsonRegularExpression that = (BsonRegularExpression)o;
/*     */     
/*  87 */     if (!this.options.equals(that.options)) {
/*  88 */       return false;
/*     */     }
/*  90 */     if (!this.pattern.equals(that.pattern)) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  99 */     int result = this.pattern.hashCode();
/* 100 */     result = 31 * result + this.options.hashCode();
/* 101 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     return "BsonRegularExpression{pattern='" + this.pattern + '\'' + ", options='" + this.options + '\'' + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String sortOptionCharacters(String options) {
/* 113 */     char[] chars = options.toCharArray();
/* 114 */     Arrays.sort(chars);
/* 115 */     return new String(chars);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonRegularExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */