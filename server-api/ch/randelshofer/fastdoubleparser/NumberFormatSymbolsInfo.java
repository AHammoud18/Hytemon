/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import ch.randelshofer.fastdoubleparser.chr.CharSet;
/*     */ import ch.randelshofer.fastdoubleparser.chr.FormatCharSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NumberFormatSymbolsInfo
/*     */ {
/*     */   static boolean isAscii(NumberFormatSymbols symbols) {
/*  21 */     return (isAsciiCharCollection(symbols.decimalSeparator()) && 
/*  22 */       isAsciiCharCollection(symbols.groupingSeparator()) && 
/*  23 */       isAsciiStringCollection(symbols.exponentSeparator()) && 
/*  24 */       isAsciiCharCollection(symbols.minusSign()) && 
/*  25 */       isAsciiCharCollection(symbols.plusSign()) && 
/*  26 */       isAsciiStringCollection(symbols.infinity()) && 
/*  27 */       isAsciiStringCollection(symbols.nan()) && 
/*  28 */       isAsciiCharCollection(symbols.digits()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isMostlyAscii(NumberFormatSymbols symbols) {
/*  36 */     return (isAsciiCharCollection(symbols.decimalSeparator()) && 
/*  37 */       isAsciiCharCollection(symbols.groupingSeparator()) && 
/*     */       
/*  39 */       isAsciiCharCollection(symbols.minusSign()) && 
/*  40 */       isAsciiCharCollection(symbols.plusSign()) && 
/*     */ 
/*     */       
/*  43 */       isAsciiCharCollection(symbols.digits()));
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isDigitsTokensAscii(NumberFormatSymbols symbols) {
/*  48 */     return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  55 */       isAsciiCharCollection(symbols.digits());
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isAsciiStringCollection(Collection<String> collection) {
/*  60 */     for (String str : collection) {
/*  61 */       for (int i = 0; i < str.length(); i++) {
/*  62 */         char ch = str.charAt(i);
/*  63 */         if (ch > '') return false; 
/*     */       } 
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isAsciiCharCollection(Collection<Character> collection) {
/*  70 */     for (Iterator<Character> iterator = collection.iterator(); iterator.hasNext(); ) { char ch = ((Character)iterator.next()).charValue();
/*  71 */       if (ch > '') return false;  }
/*     */     
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   static boolean containsFormatChars(NumberFormatSymbols symbols) {
/*  77 */     FormatCharSet formatCharSet = new FormatCharSet();
/*  78 */     return (containsChars(symbols.decimalSeparator(), (CharSet)formatCharSet) || 
/*  79 */       containsChars(symbols.groupingSeparator(), (CharSet)formatCharSet) || 
/*  80 */       containsChars(symbols.exponentSeparator(), formatCharSet) || 
/*  81 */       containsChars(symbols.minusSign(), (CharSet)formatCharSet) || 
/*  82 */       containsChars(symbols.plusSign(), (CharSet)formatCharSet) || 
/*  83 */       containsChars(symbols.infinity(), formatCharSet) || 
/*  84 */       containsChars(symbols.nan(), formatCharSet) || 
/*  85 */       containsChars(symbols.digits(), (CharSet)formatCharSet));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean containsChars(Set<String> strings, FormatCharSet set) {
/*  90 */     for (String str : strings) {
/*  91 */       for (int i = 0, n = str.length(); i < n; i++) {
/*  92 */         if (set.containsKey(str.charAt(i))) {
/*  93 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean containsChars(Collection<Character> characters, CharSet set) {
/* 101 */     for (Iterator<Character> iterator = characters.iterator(); iterator.hasNext(); ) { char ch = ((Character)iterator.next()).charValue();
/* 102 */       if (set.containsKey(ch)) {
/* 103 */         return true;
/*     */       } }
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\NumberFormatSymbolsInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */