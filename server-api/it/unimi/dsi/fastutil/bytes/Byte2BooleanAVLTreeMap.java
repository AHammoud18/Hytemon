/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Byte2BooleanAVLTreeMap
/*      */   extends AbstractByte2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Byte2BooleanMap.Entry> entries;
/*      */   protected transient ByteSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Byte> storedComparator;
/*      */   protected transient ByteComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Byte2BooleanAVLTreeMap() {
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
/*      */   public Byte2BooleanAVLTreeMap(Comparator<? super Byte> c) {
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
/*      */   public Byte2BooleanAVLTreeMap(Map<? extends Byte, ? extends Boolean> m) {
/*  110 */     this();
/*  111 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanAVLTreeMap(SortedMap<Byte, Boolean> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanAVLTreeMap(Byte2BooleanMap m) {
/*  130 */     this();
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanAVLTreeMap(Byte2BooleanSortedMap m) {
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
/*      */   public Byte2BooleanAVLTreeMap(byte[] k, boolean[] v, Comparator<? super Byte> c) {
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
/*      */   public Byte2BooleanAVLTreeMap(byte[] k, boolean[] v) {
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
/*      */   public boolean put(byte k, boolean v) {
/*  237 */     Entry e = add(k);
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
/*      */ 
/*      */   
/*      */   private Entry add(byte k) {
/*  254 */     this.modified = false;
/*  255 */     Entry e = null;
/*  256 */     if (this.tree == null) {
/*  257 */       this.count++;
/*  258 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*  259 */       this.modified = true;
/*      */     } else {
/*  261 */       Entry p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  262 */       int i = 0; while (true) {
/*      */         int cmp;
/*  264 */         if ((cmp = compare(k, p.key)) == 0) {
/*  265 */           return p;
/*      */         }
/*  267 */         if (p.balance() != 0) {
/*  268 */           i = 0;
/*  269 */           z = q;
/*  270 */           y = p;
/*      */         } 
/*  272 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  273 */           if (p.succ()) {
/*  274 */             this.count++;
/*  275 */             e = new Entry(k, this.defRetValue);
/*  276 */             this.modified = true;
/*  277 */             if (p.right == null) this.lastEntry = e; 
/*  278 */             e.left = p;
/*  279 */             e.right = p.right;
/*  280 */             p.right(e);
/*      */             break;
/*      */           } 
/*  283 */           q = p;
/*  284 */           p = p.right; continue;
/*      */         } 
/*  286 */         if (p.pred()) {
/*  287 */           this.count++;
/*  288 */           e = new Entry(k, this.defRetValue);
/*  289 */           this.modified = true;
/*  290 */           if (p.left == null) this.firstEntry = e; 
/*  291 */           e.right = p;
/*  292 */           e.left = p.left;
/*  293 */           p.left(e);
/*      */           break;
/*      */         } 
/*  296 */         q = p;
/*  297 */         p = p.left;
/*      */       } 
/*      */       
/*  300 */       p = y;
/*  301 */       i = 0;
/*  302 */       while (p != e) {
/*  303 */         if (this.dirPath[i]) { p.incBalance(); }
/*  304 */         else { p.decBalance(); }
/*  305 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  307 */       if (y.balance() == -2)
/*  308 */       { Entry x = y.left;
/*  309 */         if (x.balance() == -1) {
/*  310 */           w = x;
/*  311 */           if (x.succ())
/*  312 */           { x.succ(false);
/*  313 */             y.pred(x); }
/*  314 */           else { y.left = x.right; }
/*  315 */            x.right = y;
/*  316 */           x.balance(0);
/*  317 */           y.balance(0);
/*      */         } else {
/*  319 */           assert x.balance() == 1;
/*  320 */           w = x.right;
/*  321 */           x.right = w.left;
/*  322 */           w.left = x;
/*  323 */           y.left = w.right;
/*  324 */           w.right = y;
/*  325 */           if (w.balance() == -1) {
/*  326 */             x.balance(0);
/*  327 */             y.balance(1);
/*  328 */           } else if (w.balance() == 0) {
/*  329 */             x.balance(0);
/*  330 */             y.balance(0);
/*      */           } else {
/*  332 */             x.balance(-1);
/*  333 */             y.balance(0);
/*      */           } 
/*  335 */           w.balance(0);
/*  336 */           if (w.pred()) {
/*  337 */             x.succ(w);
/*  338 */             w.pred(false);
/*      */           } 
/*  340 */           if (w.succ()) {
/*  341 */             y.pred(w);
/*  342 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  345 */       else if (y.balance() == 2)
/*  346 */       { Entry x = y.right;
/*  347 */         if (x.balance() == 1) {
/*  348 */           w = x;
/*  349 */           if (x.pred())
/*  350 */           { x.pred(false);
/*  351 */             y.succ(x); }
/*  352 */           else { y.right = x.left; }
/*  353 */            x.left = y;
/*  354 */           x.balance(0);
/*  355 */           y.balance(0);
/*      */         } else {
/*  357 */           assert x.balance() == -1;
/*  358 */           w = x.left;
/*  359 */           x.left = w.right;
/*  360 */           w.right = x;
/*  361 */           y.right = w.left;
/*  362 */           w.left = y;
/*  363 */           if (w.balance() == 1) {
/*  364 */             x.balance(0);
/*  365 */             y.balance(-1);
/*  366 */           } else if (w.balance() == 0) {
/*  367 */             x.balance(0);
/*  368 */             y.balance(0);
/*      */           } else {
/*  370 */             x.balance(1);
/*  371 */             y.balance(0);
/*      */           } 
/*  373 */           w.balance(0);
/*  374 */           if (w.pred()) {
/*  375 */             y.succ(w);
/*  376 */             w.pred(false);
/*      */           } 
/*  378 */           if (w.succ()) {
/*  379 */             x.pred(w);
/*  380 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  383 */       else { return e; }
/*  384 */        if (z == null) { this.tree = w; }
/*      */       
/*  386 */       else if (z.left == y) { z.left = w; }
/*  387 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  390 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  400 */     if (e == this.tree) return null;
/*      */     
/*  402 */     Entry y = e, x = y;
/*      */     while (true) {
/*  404 */       if (y.succ()) {
/*  405 */         Entry p = y.right;
/*  406 */         if (p == null || p.left != e) {
/*  407 */           for (; !x.pred(); x = x.left);
/*  408 */           p = x.left;
/*      */         } 
/*  410 */         return p;
/*  411 */       }  if (x.pred()) {
/*  412 */         Entry p = x.left;
/*  413 */         if (p == null || p.right != e) {
/*  414 */           for (; !y.succ(); y = y.right);
/*  415 */           p = y.right;
/*      */         } 
/*  417 */         return p;
/*      */       } 
/*  419 */       x = x.left;
/*  420 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k) {
/*  428 */     this.modified = false;
/*  429 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  431 */     Entry p = this.tree, q = null;
/*  432 */     boolean dir = false;
/*  433 */     byte kk = k;
/*      */     int cmp;
/*  435 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  436 */       if (dir = (cmp > 0)) {
/*  437 */         q = p;
/*  438 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  440 */       q = p;
/*  441 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  444 */     if (p.left == null) this.firstEntry = p.next(); 
/*  445 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  446 */     if (p.succ())
/*  447 */     { if (p.pred())
/*  448 */       { if (q != null)
/*  449 */         { if (dir) { q.succ(p.right); }
/*  450 */           else { q.pred(p.left); }  }
/*  451 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  453 */       else { (p.prev()).right = p.right;
/*  454 */         if (q != null)
/*  455 */         { if (dir) { q.right = p.left; }
/*  456 */           else { q.left = p.left; }  }
/*  457 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  460 */     else { Entry r = p.right;
/*  461 */       if (r.pred()) {
/*  462 */         r.left = p.left;
/*  463 */         r.pred(p.pred());
/*  464 */         if (!r.pred()) (r.prev()).right = r; 
/*  465 */         if (q != null)
/*  466 */         { if (dir) { q.right = r; }
/*  467 */           else { q.left = r; }  }
/*  468 */         else { this.tree = r; }
/*  469 */          r.balance(p.balance());
/*  470 */         q = r;
/*  471 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  475 */           s = r.left;
/*  476 */           if (s.pred())
/*  477 */             break;  r = s;
/*      */         } 
/*  479 */         if (s.succ()) { r.pred(s); }
/*  480 */         else { r.left = s.right; }
/*  481 */          s.left = p.left;
/*  482 */         if (!p.pred()) {
/*  483 */           (p.prev()).right = s;
/*  484 */           s.pred(false);
/*      */         } 
/*  486 */         s.right = p.right;
/*  487 */         s.succ(false);
/*  488 */         if (q != null)
/*  489 */         { if (dir) { q.right = s; }
/*  490 */           else { q.left = s; }  }
/*  491 */         else { this.tree = s; }
/*  492 */          s.balance(p.balance());
/*  493 */         q = r;
/*  494 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  498 */     while (q != null) {
/*  499 */       Entry y = q;
/*  500 */       q = parent(y);
/*  501 */       if (!dir) {
/*  502 */         dir = (q != null && q.left != y);
/*  503 */         y.incBalance();
/*  504 */         if (y.balance() == 1)
/*  505 */           break;  if (y.balance() == 2) {
/*  506 */           Entry x = y.right;
/*  507 */           assert x != null;
/*  508 */           if (x.balance() == -1) {
/*      */             
/*  510 */             assert x.balance() == -1;
/*  511 */             Entry w = x.left;
/*  512 */             x.left = w.right;
/*  513 */             w.right = x;
/*  514 */             y.right = w.left;
/*  515 */             w.left = y;
/*  516 */             if (w.balance() == 1) {
/*  517 */               x.balance(0);
/*  518 */               y.balance(-1);
/*  519 */             } else if (w.balance() == 0) {
/*  520 */               x.balance(0);
/*  521 */               y.balance(0);
/*      */             } else {
/*  523 */               assert w.balance() == -1;
/*  524 */               x.balance(1);
/*  525 */               y.balance(0);
/*      */             } 
/*  527 */             w.balance(0);
/*  528 */             if (w.pred()) {
/*  529 */               y.succ(w);
/*  530 */               w.pred(false);
/*      */             } 
/*  532 */             if (w.succ()) {
/*  533 */               x.pred(w);
/*  534 */               w.succ(false);
/*      */             } 
/*  536 */             if (q != null) {
/*  537 */               if (dir) { q.right = w; continue; }
/*  538 */                q.left = w; continue;
/*  539 */             }  this.tree = w; continue;
/*      */           } 
/*  541 */           if (q != null)
/*  542 */           { if (dir) { q.right = x; }
/*  543 */             else { q.left = x; }  }
/*  544 */           else { this.tree = x; }
/*  545 */            if (x.balance() == 0) {
/*  546 */             y.right = x.left;
/*  547 */             x.left = y;
/*  548 */             x.balance(-1);
/*  549 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  552 */           assert x.balance() == 1;
/*  553 */           if (x.pred())
/*  554 */           { y.succ(true);
/*  555 */             x.pred(false); }
/*  556 */           else { y.right = x.left; }
/*  557 */            x.left = y;
/*  558 */           y.balance(0);
/*  559 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  563 */       dir = (q != null && q.left != y);
/*  564 */       y.decBalance();
/*  565 */       if (y.balance() == -1)
/*  566 */         break;  if (y.balance() == -2) {
/*  567 */         Entry x = y.left;
/*  568 */         assert x != null;
/*  569 */         if (x.balance() == 1) {
/*      */           
/*  571 */           assert x.balance() == 1;
/*  572 */           Entry w = x.right;
/*  573 */           x.right = w.left;
/*  574 */           w.left = x;
/*  575 */           y.left = w.right;
/*  576 */           w.right = y;
/*  577 */           if (w.balance() == -1) {
/*  578 */             x.balance(0);
/*  579 */             y.balance(1);
/*  580 */           } else if (w.balance() == 0) {
/*  581 */             x.balance(0);
/*  582 */             y.balance(0);
/*      */           } else {
/*  584 */             assert w.balance() == 1;
/*  585 */             x.balance(-1);
/*  586 */             y.balance(0);
/*      */           } 
/*  588 */           w.balance(0);
/*  589 */           if (w.pred()) {
/*  590 */             x.succ(w);
/*  591 */             w.pred(false);
/*      */           } 
/*  593 */           if (w.succ()) {
/*  594 */             y.pred(w);
/*  595 */             w.succ(false);
/*      */           } 
/*  597 */           if (q != null) {
/*  598 */             if (dir) { q.right = w; continue; }
/*  599 */              q.left = w; continue;
/*  600 */           }  this.tree = w; continue;
/*      */         } 
/*  602 */         if (q != null)
/*  603 */         { if (dir) { q.right = x; }
/*  604 */           else { q.left = x; }  }
/*  605 */         else { this.tree = x; }
/*  606 */          if (x.balance() == 0) {
/*  607 */           y.left = x.right;
/*  608 */           x.right = y;
/*  609 */           x.balance(1);
/*  610 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  613 */         assert x.balance() == -1;
/*  614 */         if (x.succ())
/*  615 */         { y.pred(true);
/*  616 */           x.succ(false); }
/*  617 */         else { y.left = x.right; }
/*  618 */          x.right = y;
/*  619 */         y.balance(0);
/*  620 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  625 */     this.modified = true;
/*  626 */     this.count--;
/*  627 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  632 */     ValueIterator i = new ValueIterator();
/*      */     
/*  634 */     int j = this.count;
/*  635 */     while (j-- != 0) {
/*  636 */       boolean ev = i.nextBoolean();
/*  637 */       if (ev == v) return true; 
/*      */     } 
/*  639 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  644 */     this.count = 0;
/*  645 */     this.tree = null;
/*  646 */     this.entries = null;
/*  647 */     this.values = null;
/*  648 */     this.keys = null;
/*  649 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractByte2BooleanMap.BasicEntry
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
/*  678 */       super((byte)0, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(byte k, boolean v) {
/*  688 */       super(k, v);
/*  689 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  698 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  707 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  716 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  725 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  734 */       if (pred) { this.info |= 0x40000000; }
/*  735 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  744 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  745 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  754 */       this.info |= 0x40000000;
/*  755 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  764 */       this.info |= Integer.MIN_VALUE;
/*  765 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  774 */       this.info &= 0xBFFFFFFF;
/*  775 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  784 */       this.info &= Integer.MAX_VALUE;
/*  785 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  794 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  803 */       this.info &= 0xFFFFFF00;
/*  804 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  809 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  814 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  823 */       Entry next = this.right;
/*  824 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  825 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  834 */       Entry prev = this.left;
/*  835 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  836 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  841 */       boolean oldValue = this.value;
/*  842 */       this.value = value;
/*  843 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  851 */         c = (Entry)super.clone();
/*  852 */       } catch (CloneNotSupportedException cantHappen) {
/*  853 */         throw new InternalError();
/*      */       } 
/*  855 */       c.key = this.key;
/*  856 */       c.value = this.value;
/*  857 */       c.info = this.info;
/*  858 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  864 */       if (!(o instanceof Map.Entry)) return false; 
/*  865 */       Map.Entry<Byte, Boolean> e = (Map.Entry<Byte, Boolean>)o;
/*  866 */       return (this.key == ((Byte)e.getKey()).byteValue() && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  871 */       return this.key ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  876 */       return this.key + "=>" + this.value;
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
/*  914 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  919 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  924 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(byte k) {
/*  929 */     Entry e = findKey(k);
/*  930 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte firstByteKey() {
/*  935 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  936 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte lastByteKey() {
/*  941 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  942 */     return this.lastEntry.key;
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
/*      */     Byte2BooleanAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2BooleanAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2BooleanAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  971 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  974 */       this.next = Byte2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(byte k) {
/*  978 */       if ((this.next = Byte2BooleanAVLTreeMap.this.locateKey(k)) != null)
/*  979 */         if (Byte2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0)
/*  980 */         { this.prev = this.next;
/*  981 */           this.next = this.next.next(); }
/*  982 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  987 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  991 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  995 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Byte2BooleanAVLTreeMap.Entry nextEntry() {
/*  999 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1000 */       this.curr = this.prev = this.next;
/* 1001 */       this.index++;
/* 1002 */       updateNext();
/* 1003 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1007 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Byte2BooleanAVLTreeMap.Entry previousEntry() {
/* 1011 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1012 */       this.curr = this.next = this.prev;
/* 1013 */       this.index--;
/* 1014 */       updatePrevious();
/* 1015 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1019 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1023 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1027 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1030 */       if (this.curr == this.prev) this.index--; 
/* 1031 */       this.next = this.prev = this.curr;
/* 1032 */       updatePrevious();
/* 1033 */       updateNext();
/* 1034 */       Byte2BooleanAVLTreeMap.this.remove(this.curr.key);
/* 1035 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1039 */       int i = n;
/* 1040 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1041 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1045 */       int i = n;
/* 1046 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1047 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Byte2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(byte k) {
/* 1062 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2BooleanMap.Entry next() {
/* 1067 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2BooleanMap.Entry previous() {
/* 1072 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Byte2BooleanMap.Entry ok) {
/* 1077 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Byte2BooleanMap.Entry ok) {
/* 1082 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet() {
/* 1089 */     if (this.entries == null) this.entries = (ObjectSortedSet<Byte2BooleanMap.Entry>)new AbstractObjectSortedSet<Byte2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Byte2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Byte2BooleanMap.Entry> comparator() {
/* 1094 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2BooleanMap.Entry> iterator() {
/* 1099 */             return (ObjectBidirectionalIterator<Byte2BooleanMap.Entry>)new Byte2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2BooleanMap.Entry> iterator(Byte2BooleanMap.Entry from) {
/* 1104 */             return (ObjectBidirectionalIterator<Byte2BooleanMap.Entry>)new Byte2BooleanAVLTreeMap.EntryIterator(from.getByteKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1110 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1111 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1112 */             if (e.getKey() == null) return false; 
/* 1113 */             if (!(e.getKey() instanceof Byte)) return false; 
/* 1114 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1115 */             Byte2BooleanAVLTreeMap.Entry f = Byte2BooleanAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1116 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1122 */             if (!(o instanceof Map.Entry)) return false; 
/* 1123 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1124 */             if (e.getKey() == null) return false; 
/* 1125 */             if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1126 */             if (!(e.getValue() instanceof Boolean)) return false; 
/* 1127 */             Byte2BooleanAVLTreeMap.Entry f = Byte2BooleanAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1128 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue()) return false; 
/* 1129 */             Byte2BooleanAVLTreeMap.this.remove(f.key);
/* 1130 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1135 */             return Byte2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1140 */             Byte2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Byte2BooleanMap.Entry first() {
/* 1145 */             return Byte2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Byte2BooleanMap.Entry last() {
/* 1150 */             return Byte2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2BooleanMap.Entry> subSet(Byte2BooleanMap.Entry from, Byte2BooleanMap.Entry to) {
/* 1155 */             return Byte2BooleanAVLTreeMap.this.subMap(from.getByteKey(), to.getByteKey()).byte2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2BooleanMap.Entry> headSet(Byte2BooleanMap.Entry to) {
/* 1160 */             return Byte2BooleanAVLTreeMap.this.headMap(to.getByteKey()).byte2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2BooleanMap.Entry> tailSet(Byte2BooleanMap.Entry from) {
/* 1165 */             return Byte2BooleanAVLTreeMap.this.tailMap(from.getByteKey()).byte2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1168 */     return this.entries;
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
/* 1184 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1189 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/* 1194 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractByte2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteBidirectionalIterator iterator() {
/* 1202 */       return new Byte2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBidirectionalIterator iterator(byte from) {
/* 1207 */       return new Byte2BooleanAVLTreeMap.KeyIterator(from);
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
/* 1222 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1223 */     return this.keys;
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
/* 1237 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/* 1242 */       return (previousEntry()).value;
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
/* 1257 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1260 */             return (BooleanIterator)new Byte2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1265 */             return Byte2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1270 */             return Byte2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1275 */             Byte2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1278 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteComparator comparator() {
/* 1283 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap headMap(byte to) {
/* 1288 */     return new Submap((byte)0, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap tailMap(byte from) {
/* 1293 */     return new Submap(from, false, (byte)0, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap subMap(byte from, byte to) {
/* 1298 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractByte2BooleanSortedMap
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
/*      */     protected transient ObjectSortedSet<Byte2BooleanMap.Entry> entries;
/*      */ 
/*      */     
/*      */     protected transient ByteSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(byte from, boolean bottom, byte to, boolean top) {
/* 1337 */       if (!bottom && !top && Byte2BooleanAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1338 */       this.from = from;
/* 1339 */       this.bottom = bottom;
/* 1340 */       this.to = to;
/* 1341 */       this.top = top;
/* 1342 */       this.defRetValue = Byte2BooleanAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1347 */       SubmapIterator i = new SubmapIterator();
/* 1348 */       while (i.hasNext()) {
/* 1349 */         i.nextEntry();
/* 1350 */         i.remove();
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
/* 1361 */       return ((this.bottom || Byte2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Byte2BooleanAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet() {
/* 1366 */       if (this.entries == null) this.entries = (ObjectSortedSet<Byte2BooleanMap.Entry>)new AbstractObjectSortedSet<Byte2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Byte2BooleanMap.Entry> iterator() {
/* 1369 */               return (ObjectBidirectionalIterator<Byte2BooleanMap.Entry>)new Byte2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Byte2BooleanMap.Entry> iterator(Byte2BooleanMap.Entry from) {
/* 1374 */               return (ObjectBidirectionalIterator<Byte2BooleanMap.Entry>)new Byte2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getByteKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Byte2BooleanMap.Entry> comparator() {
/* 1379 */               return Byte2BooleanAVLTreeMap.this.byte2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1385 */               if (!(o instanceof Map.Entry)) return false; 
/* 1386 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1387 */               if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1388 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1389 */               Byte2BooleanAVLTreeMap.Entry f = Byte2BooleanAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1390 */               return (f != null && Byte2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1396 */               if (!(o instanceof Map.Entry)) return false; 
/* 1397 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1398 */               if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1399 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1400 */               Byte2BooleanAVLTreeMap.Entry f = Byte2BooleanAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1401 */               if (f != null && Byte2BooleanAVLTreeMap.Submap.this.in(f.key)) Byte2BooleanAVLTreeMap.Submap.this.remove(f.key); 
/* 1402 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1407 */               int c = 0;
/* 1408 */               for (ObjectBidirectionalIterator<Byte2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1409 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1414 */               return !(new Byte2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1419 */               Byte2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Byte2BooleanMap.Entry first() {
/* 1424 */               return Byte2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Byte2BooleanMap.Entry last() {
/* 1429 */               return Byte2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2BooleanMap.Entry> subSet(Byte2BooleanMap.Entry from, Byte2BooleanMap.Entry to) {
/* 1434 */               return Byte2BooleanAVLTreeMap.Submap.this.subMap(from.getByteKey(), to.getByteKey()).byte2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2BooleanMap.Entry> headSet(Byte2BooleanMap.Entry to) {
/* 1439 */               return Byte2BooleanAVLTreeMap.Submap.this.headMap(to.getByteKey()).byte2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2BooleanMap.Entry> tailSet(Byte2BooleanMap.Entry from) {
/* 1444 */               return Byte2BooleanAVLTreeMap.Submap.this.tailMap(from.getByteKey()).byte2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1447 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractByte2BooleanSortedMap.KeySet { private KeySet() {}
/*      */       
/*      */       public ByteBidirectionalIterator iterator() {
/* 1453 */         return new Byte2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ByteBidirectionalIterator iterator(byte from) {
/* 1458 */         return new Byte2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteSortedSet keySet() {
/* 1464 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1465 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanCollection values() {
/* 1470 */       if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1473 */               return (BooleanIterator)new Byte2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1478 */               return Byte2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1483 */               return Byte2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1488 */               Byte2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1491 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(byte k) {
/* 1498 */       return (in(k) && Byte2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1503 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1505 */       while (i.hasNext()) {
/* 1506 */         boolean ev = (i.nextEntry()).value;
/* 1507 */         if (ev == v) return true; 
/*      */       } 
/* 1509 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(byte k) {
/* 1516 */       byte kk = k; Byte2BooleanAVLTreeMap.Entry e;
/* 1517 */       return (in(kk) && (e = Byte2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean put(byte k, boolean v) {
/* 1522 */       Byte2BooleanAVLTreeMap.this.modified = false;
/* 1523 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1524 */       boolean oldValue = Byte2BooleanAVLTreeMap.this.put(k, v);
/* 1525 */       return Byte2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(byte k) {
/* 1531 */       Byte2BooleanAVLTreeMap.this.modified = false;
/* 1532 */       if (!in(k)) return this.defRetValue; 
/* 1533 */       boolean oldValue = Byte2BooleanAVLTreeMap.this.remove(k);
/* 1534 */       return Byte2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1539 */       SubmapIterator i = new SubmapIterator();
/* 1540 */       int n = 0;
/* 1541 */       while (i.hasNext()) {
/* 1542 */         n++;
/* 1543 */         i.nextEntry();
/*      */       } 
/* 1545 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1550 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator comparator() {
/* 1555 */       return Byte2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2BooleanSortedMap headMap(byte to) {
/* 1560 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1561 */       return (Byte2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2BooleanSortedMap tailMap(byte from) {
/* 1566 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1567 */       return (Byte2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2BooleanSortedMap subMap(byte from, byte to) {
/* 1572 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1573 */       if (!this.top) to = (Byte2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1574 */       if (!this.bottom) from = (Byte2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1575 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1576 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2BooleanAVLTreeMap.Entry firstEntry() {
/*      */       Byte2BooleanAVLTreeMap.Entry e;
/* 1585 */       if (Byte2BooleanAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1589 */       if (this.bottom) { e = Byte2BooleanAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1591 */       { e = Byte2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1593 */         if (Byte2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1597 */       if (e == null || (!this.top && Byte2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1598 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2BooleanAVLTreeMap.Entry lastEntry() {
/*      */       Byte2BooleanAVLTreeMap.Entry e;
/* 1607 */       if (Byte2BooleanAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1611 */       if (this.top) { e = Byte2BooleanAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1613 */       { e = Byte2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1615 */         if (Byte2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1619 */       if (e == null || (!this.bottom && Byte2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1620 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte firstByteKey() {
/* 1625 */       Byte2BooleanAVLTreeMap.Entry e = firstEntry();
/* 1626 */       if (e == null) throw new NoSuchElementException(); 
/* 1627 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte lastByteKey() {
/* 1632 */       Byte2BooleanAVLTreeMap.Entry e = lastEntry();
/* 1633 */       if (e == null) throw new NoSuchElementException(); 
/* 1634 */       return e.key;
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
/*      */       extends Byte2BooleanAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1648 */         this.next = Byte2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(byte k) {
/* 1652 */         this();
/* 1653 */         if (this.next != null) {
/* 1654 */           if (!Byte2BooleanAVLTreeMap.Submap.this.bottom && Byte2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1655 */           else if (!Byte2BooleanAVLTreeMap.Submap.this.top && Byte2BooleanAVLTreeMap.this.compare(k, (this.prev = Byte2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1657 */           { this.next = Byte2BooleanAVLTreeMap.this.locateKey(k);
/* 1658 */             if (Byte2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1659 */             { this.prev = this.next;
/* 1660 */               this.next = this.next.next(); }
/* 1661 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1668 */         this.prev = this.prev.prev();
/* 1669 */         if (!Byte2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Byte2BooleanAVLTreeMap.this.compare(this.prev.key, Byte2BooleanAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1674 */         this.next = this.next.next();
/* 1675 */         if (!Byte2BooleanAVLTreeMap.Submap.this.top && this.next != null && Byte2BooleanAVLTreeMap.this.compare(this.next.key, Byte2BooleanAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Byte2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(byte k) {
/* 1684 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Byte2BooleanMap.Entry next() {
/* 1689 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Byte2BooleanMap.Entry previous() {
/* 1694 */         return previousEntry();
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
/* 1713 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public byte nextByte() {
/* 1718 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public byte previousByte() {
/* 1723 */         return (previousEntry()).key;
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
/* 1739 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean previousBoolean() {
/* 1744 */         return (previousEntry()).value;
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
/*      */   public Byte2BooleanAVLTreeMap clone() {
/*      */     Byte2BooleanAVLTreeMap c;
/*      */     try {
/* 1763 */       c = (Byte2BooleanAVLTreeMap)super.clone();
/* 1764 */     } catch (CloneNotSupportedException cantHappen) {
/* 1765 */       throw new InternalError();
/*      */     } 
/* 1767 */     c.keys = null;
/* 1768 */     c.values = null;
/* 1769 */     c.entries = null;
/* 1770 */     c.allocatePaths();
/* 1771 */     if (this.count != 0) {
/*      */       
/* 1773 */       Entry rp = new Entry(), rq = new Entry();
/* 1774 */       Entry p = rp;
/* 1775 */       rp.left(this.tree);
/* 1776 */       Entry q = rq;
/* 1777 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1779 */         if (!p.pred()) {
/* 1780 */           Entry e = p.left.clone();
/* 1781 */           e.pred(q.left);
/* 1782 */           e.succ(q);
/* 1783 */           q.left(e);
/* 1784 */           p = p.left;
/* 1785 */           q = q.left;
/*      */         } else {
/* 1787 */           while (p.succ()) {
/* 1788 */             p = p.right;
/* 1789 */             if (p == null) {
/* 1790 */               q.right = null;
/* 1791 */               c.tree = rq.left;
/* 1792 */               c.firstEntry = c.tree;
/* 1793 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1794 */               c.lastEntry = c.tree;
/* 1795 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1796 */               return c;
/*      */             } 
/* 1798 */             q = q.right;
/*      */           } 
/* 1800 */           p = p.right;
/* 1801 */           q = q.right;
/*      */         } 
/* 1803 */         if (!p.succ()) {
/* 1804 */           Entry e = p.right.clone();
/* 1805 */           e.succ(q.right);
/* 1806 */           e.pred(q);
/* 1807 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1811 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1815 */     int n = this.count;
/* 1816 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1818 */     s.defaultWriteObject();
/* 1819 */     while (n-- != 0) {
/* 1820 */       Entry e = i.nextEntry();
/* 1821 */       s.writeByte(e.key);
/* 1822 */       s.writeBoolean(e.value);
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
/* 1836 */     if (n == 1) {
/* 1837 */       Entry entry = new Entry(s.readByte(), s.readBoolean());
/* 1838 */       entry.pred(pred);
/* 1839 */       entry.succ(succ);
/* 1840 */       return entry;
/*      */     } 
/* 1842 */     if (n == 2) {
/*      */ 
/*      */       
/* 1845 */       Entry entry = new Entry(s.readByte(), s.readBoolean());
/* 1846 */       entry.right(new Entry(s.readByte(), s.readBoolean()));
/* 1847 */       entry.right.pred(entry);
/* 1848 */       entry.balance(1);
/* 1849 */       entry.pred(pred);
/* 1850 */       entry.right.succ(succ);
/* 1851 */       return entry;
/*      */     } 
/*      */     
/* 1854 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1855 */     Entry top = new Entry();
/* 1856 */     top.left(readTree(s, leftN, pred, top));
/* 1857 */     top.key = s.readByte();
/* 1858 */     top.value = s.readBoolean();
/* 1859 */     top.right(readTree(s, rightN, top, succ));
/* 1860 */     if (n == (n & -n)) top.balance(1); 
/* 1861 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1865 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1868 */     setActualComparator();
/* 1869 */     allocatePaths();
/* 1870 */     if (this.count != 0) {
/* 1871 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1873 */       Entry e = this.tree;
/* 1874 */       for (; e.left() != null; e = e.left());
/* 1875 */       this.firstEntry = e;
/* 1876 */       e = this.tree;
/* 1877 */       for (; e.right() != null; e = e.right());
/* 1878 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */