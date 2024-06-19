package ru.example.javashells.commands.catalogues;

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

			if (files.length >= 0) {
				System.out.println("Файлы и папки в текущей директории:");

				System.out.println(directoryContentBuilder(files));
			} else {
				System.out.println("Файлы и папки не найдены в текущей директории:");
			}
		
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

	private String directoryContentBuilder(File[] files) {
	    StringBuilder sb = new StringBuilder();
	    String format = "%-10s %-30s %20s %8.2f КБ%n";

	    for (File file : files) {
	        double lengthFile = file.length() / 1024.0;

	        String type = file.isFile() ? "Файл:" : "Папка:";
	        
	        sb.append(String.format(format, type, file.getName(), "размер:", lengthFile));
	    }

	    return sb.toString();
	}


}
