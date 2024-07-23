package ru.example.javashells.commands.catalogues;

import java.io.File;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CdCommand implements Command {

	private DirectoryManager directoryManager;
	private Stack<File> directoryStack;

	public CdCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
		this.directoryStack = new Stack<>();
		initializeDirectoryStack();
		directoryStack.pop();
	}

	@Override
	public String getName() {
		return "cd";
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: cd <directory>");
			return;
		}

		String targetDirectory = args[1];
		
		if ("-".equals(targetDirectory) || "..".equals(targetDirectory) ) {
	        int col = args.length > 2 ? Integer.parseInt(args[2]) : 1;

	        IntStream.range(0, col)
	                .takeWhile(i -> !directoryStack.isEmpty())
	                .forEach(i -> directoryManager.setCurrentDirectory(directoryStack.pop()));

	        Optional.ofNullable(directoryManager.getCurrentDirectory())
	                .ifPresentOrElse(
	                        dir -> System.out.println("Каталог изменен на " + dir.getAbsolutePath()),
	                        () -> System.out.println("Нет каталога, к которому можно было бы вернуться.")
	                );

	    } else {
	        File newDir = new File(directoryManager.getCurrentDirectory().getAbsolutePath(), targetDirectory);
	        
	        if (newDir.exists() && newDir.isDirectory()) {
	            directoryStack.push(directoryManager.getCurrentDirectory());
	            directoryManager.setCurrentDirectory(newDir);
	            System.out.println("Каталог изменен на " + directoryManager.getCurrentDirectory().getAbsolutePath());
	        } else {
	            System.out.println("Каталог не найден: " + targetDirectory);
	        }
	    }
	}

	public File getCurrentDirectory() {
		return directoryManager.getCurrentDirectory().getAbsoluteFile();
	}

	private void initializeDirectoryStack() {
		File currentDirectory = directoryManager.getCurrentDirectory();

		Stack<File> tempStack = new Stack<>();

		Stream.iterate(currentDirectory, File::getParentFile)
		.takeWhile(file -> file != null)
		.forEach(tempStack::push);

		while (!tempStack.isEmpty()) {
			directoryStack.push(tempStack.pop());
		}
	}

}
