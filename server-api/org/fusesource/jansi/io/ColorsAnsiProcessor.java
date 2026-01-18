/*     */ package org.fusesource.jansi.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.fusesource.jansi.AnsiColors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorsAnsiProcessor
/*     */   extends AnsiProcessor
/*     */ {
/*     */   private final AnsiColors colors;
/*     */   
/*     */   public ColorsAnsiProcessor(OutputStream os, AnsiColors colors) {
/*  33 */     super(os);
/*  34 */     this.colors = colors;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processEscapeCommand(ArrayList<Object> options, int command) throws IOException {
/*  39 */     if (command == 109 && (this.colors == AnsiColors.Colors256 || this.colors == AnsiColors.Colors16)) {
/*     */       int i;
/*  41 */       boolean has38or48 = false;
/*  42 */       for (Object next : options) {
/*  43 */         if (next != null && next.getClass() != Integer.class) {
/*  44 */           throw new IllegalArgumentException();
/*     */         }
/*  46 */         Integer value = (Integer)next;
/*  47 */         i = has38or48 | ((value.intValue() == 38 || value.intValue() == 48) ? 1 : 0);
/*     */       } 
/*     */       
/*  50 */       if (i == 0) {
/*  51 */         return false;
/*     */       }
/*  53 */       StringBuilder sb = new StringBuilder(32);
/*  54 */       sb.append('\033').append('[');
/*  55 */       boolean first = true;
/*  56 */       Iterator<Object> optionsIterator = options.iterator();
/*  57 */       while (optionsIterator.hasNext()) {
/*  58 */         Object next = optionsIterator.next();
/*  59 */         if (next != null) {
/*  60 */           int value = ((Integer)next).intValue();
/*  61 */           if (value == 38 || value == 48) {
/*     */             
/*  63 */             int arg2or5 = getNextOptionInt(optionsIterator);
/*  64 */             if (arg2or5 == 2) {
/*     */               
/*  66 */               int r = getNextOptionInt(optionsIterator);
/*  67 */               int g = getNextOptionInt(optionsIterator);
/*  68 */               int b = getNextOptionInt(optionsIterator);
/*  69 */               if (this.colors == AnsiColors.Colors256) {
/*  70 */                 int j = Colors.roundRgbColor(r, g, b, 256);
/*  71 */                 if (!first) {
/*  72 */                   sb.append(';');
/*     */                 }
/*  74 */                 first = false;
/*  75 */                 sb.append(value);
/*  76 */                 sb.append(';');
/*  77 */                 sb.append(5);
/*  78 */                 sb.append(';');
/*  79 */                 sb.append(j); continue;
/*     */               } 
/*  81 */               int col = Colors.roundRgbColor(r, g, b, 16);
/*  82 */               if (!first) {
/*  83 */                 sb.append(';');
/*     */               }
/*  85 */               first = false;
/*  86 */               sb.append(
/*  87 */                   (value == 38) ? (
/*  88 */                   (col >= 8) ? (90 + col - 8) : (30 + col)) : (
/*  89 */                   (col >= 8) ? (100 + col - 8) : (40 + col))); continue;
/*     */             } 
/*  91 */             if (arg2or5 == 5) {
/*     */               
/*  93 */               int paletteIndex = getNextOptionInt(optionsIterator);
/*  94 */               if (this.colors == AnsiColors.Colors256) {
/*  95 */                 if (!first) {
/*  96 */                   sb.append(';');
/*     */                 }
/*  98 */                 first = false;
/*  99 */                 sb.append(value);
/* 100 */                 sb.append(';');
/* 101 */                 sb.append(5);
/* 102 */                 sb.append(';');
/* 103 */                 sb.append(paletteIndex); continue;
/*     */               } 
/* 105 */               int col = Colors.roundColor(paletteIndex, 16);
/* 106 */               if (!first) {
/* 107 */                 sb.append(';');
/*     */               }
/* 109 */               first = false;
/* 110 */               sb.append(
/* 111 */                   (value == 38) ? (
/* 112 */                   (col >= 8) ? (90 + col - 8) : (30 + col)) : (
/* 113 */                   (col >= 8) ? (100 + col - 8) : (40 + col)));
/*     */               continue;
/*     */             } 
/* 116 */             throw new IllegalArgumentException();
/*     */           } 
/*     */           
/* 119 */           if (!first) {
/* 120 */             sb.append(';');
/*     */           }
/* 122 */           first = false;
/* 123 */           sb.append(value);
/*     */         } 
/*     */       } 
/*     */       
/* 127 */       sb.append('m');
/* 128 */       this.os.write(sb.toString().getBytes());
/* 129 */       return true;
/*     */     } 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processOperatingSystemCommand(ArrayList<Object> options) {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processCharsetSelect(ArrayList<Object> options) {
/* 143 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\io\ColorsAnsiProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */