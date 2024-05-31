package ru.example.javashells.commands;

import java.io.File;
import java.util.Stack;

import ru.example.javashells.interfaces.Command;

public class CdCommand implements Command {

    private File currentDirectory;
    private Stack<File> directoryStack;

    public CdCommand(File initialDirectory) {
        this.currentDirectory = initialDirectory;
        this.directoryStack = new Stack<>();
    }

    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Используйте: cd <Каталог>");
            return;
        }

        String targetDirectory = args[1];

        if ("-".equals(targetDirectory)) {
            if (!directoryStack.isEmpty()) {
                File previousDirectory = directoryStack.pop();
                currentDirectory = previousDirectory;
                System.out.println("Каталог изменён на " + currentDirectory.getAbsolutePath());
            } else {
                System.out.println("Нет предыдущего каталога, к которому можно было бы вернуться.");
            }
        } else {
            File newDir = new File(currentDirectory, targetDirectory);
            if (newDir.exists() && newDir.isDirectory()) {
                directoryStack.push(currentDirectory);
                currentDirectory = newDir;
                System.out.println("Каталог изменён на " + currentDirectory.getAbsolutePath());
            } else {
                System.out.println("Directory not found: " + targetDirectory);
            }
        }
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }
}
