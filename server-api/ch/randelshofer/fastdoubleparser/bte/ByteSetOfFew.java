/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteSetOfFew
/*    */   implements ByteSet
/*    */ {
/*    */   private final byte[] bytes;
/*    */   
/*    */   public ByteSetOfFew(Set<Character> set) {
/* 18 */     byte[] tmp = new byte[set.size() * 4];
/* 19 */     int i = 0;
/* 20 */     for (Iterator<Character> iterator = set.iterator(); iterator.hasNext(); ) { char ch = ((Character)iterator.next()).charValue();
/* 21 */       for (byte b : String.valueOf(ch).getBytes(StandardCharsets.UTF_8)) {
/* 22 */         tmp[i++] = b;
/*    */       } }
/*    */     
/* 25 */     this.bytes = Arrays.copyOf(tmp, i);
/*    */   }
/*    */   public boolean containsKey(byte b) {
/*    */     int i;
/* 29 */     boolean found = false;
/* 30 */     for (byte aChar : this.bytes) {
/* 31 */       i = found | ((aChar == b) ? 1 : 0);
/*    */     }
/* 33 */     return i;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteSetOfFew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */