package ru.example.javashells.commands;

import java.io.File;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CrdirCommand implements Command {

	private DirectoryManager directoryManager;

	public CrdirCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

	@Override
	public String getName() {
		return "crdir";
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: crdir <directory>");
			return;
		}

		String directoryName = args[1];
		File newDir;

		if (directoryName.charAt(0) == '/') {
			directoryName = directoryName.substring(1, directoryName.length());
			newDir = new File(directoryManager.getCurrentDirectory(), directoryName);

		} else {
			newDir = new File(directoryName);
		}

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