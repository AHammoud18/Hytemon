/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2IntAVLTreeMap<K>
/*      */   extends AbstractObject2IntSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2IntMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient IntCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2IntAVLTreeMap() {
/*   66 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   73 */     this.tree = null;
/*   74 */     this.count = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setActualComparator() {
/*   86 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(Comparator<? super K> c) {
/*   95 */     this();
/*   96 */     this.storedComparator = c;
/*   97 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(Map<? extends K, ? extends Integer> m) {
/*  106 */     this();
/*  107 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(SortedMap<K, Integer> m) {
/*  116 */     this(m.comparator());
/*  117 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(Object2IntMap<? extends K> m) {
/*  126 */     this();
/*  127 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(Object2IntSortedMap<K> m) {
/*  136 */     this(m.comparator());
/*  137 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(K[] k, int[] v, Comparator<? super K> c) {
/*  149 */     this(c);
/*  150 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  151 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(K[] k, int[] v) {
/*  162 */     this(k, v, (Comparator<? super K>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(K k1, K k2) {
/*  188 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K> findKey(K k) {
/*  198 */     Entry<K> e = this.tree;
/*      */     int cmp;
/*  200 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  201 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K> locateKey(K k) {
/*  212 */     Entry<K> e = this.tree, last = this.tree;
/*  213 */     int cmp = 0;
/*  214 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  215 */       last = e;
/*  216 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  218 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  228 */     this.dirPath = new boolean[48];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int addTo(K k, int incr) {
/*  245 */     Entry<K> e = add(k);
/*  246 */     int oldValue = e.value;
/*  247 */     e.value += incr;
/*  248 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(K k, int v) {
/*  253 */     Entry<K> e = add(k);
/*  254 */     int oldValue = e.value;
/*  255 */     e.value = v;
/*  256 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> add(K k) {
/*  268 */     Objects.requireNonNull(k);
/*      */     
/*  270 */     this.modified = false;
/*  271 */     Entry<K> e = null;
/*  272 */     if (this.tree == null) {
/*  273 */       this.count++;
/*  274 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  275 */       this.modified = true;
/*      */     } else {
/*  277 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  278 */       int i = 0; while (true) {
/*      */         int cmp;
/*  280 */         if ((cmp = compare(k, p.key)) == 0) {
/*  281 */           return p;
/*      */         }
/*  283 */         if (p.balance() != 0) {
/*  284 */           i = 0;
/*  285 */           z = q;
/*  286 */           y = p;
/*      */         } 
/*  288 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  289 */           if (p.succ()) {
/*  290 */             this.count++;
/*  291 */             e = new Entry<>(k, this.defRetValue);
/*  292 */             this.modified = true;
/*  293 */             if (p.right == null) this.lastEntry = e; 
/*  294 */             e.left = p;
/*  295 */             e.right = p.right;
/*  296 */             p.right(e);
/*      */             break;
/*      */           } 
/*  299 */           q = p;
/*  300 */           p = p.right; continue;
/*      */         } 
/*  302 */         if (p.pred()) {
/*  303 */           this.count++;
/*  304 */           e = new Entry<>(k, this.defRetValue);
/*  305 */           this.modified = true;
/*  306 */           if (p.left == null) this.firstEntry = e; 
/*  307 */           e.right = p;
/*  308 */           e.left = p.left;
/*  309 */           p.left(e);
/*      */           break;
/*      */         } 
/*  312 */         q = p;
/*  313 */         p = p.left;
/*      */       } 
/*      */       
/*  316 */       p = y;
/*  317 */       i = 0;
/*  318 */       while (p != e) {
/*  319 */         if (this.dirPath[i]) { p.incBalance(); }
/*  320 */         else { p.decBalance(); }
/*  321 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  323 */       if (y.balance() == -2)
/*  324 */       { Entry<K> x = y.left;
/*  325 */         if (x.balance() == -1) {
/*  326 */           w = x;
/*  327 */           if (x.succ())
/*  328 */           { x.succ(false);
/*  329 */             y.pred(x); }
/*  330 */           else { y.left = x.right; }
/*  331 */            x.right = y;
/*  332 */           x.balance(0);
/*  333 */           y.balance(0);
/*      */         } else {
/*  335 */           assert x.balance() == 1;
/*  336 */           w = x.right;
/*  337 */           x.right = w.left;
/*  338 */           w.left = x;
/*  339 */           y.left = w.right;
/*  340 */           w.right = y;
/*  341 */           if (w.balance() == -1) {
/*  342 */             x.balance(0);
/*  343 */             y.balance(1);
/*  344 */           } else if (w.balance() == 0) {
/*  345 */             x.balance(0);
/*  346 */             y.balance(0);
/*      */           } else {
/*  348 */             x.balance(-1);
/*  349 */             y.balance(0);
/*      */           } 
/*  351 */           w.balance(0);
/*  352 */           if (w.pred()) {
/*  353 */             x.succ(w);
/*  354 */             w.pred(false);
/*      */           } 
/*  356 */           if (w.succ()) {
/*  357 */             y.pred(w);
/*  358 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  361 */       else if (y.balance() == 2)
/*  362 */       { Entry<K> x = y.right;
/*  363 */         if (x.balance() == 1) {
/*  364 */           w = x;
/*  365 */           if (x.pred())
/*  366 */           { x.pred(false);
/*  367 */             y.succ(x); }
/*  368 */           else { y.right = x.left; }
/*  369 */            x.left = y;
/*  370 */           x.balance(0);
/*  371 */           y.balance(0);
/*      */         } else {
/*  373 */           assert x.balance() == -1;
/*  374 */           w = x.left;
/*  375 */           x.left = w.right;
/*  376 */           w.right = x;
/*  377 */           y.right = w.left;
/*  378 */           w.left = y;
/*  379 */           if (w.balance() == 1) {
/*  380 */             x.balance(0);
/*  381 */             y.balance(-1);
/*  382 */           } else if (w.balance() == 0) {
/*  383 */             x.balance(0);
/*  384 */             y.balance(0);
/*      */           } else {
/*  386 */             x.balance(1);
/*  387 */             y.balance(0);
/*      */           } 
/*  389 */           w.balance(0);
/*  390 */           if (w.pred()) {
/*  391 */             y.succ(w);
/*  392 */             w.pred(false);
/*      */           } 
/*  394 */           if (w.succ()) {
/*  395 */             x.pred(w);
/*  396 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  399 */       else { return e; }
/*  400 */        if (z == null) { this.tree = w; }
/*      */       
/*  402 */       else if (z.left == y) { z.left = w; }
/*  403 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  406 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  416 */     if (e == this.tree) return null;
/*      */     
/*  418 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  420 */       if (y.succ()) {
/*  421 */         Entry<K> p = y.right;
/*  422 */         if (p == null || p.left != e) {
/*  423 */           for (; !x.pred(); x = x.left);
/*  424 */           p = x.left;
/*      */         } 
/*  426 */         return p;
/*  427 */       }  if (x.pred()) {
/*  428 */         Entry<K> p = x.left;
/*  429 */         if (p == null || p.right != e) {
/*  430 */           for (; !y.succ(); y = y.right);
/*  431 */           p = y.right;
/*      */         } 
/*  433 */         return p;
/*      */       } 
/*  435 */       x = x.left;
/*  436 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeInt(Object k) {
/*  445 */     this.modified = false;
/*  446 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  448 */     Entry<K> p = this.tree, q = null;
/*  449 */     boolean dir = false;
/*  450 */     K kk = (K)k;
/*      */     int cmp;
/*  452 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  453 */       if (dir = (cmp > 0)) {
/*  454 */         q = p;
/*  455 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  457 */       q = p;
/*  458 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  461 */     if (p.left == null) this.firstEntry = p.next(); 
/*  462 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  463 */     if (p.succ())
/*  464 */     { if (p.pred())
/*  465 */       { if (q != null)
/*  466 */         { if (dir) { q.succ(p.right); }
/*  467 */           else { q.pred(p.left); }  }
/*  468 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  470 */       else { (p.prev()).right = p.right;
/*  471 */         if (q != null)
/*  472 */         { if (dir) { q.right = p.left; }
/*  473 */           else { q.left = p.left; }  }
/*  474 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  477 */     else { Entry<K> r = p.right;
/*  478 */       if (r.pred()) {
/*  479 */         r.left = p.left;
/*  480 */         r.pred(p.pred());
/*  481 */         if (!r.pred()) (r.prev()).right = r; 
/*  482 */         if (q != null)
/*  483 */         { if (dir) { q.right = r; }
/*  484 */           else { q.left = r; }  }
/*  485 */         else { this.tree = r; }
/*  486 */          r.balance(p.balance());
/*  487 */         q = r;
/*  488 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  492 */           s = r.left;
/*  493 */           if (s.pred())
/*  494 */             break;  r = s;
/*      */         } 
/*  496 */         if (s.succ()) { r.pred(s); }
/*  497 */         else { r.left = s.right; }
/*  498 */          s.left = p.left;
/*  499 */         if (!p.pred()) {
/*  500 */           (p.prev()).right = s;
/*  501 */           s.pred(false);
/*      */         } 
/*  503 */         s.right = p.right;
/*  504 */         s.succ(false);
/*  505 */         if (q != null)
/*  506 */         { if (dir) { q.right = s; }
/*  507 */           else { q.left = s; }  }
/*  508 */         else { this.tree = s; }
/*  509 */          s.balance(p.balance());
/*  510 */         q = r;
/*  511 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  515 */     while (q != null) {
/*  516 */       Entry<K> y = q;
/*  517 */       q = parent(y);
/*  518 */       if (!dir) {
/*  519 */         dir = (q != null && q.left != y);
/*  520 */         y.incBalance();
/*  521 */         if (y.balance() == 1)
/*  522 */           break;  if (y.balance() == 2) {
/*  523 */           Entry<K> x = y.right;
/*  524 */           assert x != null;
/*  525 */           if (x.balance() == -1) {
/*      */             
/*  527 */             assert x.balance() == -1;
/*  528 */             Entry<K> w = x.left;
/*  529 */             x.left = w.right;
/*  530 */             w.right = x;
/*  531 */             y.right = w.left;
/*  532 */             w.left = y;
/*  533 */             if (w.balance() == 1) {
/*  534 */               x.balance(0);
/*  535 */               y.balance(-1);
/*  536 */             } else if (w.balance() == 0) {
/*  537 */               x.balance(0);
/*  538 */               y.balance(0);
/*      */             } else {
/*  540 */               assert w.balance() == -1;
/*  541 */               x.balance(1);
/*  542 */               y.balance(0);
/*      */             } 
/*  544 */             w.balance(0);
/*  545 */             if (w.pred()) {
/*  546 */               y.succ(w);
/*  547 */               w.pred(false);
/*      */             } 
/*  549 */             if (w.succ()) {
/*  550 */               x.pred(w);
/*  551 */               w.succ(false);
/*      */             } 
/*  553 */             if (q != null) {
/*  554 */               if (dir) { q.right = w; continue; }
/*  555 */                q.left = w; continue;
/*  556 */             }  this.tree = w; continue;
/*      */           } 
/*  558 */           if (q != null)
/*  559 */           { if (dir) { q.right = x; }
/*  560 */             else { q.left = x; }  }
/*  561 */           else { this.tree = x; }
/*  562 */            if (x.balance() == 0) {
/*  563 */             y.right = x.left;
/*  564 */             x.left = y;
/*  565 */             x.balance(-1);
/*  566 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  569 */           assert x.balance() == 1;
/*  570 */           if (x.pred())
/*  571 */           { y.succ(true);
/*  572 */             x.pred(false); }
/*  573 */           else { y.right = x.left; }
/*  574 */            x.left = y;
/*  575 */           y.balance(0);
/*  576 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  580 */       dir = (q != null && q.left != y);
/*  581 */       y.decBalance();
/*  582 */       if (y.balance() == -1)
/*  583 */         break;  if (y.balance() == -2) {
/*  584 */         Entry<K> x = y.left;
/*  585 */         assert x != null;
/*  586 */         if (x.balance() == 1) {
/*      */           
/*  588 */           assert x.balance() == 1;
/*  589 */           Entry<K> w = x.right;
/*  590 */           x.right = w.left;
/*  591 */           w.left = x;
/*  592 */           y.left = w.right;
/*  593 */           w.right = y;
/*  594 */           if (w.balance() == -1) {
/*  595 */             x.balance(0);
/*  596 */             y.balance(1);
/*  597 */           } else if (w.balance() == 0) {
/*  598 */             x.balance(0);
/*  599 */             y.balance(0);
/*      */           } else {
/*  601 */             assert w.balance() == 1;
/*  602 */             x.balance(-1);
/*  603 */             y.balance(0);
/*      */           } 
/*  605 */           w.balance(0);
/*  606 */           if (w.pred()) {
/*  607 */             x.succ(w);
/*  608 */             w.pred(false);
/*      */           } 
/*  610 */           if (w.succ()) {
/*  611 */             y.pred(w);
/*  612 */             w.succ(false);
/*      */           } 
/*  614 */           if (q != null) {
/*  615 */             if (dir) { q.right = w; continue; }
/*  616 */              q.left = w; continue;
/*  617 */           }  this.tree = w; continue;
/*      */         } 
/*  619 */         if (q != null)
/*  620 */         { if (dir) { q.right = x; }
/*  621 */           else { q.left = x; }  }
/*  622 */         else { this.tree = x; }
/*  623 */          if (x.balance() == 0) {
/*  624 */           y.left = x.right;
/*  625 */           x.right = y;
/*  626 */           x.balance(1);
/*  627 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  630 */         assert x.balance() == -1;
/*  631 */         if (x.succ())
/*  632 */         { y.pred(true);
/*  633 */           x.succ(false); }
/*  634 */         else { y.left = x.right; }
/*  635 */          x.right = y;
/*  636 */         y.balance(0);
/*  637 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  642 */     this.modified = true;
/*  643 */     this.count--;
/*  644 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(int v) {
/*  649 */     ValueIterator i = new ValueIterator();
/*      */     
/*  651 */     int j = this.count;
/*  652 */     while (j-- != 0) {
/*  653 */       int ev = i.nextInt();
/*  654 */       if (ev == v) return true; 
/*      */     } 
/*  656 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  661 */     this.count = 0;
/*  662 */     this.tree = null;
/*  663 */     this.entries = null;
/*  664 */     this.values = null;
/*  665 */     this.keys = null;
/*  666 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2IntMap.BasicEntry<K>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
/*      */ 
/*      */     
/*      */     Entry<K> left;
/*      */ 
/*      */     
/*      */     Entry<K> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  695 */       super((K)null, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, int v) {
/*  705 */       super(k, v);
/*  706 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  715 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  724 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  733 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  742 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  751 */       if (pred) { this.info |= 0x40000000; }
/*  752 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  761 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  762 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  771 */       this.info |= 0x40000000;
/*  772 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  781 */       this.info |= Integer.MIN_VALUE;
/*  782 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  791 */       this.info &= 0xBFFFFFFF;
/*  792 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  801 */       this.info &= Integer.MAX_VALUE;
/*  802 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  811 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  820 */       this.info &= 0xFFFFFF00;
/*  821 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  826 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  831 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  840 */       Entry<K> next = this.right;
/*  841 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  842 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  851 */       Entry<K> prev = this.left;
/*  852 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  853 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public int setValue(int value) {
/*  858 */       int oldValue = this.value;
/*  859 */       this.value = value;
/*  860 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  868 */         c = (Entry<K>)super.clone();
/*  869 */       } catch (CloneNotSupportedException cantHappen) {
/*  870 */         throw new InternalError();
/*      */       } 
/*  872 */       c.key = this.key;
/*  873 */       c.value = this.value;
/*  874 */       c.info = this.info;
/*  875 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  881 */       if (!(o instanceof Map.Entry)) return false; 
/*  882 */       Map.Entry<K, Integer> e = (Map.Entry<K, Integer>)o;
/*  883 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  888 */       return this.key.hashCode() ^ this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  893 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  931 */     if (k == null) return false; 
/*  932 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  937 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  942 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(Object k) {
/*  948 */     Entry<K> e = findKey((K)k);
/*  949 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  954 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  955 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  960 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  961 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class TreeIterator
/*      */   {
/*      */     Object2IntAVLTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2IntAVLTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2IntAVLTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  990 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  993 */       this.next = Object2IntAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(K k) {
/*  997 */       if ((this.next = Object2IntAVLTreeMap.this.locateKey(k)) != null)
/*  998 */         if (Object2IntAVLTreeMap.this.compare(this.next.key, k) <= 0)
/*  999 */         { this.prev = this.next;
/* 1000 */           this.next = this.next.next(); }
/* 1001 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1006 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1010 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/* 1014 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Object2IntAVLTreeMap.Entry<K> nextEntry() {
/* 1018 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1019 */       this.curr = this.prev = this.next;
/* 1020 */       this.index++;
/* 1021 */       updateNext();
/* 1022 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1026 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Object2IntAVLTreeMap.Entry<K> previousEntry() {
/* 1030 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1031 */       this.curr = this.next = this.prev;
/* 1032 */       this.index--;
/* 1033 */       updatePrevious();
/* 1034 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1038 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1042 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1046 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1049 */       if (this.curr == this.prev) this.index--; 
/* 1050 */       this.next = this.prev = this.curr;
/* 1051 */       updatePrevious();
/* 1052 */       updateNext();
/* 1053 */       Object2IntAVLTreeMap.this.removeInt(this.curr.key);
/* 1054 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1058 */       int i = n;
/* 1059 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1060 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1064 */       int i = n;
/* 1065 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1066 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2IntMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1081 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntMap.Entry<K> next() {
/* 1086 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntMap.Entry<K> previous() {
/* 1091 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Object2IntMap.Entry<K> ok) {
/* 1096 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Object2IntMap.Entry<K> ok) {
/* 1101 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 1108 */     if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2IntMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1113 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1118 */             return new Object2IntAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1123 */             return new Object2IntAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1129 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1130 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1131 */             if (e.getKey() == null) return false; 
/* 1132 */             if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1133 */             Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1134 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1140 */             if (!(o instanceof Map.Entry)) return false; 
/* 1141 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1142 */             if (e.getKey() == null) return false; 
/* 1143 */             if (!(e.getValue() instanceof Integer)) return false; 
/* 1144 */             Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1145 */             if (f == null || f.getIntValue() != ((Integer)e.getValue()).intValue()) return false; 
/* 1146 */             Object2IntAVLTreeMap.this.removeInt(f.key);
/* 1147 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1152 */             return Object2IntAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1157 */             Object2IntAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2IntMap.Entry<K> first() {
/* 1162 */             return Object2IntAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2IntMap.Entry<K> last() {
/* 1167 */             return Object2IntAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> from, Object2IntMap.Entry<K> to) {
/* 1172 */             return Object2IntAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2IntEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> to) {
/* 1177 */             return Object2IntAVLTreeMap.this.headMap(to.getKey()).object2IntEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> from) {
/* 1182 */             return Object2IntAVLTreeMap.this.tailMap(from.getKey()).object2IntEntrySet();
/*      */           }
/*      */         }; 
/* 1185 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1201 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1206 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1211 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractObject2IntSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1219 */       return new Object2IntAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1224 */       return new Object2IntAVLTreeMap.KeyIterator(from);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1239 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1240 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1254 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1259 */       return (previousEntry()).value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntCollection values() {
/* 1274 */     if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1277 */             return (IntIterator)new Object2IntAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(int k) {
/* 1282 */             return Object2IntAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1287 */             return Object2IntAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1292 */             Object2IntAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1295 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1300 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap<K> headMap(K to) {
/* 1305 */     return new Submap(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap<K> tailMap(K from) {
/* 1310 */     return new Submap(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1315 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2IntSortedMap<K>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     K from;
/*      */ 
/*      */ 
/*      */     
/*      */     K to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Object2IntMap.Entry<K>> entries;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */     
/*      */     protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1354 */       if (!bottom && !top && Object2IntAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1355 */       this.from = from;
/* 1356 */       this.bottom = bottom;
/* 1357 */       this.to = to;
/* 1358 */       this.top = top;
/* 1359 */       this.defRetValue = Object2IntAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1364 */       SubmapIterator i = new SubmapIterator();
/* 1365 */       while (i.hasNext()) {
/* 1366 */         i.nextEntry();
/* 1367 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(K k) {
/* 1378 */       return ((this.bottom || Object2IntAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2IntAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 1383 */       if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1386 */               return new Object2IntAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1391 */               return new Object2IntAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1396 */               return Object2IntAVLTreeMap.this.object2IntEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1402 */               if (!(o instanceof Map.Entry)) return false; 
/* 1403 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1404 */               if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1405 */               Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1406 */               return (f != null && Object2IntAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1412 */               if (!(o instanceof Map.Entry)) return false; 
/* 1413 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1414 */               if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1415 */               Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1416 */               if (f != null && Object2IntAVLTreeMap.Submap.this.in(f.key)) Object2IntAVLTreeMap.Submap.this.removeInt(f.key); 
/* 1417 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1422 */               int c = 0;
/* 1423 */               for (Iterator<?> i = iterator(); i.hasNext(); ) { c++; i.next(); }
/* 1424 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1429 */               return !(new Object2IntAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1434 */               Object2IntAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2IntMap.Entry<K> first() {
/* 1439 */               return Object2IntAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2IntMap.Entry<K> last() {
/* 1444 */               return Object2IntAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> from, Object2IntMap.Entry<K> to) {
/* 1449 */               return Object2IntAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2IntEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> to) {
/* 1454 */               return Object2IntAVLTreeMap.Submap.this.headMap(to.getKey()).object2IntEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> from) {
/* 1459 */               return Object2IntAVLTreeMap.Submap.this.tailMap(from.getKey()).object2IntEntrySet();
/*      */             }
/*      */           }; 
/* 1462 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2IntSortedMap<K>.KeySet { private KeySet() {}
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1468 */         return new Object2IntAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1473 */         return new Object2IntAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1479 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1480 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntCollection values() {
/* 1485 */       if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */           {
/*      */             public IntIterator iterator() {
/* 1488 */               return (IntIterator)new Object2IntAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(int k) {
/* 1493 */               return Object2IntAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1498 */               return Object2IntAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1503 */               Object2IntAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1506 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1512 */       if (k == null) return false; 
/* 1513 */       return (in((K)k) && Object2IntAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(int v) {
/* 1518 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1520 */       while (i.hasNext()) {
/* 1521 */         int ev = (i.nextEntry()).value;
/* 1522 */         if (ev == v) return true; 
/*      */       } 
/* 1524 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(Object k) {
/* 1531 */       K kk = (K)k; Object2IntAVLTreeMap.Entry<K> e;
/* 1532 */       return (in(kk) && (e = Object2IntAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int put(K k, int v) {
/* 1537 */       Object2IntAVLTreeMap.this.modified = false;
/* 1538 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1539 */       int oldValue = Object2IntAVLTreeMap.this.put(k, v);
/* 1540 */       return Object2IntAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int removeInt(Object k) {
/* 1546 */       Object2IntAVLTreeMap.this.modified = false;
/* 1547 */       if (!in((K)k)) return this.defRetValue; 
/* 1548 */       int oldValue = Object2IntAVLTreeMap.this.removeInt(k);
/* 1549 */       return Object2IntAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1554 */       SubmapIterator i = new SubmapIterator();
/* 1555 */       int n = 0;
/* 1556 */       while (i.hasNext()) {
/* 1557 */         n++;
/* 1558 */         i.nextEntry();
/*      */       } 
/* 1560 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1565 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1570 */       return Object2IntAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntSortedMap<K> headMap(K to) {
/* 1575 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1576 */       return (Object2IntAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntSortedMap<K> tailMap(K from) {
/* 1581 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1582 */       return (Object2IntAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1587 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1588 */       if (!this.top) to = (Object2IntAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1589 */       if (!this.bottom) from = (Object2IntAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1590 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1591 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2IntAVLTreeMap.Entry<K> firstEntry() {
/*      */       Object2IntAVLTreeMap.Entry<K> e;
/* 1600 */       if (Object2IntAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1604 */       if (this.bottom) { e = Object2IntAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1606 */       { e = Object2IntAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1608 */         if (Object2IntAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1612 */       if (e == null || (!this.top && Object2IntAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1613 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2IntAVLTreeMap.Entry<K> lastEntry() {
/*      */       Object2IntAVLTreeMap.Entry<K> e;
/* 1622 */       if (Object2IntAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1626 */       if (this.top) { e = Object2IntAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1628 */       { e = Object2IntAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1630 */         if (Object2IntAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1634 */       if (e == null || (!this.bottom && Object2IntAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1635 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1640 */       Object2IntAVLTreeMap.Entry<K> e = firstEntry();
/* 1641 */       if (e == null) throw new NoSuchElementException(); 
/* 1642 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1647 */       Object2IntAVLTreeMap.Entry<K> e = lastEntry();
/* 1648 */       if (e == null) throw new NoSuchElementException(); 
/* 1649 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2IntAVLTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1663 */         this.next = Object2IntAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(K k) {
/* 1667 */         this();
/* 1668 */         if (this.next != null) {
/* 1669 */           if (!Object2IntAVLTreeMap.Submap.this.bottom && Object2IntAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1670 */           else if (!Object2IntAVLTreeMap.Submap.this.top && Object2IntAVLTreeMap.this.compare(k, (this.prev = Object2IntAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1672 */           { this.next = Object2IntAVLTreeMap.this.locateKey(k);
/* 1673 */             if (Object2IntAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1674 */             { this.prev = this.next;
/* 1675 */               this.next = this.next.next(); }
/* 1676 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1683 */         this.prev = this.prev.prev();
/* 1684 */         if (!Object2IntAVLTreeMap.Submap.this.bottom && this.prev != null && Object2IntAVLTreeMap.this.compare(this.prev.key, Object2IntAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1689 */         this.next = this.next.next();
/* 1690 */         if (!Object2IntAVLTreeMap.Submap.this.top && this.next != null && Object2IntAVLTreeMap.this.compare(this.next.key, Object2IntAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2IntMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1699 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2IntMap.Entry<K> next() {
/* 1704 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2IntMap.Entry<K> previous() {
/* 1709 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<K>
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(K from) {
/* 1728 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/* 1733 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/* 1738 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public int nextInt() {
/* 1754 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public int previousInt() {
/* 1759 */         return (previousEntry()).value;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap<K> clone() {
/*      */     Object2IntAVLTreeMap<K> c;
/*      */     try {
/* 1778 */       c = (Object2IntAVLTreeMap<K>)super.clone();
/* 1779 */     } catch (CloneNotSupportedException cantHappen) {
/* 1780 */       throw new InternalError();
/*      */     } 
/* 1782 */     c.keys = null;
/* 1783 */     c.values = null;
/* 1784 */     c.entries = null;
/* 1785 */     c.allocatePaths();
/* 1786 */     if (this.count != 0) {
/*      */       
/* 1788 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1789 */       Entry<K> p = rp;
/* 1790 */       rp.left(this.tree);
/* 1791 */       Entry<K> q = rq;
/* 1792 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1794 */         if (!p.pred()) {
/* 1795 */           Entry<K> e = p.left.clone();
/* 1796 */           e.pred(q.left);
/* 1797 */           e.succ(q);
/* 1798 */           q.left(e);
/* 1799 */           p = p.left;
/* 1800 */           q = q.left;
/*      */         } else {
/* 1802 */           while (p.succ()) {
/* 1803 */             p = p.right;
/* 1804 */             if (p == null) {
/* 1805 */               q.right = null;
/* 1806 */               c.tree = rq.left;
/* 1807 */               c.firstEntry = c.tree;
/* 1808 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1809 */               c.lastEntry = c.tree;
/* 1810 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1811 */               return c;
/*      */             } 
/* 1813 */             q = q.right;
/*      */           } 
/* 1815 */           p = p.right;
/* 1816 */           q = q.right;
/*      */         } 
/* 1818 */         if (!p.succ()) {
/* 1819 */           Entry<K> e = p.right.clone();
/* 1820 */           e.succ(q.right);
/* 1821 */           e.pred(q);
/* 1822 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1826 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1830 */     int n = this.count;
/* 1831 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1833 */     s.defaultWriteObject();
/* 1834 */     while (n-- != 0) {
/* 1835 */       Entry<K> e = i.nextEntry();
/* 1836 */       s.writeObject(e.key);
/* 1837 */       s.writeInt(e.value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
/* 1851 */     if (n == 1) {
/* 1852 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readInt());
/* 1853 */       entry.pred(pred);
/* 1854 */       entry.succ(succ);
/* 1855 */       return entry;
/*      */     } 
/* 1857 */     if (n == 2) {
/*      */ 
/*      */       
/* 1860 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readInt());
/* 1861 */       entry.right(new Entry<>((K)s.readObject(), s.readInt()));
/* 1862 */       entry.right.pred(entry);
/* 1863 */       entry.balance(1);
/* 1864 */       entry.pred(pred);
/* 1865 */       entry.right.succ(succ);
/* 1866 */       return entry;
/*      */     } 
/*      */     
/* 1869 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1870 */     Entry<K> top = new Entry<>();
/* 1871 */     top.left(readTree(s, leftN, pred, top));
/* 1872 */     top.key = (K)s.readObject();
/* 1873 */     top.value = s.readInt();
/* 1874 */     top.right(readTree(s, rightN, top, succ));
/* 1875 */     if (n == (n & -n)) top.balance(1); 
/* 1876 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1880 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1883 */     setActualComparator();
/* 1884 */     allocatePaths();
/* 1885 */     if (this.count != 0) {
/* 1886 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1888 */       Entry<K> e = this.tree;
/* 1889 */       for (; e.left() != null; e = e.left());
/* 1890 */       this.firstEntry = e;
/* 1891 */       e = this.tree;
/* 1892 */       for (; e.right() != null; e = e.right());
/* 1893 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2IntAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */