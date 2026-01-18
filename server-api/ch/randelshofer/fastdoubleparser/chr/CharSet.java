/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CharSet
/*    */ {
/*    */   boolean containsKey(char paramChar);
/*    */   
/*    */   static CharSet copyOf(Set<Character> set, boolean ignoreCase) {
/* 34 */     set = applyIgnoreCase(set, ignoreCase);
/* 35 */     switch (set.size()) {
/*    */       case 0:
/* 37 */         return new CharSetOfNone();
/*    */       case 1:
/* 39 */         return new CharSetOfOne(set);
/*    */     } 
/* 41 */     return (set.size() < 5) ? new CharSetOfFew(set) : new CharToIntMap(set);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Set<Character> applyIgnoreCase(Set<Character> set, boolean ignoreCase) {
/* 59 */     if (ignoreCase) {
/* 60 */       LinkedHashSet<Character> convertedSet = new LinkedHashSet<>();
/* 61 */       for (Character ch : set) {
/*    */         
/* 63 */         convertedSet.add(ch);
/*    */ 
/*    */         
/* 66 */         char lc = Character.toLowerCase(ch.charValue());
/*    */ 
/*    */ 
/*    */         
/* 70 */         char uc = Character.toUpperCase(ch.charValue());
/* 71 */         char uclc = Character.toLowerCase(uc);
/*    */         
/* 73 */         convertedSet.add(Character.valueOf(lc));
/* 74 */         convertedSet.add(Character.valueOf(uc));
/* 75 */         convertedSet.add(Character.valueOf(uclc));
/*    */       } 
/* 77 */       set = convertedSet;
/*    */     } 
/* 79 */     return set;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */