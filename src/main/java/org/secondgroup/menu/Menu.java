package org.secondgroup.menu;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Menu {
    private String text;
    private Set<Handler> handlers;
    private String notHandledText;
    private Scanner sysin;

    public Menu(String text, String notHandledText, Scanner sysin) {
        this.text = text;
        this.notHandledText = notHandledText;
        this.handlers = new HashSet<>();
        this.sysin = sysin;
    }

    public void run() {
        System.out.println(text);
        Scanner scanner = sysin;
        String input = scanner.nextLine().trim();
        handlers.stream()
                .filter(handler -> handler.handle(input)) //обрабатываем ввод
                .findFirst()
                .orElseGet(() -> {
                    System.out.println(notHandledText); //Если не значение не обработалось, выводим notHandledText
                    return null;
                });
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);

    }
}
