/*     */ package org.jline.jansi.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.jline.jansi.AnsiColors;
/*     */ 
/*     */ 
/*     */ 
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
/*  26 */     super(os);
/*  27 */     this.colors = colors;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processEscapeCommand(ArrayList<Object> options, int command) throws IOException {
/*  32 */     if (command == 109 && (this.colors == AnsiColors.Colors256 || this.colors == AnsiColors.Colors16)) {
/*     */       int i;
/*  34 */       boolean has38or48 = false;
/*  35 */       for (Object next : options) {
/*  36 */         if (next != null && next.getClass() != Integer.class) {
/*  37 */           throw new IllegalArgumentException();
/*     */         }
/*  39 */         Integer value = (Integer)next;
/*  40 */         i = has38or48 | ((value.intValue() == 38 || value.intValue() == 48) ? 1 : 0);
/*     */       } 
/*     */       
/*  43 */       if (i == 0) {
/*  44 */         return false;
/*     */       }
/*  46 */       StringBuilder sb = new StringBuilder(32);
/*  47 */       sb.append('\033').append('[');
/*  48 */       boolean first = true;
/*  49 */       Iterator<Object> optionsIterator = options.iterator();
/*  50 */       while (optionsIterator.hasNext()) {
/*  51 */         Object next = optionsIterator.next();
/*  52 */         if (next != null) {
/*  53 */           int value = ((Integer)next).intValue();
/*  54 */           if (value == 38 || value == 48) {
/*     */             
/*  56 */             int arg2or5 = getNextOptionInt(optionsIterator);
/*  57 */             if (arg2or5 == 2) {
/*     */               
/*  59 */               int r = getNextOptionInt(optionsIterator);
/*  60 */               int g = getNextOptionInt(optionsIterator);
/*  61 */               int b = getNextOptionInt(optionsIterator);
/*  62 */               if (this.colors == AnsiColors.Colors256) {
/*  63 */                 int j = Colors.roundRgbColor(r, g, b, 256);
/*  64 */                 if (!first) {
/*  65 */                   sb.append(';');
/*     */                 }
/*  67 */                 first = false;
/*  68 */                 sb.append(value);
/*  69 */                 sb.append(';');
/*  70 */                 sb.append(5);
/*  71 */                 sb.append(';');
/*  72 */                 sb.append(j); continue;
/*     */               } 
/*  74 */               int col = Colors.roundRgbColor(r, g, b, 16);
/*  75 */               if (!first) {
/*  76 */                 sb.append(';');
/*     */               }
/*  78 */               first = false;
/*  79 */               sb.append(
/*  80 */                   (value == 38) ? (
/*  81 */                   (col >= 8) ? (90 + col - 8) : (30 + col)) : (
/*  82 */                   (col >= 8) ? (100 + col - 8) : (40 + col))); continue;
/*     */             } 
/*  84 */             if (arg2or5 == 5) {
/*     */               
/*  86 */               int paletteIndex = getNextOptionInt(optionsIterator);
/*  87 */               if (this.colors == AnsiColors.Colors256) {
/*  88 */                 if (!first) {
/*  89 */                   sb.append(';');
/*     */                 }
/*  91 */                 first = false;
/*  92 */                 sb.append(value);
/*  93 */                 sb.append(';');
/*  94 */                 sb.append(5);
/*  95 */                 sb.append(';');
/*  96 */                 sb.append(paletteIndex); continue;
/*     */               } 
/*  98 */               int col = Colors.roundColor(paletteIndex, 16);
/*  99 */               if (!first) {
/* 100 */                 sb.append(';');
/*     */               }
/* 102 */               first = false;
/* 103 */               sb.append(
/* 104 */                   (value == 38) ? (
/* 105 */                   (col >= 8) ? (90 + col - 8) : (30 + col)) : (
/* 106 */                   (col >= 8) ? (100 + col - 8) : (40 + col)));
/*     */               continue;
/*     */             } 
/* 109 */             throw new IllegalArgumentException();
/*     */           } 
/*     */           
/* 112 */           if (!first) {
/* 113 */             sb.append(';');
/*     */           }
/* 115 */           first = false;
/* 116 */           sb.append(value);
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       sb.append('m');
/* 121 */       this.os.write(sb.toString().getBytes());
/* 122 */       return true;
/*     */     } 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processOperatingSystemCommand(ArrayList<Object> options) {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processCharsetSelect(ArrayList<Object> options) {
/* 136 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\io\ColorsAnsiProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */