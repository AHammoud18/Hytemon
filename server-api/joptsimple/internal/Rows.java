/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rows
/*     */ {
/*     */   private final int overallWidth;
/*     */   private final int columnSeparatorWidth;
/*  41 */   private final List<Row> rows = new ArrayList<>();
/*     */   
/*     */   private int widthOfWidestOption;
/*     */   private int widthOfWidestDescription;
/*     */   
/*     */   public Rows(int overallWidth, int columnSeparatorWidth) {
/*  47 */     this.overallWidth = overallWidth;
/*  48 */     this.columnSeparatorWidth = columnSeparatorWidth;
/*     */   }
/*     */   
/*     */   public void add(String option, String description) {
/*  52 */     add(new Row(option, description));
/*     */   }
/*     */   
/*     */   private void add(Row row) {
/*  56 */     this.rows.add(row);
/*  57 */     this.widthOfWidestOption = Math.max(this.widthOfWidestOption, row.option.length());
/*  58 */     this.widthOfWidestDescription = Math.max(this.widthOfWidestDescription, row.description.length());
/*     */   }
/*     */   
/*     */   public void reset() {
/*  62 */     this.rows.clear();
/*  63 */     this.widthOfWidestOption = 0;
/*  64 */     this.widthOfWidestDescription = 0;
/*     */   }
/*     */   
/*     */   public void fitToWidth() {
/*  68 */     Columns columns = new Columns(optionWidth(), descriptionWidth());
/*     */     
/*  70 */     List<Row> fitted = new ArrayList<>();
/*  71 */     for (Row each : this.rows) {
/*  72 */       fitted.addAll(columns.fit(each));
/*     */     }
/*  74 */     reset();
/*     */     
/*  76 */     for (Row each : fitted)
/*  77 */       add(each); 
/*     */   }
/*     */   
/*     */   public String render() {
/*  81 */     StringBuilder buffer = new StringBuilder();
/*     */     
/*  83 */     for (Row each : this.rows) {
/*  84 */       pad(buffer, each.option, optionWidth()).append(Strings.repeat(' ', this.columnSeparatorWidth));
/*  85 */       pad(buffer, each.description, descriptionWidth()).append(Strings.LINE_SEPARATOR);
/*     */     } 
/*     */     
/*  88 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   private int optionWidth() {
/*  92 */     return Math.min((this.overallWidth - this.columnSeparatorWidth) / 2, this.widthOfWidestOption);
/*     */   }
/*     */   
/*     */   private int descriptionWidth() {
/*  96 */     return Math.min(this.overallWidth - optionWidth() - this.columnSeparatorWidth, this.widthOfWidestDescription);
/*     */   }
/*     */   
/*     */   private StringBuilder pad(StringBuilder buffer, String s, int length) {
/* 100 */     buffer.append(s).append(Strings.repeat(' ', length - s.length()));
/* 101 */     return buffer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\Rows.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */