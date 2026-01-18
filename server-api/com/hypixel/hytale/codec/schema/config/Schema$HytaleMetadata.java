/*      */ package com.hypixel.hytale.codec.schema.config;
/*      */ 
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*      */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIButton;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorFeatures;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorPreview;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIRebuildCaches;
/*      */ import java.util.Arrays;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HytaleMetadata
/*      */ {
/*      */   public static final BuilderCodec<HytaleMetadata> CODEC;
/*      */   private String type;
/*      */   private String path;
/*      */   private String virtualPath;
/*      */   private String extension;
/*      */   private String idProvider;
/*      */   private String[] internalKeys;
/*      */   private Boolean inheritsProperty;
/*      */   private Boolean mergesProperties;
/*      */   private UIEditorFeatures.EditorFeature[] uiEditorFeatures;
/*      */   private UIEditorPreview.PreviewType uiEditorPreview;
/*      */   private String uiTypeIcon;
/*      */   private Boolean uiEditorIgnore;
/*      */   private Boolean allowEmptyObject;
/*      */   private UIDisplayMode.DisplayMode uiDisplayMode;
/*      */   private UIEditor.EditorComponent uiEditorComponent;
/*      */   private String uiPropertyTitle;
/*      */   private String uiSectionStart;
/*      */   private UIRebuildCaches.ClientCache[] uiRebuildCaches;
/*      */   private Boolean uiRebuildCachesForChildProperties;
/*      */   private UIButton[] uiSidebarButtons;
/*      */   private Boolean uiCollapsedByDefault;
/*      */   private UIButton[] uiCreateButtons;
/*      */   
/*      */   static {
/*  725 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HytaleMetadata.class, HytaleMetadata::new).addField(new KeyedCodec("type", (Codec)Codec.STRING, false, true), (o, i) -> o.type = i, o -> (o.type != null && o.type.isEmpty()) ? null : o.type)).addField(new KeyedCodec("internalKeys", (Codec)Codec.STRING_ARRAY, false, true), (o, i) -> o.internalKeys = i, o -> o.internalKeys)).addField(new KeyedCodec("path", (Codec)Codec.STRING, false, true), (o, i) -> o.path = i, o -> o.path)).addField(new KeyedCodec("virtualPath", (Codec)Codec.STRING, false, true), (o, i) -> o.virtualPath = i, o -> o.virtualPath)).addField(new KeyedCodec("extension", (Codec)Codec.STRING, false, true), (o, i) -> o.extension = i, o -> o.extension)).addField(new KeyedCodec("idProvider", (Codec)Codec.STRING, false, true), (o, i) -> o.idProvider = i, o -> o.idProvider)).addField(new KeyedCodec("inheritsProperty", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.inheritsProperty = i, o -> o.inheritsProperty)).addField(new KeyedCodec("mergesProperties", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.mergesProperties = i, o -> o.mergesProperties)).addField(new KeyedCodec("uiDisplayMode", (Codec)new EnumCodec(UIDisplayMode.DisplayMode.class), false, true), (o, i) -> o.uiDisplayMode = i, o -> o.uiDisplayMode)).addField(new KeyedCodec("uiEditorComponent", (Codec)UIEditor.CODEC, false, true), (o, i) -> o.uiEditorComponent = i, o -> o.uiEditorComponent)).addField(new KeyedCodec("allowEmptyObject", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.allowEmptyObject = i, o -> o.allowEmptyObject)).addField(new KeyedCodec("uiEditorIgnore", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.uiEditorIgnore = i, o -> o.uiEditorIgnore)).addField(new KeyedCodec("uiEditorFeatures", (Codec)new ArrayCodec((Codec)new EnumCodec(UIEditorFeatures.EditorFeature.class), x$0 -> new UIEditorFeatures.EditorFeature[x$0]), false, true), (o, i) -> o.uiEditorFeatures = i, o -> o.uiEditorFeatures)).addField(new KeyedCodec("uiEditorPreview", (Codec)new EnumCodec(UIEditorPreview.PreviewType.class), false, true), (o, i) -> o.uiEditorPreview = i, o -> o.uiEditorPreview)).addField(new KeyedCodec("uiTypeIcon", (Codec)Codec.STRING, false, true), (o, i) -> o.uiTypeIcon = i, o -> o.uiTypeIcon)).addField(new KeyedCodec("uiPropertyTitle", (Codec)Codec.STRING, false, true), (o, i) -> o.uiPropertyTitle = i, o -> o.uiPropertyTitle)).addField(new KeyedCodec("uiSectionStart", (Codec)Codec.STRING, false, true), (o, i) -> o.uiSectionStart = i, o -> o.uiSectionStart)).addField(new KeyedCodec("uiRebuildCaches", (Codec)new ArrayCodec((Codec)new EnumCodec(UIRebuildCaches.ClientCache.class), x$0 -> new UIRebuildCaches.ClientCache[x$0]), false, true), (o, i) -> o.uiRebuildCaches = i, o -> o.uiRebuildCaches)).addField(new KeyedCodec("uiSidebarButtons", (Codec)new ArrayCodec((Codec)UIButton.CODEC, x$0 -> new UIButton[x$0]), false, true), (o, i) -> o.uiSidebarButtons = i, o -> o.uiSidebarButtons)).addField(new KeyedCodec("uiRebuildCachesForChildProperties", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.uiRebuildCachesForChildProperties = i, o -> o.uiRebuildCachesForChildProperties)).addField(new KeyedCodec("uiCollapsedByDefault", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.uiCollapsedByDefault = i, o -> o.uiCollapsedByDefault)).addField(new KeyedCodec("uiCreateButtons", (Codec)new ArrayCodec((Codec)UIButton.CODEC, x$0 -> new UIButton[x$0]), false, true), (o, i) -> o.uiCreateButtons = i, o -> o.uiCreateButtons)).build();
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
/*      */   public HytaleMetadata(String type) {
/*  757 */     this.type = type;
/*      */   }
/*      */ 
/*      */   
/*      */   public HytaleMetadata() {}
/*      */   
/*      */   public String getType() {
/*  764 */     return this.type;
/*      */   }
/*      */   
/*      */   public void setType(String type) {
/*  768 */     this.type = type;
/*      */   }
/*      */   
/*      */   public String getPath() {
/*  772 */     return this.path;
/*      */   }
/*      */   
/*      */   public void setPath(String path) {
/*  776 */     this.path = path;
/*      */   }
/*      */   
/*      */   public String getVirtualPath() {
/*  780 */     return this.virtualPath;
/*      */   }
/*      */   
/*      */   public void setVirtualPath(String virtualPath) {
/*  784 */     this.virtualPath = virtualPath;
/*      */   }
/*      */   
/*      */   public String getExtension() {
/*  788 */     return this.extension;
/*      */   }
/*      */   
/*      */   public void setExtension(String extension) {
/*  792 */     this.extension = extension;
/*      */   }
/*      */   
/*      */   public String getIdProvider() {
/*  796 */     return this.idProvider;
/*      */   }
/*      */   
/*      */   public void setIdProvider(String idProvider) {
/*  800 */     this.idProvider = idProvider;
/*      */   }
/*      */   
/*      */   public String[] getInternalKeys() {
/*  804 */     return this.internalKeys;
/*      */   }
/*      */   
/*      */   public void setInternalKeys(String[] internalKeys) {
/*  808 */     this.internalKeys = internalKeys;
/*      */   }
/*      */   
/*      */   public UIDisplayMode.DisplayMode getUiDisplayMode() {
/*  812 */     return this.uiDisplayMode;
/*      */   }
/*      */   
/*      */   public void setUiDisplayMode(UIDisplayMode.DisplayMode uiDisplayMode) {
/*  816 */     this.uiDisplayMode = uiDisplayMode;
/*      */   }
/*      */   
/*      */   public UIEditor.EditorComponent getUiEditorComponent() {
/*  820 */     return this.uiEditorComponent;
/*      */   }
/*      */   
/*      */   public void setUiEditorComponent(UIEditor.EditorComponent uiEditorComponent) {
/*  824 */     this.uiEditorComponent = uiEditorComponent;
/*      */   }
/*      */   
/*      */   public UIEditorFeatures.EditorFeature[] getUiEditorFeatures() {
/*  828 */     return this.uiEditorFeatures;
/*      */   }
/*      */   
/*      */   public void setUiEditorFeatures(UIEditorFeatures.EditorFeature[] uiEditorFeatures) {
/*  832 */     this.uiEditorFeatures = uiEditorFeatures;
/*      */   }
/*      */   
/*      */   public UIEditorPreview.PreviewType getUiEditorPreview() {
/*  836 */     return this.uiEditorPreview;
/*      */   }
/*      */   
/*      */   public void setUiEditorPreview(UIEditorPreview.PreviewType uiEditorPreview) {
/*  840 */     this.uiEditorPreview = uiEditorPreview;
/*      */   }
/*      */   
/*      */   public String getUiTypeIcon() {
/*  844 */     return this.uiTypeIcon;
/*      */   }
/*      */   
/*      */   public void setUiTypeIcon(String uiTypeIcon) {
/*  848 */     this.uiTypeIcon = uiTypeIcon;
/*      */   }
/*      */   
/*      */   public Boolean getUiEditorIgnore() {
/*  852 */     return this.uiEditorIgnore;
/*      */   }
/*      */   
/*      */   public void setUiEditorIgnore(Boolean uiEditorIgnore) {
/*  856 */     this.uiEditorIgnore = uiEditorIgnore;
/*      */   }
/*      */   
/*      */   public Boolean getAllowEmptyObject() {
/*  860 */     return this.allowEmptyObject;
/*      */   }
/*      */   
/*      */   public void setAllowEmptyObject(Boolean allowEmptyObject) {
/*  864 */     this.allowEmptyObject = allowEmptyObject;
/*      */   }
/*      */   
/*      */   public String getUiPropertyTitle() {
/*  868 */     return this.uiPropertyTitle;
/*      */   }
/*      */   
/*      */   public void setUiPropertyTitle(String uiPropertyTitle) {
/*  872 */     this.uiPropertyTitle = uiPropertyTitle;
/*      */   }
/*      */   
/*      */   public String getUiSectionStart() {
/*  876 */     return this.uiSectionStart;
/*      */   }
/*      */   
/*      */   public void setUiSectionStart(String uiSectionStart) {
/*  880 */     this.uiSectionStart = uiSectionStart;
/*      */   }
/*      */   
/*      */   public boolean isInheritsProperty() {
/*  884 */     return this.inheritsProperty.booleanValue();
/*      */   }
/*      */   
/*      */   public void setInheritsProperty(boolean inheritsProperty) {
/*  888 */     this.inheritsProperty = Boolean.valueOf(inheritsProperty);
/*      */   }
/*      */   
/*      */   public boolean getMergesProperties() {
/*  892 */     return this.mergesProperties.booleanValue();
/*      */   }
/*      */   
/*      */   public void setMergesProperties(boolean mergesProperties) {
/*  896 */     this.mergesProperties = Boolean.valueOf(mergesProperties);
/*      */   }
/*      */   
/*      */   public UIRebuildCaches.ClientCache[] getUiRebuildCaches() {
/*  900 */     return this.uiRebuildCaches;
/*      */   }
/*      */   
/*      */   public void setUiRebuildCaches(UIRebuildCaches.ClientCache[] uiRebuildCaches) {
/*  904 */     this.uiRebuildCaches = uiRebuildCaches;
/*      */   }
/*      */   
/*      */   public Boolean getUiRebuildCachesForChildProperties() {
/*  908 */     return this.uiRebuildCachesForChildProperties;
/*      */   }
/*      */   
/*      */   public void setUiRebuildCachesForChildProperties(Boolean uiRebuildCachesForChildProperties) {
/*  912 */     this.uiRebuildCachesForChildProperties = uiRebuildCachesForChildProperties;
/*      */   }
/*      */   
/*      */   public UIButton[] getUiSidebarButtons() {
/*  916 */     return this.uiSidebarButtons;
/*      */   }
/*      */   
/*      */   public void setUiSidebarButtons(UIButton[] uiSidebarButtons) {
/*  920 */     this.uiSidebarButtons = uiSidebarButtons;
/*      */   }
/*      */   
/*      */   public Boolean getUiCollapsedByDefault() {
/*  924 */     return this.uiCollapsedByDefault;
/*      */   }
/*      */   
/*      */   public void setUiCollapsedByDefault(Boolean uiCollapsedByDefault) {
/*  928 */     this.uiCollapsedByDefault = uiCollapsedByDefault;
/*      */   }
/*      */   
/*      */   public UIButton[] getUiCreateButtons() {
/*  932 */     return this.uiCreateButtons;
/*      */   }
/*      */   
/*      */   public void setUiCreateButtons(UIButton[] uiCreateButtons) {
/*  936 */     this.uiCreateButtons = uiCreateButtons;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object o) {
/*  941 */     if (this == o) return true; 
/*  942 */     if (o == null || getClass() != o.getClass()) return false;
/*      */     
/*  944 */     HytaleMetadata that = (HytaleMetadata)o;
/*      */     
/*  946 */     if ((this.type != null) ? !this.type.equals(that.type) : (that.type != null)) return false; 
/*  947 */     if ((this.path != null) ? !this.path.equals(that.path) : (that.path != null)) return false; 
/*  948 */     if ((this.virtualPath != null) ? !this.virtualPath.equals(that.virtualPath) : (that.virtualPath != null)) return false; 
/*  949 */     if ((this.extension != null) ? !this.extension.equals(that.extension) : (that.extension != null)) return false; 
/*  950 */     if ((this.idProvider != null) ? !this.idProvider.equals(that.idProvider) : (that.idProvider != null)) return false;
/*      */     
/*  952 */     if (!Arrays.equals((Object[])this.internalKeys, (Object[])that.internalKeys)) return false; 
/*  953 */     if ((this.inheritsProperty != null) ? !this.inheritsProperty.equals(that.inheritsProperty) : (that.inheritsProperty != null)) return false; 
/*  954 */     if ((this.mergesProperties != null) ? !this.mergesProperties.equals(that.mergesProperties) : (that.mergesProperties != null)) return false;
/*      */     
/*  956 */     if (!Arrays.equals((Object[])this.uiEditorFeatures, (Object[])that.uiEditorFeatures)) return false; 
/*  957 */     if (this.uiEditorPreview != that.uiEditorPreview) return false; 
/*  958 */     if ((this.uiTypeIcon != null) ? !this.uiTypeIcon.equals(that.uiTypeIcon) : (that.uiTypeIcon != null)) return false; 
/*  959 */     if ((this.uiEditorIgnore != null) ? !this.uiEditorIgnore.equals(that.uiEditorIgnore) : (that.uiEditorIgnore != null)) return false; 
/*  960 */     if ((this.allowEmptyObject != null) ? !this.allowEmptyObject.equals(that.allowEmptyObject) : (that.allowEmptyObject != null)) return false; 
/*  961 */     if (this.uiDisplayMode != that.uiDisplayMode) return false; 
/*  962 */     if ((this.uiEditorComponent != null) ? !this.uiEditorComponent.equals(that.uiEditorComponent) : (that.uiEditorComponent != null)) return false; 
/*  963 */     if ((this.uiPropertyTitle != null) ? !this.uiPropertyTitle.equals(that.uiPropertyTitle) : (that.uiPropertyTitle != null)) return false; 
/*  964 */     if ((this.uiSectionStart != null) ? !this.uiSectionStart.equals(that.uiSectionStart) : (that.uiSectionStart != null)) return false;
/*      */     
/*  966 */     if (!Arrays.equals((Object[])this.uiRebuildCaches, (Object[])that.uiRebuildCaches)) return false; 
/*  967 */     if ((this.uiRebuildCachesForChildProperties != null) ? !this.uiRebuildCachesForChildProperties.equals(that.uiRebuildCachesForChildProperties) : (that.uiRebuildCachesForChildProperties != null)) {
/*  968 */       return false;
/*      */     }
/*      */     
/*  971 */     if (!Arrays.equals((Object[])this.uiSidebarButtons, (Object[])that.uiSidebarButtons)) return false; 
/*  972 */     if ((this.uiCollapsedByDefault != null) ? !this.uiCollapsedByDefault.equals(that.uiCollapsedByDefault) : (that.uiCollapsedByDefault != null)) return false;
/*      */     
/*  974 */     return Arrays.equals((Object[])this.uiCreateButtons, (Object[])that.uiCreateButtons);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  979 */     int result = (this.type != null) ? this.type.hashCode() : 0;
/*  980 */     result = 31 * result + ((this.path != null) ? this.path.hashCode() : 0);
/*  981 */     result = 31 * result + ((this.virtualPath != null) ? this.virtualPath.hashCode() : 0);
/*  982 */     result = 31 * result + ((this.extension != null) ? this.extension.hashCode() : 0);
/*  983 */     result = 31 * result + ((this.idProvider != null) ? this.idProvider.hashCode() : 0);
/*  984 */     result = 31 * result + Arrays.hashCode((Object[])this.internalKeys);
/*  985 */     result = 31 * result + ((this.inheritsProperty != null) ? this.inheritsProperty.hashCode() : 0);
/*  986 */     result = 31 * result + ((this.mergesProperties != null) ? this.mergesProperties.hashCode() : 0);
/*  987 */     result = 31 * result + Arrays.hashCode((Object[])this.uiEditorFeatures);
/*  988 */     result = 31 * result + ((this.uiEditorPreview != null) ? this.uiEditorPreview.hashCode() : 0);
/*  989 */     result = 31 * result + ((this.uiTypeIcon != null) ? this.uiTypeIcon.hashCode() : 0);
/*  990 */     result = 31 * result + ((this.uiEditorIgnore != null) ? this.uiEditorIgnore.hashCode() : 0);
/*  991 */     result = 31 * result + ((this.allowEmptyObject != null) ? this.allowEmptyObject.hashCode() : 0);
/*  992 */     result = 31 * result + ((this.uiDisplayMode != null) ? this.uiDisplayMode.hashCode() : 0);
/*  993 */     result = 31 * result + ((this.uiEditorComponent != null) ? this.uiEditorComponent.hashCode() : 0);
/*  994 */     result = 31 * result + ((this.uiPropertyTitle != null) ? this.uiPropertyTitle.hashCode() : 0);
/*  995 */     result = 31 * result + ((this.uiSectionStart != null) ? this.uiSectionStart.hashCode() : 0);
/*  996 */     result = 31 * result + Arrays.hashCode((Object[])this.uiRebuildCaches);
/*  997 */     result = 31 * result + ((this.uiRebuildCachesForChildProperties != null) ? this.uiRebuildCachesForChildProperties.hashCode() : 0);
/*  998 */     result = 31 * result + Arrays.hashCode((Object[])this.uiSidebarButtons);
/*  999 */     result = 31 * result + ((this.uiCollapsedByDefault != null) ? this.uiCollapsedByDefault.hashCode() : 0);
/* 1000 */     result = 31 * result + Arrays.hashCode((Object[])this.uiCreateButtons);
/* 1001 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\Schema$HytaleMetadata.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */