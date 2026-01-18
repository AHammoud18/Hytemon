/*      */ package io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufHolder;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.handler.codec.DecoderResult;
/*      */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*      */ import io.netty.handler.codec.http.DefaultHttpContent;
/*      */ import io.netty.handler.codec.http.EmptyHttpHeaders;
/*      */ import io.netty.handler.codec.http.FullHttpMessage;
/*      */ import io.netty.handler.codec.http.FullHttpRequest;
/*      */ import io.netty.handler.codec.http.HttpConstants;
/*      */ import io.netty.handler.codec.http.HttpContent;
/*      */ import io.netty.handler.codec.http.HttpHeaderNames;
/*      */ import io.netty.handler.codec.http.HttpHeaderValues;
/*      */ import io.netty.handler.codec.http.HttpHeaders;
/*      */ import io.netty.handler.codec.http.HttpMessage;
/*      */ import io.netty.handler.codec.http.HttpMethod;
/*      */ import io.netty.handler.codec.http.HttpRequest;
/*      */ import io.netty.handler.codec.http.HttpUtil;
/*      */ import io.netty.handler.codec.http.HttpVersion;
/*      */ import io.netty.handler.codec.http.LastHttpContent;
/*      */ import io.netty.handler.stream.ChunkedInput;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URLEncoder;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.concurrent.ThreadLocalRandom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpPostRequestEncoder
/*      */   implements ChunkedInput<HttpContent>
/*      */ {
/*      */   private static final String ASTERISK = "*";
/*      */   private static final String PLUS = "+";
/*      */   private static final String TILDE = "~";
/*      */   private static final String ASTERISK_REPLACEMENT = "%2A";
/*      */   private static final String PLUS_REPLACEMENT = "%20";
/*      */   private static final String TILDE_REPLACEMENT = "%7E";
/*      */   private final HttpDataFactory factory;
/*      */   private final HttpRequest request;
/*      */   private final Charset charset;
/*      */   private boolean isChunked;
/*      */   private final List<InterfaceHttpData> bodyListDatas;
/*      */   final List<InterfaceHttpData> multipartHttpDatas;
/*      */   private final boolean isMultipart;
/*      */   String multipartDataBoundary;
/*      */   String multipartMixedBoundary;
/*      */   private boolean headerFinalized;
/*      */   private final EncoderMode encoderMode;
/*      */   private boolean isLastChunk;
/*      */   private boolean isLastChunkSent;
/*      */   private FileUpload currentFileUpload;
/*      */   private boolean duringMixedMode;
/*      */   private long globalBodySize;
/*      */   private long globalProgress;
/*      */   private ListIterator<InterfaceHttpData> iterator;
/*      */   private ByteBuf currentBuffer;
/*      */   private InterfaceHttpData currentData;
/*      */   private boolean isKey;
/*      */   
/*      */   public enum EncoderMode
/*      */   {
/*   75 */     RFC1738,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   80 */     RFC3986,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   91 */     HTML5;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestEncoder(HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
/*  163 */     this(new DefaultHttpDataFactory(16384L), request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
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
/*      */   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
/*  182 */     this(factory, request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart, Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
/*  856 */     this.isKey = true; this.request = (HttpRequest)ObjectUtil.checkNotNull(request, "request"); this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset"); this.factory = (HttpDataFactory)ObjectUtil.checkNotNull(factory, "factory"); if (HttpMethod.TRACE.equals(request.method())) throw new ErrorDataEncoderException("Cannot create a Encoder if request is a TRACE");  this.bodyListDatas = new ArrayList<>(); this.isLastChunk = false; this.isLastChunkSent = false; this.isMultipart = multipart; this.multipartHttpDatas = new ArrayList<>(); this.encoderMode = encoderMode; if (this.isMultipart) initDataMultipart(); 
/*      */   } public void cleanFiles() { this.factory.cleanRequestHttpData(this.request); } public boolean isMultipart() { return this.isMultipart; }
/*      */   private void initDataMultipart() { this.multipartDataBoundary = getNewMultipartDelimiter(); }
/*      */   private void initMixedMultipart() { this.multipartMixedBoundary = getNewMultipartDelimiter(); }
/*      */   private static String getNewMultipartDelimiter() { return Long.toHexString(ThreadLocalRandom.current().nextLong()); }
/*      */   public List<InterfaceHttpData> getBodyListAttributes() { return this.bodyListDatas; }
/*      */   public void setBodyHttpDatas(List<InterfaceHttpData> datas) throws ErrorDataEncoderException { ObjectUtil.checkNotNull(datas, "datas"); this.globalBodySize = 0L; this.bodyListDatas.clear(); this.currentFileUpload = null; this.duringMixedMode = false; this.multipartHttpDatas.clear(); for (InterfaceHttpData data : datas) addBodyHttpData(data);  }
/*  863 */   private ByteBuf fillByteBuf() { int length = this.currentBuffer.readableBytes();
/*  864 */     if (length > 8096) {
/*  865 */       return this.currentBuffer.readRetainedSlice(8096);
/*      */     }
/*      */     
/*  868 */     ByteBuf slice = this.currentBuffer;
/*  869 */     this.currentBuffer = null;
/*  870 */     return slice; } public void addBodyAttribute(String name, String value) throws ErrorDataEncoderException { String svalue = (value != null) ? value : ""; Attribute data = this.factory.createAttribute(this.request, (String)ObjectUtil.checkNotNull(name, "name"), svalue); addBodyHttpData(data); }
/*      */   public void addBodyFileUpload(String name, File file, String contentType, boolean isText) throws ErrorDataEncoderException { addBodyFileUpload(name, file.getName(), file, contentType, isText); }
/*      */   public void addBodyFileUpload(String name, String filename, File file, String contentType, boolean isText) throws ErrorDataEncoderException { ObjectUtil.checkNotNull(name, "name"); ObjectUtil.checkNotNull(file, "file"); if (filename == null)
/*      */       filename = "";  String scontentType = contentType; String contentTransferEncoding = null; if (contentType == null)
/*      */       if (isText) { scontentType = "text/plain"; }
/*      */       else { scontentType = "application/octet-stream"; }
/*      */         if (!isText)
/*      */       contentTransferEncoding = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();  FileUpload fileUpload = this.factory.createFileUpload(this.request, name, filename, scontentType, contentTransferEncoding, null, file.length()); try {
/*      */       fileUpload.setContent(file);
/*      */     } catch (IOException e) {
/*      */       throw new ErrorDataEncoderException(e);
/*      */     }  addBodyHttpData(fileUpload); }
/*      */   public void addBodyFileUploads(String name, File[] file, String[] contentType, boolean[] isText) throws ErrorDataEncoderException { if (file.length != contentType.length && file.length != isText.length)
/*      */       throw new IllegalArgumentException("Different array length");  for (int i = 0; i < file.length; i++)
/*      */       addBodyFileUpload(name, file[i], contentType[i], isText[i]);  }
/*  885 */   private HttpContent encodeNextChunkMultipart(int sizeleft) throws ErrorDataEncoderException { if (this.currentData == null) {
/*  886 */       return null;
/*      */     }
/*      */     
/*  889 */     if (this.currentData instanceof InternalAttribute) {
/*  890 */       buffer = ((InternalAttribute)this.currentData).toByteBuf();
/*  891 */       this.currentData = null;
/*      */     } else {
/*      */       try {
/*  894 */         buffer = ((HttpData)this.currentData).getChunk(sizeleft);
/*  895 */       } catch (IOException e) {
/*  896 */         throw new ErrorDataEncoderException(e);
/*      */       } 
/*  898 */       if (buffer.capacity() == 0) {
/*      */         
/*  900 */         this.currentData = null;
/*  901 */         return null;
/*      */       } 
/*      */     } 
/*  904 */     if (this.currentBuffer == null) {
/*  905 */       this.currentBuffer = buffer;
/*      */     } else {
/*  907 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer });
/*      */     } 
/*  909 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  910 */       this.currentData = null;
/*  911 */       return null;
/*      */     } 
/*  913 */     ByteBuf buffer = fillByteBuf();
/*  914 */     return (HttpContent)new DefaultHttpContent(buffer); }
/*      */   public void addBodyHttpData(InterfaceHttpData data) throws ErrorDataEncoderException { if (this.headerFinalized)
/*      */       throw new ErrorDataEncoderException("Cannot add value once finalized");  this.bodyListDatas.add((InterfaceHttpData)ObjectUtil.checkNotNull(data, "data")); if (!this.isMultipart) { if (data instanceof Attribute) { Attribute attribute = (Attribute)data; try { String key = encodeAttribute(attribute.getName(), this.charset); String value = encodeAttribute(attribute.getValue(), this.charset); Attribute newattribute = this.factory.createAttribute(this.request, key, value); this.multipartHttpDatas.add(newattribute); this.globalBodySize += (newattribute.getName().length() + 1) + newattribute.length() + 1L; } catch (IOException e) { throw new ErrorDataEncoderException(e); }  } else if (data instanceof FileUpload) { FileUpload fileUpload = (FileUpload)data; String key = encodeAttribute(fileUpload.getName(), this.charset); String value = encodeAttribute(fileUpload.getFilename(), this.charset); Attribute newattribute = this.factory.createAttribute(this.request, key, value); this.multipartHttpDatas.add(newattribute); this.globalBodySize += (newattribute.getName().length() + 1) + newattribute.length() + 1L; }  return; }  if (data instanceof Attribute) { if (this.duringMixedMode) { InternalAttribute internalAttribute = new InternalAttribute(this.charset); internalAttribute.addValue("\r\n--" + this.multipartMixedBoundary + "--"); this.multipartHttpDatas.add(internalAttribute); this.multipartMixedBoundary = null; this.currentFileUpload = null; this.duringMixedMode = false; }  InternalAttribute internal = new InternalAttribute(this.charset); if (!this.multipartHttpDatas.isEmpty())
/*      */         internal.addValue("\r\n");  internal.addValue("--" + this.multipartDataBoundary + "\r\n"); Attribute attribute = (Attribute)data; internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + attribute.getName() + "\"\r\n"); internal.addValue(HttpHeaderNames.CONTENT_LENGTH + ": " + attribute.length() + "\r\n"); Charset localcharset = attribute.getCharset(); if (localcharset != null)
/*      */         internal.addValue(HttpHeaderNames.CONTENT_TYPE + ": " + "text/plain" + "; " + HttpHeaderValues.CHARSET + '=' + localcharset.name() + "\r\n");  internal.addValue("\r\n"); this.multipartHttpDatas.add(internal); this.multipartHttpDatas.add(data); this.globalBodySize += attribute.length() + internal.size(); } else if (data instanceof FileUpload) { boolean localMixed; FileUpload fileUpload = (FileUpload)data; InternalAttribute internal = new InternalAttribute(this.charset); if (!this.multipartHttpDatas.isEmpty())
/*      */         internal.addValue("\r\n");  if (this.duringMixedMode) { if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) { localMixed = true; } else { internal.addValue("--" + this.multipartMixedBoundary + "--"); this.multipartHttpDatas.add(internal); this.multipartMixedBoundary = null; internal = new InternalAttribute(this.charset); internal.addValue("\r\n"); localMixed = false; this.currentFileUpload = fileUpload; this.duringMixedMode = false; }  } else if (this.encoderMode != EncoderMode.HTML5 && this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) { initMixedMultipart(); InternalAttribute pastAttribute = (InternalAttribute)this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2); this.globalBodySize -= pastAttribute.size(); StringBuilder replacement = (new StringBuilder(139 + this.multipartDataBoundary.length() + this.multipartMixedBoundary.length() * 2 + fileUpload.getFilename().length() + fileUpload.getName().length())).append("--").append(this.multipartDataBoundary).append("\r\n").append((CharSequence)HttpHeaderNames.CONTENT_DISPOSITION).append(": ").append((CharSequence)HttpHeaderValues.FORM_DATA).append("; ").append((CharSequence)HttpHeaderValues.NAME).append("=\"").append(fileUpload.getName()).append("\"\r\n").append((CharSequence)HttpHeaderNames.CONTENT_TYPE).append(": ").append((CharSequence)HttpHeaderValues.MULTIPART_MIXED).append("; ").append((CharSequence)HttpHeaderValues.BOUNDARY).append('=').append(this.multipartMixedBoundary).append("\r\n\r\n").append("--").append(this.multipartMixedBoundary).append("\r\n").append((CharSequence)HttpHeaderNames.CONTENT_DISPOSITION).append(": ").append((CharSequence)HttpHeaderValues.ATTACHMENT); if (!fileUpload.getFilename().isEmpty())
/*      */           replacement.append("; ").append((CharSequence)HttpHeaderValues.FILENAME).append("=\"").append(this.currentFileUpload.getFilename()).append('"');  replacement.append("\r\n"); pastAttribute.setValue(replacement.toString(), 1); pastAttribute.setValue("", 2); this.globalBodySize += pastAttribute.size(); localMixed = true; this.duringMixedMode = true; } else { localMixed = false; this.currentFileUpload = fileUpload; this.duringMixedMode = false; }  if (localMixed) { internal.addValue("--" + this.multipartMixedBoundary + "\r\n"); if (fileUpload.getFilename().isEmpty()) { internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.ATTACHMENT + "\r\n"); } else { internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.ATTACHMENT + "; " + HttpHeaderValues.FILENAME + "=\"" + fileUpload.getFilename() + "\"\r\n"); }  }
/*      */       else { internal.addValue("--" + this.multipartDataBoundary + "\r\n"); if (fileUpload.getFilename().isEmpty()) { internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + fileUpload.getName() + "\"\r\n"); }
/*      */         else { internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + fileUpload.getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + fileUpload.getFilename() + "\"\r\n"); }
/*      */          }
/*      */        internal.addValue(HttpHeaderNames.CONTENT_LENGTH + ": " + fileUpload.length() + "\r\n"); internal.addValue(HttpHeaderNames.CONTENT_TYPE + ": " + fileUpload.getContentType()); String contentTransferEncoding = fileUpload.getContentTransferEncoding(); if (contentTransferEncoding != null && contentTransferEncoding.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) { internal.addValue("\r\n" + HttpHeaderNames.CONTENT_TRANSFER_ENCODING + ": " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n"); }
/*      */       else if (fileUpload.getCharset() != null) { internal.addValue("; " + HttpHeaderValues.CHARSET + '=' + fileUpload.getCharset().name() + "\r\n\r\n"); }
/*      */       else { internal.addValue("\r\n\r\n"); }
/*      */        this.multipartHttpDatas.add(internal); this.multipartHttpDatas.add(data); this.globalBodySize += fileUpload.length() + internal.size(); }
/*  928 */      } private HttpContent encodeNextChunkUrlEncoded(int sizeleft) throws ErrorDataEncoderException { ByteBuf buffer; if (this.currentData == null) {
/*  929 */       return null;
/*      */     }
/*  931 */     int size = sizeleft;
/*      */ 
/*      */ 
/*      */     
/*  935 */     if (this.isKey) {
/*  936 */       String key = this.currentData.getName();
/*  937 */       buffer = Unpooled.wrappedBuffer(key.getBytes(this.charset));
/*  938 */       this.isKey = false;
/*  939 */       if (this.currentBuffer == null) {
/*  940 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { buffer, Unpooled.wrappedBuffer("=".getBytes(this.charset)) });
/*      */       } else {
/*  942 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer, Unpooled.wrappedBuffer("=".getBytes(this.charset)) });
/*      */       } 
/*      */       
/*  945 */       size -= buffer.readableBytes() + 1;
/*  946 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  947 */         buffer = fillByteBuf();
/*  948 */         return (HttpContent)new DefaultHttpContent(buffer);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  954 */       buffer = ((HttpData)this.currentData).getChunk(size);
/*  955 */     } catch (IOException e) {
/*  956 */       throw new ErrorDataEncoderException(e);
/*      */     } 
/*      */ 
/*      */     
/*  960 */     ByteBuf delimiter = null;
/*  961 */     if (buffer.readableBytes() < size) {
/*  962 */       this.isKey = true;
/*  963 */       this.currentData = null;
/*  964 */       delimiter = this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes(this.charset)) : null;
/*      */     } 
/*      */ 
/*      */     
/*  968 */     if (buffer.capacity() == 0) {
/*  969 */       this.isKey = true;
/*  970 */       this.currentData = null;
/*  971 */       if (this.currentBuffer == null) {
/*  972 */         if (delimiter == null) {
/*  973 */           return null;
/*      */         }
/*  975 */         this.currentBuffer = delimiter;
/*      */       
/*      */       }
/*  978 */       else if (delimiter != null) {
/*  979 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, delimiter });
/*      */       } 
/*      */       
/*  982 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  983 */         buffer = fillByteBuf();
/*  984 */         return (HttpContent)new DefaultHttpContent(buffer);
/*      */       } 
/*  986 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  990 */     if (this.currentBuffer == null) {
/*  991 */       if (delimiter != null) {
/*  992 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { buffer, delimiter });
/*      */       } else {
/*  994 */         this.currentBuffer = buffer;
/*      */       }
/*      */     
/*  997 */     } else if (delimiter != null) {
/*  998 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer, delimiter });
/*      */     } else {
/* 1000 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer });
/*      */     } 
/*      */ 
/*      */     
/* 1004 */     if (this.currentBuffer.readableBytes() >= 8096) {
/* 1005 */       return (HttpContent)new DefaultHttpContent(fillByteBuf());
/*      */     }
/* 1007 */     return null; }
/*      */   public HttpRequest finalizeRequest() throws ErrorDataEncoderException { if (!this.headerFinalized) { if (this.isMultipart) { InternalAttribute internal = new InternalAttribute(this.charset); if (this.duringMixedMode)
/*      */           internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");  internal.addValue("\r\n--" + this.multipartDataBoundary + "--\r\n"); this.multipartHttpDatas.add(internal); this.multipartMixedBoundary = null; this.currentFileUpload = null; this.duringMixedMode = false; this.globalBodySize += internal.size(); }  this.headerFinalized = true; } else { throw new ErrorDataEncoderException("Header already encoded"); }  HttpHeaders headers = this.request.headers(); List<String> contentTypes = headers.getAll((CharSequence)HttpHeaderNames.CONTENT_TYPE); List<String> transferEncoding = headers.getAll((CharSequence)HttpHeaderNames.TRANSFER_ENCODING); if (contentTypes != null) { headers.remove((CharSequence)HttpHeaderNames.CONTENT_TYPE); for (String contentType : contentTypes) { String lowercased = contentType.toLowerCase(); if (lowercased.startsWith(HttpHeaderValues.MULTIPART_FORM_DATA.toString()) || lowercased.startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString()))
/*      */           continue;  headers.add((CharSequence)HttpHeaderNames.CONTENT_TYPE, contentType); }  }  if (this.isMultipart) { String value = HttpHeaderValues.MULTIPART_FORM_DATA + "; " + HttpHeaderValues.BOUNDARY + '=' + this.multipartDataBoundary; headers.add((CharSequence)HttpHeaderNames.CONTENT_TYPE, value); } else { headers.add((CharSequence)HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED); }  long realSize = this.globalBodySize; if (!this.isMultipart)
/*      */       realSize--;  this.iterator = this.multipartHttpDatas.listIterator(); headers.set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, String.valueOf(realSize)); if (realSize > 8096L || this.isMultipart) { this.isChunked = true; if (transferEncoding != null) { headers.remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING); for (CharSequence v : transferEncoding) { if (HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(v))
/*      */             continue;  headers.add((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, v); }  }  HttpUtil.setTransferEncodingChunked((HttpMessage)this.request, true); return new WrappedHttpRequest(this.request); }  HttpContent chunk = nextChunk(); if (this.request instanceof FullHttpRequest) { FullHttpRequest fullRequest = (FullHttpRequest)this.request; ByteBuf chunkContent = chunk.content(); if (fullRequest.content() != chunkContent) { fullRequest.content().clear().writeBytes(chunkContent); chunkContent.release(); }  return (HttpRequest)fullRequest; }
/*      */      return new WrappedFullHttpRequest(this.request, chunk); }
/*      */   public boolean isChunked() { return this.isChunked; }
/*      */   private String encodeAttribute(String s, Charset charset) throws ErrorDataEncoderException { if (s == null)
/*      */       return "";  try { String encoded = URLEncoder.encode(s, charset.name()); if (this.encoderMode == EncoderMode.RFC3986)
/*      */         encoded = encoded.replace("*", "%2A").replace("+", "%20").replace("~", "%7E");  return encoded; }
/*      */     catch (UnsupportedEncodingException e) { throw new ErrorDataEncoderException(charset.name(), e); }
/* 1019 */      } public void close() throws Exception {} @Deprecated public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception { return readChunk(ctx.alloc()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpContent readChunk(ByteBufAllocator allocator) throws Exception {
/* 1032 */     if (this.isLastChunkSent) {
/* 1033 */       return null;
/*      */     }
/* 1035 */     HttpContent nextChunk = nextChunk();
/* 1036 */     this.globalProgress += nextChunk.content().readableBytes();
/* 1037 */     return nextChunk;
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
/*      */   private HttpContent nextChunk() throws ErrorDataEncoderException {
/* 1050 */     if (this.isLastChunk) {
/* 1051 */       this.isLastChunkSent = true;
/* 1052 */       return (HttpContent)LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     } 
/*      */     
/* 1055 */     int size = calculateRemainingSize();
/* 1056 */     if (size <= 0) {
/*      */       
/* 1058 */       ByteBuf buffer = fillByteBuf();
/* 1059 */       return (HttpContent)new DefaultHttpContent(buffer);
/*      */     } 
/*      */     
/* 1062 */     if (this.currentData != null) {
/*      */       HttpContent chunk;
/*      */       
/* 1065 */       if (this.isMultipart) {
/* 1066 */         chunk = encodeNextChunkMultipart(size);
/*      */       } else {
/* 1068 */         chunk = encodeNextChunkUrlEncoded(size);
/*      */       } 
/* 1070 */       if (chunk != null)
/*      */       {
/* 1072 */         return chunk;
/*      */       }
/* 1074 */       size = calculateRemainingSize();
/*      */     } 
/* 1076 */     if (!this.iterator.hasNext()) {
/* 1077 */       return lastChunk();
/*      */     }
/* 1079 */     while (size > 0 && this.iterator.hasNext()) {
/* 1080 */       HttpContent chunk; this.currentData = this.iterator.next();
/*      */       
/* 1082 */       if (this.isMultipart) {
/* 1083 */         chunk = encodeNextChunkMultipart(size);
/*      */       } else {
/* 1085 */         chunk = encodeNextChunkUrlEncoded(size);
/*      */       } 
/* 1087 */       if (chunk == null) {
/*      */         
/* 1089 */         size = calculateRemainingSize();
/*      */         
/*      */         continue;
/*      */       } 
/* 1093 */       return chunk;
/*      */     } 
/*      */     
/* 1096 */     return lastChunk();
/*      */   }
/*      */   
/*      */   private int calculateRemainingSize() {
/* 1100 */     int size = 8096;
/* 1101 */     if (this.currentBuffer != null) {
/* 1102 */       size -= this.currentBuffer.readableBytes();
/*      */     }
/* 1104 */     return size;
/*      */   }
/*      */   
/*      */   private HttpContent lastChunk() {
/* 1108 */     this.isLastChunk = true;
/* 1109 */     if (this.currentBuffer == null) {
/* 1110 */       this.isLastChunkSent = true;
/*      */       
/* 1112 */       return (HttpContent)LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     } 
/*      */     
/* 1115 */     ByteBuf buffer = this.currentBuffer;
/* 1116 */     this.currentBuffer = null;
/* 1117 */     return (HttpContent)new DefaultHttpContent(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEndOfInput() throws Exception {
/* 1122 */     return this.isLastChunkSent;
/*      */   }
/*      */ 
/*      */   
/*      */   public long length() {
/* 1127 */     return this.isMultipart ? this.globalBodySize : (this.globalBodySize - 1L);
/*      */   }
/*      */ 
/*      */   
/*      */   public long progress() {
/* 1132 */     return this.globalProgress;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ErrorDataEncoderException
/*      */     extends Exception
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */ 
/*      */     
/*      */     public ErrorDataEncoderException() {}
/*      */     
/*      */     public ErrorDataEncoderException(String msg) {
/* 1145 */       super(msg);
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(Throwable cause) {
/* 1149 */       super(cause);
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(String msg, Throwable cause) {
/* 1153 */       super(msg, cause);
/*      */     } }
/*      */   
/*      */   private static class WrappedHttpRequest implements HttpRequest {
/*      */     private final HttpRequest request;
/*      */     
/*      */     WrappedHttpRequest(HttpRequest request) {
/* 1160 */       this.request = request;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setProtocolVersion(HttpVersion version) {
/* 1165 */       this.request.setProtocolVersion(version);
/* 1166 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setMethod(HttpMethod method) {
/* 1171 */       this.request.setMethod(method);
/* 1172 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setUri(String uri) {
/* 1177 */       this.request.setUri(uri);
/* 1178 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpMethod getMethod() {
/* 1183 */       return this.request.method();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpMethod method() {
/* 1188 */       return this.request.method();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getUri() {
/* 1193 */       return this.request.uri();
/*      */     }
/*      */ 
/*      */     
/*      */     public String uri() {
/* 1198 */       return this.request.uri();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpVersion getProtocolVersion() {
/* 1203 */       return this.request.protocolVersion();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpVersion protocolVersion() {
/* 1208 */       return this.request.protocolVersion();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpHeaders headers() {
/* 1213 */       return this.request.headers();
/*      */     }
/*      */ 
/*      */     
/*      */     public DecoderResult decoderResult() {
/* 1218 */       return this.request.decoderResult();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public DecoderResult getDecoderResult() {
/* 1224 */       return this.request.getDecoderResult();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDecoderResult(DecoderResult result) {
/* 1229 */       this.request.setDecoderResult(result);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class WrappedFullHttpRequest extends WrappedHttpRequest implements FullHttpRequest {
/*      */     private final HttpContent content;
/*      */     
/*      */     private WrappedFullHttpRequest(HttpRequest request, HttpContent content) {
/* 1237 */       super(request);
/* 1238 */       this.content = content;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setProtocolVersion(HttpVersion version) {
/* 1243 */       super.setProtocolVersion(version);
/* 1244 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setMethod(HttpMethod method) {
/* 1249 */       super.setMethod(method);
/* 1250 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setUri(String uri) {
/* 1255 */       super.setUri(uri);
/* 1256 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest copy() {
/* 1261 */       return replace(content().copy());
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest duplicate() {
/* 1266 */       return replace(content().duplicate());
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retainedDuplicate() {
/* 1271 */       return replace(content().retainedDuplicate());
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest replace(ByteBuf content) {
/* 1276 */       DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), content);
/* 1277 */       duplicate.headers().set(headers());
/* 1278 */       duplicate.trailingHeaders().set(trailingHeaders());
/* 1279 */       return (FullHttpRequest)duplicate;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retain(int increment) {
/* 1284 */       this.content.retain(increment);
/* 1285 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retain() {
/* 1290 */       this.content.retain();
/* 1291 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest touch() {
/* 1296 */       this.content.touch();
/* 1297 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest touch(Object hint) {
/* 1302 */       this.content.touch(hint);
/* 1303 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf content() {
/* 1308 */       return this.content.content();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpHeaders trailingHeaders() {
/* 1313 */       if (this.content instanceof LastHttpContent) {
/* 1314 */         return ((LastHttpContent)this.content).trailingHeaders();
/*      */       }
/* 1316 */       return (HttpHeaders)EmptyHttpHeaders.INSTANCE;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int refCnt() {
/* 1322 */       return this.content.refCnt();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean release() {
/* 1327 */       return this.content.release();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean release(int decrement) {
/* 1332 */       return this.content.release(decrement);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\HttpPostRequestEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */