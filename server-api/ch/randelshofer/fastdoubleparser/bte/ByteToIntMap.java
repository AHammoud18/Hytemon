/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteToIntMap
/*    */   implements ByteDigitSet, ByteSet
/*    */ {
/*    */   private Node[] table;
/*    */   
/*    */   public ByteToIntMap(Collection<Character> chars) {
/* 16 */     this(chars.size());
/* 17 */     int i = 0;
/* 18 */     for (Iterator<Character> iterator = chars.iterator(); iterator.hasNext(); ) { char ch = ((Character)iterator.next()).charValue();
/* 19 */       if (ch > '') throw new IllegalArgumentException("can not map to a single byte. ch=" + ch); 
/* 20 */       put((byte)ch, i++); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsKey(byte b) {
/* 26 */     return (getOrDefault(b, -1) >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int toDigit(byte ch) {
/* 31 */     return getOrDefault(ch, 10);
/*    */   }
/*    */   
/*    */   private static class Node {
/*    */     byte key;
/*    */     int value;
/*    */     Node next;
/*    */     
/*    */     public Node(byte key, int value) {
/* 40 */       this.key = key;
/* 41 */       this.value = value;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteToIntMap(int maxSize) {
/* 49 */     int n = (-1 >>> Integer.numberOfLeadingZeros(maxSize * 2)) + 1;
/* 50 */     this.table = new Node[n];
/*    */   }
/*    */   
/*    */   public void put(byte key, int value) {
/* 54 */     int index = getIndex(key);
/* 55 */     Node found = this.table[index];
/* 56 */     if (found == null) {
/* 57 */       this.table[index] = new Node(key, value);
/*    */     } else {
/* 59 */       while (found.next != null && found.key != key) {
/* 60 */         found = found.next;
/*    */       }
/* 62 */       if (found.key == key) {
/* 63 */         found.value = value;
/*    */       } else {
/* 65 */         found.next = new Node(key, value);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private int getIndex(byte key) {
/* 71 */     return key & this.table.length - 1;
/*    */   }
/*    */   
/*    */   public int getOrDefault(byte key, int defaultValue) {
/* 75 */     int index = getIndex(key);
/* 76 */     Node found = this.table[index];
/* 77 */     while (found != null) {
/* 78 */       if (found.key == key) return found.value; 
/* 79 */       found = found.next;
/*    */     } 
/* 81 */     return defaultValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteToIntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */