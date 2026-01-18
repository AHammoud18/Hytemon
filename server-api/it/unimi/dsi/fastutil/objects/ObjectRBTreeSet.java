/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
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
/*      */ public class ObjectRBTreeSet<K>
/*      */   extends AbstractObjectSortedSet<K>
/*      */   implements Serializable, Cloneable, ObjectSortedSet<K>
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public ObjectRBTreeSet() {
/*   51 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   58 */     this.tree = null;
/*   59 */     this.count = 0;
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
/*   71 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(Comparator<? super K> c) {
/*   80 */     this();
/*   81 */     this.storedComparator = c;
/*   82 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(Collection<? extends K> c) {
/*   91 */     this();
/*   92 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(SortedSet<K> s) {
/*  101 */     this(s.comparator());
/*  102 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(ObjectCollection<? extends K> c) {
/*  111 */     this();
/*  112 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(ObjectSortedSet<K> s) {
/*  121 */     this(s.comparator());
/*  122 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(Iterator<? extends K> i) {
/*      */     allocatePaths();
/*  131 */     for (; i.hasNext(); add(i.next()));
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
/*      */   public ObjectRBTreeSet(K[] a, int offset, int length, Comparator<? super K> c) {
/*  144 */     this(c);
/*  145 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  146 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(K[] a, int offset, int length) {
/*  157 */     this(a, offset, length, (Comparator<? super K>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(K[] a) {
/*  166 */     this();
/*  167 */     int i = a.length;
/*  168 */     for (; i-- != 0; add(a[i]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(K[] a, Comparator<? super K> c) {
/*  178 */     this(c);
/*  179 */     int i = a.length;
/*  180 */     for (; i-- != 0; add(a[i]));
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
/*  206 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> findKey(K k) {
/*  216 */     Entry<K> e = this.tree;
/*      */     int cmp;
/*  218 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  219 */     return e;
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
/*  230 */     Entry<K> e = this.tree, last = this.tree;
/*  231 */     int cmp = 0;
/*  232 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  233 */       last = e;
/*  234 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  236 */     return (cmp == 0) ? e : last;
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
/*  248 */     this.dirPath = new boolean[64];
/*  249 */     this.nodePath = (Entry<K>[])new Entry[64];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(K k) {
/*  254 */     Objects.requireNonNull(k);
/*  255 */     int maxDepth = 0;
/*  256 */     if (this.tree == null) {
/*  257 */       this.count++;
/*  258 */       this.tree = this.lastEntry = this.firstEntry = new Entry<>(k);
/*      */     } else {
/*  260 */       Entry<K> p = this.tree;
/*  261 */       int i = 0; while (true) {
/*      */         int cmp;
/*  263 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  265 */           for (; i-- != 0; this.nodePath[i] = null);
/*  266 */           return false;
/*      */         } 
/*  268 */         this.nodePath[i] = p;
/*  269 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  270 */           if (p.succ()) {
/*  271 */             this.count++;
/*  272 */             Entry<K> e = new Entry<>(k);
/*  273 */             if (p.right == null) this.lastEntry = e; 
/*  274 */             e.left = p;
/*  275 */             e.right = p.right;
/*  276 */             p.right(e);
/*      */             break;
/*      */           } 
/*  279 */           p = p.right; continue;
/*      */         } 
/*  281 */         if (p.pred()) {
/*  282 */           this.count++;
/*  283 */           Entry<K> e = new Entry<>(k);
/*  284 */           if (p.left == null) this.firstEntry = e; 
/*  285 */           e.right = p;
/*  286 */           e.left = p.left;
/*  287 */           p.left(e);
/*      */           break;
/*      */         } 
/*  290 */         p = p.left;
/*      */       } 
/*      */       
/*  293 */       maxDepth = i--;
/*  294 */       while (i > 0 && !this.nodePath[i].black()) {
/*  295 */         if (!this.dirPath[i - 1]) {
/*  296 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
/*  297 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  298 */             this.nodePath[i].black(true);
/*  299 */             entry1.black(true);
/*  300 */             this.nodePath[i - 1].black(false);
/*  301 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  304 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  306 */           { Entry<K> entry = this.nodePath[i];
/*  307 */             entry1 = entry.right;
/*  308 */             entry.right = entry1.left;
/*  309 */             entry1.left = entry;
/*  310 */             (this.nodePath[i - 1]).left = entry1;
/*  311 */             if (entry1.pred()) {
/*  312 */               entry1.pred(false);
/*  313 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  316 */           Entry<K> entry2 = this.nodePath[i - 1];
/*  317 */           entry2.black(false);
/*  318 */           entry1.black(true);
/*  319 */           entry2.left = entry1.right;
/*  320 */           entry1.right = entry2;
/*  321 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  323 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  324 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  326 */           if (entry1.succ()) {
/*  327 */             entry1.succ(false);
/*  328 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  333 */         Entry<K> y = (this.nodePath[i - 1]).left;
/*  334 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  335 */           this.nodePath[i].black(true);
/*  336 */           y.black(true);
/*  337 */           this.nodePath[i - 1].black(false);
/*  338 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  341 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  343 */         { Entry<K> entry = this.nodePath[i];
/*  344 */           y = entry.left;
/*  345 */           entry.left = y.right;
/*  346 */           y.right = entry;
/*  347 */           (this.nodePath[i - 1]).right = y;
/*  348 */           if (y.succ()) {
/*  349 */             y.succ(false);
/*  350 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  353 */         Entry<K> x = this.nodePath[i - 1];
/*  354 */         x.black(false);
/*  355 */         y.black(true);
/*  356 */         x.right = y.left;
/*  357 */         y.left = x;
/*  358 */         if (i < 2) { this.tree = y; }
/*      */         
/*  360 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  361 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  363 */         if (y.pred()) {
/*  364 */           y.pred(false);
/*  365 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  372 */     this.tree.black(true);
/*      */     
/*  374 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  375 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  381 */     if (this.tree == null) return false; 
/*  382 */     Entry<K> p = this.tree;
/*      */     
/*  384 */     int i = 0;
/*  385 */     K kk = (K)k;
/*      */     int cmp;
/*  387 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  388 */       this.dirPath[i] = (cmp > 0);
/*  389 */       this.nodePath[i] = p;
/*  390 */       if (this.dirPath[i++]) {
/*  391 */         if ((p = p.right()) == null) {
/*      */           
/*  393 */           for (; i-- != 0; this.nodePath[i] = null);
/*  394 */           return false;
/*      */         }  continue;
/*      */       } 
/*  397 */       if ((p = p.left()) == null) {
/*      */         
/*  399 */         for (; i-- != 0; this.nodePath[i] = null);
/*  400 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  404 */     if (p.left == null) this.firstEntry = p.next(); 
/*  405 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  406 */     if (p.succ()) {
/*  407 */       if (p.pred()) {
/*  408 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  410 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  411 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  414 */         (p.prev()).right = p.right;
/*  415 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  417 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  418 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  423 */       Entry<K> r = p.right;
/*  424 */       if (r.pred()) {
/*  425 */         r.left = p.left;
/*  426 */         r.pred(p.pred());
/*  427 */         if (!r.pred()) (r.prev()).right = r; 
/*  428 */         if (i == 0) { this.tree = r; }
/*      */         
/*  430 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  431 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  433 */         boolean color = r.black();
/*  434 */         r.black(p.black());
/*  435 */         p.black(color);
/*  436 */         this.dirPath[i] = true;
/*  437 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K> s;
/*  440 */         int j = i++;
/*      */         while (true) {
/*  442 */           this.dirPath[i] = false;
/*  443 */           this.nodePath[i++] = r;
/*  444 */           s = r.left;
/*  445 */           if (s.pred())
/*  446 */             break;  r = s;
/*      */         } 
/*  448 */         this.dirPath[j] = true;
/*  449 */         this.nodePath[j] = s;
/*  450 */         if (s.succ()) { r.pred(s); }
/*  451 */         else { r.left = s.right; }
/*  452 */          s.left = p.left;
/*  453 */         if (!p.pred()) {
/*  454 */           (p.prev()).right = s;
/*  455 */           s.pred(false);
/*      */         } 
/*  457 */         s.right(p.right);
/*  458 */         boolean color = s.black();
/*  459 */         s.black(p.black());
/*  460 */         p.black(color);
/*  461 */         if (j == 0) { this.tree = s; }
/*      */         
/*  463 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  464 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  468 */     int maxDepth = i;
/*  469 */     if (p.black()) {
/*  470 */       for (; i > 0; i--) {
/*  471 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  472 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  473 */           if (!x.black()) {
/*  474 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  478 */         if (!this.dirPath[i - 1]) {
/*  479 */           Entry<K> w = (this.nodePath[i - 1]).right;
/*  480 */           if (!w.black()) {
/*  481 */             w.black(true);
/*  482 */             this.nodePath[i - 1].black(false);
/*  483 */             (this.nodePath[i - 1]).right = w.left;
/*  484 */             w.left = this.nodePath[i - 1];
/*  485 */             if (i < 2) { this.tree = w; }
/*      */             
/*  487 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  488 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  490 */             this.nodePath[i] = this.nodePath[i - 1];
/*  491 */             this.dirPath[i] = false;
/*  492 */             this.nodePath[i - 1] = w;
/*  493 */             if (maxDepth == i++) maxDepth++; 
/*  494 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  496 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  497 */             w.black(false);
/*      */           } else {
/*  499 */             if (w.succ() || w.right.black()) {
/*  500 */               Entry<K> y = w.left;
/*  501 */               y.black(true);
/*  502 */               w.black(false);
/*  503 */               w.left = y.right;
/*  504 */               y.right = w;
/*  505 */               w = (this.nodePath[i - 1]).right = y;
/*  506 */               if (w.succ()) {
/*  507 */                 w.succ(false);
/*  508 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  511 */             w.black(this.nodePath[i - 1].black());
/*  512 */             this.nodePath[i - 1].black(true);
/*  513 */             w.right.black(true);
/*  514 */             (this.nodePath[i - 1]).right = w.left;
/*  515 */             w.left = this.nodePath[i - 1];
/*  516 */             if (i < 2) { this.tree = w; }
/*      */             
/*  518 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  519 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  521 */             if (w.pred()) {
/*  522 */               w.pred(false);
/*  523 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  528 */           Entry<K> w = (this.nodePath[i - 1]).left;
/*  529 */           if (!w.black()) {
/*  530 */             w.black(true);
/*  531 */             this.nodePath[i - 1].black(false);
/*  532 */             (this.nodePath[i - 1]).left = w.right;
/*  533 */             w.right = this.nodePath[i - 1];
/*  534 */             if (i < 2) { this.tree = w; }
/*      */             
/*  536 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  537 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  539 */             this.nodePath[i] = this.nodePath[i - 1];
/*  540 */             this.dirPath[i] = true;
/*  541 */             this.nodePath[i - 1] = w;
/*  542 */             if (maxDepth == i++) maxDepth++; 
/*  543 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  545 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  546 */             w.black(false);
/*      */           } else {
/*  548 */             if (w.pred() || w.left.black()) {
/*  549 */               Entry<K> y = w.right;
/*  550 */               y.black(true);
/*  551 */               w.black(false);
/*  552 */               w.right = y.left;
/*  553 */               y.left = w;
/*  554 */               w = (this.nodePath[i - 1]).left = y;
/*  555 */               if (w.pred()) {
/*  556 */                 w.pred(false);
/*  557 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  560 */             w.black(this.nodePath[i - 1].black());
/*  561 */             this.nodePath[i - 1].black(true);
/*  562 */             w.left.black(true);
/*  563 */             (this.nodePath[i - 1]).left = w.right;
/*  564 */             w.right = this.nodePath[i - 1];
/*  565 */             if (i < 2) { this.tree = w; }
/*      */             
/*  567 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  568 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  570 */             if (w.succ()) {
/*  571 */               w.succ(false);
/*  572 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  578 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  580 */     this.count--;
/*      */     
/*  582 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  583 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  589 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public K get(Object k) {
/*  594 */     Entry<K> entry = findKey((K)k);
/*  595 */     return (entry == null) ? null : entry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  600 */     this.count = 0;
/*  601 */     this.tree = null;
/*  602 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */ 
/*      */     
/*      */     K key;
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
/*      */     Entry() {}
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k) {
/*  641 */       this.key = k;
/*  642 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  651 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  660 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  669 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  678 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  687 */       if (pred) { this.info |= 0x40000000; }
/*  688 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  697 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  698 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  707 */       this.info |= 0x40000000;
/*  708 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  717 */       this.info |= Integer.MIN_VALUE;
/*  718 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  727 */       this.info &= 0xBFFFFFFF;
/*  728 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  737 */       this.info &= Integer.MAX_VALUE;
/*  738 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  747 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  756 */       if (black) { this.info |= 0x1; }
/*  757 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  766 */       Entry<K> next = this.right;
/*  767 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  768 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  777 */       Entry<K> prev = this.left;
/*  778 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  779 */       return prev;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  787 */         c = (Entry<K>)super.clone();
/*  788 */       } catch (CloneNotSupportedException cantHappen) {
/*  789 */         throw new InternalError();
/*      */       } 
/*  791 */       c.key = this.key;
/*  792 */       c.info = this.info;
/*  793 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  798 */       if (!(o instanceof Entry)) return false; 
/*  799 */       Entry<?> e = (Entry)o;
/*  800 */       return Objects.equals(this.key, e.key);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  805 */       return this.key.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  810 */       return String.valueOf(this.key);
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
/*      */   public int size() {
/*  847 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  852 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public K first() {
/*  857 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  858 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K last() {
/*  863 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  864 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     ObjectRBTreeSet.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectRBTreeSet.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectRBTreeSet.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  893 */     int index = 0;
/*      */     
/*      */     SetIterator() {
/*  896 */       this.next = ObjectRBTreeSet.this.firstEntry;
/*      */     }
/*      */     
/*      */     SetIterator(K k) {
/*  900 */       if ((this.next = ObjectRBTreeSet.this.locateKey(k)) != null) {
/*  901 */         if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0)
/*  902 */         { this.prev = this.next;
/*  903 */           this.next = this.next.next(); }
/*  904 */         else { this.prev = this.next.prev(); }
/*      */       
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  910 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  915 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  919 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  923 */       this.prev = this.prev.prev();
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  928 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  933 */       return (previousEntry()).key;
/*      */     }
/*      */     
/*      */     ObjectRBTreeSet.Entry<K> nextEntry() {
/*  937 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  938 */       this.curr = this.prev = this.next;
/*  939 */       this.index++;
/*  940 */       updateNext();
/*  941 */       return this.curr;
/*      */     }
/*      */     
/*      */     ObjectRBTreeSet.Entry<K> previousEntry() {
/*  945 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  946 */       this.curr = this.next = this.prev;
/*  947 */       this.index--;
/*  948 */       updatePrevious();
/*  949 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  954 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  959 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  964 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/*  967 */       if (this.curr == this.prev) this.index--; 
/*  968 */       this.next = this.prev = this.curr;
/*  969 */       updatePrevious();
/*  970 */       updateNext();
/*  971 */       ObjectRBTreeSet.this.remove(this.curr.key);
/*  972 */       this.curr = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator() {
/*  978 */     return new SetIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator(K from) {
/*  983 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  988 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/*  993 */     return new Subset(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/*  998 */     return new Subset(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/* 1003 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractObjectSortedSet<K>
/*      */     implements Serializable, ObjectSortedSet<K>
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
/*      */     
/*      */     boolean top;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Subset(K from, boolean bottom, K to, boolean top) {
/* 1036 */       if (!bottom && !top && ObjectRBTreeSet.this.compare(from, to) > 0) throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")"); 
/* 1037 */       this.from = from;
/* 1038 */       this.bottom = bottom;
/* 1039 */       this.to = to;
/* 1040 */       this.top = top;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1045 */       SubsetIterator i = new SubsetIterator();
/* 1046 */       while (i.hasNext()) {
/* 1047 */         i.next();
/* 1048 */         i.remove();
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
/* 1059 */       return ((this.bottom || ObjectRBTreeSet.this.compare(k, this.from) >= 0) && (this.top || ObjectRBTreeSet.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1065 */       return (in((K)k) && ObjectRBTreeSet.this.contains(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(K k) {
/* 1070 */       if (!in(k)) throw new IllegalArgumentException("Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1071 */       return ObjectRBTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1077 */       if (!in((K)k)) return false; 
/* 1078 */       return ObjectRBTreeSet.this.remove(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1083 */       SubsetIterator i = new SubsetIterator();
/* 1084 */       int n = 0;
/* 1085 */       while (i.hasNext()) {
/* 1086 */         n++;
/* 1087 */         i.next();
/*      */       } 
/* 1089 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1094 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1099 */       return ObjectRBTreeSet.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1104 */       return new SubsetIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1109 */       return new SubsetIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1114 */       if (this.top) return new Subset(this.from, this.bottom, to, false); 
/* 1115 */       return (ObjectRBTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1120 */       if (this.bottom) return new Subset(from, false, this.to, this.top); 
/* 1121 */       return (ObjectRBTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1126 */       if (this.top && this.bottom) return new Subset(from, false, to, false); 
/* 1127 */       if (!this.top) to = (ObjectRBTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1128 */       if (!this.bottom) from = (ObjectRBTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1129 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1130 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectRBTreeSet.Entry<K> firstEntry() {
/*      */       ObjectRBTreeSet.Entry<K> e;
/* 1139 */       if (ObjectRBTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1143 */       if (this.bottom) { e = ObjectRBTreeSet.this.firstEntry; }
/*      */       else
/* 1145 */       { e = ObjectRBTreeSet.this.locateKey(this.from);
/*      */         
/* 1147 */         if (ObjectRBTreeSet.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1151 */       if (e == null || (!this.top && ObjectRBTreeSet.this.compare(e.key, this.to) >= 0)) return null; 
/* 1152 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectRBTreeSet.Entry<K> lastEntry() {
/*      */       ObjectRBTreeSet.Entry<K> e;
/* 1161 */       if (ObjectRBTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1165 */       if (this.top) { e = ObjectRBTreeSet.this.lastEntry; }
/*      */       else
/* 1167 */       { e = ObjectRBTreeSet.this.locateKey(this.to);
/*      */         
/* 1169 */         if (ObjectRBTreeSet.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1173 */       if (e == null || (!this.bottom && ObjectRBTreeSet.this.compare(e.key, this.from) < 0)) return null; 
/* 1174 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1179 */       ObjectRBTreeSet.Entry<K> e = firstEntry();
/* 1180 */       if (e == null) throw new NoSuchElementException(); 
/* 1181 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1186 */       ObjectRBTreeSet.Entry<K> e = lastEntry();
/* 1187 */       if (e == null) throw new NoSuchElementException(); 
/* 1188 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends ObjectRBTreeSet<K>.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1202 */         this.next = ObjectRBTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubsetIterator(K k) {
/* 1206 */         this();
/* 1207 */         if (this.next != null) {
/* 1208 */           if (!ObjectRBTreeSet.Subset.this.bottom && ObjectRBTreeSet.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1209 */           else if (!ObjectRBTreeSet.Subset.this.top && ObjectRBTreeSet.this.compare(k, (this.prev = ObjectRBTreeSet.Subset.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1211 */           { this.next = ObjectRBTreeSet.this.locateKey(k);
/* 1212 */             if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0)
/* 1213 */             { this.prev = this.next;
/* 1214 */               this.next = this.next.next(); }
/* 1215 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1222 */         this.prev = this.prev.prev();
/* 1223 */         if (!ObjectRBTreeSet.Subset.this.bottom && this.prev != null && ObjectRBTreeSet.this.compare(this.prev.key, ObjectRBTreeSet.Subset.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1228 */         this.next = this.next.next();
/* 1229 */         if (!ObjectRBTreeSet.Subset.this.top && this.next != null && ObjectRBTreeSet.this.compare(this.next.key, ObjectRBTreeSet.Subset.this.to) >= 0) this.next = null;
/*      */       
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
/*      */   public Object clone() {
/*      */     ObjectRBTreeSet<K> c;
/*      */     try {
/* 1248 */       c = (ObjectRBTreeSet<K>)super.clone();
/* 1249 */     } catch (CloneNotSupportedException cantHappen) {
/* 1250 */       throw new InternalError();
/*      */     } 
/* 1252 */     c.allocatePaths();
/* 1253 */     if (this.count != 0) {
/*      */       
/* 1255 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1256 */       Entry<K> p = rp;
/* 1257 */       rp.left(this.tree);
/* 1258 */       Entry<K> q = rq;
/* 1259 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1261 */         if (!p.pred()) {
/* 1262 */           Entry<K> e = p.left.clone();
/* 1263 */           e.pred(q.left);
/* 1264 */           e.succ(q);
/* 1265 */           q.left(e);
/* 1266 */           p = p.left;
/* 1267 */           q = q.left;
/*      */         } else {
/* 1269 */           while (p.succ()) {
/* 1270 */             p = p.right;
/* 1271 */             if (p == null) {
/* 1272 */               q.right = null;
/* 1273 */               c.tree = rq.left;
/* 1274 */               c.firstEntry = c.tree;
/* 1275 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1276 */               c.lastEntry = c.tree;
/* 1277 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1278 */               return c;
/*      */             } 
/* 1280 */             q = q.right;
/*      */           } 
/* 1282 */           p = p.right;
/* 1283 */           q = q.right;
/*      */         } 
/* 1285 */         if (!p.succ()) {
/* 1286 */           Entry<K> e = p.right.clone();
/* 1287 */           e.succ(q.right);
/* 1288 */           e.pred(q);
/* 1289 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1293 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1297 */     int n = this.count;
/* 1298 */     SetIterator i = new SetIterator();
/* 1299 */     s.defaultWriteObject();
/* 1300 */     for (; n-- != 0; s.writeObject(i.next()));
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
/* 1313 */     if (n == 1) {
/* 1314 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1315 */       entry.pred(pred);
/* 1316 */       entry.succ(succ);
/* 1317 */       entry.black(true);
/* 1318 */       return entry;
/*      */     } 
/* 1320 */     if (n == 2) {
/*      */ 
/*      */       
/* 1323 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1324 */       entry.black(true);
/* 1325 */       entry.right(new Entry<>((K)s.readObject()));
/* 1326 */       entry.right.pred(entry);
/* 1327 */       entry.pred(pred);
/* 1328 */       entry.right.succ(succ);
/* 1329 */       return entry;
/*      */     } 
/*      */     
/* 1332 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1333 */     Entry<K> top = new Entry<>();
/* 1334 */     top.left(readTree(s, leftN, pred, top));
/* 1335 */     top.key = (K)s.readObject();
/* 1336 */     top.black(true);
/* 1337 */     top.right(readTree(s, rightN, top, succ));
/* 1338 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1340 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1344 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1347 */     setActualComparator();
/* 1348 */     allocatePaths();
/* 1349 */     if (this.count != 0) {
/* 1350 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1352 */       Entry<K> e = this.tree;
/* 1353 */       for (; e.left() != null; e = e.left());
/* 1354 */       this.firstEntry = e;
/* 1355 */       e = this.tree;
/* 1356 */       for (; e.right() != null; e = e.right());
/* 1357 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectRBTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */