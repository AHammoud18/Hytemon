/*     */ package org.jline.reader.impl;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import org.jline.reader.Highlighter;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.AttributedStringBuilder;
/*     */ import org.jline.utils.AttributedStyle;
/*     */ import org.jline.utils.WCWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHighlighter
/*     */   implements Highlighter
/*     */ {
/*     */   protected Pattern errorPattern;
/*  44 */   protected int errorIndex = -1;
/*     */ 
/*     */   
/*     */   public void setErrorPattern(Pattern errorPattern) {
/*  48 */     this.errorPattern = errorPattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setErrorIndex(int errorIndex) {
/*  53 */     this.errorIndex = errorIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributedString highlight(LineReader reader, String buffer) {
/*  58 */     int underlineStart = -1;
/*  59 */     int underlineEnd = -1;
/*  60 */     int negativeStart = -1;
/*  61 */     int negativeEnd = -1;
/*  62 */     boolean first = true;
/*  63 */     String search = reader.getSearchTerm();
/*  64 */     if (search != null && search.length() > 0) {
/*  65 */       underlineStart = buffer.indexOf(search);
/*  66 */       if (underlineStart >= 0) {
/*  67 */         underlineEnd = underlineStart + search.length() - 1;
/*     */       }
/*     */     } 
/*  70 */     if (reader.getRegionActive() != LineReader.RegionType.NONE) {
/*  71 */       negativeStart = reader.getRegionMark();
/*  72 */       negativeEnd = reader.getBuffer().cursor();
/*  73 */       if (negativeStart > negativeEnd) {
/*  74 */         int x = negativeEnd;
/*  75 */         negativeEnd = negativeStart;
/*  76 */         negativeStart = x;
/*     */       } 
/*  78 */       if (reader.getRegionActive() == LineReader.RegionType.LINE) {
/*  79 */         while (negativeStart > 0 && reader.getBuffer().atChar(negativeStart - 1) != 10) {
/*  80 */           negativeStart--;
/*     */         }
/*  82 */         while (negativeEnd < reader.getBuffer().length() - 1 && reader
/*  83 */           .getBuffer().atChar(negativeEnd + 1) != 10) {
/*  84 */           negativeEnd++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     AttributedStringBuilder sb = new AttributedStringBuilder();
/*  90 */     commandStyle(reader, sb, true);
/*  91 */     for (int i = 0; i < buffer.length(); i++) {
/*  92 */       if (i == underlineStart) {
/*  93 */         sb.style(AttributedStyle::underline);
/*     */       }
/*  95 */       if (i == negativeStart) {
/*  96 */         sb.style(AttributedStyle::inverse);
/*     */       }
/*  98 */       if (i == this.errorIndex) {
/*  99 */         sb.style(AttributedStyle::inverse);
/*     */       }
/*     */       
/* 102 */       char c = buffer.charAt(i);
/* 103 */       if (first && Character.isSpaceChar(c)) {
/* 104 */         first = false;
/* 105 */         commandStyle(reader, sb, false);
/*     */       } 
/* 107 */       if (c == '\t' || c == '\n') {
/* 108 */         sb.append(c);
/* 109 */       } else if (c < ' ') {
/* 110 */         sb.style(AttributedStyle::inverseNeg)
/* 111 */           .append('^')
/* 112 */           .append((char)(c + 64))
/* 113 */           .style(AttributedStyle::inverseNeg);
/*     */       } else {
/* 115 */         int w = WCWidth.wcwidth(c);
/* 116 */         if (w > 0) {
/* 117 */           sb.append(c);
/*     */         }
/*     */       } 
/* 120 */       if (i == underlineEnd) {
/* 121 */         sb.style(AttributedStyle::underlineOff);
/*     */       }
/* 123 */       if (i == negativeEnd) {
/* 124 */         sb.style(AttributedStyle::inverseOff);
/*     */       }
/* 126 */       if (i == this.errorIndex) {
/* 127 */         sb.style(AttributedStyle::inverseOff);
/*     */       }
/*     */     } 
/* 130 */     if (this.errorPattern != null) {
/* 131 */       sb.styleMatches(this.errorPattern, AttributedStyle.INVERSE);
/*     */     }
/* 133 */     return sb.toAttributedString();
/*     */   }
/*     */   
/*     */   protected void commandStyle(LineReader reader, AttributedStringBuilder sb, boolean enable) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\DefaultHighlighter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */