/*    */ package ch.randelshofer.fastdoubleparser.bte;
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
/*    */ public interface ByteSet
/*    */ {
/*    */   boolean containsKey(byte paramByte);
/*    */   
/*    */   static ByteSet copyOf(Set<Character> set, boolean ignoreCase) {
/* 31 */     set = applyIgnoreCase(set, ignoreCase);
/* 32 */     switch (set.size()) {
/*    */       case 0:
/* 34 */         return new ByteSetOfNone();
/*    */       case 1:
/* 36 */         return new ByteSetOfOne(set);
/*    */     } 
/* 38 */     return (set.size() < 5) ? new ByteSetOfFew(set) : new ByteToIntMap(set);
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
/* 56 */     if (ignoreCase) {
/* 57 */       LinkedHashSet<Character> convertedSet = new LinkedHashSet<>();
/* 58 */       for (Character ch : set) {
/*    */         
/* 60 */         convertedSet.add(ch);
/*    */ 
/*    */         
/* 63 */         char lc = Character.toLowerCase(ch.charValue());
/*    */ 
/*    */ 
/*    */         
/* 67 */         char uc = Character.toUpperCase(ch.charValue());
/* 68 */         char uclc = Character.toLowerCase(uc);
/*    */         
/* 70 */         convertedSet.add(Character.valueOf(lc));
/* 71 */         convertedSet.add(Character.valueOf(uc));
/* 72 */         convertedSet.add(Character.valueOf(uclc));
/*    */       } 
/* 74 */       set = convertedSet;
/*    */     } 
/* 76 */     return set;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */