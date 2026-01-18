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
/*      */ public class Object2BooleanRBTreeMap<K>
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
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public Object2BooleanRBTreeMap() {
/*   67 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   74 */     this.tree = null;
/*   75 */     this.count = 0;
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
/*   87 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(Comparator<? super K> c) {
/*   96 */     this();
/*   97 */     this.storedComparator = c;
/*   98 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(Map<? extends K, ? extends Boolean> m) {
/*  107 */     this();
/*  108 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(SortedMap<K, Boolean> m) {
/*  117 */     this(m.comparator());
/*  118 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(Object2BooleanMap<? extends K> m) {
/*  127 */     this();
/*  128 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(Object2BooleanSortedMap<K> m) {
/*  137 */     this(m.comparator());
/*  138 */     putAll(m);
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
/*      */   public Object2BooleanRBTreeMap(K[] k, boolean[] v, Comparator<? super K> c) {
/*  150 */     this(c);
/*  151 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  152 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(K[] k, boolean[] v) {
/*  163 */     this(k, v, (Comparator<? super K>)null);
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
/*  189 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K> findKey(K k) {
/*  199 */     Entry<K> e = this.tree;
/*      */     int cmp;
/*  201 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  202 */     return e;
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
/*  213 */     Entry<K> e = this.tree, last = this.tree;
/*  214 */     int cmp = 0;
/*  215 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  216 */       last = e;
/*  217 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  219 */     return (cmp == 0) ? e : last;
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
/*  231 */     this.dirPath = new boolean[64];
/*  232 */     this.nodePath = (Entry<K>[])new Entry[64];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  237 */     Entry<K> e = add(k);
/*  238 */     boolean oldValue = e.value;
/*  239 */     e.value = v;
/*  240 */     return oldValue;
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
/*      */     Entry<K> e;
/*  253 */     Objects.requireNonNull(k);
/*  254 */     this.modified = false;
/*  255 */     int maxDepth = 0;
/*      */     
/*  257 */     if (this.tree == null) {
/*  258 */       this.count++;
/*  259 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  261 */       Entry<K> p = this.tree;
/*  262 */       int i = 0; while (true) {
/*      */         int cmp;
/*  264 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  266 */           for (; i-- != 0; this.nodePath[i] = null);
/*  267 */           return p;
/*      */         } 
/*  269 */         this.nodePath[i] = p;
/*  270 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  271 */           if (p.succ()) {
/*  272 */             this.count++;
/*  273 */             e = new Entry<>(k, this.defRetValue);
/*  274 */             if (p.right == null) this.lastEntry = e; 
/*  275 */             e.left = p;
/*  276 */             e.right = p.right;
/*  277 */             p.right(e);
/*      */             break;
/*      */           } 
/*  280 */           p = p.right; continue;
/*      */         } 
/*  282 */         if (p.pred()) {
/*  283 */           this.count++;
/*  284 */           e = new Entry<>(k, this.defRetValue);
/*  285 */           if (p.left == null) this.firstEntry = e; 
/*  286 */           e.right = p;
/*  287 */           e.left = p.left;
/*  288 */           p.left(e);
/*      */           break;
/*      */         } 
/*  291 */         p = p.left;
/*      */       } 
/*      */       
/*  294 */       this.modified = true;
/*  295 */       maxDepth = i--;
/*  296 */       while (i > 0 && !this.nodePath[i].black()) {
/*  297 */         if (!this.dirPath[i - 1]) {
/*  298 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
/*  299 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  300 */             this.nodePath[i].black(true);
/*  301 */             entry1.black(true);
/*  302 */             this.nodePath[i - 1].black(false);
/*  303 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  306 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  308 */           { Entry<K> entry = this.nodePath[i];
/*  309 */             entry1 = entry.right;
/*  310 */             entry.right = entry1.left;
/*  311 */             entry1.left = entry;
/*  312 */             (this.nodePath[i - 1]).left = entry1;
/*  313 */             if (entry1.pred()) {
/*  314 */               entry1.pred(false);
/*  315 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  318 */           Entry<K> entry2 = this.nodePath[i - 1];
/*  319 */           entry2.black(false);
/*  320 */           entry1.black(true);
/*  321 */           entry2.left = entry1.right;
/*  322 */           entry1.right = entry2;
/*  323 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  325 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  326 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  328 */           if (entry1.succ()) {
/*  329 */             entry1.succ(false);
/*  330 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  335 */         Entry<K> y = (this.nodePath[i - 1]).left;
/*  336 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  337 */           this.nodePath[i].black(true);
/*  338 */           y.black(true);
/*  339 */           this.nodePath[i - 1].black(false);
/*  340 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  343 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  345 */         { Entry<K> entry = this.nodePath[i];
/*  346 */           y = entry.left;
/*  347 */           entry.left = y.right;
/*  348 */           y.right = entry;
/*  349 */           (this.nodePath[i - 1]).right = y;
/*  350 */           if (y.succ()) {
/*  351 */             y.succ(false);
/*  352 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  355 */         Entry<K> x = this.nodePath[i - 1];
/*  356 */         x.black(false);
/*  357 */         y.black(true);
/*  358 */         x.right = y.left;
/*  359 */         y.left = x;
/*  360 */         if (i < 2) { this.tree = y; }
/*      */         
/*  362 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  363 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  365 */         if (y.pred()) {
/*  366 */           y.pred(false);
/*  367 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  374 */     this.tree.black(true);
/*      */     
/*  376 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  377 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  385 */     this.modified = false;
/*  386 */     if (this.tree == null) return this.defRetValue; 
/*  387 */     Entry<K> p = this.tree;
/*      */     
/*  389 */     int i = 0;
/*  390 */     K kk = (K)k;
/*      */     int cmp;
/*  392 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  393 */       this.dirPath[i] = (cmp > 0);
/*  394 */       this.nodePath[i] = p;
/*  395 */       if (this.dirPath[i++]) {
/*  396 */         if ((p = p.right()) == null) {
/*      */           
/*  398 */           for (; i-- != 0; this.nodePath[i] = null);
/*  399 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  402 */       if ((p = p.left()) == null) {
/*      */         
/*  404 */         for (; i-- != 0; this.nodePath[i] = null);
/*  405 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  409 */     if (p.left == null) this.firstEntry = p.next(); 
/*  410 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  411 */     if (p.succ()) {
/*  412 */       if (p.pred()) {
/*  413 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  415 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  416 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  419 */         (p.prev()).right = p.right;
/*  420 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  422 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  423 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  428 */       Entry<K> r = p.right;
/*  429 */       if (r.pred()) {
/*  430 */         r.left = p.left;
/*  431 */         r.pred(p.pred());
/*  432 */         if (!r.pred()) (r.prev()).right = r; 
/*  433 */         if (i == 0) { this.tree = r; }
/*      */         
/*  435 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  436 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  438 */         boolean color = r.black();
/*  439 */         r.black(p.black());
/*  440 */         p.black(color);
/*  441 */         this.dirPath[i] = true;
/*  442 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K> s;
/*  445 */         int j = i++;
/*      */         while (true) {
/*  447 */           this.dirPath[i] = false;
/*  448 */           this.nodePath[i++] = r;
/*  449 */           s = r.left;
/*  450 */           if (s.pred())
/*  451 */             break;  r = s;
/*      */         } 
/*  453 */         this.dirPath[j] = true;
/*  454 */         this.nodePath[j] = s;
/*  455 */         if (s.succ()) { r.pred(s); }
/*  456 */         else { r.left = s.right; }
/*  457 */          s.left = p.left;
/*  458 */         if (!p.pred()) {
/*  459 */           (p.prev()).right = s;
/*  460 */           s.pred(false);
/*      */         } 
/*  462 */         s.right(p.right);
/*  463 */         boolean color = s.black();
/*  464 */         s.black(p.black());
/*  465 */         p.black(color);
/*  466 */         if (j == 0) { this.tree = s; }
/*      */         
/*  468 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  469 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  473 */     int maxDepth = i;
/*  474 */     if (p.black()) {
/*  475 */       for (; i > 0; i--) {
/*  476 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  477 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  478 */           if (!x.black()) {
/*  479 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  483 */         if (!this.dirPath[i - 1]) {
/*  484 */           Entry<K> w = (this.nodePath[i - 1]).right;
/*  485 */           if (!w.black()) {
/*  486 */             w.black(true);
/*  487 */             this.nodePath[i - 1].black(false);
/*  488 */             (this.nodePath[i - 1]).right = w.left;
/*  489 */             w.left = this.nodePath[i - 1];
/*  490 */             if (i < 2) { this.tree = w; }
/*      */             
/*  492 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  493 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  495 */             this.nodePath[i] = this.nodePath[i - 1];
/*  496 */             this.dirPath[i] = false;
/*  497 */             this.nodePath[i - 1] = w;
/*  498 */             if (maxDepth == i++) maxDepth++; 
/*  499 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  501 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  502 */             w.black(false);
/*      */           } else {
/*  504 */             if (w.succ() || w.right.black()) {
/*  505 */               Entry<K> y = w.left;
/*  506 */               y.black(true);
/*  507 */               w.black(false);
/*  508 */               w.left = y.right;
/*  509 */               y.right = w;
/*  510 */               w = (this.nodePath[i - 1]).right = y;
/*  511 */               if (w.succ()) {
/*  512 */                 w.succ(false);
/*  513 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  516 */             w.black(this.nodePath[i - 1].black());
/*  517 */             this.nodePath[i - 1].black(true);
/*  518 */             w.right.black(true);
/*  519 */             (this.nodePath[i - 1]).right = w.left;
/*  520 */             w.left = this.nodePath[i - 1];
/*  521 */             if (i < 2) { this.tree = w; }
/*      */             
/*  523 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  524 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  526 */             if (w.pred()) {
/*  527 */               w.pred(false);
/*  528 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  533 */           Entry<K> w = (this.nodePath[i - 1]).left;
/*  534 */           if (!w.black()) {
/*  535 */             w.black(true);
/*  536 */             this.nodePath[i - 1].black(false);
/*  537 */             (this.nodePath[i - 1]).left = w.right;
/*  538 */             w.right = this.nodePath[i - 1];
/*  539 */             if (i < 2) { this.tree = w; }
/*      */             
/*  541 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  542 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  544 */             this.nodePath[i] = this.nodePath[i - 1];
/*  545 */             this.dirPath[i] = true;
/*  546 */             this.nodePath[i - 1] = w;
/*  547 */             if (maxDepth == i++) maxDepth++; 
/*  548 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  550 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  551 */             w.black(false);
/*      */           } else {
/*  553 */             if (w.pred() || w.left.black()) {
/*  554 */               Entry<K> y = w.right;
/*  555 */               y.black(true);
/*  556 */               w.black(false);
/*  557 */               w.right = y.left;
/*  558 */               y.left = w;
/*  559 */               w = (this.nodePath[i - 1]).left = y;
/*  560 */               if (w.pred()) {
/*  561 */                 w.pred(false);
/*  562 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  565 */             w.black(this.nodePath[i - 1].black());
/*  566 */             this.nodePath[i - 1].black(true);
/*  567 */             w.left.black(true);
/*  568 */             (this.nodePath[i - 1]).left = w.right;
/*  569 */             w.right = this.nodePath[i - 1];
/*  570 */             if (i < 2) { this.tree = w; }
/*      */             
/*  572 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  573 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  575 */             if (w.succ()) {
/*  576 */               w.succ(false);
/*  577 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  583 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  585 */     this.modified = true;
/*  586 */     this.count--;
/*      */     
/*  588 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  589 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  594 */     ValueIterator i = new ValueIterator();
/*      */     
/*  596 */     int j = this.count;
/*  597 */     while (j-- != 0) {
/*  598 */       boolean ev = i.nextBoolean();
/*  599 */       if (ev == v) return true; 
/*      */     } 
/*  601 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  606 */     this.count = 0;
/*  607 */     this.tree = null;
/*  608 */     this.entries = null;
/*  609 */     this.values = null;
/*  610 */     this.keys = null;
/*  611 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2BooleanMap.BasicEntry<K>
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
/*  640 */       super((K)null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, boolean v) {
/*  650 */       super(k, v);
/*  651 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  660 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  669 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  678 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  687 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  696 */       if (pred) { this.info |= 0x40000000; }
/*  697 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  706 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  707 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  716 */       this.info |= 0x40000000;
/*  717 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  726 */       this.info |= Integer.MIN_VALUE;
/*  727 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  736 */       this.info &= 0xBFFFFFFF;
/*  737 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  746 */       this.info &= Integer.MAX_VALUE;
/*  747 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  756 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  765 */       if (black) { this.info |= 0x1; }
/*  766 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  775 */       Entry<K> next = this.right;
/*  776 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  777 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  786 */       Entry<K> prev = this.left;
/*  787 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  788 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  793 */       boolean oldValue = this.value;
/*  794 */       this.value = value;
/*  795 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  803 */         c = (Entry<K>)super.clone();
/*  804 */       } catch (CloneNotSupportedException cantHappen) {
/*  805 */         throw new InternalError();
/*      */       } 
/*  807 */       c.key = this.key;
/*  808 */       c.value = this.value;
/*  809 */       c.info = this.info;
/*  810 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  816 */       if (!(o instanceof Map.Entry)) return false; 
/*  817 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  818 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  823 */       return this.key.hashCode() ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  828 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  864 */     if (k == null) return false; 
/*  865 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  870 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  875 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  881 */     Entry<K> e = findKey((K)k);
/*  882 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  887 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  888 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  893 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  894 */     return this.lastEntry.key;
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
/*      */     Object2BooleanRBTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanRBTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanRBTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  923 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  926 */       this.next = Object2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(K k) {
/*  930 */       if ((this.next = Object2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  931 */         if (Object2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0)
/*  932 */         { this.prev = this.next;
/*  933 */           this.next = this.next.next(); }
/*  934 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  939 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  943 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  947 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Object2BooleanRBTreeMap.Entry<K> nextEntry() {
/*  951 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  952 */       this.curr = this.prev = this.next;
/*  953 */       this.index++;
/*  954 */       updateNext();
/*  955 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  959 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Object2BooleanRBTreeMap.Entry<K> previousEntry() {
/*  963 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  964 */       this.curr = this.next = this.prev;
/*  965 */       this.index--;
/*  966 */       updatePrevious();
/*  967 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  971 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  975 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  979 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/*  982 */       if (this.curr == this.prev) this.index--; 
/*  983 */       this.next = this.prev = this.curr;
/*  984 */       updatePrevious();
/*  985 */       updateNext();
/*  986 */       Object2BooleanRBTreeMap.this.removeBoolean(this.curr.key);
/*  987 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  991 */       int i = n;
/*  992 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  993 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*  997 */       int i = n;
/*  998 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/*  999 */       return n - i - 1;
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
/* 1014 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanMap.Entry<K> next() {
/* 1019 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanMap.Entry<K> previous() {
/* 1024 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1031 */     if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2BooleanMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1036 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1041 */             return new Object2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1046 */             return new Object2BooleanRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1052 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1053 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1054 */             if (e.getKey() == null) return false; 
/* 1055 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1056 */             Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1057 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1063 */             if (!(o instanceof Map.Entry)) return false; 
/* 1064 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1065 */             if (e.getKey() == null) return false; 
/* 1066 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1067 */             Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1068 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue()) return false; 
/* 1069 */             Object2BooleanRBTreeMap.this.removeBoolean(f.key);
/* 1070 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1075 */             return Object2BooleanRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1080 */             Object2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2BooleanMap.Entry<K> first() {
/* 1085 */             return Object2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2BooleanMap.Entry<K> last() {
/* 1090 */             return Object2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1095 */             return Object2BooleanRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1100 */             return Object2BooleanRBTreeMap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1105 */             return Object2BooleanRBTreeMap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1108 */     return this.entries;
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
/* 1124 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1129 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1134 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1142 */       return new Object2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1147 */       return new Object2BooleanRBTreeMap.KeyIterator(from);
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
/* 1162 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1163 */     return this.keys;
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
/* 1177 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/* 1182 */       return (previousEntry()).value;
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
/* 1197 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1200 */             return (BooleanIterator)new Object2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1205 */             return Object2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1210 */             return Object2BooleanRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1215 */             Object2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1218 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1223 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1228 */     return new Submap(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1233 */     return new Submap(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1238 */     return new Submap(from, false, to, false);
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
/* 1277 */       if (!bottom && !top && Object2BooleanRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1278 */       this.from = from;
/* 1279 */       this.bottom = bottom;
/* 1280 */       this.to = to;
/* 1281 */       this.top = top;
/* 1282 */       this.defRetValue = Object2BooleanRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1287 */       SubmapIterator i = new SubmapIterator();
/* 1288 */       while (i.hasNext()) {
/* 1289 */         i.nextEntry();
/* 1290 */         i.remove();
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
/* 1301 */       return ((this.bottom || Object2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2BooleanRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1306 */       if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1309 */               return new Object2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1314 */               return new Object2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1319 */               return Object2BooleanRBTreeMap.this.object2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1325 */               if (!(o instanceof Map.Entry)) return false; 
/* 1326 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1327 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1328 */               Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1329 */               return (f != null && Object2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1335 */               if (!(o instanceof Map.Entry)) return false; 
/* 1336 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1337 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1338 */               Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1339 */               if (f != null && Object2BooleanRBTreeMap.Submap.this.in(f.key)) Object2BooleanRBTreeMap.Submap.this.removeBoolean(f.key); 
/* 1340 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1345 */               int c = 0;
/* 1346 */               for (Iterator<?> i = iterator(); i.hasNext(); ) { c++; i.next(); }
/* 1347 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1352 */               return !(new Object2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1357 */               Object2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2BooleanMap.Entry<K> first() {
/* 1362 */               return Object2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2BooleanMap.Entry<K> last() {
/* 1367 */               return Object2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1372 */               return Object2BooleanRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1377 */               return Object2BooleanRBTreeMap.Submap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1382 */               return Object2BooleanRBTreeMap.Submap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1385 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet { private KeySet() {}
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1391 */         return new Object2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1396 */         return new Object2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1402 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1403 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanCollection values() {
/* 1408 */       if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1411 */               return (BooleanIterator)new Object2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1416 */               return Object2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1421 */               return Object2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1426 */               Object2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1429 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1435 */       if (k == null) return false; 
/* 1436 */       return (in((K)k) && Object2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1441 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1443 */       while (i.hasNext()) {
/* 1444 */         boolean ev = (i.nextEntry()).value;
/* 1445 */         if (ev == v) return true; 
/*      */       } 
/* 1447 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object k) {
/* 1454 */       K kk = (K)k; Object2BooleanRBTreeMap.Entry<K> e;
/* 1455 */       return (in(kk) && (e = Object2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean put(K k, boolean v) {
/* 1460 */       Object2BooleanRBTreeMap.this.modified = false;
/* 1461 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1462 */       boolean oldValue = Object2BooleanRBTreeMap.this.put(k, v);
/* 1463 */       return Object2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(Object k) {
/* 1469 */       Object2BooleanRBTreeMap.this.modified = false;
/* 1470 */       if (!in((K)k)) return this.defRetValue; 
/* 1471 */       boolean oldValue = Object2BooleanRBTreeMap.this.removeBoolean(k);
/* 1472 */       return Object2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1477 */       SubmapIterator i = new SubmapIterator();
/* 1478 */       int n = 0;
/* 1479 */       while (i.hasNext()) {
/* 1480 */         n++;
/* 1481 */         i.nextEntry();
/*      */       } 
/* 1483 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1488 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1493 */       return Object2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 1498 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1499 */       return (Object2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1504 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1505 */       return (Object2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1510 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1511 */       if (!this.top) to = (Object2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1512 */       if (!this.bottom) from = (Object2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1513 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1514 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanRBTreeMap.Entry<K> firstEntry() {
/*      */       Object2BooleanRBTreeMap.Entry<K> e;
/* 1523 */       if (Object2BooleanRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1527 */       if (this.bottom) { e = Object2BooleanRBTreeMap.this.firstEntry; }
/*      */       else
/* 1529 */       { e = Object2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1531 */         if (Object2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1535 */       if (e == null || (!this.top && Object2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1536 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanRBTreeMap.Entry<K> lastEntry() {
/*      */       Object2BooleanRBTreeMap.Entry<K> e;
/* 1545 */       if (Object2BooleanRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1549 */       if (this.top) { e = Object2BooleanRBTreeMap.this.lastEntry; }
/*      */       else
/* 1551 */       { e = Object2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1553 */         if (Object2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1557 */       if (e == null || (!this.bottom && Object2BooleanRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1558 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1563 */       Object2BooleanRBTreeMap.Entry<K> e = firstEntry();
/* 1564 */       if (e == null) throw new NoSuchElementException(); 
/* 1565 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1570 */       Object2BooleanRBTreeMap.Entry<K> e = lastEntry();
/* 1571 */       if (e == null) throw new NoSuchElementException(); 
/* 1572 */       return e.key;
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
/*      */       extends Object2BooleanRBTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1586 */         this.next = Object2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(K k) {
/* 1590 */         this();
/* 1591 */         if (this.next != null) {
/* 1592 */           if (!Object2BooleanRBTreeMap.Submap.this.bottom && Object2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1593 */           else if (!Object2BooleanRBTreeMap.Submap.this.top && Object2BooleanRBTreeMap.this.compare(k, (this.prev = Object2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1595 */           { this.next = Object2BooleanRBTreeMap.this.locateKey(k);
/* 1596 */             if (Object2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0)
/* 1597 */             { this.prev = this.next;
/* 1598 */               this.next = this.next.next(); }
/* 1599 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1606 */         this.prev = this.prev.prev();
/* 1607 */         if (!Object2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Object2BooleanRBTreeMap.this.compare(this.prev.key, Object2BooleanRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1612 */         this.next = this.next.next();
/* 1613 */         if (!Object2BooleanRBTreeMap.Submap.this.top && this.next != null && Object2BooleanRBTreeMap.this.compare(this.next.key, Object2BooleanRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1622 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2BooleanMap.Entry<K> next() {
/* 1627 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2BooleanMap.Entry<K> previous() {
/* 1632 */         return previousEntry();
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
/* 1651 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/* 1656 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/* 1661 */         return (previousEntry()).key;
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
/* 1677 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean previousBoolean() {
/* 1682 */         return (previousEntry()).value;
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
/*      */   public Object2BooleanRBTreeMap<K> clone() {
/*      */     Object2BooleanRBTreeMap<K> c;
/*      */     try {
/* 1701 */       c = (Object2BooleanRBTreeMap<K>)super.clone();
/* 1702 */     } catch (CloneNotSupportedException cantHappen) {
/* 1703 */       throw new InternalError();
/*      */     } 
/* 1705 */     c.keys = null;
/* 1706 */     c.values = null;
/* 1707 */     c.entries = null;
/* 1708 */     c.allocatePaths();
/* 1709 */     if (this.count != 0) {
/*      */       
/* 1711 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1712 */       Entry<K> p = rp;
/* 1713 */       rp.left(this.tree);
/* 1714 */       Entry<K> q = rq;
/* 1715 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1717 */         if (!p.pred()) {
/* 1718 */           Entry<K> e = p.left.clone();
/* 1719 */           e.pred(q.left);
/* 1720 */           e.succ(q);
/* 1721 */           q.left(e);
/* 1722 */           p = p.left;
/* 1723 */           q = q.left;
/*      */         } else {
/* 1725 */           while (p.succ()) {
/* 1726 */             p = p.right;
/* 1727 */             if (p == null) {
/* 1728 */               q.right = null;
/* 1729 */               c.tree = rq.left;
/* 1730 */               c.firstEntry = c.tree;
/* 1731 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1732 */               c.lastEntry = c.tree;
/* 1733 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1734 */               return c;
/*      */             } 
/* 1736 */             q = q.right;
/*      */           } 
/* 1738 */           p = p.right;
/* 1739 */           q = q.right;
/*      */         } 
/* 1741 */         if (!p.succ()) {
/* 1742 */           Entry<K> e = p.right.clone();
/* 1743 */           e.succ(q.right);
/* 1744 */           e.pred(q);
/* 1745 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1749 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1753 */     int n = this.count;
/* 1754 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1756 */     s.defaultWriteObject();
/* 1757 */     while (n-- != 0) {
/* 1758 */       Entry<K> e = i.nextEntry();
/* 1759 */       s.writeObject(e.key);
/* 1760 */       s.writeBoolean(e.value);
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
/* 1774 */     if (n == 1) {
/* 1775 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1776 */       entry.pred(pred);
/* 1777 */       entry.succ(succ);
/* 1778 */       entry.black(true);
/* 1779 */       return entry;
/*      */     } 
/* 1781 */     if (n == 2) {
/*      */ 
/*      */       
/* 1784 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1785 */       entry.black(true);
/* 1786 */       entry.right(new Entry<>((K)s.readObject(), s.readBoolean()));
/* 1787 */       entry.right.pred(entry);
/* 1788 */       entry.pred(pred);
/* 1789 */       entry.right.succ(succ);
/* 1790 */       return entry;
/*      */     } 
/*      */     
/* 1793 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1794 */     Entry<K> top = new Entry<>();
/* 1795 */     top.left(readTree(s, leftN, pred, top));
/* 1796 */     top.key = (K)s.readObject();
/* 1797 */     top.value = s.readBoolean();
/* 1798 */     top.black(true);
/* 1799 */     top.right(readTree(s, rightN, top, succ));
/* 1800 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1802 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1806 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1809 */     setActualComparator();
/* 1810 */     allocatePaths();
/* 1811 */     if (this.count != 0) {
/* 1812 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1814 */       Entry<K> e = this.tree;
/* 1815 */       for (; e.left() != null; e = e.left());
/* 1816 */       this.firstEntry = e;
/* 1817 */       e = this.tree;
/* 1818 */       for (; e.right() != null; e = e.right());
/* 1819 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */