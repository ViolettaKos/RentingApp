package com.example.rentingapp.web.tags;

import jakarta.servlet.jsp.JspWriter;

import java.io.IOException;
import java.io.StringWriter;

public class CustomJspWriter extends JspWriter {

    private final StringWriter stringWriter;
    public CustomJspWriter(int bufferSize, boolean autoFlush) {
        super(bufferSize, autoFlush);
        stringWriter = new StringWriter();
    }

    @Override
    public String toString() {
        return stringWriter.toString();
    }

    @Override
    public void newLine() throws IOException {

    }

    @Override
    public void print(boolean b) throws IOException {

    }

    @Override
    public void print(char c) throws IOException {

    }

    @Override
    public void print(int i) throws IOException {

    }

    @Override
    public void print(long l) throws IOException {

    }

    @Override
    public void print(float v) throws IOException {

    }

    @Override
    public void print(double v) throws IOException {

    }

    @Override
    public void print(char[] chars) throws IOException {

    }

    @Override
    public void print(String s) throws IOException {
        stringWriter.write(s);
    }

    @Override
    public void print(Object o) throws IOException {

    }


    @Override
    public void println() throws IOException {

    }

    @Override
    public void println(boolean b) throws IOException {

    }

    @Override
    public void println(char c) throws IOException {

    }

    @Override
    public void println(int i) throws IOException {

    }

    @Override
    public void println(long l) throws IOException {

    }

    @Override
    public void println(float v) throws IOException {

    }

    @Override
    public void println(double v) throws IOException {

    }

    @Override
    public void println(char[] chars) throws IOException {

    }

    @Override
    public void println(String s) throws IOException {

    }

    @Override
    public void println(Object o) throws IOException {

    }

    @Override
    public void clear() throws IOException {

    }

    @Override
    public void clearBuffer() throws IOException {

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public int getRemaining() {
        return 0;
    }
}
