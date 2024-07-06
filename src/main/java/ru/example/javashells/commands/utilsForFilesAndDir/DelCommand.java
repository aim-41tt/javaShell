package ru.example.javashells.commands.utilsForFilesAndDir;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class DelCommand implements Command {

	private DirectoryManager directoryManager;

	public DelCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

	@Override
	public String getName() {
		return "del";
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: Del <directory or file>");
			return;
		}

		Path fileOrDirectoryPath = Paths.get(directoryManager.getCurrentDirectory().getAbsolutePath(), args[1]);
		
		try {
			deleteDirectoryRecursively(fileOrDirectoryPath);

			System.out.println("Директория или файл: " + fileOrDirectoryPath.toString() + " успешно удалены.");
		} catch (IOException e) {
			System.err.println("Ошибка при удалении директории или файла: " + e.getMessage());
		}

	}

	private void deleteDirectoryRecursively(Path path) throws IOException {
		Files.walkFileTree(path, new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
