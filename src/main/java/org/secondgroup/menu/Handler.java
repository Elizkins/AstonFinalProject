package org.secondgroup.menu;

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

    Runnable action;

    protected String key;
}

