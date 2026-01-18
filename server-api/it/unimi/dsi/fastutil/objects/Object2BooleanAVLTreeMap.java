/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Object2BooleanAVLTreeMap<K>
/*      */   extends AbstractObject2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2BooleanMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2BooleanAVLTreeMap() {
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
/*      */   public Object2BooleanAVLTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2BooleanAVLTreeMap(Map<? extends K, ? extends Boolean> m) {
/*  106 */     this();
/*  107 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanAVLTreeMap(SortedMap<K, Boolean> m) {
/*  116 */     this(m.comparator());
/*  117 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanAVLTreeMap(Object2BooleanMap<? extends K> m) {
/*  126 */     this();
/*  127 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanAVLTreeMap(Object2BooleanSortedMap<K> m) {
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
/*      */   public Object2BooleanAVLTreeMap(K[] k, boolean[] v, Comparator<? super K> c) {
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
/*      */   public Object2BooleanAVLTreeMap(K[] k, boolean[] v) {
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
/*      */   public boolean put(K k, boolean v) {
/*  233 */     Entry<K> e = add(k);
/*  234 */     boolean oldValue = e.value;
/*  235 */     e.value = v;
/*  236 */     return oldValue;
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
/*  248 */     Objects.requireNonNull(k);
/*      */     
/*  250 */     this.modified = false;
/*  251 */     Entry<K> e = null;
/*  252 */     if (this.tree == null) {
/*  253 */       this.count++;
/*  254 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  255 */       this.modified = true;
/*      */     } else {
/*  257 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  258 */       int i = 0; while (true) {
/*      */         int cmp;
/*  260 */         if ((cmp = compare(k, p.key)) == 0) {
/*  261 */           return p;
/*      */         }
/*  263 */         if (p.balance() != 0) {
/*  264 */           i = 0;
/*  265 */           z = q;
/*  266 */           y = p;
/*      */         } 
/*  268 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  269 */           if (p.succ()) {
/*  270 */             this.count++;
/*  271 */             e = new Entry<>(k, this.defRetValue);
/*  272 */             this.modified = true;
/*  273 */             if (p.right == null) this.lastEntry = e; 
/*  274 */             e.left = p;
/*  275 */             e.right = p.right;
/*  276 */             p.right(e);
/*      */             break;
/*      */           } 
/*  279 */           q = p;
/*  280 */           p = p.right; continue;
/*      */         } 
/*  282 */         if (p.pred()) {
/*  283 */           this.count++;
/*  284 */           e = new Entry<>(k, this.defRetValue);
/*  285 */           this.modified = true;
/*  286 */           if (p.left == null) this.firstEntry = e; 
/*  287 */           e.right = p;
/*  288 */           e.left = p.left;
/*  289 */           p.left(e);
/*      */           break;
/*      */         } 
/*  292 */         q = p;
/*  293 */         p = p.left;
/*      */       } 
/*      */       
/*  296 */       p = y;
/*  297 */       i = 0;
/*  298 */       while (p != e) {
/*  299 */         if (this.dirPath[i]) { p.incBalance(); }
/*  300 */         else { p.decBalance(); }
/*  301 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  303 */       if (y.balance() == -2)
/*  304 */       { Entry<K> x = y.left;
/*  305 */         if (x.balance() == -1) {
/*  306 */           w = x;
/*  307 */           if (x.succ())
/*  308 */           { x.succ(false);
/*  309 */             y.pred(x); }
/*  310 */           else { y.left = x.right; }
/*  311 */            x.right = y;
/*  312 */           x.balance(0);
/*  313 */           y.balance(0);
/*      */         } else {
/*  315 */           assert x.balance() == 1;
/*  316 */           w = x.right;
/*  317 */           x.right = w.left;
/*  318 */           w.left = x;
/*  319 */           y.left = w.right;
/*  320 */           w.right = y;
/*  321 */           if (w.balance() == -1) {
/*  322 */             x.balance(0);
/*  323 */             y.balance(1);
/*  324 */           } else if (w.balance() == 0) {
/*  325 */             x.balance(0);
/*  326 */             y.balance(0);
/*      */           } else {
/*  328 */             x.balance(-1);
/*  329 */             y.balance(0);
/*      */           } 
/*  331 */           w.balance(0);
/*  332 */           if (w.pred()) {
/*  333 */             x.succ(w);
/*  334 */             w.pred(false);
/*      */           } 
/*  336 */           if (w.succ()) {
/*  337 */             y.pred(w);
/*  338 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  341 */       else if (y.balance() == 2)
/*  342 */       { Entry<K> x = y.right;
/*  343 */         if (x.balance() == 1) {
/*  344 */           w = x;
/*  345 */           if (x.pred())
/*  346 */           { x.pred(false);
/*  347 */             y.succ(x); }
/*  348 */           else { y.right = x.left; }
/*  349 */            x.left = y;
/*  350 */           x.balance(0);
/*  351 */           y.balance(0);
/*      */         } else {
/*  353 */           assert x.balance() == -1;
/*  354 */           w = x.left;
/*  355 */           x.left = w.right;
/*  356 */           w.right = x;
/*  357 */           y.right = w.left;
/*  358 */           w.left = y;
/*  359 */           if (w.balance() == 1) {
/*  360 */             x.balance(0);
/*  361 */             y.balance(-1);
/*  362 */           } else if (w.balance() == 0) {
/*  363 */             x.balance(0);
/*  364 */             y.balance(0);
/*      */           } else {
/*  366 */             x.balance(1);
/*  367 */             y.balance(0);
/*      */           } 
/*  369 */           w.balance(0);
/*  370 */           if (w.pred()) {
/*  371 */             y.succ(w);
/*  372 */             w.pred(false);
/*      */           } 
/*  374 */           if (w.succ()) {
/*  375 */             x.pred(w);
/*  376 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  379 */       else { return e; }
/*  380 */        if (z == null) { this.tree = w; }
/*      */       
/*  382 */       else if (z.left == y) { z.left = w; }
/*  383 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  386 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  396 */     if (e == this.tree) return null;
/*      */     
/*  398 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  400 */       if (y.succ()) {
/*  401 */         Entry<K> p = y.right;
/*  402 */         if (p == null || p.left != e) {
/*  403 */           for (; !x.pred(); x = x.left);
/*  404 */           p = x.left;
/*      */         } 
/*  406 */         return p;
/*  407 */       }  if (x.pred()) {
/*  408 */         Entry<K> p = x.left;
/*  409 */         if (p == null || p.right != e) {
/*  410 */           for (; !y.succ(); y = y.right);
/*  411 */           p = y.right;
/*      */         } 
/*  413 */         return p;
/*      */       } 
/*  415 */       x = x.left;
/*  416 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  425 */     this.modified = false;
/*  426 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  428 */     Entry<K> p = this.tree, q = null;
/*  429 */     boolean dir = false;
/*  430 */     K kk = (K)k;
/*      */     int cmp;
/*  432 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  433 */       if (dir = (cmp > 0)) {
/*  434 */         q = p;
/*  435 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  437 */       q = p;
/*  438 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  441 */     if (p.left == null) this.firstEntry = p.next(); 
/*  442 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  443 */     if (p.succ())
/*  444 */     { if (p.pred())
/*  445 */       { if (q != null)
/*  446 */         { if (dir) { q.succ(p.right); }
/*  447 */           else { q.pred(p.left); }  }
/*  448 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  450 */       else { (p.prev()).right = p.right;
/*  451 */         if (q != null)
/*  452 */         { if (dir) { q.right = p.left; }
/*  453 */           else { q.left = p.left; }  }
/*  454 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  457 */     else { Entry<K> r = p.right;
/*  458 */       if (r.pred()) {
/*  459 */         r.left = p.left;
/*  460 */         r.pred(p.pred());
/*  461 */         if (!r.pred()) (r.prev()).right = r; 
/*  462 */         if (q != null)
/*  463 */         { if (dir) { q.right = r; }
/*  464 */           else { q.left = r; }  }
/*  465 */         else { this.tree = r; }
/*  466 */          r.balance(p.balance());
/*  467 */         q = r;
/*  468 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  472 */           s = r.left;
/*  473 */           if (s.pred())
/*  474 */             break;  r = s;
/*      */         } 
/*  476 */         if (s.succ()) { r.pred(s); }
/*  477 */         else { r.left = s.right; }
/*  478 */          s.left = p.left;
/*  479 */         if (!p.pred()) {
/*  480 */           (p.prev()).right = s;
/*  481 */           s.pred(false);
/*      */         } 
/*  483 */         s.right = p.right;
/*  484 */         s.succ(false);
/*  485 */         if (q != null)
/*  486 */         { if (dir) { q.right = s; }
/*  487 */           else { q.left = s; }  }
/*  488 */         else { this.tree = s; }
/*  489 */          s.balance(p.balance());
/*  490 */         q = r;
/*  491 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  495 */     while (q != null) {
/*  496 */       Entry<K> y = q;
/*  497 */       q = parent(y);
/*  498 */       if (!dir) {
/*  499 */         dir = (q != null && q.left != y);
/*  500 */         y.incBalance();
/*  501 */         if (y.balance() == 1)
/*  502 */           break;  if (y.balance() == 2) {
/*  503 */           Entry<K> x = y.right;
/*  504 */           assert x != null;
/*  505 */           if (x.balance() == -1) {
/*      */             
/*  507 */             assert x.balance() == -1;
/*  508 */             Entry<K> w = x.left;
/*  509 */             x.left = w.right;
/*  510 */             w.right = x;
/*  511 */             y.right = w.left;
/*  512 */             w.left = y;
/*  513 */             if (w.balance() == 1) {
/*  514 */               x.balance(0);
/*  515 */               y.balance(-1);
/*  516 */             } else if (w.balance() == 0) {
/*  517 */               x.balance(0);
/*  518 */               y.balance(0);
/*      */             } else {
/*  520 */               assert w.balance() == -1;
/*  521 */               x.balance(1);
/*  522 */               y.balance(0);
/*      */             } 
/*  524 */             w.balance(0);
/*  525 */             if (w.pred()) {
/*  526 */               y.succ(w);
/*  527 */               w.pred(false);
/*      */             } 
/*  529 */             if (w.succ()) {
/*  530 */               x.pred(w);
/*  531 */               w.succ(false);
/*      */             } 
/*  533 */             if (q != null) {
/*  534 */               if (dir) { q.right = w; continue; }
/*  535 */                q.left = w; continue;
/*  536 */             }  this.tree = w; continue;
/*      */           } 
/*  538 */           if (q != null)
/*  539 */           { if (dir) { q.right = x; }
/*  540 */             else { q.left = x; }  }
/*  541 */           else { this.tree = x; }
/*  542 */            if (x.balance() == 0) {
/*  543 */             y.right = x.left;
/*  544 */             x.left = y;
/*  545 */             x.balance(-1);
/*  546 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  549 */           assert x.balance() == 1;
/*  550 */           if (x.pred())
/*  551 */           { y.succ(true);
/*  552 */             x.pred(false); }
/*  553 */           else { y.right = x.left; }
/*  554 */            x.left = y;
/*  555 */           y.balance(0);
/*  556 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  560 */       dir = (q != null && q.left != y);
/*  561 */       y.decBalance();
/*  562 */       if (y.balance() == -1)
/*  563 */         break;  if (y.balance() == -2) {
/*  564 */         Entry<K> x = y.left;
/*  565 */         assert x != null;
/*  566 */         if (x.balance() == 1) {
/*      */           
/*  568 */           assert x.balance() == 1;
/*  569 */           Entry<K> w = x.right;
/*  570 */           x.right = w.left;
/*  571 */           w.left = x;
/*  572 */           y.left = w.right;
/*  573 */           w.right = y;
/*  574 */           if (w.balance() == -1) {
/*  575 */             x.balance(0);
/*  576 */             y.balance(1);
/*  577 */           } else if (w.balance() == 0) {
/*  578 */             x.balance(0);
/*  579 */             y.balance(0);
/*      */           } else {
/*  581 */             assert w.balance() == 1;
/*  582 */             x.balance(-1);
/*  583 */             y.balance(0);
/*      */           } 
/*  585 */           w.balance(0);
/*  586 */           if (w.pred()) {
/*  587 */             x.succ(w);
/*  588 */             w.pred(false);
/*      */           } 
/*  590 */           if (w.succ()) {
/*  591 */             y.pred(w);
/*  592 */             w.succ(false);
/*      */           } 
/*  594 */           if (q != null) {
/*  595 */             if (dir) { q.right = w; continue; }
/*  596 */              q.left = w; continue;
/*  597 */           }  this.tree = w; continue;
/*      */         } 
/*  599 */         if (q != null)
/*  600 */         { if (dir) { q.right = x; }
/*  601 */           else { q.left = x; }  }
/*  602 */         else { this.tree = x; }
/*  603 */          if (x.balance() == 0) {
/*  604 */           y.left = x.right;
/*  605 */           x.right = y;
/*  606 */           x.balance(1);
/*  607 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  610 */         assert x.balance() == -1;
/*  611 */         if (x.succ())
/*  612 */         { y.pred(true);
/*  613 */           x.succ(false); }
/*  614 */         else { y.left = x.right; }
/*  615 */          x.right = y;
/*  616 */         y.balance(0);
/*  617 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  622 */     this.modified = true;
/*  623 */     this.count--;
/*  624 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  629 */     ValueIterator i = new ValueIterator();
/*      */     
/*  631 */     int j = this.count;
/*  632 */     while (j-- != 0) {
/*  633 */       boolean ev = i.nextBoolean();
/*  634 */       if (ev == v) return true; 
/*      */     } 
/*  636 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  641 */     this.count = 0;
/*  642 */     this.tree = null;
/*  643 */     this.entries = null;
/*  644 */     this.values = null;
/*  645 */     this.keys = null;
/*  646 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2BooleanMap.BasicEntry<K>
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
/*  675 */       super((K)null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, boolean v) {
/*  685 */       super(k, v);
/*  686 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  695 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  704 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  713 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  722 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  731 */       if (pred) { this.info |= 0x40000000; }
/*  732 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  741 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  742 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  751 */       this.info |= 0x40000000;
/*  752 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  761 */       this.info |= Integer.MIN_VALUE;
/*  762 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  771 */       this.info &= 0xBFFFFFFF;
/*  772 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  781 */       this.info &= Integer.MAX_VALUE;
/*  782 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  791 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  800 */       this.info &= 0xFFFFFF00;
/*  801 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  806 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  811 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  820 */       Entry<K> next = this.right;
/*  821 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  822 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  831 */       Entry<K> prev = this.left;
/*  832 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  833 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  838 */       boolean oldValue = this.value;
/*  839 */       this.value = value;
/*  840 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  848 */         c = (Entry<K>)super.clone();
/*  849 */       } catch (CloneNotSupportedException cantHappen) {
/*  850 */         throw new InternalError();
/*      */       } 
/*  852 */       c.key = this.key;
/*  853 */       c.value = this.value;
/*  854 */       c.info = this.info;
/*  855 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  861 */       if (!(o instanceof Map.Entry)) return false; 
/*  862 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  863 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  868 */       return this.key.hashCode() ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  873 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  911 */     if (k == null) return false; 
/*  912 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  917 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  922 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  928 */     Entry<K> e = findKey((K)k);
/*  929 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  934 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  935 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  940 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  941 */     return this.lastEntry.key;
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
/*      */     Object2BooleanAVLTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanAVLTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanAVLTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  970 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  973 */       this.next = Object2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(K k) {
/*  977 */       if ((this.next = Object2BooleanAVLTreeMap.this.locateKey(k)) != null)
/*  978 */         if (Object2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0)
/*  979 */         { this.prev = this.next;
/*  980 */           this.next = this.next.next(); }
/*  981 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  986 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  990 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  994 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Object2BooleanAVLTreeMap.Entry<K> nextEntry() {
/*  998 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  999 */       this.curr = this.prev = this.next;
/* 1000 */       this.index++;
/* 1001 */       updateNext();
/* 1002 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1006 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Object2BooleanAVLTreeMap.Entry<K> previousEntry() {
/* 1010 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1011 */       this.curr = this.next = this.prev;
/* 1012 */       this.index--;
/* 1013 */       updatePrevious();
/* 1014 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1018 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1022 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1026 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1029 */       if (this.curr == this.prev) this.index--; 
/* 1030 */       this.next = this.prev = this.curr;
/* 1031 */       updatePrevious();
/* 1032 */       updateNext();
/* 1033 */       Object2BooleanAVLTreeMap.this.removeBoolean(this.curr.key);
/* 1034 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1038 */       int i = n;
/* 1039 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1040 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1044 */       int i = n;
/* 1045 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1046 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2BooleanMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1061 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanMap.Entry<K> next() {
/* 1066 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanMap.Entry<K> previous() {
/* 1071 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Object2BooleanMap.Entry<K> ok) {
/* 1076 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Object2BooleanMap.Entry<K> ok) {
/* 1081 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1088 */     if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2BooleanMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1093 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1098 */             return new Object2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1103 */             return new Object2BooleanAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1109 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1110 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1111 */             if (e.getKey() == null) return false; 
/* 1112 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1113 */             Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1114 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1120 */             if (!(o instanceof Map.Entry)) return false; 
/* 1121 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1122 */             if (e.getKey() == null) return false; 
/* 1123 */             if (!(e.getValue() instanceof Boolean)) return false; 
/* 1124 */             Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1125 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue()) return false; 
/* 1126 */             Object2BooleanAVLTreeMap.this.removeBoolean(f.key);
/* 1127 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1132 */             return Object2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1137 */             Object2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2BooleanMap.Entry<K> first() {
/* 1142 */             return Object2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2BooleanMap.Entry<K> last() {
/* 1147 */             return Object2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1152 */             return Object2BooleanAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1157 */             return Object2BooleanAVLTreeMap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1162 */             return Object2BooleanAVLTreeMap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1165 */     return this.entries;
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
/* 1181 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1186 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1191 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1199 */       return new Object2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1204 */       return new Object2BooleanAVLTreeMap.KeyIterator(from);
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
/* 1219 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1220 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements BooleanListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1234 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/* 1239 */       return (previousEntry()).value;
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
/*      */   public BooleanCollection values() {
/* 1254 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1257 */             return (BooleanIterator)new Object2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1262 */             return Object2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1267 */             return Object2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1272 */             Object2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1275 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1280 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1285 */     return new Submap(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1290 */     return new Submap(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1295 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2BooleanSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2BooleanMap.Entry<K>> entries;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1334 */       if (!bottom && !top && Object2BooleanAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1335 */       this.from = from;
/* 1336 */       this.bottom = bottom;
/* 1337 */       this.to = to;
/* 1338 */       this.top = top;
/* 1339 */       this.defRetValue = Object2BooleanAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1344 */       SubmapIterator i = new SubmapIterator();
/* 1345 */       while (i.hasNext()) {
/* 1346 */         i.nextEntry();
/* 1347 */         i.remove();
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
/* 1358 */       return ((this.bottom || Object2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2BooleanAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1363 */       if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1366 */               return new Object2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1371 */               return new Object2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1376 */               return Object2BooleanAVLTreeMap.this.object2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1382 */               if (!(o instanceof Map.Entry)) return false; 
/* 1383 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1384 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1385 */               Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1386 */               return (f != null && Object2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1392 */               if (!(o instanceof Map.Entry)) return false; 
/* 1393 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1394 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1395 */               Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1396 */               if (f != null && Object2BooleanAVLTreeMap.Submap.this.in(f.key)) Object2BooleanAVLTreeMap.Submap.this.removeBoolean(f.key); 
/* 1397 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1402 */               int c = 0;
/* 1403 */               for (Iterator<?> i = iterator(); i.hasNext(); ) { c++; i.next(); }
/* 1404 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1409 */               return !(new Object2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1414 */               Object2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2BooleanMap.Entry<K> first() {
/* 1419 */               return Object2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2BooleanMap.Entry<K> last() {
/* 1424 */               return Object2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1429 */               return Object2BooleanAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1434 */               return Object2BooleanAVLTreeMap.Submap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1439 */               return Object2BooleanAVLTreeMap.Submap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1442 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet { private KeySet() {}
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1448 */         return new Object2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1453 */         return new Object2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1459 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1460 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanCollection values() {
/* 1465 */       if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1468 */               return (BooleanIterator)new Object2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1473 */               return Object2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1478 */               return Object2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1483 */               Object2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1486 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1492 */       if (k == null) return false; 
/* 1493 */       return (in((K)k) && Object2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1498 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1500 */       while (i.hasNext()) {
/* 1501 */         boolean ev = (i.nextEntry()).value;
/* 1502 */         if (ev == v) return true; 
/*      */       } 
/* 1504 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object k) {
/* 1511 */       K kk = (K)k; Object2BooleanAVLTreeMap.Entry<K> e;
/* 1512 */       return (in(kk) && (e = Object2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean put(K k, boolean v) {
/* 1517 */       Object2BooleanAVLTreeMap.this.modified = false;
/* 1518 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1519 */       boolean oldValue = Object2BooleanAVLTreeMap.this.put(k, v);
/* 1520 */       return Object2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(Object k) {
/* 1526 */       Object2BooleanAVLTreeMap.this.modified = false;
/* 1527 */       if (!in((K)k)) return this.defRetValue; 
/* 1528 */       boolean oldValue = Object2BooleanAVLTreeMap.this.removeBoolean(k);
/* 1529 */       return Object2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1534 */       SubmapIterator i = new SubmapIterator();
/* 1535 */       int n = 0;
/* 1536 */       while (i.hasNext()) {
/* 1537 */         n++;
/* 1538 */         i.nextEntry();
/*      */       } 
/* 1540 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1545 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1550 */       return Object2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 1555 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1556 */       return (Object2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1561 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1562 */       return (Object2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1567 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1568 */       if (!this.top) to = (Object2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1569 */       if (!this.bottom) from = (Object2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1570 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1571 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanAVLTreeMap.Entry<K> firstEntry() {
/*      */       Object2BooleanAVLTreeMap.Entry<K> e;
/* 1580 */       if (Object2BooleanAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1584 */       if (this.bottom) { e = Object2BooleanAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1586 */       { e = Object2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1588 */         if (Object2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1592 */       if (e == null || (!this.top && Object2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1593 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanAVLTreeMap.Entry<K> lastEntry() {
/*      */       Object2BooleanAVLTreeMap.Entry<K> e;
/* 1602 */       if (Object2BooleanAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1606 */       if (this.top) { e = Object2BooleanAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1608 */       { e = Object2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1610 */         if (Object2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1614 */       if (e == null || (!this.bottom && Object2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1615 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1620 */       Object2BooleanAVLTreeMap.Entry<K> e = firstEntry();
/* 1621 */       if (e == null) throw new NoSuchElementException(); 
/* 1622 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1627 */       Object2BooleanAVLTreeMap.Entry<K> e = lastEntry();
/* 1628 */       if (e == null) throw new NoSuchElementException(); 
/* 1629 */       return e.key;
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
/*      */       extends Object2BooleanAVLTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1643 */         this.next = Object2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(K k) {
/* 1647 */         this();
/* 1648 */         if (this.next != null) {
/* 1649 */           if (!Object2BooleanAVLTreeMap.Submap.this.bottom && Object2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1650 */           else if (!Object2BooleanAVLTreeMap.Submap.this.top && Object2BooleanAVLTreeMap.this.compare(k, (this.prev = Object2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1652 */           { this.next = Object2BooleanAVLTreeMap.this.locateKey(k);
/* 1653 */             if (Object2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1654 */             { this.prev = this.next;
/* 1655 */               this.next = this.next.next(); }
/* 1656 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1663 */         this.prev = this.prev.prev();
/* 1664 */         if (!Object2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Object2BooleanAVLTreeMap.this.compare(this.prev.key, Object2BooleanAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1669 */         this.next = this.next.next();
/* 1670 */         if (!Object2BooleanAVLTreeMap.Submap.this.top && this.next != null && Object2BooleanAVLTreeMap.this.compare(this.next.key, Object2BooleanAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1679 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2BooleanMap.Entry<K> next() {
/* 1684 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2BooleanMap.Entry<K> previous() {
/* 1689 */         return previousEntry();
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
/* 1708 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/* 1713 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/* 1718 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements BooleanListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean nextBoolean() {
/* 1734 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean previousBoolean() {
/* 1739 */         return (previousEntry()).value;
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
/*      */   public Object2BooleanAVLTreeMap<K> clone() {
/*      */     Object2BooleanAVLTreeMap<K> c;
/*      */     try {
/* 1758 */       c = (Object2BooleanAVLTreeMap<K>)super.clone();
/* 1759 */     } catch (CloneNotSupportedException cantHappen) {
/* 1760 */       throw new InternalError();
/*      */     } 
/* 1762 */     c.keys = null;
/* 1763 */     c.values = null;
/* 1764 */     c.entries = null;
/* 1765 */     c.allocatePaths();
/* 1766 */     if (this.count != 0) {
/*      */       
/* 1768 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1769 */       Entry<K> p = rp;
/* 1770 */       rp.left(this.tree);
/* 1771 */       Entry<K> q = rq;
/* 1772 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1774 */         if (!p.pred()) {
/* 1775 */           Entry<K> e = p.left.clone();
/* 1776 */           e.pred(q.left);
/* 1777 */           e.succ(q);
/* 1778 */           q.left(e);
/* 1779 */           p = p.left;
/* 1780 */           q = q.left;
/*      */         } else {
/* 1782 */           while (p.succ()) {
/* 1783 */             p = p.right;
/* 1784 */             if (p == null) {
/* 1785 */               q.right = null;
/* 1786 */               c.tree = rq.left;
/* 1787 */               c.firstEntry = c.tree;
/* 1788 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1789 */               c.lastEntry = c.tree;
/* 1790 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1791 */               return c;
/*      */             } 
/* 1793 */             q = q.right;
/*      */           } 
/* 1795 */           p = p.right;
/* 1796 */           q = q.right;
/*      */         } 
/* 1798 */         if (!p.succ()) {
/* 1799 */           Entry<K> e = p.right.clone();
/* 1800 */           e.succ(q.right);
/* 1801 */           e.pred(q);
/* 1802 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1806 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1810 */     int n = this.count;
/* 1811 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1813 */     s.defaultWriteObject();
/* 1814 */     while (n-- != 0) {
/* 1815 */       Entry<K> e = i.nextEntry();
/* 1816 */       s.writeObject(e.key);
/* 1817 */       s.writeBoolean(e.value);
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
/* 1831 */     if (n == 1) {
/* 1832 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1833 */       entry.pred(pred);
/* 1834 */       entry.succ(succ);
/* 1835 */       return entry;
/*      */     } 
/* 1837 */     if (n == 2) {
/*      */ 
/*      */       
/* 1840 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1841 */       entry.right(new Entry<>((K)s.readObject(), s.readBoolean()));
/* 1842 */       entry.right.pred(entry);
/* 1843 */       entry.balance(1);
/* 1844 */       entry.pred(pred);
/* 1845 */       entry.right.succ(succ);
/* 1846 */       return entry;
/*      */     } 
/*      */     
/* 1849 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1850 */     Entry<K> top = new Entry<>();
/* 1851 */     top.left(readTree(s, leftN, pred, top));
/* 1852 */     top.key = (K)s.readObject();
/* 1853 */     top.value = s.readBoolean();
/* 1854 */     top.right(readTree(s, rightN, top, succ));
/* 1855 */     if (n == (n & -n)) top.balance(1); 
/* 1856 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1860 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1863 */     setActualComparator();
/* 1864 */     allocatePaths();
/* 1865 */     if (this.count != 0) {
/* 1866 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1868 */       Entry<K> e = this.tree;
/* 1869 */       for (; e.left() != null; e = e.left());
/* 1870 */       this.firstEntry = e;
/* 1871 */       e = this.tree;
/* 1872 */       for (; e.right() != null; e = e.right());
/* 1873 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */