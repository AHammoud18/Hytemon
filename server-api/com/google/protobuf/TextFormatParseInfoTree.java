/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ public class TextFormatParseInfoTree
/*     */ {
/*     */   private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;
/*     */   Map<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>> subtreesFromField;
/*     */   
/*     */   private TextFormatParseInfoTree(Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField, Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField) {
/*  48 */     Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locs = new HashMap<>();
/*     */     
/*  50 */     for (Map.Entry<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> kv : locationsFromField.entrySet()) {
/*  51 */       locs.put(kv.getKey(), Collections.unmodifiableList(kv.getValue()));
/*     */     }
/*  53 */     this.locationsFromField = Collections.unmodifiableMap(locs);
/*     */     
/*  55 */     Map<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>> subs = new HashMap<>();
/*     */     
/*  57 */     for (Map.Entry<Descriptors.FieldDescriptor, List<Builder>> kv : subtreeBuildersFromField.entrySet()) {
/*  58 */       List<TextFormatParseInfoTree> submessagesOfField = new ArrayList<>();
/*  59 */       for (Builder subBuilder : kv.getValue()) {
/*  60 */         submessagesOfField.add(subBuilder.build());
/*     */       }
/*  62 */       subs.put(kv.getKey(), Collections.unmodifiableList(submessagesOfField));
/*     */     } 
/*  64 */     this.subtreesFromField = Collections.unmodifiableMap(subs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TextFormatParseLocation> getLocations(Descriptors.FieldDescriptor fieldDescriptor) {
/*  75 */     List<TextFormatParseLocation> result = this.locationsFromField.get(fieldDescriptor);
/*  76 */     return (result == null) ? Collections.<TextFormatParseLocation>emptyList() : result;
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
/*     */   public TextFormatParseLocation getLocation(Descriptors.FieldDescriptor fieldDescriptor, int index) {
/*  91 */     return getFromList(getLocations(fieldDescriptor), index, fieldDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TextFormatParseInfoTree> getNestedTrees(Descriptors.FieldDescriptor fieldDescriptor) {
/* 101 */     List<TextFormatParseInfoTree> result = this.subtreesFromField.get(fieldDescriptor);
/* 102 */     return (result == null) ? Collections.<TextFormatParseInfoTree>emptyList() : result;
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
/*     */   public TextFormatParseInfoTree getNestedTree(Descriptors.FieldDescriptor fieldDescriptor, int index) {
/* 115 */     return getFromList(getNestedTrees(fieldDescriptor), index, fieldDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/* 124 */     return new Builder();
/*     */   }
/*     */   
/*     */   private static <T> T getFromList(List<T> list, int index, Descriptors.FieldDescriptor fieldDescriptor) {
/* 128 */     if (index >= list.size() || index < 0)
/* 129 */       throw new IllegalArgumentException(
/* 130 */           String.format("Illegal index field: %s, index %d", new Object[] {
/*     */               
/* 132 */               (fieldDescriptor == null) ? "<null>" : fieldDescriptor.getName(), Integer.valueOf(index)
/*     */             })); 
/* 134 */     return list.get(index);
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
/*     */   public static class Builder
/*     */   {
/* 148 */     private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField = new HashMap<>();
/* 149 */     private Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setLocation(Descriptors.FieldDescriptor fieldDescriptor, TextFormatParseLocation location) {
/* 160 */       List<TextFormatParseLocation> fieldLocations = this.locationsFromField.get(fieldDescriptor);
/* 161 */       if (fieldLocations == null) {
/* 162 */         fieldLocations = new ArrayList<>();
/* 163 */         this.locationsFromField.put(fieldDescriptor, fieldLocations);
/*     */       } 
/* 165 */       fieldLocations.add(location);
/* 166 */       return this;
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
/*     */     public Builder getBuilderForSubMessageField(Descriptors.FieldDescriptor fieldDescriptor) {
/* 179 */       List<Builder> submessageBuilders = this.subtreeBuildersFromField.get(fieldDescriptor);
/* 180 */       if (submessageBuilders == null) {
/* 181 */         submessageBuilders = new ArrayList<>();
/* 182 */         this.subtreeBuildersFromField.put(fieldDescriptor, submessageBuilders);
/*     */       } 
/* 184 */       Builder subtreeBuilder = new Builder();
/* 185 */       submessageBuilders.add(subtreeBuilder);
/* 186 */       return subtreeBuilder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TextFormatParseInfoTree build() {
/* 195 */       return new TextFormatParseInfoTree(this.locationsFromField, this.subtreeBuildersFromField);
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\TextFormatParseInfoTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */