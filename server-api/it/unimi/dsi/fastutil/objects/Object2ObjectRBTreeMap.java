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
/*      */ public class Object2ObjectRBTreeMap<K, V>
/*      */   extends AbstractObject2ObjectSortedMap<K, V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K, V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K, V> firstEntry;
/*      */   protected transient Entry<K, V> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K, V>[] nodePath;
/*      */   
/*      */   public Object2ObjectRBTreeMap() {
/*   63 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   70 */     this.tree = null;
/*   71 */     this.count = 0;
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
/*   83 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(Comparator<? super K> c) {
/*   92 */     this();
/*   93 */     this.storedComparator = c;
/*   94 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(Map<? extends K, ? extends V> m) {
/*  103 */     this();
/*  104 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(SortedMap<K, V> m) {
/*  113 */     this(m.comparator());
/*  114 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(Object2ObjectMap<? extends K, ? extends V> m) {
/*  123 */     this();
/*  124 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(Object2ObjectSortedMap<K, V> m) {
/*  133 */     this(m.comparator());
/*  134 */     putAll(m);
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
/*      */   public Object2ObjectRBTreeMap(K[] k, V[] v, Comparator<? super K> c) {
/*  146 */     this(c);
/*  147 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  148 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(K[] k, V[] v) {
/*  159 */     this(k, v, (Comparator<? super K>)null);
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
/*  185 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K, V> findKey(K k) {
/*  195 */     Entry<K, V> e = this.tree;
/*      */     int cmp;
/*  197 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  198 */     return e;
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
/*  209 */     Entry<K, V> e = this.tree, last = this.tree;
/*  210 */     int cmp = 0;
/*  211 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  212 */       last = e;
/*  213 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  215 */     return (cmp == 0) ? e : last;
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
/*      */   private void allocatePaths() {
/*  227 */     this.dirPath = new boolean[64];
/*  228 */     this.nodePath = (Entry<K, V>[])new Entry[64];
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  233 */     Entry<K, V> e = add(k);
/*  234 */     V oldValue = e.value;
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
/*      */   private Entry<K, V> add(K k) {
/*      */     Entry<K, V> e;
/*  249 */     Objects.requireNonNull(k);
/*  250 */     this.modified = false;
/*  251 */     int maxDepth = 0;
/*      */     
/*  253 */     if (this.tree == null) {
/*  254 */       this.count++;
/*  255 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  257 */       Entry<K, V> p = this.tree;
/*  258 */       int i = 0; while (true) {
/*      */         int cmp;
/*  260 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  262 */           for (; i-- != 0; this.nodePath[i] = null);
/*  263 */           return p;
/*      */         } 
/*  265 */         this.nodePath[i] = p;
/*  266 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  267 */           if (p.succ()) {
/*  268 */             this.count++;
/*  269 */             e = new Entry<>(k, this.defRetValue);
/*  270 */             if (p.right == null) this.lastEntry = e; 
/*  271 */             e.left = p;
/*  272 */             e.right = p.right;
/*  273 */             p.right(e);
/*      */             break;
/*      */           } 
/*  276 */           p = p.right; continue;
/*      */         } 
/*  278 */         if (p.pred()) {
/*  279 */           this.count++;
/*  280 */           e = new Entry<>(k, this.defRetValue);
/*  281 */           if (p.left == null) this.firstEntry = e; 
/*  282 */           e.right = p;
/*  283 */           e.left = p.left;
/*  284 */           p.left(e);
/*      */           break;
/*      */         } 
/*  287 */         p = p.left;
/*      */       } 
/*      */       
/*  290 */       this.modified = true;
/*  291 */       maxDepth = i--;
/*  292 */       while (i > 0 && !this.nodePath[i].black()) {
/*  293 */         if (!this.dirPath[i - 1]) {
/*  294 */           Entry<K, V> entry1 = (this.nodePath[i - 1]).right;
/*  295 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  296 */             this.nodePath[i].black(true);
/*  297 */             entry1.black(true);
/*  298 */             this.nodePath[i - 1].black(false);
/*  299 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  302 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  304 */           { Entry<K, V> entry = this.nodePath[i];
/*  305 */             entry1 = entry.right;
/*  306 */             entry.right = entry1.left;
/*  307 */             entry1.left = entry;
/*  308 */             (this.nodePath[i - 1]).left = entry1;
/*  309 */             if (entry1.pred()) {
/*  310 */               entry1.pred(false);
/*  311 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  314 */           Entry<K, V> entry2 = this.nodePath[i - 1];
/*  315 */           entry2.black(false);
/*  316 */           entry1.black(true);
/*  317 */           entry2.left = entry1.right;
/*  318 */           entry1.right = entry2;
/*  319 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  321 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  322 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  324 */           if (entry1.succ()) {
/*  325 */             entry1.succ(false);
/*  326 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  331 */         Entry<K, V> y = (this.nodePath[i - 1]).left;
/*  332 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  333 */           this.nodePath[i].black(true);
/*  334 */           y.black(true);
/*  335 */           this.nodePath[i - 1].black(false);
/*  336 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  339 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  341 */         { Entry<K, V> entry = this.nodePath[i];
/*  342 */           y = entry.left;
/*  343 */           entry.left = y.right;
/*  344 */           y.right = entry;
/*  345 */           (this.nodePath[i - 1]).right = y;
/*  346 */           if (y.succ()) {
/*  347 */             y.succ(false);
/*  348 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  351 */         Entry<K, V> x = this.nodePath[i - 1];
/*  352 */         x.black(false);
/*  353 */         y.black(true);
/*  354 */         x.right = y.left;
/*  355 */         y.left = x;
/*  356 */         if (i < 2) { this.tree = y; }
/*      */         
/*  358 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  359 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  361 */         if (y.pred()) {
/*  362 */           y.pred(false);
/*  363 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  370 */     this.tree.black(true);
/*      */     
/*  372 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  373 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  381 */     this.modified = false;
/*  382 */     if (this.tree == null) return this.defRetValue; 
/*  383 */     Entry<K, V> p = this.tree;
/*      */     
/*  385 */     int i = 0;
/*  386 */     K kk = (K)k;
/*      */     int cmp;
/*  388 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  389 */       this.dirPath[i] = (cmp > 0);
/*  390 */       this.nodePath[i] = p;
/*  391 */       if (this.dirPath[i++]) {
/*  392 */         if ((p = p.right()) == null) {
/*      */           
/*  394 */           for (; i-- != 0; this.nodePath[i] = null);
/*  395 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  398 */       if ((p = p.left()) == null) {
/*      */         
/*  400 */         for (; i-- != 0; this.nodePath[i] = null);
/*  401 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  405 */     if (p.left == null) this.firstEntry = p.next(); 
/*  406 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  407 */     if (p.succ()) {
/*  408 */       if (p.pred()) {
/*  409 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  411 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  412 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  415 */         (p.prev()).right = p.right;
/*  416 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  418 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  419 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  424 */       Entry<K, V> r = p.right;
/*  425 */       if (r.pred()) {
/*  426 */         r.left = p.left;
/*  427 */         r.pred(p.pred());
/*  428 */         if (!r.pred()) (r.prev()).right = r; 
/*  429 */         if (i == 0) { this.tree = r; }
/*      */         
/*  431 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  432 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  434 */         boolean color = r.black();
/*  435 */         r.black(p.black());
/*  436 */         p.black(color);
/*  437 */         this.dirPath[i] = true;
/*  438 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K, V> s;
/*  441 */         int j = i++;
/*      */         while (true) {
/*  443 */           this.dirPath[i] = false;
/*  444 */           this.nodePath[i++] = r;
/*  445 */           s = r.left;
/*  446 */           if (s.pred())
/*  447 */             break;  r = s;
/*      */         } 
/*  449 */         this.dirPath[j] = true;
/*  450 */         this.nodePath[j] = s;
/*  451 */         if (s.succ()) { r.pred(s); }
/*  452 */         else { r.left = s.right; }
/*  453 */          s.left = p.left;
/*  454 */         if (!p.pred()) {
/*  455 */           (p.prev()).right = s;
/*  456 */           s.pred(false);
/*      */         } 
/*  458 */         s.right(p.right);
/*  459 */         boolean color = s.black();
/*  460 */         s.black(p.black());
/*  461 */         p.black(color);
/*  462 */         if (j == 0) { this.tree = s; }
/*      */         
/*  464 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  465 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  469 */     int maxDepth = i;
/*  470 */     if (p.black()) {
/*  471 */       for (; i > 0; i--) {
/*  472 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  473 */           Entry<K, V> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  474 */           if (!x.black()) {
/*  475 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  479 */         if (!this.dirPath[i - 1]) {
/*  480 */           Entry<K, V> w = (this.nodePath[i - 1]).right;
/*  481 */           if (!w.black()) {
/*  482 */             w.black(true);
/*  483 */             this.nodePath[i - 1].black(false);
/*  484 */             (this.nodePath[i - 1]).right = w.left;
/*  485 */             w.left = this.nodePath[i - 1];
/*  486 */             if (i < 2) { this.tree = w; }
/*      */             
/*  488 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  489 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  491 */             this.nodePath[i] = this.nodePath[i - 1];
/*  492 */             this.dirPath[i] = false;
/*  493 */             this.nodePath[i - 1] = w;
/*  494 */             if (maxDepth == i++) maxDepth++; 
/*  495 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  497 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  498 */             w.black(false);
/*      */           } else {
/*  500 */             if (w.succ() || w.right.black()) {
/*  501 */               Entry<K, V> y = w.left;
/*  502 */               y.black(true);
/*  503 */               w.black(false);
/*  504 */               w.left = y.right;
/*  505 */               y.right = w;
/*  506 */               w = (this.nodePath[i - 1]).right = y;
/*  507 */               if (w.succ()) {
/*  508 */                 w.succ(false);
/*  509 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  512 */             w.black(this.nodePath[i - 1].black());
/*  513 */             this.nodePath[i - 1].black(true);
/*  514 */             w.right.black(true);
/*  515 */             (this.nodePath[i - 1]).right = w.left;
/*  516 */             w.left = this.nodePath[i - 1];
/*  517 */             if (i < 2) { this.tree = w; }
/*      */             
/*  519 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  520 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  522 */             if (w.pred()) {
/*  523 */               w.pred(false);
/*  524 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  529 */           Entry<K, V> w = (this.nodePath[i - 1]).left;
/*  530 */           if (!w.black()) {
/*  531 */             w.black(true);
/*  532 */             this.nodePath[i - 1].black(false);
/*  533 */             (this.nodePath[i - 1]).left = w.right;
/*  534 */             w.right = this.nodePath[i - 1];
/*  535 */             if (i < 2) { this.tree = w; }
/*      */             
/*  537 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  538 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  540 */             this.nodePath[i] = this.nodePath[i - 1];
/*  541 */             this.dirPath[i] = true;
/*  542 */             this.nodePath[i - 1] = w;
/*  543 */             if (maxDepth == i++) maxDepth++; 
/*  544 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  546 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  547 */             w.black(false);
/*      */           } else {
/*  549 */             if (w.pred() || w.left.black()) {
/*  550 */               Entry<K, V> y = w.right;
/*  551 */               y.black(true);
/*  552 */               w.black(false);
/*  553 */               w.right = y.left;
/*  554 */               y.left = w;
/*  555 */               w = (this.nodePath[i - 1]).left = y;
/*  556 */               if (w.pred()) {
/*  557 */                 w.pred(false);
/*  558 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  561 */             w.black(this.nodePath[i - 1].black());
/*  562 */             this.nodePath[i - 1].black(true);
/*  563 */             w.left.black(true);
/*  564 */             (this.nodePath[i - 1]).left = w.right;
/*  565 */             w.right = this.nodePath[i - 1];
/*  566 */             if (i < 2) { this.tree = w; }
/*      */             
/*  568 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  569 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  571 */             if (w.succ()) {
/*  572 */               w.succ(false);
/*  573 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  579 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  581 */     this.modified = true;
/*  582 */     this.count--;
/*      */     
/*  584 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  585 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  590 */     ValueIterator i = new ValueIterator();
/*      */     
/*  592 */     int j = this.count;
/*  593 */     while (j-- != 0) {
/*  594 */       Object ev = i.next();
/*  595 */       if (Objects.equals(ev, v)) return true; 
/*      */     } 
/*  597 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  602 */     this.count = 0;
/*  603 */     this.tree = null;
/*  604 */     this.entries = null;
/*  605 */     this.values = null;
/*  606 */     this.keys = null;
/*  607 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K, V>
/*      */     extends AbstractObject2ObjectMap.BasicEntry<K, V>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
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
/*  636 */       super(null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, V v) {
/*  646 */       super(k, v);
/*  647 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> left() {
/*  656 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> right() {
/*  665 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  674 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  683 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  692 */       if (pred) { this.info |= 0x40000000; }
/*  693 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  702 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  703 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K, V> pred) {
/*  712 */       this.info |= 0x40000000;
/*  713 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K, V> succ) {
/*  722 */       this.info |= Integer.MIN_VALUE;
/*  723 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K, V> left) {
/*  732 */       this.info &= 0xBFFFFFFF;
/*  733 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K, V> right) {
/*  742 */       this.info &= Integer.MAX_VALUE;
/*  743 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  752 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  761 */       if (black) { this.info |= 0x1; }
/*  762 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> next() {
/*  771 */       Entry<K, V> next = this.right;
/*  772 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  773 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> prev() {
/*  782 */       Entry<K, V> prev = this.left;
/*  783 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  784 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V value) {
/*  789 */       V oldValue = this.value;
/*  790 */       this.value = value;
/*  791 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K, V> clone() {
/*      */       Entry<K, V> c;
/*      */       try {
/*  799 */         c = (Entry<K, V>)super.clone();
/*  800 */       } catch (CloneNotSupportedException cantHappen) {
/*  801 */         throw new InternalError();
/*      */       } 
/*  803 */       c.key = this.key;
/*  804 */       c.value = this.value;
/*  805 */       c.info = this.info;
/*  806 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  812 */       if (!(o instanceof Map.Entry)) return false; 
/*  813 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  814 */       return (Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  819 */       return this.key.hashCode() ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  824 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*      */   public boolean containsKey(Object k) {
/*  860 */     if (k == null) return false; 
/*  861 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  866 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  871 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  877 */     Entry<K, V> e = findKey((K)k);
/*  878 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  883 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  884 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  889 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  890 */     return this.lastEntry.key;
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
/*      */     Object2ObjectRBTreeMap.Entry<K, V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ObjectRBTreeMap.Entry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ObjectRBTreeMap.Entry<K, V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  919 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  922 */       this.next = Object2ObjectRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(K k) {
/*  926 */       if ((this.next = Object2ObjectRBTreeMap.this.locateKey(k)) != null)
/*  927 */         if (Object2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0)
/*  928 */         { this.prev = this.next;
/*  929 */           this.next = this.next.next(); }
/*  930 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  935 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  939 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  943 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Object2ObjectRBTreeMap.Entry<K, V> nextEntry() {
/*  947 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  948 */       this.curr = this.prev = this.next;
/*  949 */       this.index++;
/*  950 */       updateNext();
/*  951 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  955 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Object2ObjectRBTreeMap.Entry<K, V> previousEntry() {
/*  959 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  960 */       this.curr = this.next = this.prev;
/*  961 */       this.index--;
/*  962 */       updatePrevious();
/*  963 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  967 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  971 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  975 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/*  978 */       if (this.curr == this.prev) this.index--; 
/*  979 */       this.next = this.prev = this.curr;
/*  980 */       updatePrevious();
/*  981 */       updateNext();
/*  982 */       Object2ObjectRBTreeMap.this.remove(this.curr.key);
/*  983 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  987 */       int i = n;
/*  988 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  989 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*  993 */       int i = n;
/*  994 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/*  995 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2ObjectMap.Entry<K, V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1010 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> next() {
/* 1015 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> previous() {
/* 1020 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 1027 */     if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*      */         {
/*      */           final Comparator<? super Object2ObjectMap.Entry<K, V>> comparator;
/*      */           
/*      */           public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1032 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1037 */             return new Object2ObjectRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1042 */             return new Object2ObjectRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1048 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1049 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1050 */             if (e.getKey() == null) return false; 
/* 1051 */             Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1052 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1058 */             if (!(o instanceof Map.Entry)) return false; 
/* 1059 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1060 */             if (e.getKey() == null) return false; 
/* 1061 */             Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1062 */             if (f == null || !Objects.equals(f.getValue(), e.getValue())) return false; 
/* 1063 */             Object2ObjectRBTreeMap.this.remove(f.key);
/* 1064 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1069 */             return Object2ObjectRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1074 */             Object2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2ObjectMap.Entry<K, V> first() {
/* 1079 */             return Object2ObjectRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2ObjectMap.Entry<K, V> last() {
/* 1084 */             return Object2ObjectRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> from, Object2ObjectMap.Entry<K, V> to) {
/* 1089 */             return Object2ObjectRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> to) {
/* 1094 */             return Object2ObjectRBTreeMap.this.headMap(to.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> from) {
/* 1099 */             return Object2ObjectRBTreeMap.this.tailMap(from.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */         }; 
/* 1102 */     return this.entries;
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
/* 1118 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1123 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1128 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractObject2ObjectSortedMap<K, V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1136 */       return new Object2ObjectRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1141 */       return new Object2ObjectRBTreeMap.KeyIterator(from);
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
/* 1156 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1157 */     return this.keys;
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
/* 1171 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V previous() {
/* 1176 */       return (previousEntry()).value;
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
/*      */   public ObjectCollection<V> values() {
/* 1191 */     if (this.values == null) this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1194 */             return new Object2ObjectRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object k) {
/* 1199 */             return Object2ObjectRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1204 */             return Object2ObjectRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1209 */             Object2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1212 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1217 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> headMap(K to) {
/* 1222 */     return new Submap(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> tailMap(K from) {
/* 1227 */     return new Submap(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 1232 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2ObjectSortedMap<K, V>
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
/*      */     protected transient ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1271 */       if (!bottom && !top && Object2ObjectRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1272 */       this.from = from;
/* 1273 */       this.bottom = bottom;
/* 1274 */       this.to = to;
/* 1275 */       this.top = top;
/* 1276 */       this.defRetValue = Object2ObjectRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1281 */       SubmapIterator i = new SubmapIterator();
/* 1282 */       while (i.hasNext()) {
/* 1283 */         i.nextEntry();
/* 1284 */         i.remove();
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
/* 1295 */       return ((this.bottom || Object2ObjectRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2ObjectRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 1300 */       if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1303 */               return new Object2ObjectRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1308 */               return new Object2ObjectRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1313 */               return Object2ObjectRBTreeMap.this.object2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1319 */               if (!(o instanceof Map.Entry)) return false; 
/* 1320 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1321 */               Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1322 */               return (f != null && Object2ObjectRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1328 */               if (!(o instanceof Map.Entry)) return false; 
/* 1329 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1330 */               Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1331 */               if (f != null && Object2ObjectRBTreeMap.Submap.this.in(f.key)) Object2ObjectRBTreeMap.Submap.this.remove(f.key); 
/* 1332 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1337 */               int c = 0;
/* 1338 */               for (Iterator<?> i = iterator(); i.hasNext(); ) { c++; i.next(); }
/* 1339 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1344 */               return !(new Object2ObjectRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1349 */               Object2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2ObjectMap.Entry<K, V> first() {
/* 1354 */               return Object2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2ObjectMap.Entry<K, V> last() {
/* 1359 */               return Object2ObjectRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> from, Object2ObjectMap.Entry<K, V> to) {
/* 1364 */               return Object2ObjectRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> to) {
/* 1369 */               return Object2ObjectRBTreeMap.Submap.this.headMap(to.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> from) {
/* 1374 */               return Object2ObjectRBTreeMap.Submap.this.tailMap(from.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1377 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2ObjectSortedMap<K, V>.KeySet { private KeySet() {}
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1383 */         return new Object2ObjectRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1388 */         return new Object2ObjectRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1394 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1395 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1400 */       if (this.values == null) this.values = new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1403 */               return new Object2ObjectRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object k) {
/* 1408 */               return Object2ObjectRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1413 */               return Object2ObjectRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1418 */               Object2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1421 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1427 */       if (k == null) return false; 
/* 1428 */       return (in((K)k) && Object2ObjectRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1433 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1435 */       while (i.hasNext()) {
/* 1436 */         Object ev = (i.nextEntry()).value;
/* 1437 */         if (Objects.equals(ev, v)) return true; 
/*      */       } 
/* 1439 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 1446 */       K kk = (K)k; Object2ObjectRBTreeMap.Entry<K, V> e;
/* 1447 */       return (in(kk) && (e = Object2ObjectRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V put(K k, V v) {
/* 1452 */       Object2ObjectRBTreeMap.this.modified = false;
/* 1453 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1454 */       V oldValue = Object2ObjectRBTreeMap.this.put(k, v);
/* 1455 */       return Object2ObjectRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V remove(Object k) {
/* 1461 */       Object2ObjectRBTreeMap.this.modified = false;
/* 1462 */       if (!in((K)k)) return this.defRetValue; 
/* 1463 */       V oldValue = (V)Object2ObjectRBTreeMap.this.remove(k);
/* 1464 */       return Object2ObjectRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1469 */       SubmapIterator i = new SubmapIterator();
/* 1470 */       int n = 0;
/* 1471 */       while (i.hasNext()) {
/* 1472 */         n++;
/* 1473 */         i.nextEntry();
/*      */       } 
/* 1475 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1480 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1485 */       return Object2ObjectRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> headMap(K to) {
/* 1490 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1491 */       return (Object2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> tailMap(K from) {
/* 1496 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1497 */       return (Object2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 1502 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1503 */       if (!this.top) to = (Object2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1504 */       if (!this.bottom) from = (Object2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1505 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1506 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ObjectRBTreeMap.Entry<K, V> firstEntry() {
/*      */       Object2ObjectRBTreeMap.Entry<K, V> e;
/* 1515 */       if (Object2ObjectRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1519 */       if (this.bottom) { e = Object2ObjectRBTreeMap.this.firstEntry; }
/*      */       else
/* 1521 */       { e = Object2ObjectRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1523 */         if (Object2ObjectRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1527 */       if (e == null || (!this.top && Object2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1528 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ObjectRBTreeMap.Entry<K, V> lastEntry() {
/*      */       Object2ObjectRBTreeMap.Entry<K, V> e;
/* 1537 */       if (Object2ObjectRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1541 */       if (this.top) { e = Object2ObjectRBTreeMap.this.lastEntry; }
/*      */       else
/* 1543 */       { e = Object2ObjectRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1545 */         if (Object2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1549 */       if (e == null || (!this.bottom && Object2ObjectRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1550 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1555 */       Object2ObjectRBTreeMap.Entry<K, V> e = firstEntry();
/* 1556 */       if (e == null) throw new NoSuchElementException(); 
/* 1557 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1562 */       Object2ObjectRBTreeMap.Entry<K, V> e = lastEntry();
/* 1563 */       if (e == null) throw new NoSuchElementException(); 
/* 1564 */       return e.key;
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
/*      */       extends Object2ObjectRBTreeMap<K, V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1578 */         this.next = Object2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(K k) {
/* 1582 */         this();
/* 1583 */         if (this.next != null) {
/* 1584 */           if (!Object2ObjectRBTreeMap.Submap.this.bottom && Object2ObjectRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1585 */           else if (!Object2ObjectRBTreeMap.Submap.this.top && Object2ObjectRBTreeMap.this.compare(k, (this.prev = Object2ObjectRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1587 */           { this.next = Object2ObjectRBTreeMap.this.locateKey(k);
/* 1588 */             if (Object2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0)
/* 1589 */             { this.prev = this.next;
/* 1590 */               this.next = this.next.next(); }
/* 1591 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1598 */         this.prev = this.prev.prev();
/* 1599 */         if (!Object2ObjectRBTreeMap.Submap.this.bottom && this.prev != null && Object2ObjectRBTreeMap.this.compare(this.prev.key, Object2ObjectRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1604 */         this.next = this.next.next();
/* 1605 */         if (!Object2ObjectRBTreeMap.Submap.this.top && this.next != null && Object2ObjectRBTreeMap.this.compare(this.next.key, Object2ObjectRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1614 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2ObjectMap.Entry<K, V> next() {
/* 1619 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2ObjectMap.Entry<K, V> previous() {
/* 1624 */         return previousEntry();
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
/* 1643 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/* 1648 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/* 1653 */         return (previousEntry()).key;
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
/* 1669 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public V previous() {
/* 1674 */         return (previousEntry()).value;
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
/*      */   public Object2ObjectRBTreeMap<K, V> clone() {
/*      */     Object2ObjectRBTreeMap<K, V> c;
/*      */     try {
/* 1693 */       c = (Object2ObjectRBTreeMap<K, V>)super.clone();
/* 1694 */     } catch (CloneNotSupportedException cantHappen) {
/* 1695 */       throw new InternalError();
/*      */     } 
/* 1697 */     c.keys = null;
/* 1698 */     c.values = null;
/* 1699 */     c.entries = null;
/* 1700 */     c.allocatePaths();
/* 1701 */     if (this.count != 0) {
/*      */       
/* 1703 */       Entry<K, V> rp = new Entry<>(), rq = new Entry<>();
/* 1704 */       Entry<K, V> p = rp;
/* 1705 */       rp.left(this.tree);
/* 1706 */       Entry<K, V> q = rq;
/* 1707 */       rq.pred((Entry<K, V>)null);
/*      */       while (true) {
/* 1709 */         if (!p.pred()) {
/* 1710 */           Entry<K, V> e = p.left.clone();
/* 1711 */           e.pred(q.left);
/* 1712 */           e.succ(q);
/* 1713 */           q.left(e);
/* 1714 */           p = p.left;
/* 1715 */           q = q.left;
/*      */         } else {
/* 1717 */           while (p.succ()) {
/* 1718 */             p = p.right;
/* 1719 */             if (p == null) {
/* 1720 */               q.right = null;
/* 1721 */               c.tree = rq.left;
/* 1722 */               c.firstEntry = c.tree;
/* 1723 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1724 */               c.lastEntry = c.tree;
/* 1725 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1726 */               return c;
/*      */             } 
/* 1728 */             q = q.right;
/*      */           } 
/* 1730 */           p = p.right;
/* 1731 */           q = q.right;
/*      */         } 
/* 1733 */         if (!p.succ()) {
/* 1734 */           Entry<K, V> e = p.right.clone();
/* 1735 */           e.succ(q.right);
/* 1736 */           e.pred(q);
/* 1737 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1741 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1745 */     int n = this.count;
/* 1746 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1748 */     s.defaultWriteObject();
/* 1749 */     while (n-- != 0) {
/* 1750 */       Entry<K, V> e = i.nextEntry();
/* 1751 */       s.writeObject(e.key);
/* 1752 */       s.writeObject(e.value);
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
/* 1766 */     if (n == 1) {
/* 1767 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1768 */       entry.pred(pred);
/* 1769 */       entry.succ(succ);
/* 1770 */       entry.black(true);
/* 1771 */       return entry;
/*      */     } 
/* 1773 */     if (n == 2) {
/*      */ 
/*      */       
/* 1776 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1777 */       entry.black(true);
/* 1778 */       entry.right(new Entry<>((K)s.readObject(), (V)s.readObject()));
/* 1779 */       entry.right.pred(entry);
/* 1780 */       entry.pred(pred);
/* 1781 */       entry.right.succ(succ);
/* 1782 */       return entry;
/*      */     } 
/*      */     
/* 1785 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1786 */     Entry<K, V> top = new Entry<>();
/* 1787 */     top.left(readTree(s, leftN, pred, top));
/* 1788 */     top.key = (K)s.readObject();
/* 1789 */     top.value = (V)s.readObject();
/* 1790 */     top.black(true);
/* 1791 */     top.right(readTree(s, rightN, top, succ));
/* 1792 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1794 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1798 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1801 */     setActualComparator();
/* 1802 */     allocatePaths();
/* 1803 */     if (this.count != 0) {
/* 1804 */       this.tree = readTree(s, this.count, (Entry<K, V>)null, (Entry<K, V>)null);
/*      */       
/* 1806 */       Entry<K, V> e = this.tree;
/* 1807 */       for (; e.left() != null; e = e.left());
/* 1808 */       this.firstEntry = e;
/* 1809 */       e = this.tree;
/* 1810 */       for (; e.right() != null; e = e.right());
/* 1811 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */