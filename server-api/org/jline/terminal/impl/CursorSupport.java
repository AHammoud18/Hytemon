/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jline.terminal.Cursor;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.utils.Curses;
/*     */ import org.jline.utils.InfoCmp;
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
/*     */ public class CursorSupport
/*     */ {
/*     */   public static Cursor getCursorPosition(Terminal terminal, IntConsumer discarded) {
/*     */     try {
/*  85 */       String u6 = terminal.getStringCapability(InfoCmp.Capability.user6);
/*  86 */       String u7 = terminal.getStringCapability(InfoCmp.Capability.user7);
/*  87 */       if (u6 == null || u7 == null) {
/*  88 */         return null;
/*     */       }
/*     */       
/*  91 */       boolean inc1 = false;
/*  92 */       StringBuilder patb = new StringBuilder();
/*  93 */       int index = 0;
/*  94 */       while (index < u6.length()) {
/*     */         char ch;
/*  96 */         switch (ch = u6.charAt(index++)) {
/*     */           case '\\':
/*  98 */             switch (u6.charAt(index++)) {
/*     */               case 'E':
/*     */               case 'e':
/* 101 */                 patb.append("\\x1b");
/*     */                 continue;
/*     */             } 
/* 104 */             throw new IllegalArgumentException();
/*     */ 
/*     */           
/*     */           case '%':
/* 108 */             ch = u6.charAt(index++);
/* 109 */             switch (ch) {
/*     */               case '%':
/* 111 */                 patb.append('%');
/*     */                 continue;
/*     */               case 'i':
/* 114 */                 inc1 = true;
/*     */                 continue;
/*     */               case 'd':
/* 117 */                 patb.append("([0-9]+)");
/*     */                 continue;
/*     */             } 
/* 120 */             throw new IllegalArgumentException();
/*     */         } 
/*     */ 
/*     */         
/* 124 */         switch (ch) {
/*     */           case '[':
/* 126 */             patb.append('\\');
/*     */             break;
/*     */         } 
/* 129 */         patb.append(ch);
/*     */       } 
/*     */ 
/*     */       
/* 133 */       Pattern pattern = Pattern.compile(patb.toString());
/*     */       
/* 135 */       Curses.tputs(terminal.writer(), u7, new Object[0]);
/* 136 */       terminal.flush();
/* 137 */       StringBuilder sb = new StringBuilder();
/* 138 */       int start = 0;
/*     */       while (true) {
/* 140 */         int c = terminal.reader().read();
/* 141 */         if (c < 0) {
/* 142 */           return null;
/*     */         }
/* 144 */         sb.append((char)c);
/* 145 */         Matcher matcher = pattern.matcher(sb.substring(start));
/* 146 */         if (matcher.matches()) {
/* 147 */           int y = Integer.parseInt(matcher.group(1));
/* 148 */           int x = Integer.parseInt(matcher.group(2));
/* 149 */           if (inc1) {
/* 150 */             x--;
/* 151 */             y--;
/*     */           } 
/* 153 */           if (discarded != null) {
/* 154 */             for (int i = 0; i < start; i++) {
/* 155 */               discarded.accept(sb.charAt(i));
/*     */             }
/*     */           }
/* 158 */           return new Cursor(x, y);
/* 159 */         }  if (!matcher.hitEnd()) {
/* 160 */           start++;
/*     */         }
/*     */       } 
/* 163 */     } catch (IOException e) {
/* 164 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\CursorSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */