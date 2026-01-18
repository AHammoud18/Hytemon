/*      */ package it.unimi.dsi.fastutil.io;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterable;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterable;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterable;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterable;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterable;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterable;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterable;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.NoSuchElementException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TextIO
/*      */ {
/*      */   public static final int BUFFER_SIZE = 8192;
/*      */   
/*      */   public static int loadInts(BufferedReader reader, int[] array, int offset, int length) throws IOException {
/*  109 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  110 */     int i = 0;
/*      */     try {
/*      */       String s;
/*  113 */       for (i = 0; i < length && (
/*  114 */         s = reader.readLine()) != null; i++) array[i + offset] = Integer.parseInt(s.trim());
/*      */     
/*      */     }
/*  117 */     catch (EOFException eOFException) {}
/*  118 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(BufferedReader reader, int[] array) throws IOException {
/*  127 */     return loadInts(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, int[] array, int offset, int length) throws IOException {
/*  138 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  139 */     int result = loadInts(reader, array, offset, length);
/*  140 */     reader.close();
/*  141 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, int[] array, int offset, int length) throws IOException {
/*  152 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, int[] array) throws IOException {
/*  161 */     return loadInts(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, int[] array) throws IOException {
/*  170 */     return loadInts(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, PrintStream stream) {
/*  180 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  181 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, PrintStream stream) {
/*  189 */     storeInts(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, File file) throws IOException {
/*  199 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  200 */     storeInts(array, offset, length, stream);
/*  201 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, CharSequence filename) throws IOException {
/*  211 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, File file) throws IOException {
/*  219 */     storeInts(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, CharSequence filename) throws IOException {
/*  227 */     storeInts(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, PrintStream stream) {
/*  235 */     for (; i.hasNext(); stream.println(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, File file) throws IOException {
/*  243 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  244 */     storeInts(i, stream);
/*  245 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, CharSequence filename) throws IOException {
/*  253 */     storeInts(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(BufferedReader reader, int[][] array, long offset, long length) throws IOException {
/*  264 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  265 */     long c = 0L;
/*      */     
/*      */     try {
/*  268 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  269 */         int[] t = array[i];
/*  270 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  271 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  272 */           String s; if ((s = reader.readLine()) != null) { t[d] = Integer.parseInt(s.trim()); }
/*  273 */           else { return c; }
/*  274 */            c++;
/*      */         }
/*      */       
/*      */       } 
/*  278 */     } catch (EOFException eOFException) {}
/*  279 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(BufferedReader reader, int[][] array) throws IOException {
/*  288 */     return loadInts(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, int[][] array, long offset, long length) throws IOException {
/*  299 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  300 */     long result = loadInts(reader, array, offset, length);
/*  301 */     reader.close();
/*  302 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, int[][] array, long offset, long length) throws IOException {
/*  313 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, int[][] array) throws IOException {
/*  322 */     return loadInts(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, int[][] array) throws IOException {
/*  331 */     return loadInts(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, PrintStream stream) {
/*  341 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  342 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  343 */       int[] t = array[i];
/*  344 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  345 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, PrintStream stream) {
/*  354 */     storeInts(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, File file) throws IOException {
/*  364 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  365 */     storeInts(array, offset, length, stream);
/*  366 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, CharSequence filename) throws IOException {
/*  376 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, File file) throws IOException {
/*  384 */     storeInts(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, CharSequence filename) throws IOException {
/*  392 */     storeInts(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class IntReaderWrapper implements IntIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private int next;
/*      */     
/*      */     public IntReaderWrapper(BufferedReader reader) {
/*  401 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  405 */       if (!this.toAdvance) return (this.s != null); 
/*  406 */       this.toAdvance = false;
/*      */       
/*  408 */       try { this.s = this.reader.readLine(); }
/*      */       
/*  410 */       catch (EOFException eOFException) {  }
/*  411 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/*  412 */        if (this.s == null) return false; 
/*  413 */       this.next = Integer.parseInt(this.s.trim());
/*  414 */       return true;
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  418 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  419 */       this.toAdvance = true;
/*  420 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(BufferedReader reader) {
/*  428 */     return new IntReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(File file) throws IOException {
/*  435 */     return new IntReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(CharSequence filename) throws IOException {
/*  442 */     return asIntIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(File file) {
/*  449 */     return () -> { try {
/*      */           return asIntIterator(file);
/*  451 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(CharSequence filename) {
/*  459 */     return () -> { try {
/*      */           return asIntIterator(filename);
/*  461 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(BufferedReader reader, long[] array, int offset, int length) throws IOException {
/*  513 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  514 */     int i = 0;
/*      */     try {
/*      */       String s;
/*  517 */       for (i = 0; i < length && (
/*  518 */         s = reader.readLine()) != null; i++) array[i + offset] = Long.parseLong(s.trim());
/*      */     
/*      */     }
/*  521 */     catch (EOFException eOFException) {}
/*  522 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(BufferedReader reader, long[] array) throws IOException {
/*  531 */     return loadLongs(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, long[] array, int offset, int length) throws IOException {
/*  542 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  543 */     int result = loadLongs(reader, array, offset, length);
/*  544 */     reader.close();
/*  545 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, long[] array, int offset, int length) throws IOException {
/*  556 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, long[] array) throws IOException {
/*  565 */     return loadLongs(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, long[] array) throws IOException {
/*  574 */     return loadLongs(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, PrintStream stream) {
/*  584 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  585 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, PrintStream stream) {
/*  593 */     storeLongs(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, File file) throws IOException {
/*  603 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  604 */     storeLongs(array, offset, length, stream);
/*  605 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, CharSequence filename) throws IOException {
/*  615 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, File file) throws IOException {
/*  623 */     storeLongs(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, CharSequence filename) throws IOException {
/*  631 */     storeLongs(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, PrintStream stream) {
/*  639 */     for (; i.hasNext(); stream.println(i.nextLong()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, File file) throws IOException {
/*  647 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  648 */     storeLongs(i, stream);
/*  649 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, CharSequence filename) throws IOException {
/*  657 */     storeLongs(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(BufferedReader reader, long[][] array, long offset, long length) throws IOException {
/*  668 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  669 */     long c = 0L;
/*      */     
/*      */     try {
/*  672 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  673 */         long[] t = array[i];
/*  674 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  675 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  676 */           String s; if ((s = reader.readLine()) != null) { t[d] = Long.parseLong(s.trim()); }
/*  677 */           else { return c; }
/*  678 */            c++;
/*      */         }
/*      */       
/*      */       } 
/*  682 */     } catch (EOFException eOFException) {}
/*  683 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(BufferedReader reader, long[][] array) throws IOException {
/*  692 */     return loadLongs(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, long[][] array, long offset, long length) throws IOException {
/*  703 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  704 */     long result = loadLongs(reader, array, offset, length);
/*  705 */     reader.close();
/*  706 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, long[][] array, long offset, long length) throws IOException {
/*  717 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, long[][] array) throws IOException {
/*  726 */     return loadLongs(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, long[][] array) throws IOException {
/*  735 */     return loadLongs(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, PrintStream stream) {
/*  745 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  746 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  747 */       long[] t = array[i];
/*  748 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  749 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, PrintStream stream) {
/*  758 */     storeLongs(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, File file) throws IOException {
/*  768 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  769 */     storeLongs(array, offset, length, stream);
/*  770 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, CharSequence filename) throws IOException {
/*  780 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, File file) throws IOException {
/*  788 */     storeLongs(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, CharSequence filename) throws IOException {
/*  796 */     storeLongs(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class LongReaderWrapper implements LongIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private long next;
/*      */     
/*      */     public LongReaderWrapper(BufferedReader reader) {
/*  805 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  809 */       if (!this.toAdvance) return (this.s != null); 
/*  810 */       this.toAdvance = false;
/*      */       
/*  812 */       try { this.s = this.reader.readLine(); }
/*      */       
/*  814 */       catch (EOFException eOFException) {  }
/*  815 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/*  816 */        if (this.s == null) return false; 
/*  817 */       this.next = Long.parseLong(this.s.trim());
/*  818 */       return true;
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  822 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  823 */       this.toAdvance = true;
/*  824 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(BufferedReader reader) {
/*  832 */     return new LongReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(File file) throws IOException {
/*  839 */     return new LongReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(CharSequence filename) throws IOException {
/*  846 */     return asLongIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(File file) {
/*  853 */     return () -> { try {
/*      */           return asLongIterator(file);
/*  855 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(CharSequence filename) {
/*  863 */     return () -> { try {
/*      */           return asLongIterator(filename);
/*  865 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(BufferedReader reader, double[] array, int offset, int length) throws IOException {
/*  917 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/*  918 */     int i = 0;
/*      */     try {
/*      */       String s;
/*  921 */       for (i = 0; i < length && (
/*  922 */         s = reader.readLine()) != null; i++) array[i + offset] = Double.parseDouble(s.trim());
/*      */     
/*      */     }
/*  925 */     catch (EOFException eOFException) {}
/*  926 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(BufferedReader reader, double[] array) throws IOException {
/*  935 */     return loadDoubles(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, double[] array, int offset, int length) throws IOException {
/*  946 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  947 */     int result = loadDoubles(reader, array, offset, length);
/*  948 */     reader.close();
/*  949 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, double[] array, int offset, int length) throws IOException {
/*  960 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, double[] array) throws IOException {
/*  969 */     return loadDoubles(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, double[] array) throws IOException {
/*  978 */     return loadDoubles(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, PrintStream stream) {
/*  988 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/*  989 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, PrintStream stream) {
/*  997 */     storeDoubles(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, File file) throws IOException {
/* 1007 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1008 */     storeDoubles(array, offset, length, stream);
/* 1009 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1019 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, File file) throws IOException {
/* 1027 */     storeDoubles(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, CharSequence filename) throws IOException {
/* 1035 */     storeDoubles(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, PrintStream stream) {
/* 1043 */     for (; i.hasNext(); stream.println(i.nextDouble()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, File file) throws IOException {
/* 1051 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1052 */     storeDoubles(i, stream);
/* 1053 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, CharSequence filename) throws IOException {
/* 1061 */     storeDoubles(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(BufferedReader reader, double[][] array, long offset, long length) throws IOException {
/* 1072 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1073 */     long c = 0L;
/*      */     
/*      */     try {
/* 1076 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1077 */         double[] t = array[i];
/* 1078 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1079 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1080 */           String s; if ((s = reader.readLine()) != null) { t[d] = Double.parseDouble(s.trim()); }
/* 1081 */           else { return c; }
/* 1082 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 1086 */     } catch (EOFException eOFException) {}
/* 1087 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(BufferedReader reader, double[][] array) throws IOException {
/* 1096 */     return loadDoubles(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, double[][] array, long offset, long length) throws IOException {
/* 1107 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1108 */     long result = loadDoubles(reader, array, offset, length);
/* 1109 */     reader.close();
/* 1110 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, double[][] array, long offset, long length) throws IOException {
/* 1121 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, double[][] array) throws IOException {
/* 1130 */     return loadDoubles(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, double[][] array) throws IOException {
/* 1139 */     return loadDoubles(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, PrintStream stream) {
/* 1149 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1150 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1151 */       double[] t = array[i];
/* 1152 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1153 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, PrintStream stream) {
/* 1162 */     storeDoubles(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, File file) throws IOException {
/* 1172 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1173 */     storeDoubles(array, offset, length, stream);
/* 1174 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1184 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, File file) throws IOException {
/* 1192 */     storeDoubles(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, CharSequence filename) throws IOException {
/* 1200 */     storeDoubles(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class DoubleReaderWrapper implements DoubleIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private double next;
/*      */     
/*      */     public DoubleReaderWrapper(BufferedReader reader) {
/* 1209 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1213 */       if (!this.toAdvance) return (this.s != null); 
/* 1214 */       this.toAdvance = false;
/*      */       
/* 1216 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 1218 */       catch (EOFException eOFException) {  }
/* 1219 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1220 */        if (this.s == null) return false; 
/* 1221 */       this.next = Double.parseDouble(this.s.trim());
/* 1222 */       return true;
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1226 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1227 */       this.toAdvance = true;
/* 1228 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(BufferedReader reader) {
/* 1236 */     return new DoubleReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(File file) throws IOException {
/* 1243 */     return new DoubleReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(CharSequence filename) throws IOException {
/* 1250 */     return asDoubleIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(File file) {
/* 1257 */     return () -> { try {
/*      */           return asDoubleIterator(file);
/* 1259 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(CharSequence filename) {
/* 1267 */     return () -> { try {
/*      */           return asDoubleIterator(filename);
/* 1269 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(BufferedReader reader, boolean[] array, int offset, int length) throws IOException {
/* 1321 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 1322 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 1325 */       for (i = 0; i < length && (
/* 1326 */         s = reader.readLine()) != null; i++) array[i + offset] = Boolean.parseBoolean(s.trim());
/*      */     
/*      */     }
/* 1329 */     catch (EOFException eOFException) {}
/* 1330 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(BufferedReader reader, boolean[] array) throws IOException {
/* 1339 */     return loadBooleans(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(File file, boolean[] array, int offset, int length) throws IOException {
/* 1350 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1351 */     int result = loadBooleans(reader, array, offset, length);
/* 1352 */     reader.close();
/* 1353 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(CharSequence filename, boolean[] array, int offset, int length) throws IOException {
/* 1364 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(File file, boolean[] array) throws IOException {
/* 1373 */     return loadBooleans(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(CharSequence filename, boolean[] array) throws IOException {
/* 1382 */     return loadBooleans(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, PrintStream stream) {
/* 1392 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 1393 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, PrintStream stream) {
/* 1401 */     storeBooleans(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, File file) throws IOException {
/* 1411 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1412 */     storeBooleans(array, offset, length, stream);
/* 1413 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1423 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, File file) throws IOException {
/* 1431 */     storeBooleans(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, CharSequence filename) throws IOException {
/* 1439 */     storeBooleans(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, PrintStream stream) {
/* 1447 */     for (; i.hasNext(); stream.println(i.nextBoolean()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, File file) throws IOException {
/* 1455 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1456 */     storeBooleans(i, stream);
/* 1457 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, CharSequence filename) throws IOException {
/* 1465 */     storeBooleans(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(BufferedReader reader, boolean[][] array, long offset, long length) throws IOException {
/* 1476 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1477 */     long c = 0L;
/*      */     
/*      */     try {
/* 1480 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1481 */         boolean[] t = array[i];
/* 1482 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1483 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1484 */           String s; if ((s = reader.readLine()) != null) { t[d] = Boolean.parseBoolean(s.trim()); }
/* 1485 */           else { return c; }
/* 1486 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 1490 */     } catch (EOFException eOFException) {}
/* 1491 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(BufferedReader reader, boolean[][] array) throws IOException {
/* 1500 */     return loadBooleans(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(File file, boolean[][] array, long offset, long length) throws IOException {
/* 1511 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1512 */     long result = loadBooleans(reader, array, offset, length);
/* 1513 */     reader.close();
/* 1514 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(CharSequence filename, boolean[][] array, long offset, long length) throws IOException {
/* 1525 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(File file, boolean[][] array) throws IOException {
/* 1534 */     return loadBooleans(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(CharSequence filename, boolean[][] array) throws IOException {
/* 1543 */     return loadBooleans(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, PrintStream stream) {
/* 1553 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1554 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1555 */       boolean[] t = array[i];
/* 1556 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1557 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, PrintStream stream) {
/* 1566 */     storeBooleans(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, File file) throws IOException {
/* 1576 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1577 */     storeBooleans(array, offset, length, stream);
/* 1578 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1588 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, File file) throws IOException {
/* 1596 */     storeBooleans(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, CharSequence filename) throws IOException {
/* 1604 */     storeBooleans(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class BooleanReaderWrapper implements BooleanIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private boolean next;
/*      */     
/*      */     public BooleanReaderWrapper(BufferedReader reader) {
/* 1613 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1617 */       if (!this.toAdvance) return (this.s != null); 
/* 1618 */       this.toAdvance = false;
/*      */       
/* 1620 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 1622 */       catch (EOFException eOFException) {  }
/* 1623 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1624 */        if (this.s == null) return false; 
/* 1625 */       this.next = Boolean.parseBoolean(this.s.trim());
/* 1626 */       return true;
/*      */     }
/*      */     
/*      */     public boolean nextBoolean() {
/* 1630 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1631 */       this.toAdvance = true;
/* 1632 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(BufferedReader reader) {
/* 1640 */     return new BooleanReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(File file) throws IOException {
/* 1647 */     return new BooleanReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(CharSequence filename) throws IOException {
/* 1654 */     return asBooleanIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(File file) {
/* 1661 */     return () -> { try {
/*      */           return asBooleanIterator(file);
/* 1663 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(CharSequence filename) {
/* 1671 */     return () -> { try {
/*      */           return asBooleanIterator(filename);
/* 1673 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(BufferedReader reader, byte[] array, int offset, int length) throws IOException {
/* 1725 */     ByteArrays.ensureOffsetLength(array, offset, length);
/* 1726 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 1729 */       for (i = 0; i < length && (
/* 1730 */         s = reader.readLine()) != null; i++) array[i + offset] = Byte.parseByte(s.trim());
/*      */     
/*      */     }
/* 1733 */     catch (EOFException eOFException) {}
/* 1734 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(BufferedReader reader, byte[] array) throws IOException {
/* 1743 */     return loadBytes(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(File file, byte[] array, int offset, int length) throws IOException {
/* 1754 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1755 */     int result = loadBytes(reader, array, offset, length);
/* 1756 */     reader.close();
/* 1757 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(CharSequence filename, byte[] array, int offset, int length) throws IOException {
/* 1768 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(File file, byte[] array) throws IOException {
/* 1777 */     return loadBytes(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(CharSequence filename, byte[] array) throws IOException {
/* 1786 */     return loadBytes(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, PrintStream stream) {
/* 1796 */     ByteArrays.ensureOffsetLength(array, offset, length);
/* 1797 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, PrintStream stream) {
/* 1805 */     storeBytes(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, File file) throws IOException {
/* 1815 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1816 */     storeBytes(array, offset, length, stream);
/* 1817 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1827 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, File file) throws IOException {
/* 1835 */     storeBytes(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, CharSequence filename) throws IOException {
/* 1843 */     storeBytes(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, PrintStream stream) {
/* 1851 */     for (; i.hasNext(); stream.println(i.nextByte()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, File file) throws IOException {
/* 1859 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1860 */     storeBytes(i, stream);
/* 1861 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, CharSequence filename) throws IOException {
/* 1869 */     storeBytes(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(BufferedReader reader, byte[][] array, long offset, long length) throws IOException {
/* 1880 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1881 */     long c = 0L;
/*      */     
/*      */     try {
/* 1884 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1885 */         byte[] t = array[i];
/* 1886 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1887 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1888 */           String s; if ((s = reader.readLine()) != null) { t[d] = Byte.parseByte(s.trim()); }
/* 1889 */           else { return c; }
/* 1890 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 1894 */     } catch (EOFException eOFException) {}
/* 1895 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(BufferedReader reader, byte[][] array) throws IOException {
/* 1904 */     return loadBytes(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(File file, byte[][] array, long offset, long length) throws IOException {
/* 1915 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1916 */     long result = loadBytes(reader, array, offset, length);
/* 1917 */     reader.close();
/* 1918 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(CharSequence filename, byte[][] array, long offset, long length) throws IOException {
/* 1929 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(File file, byte[][] array) throws IOException {
/* 1938 */     return loadBytes(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(CharSequence filename, byte[][] array) throws IOException {
/* 1947 */     return loadBytes(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, PrintStream stream) {
/* 1957 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1958 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1959 */       byte[] t = array[i];
/* 1960 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1961 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, PrintStream stream) {
/* 1970 */     storeBytes(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, File file) throws IOException {
/* 1980 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1981 */     storeBytes(array, offset, length, stream);
/* 1982 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1992 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, File file) throws IOException {
/* 2000 */     storeBytes(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, CharSequence filename) throws IOException {
/* 2008 */     storeBytes(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class ByteReaderWrapper implements ByteIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private byte next;
/*      */     
/*      */     public ByteReaderWrapper(BufferedReader reader) {
/* 2017 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2021 */       if (!this.toAdvance) return (this.s != null); 
/* 2022 */       this.toAdvance = false;
/*      */       
/* 2024 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 2026 */       catch (EOFException eOFException) {  }
/* 2027 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2028 */        if (this.s == null) return false; 
/* 2029 */       this.next = Byte.parseByte(this.s.trim());
/* 2030 */       return true;
/*      */     }
/*      */     
/*      */     public byte nextByte() {
/* 2034 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2035 */       this.toAdvance = true;
/* 2036 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(BufferedReader reader) {
/* 2044 */     return new ByteReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(File file) throws IOException {
/* 2051 */     return new ByteReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(CharSequence filename) throws IOException {
/* 2058 */     return asByteIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(File file) {
/* 2065 */     return () -> { try {
/*      */           return asByteIterator(file);
/* 2067 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(CharSequence filename) {
/* 2075 */     return () -> { try {
/*      */           return asByteIterator(filename);
/* 2077 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(BufferedReader reader, short[] array, int offset, int length) throws IOException {
/* 2129 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 2130 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 2133 */       for (i = 0; i < length && (
/* 2134 */         s = reader.readLine()) != null; i++) array[i + offset] = Short.parseShort(s.trim());
/*      */     
/*      */     }
/* 2137 */     catch (EOFException eOFException) {}
/* 2138 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(BufferedReader reader, short[] array) throws IOException {
/* 2147 */     return loadShorts(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, short[] array, int offset, int length) throws IOException {
/* 2158 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2159 */     int result = loadShorts(reader, array, offset, length);
/* 2160 */     reader.close();
/* 2161 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, short[] array, int offset, int length) throws IOException {
/* 2172 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, short[] array) throws IOException {
/* 2181 */     return loadShorts(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, short[] array) throws IOException {
/* 2190 */     return loadShorts(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, PrintStream stream) {
/* 2200 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 2201 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, PrintStream stream) {
/* 2209 */     storeShorts(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, File file) throws IOException {
/* 2219 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2220 */     storeShorts(array, offset, length, stream);
/* 2221 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2231 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, File file) throws IOException {
/* 2239 */     storeShorts(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, CharSequence filename) throws IOException {
/* 2247 */     storeShorts(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, PrintStream stream) {
/* 2255 */     for (; i.hasNext(); stream.println(i.nextShort()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, File file) throws IOException {
/* 2263 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2264 */     storeShorts(i, stream);
/* 2265 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, CharSequence filename) throws IOException {
/* 2273 */     storeShorts(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(BufferedReader reader, short[][] array, long offset, long length) throws IOException {
/* 2284 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2285 */     long c = 0L;
/*      */     
/*      */     try {
/* 2288 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2289 */         short[] t = array[i];
/* 2290 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2291 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2292 */           String s; if ((s = reader.readLine()) != null) { t[d] = Short.parseShort(s.trim()); }
/* 2293 */           else { return c; }
/* 2294 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 2298 */     } catch (EOFException eOFException) {}
/* 2299 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(BufferedReader reader, short[][] array) throws IOException {
/* 2308 */     return loadShorts(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, short[][] array, long offset, long length) throws IOException {
/* 2319 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2320 */     long result = loadShorts(reader, array, offset, length);
/* 2321 */     reader.close();
/* 2322 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, short[][] array, long offset, long length) throws IOException {
/* 2333 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, short[][] array) throws IOException {
/* 2342 */     return loadShorts(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, short[][] array) throws IOException {
/* 2351 */     return loadShorts(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, PrintStream stream) {
/* 2361 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2362 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2363 */       short[] t = array[i];
/* 2364 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2365 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, PrintStream stream) {
/* 2374 */     storeShorts(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, File file) throws IOException {
/* 2384 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2385 */     storeShorts(array, offset, length, stream);
/* 2386 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2396 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, File file) throws IOException {
/* 2404 */     storeShorts(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, CharSequence filename) throws IOException {
/* 2412 */     storeShorts(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class ShortReaderWrapper implements ShortIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private short next;
/*      */     
/*      */     public ShortReaderWrapper(BufferedReader reader) {
/* 2421 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2425 */       if (!this.toAdvance) return (this.s != null); 
/* 2426 */       this.toAdvance = false;
/*      */       
/* 2428 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 2430 */       catch (EOFException eOFException) {  }
/* 2431 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2432 */        if (this.s == null) return false; 
/* 2433 */       this.next = Short.parseShort(this.s.trim());
/* 2434 */       return true;
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 2438 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2439 */       this.toAdvance = true;
/* 2440 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(BufferedReader reader) {
/* 2448 */     return new ShortReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(File file) throws IOException {
/* 2455 */     return new ShortReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(CharSequence filename) throws IOException {
/* 2462 */     return asShortIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(File file) {
/* 2469 */     return () -> { try {
/*      */           return asShortIterator(file);
/* 2471 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(CharSequence filename) {
/* 2479 */     return () -> { try {
/*      */           return asShortIterator(filename);
/* 2481 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(BufferedReader reader, float[] array, int offset, int length) throws IOException {
/* 2533 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 2534 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 2537 */       for (i = 0; i < length && (
/* 2538 */         s = reader.readLine()) != null; i++) array[i + offset] = Float.parseFloat(s.trim());
/*      */     
/*      */     }
/* 2541 */     catch (EOFException eOFException) {}
/* 2542 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(BufferedReader reader, float[] array) throws IOException {
/* 2551 */     return loadFloats(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, float[] array, int offset, int length) throws IOException {
/* 2562 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2563 */     int result = loadFloats(reader, array, offset, length);
/* 2564 */     reader.close();
/* 2565 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, float[] array, int offset, int length) throws IOException {
/* 2576 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, float[] array) throws IOException {
/* 2585 */     return loadFloats(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, float[] array) throws IOException {
/* 2594 */     return loadFloats(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, PrintStream stream) {
/* 2604 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 2605 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, PrintStream stream) {
/* 2613 */     storeFloats(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, File file) throws IOException {
/* 2623 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2624 */     storeFloats(array, offset, length, stream);
/* 2625 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2635 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, File file) throws IOException {
/* 2643 */     storeFloats(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, CharSequence filename) throws IOException {
/* 2651 */     storeFloats(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, PrintStream stream) {
/* 2659 */     for (; i.hasNext(); stream.println(i.nextFloat()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, File file) throws IOException {
/* 2667 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2668 */     storeFloats(i, stream);
/* 2669 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, CharSequence filename) throws IOException {
/* 2677 */     storeFloats(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(BufferedReader reader, float[][] array, long offset, long length) throws IOException {
/* 2688 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2689 */     long c = 0L;
/*      */     
/*      */     try {
/* 2692 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2693 */         float[] t = array[i];
/* 2694 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2695 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2696 */           String s; if ((s = reader.readLine()) != null) { t[d] = Float.parseFloat(s.trim()); }
/* 2697 */           else { return c; }
/* 2698 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 2702 */     } catch (EOFException eOFException) {}
/* 2703 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(BufferedReader reader, float[][] array) throws IOException {
/* 2712 */     return loadFloats(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, float[][] array, long offset, long length) throws IOException {
/* 2723 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2724 */     long result = loadFloats(reader, array, offset, length);
/* 2725 */     reader.close();
/* 2726 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, float[][] array, long offset, long length) throws IOException {
/* 2737 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, float[][] array) throws IOException {
/* 2746 */     return loadFloats(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, float[][] array) throws IOException {
/* 2755 */     return loadFloats(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, PrintStream stream) {
/* 2765 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2766 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2767 */       float[] t = array[i];
/* 2768 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2769 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, PrintStream stream) {
/* 2778 */     storeFloats(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, File file) throws IOException {
/* 2788 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2789 */     storeFloats(array, offset, length, stream);
/* 2790 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2800 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, File file) throws IOException {
/* 2808 */     storeFloats(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, CharSequence filename) throws IOException {
/* 2816 */     storeFloats(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class FloatReaderWrapper implements FloatIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private float next;
/*      */     
/*      */     public FloatReaderWrapper(BufferedReader reader) {
/* 2825 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2829 */       if (!this.toAdvance) return (this.s != null); 
/* 2830 */       this.toAdvance = false;
/*      */       
/* 2832 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 2834 */       catch (EOFException eOFException) {  }
/* 2835 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2836 */        if (this.s == null) return false; 
/* 2837 */       this.next = Float.parseFloat(this.s.trim());
/* 2838 */       return true;
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 2842 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2843 */       this.toAdvance = true;
/* 2844 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(BufferedReader reader) {
/* 2852 */     return new FloatReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(File file) throws IOException {
/* 2859 */     return new FloatReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(CharSequence filename) throws IOException {
/* 2866 */     return asFloatIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(File file) {
/* 2873 */     return () -> { try {
/*      */           return asFloatIterator(file);
/* 2875 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(CharSequence filename) {
/* 2883 */     return () -> { try {
/*      */           return asFloatIterator(filename);
/* 2885 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\io\TextIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */