/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class Byte2IntAVLTreeMap
/*      */   extends AbstractByte2IntSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Byte2IntMap.Entry> entries;
/*      */   protected transient ByteSortedSet keys;
/*      */   protected transient IntCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Byte> storedComparator;
/*      */   protected transient ByteComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Byte2IntAVLTreeMap() {
/*   70 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   77 */     this.tree = null;
/*   78 */     this.count = 0;
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
/*   90 */     this.actualComparator = ByteComparators.asByteComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2IntAVLTreeMap(Comparator<? super Byte> c) {
/*   99 */     this();
/*  100 */     this.storedComparator = c;
/*  101 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2IntAVLTreeMap(Map<? extends Byte, ? extends Integer> m) {
/*  110 */     this();
/*  111 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2IntAVLTreeMap(SortedMap<Byte, Integer> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2IntAVLTreeMap(Byte2IntMap m) {
/*  130 */     this();
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2IntAVLTreeMap(Byte2IntSortedMap m) {
/*  140 */     this(m.comparator());
/*  141 */     putAll(m);
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
/*      */   public Byte2IntAVLTreeMap(byte[] k, int[] v, Comparator<? super Byte> c) {
/*  153 */     this(c);
/*  154 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  155 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2IntAVLTreeMap(byte[] k, int[] v) {
/*  166 */     this(k, v, (Comparator<? super Byte>)null);
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
/*      */   final int compare(byte k1, byte k2) {
/*  192 */     return (this.actualComparator == null) ? Byte.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry findKey(byte k) {
/*  202 */     Entry e = this.tree;
/*      */     int cmp;
/*  204 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  205 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry locateKey(byte k) {
/*  216 */     Entry e = this.tree, last = this.tree;
/*  217 */     int cmp = 0;
/*  218 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  219 */       last = e;
/*  220 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  222 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  232 */     this.dirPath = new boolean[48];
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
/*      */   public int addTo(byte k, int incr) {
/*  249 */     Entry e = add(k);
/*  250 */     int oldValue = e.value;
/*  251 */     e.value += incr;
/*  252 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(byte k, int v) {
/*  257 */     Entry e = add(k);
/*  258 */     int oldValue = e.value;
/*  259 */     e.value = v;
/*  260 */     return oldValue;
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
/*      */   private Entry add(byte k) {
/*  274 */     this.modified = false;
/*  275 */     Entry e = null;
/*  276 */     if (this.tree == null) {
/*  277 */       this.count++;
/*  278 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*  279 */       this.modified = true;
/*      */     } else {
/*  281 */       Entry p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  282 */       int i = 0; while (true) {
/*      */         int cmp;
/*  284 */         if ((cmp = compare(k, p.key)) == 0) {
/*  285 */           return p;
/*      */         }
/*  287 */         if (p.balance() != 0) {
/*  288 */           i = 0;
/*  289 */           z = q;
/*  290 */           y = p;
/*      */         } 
/*  292 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  293 */           if (p.succ()) {
/*  294 */             this.count++;
/*  295 */             e = new Entry(k, this.defRetValue);
/*  296 */             this.modified = true;
/*  297 */             if (p.right == null) this.lastEntry = e; 
/*  298 */             e.left = p;
/*  299 */             e.right = p.right;
/*  300 */             p.right(e);
/*      */             break;
/*      */           } 
/*  303 */           q = p;
/*  304 */           p = p.right; continue;
/*      */         } 
/*  306 */         if (p.pred()) {
/*  307 */           this.count++;
/*  308 */           e = new Entry(k, this.defRetValue);
/*  309 */           this.modified = true;
/*  310 */           if (p.left == null) this.firstEntry = e; 
/*  311 */           e.right = p;
/*  312 */           e.left = p.left;
/*  313 */           p.left(e);
/*      */           break;
/*      */         } 
/*  316 */         q = p;
/*  317 */         p = p.left;
/*      */       } 
/*      */       
/*  320 */       p = y;
/*  321 */       i = 0;
/*  322 */       while (p != e) {
/*  323 */         if (this.dirPath[i]) { p.incBalance(); }
/*  324 */         else { p.decBalance(); }
/*  325 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  327 */       if (y.balance() == -2)
/*  328 */       { Entry x = y.left;
/*  329 */         if (x.balance() == -1) {
/*  330 */           w = x;
/*  331 */           if (x.succ())
/*  332 */           { x.succ(false);
/*  333 */             y.pred(x); }
/*  334 */           else { y.left = x.right; }
/*  335 */            x.right = y;
/*  336 */           x.balance(0);
/*  337 */           y.balance(0);
/*      */         } else {
/*  339 */           assert x.balance() == 1;
/*  340 */           w = x.right;
/*  341 */           x.right = w.left;
/*  342 */           w.left = x;
/*  343 */           y.left = w.right;
/*  344 */           w.right = y;
/*  345 */           if (w.balance() == -1) {
/*  346 */             x.balance(0);
/*  347 */             y.balance(1);
/*  348 */           } else if (w.balance() == 0) {
/*  349 */             x.balance(0);
/*  350 */             y.balance(0);
/*      */           } else {
/*  352 */             x.balance(-1);
/*  353 */             y.balance(0);
/*      */           } 
/*  355 */           w.balance(0);
/*  356 */           if (w.pred()) {
/*  357 */             x.succ(w);
/*  358 */             w.pred(false);
/*      */           } 
/*  360 */           if (w.succ()) {
/*  361 */             y.pred(w);
/*  362 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  365 */       else if (y.balance() == 2)
/*  366 */       { Entry x = y.right;
/*  367 */         if (x.balance() == 1) {
/*  368 */           w = x;
/*  369 */           if (x.pred())
/*  370 */           { x.pred(false);
/*  371 */             y.succ(x); }
/*  372 */           else { y.right = x.left; }
/*  373 */            x.left = y;
/*  374 */           x.balance(0);
/*  375 */           y.balance(0);
/*      */         } else {
/*  377 */           assert x.balance() == -1;
/*  378 */           w = x.left;
/*  379 */           x.left = w.right;
/*  380 */           w.right = x;
/*  381 */           y.right = w.left;
/*  382 */           w.left = y;
/*  383 */           if (w.balance() == 1) {
/*  384 */             x.balance(0);
/*  385 */             y.balance(-1);
/*  386 */           } else if (w.balance() == 0) {
/*  387 */             x.balance(0);
/*  388 */             y.balance(0);
/*      */           } else {
/*  390 */             x.balance(1);
/*  391 */             y.balance(0);
/*      */           } 
/*  393 */           w.balance(0);
/*  394 */           if (w.pred()) {
/*  395 */             y.succ(w);
/*  396 */             w.pred(false);
/*      */           } 
/*  398 */           if (w.succ()) {
/*  399 */             x.pred(w);
/*  400 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  403 */       else { return e; }
/*  404 */        if (z == null) { this.tree = w; }
/*      */       
/*  406 */       else if (z.left == y) { z.left = w; }
/*  407 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  410 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  420 */     if (e == this.tree) return null;
/*      */     
/*  422 */     Entry y = e, x = y;
/*      */     while (true) {
/*  424 */       if (y.succ()) {
/*  425 */         Entry p = y.right;
/*  426 */         if (p == null || p.left != e) {
/*  427 */           for (; !x.pred(); x = x.left);
/*  428 */           p = x.left;
/*      */         } 
/*  430 */         return p;
/*  431 */       }  if (x.pred()) {
/*  432 */         Entry p = x.left;
/*  433 */         if (p == null || p.right != e) {
/*  434 */           for (; !y.succ(); y = y.right);
/*  435 */           p = y.right;
/*      */         } 
/*  437 */         return p;
/*      */       } 
/*  439 */       x = x.left;
/*  440 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int remove(byte k) {
/*  448 */     this.modified = false;
/*  449 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  451 */     Entry p = this.tree, q = null;
/*  452 */     boolean dir = false;
/*  453 */     byte kk = k;
/*      */     int cmp;
/*  455 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  456 */       if (dir = (cmp > 0)) {
/*  457 */         q = p;
/*  458 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  460 */       q = p;
/*  461 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  464 */     if (p.left == null) this.firstEntry = p.next(); 
/*  465 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  466 */     if (p.succ())
/*  467 */     { if (p.pred())
/*  468 */       { if (q != null)
/*  469 */         { if (dir) { q.succ(p.right); }
/*  470 */           else { q.pred(p.left); }  }
/*  471 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  473 */       else { (p.prev()).right = p.right;
/*  474 */         if (q != null)
/*  475 */         { if (dir) { q.right = p.left; }
/*  476 */           else { q.left = p.left; }  }
/*  477 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  480 */     else { Entry r = p.right;
/*  481 */       if (r.pred()) {
/*  482 */         r.left = p.left;
/*  483 */         r.pred(p.pred());
/*  484 */         if (!r.pred()) (r.prev()).right = r; 
/*  485 */         if (q != null)
/*  486 */         { if (dir) { q.right = r; }
/*  487 */           else { q.left = r; }  }
/*  488 */         else { this.tree = r; }
/*  489 */          r.balance(p.balance());
/*  490 */         q = r;
/*  491 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  495 */           s = r.left;
/*  496 */           if (s.pred())
/*  497 */             break;  r = s;
/*      */         } 
/*  499 */         if (s.succ()) { r.pred(s); }
/*  500 */         else { r.left = s.right; }
/*  501 */          s.left = p.left;
/*  502 */         if (!p.pred()) {
/*  503 */           (p.prev()).right = s;
/*  504 */           s.pred(false);
/*      */         } 
/*  506 */         s.right = p.right;
/*  507 */         s.succ(false);
/*  508 */         if (q != null)
/*  509 */         { if (dir) { q.right = s; }
/*  510 */           else { q.left = s; }  }
/*  511 */         else { this.tree = s; }
/*  512 */          s.balance(p.balance());
/*  513 */         q = r;
/*  514 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  518 */     while (q != null) {
/*  519 */       Entry y = q;
/*  520 */       q = parent(y);
/*  521 */       if (!dir) {
/*  522 */         dir = (q != null && q.left != y);
/*  523 */         y.incBalance();
/*  524 */         if (y.balance() == 1)
/*  525 */           break;  if (y.balance() == 2) {
/*  526 */           Entry x = y.right;
/*  527 */           assert x != null;
/*  528 */           if (x.balance() == -1) {
/*      */             
/*  530 */             assert x.balance() == -1;
/*  531 */             Entry w = x.left;
/*  532 */             x.left = w.right;
/*  533 */             w.right = x;
/*  534 */             y.right = w.left;
/*  535 */             w.left = y;
/*  536 */             if (w.balance() == 1) {
/*  537 */               x.balance(0);
/*  538 */               y.balance(-1);
/*  539 */             } else if (w.balance() == 0) {
/*  540 */               x.balance(0);
/*  541 */               y.balance(0);
/*      */             } else {
/*  543 */               assert w.balance() == -1;
/*  544 */               x.balance(1);
/*  545 */               y.balance(0);
/*      */             } 
/*  547 */             w.balance(0);
/*  548 */             if (w.pred()) {
/*  549 */               y.succ(w);
/*  550 */               w.pred(false);
/*      */             } 
/*  552 */             if (w.succ()) {
/*  553 */               x.pred(w);
/*  554 */               w.succ(false);
/*      */             } 
/*  556 */             if (q != null) {
/*  557 */               if (dir) { q.right = w; continue; }
/*  558 */                q.left = w; continue;
/*  559 */             }  this.tree = w; continue;
/*      */           } 
/*  561 */           if (q != null)
/*  562 */           { if (dir) { q.right = x; }
/*  563 */             else { q.left = x; }  }
/*  564 */           else { this.tree = x; }
/*  565 */            if (x.balance() == 0) {
/*  566 */             y.right = x.left;
/*  567 */             x.left = y;
/*  568 */             x.balance(-1);
/*  569 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  572 */           assert x.balance() == 1;
/*  573 */           if (x.pred())
/*  574 */           { y.succ(true);
/*  575 */             x.pred(false); }
/*  576 */           else { y.right = x.left; }
/*  577 */            x.left = y;
/*  578 */           y.balance(0);
/*  579 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  583 */       dir = (q != null && q.left != y);
/*  584 */       y.decBalance();
/*  585 */       if (y.balance() == -1)
/*  586 */         break;  if (y.balance() == -2) {
/*  587 */         Entry x = y.left;
/*  588 */         assert x != null;
/*  589 */         if (x.balance() == 1) {
/*      */           
/*  591 */           assert x.balance() == 1;
/*  592 */           Entry w = x.right;
/*  593 */           x.right = w.left;
/*  594 */           w.left = x;
/*  595 */           y.left = w.right;
/*  596 */           w.right = y;
/*  597 */           if (w.balance() == -1) {
/*  598 */             x.balance(0);
/*  599 */             y.balance(1);
/*  600 */           } else if (w.balance() == 0) {
/*  601 */             x.balance(0);
/*  602 */             y.balance(0);
/*      */           } else {
/*  604 */             assert w.balance() == 1;
/*  605 */             x.balance(-1);
/*  606 */             y.balance(0);
/*      */           } 
/*  608 */           w.balance(0);
/*  609 */           if (w.pred()) {
/*  610 */             x.succ(w);
/*  611 */             w.pred(false);
/*      */           } 
/*  613 */           if (w.succ()) {
/*  614 */             y.pred(w);
/*  615 */             w.succ(false);
/*      */           } 
/*  617 */           if (q != null) {
/*  618 */             if (dir) { q.right = w; continue; }
/*  619 */              q.left = w; continue;
/*  620 */           }  this.tree = w; continue;
/*      */         } 
/*  622 */         if (q != null)
/*  623 */         { if (dir) { q.right = x; }
/*  624 */           else { q.left = x; }  }
/*  625 */         else { this.tree = x; }
/*  626 */          if (x.balance() == 0) {
/*  627 */           y.left = x.right;
/*  628 */           x.right = y;
/*  629 */           x.balance(1);
/*  630 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  633 */         assert x.balance() == -1;
/*  634 */         if (x.succ())
/*  635 */         { y.pred(true);
/*  636 */           x.succ(false); }
/*  637 */         else { y.left = x.right; }
/*  638 */          x.right = y;
/*  639 */         y.balance(0);
/*  640 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  645 */     this.modified = true;
/*  646 */     this.count--;
/*  647 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(int v) {
/*  652 */     ValueIterator i = new ValueIterator();
/*      */     
/*  654 */     int j = this.count;
/*  655 */     while (j-- != 0) {
/*  656 */       int ev = i.nextInt();
/*  657 */       if (ev == v) return true; 
/*      */     } 
/*  659 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  664 */     this.count = 0;
/*  665 */     this.tree = null;
/*  666 */     this.entries = null;
/*  667 */     this.values = null;
/*  668 */     this.keys = null;
/*  669 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractByte2IntMap.BasicEntry
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
/*      */     Entry left;
/*      */ 
/*      */     
/*      */     Entry right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  698 */       super((byte)0, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(byte k, int v) {
/*  708 */       super(k, v);
/*  709 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  718 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  727 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  736 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  745 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  754 */       if (pred) { this.info |= 0x40000000; }
/*  755 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  764 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  765 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  774 */       this.info |= 0x40000000;
/*  775 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  784 */       this.info |= Integer.MIN_VALUE;
/*  785 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  794 */       this.info &= 0xBFFFFFFF;
/*  795 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  804 */       this.info &= Integer.MAX_VALUE;
/*  805 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  814 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  823 */       this.info &= 0xFFFFFF00;
/*  824 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  829 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  834 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  843 */       Entry next = this.right;
/*  844 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  845 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  854 */       Entry prev = this.left;
/*  855 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  856 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public int setValue(int value) {
/*  861 */       int oldValue = this.value;
/*  862 */       this.value = value;
/*  863 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  871 */         c = (Entry)super.clone();
/*  872 */       } catch (CloneNotSupportedException cantHappen) {
/*  873 */         throw new InternalError();
/*      */       } 
/*  875 */       c.key = this.key;
/*  876 */       c.value = this.value;
/*  877 */       c.info = this.info;
/*  878 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  884 */       if (!(o instanceof Map.Entry)) return false; 
/*  885 */       Map.Entry<Byte, Integer> e = (Map.Entry<Byte, Integer>)o;
/*  886 */       return (this.key == ((Byte)e.getKey()).byteValue() && this.value == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  891 */       return this.key ^ this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  896 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(byte k) {
/*  934 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  939 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  944 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int get(byte k) {
/*  949 */     Entry e = findKey(k);
/*  950 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte firstByteKey() {
/*  955 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  956 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte lastByteKey() {
/*  961 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  962 */     return this.lastEntry.key;
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
/*      */     Byte2IntAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2IntAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2IntAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  994 */       this.next = Byte2IntAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(byte k) {
/*  998 */       if ((this.next = Byte2IntAVLTreeMap.this.locateKey(k)) != null)
/*  999 */         if (Byte2IntAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1000 */         { this.prev = this.next;
/* 1001 */           this.next = this.next.next(); }
/* 1002 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1007 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1011 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/* 1015 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Byte2IntAVLTreeMap.Entry nextEntry() {
/* 1019 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1020 */       this.curr = this.prev = this.next;
/* 1021 */       this.index++;
/* 1022 */       updateNext();
/* 1023 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1027 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Byte2IntAVLTreeMap.Entry previousEntry() {
/* 1031 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1032 */       this.curr = this.next = this.prev;
/* 1033 */       this.index--;
/* 1034 */       updatePrevious();
/* 1035 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1039 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1043 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1047 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1050 */       if (this.curr == this.prev) this.index--; 
/* 1051 */       this.next = this.prev = this.curr;
/* 1052 */       updatePrevious();
/* 1053 */       updateNext();
/* 1054 */       Byte2IntAVLTreeMap.this.remove(this.curr.key);
/* 1055 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1059 */       int i = n;
/* 1060 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1061 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1065 */       int i = n;
/* 1066 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1067 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Byte2IntMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(byte k) {
/* 1082 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2IntMap.Entry next() {
/* 1087 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2IntMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Byte2IntMap.Entry ok) {
/* 1097 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Byte2IntMap.Entry ok) {
/* 1102 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet() {
/* 1109 */     if (this.entries == null) this.entries = (ObjectSortedSet<Byte2IntMap.Entry>)new AbstractObjectSortedSet<Byte2IntMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Byte2IntMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Byte2IntMap.Entry> comparator() {
/* 1114 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2IntMap.Entry> iterator() {
/* 1119 */             return (ObjectBidirectionalIterator<Byte2IntMap.Entry>)new Byte2IntAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2IntMap.Entry> iterator(Byte2IntMap.Entry from) {
/* 1124 */             return (ObjectBidirectionalIterator<Byte2IntMap.Entry>)new Byte2IntAVLTreeMap.EntryIterator(from.getByteKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1130 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1131 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1132 */             if (e.getKey() == null) return false; 
/* 1133 */             if (!(e.getKey() instanceof Byte)) return false; 
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1135 */             Byte2IntAVLTreeMap.Entry f = Byte2IntAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1136 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1142 */             if (!(o instanceof Map.Entry)) return false; 
/* 1143 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1144 */             if (e.getKey() == null) return false; 
/* 1145 */             if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1146 */             if (!(e.getValue() instanceof Integer)) return false; 
/* 1147 */             Byte2IntAVLTreeMap.Entry f = Byte2IntAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1148 */             if (f == null || f.getIntValue() != ((Integer)e.getValue()).intValue()) return false; 
/* 1149 */             Byte2IntAVLTreeMap.this.remove(f.key);
/* 1150 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1155 */             return Byte2IntAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1160 */             Byte2IntAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Byte2IntMap.Entry first() {
/* 1165 */             return Byte2IntAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Byte2IntMap.Entry last() {
/* 1170 */             return Byte2IntAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2IntMap.Entry> subSet(Byte2IntMap.Entry from, Byte2IntMap.Entry to) {
/* 1175 */             return Byte2IntAVLTreeMap.this.subMap(from.getByteKey(), to.getByteKey()).byte2IntEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2IntMap.Entry> headSet(Byte2IntMap.Entry to) {
/* 1180 */             return Byte2IntAVLTreeMap.this.headMap(to.getByteKey()).byte2IntEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2IntMap.Entry> tailSet(Byte2IntMap.Entry from) {
/* 1185 */             return Byte2IntAVLTreeMap.this.tailMap(from.getByteKey()).byte2IntEntrySet();
/*      */           }
/*      */         }; 
/* 1188 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ByteListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(byte k) {
/* 1204 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1209 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/* 1214 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractByte2IntSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteBidirectionalIterator iterator() {
/* 1222 */       return new Byte2IntAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBidirectionalIterator iterator(byte from) {
/* 1227 */       return new Byte2IntAVLTreeMap.KeyIterator(from);
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
/*      */   public ByteSortedSet keySet() {
/* 1242 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1243 */     return this.keys;
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
/* 1257 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1262 */       return (previousEntry()).value;
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
/* 1277 */     if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1280 */             return (IntIterator)new Byte2IntAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(int k) {
/* 1285 */             return Byte2IntAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1290 */             return Byte2IntAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1295 */             Byte2IntAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1298 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteComparator comparator() {
/* 1303 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2IntSortedMap headMap(byte to) {
/* 1308 */     return new Submap((byte)0, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2IntSortedMap tailMap(byte from) {
/* 1313 */     return new Submap(from, false, (byte)0, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2IntSortedMap subMap(byte from, byte to) {
/* 1318 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractByte2IntSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     byte from;
/*      */ 
/*      */ 
/*      */     
/*      */     byte to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Byte2IntMap.Entry> entries;
/*      */ 
/*      */     
/*      */     protected transient ByteSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(byte from, boolean bottom, byte to, boolean top) {
/* 1357 */       if (!bottom && !top && Byte2IntAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1358 */       this.from = from;
/* 1359 */       this.bottom = bottom;
/* 1360 */       this.to = to;
/* 1361 */       this.top = top;
/* 1362 */       this.defRetValue = Byte2IntAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1367 */       SubmapIterator i = new SubmapIterator();
/* 1368 */       while (i.hasNext()) {
/* 1369 */         i.nextEntry();
/* 1370 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(byte k) {
/* 1381 */       return ((this.bottom || Byte2IntAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Byte2IntAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet() {
/* 1386 */       if (this.entries == null) this.entries = (ObjectSortedSet<Byte2IntMap.Entry>)new AbstractObjectSortedSet<Byte2IntMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Byte2IntMap.Entry> iterator() {
/* 1389 */               return (ObjectBidirectionalIterator<Byte2IntMap.Entry>)new Byte2IntAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Byte2IntMap.Entry> iterator(Byte2IntMap.Entry from) {
/* 1394 */               return (ObjectBidirectionalIterator<Byte2IntMap.Entry>)new Byte2IntAVLTreeMap.Submap.SubmapEntryIterator(from.getByteKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Byte2IntMap.Entry> comparator() {
/* 1399 */               return Byte2IntAVLTreeMap.this.byte2IntEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1405 */               if (!(o instanceof Map.Entry)) return false; 
/* 1406 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1407 */               if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1408 */               if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1409 */               Byte2IntAVLTreeMap.Entry f = Byte2IntAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1410 */               return (f != null && Byte2IntAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1416 */               if (!(o instanceof Map.Entry)) return false; 
/* 1417 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1418 */               if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1419 */               if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1420 */               Byte2IntAVLTreeMap.Entry f = Byte2IntAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1421 */               if (f != null && Byte2IntAVLTreeMap.Submap.this.in(f.key)) Byte2IntAVLTreeMap.Submap.this.remove(f.key); 
/* 1422 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1427 */               int c = 0;
/* 1428 */               for (ObjectBidirectionalIterator<Byte2IntMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1429 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1434 */               return !(new Byte2IntAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1439 */               Byte2IntAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Byte2IntMap.Entry first() {
/* 1444 */               return Byte2IntAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Byte2IntMap.Entry last() {
/* 1449 */               return Byte2IntAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2IntMap.Entry> subSet(Byte2IntMap.Entry from, Byte2IntMap.Entry to) {
/* 1454 */               return Byte2IntAVLTreeMap.Submap.this.subMap(from.getByteKey(), to.getByteKey()).byte2IntEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2IntMap.Entry> headSet(Byte2IntMap.Entry to) {
/* 1459 */               return Byte2IntAVLTreeMap.Submap.this.headMap(to.getByteKey()).byte2IntEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2IntMap.Entry> tailSet(Byte2IntMap.Entry from) {
/* 1464 */               return Byte2IntAVLTreeMap.Submap.this.tailMap(from.getByteKey()).byte2IntEntrySet();
/*      */             }
/*      */           }; 
/* 1467 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractByte2IntSortedMap.KeySet { private KeySet() {}
/*      */       
/*      */       public ByteBidirectionalIterator iterator() {
/* 1473 */         return new Byte2IntAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ByteBidirectionalIterator iterator(byte from) {
/* 1478 */         return new Byte2IntAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteSortedSet keySet() {
/* 1484 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1485 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntCollection values() {
/* 1490 */       if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */           {
/*      */             public IntIterator iterator() {
/* 1493 */               return (IntIterator)new Byte2IntAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(int k) {
/* 1498 */               return Byte2IntAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1503 */               return Byte2IntAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1508 */               Byte2IntAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1511 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(byte k) {
/* 1518 */       return (in(k) && Byte2IntAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(int v) {
/* 1523 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1525 */       while (i.hasNext()) {
/* 1526 */         int ev = (i.nextEntry()).value;
/* 1527 */         if (ev == v) return true; 
/*      */       } 
/* 1529 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int get(byte k) {
/* 1536 */       byte kk = k; Byte2IntAVLTreeMap.Entry e;
/* 1537 */       return (in(kk) && (e = Byte2IntAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int put(byte k, int v) {
/* 1542 */       Byte2IntAVLTreeMap.this.modified = false;
/* 1543 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1544 */       int oldValue = Byte2IntAVLTreeMap.this.put(k, v);
/* 1545 */       return Byte2IntAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int remove(byte k) {
/* 1551 */       Byte2IntAVLTreeMap.this.modified = false;
/* 1552 */       if (!in(k)) return this.defRetValue; 
/* 1553 */       int oldValue = Byte2IntAVLTreeMap.this.remove(k);
/* 1554 */       return Byte2IntAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1559 */       SubmapIterator i = new SubmapIterator();
/* 1560 */       int n = 0;
/* 1561 */       while (i.hasNext()) {
/* 1562 */         n++;
/* 1563 */         i.nextEntry();
/*      */       } 
/* 1565 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1570 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator comparator() {
/* 1575 */       return Byte2IntAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2IntSortedMap headMap(byte to) {
/* 1580 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1581 */       return (Byte2IntAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2IntSortedMap tailMap(byte from) {
/* 1586 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1587 */       return (Byte2IntAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2IntSortedMap subMap(byte from, byte to) {
/* 1592 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1593 */       if (!this.top) to = (Byte2IntAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1594 */       if (!this.bottom) from = (Byte2IntAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1595 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1596 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2IntAVLTreeMap.Entry firstEntry() {
/*      */       Byte2IntAVLTreeMap.Entry e;
/* 1605 */       if (Byte2IntAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1609 */       if (this.bottom) { e = Byte2IntAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1611 */       { e = Byte2IntAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1613 */         if (Byte2IntAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1617 */       if (e == null || (!this.top && Byte2IntAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1618 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2IntAVLTreeMap.Entry lastEntry() {
/*      */       Byte2IntAVLTreeMap.Entry e;
/* 1627 */       if (Byte2IntAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1631 */       if (this.top) { e = Byte2IntAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1633 */       { e = Byte2IntAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1635 */         if (Byte2IntAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1639 */       if (e == null || (!this.bottom && Byte2IntAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1640 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte firstByteKey() {
/* 1645 */       Byte2IntAVLTreeMap.Entry e = firstEntry();
/* 1646 */       if (e == null) throw new NoSuchElementException(); 
/* 1647 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte lastByteKey() {
/* 1652 */       Byte2IntAVLTreeMap.Entry e = lastEntry();
/* 1653 */       if (e == null) throw new NoSuchElementException(); 
/* 1654 */       return e.key;
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
/*      */       extends Byte2IntAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1668 */         this.next = Byte2IntAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(byte k) {
/* 1672 */         this();
/* 1673 */         if (this.next != null) {
/* 1674 */           if (!Byte2IntAVLTreeMap.Submap.this.bottom && Byte2IntAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1675 */           else if (!Byte2IntAVLTreeMap.Submap.this.top && Byte2IntAVLTreeMap.this.compare(k, (this.prev = Byte2IntAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1677 */           { this.next = Byte2IntAVLTreeMap.this.locateKey(k);
/* 1678 */             if (Byte2IntAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1679 */             { this.prev = this.next;
/* 1680 */               this.next = this.next.next(); }
/* 1681 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1688 */         this.prev = this.prev.prev();
/* 1689 */         if (!Byte2IntAVLTreeMap.Submap.this.bottom && this.prev != null && Byte2IntAVLTreeMap.this.compare(this.prev.key, Byte2IntAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1694 */         this.next = this.next.next();
/* 1695 */         if (!Byte2IntAVLTreeMap.Submap.this.top && this.next != null && Byte2IntAVLTreeMap.this.compare(this.next.key, Byte2IntAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Byte2IntMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(byte k) {
/* 1704 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Byte2IntMap.Entry next() {
/* 1709 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Byte2IntMap.Entry previous() {
/* 1714 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ByteListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(byte from) {
/* 1733 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public byte nextByte() {
/* 1738 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public byte previousByte() {
/* 1743 */         return (previousEntry()).key;
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
/* 1759 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public int previousInt() {
/* 1764 */         return (previousEntry()).value;
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
/*      */   public Byte2IntAVLTreeMap clone() {
/*      */     Byte2IntAVLTreeMap c;
/*      */     try {
/* 1783 */       c = (Byte2IntAVLTreeMap)super.clone();
/* 1784 */     } catch (CloneNotSupportedException cantHappen) {
/* 1785 */       throw new InternalError();
/*      */     } 
/* 1787 */     c.keys = null;
/* 1788 */     c.values = null;
/* 1789 */     c.entries = null;
/* 1790 */     c.allocatePaths();
/* 1791 */     if (this.count != 0) {
/*      */       
/* 1793 */       Entry rp = new Entry(), rq = new Entry();
/* 1794 */       Entry p = rp;
/* 1795 */       rp.left(this.tree);
/* 1796 */       Entry q = rq;
/* 1797 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1799 */         if (!p.pred()) {
/* 1800 */           Entry e = p.left.clone();
/* 1801 */           e.pred(q.left);
/* 1802 */           e.succ(q);
/* 1803 */           q.left(e);
/* 1804 */           p = p.left;
/* 1805 */           q = q.left;
/*      */         } else {
/* 1807 */           while (p.succ()) {
/* 1808 */             p = p.right;
/* 1809 */             if (p == null) {
/* 1810 */               q.right = null;
/* 1811 */               c.tree = rq.left;
/* 1812 */               c.firstEntry = c.tree;
/* 1813 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1814 */               c.lastEntry = c.tree;
/* 1815 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1816 */               return c;
/*      */             } 
/* 1818 */             q = q.right;
/*      */           } 
/* 1820 */           p = p.right;
/* 1821 */           q = q.right;
/*      */         } 
/* 1823 */         if (!p.succ()) {
/* 1824 */           Entry e = p.right.clone();
/* 1825 */           e.succ(q.right);
/* 1826 */           e.pred(q);
/* 1827 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1831 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1835 */     int n = this.count;
/* 1836 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1838 */     s.defaultWriteObject();
/* 1839 */     while (n-- != 0) {
/* 1840 */       Entry e = i.nextEntry();
/* 1841 */       s.writeByte(e.key);
/* 1842 */       s.writeInt(e.value);
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1856 */     if (n == 1) {
/* 1857 */       Entry entry = new Entry(s.readByte(), s.readInt());
/* 1858 */       entry.pred(pred);
/* 1859 */       entry.succ(succ);
/* 1860 */       return entry;
/*      */     } 
/* 1862 */     if (n == 2) {
/*      */ 
/*      */       
/* 1865 */       Entry entry = new Entry(s.readByte(), s.readInt());
/* 1866 */       entry.right(new Entry(s.readByte(), s.readInt()));
/* 1867 */       entry.right.pred(entry);
/* 1868 */       entry.balance(1);
/* 1869 */       entry.pred(pred);
/* 1870 */       entry.right.succ(succ);
/* 1871 */       return entry;
/*      */     } 
/*      */     
/* 1874 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1875 */     Entry top = new Entry();
/* 1876 */     top.left(readTree(s, leftN, pred, top));
/* 1877 */     top.key = s.readByte();
/* 1878 */     top.value = s.readInt();
/* 1879 */     top.right(readTree(s, rightN, top, succ));
/* 1880 */     if (n == (n & -n)) top.balance(1); 
/* 1881 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1885 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1888 */     setActualComparator();
/* 1889 */     allocatePaths();
/* 1890 */     if (this.count != 0) {
/* 1891 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1893 */       Entry e = this.tree;
/* 1894 */       for (; e.left() != null; e = e.left());
/* 1895 */       this.firstEntry = e;
/* 1896 */       e = this.tree;
/* 1897 */       for (; e.right() != null; e = e.right());
/* 1898 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2IntAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */