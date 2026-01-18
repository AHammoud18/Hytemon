/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.MetadataKey;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*     */ public final class KeyValueFormatter
/*     */   implements MetadataKey.KeyValueHandler
/*     */ {
/*     */   private static final int NEWLINE_LIMIT = 1000;
/*  61 */   private static final Set<Class<?>> FUNDAMENTAL_TYPES = new HashSet<Class<?>>(
/*     */       
/*  63 */       Arrays.asList(new Class[] { Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class }));
/*     */ 
/*     */ 
/*     */   
/*     */   private final String prefix;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String suffix;
/*     */ 
/*     */   
/*     */   private final StringBuilder out;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void appendJsonFormattedKeyAndValue(String label, Object value, StringBuilder out) {
/*  79 */     out.append(label).append('=');
/*     */     
/*  81 */     if (value == null) {
/*     */       
/*  83 */       out.append(true);
/*  84 */     } else if (FUNDAMENTAL_TYPES.contains(value.getClass())) {
/*  85 */       out.append(value);
/*     */     } else {
/*  87 */       out.append('"');
/*  88 */       appendEscaped(out, value.toString());
/*  89 */       out.append('"');
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean haveSeenValues = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValueFormatter(String prefix, String suffix, StringBuilder out) {
/* 107 */     this.prefix = prefix;
/* 108 */     this.suffix = suffix;
/* 109 */     this.out = out;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(String label, @NullableDecl Object value) {
/* 114 */     if (this.haveSeenValues) {
/* 115 */       this.out.append(' ');
/*     */     } else {
/*     */       
/* 118 */       if (this.out.length() > 0) {
/* 119 */         this.out.append((this.out.length() > 1000 || this.out.indexOf("\n") != -1) ? 10 : 32);
/*     */       }
/* 121 */       this.out.append(this.prefix);
/* 122 */       this.haveSeenValues = true;
/*     */     } 
/* 124 */     appendJsonFormattedKeyAndValue(label, value, this.out);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/* 129 */     if (this.haveSeenValues) {
/* 130 */       this.out.append(this.suffix);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void appendEscaped(StringBuilder out, String s) {
/* 135 */     int start = 0;
/*     */     
/* 137 */     int idx = nextEscapableChar(s, start); while (true) { if (idx != -1)
/* 138 */       { out.append(s, start, idx);
/* 139 */         start = idx + 1;
/* 140 */         char c = s.charAt(idx);
/* 141 */         switch (c) {
/*     */           case '"':
/*     */           case '\\':
/*     */             break;
/*     */           
/*     */           case '\n':
/* 147 */             c = 'n';
/*     */             break;
/*     */           
/*     */           case '\r':
/* 151 */             c = 'r';
/*     */             break;
/*     */           
/*     */           case '\t':
/* 155 */             c = 't';
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 162 */             out.append('�'); idx = nextEscapableChar(s, start);
/*     */             continue;
/*     */         } 
/* 165 */         out.append("\\").append(c); } else { break; }
/*     */        idx = nextEscapableChar(s, start); }
/* 167 */      out.append(s, start, s.length());
/*     */   }
/*     */   
/*     */   private static int nextEscapableChar(String s, int n) {
/* 171 */     for (; n < s.length(); n++) {
/* 172 */       char c = s.charAt(n);
/* 173 */       if (c < ' ' || c == '"' || c == '\\') {
/* 174 */         return n;
/*     */       }
/*     */     } 
/* 177 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\KeyValueFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */