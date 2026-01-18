/*     */ package ch.randelshofer.fastdoubleparser.bte;
/*     */ 
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ByteTrie
/*     */ {
/*     */   default int match(byte[] str) {
/*  26 */     return match(str, 0, str.length);
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
/*     */   int match(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteTrie copyOf(Set<String> set, boolean ignoreCase) {
/*     */     String str;
/*  50 */     switch (set.size()) {
/*     */       case 0:
/*  52 */         return new ByteTrieOfNone();
/*     */       case 1:
/*  54 */         str = set.iterator().next();
/*  55 */         if (ignoreCase) {
/*  56 */           LinkedHashSet<String> newSet; switch (str.length()) {
/*     */             case 0:
/*  58 */               return new ByteTrieOfNone();
/*     */             case 1:
/*  60 */               newSet = new LinkedHashSet<>();
/*  61 */               newSet.add(str.toLowerCase());
/*  62 */               newSet.add(str.toUpperCase());
/*  63 */               if (newSet.size() == 1) {
/*  64 */                 if ((((String)newSet.iterator().next()).getBytes(StandardCharsets.UTF_8)).length == 1) {
/*  65 */                   return new ByteTrieOfOneSingleByte(newSet);
/*     */                 }
/*  67 */                 return new ByteTrieOfOne(newSet);
/*     */               } 
/*  69 */               return new ByteTrieOfFew(newSet);
/*     */           } 
/*  71 */           return new ByteTrieOfFewIgnoreCase(set);
/*     */         } 
/*     */         
/*  74 */         if ((((String)set.iterator().next()).getBytes(StandardCharsets.UTF_8)).length == 1) {
/*  75 */           return new ByteTrieOfOneSingleByte(set);
/*     */         }
/*  77 */         return new ByteTrieOfOne(set);
/*     */     } 
/*  79 */     if (ignoreCase) {
/*  80 */       return new ByteTrieOfFewIgnoreCase(set);
/*     */     }
/*  82 */     return new ByteTrieOfFew(set);
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
/*     */   static ByteTrie copyOfChars(Set<Character> set, boolean ignoreCase) {
/*  95 */     Set<String> strSet = new HashSet<>(set.size() * 2);
/*  96 */     if (ignoreCase) {
/*  97 */       for (Iterator<Character> iterator1 = set.iterator(); iterator1.hasNext(); ) { char ch = ((Character)iterator1.next()).charValue();
/*  98 */         String string = new String(new char[] { ch });
/*  99 */         strSet.add(string.toLowerCase());
/* 100 */         strSet.add(string.toUpperCase()); }
/*     */ 
/*     */       
/* 103 */       return copyOf(strSet, false);
/*     */     } 
/*     */     
/* 106 */     for (Iterator<Character> iterator = set.iterator(); iterator.hasNext(); ) { char ch = ((Character)iterator.next()).charValue();
/* 107 */       strSet.add(new String(new char[] { ch })); }
/*     */ 
/*     */     
/* 110 */     return copyOf(strSet, ignoreCase);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteTrie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */