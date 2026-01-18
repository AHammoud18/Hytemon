/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.text.BreakIterator;
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
/*     */ 
/*     */ class Columns
/*     */ {
/*     */   private static final int INDENT_WIDTH = 2;
/*     */   private final int optionWidth;
/*     */   private final int descriptionWidth;
/*     */   
/*     */   Columns(int optionWidth, int descriptionWidth) {
/*  46 */     this.optionWidth = optionWidth;
/*  47 */     this.descriptionWidth = descriptionWidth;
/*     */   }
/*     */   
/*     */   List<Row> fit(Row row) {
/*  51 */     List<String> options = piecesOf(row.option, this.optionWidth);
/*  52 */     List<String> descriptions = piecesOf(row.description, this.descriptionWidth);
/*     */     
/*  54 */     List<Row> rows = new ArrayList<>();
/*  55 */     for (int i = 0; i < Math.max(options.size(), descriptions.size()); i++) {
/*  56 */       rows.add(new Row(itemOrEmpty(options, i), itemOrEmpty(descriptions, i)));
/*     */     }
/*  58 */     return rows;
/*     */   }
/*     */   
/*     */   private static String itemOrEmpty(List<String> items, int index) {
/*  62 */     return (index >= items.size()) ? "" : items.get(index);
/*     */   }
/*     */   
/*     */   private List<String> piecesOf(String raw, int width) {
/*  66 */     List<String> pieces = new ArrayList<>();
/*     */     
/*  68 */     for (String each : raw.trim().split(Strings.LINE_SEPARATOR)) {
/*  69 */       pieces.addAll(piecesOfEmbeddedLine(each, width));
/*     */     }
/*  71 */     return pieces;
/*     */   }
/*     */   
/*     */   private List<String> piecesOfEmbeddedLine(String line, int width) {
/*  75 */     List<String> pieces = new ArrayList<>();
/*     */     
/*  77 */     BreakIterator words = BreakIterator.getLineInstance();
/*  78 */     words.setText(line);
/*     */     
/*  80 */     StringBuilder nextPiece = new StringBuilder();
/*     */     
/*  82 */     int start = words.first(); int end;
/*  83 */     for (end = words.next(); end != -1; start = end, end = words.next()) {
/*  84 */       nextPiece = processNextWord(line, nextPiece, start, end, width, pieces);
/*     */     }
/*  86 */     if (nextPiece.length() > 0) {
/*  87 */       pieces.add(nextPiece.toString());
/*     */     }
/*  89 */     return pieces;
/*     */   }
/*     */ 
/*     */   
/*     */   private StringBuilder processNextWord(String source, StringBuilder nextPiece, int start, int end, int width, List<String> pieces) {
/*  94 */     StringBuilder augmented = nextPiece;
/*     */     
/*  96 */     String word = source.substring(start, end);
/*  97 */     if (augmented.length() + word.length() > width) {
/*  98 */       pieces.add(augmented.toString().replaceAll("\\s+$", ""));
/*  99 */       augmented = (new StringBuilder(Strings.repeat(' ', 2))).append(word);
/*     */     } else {
/*     */       
/* 102 */       augmented.append(word);
/*     */     } 
/* 104 */     return augmented;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\Columns.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */