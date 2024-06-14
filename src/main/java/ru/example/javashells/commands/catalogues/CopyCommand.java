package ru.example.javashells.commands.catalogues;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CopyCommand implements Command {

	private DirectoryManager directoryManager;

	/**
	 * @param directoryManager
	 */
	public CopyCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

	@Override
	public String getName() {
		return "copy";
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: copy <file/dir> <directory>");
			return;
		}

		if (args.length == 3) {
			Path sourceFile = Paths.get(directoryManager.getCurrentDirectory().getAbsolutePath() + "/" + args[1]);
			Path targetDir = Paths.get(args[2]);

			try {
				if (!Files.exists(targetDir)) {
					Files.createDirectories(targetDir);
				}

				Path targetPath = targetDir.resolve(sourceFile.getFileName());

				Files.copy(sourceFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
				
				if (targetPath.toFile().isFile()) {
					System.out.println("Файл " + sourceFile.getFileName() + " успешно скопирован в " + targetPath);
				} else if (targetPath.toFile().isDirectory()) {
					System.out.println("Каталог " + sourceFile.getFileName() + " успешно скопирован в " + targetPath);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("В команде не хватает некоторых компонентов.");
		}

	}

}
