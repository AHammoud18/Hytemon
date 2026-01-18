/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteSetOfOne
/*    */   implements ByteSet
/*    */ {
/*    */   private final byte ch;
/*    */   
/*    */   ByteSetOfOne(Set<Character> set) {
/* 13 */     if (set.size() != 1) throw new IllegalArgumentException("set size must be 1, size=" + set.size()); 
/* 14 */     char ch = ((Character)set.iterator().next()).charValue();
/* 15 */     if (ch > '')
/* 16 */       throw new IllegalArgumentException("can not map to a single byte. ch='" + ch + "' 0x" + Integer.toHexString(ch)); 
/* 17 */     this.ch = (byte)ch;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsKey(byte b) {
/* 22 */     return (this.ch == b);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteSetOfOne.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */