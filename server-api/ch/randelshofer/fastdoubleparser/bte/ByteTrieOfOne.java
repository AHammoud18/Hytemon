/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteTrieOfOne
/*    */   implements ByteTrie
/*    */ {
/*    */   private final byte[] chars;
/*    */   
/*    */   public ByteTrieOfOne(Set<String> set) {
/* 14 */     if (set.size() != 1) throw new IllegalArgumentException("set size must be 1, size=" + set.size()); 
/* 15 */     this.chars = ((String)set.iterator().next()).getBytes(StandardCharsets.UTF_8);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(byte[] str) {
/* 21 */     return match(str, 0, str.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(byte[] str, int startIndex, int endIndex) {
/* 26 */     int i = 0;
/* 27 */     int limit = Math.min(endIndex - startIndex, this.chars.length);
/* 28 */     while (i < limit && str[i + startIndex] == this.chars[i]) {
/* 29 */       i++;
/*    */     }
/* 31 */     return (i == this.chars.length) ? this.chars.length : 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteTrieOfOne.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */