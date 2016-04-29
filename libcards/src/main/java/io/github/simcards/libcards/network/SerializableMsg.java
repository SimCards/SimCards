package io.github.simcards.libcards.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.DatatypeConverter;

import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;

/**
 * Created by Vishal on 4/29/16.
 */
public class SerializableMsg implements Serializable {

    private Serializable content;
    public final MessageType type;

    public SerializableMsg(MessageType type, Serializable content) {
        this.type = type;
        this.content = content;
    }

    /**
     * Serializes this instance of the SerializableMsg, converting this message into a byte array
     * Use fromBytes(byte[]) to convert back into a SerializableMsg
     * @return a byte array representing the fields in this message or null if there was an error
     */
    public byte[] getBytes() {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(this);
            os.flush();
            return byteStream.toByteArray();
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Serializes this instance of the SerializableMsg, converting this message into a byte array
     * using base64 encoding and turning into a string
     * @return base64 encoded byte array representing the SerializableMsg
     */
    public String getBytesBase64() {
        byte[] data = this.getBytes();
        return DatatypeConverter.printBase64Binary(data);
    }

    /**
     * Deserializes a SerializableMsg from an array of bytes that was originally constructed by SerializableMsg.getBytes()
     * @param  recvBuf the array of bytes to deserialize (most likely from SerializableMsg.getBytes())
     * @return         a deserialized SerializableMsg or null if improper input
     */
    public static SerializableMsg fromBytes(byte[] recvBuf) {
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
            Object o = is.readObject();
            is.close();
            return (SerializableMsg) o;
        } catch (IOException e) {
            System.err.println(e);
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Decodes bytes from base64 and constructs a SerializableMsg
     * @param  encoded the encoded base64 string
     * @return         a valid FXAMessage
     */
    public static SerializableMsg fromBytesBase64(String encoded) {
        byte[] data = DatatypeConverter.parseBase64Binary(encoded);
        return fromBytes(data);
    }

    public Serializable getContent() {
        return content;
    }

    public Deck getDeck() {
        return (Deck) content;
    }

    public CardGameEvent getCardGameEvent() {
        return (CardGameEvent) content;
    }

    public Integer getInt() { return (Integer) content; }

    public String getString() { return (String) content; }

}
