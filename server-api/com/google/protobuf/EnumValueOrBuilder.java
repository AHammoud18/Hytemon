package com.google.protobuf;

import java.util.List;

public interface EnumValueOrBuilder extends MessageOrBuilder {
  String getName();
  
  ByteString getNameBytes();
  
  int getNumber();
  
  List<Option> getOptionsList();
  
  Option getOptions(int paramInt);
  
  int getOptionsCount();
  
  List<? extends OptionOrBuilder> getOptionsOrBuilderList();
  
  OptionOrBuilder getOptionsOrBuilder(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\EnumValueOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */