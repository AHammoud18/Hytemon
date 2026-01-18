/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpDataFactory
/*     */   implements HttpDataFactory
/*     */ {
/*     */   public static final long MINSIZE = 16384L;
/*     */   public static final long MAXSIZE = -1L;
/*     */   private final boolean useDisk;
/*     */   private final boolean checkSize;
/*     */   private long minSize;
/*  67 */   private long maxSize = -1L;
/*     */   
/*  69 */   private Charset charset = HttpConstants.DEFAULT_CHARSET;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String baseDir;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deleteOnExit;
/*     */ 
/*     */ 
/*     */   
/*  83 */   private final Map<HttpRequest, List<HttpData>> requestFileDeleteMap = Collections.synchronizedMap(new IdentityHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory() {
/*  90 */     this.useDisk = false;
/*  91 */     this.checkSize = true;
/*  92 */     this.minSize = 16384L;
/*     */   }
/*     */   
/*     */   public DefaultHttpDataFactory(Charset charset) {
/*  96 */     this();
/*  97 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory(boolean useDisk) {
/* 104 */     this.useDisk = useDisk;
/* 105 */     this.checkSize = false;
/*     */   }
/*     */   
/*     */   public DefaultHttpDataFactory(boolean useDisk, Charset charset) {
/* 109 */     this(useDisk);
/* 110 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory(long minSize) {
/* 117 */     this.useDisk = false;
/* 118 */     this.checkSize = true;
/* 119 */     this.minSize = minSize;
/*     */   }
/*     */   
/*     */   public DefaultHttpDataFactory(long minSize, Charset charset) {
/* 123 */     this(minSize);
/* 124 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseDir(String baseDir) {
/* 133 */     this.baseDir = baseDir;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeleteOnExit(boolean deleteOnExit) {
/* 143 */     this.deleteOnExit = deleteOnExit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxLimit(long maxSize) {
/* 148 */     this.maxSize = maxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<HttpData> getList(HttpRequest request) {
/* 155 */     List<HttpData> list = this.requestFileDeleteMap.get(request);
/* 156 */     if (list == null) {
/* 157 */       list = new ArrayList<>();
/* 158 */       this.requestFileDeleteMap.put(request, list);
/*     */     } 
/* 160 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name) {
/* 165 */     if (this.useDisk) {
/* 166 */       Attribute attribute1 = new DiskAttribute(name, this.charset, this.baseDir, this.deleteOnExit);
/* 167 */       attribute1.setMaxSize(this.maxSize);
/* 168 */       List<HttpData> list = getList(request);
/* 169 */       list.add(attribute1);
/* 170 */       return attribute1;
/*     */     } 
/* 172 */     if (this.checkSize) {
/* 173 */       Attribute attribute1 = new MixedAttribute(name, this.minSize, this.charset, this.baseDir, this.deleteOnExit);
/* 174 */       attribute1.setMaxSize(this.maxSize);
/* 175 */       List<HttpData> list = getList(request);
/* 176 */       list.add(attribute1);
/* 177 */       return attribute1;
/*     */     } 
/* 179 */     MemoryAttribute attribute = new MemoryAttribute(name);
/* 180 */     attribute.setMaxSize(this.maxSize);
/* 181 */     return attribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name, long definedSize) {
/* 186 */     if (this.useDisk) {
/* 187 */       Attribute attribute1 = new DiskAttribute(name, definedSize, this.charset, this.baseDir, this.deleteOnExit);
/* 188 */       attribute1.setMaxSize(this.maxSize);
/* 189 */       List<HttpData> list = getList(request);
/* 190 */       list.add(attribute1);
/* 191 */       return attribute1;
/*     */     } 
/* 193 */     if (this.checkSize) {
/* 194 */       Attribute attribute1 = new MixedAttribute(name, definedSize, this.minSize, this.charset, this.baseDir, this.deleteOnExit);
/* 195 */       attribute1.setMaxSize(this.maxSize);
/* 196 */       List<HttpData> list = getList(request);
/* 197 */       list.add(attribute1);
/* 198 */       return attribute1;
/*     */     } 
/* 200 */     MemoryAttribute attribute = new MemoryAttribute(name, definedSize);
/* 201 */     attribute.setMaxSize(this.maxSize);
/* 202 */     return attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkHttpDataSize(HttpData data) {
/*     */     try {
/* 210 */       data.checkSize(data.length());
/* 211 */     } catch (IOException ignored) {
/* 212 */       throw new IllegalArgumentException("Attribute bigger than maxSize allowed");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name, String value) {
/* 218 */     if (this.useDisk) {
/*     */       Attribute attribute;
/*     */       try {
/* 221 */         attribute = new DiskAttribute(name, value, this.charset, this.baseDir, this.deleteOnExit);
/* 222 */         attribute.setMaxSize(this.maxSize);
/* 223 */       } catch (IOException e) {
/*     */         
/* 225 */         attribute = new MixedAttribute(name, value, this.minSize, this.charset, this.baseDir, this.deleteOnExit);
/* 226 */         attribute.setMaxSize(this.maxSize);
/*     */       } 
/* 228 */       checkHttpDataSize(attribute);
/* 229 */       List<HttpData> list = getList(request);
/* 230 */       list.add(attribute);
/* 231 */       return attribute;
/*     */     } 
/* 233 */     if (this.checkSize) {
/* 234 */       Attribute attribute = new MixedAttribute(name, value, this.minSize, this.charset, this.baseDir, this.deleteOnExit);
/* 235 */       attribute.setMaxSize(this.maxSize);
/* 236 */       checkHttpDataSize(attribute);
/* 237 */       List<HttpData> list = getList(request);
/* 238 */       list.add(attribute);
/* 239 */       return attribute;
/*     */     } 
/*     */     try {
/* 242 */       MemoryAttribute attribute = new MemoryAttribute(name, value, this.charset);
/* 243 */       attribute.setMaxSize(this.maxSize);
/* 244 */       checkHttpDataSize(attribute);
/* 245 */       return attribute;
/* 246 */     } catch (IOException e) {
/* 247 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload createFileUpload(HttpRequest request, String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/* 255 */     if (this.useDisk) {
/* 256 */       FileUpload fileUpload1 = new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size, this.baseDir, this.deleteOnExit);
/*     */       
/* 258 */       fileUpload1.setMaxSize(this.maxSize);
/* 259 */       checkHttpDataSize(fileUpload1);
/* 260 */       List<HttpData> list = getList(request);
/* 261 */       list.add(fileUpload1);
/* 262 */       return fileUpload1;
/*     */     } 
/* 264 */     if (this.checkSize) {
/* 265 */       FileUpload fileUpload1 = new MixedFileUpload(name, filename, contentType, contentTransferEncoding, charset, size, this.minSize, this.baseDir, this.deleteOnExit);
/*     */       
/* 267 */       fileUpload1.setMaxSize(this.maxSize);
/* 268 */       checkHttpDataSize(fileUpload1);
/* 269 */       List<HttpData> list = getList(request);
/* 270 */       list.add(fileUpload1);
/* 271 */       return fileUpload1;
/*     */     } 
/* 273 */     MemoryFileUpload fileUpload = new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */     
/* 275 */     fileUpload.setMaxSize(this.maxSize);
/* 276 */     checkHttpDataSize(fileUpload);
/* 277 */     return fileUpload;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeHttpDataFromClean(HttpRequest request, InterfaceHttpData data) {
/* 282 */     if (!(data instanceof HttpData)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 288 */     List<HttpData> list = this.requestFileDeleteMap.get(request);
/* 289 */     if (list == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 295 */     Iterator<HttpData> i = list.iterator();
/* 296 */     while (i.hasNext()) {
/* 297 */       HttpData n = i.next();
/* 298 */       if (n == data) {
/* 299 */         i.remove();
/*     */ 
/*     */         
/* 302 */         if (list.isEmpty()) {
/* 303 */           this.requestFileDeleteMap.remove(request);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanRequestHttpData(HttpRequest request) {
/* 313 */     List<HttpData> list = this.requestFileDeleteMap.remove(request);
/* 314 */     if (list != null) {
/* 315 */       for (HttpData data : list) {
/* 316 */         data.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanAllHttpData() {
/* 323 */     Iterator<Map.Entry<HttpRequest, List<HttpData>>> i = this.requestFileDeleteMap.entrySet().iterator();
/* 324 */     while (i.hasNext()) {
/* 325 */       Map.Entry<HttpRequest, List<HttpData>> e = i.next();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 330 */       List<HttpData> list = e.getValue();
/* 331 */       for (HttpData data : list) {
/* 332 */         data.release();
/*     */       }
/*     */       
/* 335 */       i.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanRequestHttpDatas(HttpRequest request) {
/* 341 */     cleanRequestHttpData(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanAllHttpDatas() {
/* 346 */     cleanAllHttpData();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\DefaultHttpDataFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */