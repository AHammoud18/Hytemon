/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharSetOfOne
/*    */   implements CharSet
/*    */ {
/*    */   private final char ch;
/*    */   
/*    */   CharSetOfOne(Set<Character> set) {
/* 13 */     if (set.size() != 1) throw new IllegalArgumentException("set size must be 1, size=" + set.size()); 
/* 14 */     this.ch = ((Character)set.iterator().next()).charValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsKey(char ch) {
/* 19 */     return (this.ch == ch);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharSetOfOne.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */