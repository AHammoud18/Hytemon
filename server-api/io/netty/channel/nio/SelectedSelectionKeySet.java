/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SelectedSelectionKeySet
/*     */   extends AbstractSet<SelectionKey>
/*     */ {
/*  31 */   SelectionKey[] keys = new SelectionKey[1024];
/*     */   
/*     */   int size;
/*     */   
/*     */   public boolean add(SelectionKey o) {
/*  36 */     if (o == null) {
/*  37 */       return false;
/*     */     }
/*     */     
/*  40 */     if (this.size == this.keys.length) {
/*  41 */       increaseCapacity();
/*     */     }
/*     */     
/*  44 */     this.keys[this.size++] = o;
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  55 */     SelectionKey[] array = this.keys;
/*  56 */     for (int i = 0, s = this.size; i < s; i++) {
/*  57 */       SelectionKey k = array[i];
/*  58 */       if (k.equals(o)) {
/*  59 */         return true;
/*     */       }
/*     */     } 
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  67 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<SelectionKey> iterator() {
/*  72 */     return new Iterator<SelectionKey>()
/*     */       {
/*     */         private int idx;
/*     */         
/*     */         public boolean hasNext() {
/*  77 */           return (this.idx < SelectedSelectionKeySet.this.size);
/*     */         }
/*     */ 
/*     */         
/*     */         public SelectionKey next() {
/*  82 */           if (!hasNext()) {
/*  83 */             throw new NoSuchElementException();
/*     */           }
/*  85 */           return SelectedSelectionKeySet.this.keys[this.idx++];
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/*  90 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   void reset() {
/*  96 */     reset(0);
/*     */   }
/*     */   
/*     */   void reset(int start) {
/* 100 */     Arrays.fill((Object[])this.keys, start, this.size, (Object)null);
/* 101 */     this.size = 0;
/*     */   }
/*     */   
/*     */   private void increaseCapacity() {
/* 105 */     SelectionKey[] newKeys = new SelectionKey[this.keys.length << 1];
/* 106 */     System.arraycopy(this.keys, 0, newKeys, 0, this.size);
/* 107 */     this.keys = newKeys;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\SelectedSelectionKeySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */