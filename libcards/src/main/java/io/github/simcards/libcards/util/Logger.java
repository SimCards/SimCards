package io.github.simcards.libcards.util;

public class Logger implements ILog {
    public void o(Object o) {
        System.out.println(o);
    }

    public void e(Object o) {
        this.o(o);
    }

    public void d(Object o) {
        this.o(o);
    }

    public void i(Object o) {
        this.o(o);
    }

    public void notice(Object o) {
        System.out.println("Notice: " + o);
    }
}
