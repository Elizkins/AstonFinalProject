package org.secondgroup.menu;

import java.util.Objects;

public class Handler {
    public Handler(String key, Runnable action) {
        this.key = key;
        this.action = action;
    }

    //Если удалось обработать, возвращает true
    public boolean handle(String input){
        if (input.equals(key)){
            this.action.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Handler handler = (Handler) o;
        return Objects.equals(key, handler.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    Runnable action;

    protected String key;
}

