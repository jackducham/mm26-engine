// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: visualizer_turn.proto

package mech.mania.engine.server.communication.visualizer.model;

public final class VisualizerTurnProtos {
  private VisualizerTurnProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface VisualizerTurnOrBuilder extends
      // @@protoc_insertion_point(interface_extends:visualizer.VisualizerTurn)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *TODO: Insert data needed here
     * </pre>
     *
     * <code>int64 turnNumber = 1;</code>
     * @return The turnNumber.
     */
    long getTurnNumber();
  }
  /**
   * Protobuf type {@code visualizer.VisualizerTurn}
   */
  public  static final class VisualizerTurn extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:visualizer.VisualizerTurn)
      VisualizerTurnOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use VisualizerTurn.newBuilder() to construct.
    private VisualizerTurn(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private VisualizerTurn() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new VisualizerTurn();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private VisualizerTurn(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              turnNumber_ = input.readInt64();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.internal_static_visualizer_VisualizerTurn_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.internal_static_visualizer_VisualizerTurn_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.class, mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.Builder.class);
    }

    public static final int TURNNUMBER_FIELD_NUMBER = 1;
    private long turnNumber_;
    /**
     * <pre>
     *TODO: Insert data needed here
     * </pre>
     *
     * <code>int64 turnNumber = 1;</code>
     * @return The turnNumber.
     */
    public long getTurnNumber() {
      return turnNumber_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (turnNumber_ != 0L) {
        output.writeInt64(1, turnNumber_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (turnNumber_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, turnNumber_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn)) {
        return super.equals(obj);
      }
      mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn other = (mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn) obj;

      if (getTurnNumber()
          != other.getTurnNumber()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + TURNNUMBER_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTurnNumber());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code visualizer.VisualizerTurn}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:visualizer.VisualizerTurn)
        mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurnOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.internal_static_visualizer_VisualizerTurn_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.internal_static_visualizer_VisualizerTurn_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.class, mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.Builder.class);
      }

      // Construct using mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        turnNumber_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.internal_static_visualizer_VisualizerTurn_descriptor;
      }

      @java.lang.Override
      public mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn getDefaultInstanceForType() {
        return mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.getDefaultInstance();
      }

      @java.lang.Override
      public mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn build() {
        mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn buildPartial() {
        mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn result = new mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn(this);
        result.turnNumber_ = turnNumber_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn) {
          return mergeFrom((mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn other) {
        if (other == mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn.getDefaultInstance()) return this;
        if (other.getTurnNumber() != 0L) {
          setTurnNumber(other.getTurnNumber());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long turnNumber_ ;
      /**
       * <pre>
       *TODO: Insert data needed here
       * </pre>
       *
       * <code>int64 turnNumber = 1;</code>
       * @return The turnNumber.
       */
      public long getTurnNumber() {
        return turnNumber_;
      }
      /**
       * <pre>
       *TODO: Insert data needed here
       * </pre>
       *
       * <code>int64 turnNumber = 1;</code>
       * @param value The turnNumber to set.
       * @return This builder for chaining.
       */
      public Builder setTurnNumber(long value) {
        
        turnNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *TODO: Insert data needed here
       * </pre>
       *
       * <code>int64 turnNumber = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearTurnNumber() {
        
        turnNumber_ = 0L;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:visualizer.VisualizerTurn)
    }

    // @@protoc_insertion_point(class_scope:visualizer.VisualizerTurn)
    private static final mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn();
    }

    public static mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<VisualizerTurn>
        PARSER = new com.google.protobuf.AbstractParser<VisualizerTurn>() {
      @java.lang.Override
      public VisualizerTurn parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new VisualizerTurn(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<VisualizerTurn> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<VisualizerTurn> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_visualizer_VisualizerTurn_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_visualizer_VisualizerTurn_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025visualizer_turn.proto\022\nvisualizer\"$\n\016V" +
      "isualizerTurn\022\022\n\nturnNumber\030\001 \001(\003BO\n7mec" +
      "h.mania.engine.server.communication.visu" +
      "alizer.modelB\024VisualizerTurnProtosb\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_visualizer_VisualizerTurn_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_visualizer_VisualizerTurn_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_visualizer_VisualizerTurn_descriptor,
        new java.lang.String[] { "TurnNumber", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
