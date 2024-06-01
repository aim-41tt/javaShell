package ru.example.javashells.commands;

import java.io.File;
import java.util.Stack;

import ru.example.javashells.interfaces.Command;

public class CdCommand implements Command {

	private File currentDirectory;
	private Stack<File> directoryStack;
	private CrdirCommand crdirCommand;

	public CdCommand(File initialDirectory, CrdirCommand crdirCommand) {
		this.currentDirectory = initialDirectory;
		this.directoryStack = new Stack<>();
		this.crdirCommand = crdirCommand;
		this.crdirCommand.setCurrentDirectory(initialDirectory);
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
		int col = 0;

		if ("-".equals(targetDirectory)) {

			if (args.length > 2) {
				col = Integer.valueOf(args[2]);
			}
			for (int i = 0; i <= col; i++) {
				if (!directoryStack.isEmpty()) {
					File previousDirectory = directoryStack.pop();
					currentDirectory = previousDirectory;
					crdirCommand.setCurrentDirectory(currentDirectory);
				} else {
					System.out.println("Каталог изменен на " + currentDirectory.getAbsolutePath());
					System.out.println("Нет каталога, к которому можно было бы вернуться.");
					return;
				}
			}
			System.out.println("Каталог изменен на " + currentDirectory.getAbsolutePath());
		} else {
			File newDir = new File(currentDirectory, targetDirectory);
			if (newDir.exists() && newDir.isDirectory()) {
				directoryStack.push(currentDirectory);
				currentDirectory = newDir;
				crdirCommand.setCurrentDirectory(currentDirectory);
				System.out.println("Directory changed to " + currentDirectory.getAbsolutePath());
			} else {
				System.out.println("Directory not found: " + targetDirectory);
			}
		}
	}

	public File getCurrentDirectory() {
		return currentDirectory;
	}
}
