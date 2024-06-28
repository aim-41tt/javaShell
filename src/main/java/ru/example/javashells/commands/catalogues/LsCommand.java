package ru.example.javashells.commands.catalogues;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

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

			File[] files = Optional.ofNullable(directoryManager.getCurrentDirectory().listFiles())
					.orElse(new File[0]);

			if (files.length > 0) {
				System.out.println("Файлы и папки в текущей директории:");
				System.out.println(directoryContentBuilder(files));
			} else {
				System.out.println("Файлы и папки не найдены в текущей директории:");
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	private String directoryContentBuilder(File[] files) {
		String format = "%-10s %-25s %20s %10.2f КБ%n";

		return Arrays.stream(files).map(file -> {
			double lengthFile = file.length() / 1024.0;
			String type = file.isFile() ? "Файл:" : "Папка:";
			return String.format(format, type, file.getName(), "размер:", lengthFile);
		})
		.reduce(new StringBuilder(), (sb, str) -> sb.append(str), StringBuilder::append)
		.toString();
	}

}
