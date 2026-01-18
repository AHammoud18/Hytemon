/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
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
/*      */ public class Object2ReferenceAVLTreeMap<K, V>
/*      */   extends AbstractObject2ReferenceSortedMap<K, V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K, V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K, V> firstEntry;
/*      */   protected transient Entry<K, V> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2ReferenceAVLTreeMap() {
/*   62 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   69 */     this.tree = null;
/*   70 */     this.count = 0;
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
/*   82 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceAVLTreeMap(Comparator<? super K> c) {
/*   91 */     this();
/*   92 */     this.storedComparator = c;
/*   93 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceAVLTreeMap(Map<? extends K, ? extends V> m) {
/*  102 */     this();
/*  103 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceAVLTreeMap(SortedMap<K, V> m) {
/*  112 */     this(m.comparator());
/*  113 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceAVLTreeMap(Object2ReferenceMap<? extends K, ? extends V> m) {
/*  122 */     this();
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceAVLTreeMap(Object2ReferenceSortedMap<K, V> m) {
/*  132 */     this(m.comparator());
/*  133 */     putAll(m);
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
/*      */   public Object2ReferenceAVLTreeMap(K[] k, V[] v, Comparator<? super K> c) {
/*  145 */     this(c);
/*  146 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  147 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceAVLTreeMap(K[] k, V[] v) {
/*  158 */     this(k, v, (Comparator<? super K>)null);
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
/*  184 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> findKey(K k) {
/*  194 */     Entry<K, V> e = this.tree;
/*      */     int cmp;
/*  196 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  197 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> locateKey(K k) {
/*  208 */     Entry<K, V> e = this.tree, last = this.tree;
/*  209 */     int cmp = 0;
/*  210 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  211 */       last = e;
/*  212 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  214 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  224 */     this.dirPath = new boolean[48];
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  229 */     Entry<K, V> e = add(k);
/*  230 */     V oldValue = e.value;
/*  231 */     e.value = v;
/*  232 */     return oldValue;
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
/*      */   private Entry<K, V> add(K k) {
/*  244 */     Objects.requireNonNull(k);
/*      */     
/*  246 */     this.modified = false;
/*  247 */     Entry<K, V> e = null;
/*  248 */     if (this.tree == null) {
/*  249 */       this.count++;
/*  250 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  251 */       this.modified = true;
/*      */     } else {
/*  253 */       Entry<K, V> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  254 */       int i = 0; while (true) {
/*      */         int cmp;
/*  256 */         if ((cmp = compare(k, p.key)) == 0) {
/*  257 */           return p;
/*      */         }
/*  259 */         if (p.balance() != 0) {
/*  260 */           i = 0;
/*  261 */           z = q;
/*  262 */           y = p;
/*      */         } 
/*  264 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  265 */           if (p.succ()) {
/*  266 */             this.count++;
/*  267 */             e = new Entry<>(k, this.defRetValue);
/*  268 */             this.modified = true;
/*  269 */             if (p.right == null) this.lastEntry = e; 
/*  270 */             e.left = p;
/*  271 */             e.right = p.right;
/*  272 */             p.right(e);
/*      */             break;
/*      */           } 
/*  275 */           q = p;
/*  276 */           p = p.right; continue;
/*      */         } 
/*  278 */         if (p.pred()) {
/*  279 */           this.count++;
/*  280 */           e = new Entry<>(k, this.defRetValue);
/*  281 */           this.modified = true;
/*  282 */           if (p.left == null) this.firstEntry = e; 
/*  283 */           e.right = p;
/*  284 */           e.left = p.left;
/*  285 */           p.left(e);
/*      */           break;
/*      */         } 
/*  288 */         q = p;
/*  289 */         p = p.left;
/*      */       } 
/*      */       
/*  292 */       p = y;
/*  293 */       i = 0;
/*  294 */       while (p != e) {
/*  295 */         if (this.dirPath[i]) { p.incBalance(); }
/*  296 */         else { p.decBalance(); }
/*  297 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  299 */       if (y.balance() == -2)
/*  300 */       { Entry<K, V> x = y.left;
/*  301 */         if (x.balance() == -1) {
/*  302 */           w = x;
/*  303 */           if (x.succ())
/*  304 */           { x.succ(false);
/*  305 */             y.pred(x); }
/*  306 */           else { y.left = x.right; }
/*  307 */            x.right = y;
/*  308 */           x.balance(0);
/*  309 */           y.balance(0);
/*      */         } else {
/*  311 */           assert x.balance() == 1;
/*  312 */           w = x.right;
/*  313 */           x.right = w.left;
/*  314 */           w.left = x;
/*  315 */           y.left = w.right;
/*  316 */           w.right = y;
/*  317 */           if (w.balance() == -1) {
/*  318 */             x.balance(0);
/*  319 */             y.balance(1);
/*  320 */           } else if (w.balance() == 0) {
/*  321 */             x.balance(0);
/*  322 */             y.balance(0);
/*      */           } else {
/*  324 */             x.balance(-1);
/*  325 */             y.balance(0);
/*      */           } 
/*  327 */           w.balance(0);
/*  328 */           if (w.pred()) {
/*  329 */             x.succ(w);
/*  330 */             w.pred(false);
/*      */           } 
/*  332 */           if (w.succ()) {
/*  333 */             y.pred(w);
/*  334 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  337 */       else if (y.balance() == 2)
/*  338 */       { Entry<K, V> x = y.right;
/*  339 */         if (x.balance() == 1) {
/*  340 */           w = x;
/*  341 */           if (x.pred())
/*  342 */           { x.pred(false);
/*  343 */             y.succ(x); }
/*  344 */           else { y.right = x.left; }
/*  345 */            x.left = y;
/*  346 */           x.balance(0);
/*  347 */           y.balance(0);
/*      */         } else {
/*  349 */           assert x.balance() == -1;
/*  350 */           w = x.left;
/*  351 */           x.left = w.right;
/*  352 */           w.right = x;
/*  353 */           y.right = w.left;
/*  354 */           w.left = y;
/*  355 */           if (w.balance() == 1) {
/*  356 */             x.balance(0);
/*  357 */             y.balance(-1);
/*  358 */           } else if (w.balance() == 0) {
/*  359 */             x.balance(0);
/*  360 */             y.balance(0);
/*      */           } else {
/*  362 */             x.balance(1);
/*  363 */             y.balance(0);
/*      */           } 
/*  365 */           w.balance(0);
/*  366 */           if (w.pred()) {
/*  367 */             y.succ(w);
/*  368 */             w.pred(false);
/*      */           } 
/*  370 */           if (w.succ()) {
/*  371 */             x.pred(w);
/*  372 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  375 */       else { return e; }
/*  376 */        if (z == null) { this.tree = w; }
/*      */       
/*  378 */       else if (z.left == y) { z.left = w; }
/*  379 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  382 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K, V> parent(Entry<K, V> e) {
/*  392 */     if (e == this.tree) return null;
/*      */     
/*  394 */     Entry<K, V> y = e, x = y;
/*      */     while (true) {
/*  396 */       if (y.succ()) {
/*  397 */         Entry<K, V> p = y.right;
/*  398 */         if (p == null || p.left != e) {
/*  399 */           for (; !x.pred(); x = x.left);
/*  400 */           p = x.left;
/*      */         } 
/*  402 */         return p;
/*  403 */       }  if (x.pred()) {
/*  404 */         Entry<K, V> p = x.left;
/*  405 */         if (p == null || p.right != e) {
/*  406 */           for (; !y.succ(); y = y.right);
/*  407 */           p = y.right;
/*      */         } 
/*  409 */         return p;
/*      */       } 
/*  411 */       x = x.left;
/*  412 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  421 */     this.modified = false;
/*  422 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  424 */     Entry<K, V> p = this.tree, q = null;
/*  425 */     boolean dir = false;
/*  426 */     K kk = (K)k;
/*      */     int cmp;
/*  428 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  429 */       if (dir = (cmp > 0)) {
/*  430 */         q = p;
/*  431 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  433 */       q = p;
/*  434 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  437 */     if (p.left == null) this.firstEntry = p.next(); 
/*  438 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  439 */     if (p.succ())
/*  440 */     { if (p.pred())
/*  441 */       { if (q != null)
/*  442 */         { if (dir) { q.succ(p.right); }
/*  443 */           else { q.pred(p.left); }  }
/*  444 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  446 */       else { (p.prev()).right = p.right;
/*  447 */         if (q != null)
/*  448 */         { if (dir) { q.right = p.left; }
/*  449 */           else { q.left = p.left; }  }
/*  450 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  453 */     else { Entry<K, V> r = p.right;
/*  454 */       if (r.pred()) {
/*  455 */         r.left = p.left;
/*  456 */         r.pred(p.pred());
/*  457 */         if (!r.pred()) (r.prev()).right = r; 
/*  458 */         if (q != null)
/*  459 */         { if (dir) { q.right = r; }
/*  460 */           else { q.left = r; }  }
/*  461 */         else { this.tree = r; }
/*  462 */          r.balance(p.balance());
/*  463 */         q = r;
/*  464 */         dir = true;
/*      */       } else {
/*      */         Entry<K, V> s;
/*      */         while (true) {
/*  468 */           s = r.left;
/*  469 */           if (s.pred())
/*  470 */             break;  r = s;
/*      */         } 
/*  472 */         if (s.succ()) { r.pred(s); }
/*  473 */         else { r.left = s.right; }
/*  474 */          s.left = p.left;
/*  475 */         if (!p.pred()) {
/*  476 */           (p.prev()).right = s;
/*  477 */           s.pred(false);
/*      */         } 
/*  479 */         s.right = p.right;
/*  480 */         s.succ(false);
/*  481 */         if (q != null)
/*  482 */         { if (dir) { q.right = s; }
/*  483 */           else { q.left = s; }  }
/*  484 */         else { this.tree = s; }
/*  485 */          s.balance(p.balance());
/*  486 */         q = r;
/*  487 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  491 */     while (q != null) {
/*  492 */       Entry<K, V> y = q;
/*  493 */       q = parent(y);
/*  494 */       if (!dir) {
/*  495 */         dir = (q != null && q.left != y);
/*  496 */         y.incBalance();
/*  497 */         if (y.balance() == 1)
/*  498 */           break;  if (y.balance() == 2) {
/*  499 */           Entry<K, V> x = y.right;
/*  500 */           assert x != null;
/*  501 */           if (x.balance() == -1) {
/*      */             
/*  503 */             assert x.balance() == -1;
/*  504 */             Entry<K, V> w = x.left;
/*  505 */             x.left = w.right;
/*  506 */             w.right = x;
/*  507 */             y.right = w.left;
/*  508 */             w.left = y;
/*  509 */             if (w.balance() == 1) {
/*  510 */               x.balance(0);
/*  511 */               y.balance(-1);
/*  512 */             } else if (w.balance() == 0) {
/*  513 */               x.balance(0);
/*  514 */               y.balance(0);
/*      */             } else {
/*  516 */               assert w.balance() == -1;
/*  517 */               x.balance(1);
/*  518 */               y.balance(0);
/*      */             } 
/*  520 */             w.balance(0);
/*  521 */             if (w.pred()) {
/*  522 */               y.succ(w);
/*  523 */               w.pred(false);
/*      */             } 
/*  525 */             if (w.succ()) {
/*  526 */               x.pred(w);
/*  527 */               w.succ(false);
/*      */             } 
/*  529 */             if (q != null) {
/*  530 */               if (dir) { q.right = w; continue; }
/*  531 */                q.left = w; continue;
/*  532 */             }  this.tree = w; continue;
/*      */           } 
/*  534 */           if (q != null)
/*  535 */           { if (dir) { q.right = x; }
/*  536 */             else { q.left = x; }  }
/*  537 */           else { this.tree = x; }
/*  538 */            if (x.balance() == 0) {
/*  539 */             y.right = x.left;
/*  540 */             x.left = y;
/*  541 */             x.balance(-1);
/*  542 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  545 */           assert x.balance() == 1;
/*  546 */           if (x.pred())
/*  547 */           { y.succ(true);
/*  548 */             x.pred(false); }
/*  549 */           else { y.right = x.left; }
/*  550 */            x.left = y;
/*  551 */           y.balance(0);
/*  552 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  556 */       dir = (q != null && q.left != y);
/*  557 */       y.decBalance();
/*  558 */       if (y.balance() == -1)
/*  559 */         break;  if (y.balance() == -2) {
/*  560 */         Entry<K, V> x = y.left;
/*  561 */         assert x != null;
/*  562 */         if (x.balance() == 1) {
/*      */           
/*  564 */           assert x.balance() == 1;
/*  565 */           Entry<K, V> w = x.right;
/*  566 */           x.right = w.left;
/*  567 */           w.left = x;
/*  568 */           y.left = w.right;
/*  569 */           w.right = y;
/*  570 */           if (w.balance() == -1) {
/*  571 */             x.balance(0);
/*  572 */             y.balance(1);
/*  573 */           } else if (w.balance() == 0) {
/*  574 */             x.balance(0);
/*  575 */             y.balance(0);
/*      */           } else {
/*  577 */             assert w.balance() == 1;
/*  578 */             x.balance(-1);
/*  579 */             y.balance(0);
/*      */           } 
/*  581 */           w.balance(0);
/*  582 */           if (w.pred()) {
/*  583 */             x.succ(w);
/*  584 */             w.pred(false);
/*      */           } 
/*  586 */           if (w.succ()) {
/*  587 */             y.pred(w);
/*  588 */             w.succ(false);
/*      */           } 
/*  590 */           if (q != null) {
/*  591 */             if (dir) { q.right = w; continue; }
/*  592 */              q.left = w; continue;
/*  593 */           }  this.tree = w; continue;
/*      */         } 
/*  595 */         if (q != null)
/*  596 */         { if (dir) { q.right = x; }
/*  597 */           else { q.left = x; }  }
/*  598 */         else { this.tree = x; }
/*  599 */          if (x.balance() == 0) {
/*  600 */           y.left = x.right;
/*  601 */           x.right = y;
/*  602 */           x.balance(1);
/*  603 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  606 */         assert x.balance() == -1;
/*  607 */         if (x.succ())
/*  608 */         { y.pred(true);
/*  609 */           x.succ(false); }
/*  610 */         else { y.left = x.right; }
/*  611 */          x.right = y;
/*  612 */         y.balance(0);
/*  613 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  618 */     this.modified = true;
/*  619 */     this.count--;
/*  620 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  625 */     ValueIterator i = new ValueIterator();
/*      */     
/*  627 */     int j = this.count;
/*  628 */     while (j-- != 0) {
/*  629 */       V ev = i.next();
/*  630 */       if (ev == v) return true; 
/*      */     } 
/*  632 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  637 */     this.count = 0;
/*  638 */     this.tree = null;
/*  639 */     this.entries = null;
/*  640 */     this.values = null;
/*  641 */     this.keys = null;
/*  642 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K, V>
/*      */     extends AbstractObject2ReferenceMap.BasicEntry<K, V>
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
/*      */     Entry<K, V> left;
/*      */ 
/*      */     
/*      */     Entry<K, V> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  671 */       super(null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, V v) {
/*  681 */       super(k, v);
/*  682 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> left() {
/*  691 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> right() {
/*  700 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  709 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  718 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  727 */       if (pred) { this.info |= 0x40000000; }
/*  728 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  737 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  738 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K, V> pred) {
/*  747 */       this.info |= 0x40000000;
/*  748 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K, V> succ) {
/*  757 */       this.info |= Integer.MIN_VALUE;
/*  758 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K, V> left) {
/*  767 */       this.info &= 0xBFFFFFFF;
/*  768 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K, V> right) {
/*  777 */       this.info &= Integer.MAX_VALUE;
/*  778 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  787 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  796 */       this.info &= 0xFFFFFF00;
/*  797 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  802 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  807 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> next() {
/*  816 */       Entry<K, V> next = this.right;
/*  817 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  818 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> prev() {
/*  827 */       Entry<K, V> prev = this.left;
/*  828 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  829 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V value) {
/*  834 */       V oldValue = this.value;
/*  835 */       this.value = value;
/*  836 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K, V> clone() {
/*      */       Entry<K, V> c;
/*      */       try {
/*  844 */         c = (Entry<K, V>)super.clone();
/*  845 */       } catch (CloneNotSupportedException cantHappen) {
/*  846 */         throw new InternalError();
/*      */       } 
/*  848 */       c.key = this.key;
/*  849 */       c.value = this.value;
/*  850 */       c.info = this.info;
/*  851 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  857 */       if (!(o instanceof Map.Entry)) return false; 
/*  858 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  859 */       return (Objects.equals(this.key, e.getKey()) && this.value == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  864 */       return this.key.hashCode() ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  869 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  907 */     if (k == null) return false; 
/*  908 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  913 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  918 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  924 */     Entry<K, V> e = findKey((K)k);
/*  925 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  930 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  931 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  936 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  937 */     return this.lastEntry.key;
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
/*      */     Object2ReferenceAVLTreeMap.Entry<K, V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ReferenceAVLTreeMap.Entry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ReferenceAVLTreeMap.Entry<K, V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  966 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  969 */       this.next = Object2ReferenceAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(K k) {
/*  973 */       if ((this.next = Object2ReferenceAVLTreeMap.this.locateKey(k)) != null)
/*  974 */         if (Object2ReferenceAVLTreeMap.this.compare(this.next.key, k) <= 0)
/*  975 */         { this.prev = this.next;
/*  976 */           this.next = this.next.next(); }
/*  977 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  982 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  986 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  990 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Object2ReferenceAVLTreeMap.Entry<K, V> nextEntry() {
/*  994 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  995 */       this.curr = this.prev = this.next;
/*  996 */       this.index++;
/*  997 */       updateNext();
/*  998 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1002 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Object2ReferenceAVLTreeMap.Entry<K, V> previousEntry() {
/* 1006 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1007 */       this.curr = this.next = this.prev;
/* 1008 */       this.index--;
/* 1009 */       updatePrevious();
/* 1010 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1014 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1018 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1022 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1025 */       if (this.curr == this.prev) this.index--; 
/* 1026 */       this.next = this.prev = this.curr;
/* 1027 */       updatePrevious();
/* 1028 */       updateNext();
/* 1029 */       Object2ReferenceAVLTreeMap.this.remove(this.curr.key);
/* 1030 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1034 */       int i = n;
/* 1035 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1036 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1040 */       int i = n;
/* 1041 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1042 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1057 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> next() {
/* 1062 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> previous() {
/* 1067 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Object2ReferenceMap.Entry<K, V> ok) {
/* 1072 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Object2ReferenceMap.Entry<K, V> ok) {
/* 1077 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 1084 */     if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>()
/*      */         {
/*      */           final Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator;
/*      */           
/*      */           public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1089 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1094 */             return new Object2ReferenceAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1099 */             return new Object2ReferenceAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1105 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1106 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1107 */             if (e.getKey() == null) return false; 
/* 1108 */             Object2ReferenceAVLTreeMap.Entry<K, V> f = Object2ReferenceAVLTreeMap.this.findKey((K)e.getKey());
/* 1109 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1115 */             if (!(o instanceof Map.Entry)) return false; 
/* 1116 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1117 */             if (e.getKey() == null) return false; 
/* 1118 */             Object2ReferenceAVLTreeMap.Entry<K, V> f = Object2ReferenceAVLTreeMap.this.findKey((K)e.getKey());
/* 1119 */             if (f == null || f.getValue() != e.getValue()) return false; 
/* 1120 */             Object2ReferenceAVLTreeMap.this.remove(f.key);
/* 1121 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1126 */             return Object2ReferenceAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1131 */             Object2ReferenceAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2ReferenceMap.Entry<K, V> first() {
/* 1136 */             return Object2ReferenceAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2ReferenceMap.Entry<K, V> last() {
/* 1141 */             return Object2ReferenceAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> from, Object2ReferenceMap.Entry<K, V> to) {
/* 1146 */             return Object2ReferenceAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2ReferenceEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> to) {
/* 1151 */             return Object2ReferenceAVLTreeMap.this.headMap(to.getKey()).object2ReferenceEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> from) {
/* 1156 */             return Object2ReferenceAVLTreeMap.this.tailMap(from.getKey()).object2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1159 */     return this.entries;
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
/* 1175 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1180 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1185 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractObject2ReferenceSortedMap<K, V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1193 */       return new Object2ReferenceAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1198 */       return new Object2ReferenceAVLTreeMap.KeyIterator(from);
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
/* 1213 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1214 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1228 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V previous() {
/* 1233 */       return (previousEntry()).value;
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
/*      */   public ReferenceCollection<V> values() {
/* 1248 */     if (this.values == null) this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1251 */             return new Object2ReferenceAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object k) {
/* 1256 */             return Object2ReferenceAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1261 */             return Object2ReferenceAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1266 */             Object2ReferenceAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1269 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1274 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 1279 */     return new Submap(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 1284 */     return new Submap(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 1289 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2ReferenceSortedMap<K, V>
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
/*      */     protected transient ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1328 */       if (!bottom && !top && Object2ReferenceAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1329 */       this.from = from;
/* 1330 */       this.bottom = bottom;
/* 1331 */       this.to = to;
/* 1332 */       this.top = top;
/* 1333 */       this.defRetValue = Object2ReferenceAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1338 */       SubmapIterator i = new SubmapIterator();
/* 1339 */       while (i.hasNext()) {
/* 1340 */         i.nextEntry();
/* 1341 */         i.remove();
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
/* 1352 */       return ((this.bottom || Object2ReferenceAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2ReferenceAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 1357 */       if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1360 */               return new Object2ReferenceAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1365 */               return new Object2ReferenceAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1370 */               return Object2ReferenceAVLTreeMap.this.object2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1376 */               if (!(o instanceof Map.Entry)) return false; 
/* 1377 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1378 */               Object2ReferenceAVLTreeMap.Entry<K, V> f = Object2ReferenceAVLTreeMap.this.findKey((K)e.getKey());
/* 1379 */               return (f != null && Object2ReferenceAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1385 */               if (!(o instanceof Map.Entry)) return false; 
/* 1386 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1387 */               Object2ReferenceAVLTreeMap.Entry<K, V> f = Object2ReferenceAVLTreeMap.this.findKey((K)e.getKey());
/* 1388 */               if (f != null && Object2ReferenceAVLTreeMap.Submap.this.in(f.key)) Object2ReferenceAVLTreeMap.Submap.this.remove(f.key); 
/* 1389 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1394 */               int c = 0;
/* 1395 */               for (Iterator<?> i = iterator(); i.hasNext(); ) { c++; i.next(); }
/* 1396 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1401 */               return !(new Object2ReferenceAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1406 */               Object2ReferenceAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2ReferenceMap.Entry<K, V> first() {
/* 1411 */               return Object2ReferenceAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2ReferenceMap.Entry<K, V> last() {
/* 1416 */               return Object2ReferenceAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> from, Object2ReferenceMap.Entry<K, V> to) {
/* 1421 */               return Object2ReferenceAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2ReferenceEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> to) {
/* 1426 */               return Object2ReferenceAVLTreeMap.Submap.this.headMap(to.getKey()).object2ReferenceEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> from) {
/* 1431 */               return Object2ReferenceAVLTreeMap.Submap.this.tailMap(from.getKey()).object2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1434 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2ReferenceSortedMap<K, V>.KeySet { private KeySet() {}
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1440 */         return new Object2ReferenceAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1445 */         return new Object2ReferenceAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1451 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1452 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1457 */       if (this.values == null) this.values = new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1460 */               return new Object2ReferenceAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object k) {
/* 1465 */               return Object2ReferenceAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1470 */               return Object2ReferenceAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1475 */               Object2ReferenceAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1478 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1484 */       if (k == null) return false; 
/* 1485 */       return (in((K)k) && Object2ReferenceAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1490 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1492 */       while (i.hasNext()) {
/* 1493 */         Object ev = (i.nextEntry()).value;
/* 1494 */         if (ev == v) return true; 
/*      */       } 
/* 1496 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 1503 */       K kk = (K)k; Object2ReferenceAVLTreeMap.Entry<K, V> e;
/* 1504 */       return (in(kk) && (e = Object2ReferenceAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V put(K k, V v) {
/* 1509 */       Object2ReferenceAVLTreeMap.this.modified = false;
/* 1510 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1511 */       V oldValue = Object2ReferenceAVLTreeMap.this.put(k, v);
/* 1512 */       return Object2ReferenceAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V remove(Object k) {
/* 1518 */       Object2ReferenceAVLTreeMap.this.modified = false;
/* 1519 */       if (!in((K)k)) return this.defRetValue; 
/* 1520 */       V oldValue = (V)Object2ReferenceAVLTreeMap.this.remove(k);
/* 1521 */       return Object2ReferenceAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1526 */       SubmapIterator i = new SubmapIterator();
/* 1527 */       int n = 0;
/* 1528 */       while (i.hasNext()) {
/* 1529 */         n++;
/* 1530 */         i.nextEntry();
/*      */       } 
/* 1532 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1537 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1542 */       return Object2ReferenceAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 1547 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1548 */       return (Object2ReferenceAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 1553 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1554 */       return (Object2ReferenceAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 1559 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1560 */       if (!this.top) to = (Object2ReferenceAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1561 */       if (!this.bottom) from = (Object2ReferenceAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1562 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1563 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ReferenceAVLTreeMap.Entry<K, V> firstEntry() {
/*      */       Object2ReferenceAVLTreeMap.Entry<K, V> e;
/* 1572 */       if (Object2ReferenceAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1576 */       if (this.bottom) { e = Object2ReferenceAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1578 */       { e = Object2ReferenceAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1580 */         if (Object2ReferenceAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1584 */       if (e == null || (!this.top && Object2ReferenceAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1585 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ReferenceAVLTreeMap.Entry<K, V> lastEntry() {
/*      */       Object2ReferenceAVLTreeMap.Entry<K, V> e;
/* 1594 */       if (Object2ReferenceAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1598 */       if (this.top) { e = Object2ReferenceAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1600 */       { e = Object2ReferenceAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1602 */         if (Object2ReferenceAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1606 */       if (e == null || (!this.bottom && Object2ReferenceAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1607 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1612 */       Object2ReferenceAVLTreeMap.Entry<K, V> e = firstEntry();
/* 1613 */       if (e == null) throw new NoSuchElementException(); 
/* 1614 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1619 */       Object2ReferenceAVLTreeMap.Entry<K, V> e = lastEntry();
/* 1620 */       if (e == null) throw new NoSuchElementException(); 
/* 1621 */       return e.key;
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
/*      */       extends Object2ReferenceAVLTreeMap<K, V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1635 */         this.next = Object2ReferenceAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(K k) {
/* 1639 */         this();
/* 1640 */         if (this.next != null) {
/* 1641 */           if (!Object2ReferenceAVLTreeMap.Submap.this.bottom && Object2ReferenceAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1642 */           else if (!Object2ReferenceAVLTreeMap.Submap.this.top && Object2ReferenceAVLTreeMap.this.compare(k, (this.prev = Object2ReferenceAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1644 */           { this.next = Object2ReferenceAVLTreeMap.this.locateKey(k);
/* 1645 */             if (Object2ReferenceAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1646 */             { this.prev = this.next;
/* 1647 */               this.next = this.next.next(); }
/* 1648 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1655 */         this.prev = this.prev.prev();
/* 1656 */         if (!Object2ReferenceAVLTreeMap.Submap.this.bottom && this.prev != null && Object2ReferenceAVLTreeMap.this.compare(this.prev.key, Object2ReferenceAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1661 */         this.next = this.next.next();
/* 1662 */         if (!Object2ReferenceAVLTreeMap.Submap.this.top && this.next != null && Object2ReferenceAVLTreeMap.this.compare(this.next.key, Object2ReferenceAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1671 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2ReferenceMap.Entry<K, V> next() {
/* 1676 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2ReferenceMap.Entry<K, V> previous() {
/* 1681 */         return previousEntry();
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
/* 1700 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/* 1705 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/* 1710 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<V>
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public V next() {
/* 1726 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public V previous() {
/* 1731 */         return (previousEntry()).value;
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
/*      */   public Object2ReferenceAVLTreeMap<K, V> clone() {
/*      */     Object2ReferenceAVLTreeMap<K, V> c;
/*      */     try {
/* 1750 */       c = (Object2ReferenceAVLTreeMap<K, V>)super.clone();
/* 1751 */     } catch (CloneNotSupportedException cantHappen) {
/* 1752 */       throw new InternalError();
/*      */     } 
/* 1754 */     c.keys = null;
/* 1755 */     c.values = null;
/* 1756 */     c.entries = null;
/* 1757 */     c.allocatePaths();
/* 1758 */     if (this.count != 0) {
/*      */       
/* 1760 */       Entry<K, V> rp = new Entry<>(), rq = new Entry<>();
/* 1761 */       Entry<K, V> p = rp;
/* 1762 */       rp.left(this.tree);
/* 1763 */       Entry<K, V> q = rq;
/* 1764 */       rq.pred((Entry<K, V>)null);
/*      */       while (true) {
/* 1766 */         if (!p.pred()) {
/* 1767 */           Entry<K, V> e = p.left.clone();
/* 1768 */           e.pred(q.left);
/* 1769 */           e.succ(q);
/* 1770 */           q.left(e);
/* 1771 */           p = p.left;
/* 1772 */           q = q.left;
/*      */         } else {
/* 1774 */           while (p.succ()) {
/* 1775 */             p = p.right;
/* 1776 */             if (p == null) {
/* 1777 */               q.right = null;
/* 1778 */               c.tree = rq.left;
/* 1779 */               c.firstEntry = c.tree;
/* 1780 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1781 */               c.lastEntry = c.tree;
/* 1782 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1783 */               return c;
/*      */             } 
/* 1785 */             q = q.right;
/*      */           } 
/* 1787 */           p = p.right;
/* 1788 */           q = q.right;
/*      */         } 
/* 1790 */         if (!p.succ()) {
/* 1791 */           Entry<K, V> e = p.right.clone();
/* 1792 */           e.succ(q.right);
/* 1793 */           e.pred(q);
/* 1794 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1798 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1802 */     int n = this.count;
/* 1803 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1805 */     s.defaultWriteObject();
/* 1806 */     while (n-- != 0) {
/* 1807 */       Entry<K, V> e = i.nextEntry();
/* 1808 */       s.writeObject(e.key);
/* 1809 */       s.writeObject(e.value);
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
/*      */   private Entry<K, V> readTree(ObjectInputStream s, int n, Entry<K, V> pred, Entry<K, V> succ) throws IOException, ClassNotFoundException {
/* 1823 */     if (n == 1) {
/* 1824 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1825 */       entry.pred(pred);
/* 1826 */       entry.succ(succ);
/* 1827 */       return entry;
/*      */     } 
/* 1829 */     if (n == 2) {
/*      */ 
/*      */       
/* 1832 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1833 */       entry.right(new Entry<>((K)s.readObject(), (V)s.readObject()));
/* 1834 */       entry.right.pred(entry);
/* 1835 */       entry.balance(1);
/* 1836 */       entry.pred(pred);
/* 1837 */       entry.right.succ(succ);
/* 1838 */       return entry;
/*      */     } 
/*      */     
/* 1841 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1842 */     Entry<K, V> top = new Entry<>();
/* 1843 */     top.left(readTree(s, leftN, pred, top));
/* 1844 */     top.key = (K)s.readObject();
/* 1845 */     top.value = (V)s.readObject();
/* 1846 */     top.right(readTree(s, rightN, top, succ));
/* 1847 */     if (n == (n & -n)) top.balance(1); 
/* 1848 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1852 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1855 */     setActualComparator();
/* 1856 */     allocatePaths();
/* 1857 */     if (this.count != 0) {
/* 1858 */       this.tree = readTree(s, this.count, (Entry<K, V>)null, (Entry<K, V>)null);
/*      */       
/* 1860 */       Entry<K, V> e = this.tree;
/* 1861 */       for (; e.left() != null; e = e.left());
/* 1862 */       this.firstEntry = e;
/* 1863 */       e = this.tree;
/* 1864 */       for (; e.right() != null; e = e.right());
/* 1865 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */