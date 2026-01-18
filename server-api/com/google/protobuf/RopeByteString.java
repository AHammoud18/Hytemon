/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
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
/*     */ final class RopeByteString
/*     */   extends ByteString
/*     */ {
/*  60 */   static final int[] minLengthByDepth = new int[] { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int totalLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ByteString left;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ByteString right;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int leftLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int treeDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RopeByteString(ByteString left, ByteString right) {
/* 124 */     this.left = left;
/* 125 */     this.right = right;
/* 126 */     this.leftLength = left.size();
/* 127 */     this.totalLength = this.leftLength + right.size();
/* 128 */     this.treeDepth = Math.max(left.getTreeDepth(), right.getTreeDepth()) + 1;
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
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteString concatenate(ByteString left, ByteString right) {
/* 145 */     if (right.size() == 0) {
/* 146 */       return left;
/*     */     }
/*     */     
/* 149 */     if (left.size() == 0) {
/* 150 */       return right;
/*     */     }
/*     */     
/* 153 */     int newLength = left.size() + right.size();
/* 154 */     if (newLength < 128)
/*     */     {
/*     */       
/* 157 */       return concatenateBytes(left, right);
/*     */     }
/*     */     
/* 160 */     if (left instanceof RopeByteString) {
/* 161 */       RopeByteString leftRope = (RopeByteString)left;
/* 162 */       if (leftRope.right.size() + right.size() < 128) {
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
/* 173 */         ByteString newRight = concatenateBytes(leftRope.right, right);
/* 174 */         return new RopeByteString(leftRope.left, newRight);
/*     */       } 
/*     */       
/* 177 */       if (leftRope.left.getTreeDepth() > leftRope.right.getTreeDepth() && leftRope
/* 178 */         .getTreeDepth() > right.getTreeDepth()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 184 */         ByteString newRight = new RopeByteString(leftRope.right, right);
/* 185 */         return new RopeByteString(leftRope.left, newRight);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 190 */     int newDepth = Math.max(left.getTreeDepth(), right.getTreeDepth()) + 1;
/* 191 */     if (newLength >= minLength(newDepth))
/*     */     {
/* 193 */       return new RopeByteString(left, right);
/*     */     }
/*     */     
/* 196 */     return (new Balancer()).balance(left, right);
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
/*     */   private static ByteString concatenateBytes(ByteString left, ByteString right) {
/* 208 */     int leftSize = left.size();
/* 209 */     int rightSize = right.size();
/* 210 */     byte[] bytes = new byte[leftSize + rightSize];
/* 211 */     left.copyTo(bytes, 0, 0, leftSize);
/* 212 */     right.copyTo(bytes, 0, leftSize, rightSize);
/* 213 */     return ByteString.wrap(bytes);
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
/*     */   static RopeByteString newInstanceForTest(ByteString left, ByteString right) {
/* 227 */     return new RopeByteString(left, right);
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
/*     */   static int minLength(int depth) {
/* 240 */     if (depth >= minLengthByDepth.length) {
/* 241 */       return Integer.MAX_VALUE;
/*     */     }
/* 243 */     return minLengthByDepth[depth];
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
/*     */   public byte byteAt(int index) {
/* 257 */     checkIndex(index, this.totalLength);
/* 258 */     return internalByteAt(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   byte internalByteAt(int index) {
/* 264 */     if (index < this.leftLength) {
/* 265 */       return this.left.internalByteAt(index);
/*     */     }
/*     */     
/* 268 */     return this.right.internalByteAt(index - this.leftLength);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 273 */     return this.totalLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteString.ByteIterator iterator() {
/* 278 */     return new ByteString.AbstractByteIterator() {
/* 279 */         final RopeByteString.PieceIterator pieces = new RopeByteString.PieceIterator(RopeByteString.this);
/* 280 */         ByteString.ByteIterator current = nextPiece();
/*     */ 
/*     */ 
/*     */         
/*     */         private ByteString.ByteIterator nextPiece() {
/* 285 */           return this.pieces.hasNext() ? this.pieces.next().iterator() : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 290 */           return (this.current != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public byte nextByte() {
/* 295 */           if (this.current == null) {
/* 296 */             throw new NoSuchElementException();
/*     */           }
/* 298 */           byte b = this.current.nextByte();
/* 299 */           if (!this.current.hasNext()) {
/* 300 */             this.current = nextPiece();
/*     */           }
/* 302 */           return b;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTreeDepth() {
/* 312 */     return this.treeDepth;
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
/*     */   protected boolean isBalanced() {
/* 324 */     return (this.totalLength >= minLength(this.treeDepth));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteString substring(int beginIndex, int endIndex) {
/* 342 */     int length = checkRange(beginIndex, endIndex, this.totalLength);
/*     */     
/* 344 */     if (length == 0)
/*     */     {
/* 346 */       return ByteString.EMPTY;
/*     */     }
/*     */     
/* 349 */     if (length == this.totalLength)
/*     */     {
/* 351 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 355 */     if (endIndex <= this.leftLength)
/*     */     {
/* 357 */       return this.left.substring(beginIndex, endIndex);
/*     */     }
/*     */     
/* 360 */     if (beginIndex >= this.leftLength)
/*     */     {
/* 362 */       return this.right.substring(beginIndex - this.leftLength, endIndex - this.leftLength);
/*     */     }
/*     */ 
/*     */     
/* 366 */     ByteString leftSub = this.left.substring(beginIndex);
/* 367 */     ByteString rightSub = this.right.substring(0, endIndex - this.leftLength);
/*     */ 
/*     */ 
/*     */     
/* 371 */     return new RopeByteString(leftSub, rightSub);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
/* 380 */     if (sourceOffset + numberToCopy <= this.leftLength) {
/* 381 */       this.left.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
/* 382 */     } else if (sourceOffset >= this.leftLength) {
/* 383 */       this.right.copyToInternal(target, sourceOffset - this.leftLength, targetOffset, numberToCopy);
/*     */     } else {
/* 385 */       int leftLength = this.leftLength - sourceOffset;
/* 386 */       this.left.copyToInternal(target, sourceOffset, targetOffset, leftLength);
/* 387 */       this.right.copyToInternal(target, 0, targetOffset + leftLength, numberToCopy - leftLength);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyTo(ByteBuffer target) {
/* 393 */     this.left.copyTo(target);
/* 394 */     this.right.copyTo(target);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer asReadOnlyByteBuffer() {
/* 399 */     ByteBuffer byteBuffer = ByteBuffer.wrap(toByteArray());
/* 400 */     return byteBuffer.asReadOnlyBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ByteBuffer> asReadOnlyByteBufferList() {
/* 407 */     List<ByteBuffer> result = new ArrayList<>();
/* 408 */     PieceIterator pieces = new PieceIterator(this);
/* 409 */     while (pieces.hasNext()) {
/* 410 */       ByteString.LeafByteString byteString = pieces.next();
/* 411 */       result.add(byteString.asReadOnlyByteBuffer());
/*     */     } 
/* 413 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream outputStream) throws IOException {
/* 418 */     this.left.writeTo(outputStream);
/* 419 */     this.right.writeTo(outputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
/* 424 */     if (sourceOffset + numberToWrite <= this.leftLength) {
/* 425 */       this.left.writeToInternal(out, sourceOffset, numberToWrite);
/* 426 */     } else if (sourceOffset >= this.leftLength) {
/* 427 */       this.right.writeToInternal(out, sourceOffset - this.leftLength, numberToWrite);
/*     */     } else {
/* 429 */       int numberToWriteInLeft = this.leftLength - sourceOffset;
/* 430 */       this.left.writeToInternal(out, sourceOffset, numberToWriteInLeft);
/* 431 */       this.right.writeToInternal(out, 0, numberToWrite - numberToWriteInLeft);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void writeTo(ByteOutput output) throws IOException {
/* 437 */     this.left.writeTo(output);
/* 438 */     this.right.writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeToReverse(ByteOutput output) throws IOException {
/* 443 */     this.right.writeToReverse(output);
/* 444 */     this.left.writeToReverse(output);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String toStringInternal(Charset charset) {
/* 449 */     return new String(toByteArray(), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidUtf8() {
/* 457 */     int leftPartial = this.left.partialIsValidUtf8(0, 0, this.leftLength);
/* 458 */     int state = this.right.partialIsValidUtf8(leftPartial, 0, this.right.size());
/* 459 */     return (state == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int partialIsValidUtf8(int state, int offset, int length) {
/* 464 */     int toIndex = offset + length;
/* 465 */     if (toIndex <= this.leftLength)
/* 466 */       return this.left.partialIsValidUtf8(state, offset, length); 
/* 467 */     if (offset >= this.leftLength) {
/* 468 */       return this.right.partialIsValidUtf8(state, offset - this.leftLength, length);
/*     */     }
/* 470 */     int leftLength = this.leftLength - offset;
/* 471 */     int leftPartial = this.left.partialIsValidUtf8(state, offset, leftLength);
/* 472 */     return this.right.partialIsValidUtf8(leftPartial, 0, length - leftLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 482 */     if (other == this) {
/* 483 */       return true;
/*     */     }
/* 485 */     if (!(other instanceof ByteString)) {
/* 486 */       return false;
/*     */     }
/*     */     
/* 489 */     ByteString otherByteString = (ByteString)other;
/* 490 */     if (this.totalLength != otherByteString.size()) {
/* 491 */       return false;
/*     */     }
/* 493 */     if (this.totalLength == 0) {
/* 494 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 502 */     int thisHash = peekCachedHashCode();
/* 503 */     int thatHash = otherByteString.peekCachedHashCode();
/* 504 */     if (thisHash != 0 && thatHash != 0 && thisHash != thatHash) {
/* 505 */       return false;
/*     */     }
/*     */     
/* 508 */     return equalsFragments(otherByteString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean equalsFragments(ByteString other) {
/* 519 */     int thisOffset = 0;
/* 520 */     Iterator<ByteString.LeafByteString> thisIter = new PieceIterator(this);
/* 521 */     ByteString.LeafByteString thisString = thisIter.next();
/*     */     
/* 523 */     int thatOffset = 0;
/* 524 */     Iterator<ByteString.LeafByteString> thatIter = new PieceIterator(other);
/* 525 */     ByteString.LeafByteString thatString = thatIter.next();
/*     */     
/* 527 */     int pos = 0;
/*     */     while (true) {
/* 529 */       int thisRemaining = thisString.size() - thisOffset;
/* 530 */       int thatRemaining = thatString.size() - thatOffset;
/* 531 */       int bytesToCompare = Math.min(thisRemaining, thatRemaining);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 537 */       boolean stillEqual = (thisOffset == 0) ? thisString.equalsRange(thatString, thatOffset, bytesToCompare) : thatString.equalsRange(thisString, thisOffset, bytesToCompare);
/* 538 */       if (!stillEqual) {
/* 539 */         return false;
/*     */       }
/*     */       
/* 542 */       pos += bytesToCompare;
/* 543 */       if (pos >= this.totalLength) {
/* 544 */         if (pos == this.totalLength) {
/* 545 */           return true;
/*     */         }
/* 547 */         throw new IllegalStateException();
/*     */       } 
/*     */       
/* 550 */       if (bytesToCompare == thisRemaining) {
/* 551 */         thisOffset = 0;
/* 552 */         thisString = thisIter.next();
/*     */       } else {
/* 554 */         thisOffset += bytesToCompare;
/*     */       } 
/* 556 */       if (bytesToCompare == thatRemaining) {
/* 557 */         thatOffset = 0;
/* 558 */         thatString = thatIter.next(); continue;
/*     */       } 
/* 560 */       thatOffset += bytesToCompare;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int partialHash(int h, int offset, int length) {
/* 567 */     int toIndex = offset + length;
/* 568 */     if (toIndex <= this.leftLength)
/* 569 */       return this.left.partialHash(h, offset, length); 
/* 570 */     if (offset >= this.leftLength) {
/* 571 */       return this.right.partialHash(h, offset - this.leftLength, length);
/*     */     }
/* 573 */     int leftLength = this.leftLength - offset;
/* 574 */     int leftPartial = this.left.partialHash(h, offset, leftLength);
/* 575 */     return this.right.partialHash(leftPartial, 0, length - leftLength);
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
/*     */   public CodedInputStream newCodedInput() {
/* 589 */     return CodedInputStream.newInstance(asReadOnlyByteBufferList(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream newInput() {
/* 594 */     return new RopeInputStream();
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
/*     */   
/*     */   private static class Balancer
/*     */   {
/* 610 */     private final ArrayDeque<ByteString> prefixesStack = new ArrayDeque<>();
/*     */     
/*     */     private ByteString balance(ByteString left, ByteString right) {
/* 613 */       doBalance(left);
/* 614 */       doBalance(right);
/*     */ 
/*     */       
/* 617 */       ByteString partialString = this.prefixesStack.pop();
/* 618 */       while (!this.prefixesStack.isEmpty()) {
/* 619 */         ByteString newLeft = this.prefixesStack.pop();
/* 620 */         partialString = new RopeByteString(newLeft, partialString);
/*     */       } 
/*     */ 
/*     */       
/* 624 */       return partialString;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void doBalance(ByteString root) {
/* 632 */       if (root.isBalanced()) {
/* 633 */         insert(root);
/* 634 */       } else if (root instanceof RopeByteString) {
/* 635 */         RopeByteString rbs = (RopeByteString)root;
/* 636 */         doBalance(rbs.left);
/* 637 */         doBalance(rbs.right);
/*     */       } else {
/* 639 */         throw new IllegalArgumentException("Has a new type of ByteString been created? Found " + root
/* 640 */             .getClass());
/*     */       } 
/*     */     }
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
/*     */     private void insert(ByteString byteString) {
/* 657 */       int depthBin = getDepthBinForLength(byteString.size());
/* 658 */       int binEnd = RopeByteString.minLength(depthBin + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 664 */       if (this.prefixesStack.isEmpty() || ((ByteString)this.prefixesStack.peek()).size() >= binEnd) {
/* 665 */         this.prefixesStack.push(byteString);
/*     */       } else {
/* 667 */         int binStart = RopeByteString.minLength(depthBin);
/*     */ 
/*     */         
/* 670 */         ByteString newTree = this.prefixesStack.pop();
/* 671 */         while (!this.prefixesStack.isEmpty() && ((ByteString)this.prefixesStack.peek()).size() < binStart) {
/* 672 */           ByteString left = this.prefixesStack.pop();
/* 673 */           newTree = new RopeByteString(left, newTree);
/*     */         } 
/*     */ 
/*     */         
/* 677 */         newTree = new RopeByteString(newTree, byteString);
/*     */ 
/*     */         
/* 680 */         while (!this.prefixesStack.isEmpty()) {
/* 681 */           depthBin = getDepthBinForLength(newTree.size());
/* 682 */           binEnd = RopeByteString.minLength(depthBin + 1);
/* 683 */           if (((ByteString)this.prefixesStack.peek()).size() < binEnd) {
/* 684 */             ByteString left = this.prefixesStack.pop();
/* 685 */             newTree = new RopeByteString(left, newTree);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 690 */         this.prefixesStack.push(newTree);
/*     */       } 
/*     */     }
/*     */     
/*     */     private int getDepthBinForLength(int length) {
/* 695 */       int depth = Arrays.binarySearch(RopeByteString.minLengthByDepth, length);
/* 696 */       if (depth < 0) {
/*     */ 
/*     */         
/* 699 */         int insertionPoint = -(depth + 1);
/* 700 */         depth = insertionPoint - 1;
/*     */       } 
/*     */       
/* 703 */       return depth;
/*     */     }
/*     */ 
/*     */     
/*     */     private Balancer() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class PieceIterator
/*     */     implements Iterator<ByteString.LeafByteString>
/*     */   {
/*     */     private final ArrayDeque<RopeByteString> breadCrumbs;
/*     */     
/*     */     private ByteString.LeafByteString next;
/*     */     
/*     */     private PieceIterator(ByteString root) {
/* 719 */       if (root instanceof RopeByteString) {
/* 720 */         RopeByteString rbs = (RopeByteString)root;
/* 721 */         this.breadCrumbs = new ArrayDeque<>(rbs.getTreeDepth());
/* 722 */         this.breadCrumbs.push(rbs);
/* 723 */         this.next = getLeafByLeft(rbs.left);
/*     */       } else {
/* 725 */         this.breadCrumbs = null;
/* 726 */         this.next = (ByteString.LeafByteString)root;
/*     */       } 
/*     */     }
/*     */     
/*     */     private ByteString.LeafByteString getLeafByLeft(ByteString root) {
/* 731 */       ByteString pos = root;
/* 732 */       while (pos instanceof RopeByteString) {
/* 733 */         RopeByteString rbs = (RopeByteString)pos;
/* 734 */         this.breadCrumbs.push(rbs);
/* 735 */         pos = rbs.left;
/*     */       } 
/* 737 */       return (ByteString.LeafByteString)pos;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private ByteString.LeafByteString getNextNonEmptyLeaf() {
/*     */       while (true) {
/* 744 */         if (this.breadCrumbs == null || this.breadCrumbs.isEmpty()) {
/* 745 */           return null;
/*     */         }
/* 747 */         ByteString.LeafByteString result = getLeafByLeft((this.breadCrumbs.pop()).right);
/* 748 */         if (!result.isEmpty()) {
/* 749 */           return result;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 757 */       return (this.next != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteString.LeafByteString next() {
/* 767 */       if (this.next == null) {
/* 768 */         throw new NoSuchElementException();
/*     */       }
/* 770 */       ByteString.LeafByteString result = this.next;
/* 771 */       this.next = getNextNonEmptyLeaf();
/* 772 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 777 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 787 */     return ByteString.wrap(toByteArray());
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/* 791 */     throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
/*     */   }
/*     */ 
/*     */   
/*     */   private class RopeInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private RopeByteString.PieceIterator pieceIterator;
/*     */     
/*     */     private ByteString.LeafByteString currentPiece;
/*     */     
/*     */     private int currentPieceSize;
/*     */     
/*     */     private int currentPieceIndex;
/*     */     
/*     */     private int currentPieceOffsetInRope;
/*     */     private int mark;
/*     */     
/*     */     public RopeInputStream() {
/* 810 */       initialize();
/*     */     }
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
/*     */     public int read(byte[] b, int offset, int length) {
/* 825 */       if (b == null)
/* 826 */         throw new NullPointerException(); 
/* 827 */       if (offset < 0 || length < 0 || length > b.length - offset) {
/* 828 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 830 */       int bytesRead = readSkipInternal(b, offset, length);
/* 831 */       if (bytesRead == 0 && (length > 0 || availableInternal() == 0))
/*     */       {
/*     */ 
/*     */         
/* 835 */         return -1;
/*     */       }
/* 837 */       return bytesRead;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long skip(long length) {
/* 843 */       if (length < 0L)
/* 844 */         throw new IndexOutOfBoundsException(); 
/* 845 */       if (length > 2147483647L) {
/* 846 */         length = 2147483647L;
/*     */       }
/* 848 */       return readSkipInternal(null, 0, (int)length);
/*     */     }
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
/*     */     private int readSkipInternal(byte[] b, int offset, int length) {
/* 861 */       int bytesRemaining = length;
/* 862 */       while (bytesRemaining > 0) {
/* 863 */         advanceIfCurrentPieceFullyRead();
/* 864 */         if (this.currentPiece == null) {
/*     */           break;
/*     */         }
/*     */         
/* 868 */         int currentPieceRemaining = this.currentPieceSize - this.currentPieceIndex;
/* 869 */         int count = Math.min(currentPieceRemaining, bytesRemaining);
/* 870 */         if (b != null) {
/* 871 */           this.currentPiece.copyTo(b, this.currentPieceIndex, offset, count);
/* 872 */           offset += count;
/*     */         } 
/* 874 */         this.currentPieceIndex += count;
/* 875 */         bytesRemaining -= count;
/*     */       } 
/*     */ 
/*     */       
/* 879 */       return length - bytesRemaining;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 884 */       advanceIfCurrentPieceFullyRead();
/* 885 */       if (this.currentPiece == null) {
/* 886 */         return -1;
/*     */       }
/* 888 */       return this.currentPiece.byteAt(this.currentPieceIndex++) & 0xFF;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int available() throws IOException {
/* 894 */       return availableInternal();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean markSupported() {
/* 899 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mark(int readAheadLimit) {
/* 905 */       this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void reset() {
/* 911 */       initialize();
/* 912 */       readSkipInternal(null, 0, this.mark);
/*     */     }
/*     */ 
/*     */     
/*     */     private void initialize() {
/* 917 */       this.pieceIterator = new RopeByteString.PieceIterator(RopeByteString.this);
/* 918 */       this.currentPiece = this.pieceIterator.next();
/* 919 */       this.currentPieceSize = this.currentPiece.size();
/* 920 */       this.currentPieceIndex = 0;
/* 921 */       this.currentPieceOffsetInRope = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void advanceIfCurrentPieceFullyRead() {
/* 929 */       if (this.currentPiece != null && this.currentPieceIndex == this.currentPieceSize) {
/*     */ 
/*     */         
/* 932 */         this.currentPieceOffsetInRope += this.currentPieceSize;
/* 933 */         this.currentPieceIndex = 0;
/* 934 */         if (this.pieceIterator.hasNext()) {
/* 935 */           this.currentPiece = this.pieceIterator.next();
/* 936 */           this.currentPieceSize = this.currentPiece.size();
/*     */         } else {
/* 938 */           this.currentPiece = null;
/* 939 */           this.currentPieceSize = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private int availableInternal() {
/* 946 */       int bytesRead = this.currentPieceOffsetInRope + this.currentPieceIndex;
/* 947 */       return RopeByteString.this.size() - bytesRead;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RopeByteString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */