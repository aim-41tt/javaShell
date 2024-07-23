package ru.example.javashells.commands.catalogues;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CrdirCommand implements Command {

	private final DirectoryManager directoryManager;
	private final ExecutorService executorService;

	public CrdirCommand(DirectoryManager directoryManager, ExecutorService executorService) {
		this.directoryManager = directoryManager;
		this.executorService = executorService;
	}

	@Override
	public String getName() {
		return "crdir";
	}

	@Override
	public void execute(String[] args) {
		Future<?> future = executorService.submit(() -> {
			if (args.length < 2) {
				System.out.println("Usage: crdir <directory>");
				return;
			}

			File directoryM = directoryManager.getCurrentDirectory();
			String directoryName = args[1];
			File newDir;

			if (directoryName.charAt(0) == '/') {
				directoryName = directoryName.substring(1, directoryName.length());
				newDir = new File(directoryM, directoryName);

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