/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LinkedTreeMap<K, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements Serializable
/*     */ {
/*  46 */   private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>()
/*     */     {
/*     */       public int compare(Comparable<Comparable> a, Comparable b)
/*     */       {
/*  50 */         return a.compareTo(b);
/*     */       }
/*     */     };
/*     */   
/*     */   private final Comparator<? super K> comparator;
/*     */   private final boolean allowNullValues;
/*     */   Node<K, V> root;
/*  57 */   int size = 0;
/*  58 */   int modCount = 0;
/*     */ 
/*     */   
/*     */   final Node<K, V> header;
/*     */   
/*     */   private EntrySet entrySet;
/*     */   
/*     */   private KeySet keySet;
/*     */ 
/*     */   
/*     */   public LinkedTreeMap() {
/*  69 */     this((Comparator)NATURAL_ORDER, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedTreeMap(boolean allowNullValues) {
/*  79 */     this((Comparator)NATURAL_ORDER, allowNullValues);
/*     */   }
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
/*     */   public LinkedTreeMap(Comparator<? super K> comparator, boolean allowNullValues) {
/*  93 */     this.comparator = (comparator != null) ? comparator : (Comparator)NATURAL_ORDER;
/*  94 */     this.allowNullValues = allowNullValues;
/*  95 */     this.header = new Node<>(allowNullValues);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 100 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object key) {
/* 105 */     Node<K, V> node = findByObject(key);
/* 106 */     return (node != null) ? node.value : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 111 */     return (findByObject(key) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public V put(K key, V value) {
/* 117 */     if (key == null) {
/* 118 */       throw new NullPointerException("key == null");
/*     */     }
/* 120 */     if (value == null && !this.allowNullValues) {
/* 121 */       throw new NullPointerException("value == null");
/*     */     }
/* 123 */     Node<K, V> created = find(key, true);
/* 124 */     V result = created.value;
/* 125 */     created.value = value;
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 131 */     this.root = null;
/* 132 */     this.size = 0;
/* 133 */     this.modCount++;
/*     */ 
/*     */     
/* 136 */     Node<K, V> header = this.header;
/* 137 */     header.next = header.prev = header;
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/* 142 */     Node<K, V> node = removeInternalByKey(key);
/* 143 */     return (node != null) ? node.value : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Node<K, V> find(K key, boolean create) {
/*     */     Node<K, V> created;
/* 152 */     Comparator<? super K> comparator = this.comparator;
/* 153 */     Node<K, V> nearest = this.root;
/* 154 */     int comparison = 0;
/*     */     
/* 156 */     if (nearest != null) {
/*     */ 
/*     */ 
/*     */       
/* 160 */       Comparable<Object> comparableKey = (comparator == NATURAL_ORDER) ? (Comparable<Object>)key : null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 166 */         comparison = (comparableKey != null) ? comparableKey.compareTo(nearest.key) : comparator.compare(key, nearest.key);
/*     */ 
/*     */         
/* 169 */         if (comparison == 0) {
/* 170 */           return nearest;
/*     */         }
/*     */ 
/*     */         
/* 174 */         Node<K, V> child = (comparison < 0) ? nearest.left : nearest.right;
/* 175 */         if (child == null) {
/*     */           break;
/*     */         }
/*     */         
/* 179 */         nearest = child;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 184 */     if (!create) {
/* 185 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 189 */     Node<K, V> header = this.header;
/*     */     
/* 191 */     if (nearest == null) {
/*     */       
/* 193 */       if (comparator == NATURAL_ORDER && !(key instanceof Comparable)) {
/* 194 */         throw new ClassCastException(key.getClass().getName() + " is not Comparable");
/*     */       }
/* 196 */       created = new Node<>(this.allowNullValues, nearest, key, header, header.prev);
/* 197 */       this.root = created;
/*     */     } else {
/* 199 */       created = new Node<>(this.allowNullValues, nearest, key, header, header.prev);
/* 200 */       if (comparison < 0) {
/* 201 */         nearest.left = created;
/*     */       } else {
/* 203 */         nearest.right = created;
/*     */       } 
/* 205 */       rebalance(nearest, true);
/*     */     } 
/* 207 */     this.size++;
/* 208 */     this.modCount++;
/*     */     
/* 210 */     return created;
/*     */   }
/*     */ 
/*     */   
/*     */   Node<K, V> findByObject(Object key) {
/*     */     try {
/* 216 */       return (key != null) ? find((K)key, false) : null;
/* 217 */     } catch (ClassCastException e) {
/* 218 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
/* 231 */     Node<K, V> mine = findByObject(entry.getKey());
/* 232 */     boolean valuesEqual = (mine != null && equal(mine.value, entry.getValue()));
/* 233 */     return valuesEqual ? mine : null;
/*     */   }
/*     */   
/*     */   private static boolean equal(Object a, Object b) {
/* 237 */     return Objects.equals(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeInternal(Node<K, V> node, boolean unlink) {
/* 246 */     if (unlink) {
/* 247 */       node.prev.next = node.next;
/* 248 */       node.next.prev = node.prev;
/*     */     } 
/*     */     
/* 251 */     Node<K, V> left = node.left;
/* 252 */     Node<K, V> right = node.right;
/* 253 */     Node<K, V> originalParent = node.parent;
/* 254 */     if (left != null && right != null) {
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
/* 265 */       Node<K, V> adjacent = (left.height > right.height) ? left.last() : right.first();
/* 266 */       removeInternal(adjacent, false);
/*     */       
/* 268 */       int leftHeight = 0;
/* 269 */       left = node.left;
/* 270 */       if (left != null) {
/* 271 */         leftHeight = left.height;
/* 272 */         adjacent.left = left;
/* 273 */         left.parent = adjacent;
/* 274 */         node.left = null;
/*     */       } 
/*     */       
/* 277 */       int rightHeight = 0;
/* 278 */       right = node.right;
/* 279 */       if (right != null) {
/* 280 */         rightHeight = right.height;
/* 281 */         adjacent.right = right;
/* 282 */         right.parent = adjacent;
/* 283 */         node.right = null;
/*     */       } 
/*     */       
/* 286 */       adjacent.height = Math.max(leftHeight, rightHeight) + 1;
/* 287 */       replaceInParent(node, adjacent); return;
/*     */     } 
/* 289 */     if (left != null) {
/* 290 */       replaceInParent(node, left);
/* 291 */       node.left = null;
/* 292 */     } else if (right != null) {
/* 293 */       replaceInParent(node, right);
/* 294 */       node.right = null;
/*     */     } else {
/* 296 */       replaceInParent(node, null);
/*     */     } 
/*     */     
/* 299 */     rebalance(originalParent, false);
/* 300 */     this.size--;
/* 301 */     this.modCount++;
/*     */   }
/*     */   
/*     */   Node<K, V> removeInternalByKey(Object key) {
/* 305 */     Node<K, V> node = findByObject(key);
/* 306 */     if (node != null) {
/* 307 */       removeInternal(node, true);
/*     */     }
/* 309 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   private void replaceInParent(Node<K, V> node, Node<K, V> replacement) {
/* 314 */     Node<K, V> parent = node.parent;
/* 315 */     node.parent = null;
/* 316 */     if (replacement != null) {
/* 317 */       replacement.parent = parent;
/*     */     }
/*     */     
/* 320 */     if (parent != null) {
/* 321 */       if (parent.left == node) {
/* 322 */         parent.left = replacement;
/*     */       } else {
/* 324 */         assert parent.right == node;
/* 325 */         parent.right = replacement;
/*     */       } 
/*     */     } else {
/* 328 */       this.root = replacement;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rebalance(Node<K, V> unbalanced, boolean insert) {
/* 339 */     for (Node<K, V> node = unbalanced; node != null; node = node.parent) {
/* 340 */       Node<K, V> left = node.left;
/* 341 */       Node<K, V> right = node.right;
/* 342 */       int leftHeight = (left != null) ? left.height : 0;
/* 343 */       int rightHeight = (right != null) ? right.height : 0;
/*     */       
/* 345 */       int delta = leftHeight - rightHeight;
/* 346 */       if (delta == -2) {
/* 347 */         Node<K, V> rightLeft = right.left;
/* 348 */         Node<K, V> rightRight = right.right;
/* 349 */         int rightRightHeight = (rightRight != null) ? rightRight.height : 0;
/* 350 */         int rightLeftHeight = (rightLeft != null) ? rightLeft.height : 0;
/*     */         
/* 352 */         int rightDelta = rightLeftHeight - rightRightHeight;
/* 353 */         if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
/* 354 */           rotateLeft(node);
/*     */         } else {
/* 356 */           assert rightDelta == 1;
/* 357 */           rotateRight(right);
/* 358 */           rotateLeft(node);
/*     */         } 
/* 360 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 364 */       else if (delta == 2) {
/* 365 */         Node<K, V> leftLeft = left.left;
/* 366 */         Node<K, V> leftRight = left.right;
/* 367 */         int leftRightHeight = (leftRight != null) ? leftRight.height : 0;
/* 368 */         int leftLeftHeight = (leftLeft != null) ? leftLeft.height : 0;
/*     */         
/* 370 */         int leftDelta = leftLeftHeight - leftRightHeight;
/* 371 */         if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
/* 372 */           rotateRight(node);
/*     */         } else {
/* 374 */           assert leftDelta == -1;
/* 375 */           rotateLeft(left);
/* 376 */           rotateRight(node);
/*     */         } 
/* 378 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 382 */       else if (delta == 0) {
/* 383 */         node.height = leftHeight + 1;
/* 384 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       } else {
/*     */         
/* 389 */         assert delta == -1 || delta == 1;
/* 390 */         node.height = Math.max(leftHeight, rightHeight) + 1;
/* 391 */         if (!insert) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotateLeft(Node<K, V> root) {
/* 400 */     Node<K, V> left = root.left;
/* 401 */     Node<K, V> pivot = root.right;
/* 402 */     Node<K, V> pivotLeft = pivot.left;
/* 403 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 406 */     root.right = pivotLeft;
/* 407 */     if (pivotLeft != null) {
/* 408 */       pivotLeft.parent = root;
/*     */     }
/*     */     
/* 411 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 414 */     pivot.left = root;
/* 415 */     root.parent = pivot;
/*     */ 
/*     */     
/* 418 */     root
/* 419 */       .height = Math.max((left != null) ? left.height : 0, (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/* 420 */     pivot.height = Math.max(root.height, (pivotRight != null) ? pivotRight.height : 0) + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotateRight(Node<K, V> root) {
/* 425 */     Node<K, V> pivot = root.left;
/* 426 */     Node<K, V> right = root.right;
/* 427 */     Node<K, V> pivotLeft = pivot.left;
/* 428 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 431 */     root.left = pivotRight;
/* 432 */     if (pivotRight != null) {
/* 433 */       pivotRight.parent = root;
/*     */     }
/*     */     
/* 436 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 439 */     pivot.right = root;
/* 440 */     root.parent = pivot;
/*     */ 
/*     */     
/* 443 */     root
/* 444 */       .height = Math.max((right != null) ? right.height : 0, (pivotRight != null) ? pivotRight.height : 0) + 1;
/* 445 */     pivot.height = Math.max(root.height, (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 453 */     EntrySet result = this.entrySet;
/* 454 */     if (result == null) {
/* 455 */       result = this.entrySet = new EntrySet();
/*     */     }
/* 457 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 462 */     KeySet result = this.keySet;
/* 463 */     if (result == null) {
/* 464 */       result = this.keySet = new KeySet();
/*     */     }
/* 466 */     return result;
/*     */   }
/*     */   
/*     */   static final class Node<K, V>
/*     */     implements Map.Entry<K, V> {
/*     */     Node<K, V> parent;
/*     */     Node<K, V> left;
/*     */     Node<K, V> right;
/*     */     Node<K, V> next;
/*     */     Node<K, V> prev;
/*     */     final K key;
/*     */     final boolean allowNullValue;
/*     */     V value;
/*     */     int height;
/*     */     
/*     */     Node(boolean allowNullValue) {
/* 482 */       this.key = null;
/* 483 */       this.allowNullValue = allowNullValue;
/* 484 */       this.next = this.prev = this;
/*     */     }
/*     */ 
/*     */     
/*     */     Node(boolean allowNullValue, Node<K, V> parent, K key, Node<K, V> next, Node<K, V> prev) {
/* 489 */       this.parent = parent;
/* 490 */       this.key = key;
/* 491 */       this.allowNullValue = allowNullValue;
/* 492 */       this.height = 1;
/* 493 */       this.next = next;
/* 494 */       this.prev = prev;
/* 495 */       prev.next = this;
/* 496 */       next.prev = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 501 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 506 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 511 */       if (value == null && !this.allowNullValue) {
/* 512 */         throw new NullPointerException("value == null");
/*     */       }
/* 514 */       V oldValue = this.value;
/* 515 */       this.value = value;
/* 516 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 521 */       if (o instanceof Map.Entry) {
/* 522 */         Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
/* 523 */         return (((this.key == null) ? (other.getKey() == null) : this.key.equals(other.getKey())) && ((this.value == null) ? (other
/* 524 */           .getValue() == null) : this.value.equals(other.getValue())));
/*     */       } 
/* 526 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 531 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 536 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public Node<K, V> first() {
/* 541 */       Node<K, V> node = this;
/* 542 */       Node<K, V> child = node.left;
/* 543 */       while (child != null) {
/* 544 */         node = child;
/* 545 */         child = node.left;
/*     */       } 
/* 547 */       return node;
/*     */     }
/*     */ 
/*     */     
/*     */     public Node<K, V> last() {
/* 552 */       Node<K, V> node = this;
/* 553 */       Node<K, V> child = node.right;
/* 554 */       while (child != null) {
/* 555 */         node = child;
/* 556 */         child = node.right;
/*     */       } 
/* 558 */       return node;
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
/* 563 */     LinkedTreeMap.Node<K, V> next = LinkedTreeMap.this.header.next;
/* 564 */     LinkedTreeMap.Node<K, V> lastReturned = null;
/* 565 */     int expectedModCount = LinkedTreeMap.this.modCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 572 */       return (this.next != LinkedTreeMap.this.header);
/*     */     }
/*     */ 
/*     */     
/*     */     final LinkedTreeMap.Node<K, V> nextNode() {
/* 577 */       LinkedTreeMap.Node<K, V> e = this.next;
/* 578 */       if (e == LinkedTreeMap.this.header) {
/* 579 */         throw new NoSuchElementException();
/*     */       }
/* 581 */       if (LinkedTreeMap.this.modCount != this.expectedModCount) {
/* 582 */         throw new ConcurrentModificationException();
/*     */       }
/* 584 */       this.next = e.next;
/* 585 */       this.lastReturned = e;
/* 586 */       return e;
/*     */     }
/*     */ 
/*     */     
/*     */     public final void remove() {
/* 591 */       if (this.lastReturned == null) {
/* 592 */         throw new IllegalStateException();
/*     */       }
/* 594 */       LinkedTreeMap.this.removeInternal(this.lastReturned, true);
/* 595 */       this.lastReturned = null;
/* 596 */       this.expectedModCount = LinkedTreeMap.this.modCount;
/*     */     }
/*     */   }
/*     */   
/*     */   class EntrySet
/*     */     extends AbstractSet<Map.Entry<K, V>> {
/*     */     public int size() {
/* 603 */       return LinkedTreeMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 608 */       return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Map.Entry<K, V>>()
/*     */         {
/*     */           public Map.Entry<K, V> next() {
/* 611 */             return nextNode();
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 618 */       return (o instanceof Map.Entry && LinkedTreeMap.this.findByEntry((Map.Entry<?, ?>)o) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 623 */       if (!(o instanceof Map.Entry)) {
/* 624 */         return false;
/*     */       }
/*     */       
/* 627 */       LinkedTreeMap.Node<K, V> node = LinkedTreeMap.this.findByEntry((Map.Entry<?, ?>)o);
/* 628 */       if (node == null) {
/* 629 */         return false;
/*     */       }
/* 631 */       LinkedTreeMap.this.removeInternal(node, true);
/* 632 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 637 */       LinkedTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   final class KeySet
/*     */     extends AbstractSet<K> {
/*     */     public int size() {
/* 644 */       return LinkedTreeMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<K> iterator() {
/* 649 */       return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>()
/*     */         {
/*     */           public K next() {
/* 652 */             return (nextNode()).key;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 659 */       return LinkedTreeMap.this.containsKey(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key) {
/* 664 */       return (LinkedTreeMap.this.removeInternalByKey(key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 669 */       LinkedTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 679 */     return new LinkedHashMap<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/* 685 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\LinkedTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */