/*     */ package org.jline.keymap;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
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
/*     */ public class KeyMap<T>
/*     */ {
/*     */   public static final int KEYMAP_LENGTH = 128;
/*     */   public static final long DEFAULT_AMBIGUOUS_TIMEOUT = 1000L;
/*  60 */   private Object[] mapping = new Object[128];
/*  61 */   private T anotherKey = null;
/*     */   private T unicode;
/*     */   private T nomatch;
/*  64 */   private long ambiguousTimeout = 1000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Comparator<String> KEYSEQ_COMPARATOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String display(String key) {
/*  77 */     StringBuilder sb = new StringBuilder();
/*  78 */     sb.append("\"");
/*  79 */     for (int i = 0; i < key.length(); i++) {
/*  80 */       char c = key.charAt(i);
/*  81 */       if (c < ' ') {
/*  82 */         sb.append('^');
/*  83 */         sb.append((char)(c + 65 - 1));
/*  84 */       } else if (c == '') {
/*  85 */         sb.append("^?");
/*  86 */       } else if (c == '^' || c == '\\') {
/*  87 */         sb.append('\\').append(c);
/*  88 */       } else if (c >= '') {
/*  89 */         sb.append(String.format("\\u%04x", new Object[] { Integer.valueOf(c) }));
/*     */       } else {
/*  91 */         sb.append(c);
/*     */       } 
/*     */     } 
/*  94 */     sb.append("\"");
/*  95 */     return sb.toString();
/*     */   }
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
/*     */   public static String translate(String str) {
/* 109 */     if (!str.isEmpty()) {
/* 110 */       char c = str.charAt(0);
/* 111 */       if ((c == '\'' || c == '"') && str.charAt(str.length() - 1) == c) {
/* 112 */         str = str.substring(1, str.length() - 1);
/*     */       }
/*     */     } 
/* 115 */     StringBuilder keySeq = new StringBuilder();
/* 116 */     for (int i = 0; i < str.length(); i++) {
/* 117 */       char c = str.charAt(i);
/* 118 */       if (c == '\\') {
/* 119 */         int j; if (++i >= str.length()) {
/*     */           break;
/*     */         }
/* 122 */         c = str.charAt(i);
/* 123 */         switch (c) {
/*     */           case 'a':
/* 125 */             c = '\007';
/*     */             break;
/*     */           case 'b':
/* 128 */             c = '\b';
/*     */             break;
/*     */           case 'd':
/* 131 */             c = '';
/*     */             break;
/*     */           case 'E':
/*     */           case 'e':
/* 135 */             c = '\033';
/*     */             break;
/*     */           case 'f':
/* 138 */             c = '\f';
/*     */             break;
/*     */           case 'n':
/* 141 */             c = '\n';
/*     */             break;
/*     */           case 'r':
/* 144 */             c = '\r';
/*     */             break;
/*     */           case 't':
/* 147 */             c = '\t';
/*     */             break;
/*     */           case 'v':
/* 150 */             c = '\013';
/*     */             break;
/*     */           case '\\':
/* 153 */             c = '\\';
/*     */             break;
/*     */           case '0':
/*     */           case '1':
/*     */           case '2':
/*     */           case '3':
/*     */           case '4':
/*     */           case '5':
/*     */           case '6':
/*     */           case '7':
/* 163 */             c = Character.MIN_VALUE;
/* 164 */             for (j = 0; j < 3 && 
/* 165 */               i < str.length(); j++, i++) {
/*     */ 
/*     */               
/* 168 */               int k = Character.digit(str.charAt(i), 8);
/* 169 */               if (k < 0) {
/*     */                 break;
/*     */               }
/* 172 */               c = (char)(c * 8 + k);
/*     */             } 
/* 174 */             i--;
/* 175 */             c = (char)(c & 0xFF);
/*     */             break;
/*     */           case 'x':
/* 178 */             i++;
/* 179 */             c = Character.MIN_VALUE;
/* 180 */             for (j = 0; j < 2 && 
/* 181 */               i < str.length(); j++, i++) {
/*     */ 
/*     */               
/* 184 */               int k = Character.digit(str.charAt(i), 16);
/* 185 */               if (k < 0) {
/*     */                 break;
/*     */               }
/* 188 */               c = (char)(c * 16 + k);
/*     */             } 
/* 190 */             i--;
/* 191 */             c = (char)(c & 0xFF);
/*     */             break;
/*     */           case 'u':
/* 194 */             i++;
/* 195 */             c = Character.MIN_VALUE;
/* 196 */             for (j = 0; j < 4 && 
/* 197 */               i < str.length(); j++, i++) {
/*     */ 
/*     */               
/* 200 */               int k = Character.digit(str.charAt(i), 16);
/* 201 */               if (k < 0) {
/*     */                 break;
/*     */               }
/* 204 */               c = (char)(c * 16 + k);
/*     */             } 
/*     */             break;
/*     */           case 'C':
/* 208 */             if (++i >= str.length()) {
/*     */               break;
/*     */             }
/* 211 */             c = str.charAt(i);
/* 212 */             if (c == '-') {
/* 213 */               if (++i >= str.length()) {
/*     */                 break;
/*     */               }
/* 216 */               c = str.charAt(i);
/*     */             } 
/* 218 */             c = (c == '?') ? '' : (char)(Character.toUpperCase(c) & 0x1F);
/*     */             break;
/*     */         } 
/* 221 */       } else if (c == '^') {
/* 222 */         if (++i >= str.length()) {
/*     */           break;
/*     */         }
/* 225 */         c = str.charAt(i);
/* 226 */         if (c != '^') {
/* 227 */           c = (c == '?') ? '' : (char)(Character.toUpperCase(c) & 0x1F);
/*     */         }
/*     */       } 
/* 230 */       keySeq.append(c);
/*     */     } 
/* 232 */     return keySeq.toString();
/*     */   }
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
/*     */   public static Collection<String> range(String range) {
/* 245 */     String pfx, keys[] = range.split("-");
/* 246 */     if (keys.length != 2) {
/* 247 */       return null;
/*     */     }
/* 249 */     keys[0] = translate(keys[0]);
/* 250 */     keys[1] = translate(keys[1]);
/* 251 */     if (keys[0].length() != keys[1].length()) {
/* 252 */       return null;
/*     */     }
/*     */     
/* 255 */     if (keys[0].length() > 1) {
/* 256 */       pfx = keys[0].substring(0, keys[0].length() - 1);
/* 257 */       if (!keys[1].startsWith(pfx)) {
/* 258 */         return null;
/*     */       }
/*     */     } else {
/* 261 */       pfx = "";
/*     */     } 
/* 263 */     char c0 = keys[0].charAt(keys[0].length() - 1);
/* 264 */     char c1 = keys[1].charAt(keys[1].length() - 1);
/* 265 */     if (c0 > c1) {
/* 266 */       return null;
/*     */     }
/* 268 */     Collection<String> seqs = new ArrayList<>(); char c;
/* 269 */     for (c = c0; c <= c1; c = (char)(c + 1)) {
/* 270 */       seqs.add(pfx + c);
/*     */     }
/* 272 */     return seqs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String esc() {
/* 281 */     return "\033";
/*     */   }
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
/*     */   public static String alt(char c) {
/* 294 */     return "\033" + c;
/*     */   }
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
/*     */   public static String alt(String c) {
/* 307 */     return "\033" + c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String del() {
/* 316 */     return "";
/*     */   }
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
/*     */   public static String ctrl(char key) {
/* 329 */     return (key == '?') ? del() : Character.toString((char)(Character.toUpperCase(key) & 0x1F));
/*     */   }
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
/*     */   public static String key(Terminal terminal, InfoCmp.Capability capability) {
/* 343 */     return Curses.tputs(terminal.getStringCapability(capability), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 352 */     KEYSEQ_COMPARATOR = ((s1, s2) -> {
/*     */         int len1 = s1.length();
/*     */         int len2 = s2.length();
/*     */         int lim = Math.min(len1, len2);
/*     */         for (int k = 0; k < lim; k++) {
/*     */           char c1 = s1.charAt(k);
/*     */           char c2 = s2.charAt(k);
/*     */           if (c1 != c2) {
/*     */             int l = len1 - len2;
/*     */             return (l != 0) ? l : (c1 - c2);
/*     */           } 
/*     */         } 
/*     */         return len1 - len2;
/*     */       });
/*     */   }
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
/*     */   public T getUnicode() {
/* 383 */     return this.unicode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicode(T unicode) {
/* 392 */     this.unicode = unicode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getNomatch() {
/* 404 */     return this.nomatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNomatch(T nomatch) {
/* 413 */     this.nomatch = nomatch;
/*     */   }
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
/*     */   public long getAmbiguousTimeout() {
/* 426 */     return this.ambiguousTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmbiguousTimeout(long ambiguousTimeout) {
/* 435 */     this.ambiguousTimeout = ambiguousTimeout;
/*     */   }
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
/*     */   public T getAnotherKey() {
/* 449 */     return this.anotherKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, T> getBoundKeys() {
/* 460 */     Map<String, T> bound = new TreeMap<>(KEYSEQ_COMPARATOR);
/* 461 */     doGetBoundKeys(this, "", bound);
/* 462 */     return bound;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> void doGetBoundKeys(KeyMap<T> keyMap, String prefix, Map<String, T> bound) {
/* 467 */     if (keyMap.anotherKey != null) {
/* 468 */       bound.put(prefix, keyMap.anotherKey);
/*     */     }
/* 470 */     for (int c = 0; c < keyMap.mapping.length; c++) {
/* 471 */       if (keyMap.mapping[c] instanceof KeyMap) {
/* 472 */         doGetBoundKeys((KeyMap<T>)keyMap.mapping[c], prefix + (char)c, bound);
/* 473 */       } else if (keyMap.mapping[c] != null) {
/* 474 */         bound.put(prefix + (char)c, (T)keyMap.mapping[c]);
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   public T getBound(CharSequence keySeq, int[] remaining) {
/* 497 */     remaining[0] = -1;
/* 498 */     if (keySeq != null && keySeq.length() > 0) {
/* 499 */       char c = keySeq.charAt(0);
/* 500 */       if (c >= this.mapping.length) {
/* 501 */         remaining[0] = Character.codePointCount(keySeq, 0, keySeq.length());
/* 502 */         return null;
/*     */       } 
/* 504 */       if (this.mapping[c] instanceof KeyMap) {
/* 505 */         CharSequence sub = keySeq.subSequence(1, keySeq.length());
/* 506 */         return ((KeyMap<T>)this.mapping[c]).getBound(sub, remaining);
/* 507 */       }  if (this.mapping[c] != null) {
/* 508 */         remaining[0] = keySeq.length() - 1;
/* 509 */         return (T)this.mapping[c];
/*     */       } 
/* 511 */       remaining[0] = keySeq.length();
/* 512 */       return this.anotherKey;
/*     */     } 
/*     */ 
/*     */     
/* 516 */     return this.anotherKey;
/*     */   }
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
/*     */   public T getBound(CharSequence keySeq) {
/* 531 */     int[] remaining = new int[1];
/* 532 */     T res = getBound(keySeq, remaining);
/* 533 */     return (remaining[0] <= 0) ? res : null;
/*     */   }
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
/*     */   public void bindIfNotBound(T function, CharSequence keySeq) {
/* 546 */     if (function != null && keySeq != null) {
/* 547 */       bind(this, keySeq, function, true);
/*     */     }
/*     */   }
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
/*     */   public void bind(T function, CharSequence... keySeqs) {
/* 561 */     for (CharSequence keySeq : keySeqs) {
/* 562 */       bind(function, keySeq);
/*     */     }
/*     */   }
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
/*     */   public void bind(T function, Iterable<? extends CharSequence> keySeqs) {
/* 576 */     for (CharSequence keySeq : keySeqs) {
/* 577 */       bind(function, keySeq);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(T function, CharSequence keySeq) {
/* 590 */     if (keySeq != null) {
/* 591 */       if (function == null) {
/* 592 */         unbind(keySeq);
/*     */       } else {
/* 594 */         bind(this, keySeq, function, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void unbind(CharSequence... keySeqs) {
/* 600 */     for (CharSequence keySeq : keySeqs) {
/* 601 */       unbind(keySeq);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbind(CharSequence keySeq) {
/* 613 */     if (keySeq != null) {
/* 614 */       unbind(this, keySeq);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T unbind(KeyMap<T> map, CharSequence keySeq) {
/* 620 */     KeyMap<T> prev = null;
/* 621 */     if (keySeq != null && keySeq.length() > 0) {
/* 622 */       for (int i = 0; i < keySeq.length() - 1; i++) {
/* 623 */         char c1 = keySeq.charAt(i);
/* 624 */         if (c1 > map.mapping.length) {
/* 625 */           return null;
/*     */         }
/* 627 */         if (!(map.mapping[c1] instanceof KeyMap)) {
/* 628 */           return null;
/*     */         }
/* 630 */         prev = map;
/* 631 */         map = (KeyMap<T>)map.mapping[c1];
/*     */       } 
/* 633 */       char c = keySeq.charAt(keySeq.length() - 1);
/* 634 */       if (c > map.mapping.length) {
/* 635 */         return null;
/*     */       }
/* 637 */       if (map.mapping[c] instanceof KeyMap) {
/* 638 */         KeyMap<?> sub = (KeyMap)map.mapping[c];
/* 639 */         Object object = sub.anotherKey;
/* 640 */         sub.anotherKey = null;
/* 641 */         return (T)object;
/*     */       } 
/* 643 */       Object res = map.mapping[c];
/* 644 */       map.mapping[c] = null;
/* 645 */       int nb = 0;
/* 646 */       for (int j = 0; j < map.mapping.length; j++) {
/* 647 */         if (map.mapping[j] != null) {
/* 648 */           nb++;
/*     */         }
/*     */       } 
/* 651 */       if (nb == 0 && prev != null) {
/* 652 */         prev.mapping[keySeq.charAt(keySeq.length() - 2)] = map.anotherKey;
/*     */       }
/* 654 */       return (T)res;
/*     */     } 
/*     */     
/* 657 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> void bind(KeyMap<T> map, CharSequence keySeq, T function, boolean onlyIfNotBound) {
/* 662 */     if (keySeq != null && keySeq.length() > 0)
/* 663 */       for (int i = 0; i < keySeq.length(); i++) {
/* 664 */         char c = keySeq.charAt(i);
/* 665 */         if (c >= map.mapping.length) {
/*     */           return;
/*     */         }
/* 668 */         if (i < keySeq.length() - 1) {
/* 669 */           if (!(map.mapping[c] instanceof KeyMap)) {
/* 670 */             KeyMap<T> m = new KeyMap<>();
/* 671 */             m.anotherKey = (T)map.mapping[c];
/* 672 */             map.mapping[c] = m;
/*     */           } 
/* 674 */           map = (KeyMap<T>)map.mapping[c];
/*     */         }
/* 676 */         else if (map.mapping[c] instanceof KeyMap) {
/* 677 */           ((KeyMap)map.mapping[c]).anotherKey = function;
/*     */         } else {
/* 679 */           Object op = map.mapping[c];
/* 680 */           if (!onlyIfNotBound || op == null)
/* 681 */             map.mapping[c] = function; 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\keymap\KeyMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */