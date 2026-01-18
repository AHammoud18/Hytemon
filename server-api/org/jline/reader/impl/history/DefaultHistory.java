/*     */ package org.jline.reader.impl.history;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import org.jline.reader.History;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.impl.ReaderUtils;
/*     */ import org.jline.utils.Log;
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
/*     */ 
/*     */ 
/*     */ public class DefaultHistory
/*     */   implements History
/*     */ {
/*     */   public static final int DEFAULT_HISTORY_SIZE = 500;
/*     */   public static final int DEFAULT_HISTORY_FILE_SIZE = 10000;
/*  79 */   private final LinkedList<History.Entry> items = new LinkedList<>();
/*     */   
/*     */   private LineReader reader;
/*     */   
/*  83 */   private Map<String, HistoryFileData> historyFiles = new HashMap<>();
/*  84 */   private int offset = 0;
/*  85 */   private int index = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHistory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHistory(LineReader reader) {
/*  99 */     attach(reader);
/*     */   }
/*     */   
/*     */   private Path getPath() {
/* 103 */     Object obj = (this.reader != null) ? this.reader.getVariables().get("history-file") : null;
/* 104 */     if (obj instanceof Path)
/* 105 */       return (Path)obj; 
/* 106 */     if (obj instanceof File)
/* 107 */       return ((File)obj).toPath(); 
/* 108 */     if (obj != null) {
/* 109 */       return Paths.get(obj.toString(), new String[0]);
/*     */     }
/* 111 */     return null;
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
/*     */   public void attach(LineReader reader) {
/* 126 */     if (this.reader != reader) {
/* 127 */       this.reader = reader;
/*     */       try {
/* 129 */         load();
/* 130 */       } catch (IllegalArgumentException|IOException e) {
/* 131 */         Log.warn(new Object[] { "Failed to load history", e });
/*     */       } 
/*     */     } 
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
/*     */   public void load() throws IOException {
/* 147 */     Path path = getPath();
/* 148 */     if (path != null) {
/*     */       try {
/* 150 */         if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 151 */           Log.trace(new Object[] { "Loading history from: ", path });
/* 152 */           internalClear();
/* 153 */           boolean hasErrors = false;
/*     */           
/* 155 */           BufferedReader reader = Files.newBufferedReader(path); 
/* 156 */           try { List<String> lines = reader.lines().collect((Collector)Collectors.toList());
/* 157 */             for (String line : lines) {
/*     */               try {
/* 159 */                 addHistoryLine(path, line);
/* 160 */               } catch (IllegalArgumentException e) {
/* 161 */                 Log.debug(new Object[] { "Skipping invalid history line: " + line, e });
/* 162 */                 hasErrors = true;
/*     */               } 
/*     */             } 
/* 165 */             if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*     */               try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 167 */            setHistoryFileData(path, new HistoryFileData(this.items.size(), this.offset + this.items.size()));
/* 168 */           maybeResize();
/*     */ 
/*     */           
/* 171 */           if (hasErrors) {
/* 172 */             Log.info(new Object[] { "History file contained errors, rewriting with valid entries" });
/* 173 */             write(path, false);
/*     */           } 
/*     */         } 
/* 176 */       } catch (IOException e) {
/* 177 */         Log.debug(new Object[] { "Failed to load history; clearing", e });
/* 178 */         internalClear();
/* 179 */         throw e;
/*     */       } 
/*     */     }
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
/*     */   public void read(Path file, boolean checkDuplicates) throws IOException {
/* 198 */     Path path = (file != null) ? file : getPath();
/* 199 */     if (path != null) {
/*     */       try {
/* 201 */         if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 202 */           Log.trace(new Object[] { "Reading history from: ", path });
/* 203 */           boolean hasErrors = false;
/*     */           
/* 205 */           BufferedReader reader = Files.newBufferedReader(path); 
/* 206 */           try { List<String> lines = reader.lines().collect((Collector)Collectors.toList());
/* 207 */             for (String line : lines) {
/*     */               try {
/* 209 */                 addHistoryLine(path, line, checkDuplicates);
/* 210 */               } catch (IllegalArgumentException e) {
/* 211 */                 Log.debug(new Object[] { "Skipping invalid history line: " + line, e });
/* 212 */                 hasErrors = true;
/*     */               } 
/*     */             } 
/* 215 */             if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*     */               try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 217 */            setHistoryFileData(path, new HistoryFileData(this.items.size(), this.offset + this.items.size()));
/* 218 */           maybeResize();
/*     */ 
/*     */           
/* 221 */           if (hasErrors) {
/* 222 */             Log.info(new Object[] { "History file contained errors, rewriting with valid entries" });
/* 223 */             write(path, false);
/*     */           } 
/*     */         } 
/* 226 */       } catch (IOException e) {
/* 227 */         Log.debug(new Object[] { "Failed to read history; clearing", e });
/* 228 */         internalClear();
/* 229 */         throw e;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private String doHistoryFileDataKey(Path path) {
/* 235 */     return (path != null) ? path.toAbsolutePath().toString() : null;
/*     */   }
/*     */   
/*     */   private HistoryFileData getHistoryFileData(Path path) {
/* 239 */     String key = doHistoryFileDataKey(path);
/* 240 */     if (!this.historyFiles.containsKey(key)) {
/* 241 */       this.historyFiles.put(key, new HistoryFileData());
/*     */     }
/* 243 */     return this.historyFiles.get(key);
/*     */   }
/*     */   
/*     */   private void setHistoryFileData(Path path, HistoryFileData historyFileData) {
/* 247 */     this.historyFiles.put(doHistoryFileDataKey(path), historyFileData);
/*     */   }
/*     */   
/*     */   private boolean isLineReaderHistory(Path path) throws IOException {
/* 251 */     Path lrp = getPath();
/* 252 */     if (lrp == null) {
/* 253 */       return (path == null);
/*     */     }
/* 255 */     return Files.isSameFile(lrp, path);
/*     */   }
/*     */   
/*     */   private void setLastLoaded(Path path, int lastloaded) {
/* 259 */     getHistoryFileData(path).setLastLoaded(lastloaded);
/*     */   }
/*     */   
/*     */   private void setEntriesInFile(Path path, int entriesInFile) {
/* 263 */     getHistoryFileData(path).setEntriesInFile(entriesInFile);
/*     */   }
/*     */   
/*     */   private void incEntriesInFile(Path path, int amount) {
/* 267 */     getHistoryFileData(path).incEntriesInFile(amount);
/*     */   }
/*     */   
/*     */   private int getLastLoaded(Path path) {
/* 271 */     return getHistoryFileData(path).getLastLoaded();
/*     */   }
/*     */   
/*     */   private int getEntriesInFile(Path path) {
/* 275 */     return getHistoryFileData(path).getEntriesInFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addHistoryLine(Path path, String line) {
/* 285 */     addHistoryLine(path, line, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addHistoryLine(Path path, String line, boolean checkDuplicates) {
/* 296 */     if (this.reader.isSet(LineReader.Option.HISTORY_TIMESTAMPED)) {
/* 297 */       Instant time; int idx = line.indexOf(':');
/* 298 */       String badHistoryFileSyntax = "Bad history file syntax! The history file `" + path + "` may be an older history: please remove it or use a different history file.";
/*     */       
/* 300 */       if (idx < 0) {
/* 301 */         throw new IllegalArgumentException(badHistoryFileSyntax);
/*     */       }
/*     */       
/*     */       try {
/* 305 */         time = Instant.ofEpochMilli(Long.parseLong(line.substring(0, idx)));
/* 306 */       } catch (DateTimeException|NumberFormatException e) {
/* 307 */         throw new IllegalArgumentException(badHistoryFileSyntax);
/*     */       } 
/*     */       
/* 310 */       String unescaped = unescape(line.substring(idx + 1));
/* 311 */       internalAdd(time, unescaped, checkDuplicates);
/*     */     } else {
/* 313 */       internalAdd(Instant.now(), unescape(line), checkDuplicates);
/*     */     } 
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
/*     */   public void purge() throws IOException {
/* 327 */     internalClear();
/* 328 */     Path path = getPath();
/* 329 */     if (path != null) {
/* 330 */       Log.trace(new Object[] { "Purging history from: ", path });
/* 331 */       Files.deleteIfExists(path);
/*     */     } 
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
/*     */   public void write(Path file, boolean incremental) throws IOException {
/* 347 */     Path path = (file != null) ? file : getPath();
/* 348 */     if (path != null && Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 349 */       Files.deleteIfExists(path);
/*     */     }
/* 351 */     internalWrite(path, incremental ? getLastLoaded(path) : 0);
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
/*     */   public void append(Path file, boolean incremental) throws IOException {
/* 367 */     internalWrite((file != null) ? file : getPath(), incremental ? getLastLoaded(file) : 0);
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
/*     */   public void save() throws IOException {
/* 381 */     internalWrite(getPath(), getLastLoaded(getPath()));
/*     */   }
/*     */   
/*     */   private void internalWrite(Path path, int from) throws IOException {
/* 385 */     if (path != null) {
/* 386 */       Log.trace(new Object[] { "Saving history to: ", path });
/* 387 */       Path parent = path.toAbsolutePath().getParent();
/* 388 */       if (!Files.exists(parent, new java.nio.file.LinkOption[0])) {
/* 389 */         Files.createDirectories(parent, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/*     */       
/* 392 */       BufferedWriter writer = Files.newBufferedWriter(path
/* 393 */           .toAbsolutePath(), new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE });
/*     */ 
/*     */ 
/*     */       
/* 397 */       try { for (History.Entry entry : this.items.subList(from, this.items.size())) {
/* 398 */           if (isPersistable(entry)) {
/* 399 */             writer.append(format(entry));
/*     */           }
/*     */         } 
/* 402 */         if (writer != null) writer.close();  } catch (Throwable throwable) { if (writer != null)
/* 403 */           try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  incEntriesInFile(path, this.items.size() - from);
/* 404 */       int max = ReaderUtils.getInt(this.reader, "history-file-size", 10000);
/* 405 */       if (getEntriesInFile(path) > max + max / 4) {
/* 406 */         trimHistory(path, max);
/*     */       }
/*     */     } 
/* 409 */     setLastLoaded(path, this.items.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void trimHistory(Path path, int max) throws IOException {
/* 420 */     Log.trace(new Object[] { "Trimming history path: ", path });
/*     */     
/* 422 */     LinkedList<History.Entry> allItems = new LinkedList<>();
/* 423 */     BufferedReader historyFileReader = Files.newBufferedReader(path); 
/* 424 */     try { List<String> lines = historyFileReader.lines().collect((Collector)Collectors.toList());
/* 425 */       for (String l : lines) {
/*     */         try {
/* 427 */           if (this.reader.isSet(LineReader.Option.HISTORY_TIMESTAMPED)) {
/* 428 */             int idx = l.indexOf(':');
/* 429 */             if (idx < 0) {
/* 430 */               Log.debug(new Object[] { "Skipping invalid history line: " + l });
/*     */               continue;
/*     */             } 
/*     */             try {
/* 434 */               Instant time = Instant.ofEpochMilli(Long.parseLong(l.substring(0, idx)));
/* 435 */               String line = unescape(l.substring(idx + 1));
/* 436 */               allItems.add(createEntry(allItems.size(), time, line));
/* 437 */             } catch (DateTimeException|NumberFormatException e) {
/* 438 */               Log.debug(new Object[] { "Skipping invalid history timestamp: " + l });
/*     */             }  continue;
/*     */           } 
/* 441 */           allItems.add(createEntry(allItems.size(), Instant.now(), unescape(l)));
/*     */         }
/* 443 */         catch (Exception e) {
/* 444 */           Log.debug(new Object[] { "Skipping invalid history line: " + l, e });
/*     */         } 
/*     */       } 
/* 447 */       if (historyFileReader != null) historyFileReader.close();  } catch (Throwable throwable) { if (historyFileReader != null)
/*     */         try { historyFileReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 449 */      List<History.Entry> trimmedItems = doTrimHistory(allItems, max);
/*     */     
/* 451 */     Path temp = Files.createTempFile(path
/* 452 */         .toAbsolutePath().getParent(), path.getFileName().toString(), ".tmp", (FileAttribute<?>[])new FileAttribute[0]);
/* 453 */     BufferedWriter writer = Files.newBufferedWriter(temp, new OpenOption[] { StandardOpenOption.WRITE }); 
/* 454 */     try { for (History.Entry entry : trimmedItems) {
/* 455 */         writer.append(format(entry));
/*     */       }
/* 457 */       if (writer != null) writer.close();  } catch (Throwable throwable) { if (writer != null)
/* 458 */         try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  Files.move(temp, path, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*     */     
/* 460 */     if (isLineReaderHistory(path)) {
/* 461 */       internalClear();
/* 462 */       this.offset = ((History.Entry)trimmedItems.get(0)).index();
/* 463 */       this.items.addAll(trimmedItems);
/* 464 */       setHistoryFileData(path, new HistoryFileData(this.items.size(), this.items.size()));
/*     */     } else {
/* 466 */       setEntriesInFile(path, allItems.size());
/*     */     } 
/* 468 */     maybeResize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntryImpl createEntry(int index, Instant time, String line) {
/* 479 */     return new EntryImpl(index, time, line);
/*     */   }
/*     */   
/*     */   private void internalClear() {
/* 483 */     this.offset = 0;
/* 484 */     this.index = 0;
/* 485 */     this.historyFiles = new HashMap<>();
/* 486 */     this.items.clear();
/*     */   }
/*     */   
/*     */   static List<History.Entry> doTrimHistory(List<History.Entry> allItems, int max) {
/* 490 */     int idx = 0;
/* 491 */     while (idx < allItems.size()) {
/* 492 */       int ridx = allItems.size() - idx - 1;
/* 493 */       String line = ((History.Entry)allItems.get(ridx)).line().trim();
/* 494 */       ListIterator<History.Entry> iterator = allItems.listIterator(ridx);
/* 495 */       while (iterator.hasPrevious()) {
/* 496 */         String l = ((History.Entry)iterator.previous()).line();
/* 497 */         if (line.equals(l.trim())) {
/* 498 */           iterator.remove();
/*     */         }
/*     */       } 
/* 501 */       idx++;
/*     */     } 
/* 503 */     while (allItems.size() > max) {
/* 504 */       allItems.remove(0);
/*     */     }
/* 506 */     int index = ((History.Entry)allItems.get(allItems.size() - 1)).index() - allItems.size() + 1;
/* 507 */     List<History.Entry> out = new ArrayList<>();
/* 508 */     for (History.Entry e : allItems) {
/* 509 */       out.add(new EntryImpl(index++, e.time(), e.line()));
/*     */     }
/* 511 */     return out;
/*     */   }
/*     */   
/*     */   public int size() {
/* 515 */     return this.items.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 519 */     return this.items.isEmpty();
/*     */   }
/*     */   
/*     */   public int index() {
/* 523 */     return this.offset + this.index;
/*     */   }
/*     */   
/*     */   public int first() {
/* 527 */     return this.offset;
/*     */   }
/*     */   
/*     */   public int last() {
/* 531 */     return this.offset + this.items.size() - 1;
/*     */   }
/*     */   
/*     */   private String format(History.Entry entry) {
/* 535 */     if (this.reader.isSet(LineReader.Option.HISTORY_TIMESTAMPED)) {
/* 536 */       return entry.time().toEpochMilli() + ":" + escape(entry.line()) + "\n";
/*     */     }
/* 538 */     return escape(entry.line()) + "\n";
/*     */   }
/*     */   
/*     */   public String get(int index) {
/* 542 */     int idx = index - this.offset;
/* 543 */     if (idx >= this.items.size() || idx < 0) {
/* 544 */       throw new IllegalArgumentException("IndexOutOfBounds: Index:" + idx + ", Size:" + this.items.size());
/*     */     }
/* 546 */     return ((History.Entry)this.items.get(idx)).line();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Instant time, String line) {
/* 570 */     Objects.requireNonNull(time);
/* 571 */     Objects.requireNonNull(line);
/*     */     
/* 573 */     if (ReaderUtils.getBoolean(this.reader, "disable-history", false)) {
/*     */       return;
/*     */     }
/* 576 */     if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_IGNORE_SPACE) && line.startsWith(" ")) {
/*     */       return;
/*     */     }
/* 579 */     if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_REDUCE_BLANKS)) {
/* 580 */       line = line.trim();
/*     */     }
/* 582 */     if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_IGNORE_DUPS) && 
/* 583 */       !this.items.isEmpty() && line.equals(((History.Entry)this.items.getLast()).line())) {
/*     */       return;
/*     */     }
/*     */     
/* 587 */     if (matchPatterns(ReaderUtils.getString(this.reader, "history-ignore", ""), line)) {
/*     */       return;
/*     */     }
/* 590 */     internalAdd(time, line);
/* 591 */     if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_INCREMENTAL)) {
/*     */       try {
/* 593 */         save();
/* 594 */       } catch (IOException e) {
/* 595 */         Log.warn(new Object[] { "Failed to save history", e });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean matchPatterns(String patterns, String line) {
/* 608 */     if (patterns == null || patterns.isEmpty()) {
/* 609 */       return false;
/*     */     }
/* 611 */     StringBuilder sb = new StringBuilder();
/* 612 */     for (int i = 0; i < patterns.length(); i++) {
/* 613 */       char ch = patterns.charAt(i);
/* 614 */       if (ch == '\\') {
/* 615 */         ch = patterns.charAt(++i);
/* 616 */         sb.append(ch);
/* 617 */       } else if (ch == ':') {
/* 618 */         sb.append('|');
/* 619 */       } else if (ch == '*') {
/* 620 */         sb.append('.').append('*');
/*     */       } else {
/* 622 */         sb.append(ch);
/*     */       } 
/*     */     } 
/* 625 */     return line.matches(sb.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void internalAdd(Instant time, String line) {
/* 635 */     internalAdd(time, line, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void internalAdd(Instant time, String line, boolean checkDuplicates) {
/* 646 */     History.Entry entry = new EntryImpl(this.offset + this.items.size(), time, line);
/* 647 */     if (checkDuplicates) {
/* 648 */       for (History.Entry e : this.items) {
/* 649 */         if (e.line().trim().equals(line.trim())) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     }
/* 654 */     this.items.add(entry);
/* 655 */     maybeResize();
/*     */   }
/*     */   
/*     */   private void maybeResize() {
/* 659 */     while (size() > ReaderUtils.getInt(this.reader, "history-size", 500)) {
/* 660 */       this.items.removeFirst();
/* 661 */       for (HistoryFileData hfd : this.historyFiles.values()) {
/* 662 */         hfd.decLastLoaded();
/*     */       }
/* 664 */       this.offset++;
/*     */     } 
/* 666 */     this.index = size();
/*     */   }
/*     */   
/*     */   public ListIterator<History.Entry> iterator(int index) {
/* 670 */     return this.items.listIterator(index - this.offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public Spliterator<History.Entry> spliterator() {
/* 675 */     return this.items.spliterator();
/*     */   }
/*     */   
/*     */   public void resetIndex() {
/* 679 */     this.index = Math.min(this.index, this.items.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class EntryImpl
/*     */     implements History.Entry
/*     */   {
/*     */     private final int index;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Instant time;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String line;
/*     */ 
/*     */ 
/*     */     
/*     */     public EntryImpl(int index, Instant time, String line) {
/* 701 */       this.index = index;
/* 702 */       this.time = time;
/* 703 */       this.line = line;
/*     */     }
/*     */     
/*     */     public int index() {
/* 707 */       return this.index;
/*     */     }
/*     */     
/*     */     public Instant time() {
/* 711 */       return this.time;
/*     */     }
/*     */     
/*     */     public String line() {
/* 715 */       return this.line;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 720 */       return String.format("%d: %s", new Object[] { Integer.valueOf(this.index), this.line });
/*     */     }
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
/*     */   public boolean moveToLast() {
/* 739 */     int lastEntry = size() - 1;
/* 740 */     if (lastEntry >= 0 && lastEntry != this.index) {
/* 741 */       this.index = size() - 1;
/* 742 */       return true;
/*     */     } 
/*     */     
/* 745 */     return false;
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
/*     */   public boolean moveTo(int index) {
/* 758 */     index -= this.offset;
/* 759 */     if (index >= 0 && index < size()) {
/* 760 */       this.index = index;
/* 761 */       return true;
/*     */     } 
/* 763 */     return false;
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
/*     */   public boolean moveToFirst() {
/* 776 */     if (size() > 0 && this.index != 0) {
/* 777 */       this.index = 0;
/* 778 */       return true;
/*     */     } 
/* 780 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveToEnd() {
/* 791 */     this.index = size();
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
/*     */   public String current() {
/* 803 */     if (this.index >= size()) {
/* 804 */       return "";
/*     */     }
/* 806 */     return ((History.Entry)this.items.get(this.index)).line();
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
/*     */   public boolean previous() {
/* 819 */     if (this.index <= 0) {
/* 820 */       return false;
/*     */     }
/* 822 */     this.index--;
/* 823 */     return true;
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
/*     */   public boolean next() {
/* 836 */     if (this.index >= size()) {
/* 837 */       return false;
/*     */     }
/* 839 */     this.index++;
/* 840 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 845 */     StringBuilder sb = new StringBuilder();
/* 846 */     for (ListIterator<History.Entry> listIterator = iterator(); listIterator.hasNext(); ) { History.Entry e = listIterator.next();
/* 847 */       sb.append(e.toString()).append("\n"); }
/*     */     
/* 849 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static String escape(String s) {
/* 853 */     StringBuilder sb = new StringBuilder();
/* 854 */     for (int i = 0; i < s.length(); i++) {
/* 855 */       char ch = s.charAt(i);
/* 856 */       switch (ch) {
/*     */         case '\n':
/* 858 */           sb.append('\\');
/* 859 */           sb.append('n');
/*     */           break;
/*     */         case '\r':
/* 862 */           sb.append('\\');
/* 863 */           sb.append('r');
/*     */           break;
/*     */         case '\\':
/* 866 */           sb.append('\\');
/* 867 */           sb.append('\\');
/*     */           break;
/*     */         default:
/* 870 */           sb.append(ch);
/*     */           break;
/*     */       } 
/*     */     } 
/* 874 */     return sb.toString();
/*     */   }
/*     */   
/*     */   static String unescape(String s) {
/* 878 */     StringBuilder sb = new StringBuilder();
/* 879 */     for (int i = 0; i < s.length(); i++) {
/* 880 */       char ch = s.charAt(i);
/* 881 */       switch (ch) {
/*     */         case '\\':
/* 883 */           ch = s.charAt(++i);
/* 884 */           if (ch == 'n') {
/* 885 */             sb.append('\n'); break;
/* 886 */           }  if (ch == 'r') {
/* 887 */             sb.append('\r'); break;
/*     */           } 
/* 889 */           sb.append(ch);
/*     */           break;
/*     */         
/*     */         default:
/* 893 */           sb.append(ch);
/*     */           break;
/*     */       } 
/*     */     } 
/* 897 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class HistoryFileData
/*     */   {
/* 908 */     private int lastLoaded = 0;
/* 909 */     private int entriesInFile = 0;
/*     */     
/*     */     public HistoryFileData() {}
/*     */     
/*     */     public HistoryFileData(int lastLoaded, int entriesInFile) {
/* 914 */       this.lastLoaded = lastLoaded;
/* 915 */       this.entriesInFile = entriesInFile;
/*     */     }
/*     */     
/*     */     public int getLastLoaded() {
/* 919 */       return this.lastLoaded;
/*     */     }
/*     */     
/*     */     public void setLastLoaded(int lastLoaded) {
/* 923 */       this.lastLoaded = lastLoaded;
/*     */     }
/*     */     
/*     */     public void decLastLoaded() {
/* 927 */       this.lastLoaded--;
/* 928 */       if (this.lastLoaded < 0) {
/* 929 */         this.lastLoaded = 0;
/*     */       }
/*     */     }
/*     */     
/*     */     public int getEntriesInFile() {
/* 934 */       return this.entriesInFile;
/*     */     }
/*     */     
/*     */     public void setEntriesInFile(int entriesInFile) {
/* 938 */       this.entriesInFile = entriesInFile;
/*     */     }
/*     */     
/*     */     public void incEntriesInFile(int amount) {
/* 942 */       this.entriesInFile += amount;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\history\DefaultHistory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */