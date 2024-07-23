package ru.example.javashells.commands.catalogues;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class LsCommand implements Command {

	private final DirectoryManager directoryManager;
	private final ExecutorService executorService;

	public LsCommand(DirectoryManager directoryManager, ExecutorService executorService) {
		this.directoryManager = directoryManager;
		this.executorService = executorService;
	}

	@Override
	public String getName() {
		return "ls";
	}

	@Override
	public void execute(String[] args) {
		Future<?> future = executorService.submit(() -> {
			try {
				File[] files = Optional.ofNullable(directoryManager.getCurrentDirectory().listFiles())
						.orElse(new File[0]);

				synchronized (System.out) {
					if (files.length > 0) {
						System.out.println("Файлы и папки в текущей директории:");
						System.out.println(directoryContentBuilder(files));
					} else {
						System.out.println("Файлы и папки не найдены в текущей директории:");
					}
				}
			} catch (Exception e) {
				synchronized (System.out) {
					System.out.println("Произошла ошибка: " + e.getMessage());
				}
			}
		});
		
		try {
			future.get();
		} catch (Exception e) {
			synchronized (System.err) {
				System.err.println("Ошибка при выполнении команды: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private String directoryContentBuilder(File[] files) {
		String format = "%-10s %-25s %20s %10.2f КБ%n";

		return Arrays.stream(files).map(file -> {
			double lengthFile = file.length() / 1024.0;
			String type = file.isFile() ? "Файл:" : "Папка:";
			
			return String.format(format, type, file.getName(), "размер:", lengthFile);
		}).reduce(new StringBuilder(), (sb, str) -> sb.append(str), StringBuilder::append).toString();
	}

}
