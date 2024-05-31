package ru.example.javashells.commands;

import ru.example.javashells.interfaces.Command;

public class ExitCommand implements Command {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Выход из оболочки...");
        System.exit(0);
    }
}
