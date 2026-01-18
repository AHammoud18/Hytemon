/*     */ package io.sentry.cache;
/*     */ 
/*     */ import io.sentry.ISerializer;
/*     */ import io.sentry.SentryEnvelope;
/*     */ import io.sentry.SentryEnvelopeItem;
/*     */ import io.sentry.SentryItemType;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.Session;
/*     */ import io.sentry.util.LazyEvaluator;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class CacheStrategy
/*     */ {
/*  37 */   protected static final Charset UTF_8 = Charset.forName("UTF-8"); @NotNull
/*     */   protected SentryOptions options;
/*     */   @NotNull
/*  40 */   protected final LazyEvaluator<ISerializer> serializer = new LazyEvaluator(() -> this.options.getSerializer());
/*     */   
/*     */   @NotNull
/*     */   protected final File directory;
/*     */   
/*     */   private final int maxSize;
/*     */ 
/*     */   
/*     */   CacheStrategy(@NotNull SentryOptions options, @NotNull String directoryPath, int maxSize) {
/*  49 */     Objects.requireNonNull(directoryPath, "Directory is required.");
/*  50 */     this.options = (SentryOptions)Objects.requireNonNull(options, "SentryOptions is required.");
/*     */     
/*  52 */     this.directory = new File(directoryPath);
/*     */     
/*  54 */     this.maxSize = maxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDirectoryValid() {
/*  63 */     if (!this.directory.isDirectory() || !this.directory.canWrite() || !this.directory.canRead()) {
/*  64 */       this.options
/*  65 */         .getLogger()
/*  66 */         .log(SentryLevel.ERROR, "The directory for caching files is inaccessible.: %s", new Object[] {
/*     */ 
/*     */             
/*  69 */             this.directory.getAbsolutePath() });
/*  70 */       return false;
/*     */     } 
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortFilesOldestToNewest(@NotNull File[] files) {
/*  82 */     if (files.length > 1) {
/*  83 */       Arrays.sort(files, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rotateCacheIfNeeded(@NotNull File[] files) {
/*  93 */     int length = files.length;
/*  94 */     if (length >= this.maxSize) {
/*  95 */       this.options
/*  96 */         .getLogger()
/*  97 */         .log(SentryLevel.WARNING, "Cache folder if full (respecting maxSize). Rotating files", new Object[0]);
/*  98 */       int totalToBeDeleted = length - this.maxSize + 1;
/*     */       
/* 100 */       sortFilesOldestToNewest(files);
/*     */       
/* 102 */       File[] notDeletedFiles = Arrays.<File>copyOfRange(files, totalToBeDeleted, length);
/*     */ 
/*     */       
/* 105 */       for (int i = 0; i < totalToBeDeleted; i++) {
/* 106 */         File file = files[i];
/*     */ 
/*     */         
/* 109 */         moveInitFlagIfNecessary(file, notDeletedFiles);
/*     */         
/* 111 */         if (!file.delete()) {
/* 112 */           this.options
/* 113 */             .getLogger()
/* 114 */             .log(SentryLevel.WARNING, "File can't be deleted: %s", new Object[] { file.getAbsolutePath() });
/*     */         }
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
/*     */   private void moveInitFlagIfNecessary(@NotNull File currentFile, @NotNull File[] notDeletedFiles) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokespecial readEnvelope : (Ljava/io/File;)Lio/sentry/SentryEnvelope;
/*     */     //   5: astore_3
/*     */     //   6: aload_3
/*     */     //   7: ifnull -> 18
/*     */     //   10: aload_0
/*     */     //   11: aload_3
/*     */     //   12: invokespecial isValidEnvelope : (Lio/sentry/SentryEnvelope;)Z
/*     */     //   15: ifne -> 19
/*     */     //   18: return
/*     */     //   19: aload_0
/*     */     //   20: getfield options : Lio/sentry/SentryOptions;
/*     */     //   23: invokevirtual getClientReportRecorder : ()Lio/sentry/clientreport/IClientReportRecorder;
/*     */     //   26: getstatic io/sentry/clientreport/DiscardReason.CACHE_OVERFLOW : Lio/sentry/clientreport/DiscardReason;
/*     */     //   29: aload_3
/*     */     //   30: invokeinterface recordLostEnvelope : (Lio/sentry/clientreport/DiscardReason;Lio/sentry/SentryEnvelope;)V
/*     */     //   35: aload_0
/*     */     //   36: aload_3
/*     */     //   37: invokespecial getFirstSession : (Lio/sentry/SentryEnvelope;)Lio/sentry/Session;
/*     */     //   40: astore #4
/*     */     //   42: aload #4
/*     */     //   44: ifnull -> 56
/*     */     //   47: aload_0
/*     */     //   48: aload #4
/*     */     //   50: invokespecial isValidSession : (Lio/sentry/Session;)Z
/*     */     //   53: ifne -> 57
/*     */     //   56: return
/*     */     //   57: aload #4
/*     */     //   59: invokevirtual getInit : ()Ljava/lang/Boolean;
/*     */     //   62: astore #5
/*     */     //   64: aload #5
/*     */     //   66: ifnull -> 77
/*     */     //   69: aload #5
/*     */     //   71: invokevirtual booleanValue : ()Z
/*     */     //   74: ifne -> 78
/*     */     //   77: return
/*     */     //   78: aload_2
/*     */     //   79: astore #6
/*     */     //   81: aload #6
/*     */     //   83: arraylength
/*     */     //   84: istore #7
/*     */     //   86: iconst_0
/*     */     //   87: istore #8
/*     */     //   89: iload #8
/*     */     //   91: iload #7
/*     */     //   93: if_icmpge -> 425
/*     */     //   96: aload #6
/*     */     //   98: iload #8
/*     */     //   100: aaload
/*     */     //   101: astore #9
/*     */     //   103: aload_0
/*     */     //   104: aload #9
/*     */     //   106: invokespecial readEnvelope : (Ljava/io/File;)Lio/sentry/SentryEnvelope;
/*     */     //   109: astore #10
/*     */     //   111: aload #10
/*     */     //   113: ifnull -> 419
/*     */     //   116: aload_0
/*     */     //   117: aload #10
/*     */     //   119: invokespecial isValidEnvelope : (Lio/sentry/SentryEnvelope;)Z
/*     */     //   122: ifne -> 128
/*     */     //   125: goto -> 419
/*     */     //   128: aconst_null
/*     */     //   129: astore #11
/*     */     //   131: aload #10
/*     */     //   133: invokevirtual getItems : ()Ljava/lang/Iterable;
/*     */     //   136: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   141: astore #12
/*     */     //   143: aload #12
/*     */     //   145: invokeinterface hasNext : ()Z
/*     */     //   150: ifeq -> 347
/*     */     //   153: aload #12
/*     */     //   155: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   160: checkcast io/sentry/SentryEnvelopeItem
/*     */     //   163: astore #13
/*     */     //   165: aload_0
/*     */     //   166: aload #13
/*     */     //   168: invokespecial isSessionType : (Lio/sentry/SentryEnvelopeItem;)Z
/*     */     //   171: ifne -> 177
/*     */     //   174: goto -> 143
/*     */     //   177: aload_0
/*     */     //   178: aload #13
/*     */     //   180: invokespecial readSession : (Lio/sentry/SentryEnvelopeItem;)Lio/sentry/Session;
/*     */     //   183: astore #14
/*     */     //   185: aload #14
/*     */     //   187: ifnull -> 143
/*     */     //   190: aload_0
/*     */     //   191: aload #14
/*     */     //   193: invokespecial isValidSession : (Lio/sentry/Session;)Z
/*     */     //   196: ifne -> 202
/*     */     //   199: goto -> 143
/*     */     //   202: aload #14
/*     */     //   204: invokevirtual getInit : ()Ljava/lang/Boolean;
/*     */     //   207: astore #15
/*     */     //   209: aload #15
/*     */     //   211: ifnull -> 252
/*     */     //   214: aload #15
/*     */     //   216: invokevirtual booleanValue : ()Z
/*     */     //   219: ifeq -> 252
/*     */     //   222: aload_0
/*     */     //   223: getfield options : Lio/sentry/SentryOptions;
/*     */     //   226: invokevirtual getLogger : ()Lio/sentry/ILogger;
/*     */     //   229: getstatic io/sentry/SentryLevel.ERROR : Lio/sentry/SentryLevel;
/*     */     //   232: ldc 'Session %s has 2 times the init flag.'
/*     */     //   234: iconst_1
/*     */     //   235: anewarray java/lang/Object
/*     */     //   238: dup
/*     */     //   239: iconst_0
/*     */     //   240: aload #4
/*     */     //   242: invokevirtual getSessionId : ()Ljava/lang/String;
/*     */     //   245: aastore
/*     */     //   246: invokeinterface log : (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   251: return
/*     */     //   252: aload #4
/*     */     //   254: invokevirtual getSessionId : ()Ljava/lang/String;
/*     */     //   257: ifnull -> 344
/*     */     //   260: aload #4
/*     */     //   262: invokevirtual getSessionId : ()Ljava/lang/String;
/*     */     //   265: aload #14
/*     */     //   267: invokevirtual getSessionId : ()Ljava/lang/String;
/*     */     //   270: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   273: ifeq -> 344
/*     */     //   276: aload #14
/*     */     //   278: invokevirtual setInitAsTrue : ()V
/*     */     //   281: aload_0
/*     */     //   282: getfield serializer : Lio/sentry/util/LazyEvaluator;
/*     */     //   285: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   288: checkcast io/sentry/ISerializer
/*     */     //   291: aload #14
/*     */     //   293: invokestatic fromSession : (Lio/sentry/ISerializer;Lio/sentry/Session;)Lio/sentry/SentryEnvelopeItem;
/*     */     //   296: astore #11
/*     */     //   298: aload #12
/*     */     //   300: invokeinterface remove : ()V
/*     */     //   305: goto -> 347
/*     */     //   308: astore #16
/*     */     //   310: aload_0
/*     */     //   311: getfield options : Lio/sentry/SentryOptions;
/*     */     //   314: invokevirtual getLogger : ()Lio/sentry/ILogger;
/*     */     //   317: getstatic io/sentry/SentryLevel.ERROR : Lio/sentry/SentryLevel;
/*     */     //   320: aload #16
/*     */     //   322: ldc 'Failed to create new envelope item for the session %s'
/*     */     //   324: iconst_1
/*     */     //   325: anewarray java/lang/Object
/*     */     //   328: dup
/*     */     //   329: iconst_0
/*     */     //   330: aload #4
/*     */     //   332: invokevirtual getSessionId : ()Ljava/lang/String;
/*     */     //   335: aastore
/*     */     //   336: invokeinterface log : (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   341: goto -> 347
/*     */     //   344: goto -> 143
/*     */     //   347: aload #11
/*     */     //   349: ifnull -> 419
/*     */     //   352: aload_0
/*     */     //   353: aload #10
/*     */     //   355: aload #11
/*     */     //   357: invokespecial buildNewEnvelope : (Lio/sentry/SentryEnvelope;Lio/sentry/SentryEnvelopeItem;)Lio/sentry/SentryEnvelope;
/*     */     //   360: astore #13
/*     */     //   362: aload #9
/*     */     //   364: invokevirtual lastModified : ()J
/*     */     //   367: lstore #14
/*     */     //   369: aload #9
/*     */     //   371: invokevirtual delete : ()Z
/*     */     //   374: ifne -> 406
/*     */     //   377: aload_0
/*     */     //   378: getfield options : Lio/sentry/SentryOptions;
/*     */     //   381: invokevirtual getLogger : ()Lio/sentry/ILogger;
/*     */     //   384: getstatic io/sentry/SentryLevel.WARNING : Lio/sentry/SentryLevel;
/*     */     //   387: ldc 'File can't be deleted: %s'
/*     */     //   389: iconst_1
/*     */     //   390: anewarray java/lang/Object
/*     */     //   393: dup
/*     */     //   394: iconst_0
/*     */     //   395: aload #9
/*     */     //   397: invokevirtual getAbsolutePath : ()Ljava/lang/String;
/*     */     //   400: aastore
/*     */     //   401: invokeinterface log : (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   406: aload_0
/*     */     //   407: aload #13
/*     */     //   409: aload #9
/*     */     //   411: lload #14
/*     */     //   413: invokespecial saveNewEnvelope : (Lio/sentry/SentryEnvelope;Ljava/io/File;J)V
/*     */     //   416: goto -> 425
/*     */     //   419: iinc #8, 1
/*     */     //   422: goto -> 89
/*     */     //   425: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #122	-> 0
/*     */     //   #124	-> 6
/*     */     //   #125	-> 18
/*     */     //   #128	-> 19
/*     */     //   #129	-> 23
/*     */     //   #130	-> 30
/*     */     //   #132	-> 35
/*     */     //   #134	-> 42
/*     */     //   #135	-> 56
/*     */     //   #139	-> 57
/*     */     //   #140	-> 64
/*     */     //   #141	-> 77
/*     */     //   #145	-> 78
/*     */     //   #146	-> 103
/*     */     //   #148	-> 111
/*     */     //   #149	-> 125
/*     */     //   #152	-> 128
/*     */     //   #153	-> 131
/*     */     //   #155	-> 143
/*     */     //   #156	-> 153
/*     */     //   #158	-> 165
/*     */     //   #159	-> 174
/*     */     //   #162	-> 177
/*     */     //   #164	-> 185
/*     */     //   #165	-> 199
/*     */     //   #168	-> 202
/*     */     //   #169	-> 209
/*     */     //   #170	-> 222
/*     */     //   #171	-> 226
/*     */     //   #172	-> 242
/*     */     //   #173	-> 251
/*     */     //   #176	-> 252
/*     */     //   #177	-> 262
/*     */     //   #178	-> 276
/*     */     //   #180	-> 281
/*     */     //   #183	-> 298
/*     */     //   #192	-> 305
/*     */     //   #184	-> 308
/*     */     //   #185	-> 310
/*     */     //   #186	-> 314
/*     */     //   #191	-> 332
/*     */     //   #187	-> 336
/*     */     //   #194	-> 341
/*     */     //   #196	-> 344
/*     */     //   #198	-> 347
/*     */     //   #199	-> 352
/*     */     //   #201	-> 362
/*     */     //   #202	-> 369
/*     */     //   #203	-> 377
/*     */     //   #204	-> 381
/*     */     //   #208	-> 397
/*     */     //   #205	-> 401
/*     */     //   #211	-> 406
/*     */     //   #212	-> 416
/*     */     //   #145	-> 419
/*     */     //   #215	-> 425
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   310	31	16	e	Ljava/io/IOException;
/*     */     //   165	179	13	envelopeItem	Lio/sentry/SentryEnvelopeItem;
/*     */     //   185	159	14	session	Lio/sentry/Session;
/*     */     //   209	135	15	init	Ljava/lang/Boolean;
/*     */     //   362	57	13	newEnvelope	Lio/sentry/SentryEnvelope;
/*     */     //   369	50	14	notDeletedFileTimestamp	J
/*     */     //   111	308	10	envelope	Lio/sentry/SentryEnvelope;
/*     */     //   131	288	11	newSessionItem	Lio/sentry/SentryEnvelopeItem;
/*     */     //   143	276	12	itemsIterator	Ljava/util/Iterator;
/*     */     //   103	316	9	notDeletedFile	Ljava/io/File;
/*     */     //   0	426	0	this	Lio/sentry/cache/CacheStrategy;
/*     */     //   0	426	1	currentFile	Ljava/io/File;
/*     */     //   0	426	2	notDeletedFiles	[Ljava/io/File;
/*     */     //   6	420	3	currentEnvelope	Lio/sentry/SentryEnvelope;
/*     */     //   42	384	4	currentSession	Lio/sentry/Session;
/*     */     //   64	362	5	currentSessionInit	Ljava/lang/Boolean;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   143	276	12	itemsIterator	Ljava/util/Iterator<Lio/sentry/SentryEnvelopeItem;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   281	305	308	java/io/IOException
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
/*     */   @Nullable
/*     */   private SentryEnvelope readEnvelope(@NotNull File file) {
/*     */     
/* 218 */     try { InputStream inputStream = new BufferedInputStream(new FileInputStream(file)); 
/* 219 */       try { SentryEnvelope sentryEnvelope = ((ISerializer)this.serializer.getValue()).deserializeEnvelope(inputStream);
/* 220 */         inputStream.close(); return sentryEnvelope; } catch (Throwable throwable) { try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 221 */     { this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the envelope.", e);
/*     */ 
/*     */       
/* 224 */       return null; }
/*     */   
/*     */   } @Nullable
/*     */   private Session getFirstSession(@NotNull SentryEnvelope envelope) {
/* 228 */     for (SentryEnvelopeItem item : envelope.getItems()) {
/* 229 */       if (!isSessionType(item)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 234 */       return readSession(item);
/*     */     } 
/* 236 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isValidSession(@NotNull Session session) {
/* 240 */     if (!session.getStatus().equals(Session.State.Ok)) {
/* 241 */       return false;
/*     */     }
/*     */     
/* 244 */     String sessionId = session.getSessionId();
/*     */     
/* 246 */     return (sessionId != null);
/*     */   }
/*     */   
/*     */   private boolean isSessionType(@Nullable SentryEnvelopeItem item) {
/* 250 */     if (item == null) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     return item.getHeader().getType().equals(SentryItemType.Session);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Session readSession(@NotNull SentryEnvelopeItem item) {
/*     */     
/* 260 */     try { Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(item.getData()), UTF_8)); 
/* 261 */       try { Session session = (Session)((ISerializer)this.serializer.getValue()).deserialize(reader, Session.class);
/* 262 */         reader.close(); return session; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 263 */     { this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the session.", e);
/*     */       
/* 265 */       return null; }
/*     */   
/*     */   }
/*     */   private void saveNewEnvelope(@NotNull SentryEnvelope envelope, @NotNull File file, long timestamp) {
/*     */     
/* 270 */     try { OutputStream outputStream = new FileOutputStream(file); 
/* 271 */       try { ((ISerializer)this.serializer.getValue()).serialize(envelope, outputStream);
/*     */         
/* 273 */         file.setLastModified(timestamp);
/* 274 */         outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 275 */     { this.options.getLogger().log(SentryLevel.ERROR, "Failed to serialize the new envelope to the disk.", e); }
/*     */   
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private SentryEnvelope buildNewEnvelope(@NotNull SentryEnvelope envelope, @NotNull SentryEnvelopeItem sessionItem) {
/* 281 */     List<SentryEnvelopeItem> newEnvelopeItems = new ArrayList<>();
/*     */     
/* 283 */     for (SentryEnvelopeItem newEnvelopeItem : envelope.getItems()) {
/* 284 */       newEnvelopeItems.add(newEnvelopeItem);
/*     */     }
/* 286 */     newEnvelopeItems.add(sessionItem);
/*     */     
/* 288 */     return new SentryEnvelope(envelope.getHeader(), newEnvelopeItems);
/*     */   }
/*     */   
/*     */   private boolean isValidEnvelope(@NotNull SentryEnvelope envelope) {
/* 292 */     if (!envelope.getItems().iterator().hasNext()) {
/* 293 */       return false;
/*     */     }
/* 295 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\CacheStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */