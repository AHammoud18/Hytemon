/*      */ package it.unimi.dsi.fastutil.io;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterable;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterable;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharBigArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterable;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharMappedBigList;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterable;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleMappedBigList;
/*      */ import it.unimi.dsi.fastutil.floats.FloatBigArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterable;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatMappedBigList;
/*      */ import it.unimi.dsi.fastutil.ints.IntBigArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterable;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntMappedBigList;
/*      */ import it.unimi.dsi.fastutil.longs.LongBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterable;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongMappedBigList;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterable;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortMappedBigList;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutput;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.LongBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.ReadableByteChannel;
/*      */ import java.nio.channels.WritableByteChannel;
/*      */ import java.nio.file.OpenOption;
/*      */ import java.nio.file.StandardOpenOption;
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
/*      */ public class BinIO
/*      */ {
/*   79 */   public static int BUFFER_SIZE = 8192;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_IO_LENGTH = 1048576;
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeObject(Object o, File file) throws IOException {
/*   88 */     ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*   89 */     oos.writeObject(o);
/*   90 */     oos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeObject(Object o, CharSequence filename) throws IOException {
/*   99 */     storeObject(o, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object loadObject(File file) throws IOException, ClassNotFoundException {
/*  108 */     ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(new FileInputStream(file)));
/*  109 */     Object result = ois.readObject();
/*  110 */     ois.close();
/*  111 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object loadObject(CharSequence filename) throws IOException, ClassNotFoundException {
/*  120 */     return loadObject(new File(filename.toString()));
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
/*      */   public static void storeObject(Object o, OutputStream s) throws IOException {
/*  133 */     ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(s));
/*  134 */     oos.writeObject(o);
/*  135 */     oos.flush();
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
/*      */   public static Object loadObject(InputStream s) throws IOException, ClassNotFoundException {
/*  151 */     ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(s));
/*  152 */     Object result = ois.readObject();
/*  153 */     return result;
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
/*      */ 
/*      */   
/*      */   public static int loadBooleans(DataInput dataInput, boolean[] array, int offset, int length) throws IOException {
/*  204 */     Arrays.ensureOffsetLength(array.length, offset, length);
/*  205 */     int i = 0;
/*      */     try {
/*  207 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readBoolean(); i++; }
/*      */     
/*  209 */     } catch (EOFException eOFException) {}
/*  210 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(DataInput dataInput, boolean[] array) throws IOException {
/*  219 */     int i = 0;
/*      */     try {
/*  221 */       int length = array.length;
/*  222 */       for (i = 0; i < length; ) { array[i] = dataInput.readBoolean(); i++; }
/*      */     
/*  224 */     } catch (EOFException eOFException) {}
/*  225 */     return i;
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
/*  236 */     Arrays.ensureOffsetLength(array.length, offset, length);
/*  237 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(new FileInputStream(file)));
/*  238 */     int i = 0;
/*      */     try {
/*  240 */       for (i = 0; i < length; ) { array[i + offset] = dis.readBoolean(); i++; }
/*      */     
/*  242 */     } catch (EOFException eOFException) {}
/*  243 */     dis.close();
/*  244 */     return i;
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
/*  255 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(File file, boolean[] array) throws IOException {
/*  264 */     return loadBooleans(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(CharSequence filename, boolean[] array) throws IOException {
/*  273 */     return loadBooleans(new File(filename.toString()), array);
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
/*      */   public static boolean[] loadBooleans(File file) throws IOException {
/*  285 */     FileInputStream fis = new FileInputStream(file);
/*  286 */     long length = fis.getChannel().size();
/*  287 */     if (length > 2147483647L) {
/*  288 */       fis.close();
/*  289 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/*  291 */     boolean[] array = new boolean[(int)length];
/*  292 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  293 */     for (int i = 0; i < length; ) { array[i] = dis.readBoolean(); i++; }
/*  294 */      dis.close();
/*  295 */     return array;
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
/*      */   public static boolean[] loadBooleans(CharSequence filename) throws IOException {
/*  307 */     return loadBooleans(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/*  317 */     Arrays.ensureOffsetLength(array.length, offset, length);
/*  318 */     for (int i = 0; i < length; ) { dataOutput.writeBoolean(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, DataOutput dataOutput) throws IOException {
/*  326 */     int length = array.length;
/*  327 */     for (int i = 0; i < length; ) { dataOutput.writeBoolean(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, File file) throws IOException {
/*  337 */     Arrays.ensureOffsetLength(array.length, offset, length);
/*  338 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  339 */     for (int i = 0; i < length; ) { dos.writeBoolean(array[offset + i]); i++; }
/*  340 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, CharSequence filename) throws IOException {
/*  350 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, File file) throws IOException {
/*  358 */     storeBooleans(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, CharSequence filename) throws IOException {
/*  366 */     storeBooleans(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(DataInput dataInput, boolean[][] array, long offset, long length) throws IOException {
/*  377 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  378 */     long c = 0L;
/*      */     try {
/*  380 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  381 */         boolean[] t = array[i];
/*  382 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  383 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  384 */           t[d] = dataInput.readBoolean();
/*  385 */           c++;
/*      */         }
/*      */       
/*      */       } 
/*  389 */     } catch (EOFException eOFException) {}
/*  390 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(DataInput dataInput, boolean[][] array) throws IOException {
/*  399 */     long c = 0L;
/*      */     try {
/*  401 */       for (int i = 0; i < array.length; i++) {
/*  402 */         boolean[] t = array[i];
/*  403 */         int l = t.length;
/*  404 */         for (int d = 0; d < l; d++) {
/*  405 */           t[d] = dataInput.readBoolean();
/*  406 */           c++;
/*      */         }
/*      */       
/*      */       } 
/*  410 */     } catch (EOFException eOFException) {}
/*  411 */     return c;
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
/*  422 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  423 */     FileInputStream fis = new FileInputStream(file);
/*  424 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  425 */     long c = 0L;
/*      */     try {
/*  427 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  428 */         boolean[] t = array[i];
/*  429 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  430 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  431 */           t[d] = dis.readBoolean();
/*  432 */           c++;
/*      */         }
/*      */       
/*      */       } 
/*  436 */     } catch (EOFException eOFException) {}
/*  437 */     dis.close();
/*  438 */     return c;
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
/*  449 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(File file, boolean[][] array) throws IOException {
/*  458 */     FileInputStream fis = new FileInputStream(file);
/*  459 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  460 */     long c = 0L;
/*      */     try {
/*  462 */       for (int i = 0; i < array.length; i++) {
/*  463 */         boolean[] t = array[i];
/*  464 */         int l = t.length;
/*  465 */         for (int d = 0; d < l; d++) {
/*  466 */           t[d] = dis.readBoolean();
/*  467 */           c++;
/*      */         }
/*      */       
/*      */       } 
/*  471 */     } catch (EOFException eOFException) {}
/*  472 */     dis.close();
/*  473 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(CharSequence filename, boolean[][] array) throws IOException {
/*  482 */     return loadBooleans(new File(filename.toString()), array);
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
/*      */   public static boolean[][] loadBooleansBig(File file) throws IOException {
/*  494 */     FileInputStream fis = new FileInputStream(file);
/*  495 */     long length = fis.getChannel().size();
/*  496 */     boolean[][] array = BooleanBigArrays.newBigArray(length);
/*  497 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  498 */     for (int i = 0; i < array.length; i++) {
/*  499 */       boolean[] t = array[i];
/*  500 */       int l = t.length;
/*  501 */       for (int d = 0; d < l; ) { t[d] = dis.readBoolean(); d++; }
/*      */     
/*  503 */     }  dis.close();
/*  504 */     return array;
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
/*      */   public static boolean[][] loadBooleansBig(CharSequence filename) throws IOException {
/*  516 */     return loadBooleansBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/*  526 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  527 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  528 */       boolean[] t = array[i];
/*  529 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  530 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeBoolean(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, DataOutput dataOutput) throws IOException {
/*  539 */     for (int i = 0; i < array.length; i++) {
/*  540 */       boolean[] t = array[i];
/*  541 */       int l = t.length;
/*  542 */       for (int d = 0; d < l; ) { dataOutput.writeBoolean(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, File file) throws IOException {
/*  553 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  554 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  555 */       boolean[] t = array[i];
/*  556 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  557 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeBoolean(t[d]); d++; }
/*      */     
/*  559 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, CharSequence filename) throws IOException {
/*  569 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, File file) throws IOException {
/*  577 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  578 */     for (int i = 0; i < array.length; i++) {
/*  579 */       boolean[] t = array[i];
/*  580 */       int l = t.length;
/*  581 */       for (int d = 0; d < l; ) { dos.writeBoolean(t[d]); d++; }
/*      */     
/*  583 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, CharSequence filename) throws IOException {
/*  591 */     storeBooleans(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, DataOutput dataOutput) throws IOException {
/*  599 */     for (; i.hasNext(); dataOutput.writeBoolean(i.nextBoolean()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, File file) throws IOException {
/*  607 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  608 */     for (; i.hasNext(); dos.writeBoolean(i.nextBoolean()));
/*  609 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, CharSequence filename) throws IOException {
/*  617 */     storeBooleans(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class BooleanDataInputWrapper implements BooleanIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private boolean next;
/*      */     
/*      */     public BooleanDataInputWrapper(DataInput dataInput) {
/*  626 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  630 */       if (!this.toAdvance) return !this.endOfProcess; 
/*  631 */       this.toAdvance = false; 
/*  632 */       try { this.next = this.dataInput.readBoolean(); }
/*  633 */       catch (EOFException eof) { this.endOfProcess = true; }
/*  634 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/*  635 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public boolean nextBoolean() {
/*  639 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  640 */       this.toAdvance = true;
/*  641 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(DataInput dataInput) {
/*  649 */     return new BooleanDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(File file) throws IOException {
/*  659 */     return new BooleanDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(CharSequence filename) throws IOException {
/*  669 */     return asBooleanIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(File file) {
/*  679 */     return () -> { try {
/*      */           return asBooleanIterator(file);
/*  681 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(CharSequence filename) {
/*  692 */     return () -> { try {
/*      */           return asBooleanIterator(filename);
/*  694 */         } catch (IOException e) {
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
/*      */   private static int read(InputStream is, byte[] a, int offset, int length) throws IOException {
/*  740 */     if (length == 0) return 0; 
/*  741 */     int read = 0;
/*      */     while (true) {
/*  743 */       int result = is.read(a, offset + read, Math.min(length - read, 1048576));
/*  744 */       if (result < 0) return read; 
/*  745 */       read += result;
/*  746 */       if (read >= length)
/*  747 */         return read; 
/*      */     } 
/*      */   } private static void write(OutputStream outputStream, byte[] a, int offset, int length) throws IOException {
/*  750 */     int written = 0;
/*  751 */     while (written < length) {
/*  752 */       outputStream.write(a, offset + written, Math.min(length - written, 1048576));
/*  753 */       written += Math.min(length - written, 1048576);
/*      */     } 
/*      */   }
/*      */   private static void write(DataOutput dataOutput, byte[] a, int offset, int length) throws IOException {
/*  757 */     int written = 0;
/*  758 */     while (written < length) {
/*  759 */       dataOutput.write(a, offset + written, Math.min(length - written, 1048576));
/*  760 */       written += Math.min(length - written, 1048576);
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
/*      */   public static int loadBytes(InputStream inputStream, byte[] array, int offset, int length) throws IOException {
/*  776 */     return read(inputStream, array, offset, length);
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
/*      */   public static int loadBytes(InputStream inputStream, byte[] array) throws IOException {
/*  788 */     return read(inputStream, array, 0, array.length);
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
/*      */   public static void storeBytes(byte[] array, int offset, int length, OutputStream outputStream) throws IOException {
/*  801 */     write(outputStream, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, OutputStream outputStream) throws IOException {
/*  812 */     write(outputStream, array, 0, array.length);
/*      */   }
/*      */   private static long read(InputStream is, byte[][] a, long offset, long length) throws IOException {
/*  815 */     if (length == 0L) return 0L; 
/*  816 */     long read = 0L;
/*  817 */     int segment = BigArrays.segment(offset);
/*  818 */     int displacement = BigArrays.displacement(offset);
/*      */     
/*      */     while (true) {
/*  821 */       int result = is.read(a[segment], displacement, (int)Math.min(((a[segment]).length - displacement), Math.min(length - read, 1048576L)));
/*  822 */       if (result < 0) return read; 
/*  823 */       read += result;
/*  824 */       displacement += result;
/*  825 */       if (displacement == (a[segment]).length) {
/*  826 */         segment++;
/*  827 */         displacement = 0;
/*      */       } 
/*  829 */       if (read >= length)
/*  830 */         return read; 
/*      */     } 
/*      */   } private static void write(OutputStream outputStream, byte[][] a, long offset, long length) throws IOException {
/*  833 */     if (length == 0L)
/*  834 */       return;  long written = 0L;
/*      */     
/*  836 */     int segment = BigArrays.segment(offset);
/*  837 */     int displacement = BigArrays.displacement(offset);
/*      */     do {
/*  839 */       int toWrite = (int)Math.min(((a[segment]).length - displacement), Math.min(length - written, 1048576L));
/*  840 */       outputStream.write(a[segment], displacement, toWrite);
/*  841 */       written += toWrite;
/*  842 */       displacement += toWrite;
/*  843 */       if (displacement != (a[segment]).length)
/*  844 */         continue;  segment++;
/*  845 */       displacement = 0;
/*      */     }
/*  847 */     while (written < length);
/*      */   }
/*      */   private static void write(DataOutput dataOutput, byte[][] a, long offset, long length) throws IOException {
/*  850 */     if (length == 0L)
/*  851 */       return;  long written = 0L;
/*      */     
/*  853 */     int segment = BigArrays.segment(offset);
/*  854 */     int displacement = BigArrays.displacement(offset);
/*      */     do {
/*  856 */       int toWrite = (int)Math.min(((a[segment]).length - displacement), Math.min(length - written, 1048576L));
/*  857 */       dataOutput.write(a[segment], displacement, toWrite);
/*  858 */       written += toWrite;
/*  859 */       displacement += toWrite;
/*  860 */       if (displacement != (a[segment]).length)
/*  861 */         continue;  segment++;
/*  862 */       displacement = 0;
/*      */     }
/*  864 */     while (written < length);
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
/*      */   public static long loadBytes(InputStream inputStream, byte[][] array, long offset, long length) throws IOException {
/*  879 */     return read(inputStream, array, offset, length);
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
/*      */   public static long loadBytes(InputStream inputStream, byte[][] array) throws IOException {
/*  891 */     return read(inputStream, array, 0L, BigArrays.length(array));
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
/*      */   public static void storeBytes(byte[][] array, long offset, long length, OutputStream outputStream) throws IOException {
/*  904 */     write(outputStream, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, OutputStream outputStream) throws IOException {
/*  915 */     write(outputStream, array, 0L, BigArrays.length(array));
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
/*      */   public static int loadBytes(ReadableByteChannel channel, byte[] array, int offset, int length) throws IOException {
/*  930 */     Arrays.ensureOffsetLength(array.length, offset, length);
/*  931 */     ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
/*  932 */     int read = 0;
/*      */     while (true) {
/*  934 */       buffer.clear();
/*  935 */       buffer.limit(Math.min(buffer.capacity(), length));
/*  936 */       int r = channel.read(buffer);
/*  937 */       if (r <= 0) return read; 
/*  938 */       read += r;
/*  939 */       buffer.flip();
/*  940 */       buffer.get(array, offset, r);
/*  941 */       offset += r;
/*  942 */       length -= r;
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
/*      */   public static int loadBytes(ReadableByteChannel channel, byte[] array) throws IOException {
/*  955 */     return loadBytes(channel, array, 0, array.length);
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
/*      */   public static void storeBytes(byte[] array, int offset, int length, WritableByteChannel channel) throws IOException {
/*  968 */     Arrays.ensureOffsetLength(array.length, offset, length);
/*  969 */     ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
/*  970 */     while (length != 0) {
/*  971 */       int l = Math.min(length, buffer.capacity());
/*  972 */       buffer.clear();
/*  973 */       buffer.put(array, offset, l);
/*  974 */       buffer.flip();
/*  975 */       channel.write(buffer);
/*  976 */       offset += l;
/*  977 */       length -= l;
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
/*      */   public static void storeBytes(byte[] array, WritableByteChannel channel) throws IOException {
/*  989 */     storeBytes(array, 0, array.length, channel);
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
/*      */   public static long loadBytes(ReadableByteChannel channel, byte[][] array, long offset, long length) throws IOException {
/* 1003 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1004 */     long read = 0L;
/* 1005 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1006 */       byte[] t = array[i];
/* 1007 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 1008 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1009 */       int r = loadBytes(channel, t, s, e - s);
/* 1010 */       read += r;
/* 1011 */       if (r < e - s)
/*      */         break; 
/* 1013 */     }  return read;
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
/*      */   public static long loadBytes(ReadableByteChannel channel, byte[][] array) throws IOException {
/* 1025 */     return loadBytes(channel, array, 0L, BigArrays.length(array));
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
/*      */   public static void storeBytes(byte[][] array, long offset, long length, WritableByteChannel channel) throws IOException {
/* 1038 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1039 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 1040 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 1041 */       storeBytes(array[i], s, l - s, channel);
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
/*      */   public static void storeBytes(byte[][] array, WritableByteChannel channel) throws IOException {
/* 1053 */     for (byte[] t : array) storeBytes(t, channel);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(DataInput dataInput, byte[] array, int offset, int length) throws IOException {
/* 1064 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 1065 */     int i = 0;
/*      */     try {
/* 1067 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readByte(); i++; }
/*      */     
/* 1069 */     } catch (EOFException eOFException) {}
/* 1070 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(DataInput dataInput, byte[] array) throws IOException {
/* 1079 */     int i = 0;
/*      */     try {
/* 1081 */       int length = array.length;
/* 1082 */       for (i = 0; i < length; ) { array[i] = dataInput.readByte(); i++; }
/*      */     
/* 1084 */     } catch (EOFException eOFException) {}
/* 1085 */     return i;
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
/* 1096 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1097 */     int result = loadBytes(channel, array, offset, length);
/* 1098 */     channel.close();
/* 1099 */     return result;
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
/* 1110 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(File file, byte[] array) throws IOException {
/* 1119 */     return loadBytes(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(CharSequence filename, byte[] array) throws IOException {
/* 1128 */     return loadBytes(new File(filename.toString()), array);
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
/*      */   public static byte[] loadBytes(File file) throws IOException {
/* 1140 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1141 */     long length = channel.size();
/* 1142 */     if (length > 2147483647L) {
/* 1143 */       channel.close();
/* 1144 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 1146 */     byte[] array = new byte[(int)length];
/* 1147 */     if (loadBytes(channel, array) < length) throw new EOFException(); 
/* 1148 */     return array;
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
/*      */   public static byte[] loadBytes(CharSequence filename) throws IOException {
/* 1160 */     return loadBytes(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 1170 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 1171 */     write(dataOutput, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, DataOutput dataOutput) throws IOException {
/* 1179 */     write(dataOutput, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, File file) throws IOException {
/* 1189 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1190 */     storeBytes(array, offset, length, channel);
/* 1191 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1201 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, File file) throws IOException {
/* 1209 */     storeBytes(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, CharSequence filename) throws IOException {
/* 1217 */     storeBytes(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(DataInput dataInput, byte[][] array, long offset, long length) throws IOException {
/* 1228 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1229 */     long c = 0L;
/*      */     try {
/* 1231 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1232 */         byte[] t = array[i];
/* 1233 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1234 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1235 */           t[d] = dataInput.readByte();
/* 1236 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1240 */     } catch (EOFException eOFException) {}
/* 1241 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(DataInput dataInput, byte[][] array) throws IOException {
/* 1250 */     long c = 0L;
/*      */     try {
/* 1252 */       for (int i = 0; i < array.length; i++) {
/* 1253 */         byte[] t = array[i];
/* 1254 */         int l = t.length;
/* 1255 */         for (int d = 0; d < l; d++) {
/* 1256 */           t[d] = dataInput.readByte();
/* 1257 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1261 */     } catch (EOFException eOFException) {}
/* 1262 */     return c;
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
/* 1273 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1274 */     long read = loadBytes(channel, array, offset, length);
/* 1275 */     return read;
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
/* 1286 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(File file, byte[][] array) throws IOException {
/* 1295 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1296 */     long read = loadBytes(channel, array);
/* 1297 */     return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(CharSequence filename, byte[][] array) throws IOException {
/* 1306 */     return loadBytes(new File(filename.toString()), array);
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
/*      */   public static byte[][] loadBytesBig(File file) throws IOException {
/* 1318 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1319 */     long length = channel.size();
/* 1320 */     byte[][] array = ByteBigArrays.newBigArray(length);
/* 1321 */     loadBytes(channel, array);
/* 1322 */     channel.close();
/* 1323 */     return array;
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
/*      */   public static byte[][] loadBytesBig(CharSequence filename) throws IOException {
/* 1335 */     return loadBytesBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 1345 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1346 */     write(dataOutput, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, DataOutput dataOutput) throws IOException {
/* 1354 */     write(dataOutput, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, File file) throws IOException {
/* 1364 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1365 */     storeBytes(array, offset, length, channel);
/* 1366 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1376 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, File file) throws IOException {
/* 1384 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1385 */     storeBytes(array, channel);
/* 1386 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, CharSequence filename) throws IOException {
/* 1394 */     storeBytes(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, DataOutput dataOutput) throws IOException {
/* 1402 */     for (; i.hasNext(); dataOutput.writeByte(i.nextByte()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, File file) throws IOException {
/* 1410 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1411 */     for (; i.hasNext(); dos.writeByte(i.nextByte()));
/* 1412 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, CharSequence filename) throws IOException {
/* 1420 */     storeBytes(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class ByteDataInputWrapper implements ByteIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private byte next;
/*      */     
/*      */     public ByteDataInputWrapper(DataInput dataInput) {
/* 1429 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1433 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 1434 */       this.toAdvance = false; 
/* 1435 */       try { this.next = this.dataInput.readByte(); }
/* 1436 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 1437 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1438 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public byte nextByte() {
/* 1442 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1443 */       this.toAdvance = true;
/* 1444 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(DataInput dataInput) {
/* 1452 */     return new ByteDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(File file) throws IOException {
/* 1462 */     return new ByteDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(CharSequence filename) throws IOException {
/* 1472 */     return asByteIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(File file) {
/* 1482 */     return () -> { try {
/*      */           return asByteIterator(file);
/* 1484 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(CharSequence filename) {
/* 1495 */     return () -> { try {
/*      */           return asByteIterator(filename);
/* 1497 */         } catch (IOException e) {
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
/*      */   
/*      */   public static int loadChars(ReadableByteChannel channel, ByteOrder byteOrder, char[] array, int offset, int length) throws IOException {
/* 1550 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 1551 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 1552 */     CharBuffer buffer = byteBuffer.asCharBuffer();
/* 1553 */     int read = 0;
/*      */     while (true) {
/* 1555 */       byteBuffer.clear();
/* 1556 */       byteBuffer.limit((int)Math.min(buffer.capacity(), length << CharMappedBigList.LOG2_BYTES));
/* 1557 */       int r = channel.read(byteBuffer);
/* 1558 */       if (r <= 0) return read; 
/* 1559 */       r >>>= CharMappedBigList.LOG2_BYTES;
/* 1560 */       read += r;
/*      */       
/* 1562 */       buffer.clear();
/* 1563 */       buffer.limit(r);
/* 1564 */       buffer.get(array, offset, r);
/* 1565 */       offset += r;
/* 1566 */       length -= r;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(ReadableByteChannel channel, ByteOrder byteOrder, char[] array) throws IOException {
/* 1577 */     return loadChars(channel, byteOrder, array, 0, array.length);
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
/*      */   public static int loadChars(File file, ByteOrder byteOrder, char[] array, int offset, int length) throws IOException {
/* 1589 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 1590 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1591 */     int read = loadChars(channel, byteOrder, array, offset, length);
/* 1592 */     channel.close();
/* 1593 */     return read;
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
/*      */   public static int loadChars(CharSequence filename, ByteOrder byteOrder, char[] array, int offset, int length) throws IOException {
/* 1605 */     return loadChars(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(File file, ByteOrder byteOrder, char[] array) throws IOException {
/* 1615 */     return loadChars(file, byteOrder, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(CharSequence filename, ByteOrder byteOrder, char[] array) throws IOException {
/* 1625 */     return loadChars(new File(filename.toString()), byteOrder, array);
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
/*      */   public static char[] loadChars(File file, ByteOrder byteOrder) throws IOException {
/* 1638 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1639 */     long length = channel.size() / 2L;
/* 1640 */     if (length > 2147483647L) {
/* 1641 */       channel.close();
/* 1642 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 1644 */     char[] array = new char[(int)length];
/* 1645 */     if (loadChars(channel, byteOrder, array) < length) throw new EOFException(); 
/* 1646 */     channel.close();
/* 1647 */     return array;
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
/*      */   public static char[] loadChars(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1659 */     return loadChars(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 1670 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 1671 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 1672 */     CharBuffer buffer = byteBuffer.asCharBuffer();
/* 1673 */     while (length != 0) {
/* 1674 */       int l = Math.min(length, buffer.capacity());
/* 1675 */       buffer.clear();
/* 1676 */       buffer.put(array, offset, l);
/* 1677 */       buffer.flip();
/* 1678 */       byteBuffer.clear();
/* 1679 */       byteBuffer.limit(buffer.limit() << CharMappedBigList.LOG2_BYTES);
/* 1680 */       channel.write(byteBuffer);
/* 1681 */       offset += l;
/* 1682 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 1692 */     storeChars(array, 0, array.length, channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, File file, ByteOrder byteOrder) throws IOException {
/* 1703 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1704 */     storeChars(array, offset, length, channel, byteOrder);
/* 1705 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1716 */     storeChars(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, File file, ByteOrder byteOrder) throws IOException {
/* 1725 */     storeChars(array, 0, array.length, file, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1734 */     storeChars(array, new File(filename.toString()), byteOrder);
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
/*      */   public static long loadChars(ReadableByteChannel channel, ByteOrder byteOrder, char[][] array, long offset, long length) throws IOException {
/* 1746 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1747 */     long read = 0L;
/* 1748 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1749 */       char[] t = array[i];
/* 1750 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 1751 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1752 */       int r = loadChars(channel, byteOrder, t, s, e - s);
/* 1753 */       read += r;
/* 1754 */       if (r < e - s)
/*      */         break; 
/* 1756 */     }  return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(ReadableByteChannel channel, ByteOrder byteOrder, char[][] array) throws IOException {
/* 1766 */     return loadChars(channel, byteOrder, array, 0L, BigArrays.length(array));
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
/*      */   public static long loadChars(File file, ByteOrder byteOrder, char[][] array, long offset, long length) throws IOException {
/* 1778 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1779 */     long read = loadChars(channel, byteOrder, array, offset, length);
/* 1780 */     channel.close();
/* 1781 */     return read;
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
/*      */   public static long loadChars(CharSequence filename, ByteOrder byteOrder, char[][] array, long offset, long length) throws IOException {
/* 1793 */     return loadChars(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(File file, ByteOrder byteOrder, char[][] array) throws IOException {
/* 1803 */     return loadChars(file, byteOrder, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(CharSequence filename, ByteOrder byteOrder, char[][] array) throws IOException {
/* 1813 */     return loadChars(new File(filename.toString()), byteOrder, array);
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
/*      */   public static char[][] loadCharsBig(File file, ByteOrder byteOrder) throws IOException {
/* 1826 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 1827 */     long length = channel.size() / 2L;
/* 1828 */     char[][] array = CharBigArrays.newBigArray(length);
/* 1829 */     for (char[] t : array) loadChars(channel, byteOrder, t); 
/* 1830 */     channel.close();
/* 1831 */     return array;
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
/*      */   public static char[][] loadCharsBig(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1844 */     return loadCharsBig(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 1855 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1856 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 1857 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 1858 */       storeChars(array[i], s, l - s, channel, byteOrder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 1868 */     for (char[] t : array) storeChars(t, channel, byteOrder);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, File file, ByteOrder byteOrder) throws IOException {
/* 1879 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1880 */     storeChars(array, offset, length, channel, byteOrder);
/* 1881 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1892 */     storeChars(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, File file, ByteOrder byteOrder) throws IOException {
/* 1901 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1902 */     storeChars(array, channel, byteOrder);
/* 1903 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1912 */     storeChars(array, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 1921 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 1922 */     CharBuffer buffer = byteBuffer.asCharBuffer();
/* 1923 */     while (i.hasNext()) {
/* 1924 */       if (!buffer.hasRemaining()) {
/* 1925 */         buffer.flip();
/* 1926 */         byteBuffer.clear();
/* 1927 */         byteBuffer.limit(buffer.limit() << CharMappedBigList.LOG2_BYTES);
/* 1928 */         channel.write(byteBuffer);
/* 1929 */         buffer.clear();
/*      */       } 
/* 1931 */       buffer.put(i.nextChar());
/*      */     } 
/* 1933 */     buffer.flip();
/* 1934 */     byteBuffer.clear();
/* 1935 */     byteBuffer.limit(buffer.limit() << CharMappedBigList.LOG2_BYTES);
/* 1936 */     channel.write(byteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, File file, ByteOrder byteOrder) throws IOException {
/* 1945 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 1946 */     storeChars(i, channel, byteOrder);
/* 1947 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 1956 */     storeChars(i, new File(filename.toString()), byteOrder);
/*      */   }
/*      */   
/*      */   private static final class CharDataNioInputWrapper implements CharIterator { private final ReadableByteChannel channel;
/*      */     private final ByteBuffer byteBuffer;
/*      */     private final CharBuffer buffer;
/*      */     
/*      */     public CharDataNioInputWrapper(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 1964 */       this.channel = channel;
/* 1965 */       this.byteBuffer = ByteBuffer.allocateDirect(BinIO.BUFFER_SIZE).order(byteOrder);
/* 1966 */       this.buffer = this.byteBuffer.asCharBuffer();
/* 1967 */       this.buffer.clear().flip();
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1971 */       if (!this.buffer.hasRemaining()) {
/* 1972 */         this.byteBuffer.clear();
/*      */         try {
/* 1974 */           this.channel.read(this.byteBuffer);
/* 1975 */         } catch (IOException e) {
/* 1976 */           throw new RuntimeException(e);
/*      */         } 
/* 1978 */         this.byteBuffer.flip();
/* 1979 */         this.buffer.clear();
/* 1980 */         this.buffer.limit(this.byteBuffer.limit() >>> CharMappedBigList.LOG2_BYTES);
/*      */       } 
/* 1982 */       return this.buffer.hasRemaining();
/*      */     }
/*      */     
/*      */     public char nextChar() {
/* 1986 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1987 */       return this.buffer.get();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 1996 */     return new CharDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(File file, ByteOrder byteOrder) throws IOException {
/* 2007 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 2008 */     return new CharDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2019 */     return asCharIterator(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterable asCharIterable(File file, ByteOrder byteOrder) {
/* 2030 */     return () -> { try {
/*      */           return asCharIterator(file, byteOrder);
/* 2032 */         } catch (IOException e) {
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
/*      */   public static CharIterable asCharIterable(CharSequence filename, ByteOrder byteOrder) {
/* 2044 */     return () -> { try {
/*      */           return asCharIterator(filename, byteOrder);
/* 2046 */         } catch (IOException e) {
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
/*      */   public static int loadChars(DataInput dataInput, char[] array, int offset, int length) throws IOException {
/* 2058 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 2059 */     int i = 0;
/*      */     try {
/* 2061 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readChar(); i++; }
/*      */     
/* 2063 */     } catch (EOFException eOFException) {}
/* 2064 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(DataInput dataInput, char[] array) throws IOException {
/* 2073 */     int i = 0;
/*      */     try {
/* 2075 */       int length = array.length;
/* 2076 */       for (i = 0; i < length; ) { array[i] = dataInput.readChar(); i++; }
/*      */     
/* 2078 */     } catch (EOFException eOFException) {}
/* 2079 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(File file, char[] array, int offset, int length) throws IOException {
/* 2090 */     return loadChars(file, ByteOrder.BIG_ENDIAN, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(CharSequence filename, char[] array, int offset, int length) throws IOException {
/* 2101 */     return loadChars(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(File file, char[] array) throws IOException {
/* 2110 */     return loadChars(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(CharSequence filename, char[] array) throws IOException {
/* 2119 */     return loadChars(new File(filename.toString()), array);
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
/*      */   public static char[] loadChars(File file) throws IOException {
/* 2131 */     return loadChars(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static char[] loadChars(CharSequence filename) throws IOException {
/* 2143 */     return loadChars(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 2153 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 2154 */     for (int i = 0; i < length; ) { dataOutput.writeChar(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, DataOutput dataOutput) throws IOException {
/* 2162 */     int length = array.length;
/* 2163 */     for (int i = 0; i < length; ) { dataOutput.writeChar(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, File file) throws IOException {
/* 2173 */     storeChars(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2183 */     storeChars(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, File file) throws IOException {
/* 2191 */     storeChars(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, CharSequence filename) throws IOException {
/* 2199 */     storeChars(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(DataInput dataInput, char[][] array, long offset, long length) throws IOException {
/* 2210 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2211 */     long c = 0L;
/*      */     try {
/* 2213 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2214 */         char[] t = array[i];
/* 2215 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2216 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2217 */           t[d] = dataInput.readChar();
/* 2218 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2222 */     } catch (EOFException eOFException) {}
/* 2223 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(DataInput dataInput, char[][] array) throws IOException {
/* 2232 */     long c = 0L;
/*      */     try {
/* 2234 */       for (int i = 0; i < array.length; i++) {
/* 2235 */         char[] t = array[i];
/* 2236 */         int l = t.length;
/* 2237 */         for (int d = 0; d < l; d++) {
/* 2238 */           t[d] = dataInput.readChar();
/* 2239 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2243 */     } catch (EOFException eOFException) {}
/* 2244 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(File file, char[][] array, long offset, long length) throws IOException {
/* 2255 */     return loadChars(file, ByteOrder.BIG_ENDIAN, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(CharSequence filename, char[][] array, long offset, long length) throws IOException {
/* 2266 */     return loadChars(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(File file, char[][] array) throws IOException {
/* 2275 */     return loadChars(file, ByteOrder.BIG_ENDIAN, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(CharSequence filename, char[][] array) throws IOException {
/* 2284 */     return loadChars(new File(filename.toString()), array);
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
/*      */   public static char[][] loadCharsBig(File file) throws IOException {
/* 2296 */     return loadCharsBig(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static char[][] loadCharsBig(CharSequence filename) throws IOException {
/* 2308 */     return loadCharsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 2318 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2319 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2320 */       char[] t = array[i];
/* 2321 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2322 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeChar(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, DataOutput dataOutput) throws IOException {
/* 2331 */     for (int i = 0; i < array.length; i++) {
/* 2332 */       char[] t = array[i];
/* 2333 */       int l = t.length;
/* 2334 */       for (int d = 0; d < l; ) { dataOutput.writeChar(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, File file) throws IOException {
/* 2345 */     storeChars(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2355 */     storeChars(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, File file) throws IOException {
/* 2363 */     storeChars(array, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, CharSequence filename) throws IOException {
/* 2371 */     storeChars(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, DataOutput dataOutput) throws IOException {
/* 2379 */     for (; i.hasNext(); dataOutput.writeChar(i.nextChar()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, File file) throws IOException {
/* 2387 */     storeChars(i, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, CharSequence filename) throws IOException {
/* 2395 */     storeChars(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class CharDataInputWrapper implements CharIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private char next;
/*      */     
/*      */     public CharDataInputWrapper(DataInput dataInput) {
/* 2404 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2408 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 2409 */       this.toAdvance = false; 
/* 2410 */       try { this.next = this.dataInput.readChar(); }
/* 2411 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 2412 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2413 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public char nextChar() {
/* 2417 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2418 */       this.toAdvance = true;
/* 2419 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(DataInput dataInput) {
/* 2427 */     return new CharDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(File file) throws IOException {
/* 2437 */     return asCharIterator(file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(CharSequence filename) throws IOException {
/* 2447 */     return asCharIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterable asCharIterable(File file) {
/* 2457 */     return () -> { try {
/*      */           return asCharIterator(file);
/* 2459 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterable asCharIterable(CharSequence filename) {
/* 2470 */     return () -> { try {
/*      */           return asCharIterator(filename);
/* 2472 */         } catch (IOException e) {
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
/*      */   
/*      */   public static int loadShorts(ReadableByteChannel channel, ByteOrder byteOrder, short[] array, int offset, int length) throws IOException {
/* 2525 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 2526 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 2527 */     ShortBuffer buffer = byteBuffer.asShortBuffer();
/* 2528 */     int read = 0;
/*      */     while (true) {
/* 2530 */       byteBuffer.clear();
/* 2531 */       byteBuffer.limit((int)Math.min(buffer.capacity(), length << ShortMappedBigList.LOG2_BYTES));
/* 2532 */       int r = channel.read(byteBuffer);
/* 2533 */       if (r <= 0) return read; 
/* 2534 */       r >>>= ShortMappedBigList.LOG2_BYTES;
/* 2535 */       read += r;
/*      */       
/* 2537 */       buffer.clear();
/* 2538 */       buffer.limit(r);
/* 2539 */       buffer.get(array, offset, r);
/* 2540 */       offset += r;
/* 2541 */       length -= r;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(ReadableByteChannel channel, ByteOrder byteOrder, short[] array) throws IOException {
/* 2552 */     return loadShorts(channel, byteOrder, array, 0, array.length);
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
/*      */   public static int loadShorts(File file, ByteOrder byteOrder, short[] array, int offset, int length) throws IOException {
/* 2564 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 2565 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 2566 */     int read = loadShorts(channel, byteOrder, array, offset, length);
/* 2567 */     channel.close();
/* 2568 */     return read;
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
/*      */   public static int loadShorts(CharSequence filename, ByteOrder byteOrder, short[] array, int offset, int length) throws IOException {
/* 2580 */     return loadShorts(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, ByteOrder byteOrder, short[] array) throws IOException {
/* 2590 */     return loadShorts(file, byteOrder, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, ByteOrder byteOrder, short[] array) throws IOException {
/* 2600 */     return loadShorts(new File(filename.toString()), byteOrder, array);
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
/*      */   public static short[] loadShorts(File file, ByteOrder byteOrder) throws IOException {
/* 2613 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 2614 */     long length = channel.size() / 2L;
/* 2615 */     if (length > 2147483647L) {
/* 2616 */       channel.close();
/* 2617 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 2619 */     short[] array = new short[(int)length];
/* 2620 */     if (loadShorts(channel, byteOrder, array) < length) throw new EOFException(); 
/* 2621 */     channel.close();
/* 2622 */     return array;
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
/*      */   public static short[] loadShorts(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2634 */     return loadShorts(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 2645 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 2646 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 2647 */     ShortBuffer buffer = byteBuffer.asShortBuffer();
/* 2648 */     while (length != 0) {
/* 2649 */       int l = Math.min(length, buffer.capacity());
/* 2650 */       buffer.clear();
/* 2651 */       buffer.put(array, offset, l);
/* 2652 */       buffer.flip();
/* 2653 */       byteBuffer.clear();
/* 2654 */       byteBuffer.limit(buffer.limit() << ShortMappedBigList.LOG2_BYTES);
/* 2655 */       channel.write(byteBuffer);
/* 2656 */       offset += l;
/* 2657 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 2667 */     storeShorts(array, 0, array.length, channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, File file, ByteOrder byteOrder) throws IOException {
/* 2678 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 2679 */     storeShorts(array, offset, length, channel, byteOrder);
/* 2680 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2691 */     storeShorts(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, File file, ByteOrder byteOrder) throws IOException {
/* 2700 */     storeShorts(array, 0, array.length, file, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2709 */     storeShorts(array, new File(filename.toString()), byteOrder);
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
/*      */   public static long loadShorts(ReadableByteChannel channel, ByteOrder byteOrder, short[][] array, long offset, long length) throws IOException {
/* 2721 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2722 */     long read = 0L;
/* 2723 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2724 */       short[] t = array[i];
/* 2725 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 2726 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2727 */       int r = loadShorts(channel, byteOrder, t, s, e - s);
/* 2728 */       read += r;
/* 2729 */       if (r < e - s)
/*      */         break; 
/* 2731 */     }  return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(ReadableByteChannel channel, ByteOrder byteOrder, short[][] array) throws IOException {
/* 2741 */     return loadShorts(channel, byteOrder, array, 0L, BigArrays.length(array));
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
/*      */   public static long loadShorts(File file, ByteOrder byteOrder, short[][] array, long offset, long length) throws IOException {
/* 2753 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 2754 */     long read = loadShorts(channel, byteOrder, array, offset, length);
/* 2755 */     channel.close();
/* 2756 */     return read;
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
/*      */   public static long loadShorts(CharSequence filename, ByteOrder byteOrder, short[][] array, long offset, long length) throws IOException {
/* 2768 */     return loadShorts(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, ByteOrder byteOrder, short[][] array) throws IOException {
/* 2778 */     return loadShorts(file, byteOrder, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, ByteOrder byteOrder, short[][] array) throws IOException {
/* 2788 */     return loadShorts(new File(filename.toString()), byteOrder, array);
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
/*      */   public static short[][] loadShortsBig(File file, ByteOrder byteOrder) throws IOException {
/* 2801 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 2802 */     long length = channel.size() / 2L;
/* 2803 */     short[][] array = ShortBigArrays.newBigArray(length);
/* 2804 */     for (short[] t : array) loadShorts(channel, byteOrder, t); 
/* 2805 */     channel.close();
/* 2806 */     return array;
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
/*      */   public static short[][] loadShortsBig(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2819 */     return loadShortsBig(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 2830 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2831 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 2832 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 2833 */       storeShorts(array[i], s, l - s, channel, byteOrder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 2843 */     for (short[] t : array) storeShorts(t, channel, byteOrder);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, File file, ByteOrder byteOrder) throws IOException {
/* 2854 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 2855 */     storeShorts(array, offset, length, channel, byteOrder);
/* 2856 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2867 */     storeShorts(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, File file, ByteOrder byteOrder) throws IOException {
/* 2876 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 2877 */     storeShorts(array, channel, byteOrder);
/* 2878 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2887 */     storeShorts(array, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 2896 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 2897 */     ShortBuffer buffer = byteBuffer.asShortBuffer();
/* 2898 */     while (i.hasNext()) {
/* 2899 */       if (!buffer.hasRemaining()) {
/* 2900 */         buffer.flip();
/* 2901 */         byteBuffer.clear();
/* 2902 */         byteBuffer.limit(buffer.limit() << ShortMappedBigList.LOG2_BYTES);
/* 2903 */         channel.write(byteBuffer);
/* 2904 */         buffer.clear();
/*      */       } 
/* 2906 */       buffer.put(i.nextShort());
/*      */     } 
/* 2908 */     buffer.flip();
/* 2909 */     byteBuffer.clear();
/* 2910 */     byteBuffer.limit(buffer.limit() << ShortMappedBigList.LOG2_BYTES);
/* 2911 */     channel.write(byteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, File file, ByteOrder byteOrder) throws IOException {
/* 2920 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 2921 */     storeShorts(i, channel, byteOrder);
/* 2922 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2931 */     storeShorts(i, new File(filename.toString()), byteOrder);
/*      */   }
/*      */   
/*      */   private static final class ShortDataNioInputWrapper implements ShortIterator { private final ReadableByteChannel channel;
/*      */     private final ByteBuffer byteBuffer;
/*      */     private final ShortBuffer buffer;
/*      */     
/*      */     public ShortDataNioInputWrapper(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 2939 */       this.channel = channel;
/* 2940 */       this.byteBuffer = ByteBuffer.allocateDirect(BinIO.BUFFER_SIZE).order(byteOrder);
/* 2941 */       this.buffer = this.byteBuffer.asShortBuffer();
/* 2942 */       this.buffer.clear().flip();
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2946 */       if (!this.buffer.hasRemaining()) {
/* 2947 */         this.byteBuffer.clear();
/*      */         try {
/* 2949 */           this.channel.read(this.byteBuffer);
/* 2950 */         } catch (IOException e) {
/* 2951 */           throw new RuntimeException(e);
/*      */         } 
/* 2953 */         this.byteBuffer.flip();
/* 2954 */         this.buffer.clear();
/* 2955 */         this.buffer.limit(this.byteBuffer.limit() >>> ShortMappedBigList.LOG2_BYTES);
/*      */       } 
/* 2957 */       return this.buffer.hasRemaining();
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 2961 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2962 */       return this.buffer.get();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 2971 */     return new ShortDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(File file, ByteOrder byteOrder) throws IOException {
/* 2982 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 2983 */     return new ShortDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 2994 */     return asShortIterator(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(File file, ByteOrder byteOrder) {
/* 3005 */     return () -> { try {
/*      */           return asShortIterator(file, byteOrder);
/* 3007 */         } catch (IOException e) {
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
/*      */   public static ShortIterable asShortIterable(CharSequence filename, ByteOrder byteOrder) {
/* 3019 */     return () -> { try {
/*      */           return asShortIterator(filename, byteOrder);
/* 3021 */         } catch (IOException e) {
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
/*      */   public static int loadShorts(DataInput dataInput, short[] array, int offset, int length) throws IOException {
/* 3033 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 3034 */     int i = 0;
/*      */     try {
/* 3036 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readShort(); i++; }
/*      */     
/* 3038 */     } catch (EOFException eOFException) {}
/* 3039 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(DataInput dataInput, short[] array) throws IOException {
/* 3048 */     int i = 0;
/*      */     try {
/* 3050 */       int length = array.length;
/* 3051 */       for (i = 0; i < length; ) { array[i] = dataInput.readShort(); i++; }
/*      */     
/* 3053 */     } catch (EOFException eOFException) {}
/* 3054 */     return i;
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
/* 3065 */     return loadShorts(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 3076 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, short[] array) throws IOException {
/* 3085 */     return loadShorts(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, short[] array) throws IOException {
/* 3094 */     return loadShorts(new File(filename.toString()), array);
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
/*      */   public static short[] loadShorts(File file) throws IOException {
/* 3106 */     return loadShorts(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static short[] loadShorts(CharSequence filename) throws IOException {
/* 3118 */     return loadShorts(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 3128 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 3129 */     for (int i = 0; i < length; ) { dataOutput.writeShort(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, DataOutput dataOutput) throws IOException {
/* 3137 */     int length = array.length;
/* 3138 */     for (int i = 0; i < length; ) { dataOutput.writeShort(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, File file) throws IOException {
/* 3148 */     storeShorts(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, CharSequence filename) throws IOException {
/* 3158 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, File file) throws IOException {
/* 3166 */     storeShorts(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, CharSequence filename) throws IOException {
/* 3174 */     storeShorts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(DataInput dataInput, short[][] array, long offset, long length) throws IOException {
/* 3185 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3186 */     long c = 0L;
/*      */     try {
/* 3188 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3189 */         short[] t = array[i];
/* 3190 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3191 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 3192 */           t[d] = dataInput.readShort();
/* 3193 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3197 */     } catch (EOFException eOFException) {}
/* 3198 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(DataInput dataInput, short[][] array) throws IOException {
/* 3207 */     long c = 0L;
/*      */     try {
/* 3209 */       for (int i = 0; i < array.length; i++) {
/* 3210 */         short[] t = array[i];
/* 3211 */         int l = t.length;
/* 3212 */         for (int d = 0; d < l; d++) {
/* 3213 */           t[d] = dataInput.readShort();
/* 3214 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3218 */     } catch (EOFException eOFException) {}
/* 3219 */     return c;
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
/* 3230 */     return loadShorts(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 3241 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, short[][] array) throws IOException {
/* 3250 */     return loadShorts(file, ByteOrder.BIG_ENDIAN, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, short[][] array) throws IOException {
/* 3259 */     return loadShorts(new File(filename.toString()), array);
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
/*      */   public static short[][] loadShortsBig(File file) throws IOException {
/* 3271 */     return loadShortsBig(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static short[][] loadShortsBig(CharSequence filename) throws IOException {
/* 3283 */     return loadShortsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 3293 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3294 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3295 */       short[] t = array[i];
/* 3296 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3297 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeShort(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, DataOutput dataOutput) throws IOException {
/* 3306 */     for (int i = 0; i < array.length; i++) {
/* 3307 */       short[] t = array[i];
/* 3308 */       int l = t.length;
/* 3309 */       for (int d = 0; d < l; ) { dataOutput.writeShort(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, File file) throws IOException {
/* 3320 */     storeShorts(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 3330 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, File file) throws IOException {
/* 3338 */     storeShorts(array, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, CharSequence filename) throws IOException {
/* 3346 */     storeShorts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, DataOutput dataOutput) throws IOException {
/* 3354 */     for (; i.hasNext(); dataOutput.writeShort(i.nextShort()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, File file) throws IOException {
/* 3362 */     storeShorts(i, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, CharSequence filename) throws IOException {
/* 3370 */     storeShorts(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class ShortDataInputWrapper implements ShortIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private short next;
/*      */     
/*      */     public ShortDataInputWrapper(DataInput dataInput) {
/* 3379 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 3383 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 3384 */       this.toAdvance = false; 
/* 3385 */       try { this.next = this.dataInput.readShort(); }
/* 3386 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 3387 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 3388 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 3392 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 3393 */       this.toAdvance = true;
/* 3394 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(DataInput dataInput) {
/* 3402 */     return new ShortDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(File file) throws IOException {
/* 3412 */     return asShortIterator(file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(CharSequence filename) throws IOException {
/* 3422 */     return asShortIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(File file) {
/* 3432 */     return () -> { try {
/*      */           return asShortIterator(file);
/* 3434 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(CharSequence filename) {
/* 3445 */     return () -> { try {
/*      */           return asShortIterator(filename);
/* 3447 */         } catch (IOException e) {
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
/*      */   
/*      */   public static int loadInts(ReadableByteChannel channel, ByteOrder byteOrder, int[] array, int offset, int length) throws IOException {
/* 3500 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 3501 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 3502 */     IntBuffer buffer = byteBuffer.asIntBuffer();
/* 3503 */     int read = 0;
/*      */     while (true) {
/* 3505 */       byteBuffer.clear();
/* 3506 */       byteBuffer.limit((int)Math.min(buffer.capacity(), length << IntMappedBigList.LOG2_BYTES));
/* 3507 */       int r = channel.read(byteBuffer);
/* 3508 */       if (r <= 0) return read; 
/* 3509 */       r >>>= IntMappedBigList.LOG2_BYTES;
/* 3510 */       read += r;
/*      */       
/* 3512 */       buffer.clear();
/* 3513 */       buffer.limit(r);
/* 3514 */       buffer.get(array, offset, r);
/* 3515 */       offset += r;
/* 3516 */       length -= r;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(ReadableByteChannel channel, ByteOrder byteOrder, int[] array) throws IOException {
/* 3527 */     return loadInts(channel, byteOrder, array, 0, array.length);
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
/*      */   public static int loadInts(File file, ByteOrder byteOrder, int[] array, int offset, int length) throws IOException {
/* 3539 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 3540 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 3541 */     int read = loadInts(channel, byteOrder, array, offset, length);
/* 3542 */     channel.close();
/* 3543 */     return read;
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
/*      */   public static int loadInts(CharSequence filename, ByteOrder byteOrder, int[] array, int offset, int length) throws IOException {
/* 3555 */     return loadInts(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, ByteOrder byteOrder, int[] array) throws IOException {
/* 3565 */     return loadInts(file, byteOrder, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, ByteOrder byteOrder, int[] array) throws IOException {
/* 3575 */     return loadInts(new File(filename.toString()), byteOrder, array);
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
/*      */   public static int[] loadInts(File file, ByteOrder byteOrder) throws IOException {
/* 3588 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 3589 */     long length = channel.size() / 4L;
/* 3590 */     if (length > 2147483647L) {
/* 3591 */       channel.close();
/* 3592 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 3594 */     int[] array = new int[(int)length];
/* 3595 */     if (loadInts(channel, byteOrder, array) < length) throw new EOFException(); 
/* 3596 */     channel.close();
/* 3597 */     return array;
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
/*      */   public static int[] loadInts(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3609 */     return loadInts(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 3620 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 3621 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 3622 */     IntBuffer buffer = byteBuffer.asIntBuffer();
/* 3623 */     while (length != 0) {
/* 3624 */       int l = Math.min(length, buffer.capacity());
/* 3625 */       buffer.clear();
/* 3626 */       buffer.put(array, offset, l);
/* 3627 */       buffer.flip();
/* 3628 */       byteBuffer.clear();
/* 3629 */       byteBuffer.limit(buffer.limit() << IntMappedBigList.LOG2_BYTES);
/* 3630 */       channel.write(byteBuffer);
/* 3631 */       offset += l;
/* 3632 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 3642 */     storeInts(array, 0, array.length, channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, File file, ByteOrder byteOrder) throws IOException {
/* 3653 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 3654 */     storeInts(array, offset, length, channel, byteOrder);
/* 3655 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3666 */     storeInts(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, File file, ByteOrder byteOrder) throws IOException {
/* 3675 */     storeInts(array, 0, array.length, file, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3684 */     storeInts(array, new File(filename.toString()), byteOrder);
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
/*      */   public static long loadInts(ReadableByteChannel channel, ByteOrder byteOrder, int[][] array, long offset, long length) throws IOException {
/* 3696 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3697 */     long read = 0L;
/* 3698 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3699 */       int[] t = array[i];
/* 3700 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 3701 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3702 */       int r = loadInts(channel, byteOrder, t, s, e - s);
/* 3703 */       read += r;
/* 3704 */       if (r < e - s)
/*      */         break; 
/* 3706 */     }  return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(ReadableByteChannel channel, ByteOrder byteOrder, int[][] array) throws IOException {
/* 3716 */     return loadInts(channel, byteOrder, array, 0L, BigArrays.length(array));
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
/*      */   public static long loadInts(File file, ByteOrder byteOrder, int[][] array, long offset, long length) throws IOException {
/* 3728 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 3729 */     long read = loadInts(channel, byteOrder, array, offset, length);
/* 3730 */     channel.close();
/* 3731 */     return read;
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
/*      */   public static long loadInts(CharSequence filename, ByteOrder byteOrder, int[][] array, long offset, long length) throws IOException {
/* 3743 */     return loadInts(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, ByteOrder byteOrder, int[][] array) throws IOException {
/* 3753 */     return loadInts(file, byteOrder, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, ByteOrder byteOrder, int[][] array) throws IOException {
/* 3763 */     return loadInts(new File(filename.toString()), byteOrder, array);
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
/*      */   public static int[][] loadIntsBig(File file, ByteOrder byteOrder) throws IOException {
/* 3776 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 3777 */     long length = channel.size() / 4L;
/* 3778 */     int[][] array = IntBigArrays.newBigArray(length);
/* 3779 */     for (int[] t : array) loadInts(channel, byteOrder, t); 
/* 3780 */     channel.close();
/* 3781 */     return array;
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
/*      */   public static int[][] loadIntsBig(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3794 */     return loadIntsBig(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 3805 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3806 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 3807 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 3808 */       storeInts(array[i], s, l - s, channel, byteOrder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 3818 */     for (int[] t : array) storeInts(t, channel, byteOrder);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, File file, ByteOrder byteOrder) throws IOException {
/* 3829 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 3830 */     storeInts(array, offset, length, channel, byteOrder);
/* 3831 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3842 */     storeInts(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, File file, ByteOrder byteOrder) throws IOException {
/* 3851 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 3852 */     storeInts(array, channel, byteOrder);
/* 3853 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3862 */     storeInts(array, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 3871 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 3872 */     IntBuffer buffer = byteBuffer.asIntBuffer();
/* 3873 */     while (i.hasNext()) {
/* 3874 */       if (!buffer.hasRemaining()) {
/* 3875 */         buffer.flip();
/* 3876 */         byteBuffer.clear();
/* 3877 */         byteBuffer.limit(buffer.limit() << IntMappedBigList.LOG2_BYTES);
/* 3878 */         channel.write(byteBuffer);
/* 3879 */         buffer.clear();
/*      */       } 
/* 3881 */       buffer.put(i.nextInt());
/*      */     } 
/* 3883 */     buffer.flip();
/* 3884 */     byteBuffer.clear();
/* 3885 */     byteBuffer.limit(buffer.limit() << IntMappedBigList.LOG2_BYTES);
/* 3886 */     channel.write(byteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, File file, ByteOrder byteOrder) throws IOException {
/* 3895 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 3896 */     storeInts(i, channel, byteOrder);
/* 3897 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3906 */     storeInts(i, new File(filename.toString()), byteOrder);
/*      */   }
/*      */   
/*      */   private static final class IntDataNioInputWrapper implements IntIterator { private final ReadableByteChannel channel;
/*      */     private final ByteBuffer byteBuffer;
/*      */     private final IntBuffer buffer;
/*      */     
/*      */     public IntDataNioInputWrapper(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 3914 */       this.channel = channel;
/* 3915 */       this.byteBuffer = ByteBuffer.allocateDirect(BinIO.BUFFER_SIZE).order(byteOrder);
/* 3916 */       this.buffer = this.byteBuffer.asIntBuffer();
/* 3917 */       this.buffer.clear().flip();
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 3921 */       if (!this.buffer.hasRemaining()) {
/* 3922 */         this.byteBuffer.clear();
/*      */         try {
/* 3924 */           this.channel.read(this.byteBuffer);
/* 3925 */         } catch (IOException e) {
/* 3926 */           throw new RuntimeException(e);
/*      */         } 
/* 3928 */         this.byteBuffer.flip();
/* 3929 */         this.buffer.clear();
/* 3930 */         this.buffer.limit(this.byteBuffer.limit() >>> IntMappedBigList.LOG2_BYTES);
/*      */       } 
/* 3932 */       return this.buffer.hasRemaining();
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 3936 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 3937 */       return this.buffer.get();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 3946 */     return new IntDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(File file, ByteOrder byteOrder) throws IOException {
/* 3957 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 3958 */     return new IntDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 3969 */     return asIntIterator(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(File file, ByteOrder byteOrder) {
/* 3980 */     return () -> { try {
/*      */           return asIntIterator(file, byteOrder);
/* 3982 */         } catch (IOException e) {
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
/*      */   public static IntIterable asIntIterable(CharSequence filename, ByteOrder byteOrder) {
/* 3994 */     return () -> { try {
/*      */           return asIntIterator(filename, byteOrder);
/* 3996 */         } catch (IOException e) {
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
/*      */   public static int loadInts(DataInput dataInput, int[] array, int offset, int length) throws IOException {
/* 4008 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 4009 */     int i = 0;
/*      */     try {
/* 4011 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readInt(); i++; }
/*      */     
/* 4013 */     } catch (EOFException eOFException) {}
/* 4014 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(DataInput dataInput, int[] array) throws IOException {
/* 4023 */     int i = 0;
/*      */     try {
/* 4025 */       int length = array.length;
/* 4026 */       for (i = 0; i < length; ) { array[i] = dataInput.readInt(); i++; }
/*      */     
/* 4028 */     } catch (EOFException eOFException) {}
/* 4029 */     return i;
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
/* 4040 */     return loadInts(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 4051 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, int[] array) throws IOException {
/* 4060 */     return loadInts(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, int[] array) throws IOException {
/* 4069 */     return loadInts(new File(filename.toString()), array);
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
/*      */   public static int[] loadInts(File file) throws IOException {
/* 4081 */     return loadInts(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static int[] loadInts(CharSequence filename) throws IOException {
/* 4093 */     return loadInts(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 4103 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 4104 */     for (int i = 0; i < length; ) { dataOutput.writeInt(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, DataOutput dataOutput) throws IOException {
/* 4112 */     int length = array.length;
/* 4113 */     for (int i = 0; i < length; ) { dataOutput.writeInt(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, File file) throws IOException {
/* 4123 */     storeInts(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, CharSequence filename) throws IOException {
/* 4133 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, File file) throws IOException {
/* 4141 */     storeInts(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, CharSequence filename) throws IOException {
/* 4149 */     storeInts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(DataInput dataInput, int[][] array, long offset, long length) throws IOException {
/* 4160 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4161 */     long c = 0L;
/*      */     try {
/* 4163 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4164 */         int[] t = array[i];
/* 4165 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4166 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 4167 */           t[d] = dataInput.readInt();
/* 4168 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 4172 */     } catch (EOFException eOFException) {}
/* 4173 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(DataInput dataInput, int[][] array) throws IOException {
/* 4182 */     long c = 0L;
/*      */     try {
/* 4184 */       for (int i = 0; i < array.length; i++) {
/* 4185 */         int[] t = array[i];
/* 4186 */         int l = t.length;
/* 4187 */         for (int d = 0; d < l; d++) {
/* 4188 */           t[d] = dataInput.readInt();
/* 4189 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 4193 */     } catch (EOFException eOFException) {}
/* 4194 */     return c;
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
/* 4205 */     return loadInts(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 4216 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, int[][] array) throws IOException {
/* 4225 */     return loadInts(file, ByteOrder.BIG_ENDIAN, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, int[][] array) throws IOException {
/* 4234 */     return loadInts(new File(filename.toString()), array);
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
/*      */   public static int[][] loadIntsBig(File file) throws IOException {
/* 4246 */     return loadIntsBig(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static int[][] loadIntsBig(CharSequence filename) throws IOException {
/* 4258 */     return loadIntsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 4268 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4269 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4270 */       int[] t = array[i];
/* 4271 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4272 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeInt(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, DataOutput dataOutput) throws IOException {
/* 4281 */     for (int i = 0; i < array.length; i++) {
/* 4282 */       int[] t = array[i];
/* 4283 */       int l = t.length;
/* 4284 */       for (int d = 0; d < l; ) { dataOutput.writeInt(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, File file) throws IOException {
/* 4295 */     storeInts(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 4305 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, File file) throws IOException {
/* 4313 */     storeInts(array, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, CharSequence filename) throws IOException {
/* 4321 */     storeInts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, DataOutput dataOutput) throws IOException {
/* 4329 */     for (; i.hasNext(); dataOutput.writeInt(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, File file) throws IOException {
/* 4337 */     storeInts(i, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, CharSequence filename) throws IOException {
/* 4345 */     storeInts(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class IntDataInputWrapper implements IntIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private int next;
/*      */     
/*      */     public IntDataInputWrapper(DataInput dataInput) {
/* 4354 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 4358 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 4359 */       this.toAdvance = false; 
/* 4360 */       try { this.next = this.dataInput.readInt(); }
/* 4361 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 4362 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 4363 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 4367 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 4368 */       this.toAdvance = true;
/* 4369 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(DataInput dataInput) {
/* 4377 */     return new IntDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(File file) throws IOException {
/* 4387 */     return asIntIterator(file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(CharSequence filename) throws IOException {
/* 4397 */     return asIntIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(File file) {
/* 4407 */     return () -> { try {
/*      */           return asIntIterator(file);
/* 4409 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(CharSequence filename) {
/* 4420 */     return () -> { try {
/*      */           return asIntIterator(filename);
/* 4422 */         } catch (IOException e) {
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
/*      */   
/*      */   public static int loadFloats(ReadableByteChannel channel, ByteOrder byteOrder, float[] array, int offset, int length) throws IOException {
/* 4475 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 4476 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 4477 */     FloatBuffer buffer = byteBuffer.asFloatBuffer();
/* 4478 */     int read = 0;
/*      */     while (true) {
/* 4480 */       byteBuffer.clear();
/* 4481 */       byteBuffer.limit((int)Math.min(buffer.capacity(), length << FloatMappedBigList.LOG2_BYTES));
/* 4482 */       int r = channel.read(byteBuffer);
/* 4483 */       if (r <= 0) return read; 
/* 4484 */       r >>>= FloatMappedBigList.LOG2_BYTES;
/* 4485 */       read += r;
/*      */       
/* 4487 */       buffer.clear();
/* 4488 */       buffer.limit(r);
/* 4489 */       buffer.get(array, offset, r);
/* 4490 */       offset += r;
/* 4491 */       length -= r;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(ReadableByteChannel channel, ByteOrder byteOrder, float[] array) throws IOException {
/* 4502 */     return loadFloats(channel, byteOrder, array, 0, array.length);
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
/*      */   public static int loadFloats(File file, ByteOrder byteOrder, float[] array, int offset, int length) throws IOException {
/* 4514 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 4515 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 4516 */     int read = loadFloats(channel, byteOrder, array, offset, length);
/* 4517 */     channel.close();
/* 4518 */     return read;
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
/*      */   public static int loadFloats(CharSequence filename, ByteOrder byteOrder, float[] array, int offset, int length) throws IOException {
/* 4530 */     return loadFloats(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, ByteOrder byteOrder, float[] array) throws IOException {
/* 4540 */     return loadFloats(file, byteOrder, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, ByteOrder byteOrder, float[] array) throws IOException {
/* 4550 */     return loadFloats(new File(filename.toString()), byteOrder, array);
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
/*      */   public static float[] loadFloats(File file, ByteOrder byteOrder) throws IOException {
/* 4563 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 4564 */     long length = channel.size() / 4L;
/* 4565 */     if (length > 2147483647L) {
/* 4566 */       channel.close();
/* 4567 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 4569 */     float[] array = new float[(int)length];
/* 4570 */     if (loadFloats(channel, byteOrder, array) < length) throw new EOFException(); 
/* 4571 */     channel.close();
/* 4572 */     return array;
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
/*      */   public static float[] loadFloats(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4584 */     return loadFloats(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 4595 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 4596 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 4597 */     FloatBuffer buffer = byteBuffer.asFloatBuffer();
/* 4598 */     while (length != 0) {
/* 4599 */       int l = Math.min(length, buffer.capacity());
/* 4600 */       buffer.clear();
/* 4601 */       buffer.put(array, offset, l);
/* 4602 */       buffer.flip();
/* 4603 */       byteBuffer.clear();
/* 4604 */       byteBuffer.limit(buffer.limit() << FloatMappedBigList.LOG2_BYTES);
/* 4605 */       channel.write(byteBuffer);
/* 4606 */       offset += l;
/* 4607 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 4617 */     storeFloats(array, 0, array.length, channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, File file, ByteOrder byteOrder) throws IOException {
/* 4628 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 4629 */     storeFloats(array, offset, length, channel, byteOrder);
/* 4630 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4641 */     storeFloats(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, File file, ByteOrder byteOrder) throws IOException {
/* 4650 */     storeFloats(array, 0, array.length, file, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4659 */     storeFloats(array, new File(filename.toString()), byteOrder);
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
/*      */   public static long loadFloats(ReadableByteChannel channel, ByteOrder byteOrder, float[][] array, long offset, long length) throws IOException {
/* 4671 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4672 */     long read = 0L;
/* 4673 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4674 */       float[] t = array[i];
/* 4675 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 4676 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4677 */       int r = loadFloats(channel, byteOrder, t, s, e - s);
/* 4678 */       read += r;
/* 4679 */       if (r < e - s)
/*      */         break; 
/* 4681 */     }  return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(ReadableByteChannel channel, ByteOrder byteOrder, float[][] array) throws IOException {
/* 4691 */     return loadFloats(channel, byteOrder, array, 0L, BigArrays.length(array));
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
/*      */   public static long loadFloats(File file, ByteOrder byteOrder, float[][] array, long offset, long length) throws IOException {
/* 4703 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 4704 */     long read = loadFloats(channel, byteOrder, array, offset, length);
/* 4705 */     channel.close();
/* 4706 */     return read;
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
/*      */   public static long loadFloats(CharSequence filename, ByteOrder byteOrder, float[][] array, long offset, long length) throws IOException {
/* 4718 */     return loadFloats(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, ByteOrder byteOrder, float[][] array) throws IOException {
/* 4728 */     return loadFloats(file, byteOrder, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, ByteOrder byteOrder, float[][] array) throws IOException {
/* 4738 */     return loadFloats(new File(filename.toString()), byteOrder, array);
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
/*      */   public static float[][] loadFloatsBig(File file, ByteOrder byteOrder) throws IOException {
/* 4751 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 4752 */     long length = channel.size() / 4L;
/* 4753 */     float[][] array = FloatBigArrays.newBigArray(length);
/* 4754 */     for (float[] t : array) loadFloats(channel, byteOrder, t); 
/* 4755 */     channel.close();
/* 4756 */     return array;
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
/*      */   public static float[][] loadFloatsBig(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4769 */     return loadFloatsBig(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 4780 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4781 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 4782 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 4783 */       storeFloats(array[i], s, l - s, channel, byteOrder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 4793 */     for (float[] t : array) storeFloats(t, channel, byteOrder);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, File file, ByteOrder byteOrder) throws IOException {
/* 4804 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 4805 */     storeFloats(array, offset, length, channel, byteOrder);
/* 4806 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4817 */     storeFloats(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, File file, ByteOrder byteOrder) throws IOException {
/* 4826 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 4827 */     storeFloats(array, channel, byteOrder);
/* 4828 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4837 */     storeFloats(array, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 4846 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 4847 */     FloatBuffer buffer = byteBuffer.asFloatBuffer();
/* 4848 */     while (i.hasNext()) {
/* 4849 */       if (!buffer.hasRemaining()) {
/* 4850 */         buffer.flip();
/* 4851 */         byteBuffer.clear();
/* 4852 */         byteBuffer.limit(buffer.limit() << FloatMappedBigList.LOG2_BYTES);
/* 4853 */         channel.write(byteBuffer);
/* 4854 */         buffer.clear();
/*      */       } 
/* 4856 */       buffer.put(i.nextFloat());
/*      */     } 
/* 4858 */     buffer.flip();
/* 4859 */     byteBuffer.clear();
/* 4860 */     byteBuffer.limit(buffer.limit() << FloatMappedBigList.LOG2_BYTES);
/* 4861 */     channel.write(byteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, File file, ByteOrder byteOrder) throws IOException {
/* 4870 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 4871 */     storeFloats(i, channel, byteOrder);
/* 4872 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4881 */     storeFloats(i, new File(filename.toString()), byteOrder);
/*      */   }
/*      */   
/*      */   private static final class FloatDataNioInputWrapper implements FloatIterator { private final ReadableByteChannel channel;
/*      */     private final ByteBuffer byteBuffer;
/*      */     private final FloatBuffer buffer;
/*      */     
/*      */     public FloatDataNioInputWrapper(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 4889 */       this.channel = channel;
/* 4890 */       this.byteBuffer = ByteBuffer.allocateDirect(BinIO.BUFFER_SIZE).order(byteOrder);
/* 4891 */       this.buffer = this.byteBuffer.asFloatBuffer();
/* 4892 */       this.buffer.clear().flip();
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 4896 */       if (!this.buffer.hasRemaining()) {
/* 4897 */         this.byteBuffer.clear();
/*      */         try {
/* 4899 */           this.channel.read(this.byteBuffer);
/* 4900 */         } catch (IOException e) {
/* 4901 */           throw new RuntimeException(e);
/*      */         } 
/* 4903 */         this.byteBuffer.flip();
/* 4904 */         this.buffer.clear();
/* 4905 */         this.buffer.limit(this.byteBuffer.limit() >>> FloatMappedBigList.LOG2_BYTES);
/*      */       } 
/* 4907 */       return this.buffer.hasRemaining();
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 4911 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 4912 */       return this.buffer.get();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 4921 */     return new FloatDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(File file, ByteOrder byteOrder) throws IOException {
/* 4932 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 4933 */     return new FloatDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 4944 */     return asFloatIterator(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(File file, ByteOrder byteOrder) {
/* 4955 */     return () -> { try {
/*      */           return asFloatIterator(file, byteOrder);
/* 4957 */         } catch (IOException e) {
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
/*      */   public static FloatIterable asFloatIterable(CharSequence filename, ByteOrder byteOrder) {
/* 4969 */     return () -> { try {
/*      */           return asFloatIterator(filename, byteOrder);
/* 4971 */         } catch (IOException e) {
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
/*      */   public static int loadFloats(DataInput dataInput, float[] array, int offset, int length) throws IOException {
/* 4983 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 4984 */     int i = 0;
/*      */     try {
/* 4986 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readFloat(); i++; }
/*      */     
/* 4988 */     } catch (EOFException eOFException) {}
/* 4989 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(DataInput dataInput, float[] array) throws IOException {
/* 4998 */     int i = 0;
/*      */     try {
/* 5000 */       int length = array.length;
/* 5001 */       for (i = 0; i < length; ) { array[i] = dataInput.readFloat(); i++; }
/*      */     
/* 5003 */     } catch (EOFException eOFException) {}
/* 5004 */     return i;
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
/* 5015 */     return loadFloats(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 5026 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, float[] array) throws IOException {
/* 5035 */     return loadFloats(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, float[] array) throws IOException {
/* 5044 */     return loadFloats(new File(filename.toString()), array);
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
/*      */   public static float[] loadFloats(File file) throws IOException {
/* 5056 */     return loadFloats(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static float[] loadFloats(CharSequence filename) throws IOException {
/* 5068 */     return loadFloats(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 5078 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 5079 */     for (int i = 0; i < length; ) { dataOutput.writeFloat(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, DataOutput dataOutput) throws IOException {
/* 5087 */     int length = array.length;
/* 5088 */     for (int i = 0; i < length; ) { dataOutput.writeFloat(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, File file) throws IOException {
/* 5098 */     storeFloats(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, CharSequence filename) throws IOException {
/* 5108 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, File file) throws IOException {
/* 5116 */     storeFloats(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, CharSequence filename) throws IOException {
/* 5124 */     storeFloats(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(DataInput dataInput, float[][] array, long offset, long length) throws IOException {
/* 5135 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 5136 */     long c = 0L;
/*      */     try {
/* 5138 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 5139 */         float[] t = array[i];
/* 5140 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 5141 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 5142 */           t[d] = dataInput.readFloat();
/* 5143 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 5147 */     } catch (EOFException eOFException) {}
/* 5148 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(DataInput dataInput, float[][] array) throws IOException {
/* 5157 */     long c = 0L;
/*      */     try {
/* 5159 */       for (int i = 0; i < array.length; i++) {
/* 5160 */         float[] t = array[i];
/* 5161 */         int l = t.length;
/* 5162 */         for (int d = 0; d < l; d++) {
/* 5163 */           t[d] = dataInput.readFloat();
/* 5164 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 5168 */     } catch (EOFException eOFException) {}
/* 5169 */     return c;
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
/* 5180 */     return loadFloats(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 5191 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, float[][] array) throws IOException {
/* 5200 */     return loadFloats(file, ByteOrder.BIG_ENDIAN, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, float[][] array) throws IOException {
/* 5209 */     return loadFloats(new File(filename.toString()), array);
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
/*      */   public static float[][] loadFloatsBig(File file) throws IOException {
/* 5221 */     return loadFloatsBig(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static float[][] loadFloatsBig(CharSequence filename) throws IOException {
/* 5233 */     return loadFloatsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 5243 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 5244 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 5245 */       float[] t = array[i];
/* 5246 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 5247 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeFloat(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, DataOutput dataOutput) throws IOException {
/* 5256 */     for (int i = 0; i < array.length; i++) {
/* 5257 */       float[] t = array[i];
/* 5258 */       int l = t.length;
/* 5259 */       for (int d = 0; d < l; ) { dataOutput.writeFloat(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, File file) throws IOException {
/* 5270 */     storeFloats(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 5280 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, File file) throws IOException {
/* 5288 */     storeFloats(array, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, CharSequence filename) throws IOException {
/* 5296 */     storeFloats(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, DataOutput dataOutput) throws IOException {
/* 5304 */     for (; i.hasNext(); dataOutput.writeFloat(i.nextFloat()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, File file) throws IOException {
/* 5312 */     storeFloats(i, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, CharSequence filename) throws IOException {
/* 5320 */     storeFloats(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class FloatDataInputWrapper implements FloatIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private float next;
/*      */     
/*      */     public FloatDataInputWrapper(DataInput dataInput) {
/* 5329 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 5333 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 5334 */       this.toAdvance = false; 
/* 5335 */       try { this.next = this.dataInput.readFloat(); }
/* 5336 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 5337 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 5338 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 5342 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 5343 */       this.toAdvance = true;
/* 5344 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(DataInput dataInput) {
/* 5352 */     return new FloatDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(File file) throws IOException {
/* 5362 */     return asFloatIterator(file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(CharSequence filename) throws IOException {
/* 5372 */     return asFloatIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(File file) {
/* 5382 */     return () -> { try {
/*      */           return asFloatIterator(file);
/* 5384 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(CharSequence filename) {
/* 5395 */     return () -> { try {
/*      */           return asFloatIterator(filename);
/* 5397 */         } catch (IOException e) {
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
/*      */   
/*      */   public static int loadLongs(ReadableByteChannel channel, ByteOrder byteOrder, long[] array, int offset, int length) throws IOException {
/* 5450 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 5451 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 5452 */     LongBuffer buffer = byteBuffer.asLongBuffer();
/* 5453 */     int read = 0;
/*      */     while (true) {
/* 5455 */       byteBuffer.clear();
/* 5456 */       byteBuffer.limit((int)Math.min(buffer.capacity(), length << LongMappedBigList.LOG2_BYTES));
/* 5457 */       int r = channel.read(byteBuffer);
/* 5458 */       if (r <= 0) return read; 
/* 5459 */       r >>>= LongMappedBigList.LOG2_BYTES;
/* 5460 */       read += r;
/*      */       
/* 5462 */       buffer.clear();
/* 5463 */       buffer.limit(r);
/* 5464 */       buffer.get(array, offset, r);
/* 5465 */       offset += r;
/* 5466 */       length -= r;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(ReadableByteChannel channel, ByteOrder byteOrder, long[] array) throws IOException {
/* 5477 */     return loadLongs(channel, byteOrder, array, 0, array.length);
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
/*      */   public static int loadLongs(File file, ByteOrder byteOrder, long[] array, int offset, int length) throws IOException {
/* 5489 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 5490 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 5491 */     int read = loadLongs(channel, byteOrder, array, offset, length);
/* 5492 */     channel.close();
/* 5493 */     return read;
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
/*      */   public static int loadLongs(CharSequence filename, ByteOrder byteOrder, long[] array, int offset, int length) throws IOException {
/* 5505 */     return loadLongs(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, ByteOrder byteOrder, long[] array) throws IOException {
/* 5515 */     return loadLongs(file, byteOrder, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, ByteOrder byteOrder, long[] array) throws IOException {
/* 5525 */     return loadLongs(new File(filename.toString()), byteOrder, array);
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
/*      */   public static long[] loadLongs(File file, ByteOrder byteOrder) throws IOException {
/* 5538 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 5539 */     long length = channel.size() / 8L;
/* 5540 */     if (length > 2147483647L) {
/* 5541 */       channel.close();
/* 5542 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 5544 */     long[] array = new long[(int)length];
/* 5545 */     if (loadLongs(channel, byteOrder, array) < length) throw new EOFException(); 
/* 5546 */     channel.close();
/* 5547 */     return array;
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
/*      */   public static long[] loadLongs(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5559 */     return loadLongs(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 5570 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 5571 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 5572 */     LongBuffer buffer = byteBuffer.asLongBuffer();
/* 5573 */     while (length != 0) {
/* 5574 */       int l = Math.min(length, buffer.capacity());
/* 5575 */       buffer.clear();
/* 5576 */       buffer.put(array, offset, l);
/* 5577 */       buffer.flip();
/* 5578 */       byteBuffer.clear();
/* 5579 */       byteBuffer.limit(buffer.limit() << LongMappedBigList.LOG2_BYTES);
/* 5580 */       channel.write(byteBuffer);
/* 5581 */       offset += l;
/* 5582 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 5592 */     storeLongs(array, 0, array.length, channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, File file, ByteOrder byteOrder) throws IOException {
/* 5603 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 5604 */     storeLongs(array, offset, length, channel, byteOrder);
/* 5605 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5616 */     storeLongs(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, File file, ByteOrder byteOrder) throws IOException {
/* 5625 */     storeLongs(array, 0, array.length, file, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5634 */     storeLongs(array, new File(filename.toString()), byteOrder);
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
/*      */   public static long loadLongs(ReadableByteChannel channel, ByteOrder byteOrder, long[][] array, long offset, long length) throws IOException {
/* 5646 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 5647 */     long read = 0L;
/* 5648 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 5649 */       long[] t = array[i];
/* 5650 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 5651 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 5652 */       int r = loadLongs(channel, byteOrder, t, s, e - s);
/* 5653 */       read += r;
/* 5654 */       if (r < e - s)
/*      */         break; 
/* 5656 */     }  return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(ReadableByteChannel channel, ByteOrder byteOrder, long[][] array) throws IOException {
/* 5666 */     return loadLongs(channel, byteOrder, array, 0L, BigArrays.length(array));
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
/*      */   public static long loadLongs(File file, ByteOrder byteOrder, long[][] array, long offset, long length) throws IOException {
/* 5678 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 5679 */     long read = loadLongs(channel, byteOrder, array, offset, length);
/* 5680 */     channel.close();
/* 5681 */     return read;
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
/*      */   public static long loadLongs(CharSequence filename, ByteOrder byteOrder, long[][] array, long offset, long length) throws IOException {
/* 5693 */     return loadLongs(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, ByteOrder byteOrder, long[][] array) throws IOException {
/* 5703 */     return loadLongs(file, byteOrder, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, ByteOrder byteOrder, long[][] array) throws IOException {
/* 5713 */     return loadLongs(new File(filename.toString()), byteOrder, array);
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
/*      */   public static long[][] loadLongsBig(File file, ByteOrder byteOrder) throws IOException {
/* 5726 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 5727 */     long length = channel.size() / 8L;
/* 5728 */     long[][] array = LongBigArrays.newBigArray(length);
/* 5729 */     for (long[] t : array) loadLongs(channel, byteOrder, t); 
/* 5730 */     channel.close();
/* 5731 */     return array;
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
/*      */   public static long[][] loadLongsBig(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5744 */     return loadLongsBig(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 5755 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 5756 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 5757 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 5758 */       storeLongs(array[i], s, l - s, channel, byteOrder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 5768 */     for (long[] t : array) storeLongs(t, channel, byteOrder);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, File file, ByteOrder byteOrder) throws IOException {
/* 5779 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 5780 */     storeLongs(array, offset, length, channel, byteOrder);
/* 5781 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5792 */     storeLongs(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, File file, ByteOrder byteOrder) throws IOException {
/* 5801 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 5802 */     storeLongs(array, channel, byteOrder);
/* 5803 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5812 */     storeLongs(array, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 5821 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 5822 */     LongBuffer buffer = byteBuffer.asLongBuffer();
/* 5823 */     while (i.hasNext()) {
/* 5824 */       if (!buffer.hasRemaining()) {
/* 5825 */         buffer.flip();
/* 5826 */         byteBuffer.clear();
/* 5827 */         byteBuffer.limit(buffer.limit() << LongMappedBigList.LOG2_BYTES);
/* 5828 */         channel.write(byteBuffer);
/* 5829 */         buffer.clear();
/*      */       } 
/* 5831 */       buffer.put(i.nextLong());
/*      */     } 
/* 5833 */     buffer.flip();
/* 5834 */     byteBuffer.clear();
/* 5835 */     byteBuffer.limit(buffer.limit() << LongMappedBigList.LOG2_BYTES);
/* 5836 */     channel.write(byteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, File file, ByteOrder byteOrder) throws IOException {
/* 5845 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 5846 */     storeLongs(i, channel, byteOrder);
/* 5847 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5856 */     storeLongs(i, new File(filename.toString()), byteOrder);
/*      */   }
/*      */   
/*      */   private static final class LongDataNioInputWrapper implements LongIterator { private final ReadableByteChannel channel;
/*      */     private final ByteBuffer byteBuffer;
/*      */     private final LongBuffer buffer;
/*      */     
/*      */     public LongDataNioInputWrapper(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 5864 */       this.channel = channel;
/* 5865 */       this.byteBuffer = ByteBuffer.allocateDirect(BinIO.BUFFER_SIZE).order(byteOrder);
/* 5866 */       this.buffer = this.byteBuffer.asLongBuffer();
/* 5867 */       this.buffer.clear().flip();
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 5871 */       if (!this.buffer.hasRemaining()) {
/* 5872 */         this.byteBuffer.clear();
/*      */         try {
/* 5874 */           this.channel.read(this.byteBuffer);
/* 5875 */         } catch (IOException e) {
/* 5876 */           throw new RuntimeException(e);
/*      */         } 
/* 5878 */         this.byteBuffer.flip();
/* 5879 */         this.buffer.clear();
/* 5880 */         this.buffer.limit(this.byteBuffer.limit() >>> LongMappedBigList.LOG2_BYTES);
/*      */       } 
/* 5882 */       return this.buffer.hasRemaining();
/*      */     }
/*      */     
/*      */     public long nextLong() {
/* 5886 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 5887 */       return this.buffer.get();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 5896 */     return new LongDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(File file, ByteOrder byteOrder) throws IOException {
/* 5907 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 5908 */     return new LongDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 5919 */     return asLongIterator(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(File file, ByteOrder byteOrder) {
/* 5930 */     return () -> { try {
/*      */           return asLongIterator(file, byteOrder);
/* 5932 */         } catch (IOException e) {
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
/*      */   public static LongIterable asLongIterable(CharSequence filename, ByteOrder byteOrder) {
/* 5944 */     return () -> { try {
/*      */           return asLongIterator(filename, byteOrder);
/* 5946 */         } catch (IOException e) {
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
/*      */   public static int loadLongs(DataInput dataInput, long[] array, int offset, int length) throws IOException {
/* 5958 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 5959 */     int i = 0;
/*      */     try {
/* 5961 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readLong(); i++; }
/*      */     
/* 5963 */     } catch (EOFException eOFException) {}
/* 5964 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(DataInput dataInput, long[] array) throws IOException {
/* 5973 */     int i = 0;
/*      */     try {
/* 5975 */       int length = array.length;
/* 5976 */       for (i = 0; i < length; ) { array[i] = dataInput.readLong(); i++; }
/*      */     
/* 5978 */     } catch (EOFException eOFException) {}
/* 5979 */     return i;
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
/* 5990 */     return loadLongs(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 6001 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, long[] array) throws IOException {
/* 6010 */     return loadLongs(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, long[] array) throws IOException {
/* 6019 */     return loadLongs(new File(filename.toString()), array);
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
/*      */   public static long[] loadLongs(File file) throws IOException {
/* 6031 */     return loadLongs(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static long[] loadLongs(CharSequence filename) throws IOException {
/* 6043 */     return loadLongs(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 6053 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 6054 */     for (int i = 0; i < length; ) { dataOutput.writeLong(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, DataOutput dataOutput) throws IOException {
/* 6062 */     int length = array.length;
/* 6063 */     for (int i = 0; i < length; ) { dataOutput.writeLong(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, File file) throws IOException {
/* 6073 */     storeLongs(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, CharSequence filename) throws IOException {
/* 6083 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, File file) throws IOException {
/* 6091 */     storeLongs(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, CharSequence filename) throws IOException {
/* 6099 */     storeLongs(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(DataInput dataInput, long[][] array, long offset, long length) throws IOException {
/* 6110 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 6111 */     long c = 0L;
/*      */     try {
/* 6113 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 6114 */         long[] t = array[i];
/* 6115 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 6116 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 6117 */           t[d] = dataInput.readLong();
/* 6118 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 6122 */     } catch (EOFException eOFException) {}
/* 6123 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(DataInput dataInput, long[][] array) throws IOException {
/* 6132 */     long c = 0L;
/*      */     try {
/* 6134 */       for (int i = 0; i < array.length; i++) {
/* 6135 */         long[] t = array[i];
/* 6136 */         int l = t.length;
/* 6137 */         for (int d = 0; d < l; d++) {
/* 6138 */           t[d] = dataInput.readLong();
/* 6139 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 6143 */     } catch (EOFException eOFException) {}
/* 6144 */     return c;
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
/* 6155 */     return loadLongs(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 6166 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, long[][] array) throws IOException {
/* 6175 */     return loadLongs(file, ByteOrder.BIG_ENDIAN, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, long[][] array) throws IOException {
/* 6184 */     return loadLongs(new File(filename.toString()), array);
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
/*      */   public static long[][] loadLongsBig(File file) throws IOException {
/* 6196 */     return loadLongsBig(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static long[][] loadLongsBig(CharSequence filename) throws IOException {
/* 6208 */     return loadLongsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 6218 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 6219 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 6220 */       long[] t = array[i];
/* 6221 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 6222 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeLong(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, DataOutput dataOutput) throws IOException {
/* 6231 */     for (int i = 0; i < array.length; i++) {
/* 6232 */       long[] t = array[i];
/* 6233 */       int l = t.length;
/* 6234 */       for (int d = 0; d < l; ) { dataOutput.writeLong(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, File file) throws IOException {
/* 6245 */     storeLongs(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 6255 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, File file) throws IOException {
/* 6263 */     storeLongs(array, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, CharSequence filename) throws IOException {
/* 6271 */     storeLongs(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, DataOutput dataOutput) throws IOException {
/* 6279 */     for (; i.hasNext(); dataOutput.writeLong(i.nextLong()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, File file) throws IOException {
/* 6287 */     storeLongs(i, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, CharSequence filename) throws IOException {
/* 6295 */     storeLongs(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class LongDataInputWrapper implements LongIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private long next;
/*      */     
/*      */     public LongDataInputWrapper(DataInput dataInput) {
/* 6304 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 6308 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 6309 */       this.toAdvance = false; 
/* 6310 */       try { this.next = this.dataInput.readLong(); }
/* 6311 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 6312 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 6313 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public long nextLong() {
/* 6317 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 6318 */       this.toAdvance = true;
/* 6319 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(DataInput dataInput) {
/* 6327 */     return new LongDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(File file) throws IOException {
/* 6337 */     return asLongIterator(file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(CharSequence filename) throws IOException {
/* 6347 */     return asLongIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(File file) {
/* 6357 */     return () -> { try {
/*      */           return asLongIterator(file);
/* 6359 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(CharSequence filename) {
/* 6370 */     return () -> { try {
/*      */           return asLongIterator(filename);
/* 6372 */         } catch (IOException e) {
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
/*      */   
/*      */   public static int loadDoubles(ReadableByteChannel channel, ByteOrder byteOrder, double[] array, int offset, int length) throws IOException {
/* 6425 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 6426 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 6427 */     DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
/* 6428 */     int read = 0;
/*      */     while (true) {
/* 6430 */       byteBuffer.clear();
/* 6431 */       byteBuffer.limit((int)Math.min(buffer.capacity(), length << DoubleMappedBigList.LOG2_BYTES));
/* 6432 */       int r = channel.read(byteBuffer);
/* 6433 */       if (r <= 0) return read; 
/* 6434 */       r >>>= DoubleMappedBigList.LOG2_BYTES;
/* 6435 */       read += r;
/*      */       
/* 6437 */       buffer.clear();
/* 6438 */       buffer.limit(r);
/* 6439 */       buffer.get(array, offset, r);
/* 6440 */       offset += r;
/* 6441 */       length -= r;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(ReadableByteChannel channel, ByteOrder byteOrder, double[] array) throws IOException {
/* 6452 */     return loadDoubles(channel, byteOrder, array, 0, array.length);
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
/*      */   public static int loadDoubles(File file, ByteOrder byteOrder, double[] array, int offset, int length) throws IOException {
/* 6464 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 6465 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 6466 */     int read = loadDoubles(channel, byteOrder, array, offset, length);
/* 6467 */     channel.close();
/* 6468 */     return read;
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
/*      */   public static int loadDoubles(CharSequence filename, ByteOrder byteOrder, double[] array, int offset, int length) throws IOException {
/* 6480 */     return loadDoubles(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, ByteOrder byteOrder, double[] array) throws IOException {
/* 6490 */     return loadDoubles(file, byteOrder, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, ByteOrder byteOrder, double[] array) throws IOException {
/* 6500 */     return loadDoubles(new File(filename.toString()), byteOrder, array);
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
/*      */   public static double[] loadDoubles(File file, ByteOrder byteOrder) throws IOException {
/* 6513 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 6514 */     long length = channel.size() / 8L;
/* 6515 */     if (length > 2147483647L) {
/* 6516 */       channel.close();
/* 6517 */       throw new IllegalArgumentException("File too long: " + channel.size() + " bytes (" + length + " elements)");
/*      */     } 
/* 6519 */     double[] array = new double[(int)length];
/* 6520 */     if (loadDoubles(channel, byteOrder, array) < length) throw new EOFException(); 
/* 6521 */     channel.close();
/* 6522 */     return array;
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
/*      */   public static double[] loadDoubles(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6534 */     return loadDoubles(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 6545 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 6546 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 6547 */     DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
/* 6548 */     while (length != 0) {
/* 6549 */       int l = Math.min(length, buffer.capacity());
/* 6550 */       buffer.clear();
/* 6551 */       buffer.put(array, offset, l);
/* 6552 */       buffer.flip();
/* 6553 */       byteBuffer.clear();
/* 6554 */       byteBuffer.limit(buffer.limit() << DoubleMappedBigList.LOG2_BYTES);
/* 6555 */       channel.write(byteBuffer);
/* 6556 */       offset += l;
/* 6557 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 6567 */     storeDoubles(array, 0, array.length, channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, File file, ByteOrder byteOrder) throws IOException {
/* 6578 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 6579 */     storeDoubles(array, offset, length, channel, byteOrder);
/* 6580 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6591 */     storeDoubles(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, File file, ByteOrder byteOrder) throws IOException {
/* 6600 */     storeDoubles(array, 0, array.length, file, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6609 */     storeDoubles(array, new File(filename.toString()), byteOrder);
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
/*      */   public static long loadDoubles(ReadableByteChannel channel, ByteOrder byteOrder, double[][] array, long offset, long length) throws IOException {
/* 6621 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 6622 */     long read = 0L;
/* 6623 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 6624 */       double[] t = array[i];
/* 6625 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 6626 */       int e = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 6627 */       int r = loadDoubles(channel, byteOrder, t, s, e - s);
/* 6628 */       read += r;
/* 6629 */       if (r < e - s)
/*      */         break; 
/* 6631 */     }  return read;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(ReadableByteChannel channel, ByteOrder byteOrder, double[][] array) throws IOException {
/* 6641 */     return loadDoubles(channel, byteOrder, array, 0L, BigArrays.length(array));
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
/*      */   public static long loadDoubles(File file, ByteOrder byteOrder, double[][] array, long offset, long length) throws IOException {
/* 6653 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 6654 */     long read = loadDoubles(channel, byteOrder, array, offset, length);
/* 6655 */     channel.close();
/* 6656 */     return read;
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
/*      */   public static long loadDoubles(CharSequence filename, ByteOrder byteOrder, double[][] array, long offset, long length) throws IOException {
/* 6668 */     return loadDoubles(new File(filename.toString()), byteOrder, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, ByteOrder byteOrder, double[][] array) throws IOException {
/* 6678 */     return loadDoubles(file, byteOrder, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, ByteOrder byteOrder, double[][] array) throws IOException {
/* 6688 */     return loadDoubles(new File(filename.toString()), byteOrder, array);
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
/*      */   public static double[][] loadDoublesBig(File file, ByteOrder byteOrder) throws IOException {
/* 6701 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 6702 */     long length = channel.size() / 8L;
/* 6703 */     double[][] array = DoubleBigArrays.newBigArray(length);
/* 6704 */     for (double[] t : array) loadDoubles(channel, byteOrder, t); 
/* 6705 */     channel.close();
/* 6706 */     return array;
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
/*      */   public static double[][] loadDoublesBig(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6719 */     return loadDoublesBig(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 6730 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 6731 */       int s = (int)Math.max(0L, offset - BigArrays.start(i));
/* 6732 */       int l = (int)Math.min((array[i]).length, offset + length - BigArrays.start(i));
/* 6733 */       storeDoubles(array[i], s, l - s, channel, byteOrder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 6743 */     for (double[] t : array) storeDoubles(t, channel, byteOrder);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, File file, ByteOrder byteOrder) throws IOException {
/* 6754 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 6755 */     storeDoubles(array, offset, length, channel, byteOrder);
/* 6756 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6767 */     storeDoubles(array, offset, length, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, File file, ByteOrder byteOrder) throws IOException {
/* 6776 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 6777 */     storeDoubles(array, channel, byteOrder);
/* 6778 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6787 */     storeDoubles(array, new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, WritableByteChannel channel, ByteOrder byteOrder) throws IOException {
/* 6796 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE).order(byteOrder);
/* 6797 */     DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
/* 6798 */     while (i.hasNext()) {
/* 6799 */       if (!buffer.hasRemaining()) {
/* 6800 */         buffer.flip();
/* 6801 */         byteBuffer.clear();
/* 6802 */         byteBuffer.limit(buffer.limit() << DoubleMappedBigList.LOG2_BYTES);
/* 6803 */         channel.write(byteBuffer);
/* 6804 */         buffer.clear();
/*      */       } 
/* 6806 */       buffer.put(i.nextDouble());
/*      */     } 
/* 6808 */     buffer.flip();
/* 6809 */     byteBuffer.clear();
/* 6810 */     byteBuffer.limit(buffer.limit() << DoubleMappedBigList.LOG2_BYTES);
/* 6811 */     channel.write(byteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, File file, ByteOrder byteOrder) throws IOException {
/* 6820 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING });
/* 6821 */     storeDoubles(i, channel, byteOrder);
/* 6822 */     channel.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6831 */     storeDoubles(i, new File(filename.toString()), byteOrder);
/*      */   }
/*      */   
/*      */   private static final class DoubleDataNioInputWrapper implements DoubleIterator { private final ReadableByteChannel channel;
/*      */     private final ByteBuffer byteBuffer;
/*      */     private final DoubleBuffer buffer;
/*      */     
/*      */     public DoubleDataNioInputWrapper(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 6839 */       this.channel = channel;
/* 6840 */       this.byteBuffer = ByteBuffer.allocateDirect(BinIO.BUFFER_SIZE).order(byteOrder);
/* 6841 */       this.buffer = this.byteBuffer.asDoubleBuffer();
/* 6842 */       this.buffer.clear().flip();
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 6846 */       if (!this.buffer.hasRemaining()) {
/* 6847 */         this.byteBuffer.clear();
/*      */         try {
/* 6849 */           this.channel.read(this.byteBuffer);
/* 6850 */         } catch (IOException e) {
/* 6851 */           throw new RuntimeException(e);
/*      */         } 
/* 6853 */         this.byteBuffer.flip();
/* 6854 */         this.buffer.clear();
/* 6855 */         this.buffer.limit(this.byteBuffer.limit() >>> DoubleMappedBigList.LOG2_BYTES);
/*      */       } 
/* 6857 */       return this.buffer.hasRemaining();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 6861 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 6862 */       return this.buffer.get();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(ReadableByteChannel channel, ByteOrder byteOrder) {
/* 6871 */     return new DoubleDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(File file, ByteOrder byteOrder) throws IOException {
/* 6882 */     FileChannel channel = FileChannel.open(file.toPath(), new OpenOption[0]);
/* 6883 */     return new DoubleDataNioInputWrapper(channel, byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(CharSequence filename, ByteOrder byteOrder) throws IOException {
/* 6894 */     return asDoubleIterator(new File(filename.toString()), byteOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(File file, ByteOrder byteOrder) {
/* 6905 */     return () -> { try {
/*      */           return asDoubleIterator(file, byteOrder);
/* 6907 */         } catch (IOException e) {
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
/*      */   public static DoubleIterable asDoubleIterable(CharSequence filename, ByteOrder byteOrder) {
/* 6919 */     return () -> { try {
/*      */           return asDoubleIterator(filename, byteOrder);
/* 6921 */         } catch (IOException e) {
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
/*      */   public static int loadDoubles(DataInput dataInput, double[] array, int offset, int length) throws IOException {
/* 6933 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 6934 */     int i = 0;
/*      */     try {
/* 6936 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readDouble(); i++; }
/*      */     
/* 6938 */     } catch (EOFException eOFException) {}
/* 6939 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(DataInput dataInput, double[] array) throws IOException {
/* 6948 */     int i = 0;
/*      */     try {
/* 6950 */       int length = array.length;
/* 6951 */       for (i = 0; i < length; ) { array[i] = dataInput.readDouble(); i++; }
/*      */     
/* 6953 */     } catch (EOFException eOFException) {}
/* 6954 */     return i;
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
/* 6965 */     return loadDoubles(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 6976 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, double[] array) throws IOException {
/* 6985 */     return loadDoubles(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, double[] array) throws IOException {
/* 6994 */     return loadDoubles(new File(filename.toString()), array);
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
/*      */   public static double[] loadDoubles(File file) throws IOException {
/* 7006 */     return loadDoubles(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static double[] loadDoubles(CharSequence filename) throws IOException {
/* 7018 */     return loadDoubles(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 7028 */     Arrays.ensureOffsetLength(array.length, offset, length);
/* 7029 */     for (int i = 0; i < length; ) { dataOutput.writeDouble(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, DataOutput dataOutput) throws IOException {
/* 7037 */     int length = array.length;
/* 7038 */     for (int i = 0; i < length; ) { dataOutput.writeDouble(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, File file) throws IOException {
/* 7048 */     storeDoubles(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, CharSequence filename) throws IOException {
/* 7058 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, File file) throws IOException {
/* 7066 */     storeDoubles(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, CharSequence filename) throws IOException {
/* 7074 */     storeDoubles(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(DataInput dataInput, double[][] array, long offset, long length) throws IOException {
/* 7085 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 7086 */     long c = 0L;
/*      */     try {
/* 7088 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 7089 */         double[] t = array[i];
/* 7090 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 7091 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 7092 */           t[d] = dataInput.readDouble();
/* 7093 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 7097 */     } catch (EOFException eOFException) {}
/* 7098 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(DataInput dataInput, double[][] array) throws IOException {
/* 7107 */     long c = 0L;
/*      */     try {
/* 7109 */       for (int i = 0; i < array.length; i++) {
/* 7110 */         double[] t = array[i];
/* 7111 */         int l = t.length;
/* 7112 */         for (int d = 0; d < l; d++) {
/* 7113 */           t[d] = dataInput.readDouble();
/* 7114 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 7118 */     } catch (EOFException eOFException) {}
/* 7119 */     return c;
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
/* 7130 */     return loadDoubles(file, ByteOrder.BIG_ENDIAN, array, offset, length);
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
/* 7141 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, double[][] array) throws IOException {
/* 7150 */     return loadDoubles(file, ByteOrder.BIG_ENDIAN, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, double[][] array) throws IOException {
/* 7159 */     return loadDoubles(new File(filename.toString()), array);
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
/*      */   public static double[][] loadDoublesBig(File file) throws IOException {
/* 7171 */     return loadDoublesBig(file, ByteOrder.BIG_ENDIAN);
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
/*      */   public static double[][] loadDoublesBig(CharSequence filename) throws IOException {
/* 7183 */     return loadDoublesBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 7193 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 7194 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 7195 */       double[] t = array[i];
/* 7196 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 7197 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeDouble(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, DataOutput dataOutput) throws IOException {
/* 7206 */     for (int i = 0; i < array.length; i++) {
/* 7207 */       double[] t = array[i];
/* 7208 */       int l = t.length;
/* 7209 */       for (int d = 0; d < l; ) { dataOutput.writeDouble(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, File file) throws IOException {
/* 7220 */     storeDoubles(array, offset, length, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 7230 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, File file) throws IOException {
/* 7238 */     storeDoubles(array, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, CharSequence filename) throws IOException {
/* 7246 */     storeDoubles(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, DataOutput dataOutput) throws IOException {
/* 7254 */     for (; i.hasNext(); dataOutput.writeDouble(i.nextDouble()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, File file) throws IOException {
/* 7262 */     storeDoubles(i, file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, CharSequence filename) throws IOException {
/* 7270 */     storeDoubles(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class DoubleDataInputWrapper implements DoubleIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private double next;
/*      */     
/*      */     public DoubleDataInputWrapper(DataInput dataInput) {
/* 7279 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 7283 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 7284 */       this.toAdvance = false; 
/* 7285 */       try { this.next = this.dataInput.readDouble(); }
/* 7286 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 7287 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 7288 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 7292 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 7293 */       this.toAdvance = true;
/* 7294 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(DataInput dataInput) {
/* 7302 */     return new DoubleDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(File file) throws IOException {
/* 7312 */     return asDoubleIterator(file, ByteOrder.BIG_ENDIAN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(CharSequence filename) throws IOException {
/* 7322 */     return asDoubleIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(File file) {
/* 7332 */     return () -> { try {
/*      */           return asDoubleIterator(file);
/* 7334 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(CharSequence filename) {
/* 7345 */     return () -> { try {
/*      */           return asDoubleIterator(filename);
/* 7347 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\io\BinIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */