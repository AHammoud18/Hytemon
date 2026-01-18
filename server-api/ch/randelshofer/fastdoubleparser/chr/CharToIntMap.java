/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharToIntMap
/*    */   implements CharDigitSet, CharSet
/*    */ {
/*    */   private Node[] table;
/*    */   
/*    */   public CharToIntMap(Collection<Character> chars) {
/* 16 */     this(chars.size());
/* 17 */     int i = 0;
/* 18 */     for (Iterator<Character> iterator = chars.iterator(); iterator.hasNext(); ) { char ch = ((Character)iterator.next()).charValue();
/* 19 */       put(ch, i++); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsKey(char key) {
/* 25 */     return (getOrDefault(key, -1) >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int toDigit(char ch) {
/* 30 */     return getOrDefault(ch, 10);
/*    */   }
/*    */   
/*    */   private static class Node {
/*    */     char key;
/*    */     int value;
/*    */     Node next;
/*    */     
/*    */     public Node(char key, int value) {
/* 39 */       this.key = key;
/* 40 */       this.value = value;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CharToIntMap(int maxSize) {
/* 48 */     int n = (-1 >>> Integer.numberOfLeadingZeros(maxSize * 2)) + 1;
/* 49 */     this.table = new Node[n];
/*    */   }
/*    */   
/*    */   public void put(char key, int value) {
/* 53 */     int index = getIndex(key);
/* 54 */     Node found = this.table[index];
/* 55 */     if (found == null) {
/* 56 */       this.table[index] = new Node(key, value);
/*    */     } else {
/* 58 */       while (found.next != null && found.key != key) {
/* 59 */         found = found.next;
/*    */       }
/* 61 */       if (found.key == key) {
/* 62 */         found.value = value;
/*    */       } else {
/* 64 */         found.next = new Node(key, value);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private int getIndex(char key) {
/* 70 */     return key & this.table.length - 1;
/*    */   }
/*    */   
/*    */   public int getOrDefault(char key, int defaultValue) {
/* 74 */     int index = getIndex(key);
/* 75 */     Node found = this.table[index];
/* 76 */     while (found != null) {
/* 77 */       if (found.key == key) return found.value; 
/* 78 */       found = found.next;
/*    */     } 
/* 80 */     return defaultValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharToIntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */