/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteTrieOfOneSingleByte
/*    */   implements ByteTrie
/*    */ {
/*    */   private final byte ch;
/*    */   
/*    */   public ByteTrieOfOneSingleByte(Set<String> set) {
/* 14 */     if (set.size() != 1) throw new IllegalArgumentException("set size must be 1, size=" + set.size()); 
/* 15 */     byte[] chars = ((String)set.iterator().next()).getBytes(StandardCharsets.UTF_8);
/* 16 */     if (chars.length != 1) throw new IllegalArgumentException("char size must be 1, size=" + set.size()); 
/* 17 */     this.ch = chars[0];
/*    */   }
/*    */   
/*    */   public ByteTrieOfOneSingleByte(byte ch) {
/* 21 */     this.ch = ch;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(byte[] str) {
/* 27 */     return match(str, 0, str.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(byte[] str, int startIndex, int endIndex) {
/* 32 */     return (startIndex < endIndex && str[startIndex] == this.ch) ? 1 : 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteTrieOfOneSingleByte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */