package com.google.protobuf;

import java.util.List;

public interface MethodOrBuilder extends MessageOrBuilder {
  String getName();
  
  ByteString getNameBytes();
  
  String getRequestTypeUrl();
  
  ByteString getRequestTypeUrlBytes();
  
  boolean getRequestStreaming();
  
  String getResponseTypeUrl();
  
  ByteString getResponseTypeUrlBytes();
  
  boolean getResponseStreaming();
  
  List<Option> getOptionsList();
  
  Option getOptions(int paramInt);
  
  int getOptionsCount();
  
  List<? extends OptionOrBuilder> getOptionsOrBuilderList();
  
  OptionOrBuilder getOptionsOrBuilder(int paramInt);
  
  @Deprecated
  int getSyntaxValue();
  
  @Deprecated
  Syntax getSyntax();
  
  @Deprecated
  String getEdition();
  
  @Deprecated
  ByteString getEditionBytes();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MethodOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */