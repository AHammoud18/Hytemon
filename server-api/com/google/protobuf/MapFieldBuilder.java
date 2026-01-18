/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class MapFieldBuilder<KeyT, MessageOrBuilderT extends MessageOrBuilder, MessageT extends MessageOrBuilderT, BuilderT extends MessageOrBuilderT>
/*     */   extends MapFieldReflectionAccessor
/*     */ {
/*  33 */   Map<KeyT, MessageOrBuilderT> builderMap = new LinkedHashMap<>();
/*     */ 
/*     */   
/*  36 */   Map<KeyT, MessageT> messageMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   List<Message> messageList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Converter<KeyT, MessageOrBuilderT, MessageT> converter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapFieldBuilder(Converter<KeyT, MessageOrBuilderT, MessageT> converter) {
/*  56 */     this.converter = converter;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<MapEntry<KeyT, MessageT>> getMapEntryList() {
/*  61 */     ArrayList<MapEntry<KeyT, MessageT>> list = new ArrayList<>(this.messageList.size());
/*  62 */     Class<?> valueClass = ((MessageOrBuilder)this.converter.defaultEntry().getValue()).getClass();
/*  63 */     for (Message entry : this.messageList) {
/*  64 */       MapEntry<KeyT, ?> typedEntry = (MapEntry<KeyT, ?>)entry;
/*  65 */       if (valueClass.isInstance(typedEntry.getValue())) {
/*  66 */         list.add(typedEntry);
/*     */         continue;
/*     */       } 
/*  69 */       list.add(this.converter.defaultEntry().toBuilder().mergeFrom(entry).build());
/*     */     } 
/*     */     
/*  72 */     return list;
/*     */   }
/*     */   
/*     */   public Map<KeyT, MessageOrBuilderT> ensureBuilderMap() {
/*  76 */     if (this.builderMap != null) {
/*  77 */       return this.builderMap;
/*     */     }
/*  79 */     if (this.messageMap != null) {
/*  80 */       this.builderMap = new LinkedHashMap<>(this.messageMap.size());
/*  81 */       for (Map.Entry<KeyT, MessageT> entry : this.messageMap.entrySet()) {
/*  82 */         this.builderMap.put(entry.getKey(), (MessageOrBuilderT)entry.getValue());
/*     */       }
/*  84 */       this.messageMap = null;
/*  85 */       return this.builderMap;
/*     */     } 
/*  87 */     this.builderMap = new LinkedHashMap<>(this.messageList.size());
/*  88 */     for (MapEntry<KeyT, MessageT> entry : getMapEntryList()) {
/*  89 */       this.builderMap.put(entry.getKey(), (MessageOrBuilderT)entry.getValue());
/*     */     }
/*  91 */     this.messageList = null;
/*  92 */     return this.builderMap;
/*     */   }
/*     */   
/*     */   public List<Message> ensureMessageList() {
/*  96 */     if (this.messageList != null) {
/*  97 */       return this.messageList;
/*     */     }
/*  99 */     if (this.builderMap != null) {
/* 100 */       this.messageList = new ArrayList<>(this.builderMap.size());
/* 101 */       for (Map.Entry<KeyT, MessageOrBuilderT> entry : this.builderMap.entrySet()) {
/* 102 */         this.messageList.add(this.converter
/* 103 */             .defaultEntry().toBuilder()
/* 104 */             .setKey(entry.getKey())
/* 105 */             .setValue(this.converter.build(entry.getValue()))
/* 106 */             .build());
/*     */       }
/* 108 */       this.builderMap = null;
/* 109 */       return this.messageList;
/*     */     } 
/* 111 */     this.messageList = new ArrayList<>(this.messageMap.size());
/* 112 */     for (Map.Entry<KeyT, MessageT> entry : this.messageMap.entrySet()) {
/* 113 */       this.messageList.add(this.converter
/* 114 */           .defaultEntry().toBuilder()
/* 115 */           .setKey(entry.getKey())
/* 116 */           .setValue((MessageOrBuilder)entry.getValue())
/* 117 */           .build());
/*     */     }
/* 119 */     this.messageMap = null;
/* 120 */     return this.messageList;
/*     */   }
/*     */   
/*     */   public Map<KeyT, MessageT> ensureMessageMap() {
/* 124 */     this.messageMap = populateMutableMap();
/* 125 */     this.builderMap = null;
/* 126 */     this.messageList = null;
/* 127 */     return this.messageMap;
/*     */   }
/*     */   
/*     */   public Map<KeyT, MessageT> getImmutableMap() {
/* 131 */     return new MapField.MutabilityAwareMap<>(MutabilityOracle.IMMUTABLE, populateMutableMap());
/*     */   }
/*     */   
/*     */   private Map<KeyT, MessageT> populateMutableMap() {
/* 135 */     if (this.messageMap != null) {
/* 136 */       return this.messageMap;
/*     */     }
/* 138 */     if (this.builderMap != null) {
/* 139 */       Map<KeyT, MessageT> map = new LinkedHashMap<>(this.builderMap.size());
/* 140 */       for (Map.Entry<KeyT, MessageOrBuilderT> entry : this.builderMap.entrySet()) {
/* 141 */         map.put(entry.getKey(), this.converter.build(entry.getValue()));
/*     */       }
/* 143 */       return map;
/*     */     } 
/* 145 */     Map<KeyT, MessageT> toReturn = new LinkedHashMap<>(this.messageList.size());
/* 146 */     for (MapEntry<KeyT, MessageT> entry : getMapEntryList()) {
/* 147 */       toReturn.put(entry.getKey(), entry.getValue());
/*     */     }
/* 149 */     return toReturn;
/*     */   }
/*     */   
/*     */   public void mergeFrom(MapField<KeyT, MessageT> other) {
/* 153 */     ensureBuilderMap().putAll(MapFieldLite.copy(other.getMap()));
/*     */   }
/*     */   
/*     */   public void clear() {
/* 157 */     this.builderMap = new LinkedHashMap<>();
/* 158 */     this.messageMap = null;
/* 159 */     this.messageList = null;
/*     */   }
/*     */   
/*     */   private boolean typedEquals(MapFieldBuilder<KeyT, MessageOrBuilderT, MessageT, BuilderT> other) {
/* 163 */     return MapFieldLite.equals(
/* 164 */         ensureBuilderMap(), other.ensureBuilderMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 171 */     if (!(object instanceof MapFieldBuilder)) {
/* 172 */       return false;
/*     */     }
/* 174 */     return typedEquals((MapFieldBuilder<KeyT, MessageOrBuilderT, MessageT, BuilderT>)object);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 179 */     return MapFieldLite.calculateHashCodeForMap(ensureBuilderMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public MapFieldBuilder<KeyT, MessageOrBuilderT, MessageT, BuilderT> copy() {
/* 184 */     MapFieldBuilder<KeyT, MessageOrBuilderT, MessageT, BuilderT> clone = new MapFieldBuilder(this.converter);
/*     */     
/* 186 */     clone.ensureBuilderMap().putAll(ensureBuilderMap());
/* 187 */     return clone;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapField<KeyT, MessageT> build(MapEntry<KeyT, MessageT> defaultEntry) {
/* 192 */     MapField<KeyT, MessageT> mapField = MapField.newMapField(defaultEntry);
/* 193 */     Map<KeyT, MessageT> map = mapField.getMutableMap();
/* 194 */     for (Map.Entry<KeyT, MessageOrBuilderT> entry : ensureBuilderMap().entrySet()) {
/* 195 */       map.put(entry.getKey(), this.converter.build(entry.getValue()));
/*     */     }
/* 197 */     mapField.makeImmutable();
/* 198 */     return mapField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<Message> getList() {
/* 205 */     return ensureMessageList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   List<Message> getMutableList() {
/* 211 */     return ensureMessageList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Message getMapEntryMessageDefaultInstance() {
/* 217 */     return this.converter.defaultEntry();
/*     */   }
/*     */   
/*     */   public static interface Converter<KeyT, MessageOrBuilderT extends MessageOrBuilder, MessageT extends MessageOrBuilderT> {
/*     */     MessageT build(MessageOrBuilderT param1MessageOrBuilderT);
/*     */     
/*     */     MapEntry<KeyT, MessageT> defaultEntry();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapFieldBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */