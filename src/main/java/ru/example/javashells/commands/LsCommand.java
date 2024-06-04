package ru.example.javashells.commands;

import java.io.File;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class LsCommand implements Command {

	private DirectoryManager directoryManager;

	public LsCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

	@Override
	public String getName() {
		return "ls";
	}

	@Override
	public void execute(String[] args) {
		try {

			File[] files = directoryManager.getCurrentDirectory().listFiles();
			System.out.println("Файлы и папки в текущей директории:");
			for (File file : files) {
				double lengthFile = file.length() / 1024;
				if (file.isFile()) {
					System.out.println("Файл:\t\t" + file.getName() + "\tазмер \t" + lengthFile + "КБ");
				} else if (file.isDirectory()) {
					System.out.println("Папка:\t\t" + file.getName() + "\tазмер \t" + lengthFile + "КБ");
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
