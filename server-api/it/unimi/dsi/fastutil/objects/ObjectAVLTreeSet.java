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
/*      */ 
/*      */ public class ObjectAVLTreeSet<K>
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
/*      */   
/*      */   public ObjectAVLTreeSet() {
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
/*      */   public ObjectAVLTreeSet(Comparator<? super K> c) {
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
/*      */   public ObjectAVLTreeSet(Collection<? extends K> c) {
/*   91 */     this();
/*   92 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(SortedSet<K> s) {
/*  101 */     this(s.comparator());
/*  102 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(ObjectCollection<? extends K> c) {
/*  111 */     this();
/*  112 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(ObjectSortedSet<K> s) {
/*  121 */     this(s.comparator());
/*  122 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(Iterator<? extends K> i) {
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
/*      */   public ObjectAVLTreeSet(K[] a, int offset, int length, Comparator<? super K> c) {
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
/*      */   public ObjectAVLTreeSet(K[] a, int offset, int length) {
/*  157 */     this(a, offset, length, (Comparator<? super K>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(K[] a) {
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
/*      */   public ObjectAVLTreeSet(K[] a, Comparator<? super K> c) {
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
/*      */   private void allocatePaths() {
/*  246 */     this.dirPath = new boolean[48];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(K k) {
/*  251 */     Objects.requireNonNull(k);
/*  252 */     if (this.tree == null) {
/*  253 */       this.count++;
/*  254 */       this.tree = this.lastEntry = this.firstEntry = new Entry<>(k);
/*      */     } else {
/*  256 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, e = null, w = null;
/*  257 */       int i = 0; while (true) {
/*      */         int cmp;
/*  259 */         if ((cmp = compare(k, p.key)) == 0) return false; 
/*  260 */         if (p.balance() != 0) {
/*  261 */           i = 0;
/*  262 */           z = q;
/*  263 */           y = p;
/*      */         } 
/*  265 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  266 */           if (p.succ()) {
/*  267 */             this.count++;
/*  268 */             e = new Entry<>(k);
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
/*  280 */           e = new Entry<>(k);
/*  281 */           if (p.left == null) this.firstEntry = e; 
/*  282 */           e.right = p;
/*  283 */           e.left = p.left;
/*  284 */           p.left(e);
/*      */           break;
/*      */         } 
/*  287 */         q = p;
/*  288 */         p = p.left;
/*      */       } 
/*      */       
/*  291 */       p = y;
/*  292 */       i = 0;
/*  293 */       while (p != e) {
/*  294 */         if (this.dirPath[i]) { p.incBalance(); }
/*  295 */         else { p.decBalance(); }
/*  296 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  298 */       if (y.balance() == -2)
/*  299 */       { Entry<K> x = y.left;
/*  300 */         if (x.balance() == -1) {
/*  301 */           w = x;
/*  302 */           if (x.succ())
/*  303 */           { x.succ(false);
/*  304 */             y.pred(x); }
/*  305 */           else { y.left = x.right; }
/*  306 */            x.right = y;
/*  307 */           x.balance(0);
/*  308 */           y.balance(0);
/*      */         } else {
/*  310 */           assert x.balance() == 1;
/*  311 */           w = x.right;
/*  312 */           x.right = w.left;
/*  313 */           w.left = x;
/*  314 */           y.left = w.right;
/*  315 */           w.right = y;
/*  316 */           if (w.balance() == -1) {
/*  317 */             x.balance(0);
/*  318 */             y.balance(1);
/*  319 */           } else if (w.balance() == 0) {
/*  320 */             x.balance(0);
/*  321 */             y.balance(0);
/*      */           } else {
/*  323 */             x.balance(-1);
/*  324 */             y.balance(0);
/*      */           } 
/*  326 */           w.balance(0);
/*  327 */           if (w.pred()) {
/*  328 */             x.succ(w);
/*  329 */             w.pred(false);
/*      */           } 
/*  331 */           if (w.succ()) {
/*  332 */             y.pred(w);
/*  333 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  336 */       else if (y.balance() == 2)
/*  337 */       { Entry<K> x = y.right;
/*  338 */         if (x.balance() == 1) {
/*  339 */           w = x;
/*  340 */           if (x.pred())
/*  341 */           { x.pred(false);
/*  342 */             y.succ(x); }
/*  343 */           else { y.right = x.left; }
/*  344 */            x.left = y;
/*  345 */           x.balance(0);
/*  346 */           y.balance(0);
/*      */         } else {
/*  348 */           assert x.balance() == -1;
/*  349 */           w = x.left;
/*  350 */           x.left = w.right;
/*  351 */           w.right = x;
/*  352 */           y.right = w.left;
/*  353 */           w.left = y;
/*  354 */           if (w.balance() == 1) {
/*  355 */             x.balance(0);
/*  356 */             y.balance(-1);
/*  357 */           } else if (w.balance() == 0) {
/*  358 */             x.balance(0);
/*  359 */             y.balance(0);
/*      */           } else {
/*  361 */             x.balance(1);
/*  362 */             y.balance(0);
/*      */           } 
/*  364 */           w.balance(0);
/*  365 */           if (w.pred()) {
/*  366 */             y.succ(w);
/*  367 */             w.pred(false);
/*      */           } 
/*  369 */           if (w.succ()) {
/*  370 */             x.pred(w);
/*  371 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  374 */       else { return true; }
/*  375 */        if (z == null) { this.tree = w; }
/*      */       
/*  377 */       else if (z.left == y) { z.left = w; }
/*  378 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  381 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  391 */     if (e == this.tree) return null;
/*      */     
/*  393 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  395 */       if (y.succ()) {
/*  396 */         Entry<K> p = y.right;
/*  397 */         if (p == null || p.left != e) {
/*  398 */           for (; !x.pred(); x = x.left);
/*  399 */           p = x.left;
/*      */         } 
/*  401 */         return p;
/*  402 */       }  if (x.pred()) {
/*  403 */         Entry<K> p = x.left;
/*  404 */         if (p == null || p.right != e) {
/*  405 */           for (; !y.succ(); y = y.right);
/*  406 */           p = y.right;
/*      */         } 
/*  408 */         return p;
/*      */       } 
/*  410 */       x = x.left;
/*  411 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  418 */     if (this.tree == null) return false;
/*      */     
/*  420 */     Entry<K> p = this.tree, q = null;
/*  421 */     boolean dir = false;
/*  422 */     K kk = (K)k;
/*      */     int cmp;
/*  424 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  425 */       if (dir = (cmp > 0)) {
/*  426 */         q = p;
/*  427 */         if ((p = p.right()) == null) return false;  continue;
/*      */       } 
/*  429 */       q = p;
/*  430 */       if ((p = p.left()) == null) return false;
/*      */     
/*      */     } 
/*  433 */     if (p.left == null) this.firstEntry = p.next(); 
/*  434 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  435 */     if (p.succ())
/*  436 */     { if (p.pred())
/*  437 */       { if (q != null)
/*  438 */         { if (dir) { q.succ(p.right); }
/*  439 */           else { q.pred(p.left); }  }
/*  440 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  442 */       else { (p.prev()).right = p.right;
/*  443 */         if (q != null)
/*  444 */         { if (dir) { q.right = p.left; }
/*  445 */           else { q.left = p.left; }  }
/*  446 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  449 */     else { Entry<K> r = p.right;
/*  450 */       if (r.pred()) {
/*  451 */         r.left = p.left;
/*  452 */         r.pred(p.pred());
/*  453 */         if (!r.pred()) (r.prev()).right = r; 
/*  454 */         if (q != null)
/*  455 */         { if (dir) { q.right = r; }
/*  456 */           else { q.left = r; }  }
/*  457 */         else { this.tree = r; }
/*  458 */          r.balance(p.balance());
/*  459 */         q = r;
/*  460 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  464 */           s = r.left;
/*  465 */           if (s.pred())
/*  466 */             break;  r = s;
/*      */         } 
/*  468 */         if (s.succ()) { r.pred(s); }
/*  469 */         else { r.left = s.right; }
/*  470 */          s.left = p.left;
/*  471 */         if (!p.pred()) {
/*  472 */           (p.prev()).right = s;
/*  473 */           s.pred(false);
/*      */         } 
/*  475 */         s.right = p.right;
/*  476 */         s.succ(false);
/*  477 */         if (q != null)
/*  478 */         { if (dir) { q.right = s; }
/*  479 */           else { q.left = s; }  }
/*  480 */         else { this.tree = s; }
/*  481 */          s.balance(p.balance());
/*  482 */         q = r;
/*  483 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  487 */     while (q != null) {
/*  488 */       Entry<K> y = q;
/*  489 */       q = parent(y);
/*  490 */       if (!dir) {
/*  491 */         dir = (q != null && q.left != y);
/*  492 */         y.incBalance();
/*  493 */         if (y.balance() == 1)
/*  494 */           break;  if (y.balance() == 2) {
/*  495 */           Entry<K> x = y.right;
/*  496 */           assert x != null;
/*  497 */           if (x.balance() == -1) {
/*      */             
/*  499 */             assert x.balance() == -1;
/*  500 */             Entry<K> w = x.left;
/*  501 */             x.left = w.right;
/*  502 */             w.right = x;
/*  503 */             y.right = w.left;
/*  504 */             w.left = y;
/*  505 */             if (w.balance() == 1) {
/*  506 */               x.balance(0);
/*  507 */               y.balance(-1);
/*  508 */             } else if (w.balance() == 0) {
/*  509 */               x.balance(0);
/*  510 */               y.balance(0);
/*      */             } else {
/*  512 */               assert w.balance() == -1;
/*  513 */               x.balance(1);
/*  514 */               y.balance(0);
/*      */             } 
/*  516 */             w.balance(0);
/*  517 */             if (w.pred()) {
/*  518 */               y.succ(w);
/*  519 */               w.pred(false);
/*      */             } 
/*  521 */             if (w.succ()) {
/*  522 */               x.pred(w);
/*  523 */               w.succ(false);
/*      */             } 
/*  525 */             if (q != null) {
/*  526 */               if (dir) { q.right = w; continue; }
/*  527 */                q.left = w; continue;
/*  528 */             }  this.tree = w; continue;
/*      */           } 
/*  530 */           if (q != null)
/*  531 */           { if (dir) { q.right = x; }
/*  532 */             else { q.left = x; }  }
/*  533 */           else { this.tree = x; }
/*  534 */            if (x.balance() == 0) {
/*  535 */             y.right = x.left;
/*  536 */             x.left = y;
/*  537 */             x.balance(-1);
/*  538 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  541 */           assert x.balance() == 1;
/*  542 */           if (x.pred())
/*  543 */           { y.succ(true);
/*  544 */             x.pred(false); }
/*  545 */           else { y.right = x.left; }
/*  546 */            x.left = y;
/*  547 */           y.balance(0);
/*  548 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  552 */       dir = (q != null && q.left != y);
/*  553 */       y.decBalance();
/*  554 */       if (y.balance() == -1)
/*  555 */         break;  if (y.balance() == -2) {
/*  556 */         Entry<K> x = y.left;
/*  557 */         assert x != null;
/*  558 */         if (x.balance() == 1) {
/*      */           
/*  560 */           assert x.balance() == 1;
/*  561 */           Entry<K> w = x.right;
/*  562 */           x.right = w.left;
/*  563 */           w.left = x;
/*  564 */           y.left = w.right;
/*  565 */           w.right = y;
/*  566 */           if (w.balance() == -1) {
/*  567 */             x.balance(0);
/*  568 */             y.balance(1);
/*  569 */           } else if (w.balance() == 0) {
/*  570 */             x.balance(0);
/*  571 */             y.balance(0);
/*      */           } else {
/*  573 */             assert w.balance() == 1;
/*  574 */             x.balance(-1);
/*  575 */             y.balance(0);
/*      */           } 
/*  577 */           w.balance(0);
/*  578 */           if (w.pred()) {
/*  579 */             x.succ(w);
/*  580 */             w.pred(false);
/*      */           } 
/*  582 */           if (w.succ()) {
/*  583 */             y.pred(w);
/*  584 */             w.succ(false);
/*      */           } 
/*  586 */           if (q != null) {
/*  587 */             if (dir) { q.right = w; continue; }
/*  588 */              q.left = w; continue;
/*  589 */           }  this.tree = w; continue;
/*      */         } 
/*  591 */         if (q != null)
/*  592 */         { if (dir) { q.right = x; }
/*  593 */           else { q.left = x; }  }
/*  594 */         else { this.tree = x; }
/*  595 */          if (x.balance() == 0) {
/*  596 */           y.left = x.right;
/*  597 */           x.right = y;
/*  598 */           x.balance(1);
/*  599 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  602 */         assert x.balance() == -1;
/*  603 */         if (x.succ())
/*  604 */         { y.pred(true);
/*  605 */           x.succ(false); }
/*  606 */         else { y.left = x.right; }
/*  607 */          x.right = y;
/*  608 */         y.balance(0);
/*  609 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  614 */     this.count--;
/*  615 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  621 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public K get(Object k) {
/*  626 */     Entry<K> entry = findKey((K)k);
/*  627 */     return (entry == null) ? null : entry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  632 */     this.count = 0;
/*  633 */     this.tree = null;
/*  634 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
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
/*  673 */       this.key = k;
/*  674 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  683 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  692 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  701 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  710 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  719 */       if (pred) { this.info |= 0x40000000; }
/*  720 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  729 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  730 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  739 */       this.info |= 0x40000000;
/*  740 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  749 */       this.info |= Integer.MIN_VALUE;
/*  750 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  759 */       this.info &= 0xBFFFFFFF;
/*  760 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  769 */       this.info &= Integer.MAX_VALUE;
/*  770 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  779 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  788 */       this.info &= 0xFFFFFF00;
/*  789 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  794 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  799 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  808 */       Entry<K> next = this.right;
/*  809 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  810 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  819 */       Entry<K> prev = this.left;
/*  820 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  821 */       return prev;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  829 */         c = (Entry<K>)super.clone();
/*  830 */       } catch (CloneNotSupportedException cantHappen) {
/*  831 */         throw new InternalError();
/*      */       } 
/*  833 */       c.key = this.key;
/*  834 */       c.info = this.info;
/*  835 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  840 */       if (!(o instanceof Entry)) return false; 
/*  841 */       Entry<?> e = (Entry)o;
/*  842 */       return Objects.equals(this.key, e.key);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  847 */       return this.key.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  852 */       return String.valueOf(this.key);
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
/*  889 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  894 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public K first() {
/*  899 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  900 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K last() {
/*  905 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  906 */     return this.lastEntry.key;
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
/*      */     ObjectAVLTreeSet.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectAVLTreeSet.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectAVLTreeSet.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  935 */     int index = 0;
/*      */     
/*      */     SetIterator() {
/*  938 */       this.next = ObjectAVLTreeSet.this.firstEntry;
/*      */     }
/*      */     
/*      */     SetIterator(K k) {
/*  942 */       if ((this.next = ObjectAVLTreeSet.this.locateKey(k)) != null) {
/*  943 */         if (ObjectAVLTreeSet.this.compare(this.next.key, k) <= 0)
/*  944 */         { this.prev = this.next;
/*  945 */           this.next = this.next.next(); }
/*  946 */         else { this.prev = this.next.prev(); }
/*      */       
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  952 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  957 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  961 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     ObjectAVLTreeSet.Entry<K> nextEntry() {
/*  965 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  966 */       this.curr = this.prev = this.next;
/*  967 */       this.index++;
/*  968 */       updateNext();
/*  969 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  974 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  979 */       return (previousEntry()).key;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  983 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     ObjectAVLTreeSet.Entry<K> previousEntry() {
/*  987 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  988 */       this.curr = this.next = this.prev;
/*  989 */       this.index--;
/*  990 */       updatePrevious();
/*  991 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  996 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1001 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1006 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1009 */       if (this.curr == this.prev) this.index--; 
/* 1010 */       this.next = this.prev = this.curr;
/* 1011 */       updatePrevious();
/* 1012 */       updateNext();
/* 1013 */       ObjectAVLTreeSet.this.remove(this.curr.key);
/* 1014 */       this.curr = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator() {
/* 1020 */     return new SetIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1025 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1030 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/* 1035 */     return new Subset(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/* 1040 */     return new Subset(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/* 1045 */     return new Subset(from, false, to, false);
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
/* 1078 */       if (!bottom && !top && ObjectAVLTreeSet.this.compare(from, to) > 0) throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")"); 
/* 1079 */       this.from = from;
/* 1080 */       this.bottom = bottom;
/* 1081 */       this.to = to;
/* 1082 */       this.top = top;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1087 */       SubsetIterator i = new SubsetIterator();
/* 1088 */       while (i.hasNext()) {
/* 1089 */         i.next();
/* 1090 */         i.remove();
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
/* 1101 */       return ((this.bottom || ObjectAVLTreeSet.this.compare(k, this.from) >= 0) && (this.top || ObjectAVLTreeSet.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1107 */       return (in((K)k) && ObjectAVLTreeSet.this.contains(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(K k) {
/* 1112 */       if (!in(k)) throw new IllegalArgumentException("Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1113 */       return ObjectAVLTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1119 */       if (!in((K)k)) return false; 
/* 1120 */       return ObjectAVLTreeSet.this.remove(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1125 */       SubsetIterator i = new SubsetIterator();
/* 1126 */       int n = 0;
/* 1127 */       while (i.hasNext()) {
/* 1128 */         n++;
/* 1129 */         i.next();
/*      */       } 
/* 1131 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1136 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1141 */       return ObjectAVLTreeSet.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1146 */       return new SubsetIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1151 */       return new SubsetIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1156 */       if (this.top) return new Subset(this.from, this.bottom, to, false); 
/* 1157 */       return (ObjectAVLTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1162 */       if (this.bottom) return new Subset(from, false, this.to, this.top); 
/* 1163 */       return (ObjectAVLTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1168 */       if (this.top && this.bottom) return new Subset(from, false, to, false); 
/* 1169 */       if (!this.top) to = (ObjectAVLTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1170 */       if (!this.bottom) from = (ObjectAVLTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1171 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1172 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectAVLTreeSet.Entry<K> firstEntry() {
/*      */       ObjectAVLTreeSet.Entry<K> e;
/* 1181 */       if (ObjectAVLTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1185 */       if (this.bottom) { e = ObjectAVLTreeSet.this.firstEntry; }
/*      */       else
/* 1187 */       { e = ObjectAVLTreeSet.this.locateKey(this.from);
/*      */         
/* 1189 */         if (ObjectAVLTreeSet.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1193 */       if (e == null || (!this.top && ObjectAVLTreeSet.this.compare(e.key, this.to) >= 0)) return null; 
/* 1194 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectAVLTreeSet.Entry<K> lastEntry() {
/*      */       ObjectAVLTreeSet.Entry<K> e;
/* 1203 */       if (ObjectAVLTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1207 */       if (this.top) { e = ObjectAVLTreeSet.this.lastEntry; }
/*      */       else
/* 1209 */       { e = ObjectAVLTreeSet.this.locateKey(this.to);
/*      */         
/* 1211 */         if (ObjectAVLTreeSet.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1215 */       if (e == null || (!this.bottom && ObjectAVLTreeSet.this.compare(e.key, this.from) < 0)) return null; 
/* 1216 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1221 */       ObjectAVLTreeSet.Entry<K> e = firstEntry();
/* 1222 */       if (e == null) throw new NoSuchElementException(); 
/* 1223 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1228 */       ObjectAVLTreeSet.Entry<K> e = lastEntry();
/* 1229 */       if (e == null) throw new NoSuchElementException(); 
/* 1230 */       return e.key;
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
/*      */       extends ObjectAVLTreeSet<K>.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1244 */         this.next = ObjectAVLTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubsetIterator(K k) {
/* 1248 */         this();
/* 1249 */         if (this.next != null) {
/* 1250 */           if (!ObjectAVLTreeSet.Subset.this.bottom && ObjectAVLTreeSet.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1251 */           else if (!ObjectAVLTreeSet.Subset.this.top && ObjectAVLTreeSet.this.compare(k, (this.prev = ObjectAVLTreeSet.Subset.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1253 */           { this.next = ObjectAVLTreeSet.this.locateKey(k);
/* 1254 */             if (ObjectAVLTreeSet.this.compare(this.next.key, k) <= 0)
/* 1255 */             { this.prev = this.next;
/* 1256 */               this.next = this.next.next(); }
/* 1257 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1264 */         this.prev = this.prev.prev();
/* 1265 */         if (!ObjectAVLTreeSet.Subset.this.bottom && this.prev != null && ObjectAVLTreeSet.this.compare(this.prev.key, ObjectAVLTreeSet.Subset.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1270 */         this.next = this.next.next();
/* 1271 */         if (!ObjectAVLTreeSet.Subset.this.top && this.next != null && ObjectAVLTreeSet.this.compare(this.next.key, ObjectAVLTreeSet.Subset.this.to) >= 0) this.next = null;
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
/*      */     ObjectAVLTreeSet<K> c;
/*      */     try {
/* 1290 */       c = (ObjectAVLTreeSet<K>)super.clone();
/* 1291 */     } catch (CloneNotSupportedException cantHappen) {
/* 1292 */       throw new InternalError();
/*      */     } 
/* 1294 */     c.allocatePaths();
/* 1295 */     if (this.count != 0) {
/*      */       
/* 1297 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1298 */       Entry<K> p = rp;
/* 1299 */       rp.left(this.tree);
/* 1300 */       Entry<K> q = rq;
/* 1301 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1303 */         if (!p.pred()) {
/* 1304 */           Entry<K> e = p.left.clone();
/* 1305 */           e.pred(q.left);
/* 1306 */           e.succ(q);
/* 1307 */           q.left(e);
/* 1308 */           p = p.left;
/* 1309 */           q = q.left;
/*      */         } else {
/* 1311 */           while (p.succ()) {
/* 1312 */             p = p.right;
/* 1313 */             if (p == null) {
/* 1314 */               q.right = null;
/* 1315 */               c.tree = rq.left;
/* 1316 */               c.firstEntry = c.tree;
/* 1317 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1318 */               c.lastEntry = c.tree;
/* 1319 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1320 */               return c;
/*      */             } 
/* 1322 */             q = q.right;
/*      */           } 
/* 1324 */           p = p.right;
/* 1325 */           q = q.right;
/*      */         } 
/* 1327 */         if (!p.succ()) {
/* 1328 */           Entry<K> e = p.right.clone();
/* 1329 */           e.succ(q.right);
/* 1330 */           e.pred(q);
/* 1331 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1335 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1339 */     int n = this.count;
/* 1340 */     SetIterator i = new SetIterator();
/* 1341 */     s.defaultWriteObject();
/* 1342 */     for (; n-- != 0; s.writeObject(i.next()));
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
/* 1355 */     if (n == 1) {
/* 1356 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1357 */       entry.pred(pred);
/* 1358 */       entry.succ(succ);
/* 1359 */       return entry;
/*      */     } 
/* 1361 */     if (n == 2) {
/*      */ 
/*      */       
/* 1364 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1365 */       entry.right(new Entry<>((K)s.readObject()));
/* 1366 */       entry.right.pred(entry);
/* 1367 */       entry.balance(1);
/* 1368 */       entry.pred(pred);
/* 1369 */       entry.right.succ(succ);
/* 1370 */       return entry;
/*      */     } 
/*      */     
/* 1373 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1374 */     Entry<K> top = new Entry<>();
/* 1375 */     top.left(readTree(s, leftN, pred, top));
/* 1376 */     top.key = (K)s.readObject();
/* 1377 */     top.right(readTree(s, rightN, top, succ));
/* 1378 */     if (n == (n & -n)) top.balance(1); 
/* 1379 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1383 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1386 */     setActualComparator();
/* 1387 */     allocatePaths();
/* 1388 */     if (this.count != 0) {
/* 1389 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1391 */       Entry<K> e = this.tree;
/* 1392 */       for (; e.left() != null; e = e.left());
/* 1393 */       this.firstEntry = e;
/* 1394 */       e = this.tree;
/* 1395 */       for (; e.right() != null; e = e.right());
/* 1396 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectAVLTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */