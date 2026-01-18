/*     */ package org.jline.reader.impl;
/*     */ 
/*     */ import java.util.ListIterator;
/*     */ import org.jline.reader.Expander;
/*     */ import org.jline.reader.History;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExpander
/*     */   implements Expander
/*     */ {
/*     */   public String expandHistory(History history, String line) {
/*  45 */     boolean inQuote = false;
/*  46 */     StringBuilder sb = new StringBuilder();
/*  47 */     boolean escaped = false;
/*  48 */     int unicode = 0;
/*  49 */     for (int i = 0; i < line.length(); i++) {
/*  50 */       char c = line.charAt(i);
/*  51 */       if (unicode > 0) {
/*  52 */         escaped = (--unicode >= 0);
/*  53 */         sb.append(c);
/*  54 */       } else if (escaped) {
/*  55 */         if (c == 'u') {
/*  56 */           unicode = 4;
/*     */         } else {
/*  58 */           escaped = false;
/*     */         } 
/*  60 */         sb.append(c);
/*  61 */       } else if (c == '\'') {
/*  62 */         inQuote = !inQuote;
/*  63 */         sb.append(c);
/*  64 */       } else if (inQuote) {
/*  65 */         sb.append(c);
/*     */       } else {
/*  67 */         switch (c) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case '\\':
/*  74 */             escaped = true;
/*  75 */             sb.append(c);
/*     */             break;
/*     */           case '!':
/*  78 */             if (i + 1 < line.length()) {
/*  79 */               int i1, idx; String sc, previous; int lastSpace; String ss; c = line.charAt(++i);
/*  80 */               boolean neg = false;
/*  81 */               String rep = null;
/*     */               
/*  83 */               switch (c) {
/*     */                 case '!':
/*  85 */                   if (history.size() == 0) {
/*  86 */                     throw new IllegalArgumentException("!!: event not found");
/*     */                   }
/*  88 */                   rep = history.get(history.index() - 1);
/*     */                   break;
/*     */                 case '#':
/*  91 */                   sb.append(sb.toString());
/*     */                   break;
/*     */                 case '?':
/*  94 */                   i1 = line.indexOf('?', i + 1);
/*  95 */                   if (i1 < 0) {
/*  96 */                     i1 = line.length();
/*     */                   }
/*  98 */                   sc = line.substring(i + 1, i1);
/*  99 */                   i = i1;
/* 100 */                   idx = searchBackwards(history, sc, history.index(), false);
/* 101 */                   if (idx < 0) {
/* 102 */                     throw new IllegalArgumentException("!?" + sc + ": event not found");
/*     */                   }
/* 104 */                   rep = history.get(idx);
/*     */                   break;
/*     */                 
/*     */                 case '$':
/* 108 */                   if (history.size() == 0) {
/* 109 */                     throw new IllegalArgumentException("!$: event not found");
/*     */                   }
/*     */                   
/* 112 */                   previous = history.get(history.index() - 1).trim();
/* 113 */                   lastSpace = previous.lastIndexOf(' ');
/* 114 */                   if (lastSpace != -1) {
/* 115 */                     rep = previous.substring(lastSpace + 1); break;
/*     */                   } 
/* 117 */                   rep = previous;
/*     */                   break;
/*     */                 
/*     */                 case '\t':
/*     */                 case ' ':
/* 122 */                   sb.append('!');
/* 123 */                   sb.append(c);
/*     */                   break;
/*     */                 case '-':
/* 126 */                   neg = true;
/* 127 */                   i++;
/*     */                 
/*     */                 case '0':
/*     */                 case '1':
/*     */                 case '2':
/*     */                 case '3':
/*     */                 case '4':
/*     */                 case '5':
/*     */                 case '6':
/*     */                 case '7':
/*     */                 case '8':
/*     */                 case '9':
/* 139 */                   i1 = i;
/* 140 */                   for (; i < line.length(); i++) {
/* 141 */                     c = line.charAt(i);
/* 142 */                     if (c < '0' || c > '9') {
/*     */                       break;
/*     */                     }
/*     */                   } 
/*     */                   try {
/* 147 */                     idx = Integer.parseInt(line.substring(i1, i));
/* 148 */                   } catch (NumberFormatException e) {
/* 149 */                     throw new IllegalArgumentException((
/* 150 */                         neg ? "!-" : "!") + line.substring(i1, i) + ": event not found");
/*     */                   } 
/* 152 */                   if (neg && idx > 0 && idx <= history.size()) {
/* 153 */                     rep = history.get(history.index() - idx); break;
/* 154 */                   }  if (!neg && idx > history
/* 155 */                     .index() - history.size() && idx <= history
/* 156 */                     .index()) {
/* 157 */                     rep = history.get(idx - 1); break;
/*     */                   } 
/* 159 */                   throw new IllegalArgumentException((
/* 160 */                       neg ? "!-" : "!") + line.substring(i1, i) + ": event not found");
/*     */ 
/*     */                 
/*     */                 default:
/* 164 */                   ss = line.substring(i);
/* 165 */                   i = line.length();
/* 166 */                   idx = searchBackwards(history, ss, history.index(), true);
/* 167 */                   if (idx < 0) {
/* 168 */                     throw new IllegalArgumentException("!" + ss + ": event not found");
/*     */                   }
/* 170 */                   rep = history.get(idx);
/*     */                   break;
/*     */               } 
/*     */               
/* 174 */               if (rep != null)
/* 175 */                 sb.append(rep); 
/*     */               break;
/*     */             } 
/* 178 */             sb.append(c);
/*     */             break;
/*     */           
/*     */           case '^':
/* 182 */             if (i == 0) {
/* 183 */               int i1 = line.indexOf('^', i + 1);
/* 184 */               int i2 = line.indexOf('^', i1 + 1);
/* 185 */               if (i2 < 0) {
/* 186 */                 i2 = line.length();
/*     */               }
/* 188 */               if (i1 > 0 && i2 > 0) {
/* 189 */                 String s1 = line.substring(i + 1, i1);
/* 190 */                 String s2 = line.substring(i1 + 1, i2);
/* 191 */                 String s = history.get(history.index() - 1).replace(s1, s2);
/* 192 */                 sb.append(s);
/* 193 */                 i = i2 + 1;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 197 */             sb.append(c);
/*     */             break;
/*     */           default:
/* 200 */             sb.append(c);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 205 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String expandVar(String word) {
/* 210 */     return word;
/*     */   }
/*     */   
/*     */   protected int searchBackwards(History history, String searchTerm, int startIndex, boolean startsWith) {
/* 214 */     ListIterator<History.Entry> it = history.iterator(startIndex);
/* 215 */     while (it.hasPrevious()) {
/* 216 */       History.Entry e = it.previous();
/* 217 */       if (startsWith) {
/* 218 */         if (e.line().startsWith(searchTerm))
/* 219 */           return e.index(); 
/*     */         continue;
/*     */       } 
/* 222 */       if (e.line().contains(searchTerm)) {
/* 223 */         return e.index();
/*     */       }
/*     */     } 
/*     */     
/* 227 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\DefaultExpander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */