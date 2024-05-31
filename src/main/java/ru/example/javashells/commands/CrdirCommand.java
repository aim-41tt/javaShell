package ru.example.javashells.commands;

import java.io.File;

import ru.example.javashells.interfaces.Command;

public class CrdirCommand implements Command {

    @Override
    public String getName() {
        return "crdir";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: mkdir <directory>");
            return;
        }

        String directoryName = args[1];
        File newDir = new File(directoryName);

        if (newDir.exists()) {
            System.out.println("Directory already exists: " + directoryName);
        } else {
            boolean created = newDir.mkdirs();
            if (created) {
                System.out.println("Directory created: " + newDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory: " + directoryName);
            }
        }
    }
}
