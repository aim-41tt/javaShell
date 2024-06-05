package ru.example.javashells.commands.catalogues;

import java.io.File;
import java.util.Stack;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CdCommand implements Command {

	private DirectoryManager directoryManager;
	private Stack<File> directoryStack;

	public CdCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
		this.directoryStack = new Stack<>();
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
			for (int i = 0; i < col; i++) {
				if (!directoryStack.isEmpty()) {
					File previousDirectory = directoryStack.pop();
					directoryManager.setCurrentDirectory(previousDirectory);
				} else {
					System.out.println("Каталог изменен на " + directoryManager.getCurrentDirectory().getAbsolutePath());
					System.out.println("Нет каталога, к которому можно было бы вернуться.");
					return;
				}
			}
			System.out.println("Каталог изменен на " + directoryManager.getCurrentDirectory().getAbsolutePath());
		} else {
			File newDir = new File(directoryManager.getCurrentDirectory().getAbsolutePath(), targetDirectory);
			if (newDir.exists() && newDir.isDirectory()) {
				directoryStack.push(directoryManager.getCurrentDirectory());
				directoryManager.setCurrentDirectory(newDir);
				System.out.println("Directory changed to " + directoryManager.getCurrentDirectory().getAbsolutePath());
			} else {
				System.out.println("Directory not found: " + targetDirectory);
			}
		}
	}

	public File getCurrentDirectory() {
		return directoryManager.getCurrentDirectory().getAbsoluteFile();
	}
}
