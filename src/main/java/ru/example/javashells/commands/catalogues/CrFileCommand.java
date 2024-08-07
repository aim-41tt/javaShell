package ru.example.javashells.commands.catalogues;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CrFileCommand implements Command {

	private final DirectoryManager directoryManager;
	private final ExecutorService executorService;

	public CrFileCommand(DirectoryManager directoryManager, ExecutorService executorService) {
		this.directoryManager = directoryManager;
		this.executorService = executorService;
	}

	@Override
	public String getName() {
		return "crfile";
	}

	@Override
	public void execute(String[] args) {
		Future<?> future = executorService.submit(() -> {
			if (args.length < 2) {
				System.out.println("Usage: crfile <name file> or\n Usage: crfile <name file > initial Content> ");
				return;
			}

			String nameFile = args[1];
			File directoryM = directoryManager.getCurrentDirectory();

			if (args.length > 3) {

				StringBuilder contentBuilder = new StringBuilder();

				for (int i = 3; i < args.length; i++) {
					contentBuilder.append(args[i]);
					if (i < args.length - 1) {
						contentBuilder.append(" ");
					}
				}

				String content = contentBuilder.toString();

				// Удаляем кавычки, если они есть
				if (content.startsWith("\"") && content.endsWith("\"")) {
					content = content.substring(1, content.length() - 1);
				}

				File newFile = new File(directoryM.getAbsolutePath(), nameFile);

				try (BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(newFile), StandardCharsets.UTF_8.name()))) {

					writer.write(content);
					System.out.println("Файл успешно создан и записан.");

				} catch (IOException e) {
					System.err.println("Произошла ошибка при создании файла: " + e.getMessage());
				}

			} else {
				Path filePath = Paths.get(directoryM.getAbsolutePath(), nameFile);
				try {
					Files.createFile(filePath);
					System.out.println("Файл успешно создан.");
				} catch (IOException e) {
					System.err.println("Произошла ошибка при создании файла: " + e.getMessage());
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
}
