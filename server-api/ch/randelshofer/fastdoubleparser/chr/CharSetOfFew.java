/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharSetOfFew
/*    */   implements CharSet
/*    */ {
/*    */   private final char[] chars;
/*    */   
/*    */   public CharSetOfFew(Set<Character> set) {
/* 16 */     this.chars = new char[set.size()];
/* 17 */     int i = 0;
/* 18 */     for (Character ch : set)
/* 19 */       this.chars[i++] = ch.charValue(); 
/*    */   }
/*    */   
/*    */   public boolean containsKey(char ch) {
/*    */     int i;
/* 24 */     boolean found = false;
/* 25 */     for (char aChar : this.chars) {
/* 26 */       i = found | ((aChar == ch) ? 1 : 0);
/*    */     }
/* 28 */     return i;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharSetOfFew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */