// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: player_decision.proto

package mech.mania.engine.server.communication.player.model;

public final class PlayerDecisionProtos {
  private PlayerDecisionProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PlayerDecisionOrBuilder extends
      // @@protoc_insertion_point(interface_extends:player_communication.PlayerDecision)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string player_uuid = 1;</code>
     * @return The playerUuid.
     */
    java.lang.String getPlayerUuid();
    /**
     * <code>string player_uuid = 1;</code>
     * @return The bytes for playerUuid.
     */
    com.google.protobuf.ByteString
        getPlayerUuidBytes();

    /**
     * <code>string player_name = 2;</code>
     * @return The playerName.
     */
    java.lang.String getPlayerName();
    /**
     * <code>string player_name = 2;</code>
     * @return The bytes for playerName.
     */
    com.google.protobuf.ByteString
        getPlayerNameBytes();

    /**
     * <code>int32 turn = 3;</code>
     * @return The turn.
     */
    int getTurn();

    /**
     * <pre>
     * TODO: Insert data needed here
     * </pre>
     *
     * <code>int32 increment = 4;</code>
     * @return The increment.
     */
    int getIncrement();
  }
  /**
   * Protobuf type {@code player_communication.PlayerDecision}
   */
  public  static final class PlayerDecision extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:player_communication.PlayerDecision)
      PlayerDecisionOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PlayerDecision.newBuilder() to construct.
    private PlayerDecision(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PlayerDecision() {
      playerUuid_ = "";
      playerName_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new PlayerDecision();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PlayerDecision(
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
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              playerUuid_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              playerName_ = s;
              break;
            }
            case 24: {

              turn_ = input.readInt32();
              break;
            }
            case 32: {

              increment_ = input.readInt32();
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
      return mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.internal_static_player_communication_PlayerDecision_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.internal_static_player_communication_PlayerDecision_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.class, mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.Builder.class);
    }

    public static final int PLAYER_UUID_FIELD_NUMBER = 1;
    private volatile java.lang.Object playerUuid_;
    /**
     * <code>string player_uuid = 1;</code>
     * @return The playerUuid.
     */
    public java.lang.String getPlayerUuid() {
      java.lang.Object ref = playerUuid_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        playerUuid_ = s;
        return s;
      }
    }
    /**
     * <code>string player_uuid = 1;</code>
     * @return The bytes for playerUuid.
     */
    public com.google.protobuf.ByteString
        getPlayerUuidBytes() {
      java.lang.Object ref = playerUuid_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        playerUuid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PLAYER_NAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object playerName_;
    /**
     * <code>string player_name = 2;</code>
     * @return The playerName.
     */
    public java.lang.String getPlayerName() {
      java.lang.Object ref = playerName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        playerName_ = s;
        return s;
      }
    }
    /**
     * <code>string player_name = 2;</code>
     * @return The bytes for playerName.
     */
    public com.google.protobuf.ByteString
        getPlayerNameBytes() {
      java.lang.Object ref = playerName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        playerName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TURN_FIELD_NUMBER = 3;
    private int turn_;
    /**
     * <code>int32 turn = 3;</code>
     * @return The turn.
     */
    public int getTurn() {
      return turn_;
    }

    public static final int INCREMENT_FIELD_NUMBER = 4;
    private int increment_;
    /**
     * <pre>
     * TODO: Insert data needed here
     * </pre>
     *
     * <code>int32 increment = 4;</code>
     * @return The increment.
     */
    public int getIncrement() {
      return increment_;
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
      if (!getPlayerUuidBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, playerUuid_);
      }
      if (!getPlayerNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, playerName_);
      }
      if (turn_ != 0) {
        output.writeInt32(3, turn_);
      }
      if (increment_ != 0) {
        output.writeInt32(4, increment_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getPlayerUuidBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, playerUuid_);
      }
      if (!getPlayerNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, playerName_);
      }
      if (turn_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, turn_);
      }
      if (increment_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, increment_);
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
      if (!(obj instanceof mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision)) {
        return super.equals(obj);
      }
      mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision other = (mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision) obj;

      if (!getPlayerUuid()
          .equals(other.getPlayerUuid())) return false;
      if (!getPlayerName()
          .equals(other.getPlayerName())) return false;
      if (getTurn()
          != other.getTurn()) return false;
      if (getIncrement()
          != other.getIncrement()) return false;
      return unknownFields.equals(other.unknownFields);
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + PLAYER_UUID_FIELD_NUMBER;
      hash = (53 * hash) + getPlayerUuid().hashCode();
      hash = (37 * hash) + PLAYER_NAME_FIELD_NUMBER;
      hash = (53 * hash) + getPlayerName().hashCode();
      hash = (37 * hash) + TURN_FIELD_NUMBER;
      hash = (53 * hash) + getTurn();
      hash = (37 * hash) + INCREMENT_FIELD_NUMBER;
      hash = (53 * hash) + getIncrement();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parseFrom(
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
    public static Builder newBuilder(mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision prototype) {
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
     * Protobuf type {@code player_communication.PlayerDecision}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:player_communication.PlayerDecision)
        mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecisionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.internal_static_player_communication_PlayerDecision_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.internal_static_player_communication_PlayerDecision_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.class, mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.Builder.class);
      }

      // Construct using mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.newBuilder()
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
        playerUuid_ = "";

        playerName_ = "";

        turn_ = 0;

        increment_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.internal_static_player_communication_PlayerDecision_descriptor;
      }

      @java.lang.Override
      public mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision getDefaultInstanceForType() {
        return mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.getDefaultInstance();
      }

      @java.lang.Override
      public mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision build() {
        mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision buildPartial() {
        mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision result = new mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision(this);
        result.playerUuid_ = playerUuid_;
        result.playerName_ = playerName_;
        result.turn_ = turn_;
        result.increment_ = increment_;
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
        if (other instanceof mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision) {
          return mergeFrom((mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision other) {
        if (other == mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision.getDefaultInstance()) return this;
        if (!other.getPlayerUuid().isEmpty()) {
          playerUuid_ = other.playerUuid_;
          onChanged();
        }
        if (!other.getPlayerName().isEmpty()) {
          playerName_ = other.playerName_;
          onChanged();
        }
        if (other.getTurn() != 0) {
          setTurn(other.getTurn());
        }
        if (other.getIncrement() != 0) {
          setIncrement(other.getIncrement());
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
        mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object playerUuid_ = "";
      /**
       * <code>string player_uuid = 1;</code>
       * @return The playerUuid.
       */
      public java.lang.String getPlayerUuid() {
        java.lang.Object ref = playerUuid_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          playerUuid_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string player_uuid = 1;</code>
       * @return The bytes for playerUuid.
       */
      public com.google.protobuf.ByteString
          getPlayerUuidBytes() {
        java.lang.Object ref = playerUuid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          playerUuid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string player_uuid = 1;</code>
       * @param value The playerUuid to set.
       * @return This builder for chaining.
       */
      public Builder setPlayerUuid(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        playerUuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string player_uuid = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearPlayerUuid() {
        
        playerUuid_ = getDefaultInstance().getPlayerUuid();
        onChanged();
        return this;
      }
      /**
       * <code>string player_uuid = 1;</code>
       * @param value The bytes for playerUuid to set.
       * @return This builder for chaining.
       */
      public Builder setPlayerUuidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        playerUuid_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object playerName_ = "";
      /**
       * <code>string player_name = 2;</code>
       * @return The playerName.
       */
      public java.lang.String getPlayerName() {
        java.lang.Object ref = playerName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          playerName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string player_name = 2;</code>
       * @return The bytes for playerName.
       */
      public com.google.protobuf.ByteString
          getPlayerNameBytes() {
        java.lang.Object ref = playerName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          playerName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string player_name = 2;</code>
       * @param value The playerName to set.
       * @return This builder for chaining.
       */
      public Builder setPlayerName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        playerName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string player_name = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearPlayerName() {
        
        playerName_ = getDefaultInstance().getPlayerName();
        onChanged();
        return this;
      }
      /**
       * <code>string player_name = 2;</code>
       * @param value The bytes for playerName to set.
       * @return This builder for chaining.
       */
      public Builder setPlayerNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        playerName_ = value;
        onChanged();
        return this;
      }

      private int turn_ ;
      /**
       * <code>int32 turn = 3;</code>
       * @return The turn.
       */
      public int getTurn() {
        return turn_;
      }
      /**
       * <code>int32 turn = 3;</code>
       * @param value The turn to set.
       * @return This builder for chaining.
       */
      public Builder setTurn(int value) {
        
        turn_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 turn = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearTurn() {
        
        turn_ = 0;
        onChanged();
        return this;
      }

      private int increment_ ;
      /**
       * <pre>
       * TODO: Insert data needed here
       * </pre>
       *
       * <code>int32 increment = 4;</code>
       * @return The increment.
       */
      public int getIncrement() {
        return increment_;
      }
      /**
       * <pre>
       * TODO: Insert data needed here
       * </pre>
       *
       * <code>int32 increment = 4;</code>
       * @param value The increment to set.
       * @return This builder for chaining.
       */
      public Builder setIncrement(int value) {
        
        increment_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * TODO: Insert data needed here
       * </pre>
       *
       * <code>int32 increment = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearIncrement() {
        
        increment_ = 0;
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


      // @@protoc_insertion_point(builder_scope:player_communication.PlayerDecision)
    }

    // @@protoc_insertion_point(class_scope:player_communication.PlayerDecision)
    private static final mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision();
    }

    public static mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PlayerDecision>
        PARSER = new com.google.protobuf.AbstractParser<PlayerDecision>() {
      @java.lang.Override
      public PlayerDecision parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PlayerDecision(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PlayerDecision> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PlayerDecision> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_player_communication_PlayerDecision_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_player_communication_PlayerDecision_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025player_decision.proto\022\024player_communic" +
      "ation\"[\n\016PlayerDecision\022\023\n\013player_uuid\030\001" +
      " \001(\t\022\023\n\013player_name\030\002 \001(\t\022\014\n\004turn\030\003 \001(\005\022" +
      "\021\n\tincrement\030\004 \001(\005BK\n3mech.mania.engine." +
      "server.communication.player.modelB\024Playe" +
      "rDecisionProtosb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_player_communication_PlayerDecision_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_player_communication_PlayerDecision_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_player_communication_PlayerDecision_descriptor,
        new java.lang.String[] { "PlayerUuid", "PlayerName", "Turn", "Increment", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
