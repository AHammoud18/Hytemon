/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.ViewHierarchy;
/*     */ import java.io.File;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Attachment
/*     */ {
/*     */   @Nullable
/*     */   private byte[] bytes;
/*     */   @Nullable
/*     */   private final JsonSerializable serializable;
/*     */   @Nullable
/*     */   private final Callable<byte[]> byteProvider;
/*     */   @Nullable
/*  21 */   private String attachmentType = "event.attachment";
/*     */   
/*     */   @Nullable
/*     */   private String pathname;
/*     */   
/*     */   @NotNull
/*     */   private final String filename;
/*     */   
/*     */   @Nullable
/*     */   private final String contentType;
/*     */   private final boolean addToTransactions;
/*     */   private static final String DEFAULT_ATTACHMENT_TYPE = "event.attachment";
/*     */   private static final String VIEW_HIERARCHY_ATTACHMENT_TYPE = "event.view_hierarchy";
/*     */   
/*     */   public Attachment(@NotNull byte[] bytes, @NotNull String filename) {
/*  36 */     this(bytes, filename, (String)null);
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
/*     */   public Attachment(@NotNull byte[] bytes, @NotNull String filename, @Nullable String contentType) {
/*  51 */     this(bytes, filename, contentType, false);
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
/*     */   public Attachment(@NotNull byte[] bytes, @NotNull String filename, @Nullable String contentType, boolean addToTransactions) {
/*  68 */     this(bytes, filename, contentType, "event.attachment", addToTransactions);
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
/*     */   public Attachment(@NotNull byte[] bytes, @NotNull String filename, @Nullable String contentType, @Nullable String attachmentType, boolean addToTransactions) {
/*  87 */     this.bytes = bytes;
/*  88 */     this.serializable = null;
/*  89 */     this.byteProvider = null;
/*  90 */     this.filename = filename;
/*  91 */     this.contentType = contentType;
/*  92 */     this.attachmentType = attachmentType;
/*  93 */     this.addToTransactions = addToTransactions;
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
/*     */   public Attachment(@NotNull JsonSerializable serializable, @NotNull String filename, @Nullable String contentType, @Nullable String attachmentType, boolean addToTransactions) {
/* 113 */     this.bytes = null;
/* 114 */     this.serializable = serializable;
/* 115 */     this.byteProvider = null;
/* 116 */     this.filename = filename;
/* 117 */     this.contentType = contentType;
/* 118 */     this.attachmentType = attachmentType;
/* 119 */     this.addToTransactions = addToTransactions;
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
/*     */   public Attachment(@NotNull Callable<byte[]> byteProvider, @NotNull String filename, @Nullable String contentType, @Nullable String attachmentType, boolean addToTransactions) {
/* 139 */     this.bytes = null;
/* 140 */     this.serializable = null;
/* 141 */     this.byteProvider = byteProvider;
/* 142 */     this.filename = filename;
/* 143 */     this.contentType = contentType;
/* 144 */     this.attachmentType = attachmentType;
/* 145 */     this.addToTransactions = addToTransactions;
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
/*     */   public Attachment(@NotNull String pathname) {
/* 159 */     this(pathname, (new File(pathname)).getName());
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
/*     */   public Attachment(@NotNull String pathname, @NotNull String filename) {
/* 174 */     this(pathname, filename, (String)null);
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
/*     */   public Attachment(@NotNull String pathname, @NotNull String filename, @Nullable String contentType) {
/* 193 */     this(pathname, filename, contentType, "event.attachment", false);
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
/*     */   public Attachment(@NotNull String pathname, @NotNull String filename, @Nullable String contentType, @Nullable String attachmentType, boolean addToTransactions) {
/* 216 */     this.pathname = pathname;
/* 217 */     this.filename = filename;
/* 218 */     this.serializable = null;
/* 219 */     this.byteProvider = null;
/* 220 */     this.contentType = contentType;
/* 221 */     this.attachmentType = attachmentType;
/* 222 */     this.addToTransactions = addToTransactions;
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
/*     */   public Attachment(@NotNull String pathname, @NotNull String filename, @Nullable String contentType, boolean addToTransactions) {
/* 243 */     this.pathname = pathname;
/* 244 */     this.filename = filename;
/* 245 */     this.serializable = null;
/* 246 */     this.byteProvider = null;
/* 247 */     this.contentType = contentType;
/* 248 */     this.addToTransactions = addToTransactions;
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
/*     */   public Attachment(@NotNull String pathname, @NotNull String filename, @Nullable String contentType, boolean addToTransactions, @Nullable String attachmentType) {
/* 272 */     this.pathname = pathname;
/* 273 */     this.filename = filename;
/* 274 */     this.serializable = null;
/* 275 */     this.byteProvider = null;
/* 276 */     this.contentType = contentType;
/* 277 */     this.addToTransactions = addToTransactions;
/* 278 */     this.attachmentType = attachmentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public byte[] getBytes() {
/* 287 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public JsonSerializable getSerializable() {
/* 296 */     return this.serializable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getPathname() {
/* 305 */     return this.pathname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getFilename() {
/* 314 */     return this.filename;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getContentType() {
/* 324 */     return this.contentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isAddToTransactions() {
/* 334 */     return this.addToTransactions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getAttachmentType() {
/* 343 */     return this.attachmentType;
/*     */   }
/*     */   @Nullable
/*     */   public Callable<byte[]> getByteProvider() {
/* 347 */     return this.byteProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Attachment fromScreenshot(byte[] screenshotBytes) {
/* 357 */     return new Attachment(screenshotBytes, "screenshot.png", "image/png", false);
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
/*     */   @NotNull
/*     */   public static Attachment fromByteProvider(@NotNull Callable<byte[]> provider, @NotNull String filename, @Nullable String contentType, boolean addToTransactions) {
/* 371 */     return new Attachment(provider, filename, contentType, "event.attachment", addToTransactions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Attachment fromViewHierarchy(ViewHierarchy viewHierarchy) {
/* 382 */     return new Attachment((JsonSerializable)viewHierarchy, "view-hierarchy.json", "application/json", "event.view_hierarchy", false);
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
/*     */   @NotNull
/*     */   public static Attachment fromThreadDump(byte[] bytes) {
/* 397 */     return new Attachment(bytes, "thread-dump.txt", "text/plain", false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Attachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */